package com.esite.test.oldperson;

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
import com.esite.ops.oldperson.entity.OldPersonEntity;
import com.esite.ops.oldperson.service.IOldPersonService;
import com.esite.ops.operator.service.IAreaConfigService;
import com.esite.ops.ws.entity.UpLoadDataVO;
import com.esite.ops.ws.entity.UpLoadOldPersonFingerprint;
import com.esite.ops.ws.entity.UpLoadOldPersonHealthVO;
import com.esite.ops.ws.entity.UpLoadOldPersonPhotoVO;
import com.esite.ops.ws.enums.PhotoPositionEnum;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/spring_configs/**/applicationContext.xml"})
public class HealthUpLoadTest {

	@Autowired
	private IOldPersonService oldPersonService;
	
	@Autowired
	private IAreaConfigService areaConfigService;
	
	@Test
	public void myTest() throws FileNotFoundException, IOException {
		List<String> areaList = areaConfigService.getAreaIdCollectionWithOperatorId("402881ea4a5d9a0e014a5d9b4a0e0001");
		List<OldPersonEntity> list = oldPersonService.getOldPersonWithArea(areaList);
	}
	
}
