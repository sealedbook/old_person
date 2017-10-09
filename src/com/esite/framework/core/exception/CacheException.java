package com.esite.framework.core.exception;


public class CacheException extends CatchedException {

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
	public CacheException() {
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
	public CacheException(int errorCode, String s, Throwable e) {
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
	public CacheException(int errorCode, String s) {
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
	public CacheException(int errorCode) {
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
	public CacheException(String s, Throwable e) {
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
	public CacheException(String s) {
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
	public CacheException(Throwable e) {
		super(e);
		// TODO Auto-generated constructor stub
	}

	
}
