package com.pingansec.utils.test;

import java.io.File;

import org.apache.http.HttpEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import com.pingansec.utils.HttpCommonUtil;
import com.pingansec.utils.SecureUtil;

public class EvidenceTest {
	public static void main(String[] args) {

		Long timeStamp = System.currentTimeMillis();
		String busName = "zjgs";
		String busPwd = "ed3258e2d85270787759ba1c00295b03";
		String mineKey = busName.concat("_").concat(busPwd);
		String pName = "zjgs";
		Integer busId = 1;
		Integer isSync = 1;
		String sourceUrl = "http://www.baid11u.com";
		String evidenceName = "urlEvidence";
		Integer evidenceType = 1;
		Integer clueId = 999;
		try {
			String mineMd5k = SecureUtil.md5(SecureUtil.md5(mineKey).concat("_").concat(timeStamp.toString()));
			StringBuilder stringBuilder = new StringBuilder("http://localhost:8080/EvidenceServer/certification/persistent/add");
			System.out.println(stringBuilder.toString());
			System.out.println(mineMd5k);
			System.out.println(timeStamp);
//			HttpEntity httpEntity = getAddCertificationParam(); 
//			String result = HttpCommonUtil.excuteFileMultipartHttpRequest(stringBuilder.toString(), httpEntity);
//			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static HttpEntity getAddCertificationParam() {
		String filePath = "C:\\Users\\JOKING\\Desktop\\桌面\\pingan\\aic\\出证\\微信图片_20180710161120.png";
		MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
		multipartEntityBuilder.addTextBody("", "");
		multipartEntityBuilder.addBinaryBody("file", new File(filePath));
		return multipartEntityBuilder.build();
	}
}
