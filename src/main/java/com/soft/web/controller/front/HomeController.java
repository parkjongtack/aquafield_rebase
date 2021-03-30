package com.soft.web.controller.front;

import com.soft.web.base.GenericController;
import com.soft.web.mail.MailService;
import com.soft.web.service.admin.AdminTermsService;
import com.soft.web.service.common.CommonService;
import com.soft.web.service.front.FrontMemberService;
import com.soft.web.service.front.FrontOneToOneService;
import com.soft.web.util.Util;
import java.io.IOException;
import java.math.BigInteger;
import java.net.InetAddress;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController extends GenericController
{
  protected Logger logger = LoggerFactory.getLogger(HomeController.class);

  @Autowired
  FrontMemberService frontMemberService;

  @Autowired
  AdminTermsService adminTermsService;

  @Autowired
  CommonService commonService;

  @Autowired
  FrontOneToOneService frontOneToOneService;

  @Autowired
  private MailService mailService;

  @Autowired
  private JavaMailSender mailSender;

  @Resource(name="config")
  private Properties config;
  private static String RSA_WEB_KEY = "_RSA_WEB_Key_";
  private static String RSA_INSTANCE = "RSA";

  @RequestMapping({"/{point}/index.af"})
  public String mainIndexPoint(@PathVariable String point, @RequestParam Map param, Model model, HttpSession session) {
    Map pointInfo = super.getPointInfo(point);
    session.setAttribute("POINT_CODE", pointInfo.get("CODE_ID"));
    session.setAttribute("POINT_URL", pointInfo.get("CODE_URL"));
    return "/front/index";
  }
  @RequestMapping({"/main.af"})
  public String main(@RequestParam Map param, Model model, HttpSession session) {
    List pouintList = super.getCommonCodes("POINT_CODE");
    model.addAttribute("pouintList", pouintList);
    return "/front/main";
  }
  @RequestMapping({"/m_main_cont.af"})
  public String m_main_cont(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request) { return "/front/m_main_content"; } 
  @RequestMapping({"/member/logout.af"})
  public String logout(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request) {
    checkURL(request, session);
    String pointUrl = session.getAttribute("POINT_URL").toString();

    //session.invalidate();
    session.removeAttribute("MEM_INFO");
    return "redirect:" + pointUrl;
  }

  @RequestMapping({"/member/login.af"})
  public String login(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request) {
    initRsa(request);

    return "/front/member/login";
  }
  @RequestMapping({"/member/loginproccess.af"})
  public String loginproccess(@RequestParam Map param, Model model, HttpSession session, HttpServletResponse response, HttpServletRequest request) throws IOException { String html = "";
    Map parameter = new HashMap();
    parameter.put("point_code", param.get("point"));
    parameter.put("mem_id", (String)param.get("user_id"));
    parameter.put("loginDate", new Date());
    int idChk = this.frontMemberService.idChk(parameter);

    if (idChk != 0) {
      parameter.put("mem_pw", (String)param.get("user_pwd"));
      Map memberInfo = this.frontMemberService.memberInfo(parameter);

      if (memberInfo == null)
      {
        String message = "濡쒓렇�씤 �떎�뙣 �뻽�뒿�땲�떎. \\n�븘�씠�뵒/鍮꾨�踰덊샇瑜� �떎�떆 �솗�씤�븯�떗�떆�삤. \\n鍮꾨�踰덊샇�뒗 ��.�냼臾몄옄瑜� 援щ텇�빀�땲�떎.";
        String action = "document.loginForm.user_pwd.value = '';document.loginForm.user_pwd.focus();";
        html = Util.alertToAction(message, action);
      }
      else {
        Map inactMemInfo = this.frontMemberService.inactMemInfo(parameter);
        if (inactMemInfo != null) {
          String message = "�쁽�옱 �쉶�썝�떂�� �쑕硫닿퀎�젙�긽�깭�엯�땲�떎.\\n�쑕硫닿퀎�젙�빐�젣瑜� �빐二쇱꽭�슂.";
          String url = "memberFn.inactivityMemberCer({data : {num : '" + inactMemInfo.get("MEM_UID") + "'}})";
          html = "{\"result\":\"INACTIVITY\",\"num\":\"" + inactMemInfo.get("MEM_UID") + "\",\"message\":\"�쁽�옱 �쉶�썝�떂�� �쑕硫닿퀎�젙�긽�깭�엯�땲�떎.\\n�쑕硫닿퀎�젙�빐�젣瑜� �빐二쇱꽭�슂.\"}";
        }
        else
        {
          InetAddress ip = InetAddress.getLocalHost();
          String userIp = ip.getHostAddress();
          memberInfo.put("MEM_IP", userIp);
          session.removeAttribute("MEM_INFO");
          session.removeAttribute("RS_DATA");
          session.setAttribute("MEM_INFO", memberInfo);

          int memUid = Util.getInt(memberInfo.get("MEM_UID").toString());

          String lastLoginUpd = this.frontMemberService.setLastloginDate(parameter);

          if ("ERROR".equals(lastLoginUpd)) {
            String message = "�뿉�윭媛� 諛쒖깮�뻽�뒿�땲�떎.\\n�옞�떆 �썑�뿉 �떎�떆 �빐二쇱꽭�슂.";
            String action = "document.loginForm.user_id.value = '';document.loginForm.user_pwd.value = '';";
            action = action + "document.loginForm.user_id.focus();";
            html = Util.alertToAction(message, action);
          } else {
            checkURL(request, session);
            String pointUrl = session.getAttribute("POINT_URL").toString();
            String url = pointUrl;
            html = Util.goUrl(url);
          }

        }

      }

    }
    else
    {
      String message = "�씪移섑븯�뒗 �쉶�썝�젙蹂닿� �뾾�뒿�땲�떎.\\n�쉶�썝媛��엯�쓣 �빐二쇱꽭�슂.";
      String action = "document.loginForm.user_id.value = '';document.loginForm.user_pwd.value = '';";
      action = action + "document.loginForm.user_id.focus();";
      html = Util.alertToAction(message, action);
    }
    Util.htmlPrint(html, response);

    return null;
  }

  public void initRsa(HttpServletRequest request)
  {
    HttpSession session = request.getSession();
    try
    {
      KeyPairGenerator generator = KeyPairGenerator.getInstance(RSA_INSTANCE);
      generator.initialize(1024);

      KeyPair keyPair = generator.genKeyPair();
      KeyFactory keyFactory = KeyFactory.getInstance(RSA_INSTANCE);
      PublicKey publicKey = keyPair.getPublic();
      PrivateKey privateKey = keyPair.getPrivate();

      session.setAttribute(RSA_WEB_KEY, privateKey);

      RSAPublicKeySpec publicSpec = (RSAPublicKeySpec)keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
      String publicKeyModulus = publicSpec.getModulus().toString(16);
      String publicKeyExponent = publicSpec.getPublicExponent().toString(16);

      request.setAttribute("RSAModulus", publicKeyModulus);
      request.setAttribute("RSAExponent", publicKeyExponent);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  @RequestMapping({"/member/ReSet.af"})
  public String myinfoUpd2(@RequestParam Map param, Model model, HttpSession session, HttpServletResponse response) throws IOException
  {
    String html = "";
    Map parameter = new HashMap();
    parameter.put("point_code", param.get("point"));
    parameter.put("mem_id", param.get("id"));
    parameter.put("mem_pw", (String)param.get("userPW"));
    Map memInfo = this.frontMemberService.memberInfo(parameter);

    if (memInfo != null)
    {
      if (memInfo.get("MEM_PW").toString().equals(param.get("PWD1"))) {
        html = "alert('湲곗〈鍮꾨�踰덊샇�� �룞�씪�빀�땲�떎.\\n �떎�떆 �엯�젰�빐 二쇱떆湲� 諛붾엻�땲�떎.');";
        html = html + "$('#userPW').val('');$('#PWD1').val('');$('#PWD2').val('');$('#userPW').focus()";
      } else {
        parameter.put("mem_uid", param.get("uid"));
        parameter.put("mem_pw", param.get("PWD1"));
        parameter.put("upd_id", param.get("id"));

        String result = this.frontMemberService.setMemPwUpd(parameter);

        if ("SETOK".equals(result)) {
          html = "alert('�닔�젙�릺�뿀�뒿�땲�떎.');";
          html = html + "$('#pop_member_login_signup .btn_close').trigger('click')";
        } else {
          html = "alert('泥섎━以� �뿉�윭媛� 諛쒖깮�븯���뒿�땲�떎.\\n�떎�떆 �떆�룄�빐 二쇱떆湲곕컮�엻�땲�떎.');";
          html = html + "$('#userPW').val('');$('#PWD1').val('');$('#PWD2').val('');$('#userPW').focus()";
        }
      }
    } else {
      html = "alert('�쁽�옱鍮꾨�踰덊샇媛� ��由쎈땲�떎.\\n�떎�떆 �떆�룄�빐 二쇱떆湲곕컮�엻�땲�떎.');";
      html = html + "$('#userPW').val('');$('#PWD1').val('');$('#PWD2').val('');$('#userPW').focus()";
    }
    Util.htmlPrint(html, response);

    return null;
  }

  @RequestMapping({"/member/ReSet90.af"})
  public String ReSet90(@RequestParam Map param, Model model, HttpSession session, HttpServletResponse response) throws IOException
  {
    String html = "";
    Map parameter = new HashMap();
    parameter.put("mem_uid", param.get("uid"));
    parameter.put("upd_id", param.get("id"));

    String result = this.frontMemberService.setMemPwUpd(parameter);

    if ("SETOK".equals(result)) {
      html = "alert('泥섎━�릺�뿀�뒿�땲�떎.');";
      html = html + "$('#pop_member_login_signup .btn_close').trigger('click')";
    } else {
      html = "alert('泥섎━以� �뿉�윭媛� 諛쒖깮�븯���뒿�땲�떎.\\n�떎�떆 �떆�룄�빐 二쇱떆湲곕컮�엻�땲�떎.');";
    }

    Util.htmlPrint(html, response);

    return null;
  }
  @RequestMapping({"/useridCheck.af"})
  public String useridCheck(@RequestParam Map param, Model model, HttpSession session, HttpServletResponse response) throws Exception {
    String html = "";
    if ((param.get("user_id") != null) && (!"".equals(param.get("user_id")))) {
      Map parameter = new HashMap();
      parameter.put("point_code", "POINT01");
      parameter.put("mem_id", (String)param.get("user_id"));

      int idChk = this.frontMemberService.idChk(parameter);
      if (idChk > 0)
        html = "DUPLITE";
    }
    else {
      html = "NODATA";
    }
    Util.htmlPrint(html, response);

    return null;
  }
  @RequestMapping({"/member/threeMonthsReset.af"})
  public String threeMonthsReset(@RequestParam Map param, Model model, HttpSession session) {
    String pointUrl = session.getAttribute("POINT_URL").toString();
    model.addAttribute("pointUrl", pointUrl);
    model.addAttribute("param", param);
    return "/front/member/3months_reset_pwd";
  }
  @RequestMapping({"/about/index.af"})
  public String aboutIndex(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request) { checkURL(request, session);
    return "/front/" + session.getAttribute("POINT_CODE").toString() + "/about/index"; } 
  @RequestMapping({"/about/concept/index.af"})
  public String conceptIndex(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request) {
    checkURL(request, session);
    return "/front/" + session.getAttribute("POINT_CODE").toString() + "/about/concept/index";
  }
  @RequestMapping({"/about/concept/concept.af"})
  public String concept(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request) { checkURL(request, session);
    return "/front/" + session.getAttribute("POINT_CODE").toString() + "/about/concept/concept"; } 
  @RequestMapping({"/about/concept/videoPop.af"})
  public String videoPop(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request) {
    checkURL(request, session);
    return "/front/" + session.getAttribute("POINT_CODE").toString() + "/about/concept/video_popup";
  }
  @RequestMapping({"/about/concept/video01.af"})
  public String video01(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request) { checkURL(request, session);
    return "/front/" + session.getAttribute("POINT_CODE").toString() + "/about/concept/video1"; } 
  @RequestMapping({"/about/location/index.af"})
  public String locationIndex(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request) {
    checkURL(request, session);
    return "/front/" + session.getAttribute("POINT_CODE").toString() + "/about/location/index";
  }
  @RequestMapping({"/about/location/location.af"})
  public String location(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request) { checkURL(request, session);
    return "/front/" + session.getAttribute("POINT_CODE").toString() + "/about/location/location"; } 
  @RequestMapping({"/facilities/index.af"})
  public String facilitiesIndex(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request) {
    checkURL(request, session);
    return "/front/" + session.getAttribute("POINT_CODE").toString() + "/facilities/index";
  }
  @RequestMapping({"/facilities/popup.af"})
  public String facilitiePopup(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request) { checkURL(request, session);
    return "/front/" + session.getAttribute("POINT_CODE").toString() + "/facilities/popup"; } 
  @RequestMapping({"/guide/index.af"})
  public String index(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request) {
    checkURL(request, session);
    model.addAttribute("page", param.get("page"));
    return "/front/" + session.getAttribute("POINT_CODE").toString() + "/guide/page/index";
  }
  @RequestMapping({"/guide/page1.af"})
  public String page1(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request) { checkURL(request, session);
    return "/front/" + session.getAttribute("POINT_CODE").toString() + "/guide/page/1"; } 
  @RequestMapping({"/guide/page2.af"})
  public String page2(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request) {
    checkURL(request, session);
    return "/front/" + session.getAttribute("POINT_CODE").toString() + "/guide/page/2";
  }
  @RequestMapping({"/guide/page3.af"})
  public String page3(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request) { checkURL(request, session);
    return "/front/" + session.getAttribute("POINT_CODE").toString() + "/guide/page/3"; } 
  @RequestMapping({"/guide/page4.af"})
  public String page4(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request) {
    checkURL(request, session);
    param.put("point_code", "POINT01");

    return "/front/" + session.getAttribute("POINT_CODE").toString() + "/guide/page/4";
  }
  @RequestMapping({"/guide/page5.af"})
  public String page5(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request) { checkURL(request, session);
    return "/front/" + session.getAttribute("POINT_CODE").toString() + "/guide/page/5"; } 
  @RequestMapping({"/guide/step/index.af"})
  public String step(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request) {
    checkURL(request, session);
    model.addAttribute("page", param.get("page"));
    return "/front/" + session.getAttribute("POINT_CODE").toString() + "/guide/step/index";
  }
  @RequestMapping({"/guide/step/step1.af"})
  public String step1(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request) { checkURL(request, session);
    return "/front/" + session.getAttribute("POINT_CODE").toString() + "/guide/step/1"; } 
  @RequestMapping({"/guide/step/step2.af"})
  public String step2(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request) {
    checkURL(request, session);
    return "/front/" + session.getAttribute("POINT_CODE").toString() + "/guide/step/2";
  }
  @RequestMapping({"/guide/step/step3.af"})
  public String step3(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request) { checkURL(request, session);
    return "/front/" + session.getAttribute("POINT_CODE").toString() + "/guide/step/3"; } 
  @RequestMapping({"/guide/step/step4.af"})
  public String step4(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request) {
    checkURL(request, session);
    return "/front/" + session.getAttribute("POINT_CODE").toString() + "/guide/step/4";
  }
  @RequestMapping({"/guide/step/step5.af"})
  public String step5(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request) { checkURL(request, session);
    return "/front/" + session.getAttribute("POINT_CODE").toString() + "/guide/step/5"; } 
  @RequestMapping({"/guide/step/step6.af"})
  public String step6(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request) {
    checkURL(request, session);
    return "/front/" + session.getAttribute("POINT_CODE").toString() + "/guide/step/6";
  }
  @RequestMapping({"/guide/step/step7.af"})
  public String step7(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request) { checkURL(request, session);
    return "/front/guide/step/7"; } 
  @RequestMapping({"/guide/step/step8.af"})
  public String step8(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request) {
    checkURL(request, session);
    return "/front/" + session.getAttribute("POINT_CODE").toString() + "/guide/step/8";
  }
  @RequestMapping({"/guide/step/step9.af"})
  public String step9(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request) { checkURL(request, session);
    return "/front/" + session.getAttribute("POINT_CODE").toString() + "/guide/step/9"; } 
  @RequestMapping({"/guide/step/step10.af"})
  public String step10(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request) {
    checkURL(request, session);
    return "/front/" + session.getAttribute("POINT_CODE").toString() + "/guide/step/10";
  }
  @RequestMapping({"/guide/step/step11.af"})
  public String step11(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request) { checkURL(request, session);
    return "/front/" + session.getAttribute("POINT_CODE").toString() + "/guide/step/11"; } 
  @RequestMapping({"/guide/step/step12.af"})
  public String step12(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request) {
    checkURL(request, session);
    return "/front/" + session.getAttribute("POINT_CODE").toString() + "/guide/step/12";
  }
  @RequestMapping({"/guide/step/step13.af"})
  public String step13(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request) { checkURL(request, session);
    return "/front/" + session.getAttribute("POINT_CODE").toString() + "/guide/step/13"; } 
  @RequestMapping({"/guide/step/step14.af"})
  public String step14(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request) {
    checkURL(request, session);
    return "/front/" + session.getAttribute("POINT_CODE").toString() + "/guide/step/14";
  }
  @RequestMapping({"/guide/step/step15.af"})
  public String step15(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request) { checkURL(request, session);
    return "/front/" + session.getAttribute("POINT_CODE").toString() + "/guide/step/15"; } 
  @RequestMapping({"/guide/step/step16.af"})
  public String step16(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request) {
    checkURL(request, session);
    return "/front/" + session.getAttribute("POINT_CODE").toString() + "/guide/step/16";
  }
  @RequestMapping({"/service/index.af"})
  public String serviceIndex(@RequestParam Map param, Model model, HttpSession session) {
    return "/front/service/index";
  }
  @RequestMapping({"/service/email.af"})
  public String email(@RequestParam Map param, Model model, HttpSession session) {
    return "/front/service/email";
  }
  @RequestMapping({"/service/guide.af"})
  public String guide(@RequestParam Map param, Model model, HttpSession session) {
    return "/front/service/guide";
  }
  @RequestMapping({"/service/privacy.af"})
  public String privacy(@RequestParam Map param, Model model, HttpSession session) {
    String upload = "/common/upload/terms";
    String realPath = "";
    realPath = super.getSession().getServletContext().getRealPath(upload);
    Util.makeDir(realPath);

    param.put("tt", "PRIVACY");
    List results = this.adminTermsService.adminTermsList(param);
    Map view = null;
    if ((param.get("num") == null) || ("".equals(param.get("num").toString()))) {
      Iterator localIterator = results.iterator(); if (localIterator.hasNext()) { Map result = (Map)localIterator.next();
        view = result;
      }

      if (view != null) model.addAttribute("resultContent", Util.getReadFile(realPath + "/" + view.get("TERMS_UID")));
    }
    else
    {
      view = this.adminTermsService.adminTermsDetail(param);
      if (view != null) model.addAttribute("resultContent", Util.getReadFile(realPath + "/" + view.get("TERMS_UID")));
    }

    model.addAttribute("resultParam", param);
    model.addAttribute("result", view);
    model.addAttribute("results", results);

    return "/front/service/privacy";
  }
  @RequestMapping({"/service/terms.af"})
  public String terms(@RequestParam Map param, Model model, HttpSession session) {
    String upload = "/common/upload/terms";
    String realPath = "";
    realPath = super.getSession().getServletContext().getRealPath(upload);
    Util.makeDir(realPath);

    param.put("tt", "HOMEPAGE_USE");
    List results = this.adminTermsService.adminTermsList(param);
    Map view = null;
    if ((param.get("num") == null) || ("".equals(param.get("num").toString()))) {
      Iterator localIterator = results.iterator(); if (localIterator.hasNext()) { Map result = (Map)localIterator.next();
        view = result;
      }

      if (view != null) model.addAttribute("resultContent", Util.getReadFile(realPath + "/" + view.get("TERMS_UID")));
    }
    else
    {
      view = this.adminTermsService.adminTermsDetail(param);
      if (view != null) model.addAttribute("resultContent", Util.getReadFile(realPath + "/" + view.get("TERMS_UID")));
    }

    model.addAttribute("resultParam", param);
    model.addAttribute("result", view);
    model.addAttribute("results", results);

    return "/front/service/terms";
  }
  @RequestMapping({"/service/terms2.af"})
  public String terms2(@RequestParam Map param, Model model, HttpSession session) {
    String upload = "/common/upload/terms";
    String realPath = "";
    realPath = super.getSession().getServletContext().getRealPath(upload);
    Util.makeDir(realPath);

    param.put("tt", "AQUA_USE");
    List results = this.adminTermsService.adminTermsList(param);
    Map view = null;
    if ((param.get("num") == null) || ("".equals(param.get("num").toString()))) {
      Iterator localIterator = results.iterator(); if (localIterator.hasNext()) { Map result = (Map)localIterator.next();
        view = result;
      }

      if (view != null) model.addAttribute("resultContent", Util.getReadFile(realPath + "/" + view.get("TERMS_UID")));
    }
    else
    {
      view = this.adminTermsService.adminTermsDetail(param);
      if (view != null) model.addAttribute("resultContent", Util.getReadFile(realPath + "/" + view.get("TERMS_UID")));
    }

    model.addAttribute("resultParam", param);
    model.addAttribute("result", view);
    model.addAttribute("results", results);

    return "/front/service/terms2";
  }
  @RequestMapping({"/service/communication.af"})
  public String communication(@RequestParam Map param, Model model, HttpSession session) {
    String upload = "/common/upload/terms";
    String realPath = "";
    realPath = super.getSession().getServletContext().getRealPath(upload);
    Util.makeDir(realPath);

    param.put("tt", "communication");
    List results = this.adminTermsService.adminTermsList(param);
    Map view = null;
    if ((param.get("num") == null) || ("".equals(param.get("num").toString()))) {
      Iterator localIterator = results.iterator(); if (localIterator.hasNext()) { Map result = (Map)localIterator.next();
        view = result;
      }

      if (view != null) model.addAttribute("resultContent", Util.getReadFile(realPath + "/" + view.get("TERMS_UID")));
    }
    else
    {
      view = this.adminTermsService.adminTermsDetail(param);
      if (view != null) model.addAttribute("resultContent", Util.getReadFile(realPath + "/" + view.get("TERMS_UID")));
    }

    model.addAttribute("resultParam", param);
    model.addAttribute("result", view);
    model.addAttribute("results", results);

    return "/front/service/communication";
  }
  @RequestMapping({"/service/video.af"})
  public String video(@RequestParam Map param, Model model, HttpSession session) {
    String upload = "/common/upload/terms";
    String realPath = "";
    realPath = super.getSession().getServletContext().getRealPath(upload);
    Util.makeDir(realPath);

    param.put("tt", "GENERAL");
    List results = this.adminTermsService.adminTermsList(param);
    Map view = null;
    if ((param.get("num") == null) || ("".equals(param.get("num").toString()))) {
      Iterator localIterator = results.iterator(); if (localIterator.hasNext()) { Map result = (Map)localIterator.next();
        view = result;
      }

      if (view != null) model.addAttribute("resultContent", Util.getReadFile(realPath + "/" + view.get("TERMS_UID")));
    }
    else
    {
      view = this.adminTermsService.adminTermsDetail(param);
      if (view != null) model.addAttribute("resultContent", Util.getReadFile(realPath + "/" + view.get("TERMS_UID")));
    }

    model.addAttribute("resultParam", param);
    model.addAttribute("result", view);
    model.addAttribute("results", results);

    return "/front/service/video";
  }
  @RequestMapping({"/service/terms_view.af"})
  public void termsView(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
    String upload = "/common/upload/terms";
    String realPath = "";
    realPath = super.getSession().getServletContext().getRealPath(upload);
    Util.makeDir(realPath);

    if (!request.getParameter("num").contains("../"))
    {
      if ((param.get("num") != null) && (!"".equals(param.get("num").toString())))
        try {
          Util.htmlPrint(Util.getReadFile(realPath + "/" + param.get("num")), response);
        } catch (IOException e) {
          e.printStackTrace();
        }
    }
  }

  @RequestMapping({"/service/popup.af"})
  public String servicePopup(@RequestParam Map param, Model model, HttpSession session)
  {
    return "/front/service/popup";
  }
  @RequestMapping({"/service/customer.af"})
  public String customerWrite(@RequestParam Map param, Model model, HttpSession session) {
    Map memberInfo = (Map)session.getAttribute("MEM_INFO");

    if (memberInfo == null) {
      memberInfo = new HashMap();
      memberInfo.put("MEM_UID", "");
      memberInfo.put("MEM_NM", "");
      memberInfo.put("MOBILE_NUM", "");
      memberInfo.put("MEM_ID", "");
    }

    List typeList = super.getCommonCodes("ASK_TYPE");
    List pointList = super.getCommonCodes("POINT_CODE");
    model.addAttribute("typeList", typeList);
    model.addAttribute("pointList", pointList);
    model.addAttribute("MEMBERINFO", memberInfo);
    return "/front/service/customer";
  }
  @RequestMapping({"/service/member.af"})
  public String memberWrite(@RequestParam Map param, Model model, HttpSession session) {
    Map memberInfo = (Map)session.getAttribute("MEM_INFO");

    if (memberInfo == null) {
      memberInfo = new HashMap();
      memberInfo.put("MEM_UID", "");
      memberInfo.put("MEM_NM", "");
      memberInfo.put("MOBILE_NUM", "");
      memberInfo.put("MEM_ID", "");
    }

    List typeList = super.getCommonCodes("ASK_TYPE");
    List pointList = super.getCommonCodes("POINT_CODE");
    model.addAttribute("typeList", typeList);
    model.addAttribute("pointList", pointList);
    model.addAttribute("MEMBERINFO", memberInfo);
    return "/front/service/member";
  }
  @RequestMapping({"/ajaxCsIns.af"})
  public String ajaxCsIns(@RequestParam Map param, Model model, HttpSession session, HttpServletResponse response) throws IOException, MessagingException {
    String html = "";
    String strContent = (String)param.get("content");
    strContent = strContent.replaceAll("\r\n", "<br/>");
    InetAddress ip = InetAddress.getLocalHost();
    String userIp = ip.getHostAddress();

    Map memberInfo = (Map)session.getAttribute("MEM_INFO");
    param.put("point_code", param.get("pointCode"));
    param.put("writer", param.get("userName"));
    param.put("ask_title", param.get("title"));
    param.put("ask_content", strContent);
    param.put("ins_ip", userIp);
    param.put("ins_id", param.get("email"));
    param.put("ins_uid", param.get("uid"));
    param.put("mobile_num", param.get("phoneNum"));
    param.put("ask_type", param.get("askType"));
    param.put("mem_type", param.get("type"));

    String result = this.frontOneToOneService.oneToOneIns(param);

    if ("INSOK".equals(result)) {
      html = html + "alert('등록되었습니다..');";
      html = html + "popFn.hide($('#pop_service'));";

      String reHtml = param.get("userName").toString() + "(" + param.get("email") + ") 怨좉컼�떂�씠 怨좉컼�쓽�냼由щ�� �벑濡앺븯�뀲�뒿�땲�떎.";

      boolean booleanresult = this.mailService.sendmail(this.mailSender, "aquafield@shinsegae.com", "�븘荑좎븘�븘�뱶 �슫�쁺�옄", "aquafield@shinsegae.com", "�븘荑좎븘�븘�뱶 �슫�쁺�옄", "[�븘荑좎븘�븘�뱶]怨좉컼�쓽�냼由� 臾몄쓽 �븣由쇰찓�씪�엯�땲�떎.", reHtml);

      if (!booleanresult) {
        this.logger.debug("@@@@@@@@@@@@@@@@ 怨좉컼�쓽�냼由� 臾몄쓽 �븣由� 硫붿씪 諛쒖넚以� �뿉�윭媛� 諛쒖깮�뻽�뒿�땲�떎. @@@@@@@@@@@@@@@@@");
      }
    }
    else
    {
      html = html + "alert('泥섎━以� �뿉�윭媛� 諛쒖깮�븯���뒿�땲�떎.');";
    }
    Util.htmlPrint(html, response);

    return null;
  }

  @RequestMapping({"/ajaxPointCodeSet.af"})
  public String ajaxPointCodeSet(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {
    String result = "false";
    session.setAttribute("POINT_CODE", param.get("pointCode").toString());
    session.setAttribute("POINT_URL", param.get("pointUrl").toString());

    result = "success";
    Util.htmlPrint(String.valueOf(result), response);

    return null;
  }

  public String checkURL(HttpServletRequest request, HttpSession session)
  {
    String pointUrl = (String)session.getAttribute("POINT_URL");

    if (request.getHeader("REFERER") != null)
    {
      String beforeUrl = pointUrl;

      if (beforeUrl.indexOf(pointUrl) == -1)
      {
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

  @RequestMapping({"/member/loginMain.af"})
  public String loginMain(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request) {
    initRsa(request);

    return "/front/member/loginMain";
  }

  @ResponseBody
  @RequestMapping({"/member/loginproccessMain.af"})
  public Map<String, Object> loginproccessMain(@RequestParam Map param, Model model, HttpSession session, HttpServletResponse response, HttpServletRequest request) throws IOException, ParseException {
    Map loginMap = new HashMap();
    String result = "";
    String html = "";
    String url = "/member/loginMain.af";

    DateFormat pmtDateTransFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date today = new Date();
    Map parameter = new HashMap();
    parameter.put("point_code", param.get("point"));
    parameter.put("mem_id", (String)param.get("user_id"));
    parameter.put("loginDate", pmtDateTransFormat.format(today));

    int idChk = this.frontMemberService.idChk(parameter);
    if (idChk != 0) {
      Map inactMemInfo = this.frontMemberService.inactMemInfo(parameter);
      if (inactMemInfo != null) {
        this.frontMemberService.loginFail(parameter);

        result = "등록된 회원이 아닙니다.";
        url = "/member/inactivityCerti.af?data=" + inactMemInfo.get("MEM_UID");

        loginMap.put("check", "loginHuman");
        loginMap.put("result", result);
        loginMap.put("url", url);
        return loginMap;
      }
      Map memberInfo = this.frontMemberService.memberInfo(parameter);
      int attemp = Integer.parseInt(memberInfo.get("LOGIN_ATTEMP").toString());
      if (attemp >= 5) {
        String tmp = memberInfo.get("LAST_LOGIN_DATE").toString();
        Date loginDate = pmtDateTransFormat.parse(tmp);
        long seconds = (today.getTime() - loginDate.getTime()) / 1000L;
        int wrongSecond = Integer.parseInt(this.config.getProperty("login.wrong.second"));
        if (seconds <= wrongSecond) {
          this.frontMemberService.loginFail(parameter);

          result = "로그인 횟수 초과입니다. 30분뒤에 재시도해주세요.";
          loginMap.put("check", "loginFail");
          loginMap.put("url", url);
          loginMap.put("result", result);
          return loginMap;
        } else {
        	// 30분뒤 자동해제해줌.
        	this.frontMemberService.setLastloginDate(parameter);
        }

        this.frontMemberService.setLastloginDate(parameter);
      }

      parameter.put("mem_pw", (String)param.get("user_pwd"));
      memberInfo = this.frontMemberService.memberInfo(parameter);
      if (memberInfo == null || attemp >= 5) {
        this.frontMemberService.loginFail(parameter);

        result = "비밀번호가 틀립니다. 로그인을 " + (attemp + 1) + "회 시도하셨습니다. 5회 이상부터는 잠금처리됩니다.";
        loginMap.put("check", "loginFail");
        loginMap.put("url", url);
        loginMap.put("result", result);
        loginMap.put("attemp", "로그인을 " + (attemp + 1) + "회 시도하셨습니다. 5회 이상부터는 잠금처리됩니다.");
        return loginMap;
      }
      
      if(attemp >= 5) {
    	  this.frontMemberService.loginFail(parameter);

          result = "로그인을 " + (attemp + 1) + "회 시도하셨습니다. 5회 이상부터는 잠금처리됩니다.";
          loginMap.put("check", "loginFail");
          loginMap.put("url", url);
          loginMap.put("result", result);
          loginMap.put("attemp", "로그인을 " + (attemp + 1) + "회 시도하셨습니다. 5회 이상부터는 잠금처리됩니다.");    	  
          return loginMap;
      }
      
      InetAddress ip = InetAddress.getLocalHost();
      String userIp = ip.getHostAddress();
      memberInfo.put("MEM_IP", userIp);
      session.removeAttribute("MEM_INFO");
      session.removeAttribute("RS_DATA");
      session.setAttribute("MEM_INFO", memberInfo);

      this.frontMemberService.setLastloginDate(parameter);

      url = session.getAttribute("POINT_URL").toString();
      loginMap.put("check", "loginSuccess");
      loginMap.put("result", result);
      loginMap.put("url", url);
      return loginMap;
    }

    result = "등록된 아이디가 아닙니다.";
    loginMap.put("check", "loginHuman");

    loginMap.put("url", url);
    loginMap.put("result", result);
    return loginMap;
  }

  @ResponseBody
  @RequestMapping({"/member/loginkakaoproccessMain.af"})
  public Map<String, Object> loginkakaoproccessMain(@RequestParam Map param, Model model, HttpSession session, HttpServletResponse response, HttpServletRequest request) throws IOException, ParseException {
    Map loginMap = new HashMap();
    String result = "";
    String html = "";
    String url = "/member/loginMain.af";

    DateFormat pmtDateTransFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date today = new Date();
    Map parameter = new HashMap();
    parameter.put("point_code", param.get("point"));
    parameter.put("mem_id", (String)param.get("user_id"));
    parameter.put("loginDate", pmtDateTransFormat.format(today));

    int idChk = this.frontMemberService.idChk(parameter);
    if (idChk != 0) {
      Map inactMemInfo = this.frontMemberService.inactMemInfo(parameter);
      if (inactMemInfo != null) {
        this.frontMemberService.loginFail(parameter);

        result = "등록된 아이디가 아닙니다.";
        url = "/member/inactivityCerti.af?data=" + inactMemInfo.get("MEM_UID");

        loginMap.put("check", "loginHuman");
        loginMap.put("result", result);
        loginMap.put("url", url);
        return loginMap;
      }
      Map memberInfo = this.frontMemberService.memberInfo(parameter);
      int attemp = Integer.parseInt(memberInfo.get("LOGIN_ATTEMP").toString());
      if (attemp >= 5) {
        String tmp = memberInfo.get("LAST_LOGIN_DATE").toString();
        Date loginDate = pmtDateTransFormat.parse(tmp);
        long seconds = (today.getTime() - loginDate.getTime()) / 1000L;
        int wrongSecond = Integer.parseInt(this.config.getProperty("login.wrong.second"));
        if (seconds <= wrongSecond) {
          this.frontMemberService.loginFail(parameter);

          result = "로그인 횟수를 초과하셨습니다.";
          loginMap.put("check", "loginFail");
          loginMap.put("url", url);
          loginMap.put("result", result);
          return loginMap;
        }

        this.frontMemberService.setLastloginDate(parameter);
      }

      parameter.put("mem_pw", (String)param.get("user_pwd"));
      memberInfo = this.frontMemberService.memberInfo(parameter);
      if (memberInfo == null) {
        this.frontMemberService.loginFail(parameter);

        result = "비밀번호가 다릅니다.";
        loginMap.put("check", "loginFail");
        loginMap.put("url", url);
        loginMap.put("result", result);
        loginMap.put("attemp", "로그인을 " + (attemp + 1) + "회 시도하셨습니다. 5회 이상부터는 잠금처리됩니다.");
        return loginMap;
      }
      InetAddress ip = InetAddress.getLocalHost();
      String userIp = ip.getHostAddress();
      memberInfo.put("MEM_IP", userIp);
      session.removeAttribute("MEM_INFO");
      session.removeAttribute("RS_DATA");
      session.setAttribute("MEM_INFO", memberInfo);

      this.frontMemberService.setLastloginDate(parameter);

      url = session.getAttribute("POINT_URL").toString();
      loginMap.put("check", "loginSuccess");
      loginMap.put("result", result);
      loginMap.put("url", url);
      return loginMap;
    }

    result = "�븘�씠�뵒�� 鍮꾨�踰덊샇瑜� �솗�씤�븯怨� �떎�떆 �떆�룄�빐 二쇱꽭�슂.";
    loginMap.put("check", "loginHuman");

    loginMap.put("url", url);
    loginMap.put("result", result);
    return loginMap;
  }
}