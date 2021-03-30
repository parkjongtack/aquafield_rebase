package com.soft.web.base;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.soft.web.base.CommonConstant;
import com.soft.web.service.common.CommonService;

public class GenericController {
	/** 로거 */
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	CommonService commonService;	

	/**
	 * HTTP세션 객체를 구하여 반환
	 * 
	 * @return	HTTP세션 객체
	 */
	protected HttpSession getSession() {
		HttpSession session;

		try {
			ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			session = sra.getRequest().getSession();
		}
		catch (IllegalStateException e) {
			logger.error("<오류> 세션 조회 : (원인) " + e.getMessage());
			session = null;
		}
		return session;
	}
	
	/**
	 * 세션에 저장된 사용자맵 정보를 반환한다. 없으면 null 반환
	 * 
	 * @return	세션에 저장된 사용자맵
	 */
	protected Map getSessionUserMap() {
		Map sessionUserMap;

		try {
			
			ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			HttpSession session = sra.getRequest().getSession();
			sessionUserMap = null==session ? null : (Map) session.getAttribute(CommonConstant.session.SESSION_KEY_USER);
		}
		catch (IllegalStateException e) {
			logger.error("<오류> 세션 정보 조회 : (원인) " + e.getMessage());
			sessionUserMap = null;
		}

		return sessionUserMap;
	}

	/**
	 * 세션에 저장된 사용자맵에서 지정된 key의 값을 문자열로 반환한다. 없으면 null 반환
	 * 
	 * @param 	key	세션사용자맵에서 지정된 key 값(문자열)
	 * @return	세션에 저장된 사용자맵에서 지정된 key의 값
	 */
	protected String getSessionAttr(String key) {
		Map sessionUserMap = this.getSessionUserMap();
		
		String val = null==sessionUserMap ? null : (String)sessionUserMap.get(key);
		logger.debug("<콘트롤러#세션조회> key : '{}', 세션값 = '{}'", key, val);

		return val;
	}
	
	/**
	 * 세션에 저장된 사용자맵 정보를 반환한다. 없으면 null 반환
	 * 
	 * @return	세션에 저장된 사용자맵
	 */
	protected Map getSessionAdminMap() {
		Map sessionAdminMap;

		try {
			
			ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			HttpSession session = sra.getRequest().getSession();
			sessionAdminMap = null==session ? null : (Map) session.getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		}
		catch (IllegalStateException e) {
			logger.error("<오류> 관리자세션 정보 조회 : (원인) " + e.getMessage());
			sessionAdminMap = null;
		}

		return sessionAdminMap;
	}

	/**
	 * 세션에 저장된 사용자맵에서 지정된 key의 값을 문자열로 반환한다. 없으면 null 반환
	 * 
	 * @param 	key	세션사용자맵에서 지정된 key 값(문자열)
	 * @return	세션에 저장된 사용자맵에서 지정된 key의 값
	 */
	protected String getSessionAdminAttr(String key) {
		Map sessionAdminMap = this.getSessionAdminMap();
		
		String val = null==sessionAdminMap ? null : (String)sessionAdminMap.get(key);
		logger.debug("<콘트롤러#관리자세션조회> key : '{}', 세션값 = '{}'", key, val);

		return val;
	}

	
	/**
	 * 세션에 저장된 사용자맵 정보를 반환한다. 없으면 null 반환
	 * 
	 * @return	세션에 저장된 사용자맵
	 */
	protected String getSessionCartNo() {
		String cartNo = "";

		try {
			
			ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			HttpSession session = sra.getRequest().getSession();
			if(session != null && session.getAttribute(CommonConstant.session.SESSION_KEY_CARTNO) != null){
				cartNo = (String)session.getAttribute(CommonConstant.session.SESSION_KEY_CARTNO);
			}
		}
		catch (IllegalStateException e) {
			logger.error("<오류> CART NO 정보 조회 : (원인) " + e.getMessage());
		}

		return cartNo;
	}
	
	/**
	 * 공통 코드 가져오기 2016.07.27 KJH
	 * @param commoncode
	 * @return
	 */
	protected List getCommonCodes(String commoncode) {
	
		Map code = new HashMap();
		code.put("commoncode", commoncode);
		
		List<Map> commonCodes = commonService.commonlist(code);	
		
		return commonCodes;
	}
	
	/***
	 * HTML 소스 가져오기
	 * 
	 * 
	 */
	public String getOpenStreamHTML(String urlToRead) {
	    String result = "";
	    try {
	        URL url = new URL(urlToRead);
	 
//	        System.out.println("url=[" + url + "]");
//	        System.out.println("protocol=[" + url.getProtocol() + "]");
//	        System.out.println("host=[" + url.getHost() + "]");
//	        System.out.println("content=[" + url.getContent() + "]");
	 
	        InputStream is = url.openStream();
	        Reader r = new InputStreamReader(is, "utf-8");
	        int ch;
	        while ((ch = r.read()) != -1) {
//	            System.out.print((char) ch);
	            result += (char) ch;
	        }
	    } catch (MalformedURLException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return result;
	}
	
	/****
	 * 
	 * 파일 읽기
	 *  
	 * 
	 */
	public String getHTMfile(String pathfile) {
	       // 버퍼 생성
        BufferedReader br = null;        
         
        // Input 스트림 생성
        InputStreamReader isr = null;    
         
        // File Input 스트림 생성
        FileInputStream fis = null;        
 
        // File 경로
        File file = new File(pathfile);
 
        // 버퍼로 읽어들일 임시 변수
        String temp = "";
         
        // 최종 내용 출력을 위한 변수
        String content = "";
         
        try {
             
            // 파일을 읽어들여 File Input 스트림 객체 생성
            fis = new FileInputStream(file);
             
            // File Input 스트림 객체를 이용해 Input 스트림 객체를 생서하는데 인코딩을 UTF-8로 지정
            isr = new InputStreamReader(fis, "UTF-8");
             
            // Input 스트림 객체를 이용하여 버퍼를 생성
            br = new BufferedReader(isr);
         
            // 버퍼를 한줄한줄 읽어들여 내용 추출
            while( (temp = br.readLine()) != null) {
                content += temp + "\n";
            }
             
        } catch (FileNotFoundException e) {
            e.printStackTrace();
             
        } catch (Exception e) {
            e.printStackTrace();
             
        } finally {
 
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
             
            try {
                isr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
             
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
             
        }
	    return content;
	}
	
	/***
	 * 사용자 로그 관련 메서드
	 * 
	 */
	public void InsContentsLog(Map param){
		
		String menuCode = param.get("access_menu_uid").toString();
		Map getAdminMenu = commonService.getAdminMenu(menuCode);
		String access_menu_nm = getAdminMenu.get("MENU_NM").toString() +" > "+getAdminMenu.get("SUBMENU_NM").toString();
		
		param.put("access_menu_nm", access_menu_nm);
		param.put("access_menu_uid", getAdminMenu.get("MENU_UID").toString());		
		
		commonService.insAdminContentLog(param);
	}

	/**
	 * 지점 정보 가져오기
	 * @param point
	 * @return
	 */
	protected Map getPointInfo(String point) {
		Map pointInfo = commonService.getPointInfo(point);	
		return pointInfo;
	}
	
	/**
	 * 결제 코드 가져오기 
	 * @return
	 */
	protected List getPayCodes() {
		List<Map> payCodes = commonService.payList();	
		return payCodes;
	}
}
