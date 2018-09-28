package ru.sbrf.bh.asset.sppi.branch.orchestration;

import ru.sbrf.ufs.platform.core.exception.RuntimePlatformException;

// PROTECTED REGION ID(imports) ENABLED START
// PROTECTED REGION END
/**
 * Platform workflow handler exception for process: "MainProcess"
 * 
 * @author
 */
public class MainProcessException extends RuntimePlatformException {

	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;

	// PROTECTED REGION ID(fields) ENABLED START
	// PROTECTED REGION END

	/**
	 * Ошибка возникающая на шаге Статическая форма: &quot;StartForm&quot;
	 * {@link MainProcessHandler#startForm}
	 */
	public static final String ASBO_MAINPROCESS_STARTFORM_ERR = "ASBO_MAINPROCESS_STARTFORM_ERR";

	public MainProcessException(String code, String message, Throwable throwable) {
		super(message, throwable, code);
	}

	public MainProcessException(String code, String message) {
		super(message, code);
	}
	// PROTECTED REGION ID(methods) ENABLED START
	// PROTECTED REGION END

}