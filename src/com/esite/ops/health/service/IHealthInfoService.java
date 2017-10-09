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

	public void addNew(User operatorUser,String ipAddress,UpLoadDataVO upLoadDataVO,String importBatchId,String logId);

	public Page<HealthInfoEntity> getVerifyHealth(HealthInfoQueryVO healthQueryVO,Pageable instance);

	public HealthInfoEntity getHealth(String healthId);

	public Map<String, Object> statisticsHealthInfoByOperatorIdCard(String idCard);

	public void updateVerifyState(String healthId, String healthFingerprintId, String verifyState, Customer customer);

	/**
	 * 获得指定老年人的体检记录
	 * @param oldPersonId
	 * @param instance
	 * @return
	 */
	public Page<HealthInfoEntity> getHealthByOldPersonId(String oldPersonId,
			Pageable instance);

	public String getNextHealthId(String healthId);
	
	public String getNextHealthId(String healthId, String verifyState, String oldPersonId, String operatorId);

	public HealthInfoEntity getLastHealthByOldPersonId(String oldPersonId);

	public int getHealthNumberByOldPerson(String healthId, String oldPersonId);

	public boolean hasHealthInCycleByOldPersonId(String oldPersonId, CycleEntity cycle);

	public HealthInfoEntity getFirstHealthByOldPersonId(String oldPersonId);

	public List<HealthInfoEntity> getAllHealthInfoByCycleId(String cycleId);

	//SELECT @rowno:=@rowno+1 as rowno,r.* from health_info r,(select @rowno:=0) t
	//public int getHealthFrequency(String oldPerson,String healthId);

}
