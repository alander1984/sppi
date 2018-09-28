package ru.sbrf.bh.asset.sppi.branch.bs;

import ru.sbrf.ufs.platform.core.exception.RuntimePlatformException;

// PROTECTED REGION ID(imports) ENABLED START
// PROTECTED REGION END
/**
 * Platform workflow handler exception for service: "CommonBsService"
 * 
 * @author
 */
public class CommonBsServiceException extends RuntimePlatformException {

	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;

	// PROTECTED REGION ID(fields) ENABLED START
	// PROTECTED REGION END

	/**
	 * Возникла ошибка при вызове операции toJsonString (Returns JSON string
	 * from object) сервиса CommonBsService (Bs-service for not qualified common
	 * operations.&lt;br&gt; Not yet qualified.).
	 * {@link CommonBsService#toJsonString}
	 */
	public static final String ASBB_COMMON_TOJSONSTRING_ERR = "ASBB_COMMON_TOJSONSTRING_ERR";

	public CommonBsServiceException(String code, String message, Throwable throwable) {
		super(message, throwable, code);
	}

	public CommonBsServiceException(String code, String message) {
		super(message, code);
	}
	// PROTECTED REGION ID(methods) ENABLED START
	// PROTECTED REGION END

}