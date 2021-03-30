<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tile" %>
<!DOCTYPE html>
<html lang="ko">
	<head>
		<tile:insertAttribute name="top" />
	</head>
<body>
	<tile:insertAttribute name="header" />
	<section id="product_register">
		<div class="out_box">
			<tile:insertAttribute name="leftmenu" />		
			<tile:insertAttribute name="body" />
		</div>
	</section>		
	<tile:insertAttribute name="footer" />
	<tile:insertAttribute name="bot" />	
</body>
</html>