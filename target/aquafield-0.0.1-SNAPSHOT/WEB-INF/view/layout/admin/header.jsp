<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../common/taglibs.jsp" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<tiles:importAttribute name="ADMINFO" />
<tiles:importAttribute name="resultsTopMenu" />
<header id="header">
	<div class="inner">
		<section class="gnb_area">
			<div class="float_box">
				<h1 class="logo"><a href="/admin/index.do"><img src="/common/admin/images/common/h_logo.png" alt="로고"></a> <span>Administrator Mode</span></h1>
				<ul id="gnb" class="clearfix">
					<li class="my_name">
						<a href="javascript:;" onclick="showPopEditMyProfilePanel()"><span class="name">${ADMINFO.SESSION_ADMIN_NM}</span></a><span> 계정으로 로그인하셨습니다.</span>
						<!-- <a href="javascript:;" onclick="show_my_menu();"><span class="name">${SESS_ADMIN_NAME}</span></a><span> 계정으로 로그인하셨습니다.</span> -->
						<div class="mylayer">
							<ul>
								<li><a href="">계정 정보</a></li>
								<li><a href="">패스워드 변경</a></li>
								<li class="last"><a href="/admin/logout.af">log Out</a></li>
							</ul>
						</div>
						<button class="btn_logout" onclick="location.href='/admin/logout.af'">Log out</button>
					</li>
					<li class="todolist clearfix">
						<h2>To Do List</h2>
						<ul>
							<!-- <li>
								<a href=""><img src="images/common/ico_calender.gif"></a>
							</li> -->
							<li><a href="javascript:;"><img src="/common/admin/images/common/ico_monitor.png"><span class="alert2">(217)</span></a></li>
							<li>
								<a href=""><img src="/common/admin/images/common/ico_note.png"><span class="alert">2</span></a>
								<div class="alert_mg"><div>2개의 문의가 있습니다.<br/> <a href="/admin/customer/index.jsp"><span>바로가기</span></a></div></div>
							</li>
						</ul>
					</li>
				</ul>
			</div>
		</section>
		<div id="lnb">
			<ul class="clearfix">
<c:forEach items="${resultsTopMenu}" var="result">
				<li class="${result.MENU_CODE_CLASS}"><a href="${result.MENU_URL}" class="btn_mo">${result.MENU_NM}</a></li>
</c:forEach>
			</ul>
		</div>
	</div>
</header>
<script type="text/javascript">
	$(function(){
		var lnb = $("#lnb");
<%-- 		lnb.find('li.<%=pageIdx%>').addClass('on'); --%>
	});
</script>