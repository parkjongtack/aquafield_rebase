<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	String rsData		= "'"+request.getParameter("rsData")+"'";
	String sndOrdernumber	= request.getParameter("sndOrdernumber");
	String sndOrdername	= request.getParameter("sndOrdername");
	String sndGoodname	= request.getParameter("sndGoodname");
	String tr_no		= request.getParameter("tr_no");
	String tr_ddt	= request.getParameter("tr_ddt");
	String pg_uid	= request.getParameter("pg_uid");
	String payResult	= request.getParameter("payResult");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
</head>
<body>
<form id="MkSPayWeb" name=MkSPayWeb action = "/reserve/mkspayProccess.af" method="post">
	<input type="hidden" name="rsData" value="<%=rsData%>"/>
	<input type="hidden" name="sndOrdernumber" value="<%=sndOrdernumber%>"/>
	<input type="hidden" name="sndOrdername" value="<%=sndOrdername%>"/>
	<input type="hidden" name="sndGoodname" value="<%=sndGoodname%>"/>
	<input type="hidden" name="tr_no" value="<%=tr_no%>"/>
	<input type="hidden" name="tr_ddt" value="<%=tr_ddt%>"/>	
	<input type="hidden" name="pg_uid" value="<%=pg_uid%>"/>
</form>
</body>
</html>
<script language="JavaScript">
<!--
 document.getElementById("MkSPayWeb").submit();
-->
</script>