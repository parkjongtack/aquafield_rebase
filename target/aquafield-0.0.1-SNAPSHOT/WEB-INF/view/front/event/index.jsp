<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../common/taglibs.jsp" %>
<c:set var="pageParam" value="col=${resultParam.col}&sw=${resultParam.sw}"/>
			<div class="contentArea active">
				<div id="event" class="content">
					<div class="content-inner">
						<div class="tableCell">
							<div class="tit center">
								<h1>아쿠아필드 소식</h1>
								<p>아쿠아필드의 소식과 다양한 프로모션을 만나보세요!</p>
							</div>
							<div class="news-top container">
								<ul class="newsFilter">
									<li class="all active"><a href="#/event/eventIndex.af?filter=all">전체</a></li>
									<li class="notice"><a href="#/event/eventIndex.af?filter=notice">공지사항</a></li>
									<li class="event"><a href="#/event/eventIndex.af?filter=event">이벤트</a></li>
								</ul>
								<c:if test="${AUTHWRITER eq 'W' }">
									<button class="newsWrite btn_pack" onclick="">글쓰기</button>
								</c:if>
								<div class="writeTypeLayer">
									<div class="inner">
										<header>
											게시물을 선택해주세요
										</header>
										<section>
											<ul>
												<li>
													<a href="#/event/write.af?page=notice">
														<img src="/common/front/images/event/news_type1.png">
														<p>에디터(공지)</p>
													</a>
												</li>
												<li>
													<a href="#/event/write.af?page=event">
														<img src="/common/front/images/event/news_type2.png">
														<p>매거진(이벤트)</p>
													</a>
												</li>
											</ul>
										</section>
									</div>
								</div>
							</div>
							<div class="news-list container">
<c:if test="${not empty results}">
								<div class="news-slider">
<c:set var="pageUncount" value="${pu.totalRowCount - ((pu.pageNum - 1) *  pu.pageListSize)}"/>
<c:forEach items="${results}" var="result">
								    <!--<a href="javascript:void(0)" class="item ${result.KIND_CODE}" onclick="window.newsPop = new NewsPopFn({data:{num:'${result.NOTICE_EVENT_UID}', type:'${result.PAGE_TYPE_TXT}'}});">
										<div class="inner">
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
										</div>
										<input type="hidden" name="" class="pageCount" value="${pageUncount}">
								    </a>-->
								    <div class="item ${result.KIND_CODE}" onclick="window.newsPop = new NewsPopFn({data:{num:'${result.NOTICE_EVENT_UID}', type:'${result.PAGE_TYPE_TXT}'}});">
										<div class="inner">
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
										</div>
										<input type="hidden" name="" class="pageCount" value="${pageUncount}">
								    </div>
								    
	<c:set var="pageUncount" value="${pageUncount - 1 }" />
</c:forEach>
							    </div>
</c:if>
<%-- <c:if test="${empty results}"> --%>
<!-- 							    <div class="none_list"> -->
<!-- 							    	<img src="/common/front/images/ico/ico_none_list.png"/> -->
<!-- 							    	<p>등록된 게시물이 없습니다.</p> -->
<!-- 							    </div> -->
<%-- </c:if> --%>
								<div class="none_list">
							    	<img src="/common/front/images/ico/ico_none_list.png"/>
							    	<p>등록된 게시물이 없습니다.</p>
							    </div>
							</div>
						</div>
					</div>
					
					<script type="text/javascript">
					var page = ${resultParam.page};
					var eventParams = "";
					function getNoticeList(){
						var pageCount = $(".pageCount").last().val();
						if(pageCount < 2){
							//alert("더 이상 내용이 없습니다.")
							return;
						}
						page++;
						var target = $(".news-slider");

						function getData(){
							$.ajax({
								type:"GET",
								url:"event/ajaxNoticeEventList.af",
								dataType:"html",
								data:eventParams + "page="+page,
								success : function(data) {
									target.append(data);
									var params = {
											startSlide: page-2,
											slideSelector: '.item.active',
											minSlides: 3,
											maxSlides: 5,
											slideWidth: 300,
											infiniteLoop: false,
											slideMargin: 20,
											pager:false,
											controls: true,
											hideControlOnEnd: true
										};
									news.slider.destroySlider();
									window.news = new NewsFn2(params);
									$(".bx-next").on("click",function(){
										var currentPage = news.slider.getCurrentSlide();
										if(currentPage > page - 2){
											getNoticeList();
										}
									});
								},
								error : function(xhr, status, error) {
									alert("잠시 후에 다시하세요.");
								}
							});

						}
						getData();
					}
					
					
						window.news = new NewsFn();

						$(function(){
							$('.newsWrite').click(function (e) {
								if(!$(this).hasClass('active')){
									$(this).addClass('active');
									$('.writeTypeLayer').show();
								}else{
									$(this).removeClass('active');
									$('.writeTypeLayer').hide();
								}
							});
							
							if($(".pageCount").last().val() > 1){
								getNoticeList();
							}
						});
					</script>
					<script src="http://developers.kakao.com/sdk/js/kakao.min.js"></script>
					<script>
					//-- url 복사
					function urlCopy() {
						var url = location.href;
						$("#urlCopy").val(url);
						if (is_ie()) {
							if(confirm("이 글의 주소를 복사하시겠습니까?")){ window.clipboardData.setData("Text", url); }
						} else {
							temp = prompt("이 글의 주소를 Ctrl+C를 눌러 복사하세요", url);
						}
					}
					function is_ie() {
						if(navigator.userAgent.toLowerCase().indexOf("chrome") != -1) return false;
						if(navigator.userAgent.toLowerCase().indexOf("msie") != -1) return true;
						if(navigator.userAgent.toLowerCase().indexOf("windows nt") != -1) return true;
						return false;
					}
	
					function sendSns(sns,title)
					{
					    var o;
						var url = location.href;
						var txt = title;
					    var _url = encodeURIComponent(url);
					    var _txt = encodeURIComponent(txt);
					    var _br  = encodeURIComponent('\r\n');
					 
					    switch(sns)
					    {
					        case 'facebook':
					            o = {
					                method:'popup',
					                url:'https://www.facebook.com/sharer/sharer.php?u=' + _url +  "&t=" + _txt
					            };
					            break;
					 
					        case 'twitter':
					            o = {
					                method:'popup',
					                url:'https://twitter.com/intent/tweet?text=' + _txt + '&url=' + _url
					            };
					            break;
					 
					        case 'kakao':
					            o = {
					                method:'kakaostory',
					                url:url,
									text:txt
					            };
					            break;
					            
					        default:
					            alert('지원하지 않는 SNS입니다.');
					            return false;
					    }
					
					    switch(o.method)
					    {
					        case 'popup':
					            window.open(o.url);
					            break;
					
					        case 'kakaostory':
								Kakao.Story.share({
								  url: o.url,
								  text: o.text
								});
					
					            break;
					    }
					}
					</script>
					
				</div>
			</div>
