<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<section id="pop_edit_my_profile_panel" class="pop_type1 bx_pop_type1">
	<div class="inner">
		<header>
			<h1>비밀번호 변경하기</h1>
		</header>
		<article>
			<form action="" onsubmit="return false;">
				<div class="tb_type2">
					<table>
						<tbody>
							<tr>
								<th>기존 비밀번호</th>
								<td>
									<input type="text" class="ipt2 w100p" value="" id="" placeholder="기존 비밀번호를 입력해주세요"/>
								</td>
							</tr>
							<tr>
								<th>새 비밀번호</th>
								<td>
									<input type="text" class="ipt2 w100p" value="" id="" placeholder="새 비밀번호를 입력해주세요"/>
								</td>
							</tr>
							<tr>
								<th>비밀번호 확인</th>
								<td>
									<input type="text" class="ipt2 w100p" value="" id="" placeholder="새 비밀번호를 다시 입력해주세요"/>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</form>
		</article>
		<footer>
			<div class="bot_btns rgt">
				<button class="btn_pack btn_mo d_gray" onclick="registerNewPanel();">저장</button>
				<button class="btn_pack btn_mo gray btn_close">취소</button>
			</div>
		</footer>
	</div>
	<script>
		function registerNewPanel(){
			popFn.hide($("#pop_edit_my_profile_panel"));
		}
	</script>
</section>