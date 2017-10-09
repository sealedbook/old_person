package com.esite.ops.oldperson.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.esite.framework.security.entity.Customer;
import com.esite.ops.oldperson.entity.OldPersonEntity;
import com.esite.ops.oldperson.entity.OldPersonQueryEntity;
import com.esite.ops.operator.entity.OperatorEntity;

public interface IOldPersonService {

	/**
	 * 多条件查询老年人
	 * @param oldPersonQueryEntity 查询条件
	 * @param instance 分页对象
	 * @return
	 */
	public Page<OldPersonEntity> find(OldPersonQueryEntity oldPersonQueryEntity,Pageable instance);

	/**
	 * 添加一个新的老年人
	 * @param oldPerson 老年人实体对象
	 * @param customer 系统操作人员
	 * @return
	 */
	public OldPersonEntity addNew(OldPersonEntity oldPerson,Customer customer);

	/**
	 * 以Excel的方式导入老年人信息
	 * @param file 
	 * @param customer
	 * @return
	 */
	public List<String> importOldPersonExcelFile(MultipartFile file,Customer customer);

	/**
	 * 根据老年人ID 获得老年人实体对象
	 * @param oldPersonId
	 * @return
	 */
	public OldPersonEntity getOldPerson(String oldPersonId);
	
	/**
	 * 根据地区获得老年人数量，当参数为空时，获得全系统老年人数量
	 * @param areaId
	 * @return
	 */
	public long count(String areaId);

	/**
	 * 更新老年人信息
	 * @param oldPerson
	 * @param customer
	 * @return
	 */
	public OldPersonEntity update(OldPersonEntity oldPerson, Customer customer);
	
	/**
	 * 更新老年人信息ForHttpRequest
	 * @param oldPerson
	 * @param customer
	 * @return
	 */
	public OldPersonEntity updateForHttpRequest(OldPersonEntity oldPerson, Customer customer);

	/**
	 * 获得某个地区的所有老年人
	 * @param operatorEntity
	 * @return
	 */
	public List<OldPersonEntity> getOldPersonWithArea(List<String> areaCollection);
	
	/**
	 * 获得某个地区的本地老年人
	 * @param operatorEntity
	 * @return
	 */
	public List<OldPersonEntity> getLocalOldPersonWithArea(List<String> areaCollection);
	
	/**
	 * 获得某个地区的本地老年人
	 * @param areaCollection
	 * @param instance
	 * @return
	 */
	public Page<OldPersonEntity> getLocalOldPersonWithArea(List<String> areaCollection, Pageable instance);

	/**
	 * 删除某个老年人
	 * @param oldPersonId
	 * @param customer
	 */
	public void delete(String oldPersonId, Customer customer);

	/**
	 * 标记某位老年人已经死亡
	 * @param oldPersonId
	 * @param customer
	 */
	public void died(String oldPersonId, Customer customer);

	/**
	 * 更新老年人照片
	 * @param id
	 * @param photo
	 */
	public void updatePhoto(String id, byte[] photo,Customer customer);

	/**
	 * 获得指定区域下的正常状态的老年人数量(除死亡、删除外的老年人)
	 * @param areaIdCollection
	 * @return
	 */
	public long count(List<String> areaIdCollection);

	/**
	 * 根据操作员的身份证号，获得对应操作员的所有本地老年人状态信息
	 * <br/>
	 * 该接口仅提供给终端应用
	 * @param idCard
	 * @return
	 */
	public List<Map<String, Object>> getLocalOldPersonWithOperatorIdCard(String idCard);
	
	/**
	 * 根据操作员的身份证号，获得对应操作员的已完成认证的老年人信息
	 * <br/>
	 * 该接口仅提供给终端应用
	 * @param idCard
	 * @return
	 */
	public List<Map<String, Object>> getFinishAuthOldPersonWithOperatorIdCard(String idCard);

	public OldPersonEntity getOldPersonWithIdCard(String idCard);

	/**
	 * 撤销老年人的死亡状态、删除状态
	 * @param oldPersonId
	 * @param customer
	 */
	public void undo(String oldPersonId, Customer customer);

}
