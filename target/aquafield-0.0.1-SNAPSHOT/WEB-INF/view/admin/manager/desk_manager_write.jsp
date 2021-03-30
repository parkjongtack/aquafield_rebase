<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="../../common/taglibs.jsp" %><section id="contents">	<div class="contents_bx_type1">		<div class="contents_bx_inner">			<form>			<div id="path" class="fixed">				<div class="pathInner">					<h2>데스크관리자</h2>					<div class="btns_area">						<button class="btn_pack btn_mo d_gray"><img src="/common/admin/images/common/ico_save.png">저장</button>						<a href="/admin/manager/desk_manager.jsp" class="btn_pack btn_mo gray"><img src="/common/admin/images/common/ico_list.png">목록</a>					</div>				</div>			</div>			<div class="tb_type2">				<h3>데스크관리자 등록</h3>				<table>					<tbody>						<tr>							<th class="req">아이디(이메일)</th>							<td><input type="text" name="" class="ipt2 w100p"/></td>							<th class="req">비밀번호</th>							<td><input type="text" name="" class="ipt2 w100p"/></td>						</tr>						<tr>							<th>이	름</th>							<td><input type="text" name="" class="ipt2 w100p"/></td>							<th class="req">전화번호</th>							<td><input type="text" name="" class="ipt2 w100p"/></td>						</tr>						<tr>							<th>부서명</th>							<td><input type="text" name="" class="ipt2 w100p"/></td>							<th>지점명</th>							<td>								<select name="" id="" class="customized-select">									<option value="0">전체</option>								</select>							</td>						</tr>						<tr class="tb_tit">							<th colspan="4">								<div class="tb_tit_area">접근권한 설정</div>								<div class="tb_btn_area">									<!-- <button class="btn_pack btn_mo d_gray">ALL</button> -->									<div class="lst_check checkbox">										<span>											<label>												ALL<input type="checkbox" class="chkAll" value="on" onclick="checkChkbox(this);chkAll(this);">											</label>										</span>									</div>								</div>							</th>						</tr>						<tr>
						<tr>							<th>예약내역 관리</th>							<td colspan="3">								<div class="lst_check radio">									<span>										<label>											on<input type="radio" name="cs_info" id="cs_info_on" value="on" onclick="checkradio(this);chkAll(this);">										</label>									</span>									<span class="on">										<label>											off<input type="radio" name="cs_info" id="cs_info_off" value="off" checked=""  onclick="checkradio(this);chkAll(this);">										</label>									</span>								</div>							</td>						</tr>						<tr>							<th>고객문의</th>							<td colspan="3">								<div class="lst_check radio">									<span>										<label>											on<input type="radio" name="customer_info" id="customer_info_on" value="on" onclick="checkradio(this);chkAll(this);">										</label>									</span>									<span class="on">										<label>											off<input type="radio" name="customer_info" id="customer_info_off" value="off" checked=""  onclick="checkradio(this);chkAll(this);">										</label>									</span>								</div>							</td>						</tr>
					</tbody>
				</table>
			</div>
			</form>
		</div>
	</div>
</section>
<script type="text/javascript">
function chkAll(me){
	if($(me).hasClass('chkAll')){
		if($(me).prop("checked") == false){
			$(me).closest("form").find(".lst_check.radio input").each(function(i) {
				if($(this).val() == "off"){
					$(this).prop("checked", true).closest("span").addClass('on');
				}else{
					$(this).prop("checked", false).closest("span").removeClass('on');
				}
			});
	    }else{
	    	$(me).closest("form").find(".lst_check.radio input").each(function(i) {
				if($(this).val() == "on"){
					$(this).prop("checked", true).closest("span").addClass('on');
				}else{
					$(this).prop("checked", false).closest("span").removeClass('on');
				}
			});
	    }
	}else{

	}
		
}
</script>
