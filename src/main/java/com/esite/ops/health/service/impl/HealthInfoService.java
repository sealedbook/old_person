package com.esite.ops.health.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.esite.framework.security.entity.Customer;
import com.esite.framework.user.entity.User;
import com.esite.framework.util.JpaLikeQueryHelper;
import com.esite.framework.util.StringHelper;
import com.esite.framework.util.SystemConfigUtil;
import com.esite.ops.health.dao.HealthInfoDao;
import com.esite.ops.health.dao.HealthResultDao;
import com.esite.ops.health.dao.HealthFingerprintDao;
import com.esite.ops.health.dao.HealthPhotoDao;
import com.esite.ops.health.entity.HealthInfoEntity;
import com.esite.ops.health.entity.HealthMissEntity;
import com.esite.ops.health.entity.HealthResultEntity;
import com.esite.ops.health.entity.HealthFingerprintEntity;
import com.esite.ops.health.entity.HealthPhotoEntity;
import com.esite.ops.health.vo.HealthInfoQueryVO;
import com.esite.ops.mission.entity.CycleEntity;
import com.esite.ops.mission.service.impl.CycleService;
import com.esite.ops.oldperson.dao.OldPersonDao;
import com.esite.ops.oldperson.entity.OldPersonEntity;
import com.esite.ops.oldperson.service.impl.OldPersonService;
import com.esite.ops.operator.entity.OperatorEntity;
import com.esite.ops.operator.service.impl.OperatorService;
import com.esite.ops.ws.entity.UpLoadDataVO;
import com.esite.ops.ws.entity.UpLoadOldPersonFingerprint;
import com.esite.ops.ws.entity.UpLoadOldPersonHealthVO;
import com.esite.ops.ws.entity.UpLoadOldPersonPhotoVO;

@Service("healthInfoService")
public class HealthInfoService {

    private static final Logger LOG = LoggerFactory.getLogger(HealthInfoService.class);

    @Autowired
    private HealthInfoDao healthInfoDao;

    @Autowired
    private OldPersonDao oldPersonDao;

    @Autowired
    private HealthResultDao healthResultDao;

    @Autowired
    private HealthPhotoDao healthPhotoDao;

    @Autowired
    private HealthFingerprintDao healthFingerprintDao;

    @Resource
    private OperatorService operatorService;

    @Resource
    private OldPersonService oldPersonService;

    @Resource
    private FingerprintInfoService fingerprintInfoService;

    @Resource
    private CycleService cycleService;

    /**
     * 心电图输入目录
     */
    private static final String path = SystemConfigUtil.getEcgBasePath() + "input";

    @Transactional
    public void addNew(User operatorUser, String ipAddress, UpLoadDataVO upLoadDataVO, String importBatchId,
        String logId) {
        if (null == upLoadDataVO) {
            throw new IllegalArgumentException("随访人员认证整体对象【upLoadDataVO】是空指针.");
        }

        CycleEntity cycle = cycleService.getCycle(new Date(upLoadDataVO.getEndDateTime()));
        if (null == cycle) {
            throw new IllegalArgumentException("随访人员结束认证时间不在任何一个周期范围内.");
        }
        HealthInfoEntity dbHealthInfoEntity = this.healthInfoDao
            .queryByCycleIdAndOldPersonId(cycle.getId(), upLoadDataVO.getOldPersonId().toString());
        if (null != dbHealthInfoEntity) {
            throw new IllegalArgumentException("随访人员的认证信息已经存在.");
        }

        OperatorEntity operator = operatorService.getOperatorByIdentityCard(operatorUser.getIdCard());
        if (null == operator) {
            throw new IllegalArgumentException("操作员不存在.操作员身份证号：【" + operatorUser.getIdCard() + "】");
        }
        if (upLoadDataVO.getHealthCount() <= 0) {
            throw new IllegalArgumentException("请提供体检次数");
        }
        if (upLoadDataVO.getLastHealthYear() <= 0) {
            throw new IllegalArgumentException("请提供最后体检年份");
        }
        OldPersonEntity oldPerson = oldPersonService.getOldPerson(upLoadDataVO.getOldPersonId().toString());
        if (null == oldPerson) {
            throw new IllegalArgumentException("随访人员不存在.随访人员ID：【" + upLoadDataVO.getOldPersonId().toString() + "】");
        }
        oldPerson.setLastHealthYear(upLoadDataVO.getLastHealthYear());
        oldPerson.setHealthCount(upLoadDataVO.getHealthCount());
        oldPersonDao.save(oldPerson);

        HealthInfoEntity healthInfoEntity = new HealthInfoEntity();
        healthInfoEntity.setOperator(operator);
        healthInfoEntity.setOldPerson(oldPerson);
        healthInfoEntity.setBeginDateTime(new Date(upLoadDataVO.getBeginDateTime()));
        healthInfoEntity.setEndDateTime(new Date(upLoadDataVO.getEndDateTime()));
        //healthInfoEntity.setReportCode(PhysicalReportCodeCreater.create());
        healthInfoEntity.setCycle(cycle);
        healthInfoEntity.setBatchId(importBatchId);
        healthInfoEntity.setLogId(logId);
        StringBuilder randomRequestCode = new StringBuilder();
        randomRequestCode.append(oldPerson.getLastHealthYear())
            .append(oldPerson.getConvertBaseCode())
            .append("SF")
            .append(oldPerson.getHealthCount());
        LOG.info("generator random request code : [{}]", randomRequestCode.toString());
        healthInfoEntity.setRandomRequestCode(randomRequestCode.toString());
        healthInfoDao.save(healthInfoEntity);

        LOG.info("health info save finish.");
        //获得终端上传的体检信息
        UpLoadOldPersonHealthVO healthResultVO = upLoadDataVO.getOldPersonHealthVO();
        if (null == healthResultVO) {
            throw new IllegalArgumentException("随访人员这个对象-认证结果信息【healthVO】是空指针.");
        }
        //转换并保存体检信息
        HealthResultEntity healthResultEntity = new HealthResultEntity(healthInfoEntity, healthResultVO);
        try {
            this.healthResultDao.save(healthResultEntity);
        } catch (Throwable e) {
            LOG.error("healthResultDao.save error", e);
            throw e;
        }
        LOG.info("health result save finish.");

        //获得终端上传的指纹信息
        UpLoadOldPersonFingerprint fingerprint = upLoadDataVO.getOldPersonFingerprint();
        if (null == fingerprint) {
            throw new IllegalArgumentException("随访人员认证对象-指纹信息【fingerprint】是空指针.");
        }
        //转换并保存指纹信息
        HealthFingerprintEntity fingerprintEntity = new HealthFingerprintEntity(healthInfoEntity.getId(),
            oldPerson.getId(), fingerprint);
        this.healthFingerprintDao.save(fingerprintEntity);
        LOG.info("healthFingerprintDao save finish.");

        //更新体检信息的终端指纹验证状态FingerVerifyState
        healthInfoEntity.setFingerVerifyState(fingerprint.getFingerVerifyState().getCode());
        this.healthInfoDao.save(healthInfoEntity);

//		if(null != fingerprint.getFingerVerifyState()) {
//			switch(fingerprint.getFingerVerifyState()) {
//				case SUCCESS :
//					oldPerson.setModelingStatus("created");
//					break;
//				case FAIL :
//					oldPerson.setModelingStatus("contcreated");
//					break;
//				case UNKNOW :
//					oldPerson.setModelingStatus("contcreated");
//					break;
//			}
//			this.oldPersonService.update(oldPerson, null);
//		}

        //增加指纹检测日志,第一次为默认设备检测，状态根据上传的指纹信息
        fingerprintInfoService
            .verifyLog(operatorUser, ipAddress, fingerprintEntity.getHealthId(), fingerprintEntity.getId(),
                fingerprintEntity.getFingerVerifyState());

        //获得终端上传的人像照片
        List<UpLoadOldPersonPhotoVO> photoCollection = upLoadDataVO.getPhotoCollection();
        for (UpLoadOldPersonPhotoVO personPhotoVO : photoCollection) {
            if (null == personPhotoVO) {
                throw new IllegalArgumentException("随访人员认证对象-照片信息【personPhotoVO】是空指针.");
            }
            if (null == personPhotoVO.getPosition()) {
                throw new IllegalArgumentException("随访人员认证对象-照片未知描述信息【personPhotoVO.getPosition()】是空指针.");
            }
            HealthPhotoEntity healthPhotoEntity = new HealthPhotoEntity(healthInfoEntity.getId(), oldPerson.getId(),
                personPhotoVO);
            this.healthPhotoDao.save(healthPhotoEntity);
        }
        writeFile(healthInfoEntity, oldPerson, upLoadDataVO.getOldPersonHealthVO());
    }

    private void writeFile(HealthInfoEntity healthInfoEntity, OldPersonEntity oldPerson,
        UpLoadOldPersonHealthVO oldPersonHealthVO) {
        String healthId = healthInfoEntity.getId();
        byte[] ecg = oldPersonHealthVO.getEcgData();
        byte[] resp = oldPersonHealthVO.getRespData();
        byte[] spo = oldPersonHealthVO.getSpo2Data();
        writeByteFile(healthId + "-" + oldPerson.getIdCard(), ".ecg", ecg);
        writeByteFile(healthId + "-" + oldPerson.getIdCard(), ".resp", resp);
        writeByteFile(healthId + "-" + oldPerson.getIdCard(), ".spo", spo);
        writeStringFile(healthId + "-" + oldPerson.getIdCard(), healthInfoEntity, ".txt", oldPersonHealthVO);
        LOG.info("write ecg file. healthId:[{}], idCard:[{}]", healthId, oldPerson.getIdCard());
    }

    private void writeStringFile(String fileName, HealthInfoEntity healthInfoEntity, String postfix,
        UpLoadOldPersonHealthVO oldPersonHealthVO) {
        fileName += postfix;
        String tempFileName = fileName + "_temp";

        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(path);
            if (!dir.exists() && dir.isDirectory()) {//判断文件目录是否存在
                dir.mkdirs();
            }
            file = new File(path + "/" + tempFileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            StringBuffer sb = new StringBuffer();
            sb.append("ID=").append(healthInfoEntity.getId()).append("\n");
            sb.append("SOCIAL=").append(healthInfoEntity.getOldPerson().getSocialNumber()).append("\n");
            sb.append("TIME=")
                .append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(healthInfoEntity.getBeginDateTime()))
                .append("\n");
            sb.append("AGE=").append(healthInfoEntity.getOldPerson().getAge()).append("\n");
            sb.append("DOB=").append(healthInfoEntity.getOldPerson().getBirthday()).append("\n");
            sb.append("SEX=").append(healthInfoEntity.getOldPerson().getSex() == 1 ? "M" : "F").append("\n");
            sb.append("HR=").append(oldPersonHealthVO.getHeartRate()).append("\n");
            sb.append("PR=").append(oldPersonHealthVO.getPulseRate()).append("\n");
            sb.append("BPH=").append(oldPersonHealthVO.getSystolicPressure()).append("\n");
            sb.append("BPL=").append(oldPersonHealthVO.getDiastolicPressure()).append("\n");
            sb.append("RESP=").append(oldPersonHealthVO.getRespiratoryRate()).append("\n");
            sb.append("SPO2=").append(oldPersonHealthVO.getBloodOxygen());
            bos.write(sb.toString().getBytes());
            LOG.info("file write finish. path:[{}], content:[{}]", path + "/" + tempFileName, sb.toString());
        } catch (Exception e) {
            LOG.error("write file error", e);
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            file.renameTo(new File(path + "/" + fileName));
        }
    }

    public static void main(String[] args) {
        new HealthInfoService().writeByteFile("abc", ".ecg", new byte[]{1, 2, 3, 4, 56});
    }

    private void writeByteFile(String fileName, String postfix, byte[] byteFile) {
        fileName += postfix;
        String tempFileName = fileName + "_temp";

        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(path);
            if (!dir.exists() && dir.isDirectory()) {//判断文件目录是否存在
                dir.mkdirs();
            }
            file = new File(path + "/" + tempFileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(byteFile);
            LOG.info("file write finish. path:[{}]", path + "/" + tempFileName);
        } catch (Exception e) {
            LOG.error("write file error.", e);
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            file.renameTo(new File(path + "/" + fileName));
        }
    }

    public Page<HealthInfoEntity> getVerifyHealth(final HealthInfoQueryVO healthInfoQueryVO, Pageable instance) {
        return healthInfoDao.findAll(new Specification<HealthInfoEntity>() {
            @Override
            public Predicate toPredicate(Root<HealthInfoEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                List<Expression<Boolean>> expressions = predicate.getExpressions();
                if (StringHelper.isNotEmpty(healthInfoQueryVO.getOperatorName())) {
                    expressions.add(cb.like(root.<String>get("operator").<String>get("name"),
                        "%" + healthInfoQueryVO.getOperatorName() + "%"));
                }
                if (StringHelper.isNotEmpty(healthInfoQueryVO.getOperatorId())) {
                    //expressions.add(cb.like(root.<String>get("operator").<String>get("name"), "%"+healthInfoQueryVO.getOperatorId()+"%"));
                    expressions.add(
                        cb.equal(root.<String>get("operator").<String>get("id"), healthInfoQueryVO.getOperatorId()));
                }
                if (StringHelper.isNotEmpty(healthInfoQueryVO.getVerifyState())) {
                    expressions.add(cb.equal(root.<String>get("verifyState"), healthInfoQueryVO.getVerifyState()));
                }
//				if(StringHelper.isNotEmpty(healthInfoQueryVO.getOldPersonName())) {
//					expressions.add(JpaLikeQueryHelper.like(cb, root.<String>get("oldPerson").<String>get("name"), healthInfoQueryVO.getOldPersonName()));
//				}
                if (StringHelper.isNotEmpty(healthInfoQueryVO.getOldPersonIdCard())) {
                    expressions.add(JpaLikeQueryHelper.like(cb, root.<String>get("oldPerson").<String>get("idCard"),
                        healthInfoQueryVO.getOldPersonIdCard()));
                }
                if (StringHelper.isNotEmpty(healthInfoQueryVO.getOldPersonId())) {
                    expressions.add(
                        cb.equal(root.<String>get("oldPerson").<String>get("id"), healthInfoQueryVO.getOldPersonId()));
                }
                if (StringHelper.isNotEmpty(healthInfoQueryVO.getCycleId())) {
                    expressions.add(cb.equal(root.<String>get("cycle").get("id"), healthInfoQueryVO.getCycleId()));
                }
                query.orderBy(cb.desc(root.get("id")));
                return predicate;
            }
        }, instance);
    }

    public HealthInfoEntity getHealth(String healthId) {
        return this.healthInfoDao.findOne(healthId);
    }

    public Map<String, Object> statisticsHealthInfoByOperatorIdCard(String idCard) {
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
        return this.healthInfoDao
            .statisticsHealthInfoByOperatorAndCycleDate(operator.getId(), cycle.getCycleBegin(), cycle.getCycleEnd());
    }

    public void updateVerifyState(String healthId, String healthFingerprintId, String verifyState, Customer customer) {
        HealthInfoEntity health = this.getHealth(healthId);
        health.setVerifyState(verifyState);
        this.healthInfoDao.save(health);
        fingerprintInfoService
            .verifyLog(customer.getUser(), customer.getIp(), healthId, healthFingerprintId, verifyState);

        String oldPersonId = health.getOldPerson().getId();
        OldPersonEntity oldPerson = this.oldPersonService.getOldPerson(oldPersonId);
        if ("1".equals(verifyState)) {
            oldPerson.setFfStatus("ffStatus02");
        } else {
            oldPerson.setFfStatus("ffStatus03");
        }
        this.oldPersonDao.save(oldPerson);
    }

    /**
     * 获得指定随访人员的体检记录
     */
    public Page<HealthInfoEntity> getHealthByOldPersonId(String oldPersonId, Pageable instance) {
        return this.healthInfoDao.queryByOldPersonId(oldPersonId, instance);
    }

    public String getNextHealthId(String healthId) {
        return getNextHealthId(healthId, "-1", null, null);
    }

    public String getNextHealthId(String healthId, String verifyState, String oldPersonId, String operatorId) {
        Map<String, Object> obj = null;
        if (!StringUtils.hasText(operatorId) && !StringUtils.hasText(oldPersonId)) {
            obj = this.healthInfoDao.queryNextHealthById(healthId, verifyState);
        }
        if (StringUtils.hasText(operatorId) && !StringUtils.hasText(oldPersonId)) {
            obj = this.healthInfoDao.queryNextHealthByIdAndOperatorId(healthId, verifyState, operatorId);
        }
        if (!StringUtils.hasText(operatorId) && StringUtils.hasText(oldPersonId)) {
            obj = this.healthInfoDao.queryNextHealthByIdAndOldPersonId(healthId, verifyState, oldPersonId);
        }
        if (StringUtils.hasText(operatorId) && StringUtils.hasText(oldPersonId)) {
            obj = this.healthInfoDao.queryNextHealth(healthId, verifyState, operatorId, oldPersonId);
        }
        if (null == obj) {
            return "";
        }
        return obj.get("ID").toString();
    }

    public HealthInfoEntity getLastHealthByOldPersonId(String oldPersonId) {
        List<HealthInfoEntity> list = this.healthInfoDao.queryLastByOldPersonId(oldPersonId);
        if (null == list || list.size() <= 0) {
            return null;
        }
        return list.get(0);
    }

    public HealthInfoEntity getHealthByOldPersonIdAnd(String cycleId, String oldPersonId) {
        HealthInfoEntity healthInfoEntity = this.healthInfoDao.queryByCycleIdAndOldPersonId(cycleId, oldPersonId);
        return healthInfoEntity;
    }

    public int getHealthNumberByOldPerson(String healthId, String oldPersonId) {
        return this.healthInfoDao.queryHealthNumber(oldPersonId, healthId);
    }

    public boolean hasHealthInCycleByOldPersonId(String oldPersonId, CycleEntity cycle) {
        HealthInfoEntity health = this.healthInfoDao.queryByCycleIdAndOldPersonId(cycle.getId(), oldPersonId);
        if (null != health) {
            return true;
        }
        return false;
    }

    public HealthInfoEntity getFirstHealthByOldPersonId(String oldPersonId) {
        List<HealthInfoEntity> oldPersonHealthInfoList = this.healthInfoDao.queryByOldPersonId(oldPersonId);
        if (null == oldPersonHealthInfoList || oldPersonHealthInfoList.size() <= 0) {
            return null;
        }
        return oldPersonHealthInfoList.get(0);
    }

    public HealthInfoEntity getLastHealthByOldPersonId(String oldPersonId, Date lastHealthDateTime) {
        List<HealthInfoEntity> oldPersonHealthInfoList = this.healthInfoDao
            .queryLastHealthByOldPersonId(oldPersonId, lastHealthDateTime);
        if (null == oldPersonHealthInfoList || oldPersonHealthInfoList.size() <= 0) {
            return null;
        }
        return oldPersonHealthInfoList.get(0);
    }

    public List<HealthInfoEntity> getAllHealthInfoByCycleId(String cycleId) {
        return this.healthInfoDao.queryByCycleId(cycleId);
    }

    public void updateHealthInfo(HealthInfoEntity health) {
        this.healthInfoDao.save(health);
    }
}
