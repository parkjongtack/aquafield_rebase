<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="../../common/taglibs.jsp" %>
<c:set var="pageParam" value="nm=${resultParam.nm}&sd=${resultParam.sd}&ed=${resultParam.ed}&sw=${resultParam.sw}"/><section id="contents">	<div class="contents_bx_type1">
	<div class="contents_bx_inner">			<form>			<div id="path" class="fixed">				<div class="pathInner">					<h2>운영관리자</h2>					<div class="btns_area">						<a href="/admin/manager/operations_manager_write.af?${pageParam}&page=${resultParam.page}&num=${result.ADMIN_UID}" class="btn_pack btn_mo d_gray"><img src="/common/admin/images/common/ico_edit.png">수정</a>						<a href="#삭제" class="btn_pack btn_mo gray" id="delInfo"><img src="/common/admin/images/common/ico_del.png">삭제</a>						<a href="/admin/manager/manager_list.af?${pageParam}&page=${resultParam.page}" class="btn_pack btn_mo gray"><img src="/common/admin/images/common/ico_list.png">목록</a>					</div>				</div>			</div>			<div class="tb_type2">				<h3>관리자 정보</h3>				<table>					<tbody>						<tr>							<th>아이디(이메일)</th>							<td>${result.ADMIN_ID}</td>							<th>전화번호</th>							<td>${result.ADMIN_PHONE}</td>						</tr>						<tr>							<th>이	름</th>							<td>${result.ADMIN_NM}</td>							<th>최종접속일</th>							<td>${result.LOGIN_DATE_TXT}</td>						</tr>						<tr>							<th>등록일</th>							<td>${result.INS_DATE_TXT}</td>							<th>부서명</th>							<td>${result.ADMIN_DEPT}</td>						</tr>						<tr>							<th>고객정보 보기</th>							<td colspan="3">${result.MEMBER_VIEW_AT_TXT}</td>						</tr>						<tr class="tb_tit">							<th colspan="4">								<div class="tb_tit_area">접근권한</div>							</th>						</tr>						<c:forEach items="${resultsAdminMenu}" var="result" varStatus="status">			<c:if test="${result.MENU_DEPTH == 1 and status.index > 0}">								</div>							</td>						</tr>	</c:if>	<c:if test="${result.MENU_DEPTH == 1}">						<tr>							<th>${result.MENU_NM}</th>							<td colspan="3">								<div class="lst_check checkbox">	</c:if>	<c:if test="${result.MENU_DEPTH != 1}">									<span>										<label>											${result.MENU_NM}<input type="checkbox" name="MENU_CODE" id="m${result.MENU_CODE}" value="${result.MENU_CODE}">										</label>									</span>	</c:if></c:forEach>											</tbody>				</table>			</div>			</form>		</div>	</div></section>
<script type="text/javascript">	$("#delInfo").on("click",function(e) {		e.preventDefault()		if(confirm("정말 삭제하시겠습니까? 되돌릴수 없습니다.")) {			$.ajax({				 type: "post"				,url : "manager_delete_action.af"				,data : {num : "${result.ADMIN_UID}"}				,dataType : "json"				,success: function(data){					if(data.result == true){						alert(data.msg);						top.location.href="manager_list.af?${pageParam}&page=${resultParam.page}";					}					else{						alert(data.msg);					}				}				,error: function(xhr, option, error){					alert("오류가 있습니다. 잠시후에 다시하세요");				}			});		}	});</script><script type="text/javascript"><c:forEach items="${resultsAdminAuth}" var="resultSub" varStatus="status">$("#m${resultSub.MENU_CODE}").attr("checked",true);</c:forEach>$('input:checkbox[name="MENU_CODE"]').each(function(){	if($(this).is(":checked")){		$(this).parent().parent().addClass("on");	}	else{		$(this).parent().parent().remove();	}});</script>
