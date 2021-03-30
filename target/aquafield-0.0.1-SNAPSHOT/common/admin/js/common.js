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

function tab(c,d){

	var cate = c,
		first = d,
		$tab = $(".tab_position ul li"),
		$tabcont = $(".tab_section")

	$tab.eq(first-1).addClass("on")
	$tabcont.css("display","none");
	$tabcont.eq(first-1).css("display","block")

	$tab.find("a").click(function(){
		$index = $(this).parent("li").index();
		
		$tabcont.css("display","none")
		$(this).parent("li").addClass("on").siblings("li").removeClass("on");
		$tabcont.eq($index).css("display","block")
		if (cate == "img"){
			$tab.each(function(){
				$(this).find("img").attr("src",$(this).find("img").attr("src").split("_on").join("_off"));
			});
			$(this).find("img").attr("src",$(this).find("img").attr("src").split("_off").join("_on"));
		}else{

		}
		return false;
	});
}//tab




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
		var img = this.find('img');
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
	}
});

/* common 팝업 */
var popFn = {
	show : function(t, params){
		var defaults = {
			onLoad : function(){},
			btnCloseClass : 'btn_close',
			contBx : '.inner_popups',
			motion : true
		};
		params = params || {};
	    for (var prop in defaults) {
	        if (prop in params && typeof params[prop] === 'object') {
	            for (var subProp in defaults[prop]) {
	                if (! (subProp in params[prop])) {
	                    params[prop][subProp] = defaults[prop][subProp];
	                }
	            }
	        } else if (! (prop in params)) {
	        	params[prop] = defaults[prop];}
	    };
		if($("body > #pop_bg_common").length === 0){
			$("body").append("<div id='pop_bg_common'></div>");
		}
		var bg = $("body > #pop_bg_common");
		$('html').addClass('of_hide2');
		bg.css('display','block');
		t.css('display','block');
		var posi = t.css('position');
		t.checkImgsLoad(function(){
			if(!params.motion){t.addClass('notrans')};
			bg.addClass('on');
			bg.on('click',function(){popFn.hide(t);});
			
			/*안쪽 팝업 갯수 세고 가로길이 세팅하기*/
			var inPop = t.find(params.contBx);
			var inPopLen = inPop.length;
			if(inPopLen > 0){
				popFn.calWidth({t : t, inPop : inPop, inPopLen : inPopLen})
			}
			popFn.resize({data : {tg : t, posi : posi}});
			t.addClass('on');
			t.find('.'+params.btnCloseClass).on('click',function(){popFn.hide(t);})
			if(params.onLoad){params.onLoad();}
		});
		$(window).on('resize', {tg : t, posi : posi}, popFn.resize);
	},
	calWidth : function(o){
		o.t.css({'width':''});
		var inPopW = 0;
		for(var i = 0 ; i < o.inPopLen ; i++){
			!o.inPop.eq(i).hasClass('off') ? inPopW += o.inPop.eq(i).width()+6 : '';
		}
		o.t.css({'width' : inPopW});
	},
	hide : function(t, change){
		var bg = $("#pop_bg_common");
		bg.off('click');
		if(!change)bg.removeClass('on');			
		t.removeClass('on');
		$('html').removeClass('of_hide2');
		setTimeout(function(){
			if(!change)bg.remove();			
			t.css('display','none');
			t.css({'max-height':'', "top":''});
		},300);
		$(window).off('resize', popFn.resize);
	},
	resize : function(e){
		var t = e.data.tg;
		var vH = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
		//t.css({'max-height':''});
		var bxH = t.outerHeight();
		var scl = e.data.posi =='fixed' ? 0 : $(window).scrollTop();
		t.css({"top":( bxH > vH ? scl : (vH-bxH)/2+scl )+"px"});
		//t.css({'max-height':vH, "top":( bxH > vH ? scl : (vH-bxH)/2+scl )+"px"});
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
})(jQuery);


/*라디오 버튼*/
function checkradio(me){
	var name = me.getAttribute("name");
    if($(me).prop("checked") == false){
    }else{
    	$("input[name='"+name+"']").closest("span").removeClass("on");
    	$(me).closest("span").addClass("on");
    }
    return $(me).prop("checked");
}

/*체크박스*/
function checkChkbox(me, p){
	var t;
	p ? t = $(me).closest(p) : t = $(me).closest("span");
    if($(me).prop("checked") == false){
    	t.removeClass("on");
    }else{
    	t.addClass("on");
    }
    return $(me).prop("checked");
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
			lb.on('click',function(){placeHFn.focus(id);});
			ip.on('focus',function(){_this.hideLabel(id);});
			ip.on('blur',function(){_this.showLabel(id);});
			if($("#"+id).val() !== "")_this.hideLabel(id);
		}
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