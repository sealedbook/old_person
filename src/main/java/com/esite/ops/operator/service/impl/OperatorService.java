package com.esite.ops.operator.service.impl;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.esite.framework.file.entity.SysFileInfo;
import com.esite.framework.file.service.impl.FileService;
import com.esite.framework.security.entity.Customer;
import com.esite.framework.util.IdentityCardHelper;
import com.esite.framework.util.JpaLikeQueryHelper;
import com.esite.framework.util.StringHelper;
import com.esite.ops.oldperson.service.impl.OldPersonService;
import com.esite.ops.operator.dao.OperatorDao;
import com.esite.ops.operator.entity.OperatorEntity;
import com.esite.ops.operator.entity.OperatorQueryEntity;

@Service("operatorService")
public class OperatorService {

	@Autowired
	private OperatorDao operatorDao;
	
	@Resource
	private AreaConfigService areaManageService;
	
	@Resource
	private OperatorSecurityService operatorSecurityService;
	
	@Resource
	private OldPersonService oldPersonService;

	@Resource
	private OperatorOperationLogService operatorOperationLogService;
	@Resource
	private FileService fileService;
	
	private void checkOperator(OperatorEntity operator) {
		String idCard = operator.getIdCard();
		OperatorEntity dbOperator = this.getOperatorByIdentityCard(idCard);
		if(null == dbOperator) {
			return;
		}
		String status = dbOperator.getStatus();
		if(StringHelper.isEmpty(status)) {
			throw new IllegalArgumentException("身份证号【" + dbOperator.getIdCard() + "】的操作员已经存在.");
		} else if("delete".equals(status)) {
			throw new IllegalArgumentException("身份证号【" + dbOperator.getIdCard() + "】的操作员已经存在,但目前被标记为删除状态.");
		}
	}

	@Transactional
	public OperatorEntity addNew(OperatorEntity operatorEntity,String[] manageAreaArray,Customer customer) {
		checkOperator(operatorEntity);
		String idCard = operatorEntity.getIdCard();
		operatorEntity.setSex(IdentityCardHelper.getSex(idCard));
		operatorEntity.setBirthday(IdentityCardHelper.getBirthday(idCard));
		operatorEntity.setManageArea(Arrays.toString(manageAreaArray));
		try {
			this.operatorDao.save(operatorEntity);
			this.operatorSecurityService.addSystemUser(operatorEntity);
			areaManageService.saveOrUpdateOperatorInAreaConfig(operatorEntity.getId(), manageAreaArray);
			//记录操作记录,customer
			operatorOperationLogService.addLog(operatorEntity, customer);
			return operatorEntity;
		} catch(JpaSystemException e) {
			throw new IllegalArgumentException("身份证号【" + operatorEntity.getIdCard() + "】的操作员已经存在");
		} catch(IllegalArgumentException e) {
			throw e;
		} catch(Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Transactional
	public void update(OperatorEntity operatorEntity, String[] manageAreaArray,Customer customer) {
		checkOperator(operatorEntity);
		String idCard = operatorEntity.getIdCard();
		operatorEntity.setSex(IdentityCardHelper.getSex(idCard));
		operatorEntity.setBirthday(IdentityCardHelper.getBirthday(idCard));
		operatorEntity.setManageArea(Arrays.toString(manageAreaArray));
		try {
			this.operatorDao.save(operatorEntity);
			areaManageService.saveOrUpdateOperatorInAreaConfig(operatorEntity.getId(), manageAreaArray);
		} catch(JpaSystemException e) {
			throw new IllegalArgumentException("身份证号【" + operatorEntity.getIdCard() + "】的操作员已经存在");
		} catch(IllegalArgumentException e) {
			throw e;
		} catch(Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public Page<OperatorEntity> find(final OperatorQueryEntity operatorQueryEntity,Pageable instance) {
		return operatorDao.findAll(new Specification<OperatorEntity>() {
			@Override
			public Predicate toPredicate(Root<OperatorEntity> root, CriteriaQuery<?> query,CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				List<Expression<Boolean>> expressions = predicate.getExpressions();
				if(!"czy_ok".equals(operatorQueryEntity.getStatus())) {
					expressions.add(cb.equal(root.<String>get("status"), operatorQueryEntity.getStatus()));
				}
				if(StringHelper.isNotEmpty(operatorQueryEntity.getIdCard())){
					expressions.add(cb.like(root.<String>get("idCard"), "%"+operatorQueryEntity.getIdCard()+"%"));
				}
				if(StringHelper.isNotEmpty(operatorQueryEntity.getAreaId())){
					expressions.add(cb.like(root.<String>get("manageArea"), "%"+operatorQueryEntity.getAreaId()+"%"));
				}
				if(StringHelper.isNotEmpty(operatorQueryEntity.getName())){
					expressions.add(JpaLikeQueryHelper.like(cb, root.<String>get("name"), operatorQueryEntity.getName()));
				}
				if(-1 != operatorQueryEntity.getSex()){
					expressions.add(cb.equal(root.<String>get("sex"), operatorQueryEntity.getSex()));
				}
				query.orderBy(cb.desc(root.get("sysInsertDatetime")));
				return predicate;
			}
		}, instance);
	}

	public OperatorEntity getOperator(String operatorId) {
		return this.operatorDao.findOne(operatorId);
	}

	public Iterable<OperatorEntity> getAll() {
		return operatorDao.findAll();
	}

	public Iterable<OperatorEntity> getOperatorInArea(List<String> areaIdCollection) {
		return operatorDao.queryOperatorInArea(areaIdCollection);
	}

	public OperatorEntity getOperatorByIdentityCard(String identityCard) {
		return operatorDao.queryOperatorByIdCard(identityCard);
	}

	public long count(String areaId) {
		if(StringHelper.isEmpty(areaId)) {
			return this.operatorDao.count();
		}
		return this.operatorDao.countByManageAreaLike("%" + areaId + "%");
	}

	public long countOldPersonByOperatorIdCard(String idCard) {
		OperatorEntity operator = this.getOperatorByIdentityCard(idCard);
		List<String> areaIdCollection = this.areaManageService.getAreaIdCollectionWithOperatorId(operator.getId());
		return this.oldPersonService.count(areaIdCollection);
	}

	@Transactional
	public void delete(String operatorId,Customer customer) {
		OperatorEntity operator = this.getOperator(operatorId);
		operator.setStatus("delete");
		this.operatorDao.save(operator);
		
		operatorOperationLogService.deleteLog(operator, customer);
		
		this.operatorSecurityService.deleteSystemUser(operator);
	}
	
	@Transactional
	public void undo(String operatorId, Customer customer) {
		OperatorEntity operator = this.getOperator(operatorId);
		operator.setStatus("");
		this.operatorDao.save(operator);
		
		operatorOperationLogService.undoLog(operator, customer);
		
		this.operatorSecurityService.addSystemUser(operator);
	}

	public List<Map<String, Object>> workSchedule(String cycleId) {
		if(StringHelper.isEmpty(cycleId)) {
			return null;
		}
		return this.operatorDao.workSchedule(cycleId);
	}

	/**
	 * 根据操作员id更新操作员的头像
	 * @param photo
	 */
	public void uploadPhoto(MultipartFile photo, String operatorIdCard) throws IOException {
		OperatorEntity operator = this.getOperatorByIdentityCard(operatorIdCard);
		if(null == operator) {
			throw new IllegalArgumentException("系统中没有找到对应的操作员,本次查询操作员身份证号【" + operatorIdCard + "】");
		}
		SysFileInfo sysFileInfo = fileService.save(photo.getBytes());
		operator.setPhotoKey(sysFileInfo.getFileKey());
		this.operatorDao.save(operator);
	}
	
	public void uploadPhoto(MultipartFile photo, OperatorEntity operator) throws IOException {
		SysFileInfo sysFileInfo = fileService.save(photo.getBytes());
		operator.setPhotoKey(sysFileInfo.getFileKey());
		this.operatorDao.save(operator);
	}

	public byte[] downloadPhoto(String idCard) {
		OperatorEntity operator = this.getOperatorByIdentityCard(idCard);
		SysFileInfo sysFileInfo = fileService.findByFileKey(operator.getPhotoKey());
		return sysFileInfo.getContent();
	}

}
