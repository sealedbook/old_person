package com.esite.ops.statistics.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.esite.framework.organize.entity.OrganizeViewEntity;
import com.esite.ops.mission.entity.CycleEntity;

public interface IStatisticsService {

	/**
	 * 认证百分比统计(镇级别)
	 * @param cycleBegin
	 * @param cycleEnd
	 * @return
	 */
	public List<Map<String, Object>> statisticsRzbfb(CycleEntity cycle);

	/**
	 * 认证百分比统计(村级和社区级别)
	 * @param cycleBegin
	 * @param cycleEnd
	 * @return
	 */
	public List<Map<String, Object>> statisticsRzbfb(CycleEntity cycle,String areaId);
	
	/**
	 * 监控认证统计
	 * @param cycleBegin
	 * @param cycleEnd
	 * @return
	 */
	public List<Map<String, Object>> statisticsJkrz(CycleEntity cycle);

	/**
	 * 概括统计
	 * @param cycle
	 * @param organizeViewEntity
	 * @return
	 */
	public Map<String, Object> statisticsGktj(CycleEntity cycle,OrganizeViewEntity organizeViewEntity);
	
	public Map<String, Object> statisticsBdGktj(CycleEntity cycle,OrganizeViewEntity organizeViewEntity);

	/**
	 * 用户占比统计
	 * @param cycle
	 * @param organizeViewEntity
	 * @return
	 */
	public Map<String, Object> statisticsYhzb(CycleEntity cycle,OrganizeViewEntity organizeViewEntity);

	/**
	 * 本地老年人已认证占比
	 * @param cycle
	 * @param organizeViewEntity
	 * @return
	 */
	public Map<String, Object> statisticsBdyrz(CycleEntity cycle,OrganizeViewEntity organizeViewEntity);

	
	/**
	 * 外地老年人已认证占比
	 * @param cycle
	 * @param organizeViewEntity
	 * @return
	 */
	public Map<String, Object> statisticsWdyrz(CycleEntity cycle,OrganizeViewEntity organizeViewEntity);
}
