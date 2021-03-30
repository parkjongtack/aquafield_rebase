<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../common/taglibs.jsp" %>
<c:set var="pageUrl" value="/service/${param.page}.af"/>
<div class="contentArea active">
	<div id="service" class="content">
		<div class="content-inner">
			<div class="content-box">
				<div class="inner">
					<div class="box-header">
						<div class="miniTit">
					        <ul>
					            <li>HOME</li>
					            <li>SERVICE</li>
					            <li>약관 및 방침</li>
					        </ul>
					    </div>
					    <div class="box-tab">
							<ul>
								<li class="myinfo<c:if test="${param.page== 'terms2'}"> active</c:if>"><a href="#/service/index.af?page=terms2"><span>아쿠아필드 이용약관</span></a></li>
								<li class="reserve<c:if test="${param.page == 'terms'}"> active</c:if>"><a href="#/service/index.af?page=terms"><span>홈페이지 이용약관</span></a></li>
								<li class="cs<c:if test="${param.page == 'privacy'}"> active</c:if>"><a href="#/service/index.af?page=privacy"><span>개인정보 처리방침</span></a></li>
								<li class="cs<c:if test="${param.page == 'video'}"> active</c:if>"><a href="#/service/index.af?page=video"><span>영상정보처리 기기</span></a></li>
								<li class="cs<c:if test="${param.page == 'communication'}"> active</c:if>"><a href="#/service/index.af?page=communication"><span>통신판매사업자정보</span></a></li>
							</ul>
						</div>
					</div>
					<div class="box-content">
						<div class="content on">
							<jsp:include page="${pageUrl}" flush="true" />
						</div>
					</div>
				</div>
			</div>
		</div>
		<script type="text/javascript">
			// window.contentBox = new contentBoxFn();
			// var p = getParameter("page");
			// contentBox.setCont({url : '/service/'+p+'.af'});
		</script>
	</div>
</div>
