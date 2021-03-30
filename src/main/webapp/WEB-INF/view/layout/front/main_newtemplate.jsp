<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tile" %>
<!DOCTYPE html>
<html lang="ko">
	<head>
		<tile:insertAttribute name="maintop" />
		
		<link rel="stylesheet" type="text/css" href="/common/front/css/login_new.css"/>
		<link rel="stylesheet" type="text/css" href="/common/front/css/respond.css"/>
		
	</head>
<body id="${POINT_CODE}">
	<div id="wrap">
		<section class="section gateway">
			<div id="topArea"></div>
		</section>
		<section class="section main">
			<tile:insertAttribute name="mainheader" />
			<tile:insertAttribute name="mainbody" />
			
			<div id="footerTop">
				<div class="inner">
					<div class="footerOnBtn"></div>
					<div class="copyright">© 2016 SHINSEGAE E&C All Rights Reserved.</div>
					<ul class="footerSns">
						<li>
							<a href="http://pf.kakao.com/_uankxl" target="_blank">
								<img src="/common/front/images/ico/ico_sns_kas_off.png" alt="카카오 플러스 친구">
							</a>
						</li>
						<li>
							<a href="https://www.instagram.com/aquafield.official" target="_blank">
								<img src="/common/front/images/ico/ico_sns_ins_off.png" alt="인스타그램">
							</a>
						</li>
					</ul>
				</div>
			</div>
			
		</section>
		<tile:insertAttribute name="mainfooter" />
		<tile:insertAttribute name="mainbot" />
</body>
</html>
