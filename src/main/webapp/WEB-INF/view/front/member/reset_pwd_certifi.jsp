<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="../../common/taglibs.jsp" %><button class="btn_close2">닫기</button><div id="reset_pwd_certifi" class="in inc_abs_foot_btn">    <header class="hd_type2">        <h2>비밀번호 찾기</h2>    </header>   	<form id="searchPw" name="searchPw" method="post" onsubmit="return false;">   	<input type="hidden" name="obj" value="">   	<input type="hidden" name="point" value="POINT01">          <section class="opt_certification_t2">        <dl>            <dt>본인인증 후 비밀번호를 재설정할 수 있습니다</dt>            <dd class="chk_motion lst_check3 perpendicular">                <label><input type="radio" id="certPhone" name="optCertification1" checked="checked"><span class="bx_chk"></span> <span>휴대폰 인증</span></label>                <!-- <label><input type="radio" id="certIpin" name="optCertification1"><span class="bx_chk"></span> <span>아이핀 인증</span></label> -->                       </dd>        </dl>    </section>    </form>        <footer class="btns">        <button onclick="next();" class="btn_pack btn_mo wide navy"><span>다음</span> <img src="/common/front/images/ico/ico_arr_right2.gif" alt=">"></button>    </footer>    <script>    	setChkAndRadio();        function next(){            if($('#certPhone').is(':checked')) {            	certi('checkplus','/member/checkplus.sf');            } else if($('#certIpin').is(':checked')) {            	certi('ipin','/member/ipin.af');            } else {                alert('실명 인증 방법을 선택해주세요.');                return false;            }        };        	    function certi(type,url){	    	$.ajax({		   		async: false		   		,type: "post"		   		,url : url		   		,dataType : "html"		   		,success: function(obj){						if(type == "ipin"){							document.form_ipin.enc_data.value = obj;							fnIpinPopup();											}else{							document.form_chk.EncodeData.value = obj;							fnPopup();												}		   		}		   		,error: function(xhr, option, error){		   			alert("에러가 발생했습니다. 잠시 후에 다시하세요.");		   		}	   		});		    };	    	   function gotoNext(obj){		   var result = jQuery.parseJSON(obj);		   document.searchPw.obj.value = result.sDupInfo;			var formData = $("#searchPw").serialize();			memberFn.resetPwd({data: formData});	   };    </script></div><script language='javascript'>window.name ="Parent_window";function fnIpinPopup(){	window.open('', 'popupIPIN2', 'width=450, height=550, top=100, left=100, fullscreen=no, menubar=no, status=no, toolbar=no, titlebar=yes, location=no, scrollbar=no');	document.form_ipin.target = "popupIPIN2";	document.form_ipin.action = "https://cert.vno.co.kr/ipin.cb";	document.form_ipin.submit();}function fnPopup(){	window.open('', 'popupChk', 'width=500, height=550, top=100, left=100, fullscreen=no, menubar=no, status=no, toolbar=no, titlebar=yes, location=no, scrollbar=no');	document.form_chk.action = "https://nice.checkplus.co.kr/CheckPlusSafeModel/checkplus.cb";	document.form_chk.target = "popupChk";	document.form_chk.submit();}</script><!-- ############### Ipin 인증  #######################--><form name="form_ipin" method="post">	<input type="hidden" name="m" value="pubmain">    <input type="hidden" name="enc_data" value="">    <input type="hidden" name="param_r1" value="">    <input type="hidden" name="param_r2" value="">    <input type="hidden" name="param_r3" value=""></form><!-- ############### Ipin 인증  #######################--><!-- ############### 안심본인인증 ####################### --><form name="form_chk" method="post">	<input type="hidden" name="m" value="checkplusSerivce">	<input type="hidden" name="EncodeData" value="">	<input type="hidden" name="param_r1" value="">	<input type="hidden" name="param_r2" value="">	<input type="hidden" name="param_r3" value=""></form><!-- ############### 안심본인인증 ####################### -->