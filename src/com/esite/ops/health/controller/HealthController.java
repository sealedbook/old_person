package com.esite.ops.health.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.esite.framework.security.entity.Customer;
import com.esite.framework.util.PagerRequest;
import com.esite.framework.util.PagerResponse;
import com.esite.framework.util.StringHelper;
import com.esite.ops.health.entity.HealthFingerprintEntity;
import com.esite.ops.health.entity.HealthInfoEntity;
import com.esite.ops.health.entity.HealthPhotoEntity;
import com.esite.ops.health.entity.HealthResultEntity;
import com.esite.ops.health.service.IFingerprintInfoService;
import com.esite.ops.health.service.IHealthInfoService;
import com.esite.ops.health.service.IHealthPhotoService;
import com.esite.ops.health.service.IHealthResultService;
import com.esite.ops.health.vo.HealthInfoQueryVO;
import com.esite.ops.oldperson.entity.OldPersonEntity;
import com.esite.ops.oldperson.service.IFingerprintCollectService;
import com.esite.ops.oldperson.service.impl.OldPersonService;

@Controller
@RequestMapping("/health")
public class HealthController {
	
	@Autowired
	private IHealthInfoService healthInfoService;
	
	@Autowired
	private IFingerprintInfoService fingerprintInfoService;
	
	@Autowired
	private IHealthPhotoService healthPhotoService;
	
	@Autowired
	private IHealthResultService healthResultService;
	
	@RequestMapping(value="/verify/manager")
	public String manager() {
		return "/health/manager";
	}
	
	@RequestMapping(value="/list")
	public @ResponseBody PagerResponse<HealthInfoEntity> list(String oldPersonId,PagerRequest pageRequest) {
		Page<HealthInfoEntity> page = healthInfoService.getHealthByOldPersonId(oldPersonId,pageRequest.getInstance());
		return new PagerResponse<HealthInfoEntity>(page);
	} 
	
	@RequestMapping(value="/verify/list")
	public @ResponseBody PagerResponse<HealthInfoEntity> list(HealthInfoQueryVO healthInfoQueryVO,PagerRequest pageRequest) {
		Page<HealthInfoEntity> page = healthInfoService.getVerifyHealth(healthInfoQueryVO,pageRequest.getInstance());
		return new PagerResponse<HealthInfoEntity>(page);
	}
	
	@RequestMapping(value="/result/{healthId}/view")
	public String healthResult(@PathVariable String healthId,Model model) {
		HealthInfoEntity healthInfo = healthInfoService.getHealth(healthId);
		HealthResultEntity healthResult = healthInfo.getHealthResult();
		if(null == healthResult) {
			return "/health/result/view";
		}
		model.addAttribute("thisResult", healthResult.formaterResultForStatistics());
		//Map<String,Object> map = this.healthResultService.getAvgHealthResultByCycleId(healthInfo.getCycle().getId());
		double heartRateAvg = this.healthResultService.getHeartRateAvgByCycleId(healthInfo.getCycle().getId());
		double respiratoryRateAvg = this.healthResultService.getRespiratoryRateAvgByCycleId(healthInfo.getCycle().getId());
		double bloodOxygenAvg = this.healthResultService.getBloodOxygenAvgByCycleId(healthInfo.getCycle().getId());
		double pulseRateAvg = this.healthResultService.getPulseRateAvgByCycleId(healthInfo.getCycle().getId());
		double systolicPressureAvg = this.healthResultService.getSystolicPressureAvgByCycleId(healthInfo.getCycle().getId());
		double diastolicPressureAvg = this.healthResultService.getDiastolicPressureAvgByCycleId(healthInfo.getCycle().getId());
		
		StringBuffer avgHealthResult = new StringBuffer();
		avgHealthResult.append(heartRateAvg).append(",").append(respiratoryRateAvg).append(",")
		.append(bloodOxygenAvg).append(",").append(pulseRateAvg).append(",")
		.append(systolicPressureAvg).append(",").append(diastolicPressureAvg);
		
		model.addAttribute("avgHealthResult", avgHealthResult);
		return "/health/result/view";
	}
	
	@RequestMapping(value="/verify/{healthId}/view")
	public String view(@PathVariable String healthId, String verifyState, String oldPersonId, String operatorId, Model model) {
		HealthInfoEntity healthInfo = healthInfoService.getHealth(healthId);
		model.addAttribute("healthInfo", healthInfo);
		
		String nextHealthId = healthInfoService.getNextHealthId(healthId, verifyState, oldPersonId, operatorId);
		model.addAttribute("nextHealthId", nextHealthId);
		
		HealthFingerprintEntity healthFingerprint = fingerprintInfoService.getFingerprintInfoByHealthId(healthId);
		if(null != healthFingerprint) {
			model.addAttribute("healthFingerprintId", healthFingerprint.getId());
			model.addAttribute("healthFingerprintRemark", healthFingerprint.getRemark());
		}
		
		List<HealthPhotoEntity> healthPhotoCollection = healthPhotoService.getPhotoCollectionByHealthId(healthId);
		model.addAttribute("healthPhotoCollection", healthPhotoCollection);
		
		OldPersonEntity oldPerson = healthInfo.getOldPerson();
		if(null != oldPerson) {
			HealthInfoEntity firstHealthInfo = this.healthInfoService.getFirstHealthByOldPersonId(oldPerson.getId());
			if(null == firstHealthInfo) {
				model.addAttribute("firstHealthPhotoCollection", healthPhotoCollection);
			} else {
				List<HealthPhotoEntity> firstHealthPhotoCollection = healthPhotoService.getPhotoCollectionByHealthId(firstHealthInfo.getId());
				model.addAttribute("firstHealthPhotoCollection", firstHealthPhotoCollection);
			}
		}
		model.addAttribute("operatorId", operatorId);
		model.addAttribute("verifyState", verifyState);
		model.addAttribute("oldPersonId", oldPersonId);
		model.addAttribute("oldPersonName", oldPerson.getName());
		model.addAttribute("oldPersonIdCard", oldPerson.getIdCard());
		
		//FingerprintCollectEntity fingerprintCollectEntity = fingerprintCollectService.getFingerprintCollectByOldPersonId(oldPerson.getId());
		//model.addAttribute("fingerprintCollectEntity", fingerprintCollectEntity);
		return "/health/view";
	}
	
	@RequestMapping(value="/verify/next/{healthId}")
	public String next(String healthFingerprintId,String updateVerifyState, String verifyState, String oldPersonId, String operatorId,
			RedirectAttributes redirectAttributes, @PathVariable String healthId,HttpServletRequest request,Model model) {
		String nextHealthId = healthInfoService.getNextHealthId(healthId, verifyState, oldPersonId, operatorId);
		if(StringHelper.isEmpty(nextHealthId)) {
			nextHealthId = healthId;
		}
		if(StringHelper.isNotEmpty(healthFingerprintId) && StringHelper.isNotEmpty(updateVerifyState)) {
			this.healthInfoService.updateVerifyState(healthId,healthFingerprintId,updateVerifyState,new Customer(request));
		}
		redirectAttributes.addAttribute("verifyState", verifyState);
		redirectAttributes.addAttribute("oldPersonId", oldPersonId);
		redirectAttributes.addAttribute("operatorId", operatorId);

		return "redirect:/health/verify/" + nextHealthId + "/view.do";
	}
	
	@RequestMapping(value="/verify")
	public @ResponseBody String verify(@RequestParam String healthId,@RequestParam String healthFingerprintId,@RequestParam String verifyState,HttpServletRequest request) {
		this.healthInfoService.updateVerifyState(healthId,healthFingerprintId,verifyState,new Customer(request));
		//return "redirect:/health/verify/" + healthId + "/view.do";
		return "OK";
	}
	
	@RequestMapping("/fingerprin/photo")
	public @ResponseBody byte[] fingerprintPhoto(@RequestParam String id){
		HealthFingerprintEntity healthFingerprint = fingerprintInfoService.getFingerprintInfoById(id);
		if(null == healthFingerprint) {
			return null;
		}
		return healthFingerprint.getFingerImage();
	}

	@RequestMapping("/face/photo")
	public @ResponseBody byte[] facePhoto(@RequestParam String id){
		HealthPhotoEntity oldPersonPhoto = healthPhotoService.getPhotoCollectionById(id);
		if(null == oldPersonPhoto) {
			return null;
		}
		return oldPersonPhoto.getPhotoFile();
	}
	
}
