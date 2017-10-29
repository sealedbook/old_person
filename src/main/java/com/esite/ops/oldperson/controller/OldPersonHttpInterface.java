package com.esite.ops.oldperson.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.esite.framework.file.entity.SysFileInfo;
import com.esite.framework.file.service.impl.FileService;
import com.esite.framework.security.entity.Customer;
import com.esite.framework.util.StringHelper;
import com.esite.ops.health.service.impl.OldPersonWdVerifyService;
import com.esite.ops.oldperson.entity.FingerprintCollectEntity;
import com.esite.ops.oldperson.entity.OldPersonEntity;
import com.esite.ops.oldperson.service.impl.FingerprintCollectService;
import com.esite.ops.oldperson.service.impl.OldPersonService;

@Controller
@RequestMapping("/http/oldperson")
public class OldPersonHttpInterface {

    @Resource
    private OldPersonService oldPersonService;
    @Resource
    private OldPersonWdVerifyService oldPersonWdVerifyService;
    @Resource
    private FingerprintCollectService fingerprintCollectService;
    @Resource
    private FileService fileService;

    @RequestMapping("/{oldPersonId}/photo")
    public @ResponseBody
    byte[] showImg(@PathVariable String oldPersonId) {
        String filePath = this.getClass().getResource("").getPath() + "nobody.jpg";

        OldPersonEntity oldPerson = this.oldPersonService.getOldPerson(oldPersonId);
        if (null == oldPerson) {
            try {
                byte[] photoByte = IOUtils.toByteArray(new FileInputStream(new File(filePath)));
                return photoByte;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        SysFileInfo sysFileInfo = fileService.findByFileKey(oldPerson.getPhotoKey());
        byte[] photoByte = sysFileInfo.getContent();
        if (null == photoByte) {
            try {
                photoByte = IOUtils.toByteArray(new FileInputStream(new File(filePath)));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return photoByte;
    }

    /**
     * 上传头像(本地、外地通用)
     */
    @RequestMapping(value = "/upload/photo", method = RequestMethod.POST)
    public ResponseEntity<String> uploadPhoto(@RequestParam String id, @RequestParam MultipartFile photo,
        HttpServletRequest request) {
        try {
            oldPersonService.updatePhoto(id, photo.getBytes(), new Customer(request));
            return new ResponseEntity<String>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 上传5张认证照片
     */
    @RequestMapping(value = "/upload/verify/photo", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, String> uploadVerifyPhoto(@RequestParam String oldPersonId, @RequestParam MultipartFile[] photo,
        HttpServletRequest request) {
        Map<String, String> result = new HashMap<String, String>();
        try {
            oldPersonWdVerifyService.saveVerifyPhoto(oldPersonId, photo, new Customer(request));
            result.put("responseStatus", "OK");
            return result;
        } catch (IllegalArgumentException e) {
            result.put("responseStatus", "ERROR");
            result.put("errorMessage", e.getMessage());
            return result;
        } catch (IOException e) {
            result.put("responseStatus", "ERROR");
            result.put("errorMessage", e.getMessage());
            return result;
        }
    }

    @RequestMapping(value = "/update")
    public @ResponseBody
    Map<String, String> update(OldPersonEntity oldPerson, HttpServletRequest request) {
        Map<String, String> result = new HashMap<String, String>();
        try {
            //需要重新设置终端提交上来的老年人信息属性,不应该使用实体对象
            OldPersonEntity dbOldPersonEntity = this.oldPersonService.getOldPerson(oldPerson.getId());
            if (null == dbOldPersonEntity) {
                throw new IllegalArgumentException("系统中没有相关的老年人.");
            }
            if (StringHelper.isNotEmpty(oldPerson.getHomeAddress())) {
                dbOldPersonEntity.setHomeAddress(oldPerson.getHomeAddress());
            }
            if (StringHelper.isNotEmpty(oldPerson.getPhoneNumber())) {
                dbOldPersonEntity.setPhoneNumber(oldPerson.getPhoneNumber());
            }
            oldPersonService.updateForHttpRequest(dbOldPersonEntity, new Customer(request));
            result.put("responseStatus", "OK");
            return result;
        } catch (IllegalArgumentException e) {
            result.put("responseStatus", "ERROR");
            result.put("errorMessage", e.getMessage());
            return result;
        }
    }

    @RequestMapping(value = "/died")
    public ResponseEntity<String> diew(String id, HttpServletRequest request) {
        try {
            oldPersonService.died(id, new Customer(request));
            return new ResponseEntity<String>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/sign_died")
    public ResponseEntity<String> died(OldPersonEntity oldPerson, HttpServletRequest request) {
        try {
            oldPersonService
                .died(oldPerson.getId(), oldPerson.getDiedTime(), oldPerson.getDiedLocation(), oldPerson.getDiedCause(),
                    new Customer(request));
            return new ResponseEntity<String>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/list")
    public @ResponseBody
    Map<String, Object> oldPerson(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            List<Map<String, Object>> oldPersonList = oldPersonService
                .getLocalOldPersonWithOperatorIdCard(new Customer(request).getUser().getIdCard());
            result.put("list", oldPersonList);
            result.put("responseStatus", "OK");
        } catch (IllegalArgumentException e) {
            result.put("responseStatus", "ERROR");
            result.put("errorMessage", e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/{oldPersonId}/view")
    public @ResponseBody
    Map<String, Object> view(@PathVariable String oldPersonId, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            OldPersonEntity oldPerson = oldPersonService.getOldPerson(oldPersonId);
            result.put("oldPerson", oldPerson);
            result.put("responseStatus", "OK");
        } catch (IllegalArgumentException e) {
            result.put("responseStatus", "ERROR");
            result.put("errorMessage", e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/info")
    public @ResponseBody
    Map<String, Object> info(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            String idCard = new Customer(request).getUser().getIdCard();
            OldPersonEntity oldPerson = oldPersonService.getOldPersonWithIdCard(idCard);
            if (null == oldPerson) {
                result.put("responseStatus", "ERROR");
                result.put("errorMessage", "系统中不存在这个老年人的身份证号【" + idCard + "】");
            } else {
                result.put("oldPerson", oldPerson);
                result.put("responseStatus", "OK");
            }
        } catch (IllegalArgumentException e) {
            result.put("responseStatus", "ERROR");
            result.put("errorMessage", e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/{oldPersonId}/fingerprint")
    public @ResponseBody
    Map<String, Object> fingerprint(@PathVariable String oldPersonId, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            FingerprintCollectEntity fingerprintCollect = this.fingerprintCollectService
                .getFingerprintCollectByOldPersonId(oldPersonId);
            if (null == fingerprintCollect) {
                result.put("responseStatus", "EMPTY");
            } else {
                result.put("fingerprint", fingerprintCollect);
                result.put("responseStatus", "OK");
            }
        } catch (IllegalArgumentException e) {
            result.put("responseStatus", "ERROR");
            result.put("errorMessage", e.getMessage());
        }
        return result;
    }
}
