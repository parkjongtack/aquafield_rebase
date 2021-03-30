<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../common/taglibs.jsp" %>
<section id="contents">
	<div class="contents_bx_type1">
		<div class="contents_bx_inner">
			<div id="path" class="fixed">
				<div class="pathInner">
					<h2>상품관리</h2>
					<div class="btns_area">
						<button class="btn_pack btn_mo d_gray" onclick="setProdItem();"><img src="/admaf/images/common/ico_save.png">OPEN</button>					
						<a href="javascript: gotoUrl('/secu_admaf/admin/product/prodlist.af');" class="btn_pack btn_mo gray"><img src="/admaf/images/common/ico_list.png">목록</a>
					</div>
				</div>
			</div>
			<div class="tb_type2">
				<h3>기본정보</h3>
				<table>
					<colgroup>
						<col width="15%" />
						<col width="40%" />
						<col width="15%" />
						<col width="33%" />
					</colgroup>	
					<tbody>
						<tr>
							<th>카테고리</th>
							<td><b>입장상품</b></td>
							<th>지점</th>
							<td>
								${paramater.pointNm }
							</td>
						</tr>
						<tr>
							<th>상품명</th>
							<td>${itemList[0].ITEM_NM}, ${itemList[1].ITEM_NM}, ${itemList[2].ITEM_NM}</td>
							<th>상태</th>
							<td id="status">
								<span class="status">${paramater.status}</span>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<form id="itemSet" name="itemSet" method="post">
			<input type="text" name="itemyear" id="itemyear" value="${paramater.year}"/>
			<input type="text" name="itemmonth" id="itemmonth" value="${paramater.month}"/>
			<input type="text" name="itemName01" value="${itemList[0].ITEM_NM}"/>	
			<input type="text" name="itemName02" value="${itemList[1].ITEM_NM}"/>
			<input type="text" name="itemName03" value="${itemList[2].ITEM_NM}"/>
			<input type="text" name="pointCode" id="pointCode" value="${paramater.point }"/>	
			<input type="text" name="pointNm" id="pointNm" value="${paramater.pointNm }"/>	          	
			<div class="tb_type1 mb20">
				<h3>가격정보 설정</h3>
				<table>
					<colgroup>
						<col width="10%" />
						<col width="10%" />
						<col/>
					</colgroup>
					<tbody>
						<tr class="spaWeekday">
							<th rowspan="3"><!--${itemList[0].ITEM_NM}-->찜질스파 </th>
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
						<tr class="spaRestday">
							<td>공휴일</td>
							<td class="tit">
								<label>대인가: </label>
								<input type="number" name="" class="ipt2 w140" placeholder="숫자만 입력하세요"/><span class="unitTxt">원</span>
								<label>소인가: </label>
								<input type="number" name="" class="ipt2 w140" placeholder="숫자만 입력하세요"/><span class="unitTxt">원</span>
								<label>지정수량: </label>
								<input type="number" name="" class="ipt2 w140" placeholder="숫자만 입력하세요"/><span class="unitTxt">개</span>
							</td>
						</tr>
						<tr class="wpSeason">
							<th rowspan="4"><!--${itemList[1].ITEM_NM}--> 워터파크</th>
							<td>성수기 지정</td>
							<td class="tit">
								<select name="season" class="customized-select selectSeason">
								<c:forEach items="${seasonList}" var="seasons" varStatus="status">
									<option value="${seasonList[status.index].CODE_ID}">${seasonList[status.index].CODE_NM}</option>								
								</c:forEach>								
								</select>
								<input type="" name="" class="ipt2 datepicker" id="startDay_wp" value="${paramater.year}.${paramater.month}.01"/> ~ <input type="" name="" class="ipt2 datepicker" id="endDay_wp" value="${paramater.year}.${paramater.month}.01"/> 
							</td>
						</tr>
						<tr class="wp_normal_season">
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
						<tr class="wp_halfpeak_season">
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
						<tr class="wp_peak_season">
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
						<!-- 20150525 oktomato start -->
						<tr class="complexSeason">						
							<th rowspan="4"><!--${itemList[2].ITEM_NM}-->복합권</th>
							<td>성수기 지정</td>
							<td class="tit">
								<select name="season" class="customized-select selectSeason">
								<c:forEach items="${seasonList}" var="seasons" varStatus="status">
									<option value="${seasonList[status.index].CODE_ID}">${seasonList[status.index].CODE_NM}</option>								
								</c:forEach>								
								</select>
								<input type="" name="" class="ipt2 datepicker" id="startDay_complex" value="${paramater.year}.${paramater.month}.01"/> ~ <input type="" name="" class="ipt2 datepicker" id="endDay_complex" value="${paramater.year}.${paramater.month}.01"/> 
							</td>
						</tr>
						<tr class="complex_normal_season">
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
						<tr class="complex_halfpeak_season">
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
						<tr class="complex_peak_season">
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
						<!-- 20150525 oktomato end -->
						<tr>
							<th colspan="3">
								<div class="tb_btn_area">
									<button class="btn_pack btn_mo white setPrice" data-value="wp">적용</button>
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
									<div class="txt">찜질<br/>스파</div>
									<ul class="list">
										<li>대인가</li>
										<li>소인가</li>										
										<li>지정수량</li>
									</ul>
								</div>
								<div class="cate">
									<c:choose>
										<c:when test="${paramater.point eq 'POINT03'}">
											<div class="txt">루프<br/>탑풀</div>
										</c:when>
										<c:otherwise>
											<div class="txt">워터<br/>파크</div>	
										</c:otherwise>
									</c:choose>
									<ul class="list">
										<li>대인가</li>
										<li>소인가</li>
										<li>지정수량</li>
										<li class="season wp"></li>
									</ul>
								</div>
								<div class="cate">
									<div class="txt">복합<br/>권</div>
									<ul class="list">
										<li>대인가</li>
										<li>소인가</li>
										<li>지정수량</li>
										<li class="season complex"></li>									
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
											<input type="hidden" name="itemcode" value="${paramater.code}"/>
									        <input type="hidden" name="itemSubCode01" value="${itemList[0].ITEM_CODE}"/>											
										</ul>
									</div>
									<div class="cate item2">
										<ul class="list">										    
											<li class="adult"><input type="number" name="wpAdultW" value="" class="ipt2 w100p"/></li>
											<li class="child"><input type="number" name="wpChildW" value="" class="ipt2 w100p"/></li>
											<li class="qty"><input type="number" name="wpQty" value="" class="ipt2 w100p"/></li>
											<li class="season wp"></li>
										    <input type="hidden" name="wpDate" value=""/>
										    <input type="hidden" name="wpItemSeason" value="NORMAL"/>
									        <input type="hidden" name="itemSubCode02" value="${itemList[1].ITEM_CODE}"/>											
										</ul>
									</div>
									<div class="cate item3">
										<ul class="list">		
											<li class="adult"><input type="number" name="complexAdultW" value="" class="ipt2 w100p"/></li>
											<li class="child"><input type="number" name="complexChildW" value="" class="ipt2 w100p"/></li>
											<li class="qty"><input type="number" name="complexQty" value="" class="ipt2 w100p"/></li>										
										    <li class="season complex"></li>
										    <input type="hidden" name="complexDate" value=""/>
										    <input type="hidden" name="complexItemSeason" value="NORMAL"/>										    
									        <input type="hidden" name="itemSubCode03" value="${itemList[2].ITEM_CODE}"/>	
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
		$("#"+nowDay).next().next().next().find("ul > input").eq(0).val(nowDay);
	}
});

function setProdItem(){
	$("#itemOption").val($("#selItemOption option:selected").val());
	$("#itemSet").attr('action','/secu_admaf/admin/product/setprod_enter.af');
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
	<input type="hidden" name="srch_point" id="srch_point" value="${paramater.point}"/>
</form>