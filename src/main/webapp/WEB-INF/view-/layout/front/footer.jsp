<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../common/taglibs.jsp" %>
<section class="section footer">
	<footer id="footer">
		<div id="footerArea">
			<div class="inner">
				<div class="logo">
					<img src="/common/front/images/footer/footer_logo_new.png" alt="아쿠아필드">
				</div>
				<div class="info">
					<a href="javascript:void(0);" class="btn_foot_topMenu"><img src="/common/front/images/footer/btn_foot_topMenu.png" alt="더보기"></a>
					<ul class="footerMenu">
						<li><a href="#/service/index.af?page=terms2">아쿠아필드 이용약관</a></li>
						<li><a href="#/service/index.af?page=terms">홈페이지 이용약관</a></li>
						<li class="c_red"><a href="#/service/index.af?page=privacy">개인정보 처리방침</a></li>
						<!--<li><a href="#/service/index.af?page=email">이메일 무단수집 거부</a></li>-->
						<li><a href="#/service/index.af?page=video">영상정보처리 기기운영/관리방침</a></li>
						<li><a href="#/service/index.af?page=communication">통신판매사업자정보</a></li>
					</ul>
					<ul class="contact">
					<c:choose>
						<c:when test="${POINT_CODE eq 'POINT01'}">
							<li>(주) 신세계건설</li>
							<li>대표자: 윤명규</li>
							<li>사업자등록번호: 201-81-44158</li>
							<li>주소: 경기도 하남시 미사대로 750(신장동, 스타필드 하남)</li>
							<li>Tel: 031-8072-8800</li>
							<li class="copyright">© 2016 SHINSEGAE E&C All Rights Reserved.</li>
						</c:when>
						<c:when test="${POINT_CODE eq 'POINT05'}">
							<li>(주) 신세계건설</li>
							<li>대표자: 윤명규</li>
							<li>사업자등록번호: 201-81-44158</li>
							<li>주소: 경기 안성시 공도읍 서동대로 3930-39(스타필드 안성)</li>
							<li>Tel: 031-8092-1900</li>
							<li class="copyright">© 2016 SHINSEGAE E&C All Rights Reserved.</li>
						</c:when>
						<c:otherwise>
							<li>(주) 신세계건설</li>
							<li>대표자: 윤명규</li>
							<li>사업자등록번호: 201-81-44158</li>
							<li>주소: 경기도 고양시 덕양구 고양대로 1955(스타필드 고양4층)</li>
							<li>Tel: 031-5173-4500</li>
							<li class="copyright">© 2016 SHINSEGAE E&C All Rights Reserved.</li>
						</c:otherwise>
					</c:choose>
					</ul>
				</div>
			</div>
		</div>
		<div id="cooperationArea">
			<div class="inner swipe-container">
				<div class="swipe-wrapper">
					<div class="swiper-slide">
						<a href="https://www.starfield.co.kr/" target="_blank"><img src="/common/front/images/footer/coop01.png" alt="스타필드"></a>
					</div>
					<div class="swiper-slide">
						<a href="http://www.ssgblog.com/" target="_blank"><img src="/common/front/images/footer/coop02.png" alt="신세계"></a>
					</div>
					<div class="swiper-slide">
						<a href="https://www.shinsegae-enc.com:501/" target="_blank"><img src="/common/front/images/footer/coop03.png" alt="신세계건설"></a>
					</div>
					<div class="swiper-slide">
						<a href="http://www.ssg.com/" target="_blank"><img src="/common/front/images/footer/coop04.png" alt="SSG"></a>
					</div>
				</div>
			</div>
			<!-- <div class="swiper-nav">
				<a href="#" class="swiper-prev"></a>
				<a href="#" class="swiper-next"></a>
			</div> -->
		</div>
	</footer>
	
	
	<!-- Global site tag (gtag.js) - Google Analytics -->
	<script async src="https://www.googletagmanager.com/gtag/js?id=UA-115613386-1"></script>
	<script>
	    window.dataLayer = window.dataLayer || [];
	    function gtag(){dataLayer.push(arguments);}
	    gtag('js', new Date());
	
	    gtag('config', 'UA-115613386-1');
	</script>
	
	<script type="text/javascript">
		$('.btn_foot_topMenu').on('click',function(){
			var th = $('.footerMenu').outerHeight();
			if($(this).hasClass('on')){
				$(this).removeClass('on');
				$('.footerMenu').animate({'height':'70px'},200, function() {
					$('.footerMenu').css('height','');
				});
			}else {
				$(this).addClass('on')
				$('.footerMenu').animate({'height':'100%'},300);
				$(window).resize(function(){
					$('.btn_foot_topMenu').removeClass('on');
					$('.footerMenu').css('height','')
				});
			}
		});
	</script>
</section>