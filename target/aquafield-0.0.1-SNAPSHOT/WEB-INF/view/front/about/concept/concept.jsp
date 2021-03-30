<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../../common/taglibs.jsp" %>
    <section>
        <div class="iscContent">
            <div class="inner">
                <article>
                    <!-- <img src="/images/about/concept.jpg" onclick="window.videoPop = new VideoPopFn();"> -->
                    <div class="row">
                        <div class="col s6 img">
                            <img class="concept_img" src="/common/front/images/about/concept_bg.png">
                        </div>
                        <div class="col s6 txt">
                            <h4><span>01</span> 소개</h4>
                            <h1><img src="/common/front/images/about/concept_tit.png"></h1>
                            <h2 class="subtit"><span style="white-space: nowrap" class="s2">생각을 비우다, </span><span style="white-space: nowrap">여유를 채우다</span></h2>
                            <h3>-아쿠아필드</h3>
                            <p class="wordBraek">청량한 물소리와 하늘 빛 자연을 한 눈에 품은 아쿠아필드는 
					                            감각 있는 사람들의 오감만족 힐링 클럽입니다. <span class="break_word"></span>
					                            하늘과 맞닿은 인피니티풀에서 느긋이 즐기고 신비로운 오로라부터 
					                            구름 위의 휴식까지 8가지 테마의 찜질스파를 경험하는 곳 -
					                            들어서는 공간마다 색다른 휴식이 기다리는 아쿠아필드에서 
					                            삶의 진짜 멋과 여유를 누리십시오.</p>
                            <div class="btn center">
                                <button class="btn_pack btn_mo white" onclick="window.videoPop = new VideoPopFn({url: '/about/concept/video01.af'});"><img src="/common/front/images/ico/ico_concept_play.png">영상보기</button>
                            </div>
                        </div>
                    </div>
                </article>
            </div>
        </div>
        <script type="text/javascript">
        	$('.wordBraek').wordBreakKeepAll();
//             setTimeout(function(){
//                 window.videoPop = new VideoPopFn({url: '/about/concept/video01.af'});
//             },50);
            $(window).resize(function(){
            	$('.concept_img').height($('.content-box').height()-80);
            }).resize();
        </script>
    </section>