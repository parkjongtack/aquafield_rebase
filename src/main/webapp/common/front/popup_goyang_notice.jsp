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
	setCookie('goyang20190124', 'Y', 1);
	self.close();
}

</script>
</head>
	
<body topmargin=0 leftmargin=0>
	<a onclick="self.close();" style="cursor:pointer;">
		<img src="/common/front/images/POINT03/main/goyang_20190124.jpg" alt="AQUAFIELD">
	</a>
	<div style="height:38px;padding-top:8px;background:#102230;color:#fff;">
		<span style=" width: 49%;float: left; text-align: center; line-height: 30px;"><input type="checkbox" name="goyang20190124" id="goyang20190124" onclick="closePop();"/><label for="goyang20190124">오늘 하루 열지 않기</label></span>
		<span style="float:left;opacity:.5; line-height: 30px;">|</span>
		<span style=" width: 49%;float: left;text-align: center;line-height: 30px;"><a onclick="self.close();" style="cursor:pointer;">닫기</a></span>
	</div>
</body>