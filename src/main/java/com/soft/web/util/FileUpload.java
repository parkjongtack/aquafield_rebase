package com.soft.web.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public class FileUpload {
	protected static Logger logger = LoggerFactory.getLogger(FileUpload.class);
	
	public static Map fileUpload(MultipartHttpServletRequest mRequest, String realPath, String upload, String upfile) throws Exception {
		Map<String,String> result = new HashMap<String,String>();
		//String getFilename = "";
		String dirAdd = Util.makeToday(2) + Util.makeToday(3);
		realPath += "/" + dirAdd;
		upload += "/" + dirAdd;
		File dir = new File(realPath);
		if (!dir.isDirectory()) {
			dir.mkdirs();
		}
		
		logger.debug("realPath:"+realPath);

		MultipartFile mFile = mRequest.getFile(upfile);
		String originalFileName = "";
		String exp = "";
		String expLow = "";
		String saveFileName = "";
		long fileSize = 0;
		
		try{
			originalFileName = mFile.getOriginalFilename();
		}catch(Exception e){
			
		}
		
		if(originalFileName != null && !"".equals(originalFileName)) {
			Random rnd = new Random();
			int rndNum = rnd.nextInt(7) + 7;
			originalFileName = originalFileName.replaceAll(";", "");
			
			//-- 확장자
			if(originalFileName.indexOf('.')>=0){
				exp = originalFileName.substring(originalFileName.lastIndexOf('.')+1,originalFileName.length());
				expLow = exp.toLowerCase();
				Pattern pattern = Pattern.compile("(jsp|war|asp|php|xml|js|htm|html)");
		        Matcher matcher = pattern.matcher(expLow);
		        if (matcher.matches()){
		        	exp = ".xx"+exp+"xx";
		        }
		        else exp = "."+exp;
			}		

			saveFileName = Util.makeToday(5)+Util.randomString(rndNum)+exp;
			
			try {
				mFile.transferTo(new File(realPath + "/" + saveFileName));
				fileSize = mFile.getSize();
				//getFilename = new File(realPath + saveFileName).getName();
				result.put("FILE_UPNAME",upload + "/" + saveFileName);
				result.put("FILE_NAME",originalFileName);
				result.put("FILE_SIZE",String.valueOf(fileSize) );
			} catch (Exception e) {
				result.put("FILE_UPNAME","");
				result.put("FILE_NAME","");
				result.put("FILE_SIZE","0");
				e.printStackTrace();
			}
		}
		else{
			result.put("FILE_UPNAME","");
			result.put("FILE_NAME","");
			result.put("FILE_SIZE","0");
		}
		return result;
	}
}
