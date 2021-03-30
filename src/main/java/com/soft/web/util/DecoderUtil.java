package com.soft.web.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

public class DecoderUtil {

	@SuppressWarnings({ "rawtypes", "unused" })
	public static String decode(Map param, String key) throws UnsupportedEncodingException{
		String result = "";
		String temp =(String )param.get(key);
		if(temp != null & !"".equals(temp)){
			result = URLDecoder.decode((String)param.get(key), "UTF-8");
		}else{
			result = "";
		}
		return result;
	}
}
