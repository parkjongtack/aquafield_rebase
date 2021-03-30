<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../common/taglibs.jsp" %>
<script type="text/javascript" src="/common/front/js/naveridlogin_js_sdk_2.0.0.js"></script>
<script type="text/javascript" src="/common/front/js/kakao.js"></script>



<style media="screen">
	input::placeholder  {	color: #555;	font-weight:bold; }
</style>
			            

<div class="contentArea" style="display:none;">	
</div>

<div class="contentArea">
</div>

	<div id="login_wrap">
		<!-- <div class="login_top">
			<div class="logo">
					<a href="#A"><img src="../common/front/images/common/logo.png" alt="아쿠아필드"></a>
			</div>
		</div> -->
	   	<div class="inner">
			<h2>로그인</h2>
	        
	        <form id="loginForm" name="loginForm" action="/member/loginproccessMain.af" method="post">
		        <input type="hidden" name="login_type" value="kakao">
		        <input type="hidden" name="id" value="">
		        <input type="hidden" name="name" value="">
		        <input type="hidden" name="gender" value="">
		        <input type="hidden" name="birthday" value="">
		        <input type="hidden" name="nickname" value="">
		        <input type="hidden" name="email" value="">
		        <input type="hidden" name="phone_number" value="">				        
		        <input type="hidden" name="point" value="POINT01">
		        <div class="login_box">
			        <ul class="from_list">
			            <li>
			               <div class="placeh">
			                  <label for="user_id" style=""></label>
			                  <input type="text" class="ipt bd_hide injectAble" name="user_id" id="user_id" value="" placeholder="이메일 입력" onfocus="this.placeholder=''" onblur="this.placeholder='이메일 입력'">
			                </div>
			            </li>
						
			            <li>
			           		<div class="placeh">
								<label for="user_pwd" style="color:#333"></label>
				               	<input type="password" class="ipt bd_hide" name="user_pwd" id="user_pwd" value="" placeholder="비밀번호" onfocus="this.placeholder=''" onblur="this.placeholder='비밀번호'">
			              	</div>
			              	<div id="error_div" style="display:none; margin-top:5px;">
			                	<label id="user_id_error" style="color:red; font-size:12px;"></label>
			                </div>
			            </li>
			        </ul>
		
					<div class="btn_group btn2">
						<a href="javascript:login();" class="btn blue2">로그인</a>
					</div>

		         	<p class="checkbox_st">
						<input type="checkbox" name="saveId" id="saveId" value="Y" tabindex="-1" class="checkbox-style" />
						<label for="saveId">이메일 저장</label>
		         	</p>		
					<br/>

					<div id="naverIdLogin" class="btn_con">
						<img src="/common/front/images/naver_login_bt.png" class="input_btn"/>
					</div>

					<br/>
					<a id="custom-login-btn" href="javascript:loginWithKakao()">
					  <img
						src="//k.kakaocdn.net/14/dn/btqCn0WEmI3/nijroPfbpCa4at5EIsjyf0/o.jpg"
						width="390"
					  />
					</a>
					
					<!-- 네이버아디디로로그인 초기화 Script -->
					<script type="text/javascript">
						var naverLogin = new naver.LoginWithNaverId(
							{
								clientId: "occAe_qDtkFxlWrdSTnc",
								callbackUrl: "http://aquafield-ssg.co.kr/naverLoginCallBack.jsp",
								isPopup: false, /* 팝업을 통한 연동처리 여부 */
								loginButton: {color: "green", type: 3, height: 82} /* 로그인 버튼의 타입을 지정 */
							}
						);
						
						/* 설정정보를 초기화하고 연동을 준비 */
						naverLogin.init();
						
					</script>
					<!-- // 네이버아이디로로그인 초기화 Script -->					
		         	
					<ul class="option">
						<li><a href="/member/findMemberId.af">아이디 찾기</a></li>
						<li><a href="/member/findMemberPwd.af">비밀번호 찾기</a></li>
						<li><a href="/member/joinStep1.af">회원가입</a></li>
					</ul>
		        </div>
		     </form>
	    </div>
	</div>
	
	<script type="text/javascript">
		$(document).ready(function(){
	        // 저장된 쿠키값을 가져와서 ID 칸에 넣어준다. 없으면 공백으로 들어감.
	        var userInputId = getCookie("userInputId");
	        $("input[name='user_id']").val(userInputId);
	
	        if($("input[name='user_id']").val() != ""){ // 그 전에 ID를 저장해서 처음 페이지 로딩 시, 입력 칸에 저장된 ID가 표시된 상태라면,
	            $("#saveId").attr("checked", true); // ID 저장하기를 체크 상태로 두기.
	        }
	
	        $("#saveId").change(function(){ // 체크박스에 변화가 있다면,
	            if($("#saveId").is(":checked")){ // ID 저장하기 체크했을 때,
	                var userInputId = $("input[name='user_id']").val();
	                setCookie("userInputId", userInputId, 7); // 7일 동안 쿠키 보관
	            }else{ // ID 저장하기 체크 해제 시,
	                deleteCookie("userInputId");
	            }
	        });
	
	        // ID 저장하기를 체크한 상태에서 ID를 입력하는 경우, 이럴 때도 쿠키 저장.
	        $("input[name='user_id']").keyup(function(){ // ID 입력 칸에 ID를 입력할 때,
	            if($("#saveId").is(":checked")){ // ID 저장하기를 체크한 상태라면,
	                var userInputId = $("input[name='user_id']").val();
	                setCookie("userInputId", userInputId, 7); // 7일 동안 쿠키 보관
	            }
	        });
	        
	        $("input[name='user_pwd']").keyup(function(e){
	        	if(e.keyCode==13) {
	        		login();
	        	}
	        });
	        
	    });
		
		function login(){
    	    var id = $('#user_id').val();
    	    $('#error_div').hide();
    	    $('#user_id_error').html('');
    	    
    	    if(valCheck(id)) {
	   	    	var formData = $("#loginForm").serialize();
		    	$.ajax({
			   		async: false
			   		,type: "post"
			   		,url : '/member/loginproccessMain.af'
			   		,data : formData
			   		,dataType : "json"
			   		,success: function(obj){
			   			var msg = '';
			   			if(obj.result != ''){
			   				msg = obj.result.replace("\\n","\n");
			   			}
			   			
		   				if(obj.check == 'loginHuman'){
		   					if(msg != '')
				   				alert(msg);
		   					location.href = obj.url;
		   				}
		   				else if(obj.check == 'loginFail'){
		   					if(msg != '')
				   				alert(msg);
		   					if(obj.attemp != ''){
		   						$('#user_pwd').val('');
		   						$('#user_pwd').focus();
		   						/* $('#error_div').show();
		   						$('#user_id_error').html(obj.attemp); */
		   					}
		   					else {
		   						location.href = obj.url;
		   					}
		   				}
						else if(obj.check == 'loginSuccess'){
							if(msg != '')
				   				alert(msg);
							location.href = obj.url;
		   				}
			   		}
			   		,error: function(error){
			   			alert("에러가 발생했습니다. 잠시 후에 다시하세요.");
			   		}
		   		});
    	    }
	    };
		
	    function valCheck(id){
		   if($.trim($("#user_id").val()) == "" || $("#user_id").val() == null){
			   alert('사용자 이메일을 입력해 주세요.');
			   $("#user_id").focus();
			   return false;
		   } else {
			   /* 아이디가 이메일 형식이 아닐떄 false */
			   var regex=/^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/;
			   if(!regex.test(id)){
	               alert("잘못된 이메일 형식입니다.");
	               return false;
	           }
		   }
		   
		   if($.trim($("#user_pwd").val()) == "" || $("#user_pwd").val() == null){
			   alert('사용자 패스워드를 입력해 주세요.');
			   $("#user_pwd").focus();
			   return false;
		   }

		   return true;
	   };
	    
	</script>

<!-- 카카오톡로그인 초기화 Script -->
<script type="text/javascript">
  // input your appkey
  
  Kakao.init('0fbb7d4fae30c19a80e92d17c32cdcb3')
  function loginWithKakao() {
	Kakao.Auth.loginForm({
	  success: function(authObj) {
		//alert(JSON.stringify(authObj));
		Kakao.API.request({
			url: '/v2/user/me',
			success: function(res) {
				/*
				console.log(res);
				console.log(res.kakao_account.phone_number);	
				console.log(res.kakao_account.age);	
				console.log(res.kakao_account.gender);	
				console.log(res.kakao_account.birthyear);
				console.log(res.kakao_account.birthday);
				return;
				
				console.log(res);				
				console.log(res.properties.birthday);
				console.log(res.kakao_account.birthday);
				console.log(res.kakao_account.phone_number);
				console.log(res.properties.age_range);
				console.log(res.properties.gender);
				console.log(res.id);
				console.log(res.properties.nickname);
				console.log(res.kakao_account.email);
				return;
				*/
				
				//alert(res.id);
				//alert(res.properties.nickname);
				//alert(res.kakao_account.email);

			  //alert(JSON.stringify(res));
			  $("input[name=id]").val("kakao_" + res.id);
			  //$("input[name=name]").val(res.properties.nickname);
			  $("input[name=name]").val("12341234");
			  $("input[name=email]").val(res.kakao_account.email);
			  $("input[name=gender]").val(res.kakao_account.gender);
			  $("input[name=phone_number]").val(res.kakao_account.phone_number);
			  $("input[name=birthday]").val(res.kakao_account.birthyear + "-" + res.kakao_account.birthday);
			  document.loginForm.action = "/member/kakaologin.af";
			  document.loginForm.submit();
			},
			fail: function(error) {
			  alert(
				'login success, but failed to request user information: ' +
				  JSON.stringify(error)
			  )
			},
		})
	  },
	  fail: function(err) {
		alert(JSON.stringify(err));
	  },
	})
  }
  
</script>	
	
	
