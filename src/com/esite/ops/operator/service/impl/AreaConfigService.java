package com.esite.ops.operator.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esite.framework.organize.entity.OrganizeViewEntity;
import com.esite.framework.organize.service.impl.OrganizeService;
import com.esite.ops.operator.dao.AreaConfigDao;
import com.esite.ops.operator.entity.AreaConfigEntity;

@Service("areaManageService")
public class AreaConfigService {

	@Resource
	private OrganizeService organizeService;
	
	@Autowired
	private AreaConfigDao areaConfigDao;

	/**
	 * 新增或更新操作员所管辖的地区
	 * @param operatorId 操作员唯一编号
	 * @param areaIdArray 管辖地区编号集合
	 */
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

	/**
	 * 获得操作员所管辖的所有地区id集合
	 * @param operatorId
	 * @return
	 */
	public List<String> getAreaIdCollectionWithOperatorId(String operatorId) {
		// TODO Auto-generated method stub
		return this.areaConfigDao.findAreaIdByOperatorId(operatorId);
	}

}
