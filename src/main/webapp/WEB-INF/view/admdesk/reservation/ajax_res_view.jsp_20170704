<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.util.Date" %>
<%@ include file="../../common/taglibs.jsp" %>
<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate value="${now }" pattern="yyyyMMdd" var="today" />
<fmt:parseDate value="${fn:replace(vo.RESERVE_DATE, '.', '')}" var="temp" pattern="yyyyMMdd" />
<fmt:formatDate value="${temp}" pattern="yyyyMMdd" var="reserve_date1"/> 

<section id="pop_res_view_panel" class="pop_type1 bx_pop_type1">
	<div class="inner">
		<header>
			<h1>예약정보</h1>
			<div class="header_btns">
				<button type="button" class="btn_pack btn_close x">Cancel</button>
			</div>
		</header>
		<article>
			<form action="/admdesk/reservation/res_edit.af" onsubmit="return false;">
				<input type="hidden" name="reserve_uid" id="resLinkIdx" value="${vo.reserve_uid }" />
				<div class="tb_type2 mb20">
					<table>
						<tbody>
							<tr>
								<th>예약번호</th>
								<td colspan="3">${vo.ORDER_NUM }</td>
							</tr>
							<tr>
								<th>방문일</th>
								<td>${vo.RESERVE_DATE }</td>
								<th>상	태</th>
								<td>
									${vo.RESERVE_STATE_NM }
									<c:if test="${vo.RESERVE_STATE eq 'ING' }">
										<span class="greenPoint"></span>
									</c:if>
									<c:if test="${vo.RESERVE_STATE eq 'USE' }">
										<span class="grayPoint"></span>
									</c:if>
									<c:if test="${vo.RESERVE_STATE eq 'NOUSE' }">
										<span class="redPoint"></span>
									</c:if>
									<c:if test="${vo.RESERVE_STATE eq 'CANCEL' }">
										<span class="redPoint"></span>
									</c:if>
									<c:if test="${vo.RESERVE_STATE eq 'FCANCEL' }">
										<span class="redPoint"></span>
									</c:if>
								</td>
							</tr>
							<tr>
								<th>회원명</th>
								<td>
									<c:choose>
										<c:when test="${MEMINFOYN eq 'Y'}">
											${vo.MEM_NM }( ${vo.MEM_ID} )										
										</c:when>
										<c:otherwise>
											${vo.MASK_MEM_NM }( ${vo.MASK_MEM_ID} )
										</c:otherwise>
									</c:choose>									
								</td>
								<th>결제방법</th>
								<td>${vo.PAYMENT_TYPE }</td>
							</tr>
							<tr>
								<th>총 방문인원</th>
								<td>${vo.ADULT_SUM + vo.CHILD_SUM } 명</td>
								<th>결제일</th>
								<td>${vo.PAYMENT_DATE }</td>
							</tr>
							<tr>
								<th>휴대폰번호</th>
								<td>
									<c:if test="${MEMINFOYN eq 'Y'}">
								    <c:choose>
								    <c:when test="${fn:length(vo.MEM_MOBILE) > 10 }">
								    	${fn:substring(vo.MEM_MOBILE,0,3)}-${fn:substring(vo.MEM_MOBILE,3,7)}-${fn:substring(vo.MEM_MOBILE,7,11)}
								    </c:when>
								    <c:otherwise>
								    	${fn:substring(vo.MEM_MOBILE,0,3)}-${fn:substring(vo.MEM_MOBILE,3,6)}-${fn:substring(vo.MEM_MOBILE,6,10)}
								    </c:otherwise>							    
								    </c:choose>	
								    </c:if>
								    <c:if test="${MEMINFOYN eq 'N'}">
								    	${vo.MASK_MEM_MOBILE}
								    </c:if>
								</td>
								<th>결제금액</th>
								<td class="price2" >${vo.PAYMENT_PRICE} 원</td>
							</tr>
							<tr>
								<th>상품유형</th>
								<td>${vo.ORDER_NM }</td>
								<th>취소일</th>
								<td>${vo.CANCEL_DATE }</td>
							</tr>
						</tbody>
					</table>
				</div>

				<div class="tb_type5">
					<table>
						<thead>
							<tr>
								<th width="15%">카테고리</th>
								<th width="40%">상품명</th>
								<th width="20%">방문인원</th>
								<th width="10%">수	량</th>
								<th width="15%">가	격</th>
							</tr>
						</thead>
						<tbody>
							<%-- 워터파크 시작 --%>
							<c:if test="${not empty vo.WATER_ITEM && vo.WATER_ITEM ne '' && vo.WATER_ADULT_MAN ne '0' && not empty vo.WATER_ADULT_MAN }">
								<tr>
									<td class="nomal"><!-- 일반상품 -->&nbsp;</td>
									<td class="tit water">${vo.WATER_ITEM_NM}&nbsp;</td>
									<td class="price2">성인남자(${vo.WATER_ADULTS_PRICE }원)</td>
									<td>${vo.WATER_ADULT_MAN }</td>
									<td class="price">${vo.WATER_ADULTS_PRICE * vo.WATER_ADULT_MAN } 원</td>
								</tr>
							</c:if>
							<c:if test="${not empty vo.WATER_ITEM && vo.WATER_ITEM ne '' && vo.WATER_ADULT_WOMEN ne '0' && not empty vo.WATER_ADULT_WOMEN }">
								<tr>
									<td class="nomal"><!-- 일반상품 -->&nbsp;</td>
									<td class="tit water">${vo.WATER_ITEM_NM}&nbsp;</td>
									<td class="price2">성인여자(${vo.WATER_ADULTS_PRICE }원)</td>
									<td>${vo.WATER_ADULT_WOMEN }</td>
									<td class="price">${vo.WATER_ADULTS_PRICE * vo.WATER_ADULT_WOMEN } 원</td>
								</tr>
							</c:if>
							<c:if test="${not empty vo.WATER_ITEM && vo.WATER_ITEM ne '' && vo.WATER_CHILD_MAN ne '0' && not empty vo.WATER_CHILD_MAN }">
								<tr>
									<td class="nomal"><!-- 일반상품 -->&nbsp;</td>
									<td class="tit water">${vo.WATER_ITEM_NM}&nbsp;</td>
									<td class="price2">소인남자(${vo.WATER_CHILD_PRICE }원)</td>
									<td>${vo.WATER_CHILD_MAN }</td>
									<td class="price">${vo.WATER_CHILD_PRICE * vo.WATER_CHILD_MAN } 원</td>
								</tr>
							</c:if>
							<c:if test="${not empty vo.WATER_ITEM && vo.WATER_ITEM ne '' && vo.WATER_CHILD_WOMEN ne '0' && not empty vo.WATER_CHILD_WOMEN }">
								<tr>
									<td class="nomal"><!-- 일반상품 -->&nbsp;</td>
									<td class="tit water">${vo.WATER_ITEM_NM}&nbsp;</td>
									<td class="price2">소인여자(${vo.WATER_CHILD_PRICE }원)</td>
									<td>${vo.WATER_CHILD_WOMEN }</td>
									<td class="price">${vo.WATER_CHILD_PRICE * vo.WATER_CHILD_WOMEN } 원</td>
								</tr>
							</c:if>
							<%-- //워터파크 종료 --%>
							
							<%-- 찜질스파 시작 --%>
							<c:if test="${not empty vo.SPA_ITEM && vo.SPA_ITEM ne '' && vo.SPA_ADULT_MAN ne '0' && not empty vo.SPA_ADULT_MAN }">
								<tr>
									<td class="nomal"><!-- 일반상품 -->&nbsp;</td>
									<td class="tit spa">${vo.SPA_ITEM_NM}&nbsp;</td>
									<td class="price2">성인남자(${vo.SPA_ADULTS_PRICE }원)</td>
									<td>${vo.SPA_ADULT_MAN }</td>
									<td class="price">${vo.SPA_ADULTS_PRICE * vo.SPA_ADULT_MAN } 원</td>
								</tr>
							</c:if>
							<c:if test="${not empty vo.SPA_ITEM && vo.SPA_ITEM ne '' && vo.SPA_ADULT_WOMEN ne '0' && not empty vo.SPA_ADULT_WOMEN }">
								<tr>
									<td class="nomal"><!-- 일반상품 -->&nbsp;</td>
									<td class="tit spa">${vo.SPA_ITEM_NM}&nbsp;</td>
									<td class="price2">성인여자(${vo.SPA_ADULTS_PRICE }원)</td>
									<td>${vo.SPA_ADULT_WOMEN }</td>
									<td class="price">${vo.SPA_ADULTS_PRICE * vo.SPA_ADULT_WOMEN } 원</td>
								</tr>
							</c:if>
							<c:if test="${not empty vo.SPA_ITEM && vo.SPA_ITEM ne '' && vo.SPA_CHILD_MAN ne '0' && not empty vo.SPA_CHILD_MAN }">
								<tr>
									<td class="nomal"><!-- 일반상품 -->&nbsp;</td>
									<td class="tit spa">${vo.SPA_ITEM_NM}&nbsp;</td>
									<td class="price2">소인남자(${vo.SPA_CHILD_PRICE }원)</td>
									<td>${vo.SPA_CHILD_MAN }</td>
									<td class="price">${vo.SPA_CHILD_PRICE * vo.SPA_CHILD_MAN } 원</td>
								</tr>
							</c:if>
							<c:if test="${not empty vo.SPA_ITEM && vo.SPA_ITEM ne '' && vo.SPA_CHILD_WOMEN ne '0' && not empty vo.SPA_CHILD_WOMEN }">
								<tr>
									<td class="nomal"><!-- 일반상품 -->&nbsp;</td>
									<td class="tit spa">${vo.SPA_ITEM_NM}&nbsp;</td>
									<td class="price2">소인여자(${vo.SPA_CHILD_PRICE }원)</td>
									<td>${vo.SPA_CHILD_WOMEN }</td>
									<td class="price">${vo.SPA_CHILD_PRICE * vo.SPA_CHILD_WOMEN } 원</td>
								</tr>
							</c:if>
							<%-- //찜질스파 종료 --%>
							
							<%-- 복합상품 시작 --%>
							<c:if test="${not empty vo.COMPLEX_ITEM && vo.COMPLEX_ITEM ne '' && vo.COMPLEX_ADULT_MAN ne '0' && not empty vo.COMPLEX_ADULT_MAN }">
								<tr>
									<td class="nomal"><!-- 일반상품 -->&nbsp;</td>
									<td class="tit spa">${vo.COMPLEX_ITEM_NM}&nbsp;</td>
									<td class="price2">성인남자(${vo.COMPLEX_ADULTS_PRICE }원)</td>
									<td>${vo.COMPLEX_ADULT_MAN }</td>
									<td class="price">${vo.COMPLEX_ADULTS_PRICE * vo.COMPLEX_ADULT_MAN } 원</td>
								</tr>
							</c:if>
							<c:if test="${not empty vo.COMPLEX_ITEM && vo.COMPLEX_ITEM ne '' && vo.COMPLEX_ADULT_WOMEN ne '0' && not empty vo.COMPLEX_ADULT_WOMEN }">
								<tr>
									<td class="nomal"><!-- 일반상품 -->&nbsp;</td>
									<td class="tit spa">${vo.COMPLEX_ITEM_NM}&nbsp;</td>
									<td class="price2">성인여자(${vo.COMPLEX_ADULTS_PRICE }원)</td>
									<td>${vo.COMPLEX_ADULT_WOMEN }</td>
									<td class="price">${vo.COMPLEX_ADULTS_PRICE * vo.COMPLEX_ADULT_WOMEN } 원</td>
								</tr>
							</c:if>
							<c:if test="${not empty vo.COMPLEX_ITEM && vo.COMPLEX_ITEM ne '' && vo.COMPLEX_CHILD_MAN ne '0' && not empty vo.COMPLEX_CHILD_MAN }">
								<tr>
									<td class="nomal"><!-- 일반상품 -->&nbsp;</td>
									<td class="tit spa">${vo.COMPLEX_ITEM_NM}&nbsp;</td>
									<td class="price2">소인남자(${vo.COMPLEX_CHILD_PRICE }원)</td>
									<td>${vo.COMPLEX_CHILD_MAN }</td>
									<td class="price">${vo.COMPLEX_CHILD_PRICE * vo.COMPLEX_CHILD_MAN } 원</td>
								</tr>
							</c:if>
							<c:if test="${not empty vo.COMPLEX_ITEM && vo.COMPLEX_ITEM ne '' && vo.COMPLEX_CHILD_WOMEN ne '0' && not empty vo.COMPLEX_CHILD_WOMEN }">
								<tr>
									<td class="nomal"><!-- 일반상품 -->&nbsp;</td>
									<td class="tit spa">${vo.COMPLEX_ITEM_NM}&nbsp;</td>
									<td class="price2">소인여자(${vo.COMPLEX_CHILD_PRICE }원)</td>
									<td>${vo.COMPLEX_CHILD_WOMEN }</td>
									<td class="price">${vo.COMPLEX_CHILD_PRICE * vo.COMPLEX_CHILD_WOMEN } 원</td>
								</tr>
							</c:if>
							<%-- //복합상품 종료 --%>							
							
							<%-- 대여상품 시작 --%>
							<c:if test="${not empty vo.RENTAL1_ITEM && vo.RENTAL1_ITEM ne '' }">
								<tr>
									<td class="rental"><!-- 대여상품 -->&nbsp;</td>
									<td class="tit">${vo.RENTAL1_ITEM_NM }</td>
									<td>&nbsp;</td>
									<td>${vo.RENTAL1_CNT }</td>
									<td class="price">${vo.RENTAL1_PRICE * vo.RENTAL1_CNT } 원</td>
								</tr>
							</c:if>
							<c:if test="${not empty vo.RENTAL2_ITEM && vo.RENTAL2_ITEM ne '' }">
								<tr>
									<td class="rental"><!-- 대여상품 -->&nbsp;</td>
									<td class="tit">${vo.RENTAL2_ITEM_NM }</td>
									<td>&nbsp;</td>
									<td>${vo.RENTAL2_CNT }</td>
									<td class="price">${vo.RENTAL2_PRICE * vo.RENTAL2_CNT } 원</td>
								</tr>
							</c:if>
							<c:if test="${not empty vo.RENTAL3_ITEM && vo.RENTAL3_ITEM ne '' }">
								<tr>
									<td class="rental"><!-- 대여상품 -->&nbsp;</td>
									<td class="tit">${vo.RENTAL3_ITEM_NM }</td>
									<td>&nbsp;</td>
									<td>${vo.RENTAL3_CNT }</td>
									<td class="price">${vo.RENTAL3_PRICE * vo.RENTAL3_CNT } 원</td>
								</tr>
							</c:if>
							<%-- //대여상품 종료 --%>
							
							<%-- 이벤트상품 시작 --%>
							<c:if test="${not empty vo.EVENT1_ITEM && vo.EVENT1_ITEM ne '' }">
								<tr>
									<td class="event"><!-- 이벤트상품 -->&nbsp;</td>
									<td class="tit">${vo.EVENT1_ITEM_NM }</td>
									<td>&nbsp;</td>
									<td>${vo.EVENT1_CNT }</td>
									<td class="price">${vo.EVENT1_PRICE * vo.EVENT1_CNT } 원</td>
								</tr>
							</c:if>
							<c:if test="${not empty vo.EVENT2_ITEM && vo.EVENT2_ITEM ne '' }">
								<tr>
									<td class="event"><!-- 이벤트상품 -->&nbsp;</td>
									<td class="tit">${vo.EVENT2_ITEM_NM }</td>
									<td>&nbsp;</td>
									<td>${vo.EVENT2_CNT }</td>
									<td class="price">${vo.EVENT2_PRICE * vo.EVENT2_CNT } 원</td>
								</tr>
							</c:if>
							<c:if test="${not empty vo.EVENT3_ITEM && vo.EVENT3_ITEM ne '' }">
								<tr>
									<td class="event"><!-- 이벤트상품 -->&nbsp;</td>
									<td class="tit">${vo.EVENT3_ITEM_NM }</td>
									<td>&nbsp;</td>
									<td>${vo.EVENT3_CNT }</td>
									<td class="price">${vo.EVENT3_PRICE * vo.EVENT3_CNT } 원</td>
								</tr>
							</c:if>
							<%-- //이벤트상품 종료 --%>
						</tbody>
						<tfoot>
							<tr>
								<th colspan="4">위약금</td>
								<td class="price">
									<c:choose>
<%-- 										<c:when test="${ reserve_date1 <= today }"> --%>
										<c:when test="${ vo.PANALTY_PRICE != null }">
<%-- 											<fmt:parseNumber var="temp_num" value="${ vo.PAYMENT_PRICE/ 10 }" integerOnly="true" /> --%>
<%-- 											${temp_num } 원 --%>
											${vo.PANALTY_PRICE}
										</c:when>
										<c:otherwise>
											0 원
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
							<tr>
								<th colspan="4">합계</td>
								<td class="price"><strong>${vo.PAYMENT_PRICE } 원</strong></td>
							</tr>
						</tfoot>
					</table>
				</div>
			</form>
		</article>
		<footer>
			<div class="bot_btns">
				<c:if test="${vo.RESERVE_STATE eq 'ING' }">
					<button class="btn_pack btn_mo d_gray" type="button" onclick="resCompleteUse('${vo.RESERVE_UID }')">사용완료</button>
				</c:if>
				<a href="#" onclick="goDetail('${vo.ORDER_NUM}'); return false;" class="btn_pack btn_mo gray"><img src="/admaf/images/common/ico_edit.png">수정</a>
			</div>
		</footer>
	</div>
	<script type="text/javascript">
	
		$( document ).ready(function() {
			// 표 카테고리 자동입력
		    var $nomal = $(".nomal");
		    var $rental = $(".rental");
		    var $event = $(".event");
		   	var $water = $(".water");
		   	var $spa = $(".spa");
		    var cata_code = "${vo.ORDER_TP}";
		    
		    if($nomal.length != 0 && cata_code == "0" ){   	$nomal.eq(0).text("입장상품"); 	   }
			if($nomal.length != 0 && cata_code == "1" ){		$nomal.eq(0).text("패키지"); 		}
		    if($rental.length != 0){	$rental.eq(0).text("대여상품"); 	}
		    if($event.length != 0){		$event.eq(0).text("이벤트상품");	}
//		    if($spa.length != 0){		$spa.eq(0).text("찜질스파");		}
//		    if($water.length != 0){		$water.eq(0).text("워터파크");		}
		    
		    //가격 콤파 추가
		    $(".price").each(function(){
		    	$(this).text($(this).text().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
		    });
		    $(".price2").each(function(){
		    	$(this).text($(this).text().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
		    });
		    
		});
		
		// 사용완료 처리
		function resCompleteUse(resIdx){
			var stat = "${vo.RESERVE_STATE}";
			var tempStr = "${vo.RESERVE_STATE_NM }";
			// 예약상태 혹은 미사용 상태 일 경우만 동작
			if( stat == 'ING' || stat == 'NOUSE' ){
				$.ajax({
					method: "POST",
					url: "/secu_admaf/admdesk/reservation/resCompleteUse.af",
					dataType: "JSON",
					data : { reserve_uid : resIdx },
					success:function(data){
						if(data >= 1){
							alert("수정을 완료 하였습니다.");
							
							$("#res"+resIdx).find(".stat").removeClass("res");
							$("#res"+resIdx).find(".stat").removeClass("cancel");
							$("#res"+resIdx).find(".stat").text("이용완료");
							//팝업 종료
							popFn.hide($("#pop_res_view_panel"));
						}else{
							alert("알 수 없는 오류가 발생하였습니다.");
							return false;
						}
					},
					error : function(xhr, status, error) {
		                 alert("알 수 없는 오류가 발생하였습니다.");
		                 return false;
		           }
				})
			}else{
				alert("현재 '"+ tempStr +"' 상태입니다.");
				return false;
			}
		}
	</script>
</section>