<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../common/taglibs.jsp" %>
<c:set var="pageParam" value="col=${resultParam.col}&sw=${resultParam.sw}"/>
<c:set var="pageUncount" value="${pu.totalRowCount - ((pu.pageNum - 1) *  pu.pageListSize)}"/>
<c:forEach items="${results}" var="result">
								    <a href="javascript:void(0)" class="item ${result.KIND_CODE}" onclick="window.newsPop = new NewsPopFn2({data:{num:'${result.NOTICE_EVENT_UID}', type:'${result.KIND_CODE}'}});">
										<div class="imgArea">
											<c:choose>
												<c:when test="${result.DATE_STAT eq 'Y'}"><div class="tagIco"><img src="/images/ico/ico_event_now.png"></div></c:when>
												<c:when test="${result.DATE_STAT eq 'Y'}"><div class="tagIco"><img src="/images/ico/ico_event_end.png"></div></c:when>
											</c:choose>
											<c:if test="${not empty result.FILE_LIST_FULL}">
								    		<img src="${result.FILE_LIST_FULL}">
								    		</c:if>
								    	</div>
										<div class="txtArea">
											<div class="inner">
												<h1 class="title">${result.TITLE}</h1>
												<c:if test="${result.KIND eq '2'}">
												<div class="date">기간 : ${result.START_DATE} ~ ${result.END_DATE}</div>
												<div class="location">장소 : ${result.LOCATION}</div>
												</c:if>
												<div class="tag">${result.KIND_NAME}</div>
												<div class="icon"></div>
											</div>
										</div>
										<input type="hidden" name="" class="pageCount" value="${pageUncount}">
								    </a>
	<c:set var="pageUncount" value="${pageUncount - 1 }" />
</c:forEach>
