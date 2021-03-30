<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<form name="frmNaverLoginForm" method="post" action="/member/naverlogin.af">
	<input type="hidden" name="id" />
	<input type="hidden" name="name" />
	<input type="hidden" name="email" />
	<input type="hidden" name="birthday" />
	<input type="hidden" name="gender" />
	<input type="hidden" name="naver_login_check" value="Y" />
</form>    
<script type="text/javascript" src="/common/front/js/naveridlogin_js_sdk_2.0.0.js"></script>
<script type="text/javascript">

	var naverLogin = new naver.LoginWithNaverId(
		{
			clientId: "occAe_qDtkFxlWrdSTnc",
			callbackUrl: "http://localhost:8080/naverLoginCallBack.jsp",
			isPopup: false,
			callbackHandle: false
		}
	);

	naverLogin.init();
	window.addEventListener('load', function () {
		naverLogin.getLoginStatus(function (status) {
			
			alert(status);
			
			if (status) {
				var email = naverLogin.user.getEmail();
				var name = naverLogin.user.getNickName();
				var profileImage = naverLogin.user.getProfileImage();
				var birthday = naverLogin.user.getBirthday();
				var gender = naverLogin.user.getGender();
				var uniqId = naverLogin.user.getId();
				var age = naverLogin.user.getAge();
	
				/*
				alert(email);
				alert(name);
				alert(profileImage);
				alert(birthday);
				alert(age);
				alert(gender);			
				*/
				
				var form = document.frmNaverLoginForm;
				form.id.value = "naver_"+uniqId;
				form.name.value = name;
				form.email.value = email;
				form.birthday.value = birthday;
				form.gender.value = gender;
				form.submit();
				
				//alert('끝');
			} else {
				console.log("AccessToken이 올바르지 않습니다.");
			}
		});
	});

</script>
