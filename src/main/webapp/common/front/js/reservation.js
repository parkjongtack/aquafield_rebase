/*예약결제 관련 팝업창*/
var cntVal = 0;
var ReservationPopFn = function(params){
	params = params || {};
	var _this = this, popInner, currBx, nextBx, contOutBx,  moveMotion, moving;
    this.resData = { 'pointCode':'', 'pointNm':'', 'date':'','type':'입장상품','item':{}, 'payment':0, 'dpayment':0 };
	this.popBx = '#pop_reservation';

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
					var boxH = _this.popBx.outerHeight();
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
                        TweenMax.to(_this.popBx, 0.6, {'top' : ( h > vH ? scl : (vH-h)/2+scl-30 ), ease: Power3.easeInOut});
						TweenMax.to(contOutBx,0.6,{'height' : h, ease: Power3.easeInOut, onComplete : function(){
							o.callFn && o.callFn();
                            contOutBx.css('height','');
                            moving = false;
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
	        url : '/reserve/popup.af',
            data : params.data || {},
            resize : true,
            bgClose : false,
            mobileUI : true,
			onStart : function(bx, showFn){
				_this.popBx = $(_this.popBx);
                popInner = _this.popBx.find('> .inner');
				contOutBx = _this.popBx.find('#bx_multi_content');
				_this.setCont({callFn : showFn, url : params.url || '/reserve/step01.af'});
			}
	    });
	};

	this.addCont = function(o){
        if(!$(o.target).parent().hasClass("active")){
    		o  = o || {};
    		if(!$(o.target).parent().hasClass("active")&&o.target!=null){
                if(confirm('예약유형을 변경하시면 이전 예약상품이 초기화 됩니다. 계속하시겠습니까?')) {
                    _this.resData = { 'pointCode':'','pointNm':'','date':'','type':'','item':{},'payment':0, 'dpayment':0 };
                    _this.resData.type = o.type;
                	//초기데이터는 메인페이지 세션으로 초기 셋팅
            		_this.resData.pointCode = o.pointCode;
                    _this.resData.pointNm = o.pointNm;
                    $("#bx_multi_tab ul li").removeClass("active");
                    $(o.target).parent().addClass("active");
                }else{
                    return false;
                }
    		}
            if(moving) return;
            moving = true;
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
    		if(o.type == '입장상품') o.url = o.url+'?prod=10000000';
    		if(o.type == '패키지') o.url = o.url+'?prod=20000000';
    		if(o.type == '이벤트') o.url = o.url+'?prod=30000000';
    		_this.setCont({url : o.url, callFn : move, data : o.data});
        }
	};

	this.addSubSect = function(o){
		o = o || {};
		if(!$(o.target).parent().hasClass("active")&&o.target!=null){
			$(".res_info_list li").removeClass("active");
			$(o.target).parent().addClass("active");
		}
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
                _this.popBx.find('.btn_close').on('click',function(){_this.popBx.removeClass('show_right_sec');});
            },
            error : function(a,b,c){
                alert('error : ' + c);
            }
        });
    };

    this.setResData = function(k,d){
    	//지점 선택 눌렀을때(오른쪽 화면)
    	if(k == 'point'){
    		_this.resData.pointCode = o.pointCode;
            _this.resData.pointNm = o.pointNm;
    	}else if(k == 'date') {
    		$.each($(d),function(i,v){
    			_this.resData[k] = $(this).val();
    		});
    	}else{
    		$.each($(d),function(i,v){
	    		var data =  new Object();
	    		data.code = $(this).data('code');
	    		data.title = $(this).data('title');
	    		data.uid = $(this).data('uid');
	    		data.age = $(this).data('age');
	    		data.count = new Array();
	    		data.price = 0;
	    		var itemPrice = parseInt($(this).find("input.price").val());
	    		//itemPrice = itemPrice + (itemPrice*0.1);

	    		$(this).find("input.iptNum").each(function(index, el) {
			    	data.count.push(parseInt($(this).val()));
			    	data.price += $(this).val() * itemPrice;
			    });

		 		var countVal = 0; //이벤트 가격이 0원일때 사용하는 값
		 		$.each(data.count, function(i, v) { countVal += v; });
	    		
	    		if(data.price > 0) {
                    _this.resData[k][$(this).data('code')] = data;
                }else{               	
                	if(countVal > 0){
                        _this.resData[k][$(this).data('code')] = data;                		
                	}else{
                        delete _this.resData[k][$(this).data('code')];                		
                	}
                }

			});
    	}

    	_this.setResList();
    	_this.popBx.removeClass('show_right_sec');
        _this.popBx.find('.res_info_list li').removeClass('active');
    }

    this.getResData = function(k,d){

    	if(k == 'date') {
    		$.each($(d),function(i,v){
    			$(this).val(_this.resData[k]);
    		});
    	}else{
    		$.each($(d),function(i,v){
    			var code = $(this).data('code');
	    		$(this).find("input.iptNum").each(function(index, el) {
	    			if(_this.resData[k][code] != undefined){
	    				$(this).val(_this.resData[k][code]['count'][index]);
	    			}
			    });
			});
    	}
    }

    this.setResList = function(){
    	var plantCount = 0,
			plantPerson = 0,
			plantAdult = 0,
			plantChild = 0,
			rantalCount = 0,
			rtProdCnt = 0,
			etProdCnt = 0,
			selProdUid ="",
			rtProdUid ="",
			etProdUid ="",
			eventCount = "미참여";
			_this.resData.payment = 0;
            _this.resData.dpayment = 0;

        if(_this.resData.pointCode != ''){
        	_this.popBx.find(("#selPointList")).val(_this.resData.pointCode).prop("selected",true);
        	//_this.popBx.find(".res_info_list .point span").text(_this.resData.pointNm);
        }
        
    	if(_this.resData.date != '') {
            var d = _this.resData.date.replace(/(\d\d\d\d)(\d\d)(\d\d)/g, '$1.$2.$3');
            _this.popBx.find(".res_info_list .date span").text(d);
        }else{
        	_this.popBx.find(".res_info_list .date span").text("");
        }
    	//console.log(JSON.stringify(_this.resData['item']));

        $('.item_list table').html("");
    	$.each(_this.resData['item'], function(i, v) {
    		 //$('#resRule').remove();
    		 if(v.code.indexOf('spa')!=-1 || v.code.indexOf('water')!=-1 || v.code.indexOf('complex')!=-1){
    		 	if(v.price > 0) {
    		 		plantCount += 1;
    		 		var count = 0;
    		 		$.each(v.count, function(i, v) {
    		 			plantPerson += v;
    		 			count += v;
    		 		});
    		 		$('.item_list table').append('<tr><td>'+v.title+v.age+'</td><td>X '+count+'명</td><td>= '+priceCommas(v.price)+'원</td><td><span class="del" onclick="reservationPop.delResItem(\''+v.code+'\');">x</span></td></tr>')
    		 		_this.resData.payment += v.price;
		 			if(v.age.indexOf('대인')!=-1){
			 			plantAdult += count;
	    		 		selProdUid += v.uid + ",";
		 			}
		 			if(v.age.indexOf('소인')!=-1){
			 			plantChild += count;
		 			}
    		 	}
    		 }
    		 if(v.code.indexOf('rantal')!=-1){
    		 	if(v.price > 0) {
    		 		rantalCount += 1;
    		 		var count = 0;
    		 		$.each(v.count, function(i, v) { count += v; });
    		 		$('.item_list table').append('<tr><td>'+v.title+'</td><td>X '+count+'명</td><td>= '+priceCommas(v.price)+'원</td><td><span class="del" onclick="reservationPop.delResItem(\''+v.code+'\');">x</span></td></tr>')
    		 		_this.resData.payment += v.price;
    		 		rtProdUid +=v.uid + ",";
    		 		rtProdCnt += count;
    		 	}
    		 }
    		 if(v.code.indexOf('event')!=-1){
    		 	//if(v.price > 0) {
    		 		eventCount = '참여';
    		 		var count = 0;
    		 		$.each(v.count, function(i, v) { count += v; });
    		 		$('.item_list table').append('<tr><td>'+v.title+'</td><td>X '+count+'명</td><td>= '+priceCommas(v.price)+'원</td><td><span class="del" onclick="reservationPop.delResItem(\''+v.code+'\');">x</span></td></tr>')
    		 		_this.resData.payment += v.price;
    		 		etProdUid +=v.uid +",";
    		 		etProdCnt += count;
    		 	//}
    		 }
    		 //_this.resData.dpayment = _this.resData.payment-((_this.resData.payment/1.1)*0.1);
    		 _this.resData.dpayment = _this.resData.payment;
    	});
		/*$(".plantCount").text(plantCount);*/
    	$(".plantPerson").text(plantPerson);
    	$(".rantalCount").text(rantalCount);
    	$(".eventCount").text(eventCount);
    	$(".pointNm").text(_this.resData.pointNm);
        $(".typeTitle").text(_this.resData.type);
        $(".totalPersonnel span").text(plantPerson);
        $(".nomalPrice span").text(priceCommas(_this.resData.payment));
      //$(".payment span").text(priceCommas(_this.resData.payment-((_this.resData.payment/1.1)*0.1)));
        //$(".dpayment span").text(priceCommas(_this.resData.payment-((_this.resData.payment/1.1)*0.1)));
    	$(".payment span").text(priceCommas(_this.resData.payment) );
        $(".dpayment span").text(priceCommas(_this.resData.payment) );

    	cntVal = _this.resData;
        if($("input[name='rsData']").length > 0){
        	$("input[name='rsData']").val(JSON.stringify(_this.resData));
        }
    }

    this.delResItem = function(c){
        delete _this.resData.item[c];
        _this.setResList();
    }

    this.checkResStpe1 = function () {
        _this.popBx = $(_this.popBx);
        if(_this.resCheck() == true && !_this.popBx.hasClass('show_right_sec')){
            _this.addCont({url: "/reserve/step02.af", data :{ prod : $("#bx_multi_content > .on").find("input[name='prodNum']").val()}});
        }
    }

    this.resCheck = function (o) {
        //console.log(_this.resData);
        var result = true;
        if(_this.resData.date == '') {
            alert('방문일을 선택해주세요.');
            _this.addSubSect({url : '/reserve/date.af',target:_this.popBx.find('.res_info_list .date a'), data :{ prodNum : $("#bx_multi_content > .on").find("input[name='prodNum']").val(), pointCode : _this.resData.pointCode} });
            result = false;
            return result;
        }else if(_this.resData.payment <= 0 && o != 0) {
            alert('방문인원을 선택해주세요.');
            _this.addSubSect({url : '/reserve/plant.af',target:_this.popBx.find('.res_info_list .plant a'), data :{prodNum : $("#bx_multi_content > .on").find("input[name='prodNum']").val(),seldate : $(".date > a > .info > span").html(), pointCode : _this.resData.pointCode} });
            result = false;
            return result;
        }else{
            return result;
        }
    }
    
    this.setInitialPoint = function(o){
    	//초기데이터는 메인페이지 세션으로 초기 셋팅
		_this.resData.pointCode = o.pointCode;
        _this.resData.pointNm = o.pointNm;
    }
	this.showPop();
}
var reservationPop;

var reservationFn = {
		
	date : function(me){
        if(reservationPop.resData.payment > 0) {
            if(confirm('방문일을 변경하시면 이전 예약상품이 초기화 됩니다. 계속하시겠습니까?')) {
                reservationPop.resData = { 'pointCode':reservationPop.resData.pointCode, 'pointNm':reservationPop.resData.pointNm, 'date':reservationPop.resData.date,'type':reservationPop.resData.type,'item':{},'payment':0, 'dpayment':0 };
                reservationPop.setResList();
            }else{
                return false;
            }
        }
        reservationPop.addSubSect({url : '/reserve/date.af',target:me, data :{ prodNum : $("#bx_multi_content > .on").find("input[name='prodNum']").val(), pointCode : reservationPop.resData.pointCode} });
    },
    plant : function(me){
        if(reservationPop.resCheck(0) == true) {
           reservationPop.addSubSect({url : '/reserve/plant.af',target:me, data :{prodNum : $("#bx_multi_content > .on").find("input[name='prodNum']").val(),seldate : $(".date > a > .info > span").html(), pointCode : reservationPop.resData.pointCode} }) ;
        }
    },
    package : function(me){
        if(reservationPop.resCheck(0) == true) {
           reservationPop.addSubSect({url : '/reserve/packagePlant.af',target:me, data :{prodNum : $("#bx_multi_content > .on").find("input[name='prodNum']").val(),seldate : $(".date > a > .info > span").html(), pointCode : reservationPop.resData.pointCode} }) ;
        }
    },
    rantal : function(me){
    	var spaVal = 0;
    	var wpVal = 0;
    	var cpVal = 0;    	
    	$.each(cntVal['item'], function(i, v) {
	   		 if(v.code.indexOf('spa')!=-1){
	   			$.each(v.count, function(i, v) {spaVal += v;});
	 		 }else if(v.code.indexOf('weter')!=-1){
	 			$.each(v.count, function(i, v) {wpVal += v;});
     		 }else if(v.code.indexOf('complex')!=-1){
	 			$.each(v.count, function(i, v) {cpVal += v;});
     		 }
    	});   	
        if(reservationPop.resCheck() == true) {
          reservationPop.addSubSect({url : '/reserve/rantal.af',target:me , data :{prodNum : '30000000' ,seldate : $(".date > a > .info > span").html(), spaVal : spaVal, wpVal : wpVal, cpVal : cpVal} });
        }
    },
    event : function(me){
    	//console.log("cntValOri :::: ",cntVal);
    	var spaVal = 0;
    	var wpVal = 0;
    	var cpVal = 0;    	
    	$.each(cntVal['item'], function(i, v) {
	   		 if(v.code.indexOf('spa')!=-1){
	   			$.each(v.count, function(i, v) {spaVal += v;});
	 		 }else if(v.code.indexOf('weter')!=-1){
	 			$.each(v.count, function(i, v) {wpVal += v;});
     		 }else if(v.code.indexOf('complex')!=-1){
	 			$.each(v.count, function(i, v) {cpVal += v;});
     		 }
    	});
    	//console.log("spaVal :::: ",spaVal);
    	//console.log("wpVal :::: ",wpVal);
        if(reservationPop.resCheck() == true) {
            reservationPop.addSubSect({url : '/reserve/event.af',target:me, data :{prodNum : '40000000' ,seldate : $(".date > a > .info > span").html(), spaVal : spaVal, wpVal : wpVal, cpVal : cpVal} });
        }
    },
    point : function(pointCode, pointNm){
        //if(reservationPop.resData.date != '') {
//    	if(pointCode == 'POINT03'){
//    		alert('고양점은 서비스 준비중입니다.');
//            if(reservationPop.resData.type == '입장상품'){
//            	$("#selPointList_enter option:eq(0)").prop("selected", true);
//            }else{
//            	$("#selPointList_package option:eq(0)").prop("selected", true);
//            }
//    	}else{
    		if(confirm('지점을 변경하시면 이전 예약상품이 초기화 됩니다. 계속하시겠습니까?')) {
                reservationPop.resData = { 'pointCode':pointCode, 'pointNm':pointNm, 'date':'','type':reservationPop.resData.type,'item':{},'payment':0, 'dpayment':0 };
            }
//    	}
        //}else{
        //    reservationPop.resData = { 'pointCode':pointCode, 'pointNm':pointNm, 'date':reservationPop.resData.date,'type':reservationPop.resData.type,'item':{},'payment':0, 'dpayment':0 };
        //}
        reservationPop.setResList();
        if(reservationPop.popBx.hasClass('show_right_sec')){
        	reservationPop.popBx.removeClass('show_right_sec');
        }
    }
    
}
