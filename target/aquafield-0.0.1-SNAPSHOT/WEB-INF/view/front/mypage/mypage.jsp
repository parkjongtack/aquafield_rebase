<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../common/taglibs.jsp" %>
<div class="contentArea active">
	<div id="mypage" class="content">
		<div class="content-inner">
			<div class="content-box">
				<div class="inner">
					<div class="box-tab">
						<ul>
							<li class="myinfo active"><a href="javascript:void(0);" onclick="contentBox.tabCont({url : '/mypage/pwd.af', target:this})"><span>내정보</span></a></li>
							<li class="reserve"><a href="javascript:void(0);" onclick="contentBox.tabCont({url : '/mypage/reserve.af', target:this})"><span>예약정보 관리</span></a></li>
<!-- 						    <li class="res"><a href="javascript:void(0);" onclick="alert('준비중입니다.');"><span>예약정보 관리</span></a></li> -->
							<li class="cs"><a href="javascript:void(0);" onclick="contentBox.tabCont({url : '/mypage/cs.af', target:this})"><span>1:1 문의내역</span></a></li>
						</ul>
					</div>
					<div class="box-content">
						<div class="content"></div>
          					<div class="content"></div>
					</div>
				</div>
			</div>
		</div>
		<script type="text/javascript">
			window.contentBox = new contentBoxFn();
			contentBox.setCont({url : '/mypage/pwd.af'});
		</script>
	</div>
</div>