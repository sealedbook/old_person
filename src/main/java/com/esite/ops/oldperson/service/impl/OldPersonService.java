package com.esite.ops.oldperson.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.esite.framework.core.exception.ServiceException;
import com.esite.framework.dictionary.entity.DictionaryEntity;
import com.esite.framework.dictionary.service.DictionaryService;
import com.esite.framework.organize.entity.OrganizeEntity;
import com.esite.framework.organize.entity.OrganizeViewEntity;
import com.esite.framework.organize.service.impl.OrganizeService;
import com.esite.framework.security.entity.Customer;
import com.esite.framework.util.ChineseHelper;
import com.esite.framework.util.IdentityCardHelper;
import com.esite.framework.util.JpaLikeQueryHelper;
import com.esite.framework.util.ReadExcel;
import com.esite.framework.util.StringHelper;
import com.esite.ops.mission.entity.CycleEntity;
import com.esite.ops.mission.service.impl.CycleService;
import com.esite.ops.oldperson.dao.OldPersonDao;
import com.esite.ops.oldperson.entity.OldPersonEntity;
import com.esite.ops.oldperson.entity.OldPersonQueryEntity;
import com.esite.ops.operator.entity.OperatorEntity;
import com.esite.ops.operator.service.impl.AreaConfigService;
import com.esite.ops.operator.service.impl.OperatorService;

@Service("oldPersonService")
public class OldPersonService {

	@Autowired
	private OldPersonDao oldPersonDao;
	@Autowired
	private DictionaryService dictionaryService;
	@Resource
	private OrganizeService organizeService;
	@Resource
	private CycleService cycleService;
	@Resource
	private OperatorService operatorService;
	@Resource
	private AreaConfigService areaManageService;
	@Resource
	private OldPersonOperationLogService oldPersonOperationLogService;
	@Resource
	private OldPersonSecurityService oldPersonSecurityService;

	/**
	 * 多条件查询老年人
	 * @param oldPersonQueryEntity 查询条件
	 * @param instance 分页对象
	 * @return
	 */
	public Page<OldPersonEntity> find(final OldPersonQueryEntity oldPersonQueryEntity, Pageable instance) {
		return oldPersonDao.findAll(new Specification<OldPersonEntity>() {
			@Override
			public Predicate toPredicate(Root<OldPersonEntity> root, CriteriaQuery<?> query,CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				List<Expression<Boolean>> expressions = predicate.getExpressions();
				if(StringHelper.isNotEmpty(oldPersonQueryEntity.getIdCard())){
					expressions.add(JpaLikeQueryHelper.like(cb, root.<String>get("idCard"), oldPersonQueryEntity.getIdCard()));
				}
				if(StringHelper.isNotEmpty(oldPersonQueryEntity.getName())){
					expressions.add(JpaLikeQueryHelper.like(cb, root.<String>get("name"), oldPersonQueryEntity.getName()));
				}
				if(StringHelper.isNotEmpty(oldPersonQueryEntity.getSocialNumber())){
					expressions.add(JpaLikeQueryHelper.like(cb, root.<String>get("socialNumber"), oldPersonQueryEntity.getSocialNumber()));
				}
				if(-1 != oldPersonQueryEntity.getSex()){
					expressions.add(cb.equal(root.<String>get("sex"),oldPersonQueryEntity.getSex()));
				}
				if(-1 != oldPersonQueryEntity.getType()){
					expressions.add(cb.equal(root.<String>get("type"),oldPersonQueryEntity.getType()));
				}
				if(StringHelper.isNotEmpty(oldPersonQueryEntity.getAreaId())){
					expressions.add(cb.equal(root.<String>get("area").get("id"), oldPersonQueryEntity.getAreaId()));
				}
				if(StringHelper.isNotEmpty(oldPersonQueryEntity.getModelingStatus())) {
					expressions.add(cb.equal(root.<String>get("modelingStatus"), oldPersonQueryEntity.getModelingStatus()));
				}
				if(-1 != oldPersonQueryEntity.getAgeRangeBegin()) {
					Calendar calendar = Calendar.getInstance();
					int subYear = 0;
					subYear -= oldPersonQueryEntity.getAgeRangeBegin();
					calendar.add(Calendar.YEAR, subYear);
					calendar.getTime();
					expressions.add(cb.lessThan(root.<Date>get("birthday"),calendar.getTime()));
				}
				
				if(-1 != oldPersonQueryEntity.getAgeRangeEnd()) {
					Calendar calendar = Calendar.getInstance();
					int subYear = 0;
					subYear -= oldPersonQueryEntity.getAgeRangeEnd();
					calendar.add(Calendar.YEAR, subYear);
					calendar.getTime();
					expressions.add(cb.greaterThan(root.<Date>get("birthday"),calendar.getTime()));
				}
				
				if(StringHelper.isNotEmpty(oldPersonQueryEntity.getAgeRange())) {
					Calendar calendar = Calendar.getInstance();
					int subYear = 0;
					String ageRange = oldPersonQueryEntity.getAgeRange();
					if("lnrAge60".equals(ageRange)) {
						subYear = -60;
					} else if("lnrAge70".equals(ageRange)) {
						subYear = -70;
					} else if("lnrAge80".equals(ageRange)) {
						subYear = -80;
					} else if("lnrAge90".equals(ageRange)) {
						subYear = -90;
					} else if("lnrAge100".equals(ageRange)) {
						subYear = -100;
					} else {
						subYear = 0;
					}
					calendar.add(Calendar.YEAR, subYear);
					calendar.getTime();
					expressions.add(cb.lessThan(root.<Date>get("birthday"),calendar.getTime()));
				}
				expressions.add(cb.equal(root.<String>get("status"), oldPersonQueryEntity.getStatus()));
				query.orderBy(cb.desc(root.get("sysInsertDatetime")));
				return predicate;
			}
		}, instance);
	}
	
	private void checkOldPerson(OldPersonEntity oldPerson) {
		String idCard = oldPerson.getIdCard();
		OldPersonEntity dbOldPerson = this.getOldPersonWithIdCard(idCard);
		if(null == dbOldPerson) {
			return;
		}
		String status = dbOldPerson.getStatus();
		if(StringHelper.isEmpty(oldPerson.getId()) && null != dbOldPerson) {
			throw new IllegalArgumentException("身份证号【" + dbOldPerson.getIdCard() + "】的老年人已经存在.");
		}
		if(StringHelper.isNotEmpty(oldPerson.getId()) && StringHelper.isEmpty(status) && !oldPerson.getId().equals(dbOldPerson.getId())) {
			throw new IllegalArgumentException("身份证号【" + dbOldPerson.getIdCard() + "】的老年人已经存在.");
		} else if("delete".equals(status)) {
			throw new IllegalArgumentException("身份证号【" + dbOldPerson.getIdCard() + "】的老年人已经存在,但目前被标记为删除状态.");
		} else if("died".equals(status)) {
			throw new IllegalArgumentException("身份证号【" + dbOldPerson.getIdCard() + "】的老年人已经存在,但目前被标记为死亡状态.");
		}
	}

	public OldPersonEntity getOldPersonWithIdCard(String idCard) {
		OldPersonEntity oldPerson = this.oldPersonDao.queryByIdCard(idCard);
		return oldPerson;
	}

	/**
	 * 添加一个新的老年人
	 * @param oldPerson 老年人实体对象
	 * @param customer 系统操作人员
	 * @return
	 */
	public OldPersonEntity addNew(OldPersonEntity oldPerson,Customer customer) {
		checkOldPerson(oldPerson);
		String idCard = oldPerson.getIdCard();
		try {
			oldPerson.setSex(IdentityCardHelper.getSex(idCard));
			oldPerson.setBirthday(IdentityCardHelper.getBirthday(idCard));
			oldPerson.setNameSpell(ChineseHelper.convertSpell(oldPerson.getName()));
			OrganizeViewEntity org = getRootArea(this.organizeService.getOrganizeById(oldPerson.getArea().getId()));
			oldPerson.setRootAreaId(org.getId());
			this.oldPersonDao.save(oldPerson);
			
			//系统中添加老年人信息,提供登陆功能
			oldPersonSecurityService.addSystemUser(oldPerson);
			//记录操作记录,customer
			oldPersonOperationLogService.addLog(oldPerson,customer);
			return oldPerson;
		} catch(JpaSystemException e) {
			throw new IllegalArgumentException("身份证号【" + oldPerson.getIdCard() + "】的老年人已经存在");
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			e.printStackTrace();
			throw new IllegalArgumentException("老年人的姓名输入可能有错.");
		}
	}

	private OrganizeViewEntity getRootArea(OrganizeViewEntity area) {
		if(null == area) {
			return null;
		}
		if("z".equals(area.getType())) {
			return area;
		}
		return getRootArea(area.getParent());
	}

	/**
	 * 更新老年人信息ForHttpRequest
	 * @param oldPerson
	 * @param customer
	 * @return
	 */
	public OldPersonEntity updateForHttpRequest(OldPersonEntity oldPerson,Customer customer) {
		this.oldPersonDao.save(oldPerson);
		return oldPerson;
	}

	/**
	 * 更新老年人信息
	 * @param oldPerson
	 * @param customer
	 * @return
	 */
	public OldPersonEntity update(OldPersonEntity oldPerson, Customer customer) {
		if(StringHelper.isEmpty(oldPerson.getId())) {
			throw new IllegalArgumentException("系统无法根据空的编号查询老年人信息");
		}
		OldPersonEntity dbPersonEntity = this.getOldPerson(oldPerson.getId());
		if(null == dbPersonEntity) {
			throw new IllegalArgumentException("系统中不存在这个老年人.本次查询的编号是【" + oldPerson.getId() + "】");
		}
		String idCard = oldPerson.getIdCard();
		checkOldPerson(oldPerson);
		try {
			oldPerson.setSex(IdentityCardHelper.getSex(idCard));
			oldPerson.setBirthday(IdentityCardHelper.getBirthday(idCard));
			oldPerson.setNameSpell(ChineseHelper.convertSpell(oldPerson.getName()));
			OrganizeViewEntity org = getRootArea(this.organizeService.getOrganizeById(oldPerson.getArea().getId()));
			oldPerson.setRootAreaId(org.getId());
			oldPerson.setFfStatus(dbPersonEntity.getFfStatus());
			oldPerson.setStatus(dbPersonEntity.getStatus());
			if(!StringUtils.hasText(oldPerson.getModelingStatus())) {
				oldPerson.setModelingStatus(dbPersonEntity.getModelingStatus());
			}
			this.oldPersonDao.save(oldPerson);
			int oldPersonType = oldPerson.getType();
			if(2 == oldPersonType) {
				//向系统中添加老年人用户信息,提供登陆功能
				oldPersonSecurityService.addSystemUser(oldPerson);
			}
			
			return oldPerson;
		} catch(JpaSystemException e) {
			throw new IllegalArgumentException("身份证号【" + oldPerson.getIdCard() + "】的老年人已经存在");
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			e.printStackTrace();
			throw new IllegalArgumentException("老年人的姓名输入可能有错.");
		} catch(IllegalArgumentException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	/**
	 * 根据老年人ID 获得老年人实体对象
	 * @param oldPersonId
	 * @return
	 */
	public OldPersonEntity getOldPerson(String oldPersonId) {
		if(StringHelper.isEmpty(oldPersonId)) {
			throw new IllegalArgumentException("老年人id不能为空.");
		}
		return this.oldPersonDao.findOne(oldPersonId);
	}

	/**
	 * 以Excel的方式导入老年人信息
	 * @param file
	 * @param customer
	 * @return
	 */
	public List<String> importOldPersonExcelFile(MultipartFile file,Customer customer) {
		List<String> errorMessage = new ArrayList<String>();
		if(null == file || file.getSize() <= 0) {
			errorMessage.add("请上传有效的文件.");
			return errorMessage;
		}
		String originalFileName = file.getOriginalFilename();
		String suffix = originalFileName.substring(originalFileName.lastIndexOf("."), originalFileName.length());
		
		if(!".xls".equalsIgnoreCase(suffix)) {
			errorMessage.add("请上传Office 2003版本以下的Excel文件.请注意后缀名应为【xls】.");
			return errorMessage;
		}
		InputStream fs = null;
		String batchId = "";
		try {
			//batchId = oldPersonImportLogService.log(file,customer);
			batchId = System.nanoTime() + "";
			fs = file.getInputStream();
			ReadExcel re = new ReadExcel(file.getOriginalFilename(),fs);
			List<Map<String,Object>> rawRows = re.Read(0);
			if(1 == rawRows.size()) {
				errorMessage.add("请不要导入空的文件.");
				return errorMessage;
			}
			for (int i = 1; i < rawRows.size(); i++) {
				try {
					List<String> row = (List<String>) rawRows.get(i);
					OldPersonEntity oldPersonEntity = new OldPersonEntity();
					StringBuffer simpleErrorMessage = new StringBuffer();
					if(null == row.get(0) || row.get(0).length() <= 0) {
						simpleErrorMessage.append("Excel文件中第").append(i+1).append("行,老人【社保编号】为空.");
					}
					oldPersonEntity.setSocialNumber(row.get(0));
					
					if(null == row.get(1) || row.get(1).length() <= 0) {
						simpleErrorMessage.append("Excel文件中第").append(i+1).append("行,老人【姓名】为空.");
					}
					oldPersonEntity.setName(row.get(1));
					
					if(null == row.get(2) || row.get(2).length() <= 0) {
						simpleErrorMessage.append("Excel文件中第").append(i+1).append("行,老人【身份证号】为空.");
					}
					if(!IdentityCardHelper.isIdCard(row.get(2))) {
						simpleErrorMessage.append("Excel文件中第").append(i+1).append("行,老人【身份证号】格式错误.");
					}
					oldPersonEntity.setIdCard(row.get(2));
					
					if(null == row.get(3) || row.get(3).length() <= 0) {
						simpleErrorMessage.append("Excel文件中第").append(i+1).append("行,老人【人员状态(本地/外地)】为空.");
					}
					DictionaryEntity oldPersonType = this.dictionaryService.getDictionaryByparentIdAndName("lnrlb", row.get(3));
					if(null == oldPersonType) {
						simpleErrorMessage.append("Excel文件中第").append(i+1).append("行,老人【人员状态(本地/外地)】输入错误,系统中找不到对应类型.");
					}
					
					if(null == row.get(4) || row.get(4).length() <= 0) {
						simpleErrorMessage.append("Excel文件中第").append(i+1).append("行,老人【所属地区】为空.");
					}
					String areaName = row.get(4);
					OrganizeViewEntity org = this.organizeService.getOrganizeByName(areaName);
					if(null == org) {
						simpleErrorMessage.append("Excel文件中第").append(i+1).append("行,老人【所属地区】输入错误,系统中找不到对应的地区.");
					} else if(!org.isLeaf()) {
						simpleErrorMessage.append("Excel文件中第").append(i+1).append("行,老人【所属地区】输入错误,该区域不属于最下层的管辖地区.");
					} else {
						//TODO
						OrganizeEntity orgEntity = new OrganizeEntity();
						orgEntity.setId(org.getId());
						oldPersonEntity.setArea(orgEntity);
						OrganizeViewEntity rootOrganize = getRootArea(org);
						oldPersonEntity.setRootAreaId(rootOrganize.getId());
					}
					
					if(null != row.get(5) && StringUtils.hasText(row.get(5))) {
						String lqsbsj = row.get(5);
						//lqsbrq
						try {
							Date lqsbDate = new SimpleDateFormat("yyyy-MM-dd").parse(lqsbsj);
							oldPersonEntity.setLqsbrq(lqsbDate);
						} catch(Throwable e) {
							simpleErrorMessage.append("Excel文件中第").append(i+1).append("行,老人【社保生效日期】输入错误,请输入一个日期2015/10/23.");
						}
					} else {
						simpleErrorMessage.append("Excel文件中第").append(i+1).append("行,老人【社保生效日期】为空.");
					}
					
					if(null != row.get(6) && StringUtils.hasText(row.get(6))) {
						String sflxStr = row.get(6);
						DictionaryEntity sflxDic = this.dictionaryService.getDictionaryByparentIdAndName("sflx", sflxStr);
						if(null != sflxDic) {
							oldPersonEntity.setSflx(sflxDic.getDicCode());
						}
					} else {
						simpleErrorMessage.append("Excel文件中第").append(i+1).append("行,老人【身份类型】为空.");
					}
//					if(null == row.get(5) || row.get(5).length() <= 0) {
//						simpleErrorMessage.append("Excel文件中第").append(i+1).append("行,社保信息【发放状态】为空.");
//					}
//					DictionaryEntity oldPersonSocialStatus = this.dictionaryService.getDictionaryByparentIdAndName("sbffzt", row.get(5));
//					if(null == oldPersonSocialStatus) {
//						simpleErrorMessage.append("Excel文件中第").append(i+1).append("行,社保信息【发放状态】输入错误,系统中找不到对应类型.");
//					} else {
//						oldPersonEntity.setSocialStatus(oldPersonSocialStatus.getDicCode());
//					}
//					
//					if(null != oldPersonSocialStatus && !"sbffzt01".equals(oldPersonSocialStatus.getDicCode())) {
//						if(null == row.get(6) || row.get(6).length() <= 0) {
//							simpleErrorMessage.append("Excel文件中第").append(i+1).append("行,社保信息【停发或暂停原因】为空.");
//						} else {
//							oldPersonEntity.setSocialStopSendCause(row.get(6));
//						}
//					}
					
					if(simpleErrorMessage.length() > 0) {
						errorMessage.add(simpleErrorMessage.toString());
						oldPersonEntity = null;
						continue;
					}
					oldPersonEntity.setType(Integer.parseInt(oldPersonType.getDicCode()));
					oldPersonEntity.setSex(IdentityCardHelper.getSex(oldPersonEntity.getIdCard()));
					oldPersonEntity.setBirthday(IdentityCardHelper.getBirthday(oldPersonEntity.getIdCard()));
					oldPersonEntity.setBatchId(batchId);
					oldPersonEntity.setNameSpell(ChineseHelper.convertSpell(oldPersonEntity.getName()));
					
					this.oldPersonDao.save(oldPersonEntity);
					//向系统中添加老年人用户信息,提供登陆功能
					oldPersonSecurityService.addSystemUser(oldPersonEntity);
					//记录操作记录,customer
					this.oldPersonOperationLogService.addLog(oldPersonEntity, customer);
				} catch(JpaSystemException e) {
					//errorMessage.add("Excel文件中第" + (i+1) + "行,老年人【" + oldPersonEntity.getName() + "】,身份证号【" + oldPersonEntity.getIdCard() + "】已存在.");
					//errorMessage.add(e.getMostSpecificCause().getMessage());
					errorMessage.add(e.getRootCause().getMessage());
					continue;
				} catch(IndexOutOfBoundsException e) {
					errorMessage.add("Excel文件中第" + (i+1) + "行为空." );
					continue;
				} catch(Exception e) {
					errorMessage.add("Excel文件中第" + (i+1) + "行出现异常." + e.getMessage());
					continue;
				}
				
			}
		}catch(Exception ex){
			ex.printStackTrace();
				throw new ServiceException("Excel文件处理失败."+ex);
		}finally{
			if(fs!=null){
				try{
					fs.close();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		}
		return errorMessage;
	}

	/**
	 * 根据地区获得老年人数量，当参数为空时，获得全系统老年人数量
	 * @param areaId
	 * @return
	 */
	public long count(String areaId) {
		if(StringHelper.isEmpty(areaId)) {
			return this.oldPersonDao.count();
		}
		return this.oldPersonDao.countByAreaId(areaId);
	}

	/**
	 * 获得指定区域下的正常状态的老年人数量(除死亡、删除外的老年人)
	 * @param areaIdCollection
	 * @return
	 */
	public long count(List<String> areaIdCollection) {
		if(null == areaIdCollection || areaIdCollection.size() <= 0) {
			return 0;
		}
		return this.oldPersonDao.countByAreaId(areaIdCollection);
	}

	/**
	 * 获得某个地区的所有老年人
	 * @param operatorEntity
	 * @return
	 */
	public List<OldPersonEntity> getOldPersonWithArea(List<String> areaCollection) {
		return this.oldPersonDao.queryByAreaCollection(areaCollection);
	}

	/**
	 * 获得某个地区的本地老年人
	 * @param operatorEntity
	 * @return
	 */
	public List<OldPersonEntity> getLocalOldPersonWithArea(List<String> areaCollection) {
		return this.oldPersonDao.queryLocalOldPersonByAreaCollection(areaCollection);
	}

	/**
	 * 获得某个地区的本地老年人
	 * @param areaCollection
	 * @param instance
	 * @return
	 */
	public Page<OldPersonEntity> getLocalOldPersonWithArea(
			List<String> areaCollection, Pageable instance) {
		// TODO Auto-generated method stub
		return this.oldPersonDao.queryLocalOldPersonByAreaCollection(areaCollection, instance);
	}

	/**
	 * 删除某个老年人
	 * @param oldPersonId
	 * @param customer
	 */
	@Transactional
	public void delete(String oldPersonId, Customer customer) {
		OldPersonEntity oldPerson = this.getOldPerson(oldPersonId);
		oldPerson.setStatus("delete");
		this.oldPersonDao.save(oldPerson);
		
		//删除日志
		oldPersonOperationLogService.deleteLog(oldPerson,customer);
		
		//将系统中的老年人从用户表中删除.撤销登陆功能
		oldPersonSecurityService.deleteSystemUser(oldPerson);
	}

	/**
	 * 标记某位老年人已经死亡
	 * @param oldPersonId
	 * @param customer
	 */
	@Transactional
	public void died(String oldPersonId, Customer customer) {
		OldPersonEntity oldPerson = this.getOldPerson(oldPersonId);
		oldPerson.setStatus("died");
		oldPerson.setFfStatus("ffStatus04");
		this.oldPersonDao.save(oldPerson);
		
		//将系统中的老年人从用户表中删除.撤销登陆功能
		oldPersonSecurityService.deleteSystemUser(oldPerson);
		//删除日志
		oldPersonOperationLogService.diedLog(oldPerson,customer);
	}

	@Transactional
	public void died(String id, Date diedTime, String diedLocation, String diedCause, Customer customer) {
		OldPersonEntity oldPerson = this.getOldPerson(id);
		oldPerson.setStatus("died");
		oldPerson.setFfStatus("ffStatus04");
		oldPerson.setDiedTime(diedTime);
		oldPerson.setDiedLocation(diedLocation);
		oldPerson.setDiedCause(diedCause);
		this.oldPersonDao.save(oldPerson);

		//将系统中的老年人从用户表中删除.撤销登陆功能
		oldPersonSecurityService.deleteSystemUser(oldPerson);
		//删除日志
		oldPersonOperationLogService.diedLog(oldPerson,customer);
	}

	/**
	 * 撤销老年人的死亡状态、删除状态
	 * @param oldPersonId
	 * @param customer
	 */
	public void undo(String oldPersonId, Customer customer) {
		OldPersonEntity oldPerson = this.getOldPerson(oldPersonId);
		oldPerson.setStatus("");
		this.oldPersonDao.save(oldPerson);
		//将系统中的老年人加入用户表中.提供登陆功能
		oldPersonSecurityService.addSystemUser(oldPerson);
		//撤销日志
		oldPersonOperationLogService.undoLog(oldPerson,customer);
	}

	/**
	 * 更新老年人照片
	 * @param id
	 * @param photo
	 */
	public void updatePhoto(String oldPersonId, byte[] photo,Customer customer) {
		OldPersonEntity oldPerson = this.getOldPerson(oldPersonId);
		if(null == oldPerson) {
			throw new IllegalArgumentException("系统中没有找到相关的老年人.");
		}
		oldPerson.setPhoto(photo);
		this.oldPersonDao.save(oldPerson);
	}

	/**
	 * 根据操作员的身份证号，获得对应操作员的所有本地老年人状态信息
	 * <br/>
	 * 该接口仅提供给终端应用
	 * @param idCard
	 * @return
	 */
	public List<Map<String, Object>> getLocalOldPersonWithOperatorIdCard(String idCard) {
		if(StringHelper.isEmpty(idCard)) {
			throw new IllegalArgumentException("提供的操作员身份证号是空的.");
		}
		OperatorEntity operator = this.operatorService.getOperatorByIdentityCard(idCard);
		if(null == operator) {
			throw new IllegalArgumentException("系统中没有对应的操作员.");
		}
		CycleEntity cycle = cycleService.getCycle(new Date());
		if(null == cycle) {
			throw new IllegalArgumentException("给定的日期不在某个周期设定内.");
		}
		List<String> areaCollection = this.areaManageService.getAreaIdCollectionWithOperatorId(operator.getId());
		/** 
		 * 
		 * 
		 考虑新的sql
		 

select (
		CASE
		WHEN (op.photo is null) = 0 THEN
			'true'
		ELSE
			'false'
		END
	) hasPhoto,op.id,
(
		SELECT
			CASE
		WHEN count(f.id) > 0 THEN
			'true'
		ELSE
			'false'
		END
		FROM
			fingerprint_collect_info f
		WHERE
			f.OLD_PERSON_ID = op.id
	) hasFingerprint,
	op. NAME,
	op.NAME_SPELL AS nameSpell,
	op.ID_CARD AS idCard,
	op.SOCIAL_NUMBER AS socialNumber,op.AREA_ID,h.id,
	org.NAME as areaName,
IF (
	h.VERIFY_STATE IS NULL,
	'',
	h.VERIFY_STATE
) AS verifyState,
(
		SELECT
			count(1)
		FROM
			health_result_info hri
		WHERE
			hri.HEALTH_ID = h.id
	) AS health
FROM
	old_person op
LEFT JOIN health_info h ON op.id = h.OLD_PERSON_ID
left join sys_security_org org on org.id = op.AREA_ID
AND h.cycle_id ='ff80808150f5e1060150f6aee1a63ec3'
WHERE
	op.type = '1'
AND op. STATUS = ''
AND op.area_id IN ('ff80808150ec3df70150f5c572601dd5', 'ff80808150ec3df70150f5c713b11de1', 
'ff80808150ec3df70150f5c6f94c1de0', 'ff80808150ec3df70150f5c6dc4d1ddf', 
'ff80808150ec3df70150f5c693631dde', 'ff80808150ec3df70150f5c676cd1ddd', 
'ff80808150ec3df70150f5c5cc0d1dd8', 'ff80808150ec3df70150f5c5ad711dd7', 
'ff80808150ec3df70150f5c58c661dd6', 'ff80808150f5e1060150f6272bb81cf6')
		 * **/
		return this.oldPersonDao.queryLocalOldPersonByAreaListAndCycle(areaCollection,cycle.getId());
	}

	/**
	 * 根据操作员的身份证号，获得对应操作员的已完成认证的老年人信息
	 * <br/>
	 * 该接口仅提供给终端应用
	 * @param idCard
	 * @return
	 */
	public List<Map<String, Object>> getFinishAuthOldPersonWithOperatorIdCard(String idCard) {
		if(StringHelper.isEmpty(idCard)) {
			throw new IllegalArgumentException("提供的操作员身份证号是空的.");
		}
		OperatorEntity operator = this.operatorService.getOperatorByIdentityCard(idCard);
		if(null == operator) {
			throw new IllegalArgumentException("系统中没有对应的操作员.");
		}
		CycleEntity cycle = cycleService.getCycle(new Date());
		if(null == cycle) {
			throw new IllegalArgumentException("给定的日期不在某个周期设定内.");
		}
		return this.oldPersonDao.queryFinishAutoOldPersonByOldPersonIdAndCycle(operator.getId(),cycle.getId());
	}


}
