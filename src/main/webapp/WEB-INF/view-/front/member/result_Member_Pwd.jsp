<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../common/taglibs.jsp" %>
<div class="contentArea" style="display:none;">

</div>
<div class="contentArea">

</div>	
	<input type="hidden" id="memberCheck" value="${memberCheck }"/>
	<div id="login_wrap">
		<!-- <div class="login_top">
			<div class="logo">
				<img src="../common/front/images/common/logo.png" alt="아쿠아필드">
			</div>
		</div> -->
	   	<div class="inner">
			<h2>내정보 찾기</h2>
			<ul class="tab_menu">
	            <li rel="tab2"><a href="/member/findMemberId.af">아이디 찾기</a></li>
	            <li class="active" rel="tab1"><a href="javascript:void(0);">비밀번호 찾기</a></li>
	        </ul>
	        <div class="login_box">
				<div class="form_type1" id="tab2">
					<ul>
		                <li>
		                    <div class="placeh">
		                        <input type="text" class="ipt bd_hide w260 injectAble" name="user_id" id="user_id" value="" placeholder="인증방법을 선택해주세요" onfocus="this.placeholder=''" onblur="this.placeholder='인증방법을 선택해주세요'">
		                        <label for="user_id" style=""></label>
		                    </div>
							<style media="screen">
								input::placeholder  {	color: #113a5d; font-size:1.125em; font-weight: bold;	}
							</style>
		                </li>
			        </ul>
		         	<p class="checkbox_st" style="margin-top: 20px;">
						<input type="checkbox" id="divECI_ISDVSAVE" class="checkbox-style" />
						<label for="divECI_ISDVSAVE">휴대폰 인증</label>
		         	</p>
					<div class="btn_group btn1">
						<a href="../mem/find_pwid2.html" class="btn blue">다음</a>
					</div>
				</div>
				
				<div class="form_type1" id="successForm" style="display:block;">
					<div class="form_tit blue">
						<strong>본인인증에 성공했습니다.<br> 새로운 비밀번호를 입력해주세요.</strong>
					</div>
					<ul class="from_list">
			            <li> 
			               <div class="placeh">
			                  <input type="password" class="ipt bd_hide injectAble" name="resetPWD1" id="resetPWD1" value="" placeholder="새 비밀번호" onblur="pw01('#resetPWD1')">
			                  <label for="resetPWD1" style=""></label>
			                </div>
			            </li>
			            <li>
			           		<div class="placeh">
				               	<input type="password" class="ipt bd_hide" name="resetPWD2" id="resetPWD2" value="" placeholder="비밀번호 확인" onfocus="this.placeholder=''" onblur="pw02('#resetPWD2', '#resetPWD1');">
				               	<label for="user_pwd" style=""></label>
				            </div>
			            </li>
						<li class="from_list_tit">
							<p>※ 영 소문자, 숫자 조합8자리 이상 </p>
						</li>
				    </ul>
					<style media="screen">
						input::placeholder  {	color: #555;	}
					</style>
					<div class="btn_group btn1">
						<a href="javascript:resetPw();" class="btn blue">저장</a>
					</div>
				</div>
				
				
				<div class="form_type1" id="failForm" style="display:none;">
					<div class="form_tit blue">
						<strong>회원님의 정보가 없습니다.</strong>
					</div>
					<style media="screen">
						input::placeholder  {	color: #555;	}
					</style>
					<div class="btn_group btn1">
						<a href="/member/loginMain.af" class="btn blue">뒤로</a>
					</div>
				</div>
				<script type="text/javascript">
					$(document).ready(function(){
						var memberCheck = $('#memberCheck').val();
						if(memberCheck != undefined && memberCheck != '') {
							$('#successForm').hide(); /* 회원정보가 없을 경우 숨긴다. */
							$('#failForm').show();  /* 회원정보가 없을 경우  보여준다. */
						}
						
					});
					
					/* 탭  */
					$(function() {
						$(".tab_menu .form_type1:first").show();
						$("ul.tab_menu li").click(function() {
							$("ul.tab_menu li").removeClass("active")
							$(this).addClass("active")
							$(".form_type1").hide()
							var activeTab = $(this).attr("rel");
							$("#"+activeTab).fadeIn()
						});
					});
					
					/* 새 비밀번호 형식 Check */
					function pw01(id) {
						var t = $(id), list = t.closest('li'), meg = list.find(".status");
						var chk = 0;
						if(t.val().search(/[0-9]/g) != -1 ) chk ++; //숫자
					    if(t.val().search(/[a-z]/ig)  != -1 ) chk ++; //영문
					    //if(t.val().search(/[!@#$%^&*()?_~]/g)  != -1  ) chk ++; //특수기호
						if(/^[a-zA-Z0-9!@#$%^&*()?_~]{8,15}$/.test(t.val())) chk ++; //8~14자
						if(chk < 3){
							alert("형식에 맞지 않습니다.");
							return false;
						}else{
							return true;
						}
					}
					
					/* 비밀번호 확인 */
					function pw02(id, pw1){
						var t = $(id), list = t.closest('li'), meg = list.find(".status");
						if(t.val() != $(pw1).val()){
							alert("패스워드가 일치하지 않습니다.");
							return false;
						}else{
							return true;
						}
					}
					
					placeHFn.align();
			        
					/* 패스워드 수정 */
			        function resetPw(){
				    	$.ajax({
					   		async: false
					   		,type: "post"
					   		,url : '/member/setPwd.af'
					   		,data : {obj : '${obj}', point : '${point}', pwd : $("#resetPWD1").val()}
					   		,dataType : "html"
					   		,success: function(obj){
					   			if(obj == 'YES'){
					   				alert("정상처리 되었습니다.");
					   				location.href="/member/loginMain.af";
					   				/* popFn.close(); */
					   			}else{
					   				alert("처리중 에러가 발생하였습니다.");		   				
					   			}	   			
					   		}
					   		,error: function(xhr, option, error){
					   			alert("에러가 발생했습니다. 잠시 후에 다시하세요.");
					   		}
				   		});	
			        };
				</script>
	        </div>
	    </div>
	</div>
	
