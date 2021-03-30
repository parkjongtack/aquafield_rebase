<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<%@ include file="../../common/taglibs.jsp" %>
<div class="contentArea" style="display:none;">

</div>
<div class="contentArea">

</div>
	<div id="login_wrap">
		<!-- <div class="login_top">
			<div class="logo">
				<img src="../common/front/images/common/logo.png" alt="아쿠아필드">
			</div>
		</div> -->
	   	<div class="inner">
			<h2>내정보 찾기</h2>
			<ul class="tab_menu">
	           <li class="active" rel="tab1"><a href="javascript:void(0);">아이디 찾기</a></li>
	           <li rel="tab2"><a href="/member/findMemberPwd.af">비밀번호 찾기</a></li>
	        </ul>
	        
	        <div class="login_box">
				<div class="form_type1" style="display:block;" id="tab1">
		       	    <ul>
		                <li class="form_type1_tit">
			                <p>아이디 찾기 결과</p>
		                </li>
			        </ul>
					<div class="form_tit">
						<c:choose>
							<c:when test="${empty searchId || searchId eq ''}">
								<p>회원님의 정보가 없습니다.</p>
							</c:when>
							<c:otherwise>
								<p>회원님의 아이디는</p>
								<strong>${searchId}</strong><span>입니다.</span>
							</c:otherwise>
						</c:choose>
					</div>
					<div class="btn_group">
						<a href="/member/findMemberPwd.af" class="btn gray">비밀번호 찾기</a>
						<a href="/member/loginMain.af" class="btn gray">로그인 하러가기</a>
					</div>
				</div>
				
				<script type="text/javascript">
					//tab
					$(function() {
						$(".tab_menu .form_type1:first").show();
						$("ul.tab_menu li").click(function() {
							$("ul.tab_menu li").removeClass("active")
							$(this).addClass("active")
							$(".form_type1").hide()
							var activeTab = $(this).attr("rel");
							$("#"+activeTab).fadeIn()
						});
					});
				</script>
	        </div>
    	</div>
	</div>
