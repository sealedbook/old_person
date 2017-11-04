package com.esite.ops.worker;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.esite.framework.util.SystemConfigUtil;
import com.esite.ops.health.service.impl.HealthResultService;


@Component
public class EcgTimeWorker {

    private final Logger logger = Logger.getLogger(EcgTimeWorker.class);

    @Resource
    private HealthResultService healthResultService;

    private static final String OUTPUT_PATH = SystemConfigUtil.getEcgBasePath() + "output";

    @Scheduled(cron = "0 0/10 * * * ? ")   //每5秒执行一次
    public void myTest() {
        int count = 0;
        logger.info("心电图图片处理开始...");
        File ecgFilesDic = new File(OUTPUT_PATH);
        if (ecgFilesDic.isDirectory()) {
            for (File f : ecgFilesDic.listFiles()) {
                try {
                    if (f.isFile()) {
                        String fileName = f.getName();
                        logger.info("心电图图片处理,文件名:" + fileName);
                        String suffix = fileName.substring(fileName.lastIndexOf("."));
                        if (".PNG".equalsIgnoreCase(suffix)) {
                            String[] fileNameSplit = fileName.split("-");
                            if (null != fileNameSplit && fileNameSplit.length > 0) {
                                String fileNameHealthId = fileNameSplit[0];
                                logger.info("心电图图片处理,healthId:" + fileNameHealthId);
                                byte[] fileByte = toByteArray(f);
                                this.healthResultService.updateEcg(fileNameHealthId, fileByte);
                                logger.info("心电图图片处理，已更新心电图");
                                ++count;
                            }
                        }
                    }
                    f.delete();
                    logger.info("心电图图片处理，文件已删除");
                } catch (Exception e) {
                    continue;
                }
            }
        }
        logger.info("心电图图片处理结束...共处理:" + count + "个.");
    }

    public static byte[] toByteArray(File f) throws IOException {

        ByteArrayOutputStream bos = new ByteArrayOutputStream((int) f.length());
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(f));
            int buf_size = 1024;
            byte[] buffer = new byte[buf_size];
            int len = 0;
            while (-1 != (len = in.read(buffer, 0, buf_size))) {
                bos.write(buffer, 0, len);
            }
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            bos.close();
        }
    }
}
