<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@ page import="java.net.URLEncoder"%>
<%@ page import="java.net.URLDecoder"%>
<%@page import="java.io.*, java.net.*"%>
<%@ page import="java.sql.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@ page import="java.text.DateFormat"%>
<% 



Connection conn=null;
Statement stmt=null;     

try{
	//드라이버 연결
	Class.forName("oracle.jdbc.driver.OracleDriver");
	//jspdb는 DB명 // mysql-> user는 root 비밀번호는 1234
	conn=DriverManager.getConnection("jdbc:oracle:thin:@124.51.251.71:1521:orcl","aquafield","aquafield0719");
	
	if(conn==null)
	throw new Exception("데이터베이스 연결 실패");

	//연결된 상태를 stmt로 
	//stmt=conn.createStatement();
	// 입력받은값 저장
	//stmt.executeUpdate("insert into inputXY (x,y) values('"+x+"','"+y+"');");
	// DB에 들어있는 정보를 가져와서 rs객체로저장 (출력)
	//ResultSet rs = stmt.executeQuery("delete from TB_RESERVATION where INS_DATE <= '"+df.format(cal.getTime())+"'");
	//객체의 값이 있으면 TRUE

	Calendar calnow = Calendar.getInstance();
	calnow.setTime(new Date());
	DateFormat dfnow = new SimpleDateFormat("ddMMyyHHmm");	

	
	out.println("create table TB_MEMBER_INACTIVITY_"+dfnow.format(calnow.getTime())+" as select * from TB_MEMBER_INACTIVITY");
	
	
	stmt= conn.createStatement();
	StringBuffer sb_create_1 = new StringBuffer();
	sb_create_1.append("create table TB_MEMBER_INACTIVITY"+dfnow.format(calnow.getTime())+" as select * from TB_MEMBER_INACTIVITY");
	int updateCount_create_1 = stmt.executeUpdate(sb_create_1.toString());		
	
	stmt= conn.createStatement();
	StringBuffer sb_create_2 = new StringBuffer();
	sb_create_2.append("create table TB_MEMBER"+dfnow.format(calnow.getTime())+" as select * from TB_MEMBER");
	int updateCount_create_2 = stmt.executeUpdate(sb_create_2.toString());		
	
	stmt= conn.createStatement();
	StringBuffer sb_create_3 = new StringBuffer();
	sb_create_3.append("create table TB_SMS_SEND"+dfnow.format(calnow.getTime())+" as select * from TB_SMS_SEND");
	int updateCount_create_3 = stmt.executeUpdate(sb_create_3.toString());		
	
	stmt= conn.createStatement();
	StringBuffer sb_create_5 = new StringBuffer();
	sb_create_5.append("create table TB_ONE_TO_ONE"+dfnow.format(calnow.getTime())+" as select * from TB_ONE_TO_ONE");
	int updateCount_create_5 = stmt.executeUpdate(sb_create_5.toString());	
	
	Calendar cal = Calendar.getInstance();
	cal.setTime(new Date());
	DateFormat df = new SimpleDateFormat("yy/MM/dd");
	//out.println("current: " + df.format(cal.getTime()));

	//cal.add(Calendar.MONTH, 2);
	//cal.add(Calendar.DATE, -3);
	cal.add(Calendar.YEAR, -1);
	//out.println("after: " + df.format(cal.getTime()));	
	
	stmt= conn.createStatement();
	StringBuffer sb_1 = new StringBuffer();
	sb_1.append("delete from TB_MEMBER_INACTIVITY where LAST_LOGIN_DATE <= '"+df.format(cal.getTime())+"'");
	int updateCount_1 = stmt.executeUpdate(sb_1.toString());	
	
	
	stmt= conn.createStatement();
	StringBuffer sb = new StringBuffer();
	sb.append("delete from TB_MEMBER where LAST_LOGIN_DATE <= '"+df.format(cal.getTime())+"'");
	int updateCount = stmt.executeUpdate(sb.toString());

	//out.println("delete from TB_MEMBER where LAST_LOGIN_DATE <= '"+df.format(cal.getTime())+"'");
	
	//System.out.println(updateCount);

	//out.println(df.format(cal.getTime()));	

	Calendar cal2 = Calendar.getInstance();
	cal2.setTime(new Date());
	DateFormat df2 = new SimpleDateFormat("yy/MM/dd");	
	
	cal2.add(Calendar.YEAR, -3);
	//out.println("after: " + df2.format(cal2.getTime()));
	
	stmt= conn.createStatement();
	StringBuffer sb2 = new StringBuffer();
	sb2.append("delete from TB_SMS_SEND where INS_DATE <= '"+df2.format(cal2.getTime())+"'");
	int updateCount2 = stmt.executeUpdate(sb2.toString());

	//out.println("delete from TB_SMS_SEND where INS_DATE <= '"+df2.format(cal2.getTime())+"'");
	
	//System.out.println(updateCount2);
	
	//out.println("delete from TB_ONE_TO_ONE where INS_DATE <= '"+df2.format(cal2.getTime())+"'");
	
	stmt= conn.createStatement();
	StringBuffer sb3 = new StringBuffer();
	sb3.append("delete from TB_ONE_TO_ONE where INS_DATE <= '"+df2.format(cal2.getTime())+"'");
	int updateCount3 = stmt.executeUpdate(sb3.toString());

	//System.out.println(updateCount3);	
	
	
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
%>

