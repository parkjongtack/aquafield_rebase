<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tile" %>
<!DOCTYPE html>
<html lang="ko">
	<head>
		<tile:insertAttribute name="maintop" />
	</head>
<body>
	<div id="wrap">
		<section class="section fp-auto-height">
			<div id="topArea"></div>
		</section>
		<section class="section active">
			<tile:insertAttribute name="mainheader" />
			<tile:insertAttribute name="mainbody" />		
			<div id="footerTop">
				<div class="inner">
					<div class="footerOnBtn"></div>
					<ul class="footerTopMenu">
						<li><a href="#/service/index.af?page=terms2">아쿠아필드 이용약관</a></li>
						<li><a href="#/service/index.af?page=terms">홈페이지 이용약관</a></li>
						<li><a href="#/service/index.af?page=privacy">개인정보취급(처리) 방침</a></li>
						<li><a href="#/service/index.af?page=email">이메일 무단수집 거부</a></li>
						<li><a href="#/service/index.af?page=video">영상정보처리 기기운영/관리방침</a></li>
						<li><a href="#/service/index.af?page=guide">운영정책</a></li>
					</ul>
					<ul class="footerSns">
						<li><a href="https://story.kakao.com/ch/aquafield" target="_blank"><img src="/common/front/images/ico/ico_sns_kas_off.png"></a></li>
						<li><a href="https://www.instagram.com/aquafield_/" target="_blank"><img src="/common/front/images/ico/ico_sns_ins_off.png"></a></li>
					</ul>	
					<a href="javascript:;" class="btn_foot_topMenu"><img src="/common/front/images/footer/btn_foot_topMenu.png"></a>				
				</div>
			</div>
		</section>			
		<script>
			$('.footerSns a').on('mouseover',function(){
				$(this).find('img').attr("src" ,function(iIndex,sSrc){
					return sSrc.replace('_off.png', '_on.png');
				});
			}).on('mouseout',function(){
				$(this).find('img').attr("src" ,function(iIndex,sSrc){
					return sSrc.replace('_on.png', '_off.png');
				});
			});
			
			$('.btn_foot_topMenu').on('click',function(){
				var th = $('.footerTopMenu').outerHeight();
				if($(this).hasClass('on')){
					$(this).removeClass('on').parents('#footerTop').animate({'height':'50px'},200, function() {
						$('#footerTop').css('height','');
					});
				}else {
					$(this).addClass('on').parents('#footerTop').animate({'height':th},300);	
					$(window).resize(function(){ 
						$('.btn_foot_topMenu').removeClass('on');
						$('#footerTop').css('height','')
					});
				}
			});
		</script>
			<tile:insertAttribute name="mainfooter" />
			<tile:insertAttribute name="mainbot" />	
</body>
</html>