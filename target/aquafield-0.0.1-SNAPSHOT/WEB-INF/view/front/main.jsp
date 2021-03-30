<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../common/taglibs.jsp" %>
<div class="contentArea active">
	<div id="main" class="content">
		<div class="slider-wrapper">
			<div class="slider-item main01">
				<div class="sliderInner">
					<div class="textArea">
						<img src="/common/front/images/main/main_bg_01_tit.png">
					</div>
				</div>
			</div>
			<div class="slider-item main02">
				<div class="sliderInner">
					<div class="textArea">
						<h1>Feeling &amp; Healing</h1>
						<p>생각을 비우다, 여유를 채우다</p>
					</div>
				</div>
			</div>
		</div>
		<div class="m_content">
			
		</div>		
		<script type="text/javascript">
			$(function(){
				var mainSlider = $("#main .slider-wrapper").bxSlider({
				  	mode: 'fade',
				  	auto: true,
			  		autoControls: true,
			  		controls: true,
			  		pause: 7000,
			  		speed: 700,
			  		adaptiveHeight:true,
			  		adaptiveHeightSpeed: 0
				});

				var mContLord = true;
				$(window).resize(function() {
					var w = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
					if(w < 640 ){
						if(mContLord){
							$.ajax({
								url : "/m_main_cont.af",
								type : "GET",
								dataType : "html",
								success : function(data){
									$('.m_content').html(data).addClass('active');
									mContLord = false;
								},
								error : function(a,b,c){
									alert(c);
								}
							});
						}
					}else{
						mContLord = true;
					}
				}).resize();
			});
		</script>
	</div>
</div>
