<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../common/taglibs.jsp" %>
<div class="newsArea">
<%-- 	<h2>EVENT & NOTICE</h2>
	<!-- <div class="mainNews-slider">
		<a href="javascript:void(0)" class="item event" onclick="window.newsPop = new NewsPopFn({data:{idx:'6', type:'event'}});">
			<div class="imgArea">
				<img src="/common/front/images/event/news_cont01.jpg">
			</div>
			<div class="txtArea">
				<div class="inner">
					<h1 class="title">가을 신메뉴 오픈</h1>
					<div class="date">기간 : 17.07.06 ~ 17.07.25</div>
				</div>
			</div>
		</a>
		<a href="javascript:void(0)" class="item notice" onclick="window.newsPop = new NewsPopFn({data:{idx:'5', type:'notice'}});">
			<div class="imgArea">
				<img src="/common/front/images/event/news_cont02.jpg">
			</div>
			<div class="txtArea">
				<div class="inner">
					<h1 class="title">워터파크 요가강습</h1>
					<div class="date">등록일 : 2017.07.06</div>
				</div>
			</div>
		</a>
		<a href="javascript:void(0)" class="item notice" onclick="window.newsPop = new NewsPopFn({data:{idx:'4', type:'notice'}});">
			<div class="imgArea">
				<img src="/common/front/images/event/news_cont03.jpg">
			</div>
			<div class="txtArea">
				<div class="inner">
					<h1 class="title">아쿠아필드 워터파크</h1>
					<div class="date">등록일 : 2017.07.06</div>
				</div>
			</div>
		</a>
		<a href="javascript:void(0)" class="item notice" onclick="window.newsPop = new NewsPopFn({data:{idx:'3', type:'notice'}});">
			<div class="imgArea">
				<img src="/common/front/images/event/news_cont04.jpg">
			</div>
			<div class="txtArea">
				<div class="inner">
					<h1 class="title">워터파크 요가강습 스파시설...</h1>
					<div class="date">등록일 : 2017.07.06</div>
				</div>
			</div>
		</a>
	</div> -->
<c:if test="${not empty results}">
								<div class="mainNews-slider">
<c:set var="pageUncount" value="${pu.totalRowCount - ((pu.pageNum - 1) *  pu.pageListSize)}"/>
<c:forEach items="${results}" var="result">
								    <a href="javascript:void(0)" class="item ${result.KIND_CODE}" onclick="window.newsPop = new NewsPopFn2({data:{num:'${result.NOTICE_EVENT_UID}', type:'${result.PAGE_TYPE_TXT}'}});">
										<div class="imgArea">
											<c:choose>
												<c:when test="${result.DATE_STAT eq 'Y'}"><div class="tagIco"><img src="/common/front/images/ico/ico_event_now.png"></div></c:when>
												<c:when test="${result.DATE_STAT eq 'Y'}"><div class="tagIco"><img src="/common/front/images/ico/ico_event_end.png"></div></c:when>
											</c:choose>
											<c:if test="${not empty result.FILE_LIST_FULL}">
								    		<img src="${result.FILE_LIST_FULL}">
								    		</c:if>
								    	</div>
										<div class="txtArea">
											<div class="inner">
												<h1 class="title">${result.TITLE}</h1>
												<c:if test="${result.KIND eq '2'}">
												<div class="date">기간 : ${result.START_DATE} ~ ${result.END_DATE}</div>
												<div class="location">장소 : ${result.LOCATION}</div>
												</c:if>
												<div class="tag">${result.KIND_NAME}</div>
												<div class="icon"></div>
											</div>
										</div>
										<input type="hidden" name="" class="pageCount" value="${pageUncount}">
								    </a>
	<c:set var="pageUncount" value="${pageUncount - 1 }" />
</c:forEach>
							    </div>
</c:if>
								<div class="none_list">
							    	<img src="/common/front/images/ico/ico_none_list.png"/>
							    	<p>등록된 게시물이 없습니다.</p>
							    </div>
							</div>
						</div>
					</div>
	<script type="text/javascript">
		var mainNewsSlider = $('.mainNews-slider').bxSlider({
			slideSelector: '.item',
			minSlides: 1,
			maxSlides: 2,
			slideWidth: 280,
			infiniteLoop: false,
			slideMargin: 20,
			pager:false,
			controls: true
		});
		
		$(window).resize(function(e){
			winH = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
			$("#main .slider-item, .bx-viewport, .content-inner").height(winH-$('#footerTop').height());
			var winW = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
			var boxW = winW - 40;
			if(winW < 640) {				
				if($(".mainNews-slider").length < 1) return;
				mainNewsSlider.reloadSlider({
					slideSelector: '.item',
					minSlides: 1,
					maxSlides: 2,
					slideWidth: (boxW/2) - 20,
					infiniteLoop: false,
					slideMargin: 20,
					pager:false,
					controls: true
				});
			}
		}).resize();
	</script>
	<style type="text/css">
		#main .m_content .newsArea .none_list { text-align: center; margin-bottom: 50px; }
		#main .m_content .newsArea .none_list p { margin-top: 20px; }
	</style>
</div> --%>
<div class="mainMenuArea">
	<ul>
		<li>
			<a href="#/about/concept/index.af">
				<img src="/common/front/images/main/m_ico_01.png">
				<p>소개</p>
			</a>
		</li>
		<li>
			<a href="#/facilities/index.af?pageing=0">
				<img src="/common/front/images/main/m_ico_02.png">
				<p>시설안내</p>
			</a>
		</li>
		<li>
			<a href="#/guide/index.af?page=1">
				<img src="/common/front/images/main/m_ico_03.png">
				<p>이용안내</p>
			</a>
		</li>
		<li>
			<a href="#/about/location/index.af">
				<img src="/common/front/images/main/m_ico_04.png">
				<p>찾아오시는 길</p>
			</a>
		</li>
	</ul>
</div>