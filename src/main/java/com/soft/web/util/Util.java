package com.soft.web.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

public class Util {
    
	/**
	 * 스프링에서 html을 출력하거나 javascript를 출력해 준다.
	 * @param msg
	 * @param out
	 * @throws IOException
	 */
	public static void htmlPrint(String msg, HttpServletResponse response) throws IOException {
		response.setContentType("text/html; charset=utf-8");
		//javax.servlet.ServletOutputStream out = response.getOutputStream();
        //out.print(msg);
		Writer out = response.getWriter();
		out.write(msg);
        out.flush();
    }
	
	/**
	* 경고 후 뒤로 또는 창을 닫는다.
	* @param String str - 경고문
	* @return String 결과
	*/
	public static String alert(String str,String mode){
		StringBuffer rstr = new StringBuffer();
		if(str != null){
			rstr.append("<script language='javascript'>\n");
			rstr.append("<!--\n");
			rstr.append("alert('" + str + "');\n");
			if(mode.equals("back") ) rstr.append("history.go(-1);\n");
			if(mode.equals("close") ) rstr.append("self.close();\n");
			rstr.append("//-->\n");
			rstr.append("</script>\n");
		}
		return rstr.toString();
	}
	public static String alert(String str){
		StringBuffer rstr = new StringBuffer();
		if(str != null){
			rstr.append("<script language='javascript'>\n");
			rstr.append("<!--\n");
			rstr.append("alert('" + str + "');\n");
			rstr.append("//-->\n");
			rstr.append("</script>\n");
		}
		return rstr.toString();
	}
	public static String alertToAction(String str, String action){
		StringBuffer rstr = new StringBuffer();
		if(str != null){
			rstr.append("<script language='javascript'>\n");
			rstr.append("<!--\n");
			rstr.append("alert('" + str + "');\n");
			rstr.append(action + "\n");			
			rstr.append("//-->\n");
			rstr.append("</script>\n");
		}
		return rstr.toString();
	}	
	

	/**
	* 경고 후 다른페이지로 이동한다.
	* @param String str - 이동경로
	* @param String str1 - 경고문
	* @return String 결과
	*/
	public static String historyBack(String str){
		StringBuffer rstr = new StringBuffer();
		if(str != null){
			rstr.append("<script language='javascript'>\n");
			rstr.append("<!--\n");
			rstr.append("alert('" + str + "');\n");
			rstr.append("history.back();\n");
			rstr.append("//-->\n");
			rstr.append("</script>\n");
		}
		return rstr.toString();
	}
	public static String historyBack(){
		StringBuffer rstr = new StringBuffer();
		rstr.append("<script language='javascript'>\n");
		rstr.append("<!--\n");
		rstr.append("history.back();\n");
		rstr.append("//-->\n");
		rstr.append("</script>\n");

		return rstr.toString();
	}

	

	/**
	* 경고 후 다른페이지로 이동한다.
	* @param String str - 이동경로
	* @param String str1 - 경고문
	* @return String 결과
	*/
	public static String goUrl(String str, String str1){
		StringBuffer rstr = new StringBuffer();
		if(str != null){
			rstr.append("<script language='javascript'>\n");
			rstr.append("<!--\n");
			if(str1.trim().length() > 0) rstr.append("alert('" + str1 + "');\n");
			rstr.append("location.replace('" + str + "');\n");
			rstr.append("//-->\n");
			rstr.append("</script>\n");
		}
		return rstr.toString();
	}
	public static String goUrl(String str){
		StringBuffer rstr = new StringBuffer();
		if(str != null){
			rstr.append("<script language='javascript'>\n");
			rstr.append("<!--\n");
			rstr.append("location.replace('" + str + "');\n");
			rstr.append("//-->\n");
			rstr.append("</script>\n");
		}
		return rstr.toString();
	}
	public static String gotoUrl(String str, String str1){
		StringBuffer rstr = new StringBuffer();
		if(str != null){
			rstr.append("<script language='javascript'>\n");
			rstr.append("<!--\n");
			if(str1.trim().length() > 0) rstr.append("alert('" + str1 + "');\n");
			rstr.append(str + ";\n");
			rstr.append("//-->\n");
			rstr.append("</script>\n");
		}
		return rstr.toString();
	}
	/**
	* Null 체크함수
	* @param String str - 리턴될 문자열 만약 null이면 공백을 반환한다.
	* @return String 결과
	*/
    public static String nc(String str) {
        if (str == null)
            return "";
        else
            return str.trim();
    }

	/**
	* Null 체크함수
	* @param String str - 리턴될 문자열
	* @param String nStr - 만약 str이 null이면 nStr을 리턴한다.
	* @return String 결과
	*/
    public static String nc(String str,String nStr) {
        if (str == null || str.equals(""))
            return nStr;
        else
            return str.trim();
    }

	/**
	*  검색시 사용함수 체크함수
	* @param String str - 리턴될 문자열
	* @param String nStr - 만약 str이 null이면 nStr을 리턴한다.
	* @return String 결과
	*/
    public static String sc(String str) {
        if (str == null || str.equals(""))
		{
            return "";
		}
        else
		{
			str = chkXss(str);
            return str;
		}
    }
    public static String ksc(String str) {
        if (str == null || str.equals(""))
		{
            return "";
		}
        else
		{
			
        	str = ksString(str);
			str = chkXss(str);		
            return str.trim();
		}
    }
	
	/**
	*  검색시 사용함수 체크함수
	* @param String str - 리턴될 문자열
	* @param String nStr - 만약 str이 null이면 nStr을 리턴한다.
	* @return String 결과
	*/
    public static String dbc(String str) {
        if (str == null || str.equals(""))
		{
            return "";
		}
        else
		{
        	//str = str.replaceAll("\"", "&#34;");
	        //str = str.replaceAll("\'", "&#39;");
	        //str = str.replaceAll("/", "&#47;");
        	str = str.replaceAll("\'", "\'\'");
	        String str_low= str.toLowerCase();	        
	        int num = 0;
	        /*
	        if( str_low.contains("javascript") ){	 num++; str_low = str_low.replaceAll("javascript", "x-javascript"); }
	        if( str_low.contains("script") ){		 num++; str_low = str_low.replaceAll("script", "x-script"); }
	        if( str_low.contains("iframe") ){		 num++; str_low = str_low.replaceAll("iframe", "x-iframe"); }
	        if( str_low.contains("document") ){		 num++; str_low = str_low.replaceAll("document", "x-document"); }
	        if( str_low.contains("vbscript") ){		 num++; str_low = str_low.replaceAll("vbscript", "x-vbscript"); }
	        if( str_low.contains("applet") ){		 num++; str_low = str_low.replaceAll("applet", "x-applet"); }
	        if( str_low.contains("embed") ){		 num++; str_low = str_low.replaceAll("embed", "x-embed"); }
	        if( str_low.contains("object") ){		 num++; str_low = str_low.replaceAll("object", "x-object"); }
	        if( str_low.contains("frame") ){		 num++; str_low = str_low.replaceAll("frame", "x-frame"); }
	        if( str_low.contains("grameset") ){		 num++; str_low = str_low.replaceAll("grameset", "x-grameset"); }
	        if( str_low.contains("layer") ){		 num++; str_low = str_low.replaceAll("layer", "x-layer"); }
	        if( str_low.contains("bgsound") ){		 num++; str_low = str_low.replaceAll("bgsound", "x-bgsound"); }
	        if( str_low.contains("alert") ){		 num++; str_low = str_low.replaceAll("alert", "x-alert"); }
	        if( str_low.contains("onblur") ){		 num++; str_low = str_low.replaceAll("onblur", "x-onblur"); }
	        if( str_low.contains("onchange") ){		 num++; str_low = str_low.replaceAll("onchange", "x-onchange"); }
	        if( str_low.contains("onclick") ){		 num++; str_low = str_low.replaceAll("onclick", "x-onclick"); }
	        if( str_low.contains("ondblclick") ){	 num++; str_low = str_low.replaceAll("ondblclick", "x-ondblclick"); }
	        if( str_low.contains("enerror") ){		 num++; str_low = str_low.replaceAll("enerror", "x-enerror"); }
	        if( str_low.contains("onfocus") ){		 num++; str_low = str_low.replaceAll("onfocus", "x-onfocus"); }
	        if( str_low.contains("onload") ){		 num++; str_low = str_low.replaceAll("onload", "x-onload"); }
	        if( str_low.contains("onmouse") ){		 num++; str_low = str_low.replaceAll("onmouse", "x-onmouse"); }
	        if( str_low.contains("onscroll") ){		 num++; str_low = str_low.replaceAll("onscroll", "x-onscroll"); }
	        if( str_low.contains("onsubmit") ){		 num++; str_low = str_low.replaceAll("onsubmit", "x-onsubmit"); }
	        if( str_low.contains("onunload") ){		 num++; str_low = str_low.replaceAll("onunload", "x-onunload"); }
	        */
	        if( str_low.contains("javascript") || str_low.contains("script") || str_low.contains("iframe ") || str_low.contains("document.") || 
	        	str_low.contains("vbscript") || str_low.contains("applet") || str_low.contains("embed ") || str_low.contains("object ")  || 
	        	str_low.contains("grameset") || str_low.contains("alert") || str_low.contains("onblur") || str_low.contains("onchange") || 
	        	str_low.contains("onclick") || str_low.contains("ondblclick") || str_low.contains("enerror") || str_low.contains("onfocus") || 
	        	str_low.contains("onload") ||  str_low.contains("onmouse") || str_low.contains("onscroll") || 
	        	str_low.contains("onsubmit") || str_low.contains("onunload") )
	        {
	        	num++;
	        	
	        }
	        
	        if(num > 0) return "";
	        else return str;
		}
    }
    public static String ksDbc(String str) {
        if (str == null || str.equals(""))
		{
            return "";
		}
        else
		{
			str = ksString(str);
			str = str.replaceAll("'", "''");
            return str;
		}
    }
    
	public static String chkXss(String str){
        if (str == null || str.equals("")){
            return "";
		}
        else{
        	boolean isTrue = false;
        	String[] arrPattern = {"select ","and ","or ","union ","declare ","to_char","from ","where ","insert ","update ","delete "};
        	String chkStr = str.toLowerCase();
        	for(int i = 0; i < arrPattern.length;i++){
        		if(chkStr.indexOf(arrPattern[i]) != -1){
        			isTrue = true;
        			break;
        		}
        	}
        	if(isTrue){
        		str = "";
        	}
        	else{
        		str = removeXSS(str, true);
        	}
            return str;
		}
	}
  
	/**
	 * XSS 제거
	 * @param str
	 * @param use_html
	 * @return
	 */
	public static String removeXSS(String str, boolean use_html) {
		if (str == null || str.equals("")) return "";
	    String str_low = "";
	    if(use_html){ // HTML tag를 사용하게 할 경우 부분 허용
	    	str = str.replaceAll("&", "&amp;");
	    	str = str.replaceAll("%27", "&#39;");
	    	// HTML tag를 모두 제거	    	
	    	str = str.replaceAll("<","&lt;");
	        str = str.replaceAll(">","&gt;");
	         
	        // 특수 문자 제거
	        str = str.replaceAll("%00", null);
	        str = str.replaceAll("\"", "&#34;");
	        str = str.replaceAll("\'", "&#39;");
	        str = str.replaceAll("%", "&#37;");    
	        //str = str.replaceAll("../", "");
	        str = str.replaceAll("..\\\\", "");
	        //str = str.replaceAll("./", "");
	        str = str.replaceAll("%2F", "");
	        str = str.replaceAll("\\(", "&#40;");
	        str = str.replaceAll("\\)", "&#41;");
	        str = str.replaceAll("eval\\((.*)\\)", "");
	        str = str.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");


	        // 스크립트 문자열 필터링 (선별함 - 필요한 경우 보안가이드에 첨부된 구문 추가)
	        str_low= str.toLowerCase();
	        int num = 0;
	        /*
	        if( str_low.contains("javascript") ){	 num++; str_low = str_low.replaceAll("javascript", "x-javascript"); }
	        if( str_low.contains("script") ){		 num++; str_low = str_low.replaceAll("script", "x-script"); }
	        if( str_low.contains("iframe") ){		 num++; str_low = str_low.replaceAll("iframe", "x-iframe"); }
	        if( str_low.contains("document") ){		 num++; str_low = str_low.replaceAll("document", "x-document"); }
	        if( str_low.contains("vbscript") ){		 num++; str_low = str_low.replaceAll("vbscript", "x-vbscript"); }
	        if( str_low.contains("applet") ){		 num++; str_low = str_low.replaceAll("applet", "x-applet"); }
	        if( str_low.contains("embed") ){		 num++; str_low = str_low.replaceAll("embed", "x-embed"); }
	        if( str_low.contains("object") ){		 num++; str_low = str_low.replaceAll("object", "x-object"); }
	        if( str_low.contains("frame") ){		 num++; str_low = str_low.replaceAll("frame", "x-frame"); }
	        if( str_low.contains("grameset") ){		 num++; str_low = str_low.replaceAll("grameset", "x-grameset"); }
	        if( str_low.contains("layer") ){		 num++; str_low = str_low.replaceAll("layer", "x-layer"); }
	        if( str_low.contains("bgsound") ){		 num++; str_low = str_low.replaceAll("bgsound", "x-bgsound"); }
	        if( str_low.contains("alert") ){		 num++; str_low = str_low.replaceAll("alert", "x-alert"); }
	        if( str_low.contains("onblur") ){		 num++; str_low = str_low.replaceAll("onblur", "x-onblur"); }
	        if( str_low.contains("onchange") ){		 num++; str_low = str_low.replaceAll("onchange", "x-onchange"); }
	        if( str_low.contains("onclick") ){		 num++; str_low = str_low.replaceAll("onclick", "x-onclick"); }
	        if( str_low.contains("ondblclick") ){	 num++; str_low = str_low.replaceAll("ondblclick", "x-ondblclick"); }
	        if( str_low.contains("enerror") ){		 num++; str_low = str_low.replaceAll("enerror", "x-enerror"); }
	        if( str_low.contains("onfocus") ){		 num++; str_low = str_low.replaceAll("onfocus", "x-onfocus"); }
	        if( str_low.contains("onload") ){		 num++; str_low = str_low.replaceAll("onload", "x-onload"); }
	        if( str_low.contains("onmouse") ){		 num++; str_low = str_low.replaceAll("onmouse", "x-onmouse"); }
	        if( str_low.contains("onscroll") ){		 num++; str_low = str_low.replaceAll("onscroll", "x-onscroll"); }
	        if( str_low.contains("onsubmit") ){		 num++; str_low = str_low.replaceAll("onsubmit", "x-onsubmit"); }
	        if( str_low.contains("onunload") ){		 num++; str_low = str_low.replaceAll("onunload", "x-onunload"); }
	        */
	        if( str_low.contains("javascript") || str_low.contains("script") || str_low.contains("iframe ") || str_low.contains("document.") || 
		        	str_low.contains("vbscript") || str_low.contains("applet") || str_low.contains("embed ") || str_low.contains("object ")  || 
		        	str_low.contains("grameset") || str_low.contains("alert") || str_low.contains("onblur") || str_low.contains("onchange") || 
		        	str_low.contains("onclick") || str_low.contains("ondblclick") || str_low.contains("enerror") || str_low.contains("onfocus") || 
		        	str_low.contains("onload") ||  str_low.contains("onmouse") || str_low.contains("onscroll") || 
		        	str_low.contains("onsubmit") || str_low.contains("onunload") )
	        {
	        	num++;
	        	
	        }
	        
	        
	        if(num > 0) str = "";
	        
	    }else{ // HTML tag를 사용하지 못하게 할 경우
	        str = str.replaceAll("\"","&gt;");
	        str = str.replaceAll("&", "&amp;");
	        str = str.replaceAll("<", "&lt;");
	        str = str.replaceAll(">", "&gt;");
	        str = str.replaceAll("%00", null);
	        str = str.replaceAll("\"", "&#34;");
	        str = str.replaceAll("\'", "&#39;");
	        str = str.replaceAll("%", "&#37;");    
	        str = str.replaceAll("../", "");
	        str = str.replaceAll("..\\\\", "");
	        str = str.replaceAll("./", "");
	        str = str.replaceAll("%2F", "");
	    }
	    
	    return str;
	}
	
	/**
	* 문자열을 숫자로 변환(만약 숫자형 문자가 아니면 0을 리턴한다.)
	* @param String str - 리턴될 문자열
	* @return int 결과
	*/
	public static int getInt(String str){
		try{
			if (str == null || str.equals(""))
				return 0;
			else{
				if(str.indexOf(".") != -1){
					str = str.substring(0,str.indexOf("."));
				}
				return Integer.parseInt(str);
			}
		}
		catch(NumberFormatException e){
			return 0;
		}
	}
	
	/**
	 * 문자를 실수로
	 * @param str
	 * @return
	 */
	public float getFloat(String str){
		try{
			if (str == null || str.equals(""))
				return 0;
			else{
				return Float.parseFloat(str);
			}
		}
		catch(NumberFormatException e){
			return 0;
		}
	}
	
	/**
	 * 문자를 실수로
	 * @param str
	 * @return
	 */
	public static double getDouble(String str){
		try{
			if (str == null || str.equals(""))
				return 0;
			else{
				return Double.parseDouble(str);
			}
		}
		catch(NumberFormatException e){
			return 0;
		}
	}

	
	/**
	* 문자열을 한글로 인코딩한다.
	* @param String str - 리턴될 문자열
	* @return String 결과
	*/
	public static String ksString(String str){
		String rstr = "";//넘겨줄 값을 받는 임시 변수
		try{
			if(str != null)	  rstr = new String(str.getBytes("8859_1"),"euc-kr");//인자로 받은 값을 getBytes로 코드 변환후
		}catch(java.io.UnsupportedEncodingException e){
		}
		return rstr;//값을 리턴한다.
	}

	
	/**
	* 문자열을 한글로 인코딩한다.
	* @param String str1 - 비교될 문자열 1
	* @param String str2 - 비교될 문자열 2
	* @param String str3 - str1과 str2가 같으면 리턴될 문자열
	* @return String 결과
	*/
	public static String getCheck(String str1,String str2,String str3)
	{
		String returnValue = "";
		if(str1 != null && str2 != null && str3 != null)
		{
			if(str1.equals(str2) ) returnValue = str3;
		}
		return returnValue;
	}

	/**
	* get방식으로 한글을 넘길때 한글이 깨지지 않키위해 사용하는 메소드로
	* @param String str - 인코딩될 문자열
	* @return String 결과
	*/
	//get방식으로 한글을 넘길때 한글이 깨지지 않키위해 사용하는 메소드로
	//java.net.URLEncoder.encode()메소드로 한글을 16Bit로 인코딩함
	//주의 할 점은 getBytes("euc-kr"),"8859_1")로 보통 풀때는 반대로
	//getBytes("8859_1","euc-kr")로 풀면됨
	public static String encodeKsString(String str)
	{
		String rstr = "";//넘겨줄 값을 받는 임시 변수
		try
		{
			if(str != null)
			{
				rstr = new String(str.getBytes("euc-kr"),"8859_1");//인자로 받은 값을 getBytes로 코드 변환후
				rstr = java.net.URLEncoder.encode(rstr);//encode()메소드로 16Bit 로 인코딩함
			}
		}catch(java.io.UnsupportedEncodingException e){
		}
		return rstr;//값을 리턴한다.
	}
	

	/**
	* get방식으로 한글을 넘길때 한글이 깨지지 않키위해 사용하는 메소드로
	* @param String str - 인코딩될 문자열
	* @return String 결과
	*/
	//get방식으로 한글을 넘길때 한글이 깨지지 않키위해 사용하는 메소드로
	//java.net.URLEncoder.encode()메소드로 한글을 16Bit로 인코딩함
	//주의 할 점은 getBytes("euc-kr"),"8859_1")로 보통 풀때는 반대로
	//getBytes("8859_1","euc-kr")로 풀면됨
	public static String encodeString(String str)
	{
		String rstr = null;//넘겨줄 값을 받는 임시 변수
		if(str != null)
		{
			rstr = java.net.URLEncoder.encode(str);//encode()메소드로 16Bit 로 인코딩함
		}
		return rstr;//값을 리턴한다.
	}
	/**
	* get방식으로 넘어온값을 디비에서 쿼리하기 위한 메소드
	* @param String str - 인코딩될 문자열
	* @return String 결과
	*/	
	public static String decodeString(String str){
		String rstr = null;//넘겨줄 값을 받는 임시 변수
		try{
			if(str != null) rstr = java.net.URLDecoder.decode(str);//encode()메소드로 16Bit 로 인코딩한것을 decode()로 디코딩함
		}catch(NullPointerException e){
		}
		return rstr;//값을 리턴한다.
	}
	/**
	* get방식으로 넘어온값을 디비에서 쿼리하기 위한 메소드
	* @param String str - 인코딩될 문자열
	* @return String 결과
	*/	
	public static String decodeKsString(String str){
		String rstr = null;//넘겨줄 값을 받는 임시 변수
		try{
			if(str != null){
				rstr = java.net.URLDecoder.decode(str);//encode()메소드로 16Bit 로 인코딩한것을 decode()로 디코딩함
			}
		}catch(NullPointerException e){
		}
		return rstr;//값을 리턴한다.
	}

	/**
	 * 문자 캐럭터셋 치환
	 * @param str
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String convertCharacterSet(String str) throws UnsupportedEncodingException{
		if(str == null || "".equals(str) ) return "";
		str = str.replaceAll("\r\n","<br />");
		
		//실서버에서 한글 안꺠지게
		str = new String(str.getBytes("euc-kr"),"8859_1");  
		
		return str;
	}
	
	
	/**
	 * 문자열 -> 16진수
	 * @param str
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String convertStringToHex(String str) throws UnsupportedEncodingException{
		if(str == null || "".equals(str) ) return "";
		str = str.replaceAll("\r\n","<br />");
		
		str = new String(str.getBytes("euc-kr"),"8859_1");

		char[] chars = str.toCharArray();		
		StringBuffer hex = new StringBuffer();
		for(int i = 0; i < chars.length; i++){						  
			hex.append(Integer.toHexString((int)chars[i]));
		}
		
		return hex.toString();
	}
	
	/**
	 * 16진수 -> 문자열
	 * @param hex
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String convertHexToString(String hex) throws UnsupportedEncodingException{
		if(hex == null || "".equals(hex) ) return "";
		StringBuilder sb = new StringBuilder();
		StringBuilder temp = new StringBuilder();
		for( int i=0; i<hex.length()-1; i+=2 ){
			String output = hex.substring(i, (i + 2));
			int decimal = Integer.parseInt(output, 16);
			sb.append((char)decimal);
			temp.append(decimal);
		}
		return new String(sb.toString().getBytes("8859_1"),"euc-kr");
	}

	/**
	* 디비입력시 " 표시를 &#34 코드로 변경한다.
	* @param String str - 인코딩될 문자열
	* @return String 결과
	*/	
	public static String htmlquote(String str) {
		if(str != null) return str.replaceAll("\"", "&#34");
		else return "";
	}
	
	/**
	 * enter을 <br>로
	 * @param str
	 * @return
	 */
	public static String getNl2Br(String str){
		try{
			while(str.indexOf("\n") >= 0){
				str = str.replaceAll("\n", "<br>");
			}
			/*
			while(str.indexOf("\r") >= 0){
				str = str.replaceAll("\r", "<br>");
			}
			while(str.indexOf("\n\r") >= 0){
				str = str.replaceAll("\n\r", "<br>");
			}
			*/
			return str;
		}
		catch(Exception e)
		{
			return str;
		}
	}
	
	/**
	 * 특정 문자로 배열만들기
	 * @param str
	 * @param token
	 * @return
	 */
	public static String[] split(String str,String token){
		int tokenCount;
		StringTokenizer st = new StringTokenizer(str,token);
		String ToStr[];
		tokenCount = st.countTokens();
		ToStr = new String[tokenCount];

		for(int i = 0; i < tokenCount; i++)
			ToStr[i]=st.nextToken().trim();
		return ToStr;
	}

	/**
	* 배열을 키워드를 이용하며 묶음
	* @param String str[] - 배열
	* @param String token - 키워드
	* @return String 결과
	*/
	public static String implode(String str[],String token){
		StringBuffer returnStr = new StringBuffer();
		if(str == null){
			return "";
		}
		else{
			int str_length = str.length;
			for(int i = 0; i < str_length; i++){
				returnStr.append(str[i]);
				if(i < str_length - 1) returnStr.append(token);
			}
			return returnStr.toString();
		}
	}

	/**
	* Vector을 키워드를 이용하며 묶음
	* @param Vector vc - 전체벡터
	* @param String token - 키워드
	* @return String 결과
	*/
	public static String implode(Vector vc,String token){
		StringBuffer returnStr = new StringBuffer();
		if(vc == null){
			return "";
		}
		else{
			int str_length = vc.size();
			for(int i = 0; i < str_length; i++){
				returnStr.append((String)vc.elementAt(i) );
				if(i < str_length - 1) returnStr.append(token);
			}
			return returnStr.toString();
		}
	}

	/**
	* 일정크기 이하로 문자열을 자름
	* @param String str - 전체문자열
	* @param int len - 자르고자 하는 크기
	* @return String 결과
	*/
	public static String cutString(String str,int len){

		StringBuffer returnStr = new StringBuffer();
		String addStr = "";
		String strTmp = "";
		byte[] bytTmp = null;
		int calcLen = 0;
		if(str.length() > len){
			addStr = "...";
		}
		for(int i=0; i < str.length(); i++){
		   strTmp = str.substring(i, i+1);
		   bytTmp = strTmp.getBytes();
		   if(bytTmp.length > 1){   //한글이면
		       returnStr.append(strTmp);
		       calcLen +=2;
		   } //아니면
		   else{
		       returnStr.append(strTmp);
		       calcLen ++;
		   }
		   if(len <= calcLen) break;
		}
		return returnStr.toString() + addStr;
	}
	
	/**
	* 현재 경로를 받아서 HTML경로로 바꿔준다.
	* @param String body - 전체경로
	* @return String 결과
	*/
	public static String pathString(String body){
		StringBuffer rstr = new StringBuffer();
		int pos = 0;
		int i = 0;
		if(body.length() == 1) body = body + " ";
		while((pos = body.indexOf("/", pos)) != -1){
			i++;
			pos++;
		}
		for(int j = 1; j < i; j++){
			rstr.append("../");
		}
		return rstr.toString();
	}
	
	/**
	* 테그를 사용 못하게 한다.
	* @param String htmlStr
	* @return String 결과
	*/
    public static String stripTag(String htmlStr) {
    	if(htmlStr == null || "".equals(htmlStr)) return "";
        return htmlStr.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
    }


	/**
	* 숫자 3자리마다 ,를 찍는다.
	* @param int val - 정수형
	* @return String 결과
	*/
	public static String numberFormat(int val)
	{

		NumberFormat nf = NumberFormat.getInstance();
		String val1 = nf.format((long)val);
		return val1;
	}
	
	/**
	* 숫자 3자리마다 ,를 찍는다.
	* @param String num - 문자열
	* @return String 결과
	*/
	public static String numberFormat(String num)
	{

		int val = getInt(num);
		NumberFormat nf = NumberFormat.getInstance();
		String val1 = nf.format((long)val);
		return val1;
	}

	/**
	* 입력한 년과월의 마지막일을 리턴한다.
	* @param int myyear - 년
	* @param int mymonth - 월
	* @return int 결과
	*/
	public static int getEndDay(int myyear, int mymonth)
	{
		//Date now = new Date();
		int[] endday={31,29,31,30,31,30,31,31,30,31,30,31};
		//int yyy = now.getYear() + 1900;
		//int getday = now.getMonth() + 1;
		int yyy = myyear;
		int getday = mymonth;
		int getendday = 0;
		
		endday[1] = (yyy % 4 != 0) ? 28 : (yyy % 100 != 0) ? 29 : (yyy % 400 != 0) ? 28 : 29;

		return endday[getday - 1];
	}
	
	/**
	 * map으로 받은것을 페이지 이동을 위하여 String으로 넘겨줌(URLEncoder 해서 넘겨줌)
	 * @param map
	 */
	public static String pageParam(String[] key, Map<String,String> map){
		StringBuffer sb = new StringBuffer();
		if(key != null){
			int keyCnt = key.length;
			for(int i = 0; i < keyCnt; i++){
				if(i > 0) sb.append("&");
				if(map.get(key[i]) == null)	sb.append(key[i]+"=");
				else sb.append(key[i]+"="+URLEncoder.encode(map.get(key[i])));
			}
		}
		return sb.toString();
	}
	public static Map pageParamMap(String[] key, Map<String,String> map){
		Map param = new HashMap();
		if(key != null){
			int keyCnt = key.length;
			for(int i = 0; i < keyCnt; i++){
				if(map.get(key[i]) == null)	 param.put(key[i],"");
				else param.put(key[i],URLEncoder.encode(map.get(key[i])));
			}
		}
		return param;
	}
	
	/**
	 * map으로 받은것을 페이지 이동을 위하여 String으로 넘겨줌
	 * @param map
	 */
	public static String pageParam2(String[] key, Map<String,String> map){
		StringBuffer sb = new StringBuffer();
		if(key != null){
			int keyCnt = key.length;
			for(int i = 0; i < keyCnt; i++){
				if(i > 0) sb.append("&");
				if(map.get(key[i]) == null)	sb.append(key[i]+"=");
				else sb.append(key[i]+"="+map.get(key[i]));
			}
		}
		return sb.toString();
	}
	public static Map pageParamMap2(String[] key, Map<String,String> map){
		Map param = new HashMap();
		if(key != null){
			int keyCnt = key.length;
			for(int i = 0; i < keyCnt; i++){
				if(map.get(key[i]) == null)	 param.put(key[i],"");
				else param.put(key[i],map.get(key[i]));
			}
		}
		return param;
	}

	public static Map<String,String> chkParam(String[] arrKey, Map<String,String> map){
		Map<String,String> result = new HashMap<String,String>();
		Set<String> keySet = map.keySet();
		Iterator<String> iterator = keySet.iterator();
		String key = "";
		String value = "";		
		while(iterator.hasNext() ){
			key = iterator.next();
			boolean isTrue = false;
			for(int i = 0; i < arrKey.length; i++ ){
				if(!isTrue)
				{
					isTrue = arrKey[i].equals(key);
				}
			}			
			if(isTrue){
				value = chkXss(map.get(key) );
			}
			else{
				value = map.get(key);
			}	
			
			result.put(key,value);
		}
		return result;
	}
	
	public static Map<String,String> chkParam(Map<String,String> map){
		Map<String,String> result = new HashMap<String,String>();
		Set<String> keySet = map.keySet();
		Iterator<String> iterator = keySet.iterator();
		while(iterator.hasNext() ){
			String key = iterator.next();
			String value = chkXss(map.get(key) );
			result.put(key,value);
		}
		return result;
	}

	public static Map<String,String> chkDbParam(String[] arrKey, Map<String,String> map){
		Map<String,String> result = new HashMap<String,String>();
		Set<String> keySet = map.keySet();
		Iterator<String> iterator = keySet.iterator();
		String key = "";
		String value = "";
		while(iterator.hasNext() ){
			key = iterator.next();
			boolean isTrue = false;
			for(int i = 0; i < arrKey.length; i++ ){
				isTrue = arrKey[i].equals(key);
				if(isTrue){
					continue;
				}
			}			
			if(isTrue){
				value = chkXss(map.get(key) );
			}
			else{
				value =  dbc(map.get(key) );
			}	
			result.put(key,value);
		}
		return result;
	}

	public static Map<String,String> chkDbParam(Map<String,String> map){
		Map<String,String> result = new HashMap<String,String>();
		Set<String> keySet = map.keySet();
		Iterator<String> iterator = keySet.iterator();
		while(iterator.hasNext() ){
			String key = iterator.next();
			String value = dbc(map.get(key) );
			result.put(key,value);
		}
		return result;
	}

	/**
	 * 오라클에서 한글을 처리하기 위해 문자열을 16진수로 변경
	 * @param map
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static Map<String,String> setMapHex(Map<String,String> map) throws UnsupportedEncodingException{
		Map<String,String> result = new HashMap<String,String>();
		Set<String> keySet = map.keySet();
		Iterator<String> iterator = keySet.iterator();
		while(iterator.hasNext() ){
			String key = iterator.next();
			String value = String.valueOf(map.get(key) );
			result.put(key, value);
			result.put(key+"_HEX", convertStringToHex(value));
		}
		return result;
	}

	/**
	 * 오라클에서 가져온 Map을
	 * US7ASCII에서 EUC-KR로 변환한다.
	 * @param map
	 * @return
	 * @throws UnsupportedEncodingException
	 */ 
	public static Map setMapKoConvert(Map map, String chkey) throws UnsupportedEncodingException{
		if(map == null) return null;
		Map result = new HashMap();
		if("US7ASCII".equals(chkey)){
			Set<String> keySet = map.keySet();
			Iterator<String> iterator = keySet.iterator();
			while(iterator.hasNext() ){
				String key = iterator.next();
				String value = String.valueOf(map.get(key) );
				result.put(key, new String(value.getBytes("8859_1"),"euc-kr"));
			}
		}else{
			result = map;
		}
		return result;
	}
	
	/**
	 * 오라클에서 가져온 Map을
	 * US7ASCII에서 EUC-KR로 변환한다.
	 * @param map
	 * @return
	 * @throws UnsupportedEncodingException
	 */ 
	
	public static List<Map> setListKoConvert(List<Map> list, String key) throws UnsupportedEncodingException{
		if(list == null) return null;
		List<Map> result = new ArrayList<Map>();
		int total = list.size();
		for(int i = 0; i < total; i++) {
			result.add(setMapKoConvert(list.get(i), key));
        }
		return result;
	}
	
	/**
	 * 오라클에서 가져온 Map을
	 * EUC-KR에서 로 US7ASCII변환한다.
	 * @param map
	 * @return
	 * @throws UnsupportedEncodingException
	 */ 
	public static Map setMapUs7Convert(Map map, String chkey) throws UnsupportedEncodingException{
		if(map == null) return null;
		Map result = new HashMap();
		if("US7ASCII".equals(chkey)){
			Set<String> keySet = map.keySet();
			Iterator<String> iterator = keySet.iterator();
			while(iterator.hasNext() ){
				String key = iterator.next();
				String value = String.valueOf(map.get(key) );
				result.put(key, new String(value.getBytes("euc-kr"),"8859_1"));
			}
		}else{
			result = map;
		}
		return result;
	}
	
	/**
	 * 오라클에서 가져온 Map을
	 * EUC-KR에서  US7ASCII로 변환한다.
	 * @param map
	 * @return
	 * @throws UnsupportedEncodingException
	 */ 
	
	public static List<Map> setListUs7Convert(List<Map> list, String key) throws UnsupportedEncodingException{
		if(list == null) return null;
		List<Map> result = new ArrayList<Map>();
		int total = list.size();
		for(int i = 0; i < total; i++) {
			result.add(setMapUs7Convert(list.get(i), key));
        }
		return result;
	}	
	
	/**
	* 디렉토리를 생성한다.
	* @param String dir - 디렉토리명
	*/
    public static void makeDir(String dir)
	{
		// 디렉토리 생성    
        File subDir = new File(dir);
        if (!subDir.isDirectory()) subDir.mkdir();
    } 


	/**
	* 파일을 삭제 한다.
	* @param String filename - 파일명
	*/
    public static void deleteFile(String filename)
	{
        File fl = new File(filename);
        if (fl.isFile()) fl.delete();
    } 

	/**
	* 파일을 이동 한다.
	* @param String src - 원본
	* @param String dest - 타겟
	*/
    public static boolean moveFile(String src, String dest)
	{
        File f_src  = new File(src);
        File f_dest = new File(dest);
        return f_src.renameTo(f_dest);
    }
    
    /**
     * 파일명으로 확장자 구하기
     * @param file
     * @param flag
     * @return
     */
    public static String getFileExp(String file)
	{
    	//String filename = "";
    	String exp = "";
		if(file.indexOf('.')>=0){
			//filename = file.substring(0,file.lastIndexOf('.'));
			exp = "."+file.substring(file.lastIndexOf('.')+1,file.length());
		} else {
			//filename = file;
			exp = "";
		}
		return exp;
	}

	/**
	* 디렉토리의 파일명을 반환한다.
	* @param String dir - 디렉토리명
	* @return String[] 결과
	*/
	public static String[] getFileList(String dir)
	{
		String[] get_file = null;
		File get_dir = new File(dir);
		if(get_dir.isDirectory() )
		{
			get_file = get_dir.list();
		}
		return get_file;
	}
	
	
	public static boolean writeFile(String fileName, String content){
		Writer outFile;
		try {
			outFile = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "utf-8"));
			outFile.write(content);
			outFile.flush();
			outFile.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} 


	}
	
	
	public static String getReadFile(String f){
		StringBuffer result = new StringBuffer();
		FileReader freader = null;
		BufferedReader reader = null;
		try {
		   freader = new FileReader( f );
		   //reader = new BufferedReader( freader );
		   reader = new BufferedReader( new InputStreamReader(new FileInputStream(f),"UTF-8")); 
		   String tmpWord = null;
		   while( (tmpWord = reader.readLine()) != null ) {
			   result.append( tmpWord );
		   }
		} catch( Exception e ) {
			System.out.println( e.getMessage() );
		} finally {
			if( reader != null ) {
				try {
					reader.close();
				} catch( Exception ignore ) { }
			}
		}
		return result.toString();
	}
	
	/*
	 * 다른사이트의 내용을 가져온다.
	 */
	public static String getUrl(String str){

		BufferedReader br		 = null;
		InputStreamReader isr	 = null;
		StringBuffer sb			 = null;

		try{

			URL url				 = new URL(str);
			URLConnection uCon	 = url.openConnection();
			isr					 = new InputStreamReader(uCon.getInputStream(),"UTF-8");
			br					 = new BufferedReader(isr);
			sb					 = new StringBuffer();

			String line;
			while((line=br.readLine()) != null) sb.append(line).append("\n");
		}
		catch(Exception e){
		}finally{
			if(isr!=null){ try{ isr.close(); }catch(Exception e){}finally{ isr=null; } }
			if(br!=null){ try{ br.close(); }catch(Exception e){}finally{ br=null; } }
			if(isr!=null){ try{ isr.close(); }catch(Exception e){}finally{ isr=null; } }
		}
		
		if(sb == null) return "";
		else return sb.toString();
	}
	
	/**
	 * unix timestamp 를 구한다.
	 * @return
	 */
	public static String getUnixTimestamp(){
		return String.valueOf(System.currentTimeMillis() / 1000);
	}
	
	
    /**
     * 새글확인
     * @param date
     * @return
     */
	public static boolean NewDay(String date) {

        java.util.Date yymmdd = new java.util.Date() ;
        SimpleDateFormat myformat = new SimpleDateFormat("yyyyMMdd");

        try {
            date = date.substring(0,10).replaceAll("-", "");

            String curdate = myformat.format(yymmdd);

            if( Integer.parseInt(date) >= Integer.parseInt(curdate)-1 ){
                return true;
            }
        } catch ( Exception e ) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        return false;

    }
	
	public static String getTimeStamp(int iMode) {
		String sFormat;
		if (iMode == 1) sFormat = "yyyy-MM-dd";
		else if (iMode == 2) sFormat = "yyyy";
		else if (iMode == 3) sFormat = "MM";
		else if (iMode == 4) sFormat = "dd";
		else if (iMode == 5) sFormat = "yyyyMMdd";
		else if (iMode == 6) sFormat = "HH";
		else if (iMode == 7) sFormat = "mm";
		else if (iMode == 8) sFormat = "ss";
		else if (iMode == 9) sFormat = "yyyyMMddHHmmss";
		else if (iMode == 10) sFormat = "HHmmss";
		else if(iMode == 11) sFormat = "yyyy-MM-dd HH:mm:ss";
        else if(iMode == 12) sFormat = "yyyy-MM-dd HH:mm:ss.SSSZ";
        else if(iMode == 13) sFormat = "E MMM dd HH:mm:ss z yyyy";// Wed Feb 03 15:26:32 GMT+09:00 1999
        else if(iMode == 14) sFormat = "yyyyMM";
		else sFormat = "yyyy-MM-dd";

		//Locale locale = new Locale("kor", "KOR");
		Locale locale = new Locale("en", "EN");
		// SimpleTimeZone timeZone = new SimpleTimeZone(32400000, "KST");
		SimpleDateFormat formatter = new SimpleDateFormat(sFormat, locale);
		// formatter.setTimeZone(timeZone);
		// SimpleDateFormat formatter = new SimpleDateFormat(sFormat);

		return formatter.format(new Date());
	}

	
	/**
	 * @return yyyy-MM-dd HH:mm:ss
	 * */
	public static String getLogTime(){	
		return getTimeStamp(11);
	}

	/**
	 * @return yyyyMMddHHmmss
	 * */
	public static String getTodayTime(){
		return getTimeStamp(9);
	}

	public static String getMicroTime(){
		return System.currentTimeMillis() + "";
	}
	
	/**
		// =================================================== 
		//	1 은 "2002-11-12"
		//	2 는 "2002"
		//	3 은 "11"
		//	4 는 "12"
		//	5 는 "20021112"
		//	6 은 시
		//	7 은 분
		//	8 은 초
		// ===================================================== 
	* @param int option - 리턴될값의 구분
	* @return String 결과
	*/
	public static String makeToday(int iMode){
		return getTimeStamp(iMode);
	}
	

	/**
	 * 
		result.get("year");
		result.get("month");
		result.get("day");
		result.get("hour");
		result.get("minute");
		result.get("second");
		result.get("week");
		result.get("weekCount");
	 * @return
	 */
	public static Map<String,String> makeToday(){
		Map<String,String> result = new HashMap<String,String>();
		Calendar todays = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"));
		String years		 = Integer.toString(todays.get(Calendar.YEAR) );
		String months		 = "0" + (todays.get(Calendar.MONTH) +1 );
		String days			 = "0" +  todays.get(Calendar.DATE);
		String hours		 = "0" + todays.get(Calendar.HOUR_OF_DAY);
		String minutes		 = "0" + todays.get(Calendar.MINUTE);
		String seconds		 = "0" + todays.get(Calendar.SECOND);
		String weeks		 = Integer.toString(todays.get(Calendar.DAY_OF_WEEK) );
		String weeksCount	 = Integer.toString(todays.get(Calendar.WEEK_OF_MONTH) );
		
		result.put("year",years);
		result.put("month",months.substring(months.length()-2));
		result.put("day",days.substring(days.length()-2));
		result.put("hour",hours.substring(hours.length()-2));
		result.put("minute",minutes.substring(minutes.length()-2));
		result.put("second",seconds.substring(seconds.length()-2));
		result.put("week",weeks);
		result.put("weekCount",weeksCount);

		return result;
	}


	
	/**
	* 다양한 값으로 입력한 날짜를 리턴한다.
	* @param String yyyymmdd - 입력될날짜
	* @param int option - 리턴될값의 구분
	* @return String 결과
	*/
	public static String makeAnyDay(String yyyymmdd,int option)
	{
		// ==============================================================

		//	option 값과 20021112와 같이 넣으면
		//	그날의 값들이 option에 따라 나온다.

		//	1 은 "2002-11-12"	
		//	2 는 "2002"
		//	3 은 "11"
		//	4 는 "12"
		//	5 는 "20021112"
		//	6 은  시
		//    7 은  분
		//   8 은  초
		//	9 는 weekday
		//	10은 오늘이 몇번째 주인지

		// ================================================================

		int inYear = Integer.parseInt(yyyymmdd.substring(0,4));
		int inMonth = Integer.parseInt(yyyymmdd.substring(4,6));
		int inDate = Integer.parseInt(yyyymmdd.substring(6,8));

		Calendar anyDay00 = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"));
	   
		anyDay00.set(inYear,inMonth-1,inDate);

		String dayVal = anyDay00.get(Calendar.DATE) + "";
		String monthVal = Integer.toString(anyDay00.get(Calendar.MONTH) +1); 

		if(Integer.parseInt(dayVal) <10)
			dayVal = "0" + dayVal;

		if(Integer.parseInt(monthVal) <10)
			monthVal = "0" + monthVal;
		
		String dateVal = "";

		switch(option)
		{
			case 1: dateVal = Integer.toString(anyDay00.get(Calendar.YEAR)) + "-" + monthVal + "-" + dayVal; break;
			case 2: dateVal = Integer.toString(anyDay00.get(Calendar.YEAR)); break;
			case 3: dateVal = monthVal; break;
			case 4: dateVal = dayVal; break;
			case 5: dateVal = Integer.toString(anyDay00.get(Calendar.YEAR)) + monthVal + dayVal; break;
			case 6: dateVal = Integer.toString(anyDay00.get(Calendar.HOUR)); break;
			case 7: dateVal = Integer.toString(anyDay00.get(Calendar.MINUTE)); break;
			case 8: dateVal = Integer.toString(anyDay00.get(Calendar.SECOND)); break;
			case 9: dateVal = Integer.toString(anyDay00.get(Calendar.DAY_OF_WEEK)); break;
			case 10 : dateVal = Integer.toString(anyDay00.get(Calendar.WEEK_OF_MONTH ));break;
		}

		return dateVal;
	}

	/**
	* 날짜에서 "-",","를 뺀다
	* @param String ymd - 날짜
	* @return String 결과
	*/
	public static String stripDate(String ymd){
		if(ymd == null) return "";
		else if(ymd.length() != 10) return "";

		return ymd.substring(0,4) + ymd.substring(5,7) + ymd.substring(8,10);
	}

	/**
	* 20091112로 입력하면 2009-11-12
	* @param String ymd - 날짜
	* @return String 결과
	*/
	public static String unStripDate(String ymd){
		if(ymd == null) return "";
		else if(ymd.length() < 8) return "";

		return ymd.substring(0,4) + "-" + ymd.substring(4,6) + "-" + ymd.substring(6,8);
	}

	/**
	* 20091112112324로 입력하면
	* 1. 2003-11-12
	* 2. 2003/11/12
	* 3. 2003. 11. 12.
	* 4. 2003년 11월 12일
	* 5. 2003-11-12 11:23:24
	* 반환
	* @param String ymd - 날짜
	* @param int num - 날짜
	* @return String 결과
	*/
	public static String getStringDate(String ymd,int num){
		String returnValue = "";
		if(ymd == null) return returnValue;
		else if(ymd.length() < 8) return returnValue;

		switch(num){
			case 1 : returnValue = ymd.substring(0,4) + "-" + ymd.substring(4,6) + "-" + ymd.substring(6,8); break;
			case 2 : returnValue = ymd.substring(0,4) + "/" + ymd.substring(4,6) + "/" + ymd.substring(6,8); break;
			case 3 : returnValue = ymd.substring(0,4) + ". " + ymd.substring(4,6) + ". " + ymd.substring(6,8) + "."; break;
			case 4 : returnValue = ymd.substring(0,4) + "년 " + ymd.substring(4,6) + "월 " + ymd.substring(6,8) + "일"; break;
			case 5 : if(ymd.length() < 14)
						returnValue = ymd.substring(0,4) + "-" + ymd.substring(4,6) + "-" + ymd.substring(6,8);
					 else
						returnValue = ymd.substring(0,4) + "-" + ymd.substring(4,6) + "-" + ymd.substring(6,8) + " " + ymd.substring(8,10) + ":" + ymd.substring(10,12) + ":" +  ymd.substring(12,14);
					 break;
			case 6 : returnValue = ymd.substring(8,10) + ":" + ymd.substring(10,12) + ":" +  ymd.substring(12,14); break;
			default : returnValue = "";
		}
		return returnValue;
	}

	
	
	
	/**
	* 요일을 숫자로 받는다.
	* @param int yyyy - 년
	* @param int mm - 월
	* @param int dd - 일
	* @return int 결과
	*/
	public int getWeekNum(int yyyy, int mm , int dd)	{
		Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"));
		cld.set(yyyy,mm,1);
		int cldweek = cld.get(Calendar.DAY_OF_WEEK);
		int countimsi;
		countimsi=(int)( ( ( cldweek - 2 ) + dd ) / 7 );
		countimsi++;
		return countimsi;
	}
	
	/**
	 * 날짜계산
	 * 날짜를 입력 후 계산할 값 일,월,년 을 구부낮로 하여 날짜를 계산한다.
	 */
	public static String calDate(String mDate, int val, String flag)	{
		
		mDate = mDate.replaceAll("-","");
		mDate = mDate.replaceAll("\\.","");
		
		Calendar preYear = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"));

		if("D".equals(flag) ){
			preYear.set(Integer.parseInt(mDate.substring(0,4)),Integer.parseInt(mDate.substring(4,6))-1,Integer.parseInt(mDate.substring(6,8))+val);
		}
		else if("M".equals(flag) ){
			preYear.set(Integer.parseInt(mDate.substring(0,4)),Integer.parseInt(mDate.substring(4,6))-1+val,Integer.parseInt(mDate.substring(6,8)));
		}
		else if("Y".equals(flag) ){
			preYear.set(Integer.parseInt(mDate.substring(0,4))+val,Integer.parseInt(mDate.substring(4,6))-1,Integer.parseInt(mDate.substring(6,8)));
		}

		String yearVal = Integer.toString(preYear.get(Calendar.YEAR));
		String monthVal = Integer.toString(preYear.get(Calendar.MONTH)+1);
		String dayVal = Integer.toString(preYear.get(Calendar.DATE));
		
		if(Integer.parseInt(dayVal) < 10)
			dayVal = "0" + dayVal;
		
		if(Integer.parseInt(monthVal) < 10)
			monthVal = "0" + monthVal;

		return yearVal+monthVal+dayVal;
	}
	

	
	/**
	* 날짜의 다음달로 가는 함수
	* @param String myDate - 날짜
	*/
	public static String makeNextMonth(String myDate)	{
		String lastDay = "";

		Calendar cal2 = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"));

		cal2.set(Integer.parseInt(myDate.substring(0,4)), Integer.parseInt(myDate.substring(4,6))+1,1-1);

		//이번달 1일의 하루전으로 set 

		lastDay = Integer.toString(cal2.get(Calendar.DATE));

		if(Integer.parseInt(myDate.substring(6,8)) < Integer.parseInt(lastDay))
			lastDay = myDate.substring(6,8);
			
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"));

		cal.set(Integer.parseInt(myDate.substring(0,4)), Integer.parseInt(myDate.substring(4,6)) , Integer.parseInt(lastDay));
		
		String yy = Integer.toString(cal.get(Calendar.YEAR));
		String mm = Integer.toString(cal.get(Calendar.MONTH)+1);	
		if(Integer.parseInt(mm) <10)
		{
			mm = "0" + mm;
		}
		String dd = Integer.toString(cal.get(Calendar.DATE));

		if(Integer.parseInt(dd) <10)
		{
			dd = "0" + dd;
		}

		return yy  + mm + dd;
	}

	/**
	* 날짜를 입력하면 지난달의 마지막 날을 출력해주는 함수
	*/	
	public static String makePreMonth(String myDate)	{
		
		String lastDay = "";
		Calendar cal2 = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"));

		cal2.set(Integer.parseInt(myDate.substring(0,4)), (Integer.parseInt(myDate.substring(4,6))-1),1-1);

		//이번달 1일의 하루전으로 set 

		lastDay = Integer.toString(cal2.get(Calendar.DATE));


			if(Integer.parseInt(myDate.substring(6,8)) < Integer.parseInt(lastDay))
				lastDay = myDate.substring(6,8);
			

		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"));

		cal.set(Integer.parseInt(myDate.substring(0,4)), Integer.parseInt(myDate.substring(4,6))-2 , Integer.parseInt(lastDay));
		
		String yy = Integer.toString(cal.get(Calendar.YEAR));
		String mm = Integer.toString(cal.get(Calendar.MONTH)+1);	
		if(Integer.parseInt(mm) <10)
			mm = "0" + mm;
		String dd = Integer.toString(cal.get(Calendar.DATE));

		if(Integer.parseInt(dd) <10)
			dd = "0" + dd;

		return  yy +  mm + dd;


	}

	/**
	* 숫자로 받은 요일을 문자로 출력한다.
	* @param int i - 요일
	* @return String 결과
	*/
	public static String weekString(int i)
	{

		String returnValue = "";

		switch(i)
		{
			case 0 : returnValue = "일요일"; break;
			case 1 : returnValue = "월요일"; break;
			case 2 : returnValue = "화요일"; break;
			case 3 : returnValue = "수요일"; break;
			case 4 : returnValue = "목요일"; break;
			case 5 : returnValue = "금요일"; break;
			case 6 : returnValue = "토요일"; break;
			default : returnValue = "잘못된값";
		}

		return returnValue;

	}
	
	public static String getNextDate(String nowDate, int addDate){
		DecimalFormat df = new DecimalFormat("00");
		Calendar cal = Calendar.getInstance();
		
		int p_yyyy = Integer.parseInt(nowDate.substring(0,4));
		int p_mm = Integer.parseInt(nowDate.substring(4,6))-1;
		int p_dd = Integer.parseInt(nowDate.substring(6,8));
		
		cal.set(p_yyyy,p_mm,p_dd);
		cal.add(cal.DATE, addDate);
		
		String strYear   = Integer.toString(cal.get(Calendar.YEAR));
		String strMonth  = df.format(cal.get(Calendar.MONTH) + 1);
		String strDay   = df.format(cal.get(Calendar.DATE));
		String strDate = strYear + strMonth + strDay;
		
		return strDate;
	}


	//<!----------------------------------------------------------------------------------------
	// 배열에서 선택한 번호의 값을 돌려준다.
	//<!----------------------------------------------------------------------------------------
    public static String randomPassword(int rnd_length) {
        //String possible = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String possible = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";
        String str = "";
        Random rnd = new Random();
        int rnd_pos ;
        while(str.length()< rnd_length)  {
            rnd_pos = (int)(rnd.nextDouble()*possible.length());
            str = str + possible.substring(rnd_pos, rnd_pos+1);
        }
        return str;
    }

	//<!----------------------------------------------------------------------------------------
	// 배열에서 선택한 번호의 값을 돌려준다.
	//<!----------------------------------------------------------------------------------------
    public static String randomString(int rnd_length) {
        String possible = "01234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_-";
        String str = "";
        Random rnd = new Random();
        int rnd_pos ;
        while(str.length()< rnd_length)  {
            rnd_pos = (int)(rnd.nextDouble()*possible.length());
            str = str + possible.substring(rnd_pos, rnd_pos+1);
        }
        return str;
    }

	//<!----------------------------------------------------------------------------------------
	// 배열에서 선택한 번호의 값을 돌려준다.
	//<!----------------------------------------------------------------------------------------
    public static String randomOrderNo(int rnd_length) {
        String possible = "0123456789012345678901234567890123456789";
        String str = "";
        Random rnd = new Random();
        int rnd_pos ;
        while(str.length()< rnd_length)  {
            rnd_pos = (int)(rnd.nextDouble()*possible.length());
            str = str + possible.substring(rnd_pos, rnd_pos+1);
        }
        return str;
    }

	//<!----------------------------------------------------------------------------------------
	// 배열에서 선택한 번호의 값을 돌려준다.
	//<!----------------------------------------------------------------------------------------
    public static String randomCartNo(int rnd_length) {
        String possible = "0123456789012345678901234567890123456789";
        String str = "";
        Random rnd = new Random();
        int rnd_pos ;
        while(str.length()< rnd_length)  {
            rnd_pos = (int)(rnd.nextDouble()*possible.length());
            str = str + possible.substring(rnd_pos, rnd_pos+1);
        }
        return str;
    }

	//<!----------------------------------------------------------------------------------------
	// 배열에서 선택한 번호의 값을 돌려준다.
	//<!----------------------------------------------------------------------------------------
	public static String getArray(String[] array,int i){
		if(array == null) return null;
		return array[i];
	}
	
	/**
	* 문자열을 입력하면 배열의 index를 돌려준다.
	* @return int 결과
	*/
	public int getIndexArray(String[] array,String val){
		int ret = -1;
		if(array == null) return ret;
		else{
			for(int i = 0; i < array.length; i++){
				if(array[i].equals(val) ){
					ret = i;
					break;
				}
			}
		}
		return ret;
	}
	
	/**
	 * 이메일확인
	 * @param mail
	 * @return
	 */
	public boolean isEmail(String mail){
		boolean result = false;
		
		Pattern p = Pattern.compile("^(\\w+)(((\\.?)(\\w+))*)[@](((\\w+)[.])+)(\\w{2,3})$");
		Matcher m = p.matcher(mail);
		result = m.find();
		
		return result;
	}
	
	/**
	 * 3자리 숫자에 ,찍기
	 * @param comma
	 * @return
	 */
	public static String getComma(int comma)
	{
	    NumberFormat countComma = NumberFormat.getInstance();
		String commaS="";

		commaS = countComma.format(comma);
		return commaS;
	}
	public static String getComma(float comma)
	{
	    NumberFormat countComma = NumberFormat.getInstance();
		String commaS="";

		commaS = countComma.format(comma);
		return commaS;
	}
	public static String getComma(double comma)
	{
	    NumberFormat countComma = NumberFormat.getInstance();
		String commaS="";

		commaS = countComma.format(comma);
		return commaS;
	}
	public static String getComma(int i, String num)
	{
		String val = "";
		if(i == 1) val = getComma(getInt(num) );
		else if(i == 2) val = getComma(getDouble(num) );
		return val;
	}
	
	/**
	 * 백분율
	 * @param a
	 * @param b
	 * @return
	 */
	public static double getRateTo(int a, int b){
		double result = 0;
		DecimalFormat df = new DecimalFormat("##0.00");
		if( a > 0 && b > 0){
			result = (a / (double)b) * 100;
			result = Double.parseDouble(df.format(result) );
		}
		return result;
	}
	public static double getRateTo(float a, float b){
		double result = 0;
		DecimalFormat df = new DecimalFormat("##0.00");
		if( a > 0 && b > 0){
			result = (a / (double)b) * 100;
			result = Double.parseDouble(df.format(result) );
		}
		return result;
	}
	public static double getRateTo(long a, long b){
		double result = 0;
		DecimalFormat df = new DecimalFormat("##0.00");
		if( a > 0 && b > 0){
			result = (a / (double)b) * 100;
			result = Double.parseDouble(df.format(result) );
		}
		return result;
	}
	public static double getRateTo(double a, double b){
		double result = 0;
		DecimalFormat df = new DecimalFormat("##0.00");
		if( a > 0 && b > 0){
			result = (a / (double)b) * 100;
			result = Double.parseDouble(df.format(result) );
		}
		return result;
	}
	
	/**
	 * 소수점 2자리
	 * @param i
	 * @return
	 */
	/*
	public double getDecimal(double a){
		String result = "0.00";
		DecimalFormat df = new DecimalFormat("##0.00");
		result = Double.parseDouble(df.format(a) );
		return result;
	}
	*/

	public static String getDecimal(int a){
		String result = "0.00";
		DecimalFormat df = new DecimalFormat("##0.00");
		result = df.format(a);
		return result;
	}
	public static String getDecimal(float a){
		String result = "0.00";
		DecimalFormat df = new DecimalFormat("##0.00");
		result = df.format(a);
		return result;
	}
	public static String getDecimal(long a){
		String result = "0.00";
		DecimalFormat df = new DecimalFormat("##0.00");
		result = df.format(a);
		return result;
	}
	public static String getDecimal(double a){
		String result = "0.00";
		DecimalFormat df = new DecimalFormat("##0.00");
		result = df.format(a);
		return result;
	}
	public static String getDecimal(String a){
		String result = "0.00";
		try{
			DecimalFormat df = new DecimalFormat("##0.00");
			result = df.format(a);
		}catch(Exception e){
		}
		return result;
	}
	
	public static String getDecimal(int a,String f){
		String result = f.replaceAll("##", "");
		DecimalFormat df = new DecimalFormat(f);
		result = df.format(a);
		return result;
	}
	public static String getDecimal(float a,String f){
		String result = f.replaceAll("##", "");
		DecimalFormat df = new DecimalFormat(f);
		result = df.format(a);
		return result;
	}
	public static String getDecimal(long a,String f){
		String result = f.replaceAll("##", "");
		DecimalFormat df = new DecimalFormat(f);
		result = df.format(a);
		return result;
	}
	public static String getDecimal(double a,String f){
		String result = f.replaceAll("##", "");
		DecimalFormat df = new DecimalFormat(f);
		result = df.format(a);
		return result;
	}
	public static String getDecimal(String a,String f){
		String result = f.replaceAll("##", "");
		try{
			DecimalFormat df = new DecimalFormat(f);
			result = df.format(a);
		}catch(Exception e){
		}
		return result;
	}
	
	
	/*
	 * 표준편차 구하기
	 */
	public static double standardDeviation(double[] array, int option) {
		if (array.length < 2) return Double.NaN;
		double sum = 0.0;
		double sd = 0.0;
		double diff;
		double meanValue = mean(array);

		for (int i = 0; i < array.length; i++) {
			diff = array[i] - meanValue;
			sum += diff * diff;
		}
		sd = Math.sqrt(sum / (array.length - option));

		return sd;
	}
	public static double mean(double[] array) { 
		double sum = 0.0;

		for (int i = 0; i < array.length; i++)
			sum += array[i];

		return sum / array.length;
	}
	
	
	/**
	 * 파일용량표시하기
	 */
	public static String formatFileSize(String fileSize){
		String fSize = "";
		long kb = 1024;
		long mb = 1048576;
		long gb = 1073741824;
		
		long fs = Long.parseLong(fileSize);
		if(fs < kb) fSize = fs + " Byte";
		else if(fs < mb) fSize = Math.round(fs/kb) + " Kb";
		else if(fs < gb) fSize = Math.round(fs/mb) + " Mb";
		else fSize = Math.round(fs/gb) + " Gb";

		return fSize;
	}
	
	/**
	 * 로그인 Digit 구하기
	 */
	public static String getLoginDigit(){
		Random rnd = new Random();
		return (rnd.nextInt(90000) + 10000)+getTodayTime();
	}
	
	/**
	 * 문자 확인번호
	 */
	public static String getCertNo(){
		Random rnd = new Random();
		return Integer.toString((rnd.nextInt(900000) + 100000) );
	}

	
	/**
	 * IP to Decimal
	 * @param ipAddress
	 * @return
	 */
	
	// example : 192.168.1.2
	public static long ipToLong(String ipAddress) {

		// ipAddressInArray[0] = 192
		String[] ipAddressInArray = ipAddress.split("\\.");

		long result = 0;
		for (int i = 0; i < ipAddressInArray.length; i++) {

			int power = 3 - i;
			int ip = Integer.parseInt(ipAddressInArray[i]);

			// 1. 192 * 256^3
			// 2. 168 * 256^2
			// 3. 1 * 256^1
			// 4. 2 * 256^0
			result += ip * Math.pow(256, power);

		}

		return result;

	}
	
	
	/**
	 * Decimal to IP
	 * @param ip
	 * @return
	 */
	public static String longToIp(long ip) {
		StringBuilder sb = new StringBuilder(15);

		for (int i = 0; i < 4; i++) {

			// 1. 2
			// 2. 1
			// 3. 168
			// 4. 192
			sb.insert(0, Long.toString(ip & 0xff));

			if (i < 3) {
				sb.insert(0, '.');
			}

			// 1. 192.168.1.2
			// 2. 192.168.1
			// 3. 192.168
			// 4. 192
			ip = ip >> 8;

		}

		return sb.toString();
	}
}
