package com.soft.web.controller.front;

import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.soft.web.base.GenericController;
import com.soft.web.mail.MailService;
import com.soft.web.service.admin.AdminEmailTempletService;
import com.soft.web.service.common.CommonService;
import com.soft.web.service.front.FrontMemberService;
import com.soft.web.sms.SmsService;
import com.soft.web.util.AquaDateUtil;
import com.soft.web.util.DecoderUtil;

@Controller
public class FiveYearDelete extends GenericController {

	protected Logger logger = LoggerFactory.getLogger(NaverLoginController.class);
	
    @Resource(name="config")
    private Properties config;

	@Autowired
	private JavaMailSender mailSender;
    
	@Autowired
	FrontMemberService frontMemberService;
	
	@Autowired
	SmsService smsService;
	
	@Autowired
	CommonService commonService;	
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	AdminEmailTempletService adminEmailTempletService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/member/FiveYearDelete.af")
    public String FiveYearDelete(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        DateFormat df = new SimpleDateFormat("yy/MM/dd");
        System.out.println("current: " + df.format(cal.getTime()));

        //cal.add(Calendar.MONTH, 2);
        //cal.add(Calendar.DATE, -3);
        cal.add(Calendar.YEAR, -5);
        System.out.println("after: " + df.format(cal.getTime()));

    	Connection conn=null;
    	Statement stmt=null;     
        
    	try{
    		//드라이버 연결
    		Class.forName("oracle.jdbc.driver.OracleDriver");
    		//jspdb는 DB명 // mysql-> user는 root 비밀번호는 1234
    		conn=DriverManager.getConnection("jdbc:oracle:thin:@10.253.41.218:1521:aqua","ahp","ahpaqua000");
    		
    		if(conn==null)
    		throw new Exception("데이터베이스 연결 실패");

    		//연결된 상태를 stmt로 
    		//stmt=conn.createStatement();
    		// 입력받은값 저장
    		//stmt.executeUpdate("insert into inputXY (x,y) values('"+x+"','"+y+"');");
    		// DB에 들어있는 정보를 가져와서 rs객체로저장 (출력)
    		//ResultSet rs = stmt.executeQuery("delete from TB_RESERVATION where INS_DATE <= '"+df.format(cal.getTime())+"'");
    		//객체의 값이 있으면 TRUE

    		stmt= conn.createStatement();
    		StringBuffer sb = new StringBuffer();
    		sb.append("delete from TB_RESERVATION where INS_DATE <= '"+df.format(cal.getTime())+"'");
    		int updateCount = stmt.executeUpdate(sb.toString());

    		System.out.println(updateCount);
    		
    		}finally{
	    		try{
	    			stmt.close();
	    		}catch(Exception ignored){
    		}

	    		try{
		    			conn.close();
		    		}catch(Exception ignored){
	    		}
    		}
        
        return null;
    }
	
}
