<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tile" %>
<!DOCTYPE html>
<html lang="ko">
	<head>
		<tile:insertAttribute name="maintop" />
	</head>
<body>
	<div id="wrap">
		<section class="section fp-auto-height">
			<div id="topArea"></div>
		</section>
		<section class="section active">
			<tile:insertAttribute name="mainheader" />
			<tile:insertAttribute name="mainbody" />
			<div id="footerTop">
				<div class="inner">
					<div class="footerOnBtn"></div>
					<ul class="footerTopMenu">
						<li><a href="#/service/index.af?page=terms2">아쿠아필드 이용약관</a></li>
						<li><a href="#/service/index.af?page=terms">홈페이지 이용약관</a></li>
						<li><a href="#/service/index.af?page=privacy">개인정보 처리방침</a></li>
						<li><a href="#/service/index.af?page=email">이메일 무단수집 거부</a></li>
						<li><a href="#/service/index.af?page=video">영상정보처리 기기운영/관리방침</a></li>
						<li><a href="#/service/index.af?page=guide">운영정책</a></li>
					</ul>
					<ul class="footerSns">
						<li><a href=" http://pf.kakao.com/_uankxl" target="_blank"><img src="/common/front/images/ico/ico_sns_kas_off.png" alt="카카오 플러스 친구"></a></li>
						<li><a href="https://www.instagram.com/aquafield_/" target="_blank"><img src="/common/front/images/ico/ico_sns_ins_off.png" alt="인스타그램"></a></li>
					</ul>
				</div>
			</div>
		</section>
			<tile:insertAttribute name="mainfooter" />
			<tile:insertAttribute name="mainbot" />
</body>
</html>
