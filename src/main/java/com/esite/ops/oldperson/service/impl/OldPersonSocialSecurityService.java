package com.esite.ops.oldperson.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import com.esite.framework.core.exception.ServiceException;
import com.esite.framework.dictionary.entity.DictionaryEntity;
import com.esite.framework.dictionary.service.DictionaryService;
import com.esite.framework.security.entity.Customer;
import com.esite.framework.util.IdentityCardHelper;
import com.esite.framework.util.ReadExcel;
import com.esite.ops.oldperson.dao.OldPersonSocialSecurityDao;
import com.esite.ops.oldperson.entity.OldPersonEntity;
import com.esite.ops.oldperson.entity.OldPersonSocialSecurityEntity;
import com.esite.ops.oldperson.entity.OldPersonSocialSecurityQueryVO;

@Service("oldPersonSocialSecurityService")
public class OldPersonSocialSecurityService {
	@Autowired
	private OldPersonSocialSecurityDao oldPersonSocialSecurityDao;

	@Resource
	private OldPersonSocialSecurityImportLogService oldPersonSocialSecurityImportLogService;

	@Autowired
	private DictionaryService dictionaryService;
	
	@Resource
	private OldPersonService oldPersonService;
	
	@Transactional
	public List<String> importExcelFile(MultipartFile file, Customer customer) {
		String batchId = "";
		InputStream fs = null;
		List<String> errorMessage = new ArrayList<String>();
		try {
			if (null == file || file.getSize() <= 0) {
				errorMessage.add("请上传有效的文件.");
				return errorMessage;
			}
			String originalFileName = file.getOriginalFilename();
			String suffix = originalFileName.substring(
					originalFileName.lastIndexOf("."),
					originalFileName.length());

			if (!".xls".equalsIgnoreCase(suffix)) {
				errorMessage.add("请上传Office 2003版本以下的Excel文件.请注意后缀名应为【xls】.");
				return errorMessage;
			}
			batchId = oldPersonSocialSecurityImportLogService.log(file,customer);
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
					OldPersonSocialSecurityEntity oldPersonSocialSecurityEntity = new OldPersonSocialSecurityEntity();
					oldPersonSocialSecurityEntity.setBatchId(batchId);
					StringBuffer simpleErrorMessage = new StringBuffer();
					
					OldPersonEntity importOldPerson = new OldPersonEntity();
					if(null == row.get(0) || row.get(0).length() <= 0) {
						simpleErrorMessage.append("Excel文件中第").append(i+1).append("行,老人【姓名】为空.");
					} else {
						importOldPerson.setName(row.get(0));
					}
					
					if(null == row.get(2) || row.get(2).length() <= 0) {
						simpleErrorMessage.append("Excel文件中第").append(i+1).append("行,老人【社保编号】为空.");
					} else {
						importOldPerson.setSocialNumber(row.get(2));
					}
					
					if(null == row.get(1) || row.get(1).length() <= 0) {
						simpleErrorMessage.append("Excel文件中第").append(i+1).append("行,老人【身份证号】为空.");
					}
					if(!IdentityCardHelper.isIdCard(row.get(1))) {
						simpleErrorMessage.append("Excel文件中第").append(i+1).append("行,老人【身份证号】格式错误.");
					} else {
						importOldPerson.setIdCard(row.get(1));
						OldPersonEntity dbOldPerson = oldPersonService.getOldPersonWithIdCard(row.get(1));
						if(null == dbOldPerson) {
							simpleErrorMessage.append("Excel文件中第").append(i+1).append("行,老年人不在系统中.");
						} else if (!dbOldPerson.getStatus().equals("")) {
							simpleErrorMessage.append("Excel文件中第").append(i+1).append("行,老年人在系统中的状态不属于正常状态.");
						} else if(!dbOldPerson.equals(importOldPerson)) {
							simpleErrorMessage.append("Excel文件中第").append(i+1).append("行,老人信息与系统中不匹配,请确认老年人的姓名、身份证号以及社保编号.");
						} else {
							oldPersonSocialSecurityEntity.setOldPerson(dbOldPerson);
						}
					}
					
					
					if(null == row.get(3) || row.get(3).length() <= 0) {
						simpleErrorMessage.append("Excel文件中第").append(i+1).append("行,社保【发放时间】为空.");
					} else {
						try {
							Date sendDate = new SimpleDateFormat("yyyy-MM-dd").parse(row.get(3));
							oldPersonSocialSecurityEntity.setSendDate(sendDate);
						} catch(ParseException e) {
							simpleErrorMessage.append("Excel文件中第").append(i+1).append("行,社保【发放时间】格式错误,请检查是否为【yyyy-MM-dd】的时间格式.");
						}
					}
					
					if(null == row.get(4) || row.get(4).length() <= 0) {
						simpleErrorMessage.append("Excel文件中第").append(i+1).append("行,社保【发放状态】为空.");
					}
					DictionaryEntity oldPersonSocialStatus = this.dictionaryService.getDictionaryByParentIdAndName("sendStatus", row.get(4));
					if(null == oldPersonSocialStatus) {
						simpleErrorMessage.append("Excel文件中第").append(i+1).append("行,社保【发放状态】输入错误,系统中找不到对应的发放状态.");
					} else {
						oldPersonSocialSecurityEntity.setSendStatus(oldPersonSocialStatus.getDicCode());
					}

					if(null != oldPersonSocialStatus && !"1".equals(oldPersonSocialStatus.getDicCode())) {
						if(null == row.get(5) || row.get(5).length() <= 0) {
							simpleErrorMessage.append("Excel文件中第").append(i+1).append("行,社保【停发或暂停原因】为空.");
						} else {
							DictionaryEntity socialStopSendCause = this.dictionaryService.getDictionaryByParentIdAndName("socialStopSendCause", row.get(5));
							if(null == socialStopSendCause) {
								simpleErrorMessage.append("Excel文件中第").append(i+1).append("行,社保【停发或暂停原因】输入错误,系统中找不到对应的停发或暂停原因.");
							} else {
								oldPersonSocialSecurityEntity.setSocialStopSendCause(socialStopSendCause.getDicCode());
							}
						}
					}
					
					if(simpleErrorMessage.length() > 0) {
						errorMessage.add(simpleErrorMessage.toString());
						continue;
					}
					OldPersonSocialSecurityEntity dbOldPersonSocialSecurityEntity = this.oldPersonSocialSecurityDao.getSocialSecurityByOldPersonIdAndSendDate(oldPersonSocialSecurityEntity.getOldPerson().getId(),oldPersonSocialSecurityEntity.getSendDate());
					if(null != dbOldPersonSocialSecurityEntity) {
						//新导入的老年人社保发放状态如果与数据库中冲突(检测冲突根据老年人id和发放时间检查),则替换为最新的数据(更新发放时间、发放状态、以及批次)
						oldPersonSocialSecurityEntity.setOldBatchId(dbOldPersonSocialSecurityEntity.getBatchId());
						dbOldPersonSocialSecurityEntity.setStatus("delete");
						this.oldPersonSocialSecurityDao.save(dbOldPersonSocialSecurityEntity);
					}
					this.oldPersonSocialSecurityDao.save(oldPersonSocialSecurityEntity);
					OldPersonEntity dbOldPerson = oldPersonService.getOldPersonWithIdCard(row.get(1));
					if(null != dbOldPerson) {
						dbOldPerson.setSocialStatus(oldPersonSocialSecurityEntity.getSendStatus());
						dbOldPerson.setSocialStopSendCause(oldPersonSocialSecurityEntity.getSocialStopSendCause());
						this.oldPersonService.update(dbOldPerson, customer);
					}
					//记录操作记录,customer
				} catch(JpaSystemException e) {
					//errorMessage.add("Excel文件中第" + (i+1) + "行,老年人【" + oldPersonEntity.getName() + "】,身份证号【" + oldPersonEntity.getIdCard() + "】已存在.");
					//errorMessage.add(e.getMostSpecificCause().getMessage());
					errorMessage.add(e.getRootCause().getMessage());
					continue;
				} catch(IndexOutOfBoundsException e) {
					errorMessage.add("Excel文件中第" + (i+1) + "行为空.");
					continue;
				} catch(Exception e) {
					errorMessage.add("Excel文件中第" + (i+1) + "行出现异常." + e.getMessage());
					continue;
				}
				
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new ServiceException("Excel文件处理失败." + ex);
		} finally {
			if (fs != null) {
				try {
					fs.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return errorMessage;
	}

	public Page<OldPersonSocialSecurityEntity> find(final OldPersonSocialSecurityQueryVO oldPersonSocialSecurityQueryVO,Pageable instance) {
		return this.oldPersonSocialSecurityDao.findAll(new Specification<OldPersonSocialSecurityEntity>() {
			@Override
			public Predicate toPredicate(Root<OldPersonSocialSecurityEntity> root, CriteriaQuery<?> query,CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				List<Expression<Boolean>> expressions = predicate.getExpressions();
				
				expressions.add(cb.equal(root.<String>get("status"),oldPersonSocialSecurityQueryVO.getStatus()));
				
				query.orderBy(cb.desc(root.get("insertDateTime")));
				return predicate;
			}
		}, instance);
	}

}
