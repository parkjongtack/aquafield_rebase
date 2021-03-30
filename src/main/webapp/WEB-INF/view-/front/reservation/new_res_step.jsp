<%@ page contentType="text/html; charset=euc-kr" pageEncoding="euc-kr"%>
<%@ include file="../../common/taglibs.jsp" %>

<script type="text/javascript" src="/common/front/js/reservation2.js"></script>

<!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/3.1.2/rollups/aes.js"></script> -->
<script type="text/javascript" src="/common/front/js/crypto-3.1.12.js"></script>
<script type="text/javascript" src="/common/front/js/encrypt.js"></script>

<section id="res_step1">
	
	<div id="login_wrap" class="wrap_line">
		<div class="inner">
			<h2>온라인 예약</h2>
			<ul class="tab_menu res">
        		<!-- <li class="active tab_menu1" rel="tab1-1" style="width:94%;" > -->
        		<li class="active tab_menu1" rel="tab1-1">
        			<%-- <a href="javascript:void(0);" onclick='reservationPop2.addCont({url : "/reserve/newResStep.af", type: "입장상품", pointCode: "${POINTINFO.CODE_ID}", pointNm: "${POINTINFO.CODE_NM}", target:this})'> --%>
        			<a href="javascript:Tab(1);" style="width:100%;"><img src="../common/front/images/ico/tab_menu_res1.png" alt=""><span>입장상품</span></a>
        		</li>
        		<li class="tab_menu2" rel="tab2-1">
        			<%-- <a href="javascript:void(0);" onclick='reservationPop2.addCont({url : "/reserve/newPakage.af", type: "패키지", pointCode: "${POINTINFO.CODE_ID}", pointNm: "${POINTINFO.CODE_NM}", target:this})'> --%>
        			<a href="javascript:Tab(2);"><img src="../common/front/images/ico/tab_menu_res2.png" alt=""><span>패키지</span></a>
        		</li>
        	</ul>
			
			<div class="login_box box3" id="reserBox">
				<div class="form_type1-1" id="tab1-1" style="display:block;">
					<header>
						<h3 class="res_tit"><span>1단계</span><span class="tit2_t">예약정보를 선택해 주세요.</span></h3>
					</header>
					<form id="f" name="f" method="post">
						<input type="hidden" name="prodNum" value="${num}"/>
						<input type="hidden" name="pointCode" id="pointCode" value="${pointInfo.CODE_ID}"/>
						<input type="hidden" name="pointNm" id="pointNm" value=""/>
						<input type="hidden" id="rsData" name="rsData" value=""/>
						<input type="hidden" name="connectIP" id="connectIP" value=""/>
						<input type="hidden" name="device" id="device" value=""/>
						<input type="hidden" name="browser" id="browser" value=""/>
						<input type="hidden" name="rand" value="${rand}"/>
						
						<ul class="res_info_list">
				            <li class="point">
				            	<div class="inner">
				                  	<div class="tit"><img class="ico" src="../common/front/images/ico/ico_res_rantal.png"/>지점선택</div>
				                  	<c:choose>
					                	<c:when test="${num eq 10000000}">
					                 		<!-- <select class="select" id="selPointList_enter" name="selPointList_enter" onchange="reservationFn2.point(this.value,this.options[this.selectedIndex].text, this);"> -->
					                 		<select class="select" id="selPointList_enter" name="selPointList_enter" onchange="point(this.value,this.options[this.selectedIndex].text, this);" style="border: 1px solid #d6d6d6; border-radius: 26px; text-indent: 10px;">
							                    <c:forEach items="${codePoint_code }" var="code" >
													<option value="${code.CODE_ID }" <c:if test="${pointInfo.CODE_ID == code.CODE_ID }">
														selected="selected"</c:if> >${code.CODE_NM }</option>
												</c:forEach>
						                    </select>
					                	</c:when>
					                	<c:when test="${num eq 20000000}">
					                		<select class="select" id="selPointList_package" name="selPointList_package" onchange="point(this.value,this.options[this.selectedIndex].text, this);" style="border: 1px solid #d6d6d6; border-radius: 26px; text-indent: 10px;">
							                    <c:forEach items="${codePoint_code }" var="code" >
													<option value="${code.CODE_ID }" <c:if test="${pointInfo.CODE_ID == code.CODE_ID }">selected="selected"</c:if> >${code.CODE_NM }</option>
												</c:forEach>
						                  </select>
					                	</c:when>
				                	</c:choose>
			            		</div>
			            	</li>
			
				            <li class="date">
								<div class="inner">
									<div class="tit">
										<img class="ico" src="../common/front/images/ico/ico_res_date.png"/>방문일 선택 (방문일을 선택해주세요)
								 	</div>
								</div>
								<div class="date_list">
									<a href="javascript:void(0);">
										<form method="post">
									        <section class="dateSelect">
										        <input type="hidden" name="date" id="date" value="">
										        <div id="datepicker" style="width: 320px; height: 260px; border:1px solid #ddd;">
										        
										        </div>
										        
										        <div class="notice">
										            <h3>※ 캘린더 사용안내</h3>
										            <p><span class="res_not">11</span> : 예약할 수 없는 날짜입니다.</p>
										            <p><span class="res_ok">11</span> : 예약이 가능한 날짜입니다.</p>
										        </div>
									        </section>
									    </form>
				               		</a>
								</div>
			            	</li>
					    
						    <li class="plant">
						    	<div class="inner">
				                    <div class="tit">
				                    	<img class="ico" src="../common/front/images/ico/ico_res_rantal.png"/>
				                    	<c:choose>
				                    		<c:when test="${num eq 30000000 }">이벤트 상품</c:when>
				                    		<c:otherwise>인원</c:otherwise>
				                    	</c:choose> 선택
				                    </div>
				                    <div class="plantDiv">
				                    
				                    </div>
				                </div>
	            			</li>
				    	</ul>
					<header>
						<h3 class="res_tit tit2"><span>2단계</span><span class="tit2_t">예약내용을 확인해 주세요.</span></h3>
					</header>
					
					
					<ul class="res_info_list confirm">
			            <c:if test="${pointInfo.CODE_ID eq 'POINT01'}">
				            <li class="type">
				                <div class="tit">지점</div>
				                <div class="info">
					                 <!-- <span class="pointNm">하남</span> -->
					                              하남
				                </div>
				            </li>
			            </c:if>
			            <c:if test="${pointInfo.CODE_ID eq 'POINT03'}">
				            <li class="type">
				                <div class="tit">지점</div>
				                <div class="info">
					                 <!-- <span class="pointNm">고양</span> -->
					                              고양
				                </div>
				            </li>
			            </c:if>			            
			            
			            <li class="type">
			                <div class="tit">방문일 선택</div>
			                <div class="info">
			                    <span  id="dateC" class="resDate"></span>
			                </div>
			            </li>
			            <li class="type">
			                <div class="tit">예약유형</div>
			                <div class="info">
			                    <span id="typeName" class="typeTitle"></span>
			                </div>
			            </li>
<!-- 			        <li>
			                <div class="tit">예약내역</div>
			            </li>
 -->
 			        </ul>
				    <div class="total_price">
			            <ul>
			                <li class="totalPersonnel">
			                  <div class="tit">총 방문인원</div>
			                  <div class="val"><span>0</span>명</div>
			                </li>
			                <li class="payment">
			                  <div class="tit">최종결제금액</div>
			                  <div class="val"><span>0</span>원</div>
			                </li>
			            </ul>
			        </div>
			        <div class="item_list">
			        	<div id="resRule" class="resRule">
				            <strong>[이용규정]</strong>
							<ul>
				                <li>일별 예약가능 인원이 한정되어 있어 인원 초과시 예약이 불가 합니다.</li>
				                <li>이용일 전일 23시까지 결제를 완료하셔야 온라인 예약이 확정 됩니다.</li>
				                <li>이용일 전일까지 예약하셔야 이용 가능합니다. <br/>(당일 예약 및 이용불가) </li>
				                <li>본인을 포함하여 최대 10명까지 예약 가능합니다.</li>
				                <li>36개월 미만 유아는 무료입장이 가능하며, 입장 시 의료보험증 등의 증빙서류를 매표소에 제시 바랍니다.</li>
				                <li>소인(36개월 ~ 초등학생) 입장 시 의료보험증 등의 증빙서류를 매표소에 제시 바랍니다.</li>
				                <li>입장 후 6시간 이용 가능합니다.<br/>(복합권 12시간 이용가능)</li>
			                </ul>
				            <strong>[환불규정]</strong>
							<ul>
				                <li>이용일 당일 취소 및 이용일 경과 후 미사용 건은 결제액의 10%를 위약금으로 공제하여 환불해 드립니다.</li>
				                <li>이용일 전 취소는 이용일 1일 전 23시까지 온라인에서 취소해 주시기 바랍니다.</li>
				                <li>현장 이벤트 및 프로모션 등의 할인은 중복적용이 불가하며, 온라인 예약 전 반드시 홈페이지를 확인 바랍니다.<br/> (현장 이벤트 및 프로모션 등의 할인으로 인한 당일취소도 10% 위약금이 발생합니다)</li>
				                <li>결제 완료 후 변경(인원, 일자 등)은 불가하므로 온라인에서 전체 취소 후 다시 예약해 주시기 바랍니다.</li>
				                <li>온라인 예약의 변경 및 취소는 반드시 온라인에서만 가능합니다.(현장 변경 및 취소 불가)</li>
			                </ul>
			   			</div>
					</div>
			
					<div class="chk_motion lst_check3">
						<p class="checkbox_st orange">
					    	<input type="checkbox" id="chkAllTerm" class="checkbox-style" value="N">
				        	<label for="chkAllTerm">예약내용 및 이용규정을 확인하였습니다.</label>
				        </p>
		            </div>
				</form>
		
				<div class="btn_group btn1 or_btn">
				    <a href="#" class="btn orange" id="btnGoPayment">예약하기</a>
			    </div>
			</div>
			
			</div>
	 	</div>
 	</div>
</section>

<script type="text/javascript">
	reservationPop2= new reservationPop2Fn2();
	reservationPop2.setResList();
	
	enc = new encrypt();
	enc.setConf('${key}', '${iv}');
	
	function Tab(type) {
		if(confirm('예약유형을 변경하시면 이전 예약상품이 초기화 됩니다. 계속하시겠습니까?')) {
			if(type == 1) {
				location.href = '/reserve/newResStep.af?type=res';	
			} else {
				location.href = '/reserve/newResStep.af?type=pakage';	
			}	
		} else {
			return;
		}
	}
	
	
	$( document ).ready(function (){
		var pointCheck = $('#pointCode').val();
		var point = '';
		if(pointCheck == 'POINT01') {
			point = '하남';
			$('#pointNm').val(point);
		} else if(pointCheck == 'POINT03') {
			point = '고양';
			$('#pointNm').val(point);
		}
		
		<c:if test="${not empty param.msg}">
			alert('${param.msg}');
			removeParam('msg');
		</c:if>
		
		var numType = ${num};
		if(numType == 10000000) {
			$(".tab_menu2").removeClass("active");
			$('.tab_menu1').addClass("active");
		} else if(numType == 20000000) {
			$(".tab_menu1").removeClass("active");
			$('.tab_menu2').addClass("active");
		}
		
		if(numType == 10000000) {
			$('#typeName').text('입장상품');
		} else if(numType == 20000000) {
			$('#typeName').text('패키지');
		} else if(numType == 30000000) {
			$('#typeName').text('대여상품');
		} else if(numType == 40000000) {
			$('#typeName').text('이벤트');
		} else {
			$('#typeName').text('입장상품');
		}
		
		var emptydaysObj = $.parseJSON( '${emptydays}');
        var emptydayslength = Object.keys(emptydaysObj).length;
        var reservedaysObj = $.parseJSON( '${reservedays}');
        var reservedayslength = Object.keys(reservedaysObj).length;
        var eventdaysObj = $.parseJSON( '${eventdays}');        
        var eventdayslength = Object.keys(eventdaysObj).length;
        
        var emptydays = {};
        $.each(emptydaysObj, function(index){
			emptydays[emptydaysObj[index].YYYYMMDD] = ({title:emptydaysObj[index].TITLE});
        });   

        var reservedays = {};
        $.each(reservedaysObj, function(index){
        	reservedays[reservedaysObj[index].YYYYMMDD] = ({title:reservedaysObj[index].TITLE});
        });
        
        var eventdays = {};
        var eventPeriod = {};
        var tempMM = "00";
        var lastIndex = eventdaysObj.length -1;
        $.each(eventdaysObj, function(index){
        	eventdays[eventdaysObj[index].YYYYMMDD] = ({title:eventdaysObj[index].TITLE});//날짜 셋팅
        	//진행중인 이벤트 셋팅
        	var realMM = eventdaysObj[index].YYYYMMDD;
        	if(tempMM != realMM.substr(4,2)){
        		tempMM = realMM.substr(4,2);
        		if(index != 0){
        			eventPeriod[index-1] = ({date : eventdaysObj[index-1].YYYYMMDD, title:eventdaysObj[index-1].TITLE});
        		}
        		eventPeriod[index] = ({date : eventdaysObj[index].YYYYMMDD, title:eventdaysObj[index].TITLE});
        	}
    		if(index == lastIndex ){
    			eventPeriod[index] = ({date : eventdaysObj[index].YYYYMMDD, title:eventdaysObj[index].TITLE});
    		}
        });       

        var eventHtml = "";
        var intCnt = 0;
        $.each(eventPeriod, function(index){
        	var eventDate = eventPeriod[index].date;
        	eventDate = eventDate.substr(4,2)+"월"+eventDate.substr(6,2)+"일";
			if(intCnt%2 == 0){
				eventHtml +="<p>-" + eventPeriod[index].title + "<br/>("+eventDate+ " ~ ";
			}else{
				eventHtml +=eventDate +") </p>";
			}
			intCnt ++;
        });
        
        if(eventHtml === ""){
        	eventHtml = "<p>진행중인 이벤트가 없습니다.</p>"
        }
        
        $(".event_box").append(eventHtml);
        
        var minday = 1;
        var today = new Date();
        var lastDay = new Date(today.getFullYear(), today.getMonth()+1, 0);
        var betweenDay = Math.floor((today.getTime() - lastDay.getTime())/1000/60/60/24);
        if(reservedaysObj.length == 0){
        	minday = betweenDay;
        }
        
        var normaldays = {};
        var nowMonth = today.getMonth()+1;
        
        if(today.getDate() === lastDay.getDate()){nowMonth +=1;} //마지막날 셋팅을 위한 처리
    	normaldays = setYYYYMMDD(today.getFullYear(),nowMonth);
    	//console.log("normaldays",normaldays);
    	// ##################### 달력 초기화 작업 내용 #################################

    	
        $( "#datepicker" ).datepicker({
            dayNamesMin: ['sun', 'mon', 'tue', 'wed', 'thu', 'fri', 'sat'],
            dateFormat: "yymmdd",
            minDate: 1,
            onSelect: function(date){
            	reservationPop2= new reservationPop2Fn2();
            	reservationPop2.setResList();
                $("#date").val(date);
                
                
                var y = date.substring(0,4);
                var m = date.substring(4,6);
                var d = date.substring(6,8);
                var ymd = y+'.'+m+'.'+d;
                $('#dateC').text(ymd);//방문일
                
                reservationPop2.setResData('date','#date');
                
                var num ='${num}';
                if(num == '10000000'){
                	reservationFn2.plant('plantDiv');
                	$('#typeName').text('입장상품');
                }else if(num == '20000000'){
                	reservationFn2.package('plantDiv');
                	$('#typeName').text('패키지');
                }else if(num == '30000000'){
                	reservationFn2.event('plantDiv');
                	$('#typeName').text('대여상품');
                }else{
                	reservationFn2.event('plantDiv');
                	$('#typeName').text('이벤트');
                }
                
            },
            onChangeMonthYear : function(year, month, i){
            	normaldays = setYYYYMMDD(year,month);
            },
            beforeShowDay: function(day) {
                var result;
                var normalday = normaldays[$.datepicker.formatDate("yymmdd", day)];
                var eventday = eventdays[$.datepicker.formatDate("yymmdd", day)];
                var emptyday = emptydays[$.datepicker.formatDate("yymmdd", day)];
                var reserveday = reservedays[$.datepicker.formatDate("yymmdd", day)];

				if (normalday) {
                    result =  [false, "date-normalday"];
                }
				
				if (reserveday) {
                    result =  [true, "date-reserveday"];
                }
				
                if (eventday) {
                    result =  [true, "date-eventday"];
                }
                
                if (emptyday) {
                    result =  [false, "date-emptyday"];
                }
                
                if(!result) {
                    result = [true, ""];
                }             
                return result;
            }
        });
    	
    	 reservationPop2.getResData('date','#date'); 
		 $( "#datepicker" ).datepicker( "setDate", $("#date").val());    	
	});
	
	//지점선택
	function point(pointCode, pointNm) {
		$('#pointCode').val(pointCode);
		if(confirm('지점을 변경하시면 이전 예약상품이 초기화 됩니다. 계속하시겠습니까?')) {
			$('#f').attr('/reserve/newResStep.af');
			$('#f').submit();
		}
		
	}
	
	
	//접속 아이피 정보
	var ip = "";
	var browser = "";
    $(function() {
 		$.getJSON("https://api.ipify.org?format=jsonp&callback=?",
 			function(json) {
 				ip = json.ip;
 				$("#connectIP").val(ip);
 			}
 		);
 		
 		//PC/모바일 구분
	    var filter = "win16|win32|win64|mac|macintel";
 		var device = "";
 		if(filter.indexOf(navigator.platform.toLowerCase()) < 0){
 			device = "MOBILE";
 		}else{
 			device = "PC";
 		}
 		$("#device").val(device);
 		
 		//브라우저 구분
 		browser = isBrowserCheck();
 		$("#browser").val(browser);
 		
 	});
    
    function isBrowserCheck(){
    	var userAgent = navigator.userAgent.toLowerCase();
    	console.log("userAgent ::: " + userAgent);
    	if(userAgent.indexOf("chrome") != -1) return "Chrome";
    	if(userAgent.indexOf("opera") != -1) return "Opera";
    	if(userAgent.indexOf("staroffice") != -1) return "Star Office";
    	if(userAgent.indexOf("webtv") != -1) return "WebTV";
    	if(userAgent.indexOf("beonex") != -1) return "Opera";
    	if(userAgent.indexOf("opera") != -1) return "Opera";
    	if (userAgent.indexOf("beonex") != -1) return 'Beonex'; 
    	if (userAgent.indexOf("chimera") != -1) return 'Chimera'; 
    	if (userAgent.indexOf("netpositive") != -1) return 'NetPositive'; 
    	if (userAgent.indexOf("phoenix") != -1) return 'Phoenix'; 
    	if (userAgent.indexOf("firefox") != -1) return 'Firefox'; 
    	if (userAgent.indexOf("safari") != -1) return 'Safari'; 
    	if (userAgent.indexOf("skipstone") != -1) return 'SkipStone'; 
    	if (userAgent.indexOf("netscape") != -1) return 'Netscape'; 
    	if (userAgent.indexOf('trident') > -1 ) return 'Internet Explorer 11';
    	if (userAgent.indexOf('edge') > -1 ) return 'Internet Explorer Edge';
    	if (userAgent.indexOf("msie") != -1) { 
    	// 익스플로러 일 경우 
    	var rv = -1; 
    	if (navigator.appName == 'Microsoft Internet Explorer') { 
    		var ua = navigator.userAgent; 
    		var re = new RegExp("MSIE ([0-9]{1,}[\.0-9]{0,})"); 
    		if (re.exec(ua) != null) rv = parseFloat(RegExp.$1); } 
    		return 'Internet Explorer '+rv; 
    	}
    	if (userAgent.indexOf("mozilla/5.0") != -1) return 'Mozilla'; 
    }
    
    
  	//예약 데이터 중복 등록이 안되도록 click 중복 방지 처리
	$("#btnGoPayment").unbind("click").bind("click", function(){
		var chkAllTerm = $("#chkAllTerm").prop("checked") ;
	 	var date = $('#date').val();
		var totalPrice = $('.val').find('span').text();
		
	 	if(date != null && date != '') {
		 	
	 		if(totalPrice != null && totalPrice != '' && totalPrice != 00) {
			 	if(chkAllTerm) {
					$(this).attr("disabled", "disabled");
					/* reservationPop2.addCont({url : "/reserve/step03.af", data : $("#f").serialize().replace(/%/g,'%25')}); */
					$('#f').attr('action', '/reserve/step03.af');
					enc.encrypt();
					//return;
					$('#f').serialize().replace(/%/g,'%25');
					$('#f').submit();
		 	
		 		} else {
					alert('예약내용 및 이용규정 체크를 확인해주세요.');
					return;
				}
			 	
	 		} else {
	 			alert('예약 인원을 선택해주세요.');
		 		return;
	 		}
	 	
	 	} else {
	 		alert('방문일을 선택해주세요.');
	 		return;
	 	}
	});
  	

	function removeParam(parameter){
	  	var url=document.location.href;
	  	var urlparts= url.split('?');

	 	if (urlparts.length>=2){
	  		var urlBase=urlparts.shift(); 
	  		var queryString=urlparts.join("?"); 

			var prefix = encodeURIComponent(parameter)+'=';
			var pars = queryString.split(/[&;]/g);
			for(var i= pars.length; i-->0;)               
				if(pars[i].lastIndexOf(prefix, 0) !== -1)   
			   		pars.splice(i, 1);
		  	url = urlBase+'?'+pars.join('&');
			window.history.pushState('',document.title,url); // added this line to push the new url directly to url bar .
		}
		return url;
	}
	
</script>



<script type="text/javascript">
// ##################### 달력 초기화 작업 내용 #################################
//    setChkAndRadio();
//   $('.customized-select').customSelect();
      
      function setYYYYMMDD(year,month){
      	var arrayParam = {};
      	var lastDay = ( new Date( year,month, 0) ).getDate();
      	for(x= 1; x <=lastDay;x++){
      		var mm;	var dd; var yyyymmdd;
      		mm = (month < 10)? "0"+month : month;
      		dd = (x < 10) ? "0"+x : x;
      		yyyymmdd = ''+ year + mm + dd;
      		arrayParam[yyyymmdd] = ({title:''});
      	}

      	return arrayParam;
      }
</script>


