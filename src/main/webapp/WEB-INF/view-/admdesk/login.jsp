<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../common/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="ko">
	<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>아쿠아필드 관리자</title>
<link rel="stylesheet" type="text/css" href="/admaf/css/common.css">
<link rel="stylesheet" type="text/css" href="/admaf/css/content.css">
<link rel="stylesheet" type="text/css" href="/admaf/css/jquery-ui.min.css">
<!-- <link rel="stylesheet" type="text/css" href="/admin/css/datepicker.css"> -->
<!--[if lt IE 9]>
	<script src="/admin/js/html5.js"></script>
<![endif]-->
<!--[if lt IE 8]>
	<script src="http://ie7-js.googlecode.com/svn/version/2.1(beta4)/IE8.js"></script>
<![endif]-->
<script type="text/javascript" src="/admaf/js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="/admaf/js/jquery.easing.1.3.js"></script>
<script type="text/javascript" src="/admaf/js/jquery-datepicker.js"></script>
<script type="text/javascript" src="/admaf/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="/admaf/js/jquery.calendario.js"></script>
<script type="text/javascript" src="/admaf/js/common.js"></script>
<script type="text/javascript" src="/admaf/js/commonUI.js?v=1"></script>
	</head>
<body>		

<section id="login">
	<div class="inner">
		<div class="loginBox">
			<div class="loginHeader">
				<img src="/admaf/images/login/login_logo.jpg" alt="">
			</div>
			<div class="loginBody">
				<h1>
					<p>Adimistrator Interface</p>
					<span>아쿠아필드 데스크관리자</span>
				</h1>
				<form name="form" id="form" method="post" action="login.af" onsubmit="return false;">
					<input type="hidden" name="returnurl" id="returnurl" value="${returnurl}" />
					<input type="hidden" name="chksms" id="chksms" value="N" />
					<div class="inputBox">
						<ul>
							<li><label>ID (E-MAIL)</label><input type="text" name="login_id" id="login_id" class="inputST1" value="" placeholder="ex) abc@daum.net" /></li>
							<li><label>PASSWORD</label><input type="password" name="login_pw" id="login_pw" class="inputST1" value="" /></li>
							<li>
								<label>지 점</label>
								<select name="point" id="point" class="customized-select">
									<option value="">지점선택</option>
<c:forEach items="${resultsPoint}" var="resultSub" varStatus="status">
									<option value="${resultSub.CODE_ID}">${resultSub.CODE_NM}</option>
</c:forEach>
								</select>
							</li>
							<li>
								<label>휴대폰번호</label><input type="text" name="hpnum" id="hpnum" class="inputST1 input_cert" value="" />
								<div class="btnBox"><button type="button" class="btn_cert btn_pack btn_mo" id="btnCert" onclick="chkCert();">인증</button></div>
							</li>
							<li><label>인증번호</label><input type="number" name="certnum" id="certnum" class="inputST1" value="" placeholder="숫자만 입력해 주세요."/></li>
						</ul>
					</div><!-- inputBox -->
					<div class="btnBox">
						<div class="save_id">
							<input id="saveId" name="saveId" type="checkbox"><label for="saveId">아이디 저장하기</label>
						</div>
						<button class="btn_sign btn_pack btn_mo" id="btnLogin" onclick="chkForm();">로그인</button>
					</div>
				</form>
			</div>
		</div><!-- loginBox -->
		<div class="loginFooter">
			<img src="/admaf/images/login/signature_basic_e.gif" alt="">
		</div>
	</div><!-- inner -->
</section><!-- login -->
<script type="text/javascript">
var savedId = getLocalData({name : 'saveid'});
if(savedId){document.form.login_id.value = savedId;document.form.saveId.checked = true;}
function chkForm(){
	if($.trim(document.form.login_id.value) == ""){ alert("ID를 입력하세요!"); document.form.login_id.focus(); return false; }
	if($.trim(document.form.login_pw.value) == ""){ alert("Password를 입력하세요!"); document.form.login_pw.focus(); return false; }
	if($.trim(document.form.point.value) == ""){ alert("지점을 선택하세요!"); document.form.point.focus(); return false; }
	if($.trim(document.form.hpnum.value) == ""){ alert("휴대폰번호를 입력하세요!"); document.form.hpnum.focus(); return false; }
	//if(document.chksms == "N"){ alert("휴대폰 인증을 해 주세요."); return false;}
	if($.trim(document.form.certnum.value) == ""){ alert("인증번호를 입력하세요!"); document.form.certnum.focus(); return false; }
	
	document.form.saveId.checked ? setLocalData({name : 'saveid', data : document.form.login_id.value}) : removeLocalData({name : 'saveid'});

	$.ajax({
		 type: "post"
		,url : "login.af"
		,data : $(document.form).serialize()
		,dataType : "json"
		,success: function(data){
			if(data.result == true){
				var returnurl = $("#returnurl").val() == "" ? "/secu_admaf/admdesk/index.af" : $("#returnurl").val();
				top.location.href=returnurl;
			}
			else{
				alert(data.msg);
			}
		}
		,error: function(xhr, option, error){
			alert("오류가 있습니다. 잠시후에 다시하세요");
		}
	});
	return false;
}
function chkCert(){
	if($.trim(document.form.login_id.value) == ""){ alert("ID를 입력하세요!"); document.form.login_id.focus(); return false; }
	if($.trim(document.form.login_pw.value) == ""){ alert("Password를 입력하세요!"); document.form.login_pw.focus(); return false; }
	if($.trim(document.form.hpnum.value) == ""){ alert("휴대폰번호를 입력하세요!"); document.form.hpnum.focus(); return false; }
	
 	$.ajax({
 		 type: "post"
 		,url : "login_sms.af"
 		,data : $(document.form).serialize()
 		,dataType : "json"
 		,success: function(data){
 			if(data.result == true){
 				$("#chksms").val("Y");
 				alert( "인증번호를 전송하였습니다." );
 			}
 			else{
 				alert(data.msg);
 				document.form.hpnum.focus();
 			}
 		}
//  		,error: function(xhr, option, error){
//  			alert("오류가 있습니다. 잠시후에 다시하세요");
//  		}
 	});
}
$(function(){
	$("#hpnum").on("change",function(){
		$("#chksms").val("N");
	});
});
</script>

</body>
</html>