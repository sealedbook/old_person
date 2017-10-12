package com.esite.ops.statistics.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esite.framework.organize.entity.OrganizeViewEntity;
import com.esite.ops.mission.entity.CycleEntity;
import com.esite.ops.statistics.dao.StatisticsDao;

@Service("statisticsService")
public class StatisticsService {

	@Autowired
	private StatisticsDao statisticsDao;

	/**
	 * 认证百分比统计(镇级别)
	 * @param cycleBegin
	 * @param cycleEnd
	 * @return
	 */
	public List<Map<String, Object>> statisticsRzbfb(CycleEntity cycle) {
		return statisticsDao.rzbfb(cycle.getId());
	}

	/**
	 * 认证百分比统计(村级和社区级别)
	 * @param cycleBegin
	 * @param cycleEnd
	 * @return
	 */
	public List<Map<String, Object>> statisticsRzbfb(CycleEntity cycle,String areaId) {
		return statisticsDao.rzbfb(cycle.getId(), areaId);
	}

	/**
	 * 监控认证统计
	 * @param cycleBegin
	 * @param cycleEnd
	 * @return
	 */
	public List<Map<String, Object>> statisticsJkrz(CycleEntity cycle) {
		return statisticsDao.jkrz(cycle.getId());
	}

	/**
	 * 概括统计
	 * @param cycle
	 * @param organizeViewEntity
	 * @return
	 */
	public Map<String, Object> statisticsGktj(CycleEntity cycle,OrganizeViewEntity organizeViewEntity) {
		if(organizeViewEntity.getId().equals("1")) {
			return statisticsDao.gktj(cycle.getId());
		} else if(organizeViewEntity.isLeaf()) {
			return statisticsDao.gktjIsLeaf(cycle.getId(),organizeViewEntity.getId());
		} else {
			return statisticsDao.gktjIsNotLeaf(cycle.getId(),organizeViewEntity.getId());
		}
		
	}

	/**
	 * 本地老年人已认证占比
	 * @param cycle
	 * @param organizeViewEntity
	 * @return
	 */
	public Map<String, Object> statisticsBdyrz(CycleEntity cycle,OrganizeViewEntity organizeViewEntity) {
		if(organizeViewEntity.getId().equals("1")) {
			return statisticsDao.bdyrzzb(cycle.getId());
		} else if(organizeViewEntity.isLeaf()) {
			return statisticsDao.bdyrzzbIsLeaf(cycle.getId(),organizeViewEntity.getId());
		} else {
			return statisticsDao.bdyrzzbIsNotLeaf(cycle.getId(),organizeViewEntity.getId());
		}
	}

	/**
	 * 外地老年人已认证占比
	 * @param cycle
	 * @param organizeViewEntity
	 * @return
	 */
	public Map<String, Object> statisticsWdyrz(CycleEntity cycle,OrganizeViewEntity organizeViewEntity) {
		if(organizeViewEntity.getId().equals("1")) {
			return statisticsDao.wdyrzzb(cycle.getId());
		} else if(organizeViewEntity.isLeaf()) {
			return statisticsDao.wdyrzzbIsLeaf(cycle.getId(),organizeViewEntity.getId());
		} else {
			return statisticsDao.wdyrzzbIsNotLeaf(cycle.getId(),organizeViewEntity.getId());
		}
	}
	
	public Map<String, Object> statisticsBdGktj(CycleEntity cycle,OrganizeViewEntity organizeViewEntity) {
		if(organizeViewEntity.getId().equals("1")) {
			return statisticsDao.bdgktj(cycle.getId());
		} else if(organizeViewEntity.isLeaf()) {
			return statisticsDao.bdgktjIsLeaf(cycle.getId(),organizeViewEntity.getId());
		} else {
			return statisticsDao.bdgktjIsNotLeaf(cycle.getId(),organizeViewEntity.getId());
		}
		
	}

	/**
	 * 用户占比统计
	 * @param cycle
	 * @param organizeViewEntity
	 * @return
	 */
	public Map<String, Object> statisticsYhzb(CycleEntity cycle,OrganizeViewEntity organizeViewEntity) {
		if(organizeViewEntity.getId().equals("1")) {
			return statisticsDao.yhzbtj();
		} else if(organizeViewEntity.isLeaf()) {
			return statisticsDao.yhzbIsLeaf(organizeViewEntity.getId());
		} else {
			return statisticsDao.yhzbIsNotLeaf(organizeViewEntity.getId());
		}
	}

}
