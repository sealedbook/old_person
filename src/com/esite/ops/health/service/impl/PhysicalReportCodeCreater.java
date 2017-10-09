package com.esite.ops.health.service.impl;

import com.esite.framework.util.StringHelper;
import com.esite.ops.ws.entity.UpLoadOldPersonHealthVO;

/**
 * 老年人体检报告编号生成器
 * @author Administrator
 *
 */
public class PhysicalReportCodeCreater {

	public static String create() {
		return StringHelper.createUUID().toString();
	}

}
