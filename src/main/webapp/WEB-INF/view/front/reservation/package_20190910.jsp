<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="../../common/taglibs.jsp" %><!-- <button class="btn_close">닫기</button> --> <div id="reservation_package" class="in inc_abs_foot_btn">     <c:choose>    	<c:when test="${!empty prodInfos}">		   		   	    	<form method="post" onsubmit="return false;" class="myform">		     <section class="form_type_res">		        <div class="limit_count_box total" data-limit="10">		             <img class="ico" src="/common/front/images/ico/ico_res_limit.png"/>최대 예약가능 인원 <span>10</span>명		         </div>			      <c:forEach items="${prodInfos}" var="prodInfo" varStatus="status">			         <c:if test="${prodInfo.ITEM_OPTION eq 'PRD001'}"> <c:set value="spa" var="type"/></c:if>			         <c:if test="${prodInfo.ITEM_OPTION eq 'PRD002'}"> <c:set value="water" var="type"/></c:if>			         <c:if test="${prodInfo.ITEM_OPTION eq 'PRD003'}"> <c:set value="complex" var="type"/></c:if>         			         			         <div class="item">			         	<div class="title_line">			            	<h3>${status.count}.${prodInfo.ITEM_NM}</h3>			            </div> 			            <ul class="complex" data-limit="${prodInfo.QUANTITY}">			                 <li data-code="${type}1" data-title="${prodInfo.ITEM_NM}" data-age="(대인)" data-uid="${prodInfo.SET_UID}">			                     <input type="hidden" name="price" class="price" value="${prodInfo.ADULTS_PRICE}">			                     <span class="item_txt">대 인 : </span>			                     <span>			                     	<label>남</label>			                     	<div class="custom-number-container">			                     				                     		<input type="number" name="${type}man1" class="iptNum man">			                     	</div>			                     </span>			                     <span>			                     	<label>여</label>			                     	<div class="custom-number-container">			                     		<input type="number" name="${type}woman1" class="iptNum woman">			                     	</div>			                     </span>			                     <span style="text-align:left;text-indent: 32px;float: left; font-size:13px; color:#888;">중학생 이상</span><div class="itmeTotal"></div>			                 </li>			                 <c:choose>			                 	<c:when test="${prodInfo.CHILD_CHEK eq 'Y'}">			                 		<li> 			                 			<span class="tit"></span>*소인은 입장이 불가한 패키지입니다.			                 		</li>			                 	</c:when>			                 	<c:otherwise>			                 		<li data-code="${type}2" data-title="${prodInfo.ITEM_NM}" data-age="(소인)" data-uid="${prodInfo.SET_UID}">					                     <input type="hidden" id="price" name="price" class="price" value="${prodInfo.CHILD_PRICE}">					                     <span class="item_txt">소 인 : </span>					                     <span>					                     	<label>남</label>					                     	<div class="custom-number-container">					                     		<input type="number" name="${type}man2" class="iptNum man">					                     	</div>					                     </span>					                     <span>					                     	<label>여</label>					                     	<div class="custom-number-container">					                     		<input type="number" name="${type}woman2" class="iptNum woman">					                     	</div>					                     </span>					                    <span style="text-align:left;text-indent: 32px;float: left; font-size:13px; color:#888;">36개월 ~ 초등학생</span><div class="itmeTotal"></div>					                    <input type="hidden" id="totalPrice" value=""/>					                 </li>			                 	</c:otherwise>			                 </c:choose>			             </ul>			         </div>		  		</c:forEach>		       		     </section>		     </form>		     <!-- <footer class="btns center">		         <section class="form_type_res_total">		             <span class="tit">합계</span>		             <span class="price">0원</span>		         </section>		         <button onclick="reservationPop.setResData('item','.item li');" class="btn_pack wide btn_mo blue"><span>확인</span> </button>		     </footer> -->		     		     <script type="text/javascript">		     		         reservationPop2.getResData('item','.item li');		         $(".iptNum").customNumber();		         $(".iptNum").change(function(event) {		             calculation(this);		             reservationPop2.setResData('item','.item li');		         });		         		         calculation();		         		     </script>    	</c:when>    	<c:otherwise>	    <header class="hd_type2">	        <h2>준비된 상품이 없습니다.</h2>	    </header>      	    	</c:otherwise>    </c:choose>         </div>