package com.esite.ops.ws.servlce;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;

import com.esite.framework.core.factory.WebApplicationContextUtil;
import com.esite.framework.user.entity.User;
import com.esite.framework.util.JsonConverter;
import com.esite.framework.util.PagerRequest;
import com.esite.framework.util.SerializerHelper;
import com.esite.framework.util.StringHelper;
import com.esite.ops.health.service.impl.HealthInfoImportErrorLogService;
import com.esite.ops.health.service.impl.HealthInfoImportLogService;
import com.esite.ops.health.service.impl.HealthInfoService;
import com.esite.ops.mission.entity.CycleEntity;
import com.esite.ops.mission.service.impl.CycleService;
import com.esite.ops.oldperson.entity.FingerprintCollectEntity;
import com.esite.ops.oldperson.entity.OldPersonEntity;
import com.esite.ops.oldperson.service.impl.FingerprintCollectService;
import com.esite.ops.oldperson.service.impl.OldPersonService;
import com.esite.ops.operator.entity.OperatorEntity;
import com.esite.ops.operator.service.impl.AreaConfigService;
import com.esite.ops.operator.service.impl.OperatorSecurityService;
import com.esite.ops.operator.service.impl.OperatorService;
import com.esite.ops.ws.entity.DownloadDataInfoVO;
import com.esite.ops.ws.entity.DownloadOldPersonFingerprintVO;
import com.esite.ops.ws.entity.DownloadOldPersonInfoVO;
import com.esite.ops.ws.entity.UpLoadDataVO;
import com.esite.ops.ws.enums.FingerEnum;
import com.esite.ops.ws.enums.FingerState;
import com.esite.ops.ws.enums.HandEnum;

@WebService(serviceName = "healthWebService", portName = "healthWebServicePort")
@SOAPBinding(style = Style.DOCUMENT, use = Use.LITERAL)
public class HealthWebService {

    private static Logger logger = Logger.getLogger(HealthWebService.class);

    @Resource
    private WebServiceContext wsContext;

    /**
     * 上传体检信息
     */
    @WebMethod
    public String upload(@WebParam(name = "healthInfo") String healthInfo, @WebParam(name = "token") String token) {
        Map<String, String> webServiceResult = new HashMap<String, String>(2);
        User user = getUser(token);
        if (null == user) {
            logger.info("=======Web Service 您没有权限访问.");
            webServiceResult.put("responseStatus", "ERROR");
            webServiceResult.put("message", "您没有权限访问.");
            return JsonConverter.convert(webServiceResult);
        }
        HealthInfoImportLogService healthInfoImportLogService = (HealthInfoImportLogService) WebApplicationContextUtil
            .getBean("healthInfoImportLogService");
        HealthInfoService healthInfoService = (HealthInfoService) WebApplicationContextUtil
            .getBean("healthInfoService");

        //logger.info("=======Web Service 获得的服务类：" + healthInfoService);
        logger.info("=======Web Service 接收到参数,token:" + token);
        Object obj = SerializerHelper.deserialize(healthInfo);
        int uploadOldPersonTotal = 0;
        if (obj instanceof UpLoadDataVO[]) {
            UpLoadDataVO[] upLoadDataVOArray = (UpLoadDataVO[]) obj;
            if (null == upLoadDataVOArray || upLoadDataVOArray.length <= 0) {
                logger.info("=======Web Service 您没有上传老年人认证信息.");
                webServiceResult.put("responseStatus", "ERROR");
                webServiceResult.put("message", "您没有上传老年人认证信息.");
                return JsonConverter.convert(webServiceResult);
            }
            logger.info("=======Web Service 反序列化Object属于UpLoadDataVO[]类型");
            logger.info("=======Web Service 共有" + upLoadDataVOArray.length + "位老人信息.");
            uploadOldPersonTotal = upLoadDataVOArray.length;
            int i = 0;
            String importHealthInfoBatchId = StringHelper.createUUID().toString();
            try {
                for (UpLoadDataVO upLoadDataVO : upLoadDataVOArray) {
                    if (null == upLoadDataVO) {
                        logger.info("=======Web Service 第" + (i) + "位老人的对象是空指针.程序继续运行.");
                        continue;
                    }
                    String logContent = JsonConverter.convert(upLoadDataVO);
                    //logger.info("=======Web Service 上传老年人日志记录内容：" + logContent);
                    String logId = healthInfoImportLogService
                        .log(importHealthInfoBatchId, user, getIpAddress(), logContent);
                    String uploadOldPersonId = "";
                    if (null != upLoadDataVO.getOldPersonId()) {
                        uploadOldPersonId = upLoadDataVO.getOldPersonId().toString();
                    }
                    try {
                        logger.info("=======Web Service 开始第" + (++i) + "位老人,老人ID:【" + uploadOldPersonId + "】");
                        healthInfoService.addNew(user, getIpAddress(), upLoadDataVO, importHealthInfoBatchId, logId);

                        logger.info("=======Web Service 第" + (i) + "位处理完成");
                    } catch (Exception e) {
                        //healthInfoImportErrorLogService.log(importHealthInfoBatchId,logId,e.getMessage());
                        logger.info("=======Web Service 第" + (i) + "位处理失败.message:" + e.getMessage());
                        webServiceResult.put("responseStatus", "ERROR");
                        webServiceResult.put("message", e.getMessage());
                        return JsonConverter.convert(webServiceResult);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.info("=======Web Service 第" + (i) + "位老人处理出错:", e);
                logger.info("=======Web Service 第" + (i) + "位老人处理出错:" + e.getMessage());
                String message = "第" + (i) + "位老人处理出错:" + e.getMessage();
                webServiceResult.put("responseStatus", "ERROR");
                webServiceResult.put("message", message);
                return JsonConverter.convert(webServiceResult);
            }
        } else {
            logger.info("=======Web Service反序列化Object不属于HealthEntity[]");
            webServiceResult.put("responseStatus", "ERROR");
            webServiceResult.put("message", "终端上传数据异常,Web Service反序列化Object不属于HealthEntity[]");
            return JsonConverter.convert(webServiceResult);
        }
        logger.info("=======Web Service 所有信息已经处理完成.");
        webServiceResult.put("responseStatus", "SUCCESS");
        webServiceResult.put("message", "共处理了" + uploadOldPersonTotal + "位老年人,已全部成功.");
        return JsonConverter.convert(webServiceResult);
    }


    @WebMethod
    public String download(@WebParam(name = "token") String token, @WebParam(name = "pageNum") Integer pageNum,
        @WebParam(name = "pageSize") Integer pageSize) {
        User user = getUser(token);
        if (null == user) {
            logger.info("=======Web Service 您没有权限访问.");
            return "您没有权限访问.";
        }
        OperatorService operatorService = (OperatorService) WebApplicationContextUtil.getBean("operatorService");
        OldPersonService oldPersonService = (OldPersonService) WebApplicationContextUtil.getBean("oldPersonService");
        AreaConfigService areaConfigService = (AreaConfigService) WebApplicationContextUtil
            .getBean("areaManageService");
        FingerprintCollectService fingerprintCollectService = (FingerprintCollectService) WebApplicationContextUtil
            .getBean("fingerprintCollectService");
        CycleService cycleService = (CycleService) WebApplicationContextUtil.getBean("cycleService");
        HealthInfoService healthInfoService = (HealthInfoService) WebApplicationContextUtil
            .getBean("healthInfoService");

        String operatorIdCard = user.getAccount();
        logger.info("=======Web Service 操作员身份证号:" + operatorIdCard);
        OperatorEntity operatorEntity = operatorService.getOperatorByIdentityCard(operatorIdCard);
        if (null == operatorEntity) {
            logger.info("=======Web Service 没有找到对应的操作员.身份证号:【" + operatorIdCard + "】");
            return "没有找到对应的操作员.身份证号:【" + operatorIdCard + "】";
        }
        logger.info("=======Web Service 操作员信息已查到,操作员姓名:【" + operatorEntity.getName() + "】");
        List<String> areaCollection = areaConfigService.getAreaIdCollectionWithOperatorId(operatorEntity.getId());
        if (null == areaCollection || areaCollection.size() <= 0) {
            logger.info("=======Web Service 操作员没有对应的管理地区.因此也无法查询对应的老年人");
            return "操作员没有对应的管理地区.因此也无法查询对应的老年人";
        }
        //List<OldPersonEntity> oldPersonCollection = oldPersonService.getLocalOldPersonWithArea(areaCollection);
        PagerRequest page = new PagerRequest();
        page.setPage(pageNum);
        page.setRows(pageSize);
        logger.info("=======Web Service 分页请求:" + page);
        Page<OldPersonEntity> oldPersonCollection = oldPersonService
            .getLocalOldPersonWithArea(areaCollection, page.getInstance());
        if (null == oldPersonCollection || oldPersonCollection.getContent().size() <= 0) {
            logger.info("=======Web Service 操作员没有任何老年人数据.");
            return "操作员没有任何老年人数据.";
        }

        DownloadDataInfoVO[] downloadCollection = new DownloadDataInfoVO[oldPersonCollection.getContent().size()];
        CycleEntity cycle = null;
        try {
            cycle = cycleService.getCycle(new Date());
        } catch (IllegalArgumentException e) {
            logger.info("=======Web Service " + e.getMessage());
            return e.getMessage();
        }
        int index = 0;
        for (OldPersonEntity oldPersonEntity : oldPersonCollection.getContent()) {
            if (null != oldPersonEntity && null != cycle) {
                boolean hasHealth = healthInfoService.hasHealthInCycleByOldPersonId(oldPersonEntity.getId(), cycle);
                if (hasHealth) {
                    continue;
                }
            }
            DownloadDataInfoVO downloadDataInfoVO = new DownloadDataInfoVO();
            DownloadOldPersonInfoVO downloadOldPersonInfoVO = new DownloadOldPersonInfoVO(oldPersonEntity);
            downloadDataInfoVO.setOldPerson(downloadOldPersonInfoVO);
            FingerprintCollectEntity fingerprintCollect = fingerprintCollectService
                .getFingerprintCollectByOldPersonId(oldPersonEntity.getId());
            DownloadOldPersonFingerprintVO fingerprint = createDownloadOldPersonFingerprintVO(fingerprintCollect);
            downloadDataInfoVO.setFingerprint(fingerprint);
            downloadCollection[index++] = downloadDataInfoVO;
        }
        logger.info("=======Web Service 老年人数据已加载完毕,共有加载" + oldPersonCollection.getSize() + "/" + oldPersonCollection
            .getTotalElements() + "位老年人.");
        return SerializerHelper.serialize(downloadCollection);
    }

    @WebMethod
    public String downloadByOldPersonId(@WebParam(name = "token") String token,
        @WebParam(name = "oldPersonId") String oldPersonId) {
        User user = getUser(token);
        if (null == user) {
            logger.info("=======Web Service 您没有权限访问.");
            return "您没有权限访问.";
        }
        OldPersonService oldPersonService = (OldPersonService) WebApplicationContextUtil.getBean("oldPersonService");
        FingerprintCollectService fingerprintCollectService = (FingerprintCollectService) WebApplicationContextUtil
            .getBean("fingerprintCollectService");
        CycleService cycleService = (CycleService) WebApplicationContextUtil.getBean("cycleService");
        HealthInfoService healthInfoService = (HealthInfoService) WebApplicationContextUtil
            .getBean("healthInfoService");

        OldPersonEntity oldPersonEntity = oldPersonService.getOldPerson(oldPersonId);

        DownloadDataInfoVO[] downloadCollection = new DownloadDataInfoVO[1];
        CycleEntity cycle = null;
        try {
            cycle = cycleService.getCycle(new Date());
        } catch (IllegalArgumentException e) {
            logger.info("=======Web Service " + e.getMessage());
            return e.getMessage();
        }
        int index = 0;
        if (null != oldPersonEntity && null != cycle) {
            boolean hasHealth = healthInfoService.hasHealthInCycleByOldPersonId(oldPersonEntity.getId(), cycle);
            if (hasHealth) {
                logger.info("=======Web Service 老年人数据已加载完毕,但是没有体检信息.");
                return null;
            }
        }
        DownloadDataInfoVO downloadDataInfoVO = new DownloadDataInfoVO();
        DownloadOldPersonInfoVO downloadOldPersonInfoVO = new DownloadOldPersonInfoVO(oldPersonEntity);
        downloadDataInfoVO.setOldPerson(downloadOldPersonInfoVO);
        FingerprintCollectEntity fingerprintCollect = fingerprintCollectService
            .getFingerprintCollectByOldPersonId(oldPersonEntity.getId());
        DownloadOldPersonFingerprintVO fingerprint = createDownloadOldPersonFingerprintVO(fingerprintCollect);
        downloadDataInfoVO.setFingerprint(fingerprint);
        downloadCollection[index++] = downloadDataInfoVO;
        logger.info("=======Web Service 老年人数据已加载完毕,共有加载1/1位老年人.");
        return SerializerHelper.serialize(downloadCollection);
    }

    private DownloadOldPersonFingerprintVO createDownloadOldPersonFingerprintVO(
        FingerprintCollectEntity fingerprintCollect) {
        if (null == fingerprintCollect) {
            return null;
        }
        DownloadOldPersonFingerprintVO downloadOldPersonFingerprintVO = new DownloadOldPersonFingerprintVO();
        if (null != fingerprintCollect.getFingerprintTemplate()) {
            downloadOldPersonFingerprintVO.setFingerprintMold(fingerprintCollect.getFingerprintTemplate());
        }
        downloadOldPersonFingerprintVO.setFingerEnum(FingerEnum.parse(fingerprintCollect.getFinger()));
        if (null != fingerprintCollect.getFingerprintCharOne()) {
            downloadOldPersonFingerprintVO.setFingerprintChar(fingerprintCollect.getFingerprintCharOne());
        }
        if (StringHelper.isNotEmpty(fingerprintCollect.getFingerVerifyState())) {
            downloadOldPersonFingerprintVO
                .setFingerState(FingerState.parse(Integer.parseInt(fingerprintCollect.getFingerVerifyState())));
        }
        if (StringHelper.isNotEmpty(fingerprintCollect.getHand())) {
            downloadOldPersonFingerprintVO.setHandEnum(HandEnum.parse(Integer.parseInt(fingerprintCollect.getHand())));
        }
        return downloadOldPersonFingerprintVO;
    }


    public User getUser(String token) {
        logger.info("web service 拿到的 token ： 【" + token + "】");
        if (StringHelper.isEmpty(token)) {
            return null;
        }
        MessageContext mc = wsContext.getMessageContext();
        HttpServletRequest request = (HttpServletRequest) (mc.get(MessageContext.SERVLET_REQUEST));
        logger.info("web service 客户端访问地址：" + request.getRemoteAddr());
        Object userObject = request.getServletContext().getAttribute(
            OperatorSecurityService.SERVLET_OPERATOR_TOKEN_KEY_PREFIX + token);
        if (userObject instanceof User) {
            return (User) userObject;
        }
        return null;
    }

    public String getIpAddress() {
        MessageContext mc = wsContext.getMessageContext();
        HttpServletRequest request = (HttpServletRequest) (mc.get(MessageContext.SERVLET_REQUEST));
        return request.getRemoteAddr();
    }

}
