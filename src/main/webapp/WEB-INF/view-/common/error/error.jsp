<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../common/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=0.7, maximum-scale=0.7, minimum-scale=0.7, user-scalable=no, target-densitydpi=device-dpi">
<title>AQUAFIELD - 아쿠아필드</title>
<link rel="stylesheet" type="text/css" href="/common/front/css/common.css"/>
<link rel="stylesheet" type="text/css" href="/common/front/css/content.css"/>
<link rel="stylesheet" type="text/css" href="/common/front/css/respond.css"/>
</head>
<section class="error_page">
	<div class="inner">
		<div class="erorr_cont">
			<!-- <img src="/common/front/images/common/404error.jpg"/> -->
			<img src="/common/front/images/common/error.png"/>
		</div>
		<div class="btns center">
		 	<button class="btn_pack d_gray" onclick="location.href='/';">HOME</button>
	        <button class="btn_pack white" onclick="window.history.back();">BACK</button>
        </div>
	</div>
</section>
</html>