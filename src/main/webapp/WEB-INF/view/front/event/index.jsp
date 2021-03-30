<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../common/taglibs.jsp" %>
<c:set var="pageParam" value="col=${resultParam.col}&sw=${resultParam.sw}"/>
<div class="contentArea active">
	<div id="event" class="content">
		<div class="content-inner">
			<div class="content-box">
				<div class="inner">
					<button class="btn_close layer_close" onclick="window.location='#/event/';">닫기</button>
					<div class="box-content">
						<div class="content on">
							<div class="miniTit">
					            <h1>News</h1>
					            <ul>
					                <li>HOME</li>
					                <li>NEWS</li>
					            </ul>
					        </div>
							<section>
								<header class="paraShowing" data-dirc='y' data-dist='70' data-ease='Power2.easeInOut' data-dura='1'>
									<div class="news-top container">
										<ul class="newsFilter">
											
											<c:choose>
												<c:when test="${AUTHWRITER eq 'W' }">
													<li data-cate="0" style="display:none;"><a href="javascript:fnnewsFilter(0);">전체</a></li>
													<li data-cate="1"><a href="javascript:fnnewsFilter(1);">공지사항</a></li>
													<li data-cate="2"><a href="javascript:fnnewsFilter(2);">이벤트</a></li>
													<!-- <li data-cate="3" style="display:none;"><a href="javascript:fnnewsFilter(3);">미디어</a></li> -->
												</c:when>
												<c:otherwise>
													<li data-cate="0" style="display:none;"><a href="javascript:fnnewsFilter(0);">전체</a></li>
													<li data-cate="1"><a href="javascript:fnnewsFilter(1);">공지사항</a></li>
													<li data-cate="2"><a href="javascript:fnnewsFilter(2);">이벤트</a></li>
													<!-- <li data-cate="3" style="display:none;"><a href="javascript:fnnewsFilter(3);">미디어</a></li> -->
												</c:otherwise>
											</c:choose> 
											
											<%-- 
											<c:choose>
												<c:when test="${AUTHWRITER eq 'W' }">
													<li data-cate="0"><a href="javascript:fnnewsFilter(0);">전체</a></li>
													<li data-cate="1"><a href="javascript:fnnewsFilter(1);">공지사항</a></li>
													<li data-cate="2" class="sub_on"><a href="javascript:fnnewsFilter(2);"  class="mouse_o">이벤트</a>
														<div class="sub_g">
															<ul>
																<li><a href="javascript:fnnewsFilter(2);">진행중인 이벤트</a></li>
																<li><a href="javascript:fnnewsFilter(4);">지난 이벤트</a></li>
															</ul>
														</div>
													</li>
													<li data-cate="3"><a href="javascript:fnnewsFilter(3);">미디어</a></li>
												</c:when>
												<c:otherwise>
													<li data-cate="0"><a href="javascript:fnnewsFilter(0);">전체</a></li>
													<li data-cate="1"><a href="javascript:fnnewsFilter(1);">공지사항</a></li>
													<li data-cate="2" class="sub_on"><a href="javascript:fnnewsFilter(2);"  class="mouse_o">이벤트</a>
														<div class="sub_g">
															<ul>
																<li><a href="javascript:fnnewsFilter(2);">진행중인 이벤트</a></li>
																<li><a href="javascript:fnnewsFilter(4);" >지난 이벤트</a></li>
															</ul>
														</div>
													</li>
													<li data-cate="3"><a href="javascript:fnnewsFilter(3);">미디어</a></li>
												</c:otherwise>
											</c:choose> --%>
										</ul>
										<c:if test="${AUTHWRITER eq 'W' }">
											<a href="#/event/write.af?page=notice&pointCode=${pointCode }" class="newsWrite btn_pack">글쓰기</a>
										</c:if>
										<!-- <script>
											$(function(){
												$(".sub_on").hover(function(){
													$(".sub_g").stop().slideDown(300);
												}, function(){
													$(".sub_g").stop().slideUp(300);
												});
											});
										</script> -->
									</div>
								</header>
								
								<div class="news-list container iscContent" style="height:550px;">
									<div class="inner">
										<ul class="grid">
											<!-- Ajax Load -->
										</ul>
										<div class="none_list container">
									    	<div class="inner">
									    		<img src="/common/front/images/ico/ico_none_list.png" alt="게시물" />
									    		<p>등록된 게시물이 없습니다.</p>
									    	</div>
									    </div>
									</div>
								</div>
								<footer class="paraShowing" data-dirc='y' data-dist='70' data-ease='Power2.easeInOut' data-dura='1' data-delay='0.5'>
									<div class="list-paging">
										<%-- <a href="#" class="btn prevPage disable">PREV PAGE</a>
										<a href="#" class="btn prev disable">PREV</a>
										<a href="#/event/eventIndex.af?cate=all&page=1&pointCode=${pointCode }" data-page="1" class="num on">1</a>
										<a href="#/event/eventIndex.af?cate=all&page=2&pointCode=${pointCode }" data-page="2" class="num">2</a>
										<a href="#" class="btn next">NEXT</a>
										<a href="#" class="btn nextPage">NEXT PAGE</a> --%>
									</div>
								</footer>
							</section>
							<input type="hidden" id="authWriter" name="authWriter" value="${AUTHWRITER}"/>
							<input type="hidden" id="pointCode" name="pointCode" value="${pointCode}"/>
							<input type="hidden" id="page" name="page" value="${page}"/>
							<input type="hidden" id="cate" name="cate" value="${cate}"/>
							<script type="text/javascript">
							
								//뉴스페이지
								var NewsFn = function(params){
									var _this = this, grid, page, cate, pointCode;
									this.popBx = '#news_popup';
									this.grid = $(".news-list .grid");
									this.init = function(o){
										//if($("#authWriter").val() == "W"){
										//	_this.page = "${page}";
										//}else{
											_this.page = getParameter("page");
										//}
										_this.cate = getParameter("cate");

										_this.pointCode = $("#pointCode").val();
										if(!_this.page) _this.page = 1;
										if(!_this.cate) _this.cate = 0;

										//console.log("###cate ::: " + _this.cate);
										//console.log("siblings info ::: " +$('.newsFilter li').eq(_this.cate).siblings('.active'));
										$('.newsFilter > li').eq(_this.cate).siblings('.active').removeClass('active').end().addClass('active');

										_this.getList();
									}

									this.getList = function(o){
										var thisCate =  _this.cate;
										
										$.ajax({
											type:"GET",
											url:"/event/ajaxNoticeEventList.af",
											dataType:"html",
											data: { page : _this.page, cate : _this.cate, pointCode : _this.pointCode},
											success : function(data) {
												var delay = 1000;
												if(_this.grid.find('.item').length > 0) delay = 0;
												_this.grid.hide();
												_this.grid.html(data);
												
												//news.setPaging({ totalCount: 30, recordCount: 8, perPage: 10 });
												
												_this.listCheck();
												
												_this.grid.imagesLoaded(function(){
													_this.grid.show();
													
													if(_this.cate != '0') {
														_this.grid.find('.item.'+_this.cate).siblings().hide().end().show();	
													}

													_this.showListAni(delay); 

													_this.grid.find('.item > .inner').hover(
														function() {
															$(this).parent().addClass('active');
															TweenMax.to($(this).find('.txtArea'),0.5,{bottom: 0, ease: Power2.easeInOut});
														}, function() {
															$(this).parent().removeClass('active');
															TweenMax.to($(this).find('.txtArea'),0.5,{bottom: -100+'%', ease: Power3.easeInOut});
														}
													);
												});
												$('.newsFilter> li').eq(_this.cate).siblings('.active').removeClass('active').end().addClass('active');
											},
											error : function(xhr, status, error) {
												alert("잠시 후에 다시하세요.");
											}
										});
									}

									this.setPaging = function(o){

										var paging = $(".list-paging"),
											totalCount = o.totalCount, //게시물 총갯수
											recordCount = o.recordCount, //페이지당 게시물 갯수
											//perPage = o.perPage, //페이지 표시 갯수
											perPage = 10, //페이지 표시 갯수
											currentIndex = parseInt(_this.page), //현재페이지
											currentCate = _this.cate, //현재카테고리
											totalIndexCount = Math.ceil(totalCount / recordCount),
											first = (parseInt((currentIndex-1) / perPage) * perPage) + 1,
						    				last = (parseInt(totalIndexCount/perPage) == parseInt((currentIndex-1)/perPage)) ? totalIndexCount%perPage : perPage;

										paging.empty();
										var prevStr = "";
									    var nextStr = "";
									    var firstStr = "";
									    var lastStr = "";
									    var pageStr = "";

									    var authWriter = $("#authWriter").val();

									    if(last<=perPage){
									    	if(currentIndex > first){
									    		firstStr += '<a href="#/event/eventIndex.af?cate='+currentCate+'&page='+first+'" class="btn prevPage">PREV PAGE</a>';
									    	}else{
									    		firstStr += '<a class="btn prevPage disable">PREV PAGE</a>';
									    	}
									    }else{
									    	if(first-perPage > 0) {
												//if(authWriter == "W"){
												//	firstStr += '<a href="#/event/eventIndex.af?cate='+currentCate+'&bPage='+(first-perPage)+'&pointCode='+$("#pointCode").val()+'" class="btn prevPage">PREV PAGE</a>';
												//}else{
													firstStr += '<a href="#/event/eventIndex.af?cate='+currentCate+'&page='+(first-perPage)+'" class="btn prevPage">PREV PAGE</a>';
												//}
											}else{
												firstStr += '<a class="btn prevPage disable">PREV PAGE</a>';
											}
									    }


										if(currentIndex-1 > 0) {
											//if(authWriter == "W"){
											//	prevStr += '<a href="#/event/eventIndex.af?cate='+currentCate+'&bPage='+(currentIndex-1)+'&pointCode='+$("#pointCode").val()+'" class="btn prev">PREV</a>';
											//}else{
												prevStr += '<a href="#/event/eventIndex.af?cate='+currentCate+'&page='+(currentIndex-1)+'" class="btn prev">PREV</a>';
											//}

										}else{
											prevStr += '<a class="btn prev disable">PREV</a>';
										}

										if(currentIndex+1 <= totalIndexCount) {
											//if(authWriter == "W"){
											//	nextStr += '<a href="#/event/eventIndex.af?cate='+currentCate+'&bPage='+(currentIndex+1)+'&pointCode='+$("#pointCode").val()+'" class="btn next">NEXT</a>';
											//}else{
												nextStr += '<a href="#/event/eventIndex.af?cate='+currentCate+'&page='+(currentIndex+1)+'" class="btn next">NEXT</a>';
											//}
										}else{
											nextStr += '<a class="btn next disable">NEXT</a>';
										}

										if(last <= perPage){
											if(currentIndex < last){
												lastStr += '<a href="#/event/eventIndex.af?cate='+currentCate+'&page='+last+'" class="btn nextPage">NEXT PAGE</a>';
											}else{
												lastStr += '<a class="btn nextPage disable">NEXT PAGE</a>';
											}
										}else{
											if(first+perPage <= totalIndexCount) {
												//if(authWriter == "W"){
												//	lastStr += '<a href="#/event/eventIndex.af?cate='+currentCate+'&bPage='+(first+perPage)+'&pointCode='+$("#pointCode").val()+'" class="btn nextPage">NEXT PAGE</a>';
												//}else{
													lastStr += '<a href="#/event/eventIndex.af?cate='+currentCate+'&page='+(first+perPage)+'" class="btn nextPage">NEXT PAGE</a>';
												//}

											}else{
												lastStr += '<a class="btn nextPage disable">NEXT PAGE</a>';
											}
										}

									    /* for(var i=first; i<(first+last); i++){ */
									    for(var i=first; i<(first+last); i++){
																				    	
									        if(i != currentIndex){
									        	//if(authWriter == "W"){
									        	//	pageStr += '<a href="#/event/eventIndex.af?cate='+currentCate+'&bPage='+i+'&pointCode='+$("#pointCode").val()+'" data-page="'+i+'" class="num">'+i+'</a>';
									        	//}else{
									        		pageStr += '<a href="#/event/eventIndex.af?cate='+currentCate+'&page='+i+'" data-page="'+i+'" class="num">'+i+'</a>';
									        	//}
									        }
									        else{
									        	//if(authWriter == "W"){
									        	//	pageStr += '<a href="#/event/eventIndex.af?cate='+currentCate+'&bPage='+i+'&pointCode='+$("#pointCode").val()+'" data-page="'+i+'" class="num on">'+i+'</a>';
									        	//}else{
									        		pageStr += '<a href="#/event/eventIndex.af?cate='+currentCate+'&page='+i+'" data-page="'+i+'" class="num on">'+i+'</a>';
									        	//}
									        }
									    }
									    paging.append(firstStr+ prevStr + pageStr + nextStr + lastStr);

										$(".news-paging").find('.num').click(function(e) {
											$(this).siblings('.on').removeClass('on').end().addClass('on');
										});
									}

									this.showListAni = function(t){
										var item = _this.grid.find('.item'),
											itemLength = item.length;
										TweenMax.set(item,{ scale: 0 });

										setTimeout(function(){
											item.each(function(i) {
												var _item = $(this);
												setTimeout(function(){
													TweenMax.to(_item, 0.5, {scale : 1, ease: Power2.easeInOut});
												}, 100*i);
											});
										},t);
									}

									this.listCheck = function(o){
										var item = _this.grid.find('.item').length;
										if(item <= 0){
											$('.none_list').css('display', 'table');
											 _this.grid.css('display', 'none');
										}else{
											$('.none_list').css('display', 'none');
											 _this.grid.css('display', 'block');
										}
									};

									this.init();

								}
								var news;
								window.news = new NewsFn();


								var NewsPopFn = function(params){
									params = params || {};
									var _this = this, popInner, contOutBx,  moveMotion, moving, isc, t, pointCode;
									this.popBx = '#news_popup';

									this.setCont = function(o){
										o = o || {};
										if(o.url === undefined) throw new Error('URL 주소가 필요합니다.');
										_this.t = _this.popBx.find('.content:not(".on")').eq(0);
										$.ajax({
											url : o.url,
											type : params.type || 'GET',
								            data : o.data || {},
											dataType : 'html',
											success : function(data){
												_this.t.html(data).siblings('.on').removeClass('on').end().addClass('on');
												_this.t.imagesLoaded(function(){
													var vH = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
													var h = _this.t.outerHeight();
								                    var ch = contOutBx.height();
								                    var popT = parseInt(_this.popBx.css('top'));
								                    var innerT = parseInt(popInner.css('top'));
													if(!moveMotion){
														moveMotion = !moveMotion;
														o.callFn && o.callFn();
													}else{
								                        var scl = (document.documentElement && document.documentElement.scrollTop) || document.body.scrollTop;
								                        var tgH = innerT+(ch-h)/2;
								                        //TweenMax.to(popInner, 0.6, {'top' : (popT + tgH <= 0 ? -popT : tgH), ease: Power3.easeInOut});
								                        TweenMax.to(_this.popBx, 0.6, {'top' : ( h > vH ? scl : (vH-h)/2+scl ), ease: Power3.easeInOut});
														TweenMax.to(contOutBx,0.6,{'height' : h, ease: Power3.easeInOut, onComplete : function(){
															o.callFn && o.callFn();
								                            contOutBx.css('height','');
								                            moving = false;
														}});
													}

													_this.isc = _this.t.find('.iscContent');

													_this.resize();
													$(window).off('resize', _this.resize).on('resize', _this.resize);
												});
											},
											error : function(a,b,c){
												alert('error : ' + c);
								                moving = false;
											}
										})
									};

									this.resize = function(){
										var vH = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
										if(_this.isc) _this.isc.css('height', vH-_this.t.find('header').outerHeight()-_this.t.find('footer').outerHeight());
									}

									this.showPop = function(o){
										_this.pointCode = $("#pointCode").val();
										//console.log("###### data.type ::: "+ params.data.type);
										params.url = '/event/'+params.data.type+'View.af?num='+params.data.num+'&pointCode='+_this.pointCode;
										ajaxShowPopCont({
									        url : '/event/eventPopup.af',
								            data : params.data || {},
								            resize : false,
											onStart : function(bx, showFn){
												_this.popBx = $(_this.popBx);
								                popInner = _this.popBx.find('> .inner');
												contOutBx = _this.popBx.find('#bx_multi_content');
												_this.setCont({callFn : showFn, url : params.url || '/event/eventView.af?num='+params.data.num});
											}
									    });
									};

									this.showPop(params);
								}
								var newsPop;
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

								</script>
								<script src="https://developers.kakao.com/sdk/js/kakao.min.js"></script>
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

								function fnnewsFilter(cateNum){
									console.log("####auth:::"+$("#authWriter").val());
									//if($("#authWriter").val() === "W"){
										//관리자 계정일 경우에는 page 파라미터가 안먹히는 현상이 발생됨. 아마 세션으로 물고 있는값이 존재하지 않아 생긴문제로 보여지는데
									//	location.href = "#/event/eventIndex.af?cate="+$(this).data("cate")+"&bPage=1&pointCode="+$("#pointCode").val();
									//}else{
										/* location.href = "#/event/eventIndex.af?cate="+$(this).data("cate")+"&page=1"; */
									//}

									location.href = "#/event/eventIndex.af?cate="+cateNum+"&page=1";	
								}
								
								function point01(num) {
									window.location.reload();
									if(num == 1) {
										location.href = "/hanam/index.af#/event/eventIndex.af?cate=1";
									} else {
										location.href = "/hanam/index.af#/event/eventIndex.af?cate=2";																			
									}
								}
								
								function point03(num) {
									window.location.reload();
									if(num == 1) {
										location.href = "/goyang/index.af#/event/eventIndex.af?cate=1";
									} else {
										location.href = "/goyang/index.af#/event/eventIndex.af?cate=2";																			
									}
								}

								function point05(num) {
									window.location.reload();
									if(num == 1) {
										location.href = "/anseong/index.af#/event/eventIndex.af?cate=1";
									} else {
										location.href = "/anseong/index.af#/event/eventIndex.af?cate=2";																			
									}
								}
								</script>
						</div>
					</div>
				</div>
			</div>
			<div class="content-cate">
				<div class="inner">
					<div class="cate_title">
						<div class="inner">
							<p>04</p>
							<h1>News</h1>
						</div>
					</div>
					<div class="cate_list iscContent cate01">
						<div class="inner">
							<ul>
								<li class="cate_notice">
									<!-- <a href="#/event/eventIndex.af?cate=1"> -->
									
									<c:if test="${POINT_CODE eq 'POINT01'}">
										<a href="javascript:point01(1)";> 
									</c:if>
									<c:if test="${POINT_CODE eq 'POINT03'}">
										<a href="javascript:point03(1)">
									</c:if>
									<c:if test="${POINT_CODE eq 'POINT05'}">
										<a href="javascript:point05(1)">
									</c:if>
										<div class="txt">
											<div class="inner">
												<strong>공지사항</strong>
												<p>아쿠아필드 서비스와 관련된<br/> 최신 정보들을 확인할 수 있습니다.</p>
												<div class="more"><img src="/common/front/images/btn/btn_more_b.png" alt="바로가기"></div>
											</div>
										</div>
									</a>
								</li>
								<li class="cate_event">
									<!-- <a href="#/event/eventIndex.af?cate=2"> -->
									
									<c:if test="${POINT_CODE eq 'POINT01'}">
										<a href="javascript:point01(2)";>
									</c:if>
									<c:if test="${POINT_CODE eq 'POINT03'}">
										<a href="javascript:point03(2)";>
									</c:if>
									<c:if test="${POINT_CODE eq 'POINT05'}">
										<a href="javascript:point05(2)";>
									</c:if>
									
										<div class="txt">
											<div class="inner">
												<strong>이벤트</strong>
												<p>할인, 공연정보 등 다양한 이벤트<br/> 소식들을 확인할 수 있습니다.</p>
												<div class="more"><img src="/common/front/images/btn/btn_more_w.png" alt="바로가기"></div>
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
	</div>
</div>