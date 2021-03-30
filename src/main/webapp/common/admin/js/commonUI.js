
/*ajax 팝업 띄우기*/
	function ajaxShowPopCont(o){
		var t = o.target ? $(o.target) : $("#pop_bx_common");
		o.data = o.data || {};
		$.ajax({
			url : o.url,
			type : o.type || "get",
			dataType : "html",
			data : o.data,
			success : function(data){
				if(!o.append)t.html('');
				t.append(data);
				var popup = o.pop ? $(o.pop) : t.find(">*").eq(0);
				popFn.show(popup, {motion : o.motion || true});
			},
			error : function(a,b,c){
				alert(c);
			}
		})
	}
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

	var winH = $(window).height(),
		htmlH = $("html").height();

	if(htmlH < winH){
		$("#footer").addClass("fixed");
	}else{
		$("#footer").removeClass("fixed");
	}
	
	$('.customized-select').customSelect();
	$(".datepicker").datepicker({
		showOn: "both",
		buttonImage: "/admaf/images/common/btn_calen_off.png",
		buttonImageOnly: true,
		buttonText: "Select date",
		dateFormat: "yy.mm.dd",
	});

	$(window).scroll(function(e) {
		var winT = $(window).scrollTop();
		var pathT = $("#path.fixed").offset().top + $("#path.fixed").height();

		if(winT >= pathT) {
			$("#path.fixed").addClass("on");
			$("#scrollTop").addClass("on");
		}else{
			$("#path.fixed").removeClass("on");
			$("#scrollTop").removeClass("on");
		}
	});
	$(window).trigger('scroll');

	$("#scrollTop").click(function(e) {
		$("html, body").stop().animate({scrollTop:0}, '500', 'swing', function() { 

		});
	});

	//form 세팅리셋
	$(".setReset").click(function(e) {
		e.preventDefault();
		//입력 값 초기화
		$(this).closest('form').each(function() {  
            this.reset();  
        });
		
		//성수기 지정 selectBox 초기화
		$('.selectSeason').find('option:first').attr('selected', 'selected');  

		//성수기,준성수기 초기화
		$(".calendar td").each(function(index, el) {
			var wp_normal_season = [],
			complex_normal_season = [];
			
			//워터파크
			$(this).find(".item2 .season").each(function(i, el) {
				$(this).removeClass('on');
				$(this).html('');
			});
			$(".item2").find("input[name='wpItemSeason']").each(function(i, el) {
				$(this).val("NORMAL");
			});

			//복합권
			$(this).find(".item3 .season").each(function(i, el) {
				$(this).removeClass('on');
				$(this).html('');
			});
			$(".item3").find("input[name='complexItemSeason']").each(function(i, el) {
				$(this).val("NORMAL");
			});
		});
	});



/* 상품 가격테이블 세팅 */
	
	if($('#calendar').length > 0) {
		var cal = $( '#calendar' ).calendario();
		
		if($("#year").val() != ""){
	        var monthVal = parseInt($("#month").val());
	        var yearVal = parseInt($("#year").val());
			$month = $( '#custom-month' ).html(monthVal),
			$year = $( '#custom-year' ).html(yearVal);
			cal.goto( monthVal-1, yearVal );
		}else{	
			$month = $( '#custom-month' ).html( cal.getMonthName() ),
			$year = $( '#custom-year' ).html( cal.getYear() );
		}
	}
	

	//다음달
	$( '.nextMonth' ).on( 'click', function(e) {
		e.preventDefault();
		cal.gotoNextMonth( updateMonthYear );
	} );
	
	//이전달
	$( '.prevMonth' ).on( 'click', function(e) {
		e.preventDefault();
		cal.gotoPreviousMonth( updateMonthYear );
	} );

	//다음해
	$( '.nextYear' ).on( 'click', function(e) {
		e.preventDefault();
		cal.gotoNextYear( updateMonthYear );
	} );
	
	//이전해
	$( '.prevYear' ).on( 'click', function(e) {
		e.preventDefault();
		cal.gotoPreviousYear( updateMonthYear );
	} );

	function updateMonthYear() {				
		$month.html( cal.getMonthName() );
		$year.html( cal.getYear() );
	}

	//가격테이블 null값 체크
	function nullCheck(obj) {
		var valid = true;
		$(obj).each(function (i, el) {
			if($(el).val() == null || $(el).val() == '') {
				return valid = false;
			}
		});
		return valid;
	}

	//상품상태 OPEN
	$("#status .open").click(function(e) {
		if(!nullCheck('#calendarSetting input')) {
			alert("가격테이블을 모두 입력해주세요.");
		}else{
			if(confirm("가격테이블을 오픈합니다. 한번 공개 된 가격정보는 수정할 수 없습니다. 계속 진행하시겠습니까?")) {
				$(this).remove();
				$("#status").find(".status").text("OPEN");
				alert("오픈하였습니다.");
				location.href = "/admin/product/product_list.jsp"
			}
		}
	});

	//가격세팅적용
	$(".setPrice").click(function(e) {			
		e.preventDefault();
		var gubun = $(this).data("value");
		if(gubun == "wp"){
			setSpa();
			setComplexTicket();
		}
		setPrice(gubun);
	});

	//스파 주중주말 세팅
	function setSpa() {		
		var spaWeekdayPrice = [],	//평일
			spaWeekendPrice = [],	//주말
			spaRestPrice = [];	//공휴일

		$(".spaWeekday input").each(function(index, el) {
			spaWeekdayPrice.push($(this).val());
		});
		$(".spaWeekend input").each(function(index, el) {
			spaWeekendPrice.push($(this).val());
		});
		$(".spaRestday input").each(function(index, el) {
			spaRestPrice.push($(this).val());
		});
		
		$(".calendar td").each(function(index, el) {
			var checked = $(el).find('.date p input[type=checkbox]').prop('checked');
			if($(this).find(".date p").text().indexOf('토') != -1 || $(this).find(".date p").text().indexOf('일') != -1) {
				$(this).find(".item1 > ul > li > input").each(function(i, el) {
					//console.log($(el).attr('name'))
					//console.log(checked);
					if(checked == true) {
						$(this).val(spaRestPrice[i]);
					}else{
						$(this).val(spaWeekendPrice[i]);
					}
				});
			}else{
				$(this).find(".item1 > ul > li > input").each(function(i, el) {
					if(checked == true) {
						$(this).val(spaRestPrice[i]);
					}else{
						$(this).val(spaWeekdayPrice[i]);
					}
				});
			}
		});
	}

	//워터파크 평수기&준성수기&성수기 세팅
	function setPrice(gubun) {
	
		var normal_season = [],
			halfpeak_season = [],
			peak_season = [],
			season_date = [];
		
		//gubun - wp : 입장상품(워터파크) | pkg : 패키지 | event : 이벤트  

		if($("." + gubun + "Season .selectSeason ").val() != 'NORMAL') {
			if($("." + gubun + "Season #startDay_" + gubun).datepicker('getDate') == null){
				alert("성수기 시작기간를 설정해주세요!");
				$("." + gubun + "Season #startDay_" + gubun).focus();
				return false;
			}else if($("." + gubun + "Season #endDay_" + gubun).datepicker('getDate') == null){
				alert("성수기 종료기간를 설정해주세요!");
				$("." + gubun + "Season #endDay_" + gubun).focus();
				return false;
			}
			/*
			season_date.push($.datepicker.formatDate('yymmdd', $("." + gubun + "Season #startDay_" + gubun).datepicker('getDate')));	
			season_date.push($.datepicker.formatDate('yymmdd', $("." + gubun + "Season #endDay_" + gubun).datepicker('getDate')));

			if(season_date[0] > season_date[1]){
				alert("성수기 시작기간이 종료기간보다 작아야 합니다!");
				$("." + gubun + "Season #startDay_" + gubun).focus();
				return false;
			}
			*/
			
		}
	
		$("." + gubun + "_normal_season input").each(function(index, el) {
			normal_season.push($(this).val());
		});
		$("." + gubun + "_halfpeak_season input").each(function(index, el) {
			halfpeak_season.push($(this).val());
		});
		$("." + gubun + "_peak_season input").each(function(index, el) {
			peak_season.push($(this).val());
		});

		var spaRestPrice = [];

		$(".spaRestday input").each(function(index, el) {
			spaRestPrice.push($(this).val());
		});


		$(".calendar td").each(function(index, el) {
		
			
			if(!$(this).find('.season' + "." + gubun).hasClass('on')){
				$(this).find(".item2 > ul > li > input").each(function(i, el) {
					$(this).val(normal_season[i]);
				});
			}
			
			//준성수기/성수기 셋팅 초기화
//			$(this).find(".item2 .season").each(function(i, el) {
//				$(this).removeClass('on');
//				$(this).html('');
//				
//			});
			
			if($("." + gubun + "Season .selectSeason").val() == 'NONPEAK'){
				var thisDay = $(this).find(".date").data('date');
				if(thisDay >= season_date[0] && thisDay <= season_date[1]){
					$(this).find(".item2 > ul > li > input").each(function(i, el) {
						$(this).val(halfpeak_season[i]);
					});
					$(this).find(".item2 .season").each(function(i, el) {
						$(this).addClass('on');
						$(this).html('');					
						$(this).html('<span class="season_txt halfpeak">준성수기</span><span class="season_close" data-value="item2">X</span>');
						$(this).next().next().val("NONPEAK");
					});							
				}	
			}else if($("." + gubun + "Season .selectSeason").val() == 'PEAK'){
				var thisDay = $(this).find(".date").data('date');
				if(thisDay >= season_date[0] && thisDay <= season_date[1]){
					$(this).find(".item2 > ul > li > input").each(function(i, el) {
						$(this).val(peak_season[i]);
					});
					$(this).find(".item2 .season").each(function(i, el) {
						$(this).addClass('on');
						$(this).html('');
						$(this).html('<span class="season_txt peak">성수기</span><span class="season_close" data-value="item2">X</span>');
						$(this).next().next().val("PEAK");
					});				
				}		
			}else if($("." + gubun + "Season .selectSeason").val() == 'NORMAL'){
				$(this).find(".item2 > ul > li > input").each(function(i, el) {
					$(this).val(normal_season[i]);
				});
				$(this).find(".item2 .season").each(function(i, el) {
					$(this).removeClass('on');
					$(this).html('');
					
				});
				$(".item2").find("input[name='"+ gubun + "ItemSeason']").each(function(i, el) {
					$(this).val("NORMAL");
				});
			}
			
			var checked = $(el).find('.date p input[type=checkbox]').prop('checked');
			$(this).find(".item2 > ul > li > input").each(function(i, el) {
				if(checked == true) {
					$(this).val(spaRestPrice[i]);
				}
			});				
			
		});
	}

	/* 20170620 oktoamto start - KBR */
	//복합권
	function setComplexTicket() {
		var normal_season = [],
		halfpeak_season = [],
		peak_season = [],
		season_date = [];
		
		if($(".complexSeason .selectSeason ").val() != 'NORMAL') {
			if($(".complexSeason #startDay_complex").datepicker('getDate') == null){
				alert("성수기 시작기간를 설정해주세요!");
				$(".complexSeason #startDay_complex").focus();
				return false;
			}else if($(".complexSeason #endDay_complex").datepicker('getDate') == null){
				alert("성수기 종료기간를 설정해주세요!");
				$(".complexSeason #endDay_complex").focus();
				return false;
			}
			
			/*
			season_date.push($.datepicker.formatDate('yymmdd', $(".complexSeason #startDay_complex").datepicker('getDate')));	
			season_date.push($.datepicker.formatDate('yymmdd', $(".complexSeason #endDay_complex").datepicker('getDate')));

			if(season_date[0] > season_date[1]){
				alert("성수기 시작기간이 종료기간보다 작아야 합니다!");
				$(".complexSeason #startDay_complex").focus();
				return false;
			}
			*/
			
		}
	
		
		$(".complex_normal_season input").each(function(index, el) {
			normal_season.push($(this).val());
		});
		$(".complex_halfpeak_season input").each(function(index, el) {
			halfpeak_season.push($(this).val());
		});
		$(".complex_peak_season input").each(function(index, el) {
			peak_season.push($(this).val());
		});

		
		$(".calendar td").each(function(index, el) {
			if(!$(this).find('.season.complex').hasClass('on')){
				$(this).find(".item3 > ul > li > input").each(function(i, el) {
					$(this).val(normal_season[i]);
				});
			}
			
			//준성수기/성수기 셋팅 초기화
//			$(this).find(".item3 .season").each(function(i, el) {
//				$(this).removeClass('on');
//				$(this).html('');
//				
//			});
			
			if($(".complexSeason .selectSeason").val() == 'NONPEAK'){
				var thisDay = $(this).find(".date").data('date');
				if(thisDay >= season_date[0] && thisDay <= season_date[1]){
					$(this).find(".item3 > ul > li > input").each(function(i, el) {
						$(this).val(halfpeak_season[i]);
					});
					$(this).find(".item3 .season").each(function(i, el) {
						$(this).addClass('on');
						$(this).html('');					
						$(this).html('<span class="season_txt halfpeak">준성수기</span><span class="season_close" data-value="item3">X</span>');
						$(this).next().next().val("NONPEAK");
					});							
				}					
			}else if($(".complexSeason .selectSeason").val() == 'PEAK'){
				var thisDay = $(this).find(".date").data('date');
				if(thisDay >= season_date[0] && thisDay <= season_date[1]){
					$(this).find(".item3 > ul > li > input").each(function(i, el) {
						$(this).val(peak_season[i]);
					});
					$(this).find(".item3 .season").each(function(i, el) {
						$(this).addClass('on');
						$(this).html('');
						$(this).html('<span class="season_txt peak">성수기</span><span class="season_close" data-value="item3">X</span>');
						$(this).next().next().val("PEAK");
					});				
				}						
			}else if($(".complexSeason .selectSeason").val() == 'NORMAL'){
				$(this).find(".item3 > ul > li > input").each(function(i, el) {
					$(this).val(normal_season[i]);
				});
				$(this).find(".item3 .season").each(function(i, el) {
					$(this).removeClass('on');
					$(this).html('');
				});
				$(".item3").find("input[name='complexItemSeason']").each(function(i, el) {
					$(this).val("NORMAL");
				});								
			}
		});
	}
	/* 20170526 oktoamto end*/
	
	//성수기&준성수기 취소버튼
	$('html').on('click', '.season_close', function(e) {
		var normal_season = [];
		var gubun = $(this).data("value");
		
		if(gubun == "item2"){	//워터파크
			$(".wp_normal_season input").each(function(index, el) {
				normal_season.push('NORMAL');
			});	
		}else{
			$(".complex_normal_season input").each(function(index, el) {
				normal_season.push('NORMAL');
			});
		}

		/*$(this).data("value") 값은 성수기&준성수기 취소버튼 구분값용
		<span class="season_close" data-value="item3">X</span>
		item2 : 워터파크, item3 : 복합권*/
		$(this).closest('td').find('.' + $(this).data("value") +' > ul > input').eq(1).each(function(i, el) {
			$(this).val(normal_season[i]);
		});
		$(this).parent().parent().find("input:visible").val("");
		$(this).parent().removeClass('on').html('');
		
	});
	
	$("input[type=number]").keyup(function(){
	     $(this).val($(this).val().replace(/[^0-9]/g,""));
	}); 
});