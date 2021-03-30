<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../../../common/taglibs.jsp" %>
    <section id="concept">
        <div class="miniTit">
            <h1>컨셉소개</h1>
            <ul>
                <li>HOME</li>
                <li>ABOUT</li>
                <li>01 컨셉소개</li>
            </ul>
        </div>
        <c:if test="${POINT_CODE == 'POINT01'}">
        <div class="iscContent">
            <div class="inner">
                <div class="row">
                    <div class="col s6 txt paraShowing" data-dirc='x' data-dist='-150' data-ease='Power2.easeInOut' data-dura='1'>
                        <h1>Feeling & Healing</h1>
                        <h2 class="subtit"><span style="white-space: nowrap">생각을 비우다, </span><span style="white-space: nowrap">여유를 채우다</span><br/><span style="white-space: nowrap">아쿠아필드</span></h2>
                        <div class="vidoeBox paraShowing" data-dirc='x' data-dist='150' data-ease='Power3.easeInOut' data-dura='1' data-delay='0.5'>
                            <div class="inner">
                                <a href="javascript:void(0);" onclick="window.videoPop = new VideoPopFn({url: '/about/concept/video01.af'});">
                                    <div class="ico_play"><img src="../common/front/images/${POINT_CODE }/about/ico_play.png"/ alt="play"></div>
                                    <img class="videoTumnail" src="../common/front/images/${POINT_CODE }/about/video_box_img_hanam.jpg" alt="boxImage" />
                                </a>
                            </div>
                        </div>
                        <p class="wordBraek">청량한 물소리와 하늘 빛 자연을 한 눈에 품은 아쿠아필드는 감각 있는 사람들의 오감만족 힐링 클럽입니다.<br/>
                                            <span class="break_word"></span>
				                            하늘과 맞닿은 인피니티풀에서 느긋이 즐기고 신비로운 오로라부터 구름 위의 휴식까지 8가지 테마의 찜질스파를 경험하는 곳 <br/>
				                            들어서는 공간마다 색다른 휴식이 기다리는 아쿠아필드에서 삶의 진짜 멋과 여유를 누리십시오.</p>
                    </div>
                </div>
            </div>
        </div>
        </c:if>
        <c:if test="${POINT_CODE == 'POINT05'}">
        <div class="iscContent mo_none">
            <div class="inner">
                <div class="row">
                    <div class="col s6 txt paraShowing" data-dirc='x' data-dist='-150' data-ease='Power2.easeInOut' data-dura='1'>
                        <h1>FEELING & HEALING</h1>
                        <h2 class="subtit"><span style="white-space: nowrap">생각을 비우다, </span><span style="white-space: nowrap">여유를 채우다</span><br/><span style="white-space: nowrap">아쿠아필드</span></h2>
                        <p class="wordBraek">청량한 물소리와 하늘 빛 자연을 한 눈에 품은 아쿠아필드는<br/>감각 있는 사람들의 오감만족 힐링 클럽입니다.<br/>
                                            <span class="break_word"></span>
				                            하늘과 맞닿은 인피니티풀에서 느긋이 즐기고 신비로운<br/>
                                            오로라부터 구를 위의 휴식까지 다채로운 찜질스파를 경험하는 곳<br/>
                                            들어서는 공간마다 색다른 휴식이 기다리는 아쿠아필드에서 <br/>
                                            삶의 진짜 멋과 여유를 누리십시오.</p>
                    </div>
                </div>
            </div>
        </div>
        <div class="concept_mo_title mo_block">
            <p>Healing Experience</p>
            <h2>누구나 한 번쯤 꿈꿔본 행복<br>감각이 즐거워지는 곳</h2>
        </div>
        <div class="iscContent mo_block">
            <div class="inner">
                <div class="row">
                    <div class="col s6 txt paraShowing" data-dirc='x' data-dist='-150' data-ease='Power2.easeInOut' data-dura='1'>
                        <h2 class="subtit">생각을 비우다 여유를 채우다<br>아쿠아필드</h2>
                        <h1>FEELING & HEALING</h1>
                        <img src="../../../../../../common/front/images/POINT05/about/concept_bx_bg_m2.jpg" alt="">
                        <p class="wordBraek">
                                            <span class="break_word"></span>
				                            하늘과 맞닿은 인피니티풀에서 느긋이 즐기고 신비로운 
                                            오로라부터 구를 위의 휴식까지 다채로운 찜질스파를 경험하는 곳 
                                            들어서는 공간마다 색다른 휴식이 기다리는 아쿠아필드에서 
                                            삶의 진짜 멋과 여유를 누리십시오.</p>
                    </div>
                </div>
            </div>
        </div>
        </c:if>
        <script type="text/javascript">
            $(window).resize(function(){
            	$('.concept_img').height($('.content-box').height()-80);
            });
            setTimeout(function(){$(window).resize()},0)
        </script>
    </section>
