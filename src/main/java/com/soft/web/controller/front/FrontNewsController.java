package com.soft.web.controller.front;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.soft.web.base.CommonConstant;
import com.soft.web.base.GenericController;
import com.soft.web.service.front.FrontNewsService;
import com.soft.web.util.DecoderUtil;
import com.soft.web.util.FileUpload;
import com.soft.web.util.PageUtil;
import com.soft.web.util.Util;

@Controller
public class FrontNewsController extends GenericController {

	protected Logger logger = LoggerFactory.getLogger(FrontNewsController.class);
	
	@Autowired
	FrontNewsService frontNewsService;
	
	public String[] getPageParamList(){
		String[] pageParamList = {"num","page","sw"};
		return pageParamList;
	}
	
	@RequestMapping(value = "/event/eventIndex.af")
    public String eventIndex(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request) {
		int page = 0;
		int pageListSize = 10;
		int blockListSize = 8;
		int cate = 0;
		String pointCode = "";
		
		this.checkURL(request, session);
		
		if(session.getAttribute("_sessionAdmin") != null || session.getAttribute("_sessionDeskAdmin") != null){
			model.addAttribute("AUTHWRITER", "W");
			param.put("adm", "Y");
			//param.put("page", param.get("bPage"));
		}
			
		pointCode = session.getAttribute("POINT_CODE").toString();
		param = Util.chkParam(this.getPageParamList(), param);
		
		page = (param.get("page") == null || "".equals(param.get("page").toString() ) ) ?  1 : Util.getInt(param.get("page").toString());
		if(page < 1) page = 1;
		
		cate = (param.get("cate") == null || "".equals(param.get("cate").toString() ) ) ?  0 : Util.getInt(param.get("cate").toString());
		
		param.put("pointCode", pointCode);
		param.put("page", String.valueOf(page));
		param.put("cate", String.valueOf(cate));
		
		int totalCount = frontNewsService.newsCount(param);
		PageUtil pu = new PageUtil(page, totalCount, pageListSize, blockListSize);

		param.put("pageStartRow", pu.getPageStartRow());
		param.put("pageListSize", blockListSize);
		param.put("rows", blockListSize);
		
		model.addAttribute("resultParam", Util.pageParamMap(this.getPageParamList(), param) );
		model.addAttribute("page", Util.numberFormat(page));
		model.addAttribute("cate", String.valueOf(cate));
		model.addAttribute("totalCount", Util.numberFormat(totalCount));
		model.addAttribute("pageListSize", Util.numberFormat(pageListSize));
		model.addAttribute("blockListSize", Util.numberFormat(blockListSize));
		model.addAttribute("pu", pu);
		model.addAttribute("pointCode", pointCode);
		model.addAttribute("results", frontNewsService.newsList(param));
		
        return "/front/event/index";
    }
	
	@RequestMapping(value = "/event/ajaxNoticeEventList.af")
    public String ajaxNoticeEventList(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request) {
		int page = 0;
		int pageListSize = 10;
		int blockListSize = 8;
		int cate = 0;
		String pointCode = "";
		
		this.checkURL(request, session);
		
		param = Util.chkParam(this.getPageParamList(), param);
		
		page = (param.get("page") == null || "".equals(param.get("page").toString() ) ) ?  1 : Util.getInt(param.get("page").toString());
		if(page < 1) page = 1;
		
		cate = (param.get("cate") == null || "".equals(param.get("cate").toString() ) ) ?  0 : Util.getInt(param.get("cate").toString());
		
		if(session.getAttribute("_sessionAdmin") != null || session.getAttribute("_sessionDeskAdmin") != null){
			model.addAttribute("AUTHWRITER", "W");
			param.put("adm", "Y");
		}
		
		pointCode = session.getAttribute("POINT_CODE").toString();
		param.put("pointCode", pointCode);
		
		int totalCount = frontNewsService.newsCount(param);
		PageUtil pu = new PageUtil(page, totalCount, pageListSize, blockListSize);
			
		
		param.put("pageStartRow", pu.getPageStartRow());
		param.put("pageListSize", blockListSize);
		param.put("rows", blockListSize);
		param.put("page", String.valueOf(page));
		param.put("cate", String.valueOf(cate));
		model.addAttribute("resultParam", Util.pageParamMap(this.getPageParamList(), param) );
		model.addAttribute("page", Util.numberFormat(page));
		model.addAttribute("totalCount", Util.numberFormat(totalCount));
		model.addAttribute("pageListSize", Util.numberFormat(pageListSize));
		model.addAttribute("blockListSize", Util.numberFormat(blockListSize));
		model.addAttribute("pu", pu);
		model.addAttribute("cate", cate);
		model.addAttribute("results", frontNewsService.newsList(param));

        return "/front/event/ajax_list";
    }
	
	@RequestMapping(value = "/event/eventPopup.af")
    public String eventPopup(@RequestParam Map param, Model model, HttpSession session) {
		
        return "/front/event/news_popup";
    }
	
	@RequestMapping(value = "/event/write.af")
    public String write(@RequestParam Map param, Model model, HttpSession session) {
		String pointCode = "";
		if(session.getAttribute("_sessionAdmin") != null || session.getAttribute("_sessionDeskAdmin") != null){
			pointCode = param.get("pointCode").toString();
		}else{
			pointCode = session.getAttribute("POINT_CODE").toString();
			param.put("pointCode", pointCode);
		}
		model.addAttribute("pointCode", pointCode);
		model.addAttribute("resultParam", Util.pageParamMap(this.getPageParamList(), param) );
        return "/front/event/wirte";
    }
	

	@RequestMapping(value = "/event/noticeWrite.af")
    public String noticeWrite(@RequestParam Map param, Model model, HttpSession session) {
		String pointCode = "";
		if(session.getAttribute("_sessionAdmin") != null || session.getAttribute("_sessionDeskAdmin") != null){
			param.put("adm", "Y");
			pointCode = param.get("pointCode").toString();
		}else{
			pointCode = session.getAttribute("POINT_CODE").toString();
			param.put("pointCode", pointCode);
		}
		model.addAttribute("pointCode", pointCode);
		
		logger.debug(param.toString());
		if(param.get("num") != null){
			param.put("num",String.valueOf(Util.getInt(param.get("num").toString() ) ) );
			if("0".equals(param.get("num").toString())){
				return "/front/event/notice_wirte";
			}
			else{
				model.addAttribute("resultParam", Util.pageParamMap(this.getPageParamList(), param) );
				model.addAttribute("result", frontNewsService.newsDetail(param));
				return "/front/event/notice_edit";
			}
		}
		else{
			return "/front/event/notice_wirte";
		}
    }
	
	@RequestMapping(value = "/event/noticeEventWriteAction.af", method=RequestMethod.POST)
    public void noticeEventWriteAction(@RequestParam Map param, ModelMap model, MultipartHttpServletRequest mRequest, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {		
		Map<String,String> admdesk = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_DESK_ADMIN);		
		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN) == null ? admdesk : (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		if(admin == null || admin.isEmpty()){
			Util.htmlPrint("{\"result\":false,\"msg\":\"로그인 정보가 없습니다.\"}", response);
			return;
		}
		
		Map<String,String> fileMap;
		String pointCode = param.get("POINT_CODE").toString();
		//지점별로 업로드 경로 분기 처리
		String upload = "/common/upload/notice_event/"+pointCode;
		String realPath = "";    //실제 경로 저장 변수
		realPath = super.getSession().getServletContext().getRealPath(upload);

		param.put("INST_IP",request.getRemoteAddr());
		param.put("INST_ID",admin.get("SESSION_ADMIN_ID"));
		param.put("UPDT_IP",request.getRemoteAddr());
		param.put("UPDT_ID",admin.get("SESSION_ADMIN_ID"));

		//-- 파일 업로드
		fileMap = FileUpload.fileUpload(mRequest, realPath, upload, "FILE_LIST");
		if(!(fileMap == null || fileMap.get("FILE_NAME") == null || fileMap.get("FILE_NAME").toString().equals("") ) ){
			Map<String,String> fileParam = new HashMap<String,String>();
			param.put("FILE_LIST",fileMap.get("FILE_NAME"));
			param.put("FILE_LIST_FULL",fileMap.get("FILE_UPNAME"));
		}
		else{
			param.put("FILE_LIST","");
			param.put("FILE_LIST_FULL","");
		}
		fileMap = FileUpload.fileUpload(mRequest, realPath, upload, "FILE_CONTENT");
		if(!(fileMap == null || fileMap.get("FILE_NAME") == null || fileMap.get("FILE_NAME").toString().equals("") ) ){
			Map<String,String> fileParam = new HashMap<String,String>();
			param.put("FILE_CONTENT",fileMap.get("FILE_NAME"));
			param.put("FILE_CONTENT_FULL",fileMap.get("FILE_UPNAME"));
		}
		else{
			param.put("FILE_CONTENT","");
			param.put("FILE_CONTENT_FULL","");
		}
		
		//-- 16진수 변경
		param = Util.setMapHex(param);
		try{
			//-- 등록
			if("inst".equals(param.get("mode").toString() ) ){
				param.put("NEWS_UID"	,frontNewsService.newsMaxUid() + 1);
				frontNewsService.newsInsert(param);
				Util.htmlPrint("{\"result\":true,\"msg\":\"등록되었습니다.\"}", response);
			}
			//-- 수정
			else if("updt".equals(param.get("mode").toString() ) ){
				if(param.get("num") != null && !"".equals(param.get("num"))){
					param.put("num", Util.getInt(param.get("num").toString() ) );
				}
				else{
					Util.htmlPrint("{result:false,msg:\"필수값이 없습니다.\"}", response);
					return;
				}
				frontNewsService.newsUpdate(param);
				Util.htmlPrint("{\"result\":true,\"msg\":\"수정되었습니다.\"}", response);
			}
		}
		catch(Exception e){
			Util.htmlPrint("{\"result\":false,\"msg\":\"오류가 발생되었습니다.\"}", response);
		}
    }

	@RequestMapping(value = "/event/noticeView.af")
    public String noticeView(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request) {
		String pointCode = "";
		if(param.get("num") != null) param.put("num",String.valueOf(Util.getInt(param.get("num").toString() ) ) );
		
		this.checkURL(request, session);
		
		if(session.getAttribute("_sessionAdmin") != null || session.getAttribute("_sessionDeskAdmin") != null){
			model.addAttribute("AUTHWRITER", "W");
			param.put("adm", "Y");
			pointCode = param.get("pointCode").toString();
		}else{
			pointCode = session.getAttribute("POINT_CODE").toString();
			param.put("pointCode", pointCode);
		}
		
		frontNewsService.newsHitUpdate(param);
		model.addAttribute("resultParam", Util.pageParamMap(this.getPageParamList(), param) );
		model.addAttribute("result", frontNewsService.newsDetail(param));
		model.addAttribute("pointCode", pointCode);
		
        return "/front/event/notice_view";
    }
	
	@RequestMapping(value = "/event/eventView.af")
    public String eventView(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request) {
		String pointCode = "";
		if(param.get("num") != null) param.put("num",String.valueOf(Util.getInt(param.get("num").toString() ) ) );
		
		this.checkURL(request, session);
		
		if(session.getAttribute("_sessionAdmin") != null){
			model.addAttribute("AUTHWRITER", "W");
			param.put("adm", "Y");
			pointCode = param.get("pointCode").toString();
		}else{
			pointCode = session.getAttribute("POINT_CODE").toString();
			param.put("pointCode", pointCode);
		}

		frontNewsService.newsHitUpdate(param);
		model.addAttribute("resultParam", Util.pageParamMap(this.getPageParamList(), param) );
		model.addAttribute("result", frontNewsService.newsDetail(param));
		model.addAttribute("pointCode", pointCode);
		
        return "/front/event/event_view";
    }	
	
	@RequestMapping(value = "/event/previewView.af")
    public String previewView(@RequestParam Map param, Model model, HttpSession session) {		
        return "/front/event/preview_view";
    }
	
	@RequestMapping(value = "/event/noticeEventDeleteAction.af", method=RequestMethod.POST)
    public void noticeEventDeleteAction(@RequestParam Map param, ModelMap model, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		Map<String,String> admdesk = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_DESK_ADMIN);		
		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN) == null ? admdesk : (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		if(admin == null || admin.isEmpty()){
			Util.htmlPrint("{\"result\":false,\"msg\":\"로그인 정보가 없습니다.\"}", response);
			return;
		}
		boolean isTrue = true;
		if(param.get("num") != null && !"".equals(param.get("num"))){
			param.put("num", Util.getInt(param.get("num").toString() ) );
			if("0".equals(param.get("num").toString())){
				isTrue = false;
			}
			
		}
		else{
			isTrue = false;
		}
		
		if(!isTrue){
			Util.htmlPrint("{\"result\":false,\"msg\":\"필수값이 없습니다.\"}", response);
			return;
		}

		try{
			//-- DB 삭제
			int deltCnt = frontNewsService.newsDelete(param);
			Util.htmlPrint("{\"result\":true,\"msg\":\"삭제가 되었습니다.\"}", response);
		}
		catch(Exception e){
			Util.htmlPrint("{\"result\":false,\"msg\":\"오류가 발생되었습니다.\"}", response);
		}
	}
	
	//url 체크하여 세션 변경
	public String checkURL(HttpServletRequest request, HttpSession session){
		String pointUrl = (String) session.getAttribute("POINT_URL");
		if(request.getHeader("REFERER") != null){
    		String beforeUrl = request.getHeader("REFERER");
    		//http://localhost:8083/goyang/index.af
    		int indexof = beforeUrl.indexOf(pointUrl);
    		if(beforeUrl.indexOf(pointUrl) != -1){
    			//포함
    		}else{
    			//포함아님
    			String[] aa = beforeUrl.split("/");
    			
    			String a1 = aa[3] + "/" + aa[4];
    			Map pointInfo = super.getPointInfo(a1);
    			session.removeAttribute("POINT_CODE");
    			session.removeAttribute("POINT_URL");
    			session.setAttribute("POINT_CODE", pointInfo.get("CODE_ID"));
    			session.setAttribute("POINT_URL", pointInfo.get("CODE_URL"));
    		}
    	}
		return null;
	}
}
