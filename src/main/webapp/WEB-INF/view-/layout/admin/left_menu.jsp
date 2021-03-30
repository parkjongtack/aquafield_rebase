<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../common/taglibs.jsp" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<tiles:importAttribute name="resultsLeftMenu" />
<section id="area_left_menu">
	<ul class="lnb_depth2">
		<c:forEach items="${resultsLeftMenu}" var="result">
			<li>
				<%-- <a href="${result.MENU_URL}" <c:if test="${result.MENU_URL eq '/index.af#/event/eventIndex.af?filter=all'}">target="_blank"</c:if> > --%>
				<a href="${result.MENU_URL}" <c:if test="${result.MENU_URL eq '/index.af#/event/eventIndex.af?cate=1'}">target="_blank"</c:if> >
					<span class="menu_tit">${result.MENU_NM}</span>
					<span class="right_arrow align_all_mid"><span class="h100"></span><img src="/admaf/images/common/ico_menu_arrow2.gif"></span>
				</a>
			</li>
		</c:forEach>
	</ul>
</section>		