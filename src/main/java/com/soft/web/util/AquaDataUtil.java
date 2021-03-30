package com.soft.web.util;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * Date 에 대한 Util 클래스
 * @author 공통서비스 개발팀 이중호
 * @since 2009.02.01
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2009.02.01  이중호          최초 생성
 *
 * </pre>
 */
public class AquaDataUtil {

	public static HashMap<String, Object> convertMap(HttpServletRequest request) throws Exception {
		
		  HashMap<String, Object> parameterMap = new HashMap<String, Object>();
		
		  @SuppressWarnings("rawtypes") Enumeration enums = request.getParameterNames();
		  parameterMap.put("remoteAddr", request.getRemoteAddr());
		  
		  while(enums.hasMoreElements()){
			  
			   String paramName = (String)enums.nextElement();
			   String[] parameters = request.getParameterValues(paramName); // Parameter가 배열일 경우
		   
			   if(parameters.length > 1){
				   List<Object> parmList = new ArrayList<Object>();
				   for(int i= 0; i<parameters.length;i++){
					   parmList.add(parmList.size(), parameters[i]);
				   }
				   parameterMap.put(paramName, parmList); // Parameter가 배열이 아닌 경우
			   }else{
				   parameterMap.put(paramName, parameters[0]);
			   }
		  }
		  return parameterMap;
	}
	
	// null check 함수
	public static String checkNull(String tarStr) {
		if (tarStr == null || tarStr.equals("null")) {
			return "";
		} else {
			return tarStr;
		}
	}
	
	// null check 함수
	public static Boolean booleanCheckNull(String tarStr) {
		if (tarStr == null || tarStr.equals("null")) {
			return true;
		} else {
			return false;
		}
	}

}
