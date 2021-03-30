<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=0.7, maximum-scale=0.7, minimum-scale=0.7, user-scalable=no, target-densitydpi=device-dpi">
<title>AQUAFIELD - 아쿠아필드 - 팝업 공지</title>
<head>
	<script type="text/javascript">
		function popup() {
			eventPopup();
			self.close();
		}
		function eventPopup() {
			window.open('/common/front/event_popup.jsp','pop3','width=524,height=734,top=50,left=650');	
		}
		
		
	</script>
	
	
</head>
	
<body topmargin=0 leftmargin=0>
	<a onclick="popup();" style="cursor:pointer;">
		<img src="/common/front/images/POINT03/main/popup03-1.jpg" alt="AQUAFIELD">
	</a>
</body>
