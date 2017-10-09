package com.esite.ops.operator.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;

import com.esite.framework.organize.entity.OrganizeViewEntity;
import com.esite.framework.organize.service.OrganizeService;
import com.esite.ops.operator.dao.AreaConfigDao;
import com.esite.ops.operator.entity.AreaConfigEntity;
import com.esite.ops.operator.entity.OperatorEntity;
import com.esite.ops.operator.service.IAreaConfigService;
import com.esite.ops.operator.service.IOperatorService;

public class AreaConfigServiceImpl implements IAreaConfigService {

	@Autowired
	private OrganizeService organizeService;
	
	@Autowired
	private AreaConfigDao areaConfigDao;
	
	@Autowired
	private IOperatorService operatorService;
	
//	@Override
//	public void saveOrUpdateOperatorInAreaConfig(String operatorId, String[] areaIdArray) {
//		areaConfigDao.removeConfigByOperatorId(operatorId);
//		for(String areaId : areaIdArray) {
//			OrganizeViewEntity organizeEntity = organizeService.getOrganizeById(areaId);
//			if(organizeEntity.isLeaf()) {
//				AreaConfigEntity config = areaConfigDao.findByAreaIdAndOperatorIdNot(areaId,operatorId);
//				AreaConfigEntity areaConfigEntity = new AreaConfigEntity();
//				areaConfigEntity.setAreaId(areaId);
//				areaConfigEntity.setOperatorId(operatorId);
//				areaConfigDao.save(areaConfigEntity);
//				if(null == config) {
//					AreaConfigEntity areaConfigEntity = new AreaConfigEntity();
//					areaConfigEntity.setAreaId(areaId);
//					areaConfigEntity.setOperatorId(operatorId);
//					areaConfigDao.save(areaConfigEntity);
//				} else {
//					OperatorEntity operatorEntity = operatorService.getOperator(config.getOperatorId());
//					String operatorName = (null == operatorEntity?"" : operatorEntity.getName());
//					throw new IllegalArgumentException("地区【" + organizeEntity.getName() + "】已经分配给操作员【" + operatorName + "】");
//				}
//			}
//		}
//	}
	
	@Override
	public void saveOrUpdateOperatorInAreaConfig(String operatorId, String[] areaIdArray) {
		areaConfigDao.removeConfigByOperatorId(operatorId);
		for(String areaId : areaIdArray) {
			OrganizeViewEntity organizeEntity = organizeService.getOrganizeById(areaId);
			if(organizeEntity.isLeaf()) {
				AreaConfigEntity areaConfigEntity = new AreaConfigEntity();
				areaConfigEntity.setAreaId(areaId);
				areaConfigEntity.setOperatorId(operatorId);
				areaConfigDao.save(areaConfigEntity);
			}
		}
	}

	@Override
	public List<String> getAreaIdCollectionWithOperatorId(String operatorId) {
		// TODO Auto-generated method stub
		return this.areaConfigDao.findAreaIdByOperatorId(operatorId);
	}

}
