package com.esite.ops.operator.service;

import java.util.List;

public interface IAreaConfigService {
	
	/**
	 * 新增或更新操作员所管辖的地区
	 * @param operatorId 操作员唯一编号
	 * @param areaIdArray 管辖地区编号集合
	 */
	public void saveOrUpdateOperatorInAreaConfig(String operatorId,String[] areaIdArray);
	
	/**
	 * 获得操作员所管辖的所有地区id集合
	 * @param operatorId
	 * @return
	 */
	public List<String> getAreaIdCollectionWithOperatorId(String operatorId);
	
}
