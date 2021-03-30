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

Calendar cal = Calendar.getInstance();
cal.setTime(new Date());
DateFormat df = new SimpleDateFormat("yy/MM/dd");
out.println("current: " + df.format(cal.getTime()));

//cal.add(Calendar.MONTH, 2);
//cal.add(Calendar.DATE, -3);
cal.add(Calendar.YEAR, -5);
out.println("after: " + df.format(cal.getTime()));

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
%>

