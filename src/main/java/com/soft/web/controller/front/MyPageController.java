package com.soft.web.controller.front;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.io.IOException;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import Kisinfo.Check.IPINClient;

import com.soft.web.base.Cryptography;
import com.soft.web.base.GenericController;
import com.soft.web.base.PasswordEncoder;
import com.soft.web.controller.front.MyPageController;
import com.soft.web.mail.MailService;
import com.soft.web.service.common.CommonService;
import com.soft.web.service.front.FrontMemberService;
import com.soft.web.service.front.FrontOneToOneService;
import com.soft.web.service.front.FrontReservationService;
import com.soft.web.sms.SmsService;
import com.soft.web.util.AquaDataUtil;
import com.soft.web.util.AquaDateUtil;
import com.soft.web.util.DecoderUtil;
import com.soft.web.util.PageUtil;
import com.soft.web.util.Util;

@Controller
public class MyPageController extends GenericController {

	protected Logger logger = LoggerFactory.getLogger(MyPageController.class);
	
    @Resource(name="config")
    private Properties config;
    
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private MailService mailService;

	@Autowired
	FrontMemberService frontMemberService;
	
	@Autowired
	FrontOneToOneService frontOneToOneService;
	
	@Autowired
	FrontReservationService frontReservationService;
	
	@Autowired
	SmsService smsService;
	
	@Autowired
	CommonService commonService;
    
	protected String[] pageParamList = {"page","searchtext","statdate","enddate"};    

	@RequestMapping(value = "/mypage/mypage.af")
	public String mypage(@RequestParam Map param, Model model,
			HttpSession session) {
		
		return "/front/mypage/mypage";
	}	

	@RequestMapping(value = "/mypage/pwd.af")
	public String pwd(@RequestParam Map param, Model model, HttpSession session) {
		
		session.setAttribute("check", "N");
		
		return "/front/mypage/password";
	}

	@RequestMapping(value = "/mypage/proccess.af")
	public String proccess(@RequestParam Map param, Model model,
			HttpSession session, HttpServletResponse response)
			throws IOException {

		String html = "";
		Map memberInfo = (Map) session.getAttribute("MEM_INFO");
		Map parameter = new HashMap();
		parameter.put("point_code", memberInfo.get("POINT_CODE"));
		parameter.put("mem_id", (String)memberInfo.get("MEM_ID"));
		parameter.put("mem_pw", (String)param.get("member_pw"));

		Map memberInfo2 = frontMemberService.memberInfo(parameter);
		
		
		if (memberInfo2 != null ) {
			String message = "�솗�씤�릺�뿀�뒿�땲�떎.";
			String url = "contentBox.showCont({url : '/mypage/myinfo.af', move:'next'})";
			html = Util.gotoUrl(url, message);
			session.setAttribute("check", "Y");
			
		} else {
			String message = "�엯�젰�븯�떊 鍮꾨�踰덊샇媛� ��由쎈땲�떎.\\n�솗�씤�븯�떆湲� 諛붾엻�땲�떎.";
			String url = "document.submitForm.member_pw.value='';";
			html = Util.gotoUrl(url, message);
			session.setAttribute("check", "N");
			
		}
		Util.htmlPrint(html, response);

		return null;
	}

	@RequestMapping(value = "/mypage/myinfo.af")
	public String myinfo(@RequestParam Map param, Model model,
			HttpSession session) {
		
		String chkY = (String) session.getAttribute("check");

		if("Y".equals(chkY)) {
			Map sessionMember = (Map) session.getAttribute("MEM_INFO");
			Map parameter = new HashMap();
			parameter.put("mem_uid", sessionMember.get("MEM_UID"));
			Map memberInfo = frontMemberService.memberUpdInfo(parameter);
			
			String leave = "YES";
			int intMemUid = Integer.parseInt(sessionMember.get("MEM_UID").toString());
			int getReservelistCnt = frontReservationService.getReservelistCnt(intMemUid);
			if(getReservelistCnt > 0){leave = "NO";}
			
			model.addAttribute("memberInfo", memberInfo);
			model.addAttribute("leave", leave);
			
			return "/front/mypage/myinfo";
			
		} else {
			
			return "/front/mypage/password";
			
		}
		
	}
	@RequestMapping(value = "/mypage/myinfo_write.af")
	public String myinfo_write(@RequestParam Map param, Model model,
			HttpSession session) {
		
		Map sessionMember = (Map) session.getAttribute("MEM_INFO");
		Map parameter = new HashMap();
		parameter.put("mem_uid", sessionMember.get("MEM_UID"));
		Map memberInfo = frontMemberService.memberUpdInfo(parameter);		
		
		model.addAttribute("memberInfo", memberInfo);		
		return "/front/mypage/myinfo_write";
	}
	
	//留덉씠�럹�씠吏� �닔�젙
	@RequestMapping(value = "/mypage/myinfoUpd.af")
	public String myinfoUpd(@RequestParam Map param, Model model,
			HttpSession session, HttpServletResponse response)
			throws Exception {

		String html = "";
		Map memberInfo = (Map) session.getAttribute("MEM_INFO");
		Map parameter = new HashMap();
		parameter.put("mem_uid", memberInfo.get("MEM_UID"));
		parameter.put("mem_pw", param.get("PWD1"));
		parameter.put("mem_birth", param.get("birthday"));
		parameter.put("birth_type", param.get("lunar"));
		parameter.put("mem_gender", param.get("sex"));
		parameter.put("home_addr1", DecoderUtil.decode(param, "address"));
		parameter.put("home_addr2", DecoderUtil.decode(param, "address2"));
		parameter.put("company_nm", DecoderUtil.decode(param, "companyNm"));
		parameter.put("company_addr1", DecoderUtil.decode(param, "company_address"));
		parameter.put("company_addr2", DecoderUtil.decode(param, "company_address2"));
		parameter.put("mem_id", DecoderUtil.decode(param, "userId"));
		parameter.put("company_phone_num", param.get("companytel"));
		parameter.put("memory_day", param.get("memoryday"));
		parameter.put("received_info_at", param.get("receiveChk"));
		parameter.put("upd_id", memberInfo.get("MEM_ID"));
		parameter.put("mobile_num", param.get("phoneNum"));		

		String result = frontMemberService.setMemInfo(parameter);

		if ("SETOK".equals(result)) {
			String message = "�닔�젙�릺�뿀�뒿�땲�떎.";
			String url = "contentBox.showCont({url : '/mypage/myinfo.af', move:'next'})";
			html = Util.gotoUrl(url, message);
			
			// �쉶�썝�젙蹂� �닔�젙�떆 濡쒓렇�씤 �꽭�뀡 �떎�떆 set ###################################
			int memUid = Util.getInt(memberInfo.get("MEM_UID").toString());
			
			Map reMemberInfo = frontMemberService.memberInfoTwo(memUid);
						
			InetAddress ip = InetAddress.getLocalHost();
			String userIp = ip.getHostAddress();
			reMemberInfo.put("MEM_IP", userIp); //�젒�냽 IP�젙蹂�
			session.setAttribute("MEM_INFO", reMemberInfo);
			// ############################################################
			
		} else {
			String message = "泥섎━以� �뿉�윭媛� 諛쒖깮�븯���뒿�땲�떎.\\n�떎�떆 �떆�룄�빐 二쇱떆湲곕컮�엻�땲�떎.";
			html = Util.alert(message);
		}
		Util.htmlPrint(html, response);

		return null;
	}
	
	@RequestMapping(value = "/mypage/myinfoUpd2.af")
	public String myinfoUpd2(@RequestParam Map param, Model model,
			HttpSession session, HttpServletResponse response)
			throws IOException {

		String html = "";
		Map memberInfo = (Map) session.getAttribute("MEM_INFO");
		Map parameter = new HashMap();
		parameter.put("point_code", memberInfo.get("POINT_CODE"));
		parameter.put("mem_id", memberInfo.get("MEM_ID"));
		parameter.put("mem_pw", (String)param.get("userPW"));
		Map memInfo = frontMemberService.memberInfo(parameter);		
		
		if(memInfo != null ){		
			parameter.put("mem_uid", memberInfo.get("MEM_UID"));
			parameter.put("mem_pw", param.get("PWD1"));
			parameter.put("upd_id", memberInfo.get("MEM_ID"));	
	
			String result = frontMemberService.setMemPwUpd(parameter);
	
			if ("SETOK".equals(result)) {
				String message = "�닔�젙�릺�뿀�뒿�땲�떎.";
				String url = "contentBox.showCont({url : '/mypage/myinfo.af', move:'next'})";
				html = Util.gotoUrl(url, message);
			} else {
				String message = "泥섎━以� �뿉�윭媛� 諛쒖깮�븯���뒿�땲�떎.\\n�떎�떆 �떆�룄�빐 二쇱떆湲곕컮�엻�땲�떎.";
				html = Util.alert(message);
			}
		}else{
			String message = "�쁽�옱鍮꾨�踰덊샇媛� ��由쎈땲�떎.\\n�떎�떆 �떆�룄�빐 二쇱떆湲곕컮�엻�땲�떎.";
			html = Util.alert(message);			
		}
		Util.htmlPrint(html, response);

		return null;
	}	
	
	@RequestMapping(value = "/mypage/cs.af")
	public String cs(@RequestParam Map param, Model model, HttpSession session) {
		Map memberInfo = (Map) session.getAttribute("MEM_INFO");
		
		model.addAttribute("name", memberInfo.get("MEM_NM"));
		return "/front/mypage/cs";
	}
	
	@RequestMapping(value = "/mypage/ajaxCs.af")
	public String ajaxCs(@RequestParam Map param, Model model, HttpSession session, HttpServletResponse response) throws IOException {
		int page = 0;
		int pageListSize = 6;
		int blockListSize = 10;
		
		Map memberInfo = (Map) session.getAttribute("MEM_INFO");
		
		//�럹�씠吏�
		page = (param.get("page") == null || "".equals(param.get("page").toString() ) ) ?  page = 1 : Util.getInt(param.get("page").toString());
		param.put("ins_uid", memberInfo.get("MEM_UID"));
		param.put("point_code", memberInfo.get("POINT_CODE"));
		
		int totCnt = frontOneToOneService.onetoOnelistCnt(param);
		PageUtil paging = new PageUtil(page, totCnt, pageListSize, blockListSize);
		param.put("pageStartRow", paging.getPageStartRow());
		param.put("pageListSize", pageListSize);
		param.put("page", String.valueOf(page));		
		
		//紐⑸줉 議고쉶
		List<Map> onetoOnelist = frontOneToOneService.onetoOnelist(param);
		
		//String html ="<div class=\"inner\"><div class=\"tb_type2\"><table><thead><tr><th>臾몄쓽�쑀�삎</th><th>�젣紐�</th><th>�옉�꽦�씪</th><th>泥섎━�긽�깭</th></tr></thead><tbody>";
		String html ="";
		
		if(!onetoOnelist.isEmpty()){
			for (Iterator iterator = onetoOnelist.iterator(); iterator.hasNext();) {
				Map map = (Map) iterator.next();
				
				html += "<tr><td class='type'>"+map.get("POINT_NM")+"</td>";
				html += "<td class='type'>"+map.get("ASK_TYPE")+"</td>";
				html += "<td class=\"left\"><a href=\"javascript:void(0);\" onclick=\"contentBox.popupLayer({url : '/mypage/csView.af', data : { askUid : "+map.get("ASK_UID")+" }})\">"+map.get("ASK_TITLE")+"</a></td>";
				html += "<td>"+map.get("INS_DATE")+"</td>";
				html += "<td class=\"status\"><span class="+map.get("ASK_STAT_CLASS")+">"+map.get("ASK_STAT")+"</span></td>";
			}

		}else{
			html += "<tr><td colspan=\"5\"> 議고쉶�맂 �궡�슜�씠 �뾾�뒿�땲�떎. </td><tr>";			
		}
		html +="<script type='text/javascript'>setPaging({ totalCount: "+totCnt+", recordCount: "+pageListSize+", perPage: "+blockListSize+", page: "+page+" });</script>";

		/*if(!onetoOnelist.isEmpty()){
			html +="<div class=\"paginate\">";
			if(paging.getBlockStartNum() > 1){
				html +="<a href=\"javascript:void(0);\" onclick=\"doPaging(1);\" class=\"btn prev\"><<</a>";
				html +="<a href=\"javascript:void(0);\" onclick=\"doPaging('"+(paging.getBlockStartNum()-1)+"');\" class=\"btn prev\"><</a>";
			}
			for (int i = paging.getBlockStartNum(); i <= paging.getBlockEndNum(); i++) {
				if(page == i){
					html +="<a href=\"javascript:void(0);\" class=\"num on\">"+i+"</a>";
				}else{
					html +="<a href=\"javascript:void(0);\" onclick=\"doPaging('"+i+"');\" class=\"num\">"+i+"</a>";
				}
			}
			if(paging.getBlockEndNum() < paging.getPageTotalCount()){
				html +="<a href=\"javascript:void(0);\" onclick=\"doPaging('"+(paging.getBlockEndNum()+1)+"');\" class=\"btn next\">></a>";
				html +="<a href=\"javascript:void(0);\" onclick=\"doPaging('"+paging.getPageTotalCount()+"');\" class=\"btn next\">>></a>";
			}
			html +="</div></div>";					
		}	*/
		Util.htmlPrint(html, response);		
		
		return null;
	}	
	
	@RequestMapping(value = "/mypage/csWrite.af") 
	public String csWrite(@RequestParam Map param, Model model, HttpSession session) {

		List<Map> typeList = super.getCommonCodes("ASK_TYPE");
		List<Map> pointList = super.getCommonCodes("POINT_CODE");
		
		model.addAttribute("pointList", pointList);
		model.addAttribute("typeList", typeList);		
		return "/front/mypage/cs_write";
	}
	
	@RequestMapping(value = "/mypage/ajaxCsIns.af")
	public String ajaxCsIns(@RequestParam Map param, Model model, HttpSession session, HttpServletResponse response) throws IOException, MessagingException {
		
		String html ="";
		String strContent = (String) param.get("content");
		//strContent = strContent.replaceAll("\r\n", "<br/>");
		Map memberInfo = (Map) session.getAttribute("MEM_INFO");
		param.put("point_code", param.get("pointCode"));
		param.put("writer", memberInfo.get("MEM_NM"));	
		param.put("ask_title", param.get("title"));	
		param.put("ask_content", strContent);			
		param.put("ins_ip", memberInfo.get("MEM_IP"));
		param.put("ins_id", memberInfo.get("MEM_ID"));
		param.put("ins_uid", memberInfo.get("MEM_UID"));
		param.put("mobile_num", memberInfo.get("MOBILE_NUM"));			
		param.put("ask_type", param.get("askType"));
		param.put("mem_type", "1");		

		String result = frontOneToOneService.oneToOneIns(param);
	
		if("INSOK".equals(result)){
			html +="alert('�젙�긽 �벑濡� �릺�뿀�뒿�땲�떎.');";
			html +="$('.btn_close.layer_close').click();";
			html +="contentBox.showCont({url:'/mypage/cs.af', move:'prev'})";
			
			//1:1臾몄쓽 �븣由� 硫붿씪諛쒖넚 #####################################
			String reHtml= memberInfo.get("MEM_NM").toString()+"("+memberInfo.get("MEM_ID")+") 怨좉컼�떂�씠 1:1臾몄쓽瑜� �벑濡앺븯�뀲�뒿�땲�떎.";

			boolean booleanresult =	mailService.sendmail(mailSender, param.get("ins_id").toString(), memberInfo.get("MEM_NM").toString(), "aquafield@shinsegae.com", "�븘荑좎븘�븘�뱶 �슫�쁺�옄", "[�븘荑좎븘�븘�뱶]1:1臾몄쓽 �븣由쇰찓�씪�엯�땲�떎.", reHtml);
			
			if(!booleanresult){
				logger.debug("@@@@@@@@@@@@@@@@ 1:1臾몄쓽 �븣由� 硫붿씪 諛쒖넚以� �뿉�윭媛� 諛쒖깮�뻽�뒿�땲�떎. @@@@@@@@@@@@@@@@@");
			}
			//###########################################			
			
		}else{
			html +="alert('泥섎━以� �뿉�윭媛� 諛쒖깮�븯���뒿�땲�떎.');";			
		}
		Util.htmlPrint(html, response);		
		
		return null;
	}	
	
	@RequestMapping(value = "/mypage/csView.af")
	public String csView(@RequestParam Map param, Model model, HttpSession session) throws Exception {

		Map memberInfo = (Map) session.getAttribute("MEM_INFO");
		//�럹�씠吏�
		Map param2= new HashMap<>();
		param2.put("insUid", memberInfo.get("MEM_UID"));
		param2.put("askUid", param.get("askUid"));
		//Map oneToOneInfo = frontOneToOneService.onetoOneInfo((String) param.get("askUid") );
		Map oneToOneInfo = frontOneToOneService.onetoOneInfoNew(param2);
		
		if(oneToOneInfo ==null){
			throw new Exception();
		}
		
		model.addAttribute("oneToOneInfo", oneToOneInfo);	
		return "/front/mypage/cs_view";
	}	
	
	@RequestMapping(value = "/mypage/reserve.af")
	public String reserve(@RequestParam Map param, Model model, HttpSession session) {

		Map memberInfo = (Map) session.getAttribute("MEM_INFO");
		String statdate = "";
		String enddate = AquaDateUtil.getToday().substring(0, 8);

		int term = 0;
		if(!"".equals(param.get("term")) && param.get("term") != null){
	        term = Integer.parseInt(param.get("term").toString());			
		}

        switch (term) {
			case 1: statdate = AquaDateUtil.addYearMonthDay(enddate, 0, -6, 0).substring(0, 8); break;
			case 2: statdate = AquaDateUtil.addYearMonthDay(enddate, -1, 0, 0).substring(0, 8); break;
		}
		
		Map parameter = new HashMap();
		parameter.put("mem_uid", memberInfo.get("MEM_UID"));
		parameter.put("statdate", statdate);			
		
		List<Map> getReservelist = frontReservationService.getReservelist(parameter);		
		
		model.addAttribute("getReservelist", getReservelist);	
		return "/front/mypage/reservation";
	}
	
	@RequestMapping(value = "/mypage/ajaxReserve.af")
	public String ajaxReserve(@RequestParam Map param, Model model, HttpSession session, HttpServletResponse response) throws IOException {
		
		String html ="";
		Map memberInfo = (Map) session.getAttribute("MEM_INFO");
		String re_state = "";
		String statdate = "";
		String enddate = AquaDateUtil.getToday().substring(0, 8);;
		
		if(!"".equals(param.get("re_state")) && param.get("re_state") != null){
			re_state = (String) param.get("re_state");
		}

		int term = 0;
		if(!"".equals(param.get("term")) && param.get("term") != null){
	        term = Integer.parseInt(param.get("term").toString());			
		}

        switch (term) {
        	case 0: enddate = ""; break;
			case 1: statdate = AquaDateUtil.addYearMonthDay(enddate, 0, -6, 0).substring(0, 8); break;
			case 2: statdate = AquaDateUtil.addYearMonthDay(enddate, -1, 0, 0).substring(0, 8); break;
		}
		
		Map parameter = new HashMap();
		parameter.put("mem_uid", memberInfo.get("MEM_UID"));
		parameter.put("reserve_state", re_state);
		parameter.put("statdate", statdate);	
		parameter.put("enddate", enddate);			
		
		List<Map> getReservelist = frontReservationService.getReservelist(parameter);
		
		for (Iterator iterator = getReservelist.iterator(); iterator.hasNext();) {
			Map map = (Map) iterator.next();
			
			html += "<div class=\"swiper-slide\"><div class=\"resTicket\"><div class=\"inner\"><ul><li class=\"front\"><div class=\"tickettop\">";
			html += "<img src=\"/common/front/images/mypage/ticket_header_logo.png\"></div>";
			html += "<div class=\"codenum\"><div class=\"ico res_1\"></div><div class=\"tit\">�븘荑좎븘�븘�뱶 <span>"+map.get("POINT_CODE")+"�젏</span> �엯�옣沅�</div>";
			html += "<div class=\"code\">No."+map.get("ORDER_NUM")+"</div></div><div class=\"txtArea\">";
			html += "<ul><li><span class=\"tit\">�엯 �옣 �씪</span><span class=\"value\">"+map.get("RESERVE_DATE")+"</span></li>";
			html += "<li><span class=\"tit\">�삁�빟�떆�꽕</span><span class=\"value\">"+map.get("ORDER_NM")+" �쇅</span></li>";
			html += "<li><span class=\"tit\">�삁�빟�씤�썝</span><span class=\"value\">珥� "+((Integer.parseInt(map.get("ADULT_SUM").toString())+ Integer.parseInt(map.get("CHILD_SUM").toString())))+"紐�</span></li>";
			html += "<li><span class=\"tit\">寃곗젣湲덉븸</span><span class=\"value\">"+map.get("PAYMENT_PRICE")+"�썝</span></li>";
			html += "</ul></div></li>";
			html += "<li class=\"back\"><div class=\"info\"><div class=\"tit\">�엯�옣�젙蹂�</div><ul class=\"item_list\">";
			html += "<li><span class=\"tit\">"+map.get("ORDER_NM")+"</span><span class=\"value\">���씤 "+map.get("ADULT_SUM")+"紐�</span></li>";
			html += "<li><span class=\"tit\">"+map.get("ORDER_NM")+"</span><span class=\"value\">�냼�씤 "+map.get("CHILD_SUM")+"紐�</span></li>";
			html += "</ul></div><div class=\"txtArea\"><div class=\"alert\">* 二쇱쓽�궗�빆</div>";
			html += "<p>�씠�슜�씪 �떦�씪 痍⑥냼 �떆 30% �쐞�빟湲덉씠 諛쒖깮�빀�땲�떎.</p><p>36媛쒖썡 誘몃쭔 �쑀�븘�뒗 臾대즺�엯�옣�씠 媛��뒫�븯硫� �쁽�옣諛⑸Ц �떆 �쓽猷뚮낫�뿕利� �벑�쓽 怨듭씤 �꽌瑜섎�� �젣�떆 諛붾엻�땲�떎.</p>	<p>�냼�씤(36媛쒖썡 ~ 珥덈벑�븰�깮 �엯�옣 �떆 �쓽猷뚮낫�뿕利� �벑�쓽 怨듭씤�꽌瑜섎�� 留ㅽ몴�냼�뿉 �젣�떆 諛붾엻�땲�떎.  </p>";
			html += "</div></li></ul><div class=\"btnArea\">";
			html += "<a href=\"javascript:void(0);\" onclick=\"window.resTicketPop = new ResTicketPopFn({data:{resid:'17092304221', stat:'Y'}});\">";
			html += "More Info</a></div></div><div class=\"border\">";
			html += "<img class=\"topleft\" src=\"/common/front/images/mypage/border_topleft.png\">";
			html += "<img class=\"topright\" src=\"/common/front/images/mypage/border_topright.png\">";
			html += "<img class=\"bottomleft\" src=\"/common/front/images/mypage/border_bottomleft.png\">";
			html += "<img class=\"bottomright\" src=\"/common/front/images/mypage/border_bottomright.png\"></div></div></div>";		
		}		
		Util.htmlPrint(html, response);		
		
		return null;
	}	
	
	@RequestMapping(value = "/mypage/reserveView.af")
	public String reserveView(@RequestParam Map param, Model model, HttpSession session) throws Exception {
		Map param1 = new HashMap<>();
		
		int intUid = Integer.parseInt(param.get("uid").toString());
		
		Map memberInfo1 = (Map) session.getAttribute("MEM_INFO");
		String mem_id = memberInfo1.get("MEM_ID").toString();
		param1.put("reserve_uid", intUid);
		param1.put("mem_id", mem_id);
		
		/* 湲곗〈�삁�빟�젙蹂대낫湲�
		 * Map getReserveInfo = frontReservationService.getReserveInfo(intUid);*/
		
		//�삁�빟�젙蹂대낫湲�(�쎒痍⑥빟�젏 )
		Map getReserveInfo = frontReservationService.getReserveInfoNew(param1);
		
		if(getReserveInfo == null) {
			throw new Exception();
		}
		
		int intSpaAdultW = 0, intSpaChildW = 0, intWaterAdultW =0 , intWaterChildW=0, intComplexAdultW =0 , intComplexChildW=0, event1W=0, event2W=0, event3W=0, rental1W=0, rental2W=0, rental3W  = 0;
		
		if(!"".equals(getReserveInfo.get("SPA_ITEM")) && getReserveInfo.get("SPA_ITEM") != null){
			Map priceInfo = frontReservationService.getItemInfo(getReserveInfo.get("SPA_ITEM").toString());
			intSpaAdultW = Integer.parseInt(priceInfo.get("ADULTS_PRICE").toString());
			intSpaChildW = Integer.parseInt(priceInfo.get("CHILD_PRICE").toString());
		}
		
		if(!"".equals(getReserveInfo.get("WATER_ITEM")) && getReserveInfo.get("WATER_ITEM") != null){
			Map priceInfo = frontReservationService.getItemInfo(getReserveInfo.get("WATER_ITEM").toString());
			intWaterAdultW = Integer.parseInt(priceInfo.get("ADULTS_PRICE").toString());
			intWaterChildW = Integer.parseInt(priceInfo.get("CHILD_PRICE").toString());
		}
		
		if(!"".equals(getReserveInfo.get("COMPLEX_ITEM")) && getReserveInfo.get("COMPLEX_ITEM") != null){
			Map priceInfo = frontReservationService.getItemInfo(getReserveInfo.get("COMPLEX_ITEM").toString());
			intComplexAdultW = Integer.parseInt(priceInfo.get("ADULTS_PRICE").toString());
			intComplexChildW = Integer.parseInt(priceInfo.get("CHILD_PRICE").toString());
		}		
		
		if(!"".equals(getReserveInfo.get("EVENT1_ITEM")) && getReserveInfo.get("EVENT1_ITEM") != null){
			Map priceInfo = frontReservationService.getItemInfo(getReserveInfo.get("EVENT1_ITEM").toString());
			event1W = Integer.parseInt(priceInfo.get("EVENT_PRICE").toString());
		}
		if(!"".equals(getReserveInfo.get("EVENT2_ITEM")) && getReserveInfo.get("EVENT2_ITEM") != null){
			Map priceInfo = frontReservationService.getItemInfo(getReserveInfo.get("EVENT2_ITEM").toString());
			event2W = Integer.parseInt(priceInfo.get("EVENT_PRICE").toString());
		}
		if(!"".equals(getReserveInfo.get("EVENT3_ITEM")) && getReserveInfo.get("EVENT3_ITEM") != null){
			Map priceInfo = frontReservationService.getItemInfo(getReserveInfo.get("EVENT3_ITEM").toString());
			event3W = Integer.parseInt(priceInfo.get("EVENT_PRICE").toString());
		}
		if(!"".equals(getReserveInfo.get("RENTAL1_ITEM")) && getReserveInfo.get("RENTAL1_ITEM") != null){
			Map priceInfo = frontReservationService.getItemInfo(getReserveInfo.get("RENTAL1_ITEM").toString());
			rental1W = Integer.parseInt(priceInfo.get("EVENT_PRICE").toString());
		}
		if(!"".equals(getReserveInfo.get("RENTAL2_ITEM")) && getReserveInfo.get("RENTAL2_ITEM") != null){
			Map priceInfo = frontReservationService.getItemInfo(getReserveInfo.get("RENTAL2_ITEM").toString());
			rental2W = Integer.parseInt(priceInfo.get("EVENT_PRICE").toString());
		}
		if(!"".equals(getReserveInfo.get("RENTAL3_ITEM")) && getReserveInfo.get("RENTAL3_ITEM") != null){
			Map priceInfo = frontReservationService.getItemInfo(getReserveInfo.get("RENTAL3_ITEM").toString());
			rental3W = Integer.parseInt(priceInfo.get("EVENT_PRICE").toString());
		}
		
		int cancelAmount = 0;
		int cancelGubun = 0;
		String nowDate =AquaDateUtil.getToday();
		String reserveDate = getReserveInfo.get("COMPARE_DAY").toString();
		if(reserveDate.compareTo(nowDate) > 0){
			cancelAmount = Integer.parseInt(getReserveInfo.get("PAYMENT_PRICE").toString());
		}else if(reserveDate.compareTo(nowDate) == 0){
			Double doubleAmount = Double.parseDouble(getReserveInfo.get("PAYMENT_PRICE").toString()) * 0.9;
			cancelAmount = doubleAmount.intValue();
			cancelGubun = 3;
		}		

		getReserveInfo.put("intSpaAdultW", intSpaAdultW);
		getReserveInfo.put("intSpaChildW", intSpaChildW);
		getReserveInfo.put("intWaterAdultW", intWaterAdultW);
		getReserveInfo.put("intWaterChildW", intWaterChildW);
		getReserveInfo.put("intComplexAdultW", intComplexAdultW);
		getReserveInfo.put("intComplexChildW", intComplexChildW);		
		getReserveInfo.put("event1W", event1W);
		getReserveInfo.put("event2W", event2W);
		getReserveInfo.put("event3W", event3W);
		getReserveInfo.put("rental1W", rental1W);
		getReserveInfo.put("rental2W", rental2W);
		getReserveInfo.put("rental3W", rental3W);
		getReserveInfo.put("cancelAmount", cancelAmount);
		getReserveInfo.put("cancelGubun", cancelGubun);	
		getReserveInfo.put("penalty", Integer.parseInt(getReserveInfo.get("PAYMENT_PRICE").toString()) - cancelAmount);	
		
		Map pgResultInfo = frontReservationService.pgResultInfo(getReserveInfo.get("PG_RESULT").toString());
		
		int compareVal = Integer.parseInt(pgResultInfo.get("TR_NO").toString().substring(0,1));
	
		String r_TYPE = "";		
		String authty = "";
		switch (compareVal) {
		case 1: r_TYPE = "移대뱶";authty = "1010";
			break;
		case 2: r_TYPE = "�떎�떆媛꾧퀎醫뚯씠泥�";
			//�떦�씪 �뿬遺� 泥댄겕
			Date toDay = new Date();
			SimpleDateFormat transFormat = new SimpleDateFormat("yyyy.MM.dd");
			String toDayToString = transFormat.format(toDay).trim();
				
			String pmtDate = getReserveInfo.get("PAYMENT_DATE").toString();
			DateFormat pmtDateTransFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
			Date date;
			
			String newPaymentDate = "";

			try {
				date = pmtDateTransFormat.parse(pmtDate);
				newPaymentDate = transFormat.format(date).trim();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(toDayToString.equals(newPaymentDate)){
				authty = "2010";
			}else{
				authty = "2030";
			}
			logger.debug("############## �떎�떆媛꾧퀎醫뚯씠泥� 痍⑥냼 肄붾뱶媛�>> authty : " + authty);
			break;
		case 4: r_TYPE = "SSG PAY";authty = "4110";
			break;				
		}		
	
		pgResultInfo.put("r_TYPE", r_TYPE);
		pgResultInfo.put("authty", authty);
		pgResultInfo.put("pgStore", config.getProperty("pg.store.code"));

		Map memberInfo = (Map) session.getAttribute("MEM_INFO");
		
		Map userInfo = new HashMap();
		userInfo.put("NAME", memberInfo.get("MEM_NM"));
		userInfo.put("MOBLIE", memberInfo.get("MOBILE_NUM"));
		userInfo.put("MAIL", memberInfo.get("MEM_ID"));
		userInfo.put("RESERVEUID", getReserveInfo.get("RESERVE_UID"));
		userInfo.put("RESERVEDAY", getReserveInfo.get("RESERVE_DATE"));	
		
		//1踰� �뜑 SMS 臾몄옄諛쒖넚 �뿬遺� 泥댄겕
		Map smsCnt = new HashMap();
		smsCnt.put("point_code", "POINT01");
		smsCnt.put("custom_mobile", memberInfo.get("MOBILE_NUM"));
		smsCnt.put("reserve_num", getReserveInfo.get("ORDER_NUM"));	
		int sendSmsCnt = commonService.sendSmsCnt(smsCnt);
		
		model.addAttribute("userInfo", userInfo);		
		model.addAttribute("reserveInfo", getReserveInfo);
		model.addAttribute("pgResultInfo", pgResultInfo);
		model.addAttribute("sendSmsCnt", sendSmsCnt);		
		return "/front/mypage/reservation_view";
	}
	
	@RequestMapping(value = "/mypage/sendSms.af")
	public String sendSms(@RequestParam Map param, Model model, HttpSession session, HttpServletResponse response, HttpServletRequest request) throws Exception {
		
		Map memberInfo = (Map) session.getAttribute("MEM_INFO");
		String html ="�삁�빟臾몄옄媛� 諛쒖넚�릺�뿀�뒿�땲�떎.";
		//�삁�빟臾몄옄 諛쒖넚
		String reserveDay = param.get("reserveday").toString();
		reserveDay = reserveDay.substring(0, 4)+"."+reserveDay.substring(4, 6)+"."+reserveDay.substring(6, 8);
		//String contents = "[�븘荑좎븘�븘�뱶]�삩�씪�씤 �삁�빟(�삁�빟踰덊샇:"+param.get("ordernum")+",�삁�빟�씪:"+reserveDay+")";
		Map smsParam = new HashMap();
		smsParam.put("point_code", "POINT01");
		smsParam.put("sms_type", "RESERVE");
		
		Map smsTemplte = commonService.getSmsTemplete(smsParam);
		String contents = smsTemplte.get("SMS_CONTENT").toString();
		//contents = contents.replace("{吏��젏}",param.get("pointNm").toString());//�삁�빟踰덊샇 移섑솚
		
		contents = contents.replace("{吏��젏}","");//20190116 syw: 留덉씠�럹�씠吏� sms�쟾�넚�떆 �븳湲� 源⑥쭚. 
		contents = contents.replace("{踰덊샇}",param.get("ordernum").toString());//�삁�빟踰덊샇 移섑솚
		contents = contents.replace("{�삁�빟�씪}",reserveDay);//�삁�빟�씪 移섑솚
		
		Map parameters = new HashMap();
		//parameters.put("recipient_num", param.get("phoneNo")); // �닔�떊踰덊샇
		parameters.put("recipient_num", memberInfo.get("MOBILE_NUM").toString()); // �닔�떊踰덊샇
		
		
		parameters.put("subject", "");//20190116 syw: 留덉씠�럹�씠吏� sms�쟾�넚�떆 �븳湲� 源⑥쭚.
		parameters.put("content", contents); // �궡�슜 (SMS=88Byte, LMS=2000Byte)		
		parameters.put("callback", "031-8072-8800"); //20190116 syw:  �봽濡쒗띁�떚�뿉�꽌 遺덈윭�삤吏� 紐삵븯�뿬 �삤瑜�
		
		if(!smsService.sendSms(parameters)){
			/*html = "alert('泥섎━以� �뿉�윭媛� 諛쒖깮�븯���뒿�땲�떎.');";*/
			html = "泥섎━以� �뿉�윭媛� 諛쒖깮�븯���뒿�땲�떎.";
		}else{
		
			//SMS 諛쒖넚 �씠�젰 �벑濡�
			smsParam.put("sms_uid", smsTemplte.get("SMS_UID"));
			smsParam.put("mem_id", memberInfo.get("MEM_ID").toString());
			smsParam.put("custom_nm", memberInfo.get("MEM_NM").toString());
			smsParam.put("custom_mobile",  memberInfo.get("MOBILE_NUM"));
			smsParam.put("ins_ip", request.getRemoteAddr());
			smsParam.put("ins_id", memberInfo.get("MEM_ID").toString()); 
			smsParam.put("send_status", "OK");	
			smsParam.put("bigo", param.get("ordernum").toString());	
			
			String smsResult = commonService.insSmsSend(smsParam);
			if("ERROR".equals(smsResult)){
				html = "泥섎━以� �뿉�윭媛� 諛쒖깮�븯���뒿�땲�떎.(臾몄옄�씠�젰�벑濡�)";
			}			
		}
		Util.htmlPrint(html, response);		
		
		return null;
		
	}
	
	@RequestMapping(value = "/mypage/leave.af")
	public String leave(@RequestParam Map param, Model model, HttpSession session) {

		String mem_uid = param.get("mem_uid").toString();
		model.addAttribute("mem_uid", mem_uid);
		return "/front/mypage/leave";
	}
	
	@RequestMapping(value = "/mypage/leave_complete.af")
	public String leaveComplete(@RequestParam Map param, Model model, HttpSession session) {

		/*String mem_uid = param.get("mem_uid").toString();*/
		
		Map memberInfo = (Map) session.getAttribute("MEM_INFO");
		String mem_uid = memberInfo.get("MEM_UID").toString();
		
		String result = frontMemberService.memberDel(mem_uid);
		String pointUrl = session.getAttribute("POINT_URL").toString();
		
		if("DELOK".equals(result)){
			Map pointInfo = getPointInfo(pointUrl);
			session.invalidate();
			
			//session.setAttribute("POINT_CODE", pointInfo.get("CODE_ID"));
			//session.setAttribute("POINT_URL", pointInfo.get("CODE_URL"));
		}
		
		model.addAttribute("result", result);		
		return "/front/mypage/leave_complete";
	}
	
	@RequestMapping(value = "/mypage/pw_change.af")
	public String pw_change(@RequestParam Map param, Model model, HttpSession session) {

		Map sessionMember = (Map) session.getAttribute("MEM_INFO");
		Map parameter = new HashMap();
		parameter.put("mem_uid", sessionMember.get("MEM_UID"));
		Map memberInfo = frontMemberService.memberUpdInfo(parameter);		
		
		model.addAttribute("memberInfo", memberInfo);					
		return "/front/mypage/pw_change";
	}

}
