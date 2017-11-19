package com.esite.ops.health.controller;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.esite.ops.health.entity.HealthInfoEntity;
import com.esite.ops.health.entity.HealthMissEntity;
import com.esite.ops.health.entity.HealthResultEntity;
import com.esite.ops.health.service.impl.HealthInfoService;
import com.esite.ops.health.service.impl.HealthMissService;
import com.esite.ops.mission.entity.CycleEntity;
import com.esite.ops.mission.service.impl.CycleService;
import com.esite.ops.oldperson.entity.OldPersonEntity;
import com.esite.ops.oldperson.service.impl.OldPersonService;
import com.esite.ops.operator.entity.OperatorEntity;

@Controller
@RequestMapping("/http/health")
public class HealthReportHttpInterface {

    @Resource
    private HealthInfoService healthInfoService;
    @Resource
    private OldPersonService oldPersonService;
    @Resource
    private CycleService cycleService;
    @Resource
    private HealthMissService healthMissService;

    @RequestMapping("/{oldPersonId}/report/view")
    public String reportOnlineView(@PathVariable String oldPersonId, Model model) {
        HealthInfoEntity health = healthInfoService.getLastHealthByOldPersonId(oldPersonId);
        if (null != health) {
            OperatorEntity operator = health.getOperator();
            OldPersonEntity oldPerson = health.getOldPerson();
            HealthResultEntity healthResult = health.getHealthResult();

            model.addAttribute("operator", operator);
            model.addAttribute("health", health);
            model.addAttribute("oldPerson", oldPerson);
            model.addAttribute("healthResult", healthResult);
            return "/health/report/httpview";
        }
        OldPersonEntity oldPerson = this.oldPersonService.getOldPerson(oldPersonId);
        model.addAttribute("oldPerson", oldPerson);
        return "/health/report/noview";
    }

    @RequestMapping("/miss")
    public ResponseEntity<String> healthMiss(String oldPersonId, String missCause, Model model) {

        CycleEntity cycle = null;
        try {
            cycle = cycleService.getCycle(new Date());
        } catch (Throwable e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        if (null == cycle) {
            return new ResponseEntity<String>("当前不在任意周期内", HttpStatus.BAD_REQUEST);

        }
        OldPersonEntity oldPerson = oldPersonService.getOldPerson(oldPersonId);
        HealthMissEntity healthMissEntity = new HealthMissEntity();

        healthMissEntity.setOldPerson(oldPerson);
        healthMissEntity.setCycle(cycle);
        healthMissEntity.setMissCause(missCause);
        healthMissEntity.setMissDate(new Date());
        healthMissService.saveOrUpdateMiss(healthMissEntity);
        return new ResponseEntity<String>(HttpStatus.OK);
    }
}
