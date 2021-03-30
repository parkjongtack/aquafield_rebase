<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../common/taglibs.jsp" %>
<section id="reservation">
    <div id="resTicketHeader">
    	<form name="searForm" id="searForm" method="post" onsubmit="return false;"> 		
    	<ul>
    		<li class="active">
    			<a href="javascript:void(0);" id="ALL">전체보기</a>
    		</li>
    		<li>
    			<a href="javascript:void(0);" id="ING">예약현황</a>
    		</li>
    		<li>
    			<a href="javascript:void(0);" id="USE">이용현황</a>
    		</li>
    		<li>
    			<a href="javascript:void(0);" id="CANCEL">취소내역</a>
    		</li>
    	</ul>
    	<div class="select st2">
    		<select name="term" id="term" class="customized-select w150" onchange="goSearch();">
				<option value="0">전체</option>
				<option value="1">최근 6개월</option>
				<option value="2">최근 1년</option>
			</select>
    	</div>
    	</form>
    </div>
    <div id="resTicketArea">
        <c:choose>
        	<c:when test="${ !empty getReservelist}">
				<div class="inner swiper-container">
					<div class="swiper-wrapper">
					  <c:forEach items="${getReservelist}" var="reserveInfo">
			          <c:if test="${reserveInfo.RESERVE_STATE eq 'ING'}"> <c:set var="type" value="" /></c:if>			  
			          <c:if test="${reserveInfo.RESERVE_STATE eq 'USE'}"> <c:set var="type" value="used" /></c:if>
			          <c:if test="${reserveInfo.RESERVE_STATE eq 'CANCEL'}"> <c:set var="type" value="canceled"/></c:if> 
					  <div class="swiper-slide">
					  	 <input type="hidden" id="dayCnt" value="${reserveInfo.DAYCNT}">
					  	 <div class="resTicket ${type} ${reserveInfo.RESERVE_STATE}">				    
							<div class="inner">
								<ul>
									<li class="front">
										<div class="tickettop">
											<img src="/common/front/images/mypage/ticket_header_logo.png">
										</div>
										<div class="codenum">
											<div class="ico res_1"></div>
											<div class="tit">아쿠아필드 <span>${reserveInfo.POINT_CODE}점</span> 입장권</div>
											<div class="code">${reserveInfo.ORDER_NUM}</div>
										</div>
										<div class="txtArea">
											<ul>
												<li><span class="tit">입 장 일</span><span class="value">${reserveInfo.RESERVE_DATE}</span></li>
												<li><span class="tit">예약시설</span><span class="value">${reserveInfo.ORDER_NM} 외</span></li>
												<li><span class="tit">예약인원</span><span class="value">총 ${reserveInfo.ADULT_SUM + reserveInfo.CHILD_SUM}명</span></li>
												<li><span class="tit">결제금액</span><span class="value"><fmt:formatNumber type="currency" value="${reserveInfo.PAYMENT_PRICE}" pattern="###,###"/> 원</span></li>
											</ul>
										</div>
									</li>
									<li class="back">
										<div class="info">
											<div class="tit">입장정보</div>
											<ul class="item_list">
											<c:if test="${reserveInfo.SPA_ITEM ne ''}">
												<c:if test="${reserveInfo.SPA_ADULT_MAN > 0}"><li><span class="tit">${reserveInfo.SPA_ITEM_NM}</span><span class="value">대인남자 ${reserveInfo.SPA_ADULT_MAN}명</span></li></c:if>
												<c:if test="${reserveInfo.SPA_ADULT_WOMEN > 0}"><li><span class="tit">${reserveInfo.SPA_ITEM_NM}</span><span class="value">대인여자 ${reserveInfo.SPA_ADULT_WOMEN}명</span></li></c:if>
												<c:if test="${reserveInfo.SPA_CHILD_MAN > 0}"><li><span class="tit">${reserveInfo.SPA_ITEM_NM}</span><span class="value">소인남자 ${reserveInfo.SPA_CHILD_MAN}명</span></li></c:if>
												<c:if test="${reserveInfo.SPA_CHILD_WOMEN > 0}"><li><span class="tit">${reserveInfo.SPA_ITEM_NM}</span><span class="value">소인여자 ${reserveInfo.SPA_CHILD_WOMEN}명</span></li></c:if>
											</c:if>
											<c:if test="${reserveInfo.WATER_ITEM ne ''}">
												<c:if test="${reserveInfo.WATER_ADULT_MAN > 0}"><li><span class="tit">${reserveInfo.WATER_ITEM_NM}</span><span class="value">대인남자 ${reserveInfo.WATER_ADULT_MAN}명</span></li></c:if>
												<c:if test="${reserveInfo.WATER_ADULT_WOMEN > 0}"><li><span class="tit">${reserveInfo.WATER_ITEM_NM}</span><span class="value">대인여자 ${reserveInfo.WATER_ADULT_WOMEN}명</span></li></c:if>
												<c:if test="${reserveInfo.WATER_CHILD_MAN > 0}"><li><span class="tit">${reserveInfo.WATER_ITEM_NM}</span><span class="value">소인남자 ${reserveInfo.WATER_CHILD_MAN}명</span></li></c:if>
												<c:if test="${reserveInfo.WATER_CHILD_WOMEN > 0}"><li><span class="tit">${reserveInfo.WATER_ITEM_NM}</span><span class="value">소인여자 ${reserveInfo.WATER_CHILD_WOMEN}명</span></li></c:if>
											</c:if>
											<c:if test="${reserveInfo.EVENT1_ITEM ne ''}">
												<c:if test="${reserveInfo.EVENT1_CNT > 0}"><li><span class="tit">${reserveInfo.EVENT1_ITEM_NM}</span><span class="value">${reserveInfo.EVENT1_CNT}명</span></li></c:if>
											</c:if>
											<c:if test="${reserveInfo.EVENT2_ITEM ne ''}">
												<c:if test="${reserveInfo.EVENT2_CNT > 0}"><li><span class="tit">${reserveInfo.EVENT2_ITEM_NM}</span><span class="value">${reserveInfo.EVENT2_CNT}명</span></li></c:if>
											</c:if>	
											<c:if test="${reserveInfo.EVENT3_ITEM ne ''}">
												<c:if test="${reserveInfo.EVENT3_CNT > 0}"><li><span class="tit">${reserveInfo.EVENT3_ITEM_NM}</span><span class="value">${reserveInfo.EVENT3_CNT}명</span></li></c:if>
											</c:if>
											<c:if test="${reserveInfo.RENTAL1_ITEM ne ''}">
												<c:if test="${reserveInfo.RENTAL1_CNT > 0}"><li><span class="tit">${reserveInfo.RENTAL1_ITEM_NM}</span><span class="value">${reserveInfo.RENTAL1_CNT}개</span></li></c:if>
											</c:if>
											<c:if test="${reserveInfo.RENTAL2_ITEM ne ''}">
												<c:if test="${reserveInfo.RENTAL2_CNT > 0}"><li><span class="tit">${reserveInfo.RENTAL2_ITEM_NM}</span><span class="value">${reserveInfo.RENTAL2_CNT}개</span></li></c:if>
											</c:if>	
											<c:if test="${reserveInfo.RENTAL3_ITEM ne ''}">
												<c:if test="${reserveInfo.RENTAL3_CNT > 0}"><li><span class="tit">${reserveInfo.RENTAL3_ITEM_NM}</span><span class="value">${reserveInfo.RENTAL3_CNT}개</span></li></c:if>
											</c:if>																																																											
											</ul>
										</div>
										<div class="txtArea">
											<div class="alert">* 주의사항</div>
											<p>이용일 당일 취소 시 30% 위약금이 발생합니다.</p>
											<p>36개월 미만 유아는 무료입장이 가능하며 현장방문 시 의료보험증 등의 공인 서류를 제시 바랍니다.</p>
											<p>소인(36개월 ~ 초등학생 입장 시 의료보험증 등의 공인서류를 매표소에 제시 바랍니다.  </p>
										</div>
									</li>
								</ul>
								<div class="btnArea">
									<a href="javascript:void(0);" onclick="window.resTicketPop = new ResTicketPopFn({data:{uid:${reserveInfo.RESERVE_UID}, stat:'Y'}});">
										More Info
									</a>
								</div>
							</div>
							<div class="border">
								<img class="topleft" src="/common/front/images/mypage/border_topleft.png">
								<img class="topright" src="/common/front/images/mypage/border_topright.png">
								<img class="bottomleft" src="/common/front/images/mypage/border_bottomleft.png">
								<img class="bottomright" src="/common/front/images/mypage/border_bottomright.png">
							</div>
						 </div>
					   </div>					
					</c:forEach>
					</div>
				</div>        	
        	</c:when>
        	<c:otherwise>
	 			<div class="res_none">
					<img src="/common/front/images/mypage/res_none_list.png"/>
				</div>       	
        	</c:otherwise>
        </c:choose>
	</div>
	<div id="resTicketDetail" class="pop_type2">
		<div class="inner">
			<div id="bx_multi_content">
	            <button class="btn_close">닫기</button>
	            <div class="content"></div>
	        </div>
		</div>
	</div>
	<script type="text/javascript">
		$('.customized-select').customSelect();

		var mainNewsSlider = $('.swiper-wrapper').bxSlider({
			slideSelector: '.swiper-slide',
			minSlides: 1,
			maxSlides: 3,
			slideWidth: 360,
			infiniteLoop: false,
			pager:false,
			controls: true,
			touchEnabled: false,
			hideControlOnEnd: true,
			adaptiveHeight: true
		});
		
		$('#resTicketHeader li a').on('click', function(e){
	    	e.preventDefault();
	    	$('#resTicketHeader li').removeClass('active');
			$(this).parent().addClass('active');
		});

		$('#resTicketArea li.front').on('click', function(e){
	    	e.preventDefault();
	    	var winW = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
	    	if(winW < 640){
	    		if(!$(this).hasClass("off")){
		    		$(this).addClass("off")
		    		TweenMax.to($(this), 0.6, {'alpha' : 0, ease: Power3.easeInOut});
		    	}else{
		    		$(this).removeClass("off")
		    		TweenMax.to($(this), 0.6, {'alpha' : 1, ease: Power3.easeInOut, onComplete : function(){
		    			$(this).css('opacity', '');
		    		}});
		    	}
	    	}
	    	/*if(!$(this).hasClass("off")){
	    		$(this).addClass("off")
	    		TweenMax.to($(this), 0.6, {'alpha' : 0, ease: Power3.easeInOut});
	    	}else{
	    		$(this).removeClass("off")
	    		TweenMax.to($(this), 0.6, {'alpha' : 1, ease: Power3.easeInOut});
	    	}*/		    	
		});
		
        $('#resTicketHeader').find('ul > li a').click(function(e) {
            if(!$(this).hasClass('active')){
                var t = $(this).attr('id');
                console.log("t ::: ", t);
                $('#resTicketHeader').find('ul > li a').removeClass('active')
                $(this).addClass('active');

                $('.resTicket').each(function(i, v) {
                    if($(this).hasClass(t)){
                        $(this).css('display', 'block');
                    }else if(t == 'ALL') {
                        $(this).css('display', 'block');
                    }else{
                        $(this).css('display', 'none');
                    }
                });
                iscrollFn.refresh();
            }
        });
        
        function goSearch(){
        	contentBox.setCont({url : '/mypage/reserve.af', data : {term : $("#term option:selected").val()}, target:this});
        }
	</script>


</section>