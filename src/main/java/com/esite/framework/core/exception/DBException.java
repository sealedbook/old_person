package com.esite.framework.core.exception;

public class DBException extends CatchedException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -431063866861346109L;

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
	public DBException() {
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
	public DBException(int errorCode, String s, Throwable e) {
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
	public DBException(int errorCode, String s) {
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
	public DBException(int errorCode) {
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
	public DBException(String s, Throwable e) {
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
	public DBException(String s) {
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
	public DBException(Throwable e) {
		super(e);
		// TODO Auto-generated constructor stub
	}

	

}
