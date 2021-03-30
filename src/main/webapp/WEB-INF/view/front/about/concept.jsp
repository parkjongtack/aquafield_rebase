<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../common/taglibs.jsp" %>
<div class="contentArea active">
	<div id="concept" class="content">
		<div class="content-inner" style="display: block;">
			<video preload="auto" loop="" autoplay="" tabindex="0" class="bg_movie">
			  <source src="/common/front/about/bg_movie01.mp4" type="video/mp4">
			  <source src="/common/front/about/bg_movie01.ogg" type="video/ogg">
			</video>
			<div class="vertical">
				<div class="textArea">
					<h1 class="tit"><img src="/common/front/images/about/con_tit.png"></h1>
					<h3 class="subtit">생각을 비우다, 여유를 채우다 <br/><span>-아쿠아필드</span></h3>
					<ul class="titList">
						<li class="t1"><span>청량한 물소리와 하늘 빛</span></li>
						<li class="t2"><span>자연을 한 눈에 품은 아쿠아필드는</span></li>
						<li class="t3"><span>감각 있는 사람들의 오감만족 힐링 클럽입니다.</span></li>
						<li class="t4"><span>하늘과 맞닿은 인피니티풀에서 느긋이 즐기고</span></li>
						<li class="t5"><span>신비로운 오로라부터 구름 위의 휴식까지</span></li>
						<li class="t6"><span>8가지 테마의 찜질스파를 경험하는 곳-</span></li>
						<li class="t7"><span>들어서는 공간마다 색다른 휴식이 기다리는</span></li>
						<li class="t8"><span>아쿠아필드에서 삶의 진짜 멋과 여유를 누리십시오.</span></li>
					</ul>
				</div>
			</div>
		</div>
		<script type="text/javascript">
			$(document).ready(function() {
				var titAni = new TimelineMax();
				var txtAni = new TimelineMax();
				titAni
					.fromTo($(".tit"),1,{alpha: 0, y:100},{alpha: 1, y:0, delay:1})
					.fromTo($(".subtit"),.7,{alpha: 0, y: 75},{alpha: 1, y: 0, onComplete:function(){ txtAni.play(); }});

				txtAni
					.stop()
					.fromTo($(".t1 span"),1,{alpha: 0, width: '0%', display:'block'},{alpha: 1, width: '100%'})
					.to($(".t1 span"),1,{alpha: 0, y: -50, delay:2, display:'none'})
					.fromTo($(".t2 span"),1,{alpha: 0, width: '0%', display:'block'},{alpha: 1, width: '100%'})
					.fromTo($(".t3 span"),1,{alpha: 0, width: '0%', display:'block'},{alpha: 1, width: '100%'})
					.to($(".t2 span, .t3 span"),1,{alpha: 0, y: -50, delay:2, display:'none'})
					.fromTo($(".t4 span"),1,{alpha: 0, width: '0%', display:'block'},{alpha: 1, width: '100%'})
					.to($(".t4 span"),1,{alpha: 0, y: -50, delay:2, display:'none'})
					.fromTo($(".t5 span"),1,{alpha: 0, width: '0%', display:'block'},{alpha: 1, width: '100%'})
					.fromTo($(".t6 span"),1,{alpha: 0, width: '0%', display:'block'},{alpha: 1, width: '100%'})
					.to($(".t5 span, .t6 span"),1,{alpha: 0, y: -50, delay:2, display:'none'})
					.fromTo($(".t7 span"),1,{alpha: 0, width: '0%', display:'block'},{alpha: 1, width: '100%'})
					.fromTo($(".t8 span"),1,{alpha: 0, width: '0%', display:'block'},{alpha: 1, width: '100%'})
					.to($(".t7 span, .t8 span"),1,{alpha: 0, y: -50, delay:2, display:'none', onComplete:function(){ txtAni.restart(); }})
			});
		</script>
	</div>
</div>