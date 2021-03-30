<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../common/taglibs.jsp" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<tiles:importAttribute name="resultsLeftMenu" />
<section id="area_left_menu">
	<ul class="lnb_depth2">
<c:forEach items="${resultsLeftMenu}" var="result">
		<li>
			<a href="${result.MENU_URL}" onclick="lnb2onoff(this);">
				<span class="menu_tit">${result.MENU_NM}</span>
				<span class="right_arrow align_all_mid"><span class="h100"></span><img src="/common/admin/images/common/ico_menu_arrow2.gif"></span>
			</a>
		</li>
</c:forEach>
	</ul>
</section>		