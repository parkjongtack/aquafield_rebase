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

import com.soft.web.base.GenericController;
import com.soft.web.service.front.FrontNoticeEventService;
import com.soft.web.util.AquaDataUtil;
import com.soft.web.util.FileUpload;
import com.soft.web.util.PageUtil;
import com.soft.web.util.Util;
//-- 사용안함 FrontNewsController 로 대체함
//@Controller
public class FrontNoticeEventController extends GenericController {

	protected Logger logger = LoggerFactory.getLogger(FrontNoticeEventController.class);
	
	@Autowired
	FrontNoticeEventService frontNoticeEventService;

	
	public String[] getPageParamList(){
		String[] pageParamList = {"num","page","sw"};
		return pageParamList;
	}
	
	@RequestMapping(value = "/event/eventIndex.af")
    public String eventIndex(@RequestParam Map param, Model model, HttpSession session) {
		int page = 0;
		int pageListSize = 50;
		int blockListSize = 10;
		
		param = Util.chkParam(this.getPageParamList(), param);
		
		page = (param.get("page") == null || "".equals(param.get("page").toString() ) ) ?  1 : Util.getInt(param.get("page").toString());
		if(page < 1) page = 1;
		
		int totalCount = frontNoticeEventService.noticeEventCount(param);
		PageUtil pu = new PageUtil(page, totalCount, pageListSize, blockListSize);

		param.put("pageStartRow", pu.getPageStartRow());
		param.put("pageListSize", pageListSize);
		param.put("rows", pageListSize);
		param.put("page", String.valueOf(page));
		model.addAttribute("resultParam", Util.pageParamMap(this.getPageParamList(), param) );
		model.addAttribute("totalCount", Util.numberFormat(totalCount));
		model.addAttribute("pu", pu);
		model.addAttribute("results", frontNoticeEventService.noticeEventList(param));
		
		if(session.getAttribute("_sessionAdmin") != null){
			model.addAttribute("AUTHWRITER", "W"); 
		}
        return "/front/event/index";
    }
	
	@RequestMapping(value = "/event/ajaxNoticeEventList.af")
    public String ajaxNoticeEventList(@RequestParam Map param, Model model, HttpSession session) {
		int page = 0;
		int pageListSize = 5;
		int blockListSize = 10;
		
		param = Util.chkParam(this.getPageParamList(), param);
		
		page = (param.get("page") == null || "".equals(param.get("page").toString() ) ) ?  1 : Util.getInt(param.get("page").toString());
		if(page < 1) page = 1;
		
		int totalCount = frontNoticeEventService.noticeEventCount(param);
		PageUtil pu = new PageUtil(page, totalCount, pageListSize, blockListSize);

		param.put("pageStartRow", pu.getPageStartRow());
		param.put("pageListSize", pageListSize);
		param.put("rows", pageListSize);
		param.put("page", String.valueOf(page));
		model.addAttribute("resultParam", Util.pageParamMap(this.getPageParamList(), param) );
		model.addAttribute("totalCount", Util.numberFormat(totalCount));
		model.addAttribute("pu", pu);
		model.addAttribute("results", frontNoticeEventService.noticeEventList(param));

        return "/front/event/ajax_list";
    }
	
	@RequestMapping(value = "/event/eventPopup.af")
    public String eventPopup(@RequestParam Map param, Model model, HttpSession session) {
		
        return "/front/event/news_popup";
    }
	
	
	
//	@RequestMapping(value = "/event/write.af")
//    public String write(@RequestParam Map param, Model model, HttpSession session) {
//		model.addAttribute("resultParam", Util.pageParamMap(this.getPageParamList(), param) );
//        return "/front/event/wirte";
//    }
	
	@RequestMapping(value = "/event/eventWrite.af")
    public String eventWrite(@RequestParam Map param, Model model, HttpSession session) {
		if(param.get("num") != null){
			param.put("num",String.valueOf(Util.getInt(param.get("num").toString() ) ) );
			if("0".equals(param.get("num").toString())){
				return "/front/event/event_wirte";
			}
			else{
				model.addAttribute("resultParam", Util.pageParamMap(this.getPageParamList(), param) );
				model.addAttribute("result", frontNoticeEventService.noticeEventDetail(param));
				return "/front/event/event_edit";
			}
		}
		else{
			return "/front/event/event_wirte";
		}
    }
	

	@RequestMapping(value = "/event/noticeWrite.af")
    public String noticeWrite(@RequestParam Map param, Model model, HttpSession session) {
		logger.debug(param.toString());
		if(param.get("num") != null){
			logger.debug("수정");
			param.put("num",String.valueOf(Util.getInt(param.get("num").toString() ) ) );
			if("0".equals(param.get("num").toString())){
				return "/front/event/notice_wirte";
			}
			else{
				model.addAttribute("resultParam", Util.pageParamMap(this.getPageParamList(), param) );
				model.addAttribute("result", frontNoticeEventService.noticeEventDetail(param));
				return "/front/event/notice_edit";
			}
		}
		else{
			logger.debug("등록");
			return "/front/event/notice_wirte";
		}
    }
	
	@RequestMapping(value = "/event/noticeEventWriteAction.af", method=RequestMethod.POST)
    public void noticeEventWriteAction(@RequestParam Map param, ModelMap model, MultipartHttpServletRequest mRequest, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {		
		Map<String,String> fileMap;
		String upload = "/common/upload/notice_event";
		String realPath = "";    //실제 경로 저장 변수
		realPath = super.getSession().getServletContext().getRealPath(upload);

		param.put("instIp",request.getRemoteAddr());
		param.put("instId","admin");
		param.put("updtIp",request.getRemoteAddr());
		param.put("updtId","admin");
		
		param.put("kind"					,param.get("kind")				 == null ? "1" : param.get("kind"));
		param.put("openYn"					,param.get("openYn")			 == null ? "Y" : param.get("openYn"));
		//param.put("readCnt"					,param.get("readCnt")			 == null ? "" : param.get("readCnt"));
		param.put("startDate"				,param.get("startDate")			 == null ? "" : param.get("startDate"));
		param.put("endDate"					,param.get("endDate")			 == null ? "" : param.get("endDate"));
		param.put("locationYn"				,param.get("locationYn")		 == null ? "Y" : param.get("locationYn"));
		param.put("location"				,param.get("location")			 == null ? "" : param.get("location"));
		param.put("title"					,param.get("title")				 == null ? "" : param.get("title"));
		//param.put("fileList"				,param.get("fileList")			 == null ? "" : param.get("fileList"));
		//param.put("fileListFull"			,param.get("fileListFull")		 == null ? "" : param.get("fileListFull"));
		//param.put("fileContent"				,param.get("fileContent")		 == null ? "" : param.get("fileContent"));
		//param.put("fileContentFull"			,param.get("fileContentFull")	 == null ? "" : param.get("fileContentFull"));
		param.put("contents"				,param.get("contents")			 == null ? "" : param.get("contents"));
		param.put("page1Yn"					,param.get("page1")				 == null ? "N" : param.get("page1"));
		param.put("page1LeftKind"			,param.get("page1LeftKind")		 == null ? "" : param.get("page1LeftKind"));
		param.put("page1LeftContent"		,param.get("page1LeftContent")	 == null ? "" : param.get("page1LeftContent"));
		//param.put("page1LeftImg"			,param.get("page1LeftImg")		 == null ? "" : param.get("page1LeftImg"));
		//param.put("page1LeftImgFull"		,param.get("page1LeftImgFull")	 == null ? "" : param.get("page1LeftImgFull"));
		param.put("page1RightKind"			,param.get("page1RightKind")	 == null ? "" : param.get("page1RightKind"));
		param.put("page1RightContent"		,param.get("page1RightContent")	 == null ? "" : param.get("page1RightContent"));
		//param.put("page1RightImg"			,param.get("page1RightImg")		 == null ? "" : param.get("page1RightImg"));
		//param.put("page1RightImgFull"		,param.get("page1RightImgFull")	 == null ? "" : param.get("page1RightImgFull"));
		param.put("page2Yn"					,param.get("page2")				 == null ? "N" : param.get("page2"));
		param.put("page2LeftKind"			,param.get("page2LeftKind")		 == null ? "" : param.get("page2LeftKind"));
		param.put("page2LeftContent"		,param.get("page2LeftContent")	 == null ? "" : param.get("page2LeftContent"));
		//param.put("page2LeftImg"			,param.get("page2LeftImg")		 == null ? "" : param.get("page2LeftImg"));
		//param.put("page2LeftImgFull"		,param.get("page2LeftImgFull")	 == null ? "" : param.get("page2LeftImgFull"));
		param.put("page2RightKind"			,param.get("page2RightKind")	 == null ? "" : param.get("page2RightKind"));
		param.put("page2RightContent"		,param.get("page2RightContent")	 == null ? "" : param.get("page2RightContent"));
		//param.put("page2RightImg"			,param.get("page2RightImg")		 == null ? "" : param.get("page2RightImg"));
		//param.put("page2RightImgFull"		,param.get("page2RightImgFull")	 == null ? "" : param.get("page2RightImgFull"));
		param.put("page3Yn"					,param.get("page3")				 == null ? "N" : param.get("page3"));
		param.put("page3LeftKind"			,param.get("page3LeftKind")		 == null ? "" : param.get("page3LeftKind"));
		param.put("page3LeftContent"		,param.get("page3LeftContent")	 == null ? "" : param.get("page3LeftContent"));
		//param.put("page3LeftImg"			,param.get("page3LeftImg")		 == null ? "" : param.get("page3LeftImg"));
		//param.put("page3LeftImgFull"		,param.get("page3LeftImgFull")	 == null ? "" : param.get("page3LeftImgFull"));
		param.put("page3RightKind"			,param.get("page3RightKind")	 == null ? "" : param.get("page3RightKind"));
		param.put("page3RightContent"		,param.get("page3RightContent")	 == null ? "" : param.get("page3RightContent"));
		//param.put("page3RightImg"			,param.get("page3RightImg")		 == null ? "" : param.get("page3RightImg"));
		//param.put("page3RightImgFull"		,param.get("page3RightImgFull")	 == null ? "" : param.get("page3RightImgFull"));
		param.put("displayYn"				,param.get("displayYn")			 == null ? "Y" : param.get("displayYn"));

		
		
		//-- 파일 업로드
		fileMap = FileUpload.fileUpload(mRequest, realPath, upload, "fileList");
		if(!(fileMap == null || fileMap.get("FILE_NAME") == null || fileMap.get("FILE_NAME").toString().equals("") ) ){
			Map<String,String> fileParam = new HashMap<String,String>();
			param.put("fileList",fileMap.get("FILE_NAME"));
			param.put("fileListFull",fileMap.get("FILE_UPNAME"));
		}
		else{
			param.put("fileList","");
			param.put("fileListFull","");
		}
		fileMap = FileUpload.fileUpload(mRequest, realPath, upload, "fileContent");
		if(!(fileMap == null || fileMap.get("FILE_NAME") == null || fileMap.get("FILE_NAME").toString().equals("") ) ){
			Map<String,String> fileParam = new HashMap<String,String>();
			param.put("fileContent",fileMap.get("FILE_NAME"));
			param.put("fileContentFull",fileMap.get("FILE_UPNAME"));
		}
		else{
			param.put("fileContent","");
			param.put("fileContentFull","");
		}
		fileMap = FileUpload.fileUpload(mRequest, realPath, upload, "page1LeftImg");
		if(!(fileMap == null || fileMap.get("FILE_NAME") == null || fileMap.get("FILE_NAME").toString().equals("") ) ){
			Map<String,String> fileParam = new HashMap<String,String>();
			param.put("page1LeftImg",fileMap.get("FILE_NAME"));
			param.put("page1LeftImgFull",fileMap.get("FILE_UPNAME"));
		}
		else{
			param.put("page1LeftImg","");
			param.put("page1LeftImgFull","");
		}
		fileMap = FileUpload.fileUpload(mRequest, realPath, upload, "page1RightImg");
		if(!(fileMap == null || fileMap.get("FILE_NAME") == null || fileMap.get("FILE_NAME").toString().equals("") ) ){
			Map<String,String> fileParam = new HashMap<String,String>();
			param.put("page1RightImg",fileMap.get("FILE_NAME"));
			param.put("page1RightImgFull",fileMap.get("FILE_UPNAME"));
		}
		else{
			param.put("page1RightImg","");
			param.put("page1RightImgFull","");
		}
		fileMap = FileUpload.fileUpload(mRequest, realPath, upload, "page2LeftImg");
		if(!(fileMap == null || fileMap.get("FILE_NAME") == null || fileMap.get("FILE_NAME").toString().equals("") ) ){
			Map<String,String> fileParam = new HashMap<String,String>();
			param.put("page2LeftImg",fileMap.get("FILE_NAME"));
			param.put("page2LeftImgFull",fileMap.get("FILE_UPNAME"));
		}
		else{
			param.put("page2LeftImg","");
			param.put("page2LeftImgFull","");
		}
		fileMap = FileUpload.fileUpload(mRequest, realPath, upload, "page2RightImg");
		if(!(fileMap == null || fileMap.get("FILE_NAME") == null || fileMap.get("FILE_NAME").toString().equals("") ) ){
			Map<String,String> fileParam = new HashMap<String,String>();
			param.put("page2RightImg",fileMap.get("FILE_NAME"));
			param.put("page2RightImgFull",fileMap.get("FILE_UPNAME"));
		}
		else{
			param.put("page2RightImg","");
			param.put("page2RightImgFull","");
		}
		fileMap = FileUpload.fileUpload(mRequest, realPath, upload, "page3LeftImg");
		if(!(fileMap == null || fileMap.get("FILE_NAME") == null || fileMap.get("FILE_NAME").toString().equals("") ) ){
			Map<String,String> fileParam = new HashMap<String,String>();
			param.put("page3LeftImg",fileMap.get("FILE_NAME"));
			param.put("page3LeftImgFull",fileMap.get("FILE_UPNAME"));
		}
		else{
			param.put("page3LeftImg","");
			param.put("page3LeftImgFull","");
		}
		fileMap = FileUpload.fileUpload(mRequest, realPath, upload, "page3RightImg");
		if(!(fileMap == null || fileMap.get("FILE_NAME") == null || fileMap.get("FILE_NAME").toString().equals("") ) ){
			Map<String,String> fileParam = new HashMap<String,String>();
			param.put("page3RightImg",fileMap.get("FILE_NAME"));
			param.put("page3RightImgFull",fileMap.get("FILE_UPNAME"));
		}
		else{
			param.put("page3RightImg","");
			param.put("page3RightImgFull","");
		}
		
		param = Util.setMapHex(param);
		logger.debug(param.toString() );
		
		try{
			//-- 등록
			if("inst".equals(param.get("mode").toString() ) ){
				
				int noticeEventUid = frontNoticeEventService.noticeEventMaxUid() + 1;				
				param.put("noticeEventUid"	,noticeEventUid);
				frontNoticeEventService.insertNoticeEvent(param);
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
				frontNoticeEventService.updateNoticeEvent(param);
				Util.htmlPrint("{\"result\":true,\"msg\":\"수정되었습니다.\"}", response);
			}
		}
		catch(Exception e){
			Util.htmlPrint("{\"result\":false,\"msg\":\"오류가 발생되었습니다.\"}", response);
		}
    }
	
	@RequestMapping(value = "/event/eventView.af")
    public String eventView(@RequestParam Map param, Model model, HttpSession session) {
		if(param.get("num") != null) param.put("num",String.valueOf(Util.getInt(param.get("num").toString() ) ) );
		frontNoticeEventService.updateNoticeEventHit(param);
		model.addAttribute("resultParam", Util.pageParamMap(this.getPageParamList(), param) );
		model.addAttribute("result", frontNoticeEventService.noticeEventDetail(param));
		
		if(session.getAttribute("_sessionAdmin") != null){
			model.addAttribute("AUTHWRITER", "W"); 
		}
        return "/front/event/event_view";
    }

	@RequestMapping(value = "/event/noticeView.af")
    public String noticeView(@RequestParam Map param, Model model, HttpSession session) {
		if(param.get("num") != null) param.put("num",String.valueOf(Util.getInt(param.get("num").toString() ) ) );
		frontNoticeEventService.updateNoticeEventHit(param);
		model.addAttribute("resultParam", Util.pageParamMap(this.getPageParamList(), param) );
		model.addAttribute("result", frontNoticeEventService.noticeEventDetail(param));
		
		if(session.getAttribute("_sessionAdmin") != null){
			model.addAttribute("AUTHWRITER", "W"); 
		}
        return "/front/event/notice_view";
    }
	
	@RequestMapping(value = "/event/noticeEventDeleteAction.af", method=RequestMethod.POST)
    public void noticeEventDeleteAction(@RequestParam Map param, ModelMap model, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
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
			int deltCnt = frontNoticeEventService.deleteNoticeEvent(param);
			Util.htmlPrint("{\"result\":true,\"msg\":\"삭제가 되었습니다.\"}", response);
		}
		catch(Exception e){
			Util.htmlPrint("{\"result\":false,\"msg\":\"오류가 발생되었습니다.\"}", response);
		}
	}
}
