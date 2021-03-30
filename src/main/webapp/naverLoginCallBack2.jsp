<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript" src="/common/front/js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="/common/front/js/iscroll.js"></script>
<script type="text/javascript" src="/common/front/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="/common/front/js/naveridlogin_js_sdk_2.0.0.js"></script>
<!-- 
<form name="frmNaverLoginForm" method="post" action="/member/naverlogin.af">
	연락처 : <input type="text" name="phone_number" />
	<input type="button" name="submit_button" value="로그인" onclick="javascript:login_check();" />
	<input type="hidden" name="id" />
	<input type="hidden" name="one_submit_check" />
	<input type="hidden" name="name" />
	<input type="hidden" name="email" />
	<input type="hidden" name="birthday" />
	<input type="hidden" name="gender" />
	<input type="hidden" name="naver_login_check" value="Y" />
</form>
 -->
<style>
   *{margin: 0; padding: 0;box-sizing: border-box;}
   .form_con{display:flex; justify-content: center; align-items: center; height: 100vh; width: 100vw; background-color: #f5f5f5;}
   form{width:450px; padding:50px 40px; background-color: #fff;}
   form h3{font-size: 25px; padding-bottom: 10px; border-bottom: 1px solid #555; text-align:center; font-weight: 400;}
   form input[type=text], form input[type=button]{display: block; width: 100%; padding:10px; font-size: 16px; border:1px solid #ddd; margin-top: 10px;}
   form input[type=button]{border: 1px solid #0095ce; background-color: #0095ce; color: #fff; font-size: 14px}
</style>
<body>
<div class="form_con">
   <form name="frmNaverLoginForm" method="post" action="/member/naverlogin.af">
      <h3>연락처</h3>
      <input type="text" name="phone_number" />
      <input type="button" name="submit_button" value="로그인" onclick="javascript:login_check();" />
      <input type="hidden" name="id" />
      <input type="hidden" name="one_submit_check" />
      <input type="hidden" name="name" />
      <input type="hidden" name="email" />
      <input type="hidden" name="birthday" />
      <input type="hidden" name="gender" />
      <input type="hidden" name="naver_login_check" value="Y" />
   </form>
</div>
</body> 
<script type="text/javascript">

	var naverLogin = new naver.LoginWithNaverId(
		{
			clientId: "occAe_qDtkFxlWrdSTnc",
			callbackUrl: "http://aquafield-ssg.co.kr/naverLoginCallBack.jsp",
			isPopup: false,
			callbackHandle: false
		}
	);

	naverLogin.init();
	
	naverLogin.getLoginStatus(function (status) {
		
		//alert(status);
		
		//if (status) {
			var email = naverLogin.user.getEmail();
			var name = naverLogin.user.getNickName();
			var profileImage = naverLogin.user.getProfileImage();
			var birthday = naverLogin.user.getBirthday();
			var gender = naverLogin.user.getGender();
			var uniqId = naverLogin.user.getId();
			var age = naverLogin.user.getAge();


			alert(email);
			alert(name);
			alert(profileImage);
			alert(birthday);
			alert(age);
			alert(gender);			

			
			var form = document.frmNaverLoginForm;
			form.id.value = email;
			form.name.value = name;
			form.email.value = email;
			form.birthday.value = birthday;
			form.gender.value = gender;
			//form.submit();
			
			var param = "id="+form.id.value;
			
			//alert(param);
			
			$.ajax({
				url: "/memRegCheck.jsp",
				data:  param,
				type: 'post',
				dataType: 'text',
				async: false,
				success: function(result){
					//alert($.trim(result));
					if($.trim(result).indexOf("yes") != -1){
						//alert('aaaaa');
						document.frmNaverLoginForm.action = "/member/naverlogin.af";
						document.frmNaverLoginForm.submit();
					}
					
				},
				error: function(xhr, status, error){ 
					alert(error);
				}
			});					
			
		//} else {
		//	console.log("AccessToken이 올바르지 않습니다.");
		//}
	});
	var flag = 0;
	function login_check() {
		var form = document.frmNaverLoginForm;
		
		if(form.phone_number.value == "") {
			alert("연락처를 입력해주세요.");
			form.phone_number.focus();
			return false;
		} else {
			
			//console.log('aaaa');
			
			//form.action = "/member/naverlogin.af";
			//form.submit();
			
			$.ajax({
				url: "/member/naverlogin.af",
				data:  $("form[name=frmNaverLoginForm]").serialize(),
				type: 'post',
				dataType: 'text',
				async: true,
				success: function(result){
					location.href = "/";					
				},
				error: function(xhr, status, error){ 
					alert(error);
				}
			});			
			
		}
		
		
	}

</script>
