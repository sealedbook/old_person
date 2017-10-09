package com.esite.framework.core.exception;


public class DAOException extends CatchedException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5935167937413542509L;

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
	public DAOException() {
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
	public DAOException(int errorCode, String s, Throwable e) {
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
	public DAOException(int errorCode, String s) {
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
	public DAOException(int errorCode) {
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
	public DAOException(String s, Throwable e) {
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
	public DAOException(String s) {
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
	public DAOException(Throwable e) {
		super(e);
		// TODO Auto-generated constructor stub
	}

	
}
