$(function(){

	var cooperSwiper ;
	function setCoreFn(){
		var w, groupCnt;
		function setGropt(){
			w = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
			groupCnt = 4;
			if(w<= 1300){
				groupCnt = 2;
			}
			return groupCnt
		}
		var group1 = setGropt();
		cooperSwiper = new Swiper('#cooperationArea .swiper-container', {
			cssWidthAndHeight : 'height',
			slidesPerView : groupCnt,
			initialSlide : 0,
			loop : false,
			slidesPerGroup : groupCnt,
			simulateTouch : false,
			autoplayDisableOnInteraction : false
	    });
	    $('#cooperationArea .swiper-prev').on('click', function(e){
    	e.preventDefault();
    	cooperSwiper.swipePrev();
	    });
		$('#cooperationArea .swiper-next').on('click', function(e){
	    	e.preventDefault();
			cooperSwiper.swipeNext();
		});
	    $(window).resize(function(){
	    	var group2 = setGropt();

	    	if(group1 == group2) return;
    		cooperSwiper.params.slidesPerView = group2;
    		cooperSwiper.params.slidesPerGroup = group2;
    		cooperSwiper.reInit();
	    	group1 = group2;
	    })

	}
	//setCoreFn();


	//MENU
	var hidArea = new TimelineMax(),
		homeArea = new TimelineMax();

	var mainMenuFn = function(options){
		options = options || {};
		var _this = this, menuOn, menuAni, hidAreaH, bg;

		this.init = function(o) {

			menuOn = false;
			menuAni = new TimelineMax({paused:true});
			hidAreaH = 255;

			$('#menuSlider').slick({
				arrows: false,
				dots: false,
				vertical: true,
				draggable: false,
				touchMove: false,
				infinite: false
			});

			$(".menu_content").each(function(e) {
				$(this).children('li').eq(0).addClass('on');
			});

			$(".menu_list").each(function(e) {
				var _list = $(this);
				var _content = _list.parent().find('.menu_content li');

				_list.children('li').find('a').hover(function() {
					var _eq = _list.find("li").index($(this).parent());
					var _on = _content.filter('.on');

					if(_on.length){
						_on.removeClass('on');
					}
					_content.eq(_eq).addClass('on');
				});
			});

			$("#mainMenu ul.nav > li > a").hover(
				function() {
					TweenMax.fromTo($(this).find(".lang_en"),0.3,{display:"block", alpha: 1},{display:"none", alpha:0, ease: Power1.easeInOut});
					TweenMax.fromTo($(this).find(".lang_ko"),0.3,{display:"none", alpha: 0},{display:"block", alpha:1, ease: Power1.easeInOut});
				},function() {
					TweenMax.fromTo($(this).find(".lang_en"),0.3,{display:"none", alpha: 0},{display:"block", alpha:1, ease: Power1.easeInOut});
					TweenMax.fromTo($(this).find(".lang_ko"),0.3,{display:"block", alpha: 1},{display:"none", alpha:0, ease: Power1.easeInOut});
				}
			);

			$("#mainMenu ul.nav > li > a").click(function(e) {
				e.preventDefault();
				_this.menuShowHide($(this));
			});

			$("#mainMenu #menuSlider a").click(function(e) {
				_this.menuHide();
			});

			/*$("#mainMenu").mouseleave(function(e) {
				_this.menuHide();
			});*/
		}

		this.menuShowHide = function(t) {
			var idx = $("#mainMenu ul.nav > li").index(t.parent());
			bg = "body > #pop_bg_common.t4";

			if(!t.hasClass('active')){
				$('#mainMenu ul.nav > li > a').removeClass('active');
				t.addClass('active');

				if(menuOn){
					$('#menuSlider').slick('slickGoTo', idx, false);
				}else{
					$('#menuSlider').slick('slickGoTo', idx, true);
					menuAni
						.fromTo($('#menuSlider'),0.5,{height: 0},{height:hidAreaH, ease: Power1.easeInOut, onComplete:function(){
							menuOn = true;
						}}).play();
					if($(bg).length === 0){
						$("body").append("<div id='pop_bg_common' class='t4 on'></div>");
						$(bg).css('display','block');
						$(bg).off('click').on('click',function(){_this.menuHide();});
					}

				}
			}else{
				_this.menuHide();
			}
			fullPage.hideArea('footer');
		}

		this.menuHide = function() {
			if(menuOn){
				menuAni
					.to($('#menuSlider'),0.5,{height: 0, ease: Power1.easeInOut, onComplete:function(){
						menuOn = false;
						$('#menuSlider').css('height', '');
						$('#mainMenu ul.nav > li > a').removeClass('active');
					}}).play();
				$(bg).remove();
			}
		}

		this.init();
	}
	var mainMenu = new mainMenuFn();


	var allMenuFn = function(params){ //모바일 메뉴 겸용
		params = params || {};
		var _this = this, bg, t, menuBx, moving = true, isc,
			bgId = '#pop_bg_common.t3',
			mSize = 769;

		this.init = function(o) {
			_this.menuBx = $("#allMenu");

			$(".btn_sitemap").click(function(e) {
				_this.showMenu();
			});

			_this.menuBx.find('.menuList > ul > li').hover(
				function() {
					var idx = _this.menuBx.find('.menuList > ul > li').index($(this)),
						listCount = _this.menuBx.find('.menuList > ul > li').length,
						pos = (100/listCount)*idx;
					TweenMax.to(_this.menuBx.find('.menuList > .bg'),0.5,{left: pos+'%', ease: Power1.easeInOut});

				}, function() {

				}
			);
			$(window).on('resize', _this.resize);
		};

		this.showMenu = function(o){
			if(_this.menuBx.parent().find(bgId).length == 0){
				_this.menuBx.parent().append($("<div></div>").prop({id : 'pop_bg_common'}).addClass('t3'));
			}
			_this.bg = $("#pop_bg_common"),
			_this.bg.css('display','block');
			setTimeout(function(){
				_this.bg.addClass('on');
				_this.bg.off('click').on('click',function(){_this.hideMenu();});
				$("#mainMenu ul.nav > li > a.active").trigger('click');
			},100);

			if(mSize < _this.winSizeChk().wW) { //데스크탑
				TweenMax.fromTo(_this.menuBx,0.5,{alpha: 0, display: 'block', y: -200, 'right' : 0+'%'},{y: 0, alpha: 1,  ease: Power1.easeInOut, onComplete : function(){

				}});
			}else{ //모바일
				TweenMax.fromTo(_this.menuBx,0.5,{alpha: 1, display: 'block', 'right' : -100+'%'},{'right' : 0+'%',  ease: Power1.easeInOut, onComplete : function(){

				}});
			}

			_this.menuBx.find('.btn_close').on('click',function(){_this.hideMenu();});
			_this.menuBx.find('.menuList > ul > li > a').on('click',function(e){ e.preventDefault(); if(mSize > _this.winSizeChk().wW) _this.showDepth($(this)); });
			_this.menuBx.find('.menuList > ul > li > ul > li > a').on('click',function(){
				_this.hideMenu();
				_this.menuBx.find('.menuList > ul > li > ul > li > a').removeClass("active");
				$(this).addClass("active");
			});
			_this.menuBx.find('.login, .logout, .customerBtn').on('click',function(e){_this.hideMenu();});
			_this.menuBx.find('.mypageMenu > li > a').on('click',function(e){_this.hideMenu();});

			/* _this.resize();
			if(_this.menuBx.parent().find(bgId).length == 0){
				_this.menuBx.parent().append($("<div></div>").prop({id : 'pop_bg_common'}).addClass('t2'));
			}
			_this.bg = $("#pop_bg_common"),
			_this.bg.css('display','block');
			setTimeout(function(){
				_this.bg.addClass('on');
				_this.bg.off('click').on('click',function(){_this.hideMenu();});
			},100);
			TweenMax.to(_this.menuBx,0.5,{'right' : 0+'%', ease: Power3.easeInOut, onComplete : function(){
				_this.menuBx.find('.btn_close').on('click',function(){_this.hideMenu();});
				_this.menuBx.find('.mypageMenu > li > a').on('click',function(e){_this.hideMenu();});
				_this.menuBx.find('.menuList > ul > li > a').on('click',function(e){e.preventDefault(); _this.showDepth($(this));});
				_this.menuBx.find('.menuList > ul > li > ul > li a').on('click',function(e){_this.hideMenu();});
				_this.menuBx.find('.login, .logout').on('click',function(e){_this.hideMenu();});
				$(window).on('resize', _this.resize);
			}});*/
		};

		this.hideMenu = function(o){
			_this.bg.off('click');
			_this.bg.removeClass('on');
			setTimeout(function(){
				_this.bg.remove();
			},500);
			if(mSize < _this.winSizeChk().wW) { //데스크탑
				TweenMax.to(_this.menuBx,0.5,{alpha: 0, display: 'none', ease: Power3.easeInOut, onComplete : function(){

				}});
			}else{ //모바일
				TweenMax.to(_this.menuBx,0.5,{'right' : -100+'%', display: 'none', ease: Power3.easeInOut, onComplete : function(){
					_this.menuBx.css({'right': ''});
				}});
			}
			$(window).off('resize', _this.resize);
		};

		this.showDepth = function(o){
			if(moving){
	            moving = false;
	            var content = o.next('ul'),
	            	contentH = content.height();
	            if(!o.hasClass('active')){
	            	$('.menuList > ul > li > a.active').removeClass('active').next('ul').animate({height: 0},500, function() {
	                 	$(this).css({height: '', display: ''});
	                });

	            	o.addClass('active');
		            content.css({height: 0, display: 'block'}).animate({height: contentH},500, function() {
			            $(this).css({height: ''});
			            moving = true;
		            });
	            }else{
	                o.removeClass('active');
	                content.animate({height: 0},500, function() {
		                $(this).css({height: '', display: ''});
		                moving = true;
	                });
	            }
	        }
		};

		this.resize = function(o){
			if(mSize < _this.winSizeChk().wW) { //데스크탑
				$('.menuList').css('height', '');
			}else{
				$('.menuList').css('height', _this.winSizeChk().wH-$('.menuTop').height()-$('.mypageMenu').height());
			}
		};

		this.winSizeChk = function(o){
			var wW = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
			var wH = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
			return {wW : wW , wH : wH};
		};

		this.init();
	}
	var allMenu  = new allMenuFn();

});