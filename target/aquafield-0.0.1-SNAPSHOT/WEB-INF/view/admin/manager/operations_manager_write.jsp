<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../common/taglibs.jsp" %>
<c:set var="pageParam" value="nm=${resultParam.nm}&sd=${resultParam.sd}&ed=${resultParam.ed}&sw=${resultParam.sw}"/>
<section id="contents">
	<div class="contents_bx_type1">
		<div class="contents_bx_inner">
			<form name="form" id="form" method="post" action="operations_manager_write_action.af" onsubmit="return false;">
			<c:choose>
				<c:when test="${empty result}"><input type="hidden" name="mode" id="mode" value="inst"></c:when>
				<c:otherwise><input type="hidden" name="mode" id="mode" value="updt"></c:otherwise>
			</c:choose>
			<input type="hidden" name="num" id="num" value="${result.ADMIN_UID}">
			<input type="hidden" name="POINT_CODE" id="POINT_CODE" value="0000">
			<div id="path" class="fixed">
				<div class="pathInner">
					<h2>운영관리자</h2>
					<div class="btns_area">
						<button type="button" class="btn_pack btn_mo d_gray" onclick="doSave()"><img src="/common/admin/images/common/admin/images/common/ico_save.png">저장</button>
						<a href="/admin/manager/manager_list.af" class="btn_pack btn_mo gray"><img src="/common/admin/images/common/ico_list.png">목록</a>
					</div>
				</div>
			</div>
			<div class="tb_type2">
				<h3>관리자 등록</h3>
				<table>
					<tbody>
						<tr>
							<th class="req">아이디(이메일)</th>
							<td><input type="text" name="ADMIN_ID" value="${result.ADMIN_ID}" class="ipt2 w100p"/></td>
							<th class="req">비밀번호</th>
							<td><input type="text" name="ADMIN_PW" value="" class="ipt2 w100p"/></td>
						</tr>
						<tr>
							<th class="req">이름</th>
							<td><input type="text" name="ADMIN_NM" value="${result.ADMIN_NM}" class="ipt2 w100p"/></td>
							<th class="req">전화번호</th>
							<td><input type="text" name="ADMIN_PHONE" value="${result.ADMIN_PHONE}" class="ipt2 w100p"/></td>
						</tr>
						<tr>
							<th>부서명</th>
							<td colspan="3"><input type="text" name="ADMIN_DEPT" value="${result.ADMIN_DEPT}" class="ipt2 w270"/></td>
						</tr>
						<tr>
							<th>고객정보 보기</th>
							<td colspan="3">
								<div class="lst_check radio">
									<span<c:if test="${result.MEMBER_VIEW_AT eq 'Y'}"> class="on"</c:if>>
										<label>
											on<input type="radio" name="MEMBER_VIEW_AT" id="customer_info_on" value="Y"<c:if test="${result.MEMBER_VIEW_AT eq 'Y'}"> checked</c:if> onclick="checkradio(this);chkAll(this);">
										</label>
									</span>
									<span<c:if test="${result.MEMBER_VIEW_AT ne 'Y'}"> class="on"</c:if>>
										<label>
											off<input type="radio" name="MEMBER_VIEW_AT" id="customer_info_off" value="N"<c:if test="${result.MEMBER_VIEW_AT ne 'Y'}"> checked</c:if>  onclick="checkradio(this);chkAll(this);">
										</label>
									</span>
								</div>
							
							</td>
						</tr>
						<tr class="tb_tit">
							<th colspan="4">
								<div class="tb_tit_area">접근권한 설정</div>
								<div class="tb_btn_area">
									<!-- <button class="btn_pack btn_mo d_gray">ALL</button> -->
									<div class="lst_check checkbox">
										<span>
											<label>
												ALL<input type="checkbox" class="chkAll" value="on" onclick="checkChkbox(this);chkAll(this);">
											</label>
										</span>
									</div>
								</div>
							</th>
						</tr>
<c:forEach items="${resultsAdminMenu}" var="result" varStatus="status">

		
	<c:if test="${result.MENU_DEPTH == 1 and status.index > 0}">
								</div>
							</td>
						</tr>
	</c:if>
	<c:if test="${result.MENU_DEPTH == 1}">
						<tr>
							<th>${result.MENU_NM}</th>
							<td colspan="3">
								<div class="lst_check checkbox">
	</c:if>
	<c:if test="${result.MENU_DEPTH != 1}">
									<span>
										<label>
											${result.MENU_NM}<input type="checkbox" name="MENU_CODE" id="m${result.MENU_CODE}" value="${result.MENU_CODE}" onclick="checkChkbox(this);chkAll(this);">
										</label>
									</span>
	</c:if>
</c:forEach>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			</form>
		</div>
	</div>
</section>
<script type="text/javascript">
function doSave(){
	var f = document.form;
	if($.trim(f.ADMIN_ID.value) == ""){
		alert("아이디(이메일) 항목을 입력하세요.");
		f.ADMIN_ID.focus();
		return;
	}
	if(f.mode.value == "inst" && $.trim(f.ADMIN_PW.value) == ""){
		alert("비밀번호 항목을 입력하세요.");
		f.ADMIN_PW.focus();
		return;
	}
	if($.trim(f.ADMIN_NM.value) == ""){
		alert("이름 항목을 입력하세요.");
		f.ADMIN_NM.focus();
		return;
	}
	if($.trim(f.ADMIN_PHONE.value) == ""){
		alert("전화번호 항목을 입력하세요.");
		f.ADMIN_PHONE.focus();
		return;
	}
	if($.trim(f.ADMIN_DEPT.value) == ""){
		alert("부서명 항목을 입력하세요.");
		f.ADMIN_DEPT.focus();
		return;
	}
	var msg = f.mode.value == "inst" ? "등록" : "수정";
	if(confirm(msg + " 하시겠습니까?")){
		$.ajax({
			 type: "post"
			,url : "operations_manager_write_action.af"
			,data : $(document.form).serialize()
			,dataType : "json"
			,success: function(data){
				if(data.result == true){
					alert(data.msg);
					top.location.href="manager_list.af?${pageParam}&page=${resultParam.page}";
				}
				else{
					alert(data.msg);
				}
			}
			,error: function(xhr, option, error){
				alert("오류가 있습니다. 잠시후에 다시하세요");
			}
		});
	}
}


function chkAll(me){
	if($(me).hasClass('chkAll')){
		if($(me).prop("checked") == false){
	    	$(me).closest("form").find("input[type='checkbox']").prop("checked", false).closest("span").removeClass('on');
	    	//$(me).closest("form").find("input[type='radio']#customer_info_on").prop("checked", false).closest("span").removeClass('on');
	    	//$(me).closest("form").find("input[type='radio']#customer_info_off").prop("checked", true).closest("span").addClass('on');
	    }else{
	    	$(me).closest("form").find("input[type='checkbox']").prop("checked", true).closest("span").addClass('on');
	    	//$(me).closest("form").find("input[type='radio']#customer_info_on").prop("checked", true).closest("span").addClass('on');
	    	//$(me).closest("form").find("input[type='radio']#customer_info_off").prop("checked", false).closest("span").removeClass('on');
	    }
	}else{

	}
		
}
</script>
<c:if test="${not empty resultsAdminAuth}">
<script type="text/javascript">
<c:forEach items="${resultsAdminAuth}" var="resultSub" varStatus="status">
$("#m${resultSub.MENU_CODE}").attr("checked",true);
</c:forEach>
$('input:checkbox[name="MENU_CODE"]').each(function(){
	if($(this).is(":checked")){
		$(this).parent().parent().addClass("on");
	}
});
</script>
</c:if>
