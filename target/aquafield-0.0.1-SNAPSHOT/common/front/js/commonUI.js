
/*팝업에 컨텐츠 추가하기*/	
	function addPopCont(o){
		var pop = o.pop;
		var t = o.t;
		var close = o.btnClose || '.btn_close';
		t.removeClass('off');
		
		if(o.ajaxUrl){
			$.ajax({
				url : o.ajaxUrl,
				type : "get",
				dataType : "html",
				data : o.data,
				success : function(data){
					t.html(data);
					setLayout();
				},
				error : function(a,b,c){
					alert(c);
				}
			})
		}else{
			setLayout();
		}
		
		function setLayout(){
			var inPop = pop.find('.inner_popups');
			var inPopLen = inPop.length;
			popFn.calWidth({t : pop, inPop : inPop, inPopLen : inPopLen});
			popFn.resize({data : {tg : $(pop)}});
			t.find(close).off('click').on('click', function(){
				removeAddedPopCont({pop : pop, t : t});
			});
		}
	}
	


/*추가로 생성된 팝업만 감추기*/	
	function removeAddedPopCont(o){
		var pop = $(o.pop);
		var t = $(o.t);
		t.addClass('off');
		var inPop = pop.find('.inner_popups');
		var inPopLen = inPop.length;
		popFn.calWidth({t : pop, inPop : inPop, inPopLen : inPopLen});
		popFn.resize({data : {tg : $(pop)}});
	}


/*패널 선택 토글*/
	function toggleSelectPanel(idx, me){
		var $this = $(me);
		if($this.hasClass('off')){
			$this.removeClass('off').text("Select as Panel");
		}else{
			$this.addClass('off').text("Deelect as Panel");
		}
	}
	
/*그리드 높이 맞추기*/
var AlginHeightTiles = function(el,target, resize){
	var resizeTimer;
	var vw = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
	var ele = $(el);
	var len = ele.length;
	var bx = ele.parent();
	var _this = this;
	
	var list = {ele : [],height : []};
	var tLen = target ? target.length : 1;
	for(var k = 0 ; k < tLen ; k++ ){
		list.ele[k] = [],list.height[k] = [];
	}
	this.align = function(){
		var pos1, pos2;
		var idx = 0;
		for(var i = 0; i < len ; i++){
			var ee = ele.eq(i);
			pos2 = parseInt(ee.offset().top);
			if((pos1 && pos1 !== pos2)){
				setHeight();
				pos2 = parseInt(ee.offset().top);
				idx = 0;
			}
			for(var m = 0 ; m < tLen ; m++ ){
				list.ele[m][idx] = target ? ee.find(target[m]) : ee;
				list.height[m][idx] = list.ele[m][idx].css('height','').height();
			}
			pos1 = pos2;
			idx++;
			if(i === (len-1))setHeight();
		}
	
		function setHeight(){
			for(var j = 0 ; j < tLen ; j++ ){
				var liH = Math.max.apply(null, list.height[j]); //대상들 끼리 높이 비교
				for( l = 0 ; l < idx ; l++){
					list.ele[j][l].height(liH); //대상들끼리 높이 세팅
				}
				list.ele[j] = [],list.height[j] = [];
			}
		}
	};
	this.align();
	if(!resize){
		$(window).on('resize',function(){
			clearTimeout(resizeTimer);
  			resizeTimer = setTimeout(function() {
  				_this.align();
  			},50);
		});
	}
};

/* 가격 콤마 찍기 */
function priceCommas(x) {
	return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

$(function(){
	var fullpageRe = true;
	/* 풀페이지 세팅 */
	
	function fullpage() {
		$('#wrap').fullpage({
			scrollBar: false,
			keyboardScrolling: false,
			onLeave: function(index, nextIndex, direction){
				if(index == 1 && direction =='down'){
					$('#wrap').fullpage.moveTo(1);
					$('#wrap').fullpage.setAllowScrolling(false);
					$(".gateway").removeClass("active");
	            }else if(index == 3 && direction =='up'){
					$('#wrap').fullpage.moveTo(3);
					$('#wrap').fullpage.setAllowScrolling(false);
					$(".footerOnBtn").removeClass("active");
	            }
			},
			afterLoad: function(anchorLink, index){
				//$("#main .slider-item, .bx-viewport, .content-inner").height(winH-$('#footerTop').height());
			},
			afterResize: function(){
				var vW = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth
				if(vW <= 1200) {
					$('#wrap').fullpage.destroy('all');
					fullpageRe = true;
				}
				$('#wrap').fullpage.moveTo(2);
				$(".gateway").removeClass("active");
				$(".footerOnBtn").removeClass("active");
			},
		});
		$('#wrap').fullpage.setAllowScrolling(false);
		$(window).scrollTop(0);
	}
	

	$(window).resize(function(e) {
		winH = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
		$("#main .slider-item, .bx-viewport, .content-inner").height(winH-$('#footerTop').height());
		var winW = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
		if(winW >= 640 && fullpageRe) {
			if($('.m_content').hasClass('active')) {
				$(window).scrollTop(0);
			}
			$('.m_content').html('').removeClass('active');
		}
		if(winW >= 1200 && fullpageRe) {
			fullpageRe = false;
			fullpage();
			$(window).scrollTop(0);
			$('#wrap').fullpage.moveTo(2);
		}
	}).resize();


    $("#footerTop .footerOnBtn").click(function(e) {
		e.preventDefault();
		footerArea($(this));
	});

	function footerArea(t) {
		if(!t.hasClass('active')){
			$('#wrap').fullpage.moveTo(3);
			$('#wrap').fullpage.setAllowScrolling(true);
			t.addClass('active');
		}else{
			$('#wrap').fullpage.moveTo(2);
			$('#wrap').fullpage.setAllowScrolling(false);
			t.removeClass('active');
		}
	}

	$("#topMenu .location").click(function(e) {
		e.preventDefault();
		alert("다른지점은 오픈 준비중입니다.");
		//gateway($(this));
	});

	function gateway(t) {
		if(!t.hasClass('active')){
			$('#wrap').fullpage.moveTo(1);
			$('#wrap').fullpage.setAllowScrolling(true);
			t.addClass('active');
		}else{
			$('#wrap').fullpage.moveTo(2);
			t.removeClass('active');
		}
	}


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
		homeArea = new TimelineMax(),
		menuMoving = true;

	$("#mainMenu ul.nav > li > a").click(function(e) {
		e.preventDefault();
		$('#wrap').fullpage.moveTo(2);
		if(menuMoving){
			if(!$(this).hasClass('active')){
				hidAreaOff();
				hidAreaOn($(this).closest('li'));
			}else{
				hidAreaOff();
			}
		}
	});
	$("#mainMenu .hideMenu a, #mainMenu .hideMenu .tit, #topMenu .homeBtn a").click(function(e) {
		if(menuMoving){
			hidAreaOff();
		}
	});

	$("#mainMenu ul.nav > li > .hideMenu").hover(function(e) {

	}, function() {
		if(!$("#mainMenu .logo > a").hasClass('active')){
			if(menuMoving){
				hidAreaOff();
			}
		}
	});

	$("#mainMenu .logo > a").click(function(e) {
		e.preventDefault();
		$('#wrap').fullpage.moveTo(2);
		if(menuMoving){
			if(!$(this).hasClass('active')){
				$(this).addClass('active');
				$("#mainMenu ul.nav > li > a").addClass('active');

				var hidAreaH = $(window).height()-$('#footerTop').height()-$('#mainMenu ul.nav').height()-100;
				homeAreas();
				hidArea
					.to($(".hideMenu"),0,{display: "block"})
					.to($(".hideMenu"),0.5,{height:hidAreaH});

				$(window).resize(function(e) {
					hidAreaH = $(window).height()-$('#footerTop').height()-$('#mainMenu ul.nav').height()-100;
					$(".hideMenu").css('height', hidAreaH)
				});

			}else{
				homeAreas();
				hidArea
					.to($(".hideMenu"),0.5,{height:0})
					.to($(".hideMenu"),0,{display: ""});

				$(this).removeClass('active');
				$("#mainMenu ul.nav > li > a").removeClass('active');
			}
		}
	});


	var lengEnAni = new TimelineMax(),
		lengKoAni = new TimelineMax();
	$("#mainMenu ul.nav > li a").hover(function(e) {
		lengKoAni
			.to($(this).find('.lang_ko'), 0, {'display' : 'block'})
			.to($(this).find('.lang_ko'),0.1,{alpha:1});
		lengEnAni
			.to($(this).find('.lang_en'),0.1,{alpha:0})
			.to($(this).find('.lang_en'), 0, {'display' : 'block'});
	}, function() {
		lengKoAni
			.to($(this).find('.lang_ko'),0.1,{alpha:0})
			.to($(this).find('.lang_ko'), 0, {'display' : ''});
		lengEnAni
			.to($(this).find('.lang_en'), 0, {'display' : 'block'})
			.to($(this).find('.lang_en'),0.1,{alpha:1});
	});


	function homeAreas() {
		var _this = $('.homeArea');
		if(!_this.hasClass('show')){
			homeArea
			.to(_this, 0, {'display' : 'block'})
			.to(_this,0.5,{height:100});
			_this.addClass('show');
		}else{
			homeArea
			.to(_this,0.5,{height:0})
			.to(_this, 0, {'display' : ''});
			_this.removeClass('show');
		}
	}

	function hidAreaOn(t) {
		menuMoving = false;
		$(t).find('a').addClass('active');
		var hidAreaH = $(window).height()-$('#footerTop').height()-$('#mainMenu ul.nav').height();
		hidArea
			.to(t.find(".hideMenu"),0,{display: "block"})
			.to(t.find(".hideMenu"),0.5,{height:hidAreaH, onComplete:function(){
				setTimeout(function() {
					menuMoving = true; 
				},500);
			}});
	}

	function hidAreaOff() {
		menuMoving = false;
		if($('.homeArea').hasClass('show')){
			homeAreas();
		}
		hidArea
			.to($(".hideMenu"),0.5,{height:0})
			.to($(".hideMenu"),0,{display: "", onComplete:function(){
				setTimeout(function() {
					menuMoving = true; 
				},500);
			}});
		$('#mainMenu ul.nav > li > a').removeClass('active');
		$("#mainMenu .logo a").removeClass('active');
	}

});

function calculation(me) {
	var PriceTotal = 0;
	var count = 0;
	var limit_t;
	var limit_c;
	

	$(".item li").each(function(index, el) {

    	var itemPrice = $(this).find("input.price").val();
    	var itemPriceTotal = 0;

		$(this).find("input.iptNum").each(function(index, el) {
		    count += parseInt($(this).val());
		    itemPriceTotal += ($(this).val() * itemPrice);
		});

		if(itemPriceTotal>0) {
		    $(this).find(".itmeTotal").text(priceCommas(itemPriceTotal)+"원");
		}else{
		    $(this).find(".itmeTotal").text("");
		}
	                            
	    PriceTotal += itemPriceTotal;


		if($(this).closest('.form_type_res').find('.limit_count_box').hasClass('total')) {
			limit_t = $(this).closest('.form_type_res').find('.limit_count_box.total');
			limit_c = limit_t.data("limit");

			limit_t.find("span").text(limit_c - count);
		    if(limit_c <= count) { 
		    	limit_t.parent().find('.iptNum').prop( "disabled", true );
		    }else{
		    	limit_t.parent().find('.iptNum').prop( "disabled", false  );
		    } 
			console.log(limit_c);
		}else{
			limit_t = $(this).closest('.item').prev();
			limit_c = limit_t.data("limit");

			limit_t.find("span").text(limit_c - count);
		    if(limit_c <= count) { 
		    	limit_t.next().find('.iptNum').prop( "disabled", true );
		    }else{
		    	limit_t.next().find('.iptNum').prop( "disabled", false  );
		    } 
			console.log(limit_t);
			
		}    
    });
    
    $(".form_type_res_total .price").text(priceCommas(PriceTotal)+"원");
    $(".form_type_res_total .totalprice").val(PriceTotal);
}


var MobileMenuFn = function(params){
	params = params || {};
	var _this = this, bg, t, menuBx, moving = true, isc,
		bgId = '#pop_bg_common.t2';

	this.init = function(o) {
		_this.menuBx = $("#mobileMenu");
		_this.iscroll();
		//_this.t.html('');
		//_this.t.append(data);
		/*$.ajax({
			url : '/m_menu.af',
			type : "get",
			dataType : "html",
			success : function(data){
				_this.t.html('');
				_this.t.append(data);
				_this.showMenu();
				_this.iscroll();
			},
			error : function(a,b,c){
				alert(c);
			}
		})*/
	};

	this.handleTouchMove = function(e) {
	  e.preventDefault();
	};

	this.iscroll = function(o) {
		var wH = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight
		$('.menuList').height(wH-$('.menuTop').height()-$('.mypageMenu').height());
		setTimeout(function(){
			_this.isc = new IScroll('.menuList', {
				scrollbars: 'custom',
				mouseWheel: true,
				disableMouse: true,
				preventDefault : false
			});			
			//document.addEventListener('touchmove', _this.handleTouchMove, false);
		},500);
	}

	this.showMenu = function(o){
		 _this.resize();
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
			document.addEventListener('touchmove', _this.handleTouchMove, false);
			$(window).on('resize', _this.resize);
		}});
	};

	this.hideMenu = function(o){
		_this.bg.off('click');
		_this.bg.removeClass('on');
		setTimeout(function(){
			_this.bg.remove();
		},500);
		TweenMax.to(_this.menuBx,0.5,{'right' : -100+'%', ease: Power3.easeInOut, onComplete : function(){
			//_this.menuBx.remove();
			$(window).off('resize', _this.resize);	
		}});
		//_this.isc.destroy();
		document.removeEventListener('touchmove', _this.handleTouchMove);
	};

	this.showDepth = function(o){
		if(moving){
            moving = false;
            var content = o.next('ul'),
            	contentH = content.height();
            if(!o.hasClass('active')){
            	$('.menuList .active').removeClass('active').next('ul').animate({height: 0},500, function() {
                 	$(this).css({height: '', display: ''});
                });

            	o.addClass('active');
	            content.css({height: 0, display: 'block'}).animate({height: contentH},500, function() {
		            $(this).css({height: ''});
		            _this.isc.refresh();
		            moving = true;
	            });
            }else{
                o.removeClass('active');
                content.animate({height: 0},500, function() {
	                $(this).css({height: '', display: ''});
	                _this.isc.refresh();
	                moving = true;
                });
            }
        }
	};

	this.resize = function(o){
		var wH = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight
		$('.menuList').height(wH-$('.menuTop').height()-$('.mypageMenu').height());
		_this.isc.refresh();
	};

	this.init();
}
var mobileMenu;
