/**
 * (C) Copyright esite Corporation 2011
 *       All Rights Reserved.
 * 2011-1-21
 * zhangzf
 * Provider.java
 * esite-web-framework
 */
package com.esite.framework.core.cache;

/**
 *<PRE>
 * 功能描述:开发者在此描述类的主要功能或目的
 * 修改历史:
 * -----------------------------------------------------------------------------
 * 		VERSION		DATE		BY			CHANGE/COMMENT
 * -----------------------------------------------------------------------------
 * 		1.0			2011-1-21	zhangzf		create
 * -----------------------------------------------------------------------------
 * </PRE>
 */
public interface Provider {
	public Object findObject(String key);
}
