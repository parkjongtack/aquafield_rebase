<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../common/taglibs.jsp" %>
<section id="contents">
	<div class="contents_bx_type1">
		<div class="contents_bx_inner">
			<div id="path" class="fixed">
				<div class="pathInner">
					<h2>운영관리자</h2>
					<div class="btns_area">
						<a href="#write" class="btn_pack btn_mo d_gray" onclick="goWrite();return false;"><img src="/admaf/images/common/ico_add.png">생성</a>
					</div>
				</div>
			</div>
			<form name="searchForm" id="searchForm" method="post" action="/secu_admaf/admin/manager/manager_list.af">
			<input type="hidden" name="page" value="${resultParam.page}">
			<input type="hidden" name="num" value=""> 
			<div class="tb_type2">
				<h3>조건검색</h3>
				<table>
					<tbody>
						<tr>
							<th>이	름</th>
							<td><input type="text" name="nm" class="ipt2 w100p" value="${resultParam.nm}" /></td>
							<th>등록일</th>
							<td><input type="text" name="sd" class="ipt2 datepicker" value="${resultParam.sd}" /> ~ <input type="text" name="ed" class="ipt2 datepicker" value="${resultParam.ed}" /></td>
						</tr>
						<tr>
							<th colspan="4">
								<div class="tb_btn_area center">
									<button class="btn_pack btn_mo d_gray">Search</button>
								</div>
							</th>
						</tr>
					</tbody>
				</table>
			</div>
			</form>
			<div class="tb_type1">
				<h3>검색결과</h3>
				<div class="result_summary">Total : <strong>${totalCount}</strong></div>
				<table>
					<colgroup>
						<col width="10%">
						<col width="15%">
						<col width="30%">
						<col width="15%">
						<col width="15%">
						<col width="10%">
					</colgroup>
				 	<thead>
				 		<tr>
							<th>이	름</th>
							<th>부	서</th>
							<th>E-Mail</th>
							<th>등록일</th>
							<th>최종접속일</th>
							<th>관	리</th>
						</tr>
				 	</thead>
					<tbody>
<c:set var="pageUncount" value="${pu.totalRowCount - ((pu.pageNum - 1) *  pu.pageListSize)}"/>
<c:forEach items="${results}" var="result">
						<tr>
							<td>${result.ADMIN_NM}</td>
							<td class="left">${result.ADMIN_DEPT}</td>
							<td class="left">${result.ADMIN_ID}</td>
							<td>${result.INS_DATE_TXT}</td>
							<td>${result.LOGIN_DATE_TXT}</td>
							<td><a href="#${result.ADMIN_UID}" class="link" onclick="goDetail(${result.ADMIN_UID});return false;">보기</a></td>
						</tr>
<c:set var="pageUncount" value="${pageUncount - 1 }" />
</c:forEach>
<c:if test="${empty results}">
 						<tr>
							<td colspan="6">내용이 없습니다.</td>
						</tr>
 </c:if>
					</tbody>
				</table>
			</div>
			<div class="paginate">
<c:if test="${pu.blockStartNum > 1 }">
				<a href="#${pu.blockStartNum - 1 }" class="btn prev disable" onclick="goPage(${pu.blockStartNum - 1 });return false;">PREV</a>
</c:if>
<c:forEach var="i" begin="${pu.blockStartNum}" end="${pu.blockEndNum }">
	<c:choose>
		<c:when test="${i == pu.pageNum }">
				<a href="#${i}" class="num on" onclick="goPage(${i});return false;">${i}</a>
		</c:when>
		<c:otherwise>
				<a href="#${i}" class="num" onclick="goPage(${i});return false;">${i}</a>
		</c:otherwise>
	</c:choose>
</c:forEach>
<c:if test="${pu.blockEndNum < pu.pageTotalCount }">
				<a href="#${pu.blockEndNum + 1 }" class="btn next" onclick="goPage(${pu.blockEndNum + 1 });return false;">NEXT</a>
</c:if>
			</div>
		</div>
	</div>
</section>
<script type="text/javascript">
function goPage(page) {
	var thisForm				= document.searchForm;
	thisForm.action="/secu_admaf/admin/manager/manager_list.af";
	thisForm.page.value		= page;
	thisForm.submit();
}

function goDetail(num) {
	var thisForm				= document.searchForm;
	thisForm.action="/secu_admaf/admin/manager/operations_manager_view.af";
	thisForm.num.value		= num;
	thisForm.submit();
}

function goWrite() {
	var thisForm				= document.searchForm;
	thisForm.action="/secu_admaf/admin/manager/operations_manager_write.af";
	thisForm.submit();
}
</script>
