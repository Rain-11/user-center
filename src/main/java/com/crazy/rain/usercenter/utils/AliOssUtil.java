package com.crazy.rain.usercenter.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * @ClassName: AliOssUtil
 * @Description: aliyun头像上传
 * @author: CrazyRain
 * @date: 2024/3/29 16:48
 */
@Slf4j
public class AliOssUtil {

    public static String getString(MultipartFile file, String endpoint, String AccessKeyID, String AccessKeySecret, String bucketName) {
        // 填写Object完整路径，例如exampledir/exampleobject.txt。Object完整路径中不能包含Bucket名称。
        String objectName = "";
        String originalFilename = file.getOriginalFilename();
        if (StringUtils.isNotEmpty(originalFilename)) {
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            UUID uuid = UUID.randomUUID();
            long mostSignificantBits = uuid.getMostSignificantBits();
            objectName = mostSignificantBits + suffix;
        }


        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, AccessKeyID, AccessKeySecret);

        try {
            ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(file.getBytes()));
        } catch (OSSException oe) {
            log.info("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            log.info("Error Message: {}", oe.getErrorMessage());
            log.info("Error Code: {}", oe.getErrorCode());
            log.info("Request ID: {}", oe.getRequestId());
            log.info("Host ID: {}", oe.getHostId());
        } catch (ClientException ce) {
            log.info("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            log.info("Error Message: {}", ce.getMessage());
        } catch (IOException e) {
            log.info("io exception :{}", e.toString());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        StringBuilder stringBuilder = new StringBuilder("https://");
        stringBuilder
                .append(bucketName)
                .append(".")
                .append(endpoint)
                .append("/")
                .append(objectName);

        log.info("文件上传到:{}", stringBuilder);

        return stringBuilder.toString();
    }
}
