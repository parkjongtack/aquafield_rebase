<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../common/taglibs.jsp" %>

<div id="login_wrap" class="wrap_line">
   	<div class="inner">
		<h2>회원가입</h2>
        <div class="login_box">
			<section id="bx_signup_step">
		    	<div class="con_type1">
			        <div class="signup_step">
			           <ul>
			             <li><div><img src="../common/front/images/ico/ico_signup_step1.png" alt="약관동의"></div><span>약관동의</span></li>
			             <li><div><img src="../common/front/images/ico/ico_signup_step3.png" alt="추가정보"></div><span>정보입력</span></li>
			             <li class="on"><div><img src="../common/front/images/ico/ico_signup_step4.png" alt="가입완료"></div><span>가입완료</span></li>
			           </ul>
			        </div>
					<div class="bx_txt_complete">
		           		<h3><span>${userInfo.NAME }</span> 님, 회원이 되신 것을 환영합니다. </h3>
		           		<p>아쿠아필드 온라인 회원은 다양한 이벤트 정보의 제공은 물론, <br/>현장보다 저렴한 온라인 예매 할인가로 <br/>아쿠아필드의 시설을 이용하실 수 있습니다.</p>
	       			</div>
				</div>
			 	<div class="btn_group btn1">
					 <a href="/member/loginMain.af" class="btn blue2">로그인</a>
			 	</div>
			 </section>
		</div>
	</div>
</div>