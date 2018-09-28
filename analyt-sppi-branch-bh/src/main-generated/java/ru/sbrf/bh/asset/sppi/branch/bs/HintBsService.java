package ru.sbrf.bh.asset.sppi.branch.bs;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ru.sbrf.bh.asset.sppi.branch.bs.CommonBsService;
import ru.sbrf.ufs.platform.hints.HintRequest;
import ru.sbrf.ufs.platform.hints.HintService;
import ru.sbrf.ufs.platform.hints.impl.HintRequestImpl;
import ru.sbrf.ufs.platform.workflow.EventResult;
import ru.sbrf.ufs.platform.workflow.impl.gate.hints.HintPackage;
import ru.sbrf.ufs.platform.workflow.impl.gate.hints.HintPackageSupplier;

/**
 * Hint service. Implements integrating UFS platform workflow with hints
 * service.
 * 
 * @author
 */
public class HintBsService implements HintPackageSupplier {

	/**
	* 
	*/
	@Autowired
	@Qualifier("hintService")
	private transient HintService hintService;

	/**
	* 
	*/
	@Autowired
	@Qualifier("ru.sbrf.bh.asset.sppi.branch.bs.CommonBsService")
	private transient CommonBsService commonBsService;

	/**
	 * Initialization of logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(HintBsService.class);

	@Override
	public HintPackage get(final EventResult eventResult) {
		LOGGER.debug("Enter >>> get");
		LOGGER.debug(String.format("eventResult: flowName: %s, stateName: %s", eventResult.getFlowName(),
				eventResult.getStateName()));
		try {
			final HintRequest request = buidlRequest(eventResult);
			LOGGER.debug(String.format("request: %s", commonBsService.toJsonString(request)));
			HintPackage result = hintService.find(request);
			LOGGER.debug(String.format("result: %s", commonBsService.toJsonString(result)));
			LOGGER.debug("Return <<< get");
			return result;
		} catch (RuntimeException e) {
			LOGGER.error("Error getting hint", e);
			throw new HintBsServiceException(HintBsServiceException.GET_ERROR, "Error getting hint", e);
		}
	}

	private HintRequest buidlRequest(EventResult eventResult) {
		HintRequestImpl request = new HintRequestImpl();
		request.setBoCode(eventResult.getFlowName());
		request.setBoStepCode(eventResult.getStateName());
		return request;
	}

	/**
	 * Class wrapper for hint package
	 */
	public static class HintPackageWithLevel implements HintPackage {

		private HintPackage data;
		private String level;

		private HintPackageWithLevel(HintPackage data, String level) {
			this.data = data;
			this.level = level;
		}

		@Override
		public String getBoStepCode() {
			return data.getBoStepCode();
		}

		@Override
		public String getUserLevel() {
			return level;
		}

		@Override
		public List<?> getHints() {
			return data.getHints();
		}
	}

}