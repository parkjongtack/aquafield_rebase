<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<section id="login">
	<div class="inner">
		<div class="loginBox">
			<div class="loginHeader">
				<img src="/admin//images/login/login_logo_friends.jpg" alt="">
			</div>
			<div class="loginBody">
				<h1>
					<p>Adimistrator Interface</p>
					<span>아쿠아필드 데스크관리자</span>
				</h1>
				<form name="form" id="form" method="post" action="index.jsp" onsubmit="return false;">
					<input type="hidden" name="returnurl" id="returnurl" value="${returnurl}" />
					<div class="inputBox">
						<ul>
							<li><label>ID (E-MAIL)</label><input type="text" name="loginId" id="loginId" class="inputST1" placeholder="ex) abc@daum.net" /></li>
							<li><label>PASSWORD</label><input type="password" name="loginPw" id="loginPw" class="inputST1"/></li>
							<li>
								<label>휴대폰번호</label><input type="text" name="hpnum" id="hpnum" class="inputST1 input_cert"/>
								<div class="btnBox"><button class="btn_cert btn_pack btn_mo" id="btnCert" onclick="chkCert();">인증</button></div>
							</li>
							<li><label>인증번호</label><input type="number" name="certnum" id="certnum" class="inputST1" placeholder="숫자만 입력해 주세요."/></li>
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
			<img src="/admin//images/login/signature_basic_e.gif" alt="">
		</div>
	</div><!-- inner -->
</section><!-- login -->
<script type="text/javascript">
var savedId = getLocalData({name : 'saveid'});
if(savedId){document.form.loginId.value = savedId;document.form.saveId.checked = true;}
function chkForm(){
	if(document.form.loginId.value == ""){ alert("ID를 입력하세요!"); document.form.loginId.focus(); return false; }
	if(document.form.loginPw.value == ""){ alert("Password를 입력하세요!"); document.form.loginPw.focus(); return false; }
	if(document.form.hpnum.value == ""){ alert("휴대폰 인증을 해주세요!"); document.form.hpnum.focus(); return false; }
	if(document.form.certnum.value == ""){ alert("인증번호를 입력하세요!"); document.form.certnum.focus(); return false; }
	document.form.submit();
	document.form.saveId.checked ? setLocalData({name : 'saveid', data : document.form.loginId.value}) : removeLocalData({name : 'saveid'});
	return false;
}
function chkCert(){
	if($('.input_cert').val()==""){
		alert( "휴대폰번호를 입력하세요!" );
	}else{
		alert( "인증번호를 전송하였습니다." );
	}
}
/*$(function(){
	$("#btnCert").click(function(e) {
		alert(0);
		if($('.input_cert').val()==""){
			alert( "휴대폰번호를 입력하세요!" );
		}
	});
});*/
</script>
