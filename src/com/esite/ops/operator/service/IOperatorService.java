package com.esite.ops.operator.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.esite.framework.security.entity.Customer;
import com.esite.ops.operator.entity.OperatorEntity;
import com.esite.ops.operator.entity.OperatorQueryEntity;

public interface IOperatorService {

	public OperatorEntity addNew(OperatorEntity operatorEntity,String[] manageAreaArray, Customer customer);
	
	public Page<OperatorEntity> find(OperatorQueryEntity operatorQueryEntity,Pageable instance);

	public OperatorEntity getOperator(String operatorId);
	
	public Iterable<OperatorEntity> getAll();
	
	public Iterable<OperatorEntity> getOperatorInArea(List<String> areaIdCollection);

	public OperatorEntity getOperatorByIdentityCard(String identityCard);
	
	public long count(String areaId);

	public long countOldPersonByOperatorIdCard(String idCard);

	public void delete(String operatorId,Customer customer);

	public void update(OperatorEntity operatorEntity, String[] manageAreaArray,
			Customer customer);

	public void undo(String operatorId, Customer customer);

	public List<Map<String, Object>> workSchedule(String cycleId);

	/**
	 * 根据操作员id更新操作员的头像
	 * @param photo
	 * @param customer
	 */
	public void uploadPhoto(MultipartFile photo, String operatorIdCard) throws IOException;
	
	public void uploadPhoto(MultipartFile photo, OperatorEntity operator) throws IOException;

	public byte[] downloadPhoto(String idCard);


}
