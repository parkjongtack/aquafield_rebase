<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../common/taglibs.jsp" %>
<div class="contentArea active">
<input type="hidden" id="point" name="point" value="${POINT_CODE}">
	<div id="main" class="content">
		<div id="main-slider" class="bx-wrapper">
			<c:if test="${POINT_CODE eq 'POINT01'}">
				<div class="slider-item main01">
					<div class="sliderInner">
						<div class="textArea">
							<img src="/common/front/images/main/main_bg_01_tit.png" alt="Peeling & Healing">
						</div>
					</div>
				</div>
				<div class="slider-item main02">
					<div class="sliderInner">
						<div class="textArea">
							<img src="/common/front/images/main/main_bg_02_tit.png" alt="AQUAFIELD">
						</div>
					</div>
				</div>
			</c:if>
			<c:if test="${POINT_CODE eq 'POINT03'}">
				<div class="slider-item main03">
					<div class="sliderInner">
						<div class="textArea">
							<img src="/common/front/images/main/main_bg_01_tit.png" alt="Peeling & Healing">
						</div>
					</div>
				</div>
				<div class="slider-item main04">
					<div class="sliderInner">
						<div class="textArea">
							<img src="/common/front/images/main/main_bg_02_tit.png" alt="AQUAFIELD">
						</div>
					</div>
				</div>
			</c:if>
			<c:if test="${POINT_CODE eq 'POINT05'}">
				<div class="slider-item main01">
					<div class="sliderInner">
						<div class="textArea">
							<img class="mo_none" src="/common/front/images/POINT05/main/main_bg_01_tit.png" alt="Peeling & Healing">
							<img class="mo_block" src="/common/front/images/POINT05/main/m_main_bg_01_tit.png" alt="Peeling & Healing">
						</div>
					</div>
				</div>
				<div class="slider-item main02">
					<div class="sliderInner">
						<div class="textArea">
							<img class="mo_none" src="/common/front/images/POINT05/main/main_bg_02_tit.png" alt="AQUAFIELD">
							<img class="mo_block" src="/common/front/images/POINT05/main/m_main_bg_02_tit.png" alt="AQUAFIELD">
						</div>
					</div>
				</div>
				<div class="slider-item main03">
					<div class="sliderInner">
						<div class="textArea">
							<img class="mo_none" src="/common/front/images/POINT05/main/main_bg_03_tit.png" alt="AQUAFIELD">
							<img class="mo_block" src="/common/front/images/POINT05/main/m_main_bg_03_tit.png" alt="AQUAFIELD">
						</div>
					</div>
				</div>
				<div class="slider-item main04">
					<div class="sliderInner">
						<div class="textArea">
							<img class="mo_none" src="/common/front/images/POINT05/main/main_bg_04_tit.png" alt="AQUAFIELD">
							<img class="mo_block" src="/common/front/images/POINT05/main/m_main_bg_04_tit.png" alt="AQUAFIELD">
						</div>
					</div>
				</div>
			</c:if>			
		</div>

		<div class="m_content">
			<div class="mainMenuArea"></div>
		<script type="text/javascript">
		
			$(function(){
				var progressBar = {
					_barAni: '',
					_init: function() {
						var _this = this,
							barAni,
							barColor = '#fff',
							time = 7;

						if($('#main').length != 0){

							if($('.slider-progress').length > 0){
								$('.slider-progress').remove();
							}

							$('#header').append('<div class="slider-progress"><div class="bar"></div></div>');
							this._$slider_progress = $('.slider-progress');
							this._$progress_bar = $('#header').find('.slider-progress:first .bar');

							this._$progress_bar.css({
								'height': '100%'
							});
							this._$slider_progress.css({
								'position': 'absolute',
								'top': 77,
								'left': 0,
								'height': 3,
								'width': '100%',
								'background-color': 'transparent',
								'opacity': 1,
								'z-index': 5
							});

							this._$progress_bar.css({
								'width': '0%',
								'background-color': barColor
							});

							progressBar._barAni = new TimelineMax({paused:true});
							progressBar._barAni
								.fromTo(this._$progress_bar,time,{'width': '0%'},{'width': '100%', ease: Power1.easeInOut})
								.to(this._$progress_bar,0.5,{alpha: 0, ease: Power1.easeInOut});
							progressBar._barAni.play();

							$(window).on('hashchange', function (e) {
								var _location = location.hash.split("#");
								if(_location.length > 1) {
									progressBar._barAni.kill();
					            	$('.slider-progress').remove();
								}
					        });
						}
					}
				}

				var sliderPause = false;
				$("#main-slider").on('init', function(event, slick, direction){
					$('.slick-dots').append('<li><button class="slick-stop">Stop</button></li><li><button class="slick-start">Start</button></li>');
					$('.slick-start').addClass('active');

					$('.slick-stop').click(function(e) {
						if(!$(this).hasClass('active')){
							$('#main-slider').slick('slickPause');

							$(this).addClass('active');
							$('.slick-start').removeClass('active');
						}
						sliderPause = true;
						progressBar._barAni.pause();
					});
					$('.slick-start').click(function(e) {
						if(!$(this).hasClass('active')){
							$('#main-slider').slick('slickPlay');

							$(this).addClass('active');
							$('.slick-stop').removeClass('active');
						}
						sliderPause = false;
						progressBar._barAni.restart();
					});

					progressBar._init();

					TweenMax.fromTo($('.slider-item').eq(0).find('.textArea'),1,{y: 50, alpha: 0},{y: 0, alpha:1, ease: Power1.easeInOut});
			  		TweenMax.fromTo($('.slick-dots'),1,{alpha: 0},{alpha:1, ease: Power1.easeInOut, delay: 0.5});
				});

				var mainSlider = $("#main-slider").slick({
				  	dots: true,
				  	arrows: true,
				  	fade: true,
				  	infinite: true,
				  	speed: 700,
				  	autoplay: true,
  					autoplaySpeed: 7000,
  					pauseOnFocus: false,
  					pauseOnHover: false,
				  	cssEase: 'linear'
				});
				mainSlider.on('setPosition', function(event, slick, direction){
					var winW = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
					var winH = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
					if(winW < 768 ){
						$("#main-slider .slider-item").css('height', '');
					}else{
						$("#main-slider .slider-item").css('height', winH-$('#footerTop').height());
					}
				});
				mainSlider.on('beforeChange', function(event, slick, currentSlide, nextSlide){
					TweenMax.to($('.slider-item').eq(currentSlide).find('.textArea'),0.5,{y: 50, alpha:0, ease: Power1.easeInOut});
			  		TweenMax.fromTo($('.slider-item').eq(nextSlide).find('.textArea'),1,{y: 50, alpha: 0},{y: 0, alpha:1, ease: Power1.easeInOut});
				});

				mainSlider.on('afterChange', function(event, slick, currentSlide, nextSlide){
					if(!sliderPause) progressBar._barAni.restart();
				});


				var mContLord = true;
				$(window).resize(function() {
					var w = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
					if(w < 768 ){
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
