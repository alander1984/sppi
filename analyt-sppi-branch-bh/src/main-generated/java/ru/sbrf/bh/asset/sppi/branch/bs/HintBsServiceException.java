package ru.sbrf.bh.asset.sppi.branch.bs;

import ru.sbrf.ufs.platform.core.exception.RuntimePlatformException;

// PROTECTED REGION ID(imports) ENABLED START
// PROTECTED REGION END
/**
 * Platform workflow handler exception for service: "HintBsService"
 * 
 * @author
 */
public class HintBsServiceException extends RuntimePlatformException {

	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;

	// PROTECTED REGION ID(fields) ENABLED START
	// PROTECTED REGION END

	/**
	 * При вызове метода получения подсказок get возникла ошибка.
	 */
	public static final String GET_ERROR = "GET_ERROR";

	public HintBsServiceException(String code, String message, Throwable throwable) {
		super(message, throwable, code);
	}

	public HintBsServiceException(String code, String message) {
		super(message, code);
	}
	// PROTECTED REGION ID(methods) ENABLED START
	// PROTECTED REGION END

}