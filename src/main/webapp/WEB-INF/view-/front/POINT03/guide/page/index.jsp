<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../../../common/taglibs.jsp" %>
<% String dep2 = request.getParameter("page")+".jsp"; %>
<div class="contentArea">

	<div id="guide" class="content">
		<div class="content-inner">
			<div class="content-box">
				<div class="inner">
					<button class="btn_close layer_close" onclick="window.location='#/guide/';">닫기</button>
					<div class="box-content">
						<div class="content on">
							<jsp:include page="<%= dep2 %>" flush="true" />
						</div>
					</div>
				</div>
			</div>
			<div class="content-cate">
				<div class="inner">
					<div class="cate_title">
						<div class="inner">
							<p>03</p>
							<h1>Operation<br/> Guide</h1>
						</div>
					</div>
					<div class="cate_list iscContent cate03">
						<div class="inner">
							<ul>
								<li class="cate_useinfo">
									<a href="#/guide/index.af?page=1">
										<div class="txt">
											<div class="inner">
												<strong>이용정보</strong>
												<p>시설 이용시간 및 이용요금<br/> 정책을 확인할 수 있습니다.</p>
											</div>
										</div>
									</a>
								</li>
								<li class="cate_policy">
									<a href="#/guide/index.af?page=2">
										<div class="txt">
											<div class="inner">
												<strong>운영정책</strong>
												<p>시설물 이용시 유의사항을<br/> 확인할 수 있습니다.</p>
											</div>
										</div>
									</a>
								</li>
								<li class="cate_charges">
									<a href="#/guide/index.af?page=3">
										<div class="txt">
											<div class="inner">
												<strong>정산안내</strong>
												<p>아쿠아필드 이용방법을<br/> 입장 순서별로 안내합니다.</p>
											</div>
										</div>
									</a>
								</li>
								<li class="cate_rental">
									<a href="#/guide/index.af?page=4">
										<div class="txt">
											<div class="inner">
												<strong>렌탈안내</strong>
												<p>루프탑풀과 찜질스파의 대여상품<br/> 요금표를 확인할 수 있습니다.</p>
											</div>
										</div>
									</a>
								</li>
								<li class="cate_seq">
									<a href="#/guide/index.af?page=5">
										<div class="txt">
											<div class="inner">
												<strong>이용순서</strong>
												<p>아쿠아필드 이용방법을<br/> 입장 순서별로 안내합니다.</p>
											</div>
										</div>
									</a>
								</li>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
		<script type="text/javascript"></script>
	</div>
</div>
