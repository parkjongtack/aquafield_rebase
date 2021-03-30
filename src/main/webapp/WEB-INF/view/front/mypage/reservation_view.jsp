<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../common/taglibs.jsp" %>
<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate value="${now }" pattern="yyyyMMdd" var="today" />
<fmt:parseDate value="${reserveInfo.COMPARE_DAY}" var="temp" pattern="yyyyMMdd" />
<fmt:formatDate value="${temp}" pattern="yyyyMMdd" var="compare_day"/>
<div class="iscContent">
    <div class="inner">
		<div id="resTicketDetail">
			<div class="ticketArea">
				<div class="resTicket" id="printarea">
					<div class="inner">
						<div class="tickettop">
							<img src="/common/front/images/mypage/ticket_header_logo.png">
						</div>
						<div class="codenum">
							<div class="tit"><span>${reserveInfo.POINT_NM}점</span> 입장권</div>
							<div class="code">${reserveInfo.ORDER_NUM}</div>
						</div>
						<div class="txtArea">
							예약상태 :
							<c:if test="${reserveInfo.RESERVE_STATE eq 'ING'}">
								<span class="res">예약</span>
							</c:if>
							<c:if test="${reserveInfo.RESERVE_STATE eq 'USE'}">
								<span class="res">사용</span>
							</c:if>
							<c:if test="${reserveInfo.RESERVE_STATE eq 'CANCEL'}">
								<span class="res">정상취소</span>
							</c:if>
							<c:if test="${reserveInfo.RESERVE_STATE eq 'NOUSE'}">
								<span class="res">미사용</span>
							</c:if>
							<c:if test="${reserveInfo.RESERVE_STATE eq 'FCANCEL'}">
								<span>위약취소</span>
							</c:if>
						</div>
					</div>
					<div class="border">
						<img class="topleft" src="/common/front/images/mypage/border_topleft.png">
						<img class="topright" src="/common/front/images/mypage/border_topright.png">
						<img class="bottomleft" src="/common/front/images/mypage/border_bottomleft.png">
						<img class="bottomright" src="/common/front/images/mypage/border_bottomright.png">
					</div>
				</div>
				<ul class="btns">
				    <c:if test="${reserveInfo.RESERVE_STATE eq 'ING' && compare_day >= today }">
		        	<li>
		        		<button class="btn_pack btn_mo resCancel" onclick="reserveCancel('${reserveInfo.cancelGubun}');">예약취소</button>
		        	</li>
		        	</c:if>
					<c:if test="${sendSmsCnt eq '0' && reserveInfo.RESERVE_STATE eq 'ING' }">
			        	<li class="col s6">
			        		<button class="btn_pack btn_mo white" onclick="smsSend();">SMS 발송</button>
			        	</li>
					</c:if>
					 
		        	<li>
		        		<button class="btn_pack btn_mo print" onclick="goPrint('입장권');">프린트</button>
		        	</li>
		        </ul>
		        <!-- <p class="em">※ SMS발송은 결제 후, 1회만 수동발송 가능합니다.</p> -->
			</div>
			<div class="res_info">
				<div class="row">
					<div class="col s6">
						<div class="tit">예약정보</div>
						<div class="tb_res">
							<table>
								<tr>
									<th>입 장 일</th>
									<td>${reserveInfo.RESERVE_DATE}</td>
								</tr>
								<tr>
									<th>예약시설</th>
									<td>${reserveInfo.ORDER_NM} 외</td>
								</tr>
								<tr>
									<th>예약인원</th>
									<td>총 ${reserveInfo.ADULT_SUM + reserveInfo.CHILD_SUM}명</td>
								</tr>
								<tr>
									<th>결제금액</th>
									<td><fmt:formatNumber type="currency" value="${reserveInfo.PAYMENT_PRICE}" pattern="###,###"/>원</td>
								</tr>
							</table>
						</div>
						<div class="tit">입장정보</div>
						<div class="tb_res">
							<table>
								<c:if test="${reserveInfo.SPA_ITEM ne ''}">
									<c:if test="${reserveInfo.SPA_ADULT_MAN > 0}">
									<tr>
										<th>${reserveInfo.SPA_ITEM_NM}</th>
										<td>대인남자  ${reserveInfo.SPA_ADULT_MAN}명</td>
									</tr>
									</c:if>
									<c:if test="${reserveInfo.SPA_ADULT_WOMEN > 0}">
									<tr>
										<th>${reserveInfo.SPA_ITEM_NM}</th>
										<td>대인여자  ${reserveInfo.SPA_ADULT_WOMEN}명</td>
									</tr>
									</c:if>
									<c:if test="${reserveInfo.SPA_CHILD_MAN > 0}">
									<tr>
										<th>${reserveInfo.SPA_ITEM_NM}</th>
										<td>소인남자  ${reserveInfo.SPA_CHILD_MAN}명</td>
									</tr>
									</c:if>
									<c:if test="${reserveInfo.SPA_CHILD_WOMEN > 0}">
									<tr>
										<th>${reserveInfo.SPA_ITEM_NM}</th>
										<td>소인여자  ${reserveInfo.SPA_CHILD_WOMEN}명</td>
									</tr>
									</c:if>
								</c:if>
								<c:if test="${reserveInfo.WATER_ITEM ne ''}">
									<c:if test="${reserveInfo.WATER_ADULT_MAN > 0}">
									<tr>
										<th>${reserveInfo.WATER_ITEM_NM}</th>
										<td>대인남자  ${reserveInfo.WATER_ADULT_MAN}명</td>
									</tr>
									</c:if>
									<c:if test="${reserveInfo.WATER_ADULT_WOMEN > 0}">
									<tr>
										<th>${reserveInfo.WATER_ITEM_NM}</th>
										<td>대인여자  ${reserveInfo.WATER_ADULT_WOMEN}명</td>
									</tr>
									</c:if>
									<c:if test="${reserveInfo.WATER_CHILD_MAN > 0}">
									<tr>
										<th>${reserveInfo.WATER_ITEM_NM}</th>
										<td>소인남자  ${reserveInfo.WATER_CHILD_MAN}명</td>
									</tr>
									</c:if>
									<c:if test="${reserveInfo.WATER_CHILD_WOMEN > 0}">
									<tr>
										<th>${reserveInfo.WATER_ITEM_NM}</th>
										<td>소인여자  ${reserveInfo.WATER_CHILD_WOMEN}명</td>
									</tr>
									</c:if>
								</c:if>
								<c:if test="${reserveInfo.COMPLEX_ITEM ne ''}">
									<c:if test="${reserveInfo.COMPLEX_ADULT_MAN > 0}">
									<tr>
										<th>${reserveInfo.COMPLEX_ITEM_NM}</th>
										<td>대인남자  ${reserveInfo.COMPLEX_ADULT_MAN}명</td>
									</tr>
									</c:if>
									<c:if test="${reserveInfo.COMPLEX_ADULT_WOMEN > 0}">
									<tr>
										<th>${reserveInfo.COMPLEX_ITEM_NM}</th>
										<td>대인여자  ${reserveInfo.COMPLEX_ADULT_WOMEN}명</td>
									</tr>
									</c:if>
									<c:if test="${reserveInfo.COMPLEX_CHILD_MAN > 0}">
									<tr>
										<th>${reserveInfo.COMPLEX_ITEM_NM}</th>
										<td>소인남자  ${reserveInfo.COMPLEX_CHILD_MAN}명</td>
									</tr>
									</c:if>
									<c:if test="${reserveInfo.COMPLEX_CHILD_WOMEN > 0}">
									<tr>
										<th>${reserveInfo.COMPLEX_ITEM_NM}</th>
										<td>소인여자  ${reserveInfo.COMPLEX_CHILD_WOMEN}명</td>
									</tr>
									</c:if>
								</c:if>
								<%-- <c:if test="${reserveInfo.EVENT1_ITEM ne ''}">
									<c:if test="${reserveInfo.EVENT1_CNT > 0}">
									<tr>
										<th>${reserveInfo.EVENT1_ITEM_NM}</th>
										<td>${reserveInfo.EVENT1_CNT}명</td>
									</tr>
									</c:if>
							   </c:if>
								<c:if test="${reserveInfo.EVENT2_ITEM ne ''}">
									<c:if test="${reserveInfo.EVENT2_CNT > 0}">
									<tr>
										<th>${reserveInfo.EVENT2_ITEM_NM}</th>
										<td>${reserveInfo.EVENT2_CNT}명</td>
									</tr>
									</c:if>
							   </c:if>
								<c:if test="${reserveInfo.EVENT3_ITEM ne ''}">
									<c:if test="${reserveInfo.EVENT3_CNT > 0}">
									<tr>
										<th>${reserveInfo.EVENT3_ITEM_NM}</th>
										<td>${reserveInfo.EVENT3_CNT}명</td>
									</tr>
									</c:if>
							   </c:if>
								<c:if test="${reserveInfo.RENTAL1_ITEM ne ''}">
									<c:if test="${reserveInfo.RENTAL1_CNT > 0}">
									<tr>
										<th>${reserveInfo.RENTAL1_ITEM_NM}</th>
										<td>${reserveInfo.RENTAL1_CNT}개</td>
									</tr>
									</c:if>
							   </c:if>
								<c:if test="${reserveInfo.RENTAL2_ITEM ne ''}">
									<c:if test="${reserveInfo.RENTAL2_CNT > 0}">
									<tr>
										<th>${reserveInfo.RENTAL2_ITEM_NM}</th>
										<td>${reserveInfo.RENTAL2_CNT}개</td>
									</tr>
									</c:if>
							   </c:if>
								<c:if test="${reserveInfo.RENTAL3_ITEM ne ''}">
									<c:if test="${reserveInfo.RENTAL3_CNT > 0}">
									<tr>
										<th>${reserveInfo.RENTAL3_ITEM_NM}</th>
										<td>${reserveInfo.RENTAL3_CNT}개</td>
									</tr>
									</c:if>
							   </c:if> --%>
							</table>
						</div>
					</div>
					<div class="col s6">
						<div class="tit">결제정보</div>
						<div class="tb_res">
							<table>
								<tr>
									<th>결제타입</th>
									<td>${pgResultInfo.r_TYPE}</td>
								</tr>
								<tr>
									<th>결 제 일</th>
									<td>${reserveInfo.PAYMENT_DATE}</td>
								</tr>
								<tr>
									<th>결제금액</th>
									<td><fmt:formatNumber type="currency" value="${reserveInfo.PAYMENT_PRICE}" pattern="###,###"/>원</td>
								</tr>
								<tr>
									<th></th>
									<td>
										<c:if test="${pgResultInfo.r_TYPE eq '카드'}">
											<button class="btn_pack btn_mo card" onclick="javascript:receiptView('${pgResultInfo.TR_NO}');">카드영수증 출력</button>
										</c:if>
										<c:if test="${pgResultInfo.r_TYPE eq '실시간계좌이체'}">
											<button class="btn_pack btn_mo card" onclick="javascript:CashreceiptView('${pgResultInfo.TR_NO}');">현금영수증 출력</button>
										</c:if>
									</td>
								</tr>
							</table>
						</div>
						<div class="tit">결제내역</div>
						<div class="tb_res payment">
							<table>
								<c:if test="${reserveInfo.SPA_ITEM ne ''}">
									<c:if test="${(reserveInfo.SPA_ADULT_MAN + reserveInfo.SPA_ADULT_WOMEN) > 0}">
									<tr>
										<td class="left"><fmt:formatNumber type="currency" value="${reserveInfo.intSpaAdultW}" pattern="###,###"/>원 X ${reserveInfo.SPA_ADULT_MAN + reserveInfo.SPA_ADULT_WOMEN}명 = </td>
										<td><fmt:formatNumber type="currency" value="${reserveInfo.intSpaAdultW * (reserveInfo.SPA_ADULT_MAN + reserveInfo.SPA_ADULT_WOMEN)}" pattern="###,###"/>원</td>
									</tr>
									</c:if>
									<c:if test="${(reserveInfo.SPA_CHILD_MAN + reserveInfo.SPA_CHILD_WOMEN) > 0}">
									<tr>
										<td class="left"><fmt:formatNumber type="currency" value="${reserveInfo.intSpaChildW}" pattern="###,###"/>원 X ${reserveInfo.SPA_CHILD_MAN + reserveInfo.SPA_CHILD_WOMEN}명 = </td>
										<td><fmt:formatNumber type="currency" value="${reserveInfo.intSpaChildW * (reserveInfo.SPA_CHILD_MAN + reserveInfo.SPA_CHILD_WOMEN)}" pattern="###,###"/>원</td>
									</tr>
									</c:if>
								</c:if>
								<c:if test="${reserveInfo.WATER_ITEM ne ''}">
									<c:if test="${(reserveInfo.WATER_ADULT_MAN + reserveInfo.WATER_ADULT_WOMEN) > 0}">
									<tr>
										<td class="left"><fmt:formatNumber type="currency" value="${reserveInfo.intWaterAdultW}" pattern="###,###"/>원 X ${reserveInfo.WATER_ADULT_MAN + reserveInfo.WATER_ADULT_WOMEN}명 = </td>
										<td><fmt:formatNumber type="currency" value="${reserveInfo.intWaterAdultW * (reserveInfo.WATER_ADULT_MAN + reserveInfo.WATER_ADULT_WOMEN)}" pattern="###,###"/>원</td>
									</tr>
									</c:if>
									<c:if test="${(reserveInfo.WATER_CHILD_MAN + reserveInfo.WATER_CHILD_WOMEN) > 0}">
									<tr>
										<td class="left"><fmt:formatNumber type="currency" value="${reserveInfo.intWaterChildW}" pattern="###,###"/>원 X ${reserveInfo.WATER_CHILD_MAN + reserveInfo.WATER_CHILD_WOMEN}명 = </td>
										<td><fmt:formatNumber type="currency" value="${reserveInfo.intWaterChildW * (reserveInfo.WATER_CHILD_MAN + reserveInfo.WATER_CHILD_WOMEN)}" pattern="###,###"/>원</td>
									</tr>
									</c:if>
								</c:if>
								<c:if test="${reserveInfo.COMPLEX_ITEM ne ''}">
									<c:if test="${(reserveInfo.COMPLEX_ADULT_MAN + reserveInfo.COMPLEX_ADULT_WOMEN) > 0}">
									<tr>
										<td class="left"><fmt:formatNumber type="currency" value="${reserveInfo.intComplexAdultW}" pattern="###,###"/>원 X ${reserveInfo.COMPLEX_ADULT_MAN + reserveInfo.COMPLEX_ADULT_WOMEN}명 = </td>
										<td><fmt:formatNumber type="currency" value="${reserveInfo.intComplexAdultW * (reserveInfo.COMPLEX_ADULT_MAN + reserveInfo.COMPLEX_ADULT_WOMEN)}" pattern="###,###"/>원</td>
									</tr>
									</c:if>
									<c:if test="${(reserveInfo.COMPLEX_CHILD_MAN + reserveInfo.COMPLEX_CHILD_WOMEN) > 0}">
									<tr>
										<td class="left"><fmt:formatNumber type="currency" value="${reserveInfo.intComplexChildW}" pattern="###,###"/>원 X ${reserveInfo.COMPLEX_CHILD_MAN + reserveInfo.COMPLEX_CHILD_WOMEN}명 = </td>
										<td><fmt:formatNumber type="currency" value="${reserveInfo.intComplexChildW * (reserveInfo.COMPLEX_CHILD_MAN + reserveInfo.COMPLEX_CHILD_WOMEN)}" pattern="###,###"/>원</td>
									</tr>
									</c:if>
								</c:if>
								<%-- <c:if test="${reserveInfo.EVENT1_ITEM ne ''}">
									<c:if test="${reserveInfo.EVENT1_CNT > 0}">
									<tr>
										<td class="left"><fmt:formatNumber type="currency" value="${reserveInfo.event1W}" pattern="###,###"/>원 X ${reserveInfo.EVENT1_CNT}명 = </td>
										<td><fmt:formatNumber type="currency" value="${reserveInfo.event1W * (reserveInfo.EVENT1_CNT)}" pattern="###,###"/>원</td>
									</tr>
									</c:if>
								</c:if>
								<c:if test="${reserveInfo.EVENT2_ITEM ne ''}">
									<c:if test="${reserveInfo.EVENT2_CNT > 0}">
									<tr>
										<td class="left"><fmt:formatNumber type="currency" value="${reserveInfo.event2W}" pattern="###,###"/>원 X ${reserveInfo.EVENT2_CNT}명 = </td>
										<td><fmt:formatNumber type="currency" value="${reserveInfo.event2W * (reserveInfo.EVENT2_CNT)}" pattern="###,###"/>원</td>
									</tr>
									</c:if>
								</c:if>
								<c:if test="${reserveInfo.EVENT3_ITEM ne ''}">
									<c:if test="${reserveInfo.EVENT3_CNT > 0}">
									<tr>
										<td class="left"><fmt:formatNumber type="currency" value="${reserveInfo.event3W}" pattern="###,###"/>원 X ${reserveInfo.EVENT3_CNT}명 = </td>
										<td><fmt:formatNumber type="currency" value="${reserveInfo.event3W * (reserveInfo.EVENT3_CNT)}" pattern="###,###"/>원</td>
									</tr>
									</c:if>
								</c:if>
								<c:if test="${reserveInfo.RENTAL1_ITEM ne ''}">
									<c:if test="${reserveInfo.RENTAL1_CNT > 0}">
									<tr>
										<td class="left"><fmt:formatNumber type="currency" value="${reserveInfo.rental1W}" pattern="###,###"/>원 X ${reserveInfo.RENTAL1_CNT}명 = </td>
										<td><fmt:formatNumber type="currency" value="${reserveInfo.rental1W * (reserveInfo.RENTAL1_CNT)}" pattern="###,###"/>원</td>
									</tr>
									</c:if>
								</c:if>
								<c:if test="${reserveInfo.RENTAL2_ITEM ne ''}">
									<c:if test="${reserveInfo.RENTAL2_CNT > 0}">
									<tr>
										<td class="left"><fmt:formatNumber type="currency" value="${reserveInfo.rental2W}" pattern="###,###"/>원 X ${reserveInfo.RENTAL2_CNT}명 = </td>
										<td><fmt:formatNumber type="currency" value="${reserveInfo.rental2W * (reserveInfo.RENTAL2_CNT)}" pattern="###,###"/>원</td>
									</tr>
									</c:if>
								</c:if>
								<c:if test="${reserveInfo.RENTAL3_ITEM ne ''}">
									<c:if test="${reserveInfo.RENTAL3_CNT > 0}">
									<tr>
										<td class="left"><fmt:formatNumber type="currency" value="${reserveInfo.rental3W}" pattern="###,###"/>원 X ${reserveInfo.RENTAL3_CNT}명 = </td>
										<td><fmt:formatNumber type="currency" value="${reserveInfo.rental3W * (reserveInfo.RENTAL3_CNT)}" pattern="###,###"/>원</td>
									</tr>
									</c:if>
								</c:if> --%>
							</table>
						</div>
					</div>
				</div>
				<div class="total_payment">
					<table>
						<%-- <tr>
							<th>Grand Total</th>
							<td class="price"><span><fmt:formatNumber type="currency" value="${reserveInfo.PAYMENT_PRICE * 1.1 }" pattern="###,###"/></span>원</td>
						</tr>
						<tr>
							<th>Online pays 10% off</th>
							<td class="discount"><span><fmt:formatNumber type="currency" value="${reserveInfo.PAYMENT_PRICE}" pattern="###,###"/></span>원</td>
						</tr> --%>
						<tr>
							<th>Grand Total</th>
							<td class="discount">= <span><fmt:formatNumber type="currency" value="${reserveInfo.PAYMENT_PRICE}" pattern="###,###"/></span>원</td>
						</tr>
					</table>
				</div>
				<div class="canceled_payment">
					<div class="tit">환불금액정보</div>
					<div class="tb_res">
						<table>
							<tr>
								<th>취소일</th>
								<td>${reserveInfo.CANCEL_DATE}</td>
							</tr>
							<tr>
								<th>취소금액</th>
								<td><fmt:formatNumber type="currency" value="${reserveInfo.PAYMENT_PRICE}" pattern="###,###"/>원</td>
							</tr>
							<tr>
								<th>위약금</th>
								<td><fmt:formatNumber type="currency" value="${reserveInfo.PANALTY_PRICE}" pattern="###,###"/>원</td>
							</tr>
							<tr class="refund_price">
								<th>환불금액</th>
								<td><fmt:formatNumber type="currency" value="${reserveInfo.REFUND}" pattern="###,###"/>원</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
        	</div>
    	</section>
    </div>
    <div class="resTicketDetailBg"></div>
</div>
<form name="cancelForm" id="cancelForm" method="post" onsubmit="return false;">
	<input type="hidden" name="authty" value="${pgResultInfo.authty}">
	<input type="hidden" name="storeid" value="${pgResultInfo.pgStore}">
	<input type="hidden" name="canc_type" value="${reserveInfo.cancelGubun}">
	<input type="hidden" name="trno" value="${pgResultInfo.TR_NO}">
	<input type="hidden" name="canc_amt" value="${reserveInfo.cancelAmount}">
	<input type="hidden" name="penalty_amt" value="${reserveInfo.penalty}">
	<input type="hidden" name="uid" value="${reserveInfo.RESERVE_UID}">
	<%-- <input type="hidden" name="userName" value="${reserveInfo.PAYMENT_NM}"> --%>
	<%-- <input type="hidden" name="email" value="${reserveInfo.MEM_ID}"> --%>
	<input type="hidden" name="goodName" value="${reserveInfo.ORDER_NM}">
	<%-- <input type="hidden" name="phoneNo" value="${reserveInfo.MEM_MOBILE}"> --%>
	<input type="hidden" name="reserveday" value="${reserveInfo.RESERVE_DATE}">
	<input type="hidden" name="ordernum" value="${reserveInfo.ORDER_NUM}">
	<input type="hidden" name="canc_seq" value="${reserveInfo.CANCEL_SEQ}">
	<input type="hidden" name="spa_ad_Man" value="${reserveInfo.SPA_ADULT_MAN }">
	<input type="hidden" name="spa_ad_Women" value="${reserveInfo.SPA_ADULT_WOMEN }">
	<input type="hidden" name="spa_ch_Man" value="${reserveInfo.SPA_CHILD_MAN }">
	<input type="hidden" name="spa_ch_Women" value="${reserveInfo.SPA_CHILD_WOMEN }">
	<input type="hidden" name="water_ad_Man" value="${reserveInfo.WATER_ADULT_MAN }">
	<input type="hidden" name="water_ad_Women" value="${reserveInfo.WATER_ADULT_WOMEN }">
	<input type="hidden" name="water_ch_Man" value="${reserveInfo.WATER_CHILD_MAN }">
	<input type="hidden" name="water_ch_Women" value="${reserveInfo.WATER_CHILD_WOMEN }">
	<input type="hidden" name="complex_ad_Man" value="${reserveInfo.COMPLEX_ADULT_MAN }">
	<input type="hidden" name="complex_ad_Women" value="${reserveInfo.COMPLEX_ADULT_WOMEN }">
	<input type="hidden" name="complex_ch_Man" value="${reserveInfo.COMPLEX_CHILD_MAN }">
	<input type="hidden" name="complex_ch_Women" value="${reserveInfo.COMPLEX_CHILD_WOMEN }">
	<input type="hidden" name="rental01" value="${reserveInfo.RENTAL1_CNT }">
	<input type="hidden" name="rental02" value="${reserveInfo.RENTAL2_CNT }">
	<input type="hidden" name="rental03" value="${reserveInfo.RENTAL3_CNT }">
	<input type="hidden" name="event01" value="${reserveInfo.EVENT1_CNT }">
	<input type="hidden" name="event02" value="${reserveInfo.EVENT2_CNT }">
	<input type="hidden" name="event03" value="${reserveInfo.EVENT3_CNT }">
	<input type="hidden" name="sumVisitCnt" value="${reserveInfo.ADULT_SUM + reserveInfo.CHILD_SUM}">
	<input type="hidden" name="reserve_state" value="CANCEL">
	<input type="hidden" name="pointNm" value="${reserveInfo.POINT_NM}">
	<input type="hidden" name="pointCode" value="${reserveInfo.POINT_CODE}">
</form>
<script language="javascript">
	function CashreceiptView(tr_no)
	{
	    receiptWin = "https://pgims.ksnet.co.kr/pg_infoc/src/bill/ps2.jsp?s_pg_deal_numb="+tr_no;
	    window.open(receiptWin , "" , "scrollbars=no,width=434,height=580");
	}
	function receiptView(tr_no)
	{
		receiptWin = "https://pgims.ksnet.co.kr/pg_infoc/src/bill/credit_view.jsp?tr_no="+tr_no;
	    window.open(receiptWin , "" , "scrollbars=no,width=434,height=700");
	}

	function smsSend(){
		var formData = $("#cancelForm").serialize().replace(/%/g,'%25');
	    	$.ajax({
		   		async: false
		   		,type: "post"
		   		,url : '/mypage/sendSms.af'
		   		,data : formData
		   		,dataType : "html"
		   		,success: function(obj){
					alert(obj);
					location.reload();
		   		}
		   		,error: function(xhr, option, error){
		   			alert("에러가 발생했습니다. 잠시 후에 다시하세요.");
		   		}
	   });
	}

	function reserveCancel(obj){

//alert('111111');

		   var goUrl ='';
		   var payType = '${pgResultInfo.r_TYPE}';
		   if(payType ==='카드'){
			   //실서버
			   goUrl = '/reserve/card_cancel.af';
			   //개발서버 테스트
			   //goUrl = '/reserve/card_cancel_test.af';
		   }else if(payType === '실시간계좌이체'){
			   goUrl = '/reserve/cash_cancel.af';
		   }else if(payType === 'SSG PAY'){
			   goUrl = '/reserve/ssg_cancel.af';
		   }

//alert(goUrl);
//return;

		   var text = '';
		   if(obj != '0'){
			   text = '당일취소는 위약금 10%가 발생합니다.\n 정말로 취소하시겠습니까?';
			   $("input[name='reserve_state']").val('FCANCEL');
		   }else{
			   text = '정말로 취소하시겠습니까?';
		   }

		   if(confirm(text)){
		    	var formData = $("#cancelForm").serialize().replace(/%/g,'%25');
		    	$.ajax({
			   		async: false
			   		,type: "post"
			   		,url : goUrl
			   		,data : formData
			   		,dataType : "html"
			   		,success: function(obj){
						alert(obj);
						location.reload();
			   		}
			   		,error: function(xhr, option, error){
						console.log(xhr.responseText);
			   			alert("에러가 발생했습니다. 잠시 후에 다시하세요.");
			   		}
		   		});
		   }
	}

	function goPrint(title){
	     var sw=screen.width;
	     var sh=screen.height;
	     var w=400;//팝업창 가로길이
	     var h=600;//세로길이
	     var xpos=(sw-w)/2; //화면에 띄울 위치
	     var ypos=(sh-h)/2; //중앙에 띄웁니다.

	     var pHeader="<html id='printArea'><head><link rel='stylesheet' type='text/css' href='/common/front/css/common.css'/><link rel='stylesheet' type='text/css' href='/common/front/css/content.css'/><link rel='stylesheet' type='text/css' href='/common/front/css/respond.css'/><title>"
	                 + title + "</title></head><body><div id='reservation'><div id='resTicketDetail'>";
	     var pgetContent=document.getElementById("printarea").outerHTML + "<br>";
	     //innerHTML을 이용하여 Div로 묶어준 부분을 가져옵니다.
	     var pFooter="</div></div></body></html>";
	     pContent=pHeader + pgetContent + pFooter;

	     pWin=window.open("","print","width=" + w +",height="+ h +",top=" + ypos + ",left="+ xpos +",status=yes,scrollbars=yes"); //동적인 새창을 띄웁니다.
	     pWin.document.open(); //팝업창 오픈
	     pWin.document.write(pContent); //새롭게 만든 html소스를 씁니다.
	     pWin.document.close(); //클로즈
	     setTimeout(function() {
	     	pWin.focus();
	     	pWin.print(); //윈도우 인쇄 창 띄우고
	     	//pWin.close(); //인쇄가 되던가 취소가 되면 팝업창을 닫습니다.
	     }, 500);
	}
</script>
