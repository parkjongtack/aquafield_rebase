<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../common/taglibs.jsp" %>
<c:set var="pageUrl" value="/mypage/${param.page}.af"/>
<div class="contentArea active">
	<div id="mypage" class="content">
		<div class="content-inner">
			<div class="content-box">
				<div class="inner">
					<div class="box-header">
						<div class="miniTit">
				            <ul>
				                <li>HOME</li>
				                <li>MY PAGE</li>
				            </ul>
				        </div>
						<div class="box-tab">
							<ul>
								<li class="myinfo<c:if test="${param.page == 'pwd'}"> active</c:if>"><a href="#/mypage/mypage.af?page=pwd"><span>내정보</span></a></li>
								<li class="reserve<c:if test="${param.page == 'reserve'}"> active</c:if>"><a href="#/mypage/mypage.af?page=reserve"><span>예약정보 관리</span></a></li>
								<li class="cs<c:if test="${param.page == 'cs'}"> active</c:if>"><a href="#/mypage/mypage.af?page=cs"><span>1:1 문의내역</span></a></li>
							</ul>
						</div>
					</div>
					<div class="box-content">
						<div class="content on">
							<jsp:include page="${pageUrl}" flush="true" />
						</div>
					</div>
					<div class="box-popupLayer pop_type2">
						<div class="inner">
							<button class="btn_close layer_close">닫기</button>
							<div class="box-layer_content">

							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<script type="text/javascript">
			//setTimeout(contentBox.tabContResize, 0);
			//window.contentBox = new contentBoxFn();
			//contentBox.setCont({url : '/mypage/pwd.af'});
		</script>
	</div>
</div>
