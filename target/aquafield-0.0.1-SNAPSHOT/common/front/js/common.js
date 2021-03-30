//check browser
var isie=(/msie/i).test(navigator.userAgent); //ie
var isie6=(/msie 6/i).test(navigator.userAgent); //ie 6
var isie7=(/msie 7/i).test(navigator.userAgent); //ie 7
var isie8=(/msie 8/i).test(navigator.userAgent); //ie 8
var isie9=(/msie 9/i).test(navigator.userAgent); //ie 9
var isie10=(/msie 10/i).test(navigator.userAgent); //ie 9
var isfirefox=(/firefox/i).test(navigator.userAgent); //firefox
var isapple=(/applewebkit/i).test(navigator.userAgent); //safari,chrome
var isopera=(/opera/i).test(navigator.userAgent); //opera
var isios=(/(ipod|iphone|ipad)/i).test(navigator.userAgent);//ios
var isipad=(/(ipad)/i).test(navigator.userAgent);//ipad
var isandroid=(/android/i).test(navigator.userAgent);//android
var device;
var isieLw;
if(isie6 || isie7 || isie8){ isieLw=true;}
//if(isie9){ isie=false;}
//if(isapple || isios || isipad || isandroid){}else{}



$.fn.extend({
    ensureLoad: function(handler) {//개별 이미지 로드 완료 체크
        return this.each(function() {
            if(this.complete) {
                handler.call(this);
            }else{
                $(this).load(handler);
                this.onerror = function(){
                	handler.call(this);
                }
            }
        });
    },
    checkImgsLoad : function(fn){ //다중 이미지 로드 완료 체크
		var img = this.find('img[src!=""]');
		var cntLoad = 0;
		if(img.length === 0){
			fn();
		}else{
			return img.ensureLoad(function(){
				cntLoad++;
				if(cntLoad === img.length){
					fn();
				}
			});
		}
	},
    imagesLoaded : function (fn) {
        var $imgs = this.find('img[src!=""]');
        if ($imgs.length == 0) return;
        console.log($imgs.length);
        var dfds = [], cnt = 0;
        $imgs.each(function(){
            var dfd = $.Deferred();
            dfds.push(dfd);
            var img = new Image();
            img.onload = function(){check();}
            img.onerror = function(){check();}
            img.src = this.src;
        });
        function check(){
            cnt++;
            cnt === $imgs.length ? fn() : null;
        }
    }
});

/* common 팝업 */
var popFn = {
	show : function(t, params){
		var defaults = {
            onStart : "",
			onLoad : function(){},
			onClose : "",
			btnCloseCl : 'btn_close',
			bgId : '#pop_bg_common.t1',
			align : true,
			htmlCl : "",
            resize : true
		};
		params = params || {};
	    for (var prop in defaults) {
	        if (prop in params && typeof params[prop] === 'object') {
	            for (var subProp in defaults[prop]) {if (! (subProp in params[prop])) params[prop][subProp] = defaults[prop][subProp];}
	        } else if (! (prop in params)) {params[prop] = defaults[prop];}
	    };
        var _this = this;
		if($("body > "+params.bgId).length === 0){
            var bg_id = params.bgId.substring(params.bgId.indexOf('#')+1, params.bgId.indexOf('.') === -1 ? params.bgId.length : params.bgId.indexOf('.'));
            var bg_class = params.bgId.replace("#"+bg_id,"").replace("."," ");
			$("body").append($("<div></div>").prop({id : bg_id}).addClass(bg_class));
		}
		var bg = $("body > "+params.bgId);
		params.htmlCl && $('html').addClass(params.htmlCl);
        t.css('display','block');
		bg.css('display','block');
        !params.onStart ? show() : params.onStart(t, show);
        function show(){
    		var posi = t.css('position');
    		t.checkImgsLoad(function(){
    			var vW = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
    			bg.addClass('on');
    			if(params.bgClose){
    				bg.off('click').on('click',function(){popFn.hide(t,'',params.bgId, params.onClose);});
    			}
    			if(params.align){
            		var vH = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
            		var bxH = t.outerHeight();
            		var scl = posi =='fixed' ? 0 : $(window).scrollTop();
            		t.css({"top":( bxH > vH ? scl : (vH-bxH)/2+scl )+"px"});
                };
    			t.addClass('on');
    			if(params.mobileUI && vW <= 900){
    				TweenMax.fromTo(t,0.5,{'right' : -100+'%'},{'right' : 0+'%', ease: Power3.easeInOut, onComplete : function(){

    				}});
    			}
    			t.find('.'+params.btnCloseCl).on('click',function(){popFn.hide(t,'',params.bgId, params.onClose, params.mobileUI);});
    			if(params.onLoad)params.onLoad();
    		});
    		if(params.align && params.resize){$(window).on('resize', {tg : t}, popFn.resize);};
            _this.close = function(){popFn.hide(t,'',params.bgId, params.onClose, params.mobileUI);}
        }
	},
	hide : function(t, change, bgId, onClose, mobileUI){
		var vW = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
		var bg = bgId ? $(bgId): $("#pop_bg_common");
		onClose ? onClose() : "";
		bg.off('click');
		if(!change)bg.removeClass('on');
		t.removeClass('on notrans');
		$('html').removeClass('of_hide2');
		if(mobileUI && vW <= 900){
    		TweenMax.to(t,0.5,{'right' : -100+'%', ease: Power3.easeInOut, onComplete : function(){

    		}});
    	}
		setTimeout(function(){
			if(!change)bg.remove();
			t.css('display','none');
			t.css({'max-height':'', "top":''});
		},500);
		if(videoPop){
			videoPop.closePop();
		};
		$(window).off('resize', popFn.resize);
        this.close = null;
	},
	resize : function(e){
		var t = e.data.tg;
		var vH = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
		var bxH = t.outerHeight();
		var scl = e.data.posi =='fixed' ? 0 : $(window).scrollTop();
		t.css({"top":( bxH > vH ? scl : (vH-bxH)/2+scl )+"px"});
	}
};

var popFn2 = {
	show : function(t, params){
		var defaults = {
            onStart : "",
			onLoad : function(){},
			onClose : "",
			btnCloseCl : 'btn_close',
			bgId : '#pop_bg_common.t1',
			align : true,
			htmlCl : "",
            resize : true
		};
		params = params || {};
		for (var prop in defaults) {
	        if (prop in params && typeof params[prop] === 'object') {
	            for (var subProp in defaults[prop]) {if (! (subProp in params[prop])) params[prop][subProp] = defaults[prop][subProp];}
	        } else if (! (prop in params)) {params[prop] = defaults[prop];}
	    };
		var _this = this;
		params.htmlCl && $('html').addClass(params.htmlCl);
        t.css('display','block');
        !params.onStart ? show() : params.onStart(t, show);
        function show(){
    		var posi = t.css('position');
    		t.checkImgsLoad(function(){
    			t.addClass('on');
    			t.find('.'+params.btnCloseCl).on('click',function(){popFn2.hide(t,'',params.bgId, params.onClose);});
    			if(params.onLoad)params.onLoad();
    		});
    		/*if(params.align && params.resize){$(window).on('resize', {tg : t, posi : posi}, popFn2.resize);};*/
            _this.close = function(){popFn2.hide(t,'',params.bgId, params.onClose);}
        }
	},
	hide : function(t, change, bgId, onClose){
		onClose ? onClose() : "";
		t.removeClass('on notrans');
		$('html').removeClass('of_hide2');
		setTimeout(function(){
			t.css('display','none');
			t.css({'max-height':'', "top":''});
		},500);
		$(window).off('resize', popFn2.resize);
        this.close = null;
	},
	resize : function(e){
		var t = e.data.tg;
		var vH = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
		var bxH = t.outerHeight();
		var scl = e.data.posi =='fixed' ? 0 : $(window).scrollTop();
		t.css({"top":( bxH > vH ? scl : (vH-bxH)/2+scl )+"px"});
	}
};


/* imgAlign */
function imgAlign(obj, type){
	if (typeof(obj) == "object"){
		for( var i = 0 ; i < obj.length ; i++){
			var divs = $(obj[i]);
			action(divs);
		}
	}else{
		var divs = $(obj);
		action(divs);
	}

	function action(divs){
		divs.each(function(){
			var $this = $(this);
			var divAspect = $this.outerHeight() / $this.outerWidth();
			var img = $this.find('>img');
			img.ensureLoad(function(){
				var imgAspect = img.outerHeight() / img.outerWidth();
				if (imgAspect <= divAspect) {
					var imgWidthActual = $this.outerHeight() / imgAspect;
					var imgWidthToBe = $this.outerHeight() / divAspect;
					if(!img.parent().hasClass('no_center')){
						var marginLeft = -Math.round(((imgWidthActual/$this.outerWidth())-1) / 2 * 100000)/1000;
					}else{
						var marginLeft = 0;
					}
					img.removeClass('w100p').addClass('h100p').css({"margin-left":marginLeft+"%", "top":0});
				} else {
					var imgHeightActual = $this.outerWidth() * imgAspect;
					var imgHeightToBe = $this.outerWidth() * divAspect;
					if(!img.parent().hasClass('no_center')){
						var marginTop = -Math.round(((imgHeightActual/$this.outerHeight())-1) / 2 * 100000)/1000;
					}else{
						var marginTop = 0;
					}
					img.removeClass('h100p').addClass('w100p').css({"top":marginTop+"%", "margin-left":0});
				}
				if(type === 'para'){
					if(transName){
						img[0].style[transName] = 'translateY(0px)';
					}else{
						img.css({"margin-top":0});
					}
				}
			});
		});//each
	}
}//imgAlign

var iscrollFn = {
	area: "",
	isc : "",
	t: "",
	event: true,
	init: function(t) {
		this.t = t;
		this.area = this.t.find('.iscContent');
		this.area.height(this.t.outerHeight()-this.t.find('header').outerHeight()-this.t.find('footer').outerHeight()-40);
		this.setting();
	},
	handleTouchMove: function(e) {
		e.preventDefault();
	},
	setting: function(){
		setTimeout(function(){
			iscrollFn.isc = new IScroll('.contentArea.on .content.on .iscContent > .inner', {
				scrollbars: 'custom',
				mouseWheel: true,
				disableMouse: true,
				preventDefault : true
			});
			$(window).on('resize', iscrollFn.refresh);
			iscrollFn.setCheck();

		},500);
	},
	refresh: function(){
		setTimeout(function () {
			iscrollFn.area.height(iscrollFn.t.outerHeight()-iscrollFn.t.find('header').outerHeight()-iscrollFn.t.find('footer').outerHeight()-40);
        	iscrollFn.setCheck();
        	iscrollFn.isc.refresh();
    	}, 500);
	},
	setCheck: function(){

		var wW = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
		if(wW < 1024) {
			iscrollFn.isc.destroy();
			document.removeEventListener('touchmove', this.handleTouchMove);
			this.event = true;
		}else{
			if(this.event){
				iscrollFn.isc.destroy();
				this.setting();
				this.event = false;
				//document.addEventListener('touchmove', iscrollFn.handleTouchMove, false);
			}
		}

		var scroll_bar = this.area.find('.iScrollVerticalScrollbar');
		scroll_bar.css('display', scroll_bar.find('.iScrollIndicator').css('display'));
	}
};

var iscrollNewsFn = {
	area: "",
	isc : "",
	t: "",
	event: true,
	init: function(t) {
		var vH = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
		this.t = t;
		this.area = this.t.find('.iscContent');
		this.area.height(vH-this.t.find('header').outerHeight()-this.t.find('footer').outerHeight());
		this.setting();
	},
	handleTouchMove: function(e) {
		e.preventDefault();
	},
	setting: function(){
		setTimeout(function(){
			iscrollNewsFn.isc = new IScroll(iscrollNewsFn.t.find('.iscContent').selector+' > .inner', {
				scrollbars: 'custom',
				mouseWheel: true,
				disableMouse: true,
				preventDefault : true
			});
			$(window).on('resize', iscrollNewsFn.refresh);
			iscrollNewsFn.setCheck();

		},500);
	},
	refresh: function(){
		setTimeout(function () {
			var vH = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
			iscrollNewsFn.area.height(vH-iscrollNewsFn.t.find('header').outerHeight()-iscrollNewsFn.t.find('footer').outerHeight());
        	iscrollNewsFn.setCheck();
        	iscrollNewsFn.isc.refresh();
    	}, 500);
	},
	setCheck: function(){

		var wW = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
		if(wW < 1024) {
			//iscrollNewsFn.isc.destroy();
			document.removeEventListener('touchmove', this.handleTouchMove);
			this.event = true;
		}else{
			if(this.event){
				//iscrollNewsFn.isc.destroy();
				//this.setting();
				this.event = false;
				document.addEventListener('touchmove', iscrollFn.handleTouchMove, false);
			}
		}

		var scroll_bar = this.area.find('.iScrollVerticalScrollbar');
		scroll_bar.css('display', scroll_bar.find('.iScrollIndicator').css('display'));
	}
};

(function($) {
	$.fn.customSelect = function(settings) {
		var config = {
			replacedClass: 'custom-select-replaced', // Class name added to replaced selects
			customSelectClass: 'custom-select', // Class name of the (outer) inserted span element
			activeClass: 'custom-select-isactive', // Class name assigned to the fake select when the real select is in hover/focus state
			wrapperElement: '<div class="custom-select-container" />' // Element that wraps the select to enable positioning
		};
		if (settings) {
			$.extend(config, settings);
		}
		this.each(function() {
			var select = $(this);
			if(select.parent().hasClass('custom-select-container')){
				var par = select.parent();
				val = par.find('option:selected', this).text();
				par.find('.'+config.customSelectClass+' span span').text(val);
				return;
			}
			select.addClass(config.replacedClass);
			select.wrap(config.wrapperElement);
			var update = function() {
				val = $('option:selected', this).text();
				span.find('span span').text(val);
			};
			// Update the fake select when the real select’s value changes
			select.change(update);
			select.keyup(update);
			var span = $('<span class="' + config.customSelectClass + '" aria-hidden="true"><span><span>' + $('option:selected', this).text() + '</span></span></span>');
			select.after(span);
			// Change class names to enable styling of hover/focus states
			select.on({
				mouseenter: function() {
					span.addClass(config.activeClass);
				},
				mouseleave: function() {
					span.removeClass(config.activeClass);
				},
				focus: function() {
					span.addClass(config.activeClass);
				},
				blur: function() {
					span.removeClass(config.activeClass);
				},
				change: function() {
					span.removeClass(config.activeClass);
				}
			});
		});
	};

	$.fn.customNumber = function(settings) {
		var config = {
			wrapperElement: '<div class="custom-number-container" />'
		}
		if (settings) {
			$.extend(config, settings);
		}
		this.each(function() {
			var select = $(this);
			select.prop("readonly",true);
			if(select.val() == "" || select.val() == null || select.val() == undefined ) select.val(0);
			select.wrap(config.wrapperElement);

			var minus = $('<span class="minus"></span>');
			var plus = $('<span class="plus"></span>');
			select.before(minus);
			select.after(plus);

			select.parent().on('click', '.minus', function(e) {
				var num = parseInt(select.val());
				var limitnum = parseInt(select.closest('ul').attr("data-limit"));
				if(num>0){
					select.val(num-1).trigger('change');
					select.closest('ul').attr("data-limit", limitnum+1);
				}
			});
			select.parent().on('click', '.plus', function(e) {
				var num = parseInt(select.val());
				var limitnum = parseInt(select.closest('ul').attr("data-limit"));
				if(limitnum == 0){select.closest('ul').find('.iptNum').prop( "disabled", true )}
				if(select.prop( "disabled" ) != true){
					select.val(num+1).trigger('change');
					select.closest('ul').attr("data-limit", limitnum-1);
				}

			});

			select.change(function(e) {
				if($(this).val() == '') $(this).val(0);
			});
		});
	};
})(jQuery);


/*라디오 버튼*/
function checkradio(me){
	var name = me.getAttribute("name");
	setTimeout(function(){
	    if($(me).prop("checked") == false){
	    }else{
	    	$("input[name='"+name+"']").parent().removeClass("on");
	    	$(me).parent().addClass("on");
	    }
	},1)
    return $(me).prop("checked");
}

/*체크박스*/
function checkChkbox(me, p){
	var t;
	p ? t = $(me).closest(p) : t = $(me).parent();
    if($(me).prop("checked") == false) t.removeClass("on");
    else t.addClass("on");
    return $(me).prop("checked");
}

var checkCallbackFn = function(){};
var radioCallbackFn = function(){};

function setChkAndRadio(el){
    el = el || $('.chk_motion');
    var t = $(el);
    t.find('input').filter(function(){return $(this).prop('checked')}).parent().addClass('on');
    t.find('input').filter(function(){return $(this).prop('checked') === false}).parent().removeClass('on');
    t.find('input[type=radio]').off('click', checkCallbackFn).on('click', window.checkCallbackFn = function(){checkradio(this)});
    t.find('input[type=checkbox]').off('click', radioCallbackFn).on('click', window.radioCallbackFn = function(){checkChkbox(this)});
}

/* placeholder */
var placeHFn = {
	align : function(){
		var t = $(".placeh");
		var lb, ip, id;
		var _this = this;
		for(var i = 0 ; i < t.length ; i++){
			lb = t.eq(i).find("label");
			ip = t.eq(i).find("input").length > 0 ? t.eq(i).find("input") : t.eq(i).find("textarea");
			id = lb.attr('for');
			_this.fns(id, lb, ip);
		}
	},
    fns : function(id, lb, ip){
        var _this = this;
        lb.off('click').on('click',function(){placeHFn.focus(id);});
        ip.off('focus').on('focus',function(){_this.hideLabel(id);});
        ip.off('blur').on('blur',function(){_this.showLabel(id);});
        if($("#"+id).val() !== "")_this.hideLabel(id);
    },
	focus : function(id){
		$("#"+id).focus();
	},
	hideLabel : function(id){
		var ele = $("label[for='"+id+"']");
		ele.css("display","none");
	},
	showLabel : function(id){
		if($("#"+id).val() == ""){
			var ele = $("label[for='"+id+"']");
			ele.css("display","");
		}
	}
}


function checkListMotion(){
    $(".lst_check").each(function(){
      //if($(this).hasClass("radio")){
        var $this = $(this)
        var prev;
        $this.find(">span").each(function(){
          if($(this).find(" > input").prop("checked")){
            $(this).addClass("on");
          }
        });//each
      //}else{

      //}//if
    }); //each

    $(".lst_check > span ").find(" > label").off().on("click",function(){
      if($(this).parents(".lst_check").hasClass("radio")){
       $(this).parent().siblings(".on").removeClass("on").end().addClass("on");
     }else{
      $(this).parent().toggleClass("on");
     }//if

  });
}

function bbsCheckbox(t){
	var tg = t ? $(t).find(".check_listbox.box") : $(".check_listbox.box");
	var all = t ? $(t).find(".check_listbox.all") : $(".check_listbox.all");
	tg.each(function(index){
		$(this).find("label").attr("for","user_idx_" + index).end().find("input[type='checkbox']").attr("id","user_idx_" + index);
	})
	.find("label").click(function(){
		var  on = $(this).next().prop("checked") == true;
		if(on){
			$(this).removeClass("on");
		}else{
			$(this).addClass("on");
		}
	});

	all.click(function(){
		var  on = $(this).hasClass("on");
		if(on){
			$(this).removeClass("on").parents("table").find("tbody .check_listbox > input[type='checkbox']").prop("checked",false).prev().removeClass("on");
		}else{
			$(this).addClass("on").parents("table").find("tbody .check_listbox > input[type='checkbox']").prop("checked",true).prev().addClass("on");
		}
	});
}

function setLocalData(o){
	if(typeof(Storage) !== "undefined") {
	    localStorage.setItem(o.name, o.data);
	} else {
	    setCookie(o.name, o.data, 1);
	}
}

function getLocalData(o){
	if(typeof(Storage) !== "undefined") {
	    return localStorage.getItem(o.name);
	} else {
	    return getCookie(o.name);
	}
}

function removeLocalData(o){
	if(typeof(Storage) !== "undefined") {
	    return localStorage.removeItem(o.name);
	} else {
		document.cookie = o.name + '=;expires=Thu, 01 Jan 1970 00:00:01 GMT;';
	}
}

var W3CDOM = (document.createElement && document.getElementsByTagName);
function initFileUploads(type, str) {

	if (str == "" || str === undefined){
		str = "";
	}
	if (!W3CDOM) {
		return;
	}
	var fakeFileUpload = document.createElement('div');
	fakeFileUpload.className = 'fakefile';
	var inputbox = document.createElement('input')
	fakeFileUpload.appendChild(inputbox);
	fakeFileUpload.getElementsByTagName('input')[0].className = 'ipt';
	fakeFileUpload.getElementsByTagName('input')[0].readOnly = true;
	if (type == 'btn') {
		var btn = document.createElement('button');
		fakeFileUpload.appendChild(btn);
	} else {
		var image = document.createElement('img');
		image.src = '/images/btn/btn_upload_off.gif';
		fakeFileUpload.appendChild(image);
	}

	var x = document.getElementsByTagName('input');

	for (var i = 0; i < x.length; i++) {
		if (x[i].type != 'file') continue;
		if (x[i].parentNode.className != 'fileinputs' || $(x[i]).next().hasClass('fakefile')) continue;
		//x[i].className = 'file';
		x[i].className = "file"+str;
		var clone = fakeFileUpload.cloneNode(true);
		x[i].parentNode.appendChild(clone);
		x[i].relatedElement = clone.getElementsByTagName('input')[0];
		x[i].onchange = x[i].onmouseout = function() {
			this.relatedElement.value = this.value;
		}
		if (type == 'btn') {
			var btn_txt = x[i].parentNode.getAttribute("data-text");
			x[i].parentNode.getElementsByTagName('button')[0].innerHTML = btn_txt;
			$(x[i].parentNode.getElementsByTagName('button')[0]).on('click', function() {
				return false;
			})
		}
	}

}
//fakefile

var getUrlParameter = function getUrlParameter(sParam) {
    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : sParameterName[1];
        }
    }
};


var chkAccount = {
	id : function(id){
		var t = $(id), list = t.closest('li'), meg = list.find(".status");
		this.nul(t,meg, list);
        var idReg = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/;
        if(this.nul(t,meg,list)) return;
        if( !idReg.test( t.val() ) ) {
            list.removeClass("good").addClass('bad');
			meg.html("잘못된 이메일 형식입니다.");
            return false;
        } else {
			$.ajax({
				type:"get",
				url : '/member/signup/?at=search&userId=' + t.val(),
				dataType:"json",
				success : function(data){
					if(data.result) {
						list.addClass("good").removeClass('bad');
						meg.html("&nbsp;");
					} else {
						switch (data.code) {
							case 'exist' :
								meg.html("사용중인 아이디입니다.");
								break;
						}
						list.removeClass("good").addClass('bad');
					}
				},error : function(a,b,c){
					alert('error : '+c);
				}
			});
		}
	},
	email : function(id){
		var t = $(id), list = t.closest('li'), meg = list.find(".status");
		var regex=/^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/;
		if(this.nul(t,meg,list)) return;
		if(!regex.test(t.val())){
			list.removeClass("good").addClass('bad');
			meg.html("잘못된 이메일 형식입니다.");
		}else{
			list.addClass("good").removeClass('bad');
			meg.html("사용가능한 이메일 입니다.");
		}
	},
	name : function(id){
		var t = $(id), list = t.closest('li'), meg = list.find(".status");
		this.nul(t,meg, list);
		if(this.nul(t,meg,list)) return;
		if(t.val().length < 2 || t.val().length > 13 ){
			list.removeClass("good").addClass('bad');
			meg.html("4 ~ 13자를 입력해주세요.");
			return false;
		}else{
			list.addClass("good").removeClass('bad');
			meg.html("&nbsp;");
		}
	},
	pw01 : function(id){
		var t = $(id), list = t.closest('li'), meg = list.find(".status");
		if(this.nul(t,meg,list)) return;
		var chk = 0;
		if(t.val().search(/[0-9]/g) != -1 ) chk ++; //숫자
	    if(t.val().search(/[a-z]/ig)  != -1 ) chk ++; //영문
	    //if(t.val().search(/[!@#$%^&*()?_~]/g)  != -1  ) chk ++; //특수기호
		if(/^[a-zA-Z0-9!@#$%^&*()?_~]{8,15}$/.test(t.val())) chk ++; //8~14자
		if(chk < 3){
			list.removeClass("good").addClass('bad');
			meg.html("형식에 맞지 않습니다.");
			return false;
		}else{
			list.addClass("good").removeClass('bad');
			meg.html("&nbsp;");
		}
	},
	pw02 : function(id, pw1){
		var t = $(id), list = t.closest('li'), meg = list.find(".status");
		if(this.nul(t,meg,list)) return;
		if(t.val() != $(pw1).val()){
			list.removeClass("good").addClass('bad');
			meg.html("패스워드가 일치하지 않습니다.");
			return false;
		}else{
			list.addClass("good").removeClass('bad');
			meg.html("&nbsp;");
		}
	},
	phone : function(obj){
		var t = $(obj), list = t.closest('li'), meg = list.find(".status");
		if(this.nul(t,meg,list)) return;
		if(t.val().length < 11){
			list.removeClass("good").addClass('bad');
			meg.html("전화번호를 정확히 입력해주세요.");
			return false;
		}

		list.addClass("good").removeClass('bad');
		meg.html("&nbsp;");

	},
	birthday : function(obj){
		var t = $(obj), list = t.closest('li'), meg = list.find(".status");
		if(this.nul(t,meg,list)) return;
		if(t.val().length < 1){
			list.removeClass("good").addClass('bad');
			meg.html("생년월일을 입력해주세요.");
			return false;
		}

		list.addClass("good").removeClass('bad');
		meg.html("&nbsp;");

	},
	address : function(obj){
		var t = $(obj), list = t.closest('li'), meg = list.find(".status");
		if(this.nul(t,meg,list)) return;
		if(t.val().length < 1){
			list.removeClass("good").addClass('bad');
			meg.html("생년월일을 입력해주세요.");
			return false;
		}

		list.addClass("good").removeClass('bad');
		meg.html("&nbsp;");

	},
	nul : function(t1,meg1,list){
		if(t1.val() === ""){
			meg1.html("");
            list.removeClass("good bad");
            return true;
		}
	},
	submit: function () {
		options = {
			beforeSubmit : this.formCheck,
			success      : this.joinResult,
			dataType     : 'json'
		};
		$("#joinForm").ajaxSubmit(options);
	},
	formCheck: function () {
		var result = true;
		$("#step02").find('li.require').each(function () {
			if(!$(this).hasClass('good')) {
				if($(this).find('input[type="text"]').length > 0) {
					$(this).find('input[type="text"]').eq(0).focus().blur().focus();
					//$('html,body').animate({scrollTop: $(this).find('input[type="text"]').offset().top-50}, 500);
					result = false;
					return result;
				}else if($(this).find('input[type="number"]').length > 0) {
					$(this).find('input[type="number"]').focus().blur().focus();
					//$('html,body').animate({scrollTop: $(this).find('input[type="number"]').offset().top-50}, 500);
					result = false;
					return result;
				} else if($(this).find('select').length > 0) {
					$(this).removeClass("good").addClass('bad');
					$(this).find(".status").html("급수를 선택해주세요.");
					//$('html,body').animate({scrollTop: $(this).find('select').offset().top-50}, 500);
					result = false;
					return result;
				} else if($(this).find('input[type="radio"]').length > 0) {
					$(this).removeClass("good").addClass('bad');
					$(this).find(".status").html("아바타를 선택해주세요.");
					//$('html,body').animate({scrollTop: $(this).find('input[type="radio"]').offset().top-50}, 500);
					result = false;
					return result;
				} else if($(this).find('input[type="password"]').length > 0) {
					$(this).find('input[type="password"]').focus().blur().focus();
					//$('html,body').animate({scrollTop: $(this).find('input[type="password"]').offset().top-50}, 500);
					result = false;
					return result;
				}
			}
		});
		return result;
	},
	joinResult: function (data) {
		if (data.result) {
			memberPop.addCont({url : "/member/signup/?at=main&mode=completed", showSide : "/member/intro_membership.php", data : {age : $("#joinForm").find('input[name="age"]').val()}});
		} else {
			switch (data.code) {
				case 'exist' : alert('이미 가입된 이메일 주소 입니다. 다른 이메일 주소를 이용해 주세요.'); break;
				default : alert('회원 가입 에러입니다. 관리자에게 문의해주세요.'); break;
			}
			alert('')
		}
	}
};


/*로그인 회원가입 관련 팝업창*/
var MemberPopFn = function(params){
	params = params || {};
	var _this = this, popInner, currBx, nextBx, contOutBx,  moveMotion, moving;
	this.popBx = '#pop_member_login_signup';

	this.setCont = function(o){
		o = o || {};
		if(o.url === undefined) throw new Error('URL 주소가 필요합니다.');
		var t = _this.popBx.find('.content:not(".on")').eq(0);
		$.ajax({
			url : o.url,
			type : params.type || 'GET',
            data : o.data || {},
			dataType : 'html',
			success : function(data){
				t.html(data).siblings('.on').removeClass('on').end().addClass('on');
				t.checkImgsLoad(function(){
					var h = t.outerHeight();
                    var ch = contOutBx.height();
                    var popT = parseInt(_this.popBx.css('top'));
                    var innerT = parseInt(popInner.css('top'));
					if(!moveMotion){
						moveMotion = !moveMotion;
						o.callFn && o.callFn();
					}else{
                        var vH = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
                        var scl = (document.documentElement && document.documentElement.scrollTop) || document.body.scrollTop;
                        var tgH = innerT+(ch-h)/2;
                        _this.popBx.removeClass('show_right_sec');
                        //TweenMax.to(popInner, 0.6, {'top' : (popT + tgH <= 0 ? -popT : tgH), ease: Power3.easeInOut});
                        TweenMax.to(_this.popBx, 0.6, {'top' : ( h > vH ? scl : (vH-h)/2+scl ), ease: Power3.easeInOut});
						TweenMax.to(contOutBx,0.6,{'height' : h, ease: Power3.easeInOut, onComplete : function(){
							o.callFn && o.callFn();
                            contOutBx.css('height','');
                            moving = false;
                            //console.log($(document).height())
						}});
					}
				});
			},
			error : function(a,b,c){
				alert('error : ' + c);
                moving = false;
			}
		})
	};

	this.showPop = function(){
		ajaxShowPopCont({
	        url : '/member/popup.af',
            data : params.data || {},
            resize : true,
            bgClose : false,
            mobileUI : true,
			onStart : function(bx, showFn){
				_this.popBx = $(_this.popBx);
                popInner = _this.popBx.find('> .inner');
				contOutBx = _this.popBx.find('#bx_multi_content');
				_this.setCont({callFn : showFn, url : params.url || '/member/login.af'});
			}
	    });
	};

	this.addCont = function(o){
        if(moving) return;
        moving = true;
		o  = o || {};
        if(o.showSide) _this.addSubSect({url:o.showSide, noShow : true});
		var direc = !o.direc || o.direc === 'next' ? 1 : -1;
		currBx = _this.popBx.find('.content.on').eq(0);
		nextBx = _this.popBx.find('.content:not(".on")').eq(0);
		TweenMax.set(nextBx,{'left' : 100*direc+'%'});
        TweenMax.set(contOutBx,{'height' : contOutBx.css('height')});
        //TweenMax.to($(".pop_style_login, #bx_multi_content"),0.5,{'width' : 470, ease: Power3.easeInOut});
		function move(){
			TweenMax.to(nextBx,0.5,{'left' : '0%', ease: Power3.easeInOut});
			TweenMax.to(currBx,0.5,{'left' : -100*direc+'%', ease: Power3.easeInOut});
            if(o.showSide) _this.popBx.addClass('show_right_sec');
		}
		_this.setCont({url : o.url, callFn : move, data : o.data});
	};

	this.addSubSect = function(o){
		o = o || {};
		if(o.url === undefined) throw new Error('URL 주소가 필요합니다.');
        //_this.popBx = $(_this.popBx);
        var t = _this.popBx.find('#bx_multi_aside');
        $.ajax({
            url : o.url,
            data : o.data,
            dataType : 'html',
            type : 'GET',
            success : function(data){
                t.html(data);
                //if(!o.noShow) t.imagesLoaded(function(){_this.popBx.addClass('show_right_sec');});
                if(!o.noShow) _this.popBx.addClass('show_right_sec');
                _this.popBx.find('.btn_close2').on('click',function(){_this.popBx.removeClass('show_right_sec');});
            },
            error : function(a,b,c){
                alert('error : ' + c);
            }
        });
    };

    this.goSignupStpe2 = function () {
		var inputs = $('input[name="chkTerm"]'), len = inputs.length;
		var cnt = 0;
		for (var i = 0 ; i < len ; i++) {
			if(inputs.eq(i).prop('checked')) cnt++;
		}
		if (len > cnt) {
			alert('약관을 체크를 확인해주세요.');
			return false;
		}
		//실명 인증
        if($('div.content.on #certPhone').length > 0) {
            if($('#certPhone').is(':checked')) {
            	certi('checkplus','/member/checkplus.sf');
                //_this.realNameCert();
            } else if($('#certIpin').is(':checked')) {
            	certi('ipin','/member/ipin.af');
                //_this.realNameCert();
            } else {
                alert('실명 인증 방법을 선택해주세요.');
                return false;
            }
        } else {
           //memberPop.addCont({url: "/member/signupStep2.af"});
        }
	};

	this.goSignupStpe3 = function () {
		if(chkAccount.formCheck() == true){
			var formData = $("#step02").serialize().replace(/%/g,'%25');
			memberPop.addCont({url: "/member/signupStep3.af", data: formData});
		}
		
	};

	this.goSignupStpe4 = function () {
		var formData = $("#step03").serialize().replace(/%/g,'%25');
		memberPop.addCont({url: "/member/signupStep4.af", data: formData});
	};

	this.realNameCert = function () {
        //window.open("", "cert_window", "width=500, height=300, scrollbars=no");
        //$("#formCertPhone").submit();
		var formData = $("#step01").serialize();
        memberPop.addCont({url: "/member/signupStep2.af", data: formData});
	};

	this.showPop();
}
var memberPop;

var memberFn = {
    searchId : function(){
        memberPop.addSubSect({url : '/member/searchId.af'})
    },
    resetPwdCer : function(){
        memberPop.addSubSect({url : '/member/resetPwdCerti.af'})
    },
    resetPwd : function(o){
        o = o || {};
        memberPop.addSubSect({url : '/member/resetPwd.af', data : o.data});
    },
    inactivityMemberCer : function(o){
        o = o || {};
        memberPop.addSubSect({url : '/member/inactivityCerti.af', data : o.data});
    },
    inactivityMember : function(o){
        o = o || {};    	
        memberPop.addSubSect({url : '/member/inactivity.af', data : o.data});
    },
    checkSignUp : function(){
        memberPop.addSubSect({url : '/member/checkSignup.af'});
    }
};

/*ajax 팝업 띄우기*/
function ajaxShowPopCont(o){
	var t = o.target ? $(o.target) : $("#pop_bx_common");
	o.data = o.data || {};
	o.bg = o.bg || "#pop_bg_common.t1";
	$.ajax({
		url : o.url,
		type : o.type || "get",
		dataType : "html",
		data : o.data,
		success : function(data){
			if(!o.append)t.html('');
			t.append(data);
			var popup = o.pop ? $(o.pop) : t.find(">*").eq(0);
			popFn.show(popup, {
				motion : o.motion || true, 
				bgId : o.bg, onStart : o.onStart, 
				onLoad : o.onLoad, 
				onClose : o.onClose, 
				resize : o.resize === undefined ?  true : o.resize, 
				bgClose : o.bgClose === undefined ?  true : o.bgClose, 
				mobileUI : o.mobileUI === undefined ?  false : o.mobileUI
			});
		},
		error : function(a,b,c){
			alert(c);
		}
	})
}


var inputlabel = {
	align : function(){
		var t = $(".inputlabel");
		var lb, ip, id;
		var _this = this;
		for(var i = 0 ; i < t.length ; i++){
			lb = t.eq(i).find(".tit");
			ip = t.eq(i).find("input").length > 0 ? t.eq(i).find("input") : t.eq(i).find("textarea");
			id = lb.attr('for');
			_this.fns(id, lb, ip);
		}
	},
    fns : function(id, lb, ip){
        var _this = this;
        lb.off('click').on('click',function(){inputlabel.focus(id);});
        ip.off('focus').on('focus',function(){_this.moveLabel(id);});
        ip.off('blur').on('blur',function(){_this.showLabel(id);});
        if($("#"+id).val() !== "")_this.moveLabel(id);
    },
	focus : function(id){
		$("#"+id).focus();
	},
	moveLabel : function(id){
		var ele = $("label[for='"+id+"']");
		/*ele.animate({
			"top": -35}, 300, function() {
		});*/
		ele.css("top",-35);
	},
	showLabel : function(id){
		if($("#"+id).val() == ""){
			var ele = $("label[for='"+id+"']");
			/*ele.animate({
				"top": ""}, 300, function() {
			});*/
			ele.css("top","");
		}
	}
}

/* 가격 콤마 찍기 */
function priceCommas(x) {
	return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}




var contentBoxFn = function(params){
	params = params || {};
	var _this = this, popInner, currBx, nextBx, contOutBx,  moveMotion, moving = false, isc, activeIdx, nowIdx;
	this.popBx = $('.contentArea:not(".on")').eq(0).find('.content-box');
	popInner = this.popBx.find('> .inner');
	contOutBx = this.popBx.find('.box-content');

	this.setCont = function(o){
		o = o || {};
		if(o.url === undefined) throw new Error('URL 주소가 필요합니다.');

		$.ajax({
			url : o.url,
			type : params.type || 'GET',
            data : o.data || {},
			dataType : 'html',
			contentType: "application/x-www-form-urlencoded; charset=UTF-8", 
			success : function(data){
				contOutBx.append('<div class="content" style="opacity: 0;"></div>');
				var t = contOutBx.children('.content:not(".on")').eq(0);
				t.html(data).siblings('.on').removeClass('on').end().addClass('on');
				contOutBx.css('height', _this.popBx.height() - _this.popBx.find('.box-tab').height());
				
				t.checkImgsLoad(function(){
					if(t.find('.iscContent').length > 0){
						iscrollFn.init(t);
					}
					
					if(!moveMotion){
						moveMotion = !moveMotion;
						contOutBx.children('.content:not(".on")').remove();
						o.callFn && o.callFn();
						_this.showContBox();
					}else{
						o.callFn && o.callFn();
					}

					$(window).on('resize', _this.resize);

				});
			},
			error : function(a,b,c){
				alert('error : ' + c);
                moving = false;
			}
		});
	};

	this.resize = function(){
		setTimeout(function(){
			contOutBx.css({'height': _this.popBx.height() - _this.popBx.find('.box-tab').height()});
			var t = _this.popBx.find('.content.on').eq(0);
			var ch = contOutBx.height();
			/*if(t.find('.iscContent').length > 0){
				iscrollFn.init(t, ch);
			}*/
			var vH = (window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight) - $('#mainMenu .nav').height() - $('#footerTop').height();
	        var bxH = _this.popBx.outerHeight();
	        var posi = _this.popBx.css('position');
	        var scl = posi =='fixed' ? 0 : $(window).scrollTop();
	        _this.popBx.css({"top":( bxH > vH ? scl : (vH-bxH)/2+scl+20 )+"px"});
		},500);
			
	};

	this.tabCont = function(o){
		if(!$(o.target).parent().hasClass("active")){
			o  = o || {};
			if(moving) return;
			moving = true;
			
			function move(){
				if(o.target!=null){
					activeIdx = $(o.target).parents("ul").find(".active").index();
					nowIdx = $(o.target).parents("ul").find("li").index($(o.target).parent());
		            $(o.target).parent().siblings('.active').removeClass('active').end().addClass('active');
		    	}
				currBx = contOutBx.children('.content').eq(0);
				nextBx = contOutBx.children('.content').eq(1);

				if(activeIdx < nowIdx || o.move == "next"){
					TweenMax.fromTo(nextBx,0.5,{'left' : 100+'%', 'top' : 0, 'opacity' : 1},{'left' : '0%', ease: Power3.easeInOut});
					TweenMax.fromTo(currBx,0.5,{'left' : 0, 'top' : 0, 'opacity' : 1},{'left' : -100+'%', ease: Power3.easeInOut, onComplete : function(){
						contOutBx.children('.content:not(".on")').remove();
						moving = false;
					}});
				}else if(activeIdx > nowIdx || o.move == "prev"){
					TweenMax.fromTo(nextBx,0.5,{'left' : -100+'%', 'top' : 0, 'opacity' : 1},{'left' : '0%', ease: Power3.easeInOut});
					TweenMax.fromTo(currBx,0.5,{'left' : 0, 'top' : 0, 'opacity' : 1},{'left' : '100%', ease: Power3.easeInOut, onComplete : function(){
						contOutBx.children('.content:not(".on")').remove();
						moving = false;
					}});
				}else if(o.move == "page"){
					TweenMax.fromTo(nextBx,0.5,{'left' : 0, 'top' : 0, 'opacity' : 0},{'opacity' : 1, ease: Power3.easeInOut});
					TweenMax.fromTo(currBx,0.5,{'left' : 0, 'top' : 0, 'opacity' : 1},{'opacity' : 0, ease: Power3.easeInOut, onComplete : function(){
						contOutBx.children('.content:not(".on")').remove();
						moving = false;
					}});
				}
			}
			_this.setCont({url : o.url, callFn : move, data : o.data});
		}
	};

	this.showCont = function(o){
		o  = o || {};
	    if(moving) return;
	    moving = true;

		function move(){
			currBx = contOutBx.children('.content').eq(0);
			nextBx = contOutBx.children('.content').eq(1);
			if(o.move == "next"){
				TweenMax.fromTo(nextBx,0.5,{'top' : 100+'%', 'left' : 0+'%', 'opacity' : 1},{'top' : '0%', ease: Power3.easeInOut});
				TweenMax.fromTo(currBx,0.5,{'top' : 0, 'left' : 0, 'opacity' : 1},{'top' : -100+'%', ease: Power3.easeInOut, onComplete : function(){
					contOutBx.children('.content:not(".on")').remove();
					moving = false;
				}});
			}else{
				TweenMax.fromTo(nextBx,0.5,{'top' : -100+'%', 'left' : 0+'%', 'opacity' : 1},{'top' : '0%', ease: Power3.easeInOut});
				TweenMax.fromTo(currBx,0.5,{'top' : 0, 'left' : 0, 'opacity' : 1},{'top' : '100%', ease: Power3.easeInOut, onComplete : function(){
					contOutBx.children('.content:not(".on")').remove();
					moving = false;
				}});
			}
		}
		_this.setCont({url : o.url, callFn : move, data : o.data});
	};

	this.slideCont = function(o){
		_this.setCont({url : o.url, callFn : move, data : o.data});
	};

	this.showContBox = function(o){
		_this.popBx.checkImgsLoad(function(){
			var vW = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth
			var vH = (window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight) - $('#mainMenu .nav').height() - $('#footerTop').height();
	        var bxH = _this.popBx.outerHeight();
	        var posi = _this.popBx.css('position');
	        var scl = posi =='fixed' ? 0 : $(window).scrollTop();
	        _this.popBx.css({"top":( bxH > vH ? scl : (vH-bxH)/2+scl+15 )+"px"});

	        if(vW > 1023){
	        	TweenMax.set(_this.popBx,{'y' : '100%'});
				TweenMax.to(_this.popBx,1,{'y' : '0%', ease: Power3.easeInOut});
	        }
		        
		});
	};

}

var contentBox;


/*메뉴 이동*/
var PageContentFn = function(params){
	params = params || {};
	var _this = this, currPage, moveMotion, moving, pageList, isc;

	this.change = function(o){
		if(o.para){
			$('.box-tab > ul > li.active').removeClass('active');
			$('.box-tab > ul > li.'+o.para.page).addClass('active');
		}
		if(currPage != o.url){
			currPage = o.url;
			_this.callPage({url :o.url, para : o.para});
		}else{ 
                // 시설소개 구분
                if(o.para != undefined && o.para.pageing){
                	console.log(o.para);
                  pNum = o.para.pageing;
                  if(pNum != undefined) {
                    pageStartNum = pNum;
                  }
                  if(pNum == 3) {
                  	bxSlide.goToSlide(1);
                  	rfMotion(0);
                  	}else if(pNum == 4) {
			bxPoint = 0;
			bxSlide.goToSlide(bxPoint);
			$topMenu1.siblings(".n3").trigger("click").find("button").trigger("click");			
		     }else if(pNum == 6) {
		     	bxPoint = 0;
			bxSlide.goToSlide(bxPoint);
			$topMenu1.siblings(".n4.f4").trigger("click").find("button").trigger("click");				
		      }else if(pNum == 5) {
		      	bxPoint = 2;
		      	bxOption.speed = 5000;
			bxSlide.goToSlide(bxPoint);
			$topMenu1.siblings(".n5.f3").trigger("click").find("button").trigger("click");				

                  }else{
                  	$topMenu1.siblings(".n"+pageStartNum).trigger("click").find("button").trigger("click");     
                  }
                  return;
                }	
                	
			var reUrl = o.url;
			var urlDiv = reUrl.split("/");
			var realUrl = '/' + urlDiv[1] +'/page';
			if(o.para.page == 'myinfo') o.para.page = 'pwd';
			if(reUrl.indexOf('step') != -1){realUrl = '/' + urlDiv[1] +"/"+urlDiv[2] +'/step';}
			if(reUrl.indexOf('service') != -1){realUrl = '/' + urlDiv[1] +"/";}
			if(reUrl.indexOf('mypage') != -1){realUrl = '/' + urlDiv[1] +"/";}
			if(contentBox != null && o.para.page != undefined) contentBox.tabCont({url: realUrl + o.para.page+'.af', move: 'page'});
			if(o.para.filter != undefined) news.setFilter(o.para.filter);
			if(pageContent.pageList){
				_this.updatePage();
			}
		}
		return false;
	};
	this.callPage = function(o){	//컨텐츠 가져오기
     	pageMotionFlag = true;
		var url = o.url;
		$.ajax({
	         type:"GET",
	         url:url,
	         ansync :false,
	         dataType:"html",
			 data:o.para,
	         success : function(data) {
	         	data = $(data).find('.contentArea').html();
	         	var winH = $(window).height();
	         	var currP = $('#wrap').find('.contentArea.on').eq(0);
				var nextP = $('#wrap').find('.contentArea:not(".on")').eq(0);
	         	nextP.html(data).siblings('.on').removeClass('on').end().addClass('on');
				$("#main .slider-item, .bx-viewport, .content-inner").height(winH-$('#footerTop').height());

	         	TweenMax.to(nextP,1,{'opacity' : '1', ease: Power3.easeInOut});
				TweenMax.to(currP,1,{'opacity' : '0', ease: Power3.easeInOut, onComplete:function(){
					currP.html("");
				}});

			},
			error : function(xhr, status, error) {
				alert(error);
			}
	    });
	}

	this.setPaging = function(o){
		$.each(pageContent.pageList, function(i, v) {
			var _index = Object.keys(pageContent.pageList).length-parseInt(i)+1;		
			var pagerItem = '<div class="pager-item" style="z-index:'+_index+'"><a href="#'+currPage+'?page='+i+'">'+pageContent.pageList[i].step+'</a></div>'
			$('.contentArea:not(".on")').find('.box-paging').append(pagerItem);
		});
		if(pageContent.pageList){
			setTimeout(function(){ _this.updatePage(); }, 1000)
		}
	};

	this.updatePage = function(o){
		var idx = getParameter("page");
		var p_count = Object.keys(pageContent.pageList).length;
		if(idx > 1) {
			$('.box-prev')
				.attr('href', '#'+currPage+'?page='+(parseInt(idx)-1))
				.find('span').text(pageContent.pageList[parseInt(idx)-1].title);

		}else{
			$('.box-prev')
				.attr('href', '#'+currPage+'?page='+p_count)
				.find('span').text(pageContent.pageList[p_count].title);
		}

		if(idx < p_count) {
			$('.box-next')
				.attr('href', '#'+currPage+'?page='+(parseInt(idx)+1))
				.find('span').text(pageContent.pageList[parseInt(idx)+1].title);

		}else{
			$('.box-next')
				.attr('href', '#'+currPage+'?page='+1)
				.find('span').text(pageContent.pageList[1].title);
		}
		$('.pager-item').removeClass('active').eq(idx-1).addClass('active');
	};

}
var pageContent = new PageContentFn();

/*hash 변경시 ajax로 컨텐츠 가져오기*/
function moveByHash(){
	var idx;
	//$(window).scrollTop(0);
	$('.fullpage-wrapper').each(function() {
		$('#wrap').fullpage.moveTo(2);
	})

	if(iscrollFn.isc != '') {
		iscrollFn.isc.destroy();
		document.removeEventListener('touchmove', iscrollFn.handleTouchMove);
	}
	
	if(location.hash.indexOf("#") >= 0){
		var arrLocationHref = location.hash.split("#");
		idx = arrLocationHref[1];
		var urlDiv = arrLocationHref[1].split("?");
		var url = urlDiv[0];
		if(url == '') { url =  "/main.af"; }
		var hashPara = {};
		//var conIdx = 0;
		if(urlDiv[1]){
			hashPara = QueryString(urlDiv[1]);
			//if(hashPara.conIdx)conIdx = hashPara.conIdx;
		}
		pageContent.change({url : url, para : hashPara});
	}else{
		pageContent.change({url : "/main.af"});
	}
}

 function QueryString(str) {
 	// This function is anonymous, is executed immediately and
 	// the return value is assigned to QueryString!
 	var query_string = {};
 	//var query = window.location.search.substring(1);
 	var query = str;
 	var vars = query.split("&");
 	for (var i = 0; i < vars.length; i++) {
 		var pair = vars[i].split("=");
 		// If first entry with this name
 		if (typeof query_string[pair[0]] === "undefined") {
 			query_string[pair[0]] = decodeURIComponent(pair[1]);
 			// If second entry with this name
 		} else if (typeof query_string[pair[0]] === "string") {
 			var arr = [query_string[pair[0]], decodeURIComponent(pair[1])];
 			query_string[pair[0]] = arr;
 			// If third or later entry with this name
 		} else {
 			query_string[pair[0]].push(decodeURIComponent(pair[1]));
 		}
 	}
 	return query_string;
 }

 var getParameter = function (param) {
    var returnValue;
    var url = location.href;
    var parameters = (url.slice(url.indexOf('?') + 1, url.length)).split('&');
    for (var i = 0; i < parameters.length; i++) {
        var varName = parameters[i].split('=')[0];
        if (varName.toUpperCase() == param.toUpperCase()) {
            returnValue = parameters[i].split('=')[1];
            return decodeURIComponent(returnValue);
        }
    }
};


var VideoPopFn = function(params){
	params = params || {};
	var _this = this, popInner, currBx, nextBx, contOutBx,  moveMotion, moving;
	this.popBx = '#video_popup';

	this.setCont = function(o){
		o = o || {};
		if(o.url === undefined) throw new Error('URL 주소가 필요합니다.');
		var t = _this.popBx.find('.content:not(".on")').eq(0);
		$.ajax({
			url : o.url,
			type : params.type || 'GET',
            data : o.data || {},
			dataType : 'html',
			success : function(data){
				t.html(data).siblings('.on').removeClass('on').end().addClass('on');
				t.checkImgsLoad(function(){
					_this.resize(o,t);
					$(window).on('resize',function(){ _this.resize(o,t)});
				});
			},
			error : function(a,b,c){
				alert('error : ' + c);
                moving = false;
			}
		})
	};

	this.showPop = function(){
		ajaxShowPopCont({
	        url : '/about/concept/videoPop.af',
            data : params.data || {},
            resize : false,
			onStart : function(bx, showFn){
				_this.popBx = $(_this.popBx);
                popInner = _this.popBx.find('> .inner');
				contOutBx = _this.popBx.find('#bx_multi_content');
				_this.setCont({callFn : showFn, url : params.url});
			}
	    });
	};

	this.closePop = function(o){
		var t = _this.popBx.find('.content.on').eq(0);
		t.html('').removeClass('on');
	}
	
	this.resize = function(o,t){
		var h = $('.content-box').height()
        var ch = contOutBx.height();
        var popT = parseInt(_this.popBx.css('top'));
        var innerT = parseInt(popInner.css('top'));
		var vW = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
		var vHm = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
		var vH = (window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight) - $('#mainMenu .nav').height() - $('#footerTop').height();
		var scl = (document.documentElement && document.documentElement.scrollTop) || document.body.scrollTop;
		var tgH = innerT+(ch-h)/2;
		if(vW > 1023){
			TweenMax.to(contOutBx,0.6,{'height' : h, ease: Power3.easeInOut, onComplete : function(){
				o.callFn && o.callFn();
				_this.popBx.css({"top":$('.content.on').offset().top+"px"});
                moving = false;
			}});
		}else{
			TweenMax.to(contOutBx,0.6,{'height' : vHm, ease: Power3.easeInOut, onComplete : function(){
				o.callFn && o.callFn();
				_this.popBx.css({"top":( h > vH ? scl : (vH-h)/2+scl )+"px"});
                moving = false;
			}});
		}
	}

	this.showPop();
}
var videoPop;

var ResTicketPopFn = function(params){
	params = params || {};
	params.url = '/mypage/reserveView.af';
	var _this = this, popInner, currBx, nextBx, contOutBx,  moveMotion, moving;
	this.popBx = $('#resTicketDetail');
	contOutBx = this.popBx.find('.content');

	this.setCont = function(o){
		o = o || {};
		if(o.url === undefined) throw new Error('URL 주소가 필요합니다.');
		var t = _this.popBx.find('.content:not(".on")').eq(0);
		$.ajax({
			url : o.url,
			type : params.type || 'GET',
            data : o.data || {},
			dataType : 'html',
			success : function(data){
				t.html(data);

				if(params.data.stat == 'Y'){
					$('#resTicketDetail .btns button').prop( "disabled", false );
					$('#resTicketDetail .resTicket .res').css('display', '');
					$('#resTicketDetail .resTicket .cancel').css('display', 'none');
					$('#resTicketDetail .total_payment').css('display', '');
					$('#resTicketDetail .canceled_payment').css('display', 'none');
				}else if(params.data.stat == 'N'){
					console.log("예약취소");
					$('#resTicketDetail .btns button ').prop( "disabled", true );
					$('#resTicketDetail .resTicket .res').css('display', 'none');
					$('#resTicketDetail .resTicket .cancel').css('display', '');
					$('#resTicketDetail .total_payment').css('display', 'none');
					$('#resTicketDetail .canceled_payment').css('display', '');
				}

				popFn2.show(_this.popBx);

				var boxH = _this.popBx.outerHeight();
				var h = t.outerHeight();
                var ch = contOutBx.height();

                if(t.find('.iscContent').length > 0){
					iscrollFn.init(t);
				}

					/*if(t.find('.iscContent').length > 0){
						//console.log(ch, t.find('header').outerHeight(), t.find('footer').outerHeight());
						t.find('.iscContent').height(ch-t.find('header').outerHeight()-t.find('footer').outerHeight());
						setTimeout(function(){
							//_this.popBx.find('.box-content>.content:not(".on")').html('');
			    			_this.isc = new IScroll(t.find('.iscContent').selector+' > .inner', {
			    			scrollbars: 'custom',
			    			mouseWheel: true,
			    			disableMouse: true
			    		});
			    		document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
			            },500);
					}*/

				_this.resize();
				$(window).on('resize',function(){ _this.resize()});
				$('#resTicketDetail').find('.btn_close').on('click',function(){$('#resTicketDetail').closest('.content.on').css('height', '');});



			},
			error : function(a,b,c){
				alert('error : ' + c);
                moving = false;
			}
		})
	};

	this.resize = function(o,t){
		var vW = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
		var tH = $('#resTicketDetail section').outerHeight();
		if(vW < 1024 && tH > 0){
			$('#resTicketDetail').closest('.content.on').css('height', tH);
		}else{
			$('#resTicketDetail').closest('.content.on').css('height', '');
		}
	}

	this.setCont(params)

}
var resTicketPop;


//뉴스페이지
var NewsFn = function(params){
	/*params = params || {
		slideSelector: '.item.active',
		minSlides: 1,
		maxSlides: 5,
		slideWidth: 300,
		infiniteLoop: false,
		slideMargin: 20,
		pager:false,
		controls: true,
		responsive:true,
		hideControlOnEnd: true,
		adaptiveHeight: true,
		onSliderLoad:function(i) {
			_this.listCheck();
		}
	};*/

	params = params || {
		infinite: false,
		slidesToShow: 5,
  		slidesToScroll: 1,
  		adaptiveHeight: true,
  		variableWidth: true,
  		responsive: [
		    {
		      breakpoint: 1200,
		      settings: {
		        slidesToShow: 3
		      }
		    },
		    {
		      breakpoint: 900,
		      settings: {
		        slidesToShow: 2
		      }
		    },
		    {
			  breakpoint: 640,
			  settings: {
			    slidesToShow: 1
			  }
			}
		]
	};
	var _this = this, popInner, currBx, nextBx, contOutBx,  moveMotion, moving, bxSlider;
	this.popBx = '#news_popup';
	this.slider = $(".news-slider")

	this.setContList = function(o){
		if($(".news-slider").length < 1) return;
		_this.slider.checkImgsLoad(function(){
			//_this.slider.find('.item').addClass('active');
			//bxSlider = _this.slider.bxSlider(params);
			bxSlider = _this.slider.slick(params);
			
			
			
			$(window).resize(function(){
				var filter = getParameter('filter');
				if(filter != undefined){
					_this.setFilter(filter);
				}
			}).resize();
		});
	};

	this.setFilter = function(o){
		//$('.newsFilter li.'+o).siblings('.active').removeClass('active').end().addClass('active');
		//_this.slider.find('.item').addClass('active');
		/*if(o != 'all'){
			var length = _this.slider.find('.item').length;
			setSlider = false;
			_this.slider.find('.item').each(function(i, v){
				if(!$(this).hasClass(o)){
					$(this).removeClass('active');
				}
			});
			
			_this.slider.find('.item').each(function(i, v){
				if(!$(this).hasClass(o)){
					$(this).removeClass('active');
				}
			});
		}*/
		$('.newsFilter li.'+o).siblings('.active').removeClass('active').end().addClass('active');
		if($(".news-slider").length < 1) return;
		_this.slider.slick('slickUnfilter');
		if(o != 'all'){
			_this.slider.slick('slickFilter','.'+o);
		}

		_this.listCheck();
		//bxSlider.reloadSlider(params);

	};
	
	this.listCheck = function(o){
		var item = _this.slider.find('.item').length;
		if(item <= 0){
			$('.none_list').css('display', 'block');
			$('.news-slider').css('display', 'none');
		}else{
			$('.none_list').css('display', 'none');
			$('.news-slider').css('display', 'block');
		}
	};

	this.setContList();

}
var news;

var NewsPopFn = function(params){
	params = params || {};
	var _this = this, popInner, currBx, nextBx, contOutBx,  moveMotion, moving;
	this.popBx = '#news_popup';

	this.setCont = function(o){
		o = o || {};
		if(o.url === undefined) throw new Error('URL 주소가 필요합니다.');
		var t = _this.popBx.find('.content:not(".on")').eq(0);
		$.ajax({
			url : o.url,
			type : params.type || 'GET',
            data : o.data || {},
			dataType : 'html',
			success : function(data){
				t.html(data).siblings('.on').removeClass('on').end().addClass('on');
				t.checkImgsLoad(function(){
					var vH = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
					var h = t.outerHeight();
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
                            //console.log($(document).height())
						}});
					}

					//console.log(vH, ch);

					
					if(t.find('.iscContent').length > 0){
						iscrollNewsFn.init(t);
					}
					/*if(t.find('.iscContent').length > 0 && vH < ch){
						//console.log(vH-t.find('header').outerHeight()-t.find('footer').outerHeight());
						t.find('.iscContent').height(vH-t.find('header').outerHeight()-t.find('footer').outerHeight());
						setTimeout(function(){
							_this.popBx.find('.box-content>.content:not(".on")').html('');
			    			_this.isc = new IScroll(t.find('.iscContent').selector+' > .inner', {
				    			scrollbars: 'custom',
				    			mouseWheel: true,
				    			disableMouse: true
				    		});
				    		document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
			            },500);

					}*/
				});
			},
			error : function(a,b,c){
				alert('error : ' + c);
                moving = false;
			}
		})
	};

	this.showPop = function(o){
		params.url = '/event/'+params.data.type+'View.af?num='+params.data.num;
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

function postcode(param) {
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 도로명 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var fullRoadAddr = data.roadAddress; // 도로명 주소 변수
            var extraRoadAddr = ''; // 도로명 조합형 주소 변수

            // 법정동명이 있을 경우 추가한다. (법정리는 제외)
            // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
            if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                extraRoadAddr += data.bname;
            }
            // 건물명이 있고, 공동주택일 경우 추가한다.
            if(data.buildingName !== '' && data.apartment === 'Y'){
               extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
            }
            // 도로명, 지번 조합형 주소가 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
            if(extraRoadAddr !== ''){
                extraRoadAddr = ' (' + extraRoadAddr + ')';
            }
            // 도로명, 지번 주소의 유무에 따라 해당 조합형 주소를 추가한다.
            if(fullRoadAddr !== ''){
                fullRoadAddr += extraRoadAddr;
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            //document.getElementById('sample4_postcode').value = data.zonecode; //5자리 새우편번호 사용
            document.getElementById(''+param+'').value = fullRoadAddr;
            document.getElementById(''+param+'2').value = '';
            document.getElementById(''+param+'').focus();
            //document.getElementById('address2').value = data.jibunAddress;
        },
    
        onclose: function(state) {
            //state는 우편번호 찾기 화면이 어떻게 닫혔는지에 대한 상태 변수 이며, 상세 설명은 아래 목록에서 확인하실 수 있습니다.
            if(state === 'FORCE_CLOSE'){
                //사용자가 브라우저 닫기 버튼을 통해 팝업창을 닫았을 경우, 실행될 코드를 작성하는 부분입니다.

            } else if(state === 'COMPLETE_CLOSE'){
                //사용자가 검색결과를 선택하여 팝업창이 닫혔을 경우, 실행될 코드를 작성하는 부분입니다.
                //oncomplete 콜백 함수가 실행 완료된 후에 실행됩니다.
            }
        }	        
    }).open();
    
}

function setCookie(cookieName, value, exdays){
    var exdate = new Date();
    exdate.setDate(exdate.getDate() + exdays);
    var cookieValue = escape(value) + ((exdays==null) ? "" : "; expires=" + exdate.toGMTString());
    document.cookie = cookieName + "=" + cookieValue;
}
 
function deleteCookie(cookieName){
    var expireDate = new Date();
    expireDate.setDate(expireDate.getDate() - 1);
    document.cookie = cookieName + "= " + "; expires=" + expireDate.toGMTString();
}
 
function getCookie(cookieName) {
    cookieName = cookieName + '=';
    var cookieData = document.cookie;
    var start = cookieData.indexOf(cookieName);
    var cookieValue = '';
    if(start != -1){
        start += cookieName.length;
        var end = cookieData.indexOf(';', start);
        if(end == -1)end = cookieData.length;
        cookieValue = cookieData.substring(start, end);
    }
    return unescape(cookieValue);
}