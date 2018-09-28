package ru.sbrf.bh.asset.sppi.branch.orchestration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ru.sbrf.bh.Orchestration;
import ru.sbrf.bh.asset.sppi.branch.adapter.pl.startform.ShowStartFormResponseAdapter;
import ru.sbrf.bh.asset.sppi.branch.bs.CommonBsService;
import ru.sbrf.bh.asset.sppi.branch.cg.MainProcessBridge;
import ru.sbrf.bh.asset.sppi.branch.dto.pl.startform.ShowStartFormRequestDto;
import ru.sbrf.bh.asset.sppi.branch.dto.pl.startform.ShowStartFormResponseDto;

/**
 * Orchestration class declaration.
 * 
 * for process: Главный процесс
 * 
 * @author
 */
public class MainProcess implements Orchestration {

	/**
	 * Adapter between parameters of operation &lt;b&gt;showStartForm&lt;/b&gt;
	 * and &lt;b&gt;ShowStartFormResponseDto&lt;/b&gt;
	 */
	@Autowired
	@Qualifier("ru.sbrf.bh.asset.sppi.branch.adapter.pl.startform.ShowStartFormResponseAdapter")
	private transient ShowStartFormResponseAdapter showStartFormResponseAdapter;

	/**
	 * Related bridge class
	 */
	@Autowired
	@Qualifier("ru.sbrf.bh.asset.sppi.branch.cg.MainProcessBridge")
	private transient MainProcessBridge bridge;

	/**
	* 
	*/
	@Autowired
	@Qualifier("ru.sbrf.bh.asset.sppi.branch.bs.CommonBsService")
	private transient CommonBsService commonBsService;

	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Initialization of logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(MainProcess.class);

	/**
	 * Begin method
	 */
	public void begin() {
	}

	/**
	 * End method
	 */
	public void end() {
	}

	/**
	 * Статическая форма: &quot;StartForm&quot;
	 * 
	 * @param exit
	 * 
	 * @return
	 */
	public ShowStartFormResponseDto startForm(ShowStartFormRequestDto exit) {
		// PROTECTED REGION ID(startForm) DISABLED START
		if (exit != null) {
		}
		return showStartFormResponseAdapter.createDto();
		// PROTECTED REGION END
	}

	/**
	 * Returns value of <b>bridge</b> - Related bridge class This code is
	 * automatically generated. It's not recommended to modify this code.
	 * 
	 * @return Related bridge class
	 */
	public MainProcessBridge getBridge() {
		return this.bridge;
	}

	/**
	 * Defines value of <b>bridge</b> - Related bridge class This code is
	 * automatically generated. It's not recommended to modify this code.
	 * 
	 * @param bridge
	 *            Related bridge class
	 */
	public void setBridge(MainProcessBridge bridge) {
		this.bridge = bridge;
	}

}