<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../common/taglibs.jsp" %>

<c:choose>
	<c:when test="${empty rList }">
		<tr id="totalArea">
			<td colspan="16"> 조회된 내용이 없습니다. </td>
		</tr>
	</c:when>
	<c:otherwise>
		<c:forEach items="${rList }" var="vo" >
		<tr>
			<td>${vo.RN }</td>
			<td>${vo.POINT_NM }</td>
			<td>
				<c:choose>
					<c:when test="${MEMINFOYN eq 'Y'}">
						${vo.MEM_NM }										
					</c:when>
					<c:otherwise>
						${vo.MASK_MEM_NM }
					</c:otherwise>
				</c:choose>
			</td>
			<td>
				<c:choose>
					<c:when test="${MEMINFOYN eq 'Y'}">
						${vo.MEM_MOBILE }										
					</c:when>
					<c:otherwise>
						${vo.MASK_MEM_MOBILE }
					</c:otherwise>
				</c:choose>
			</td>
			<td>
				<a href="javascript:void(0)" class="link" onclick="showResPanel('${vo.ORDER_NUM}')">
					${vo.ORDER_NUM }
				</a>
			</td>
			<td>${vo.PAYMENT_DATE }</td>
			<td>${vo.RESERVE_DATE }</td>
			<td>${vo.ORDER_NM }</td>
			<td>
				<c:choose>
					<c:when test="${vo.ORDER_TP eq '0' }">
						<c:forEach items="${fn:split(vo.ENTER_ITEM_NM, ',') }" var="item" varStatus="status">
						    ${item}<br/>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<c:forEach items="${fn:split(vo.PACKAGE_ITEM_NM, ',') }" var="item" varStatus="status">
						    ${item}<br/>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</td>
			<c:choose>
				<c:when test="${vo.RESERVE_STATE eq 'ING' }">
					<c:set var="color" value="res" />
				</c:when>
				<c:when test="${vo.RESERVE_STATE eq 'USE' || vo.RESERVE_STATE eq 'NOUSE' }">
					<c:set var="color" value="" />
				</c:when>
				<c:otherwise>
					<c:set var="color" value="cancel" />
				</c:otherwise>
			</c:choose>
			<td class="stat ${color }">${vo.RESERVE_STATE_NM }</td>
			<td>
				${vo.PAYMENT_TYPE_NM }
			</td>
			<td>${vo.TOTNUM }</td>
			<td class="price payment"><fmt:formatNumber value="${vo.PAYMENT_PRICE }" pattern="#,###"/></td>
			<td class="price refund"><fmt:formatNumber value="${vo.REFUND }" pattern="#,###"/></td>
			<td class="price panalty">
				<c:choose>
					<c:when test="${vo.PANALTY_PRICE eq null || vo.PANALTY_PRICE eq ''}">
						0
					</c:when>
					<c:otherwise>
						<fmt:formatNumber value="${vo.PANALTY_PRICE }" pattern="#,###"/>			
					</c:otherwise>
				</c:choose>
			</td>
			<td>
				<c:if test="${vo.RESERVE_STATE ne 'NOPMT'}">
					<a href="#" onclick="goDetail('${vo.ORDER_NUM}'); return false;" class="link">수정</a>
				</c:if>
			</td>
		</tr>
	</c:forEach>
	</c:otherwise>
</c:choose>
<input type="hidden" name="totCnt" value="${totCnt }">
<input type="hidden" name="ajaxPage" value="${page }">
