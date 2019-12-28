package com.gentle.scw.common.templates;

import java.io.InputStream;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;

import lombok.Data;
@Data
public class OSSTemplate {
	private String scheme;
	// Endpoint以杭州为例，其它Region请按实际情况填写。
	private String endpoint;
	// 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录
	// https://ram.console.aliyun.com 创建。
	private String accessKeyId;
	private String accessKeySecret;
	private String bucketName;
	//存储在阿里OSS中的文件夹路径
	private String imgsDirectory;
	
	//上传图片的方法，返回上传成功的图片的路径 ：用户在项目发布页面发布项目图片
	public String uploadImg(MultipartFile multipartFile) throws Exception{
		//存储文件的名字
		String fileName = System.currentTimeMillis() + "_" + UUID.randomUUID().toString().replace("-", "") 
				+ "_" + multipartFile.getOriginalFilename();
		
		// 创建OSSClient实例。
		OSS ossClient = new OSSClientBuilder().build(scheme + endpoint, accessKeyId, accessKeySecret);

		// 上传文件流。
		InputStream inputStream = multipartFile.getInputStream();
		ossClient.putObject(bucketName, imgsDirectory + fileName, inputStream);

		//https://gentle-scw.oss-cn-shanghai.aliyuncs.com/imgs/1569313776398_efdfed9c2bc4417195b841210a7234a9_2.jpg
		String path = scheme + bucketName + "." + endpoint + "/" +imgsDirectory + fileName;
		
		// 关闭OSSClient。
		ossClient.shutdown();
		
		return path;
	}
}
