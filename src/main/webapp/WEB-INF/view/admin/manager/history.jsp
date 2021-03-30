<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../common/taglibs.jsp" %>
<section id="contents">
	<div class="contents_bx_type1">
		<div class="contents_bx_inner">
			<div id="path" class="fixed">
				<div class="pathInner">
					<h2>사용기록</h2>
					<div class="btns_area">
 						<button type="button" class="btn_pack btn_mo green2" onclick="excelDownload();"><img src="/admaf/images/common/ico_excel.png" alt="회원리스트"> Exel</button>
					</div>
				</div>
			</div>
			<form name="serach_form" id="serach_form" action="/secu_admaf/admin/manager/history.af" method="post" onsubmit="retrun false;">
				<input type="hidden" name="page" value="${resultParam.page }">		
				<div class="tb_type2">
					<h3>조건검색</h3>
					<table>
						<tbody>
							<tr>
								<th>관 리 자</th>
								<td><input type="text" name="srch_text" value="${resultParam.srch_text}" class="ipt2 w100p"/></td>
								<th>접근메뉴</th>
								<td>
									<select name="access_menu" id="access_menu" class="customized-select">
										<option value="">전체</option>
										<c:forEach items="${menuList }" var="menu">
											<option value="${menu.MENU_NM }" <c:if test="${resultParam.access_menu == menu.MENU_NM }">selected="selected"</c:if> >${menu.MENU_NM }</option>
										</c:forEach>									
									</select>
								</td>
							</tr>
							<tr>
								<th>행&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;위</th>
								<td>
									<select name="action" id="action" class="customized-select">
										<option value="">전체</option>
										<c:forEach items="${actionList }" var="vo">
											<option value="${vo.CODE_ID }" <c:if test="${resultParam.action == vo.CODE_ID }">selected="selected"</c:if> >${vo.CODE_NM }</option>
										</c:forEach>									
									</select>
								</td>
								<th>일자선택</th>
								<td><input type="" name="srch_reg_s" value="${resultParam.srch_reg_s}" class="ipt2 datepicker"/> ~ <input type="" name="srch_reg_e" value="${resultParam.srch_reg_e}" class="ipt2 datepicker"/></td>
							</tr>
							<tr>
								<th colspan="4">
									<div class="tb_btn_area center">
										<button class="btn_pack btn_mo d_gray" onclick="doSearch();">Search</button>
									</div>
								</th>
							</tr>
						</tbody>
					</table>
				</div>
			</form>
			<div class="tb_type1">
				<h3>검색결과</h3>
				<div class="result_summary">Total : <strong>${totalCount }</strong></div>
				<table>
				 	<colgroup>
						<col width="10%">
						<col width="15%">
						<col width="15%">
						<col width="20%">
						<col width="10%">
						<col width="10%">
						<col width="8%">
						<!-- <col width="15%"> -->
					</colgroup>
				 	<thead>
				 		<tr>
							<th>이	름</th>
							<th>부	서</th>
							<th>ID</th>
							<th>접근메뉴</th>
							<th>행	위</th>
							<!-- <th>자료번호</th> -->
							<th>IP</th>
							<th colspan="2">시	간</th>
						</tr>
				 	</thead>
					<tbody>
						<c:forEach items="${results }" var="vo">					
						<tr>
							<td rowspan="2">${vo.ADMIN_NM}</td>
							<td style="text-align:center;">${vo.ADMIN_DEPT}</td>
							<td class="left">${vo.INS_ADMIN_ID}</td>
							<td>${vo.ACCESS_MENU_NM}</td>
							<td>
								<c:if test="${vo.ACTION eq 'C'}">생성</c:if>
								<c:if test="${vo.ACTION eq 'R'}">열람</c:if>
								<c:if test="${vo.ACTION eq 'U'}">수정</c:if>
								<c:if test="${vo.ACTION eq 'D'}">삭제</c:if>
								<c:if test="${vo.ACTION eq 'E'}">엑셀</c:if>
								<c:if test="${vo.ACTION eq 'P'}">프린트</c:if>
							</td>
							<%-- <td class="right">
								<c:if test="${vo.DATA_URL != null && vo.DATA_URL != ''}">
									<a href="${vo.DATA_URL}" target="_blank">${vo.DATA_NUM}</a>
								</c:if>
								<c:if test="${vo.DATA_URL == null || vo.DATA_URL == ''}">
									${vo.DATA_NUM}								
								</c:if>
							</td> --%>
							<td>${vo.INS_IP}</td>
							<td colspan="2">${vo.INS_DATE}</td>
						</tr>
						<tr>
							<td colspan="7" style="text-align:left;">${vo.DATA_NUM}</td>
						</tr>
						
						</c:forEach>
				 		<c:if test="${empty results }">
				 			<tr>
				 				<td colspan="8"> 조회된 내용이 없습니다. </td>
				 			<tr>
				 		</c:if>						
					</tbody>
				</table>
			</div>
			<div class="paginate">
				<c:if test="${pu.blockStartNum > 1 }">
					<a href="javascript:;" onclick="doPaging('${pu.blockStartNum - 1 }');" class="btn prev disable">PREV</a>
				</c:if>
				<c:forEach var="i" begin="${pu.blockStartNum}" end="${pu.blockEndNum }">
					<a href="javascript:;" onclick="doPaging('${i}');" class="num <c:if test="${i == pu.pageNum }">on</c:if>">${i}</a>
				</c:forEach>
				<c:if test="${pu.blockEndNum < pu.pageTotalCount }">
					<a href="javascript:;" onclick="doPaging('${pu.blockEndNum + 1 }');" class="btn next">NEXT</a>
				</c:if>
			</div>
		</div>
	</div>
</section>
<script type="text/javascript">
//검색
function doSearch(){
	if(!dateValidation("srch_reg_s")) return false;
	if(!dateValidation("srch_reg_e")) return false;
	if(!dateToDate("srch_reg_s", "srch_reg_e")) return false;
	document.serach_form.submit();
};

//엑셀 다운로드
function excelDownload(){
	if(!confirm('다운로드 하시겠습니까?')) return;
	var temp_url = document.serach_form.action;
	$('#serach_form').attr("action", "/secu_admaf/admin/manager/history_exel.af");
	document.serach_form.target="_blank";
	document.serach_form.submit();
	
	// 다운로드 후 form 옵션 초기화
	/* document.serach_form.action = temp_url; */
	$('#serach_form').attr("action", "/secu_admaf/admin/manager/history.af");
	document.serach_form.target="_self";
	//location.href='ajax_pop_no_apply_list_excel.do?scode=2016070407069'
}

//페이징
function doPaging(num){
	$("input[name='page']").val(num);
	doSearch();
};
</script>
