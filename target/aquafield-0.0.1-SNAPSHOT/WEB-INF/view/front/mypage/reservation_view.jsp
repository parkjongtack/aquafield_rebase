<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../common/taglibs.jsp" %>
<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate value="${now }" pattern="yyyyMMdd" var="today" />
<fmt:parseDate value="${reserveInfo.COMPARE_DAY}" var="temp" pattern="yyyyMMdd" />
<fmt:formatDate value="${temp}" pattern="yyyyMMdd" var="compare_day"/> 
<section>
	<div class="iscContent">
        <div class="inner">
        	<article class="row">
				<div class="col s4 ticketArea">
					<div class="resTicket" id="printarea">
						<div class="inner">
							<ul>
								<li class="fronts">
									<div class="tickettop">
										<img src="/common/front/images/mypage/ticket_header_logo.png">
									</div>
									<div class="codenum">
										<div class="tit">아쿠아필드 <span>${reserveInfo.POINT_CODE}점</span> 입장권</div>
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
									</div>
								</li>
							</ul>
						</div>
						<div class="border">
							<img class="topleft" src="/common/front/images/mypage/border_topleft.png">
							<img class="topright" src="/common/front/images/mypage/border_topright.png">
							<img class="bottomleft" src="/common/front/images/mypage/border_bottomleft.png">
							<img class="bottomright" src="/common/front/images/mypage/border_bottomright.png">
						</div>
					</div>
					<ul class="btns row">
					    <c:if test="${reserveInfo.RESERVE_STATE eq 'ING' && compare_day >= today }">
			        	<li class="col s6">
			        		<button class="btn_pack btn_mo white" onclick="reserveCancel('${reserveInfo.cancelGubun}');">예약취소</button>
			        	</li>
			        	</c:if>
			        	<li class="col s6">
			        		<button class="btn_pack btn_mo white" onclick="alert('준비중입니다.');">SMS 발송</button>
			        	</li>
			        	<li class="col s6">
			        		<button class="btn_pack btn_mo white" onclick="goPrint('입장권');">프린트</button>
			        	</li>
			        </ul>
			        <p>※ SMS발송은 결제 후, 1회만 수동발송 가능합니다.</p>
				</div>
				<div class="col s4 res_info">
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
							<c:if test="${reserveInfo.EVENT1_ITEM ne ''}">
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
						   </c:if>						   						   														
						</table>
					</div>
				</div>
				<div class="col s4 res_info">
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
										<button class="btn_pack btn_mo white card" onclick="javascript:receiptView('${pgResultInfo.TR_NO}');">카드영수증</button>									
									</c:if>
									<c:if test="${pgResultInfo.r_TYPE eq '계좌이체'}">
										<button class="btn_pack btn_mo white card" onclick="javascript:CashreceiptView('${pgResultInfo.TR_NO}');">현금영수증</button>									
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
									<td><fmt:formatNumber type="currency" value="${reserveInfo.intSpaAdultW}" pattern="###,###"/>원 X ${reserveInfo.SPA_ADULT_MAN + reserveInfo.SPA_ADULT_WOMEN}명 = <fmt:formatNumber type="currency" value="${reserveInfo.intSpaAdultW * (reserveInfo.SPA_ADULT_MAN + reserveInfo.SPA_ADULT_WOMEN)}" pattern="###,###"/>원</td>
								</tr>
								</c:if>
								<c:if test="${(reserveInfo.SPA_CHILD_MAN + reserveInfo.SPA_CHILD_WOMEN) > 0}">
								<tr>
									<td><fmt:formatNumber type="currency" value="${reserveInfo.intSpaChildW}" pattern="###,###"/>원 X ${reserveInfo.SPA_CHILD_MAN + reserveInfo.SPA_CHILD_WOMEN}명 = <fmt:formatNumber type="currency" value="${reserveInfo.intSpaChildW * (reserveInfo.SPA_CHILD_MAN + reserveInfo.SPA_CHILD_WOMEN)}" pattern="###,###"/>원</td>
								</tr>
								</c:if>																
							</c:if>
							<c:if test="${reserveInfo.WATER_ITEM ne ''}">
								<c:if test="${(reserveInfo.WATER_ADULT_MAN + reserveInfo.WATER_ADULT_WOMEN) > 0}">
								<tr>
									<td><fmt:formatNumber type="currency" value="${reserveInfo.intWaterAdultW}" pattern="###,###"/>원 X ${reserveInfo.WATER_ADULT_MAN + reserveInfo.WATER_ADULT_WOMEN}명 = <fmt:formatNumber type="currency" value="${reserveInfo.intWaterAdultW * (reserveInfo.WATER_ADULT_MAN + reserveInfo.WATER_ADULT_WOMEN)}" pattern="###,###"/>원</td>
								</tr>
								</c:if>
								<c:if test="${(reserveInfo.WATER_CHILD_MAN + reserveInfo.WATER_CHILD_WOMEN) > 0}">
								<tr>
									<td><fmt:formatNumber type="currency" value="${reserveInfo.intWaterChildW}" pattern="###,###"/>원 X ${reserveInfo.WATER_CHILD_MAN + reserveInfo.WATER_CHILD_WOMEN}명 = <fmt:formatNumber type="currency" value="${reserveInfo.intWaterChildW * (reserveInfo.WATER_CHILD_MAN + reserveInfo.WATER_CHILD_WOMEN)}" pattern="###,###"/>원</td>
								</tr>
								</c:if>																
							</c:if>
							<c:if test="${reserveInfo.EVENT1_ITEM ne ''}">
								<c:if test="${reserveInfo.EVENT1_CNT > 0}">
								<tr>
									<td><fmt:formatNumber type="currency" value="${reserveInfo.event1W}" pattern="###,###"/>원 X ${reserveInfo.EVENT1_CNT}명 = <fmt:formatNumber type="currency" value="${reserveInfo.event1W * (reserveInfo.EVENT1_CNT)}" pattern="###,###"/>원</td>
								</tr>
								</c:if>															
							</c:if>	
							<c:if test="${reserveInfo.EVENT2_ITEM ne ''}">
								<c:if test="${reserveInfo.EVENT2_CNT > 0}">
								<tr>
									<td><fmt:formatNumber type="currency" value="${reserveInfo.event2W}" pattern="###,###"/>원 X ${reserveInfo.EVENT2_CNT}명 = <fmt:formatNumber type="currency" value="${reserveInfo.event2W * (reserveInfo.EVENT2_CNT)}" pattern="###,###"/>원</td>
								</tr>
								</c:if>															
							</c:if>	
							<c:if test="${reserveInfo.EVENT3_ITEM ne ''}">
								<c:if test="${reserveInfo.EVENT3_CNT > 0}">
								<tr>
									<td><fmt:formatNumber type="currency" value="${reserveInfo.event3W}" pattern="###,###"/>원 X ${reserveInfo.EVENT3_CNT}명 = <fmt:formatNumber type="currency" value="${reserveInfo.event3W * (reserveInfo.EVENT3_CNT)}" pattern="###,###"/>원</td>
								</tr>
								</c:if>															
							</c:if>
							<c:if test="${reserveInfo.RENTAL1_ITEM ne ''}">
								<c:if test="${reserveInfo.RENTAL1_CNT > 0}">
								<tr>
									<td><fmt:formatNumber type="currency" value="${reserveInfo.rental1W}" pattern="###,###"/>원 X ${reserveInfo.RENTAL1_CNT}명 = <fmt:formatNumber type="currency" value="${reserveInfo.rental1W * (reserveInfo.RENTAL1_CNT)}" pattern="###,###"/>원</td>
								</tr>
								</c:if>															
							</c:if>	
							<c:if test="${reserveInfo.RENTAL2_ITEM ne ''}">
								<c:if test="${reserveInfo.RENTAL2_CNT > 0}">
								<tr>
									<td><fmt:formatNumber type="currency" value="${reserveInfo.rental2W}" pattern="###,###"/>원 X ${reserveInfo.RENTAL2_CNT}명 = <fmt:formatNumber type="currency" value="${reserveInfo.rental2W * (reserveInfo.RENTAL2_CNT)}" pattern="###,###"/>원</td>
								</tr>
								</c:if>															
							</c:if>	
							<c:if test="${reserveInfo.RENTAL3_ITEM ne ''}">
								<c:if test="${reserveInfo.RENTAL3_CNT > 0}">
								<tr>
									<td><fmt:formatNumber type="currency" value="${reserveInfo.rental3W}" pattern="###,###"/>원 X ${reserveInfo.RENTAL3_CNT}명 = <fmt:formatNumber type="currency" value="${reserveInfo.rental3W * (reserveInfo.RENTAL3_CNT)}" pattern="###,###"/>원</td>
								</tr>
								</c:if>															
							</c:if>																																										
						</table>
					</div>
					<div class="total_payment">
						<table>
							<tr>
								<th>Grand Total</th>
								<td class="price">= <span><fmt:formatNumber type="currency" value="${reserveInfo.PAYMENT_PRICE * 1.1 }" pattern="###,###"/></span>원</td>
							</tr>
							<tr>
								<th>Online pays 10% off</th>
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
									<td>2017.09.20</td>
								</tr>
								<tr>
									<th>취소금액</th>
									<td>235,000원</td>
								</tr>
								<tr>
									<th>위약금</th>
									<td>- 0 원</td>
								</tr>
								<tr class="refund_price">
									<th>환불금액</th>
									<td>235,000원</td>
								</tr>
							</table>
						</div>
					</div>
				</div>
        	</article>
        </div>
    </div>
</section>
<form name="cancelForm" id="cancelForm" method="post" onsubmit="return false;">
	<input type="hidden" name="authty" value="${pgResultInfo.authty}">
	<input type="hidden" name="storeid" value="${pgResultInfo.pgStore}">
	<input type="hidden" name="canc_type" value="${reserveInfo.cancelGubun}">
	<input type="hidden" name=trno value="${pgResultInfo.TR_NO}">
	<input type="hidden" name="canc_amt" value="${reserveInfo.cancelAmount}">
	<input type="hidden" name="uid" value="${reserveInfo.RESERVE_UID}">	
	<input type="hidden" name="userName" value="${reserveInfo.PAYMENT_NM}">
	<input type="hidden" name="email" value="${reserveInfo.MEM_ID}">
	<input type="hidden" name=goodName value="${reserveInfo.ORDER_NM}">
	<input type="hidden" name="phoneNo" value="${reserveInfo.MEM_MOBILE}">	
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
	
	function reserveCancel(obj){
		   var text = '';
		   if(obj != '0'){
			   text = '당일취소는 위약금 10%가 발생합니다.\n 정말로 취소하시겠습니까?';
		   }else{
			   text = '정말로 취소하시겠습니까?';
		   }
		   
		   if(confirm(text)){ 
		    	var formData = $("#cancelForm").serialize().replace(/%/g,'%25');
		    	$.ajax({
			   		async: false
			   		,type: "post"
			   		,url : '/reserve/cancel.af'
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
	}
	
	function goPrint(title){
	     var sw=screen.width;
	     var sh=screen.height;
	     var w=800;//팝업창 가로길이
	     var h=600;//세로길이
	     var xpos=(sw-w)/2; //화면에 띄울 위치
	     var ypos=(sh-h)/2; //중앙에 띄웁니다.
	 
	     var pHeader="<html><head><link rel='stylesheet' type='text/css' href='/common/front/css/common.css'/><link rel='stylesheet' type='text/css' href='/common/front/css/content.css'/><title>" 
	                 + title + "</title></head><body><div id='reservation'><div id='resTicketDetail'>";
	     var pgetContent=document.getElementById("printarea").outerHTML + "<br>";
	     //innerHTML을 이용하여 Div로 묶어준 부분을 가져옵니다.
	     var pFooter="</div></div></body></html>";
	     pContent=pHeader + pgetContent + pFooter;  

	     pWin=window.open("","print","width=" + w +",height="+ h +",top=" + ypos + ",left="+ xpos +",status=yes,scrollbars=yes"); //동적인 새창을 띄웁니다.
	     pWin.document.open(); //팝업창 오픈
	     pWin.document.write(pContent); //새롭게 만든 html소스를 씁니다.
	     pWin.document.close(); //클로즈
	     //pWin.print(); //윈도우 인쇄 창 띄우고
	     //pWin.close(); //인쇄가 되던가 취소가 되면 팝업창을 닫습니다.
	    }	
</script>
