<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=0.7, maximum-scale=0.7, minimum-scale=0.7, user-scalable=no, target-densitydpi=device-dpi">
<title>AQUAFIELD - 아쿠아필드 - 팝업 공지</title>
<head>
<script>
function setCookie(name, value, expiredays) {
	var today = new Date();
    today.setDate(today.getDate() + expiredays);
    document.cookie = name + '=' + escape(value) + '; path=/; expires=' + today.toGMTString() + ';'
}
function closePop() {        
	setCookie('goyang20190130', 'Y', 1);
	self.close();
}

</script>
</head>
	
<body topmargin=0 leftmargin=0>
	<a onclick="self.close();" style="cursor:pointer;">
		<img src="/common/front/images/POINT03/main/goyang_20190130.jpg" alt="AQUAFIELD">
	</a>
	<div style="border-top:1px solid #ddd; height:36px;padding-top:8px;">
		<span><input type="checkbox" name="goyang20190130" id="goyang20190130" onclick="closePop();"/><label for="goyang20190130">오늘하루 그만 보기</label></span>
		<span style=" float: right;padding-right: 25px;"><a onclick="self.close();" style="cursor:pointer;">[닫기]</a></span>
	</div>
</body>