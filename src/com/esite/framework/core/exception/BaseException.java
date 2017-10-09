
package com.esite.framework.core.exception;

import org.apache.log4j.Logger;



/**
 * 
 * @author zhangzf
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */


public class BaseException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1033884159796034858L;
	private Logger logger = Logger.getLogger(getClass());
	private Throwable _rootCause;
	private Object[] errorArgs;
	private int errorCode = -1;

	public BaseException() {
		super();
	}
	
	public BaseException(int errorCode) {
		this(errorCode, null);
	}
	
	
	/**
	 * @param s
	 */
	public BaseException(String s) {
		this(-1, s);
	}
	
	public BaseException(int errorCode, String s) {
		super(s);
		this.errorCode = errorCode;
		_rootCause = this;
	}

	public BaseException(String s, Throwable e) {
		this(-1, s, e);
	}
	
	public BaseException(int errorCode, String s, Throwable e) {
		super(s);
		this.errorCode = errorCode;
		if (e instanceof BaseException) {
			_rootCause = ((BaseException) e)._rootCause;
		} else {
			logger.error(e.getClass(),e);
			_rootCause = this;
		}
		logger.debug(s, e);
	}

	public BaseException(Throwable e) {
		this(e.getMessage(), e);
	}

	/**
	 * @return
	 */
	public Throwable getRootCause() {
		return _rootCause;
	}
	/**
	 * 
	 * @return
	 */
	public String getDetailMessage() {
		String msg = getMessage();
		Throwable cause = getCause();
		if (cause != null && cause instanceof BaseException) {
			return msg += "\n原因: "
				+ ((BaseException) cause).getDetailMessage();
		}
		return msg;
	}

	/**
	 * 返回错误信息
	 */
	public String getMessage() {
		return super.getMessage();
	}

	public void printStackTrace() {
		printStackTrace(System.err);
	}

	/**
	 * @return the errorCode
	 */
	public int getErrorCode() {
		return errorCode;
	}
	
	

}
