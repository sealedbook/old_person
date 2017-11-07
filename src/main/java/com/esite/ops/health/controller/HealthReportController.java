package com.esite.ops.health.controller;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.VerticalAlignment;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esite.framework.dictionary.entity.DictionaryEntity;
import com.esite.framework.dictionary.service.DictionaryService;
import com.esite.framework.file.entity.SysFileInfo;
import com.esite.framework.file.service.impl.FileService;
import com.esite.framework.organize.entity.OrganizeViewEntity;
import com.esite.framework.organize.service.impl.OrganizeService;
import com.esite.framework.security.entity.Customer;
import com.esite.framework.util.StringHelper;
import com.esite.ops.health.entity.HealthInfoEntity;
import com.esite.ops.health.entity.HealthResultEntity;
import com.esite.ops.health.service.impl.HealthInfoService;
import com.esite.ops.health.service.impl.HealthResultService;
import com.esite.ops.health.util.HealthResult;
import com.esite.ops.health.util.HealthResultHelper;
import com.esite.ops.mission.entity.CycleEntity;
import com.esite.ops.mission.service.impl.CycleService;
import com.esite.ops.oldperson.entity.OldPersonEntity;
import com.esite.ops.operator.entity.OperatorEntity;
import com.lowagie.text.DocWriter;
import com.lowagie.text.pdf.PdfWriter;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Controller
@RequestMapping("/health/report")
public class HealthReportController {

    private static final Logger LOG = LoggerFactory.getLogger(HealthReportController.class);

    @Resource
    private HealthInfoService healthInfoService;
    @Resource
    private OrganizeService organizeService;
    @Resource
    private CycleService cycleService;
    @Resource
    private HealthResultService healthResultService;
    @Resource
    private FileService fileService;
    @Autowired
    private DictionaryService dictionaryService;

    @RequestMapping(value = "/manager")
    public String manager() {
        return "/health/report/manager";
    }

    @RequestMapping("/{healthId}/view")
    public String view(@PathVariable String healthId, Model model) {
        HealthInfoEntity health = healthInfoService.getHealth(healthId);
        OperatorEntity operator = health.getOperator();
        OldPersonEntity oldPerson = health.getOldPerson();
        HealthResultEntity healthResult = health.getHealthResult();

        model.addAttribute("operator", operator);
        model.addAttribute("health", health);
        model.addAttribute("oldPerson", oldPerson);
        model.addAttribute("healthResult", healthResult);

        HealthResultEntity healthResultEntity = health.getHealthResult();
        if (null == healthResultEntity) {
            model.addAttribute("healthResult", "未检测");
            //心率结果 xljg
            model.addAttribute("xljg", HealthResultHelper.formaterHealthValue(0));
            model.addAttribute("xljg_jg", HealthResult.UNKNOW.getName());
            //呼吸率结果 hxljg
            model.addAttribute("hxljg", HealthResultHelper.formaterHealthValue(0));
            model.addAttribute("hxljg_jg", HealthResult.UNKNOW.getName());
            //血氧结果 xyjg
            model.addAttribute("xyjg", HealthResultHelper.formaterHealthValue(0));
            model.addAttribute("xyjg_jg", HealthResult.UNKNOW.getName());
            //脉率结果 mljg
            model.addAttribute("mljg", HealthResultHelper.formaterHealthValue(0));
            model.addAttribute("mljg_jg", HealthResult.UNKNOW.getName());
            //收缩压结果ssyjg
            model.addAttribute("ssyjg", HealthResultHelper.formaterHealthValue(0));
            model.addAttribute("ssyjg_jg", HealthResult.UNKNOW.getName());
            //舒张压结果szyjg
            model.addAttribute("szyjg", HealthResultHelper.formaterHealthValue(0));
            model.addAttribute("szyjg_jg", HealthResult.UNKNOW.getName());

            model.addAttribute("xljgfx", "");
            model.addAttribute("hxljgfx", "");
            model.addAttribute("xyjgfx", "");
            model.addAttribute("mljgfx", "");
            model.addAttribute("ssyjgfx", "");
            model.addAttribute("szyjgfx", "");

            model.addAttribute("xljgtips", "");
            model.addAttribute("hxljgtips", "");
            model.addAttribute("xyjgtips", "");
            model.addAttribute("ssyjgtips", "");
            model.addAttribute("szyjgtips", "");
        } else {
            //心率结果分析 xljg
            model.addAttribute("xljgfx",
                HealthResultHelper.getHeartRateResultAnalyze(healthResultEntity.getHeartRate()));
            //呼吸率结果分析  hxljg
            model.addAttribute("hxljgfx",
                HealthResultHelper.getRespiratoryRateResultAnalyze(healthResultEntity.getRespiratoryRate()));
            //血氧结果分析 xyjg
            model.addAttribute("xyjgfx",
                HealthResultHelper.getBloodOxygenResultAnalyze(healthResultEntity.getBloodOxygen()));
            //脉率结果分析 mljg
            model.addAttribute("mljgfx",
                HealthResultHelper.getPulseRateResultAnalyze(healthResultEntity.getPulseRate()));
            //收缩压结果分析ssyjg
            model.addAttribute("ssyjgfx",
                HealthResultHelper.getSystolicPressureResultAnalyze(healthResultEntity.getSystolicPressure()));
            //舒张压结果分析szyjg
            model.addAttribute("szyjgfx",
                HealthResultHelper.getDiastolicPressureResultAnalyze(healthResultEntity.getDiastolicPressure()));

            //心率结果小贴士
            model
                .addAttribute("xljgtips", HealthResultHelper.getHeartRateResultTips(healthResultEntity.getHeartRate()));
            //呼吸率结果小贴士
            model.addAttribute("hxljgtips",
                HealthResultHelper.getRespiratoryRateResultTips(healthResultEntity.getRespiratoryRate()));
            //血氧结果小贴士
            model.addAttribute("xyjgtips",
                HealthResultHelper.getBloodOxygenResultTips(healthResultEntity.getBloodOxygen()));
            //血压结果小贴士
            model.addAttribute("ssyjgtips",
                HealthResultHelper.getSystolicPressureResultTips(healthResultEntity.getSystolicPressure()));
            model.addAttribute("szyjgtips",
                HealthResultHelper.getDiastolicPressureResultTips(healthResultEntity.getDiastolicPressure()));
        }

        if (null == healthResult) {
            return "/health/report/noview";
        }
        return "/health/report/view";
    }

    @RequestMapping("/ecg")
    public @ResponseBody
    byte[] fingerprintPhoto(@RequestParam String healthResultId) {
        HealthResultEntity healthResult = this.healthResultService.getHealthResult(healthResultId);
        if (null == healthResult) {
            return null;
        }
        return healthResult.getEcgData();
    }

    @RequestMapping("/export/excel")
    public String exportExcel(String cycleId, HttpServletRequest request, HttpServletResponse response, Model model) {
        CycleEntity cycleEntity = this.cycleService.getCycle(cycleId);
        List<HealthInfoEntity> healthInfoEntityCollection = this.healthInfoService.getAllHealthInfoByCycleId(cycleId);
        if (null == healthInfoEntityCollection || healthInfoEntityCollection.size() <= 0) {
            return "redirect:/health/report/manager.do";
        }
        String cycleBegin = new SimpleDateFormat("yyyy-MM-dd").format(cycleEntity.getCycleBegin());
        String cycleEnd = new SimpleDateFormat("yyyy-MM-dd").format(cycleEntity.getCycleEnd());
        String title = cycleBegin + "至" + cycleEnd + "人员身份认证统计表";
        WritableWorkbook wwb = null;
        Workbook wb = null;
        try {
            response.setCharacterEncoding("UTF-8");
            String fileName = title + ".xls";
            response.addHeader("Content-Disposition",
                "attachment;filename=" + new String(fileName.getBytes(), "ISO-8859-1"));

            String filePath = FileService.class.getClassLoader().getResource("template/export.xls").getPath();

            LOG.info("export.xml template file path:[{}]", filePath);
            wb = Workbook.getWorkbook(new File(filePath));
            wwb = Workbook.createWorkbook(response.getOutputStream(), wb);
            WritableSheet wws = wwb.getSheet("Sheet1");

            WritableFont wfont1 = new WritableFont(WritableFont.ARIAL, 10);
            wfont1.setPointSize(26);
            WritableCellFormat titleFormat1 = new WritableCellFormat(wfont1);
            titleFormat1.setVerticalAlignment(VerticalAlignment.CENTRE);
            titleFormat1.setAlignment(Alignment.CENTRE);
            Label excelTitle = new Label(0, 0, title, titleFormat1);
            wws.addCell(excelTitle);

            WritableFont wfont2 = new WritableFont(WritableFont.ARIAL, 10);
            WritableCellFormat titleFormat2 = new WritableCellFormat(wfont2);
            titleFormat2.setVerticalAlignment(VerticalAlignment.CENTRE);
            titleFormat2.setAlignment(Alignment.RIGHT);
            Label creater = new Label(0, 1, "制表人：" + new Customer(request).getUser().getShowName(), titleFormat2);
            wws.addCell(creater);

            WritableFont wfont3 = new WritableFont(WritableFont.ARIAL, 10);
            WritableCellFormat titleFormat3 = new WritableCellFormat(wfont3);
            titleFormat3.setVerticalAlignment(VerticalAlignment.CENTRE);
            titleFormat3.setAlignment(Alignment.RIGHT);
            Label createrTime = new Label(0, 2, "制表日期：" + new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()),
                titleFormat3);
            wws.addCell(createrTime);

            int x = 0, y = 6;
            for (HealthInfoEntity healthInfoEntity : healthInfoEntityCollection) {
                OldPersonEntity oldPerson = healthInfoEntity.getOldPerson();
                if (null == oldPerson) {
                    continue;
                }
                x = 0;
                Label name = new Label(x++, y, oldPerson.getName());
                wws.addCell(name);

                Label idCard = new Label(x++, y, oldPerson.getIdCard());
                wws.addCell(idCard);

                Label socialNumber = new Label(x++, y, oldPerson.getSocialNumber());
                wws.addCell(socialNumber);

                OrganizeViewEntity org = organizeService.getOrganizeById(oldPerson.getRootAreaId());
                if (null != org) {
                    Label orgLabel = new Label(x++, y, org.getName());
                    wws.addCell(orgLabel);
                } else {
                    Label orgLabel = new Label(x++, y, "");
                    wws.addCell(orgLabel);
                }

                Label orgLabel = new Label(x++, y, oldPerson.getArea().getName());
                wws.addCell(orgLabel);

                String sflx = oldPerson.getSflx();
                DictionaryEntity sflxDicEntity = this.dictionaryService.getDictionaryByParentIdAndCode("sflx", sflx);
                if (null != sflxDicEntity) {
                    Label sflxLabel = new Label(x++, y, sflxDicEntity.getDicName());
                    wws.addCell(sflxLabel);
                } else {
                    Label sflxLabel = new Label(x++, y, "");
                    wws.addCell(sflxLabel);
                }

                int oldPersonType = oldPerson.getType();
                DictionaryEntity oldPersonTypeDicEntity = this.dictionaryService
                    .getDictionaryByParentIdAndCode("lnrlb", "" + oldPersonType);
                if (null != oldPersonTypeDicEntity) {
                    Label sflxLabel = new Label(x++, y, oldPersonTypeDicEntity.getDicName());
                    wws.addCell(sflxLabel);
                } else {
                    Label sflxLabel = new Label(x++, y, "");
                    wws.addCell(sflxLabel);
                }

                String fingerVerifyState = healthInfoEntity.getFingerVerifyState();
                DictionaryEntity fingerVerifyStateDicEntity = this.dictionaryService
                    .getDictionaryByParentIdAndCode("fingerVerifyState", fingerVerifyState);
                if (null != fingerVerifyStateDicEntity) {
                    Label sflxLabel = new Label(x++, y, fingerVerifyStateDicEntity.getDicName());
                    wws.addCell(sflxLabel);
                } else {
                    Label sflxLabel = new Label(x++, y, "");
                    wws.addCell(sflxLabel);
                }

                String verifyState = healthInfoEntity.getVerifyState();
                DictionaryEntity verifyStateDicEntity = this.dictionaryService
                    .getDictionaryByParentIdAndCode("shzt", verifyState);
                if (null != verifyStateDicEntity) {
                    Label sflxLabel = new Label(x++, y, verifyStateDicEntity.getDicName());
                    wws.addCell(sflxLabel);
                } else {
                    Label sflxLabel = new Label(x++, y, "");
                    wws.addCell(sflxLabel);
                }

                ++y;
            }
            wwb.write();
        } catch (BiffException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RowsExceededException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        } finally {
            wb.close();
            try {
                wwb.close();
            } catch (WriteException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return "redirect:/health/report/manager.do";
    }

    @RequestMapping("/export/word")
    public String exportWord(String cycleId, HttpServletRequest request, HttpServletResponse response, Model model)
        throws UnsupportedEncodingException {
        CycleEntity cycleEntity = this.cycleService.getCycle(cycleId);
        List<HealthInfoEntity> healthInfoEntityCollection = this.healthInfoService.getAllHealthInfoByCycleId(cycleId);
        if (null == healthInfoEntityCollection || healthInfoEntityCollection.size() <= 0) {
            return "redirect:/health/report/manager.do";
        }
        String cycleBegin = new SimpleDateFormat("yyyy-MM-dd").format(cycleEntity.getCycleBegin());
        String cycleEnd = new SimpleDateFormat("yyyy-MM-dd").format(cycleEntity.getCycleEnd());
        String title = cycleBegin + "至" + cycleEnd + "人员身份认证汇总.doc";

        response.setCharacterEncoding("UTF-8");
        String fileName = title;
        response
            .addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes(), "ISO-8859-1"));

        Configuration configuration = new Configuration();
        configuration.setDefaultEncoding("UTF-8");
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        int idx = 0;
        for (HealthInfoEntity health : healthInfoEntityCollection) {
            idx++;
            OperatorEntity operator = health.getOperator();
            String operatorName = "";
            if (null != operator) {
                operatorName = operator.getName();
            }
            OldPersonEntity oldPerson = health.getOldPerson();
            if (null == oldPerson) {
                continue;
            }
            int healthNumber = healthInfoService.getHealthNumberByOldPerson(health.getId(), oldPerson.getId());

            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("healthNumber", healthNumber);

            SysFileInfo sysFileInfo = fileService.findByFileKey(oldPerson.getPhotoKey());
            byte[] photo = sysFileInfo.getContent();
            if (null != photo) {
                String photoBase64Str = Base64.encodeBase64String(photo);
                dataMap.put("oldPersonPhoto", photoBase64Str);
            } else {
                dataMap.put("oldPersonPhoto", "");
            }
            dataMap.put("idx", idx);

            dataMap.put("oldPersonName", oldPerson.getName());
            dataMap.put("oldPersonSocialNumber", oldPerson.getSocialNumber());
            dataMap.put("oldPersonIdCard", oldPerson.getIdCard());

            DictionaryEntity dictionarySendStatus = dictionaryService
                .getDictionaryByParentIdAndCode("sendStatus", oldPerson.getSocialStatus());
            if (null != dictionarySendStatus) {
                dataMap.put("oldPersonSocialStatus", dictionarySendStatus.getDicName());
            } else {
                dataMap.put("oldPersonSocialStatus", "未知");
            }

            DictionaryEntity dictionarySflx = dictionaryService
                .getDictionaryByParentIdAndCode("sflx", oldPerson.getSflx());
            if (null != dictionarySflx) {
                dataMap.put("oldPersonSflx", dictionarySflx.getDicName());
            } else {
                dataMap.put("oldPersonSflx", "未知");
            }
            String workUnit = oldPerson.getWorkUnit();
            if (StringHelper.isEmpty(workUnit)) {
                workUnit = "";
            }
            dataMap.put("oldPersonWorkUnit", workUnit);
            if (null == oldPerson.getJnsbrq()) {
                dataMap.put("oldPersonJnsbrq", "");
            } else {
                dataMap.put("oldPersonJnsbrq", new SimpleDateFormat("yyyy-MM-dd").format(oldPerson.getJnsbrq()));
            }
            if (null == oldPerson.getTxrq()) {
                dataMap.put("oldPersonTxrq", "");
            } else {
                dataMap.put("oldPersonTxrq", new SimpleDateFormat("yyyy-MM-dd").format(oldPerson.getTxrq()));
            }
            if (null == oldPerson.getLqsbrq()) {
                dataMap.put("oldPersonLqsbrq", "");
            } else {
                dataMap.put("oldPersonLqsbrq", new SimpleDateFormat("yyyy-MM-dd").format(oldPerson.getLqsbrq()));
            }
            String homeAddres = oldPerson.getHomeAddress();
            if (StringHelper.isEmpty(homeAddres)) {
                homeAddres = "";
            }
            dataMap.put("oldPersonHomeAddress", homeAddres);
            String phoneNumber = oldPerson.getPhoneNumber();
            if (StringHelper.isEmpty(phoneNumber)) {
                phoneNumber = "";
            }
            dataMap.put("oldPersonPhoneNumber", phoneNumber);

            DictionaryEntity dictionaryEntity = dictionaryService
                .getDictionaryByParentIdAndCode("lnrlb", "" + oldPerson.getType());
            dataMap.put("oldPersonType", dictionaryEntity.getDicName());
            dataMap.put("oldPersonAge", oldPerson.getAge());
            dataMap.put("oldPersonArea", oldPerson.getArea().getName());

            dataMap.put("operatorName", operatorName);

            dataMap.put("healthBeginTime", health.getBeginDateTime());
            dataMap.put("healthEndTime", health.getEndDateTime());

            HealthResultEntity healthResultEntity = health.getHealthResult();
            if (null == healthResultEntity) {
                dataMap.put("healthResult", "未检测");
                //心率结果 xljg
                dataMap.put("xljg", HealthResultHelper.formaterHealthValue(0));
                dataMap.put("xljg_jg", HealthResult.UNKNOW.getName());
                //呼吸率结果 hxljg
                dataMap.put("hxljg", HealthResultHelper.formaterHealthValue(0));
                dataMap.put("hxljg_jg", HealthResult.UNKNOW.getName());
                //血氧结果 xyjg
                dataMap.put("xyjg", HealthResultHelper.formaterHealthValue(0));
                dataMap.put("xyjg_jg", HealthResult.UNKNOW.getName());
                //脉率结果 mljg
                dataMap.put("mljg", HealthResultHelper.formaterHealthValue(0));
                dataMap.put("mljg_jg", HealthResult.UNKNOW.getName());
                //收缩压结果ssyjg
                dataMap.put("ssyjg", HealthResultHelper.formaterHealthValue(0));
                dataMap.put("ssyjg_jg", HealthResult.UNKNOW.getName());
                //舒张压结果szyjg
                dataMap.put("szyjg", HealthResultHelper.formaterHealthValue(0));
                dataMap.put("szyjg_jg", HealthResult.UNKNOW.getName());

                dataMap.put("xljgfx", "");
                dataMap.put("hxljgfx", "");
                dataMap.put("xyjgfx", "");
                dataMap.put("mljgfx", "");
                dataMap.put("ssyjgfx", "");
                dataMap.put("szyjgfx", "");

                dataMap.put("xljgtips", "");
                dataMap.put("hxljgtips", "");
                dataMap.put("xyjgtips", "");
                dataMap.put("ssyjgtips", "");
                dataMap.put("szyjgtips", "");
            } else {
                dataMap.put("healthResult", "");
                //心率结果 xljg
                dataMap.put("xljg", healthResultEntity.getHeartRateToString());
                dataMap.put("xljg_jg", HealthResultHelper.getHeartRateResult(healthResultEntity.getHeartRate()));
                //呼吸率结果 hxljg
                dataMap.put("hxljg", healthResultEntity.getRespiratoryRateToString());
                dataMap.put("hxljg_jg",
                    HealthResultHelper.getRespiratoryRateResult(healthResultEntity.getRespiratoryRate()));
                //血氧结果 xyjg
                dataMap.put("xyjg", healthResultEntity.getBloodOxygenToString());
                dataMap.put("xyjg_jg", HealthResultHelper.getBloodOxygenResult(healthResultEntity.getBloodOxygen()));
                //脉率结果 mljg
                dataMap.put("mljg", healthResultEntity.getPulseRateToString());
                dataMap.put("mljg_jg", HealthResultHelper.getPulseRateResult(healthResultEntity.getPulseRate()));
                //收缩压结果ssyjg
                dataMap.put("ssyjg", healthResultEntity.getSystolicPressureToString());
                dataMap.put("ssyjg_jg",
                    HealthResultHelper.getSystolicPressureResult(healthResultEntity.getSystolicPressure()));
                //舒张压结果szyjg
                dataMap.put("szyjg", healthResultEntity.getDiastolicPressureToString());
                dataMap.put("szyjg_jg",
                    HealthResultHelper.getDiastolicPressureResult(healthResultEntity.getDiastolicPressure()));

                //心率结果分析 xljg
                dataMap.put("xljgfx", HealthResultHelper.getHeartRateResultAnalyze(healthResultEntity.getHeartRate()));
                //呼吸率结果分析  hxljg
                dataMap.put("hxljgfx",
                    HealthResultHelper.getRespiratoryRateResultAnalyze(healthResultEntity.getRespiratoryRate()));
                //血氧结果分析 xyjg
                dataMap
                    .put("xyjgfx", HealthResultHelper.getBloodOxygenResultAnalyze(healthResultEntity.getBloodOxygen()));
                //脉率结果分析 mljg
                dataMap.put("mljgfx", HealthResultHelper.getPulseRateResultAnalyze(healthResultEntity.getPulseRate()));
                //收缩压结果分析ssyjg
                dataMap.put("ssyjgfx",
                    HealthResultHelper.getSystolicPressureResultAnalyze(healthResultEntity.getSystolicPressure()));
                //舒张压结果分析szyjg
                dataMap.put("szyjgfx",
                    HealthResultHelper.getDiastolicPressureResultAnalyze(healthResultEntity.getDiastolicPressure()));

                //心率结果小贴士
                dataMap.put("xljgtips", HealthResultHelper.getHeartRateResultTips(healthResultEntity.getHeartRate()));
                //呼吸率结果小贴士
                dataMap.put("hxljgtips",
                    HealthResultHelper.getRespiratoryRateResultTips(healthResultEntity.getRespiratoryRate()));
                //血氧结果小贴士
                dataMap
                    .put("xyjgtips", HealthResultHelper.getBloodOxygenResultTips(healthResultEntity.getBloodOxygen()));
                //血压结果小贴士
                dataMap.put("ssyjgtips",
                    HealthResultHelper.getSystolicPressureResultTips(healthResultEntity.getSystolicPressure()));
                dataMap.put("szyjgtips",
                    HealthResultHelper.getDiastolicPressureResultTips(healthResultEntity.getDiastolicPressure()));
            }

            DictionaryEntity verifyStatus = dictionaryService
                .getDictionaryByParentIdAndCode("fingerVerifyState", health.getFingerVerifyState());
            dataMap.put("fingerprintVerifyStatus", verifyStatus.getDicName());
            dataMap.put("photoVerifyStatus", verifyStatus.getDicName());

            list.add(dataMap);
        }
        configuration.setClassForTemplateLoading(this.getClass(), "/com/esite/ops/health/ftl");  //FTL文件所存在的位置
        Template t = null;
        try {
            t = configuration.getTemplate("OldPersonCollectionReportTemplate.ftl"); //文件名
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter out = null;
        try {
            out = response.getWriter();
            Map<String, List> m = new HashMap<String, List>();
            m.put("list", list);
            t.process(m, out);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        return "redirect:/health/report/manager.do";
    }

    @RequestMapping("/{healthId}/download")
    public void exportWord(@PathVariable String healthId, HttpServletResponse response) throws IOException {
        HealthInfoEntity health = healthInfoService.getHealth(healthId);
        OperatorEntity operator = health.getOperator();
        String operatorName = "";
        if (null != operator) {
            operatorName = operator.getName();
        }
        OldPersonEntity oldPerson = health.getOldPerson();
        if (null == oldPerson) {
            return;
        }
        int healthNumber = healthInfoService.getHealthNumberByOldPerson(healthId, oldPerson.getId());

        response.setCharacterEncoding("UTF-8");
        String fileNameFormat = "随访人员【%1$s】%2$s认证报告.doc";
        String fileName = String.format(fileNameFormat, oldPerson.getName(),
            new SimpleDateFormat("yyyy-MM-dd").format(health.getBeginDateTime()));
        response
            .addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes(), "ISO-8859-1"));
        //
        Configuration configuration = new Configuration();
        configuration.setDefaultEncoding("UTF-8");
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("healthNumber", healthNumber);

        SysFileInfo sysFileInfo = fileService.findByFileKey(oldPerson.getPhotoKey());
        byte[] photo = sysFileInfo.getContent();
        if (null != photo) {
            String photoBase64Str = Base64.encodeBase64String(photo);
            dataMap.put("oldPersonPhoto", photoBase64Str);
        } else {
            dataMap.put("oldPersonPhoto", "");
        }

        dataMap.put("oldPersonName", oldPerson.getName());
        DictionaryEntity dictionarySex = dictionaryService
            .getDictionaryByParentIdAndCode("xb", oldPerson.getSex() + "");
        if (null != dictionarySex) {
            dataMap.put("oldPersonSex", dictionarySex.getDicName());
        } else {
            dataMap.put("oldPersonSex", "未知");
        }
        dataMap.put("oldPersonSocialNumber", oldPerson.getSocialNumber());
        dataMap.put("oldPersonIdCard", oldPerson.getIdCard());

        DictionaryEntity dictionarySendStatus = dictionaryService
            .getDictionaryByParentIdAndCode("sendStatus", oldPerson.getSocialStatus());
        if (null != dictionarySendStatus) {
            dataMap.put("oldPersonSocialStatus", dictionarySendStatus.getDicName());
        } else {
            dataMap.put("oldPersonSocialStatus", "未知");
        }

        DictionaryEntity dictionarySflx = dictionaryService.getDictionaryByParentIdAndCode("sflx", oldPerson.getSflx());
        if (null != dictionarySflx) {
            dataMap.put("oldPersonSflx", dictionarySflx.getDicName());
        } else {
            dataMap.put("oldPersonSflx", "未知");
        }
        String workUnit = oldPerson.getWorkUnit();
        if (StringHelper.isEmpty(workUnit)) {
            workUnit = "";
        }
        dataMap.put("oldPersonWorkUnit", workUnit);
        if (null == oldPerson.getJnsbrq()) {
            dataMap.put("oldPersonJnsbrq", "");
        } else {
            dataMap.put("oldPersonJnsbrq", new SimpleDateFormat("yyyy-MM-dd").format(oldPerson.getJnsbrq()));
        }
        if (null == oldPerson.getTxrq()) {
            dataMap.put("oldPersonTxrq", "");
        } else {
            dataMap.put("oldPersonTxrq", new SimpleDateFormat("yyyy-MM-dd").format(oldPerson.getTxrq()));
        }
        if (null == oldPerson.getLqsbrq()) {
            dataMap.put("oldPersonLqsbrq", "");
        } else {
            dataMap.put("oldPersonLqsbrq", new SimpleDateFormat("yyyy-MM-dd").format(oldPerson.getLqsbrq()));
        }
        String homeAddres = oldPerson.getHomeAddress();
        if (StringHelper.isEmpty(homeAddres)) {
            homeAddres = "";
        }
        dataMap.put("oldPersonHomeAddress", homeAddres);
        String phoneNumber = oldPerson.getPhoneNumber();
        if (StringHelper.isEmpty(phoneNumber)) {
            phoneNumber = "";
        }
        dataMap.put("oldPersonPhoneNumber", phoneNumber);

        DictionaryEntity dictionaryEntity = dictionaryService
            .getDictionaryByParentIdAndCode("lnrlb", "" + oldPerson.getType());
        dataMap.put("oldPersonType", dictionaryEntity.getDicName());
        dataMap.put("oldPersonAge", oldPerson.getAge());
        dataMap.put("oldPersonArea", oldPerson.getArea().getName());

        dataMap.put("operatorName", operatorName);

        if (null != health.getBeginDateTime()) {
            dataMap.put("healthBeginTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(health.getBeginDateTime()));
        } else {
            dataMap.put("healthBeginTime", "");
        }
        dataMap.put("healthEndTime", health.getEndDateTime());

        HealthResultEntity healthResultEntity = health.getHealthResult();

        byte[] ecg = healthResultEntity.getEcgData();
        if (null == ecg) {
            System.out.println("心电图二进制数据是空的.oldPersonId:" + oldPerson.getId() + ".healthId:" + healthId);
//        	dataMap.put("ecgPhoto", "");
        } else {
            BufferedImage b = Rotate(ImageIO.read(new ByteArrayInputStream(ecg)), 0);
            if (null != b) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ImageIO.write(b, "png", byteArrayOutputStream);
                ecg = byteArrayOutputStream.toByteArray();
                if (null != ecg) {
                    String ecgBase64Str = Base64.encodeBase64String(ecg);
                    dataMap.put("ecgPhoto", ecgBase64Str);
                } else {
//                	dataMap.put("ecgPhoto", "");
                }
            } else {
//        		dataMap.put("ecgPhoto", "");
            }
        }
        if (null == healthResultEntity) {
            dataMap.put("healthResult", "未检测");
            //心率结果 xljg
            dataMap.put("xljg", HealthResultHelper.formaterHealthValue(0));
            dataMap.put("xljg_jg", HealthResult.UNKNOW.getName());
            //呼吸率结果 hxljg
            dataMap.put("hxljg", HealthResultHelper.formaterHealthValue(0));
            dataMap.put("hxljg_jg", HealthResult.UNKNOW.getName());
            //血氧结果 xyjg
            dataMap.put("xyjg", HealthResultHelper.formaterHealthValue(0));
            dataMap.put("xyjg_jg", HealthResult.UNKNOW.getName());
            //脉率结果 mljg
            dataMap.put("mljg", HealthResultHelper.formaterHealthValue(0));
            dataMap.put("mljg_jg", HealthResult.UNKNOW.getName());
            //收缩压结果ssyjg
            dataMap.put("ssyjg", HealthResultHelper.formaterHealthValue(0));
            dataMap.put("ssyjg_jg", HealthResult.UNKNOW.getName());
            //舒张压结果szyjg
            dataMap.put("szyjg", HealthResultHelper.formaterHealthValue(0));
            dataMap.put("szyjg_jg", HealthResult.UNKNOW.getName());

            dataMap.put("xljgfx", "");
            dataMap.put("hxljgfx", "");
            dataMap.put("xyjgfx", "");
            dataMap.put("mljgfx", "");
            dataMap.put("ssyjgfx", "");
            dataMap.put("szyjgfx", "");

            dataMap.put("xljgtips", "");
            dataMap.put("hxljgtips", "");
            dataMap.put("xyjgtips", "");
            dataMap.put("ssyjgtips", "");
            dataMap.put("szyjgtips", "");
        } else {
            dataMap.put("healthResult", "");
            //心率结果 xljg
            dataMap.put("xljg", healthResultEntity.getHeartRateToString());
            dataMap.put("xljg_jg", HealthResultHelper.getHeartRateResult(healthResultEntity.getHeartRate()));
            //呼吸率结果 hxljg
            dataMap.put("hxljg", healthResultEntity.getRespiratoryRateToString());
            dataMap
                .put("hxljg_jg", HealthResultHelper.getRespiratoryRateResult(healthResultEntity.getRespiratoryRate()));
            //血氧结果 xyjg
            dataMap.put("xyjg", healthResultEntity.getBloodOxygenToString());
            dataMap.put("xyjg_jg", HealthResultHelper.getBloodOxygenResult(healthResultEntity.getBloodOxygen()));
            //脉率结果 mljg
            dataMap.put("mljg", healthResultEntity.getPulseRateToString());
            dataMap.put("mljg_jg", HealthResultHelper.getPulseRateResult(healthResultEntity.getPulseRate()));
            //收缩压结果ssyjg
            dataMap.put("ssyjg", healthResultEntity.getSystolicPressureToString());
            dataMap.put("ssyjg_jg",
                HealthResultHelper.getSystolicPressureResult(healthResultEntity.getSystolicPressure()));
            //舒张压结果szyjg
            dataMap.put("szyjg", healthResultEntity.getDiastolicPressureToString());
            dataMap.put("szyjg_jg",
                HealthResultHelper.getDiastolicPressureResult(healthResultEntity.getDiastolicPressure()));

            //心率结果分析 xljg
            dataMap.put("xljgfx", HealthResultHelper.getHeartRateResultAnalyze(healthResultEntity.getHeartRate()));
            //呼吸率结果分析  hxljg
            dataMap.put("hxljgfx",
                HealthResultHelper.getRespiratoryRateResultAnalyze(healthResultEntity.getRespiratoryRate()));
            //血氧结果分析 xyjg
            dataMap.put("xyjgfx", HealthResultHelper.getBloodOxygenResultAnalyze(healthResultEntity.getBloodOxygen()));
            //脉率结果分析 mljg
            dataMap.put("mljgfx", HealthResultHelper.getPulseRateResultAnalyze(healthResultEntity.getPulseRate()));
            //收缩压结果分析ssyjg
            dataMap.put("ssyjgfx",
                HealthResultHelper.getSystolicPressureResultAnalyze(healthResultEntity.getSystolicPressure()));
            //舒张压结果分析szyjg
            dataMap.put("szyjgfx",
                HealthResultHelper.getDiastolicPressureResultAnalyze(healthResultEntity.getDiastolicPressure()));

            //心率结果小贴士
            dataMap.put("xljgtips", HealthResultHelper.getHeartRateResultTips(healthResultEntity.getHeartRate()));
            //呼吸率结果小贴士
            dataMap.put("hxljgtips",
                HealthResultHelper.getRespiratoryRateResultTips(healthResultEntity.getRespiratoryRate()));
            //血氧结果小贴士
            dataMap.put("xyjgtips", HealthResultHelper.getBloodOxygenResultTips(healthResultEntity.getBloodOxygen()));
            //血压结果小贴士
            dataMap.put("ssyjgtips",
                HealthResultHelper.getSystolicPressureResultTips(healthResultEntity.getSystolicPressure()));
            dataMap.put("szyjgtips",
                HealthResultHelper.getDiastolicPressureResultTips(healthResultEntity.getDiastolicPressure()));
        }

        DictionaryEntity verifyStatus = dictionaryService
            .getDictionaryByParentIdAndCode("fingerVerifyState", health.getFingerVerifyState());
        dataMap.put("fingerprintVerifyStatus", verifyStatus.getDicName());
        dataMap.put("photoVerifyStatus", verifyStatus.getDicName());

        String filePath = FileService.class.getClassLoader().getResource("template").getPath();
        configuration.setDirectoryForTemplateLoading(new File(filePath));  //FTL文件所存在的位置
        Template t = null;
        try {
            t = configuration.getTemplate("OldPersonReportTemplate.ftl"); //文件名
            //t = configuration.getTemplate("test.xml"); //文件名
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter out = null;
        try {
            out = response.getWriter();
            t.process(dataMap, out);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }

    public static BufferedImage Rotate(Image src, int angel) {
        if (null == src) {
            return null;
        }
        int src_width = src.getWidth(null);
        int src_height = src.getHeight(null);
        // calculate the new image size  
        Rectangle rect_des = CalcRotatedSize(new Rectangle(new Dimension(
            src_width, src_height)), angel);

        BufferedImage res = null;
        res = new BufferedImage(rect_des.width, rect_des.height,
            BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = res.createGraphics();
        // transform  
        g2.translate((rect_des.width - src_width) / 2,
            (rect_des.height - src_height) / 2);
        g2.rotate(Math.toRadians(angel), src_width / 2, src_height / 2);

        g2.drawImage(src, null, null);
        return res;
    }

    public static Rectangle CalcRotatedSize(Rectangle src, int angel) {
        // if angel is greater than 90 degree, we need to do some conversion  
        if (angel >= 90) {
            if (angel / 90 % 2 == 1) {
                int temp = src.height;
                src.height = src.width;
                src.width = temp;
            }
            angel = angel % 90;
        }

        double r = Math.sqrt(src.height * src.height + src.width * src.width) / 2;
        double len = 2 * Math.sin(Math.toRadians(angel) / 2) * r;
        double angel_alpha = (Math.PI - Math.toRadians(angel)) / 2;
        double angel_dalta_width = Math.atan((double) src.height / src.width);
        double angel_dalta_height = Math.atan((double) src.width / src.height);

        int len_dalta_width = (int) (len * Math.cos(Math.PI - angel_alpha
            - angel_dalta_width));
        int len_dalta_height = (int) (len * Math.cos(Math.PI - angel_alpha
            - angel_dalta_height));
        int des_width = src.width + len_dalta_width * 2;
        int des_height = src.height + len_dalta_height * 2;
        return new java.awt.Rectangle(new Dimension(des_width, des_height));
    }
}
