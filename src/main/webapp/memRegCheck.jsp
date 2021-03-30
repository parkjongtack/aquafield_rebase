<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="java.net.URLEncoder"%>
<%@ page import="java.net.URLDecoder"%>
<%@page import="java.io.*, java.net.*"%>
<%@ page import="java.sql.*"%>
<%@ page import = "org.json.*" %>
<%@ page import="org.json.simple.parser.JSONParser" %>
<%@ page import="org.json.simple.*" %>
<%@ page import="com.fasterxml.jackson.databind.ObjectMapper" %>
<%@ page import="com.fasterxml.jackson.databind.node.ObjectNode" %>
<%@ page import="com.fasterxml.jackson.databind.JsonNode" %>    
<% 
	
	Connection conn=null;
	Statement stmt=null;

	String MEM_UID = "";

	try{
	//드라이버 연결
	Class.forName("oracle.jdbc.driver.OracleDriver");
	//jspdb는 DB명 // mysql-> user는 root 비밀번호는 1234
	conn=DriverManager.getConnection("jdbc:oracle:thin:@124.51.251.71:1521:orcl","aquafield","aquafield0719");
	
	if(conn==null)
	throw new Exception("데이터베이스 연결 실패");

	//연결된 상태를 stmt로 
	stmt=conn.createStatement();
	// 입력받은값 저장
	//stmt.executeUpdate("insert into inputXY (x,y) values('"+x+"','"+y+"');");
	// DB에 들어있는 정보를 가져와서 rs객체로저장 (출력)
	
	//out.println("select * from TB_MEMBER where MEM_ID = '"+request.getParameter("id")+"'");
	
	ResultSet rs = stmt.executeQuery("select * from TB_MEMBER where MEM_ID = '"+request.getParameter("id")+"'");
	//객체의 값이 있으면 TRUE
	while(rs.next()){
		MEM_UID= rs.getString("MEM_UID");
	//out.println("<br> x : "+STRMOBILE+"<br> y : "+STRUSERNM);
	}
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
	
	if(MEM_UID.equals("")) {
		out.println("");
	} else {
		out.println("yes");		
	}
	
	//out.println(MEM_UID);
%>
