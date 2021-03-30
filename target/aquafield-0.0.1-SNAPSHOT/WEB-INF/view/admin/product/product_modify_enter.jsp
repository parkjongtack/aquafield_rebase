<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../common/taglibs.jsp" %>
<section id="contents">
	<div class="contents_bx_type1">
		<div class="contents_bx_inner">
			<div id="path" class="fixed">
				<div class="pathInner">
					<h2>상품관리</h2>
					<div class="btns_area">
						<button class="btn_pack btn_mo d_gray" onclick="modProdItem('upd');"><img src="/common/admin/images/common/ico_save.png">수정</button>
						<button class="btn_pack btn_mo d_gray" onclick="modProdItem('open');"><img src="/common/admin/images/common/ico_save.png">OPEN</button>						
						<a href="javascript: gotoUrl('/admin/product/prodlist.af');" class="btn_pack btn_mo gray"><img src="/common/admin/images/common/ico_list.png">목록</a>
					</div>
				</div>
			</div>
			<div class="tb_type2">
				<h3>기본정보</h3>
				<table>
					<tbody>
						<tr>
							<th>카테고리</th>
							<td>입장상품</td>
							<th>상품명</th>
							<td>스파 / 워터파크</td>
						</tr>
						<tr>
							<th>상태</th>
							<td colspan="3" id="status">
								<span class="status">${paramater.status}</span>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<form id="itemSet" name="itemSet" method="post">
			<input type="hidden" name="itemyear" id="itemyear" value="${paramater.year}"/>
			<input type="hidden" name="itemmonth" id="itemmonth" value="${paramater.month}"/>			
			<input type="hidden" name="itemName01" value="스파"/>	
			<input type="hidden" name="itemName02" value="워터파크"/>
			<input type="hidden" name="mode" id="mode" value=""/>				          	
			<div class="tb_type1 mb20">
				<h3>가격정보 설정</h3>
				<table id="dddd">
					<colgroup>
						<col width="10%" />
						<col width="10%" />
						<col/>
					</colgroup>
					<tbody>
						<tr class="spaWeekday">
							<th rowspan="2">스파</th>
							<td>주중</td>
							<td class="tit">
								<label>대인가: </label>
								<input type="number" name="" class="ipt2 w140" placeholder="숫자만 입력하세요"/><span class="unitTxt">원</span>
								<label>소인가: </label>
								<input type="number" name="" class="ipt2 w140" placeholder="숫자만 입력하세요"/><span class="unitTxt">원</span>
								<label>지정수량: </label>
								<input type="number" name="" class="ipt2 w140" placeholder="숫자만 입력하세요"/><span class="unitTxt">개</span>
							</td>
						</tr>
						<tr class="spaWeekend">
							<td>주말</td>
							<td class="tit">
								<label>대인가: </label>
								<input type="number" name="" class="ipt2 w140" placeholder="숫자만 입력하세요"/><span class="unitTxt">원</span>
								<label>소인가: </label>
								<input type="number" name="" class="ipt2 w140" placeholder="숫자만 입력하세요"/><span class="unitTxt">원</span>
								<label>지정수량: </label>
								<input type="number" name="" class="ipt2 w140" placeholder="숫자만 입력하세요"/><span class="unitTxt">개</span>
							</td>
						</tr>
						<tr>
							<th rowspan="4">워터파크</th>
							<td>성수기 지정</td>
							<td class="tit">
								<select name="season" class="customized-select selectSeason">
								<c:forEach items="${seasonList}" var="seasons" varStatus="status">
									<option value="${seasonList[status.index].CODE_ID}">${seasonList[status.index].CODE_NM}</option>								
								</c:forEach>								
								</select>
								<input type="" name="" class="ipt2 datepicker" id="startDay" value="${paramater.year}.${paramater.month}.01"/> ~ <input type="" name="" class="ipt2 datepicker" id="endDay" value="${paramater.year}.${paramater.month}.01"/> 
							</td>
						</tr>
						<tr class="normal_season">
							<td>평수기</th>
							<td class="tit">
								<label>대인가: </label>
								<input type="number" name="" class="ipt2 w140" placeholder="숫자만 입력하세요"/><span class="unitTxt">원</span>
								<label>소인가: </label>
								<input type="number" name="" class="ipt2 w140" placeholder="숫자만 입력하세요"/><span class="unitTxt">원</span>
								<label>지정수량: </label>
								<input type="number" name="" class="ipt2 w140" placeholder="숫자만 입력하세요"/><span class="unitTxt">개</span>
							</td>
						</tr>
						<tr class="halfpeak_season">
							<td>준성수기</td>
							<td class="tit">
								<label>대인가: </label>
								<input type="number" name="" class="ipt2 w140" placeholder="숫자만 입력하세요"/><span class="unitTxt">원</span>
								<label>소인가: </label>
								<input type="number" name="" class="ipt2 w140" placeholder="숫자만 입력하세요"/><span class="unitTxt">원</span>
								<label>지정수량: </label>
								<input type="number" name="" class="ipt2 w140" placeholder="숫자만 입력하세요"/><span class="unitTxt">개</span>
							</td>
						</tr>
						<tr class="peak_season">
							<td>성수기</td>
							<td class="tit">
								<label>대인가: </label>
								<input type="number" name="" class="ipt2 w140" placeholder="숫자만 입력하세요"/><span class="unitTxt">원</span>
								<label>소인가: </label>
								<input type="number" name="" class="ipt2 w140" placeholder="숫자만 입력하세요"/><span class="unitTxt">원</span>
								<label>지정수량: </label>
								<input type="number" name="" class="ipt2 w140" placeholder="숫자만 입력하세요"/><span class="unitTxt">개</span>
							</td>
						</tr>
						<tr>
							<th colspan="3">
								<div class="tb_btn_area">
									<button class="btn_pack btn_mo white setPrice">적용</button>
									<button class="btn_noborder blue_nb setReset">초기화</button>
								</div>
							</th>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="tb_type1 calendar">
				<table id="calendar" class="fc-calendar-container">
					<thead>
						<th colspan="8">
							<strong><span id="custom-year">2016</span>년 <span id="custom-month">9</span>월</strong>
						</th>
					</thead>
					<tbody class="dump">
						<tr>
							<th>
								<div class="date"></div>
								<div class="cate">
									<div class="txt">스파</div>
									<ul class="list">
										<li>대인가</li>
										<li>소인가</li>										
										<li>지정수량</li>
									</ul>
								</div>
								<div class="cate">
									<div class="txt">워터<br/>파크</div>
									<ul class="list">
										<li>대인가</li>
										<li>소인가</li>
										<li>지정수량</li>
										<li class="season"></li>
									</ul>
								</div>
							</th>
							<td>
								<div class="date">
									<p>일 / 1</p>
								</div>
								<div class="prdItem">
									<div class="cate item1">
										<ul class="list">										    
											<li class="adult"><input type="number" name="spaAdultW" value="" class="ipt2 w100p"/></li>
											<li class="child"><input type="number" name="spaChildW" value="" class="ipt2 w100p"/></li>
											<li class="qty"><input type="number" name="spaQty" value="" class="ipt2 w100p"/></li>
										    <input type="hidden" name="spaDate" value=""/>
											<input type="hidden" name="itemcode" value=""/>
									        <input type="hidden" name="itemSubCode01" value=""/>
									        <input type="hidden" name="spaItemStatus" value=""/>
									        <input type="hidden" name="spaItemUid" value=""/>											
										</ul>
									</div>
									<div class="cate item2">
										<ul class="list">										    
											<li class="adult"><input type="number" name="wpAdultW" value="" class="ipt2 w100p"/></li>
											<li class="child"><input type="number" name="wpChildW" value="" class="ipt2 w100p"/></li>
											<li class="qty"><input type="number" name="wpQty" value="" class="ipt2 w100p"/></li>
											<li class="season"></li>
										    <input type="hidden" name="wpDate" value=""/>
										    <input type="hidden" name="itemSeason" value="NORMAL"/>
									        <input type="hidden" name="itemSubCode02" value=""/>
									        <input type="hidden" name="wpItemStatus" value=""/>
									        <input type="hidden" name="wpItemUid" value=""/>											
										</ul>
									</div>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			</form>
		</div>
	</div>
</section>
<script type="text/javascript">
$( document ).ready(function(){

	var nowYearMonth = '${paramater.year}${paramater.month}';
	for(var i=1;i<=$(".item2").length;i++){
		var day = "";
		if(i<10){
			day = "0"+i;
		}else{
			day = i;
		}	
		var nowDay = nowYearMonth + day;
		$("#"+nowDay).next().find("ul > input").eq(0).val(nowDay);
		$("#"+nowDay).next().next().find("ul > input").eq(0).val(nowDay);
	}
	
	
	
	modItemSet();
});

function modItemSet(){
	
	$.ajax({
	    url: "/admin/product/ajaxModItems.af",
	    type: "POST",
	    dataType:"json",
	    data: {"year":$("#year").val(),"month":$("#month").val(), "code":$("#code").val(), "subcode" : ''},
	    success: function(data) {
	    	
	        $.each(data, function(key){
	        	var itemCode = data[key].ITEM_CODE;
	        	var halfIndex = data.length/2;
	        	if(key < halfIndex){
	        		var itemSelector = $("#"+data[key].YYYYMMDD).next();
	        		itemSelector.find("input[name='spaAdultW']").val(data[key].ADULTS_PRICE);
	        		itemSelector.find("input[name='spaChildW']").val(data[key].CHILD_PRICE);
	        		itemSelector.find("input[name='spaQty']").val(data[key].QUANTITY);
	        		itemSelector.find("input[name='itemcode']").val(data[key].CATE_CODE);
	        		itemSelector.find("input[name='itemSubCode01']").val(data[key].ITEM_CODE);
	        		itemSelector.find("input[name='spaItemStatus']").val(data[key].ITEM_STATUS);
	        		itemSelector.find("input[name='spaItemUid']").val(data[key].SET_UID);
	        		
	        		var itemStatus = itemSelector.find("input[name='spaItemStatus']").val();
	        		if(itemStatus == 'OPEN'){
	        			itemSelector.find("input[name='spaAdultW']").attr("readonly", true);
	        			itemSelector.find("input[name='spaChildW']").attr("readonly", true);
	        			//itemSelector.find("input[name='spaQty']").attr("readonly", true);
	        		}
	        	}else{
	        		var itemSelector = $("#"+data[key].YYYYMMDD).next().next();
	        		itemSelector.find("input[name='wpAdultW']").val(data[key].ADULTS_PRICE);
	        		itemSelector.find("input[name='wpChildW']").val(data[key].CHILD_PRICE);
	        		itemSelector.find("input[name='wpQty']").val(data[key].QUANTITY);
	        		itemSelector.find("input[name='itemSeason']").val(data[key].SEASON_TYPE);
	        		itemSelector.find("input[name='itemSubCode02']").val(data[key].ITEM_CODE);
	        		itemSelector.find("input[name='wpItemStatus']").val(data[key].ITEM_STATUS);
	        		itemSelector.find("input[name='wpItemUid']").val(data[key].SET_UID);	        		
	        		
	        		var itemStatus = itemSelector.find("input[name='wpItemStatus']").val();
	        		var itemSeason = itemSelector.find("input[name='itemSeason']").val();
	        		
	        		if(itemStatus == 'OPEN'){
	        			itemSelector.find("input[name='wpAdultW']").attr("readonly", true);
	        			itemSelector.find("input[name='wpChildW']").attr("readonly", true);
	        			//itemSelector.find("input[name='wpQty']").attr("readonly", true);
	        			
	        			if(itemSeason == 'PEAK'){
		        			itemSelector.find('.season').addClass('on');
		        			itemSelector.find('.season').html('');
		        			itemSelector.find('.season').html('<span class="season_txt peak">성수기</span>');
	        			}else if(itemSeason == 'NONPEAK'){
		        			itemSelector.find('.season').addClass('on');
		        			itemSelector.find('.season').html('');
		        			itemSelector.find('.season').html('<span class="season_txt halfpeak">준성수기</span>');	        				
	        			}
	        			
	        		}else if(itemStatus == 'CLOSE'){
	        			
	        			if(itemSeason == 'PEAK'){
		        			itemSelector.find('.season').addClass('on');
		        			itemSelector.find('.season').html('');
		        			itemSelector.find('.season').html('<span class="season_txt peak">성수기</span><span class="season_close">X</span>');
	        			}else if(itemSeason == 'NONPEAK'){
		        			itemSelector.find('.season').addClass('on');
		        			itemSelector.find('.season').html('');
		        			itemSelector.find('.season').html('<span class="season_txt halfpeak">준성수기</span><span class="season_close">X</span>');	        				
	        			}
	        			
	        		}	        		
	        	}
	        });	
          
	        var allStatus = $("#status").find('span').text();
	        if(allStatus == 'OPEN'){
	        	$(".setPrice").hide();
	        	$(".setReset").hide();
	        	$(".btns_area > button").eq(0).hide();
	        }
	    },
	});	
};

function modProdItem(param){
	$("#mode").val(param);
	$("#itemSet").attr('action','/admin/product/updProd_enter.af');
	$("#itemSet").submit();		
};

function gotoUrl(url){
	$("#gotoUrl").attr('action',url);
	$("#gotoUrl").submit();		
}; 
</script>
<form name="gotoUrl" id="gotoUrl" method="post">
	<input type="hidden" name="year" id="year" value="${paramater.year}"/>
	<input type="hidden" name="month" id="month" value="${paramater.month}"/>
	<input type="hidden" name="code" id="code" value="${paramater.code}"/>						
</form>