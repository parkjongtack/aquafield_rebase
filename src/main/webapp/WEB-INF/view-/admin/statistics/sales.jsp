<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="../../common/taglibs.jsp" %><section id="contents">	<div class="contents_bx_type1">		<div class="contents_bx_inner">			<div id="path" class="fixed">				<div class="pathInner">					<h2>매출속보</h2>					<div class="btns_area">						<a href="javascript:void(0);" class="btn_pack btn_mo d_gray" onclick="print();">프린트</a>					</div>				</div>			</div>			<div  id="printArea">				<div class="tb_type1">					<div class="tb_header">						<div class="date">							<div class="inner">								<form action="/secu_admaf/admin/statistics/print.af" name="frm" id="frm" method="post">									<label>영업일:</label>									<input type="text" name="srch_date_s" value="" class="ipt2 datepicker" /> ~ <input type="text" name="srch_date_e" value="" class="ipt2 datepicker" onchange="search();"/>									<div class="lst_check radio noprint">										<span class="on">											<label>												금	일<input type="radio" name="date" id="today" value="today" onclick="checkradio(this);" checked="">											</label>										</span>										<span>											<label>												1주일<input type="radio" name="date" id="week" value="week" onclick="checkradio(this);">											</label>										</span>										<span>											<label>												한	달<input type="radio" name="date" id="month" value="month" onclick="checkradio(this);">											</label>										</span>									</div>								</form>							</div>						</div>						<div class="payment tb_type1">							<table>								<colgroup>							 	 	<col width="10%/">							 	 	<col width="30%/">							 	 	<col width="30%/">							 	 	<col width="30%/">							 	</colgroup>								<tbody>									<tr>										<th rowspan="2">결<br/><br/><br/><br/>재</th>										<th>담	당</th>										<th>팀	장</th>										<th>상무이사</th>									</tr>									<tr>										<td></td>										<td></td>										<td></td>									</tr>								</tbody>								<tr></tr>							</table>						</div>					</div>					<table style="width: 100%; margin-left:0">					 	<colgroup>					 	 	<col width="22%/">					 	 	<col width="16%/">					 	 	<col width="12%/">					 	 	<col width="25%/">					 	 	<col width="25%/">					 	</colgroup>						<tbody id="saleView">						</tbody>					</table>				</div>			</div>		</div>	</div>	<script type="text/javascript">		$(function(){			var today = new Date();			var week = new Date();			var month = new Date();			var startDate = today, endDate = today;			$("input[name='srch_date_s']").datepicker('setDate', startDate);			$("input[name='srch_date_e']").datepicker('setDate', endDate);			search();							$("input[name='date']").change(function(v) {				var setToday = new Date();				var setWeek = new Date();				var setMonth = new Date();								var setStartDate ="";				var setEndDate = "";				switch($(this).val()) {				    case 'today':				    	setStartDate = setToday;				    	setEndDate = setToday;				        break;				    case 'week':				    	setWeek.setDate(setWeek.getDate()-7);				    	setStartDate = setWeek;				    	setEndDate = setToday;				        break;				    case 'month':				    	setMonth.setDate(setMonth.getDate()-30);				    	setStartDate = setMonth;				    	setEndDate = setToday;				        break;				}														$("input[name='srch_date_s']").datepicker('setDate', setStartDate);				$("input[name='srch_date_e']").datepicker('setDate', setEndDate);								search();			});			//시작일-종료일 유효성			$("input[name='srch_date_s']").datepicker("option", "maxDate", $("input[name='srch_date_e']").val());		    $("input[name='srch_date_s']").datepicker("option", "onClose", function ( selectedDate ) {		        $("input[name='srch_date_e']").datepicker( "option", "minDate", selectedDate );		    });		 		    $("input[name='srch_date_e']").datepicker("option", "minDate", $("input[name='srch_date_s']").val());		    $("input[name='srch_date_e']").datepicker("option", "onClose", function ( selectedDate ) {		        $("input[name='srch_date_s']").datepicker( "option", "maxDate", selectedDate );		    });		});				function printPage(){			 var initBody;			 window.onbeforeprint = function(){				  initBody = document.body.innerHTML;				  document.body.innerHTML =  document.getElementById('printArea').innerHTML;			 };			 window.onafterprint = function(){			  	document.body.innerHTML = initBody;			 };			 //console.log(document.body.innerHTML);			 window.print();			 return false;		};		function print() {			var printArea = document.getElementById('printArea').innerHTML;			var win = window.open(); 			self.focus(); 			win.document.open();							win.document.write('<html><head><title>온라인예약 매출속보</title><link rel="stylesheet" type="text/css" href="/admaf/css/common.css"><link rel="stylesheet" type="text/css" href="/admaf/css/content.css">');			win.document.write('</haed><body id="print">');			win.document.write(printArea);		 	win.document.write('</body></html>');		 	$(win.document).find("input[name='srch_date_s']").val($("input[name='srch_date_s']").val());		 	$(win.document).find("input[name='srch_date_e']").val($("input[name='srch_date_e']").val());			win.document.close();			setTimeout(function() {				win.print();				win.close();			}, 0);						$('#frm').submit();					};				function search(){			$.ajax({			    url: "/secu_admaf/admin/statistics/ajaxSales.af",			    type: "POST",			    dataType:"json",			    data: {"srch_date_s":$("input[name='srch_date_s']").val(), "srch_date_e":$("input[name='srch_date_e']").val(), "date":$(':radio[name="date"]:checked').val()},			    success: function(data) {										var saleHtml = "";			    	var saleHtml01 = "<tr><th rowspan=\"2\" colspan=\"3\">구	분</th>";					var saleHtml02 = "";								saleHtml01 += "<th colspan=\"2\">누	계</th></tr>";					saleHtml01 += "<tr><th>예약인원</th><th>매출액</th></tr>";			    	var itemPriceTotal = 0; //가격 총합계 			    	var personTotal = 0; // 예약인원 총합계			    	var enterPackagePriceSum = 0; //찜질스파,워터파크,패키지 상품 가격 총합계 			    	var enterPackageCnt = 0;//찜질스파,워터파크,패키지 상품 예약인원 총합계			    	var rentalEventPriceSum = 0; //대여상품,이벤트 상품 가격 총합계			    	var rentalEventCnt = 0; //대여상품,이벤트 상품 예약인원 총합계														if(data.length > 0){  								    	var rentalCnt = 1;				    	var eventCnt = 1;				        $.each(data, function(key){				        	var cateCode = data[key].ITEM_CODE;				        	if(parseInt(cateCode) <= 20000000){				        		saleHtml01 += "<tr><th rowspan=\"4\">"+data[key].ITEM_NM+"</th>";				        		saleHtml01 += "<td rowspan=\"2\">대	인</td><td>남</td>";				        		saleHtml01 += "<td class=\"right\">"+data[key].AD_MAN_CNT_SUM+"</td><td class=\"right price\">"+data[key].AD_MAN_TOTAL+"</td></tr>";				        		saleHtml01 += "<tr><td>여</td><td class=\"right\">"+data[key].AD_WOMEN_CNT_SUM+"</td><td class=\"right price\">"+data[key].AD_WOMEN_TOTAL+"</td></tr>";				        		saleHtml01 += "<tr><td rowspan=\"2\">소	인</td><td>남</td><td class=\"right\">"+data[key].CH_MAN_CNT_SUM+"</td><td class=\"right price\">"+data[key].CH_MAN_TOTAL+"</td></tr>";				        		saleHtml01 += "<tr><td>여</td><td class=\"right\">"+data[key].CH_WOMEN_CNT_SUM+"</td><td class=\"right price\">"+data[key].CH_WOMEN_TOTAL+"</td></tr>";				        						        		enterPackagePriceSum += parseInt(data[key].AD_MAN_TOTAL) + parseInt(data[key].AD_WOMEN_TOTAL) + parseInt(data[key].CH_MAN_TOTAL) + parseInt(data[key].CH_WOMEN_TOTAL);				        		enterPackageCnt += parseInt(data[key].AD_MAN_CNT_SUM) + parseInt(data[key].AD_WOMEN_CNT_SUM) + parseInt(data[key].CH_MAN_CNT_SUM) + parseInt(data[key].CH_WOMEN_CNT_SUM);				        	}else{								if(cateCode === '30000000'){									if(rentalCnt == 1){										saleHtml02 += "<tr><th rowspan=\"3\">대여상품</th>";									}else{										saleHtml02 += "<tr>";									}																		saleHtml02 += "<td colspan=\"3\">"+data[key].ITEM_NM+"</td>";									saleHtml02 += "<td class=\"right\">"+data[key].ITEM_CNT_SUM+"</td>";									saleHtml02 += "<td class=\"right price\">"+data[key].ITEM_TOTAL+"</td></tr>";																		rentalCnt ++;								}								if(cateCode === '40000000'){									if(eventCnt == 1){										saleHtml02 += "<tr><th rowspan=\"3\">이벤트</th>";									}else{										saleHtml02 += "<tr>";									}																		saleHtml02 += "<td colspan=\"3\">"+data[key].ITEM_NM+"</td>";									saleHtml02 += "<td class=\"right\">"+data[key].ITEM_CNT_SUM+"</td>";									saleHtml02 += "<td class=\"right price\">"+data[key].ITEM_TOTAL+"</td></tr>";																		rentalCnt ++;								}																rentalEventPriceSum += parseInt(data[key].ITEM_TOTAL);								rentalEventCnt += parseInt(data[key].ITEM_CNT_SUM);				        	}				        });				         				      	//합계는 이벤트+대여상품 이므로 주석						//saleHtml01 += "<tr>";						//saleHtml01 += "<th colspan=\"3\">합	계</th>";						//saleHtml01 += "<td class=\"right price\" style=\"font-weight: bold;\">"+enterPackageCnt+"</td>";						//saleHtml01 += "<td class=\"right price\" style=\"font-weight: bold;\">"+enterPackagePriceSum+"</td></tr>";				        				        						if(saleHtml02 != ""){							//합계는 이벤트+대여상품 이므로 주석							//saleHtml02 += "<tr>";							//saleHtml02 += "<tr><th colspan=\"3\">합	계</th>";							//saleHtml02 += "<td class=\"right price\" style=\"font-weight: bold;\">"+rentalEventCnt+"</td>";							//saleHtml02 += "<td class=\"right price\" style=\"font-weight: bold;\">"+rentalEventPriceSum+"</td></tr>";						}				        				        						itemPriceTotal = enterPackagePriceSum + rentalEventPriceSum;						personTotal = enterPackageCnt + rentalEventCnt;						saleHtml02 += "<tr><th colspan=\"3\">총합계</th>";						saleHtml02 += "<th class=\"right price\">"+personTotal+"</th>";						saleHtml02 += "<th class=\"right price\">"+itemPriceTotal+"</th></tr>";				        					}else{						saleHtml01 += "<tr><td colspan=\"5\">검색된 매출내역이 없습니다.</td></tr>";											}					saleHtml = saleHtml01 + saleHtml02;			        $("#saleView").empty();			        $("#saleView").append(saleHtml);			        			      //가격 콤마 추가				    $(".price").each(function(){				    	$(this).text($(this).text().replace(/\B(?=(\d{3})+(?!\d))/g, ","));				    });			    }			});		};	</script></section>