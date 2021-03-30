package com.soft.web.tiles;

import java.util.ArrayList;

/* 2016.07.22 KJH
 * tiles.xml 공통 데이터 전달
 *  
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.tiles.Attribute;
import org.apache.tiles.AttributeContext;
import org.apache.tiles.preparer.PreparerException;
import org.apache.tiles.preparer.ViewPreparer;
import org.apache.tiles.request.Request;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.soft.web.base.GenericController;

public class CommonViewPreparer extends GenericController implements ViewPreparer {

    @Override
    public void execute(Request tilesRequest, AttributeContext attributeContext)
    throws PreparerException {
    	
    	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    	HttpSession session = request.getSession();
    	
    	Map memberInfo = (Map) session.getAttribute("MEM_INFO");
    	
    	if(memberInfo == null){
    		memberInfo = new HashMap();
    		memberInfo.put("MEM_UID", "");
    	}
    	
    	String pointCode = (String) session.getAttribute("POINT_CODE");
    	String pointUrl = (String) session.getAttribute("POINT_URL");
    	List<Map<String, Object>> codePoint_code = super.getCommonCodes("POINT_CODE");
    	
    	if(pointCode == null || pointCode.equals("")){
			pointCode = codePoint_code.get(0).get("CODE_ID").toString();
			pointUrl = codePoint_code.get(0).get("CODE_URL").toString();
			session.setAttribute("POINT_CODE", pointCode);
			session.setAttribute("POINT_URL", pointUrl);
		}
    	
    	//지점 목록 담기
    	ArrayList<CommonViewPreparer.CodeItem> code = new ArrayList<CommonViewPreparer.CodeItem>(); 
    	for(Map<String, Object> map : codePoint_code){
    		code.add(new CodeItem(map.get("CODE_ID").toString(), map.get("CODE_NM").toString(), map.get("CODE_URL").toString()));
    	}
		
		attributeContext.putAttribute("POINT_LIST", new Attribute(code),true);
        attributeContext.putAttribute("MEMINFO", new Attribute(memberInfo),true);
        attributeContext.putAttribute("POINT_CODE", new Attribute(pointCode), true);
        attributeContext.putAttribute("POINT_URL", new Attribute(pointUrl), true);
    }
    
    public static class CodeItem {
    	private String code_id;
    	private String code_nm;
    	private String code_url;
    	
    	public CodeItem(String code_id, String code_nm, String code_url) {
	    	this.code_id = code_id;
	    	this.code_nm = code_nm;
	    	this.code_url = code_url;
    	}
    	public String getCode_id() {
    		return code_id;
    	}
    	public String getCode_nm(){
    		return code_nm;
    	}
    	public String getCode_url(){
    		return code_url;
    	}
	}

}
