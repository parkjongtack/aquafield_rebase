<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="../../common/taglibs.jsp" %><section id="contents">	<div class="contents_bx_type1">		<div class="contents_bx_inner">			<div id="path" class="fixed">				<div class="pathInner">					<h2>상품관리</h2>				</div>			</div>			<div class="tb_type1 yearCalendar">				<table>				 	<thead>						<th colspan="4">							<strong><span id="yearCalendar-year">2016</span>년</strong>							<button class="prevYears"><img src="/common/admin/images/common/btn_prevpage.png" alt="이전해" title="이전해"></button>							<button class="nextYears"><img src="/common/admin/images/common/btn_nextpage.png" alt="다음해" title="다음해"></button>						</th>					</thead>					<tbody>						<tr>						<c:forEach begin="1" end="12" varStatus="num" step="1" >						    <c:if test="${num.index <= 9}">						    	<c:set var="month" value="0${num.index}"/>						    </c:if>						    <c:if test="${num.index > 9}">						    	<c:set var="month" value="${num.index}"/>						    </c:if>						    							<td>								<div class="monthBox empty" id="${num.index}">									<div class="boxHeader">										<div class="monthTxt"><span>${num.index}</span>월</div>										<div class="tit">EMPTY</div>									</div>									<div class="boxBody">										<ul>										<c:forEach items="${cateList}" var="cateList">											<li>											    <c:if test="${cateList.CATE_NM eq '입장상품'}">									    			<a href="javascript:gotoUrl('${num.index}','${month}','${cateList.CATE_CODE}','입장상품');">											    </c:if>											    <c:if test="${cateList.CATE_NM eq '패키지'}">													<a href="javascript:gotoUrl('${num.index}','${month}','${cateList.CATE_CODE}','패키지');">											    											    </c:if>												    <c:if test="${cateList.CATE_NM eq '대여상품'}">													<a href="javascript:gotoUrl('${num.index}','${month}','${cateList.CATE_CODE}','대여상품');">											    											    </c:if>												    <c:if test="${cateList.CATE_NM eq '이벤트'}">													<a href="javascript:gotoUrl('${num.index}','${month}','${cateList.CATE_CODE}','이벤트');">											    											    </c:if>												    											    										    													<div class="prdName">${cateList.CATE_NM}</div>													<div class="stat empty" id="${cateList.CATE_CODE}${month}">EMPTY</div>													</a>													<div class="select_pop">														<div class="inner">															<a href="#" class="close_pop">X</a>															<ul>																<li><a href="#">이벤트1</a></li>																<li><a href="#">이벤트2</a></li>																<li><a href="#">이벤트3</a></li>															</ul>															<div class="add_prd"><a href="#">상품등록</a></div>														</div>																											</div>																								</li>																			</c:forEach>										</ul>									</div>								</div>							</td>						<c:if test="${(num.index mod 4) eq 0 }">						</tr>						<tr>						</c:if>						</c:forEach>						</tr>					</tbody>				</table>			</div>		</div>	</div></section><script type="text/javascript">$( document ).ready(function(){	if($("#year").val() != ""){		$("#yearCalendar-year").text($("#year").val());				}	ajaxProdlist();});//다음해$( '.nextYears' ).on( 'click', function(e) {	e.preventDefault();	var yearCalendarYear = parseInt($("#yearCalendar-year").text());	$("#yearCalendar-year").text(yearCalendarYear+1);	ajaxProdlist();} );//이전해$( '.prevYears' ).on( 'click', function(e) {	e.preventDefault();	var yearCalendarYear = parseInt($("#yearCalendar-year").text());	$("#yearCalendar-year").text(yearCalendarYear-1);	ajaxProdlist();} );var url ="";var status = "";function ajaxProdlist(){	$.ajax({	    url: "/admin/product/ajaxProdlist.af",	    type: "POST",	    dataType:"json",	    data: {"nowYear":$("#yearCalendar-year").text()},	    success: function(data) {	    		        $.each(data, function(key){                   var nowYear = data[key].NOWYEAR;                   var nowMonth = data[key].NOWMONTH;                   var cateCode = data[key].CATE_CODE;                   var itemStatus = data[key].ITEM_STATUS;                   var idval = cateCode + nowMonth;				var classtext = $("#"+idval).text();                                      $("#"+idval).text(itemStatus);                   $("#"+idval).removeClass(classtext.toLowerCase());                   $("#"+idval).addClass(itemStatus.toLowerCase()); 	        });		        	        for(var i=1; i<13;i++){	        	var subStatus = $("#"+i+" > .boxBody").find(".stat").text();	        		        	if(subStatus.indexOf("OPEN") > -1){	        			        		if($("#"+i).hasClass("empty"))$("#"+i).removeClass("empty");	        			        		if($("#"+i).hasClass("close"))$("#"+i).removeClass("close");	        		if(!$("#"+i).hasClass("open"))$("#"+i).addClass("open");	        		$("#"+i+" > .boxHeader").find(".tit").text("OPEN");	        			        	}else if(subStatus.indexOf("CLOSE") > -1){	        			        		if($("#"+i).hasClass("empty"))$("#"+i).removeClass("empty");	        		if($("#"+i).hasClass("open"))$("#"+i).removeClass("open");		        			        		if(!$("#"+i).hasClass("close"))$("#"+i).addClass("close");	        		$("#"+i+" > .boxHeader").find(".tit").text("CLOSE");		        	}else if(subStatus.indexOf("EMPTY") > -1){	        			        		if($("#"+i).hasClass("open"))$("#"+i).removeClass("open");		        			        		if($("#"+i).hasClass("close"))$("#"+i).removeClass("close");	        		if(!$("#"+i).hasClass("empty"))$("#"+i).addClass("empty");	        		$("#"+i+" > .boxHeader").find(".tit").text("EMPTY"); 	        			        	}		        		        }	       	        	    },	});};function gotoUrl(num,month,code,prodtype){	var nowYear= $("#yearCalendar-year").text();	var status = $("#"+code+month).text();	var url ="";	if(status == 'EMPTY'){		if(prodtype =='입장상품'){url = '/admin/product/prod_enter.af';}		if(prodtype =='패키지'){url = '/admin/product/prod_package.af';}		if(prodtype =='대여상품'){url = '/admin/product/prod_rental.af';}		if(prodtype =='이벤트'){url = '/admin/product/prod_event.af';}		}else if(status == 'OPEN' || status == 'CLOSE'){		if(prodtype =='입장상품'){url = '/admin/product/modProd_enter.af';}		if(prodtype =='패키지'){url = '/admin/product/modProd_package.af';}		if(prodtype =='대여상품'){url = '/admin/product/modProd_rental.af';}		if(prodtype =='이벤트'){url = '/admin/product/modProd_event.af';}	}	$("#year").val(nowYear);	$("#month").val(month);	$("#code").val(code);	$("#status").val(status);		$("#gotoUrl").attr('action',url);	$("#gotoUrl").submit();		};$('.select_pop_on').on( 'click', function(e) {	e.preventDefault();	$('.select_pop').css('display', 'none');	$(this).parent().find('.select_pop').css('display', 'block');});$('.close_pop').on( 'click', function(e) {	e.preventDefault();	$(this).parents('.select_pop').css('display', 'none');});</script><form name="gotoUrl" id="gotoUrl" method="post">	<input type="hidden" name="year" id="year" value="${year}"/>	<input type="hidden" name="month" id="month" value=""/>	<input type="hidden" name="code" id="code" value=""/>	<input type="hidden" name="status" id="status" value=""/>		</form>