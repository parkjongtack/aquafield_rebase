<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../common/taglibs.jsp" %>
<section id="myinfo">
    <div class="iscContent">
        	<div class="inner">
			    <form name="submitForm" id="submitForm" method="post" onsubmit="return false;">
				<input type="hidden" name="idCheck" id="idCheck" value="${memberInfo.MEM_ID}">
				<input type="hidden" name="receiveChk" id="receiveChk" value="${memberInfo.RECEIVED_INFO_AT}">
				<input type="hidden" name="emailChk" id="emailChk" value="Y">
				<input type="hidden" name="phoneNum" id="phoneNum" value="${memberInfo.MOBILE_NUM}">
			    <div class="row">
			    	<div class="tb_type1 col s12 mb30">
						<table>
							<tbody>
								<tr>
									<th class="left">회원번호</th>
									<td class="left" colspan="3">${memberInfo.MEM_NUM}</td>
								</tr>
								<tr class="require">
									<th>이	름</th>
									<td>${memberInfo.MEM_NM}</td>
								</tr>

								<tr class="require">
									<th>아이디(이메일)</th>
									<td class="userId">
										<div class="placeh">
											<input type="text" class="ipt w260" name="userId" id="userId" value="${memberInfo.MEM_ID}">
											<button type="button" onclick="emailCheck('#userId');" class="btn_pack btn_mo gray small"><span>중복체크</span></button>
										</div>
										<div class="status"></div>
									</td>
								</tr>
								<tr>
									<th>휴대폰 번호</th>
									<td>
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
									<th>생년월일</th>
									<td>
										<c:choose>
											<c:when test="${memberInfo.MEM_BIRTH != '' && memberInfo.MEM_BIRTH != null}">
												<div class="placeh">${memberInfo.MEM_BIRTH}</div>
					                            <div class="chk_motion lst_check3">
						                            <label>
						                            	<c:if test="${memberInfo.BIRTH_TYPE eq 'Y'}"><span>양력</span></c:if>
						                            	<c:if test="${memberInfo.BIRTH_TYPE eq 'N'}"><span>음력</span></c:if>
						                            </label>
						                        </div>
						                        <input type="hidden" class="ipt w160" id="birthday" name="birthday" value="${memberInfo.MEM_BIRTH}">
						                        <input type="hidden" id="" name="lunar" value="${memberInfo.BIRTH_TYPE}">
											</c:when>
											<c:otherwise>
												<div class="placeh">
					                                <input type="text" class="ipt w160 datepicker" id="birthday" name="birthday" value="" readonly="">
					                                <label for="birthday">생년월일을 입력해주세요.</label>
					                            </div>
					                            <div class="chk_motion lst_check3">
						                            <label><input type="radio" id="" name="lunar" value="Y"><span class="bx_chk"></span> <span>양력</span></label>
						                            <label><input type="radio" id="" name="lunar" value="N"><span class="bx_chk"></span> <span>음력</span></label>
						                        </div>
											</c:otherwise>
										</c:choose>
									</td>
								</tr>
								<tr>
									<th>성	별</th>
									<td>
										<c:choose>
											<c:when test="${memberInfo.MEM_GENDER != '' && memberInfo.MEM_GENDER != null}">
												<label>
													<c:if test="${memberInfo.MEM_GENDER eq 'M'}"><span>남</span></c:if>
													<c:if test="${memberInfo.MEM_GENDER eq 'W'}"><span>여</span></c:if>
													<input type="hidden" id="" name="sex" value="${memberInfo.MEM_GENDER}">
												</label>
											</c:when>
											<c:otherwise>
				                                <label><input type="radio" id="" name="sex" value="M" checked><span class="bx_chk"></span> <span>남</span></label>
				                                <label><input type="radio" id="" name="sex" value="W"><span class="bx_chk"></span> <span>여</span></label>
											</c:otherwise>
										</c:choose>
									</td>
								</tr>
								<tr>
									<th>주소</th>
									<td class="address">
			                            <div class="placeh">
			                                <input type="text" class="ipt w260 mb5" id="address" name="address" value="${memberInfo.HOME_ADDR1}" onblur="chkAccount.address(this);">
			                                <button type="button" onclick="postcode('address');" class="btn_pack btn_mo gray small mb5"><span>주소검색</span></button>
										</div>
			                            <div class="placeh">
			                                <input type="text" class="ipt w100p" id="address2" name="address2" value="${memberInfo.HOME_ADDR2}" onblur="">
			                            </div>
									</td>
								</tr>
								<tr>
									<th class="left">마케팅 동의</th>
									<td class="left emailArea">
										<div class="tit">이메일/SMS 정보수신 동의 (선택)<input type="checkbox" name="info" <c:if test="${memberInfo.RECEIVED_INFO_AT eq 'Y'}">checked</c:if>></div>
										이메일/SMS 수신에 대한 동의는 서비스 필수 알림기능(예약/회원정보 찾기 등)과 무관하며 동의하실 경우 아쿠아필드에서 진행하는 이벤트 및 공지사항을 편리하게 받아보실 수 있습니다.
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
	     setChkAndRadio();

	     /* Datepicker */

	    $(".datepicker").datepicker({
	        showOn: "both",
	        buttonImage: "../../common/front/images/ico/ico_calen.png",
	        buttonImageOnly: true,
	        buttonText: "Select date",
	        dateFormat: "yy.mm.dd",
	        changeMonth: true,
	        changeYear: true,
	        yearRange: "c-50:c+50",
	        maxDate: '+0y',
	        monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'],
	        onSelect: function(event) {
	            $(this).focus();
	        }
	    });

   	    function emailCheck(id){
	    	var t = $(id), list = t.closest('tr'), meg = list.find(".status");
			var regex=/^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/;
			if(!regex.test(t.val()) || t.val() === "" ){
				list.removeClass("good").addClass('bad');
				meg.html("잘못된 이메일 형식입니다.");
			}else{
				var ajaxBoolean = false; //ajax로 중복확인 bloolean
				var oldId = $("#idCheck").val();
				var newId = $("#userId").val();
				if(oldId != newId){
			    	$.ajax({
				   		async: false
				   		,type: "post"
				   		,url : '/useridCheck.af'
				   		,data : {user_id: t.val()}
				   		,dataType : "html"
				   		,success: function(obj){
				   			if(obj === ""){
				   				ajaxBoolean = true;
				   			}
				   		}
				   		,error: function(xhr, option, error){
				   			alert("에러가 발생했습니다. 잠시 후에 다시하세요.");
				   		}
			   		});
				}else{
					ajaxBoolean = true;
				}

				if(!ajaxBoolean) {
					list.removeClass("good").addClass('bad');
					meg.html("*중복된 이메일 입니다.");
				}else{
					list.addClass("good").removeClass('bad');
					meg.html("*사용가능한 이메일 입니다.");
					$("input[name='emailChk']").val('Y');
				}
			}
	    }

	    /*function chkPw01(id) {
	    	var t = $(id), list = t.closest('tr'), meg = list.find(".status");
			if(chkNull(t,meg,list)) return;
			var chk = 0;
			if(t.val().search(/[0-9]/g) != -1 ) chk ++; //숫자
		    if(t.val().search(/[a-z]/ig)  != -1 ) chk ++; //영문
			if(/^[a-zA-Z0-9!@#$%^&*()?_~]{8,15}$/.test(t.val())) chk ++; //8~14자
			if(chk < 3){
				list.removeClass("good").addClass('bad');
				meg.html("형식에 맞지 않습니다.");
				return false;
			}else{
				list.addClass("good").removeClass('bad');
				meg.html("");
			}
	    }

	    function chkPw02(id, pw1) {
	    	var t = $(id), list = t.closest('tr'), meg = list.find(".status");
			if(chkNull(t,meg,list)) return;
			if(t.val() != $(pw1).val()){
				list.removeClass("good").addClass('bad');
				meg.html("패스워드가 일치하지 않습니다.");
				return false;
			}else{
				list.addClass("good").removeClass('bad');
				meg.html("");
			}
	    }

	    function chkNull(t1,meg1,list){
			if(t1.val() === ""){
				meg1.html("");
	            list.removeClass("good bad");
	            return true;
			}
		}*/

		function formCheck(){
			var result = true;
			$("#submitForm").find('tr.require').each(function () {
				if($(this).hasClass('bad')) {
					$(this).find('input').eq(0).focus().blur().focus();
					result = false;
					return result;
				}
			});
			return result;
		}

   	    function submit(){
   	    	if($("input[name='emailChk']").val() != 'Y'){alert('아이디 중복체크를 해주시기 바랍니다.'); return;}
   	    	if(formCheck()){
   	    		var formData = $("#submitForm").serialize().replace(/%/g,'%25');
		    	$.ajax({
			   		async: false
			   		,type: "post"
			   		,url : '/mypage/myinfoUpd.af'
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

		$("input[name='info']").click(function(e) {

			if($("input:checkbox[name='info']").is(':checked')){
				$("input[name='receiveChk']").val('Y');
			}else{
				$("input[name='receiveChk']").val('N');
			}

		});

		$("input[name='userId']").click(function(e) {
			$("input[name='emailChk']").val('N');
		});
	</script>
</section>