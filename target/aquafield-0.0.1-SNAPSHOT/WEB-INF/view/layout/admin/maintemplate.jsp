<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tile" %>
<!DOCTYPE html>
<html lang="ko">
	<head>
		<tile:insertAttribute name="maintop" />
	</head>
<body>
	<tile:insertAttribute name="mainheader" />		
	<tile:insertAttribute name="mainbody" />
	<tile:insertAttribute name="mainfooter" />
	<tile:insertAttribute name="mainbot" />	
</body>
</html>