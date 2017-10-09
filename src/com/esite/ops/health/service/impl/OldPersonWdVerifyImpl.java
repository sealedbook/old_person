package com.esite.ops.health.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.multipart.MultipartFile;

import com.esite.framework.security.entity.Customer;
import com.esite.framework.util.JpaLikeQueryHelper;
import com.esite.framework.util.StringHelper;
import com.esite.ops.health.dao.OldPersonWdVerifyInfoDao;
import com.esite.ops.health.dao.OldPersonWdVerifyPhotoDao;
import com.esite.ops.health.entity.HealthInfoEntity;
import com.esite.ops.health.entity.OldPersonWdVerifyInfoEntity;
import com.esite.ops.health.entity.OldPersonWdVerifyInfoQueryVO;
import com.esite.ops.health.entity.OldPersonWdVerifyPhotoEntity;
import com.esite.ops.health.service.IOldPersonWdVerifyLogService;
import com.esite.ops.health.service.IOldPersonWdVerifyService;
import com.esite.ops.mission.entity.CycleEntity;
import com.esite.ops.mission.service.ICycleService;
import com.esite.ops.oldperson.entity.OldPersonEntity;
import com.esite.ops.oldperson.service.IOldPersonService;

public class OldPersonWdVerifyImpl implements IOldPersonWdVerifyService {
	@Autowired
	private OldPersonWdVerifyInfoDao oldPersonWdVerifyInfoDao;
	@Autowired
	private OldPersonWdVerifyPhotoDao oldPersonWdVerifyPhotoDao;
	@Autowired
	private IOldPersonService oldPersonService;
	@Autowired
	private IOldPersonWdVerifyLogService oldPersonWdVerifyLogService;
	@Autowired
	private ICycleService cycleService;
	
	@Override
	public void saveVerifyPhoto(String oldPersonId, MultipartFile[] photo,Customer customer) throws IOException {
		if(null == photo || photo.length <= 0) {
			throw new IllegalArgumentException("没有上传任何照片");
		}
		OldPersonEntity oldPerson = oldPersonService.getOldPerson(oldPersonId);
		if(null == oldPerson) {
			throw new IllegalArgumentException("系统中没有找到对应的老年人.");
		}
		CycleEntity cycle = cycleService.getCycle(new Date());
		if(null == cycle) {
			throw new IllegalArgumentException("给定的日期不在某个周期设定内.");
		}
		OldPersonWdVerifyInfoEntity dbOldPersonWdVerifyInfoEntity = this.oldPersonWdVerifyInfoDao.queryByOldPersonIdAndCycleId(oldPersonId,cycle.getId());
		if(null != dbOldPersonWdVerifyInfoEntity) {
			throw new IllegalArgumentException("本周期内,您已经提交过认证信息.");
		}
		OldPersonWdVerifyInfoEntity oldPersonWdVerifyInfo = new OldPersonWdVerifyInfoEntity();
		oldPersonWdVerifyInfo.setInsertDateTime(new Date());
		oldPersonWdVerifyInfo.setInsertIpAddress(customer.getIp());
		oldPersonWdVerifyInfo.setInsertUserName(customer.getUser().getShowName());
		oldPersonWdVerifyInfo.setOldPerson(oldPerson);
		oldPersonWdVerifyInfo.setVerifyState("-1");
		oldPersonWdVerifyInfo.setCycleId(cycle.getId());
		oldPersonWdVerifyInfoDao.save(oldPersonWdVerifyInfo);
		
		int photoIndex = 1;
		for(MultipartFile multipartFile : photo) {
			OldPersonWdVerifyPhotoEntity oldPersonWdVerifyPhoto = new OldPersonWdVerifyPhotoEntity();
			byte[] photoFile = multipartFile.getBytes();
			oldPersonWdVerifyPhoto.setOldPerson(oldPerson);
			oldPersonWdVerifyPhoto.setOldPersonVerifyId(oldPersonWdVerifyInfo.getId());
			oldPersonWdVerifyPhoto.setPhotoFile(photoFile);
			oldPersonWdVerifyPhoto.setPhotoPosition((photoIndex++) + "");
			oldPersonWdVerifyPhoto.setCycleId(cycle.getId());
			this.oldPersonWdVerifyPhotoDao.save(oldPersonWdVerifyPhoto);
		}
	}

	@Override
	public Page<OldPersonWdVerifyInfoEntity> getVerifyHealth(final OldPersonWdVerifyInfoQueryVO oldPersonWdVerifyInfoQueryVO,Pageable instance) {
		return oldPersonWdVerifyInfoDao.findAll(new Specification<OldPersonWdVerifyInfoEntity>() {
			@Override
			public Predicate toPredicate(Root<OldPersonWdVerifyInfoEntity> root, CriteriaQuery<?> query,CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				List<Expression<Boolean>> expressions = predicate.getExpressions();
				if(StringHelper.isNotEmpty(oldPersonWdVerifyInfoQueryVO.getVerifyState())){
					expressions.add(cb.equal(root.<String>get("verifyState"), oldPersonWdVerifyInfoQueryVO.getVerifyState()));
				}
				if(StringHelper.isNotEmpty(oldPersonWdVerifyInfoQueryVO.getOldPersonName())) {
					expressions.add(JpaLikeQueryHelper.like(cb, root.<String>get("oldPerson").<String>get("name"), oldPersonWdVerifyInfoQueryVO.getOldPersonName()));
				}
				if(StringHelper.isNotEmpty(oldPersonWdVerifyInfoQueryVO.getOldPersonIdCard())) {
					expressions.add(JpaLikeQueryHelper.like(cb, root.<String>get("oldPerson").<String>get("idCard"), oldPersonWdVerifyInfoQueryVO.getOldPersonIdCard()));
				}
				query.orderBy(cb.desc(root.get("id")));
				return predicate;
			}
		}, instance);
	}

	@Override
	public OldPersonWdVerifyInfoEntity getVerifyInfoById(String id) {
		return this.oldPersonWdVerifyInfoDao.findOne(id);
	}

	@Override
	public String getNextVerifyInfoId(String verifyId) {
		Map<String,Object> oldPersonWdVerifyInfo = this.oldPersonWdVerifyInfoDao.queryNextVerifyInfo(verifyId);
		if(null == oldPersonWdVerifyInfo) {
			return "";
		}
		if(null == oldPersonWdVerifyInfo.get("ID")) {
			return "";
		}
		return oldPersonWdVerifyInfo.get("ID").toString();
	}

	@Override
	public List<OldPersonWdVerifyPhotoEntity> getPhotoCollectionByVerifyId(String id) {
		return this.oldPersonWdVerifyPhotoDao.queryByOldPersonVerifyId(id);
	}

	@Override
	public void updateVerifyState(String verifyId, String verifyState,Customer customer) {
		// TODO Auto-generated method stub
		OldPersonWdVerifyInfoEntity oldPersonWdVerifyInfo = this.oldPersonWdVerifyInfoDao.findOne(verifyId);
		oldPersonWdVerifyInfo.setVerifyState(verifyState);
		this.oldPersonWdVerifyInfoDao.save(oldPersonWdVerifyInfo);
		oldPersonWdVerifyLogService.log(verifyId,verifyState,customer);
	}

	@Override
	public OldPersonWdVerifyPhotoEntity getFacePhotoById(String id) {
		return this.oldPersonWdVerifyPhotoDao.findOne(id);
	}

	@Override
	public Page<OldPersonWdVerifyInfoEntity> getVerifyHealthByOldPersonId(String oldPersonId, Pageable instance) {
		return this.oldPersonWdVerifyInfoDao.queryVerifyHealthByOldPersonId(oldPersonId,instance);
	}

	@Override
	public OldPersonWdVerifyInfoEntity getFirstHealthByOldPersonId(String oldPersonId) {
		List<OldPersonWdVerifyInfoEntity> list = this.oldPersonWdVerifyInfoDao.queryFirstVerifyHealthByOldPersonId(oldPersonId);
		if(null == list || list.size() <= 0) {
			return null;
		}
		return list.get(0);
	}
}
