package com.esite.test.health;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.esite.ops.health.service.IHealthInfoService;
import com.esite.ops.ws.entity.UpLoadDataVO;
import com.esite.ops.ws.entity.UpLoadOldPersonFingerprint;
import com.esite.ops.ws.entity.UpLoadOldPersonHealthVO;
import com.esite.ops.ws.entity.UpLoadOldPersonPhotoVO;
import com.esite.ops.ws.enums.PhotoPositionEnum;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/spring_configs/**/applicationContext.xml"})
public class HealthUpLoadTest {

	@Autowired
	private IHealthInfoService healthInfoService;
	
	@Test
	public void myTest() throws FileNotFoundException, IOException {
		UpLoadDataVO upLoadDataVO = new UpLoadDataVO();
		upLoadDataVO.setOldPersonId("sts_test_oldperson_id");
		UpLoadOldPersonHealthVO healthVO = new UpLoadOldPersonHealthVO();
		upLoadDataVO.setOldPersonHealthVO(healthVO);
		
		UpLoadOldPersonFingerprint oldPersonFingerprint = new UpLoadOldPersonFingerprint();
		upLoadDataVO.setOldPersonFingerprint(oldPersonFingerprint);
		
		List<UpLoadOldPersonPhotoVO> list = new ArrayList<UpLoadOldPersonPhotoVO>();
		UpLoadOldPersonPhotoVO photo1 = new UpLoadOldPersonPhotoVO();
		photo1.setPosition(PhotoPositionEnum.ZHENGMIAN);
		photo1.setPhotoFile(IOUtils.toByteArray(new FileInputStream(new File("C:\\Users\\Administrator\\Desktop\\九思百天照\\入册\\巧克力盒子儿童摄影-11.jpg"))));
		//photo1.setPhotoFile(IOUtils.toByteArray(new FileInputStream(new File("C:\\Users\\Administrator\\Desktop\\巧克力盒子儿童摄影-2.jpg"))));
		list.add(photo1);
		upLoadDataVO.setPhotoCollection(list);
		
	}
	
}
