package com.esite.ops.health.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.esite.framework.security.entity.Customer;
import com.esite.framework.user.entity.User;
import com.esite.ops.health.entity.HealthInfoEntity;
import com.esite.ops.health.vo.HealthInfoQueryVO;
import com.esite.ops.mission.entity.CycleEntity;
import com.esite.ops.ws.entity.UpLoadDataVO;

public interface IHealthInfoService {

	void addNew(User operatorUser,String ipAddress,UpLoadDataVO upLoadDataVO,String importBatchId,String logId);

	Page<HealthInfoEntity> getVerifyHealth(HealthInfoQueryVO healthQueryVO,Pageable instance);

	HealthInfoEntity getHealth(String healthId);

	Map<String, Object> statisticsHealthInfoByOperatorIdCard(String idCard);

	void updateVerifyState(String healthId, String healthFingerprintId, String verifyState, Customer customer);

	/**
	 * 获得指定老年人的体检记录
	 * @param oldPersonId
	 * @param instance
	 * @return
	 */
	Page<HealthInfoEntity> getHealthByOldPersonId(String oldPersonId,
			Pageable instance);

	String getNextHealthId(String healthId);
	
	String getNextHealthId(String healthId, String verifyState, String oldPersonId, String operatorId);

	HealthInfoEntity getLastHealthByOldPersonId(String oldPersonId);

	int getHealthNumberByOldPerson(String healthId, String oldPersonId);

	boolean hasHealthInCycleByOldPersonId(String oldPersonId, CycleEntity cycle);

	HealthInfoEntity getFirstHealthByOldPersonId(String oldPersonId);

	List<HealthInfoEntity> getAllHealthInfoByCycleId(String cycleId);

}
