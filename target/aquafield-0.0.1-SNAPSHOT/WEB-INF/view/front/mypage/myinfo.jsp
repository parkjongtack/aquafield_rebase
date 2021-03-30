<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../common/taglibs.jsp" %>
    <section id="myinfo">
        <div class="iscContent">
        	<div class="inner">
        		<article>
			        <div class="row">
			        	<div class="tb_type2 col s12 mb20" style="max-width: 725px; margin: 0 auto 20px auto; float: none;">
							<table>
								<colgroup>
									<col width="30%">
									<col width="70%">
								</colgroup>				
								<tbody>
									<tr>
										<th class="left">회원번호</th>
										<td class="left" colspan="3">${memberInfo.MEM_UID}</td>
									</tr>
									<tr>
										<th class="left">이름</th>
										<td class="left" colspan="3">${memberInfo.MEM_NM}</td>
									</tr>
									<tr>
										<th class="left">회원아이디</th>
										<td class="left" colspan="3">${memberInfo.MEM_ID}</td>
									</tr>
									<tr>
										<th class="left">휴대폰 번호</th>
										<td class="left" colspan="3">
										    <c:choose>
										    <c:when test="${fn:length(memberInfo.MOBILE_NUM) > 10 }">
										    	${fn:substring(memberInfo.MOBILE_NUM,0,3)}-${fn:substring(memberInfo.MOBILE_NUM,3,7)}-${fn:substring(memberInfo.MOBILE_NUM,7,11)}
										    </c:when>
										    <c:otherwise>
										    	${fn:substring(memberInfo.MOBILE_NUM,0,3)}-${fn:substring(memberInfo.MOBILE_NUM,3,6)}-${fn:substring(memberInfo.MOBILE_NUM,6,10)}
										    </c:otherwise>							    
										    </c:choose>	
										</td>
									</tr>
									<tr>
										<th class="left">생년월일</th>
										<td class="left">${memberInfo.MEM_BIRTH} <c:if test="${memberInfo.BIRTH_TYPE eq 'Y'}">(양력)</c:if><c:if test="${memberInfo.BIRTH_TYPE eq 'N'}">(음력)</c:if></td>
									</tr>
									<tr>
										<th class="left">성별</th>
										<td class="left"><c:if test="${memberInfo.MEM_GENDER eq 'M'}">남</c:if><c:if test="${memberInfo.MEM_GENDER eq 'W'}">여</c:if></td>
									</tr>
									<tr>
										<th class="left">주소</th>
										<td class="left" colspan="3">${memberInfo.HOME_ADDR1} ${memberInfo.HOME_ADDR2}</td>
									</tr>
								</tbody>
							</table>
						</div>
			        </div>
			        <div class="btns center">
			        	<button class="btn_pack btn_mo blue" onclick="contentBox.showCont({url : '/mypage/myinfo_write.af', move:'next'})">수정</button>
			        </div>
			    </article>
			</div>
		</div>
    </section>