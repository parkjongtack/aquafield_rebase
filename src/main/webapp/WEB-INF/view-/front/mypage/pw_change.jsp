<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../common/taglibs.jsp" %>
<section id="myinfo">
    <div class="iscContent">
    	<div class="inner">
    		<h1 class="mobile_tit">비밀번호 변경</h1>
		    <form name="submitForm" id="submitForm" method="post" onsubmit="return false;">
		    <div class="row">
		    	<div class="tb_type1 col s12 mb30">
					<table>
						<tbody>
							<tr class="require">
								<th>현재 비밀번호</th>
								<td>
									<div class="placeh">
										<input type="password" class="ipt w260" name="userPW" id="userPW" onblur="nowPw(this)">
									</div>
									<div class="status"></div>
								</td>
							</tr>
							<tr class="require">
								<th>신규 비밀번호</th>
								<td>
									<div class="placeh">
										<input type="password" class="ipt w260" id="PWD1" name="PWD1" onblur="chkPw01(this)">
										<label for="PWD1">영 소문자, 숫자 조합 8자리 이상</label>
									</div>
									<div class="status"></div>
								</td>
							</tr>

							<tr class="require">
								<th>신규 비밀번호 재입력</th>
								<td>
									<div class="placeh">
										<input type="password" class="ipt w260" id="PWD2" name="PWD2" onblur="chkPw02(this,'#PWD1')">
										<label for="PWD2">영 소문자, 숫자 조합 8자리 이상</label>
									</div>
									<div class="status"></div>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
		    </div>
		    </form>
		    <div class="btns center">
				 <button class="btn_pack btn_mo brown" onclick="submit();">변경사항 저장</button>
				<button class="btn_pack btn_mo white" onclick="contentBox.showCont({url : '/mypage/myinfo.af', move:'prev'});">돌아가기</button>
			</div>
		</div>
	</div>
    <script type="text/javascript">
	     inputlabel.align();
	     placeHFn.align();


	    function nowPw(id) {
	    	var t = $(id), list = t.closest('tr'), meg = list.find(".status");
	    	if(chkNull(t,meg,list)) return;

	    	var result = true;
	    	if(!result){
				list.removeClass("good").addClass('bad');
				meg.html("*형식에 맞지 않습니다.");
				return false;
			}else{
				list.addClass("good").removeClass('bad');
				meg.html("");
			}
	    }

	    function chkPw01(id) {
	    	var t = $(id), list = t.closest('tr'), meg = list.find(".status");
			if(chkNull(t,meg,list)) return;
			var chk = 0;
			if(t.val().search(/[0-9]/g) != -1 ) chk ++; //숫자
		    if(t.val().search(/[a-z]/ig)  != -1 ) chk ++; //영문
			if(/^[a-zA-Z0-9!@#$%^&*()?_~]{8,15}$/.test(t.val())) chk ++; //8~14자
			if(chk < 3){
				list.removeClass("good").addClass('bad');
				meg.html("*형식에 맞지 않습니다.");
				return false;
			}else{
				list.removeClass("good bad");
				meg.html("");
			}
	    }

	    function chkPw02(id, pw1) {
	    	var t = $(id), list = t.closest('tr'), meg = list.find(".status");
			if(chkNull(t,meg,list)) return;
			if(t.val() != $(pw1).val()){
				list.removeClass("good").addClass('bad');
				meg.html("*비밀번호가 일치하지 않습니다.");
				return false;
			}else{
				list.addClass("good").removeClass('bad');
				meg.html("");
			}
	    }

	    function chkNull(t,meg,list){
			if(t.val() === ""){
				meg.html("*비밀번호를 입력하세요.");
	            list.addClass("bad").removeClass('good');
	            return true;
			}
		}

		function formCheck(){
			var result = true;
			$("#submitForm").find('tr.require').each(function () {
				if($(this).hasClass('bad') || $(this).find("input").val() == '') {
					$(this).find('input').eq(0).focus().blur().focus();
					result = false;
					return result;
				}
			});
			return result;
		}

   	    function submit(){
   	    	if(formCheck()){
   	    		var formData = $("#submitForm").serialize().replace(/%/g,'%25');
		    	$.ajax({
			   		async: false
			   		,type: "post"
			   		,url : '/mypage/myinfoUpd2.af'
			   		,data : formData
			   		,dataType : "html"
			   		,success: function(obj){
						$("#myinfo").append(obj);
			   		}
			   		,error: function(xhr, option, error){
			   			alert("에러가 발생했습니다. 잠시 후에 다시하세요.");
			   		}
		   		});
   	    	}
	    };
	</script>
</section>