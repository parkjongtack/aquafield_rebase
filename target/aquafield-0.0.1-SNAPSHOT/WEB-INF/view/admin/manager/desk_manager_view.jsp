<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="../../common/taglibs.jsp" %>
<section id="contents">	<div class="contents_bx_type1">		<div class="contents_bx_inner">			<form>			<div id="path" class="fixed">				<div class="pathInner">					<h2>운영관리자</h2>					<div class="btns_area">						<a href="/admin/manager/desk_manager_write.jsp" class="btn_pack btn_mo d_gray"><img src="/common/admin/images/common/ico_edit.png">수정</a>						<a href="#" class="btn_pack btn_mo gray" id="delInfo"><img src="/common/admin/images/common/ico_del.png">삭제</a>						<a href="/admin/manager/desk_manager.jsp" class="btn_pack btn_mo gray"><img src="/common/admin/images/common/ico_list.png">목록</a>					</div>				</div>			</div>			<div class="tb_type2">				<h3>데스크관리 정보o</h3>				<table>					<tbody>						<tr>							<th>아이디(이메일)</th>							<td>gildong@daum.net</td>							<th>등록일</th>							<td>2016.09.03</td>						</tr>						<tr>							<th>이	름</th>							<td>홍길동</td>							<th>휴대폰번호</th>							<td>010.2094.1111</td>						</tr>						<tr>							<th>지점명</th>							<td>하남</td>							<th>부서명</th>							<td>아쿠아 프론트</td>						</tr>
						<tr class="tb_tit">
							<th colspan="4">								<div class="tb_tit_area">접근권한</div>							</th>						</tr>						<tr>							<th>예약내력 관리</th>							<td colspan="3" class="auth_Stats">								<span>ON</span>							</td>						</tr>						<tr>							<th>고객문의</th>							<td colspan="3" class="auth_Stats">								<span>ON</span>							</td>						</tr>					</tbody>				</table>			</div>			</form>		</div>	</div></section>
<script type="text/javascript">
	$("#delInfo").click(function(e) {
		e.preventDefault()
		if(confirm("정말 삭제하시겠습니까? 되돌릴수 없습니다.")) {
			alert("삭제되었습니다.");
		}
	});
</script>
