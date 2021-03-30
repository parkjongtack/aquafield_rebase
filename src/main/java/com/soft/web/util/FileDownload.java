package com.soft.web.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

public class FileDownload extends AbstractView {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	public FileDownload() {
        setContentType("applicaiton/download;charset=utf-8");
    }

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,String> fileMap = (Map<String,String>)model.get("fileMap");
		if(fileMap != null && fileMap.size() > 0){
			String realPath = "";    //실제 경로 저장 변수
			realPath = request.getSession().getServletContext().getRealPath(fileMap.get("upfileName").toString());
			String fullPath = realPath;
			logger.debug(realPath);
			
			File file = new File(fullPath);
			
			response.setContentType(getContentType());
	        response.setContentLength((int)file.length());
	        
	        String fileName = java.net.URLEncoder.encode(fileMap.get("fileName"), "UTF-8");
	        
	        response.setHeader("Content-Disposition", "attachment;filename=\""+fileName+"\";");
	        response.setHeader("Content-Transfer-Encoding", "binary");
	        
	        OutputStream out = response.getOutputStream();
	        FileInputStream fis = null;
	         
	        try {
	            fis = new FileInputStream(file);
	            FileCopyUtils.copy(fis, out);
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            if (fis != null) {
	            	try { 
	            		fis.close();
	            	} catch (Exception e2) {}
	            }
	        }
	        out.flush();
		}
	}
}
