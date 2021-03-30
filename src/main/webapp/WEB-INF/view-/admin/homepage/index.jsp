<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="../../common/taglibs.jsp" %>
<section id="contents"><div class="contents_bx_type1">		<div class="contents_bx_inner">			<div id="path" class="fixed">				<div class="pathInner">					<h2>약관관리</h2>					<div class="btns_area">						<a href="#write" class="btn_pack btn_mo d_gray" onclick="goWrite();return false;"><img src="/admaf/images/common/ico_add.png">등록</a>					</div>				</div>			</div>			<form name="searchForm" id="searchForm" method="post" action="/secu_admaf/admin/homepage/index.af">			<input type="hidden" name="page" value="${resultParam.page}">			<input type="hidden" name="tt" value="${resultParam.tt}">			<input type="hidden" name="num" value=""> 			<div class="tabMenu">	 			<ul class="clearfix"><c:forEach items="${resultsType}" var="resultSub">	 				<li<c:if test="${resultSub.CODE_ID eq resultParam.tt}"> class="on"</c:if>><a href="/secu_admaf/admin/homepage/index.af?tt=${resultSub.CODE_ID}">${resultSub.CODE_NM}</a></li></c:forEach>	 			</ul>	 		</div>			</form>			<div class="tb_type1">				<h3>약관 목록</h3>				<table>				 	<colgroup> 						<col width="60%/"> 						<col width="20%/"> 						<col width="20%/"> 					</colgroup>				 	<thead>				 		<tr>							<th>개정이력</th>							<th>등록자</th>							<th>등록일</th>						</tr>				 	</thead>					<tbody><c:set var="pageUncount" value="${pu.totalRowCount - ((pu.pageNum - 1) *  pu.pageListSize)}"/><c:forEach items="${results}" var="result">						<tr>							<td><a href="/secu_admaf/admin/homepage/terms_write.af" class="link" onclick="goEdit(${result.TERMS_UID});return false;">${result.TERMS_TITLE}</a></td>							<td>${result.INS_ADMIN_ID}</td>							<td>${result.INS_DATE_TXT}</td>						</tr><c:set var="pageUncount" value="${pageUncount - 1 }" /></c:forEach><c:if test="${empty results}"> 						<tr>							<td colspan="6">내용이 없습니다.</td>						</tr> </c:if>					</tbody>				</table>			</div>		</div>	</div></section><script type="text/javascript">function goPage(page) {	var thisForm				= document.searchForm;	thisForm.action="/secu_admaf/admin/homepage/index.af";	thisForm.page.value		= page;	thisForm.submit();}function goEdit(num) {	var thisForm				= document.searchForm;	thisForm.action="/secu_admaf/admin/homepage/terms_write.af";	thisForm.num.value		= num;	thisForm.submit();}function goWrite() {	var thisForm				= document.searchForm;	thisForm.action="/secu_admaf/admin/homepage/terms_write.af";	thisForm.submit();}</script>