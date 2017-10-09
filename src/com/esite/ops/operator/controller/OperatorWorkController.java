package com.esite.ops.operator.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esite.framework.dictionary.entity.DictionaryEntity;
import com.esite.framework.dictionary.service.DictionaryService;
import com.esite.framework.organize.entity.OrganizeViewEntity;
import com.esite.framework.organize.service.OrganizeService;
import com.esite.framework.security.entity.Customer;
import com.esite.framework.util.StringHelper;
import com.esite.ops.health.entity.HealthInfoEntity;
import com.esite.ops.mission.entity.CycleEntity;
import com.esite.ops.mission.service.ICycleService;
import com.esite.ops.oldperson.entity.OldPersonEntity;
import com.esite.ops.operator.service.IOperatorService;

@Controller
@RequestMapping("/operator/work")
public class OperatorWorkController {

	@Autowired
	private ICycleService cycleService;
	
	@Autowired
	private IOperatorService operatorService;
	
	@Autowired
	private OrganizeService organizeService;
	
	@Autowired
	private DictionaryService dictionaryService;
	
	@RequestMapping("/manager")
	public String managerPage(String cycleId,Model model) {
		CycleEntity cycle = null;
		try {
			if(StringHelper.isEmpty(cycleId)) {
				cycle = cycleService.getCycle(new Date());
			} else {
				cycle = cycleService.getCycle(cycleId);
			}
			model.addAttribute("cycle", cycle);
		} catch(IllegalArgumentException e) {
		}
		return "/operator/work/manager";
	}
	
	@RequestMapping("/list")
	public @ResponseBody List<Map<String,Object>> list(@RequestParam String cycleId) {
		return this.operatorService.workSchedule(cycleId);
	}
	
	@RequestMapping("/list/export/excel")
	public String exportExcel(String cycleId,HttpServletRequest request,HttpServletResponse response,Model model) {
		CycleEntity cycleEntity = this.cycleService.getCycle(cycleId);
		List<Map<String,Object>> operatorWorkStatistics = this.operatorService.workSchedule(cycleId);
		if(null == operatorWorkStatistics || operatorWorkStatistics.size() <= 0) {
			return "redirect:/operator/work/manager.do";
		}
		String cycleBegin = new SimpleDateFormat("yyyy-MM-dd").format(cycleEntity.getCycleBegin());
		String cycleEnd = new SimpleDateFormat("yyyy-MM-dd").format(cycleEntity.getCycleEnd());
		String title = cycleBegin + "至" + cycleEnd + "操作员工作统计表";
		WritableWorkbook wwb = null;
		Workbook wb = null;
		try {
			response.setCharacterEncoding("UTF-8");
			String fileName = title + ".xls";
			response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes(), "ISO-8859-1")); 
			
			wb = Workbook.getWorkbook(new File(new File(this.getClass().getResource("").getPath()).getParent() + "/template/export.xls"));
			wwb = Workbook.createWorkbook(response.getOutputStream(), wb);
			WritableSheet wws = wwb.getSheet("Sheet1");
			
			int x =0 ,y = 1;
			for(Map<String,Object> operatorWork : operatorWorkStatistics) {
				if(null == operatorWork) {
					continue;
				}
				x = 0;
				String oldPersonCount = "";
				if(null != operatorWork.get("old_person_count")) {
					oldPersonCount = operatorWork.get("old_person_count").toString();
				}
				Label name = new Label(x++,y,oldPersonCount);
				wws.addCell(name);
				
				String healthTotal = "";
				if(null != operatorWork.get("health_total")) {
					healthTotal = operatorWork.get("health_total").toString();
				}
				Label idCard = new Label(x++,y,healthTotal);
				wws.addCell(idCard);
				
				String work = "";
				if(!StringUtils.hasText(oldPersonCount)) {
					oldPersonCount = "0";
				}
				work =  healthTotal + "/" + oldPersonCount;
				Label socialNumber = new Label(x++,y,work);
				wws.addCell(socialNumber);
				
				String oreratorName = "";
				if(null != operatorWork.get("NAME")) {
					oreratorName = operatorWork.get("NAME").toString();
				}
				Label oreratorNameLabel = new Label(x++,y,oreratorName);
				wws.addCell(oreratorNameLabel);
				
				
				String operatorSex = "";
				if(null != operatorWork.get("SEX")) {
					operatorSex = operatorWork.get("SEX").toString();
					DictionaryEntity operatorSexDicEntity = this.dictionaryService.getDictionaryByParentIdAndCode("xb", operatorSex);
					if(null != operatorSexDicEntity) {
						operatorSex = operatorSexDicEntity.getDicName();
					}
				}
				Label operatorSexLabel = new Label(x++,y,oreratorName);
				wws.addCell(operatorSexLabel);
				
				String operatorIdCard = "";
				if(null != operatorWork.get("ID_CARD")) {
					operatorIdCard = operatorWork.get("ID_CARD").toString();
				}
				Label operatorIdCardLabel = new Label(x++,y,operatorIdCard);
				wws.addCell(operatorIdCardLabel);
				
				
				String operatorBirthday = "";
				if(null != operatorWork.get("BIRTHDAY")) {
					operatorBirthday = operatorWork.get("BIRTHDAY").toString();
				}
				Label operatorBirthdayLabel = new Label(x++,y,operatorBirthday);
				wws.addCell(operatorBirthdayLabel);
				
				if(null != operatorWork.get("MANAGE_AREA")) {
					String manageAreaArrayStr = operatorWork.get("MANAGE_AREA").toString().replaceAll("\\[", "").replaceAll("\\]", "");
					String[] manageAreaArray = manageAreaArrayStr.split(",");
					List<OrganizeViewEntity> organizeViewEntityList = this.organizeService.getOrganizeByIdArray(manageAreaArray);
					if(null != organizeViewEntityList) {
						StringBuffer sb = new StringBuffer();
						for(OrganizeViewEntity organizeViewEntity : organizeViewEntityList) {
							sb.append(organizeViewEntity.getName()).append(" ");
						}
						Label manageAreaLabel = new Label(x++,y,sb.toString());
						wws.addCell(manageAreaLabel);
					}
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
		return null;
	}
	
}
