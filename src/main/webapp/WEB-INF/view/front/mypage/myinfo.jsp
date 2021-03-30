<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../common/taglibs.jsp" %>
    <section id="myinfo">
        <div class="iscContent">
        	<div class="inner">
		        <div class="row">
		        	<div class="tb_type1 col s12 mb30">
						<table>
							<colgroup>
								<col width="20%">
								<col width="80%">
							</colgroup>
							<tbody>
								<tr>
									<th class="left">회원번호</th>
									<td class="left" colspan="3">${memberInfo.MEM_NUM}</td>
								</tr>
								<tr>
									<th class="left">이	름</th>
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
									<th class="left">성	별</th>
									<td class="left"><c:if test="${memberInfo.MEM_GENDER eq 'M'}">남</c:if><c:if test="${memberInfo.MEM_GENDER eq 'W'}">여</c:if></td>
								</tr>
								<tr>
									<th class="left">주	소</th>
									<td class="left">
										${memberInfo.HOME_ADDR1}<br/>
										${memberInfo.HOME_ADDR2}
									</td>
								</tr>
								<tr>
									<th class="left">마케팅 동의</th>
									<td class="left">
								    <c:choose>
									    <c:when test="${memberInfo.RECEIVED_INFO_AT eq 'Y' }">
									    	수신
									    </c:when>
									    <c:otherwise>
									    	비수신
									    </c:otherwise>
								    </c:choose>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
		        </div>
		        <div class="btns center">
		        	<button class="btn_pack btn_mo brown" onclick="contentBox.showCont({url : '/mypage/myinfo_write.af', move:'next'})">정보수정</button>
		        	<button class="btn_pack btn_mo white" onclick="contentBox.showCont({url : '/mypage/pw_change.af', move:'next'})">비밀번호 변경</button>
				    <c:choose>
				    <c:when test="${leave eq 'NO' }">
						<button class="btn_pack btn_mo white" onclick="alert('예약정보가 남아있습니다.\n 먼저 예약을 취소해주세요.');">회원탈퇴</button>
				    </c:when>
				    <c:otherwise>
				    	<button class="btn_pack btn_mo white" onclick="contentBox.showCont({url : '/mypage/leave.af', move:'next', data : {mem_uid: '${memberInfo.MEM_UID}'}})">회원탈퇴</button>
				    </c:otherwise>
				    </c:choose>
		        </div>
			</div>
		</div>
    </section>