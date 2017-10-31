package com.esite.ops.statistics.controller;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.esite.framework.organize.entity.OrganizeViewEntity;
import com.esite.framework.organize.service.impl.OrganizeService;
import com.esite.framework.util.StringHelper;
import com.esite.ops.mission.entity.CycleEntity;
import com.esite.ops.mission.service.impl.CycleService;
import com.esite.ops.statistics.service.impl.StatisticsService;

@Controller
@RequestMapping("/statistics")
public class StatisticsController {
	
	@Resource
	private CycleService cycleService;
	@Resource
	private StatisticsService statisticsService;
	@Resource
	private OrganizeService organizeService;
	
	@RequestMapping("/rzbfb")
	public String rzbfb(String cycleId,String areaId,Model model) {
		CycleEntity cycle = null;
		try {
			if(StringHelper.isEmpty(cycleId)) {
				cycle = cycleService.getCycle(new Date());
			} else {
				cycle = cycleService.getCycle(cycleId);
			}
			model.addAttribute("cycle", cycle);
			List<Map<String, Object>> rzbfb = null;
			if(StringHelper.isEmpty(areaId)) {
				rzbfb = statisticsService.statisticsRzbfb(cycle);
			} else {
				rzbfb = statisticsService.statisticsRzbfb(cycle,areaId);
			}
			model.addAttribute("rzbfb", rzbfb);
			model.addAttribute("areaId", areaId);
			model.addAttribute("cycleId", cycle.getId());
		} catch(IllegalArgumentException e) {
			
		}
		return "/statistics/rzbfb";
	}
	
	@RequestMapping("/rzbfb/export/excel")
	public String exportExcel(String cycleId,String areaId,HttpServletRequest request,HttpServletResponse response,Model model) {
		CycleEntity cycleEntity = this.cycleService.getCycle(cycleId);
		List<Map<String, Object>> rzbfb = null;
		if(StringHelper.isEmpty(areaId)) {
			rzbfb = statisticsService.statisticsRzbfb(cycleEntity);
		} else {
			rzbfb = statisticsService.statisticsRzbfb(cycleEntity,areaId);
		}
		if(null == rzbfb || rzbfb.size() <= 0) {
			return "redirect:/statistics/rzbfb.do";
		}
		
		String cycleBegin = new SimpleDateFormat("yyyy-MM-dd").format(cycleEntity.getCycleBegin());
		String cycleEnd = new SimpleDateFormat("yyyy-MM-dd").format(cycleEntity.getCycleEnd());
		String title = cycleBegin + "至" + cycleEnd + "认证百分比统计表";
		WritableWorkbook wwb = null;
		Workbook wb = null;
		try {
			response.setCharacterEncoding("UTF-8");
			String fileName = title + ".xls";
			response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes(), "ISO-8859-1")); 
			
			wb = Workbook.getWorkbook(new File(new File(this.getClass().getResource("").getPath()).getParent() + "/template/rzbfbexport.xls"));
			wwb = Workbook.createWorkbook(response.getOutputStream(), wb);
			WritableSheet wws = wwb.getSheet("Sheet1");
			
			int x =0 ,y = 1;
			for(Map<String,Object> rzbfbMap : rzbfb) {
				if(null == rzbfbMap) {
					continue;
				}
				x = 0;
				String areaName = "";
				if(null != rzbfbMap.get("area_name")) {
					areaName = rzbfbMap.get("area_name").toString();
				} else {
					areaName = "泰州市";
				}
				Label name = new Label(x++,y,areaName);
				wws.addCell(name);
				
				String totalPeople = "";
				if(null != rzbfbMap.get("total")) {
					totalPeople = rzbfbMap.get("total").toString();
				}
				Label idCard = new Label(x++,y,totalPeople);
				wws.addCell(idCard);
				
				String bdPeopleTotal = "";
				if(null != rzbfbMap.get("bd")) {
					bdPeopleTotal = rzbfbMap.get("bd").toString();
				}
				Label oreratorNameLabel = new Label(x++,y,bdPeopleTotal);
				wws.addCell(oreratorNameLabel);
				
				String bdPeopleZbTotal = "";
				if(null != rzbfbMap.get("bd_bfb")) {
					bdPeopleZbTotal = rzbfbMap.get("bd_bfb").toString();
				}
				Label bdPeopleZbTotalLabel = new Label(x++,y,bdPeopleZbTotal);
				wws.addCell(bdPeopleZbTotalLabel);
				
				String wdPeopleTotal = "";
				if(null != rzbfbMap.get("wd")) {
					bdPeopleTotal = rzbfbMap.get("wd").toString();
				}
				Label wdPeopleTotalLabel = new Label(x++,y,wdPeopleTotal);
				wws.addCell(wdPeopleTotalLabel);
				
				String wdPeopleZbTotal = "";
				if(null != rzbfbMap.get("wd_bfb")) {
					wdPeopleZbTotal = rzbfbMap.get("wd_bfb").toString();
				}
				Label wdPeopleZbTotalLabel = new Label(x++,y,wdPeopleZbTotal);
				wws.addCell(wdPeopleZbTotalLabel);
				
				String wrzPeopleTotal = "";
				if(null != rzbfbMap.get("wrz")) {
					wrzPeopleTotal = rzbfbMap.get("wrz").toString();
				}
				Label wrzPeopleTotalLabel = new Label(x++,y,wrzPeopleTotal);
				wws.addCell(wrzPeopleTotalLabel);
				
				
				String wrzPeopleZbTotal = "";
				if(null != rzbfbMap.get("wrz_bfb")) {
					wrzPeopleZbTotal = rzbfbMap.get("wrz_bfb").toString();
				}
				Label wrzPeopleZbTotalLabel = new Label(x++,y,wrzPeopleZbTotal);
				wws.addCell(wrzPeopleZbTotalLabel);
				
				String yrzPeopleTotal = "";
				if(null != rzbfbMap.get("yrz")) {
					yrzPeopleTotal = rzbfbMap.get("yrz").toString();
				}
				Label yrzPeopleTotalLabel = new Label(x++,y,yrzPeopleTotal);
				wws.addCell(yrzPeopleTotalLabel);
				
				String yrzPeopleZbTotal = "";
				if(null != rzbfbMap.get("yrz_bfb")) {
					yrzPeopleZbTotal = rzbfbMap.get("yrz_bfb").toString();
				}
				Label yrzPeopleZbTotalLabel = new Label(x++,y,yrzPeopleZbTotal);
				wws.addCell(yrzPeopleZbTotalLabel);
				
				String bdYrzPeopleTotal = "";
				if(null != rzbfbMap.get("bd_yrz")) {
					bdYrzPeopleTotal = rzbfbMap.get("bd_yrz").toString();
				}
				Label bdYrzPeopleTotalLabel = new Label(x++,y,bdYrzPeopleTotal);
				wws.addCell(bdYrzPeopleTotalLabel);
				
				String wdYrzPeopleTotal = "";
				if(null != rzbfbMap.get("wd_yrz")) {
					wdYrzPeopleTotal = rzbfbMap.get("wd_yrz").toString();
				}
				Label wdYrzPeopleTotalLabel = new Label(x++,y,wdYrzPeopleTotal);
				wws.addCell(wdYrzPeopleTotalLabel);
				
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
	
	@RequestMapping("/jkrz")
	public String jkrz(String cycleId,Model model) {
		CycleEntity cycle = null;
		try {
			if(StringHelper.isEmpty(cycleId)) {
				cycle = cycleService.getCycle(new Date());
			} else {
				cycle = cycleService.getCycle(cycleId);
			}
			model.addAttribute("cycle", cycle);
			model.addAttribute("cycleId", cycle.getId());
			List<Map<String, Object>> jkrz = statisticsService.statisticsJkrz(cycle);
			StringBuffer title = new StringBuffer();
			StringBuffer bdYrz = new StringBuffer();
			StringBuffer bdWrz = new StringBuffer();
			StringBuffer wdYrz = new StringBuffer();
			StringBuffer wdWrz = new StringBuffer();
			
			for(Map<String,Object> m : jkrz) {
				if(null != m.get("area_name")) {
					title.append("'").append(m.get("area_name").toString()).append("'").append(",");
				}
				if(null != m.get("bd_yrz")) {
					bdYrz.append("'").append(m.get("bd_yrz").toString()).append("'").append(",");
				}
				if(null != m.get("bd_wrz")) {
					bdWrz.append("'").append(m.get("bd_wrz").toString()).append("'").append(",");
				}
				if(null != m.get("wd_yrz")) {
					wdYrz.append("'").append(m.get("wd_yrz").toString()).append("'").append(",");
				}
				if(null != m.get("wd_wrz")) {
					wdWrz.append("'").append(m.get("wd_wrz").toString()).append("'").append(",");
				}
			}
			if(title.length() > 0 && title.charAt(title.length()-1) == ',') {
				title.deleteCharAt(title.length()-1);
			}
			if(bdYrz.length() > 0 && bdYrz.charAt(bdYrz.length()-1) == ',') {
				bdYrz.deleteCharAt(bdYrz.length()-1);
			}
			if(bdWrz.length() > 0 && bdWrz.charAt(bdWrz.length()-1) == ',') {
				bdWrz.deleteCharAt(bdWrz.length()-1);
			}
			if(wdYrz.length() > 0 && wdYrz.charAt(wdYrz.length()-1) == ',') {
				wdYrz.deleteCharAt(wdYrz.length()-1);
			}
			if(wdWrz.length() > 0 && wdWrz.charAt(wdWrz.length()-1) == ',') {
				wdWrz.deleteCharAt(wdWrz.length()-1);
			}
			model.addAttribute("title", title);
			model.addAttribute("bdYrz", bdYrz);
			model.addAttribute("bdWrz", bdWrz);
			model.addAttribute("wdYrz", wdYrz);
			model.addAttribute("wdWrz", wdWrz);
			
			String statisticsName = "%s至%s随访人员身份认证状态汇总";
			SimpleDateFormat yyyyMMdd= new SimpleDateFormat("yyyy-MM-dd");
			statisticsName = String.format(statisticsName, yyyyMMdd.format(cycle.getCycleBegin()),yyyyMMdd.format(cycle.getCycleEnd()),"55");
			model.addAttribute("statisticsName", statisticsName);
			
		} catch(IllegalArgumentException e) {
			model.addAttribute("title", "没有数据");
			model.addAttribute("bdYrz", "0");
			model.addAttribute("bdWrz", "0");
			model.addAttribute("wdYrz", "0");
			model.addAttribute("wdWrz", "0");
		}
		return "/statistics/jkrz";
	}
	
	@RequestMapping("/qygztj")
	public String qygztj(String cycleId,String areaId,Model model) {
		CycleEntity cycle = null;
		try {
			if(StringHelper.isEmpty(cycleId)) {
				cycle = cycleService.getCycle(new Date());
			} else {
				cycle = cycleService.getCycle(cycleId);
			}
			model.addAttribute("cycle", cycle);
			
			OrganizeViewEntity organizeViewEntity = null;
			if(StringHelper.isNotEmpty(areaId)) {
				organizeViewEntity = this.organizeService.getOrganizeById(areaId);
			} else {
				organizeViewEntity = this.organizeService.getRootOrganize();
			}
			model.addAttribute("area", organizeViewEntity);
			
			Map<String, Object> gktj = statisticsService.statisticsGktj(cycle,organizeViewEntity);
			model.addAttribute("gktj",gktj);
			
			//本地已认证占比
			Map<String, Object> bdyrztj = statisticsService.statisticsBdyrz(cycle,organizeViewEntity);
			model.addAttribute("bdyrztj",bdyrztj);
			
			//外地已认证占比
			Map<String, Object> wdyrztj = statisticsService.statisticsWdyrz(cycle,organizeViewEntity);
			model.addAttribute("wdyrztj",wdyrztj);
			
			Map<String, Object> bdgktj = statisticsService.statisticsBdGktj(cycle,organizeViewEntity);
			model.addAttribute("bdgktj",bdgktj);
			
			Map<String, Object> yhzbtj = statisticsService.statisticsYhzb(cycle,organizeViewEntity);
			model.addAttribute("yhzbtj",yhzbtj);
			
			int total = Integer.parseInt(gktj.get("total").toString());
			int yrz = Integer.parseInt(gktj.get("yrz").toString());
			double yrzzb = 0.0;
			if(0 != total) {
				yrzzb = (yrz+0.0)/total * 100;
			}
			DecimalFormat df = new DecimalFormat("#.##");
			model.addAttribute("yrzzb",df.format(yrzzb));
			
			
			int bdtotal = Integer.parseInt(bdyrztj.get("total").toString());
			int bdyrz = Integer.parseInt(bdyrztj.get("yrz").toString());
			double bdyrzzb = 0.0;
			if(0 != bdtotal) {
				bdyrzzb = (bdyrz+0.0)/bdtotal * 100;
			}
			model.addAttribute("bdyrzzb",df.format(bdyrzzb));
			
			
			int wdtotal = Integer.parseInt(wdyrztj.get("total").toString());
			int wdyrz = Integer.parseInt(wdyrztj.get("yrz").toString());
			double wdyrzzb = 0.0;
			if(0 != wdtotal) {
				wdyrzzb = (wdyrz+0.0)/wdtotal * 100;
			}
			model.addAttribute("wdyrzzb",df.format(wdyrzzb));
			
			int bdTotal = Integer.parseInt(bdgktj.get("total").toString());
			int bdYrz = Integer.parseInt(bdgktj.get("yrz").toString());
			int bdDsh = Integer.parseInt(bdgktj.get("dsh").toString());
			double yjczb = 0.0;
			if(0 != bdTotal) {
				yjczb = (bdDsh + bdYrz + 0.0)/bdTotal * 100;
			}
			model.addAttribute("yjczb",df.format(yjczb));
		} catch(IllegalArgumentException e) {
			
		}
		return "/statistics/qygztj";
	}
}
