package com.esite.ops.oldperson.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.esite.framework.file.entity.SysFileInfo;
import com.esite.framework.file.service.impl.FileService;
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
import com.mchange.v2.log.LogUtils;

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
    @Resource
    private FileService fileService;
    private static final Logger LOG = LoggerFactory.getLogger(OldPersonService.class);

    /**
     * 多条件查询随访人员
     *
     * @param oldPersonQueryEntity 查询条件
     * @param instance 分页对象
     */
    public Page<OldPersonEntity> find(final OldPersonQueryEntity oldPersonQueryEntity, Pageable instance) {
        return oldPersonDao.findAll(new Specification<OldPersonEntity>() {
            @Override
            public Predicate toPredicate(Root<OldPersonEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                List<Expression<Boolean>> expressions = predicate.getExpressions();
                if (StringHelper.isNotEmpty(oldPersonQueryEntity.getIdCard())) {
                    expressions
                        .add(JpaLikeQueryHelper.like(cb, root.<String>get("idCard"), oldPersonQueryEntity.getIdCard()));
                }
                if (StringHelper.isNotEmpty(oldPersonQueryEntity.getName())) {
                    expressions
                        .add(JpaLikeQueryHelper.like(cb, root.<String>get("name"), oldPersonQueryEntity.getName()));
                }
                if (StringHelper.isNotEmpty(oldPersonQueryEntity.getSocialNumber())) {
                    expressions.add(JpaLikeQueryHelper
                        .like(cb, root.<String>get("socialNumber"), oldPersonQueryEntity.getSocialNumber()));
                }
                if (-1 != oldPersonQueryEntity.getSex()) {
                    expressions.add(cb.equal(root.<String>get("sex"), oldPersonQueryEntity.getSex()));
                }
                if (-1 != oldPersonQueryEntity.getType()) {
                    expressions.add(cb.equal(root.<String>get("type"), oldPersonQueryEntity.getType()));
                }
                if (StringHelper.isNotEmpty(oldPersonQueryEntity.getAreaId())) {
                    expressions.add(cb.equal(root.<String>get("area").get("id"), oldPersonQueryEntity.getAreaId()));
                }
                if (StringHelper.isNotEmpty(oldPersonQueryEntity.getModelingStatus())) {
                    expressions
                        .add(cb.equal(root.<String>get("modelingStatus"), oldPersonQueryEntity.getModelingStatus()));
                }
                if (-1 != oldPersonQueryEntity.getAgeRangeBegin()) {
                    Calendar calendar = Calendar.getInstance();
                    int subYear = 0;
                    subYear -= oldPersonQueryEntity.getAgeRangeBegin();
                    calendar.add(Calendar.YEAR, subYear);
                    calendar.getTime();
                    expressions.add(cb.lessThan(root.<Date>get("birthday"), calendar.getTime()));
                }

                if (-1 != oldPersonQueryEntity.getAgeRangeEnd()) {
                    Calendar calendar = Calendar.getInstance();
                    int subYear = 0;
                    subYear -= oldPersonQueryEntity.getAgeRangeEnd();
                    calendar.add(Calendar.YEAR, subYear);
                    calendar.getTime();
                    expressions.add(cb.greaterThan(root.<Date>get("birthday"), calendar.getTime()));
                }

                if (StringHelper.isNotEmpty(oldPersonQueryEntity.getAgeRange())) {
                    Calendar calendar = Calendar.getInstance();
                    int subYear = 0;
                    String ageRange = oldPersonQueryEntity.getAgeRange();
                    if ("lnrAge60".equals(ageRange)) {
                        subYear = -60;
                    } else if ("lnrAge70".equals(ageRange)) {
                        subYear = -70;
                    } else if ("lnrAge80".equals(ageRange)) {
                        subYear = -80;
                    } else if ("lnrAge90".equals(ageRange)) {
                        subYear = -90;
                    } else if ("lnrAge100".equals(ageRange)) {
                        subYear = -100;
                    } else {
                        subYear = 0;
                    }
                    calendar.add(Calendar.YEAR, subYear);
                    calendar.getTime();
                    expressions.add(cb.lessThan(root.<Date>get("birthday"), calendar.getTime()));
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
        if (null == dbOldPerson) {
            return;
        }
        String status = dbOldPerson.getStatus();
        if (StringHelper.isEmpty(oldPerson.getId()) && null != dbOldPerson) {
            throw new IllegalArgumentException("身份证号【" + dbOldPerson.getIdCard() + "】或基线编号【" + dbOldPerson.getBaseQueueCode() + "】的随访人员已经存在.");
        }
        if (StringHelper.isNotEmpty(oldPerson.getId()) && StringHelper.isEmpty(status) && !oldPerson.getId()
            .equals(dbOldPerson.getId())) {
            throw new IllegalArgumentException("身份证号【" + dbOldPerson.getIdCard() + "】或基线编号【" + dbOldPerson.getBaseQueueCode() + "】的随访人员已经存在.");
        } else if ("delete".equals(status)) {
            throw new IllegalArgumentException("身份证号【" + dbOldPerson.getIdCard() + "】或基线编号【" + dbOldPerson.getBaseQueueCode() + "】的随访人员已经存在,但目前被标记为删除状态.");
        } else if ("died".equals(status)) {
            throw new IllegalArgumentException("身份证号【" + dbOldPerson.getIdCard() + "】或基线编号【" + dbOldPerson.getBaseQueueCode() + "】的随访人员已经存在,但目前被标记为死亡状态.");
        }
    }

    public OldPersonEntity getOldPersonWithIdCard(String idCard) {
        OldPersonEntity oldPerson = this.oldPersonDao.queryByIdCard(idCard);
        return oldPerson;
    }

    /**
     * 添加一个新的随访人员
     *
     * @param oldPerson 随访人员实体对象
     * @param customer 系统操作人员
     */
    public OldPersonEntity addNew(OldPersonEntity oldPerson, Customer customer) {
        checkOldPerson(oldPerson);
        String idCard = oldPerson.getIdCard();
        try {
            oldPerson.setSex(IdentityCardHelper.getSex(idCard));
            oldPerson.setBirthday(IdentityCardHelper.getBirthday(idCard));
            oldPerson.setNameSpell(ChineseHelper.convertSpell(oldPerson.getName()));
            OrganizeViewEntity org = getRootArea(this.organizeService.getOrganizeById(oldPerson.getArea().getId()));
            oldPerson.setRootAreaId(org.getId());

            /** 基线队列编号处理 */
            String convertBaseCode = oldPerson.getBaseQueueCode();
            convertBaseCode = convertBaseCode.substring(2);
            convertBaseCode = convertBaseCode.substring(0, convertBaseCode.indexOf("WJ"));
            oldPerson.setConvertBaseCode(convertBaseCode);

            this.oldPersonDao.save(oldPerson);

            //系统中添加随访人员信息,提供登陆功能
            oldPersonSecurityService.addSystemUser(oldPerson);
            //记录操作记录,customer
            oldPersonOperationLogService.addLog(oldPerson, customer);
            return oldPerson;
        } catch (JpaSystemException e) {
            throw new IllegalArgumentException("身份证号【" + oldPerson.getIdCard() + "】或基线编号【" + oldPerson.getBaseQueueCode() + "】的随访人员已经存在");
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
            throw new IllegalArgumentException("随访人员的姓名输入可能有错.");
        }
    }

    private OrganizeViewEntity getRootArea(OrganizeViewEntity area) {
        if (null == area) {
            return null;
        }
        if ("z".equals(area.getType())) {
            return area;
        }
        return getRootArea(area.getParent());
    }

    /**
     * 更新随访人员信息ForHttpRequest
     */
    public OldPersonEntity updateForHttpRequest(OldPersonEntity oldPerson, Customer customer) {
        this.oldPersonDao.save(oldPerson);
        return oldPerson;
    }

    /**
     * 更新随访人员信息
     */
    public OldPersonEntity update(OldPersonEntity oldPerson, Customer customer) {
        if (StringHelper.isEmpty(oldPerson.getId())) {
            throw new IllegalArgumentException("系统无法根据空的编号查询随访人员信息");
        }
        OldPersonEntity dbPersonEntity = this.getOldPerson(oldPerson.getId());
        if (null == dbPersonEntity) {
            throw new IllegalArgumentException("系统中不存在这个随访人员.本次查询的编号是【" + oldPerson.getId() + "】");
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
            if (!StringUtils.hasText(oldPerson.getModelingStatus()) || "none".equals(oldPerson.getModelingStatus())) {
                oldPerson.setModelingStatus(dbPersonEntity.getModelingStatus());
            }

            /** 基线队列编号处理 */
            String convertBaseCode = oldPerson.getBaseQueueCode();
            convertBaseCode = convertBaseCode.substring(2);
            convertBaseCode = convertBaseCode.substring(0, convertBaseCode.indexOf("WJ"));
            oldPerson.setConvertBaseCode(convertBaseCode);

            this.oldPersonDao.save(oldPerson);
            int oldPersonType = oldPerson.getType();
            if (2 == oldPersonType) {
                //向系统中添加随访人员用户信息,提供登陆功能
                oldPersonSecurityService.addSystemUser(oldPerson);
            }

            return oldPerson;
        } catch (JpaSystemException e) {
            LOG.error("update old person error", e);
            throw new IllegalArgumentException("身份证号【" + oldPerson.getIdCard() + "】或基线编号【" + oldPerson.getBaseQueueCode() + "】的随访人员已经存在");
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
            throw new IllegalArgumentException("随访人员的姓名输入可能有错.");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /**
     * 根据随访人员ID 获得随访人员实体对象
     */
    public OldPersonEntity getOldPerson(String oldPersonId) {
        if (StringHelper.isEmpty(oldPersonId)) {
            throw new IllegalArgumentException("随访人员id不能为空.");
        }
        return this.oldPersonDao.findOne(oldPersonId);
    }

    /**
     * 以Excel的方式导入随访人员信息
     */
    public List<String> importOldPersonExcelFile(MultipartFile file, Customer customer) {
        List<String> errorMessage = new ArrayList<String>();
        if (null == file || file.getSize() <= 0) {
            errorMessage.add("请上传有效的文件.");
            return errorMessage;
        }
        String originalFileName = file.getOriginalFilename();
        String suffix = originalFileName.substring(originalFileName.lastIndexOf("."), originalFileName.length());

        if (!".xls".equalsIgnoreCase(suffix)) {
            errorMessage.add("请上传Office 2003版本以下的Excel文件.请注意后缀名应为【xls】.");
            return errorMessage;
        }
        InputStream fs = null;
        String batchId = "";
        try {
            //batchId = oldPersonImportLogService.log(file,customer);
            batchId = System.nanoTime() + "";
            fs = file.getInputStream();
            ReadExcel re = new ReadExcel(file.getOriginalFilename(), fs);
            List<Map<String, Object>> rawRows = re.Read(0);
            if (1 == rawRows.size()) {
                errorMessage.add("请不要导入空的文件.");
                return errorMessage;
            }
            for (int i = 1; i < rawRows.size(); i++) {
                try {
                    List<String> row = (List<String>) rawRows.get(i);
                    OldPersonEntity oldPersonEntity = new OldPersonEntity();
                    StringBuffer simpleErrorMessage = new StringBuffer();

                    /** 基线队列编号处理 */
                    int columnIndex = 0;
                    if (null == row.get(columnIndex) || row.get(columnIndex).length() <= 0) {
                        simpleErrorMessage.append("Excel文件中第").append(i + 1).append("行,【基线队列编号】为空.");
                    }
                    oldPersonEntity.setBaseQueueCode(row.get(columnIndex));

                    /** 姓名处理 */
                    columnIndex = 1;
                    if (null == row.get(columnIndex) || row.get(columnIndex).length() <= 0) {
                        simpleErrorMessage.append("Excel文件中第").append(i + 1).append("行,【姓名】为空.");
                    }
                    oldPersonEntity.setName(row.get(columnIndex));

                    /** 性别处理 */
//                    if (null == row.get(2) || row.get(2).length() <= 0) {
//                        simpleErrorMessage.append("Excel文件中第").append(i + 1).append("行,【性别】为空.");
//                    }
//                    DictionaryEntity oldPersonType = this.dictionaryService
//                        .getDictionaryByParentIdAndName("xb", row.get(2));
//                    if (null == oldPersonType) {
//                        simpleErrorMessage.append("Excel文件中第").append(i + 1)
//                            .append("行,【性别】输入错误,系统中找不到对应类型.");
//                    } else {
//                        oldPersonEntity.setSex(Integer.parseInt(oldPersonType.getDicCode()));
//                    }

                    /** 民族处理 */
                    columnIndex = 2;
                    if (null == row.get(columnIndex) || row.get(columnIndex).length() <= 0) {
                        simpleErrorMessage.append("Excel文件中第").append(i + 1).append("行,【民族】为空.");
                    }
                    DictionaryEntity oldPersonNation = this.dictionaryService
                        .getDictionaryByParentIdAndName("nationality", row.get(columnIndex));
                    if (null == oldPersonNation) {
                        simpleErrorMessage.append("Excel文件中第").append(i + 1)
                            .append("行,人员【民族】输入错误,系统中找不到对应类型.");
                    } else {
                        oldPersonEntity.setNationality(oldPersonNation.getDicCode());
                    }

                    /** 身份证处理 */
                    columnIndex = 3;
                    if (null == row.get(columnIndex) || row.get(columnIndex).length() <= 0) {
                        simpleErrorMessage.append("Excel文件中第").append(i + 1).append("行,【身份证号】为空.");
                    }
                    if (!IdentityCardHelper.isIdCard(row.get(columnIndex))) {
                        simpleErrorMessage.append("Excel文件中第").append(i + 1).append("行,【身份证号】格式错误.");
                    }
                    oldPersonEntity.setIdCard(row.get(columnIndex));

                    /** 基线队列调查时间 */
                    columnIndex = 4;
                    if (null == row.get(columnIndex) || row.get(columnIndex).length() <= 0) {
                        simpleErrorMessage.append("Excel文件中第").append(i + 1).append("行,【基线队列调查时间】为空.");
                    } else {
                        try {
                            Date baseQueueDate = new SimpleDateFormat("yyyy-M-d").parse(row.get(columnIndex));
                            oldPersonEntity.setBaseQueueTime(baseQueueDate);
                        } catch (ParseException e) {
                            simpleErrorMessage.append("Excel文件中第").append(i + 1).append("行,【基线队列调查时间】格式错误.");
                        }
                    }

                    /** 基线队列调查时住址处理 */
                    columnIndex = 5;
                    if (null == row.get(columnIndex) || row.get(columnIndex).length() <= 0) {
                        simpleErrorMessage.append("Excel文件中第").append(i + 1).append("行,【基线队列调查时住址】为空.");
                    } else {
                        oldPersonEntity.setBaseQueueAddress(row.get(columnIndex));
                    }

                    /** 随访人员所属地区 */
                    columnIndex = 6;
                    if (null == row.get(columnIndex) || row.get(columnIndex).length() <= 0) {
                        simpleErrorMessage.append("Excel文件中第").append(i + 1).append("行,【所属地区】为空.");
                    }
                    String areaName = row.get(columnIndex);
                    OrganizeViewEntity org = this.organizeService.getOrganizeByName(areaName);
                    if (null == org) {
                        simpleErrorMessage.append("Excel文件中第").append(i + 1).append("行,【所属地区】输入错误,系统中找不到对应的地区.");
                    } else if (!org.isLeaf()) {
                        simpleErrorMessage.append("Excel文件中第").append(i + 1).append("行,【所属地区】输入错误,该区域不属于最下层的管辖地区.");
                    } else {
                        //TODO
                        OrganizeEntity orgEntity = new OrganizeEntity();
                        orgEntity.setId(org.getId());
                        oldPersonEntity.setArea(orgEntity);
                        OrganizeViewEntity rootOrganize = getRootArea(org);
                        oldPersonEntity.setRootAreaId(rootOrganize.getId());
                    }

                    if (simpleErrorMessage.length() > 0) {
                        errorMessage.add(simpleErrorMessage.toString());
                        oldPersonEntity = null;
                        continue;
                    }
                    /** 根据身份证号获得性别 */
                    oldPersonEntity.setSex(IdentityCardHelper.getSex(oldPersonEntity.getIdCard()));
                    oldPersonEntity.setBirthday(IdentityCardHelper.getBirthday(oldPersonEntity.getIdCard()));
                    oldPersonEntity.setBatchId(batchId);
                    oldPersonEntity.setNameSpell(ChineseHelper.convertSpell(oldPersonEntity.getName()));

                    String convertBaseCode = oldPersonEntity.getBaseQueueCode();
                    convertBaseCode = convertBaseCode.substring(2);
                    convertBaseCode = convertBaseCode.substring(0, convertBaseCode.indexOf("WJ"));
                    oldPersonEntity.setConvertBaseCode(convertBaseCode);

                    this.oldPersonDao.save(oldPersonEntity);
                    //向系统中添加随访人员用户信息,提供登陆功能
                    oldPersonSecurityService.addSystemUser(oldPersonEntity);
                    //记录操作记录,customer
                    this.oldPersonOperationLogService.addLog(oldPersonEntity, customer);
                } catch (JpaSystemException e) {
                    errorMessage.add(e.getRootCause().getMessage());
                    continue;
                } catch (IndexOutOfBoundsException e) {
                    errorMessage.add("Excel文件中第" + (i + 1) + "行为空.");
                    continue;
                } catch (Exception e) {
                    errorMessage.add("Excel文件中第" + (i + 1) + "行出现异常." + e.getMessage());
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

    /**
     * 根据地区获得随访人员数量，当参数为空时，获得全系统随访人员数量
     */
    public long count(String areaId) {
        if (StringHelper.isEmpty(areaId)) {
            return this.oldPersonDao.count();
        }
        return this.oldPersonDao.countByAreaId(areaId);
    }

    /**
     * 获得指定区域下的正常状态的随访人员数量(除死亡、删除外的随访人员)
     */
    public long count(List<String> areaIdCollection) {
        if (null == areaIdCollection || areaIdCollection.size() <= 0) {
            return 0;
        }
        return this.oldPersonDao.countByAreaId(areaIdCollection);
    }

    /**
     * 获得某个地区的所有随访人员
     */
    public List<OldPersonEntity> getOldPersonWithArea(List<String> areaCollection) {
        return this.oldPersonDao.queryByAreaCollection(areaCollection);
    }

    /**
     * 获得某个地区的本地随访人员
     */
    public List<OldPersonEntity> getLocalOldPersonWithArea(List<String> areaCollection) {
        return this.oldPersonDao.queryLocalOldPersonByAreaCollection(areaCollection);
    }

    /**
     * 获得某个地区的本地随访人员
     */
    public Page<OldPersonEntity> getLocalOldPersonWithArea(
        List<String> areaCollection, Pageable instance) {
        // TODO Auto-generated method stub
        return this.oldPersonDao.queryLocalOldPersonByAreaCollection(areaCollection, instance);
    }

    /**
     * 删除某个随访人员
     */
    @Transactional
    public void delete(String oldPersonId, Customer customer) {
        OldPersonEntity oldPerson = this.getOldPerson(oldPersonId);
        oldPerson.setStatus("delete");
        this.oldPersonDao.save(oldPerson);

        //删除日志
        oldPersonOperationLogService.deleteLog(oldPerson, customer);

        //将系统中的随访人员从用户表中删除.撤销登陆功能
        oldPersonSecurityService.deleteSystemUser(oldPerson);
    }

    /**
     * 标记某位随访人员已经死亡
     */
    @Transactional
    public void died(String oldPersonId, Customer customer) {
        OldPersonEntity oldPerson = this.getOldPerson(oldPersonId);
        oldPerson.setStatus("died");
        oldPerson.setFfStatus("ffStatus04");
        this.oldPersonDao.save(oldPerson);

        //将系统中的随访人员从用户表中删除.撤销登陆功能
        oldPersonSecurityService.deleteSystemUser(oldPerson);
        //删除日志
        oldPersonOperationLogService.diedLog(oldPerson, customer);
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

        //将系统中的随访人员从用户表中删除.撤销登陆功能
        oldPersonSecurityService.deleteSystemUser(oldPerson);
        //删除日志
        oldPersonOperationLogService.diedLog(oldPerson, customer);
    }

    /**
     * 撤销随访人员的死亡状态、删除状态
     */
    public void undo(String oldPersonId, Customer customer) {
        OldPersonEntity oldPerson = this.getOldPerson(oldPersonId);
        oldPerson.setStatus("");
        this.oldPersonDao.save(oldPerson);
        //将系统中的随访人员加入用户表中.提供登陆功能
        oldPersonSecurityService.addSystemUser(oldPerson);
        //撤销日志
        oldPersonOperationLogService.undoLog(oldPerson, customer);
    }

    /**
     * 更新随访人员照片
     */
    @Transactional
    public void updatePhoto(String oldPersonId, byte[] photo, Customer customer) {
        OldPersonEntity oldPerson = this.getOldPerson(oldPersonId);
        if (null == oldPerson) {
            throw new IllegalArgumentException("系统中没有找到相关的随访人员.");
        }
        SysFileInfo sysFileInfo = fileService.save(photo);
        oldPerson.setPhotoKey(sysFileInfo.getFileKey());
        this.oldPersonDao.save(oldPerson);
    }

    /**
     * 根据操作员的身份证号，获得对应操作员的所有本地随访人员状态信息 <br/> 该接口仅提供给终端应用
     */
    public List<Map<String, Object>> getLocalOldPersonWithOperatorIdCard(String idCard) {
        if (StringHelper.isEmpty(idCard)) {
            throw new IllegalArgumentException("提供的操作员身份证号是空的.");
        }
        OperatorEntity operator = this.operatorService.getOperatorByIdentityCard(idCard);
        if (null == operator) {
            throw new IllegalArgumentException("系统中没有对应的操作员.");
        }
        CycleEntity cycle = cycleService.getCycle(new Date());
        if (null == cycle) {
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
        return this.oldPersonDao.queryLocalOldPersonByAreaListAndCycle(areaCollection, cycle.getId());
    }

    /**
     * 根据操作员的身份证号，获得对应操作员的已完成认证的随访人员信息 <br/> 该接口仅提供给终端应用
     */
    public List<Map<String, Object>> getFinishAuthOldPersonWithOperatorIdCard(String idCard) {
        if (StringHelper.isEmpty(idCard)) {
            throw new IllegalArgumentException("提供的操作员身份证号是空的.");
        }
        OperatorEntity operator = this.operatorService.getOperatorByIdentityCard(idCard);
        if (null == operator) {
            throw new IllegalArgumentException("系统中没有对应的操作员.");
        }
        CycleEntity cycle = cycleService.getCycle(new Date());
        if (null == cycle) {
            throw new IllegalArgumentException("给定的日期不在某个周期设定内.");
        }
        return this.oldPersonDao.queryFinishAutoOldPersonByOldPersonIdAndCycle(operator.getId(), cycle.getId());
    }


}
