/*
 * Created on 2005-5-12
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.esite.framework.core.exception;

/**
 * @author zhangzf
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class UncatchedException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1731413039567769826L;

	/**
	 *<PRE>
	 * 功能描述:开发者在此描述类的主要功能或目的
	 * 修改历史:
	 * -----------------------------------------------------------------------------
	 * 		VERSION		DATE		BY			CHANGE/COMMENT
	 * -----------------------------------------------------------------------------
	 * 		1.0			2010-6-10	zhangzf		create
	 * -----------------------------------------------------------------------------
	 * </PRE>
	 */
	public UncatchedException() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 *<PRE>
	 * 功能描述:开发者在此描述类的主要功能或目的
	 * @param errorCode
	 * @param s
	 * @param e
	 * 修改历史:
	 * -----------------------------------------------------------------------------
	 * 		VERSION		DATE		BY			CHANGE/COMMENT
	 * -----------------------------------------------------------------------------
	 * 		1.0			2010-6-10	zhangzf		create
	 * -----------------------------------------------------------------------------
	 * </PRE>
	 */
	public UncatchedException(int errorCode, String s, Throwable e) {
		super(errorCode, s, e);
		// TODO Auto-generated constructor stub
	}

	/**
	 *<PRE>
	 * 功能描述:开发者在此描述类的主要功能或目的
	 * @param errorCode
	 * @param s
	 * 修改历史:
	 * -----------------------------------------------------------------------------
	 * 		VERSION		DATE		BY			CHANGE/COMMENT
	 * -----------------------------------------------------------------------------
	 * 		1.0			2010-6-10	zhangzf		create
	 * -----------------------------------------------------------------------------
	 * </PRE>
	 */
	public UncatchedException(int errorCode, String s) {
		super(errorCode, s);
		// TODO Auto-generated constructor stub
	}

	/**
	 *<PRE>
	 * 功能描述:开发者在此描述类的主要功能或目的
	 * @param errorCode
	 * 修改历史:
	 * -----------------------------------------------------------------------------
	 * 		VERSION		DATE		BY			CHANGE/COMMENT
	 * -----------------------------------------------------------------------------
	 * 		1.0			2010-6-10	zhangzf		create
	 * -----------------------------------------------------------------------------
	 * </PRE>
	 */
	public UncatchedException(int errorCode) {
		super(errorCode);
		// TODO Auto-generated constructor stub
	}

	/**
	 *<PRE>
	 * 功能描述:开发者在此描述类的主要功能或目的
	 * @param s
	 * @param e
	 * 修改历史:
	 * -----------------------------------------------------------------------------
	 * 		VERSION		DATE		BY			CHANGE/COMMENT
	 * -----------------------------------------------------------------------------
	 * 		1.0			2010-6-10	zhangzf		create
	 * -----------------------------------------------------------------------------
	 * </PRE>
	 */
	public UncatchedException(String s, Throwable e) {
		super(s, e);
		// TODO Auto-generated constructor stub
	}

	/**
	 *<PRE>
	 * 功能描述:开发者在此描述类的主要功能或目的
	 * @param s
	 * 修改历史:
	 * -----------------------------------------------------------------------------
	 * 		VERSION		DATE		BY			CHANGE/COMMENT
	 * -----------------------------------------------------------------------------
	 * 		1.0			2010-6-10	zhangzf		create
	 * -----------------------------------------------------------------------------
	 * </PRE>
	 */
	public UncatchedException(String s) {
		super(s);
		// TODO Auto-generated constructor stub
	}

	/**
	 *<PRE>
	 * 功能描述:开发者在此描述类的主要功能或目的
	 * @param e
	 * 修改历史:
	 * -----------------------------------------------------------------------------
	 * 		VERSION		DATE		BY			CHANGE/COMMENT
	 * -----------------------------------------------------------------------------
	 * 		1.0			2010-6-10	zhangzf		create
	 * -----------------------------------------------------------------------------
	 * </PRE>
	 */
	public UncatchedException(Throwable e) {
		super(e);
		// TODO Auto-generated constructor stub
	}

	
}
