package ru.sbrf.bh.asset.sppi.branch.bs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sbrf.bh.AbstractBusiness;
import ru.sbrf.ufs.platform.core.json.DefaultJsonMapperLocator;

// PROTECTED REGION ID(imports) ENABLED START
// PROTECTED REGION END
/**
 * Bs-service for not qualified common operations.<br>
 * Not yet qualified.
 * 
 * @author
 */
public class CommonBsService extends AbstractBusiness {

	/**
	 * Initialization of logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(CommonBsService.class);

	// PROTECTED REGION ID(fields) ENABLED START
	// PROTECTED REGION END

	/**
	 * Platform JSON mapper
	 */
	private static transient final ObjectMapper MAPPER = new DefaultJsonMapperLocator().getMapper();

	/**
	 * Returns JSON string from object
	 */
	public String toJsonString(Object object) {
		try {
			return MAPPER.writeValueAsString(object);
		} catch (Exception e) {
			LOGGER.error("Error toJsonString", e);
			throw new CommonBsServiceException(CommonBsServiceException.ASBB_COMMON_TOJSONSTRING_ERR,
					"Error toJsonString", e);
		}
	}

	// PROTECTED REGION ID(methods) ENABLED START
	// PROTECTED REGION END

}