<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<section id="pop_edit_my_profile_panel" class="pop_type1 bx_pop_type1">
	<div class="inner">
		<header>
			<h1>비밀번호 변경하기</h1>
		</header>
		<article>
			<form name="topPasswordChangeForm" id="topPasswordChangeForm" method="post" action="/" onsubmit="return false;">
				<div class="tb_type2">
					<table>
						<tbody>
							<tr>
								<th>기존 비밀번호</th>
								<td>
									<input type="password" class="ipt2 w100p" value=""  name="orgAdminPw" id="orgAdminPw" placeholder="기존 비밀번호를 입력해주세요"/>
								</td>
							</tr>
							<tr>
								<th>새 비밀번호</th>
								<td>
									<input type="password" class="ipt2 w100p" value="" name="adminPw" id="adminPw" placeholder="새 비밀번호를 입력해주세요"/>
								</td>
							</tr>
							<tr>
								<th>비밀번호 확인</th>
								<td>
									<input type="password" class="ipt2 w100p" value="" name="adminPw2" id="adminPw2" placeholder="새 비밀번호를 다시 입력해주세요"/>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</form>
		</article>
		<footer>
			<div class="bot_btns rgt">
				<button class="btn_pack btn_mo d_gray" onclick="chkTopPasswordChangeForm();">저장</button>
				<button class="btn_pack btn_mo gray btn_close">취소</button>
			</div>
		</footer>
	</div>
	<script>
	function chkTopPasswordChangeForm(){
		var f = document.topPasswordChangeForm;
		if($.trim(f.orgAdminPw.value) == ""){
			alert("기존 비밀번호를 입력하세요.");
			f.orgAdminPw.focus();
			return;
		}
		if($.trim(f.adminPw.value) == ""){
			alert("변경 비밀번호를 입력하세요.");
			f.adminPw.focus();
			return;
		}
		if($.trim(f.adminPw2.value) == ""){
			alert("비밀번호 재입력를 입력하세요.");
			f.adminPw2.focus();
			return;
		}	
		if($.trim(f.orgAdminPw.value) == $.trim(f.adminPw.value)){
			alert("기존 비밀번호와 같은 비밀번호는 사용 할 수 없습니다.");
			$("#adminPw").focus();
			return;
		}
		if($.trim(f.adminPw.value) != $.trim(f.adminPw2.value)){
			alert("변경 비밀번호와 비밀번호 재입력 항목이 일치하지 않습니다.");
			f.adminPw.focus();
			return;
		}
		
		
		$.ajax({
			 async: false
			,type: "post"
			,url : "/secu_admaf/admin/manager/manager_password_action.af"
			,data : $(document.topPasswordChangeForm).serialize()
			,dataType : "json"
			,success: function(data){
				if(data.result){
					popFn.hide($("#pop_edit_my_profile_panel"));
					alert("비밀번호가 변경되었습니다.");
				}
				else{
					alert("기존 비밀번호를 확인하세요.");
				}
			}
			,error: function(xhr, option, error){
				alert("요청 처리 중 오류가 발생하였습니다.");
			}
		});
	}
	</script>
</section>