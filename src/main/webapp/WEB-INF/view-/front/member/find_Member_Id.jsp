<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../common/taglibs.jsp" %>
<div class="contentArea" style="display:none;">

</div>
<div class="contentArea">

</div>
	<script type="text/javascript">
		//tab
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
		
		setChkAndRadio();
        $('.customized-select').customSelect();
        
        function next(){
            if($('#certPhone').is(':checked')) {
            	certi('checkplus','/member/checkplus.sf');
            } else if($('#certIpin').is(':checked')) {
            	certi('ipin','/member/ipin.af');
            } else {
                alert('실명 인증 방법을 선택해주세요.');
                return;
            }
        };
		
        function certi(type,url){
	    	$.ajax({
		   		async: false
		   		,type: "post"
		   		,url : url
		   		,dataType : "html"
		   		,success: function(obj){
					if(type == "ipin"){
						document.form_ipin.enc_data.value = obj;
						fnIpinPopup();					
					}else{
						document.form_chk.EncodeData.value = obj;
						fnPopup();						
					}
		   		}
		   		,error: function(xhr, option, error){
		   			alert("에러가 발생했습니다. 잠시 후에 다시하세요.");
		   		}
	   		});	
	    };
	    
	    function gotoNext(obj){
	    	var result = jQuery.parseJSON(obj);
	    	document.searchId.obj.value = result.sDupInfo;
		    var formData = $("#searchId").serialize();
			$("#searchId").attr("action", "/member/resultMemberId.af");
			$("#searchId").submit();
			
			//memberPop.addSubSect({url : '/member/resultMemberPwd.af', data: formData});
	    };
	    
	</script>

	<div id="login_wrap">
		<!-- <div class="login_top">
			<div class="logo">
				<img src="../common/front/images/common/logo.png" alt="아쿠아필드">
			</div>
		</div> -->
		
	   	<div class="inner">
			<h2>내정보 찾기</h2>
	        <ul class="tab_menu">
	            <li class="active" rel="tab1">
	            	<a href="/member/findMemberId.af">아이디 찾기</a>
	            </li>
	            <li rel="tab2">
	            	<a href="/member/findMemberPwd.af">비밀번호 찾기</a>
	            </li>
	        </ul>
	        <form id="searchId" name="searchId" method="post">
			    <input type="hidden" name="obj" value="">
	   			<input type="hidden" name="point" value="POINT01">
	   			<input type="hidden" name="gubun" value="id">     
		        
		        <div class="login_box">
					<div class="form_type1" id="tab1" style="display:block;">
			        	<ul>
			                <li class="form_type1_tit">
				                <p>인증방법을 선택해주세요.</p>
			                </li>
				        </ul>
			         	<p class="checkbox_st" style="margin-top: 20px;">
							<input type="checkbox" id="certPhone" name="optCertification1" checked="checked" class="checkbox-style" />
							<label for="certPhone">휴대폰 인증</label>
			         	</p>
						<div class="btn_group btn1">
							<a href="javascript:next();" class="btn blue">다음</a>
						</div>
					</div>
		        </div>
		    </form>
		</div>
	</div>
	
	<!-- ############### 안심본인인증 ####################### -->
		<script type="text/javascript">
			window.name ="Parent_window";
			
			function fnIpinPopup(){
				window.open('', 'popupIPIN2', 'width=450, height=550, top=100, left=100, fullscreen=no, menubar=no, status=no, toolbar=no, titlebar=yes, location=no, scrollbar=no');
				document.form_ipin.target = "popupIPIN2";
				document.form_ipin.action = "https://cert.vno.co.kr/ipin.cb";
				document.form_ipin.submit();
			}
			
			function fnPopup(){
				var form_chk = document.form_chk;
				window.open('', 'popupChk', 'width=500, height=550, top=100, left=100, fullscreen=no, menubar=no, status=no, toolbar=no, titlebar=yes, location=no, scrollbar=no');
		 		form_chk.action = "https://nice.checkplus.co.kr/CheckPlusSafeModel/checkplus.cb";
				form_chk.target = "popupChk";
				form_chk.submit();
				
		 	}
		</script>
		
		<!-- ############### Ipin 인증  #######################-->
		<form name="form_ipin" method="post">
			<input type="hidden" name="m" value="pubmain">
		    <input type="hidden" name="enc_data" value="">
		    <input type="hidden" name="param_r1" value="">
		    <input type="hidden" name="param_r2" value="">
		    <input type="hidden" name="param_r3" value="">
		</form>
		<!-- ############### Ipin 인증  #######################-->
		
		<!-- ############### 안심본인인증 ####################### -->
		
		<form name="form_chk" id="form_chk" method="post" target="popupChk">
			<input type="hidden" name="m" value="checkplusSerivce">
			<input type="hidden" name="EncodeData" value="">
			<input type="hidden" name="param_r1" value="">
			<input type="hidden" name="param_r2" value="">
			<input type="hidden" name="param_r3" value="">
		</form>
		<!-- ############### 안심본인인증 ####################### -->
	
	
	
