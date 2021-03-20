package com.sapphire.demo.service;

import java.io.File;
import java.io.InputStream;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sapphire.demo.exception.CustomizeErrorCode;
import com.sapphire.demo.exception.CustomizeException;

import cn.ucloud.ufile.UfileClient;
import cn.ucloud.ufile.api.object.ObjectConfig;
import cn.ucloud.ufile.auth.ObjectAuthorization;
import cn.ucloud.ufile.auth.UfileObjectLocalAuthorization;
import cn.ucloud.ufile.bean.PutObjectResultBean;
import cn.ucloud.ufile.exception.UfileClientException;
import cn.ucloud.ufile.exception.UfileServerException;

/**
 * https://github.com/ucloud/ufile-sdk-java
 * 
 * @author sapphire
 *
 *
 */

@Service
public class UCloudProvider {

	@Value("${ucloud.ufile.public-key}")
	private String publicKey;

	@Value("${ucloud.ufile.private-key}")
	private String privateKey;
	
	@Value("${ucloud.ufile.bucketName}")
	private String bucketName;

	@Value("${ucloud.ufile.region}")
	private String region;
	
	// 上传文件 - 同步
	public String upload(InputStream fileStream, String mimeType, String fileName) {

		// 生成上传文件名字，避免文件上传重名
		String generatedFileName;
		// 记得加转义
		String[] filePaths = fileName.split("\\.");
		if (filePaths.length > 1) {
			generatedFileName = UUID.randomUUID().toString() + "." + filePaths[filePaths.length - 1];
		} else {
			throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
		}

		try {
			// 授权认证
			ObjectAuthorization ObjectAuthorization = new UfileObjectLocalAuthorization(publicKey, privateKey);
			// 域名配置
			ObjectConfig config = new ObjectConfig(region, "ufileos.com");

			PutObjectResultBean response = UfileClient.object(ObjectAuthorization, config)
					.putObject(fileStream, mimeType).nameAs(generatedFileName).toBucket(bucketName)
					/**
					 * 是否上传校验MD5, Default = true
					 */
					// .withVerifyMd5(false)
					/**
					 * 指定progress callback的间隔, Default = 每秒回调
					 */
					// .withProgressConfig(ProgressConfig.callbackWithPercent(10))
					/**
					 * 配置进度监听
					 */
					.setOnProgressListener((bytesWritten, contentLength) -> {

					}).execute();

			// 成功判断
			if (response != null && response.getRetCode() == 0) {
				String url = UfileClient.object(ObjectAuthorization, config)
						.getDownloadUrlFromPrivateBucket(generatedFileName, bucketName, 24 * 60 * 60 * 365).createUrl();
				return url;
			} else {
				throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
			}

		} catch (UfileClientException e) {
			e.printStackTrace();
			throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
		} catch (UfileServerException e) {
			e.printStackTrace();
			throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
		}
	}

}
