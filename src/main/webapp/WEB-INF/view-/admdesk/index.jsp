<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../common/taglibs.jsp" %>
<section id="main">
	<div class="out_box">
		<section id="contents">
			<div class="contents_bx_type1">
				<div class="contents_bx_inner">
					<div id="path" class="fixed">
						<div class="pathInner">
							<h2>Dashboard</h2>
							<div class="btns_area">
							<a href="javascript:void(0);" class="btn_pack btn_mo d_gray" onclick="window.location.reload();"><img src="/admaf/images/common/ico_reflesh.png"></a>
						</div>
						</div>
					</div>
					<form name="serach_form" method="post" action="/secu_admaf/admdesk/index.af" >
					<div class="area_date">
						<input type="text" name="search_date" class="ipt2 datepicker2" value="" maxlength="10" readonly=""  onchange="search();"/>

						<script type="text/javascript">
							$(".datepicker2").datepicker({
								showOn: "both",
								buttonImage: "/admaf/images/common/btn_calen_s2.png",
								buttonImageOnly: true,
								buttonText: "Select date",
								dateFormat: "yy-mm-dd",
							});
							$(".datepicker2").datepicker('setDate', '${resultParam.search_date}');
						</script>
					</div>
					</form>
					<div class="area_grid">
						<ul class="left_area">
							<li class="grid_s1">
								<div class="inner">
									<div class="tit">방문예약</div>
									<div class="count"><strong>${ing}</strong>명</div>
								</div>
							</li>
							<li class="grid_s1">
								<div class="inner">
									<div class="tit">입장고객</div>
									<div class="count"><strong>${use}</strong>명</div>
								</div>
							</li>
							<li class="grid_table">
								<div class="tit">이벤트 신청 고객</div>
								<table>
								<c:choose>
									<c:when test="${cntList != null && cntList != '' && cnt.ITEM_CODE eq '40000000'}">
										<c:forEach items="${cntList}" var="cnt">
											<tr>
												<td class="tit">${cnt.ITEM_NM}</td>
												<td class="count">${cnt.TOTAL_CNT}명</td>
											</tr>								    
										</c:forEach>									
									</c:when>
									<c:otherwise>
											<tr>
												<td class="tit" colspan="2">검색된 내용이 없습니다.</td>
											</tr>										
									</c:otherwise>
								</c:choose>
								</table>
							</li>
						</ul>
						<ul class="right_area">
						  <c:forEach items="${resultList}" var="result">
							<c:if test="${result.SUBCODE eq 'PRD001'}">		
								<li class="grid_s2">
									<div class="inner">
										<div class="tit">${result.ITEMNAME}</div>
										<div class="count"><strong>${result.CNT}</strong>명</div>
									</div>
								</li>							    																
							</c:if>						
							<c:if test="${result.SUBCODE eq 'PRD002'}">		
								<li class="grid_s2">
									<div class="inner">
										<div class="tit">${result.ITEMNAME}</div>
										<div class="count"><strong>${result.CNT}</strong>명</div>
									</div>
								</li>						    																
							</c:if>								
							<c:if test="${result.SUBCODE eq 'PRD003'}">		
								<li class="grid_s2">
									<div class="inner">
										<div class="tit">${result.ITEMNAME}</div>
										<div class="count"><strong>${result.CNT}</strong>명</div>
									</div>
								</li>							    																
							</c:if>
							<c:if test="${result.SUBCODE eq 'PRD004'}">		
								<li class="grid_s2">
									<div class="inner">
										<div class="tit">${result.ITEMNAME}</div>
										<div class="count"><strong>${result.CNT}</strong>명</div>
									</div>
								</li>						    																
							</c:if>	
							<c:if test="${result.SUBCODE eq 'PRD005'}">		
								<li class="grid_s2">
									<div class="inner">
										<div class="tit">${result.ITEMNAME}</div>
										<div class="count"><strong>${result.CNT}</strong>명</div>
									</div>
								</li>						    																
							</c:if>																
						  </c:forEach>
						</ul>
					</div>
				</div>
			</div>
		</section>
	</div>
</section><!-- login -->
<script type="text/javascript">
	function search(){
		document.serach_form.submit();
	}
</script>
