<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	
<div id="login_wrap" class="wrap_line">
	<!-- <div class="login_top">
		<div class="logo">
			<img src="../common/front/images/common/logo.png" alt="아쿠아필드">
		</div>
	</div> -->
   	<div class="inner">
		<h2>회원가입</h2>
     	<div class="login_box box3">
     		<div class="content on" style="left: 0%;">
	     		<section id="bx_signup_step">
				    
				    <div class="con_type1">
			      	    <div class="signup_step">
				            <ul>
				                <li><div><img src="../common/front/images/ico/ico_signup_step1.png" alt="약관동의"></div><span>약관동의</span></li>
				                <li class="on"><div><img src="../common/front/images/ico/ico_signup_step3.png" alt="추가정보"></div><span>정보입력</span></li>
				                <li><div><img src="../common/front/images/ico/ico_signup_step4.png" alt="가입완료"></div><span>가입완료</span></li>
			                </ul>
				        </div>
						
						<form action="/member/joinStep3.af" id="step02" name="step02" method="post">
					        <input type="hidden" name="chkTerm" value="${parameter.chkTerm}">
					        <input type="hidden" name="obj" value="${parameter.obj}">
							<input type="hidden" name="objGender" value="${parameter.objGender}">
							<input type="hidden" name="objBirth" value="${parameter.objBirth}">
					        <input type="hidden" name="idCheck" id="idCheck" value="N">
				         	<input type="hidden" name="receiveChk" value="N" />
				         	<input type="hidden" id="pwChk" name="pwChk" value="N" />
				         	<input type="hidden" id="pw2Chk" name="pw2Chk" value="N" />
				        
				            <div class="form_type3 type3">
							<span class="necessary" style="color: #D96C77; float: right;">* 필수항목</span>
				                <ul>
				                    <li class="require">
									    <div class="placeh">
											<span class="" style="color: #D96C77;">*</span>
										    <input style="display:inline-block;width:90%;" type="text" class="ipt bd_hide injectAble" name="userName" id="name" value="${parameter.objName}" placeholder="이름"   onblur="chkAccount.name(this)" readOnly>
										   <label for="name" style=""></label>
									    </div>
				                    </li>
					                <li class="require">
										<div class="placeh">
											<span class="" style="color: #D96C77;">*</span>
											 <input style="width:90%;" type="text" class="ipt bd_hide injectAble" name="phonenum" id="phonenum" value="${parameter.objMNum}" placeholder="휴대폰번호"   onblur="chkAccount.phone(this)" readOnly>
											 <label for="phonenum" style=""></label>
									 	</div>
					                </li>
					                <li class="require">
										<div class="placeh">
											<span class="" style="color: #D96C77;">*</span>
											 <input style="width:90%;" type="text" class="ipt bd_hide injectAble" name="userId" id="userid" value="" placeholder="아이디(이메일)"  onchange="emailCheckBlur(this)">
											 <label for="userId" style=""></label>
										</div>
					                </li>
					                <!-- <li class="require">
										<div class="placeh">
											 * <input style="width:90%;" type="password" class="ipt bd_hide injectAble" name="userPw01" id="PWD1"  placeholder="비밀번호"   onblur="chkAccount.pw01(this)">
											 <label for="PWD1" style=""></label>
										</div>
					                </li>
					                <li class="require">
										<div class="placeh">
											 * <input style="width:90%;" type="password" class="ipt bd_hide injectAble" name="userPw02" id="PWD2" placeholder="비밀번호 확인"   onblur="chkAccount.pw02(this,'#PWD1')">
											 <label for="PWD2" style=""></label>
										</div>
					                </li> -->
					                <li class="require">
										<div class="placeh">
										<span class="" style="color: #D96C77;">*</span>
											 <input style="width:90%;" type="password" class="ipt bd_hide injectAble" name="userPw01" id="PWD1"  placeholder="비밀번호"   onchange="pw01(this)">
											 <label for="PWD1" style=""></label>
										</div>
					                </li>
					                <li class="require">
										<div class="placeh">
											<span class="" style="color: #D96C77;">*</span>
											 <input style="width:90%;" type="password" class="ipt bd_hide injectAble" name="userPw02" id="PWD2" placeholder="비밀번호 확인"   onchange="pw02(this,'#PWD1')">
											 <label for="PWD2" style=""></label>
										</div>
					                </li>
									<li class="require_tit">
										<p>※영 소문자, 숫자 조합 8자리 이상</p>
									</li>
				              	</ul>
				            </div>
				            
				            <div class="form_type3">
				                 <ul>
				                     <li style="overflow: hidden;">
										
									 	<span class="der_tit"><span class="" style="color: #D96C77;">*</span>생년월일</span>
				                        <div class="inputlabel der der2">
							     			<input type="text" class="input w160 datepicker" name="birthDay" id="birthday" value="${parameter.objBirth}" readonly="readonly">
					                        <label for="birthday" class="tit" style="top:-30px;"></label>
				                        </div>
										<div class="form_type3_checkbox">
											<p class="checkbox_st">
										        <input type="checkbox" id="divECI_ISDVSAVE1" class="checkbox-style" name="lunar" value="Y"/><label for="divECI_ISDVSAVE1">양력</label>
										        <input type="checkbox" id="divECI_ISDVSAVE2" class="checkbox-style" name="lunar" value="N"/><label for="divECI_ISDVSAVE2">음력</label>
										        
										    </p>
											<!-- <p class="checkbox_st">
											    <input type="checkbox" id="divECI_ISDVSAVE2" class="checkbox-style" name="lunar" value="N"/>
											    <label for="divECI_ISDVSAVE2">음력</label>
											</p> -->
										</div>
				                     </li>
				                     <li>
										
										<span class="der_tit"><span class="" style="color: #D96C77;">*</span>성별</span>
										<div class="form_type3_checkbox">
											<p class="checkbox_st">
									            <input type="checkbox" id="divECI_ISDVSAVE3" name="sex" value="M" <c:if test="${parameter.objGender eq 'M'}">checked</c:if> onclick="return false;">
									            <label for="divECI_ISDVSAVE3">남</label>
									            
								            </p>
											<p class="checkbox_st">
									            <input type="checkbox" id="divECI_ISDVSAVE4" name="sex" value="W" <c:if test="${parameter.objGender eq 'W'}">checked</c:if> onclick="return false;">
									            <label for="divECI_ISDVSAVE4">여</label>
								            </p>
										</div>
				                    </li>
				                     <li>
				                        <div class="inputlabel">
				                            <input type="text" class="ipt w2602" name="address" id="address" onclick="postcode('address');" readOnly>
				                            <!-- <label for="address2" class="tit">주소</label> -->
				                            <button type="button" onclick="postcode('address');" class="btn_pack btn_mo black small2"><span>주소검색</span></button>
				                        </div>
				                        <div class="placeh">
				                            <input type="text" class="ipt w100p injectAble" name="address2" id="address2" onblur="" placeholder="상세주소입력">
				                            <label for="address"></label>
				                        </div>
				                     </li>
				                 </ul>
					        </div>
					        
						     <div class="opt_certification">
							     <dl>
								 	<dt class="opt_txt">이메일/SMS 정보수신 동의(선택)</dt>
									<dd class="checkbox_st">
										<input type="checkbox" id="info" name="info" class="checkbox-style" />
										<label for="info" style="margin-top: 15px;"></label>
									</dd>
								  </dl>
								  <div class="opt_tit" style="text-align:left;">
										이메일/SMS 수신에 대한 동의는 서비스 필수 알림기능 (예약/회원
										정보 찾기 등)과 무관하며 동의하실 경우 아쿠아필드에서 진행하는
										이벤트 및 공지사항을 편리하게 받아보실 수 있습니다.
								  </div>
		            		 </div>
		            		  
				       </form>
					  
					</div>
					
				    <div class="btn_group btn1">
				        <a href="javascript:joinNext();" id="btnNext" class="btn blue">다음</a>
				    </div>
				    
				</section>
			</div>
        </div>
	</div>
</div>
	
<script type="text/javascript">
     inputlabel.align();
     placeHFn.align();
     setChkAndRadio();
     /* chkAccount.name($("#name"));
     chkAccount.phone($("#phonenum")); */
     
    $(document).ready(function() {
   	 	$('input[type="checkbox"][name="lunar"]').click(function(){
	        //클릭 이벤트 발생한 요소가 체크 상태인 경우
	        if ($(this).prop('checked')) {
	            //체크박스 그룹의 요소 전체를 체크 해제후 클릭한 요소 체크 상태지정
	            $('input[type="checkbox"][name="lunar"]').prop('checked', false);
	            $(this).prop('checked', true);
	            
	        }
 	    });
   	});

    /* 아이디 형식 체크 */
    function emailCheckBlur(id){
        var t = $(id), list = t.closest('li'), meg = list.find(".status");
        var regex=/^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/;
        if(t.val() === ""){
            alert("아이디를 입력해주세요.");
            list.removeClass("good bad");
            t.focus();
            return false;
        }

        t.val(t.val().trim());

        if(!regex.test(t.val())){
            list.removeClass("good").addClass('bad');
            alert("잘못된 이메일 형식입니다.");
            t.focus();
            $('#userid').val('');
            return false;
        }else{
        	emailCheck(t,list); /* 아이디 중복 체크 함수로 이동*/
        }
    };
     
    /* 아이디 중복 체크 함수*/ 
    function emailCheck(t,list){
    	//var t = $(id), list = t.closest('li'), meg = list.find(".status");

           if(t.val() != null && t.val() != ''){
	    	//if(list.hasClass('bad')) return;
               $.ajax({
				type: "post"
		   		,url : '/useridCheck.af'
		   		,data : {user_id : t.val()}
		   		,dataType : "html"
		   		,success: function(obj){
		   			if(obj === ''){
		   				list.removeClass("bad").addClass('good');
                           alert("사용가능한 아이디 입니다.");
		   				$("#idCheck").val('Y');
		   				$('#PWD1').focus();
		   			}else if(obj === 'DUPLITE'){
		   				list.removeClass("good").addClass('bad');
		   					$("#idCheck").val('N');
		   					$('#userid').val('');
                           alert("중복된 아이디 입니다.");
                           t.focus();
		   				return;
		   			}else{
		   				$("#idCheck").val('N');
		   				alert("아이디 중복체크중 에러가 발생하였습니다.");
		   				t.focus();
		   				return;
		   			}
		   		}
		   		,error: function(xhr, option, error){
		   			alert("에러가 발생했습니다. 잠시 후에 다시하세요.");
		   		}
	   		});
    	}else{
    		alert("아이디(이메일)을 입력해주세요.");
               t.focus();
    		return;
    	}
	}
    
    /* 새 비밀번호 형식 Check */
	function pw01(id) {
		var t = $(id), list = t.closest('li'), meg = list.find(".status");
		var chk = 0;
		if(t.val().search(/[0-9]/g) != -1 ) chk ++; //숫자
	    if(t.val().search(/[a-z]/ig)  != -1 ) chk ++; //영문
	    //if(t.val().search(/[!@#$%^&*()?_~]/g)  != -1  ) chk ++; //특수기호
		if(/^[a-zA-Z0-9!@#$%^&*()?_~]{8,15}$/.test(t.val())) chk ++; //8~14자
		if(chk < 3){
			list.removeClass("good").addClass('bad');
			$('#pwChk').val('N');
			alert("형식에 맞지 않습니다.");
			t.focus();
			return false;
		}else{
			list.addClass("good").removeClass('bad');
			$('#pwChk').val('Y');
			return true;
		}
	}
	
	/* 비밀번호 확인 */
	function pw02(id, pw1){
		var t = $(id), list = t.closest('li'), meg = list.find(".status");
		if(t.val() != $(pw1).val()){
			list.removeClass("good").addClass('bad');
			$('#pw2Chk').val('N');
			alert("패스워드가 일치하지 않습니다.");
			$("#PWD2").val('');
			t.focus();
			return false;
		}else{
			list.addClass("good").removeClass('bad');
			$('#pw2Chk').val('Y');
			return true;
		}
	}
    
	
	/* 회원가입 */
	function joinNext() {
		var id = $("#userid").val();
		var pw = $("#PWD1").val();
		var pw2 = $("#PWD2").val();
		var pwChk = $("#pwChk").val();
		var pw2Chk = $("#pw2Chk").val();
		var address = $('#address').val();
		var lunar = '';
		
		
		
		$("input[name=lunar]:checked").each(function() {
			lunar = $(this).val();
		});
		
		if(id == '' || id == undefined) {
			alert("아이디(이메일)을 입력해주세요.");
            $("#userid").focus();
			return;
			
		} else if(pwChk == 'N' || pw2Chk == 'N') {
			alert("비밀번호를 확인해주세요.");
			return;
		} else if (pw == '' || pw == undefined) {
			alert("비밀번호를 입력해주세요.");
            $("#PWD1").focus();
			return;
			
		} else if (pw2 == '' || pw2 == undefined) {
			alert("비밀번호 확인을 입력해주세요.");
            $("#PWD2").focus();
			return;
			
		} else if(lunar == '' || lunar == undefined) {
			alert("양력, 음력을 선택해주세요.");
			return;
		//} else if(address == '' || address == undefined) {
		//	alert("주소를 입력해주세요.");
        //    $("#address").focus();
		//	return;
		} else {
			
			if($("#idCheck").val() === 'Y') {
				$('#step02').submit();
				
			} else {
				return;
			}
		}
	}
	
    /* Datepicker 
    $("#birthday").datepicker({
        showOn: "both",
        buttonImage: "/common/front/images/ico/ico_calen.png",
        buttonImageOnly: true,
        buttonText: "Select date",
        dateFormat: "yy.mm.dd",
           changeMonth: true,
           changeYear: true,
           yearRange: "c-50:c+50",
           maxDate: '+0y',
           monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12']
    });
	*/
	
	$("input[name='info']").click(function(e) {
		if($("input:checkbox[name='info']").is(':checked')){
			$("input[name='receiveChk']").val('Y');
		}else{
			$("input[name='receiveChk']").val('N');
		}
	});

	//예약 데이터 중복 등록이 안되도록 click 중복 방지 처리
	$("#btnGoPayment").unbind("click").bind("click", function(){
		$(this).attr("disabled", "disabled");
		reservationPop.addCont({url : "/reserve/step03.af", data : $("#formData").serialize().replace(/%/g,'%25')});
	});

</script>		
	
