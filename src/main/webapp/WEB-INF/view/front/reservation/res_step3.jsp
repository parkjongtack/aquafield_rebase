<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="../../common/taglibs.jsp" %><section id="res_step1">		<div id="login_wrap">		<div class="inner">			 <h2>온라인 예약</h2>			 			<ul class="tab_menu res">        		<li class="active tab_menu1" rel="tab1-1" style="width:100%;">        			<a href="javascript:Tab(1);" style="width:100%;">        				<img src="../common/front/images/ico/tab_menu_res1.png" alt=""><span>입장상품</span>        			</a>        		</li>        		<!-- <li class="tab_menu2" rel="tab2-1">        			<a href="javascript:Tab(2);">        				<img src="../common/front/images/ico/tab_menu_res2.png" alt=""><span>패키지</span>        			</a>        		</li> -->        	</ul>			 <div class="login_box box3" id="reserBox">				<div class="form_type1-1" id="tab1-1" style="display:block;">			 			 <header>			 	<h3 class="res_tit"><span>STEP 3</span class="tit2_t"> 결제수단을 선택하세요.</h3>			 </header>		 		 			 <form id="KSPayWeb" name=KSPayWeb action = "" method="post">			 	 <input type="text" name="mem_mobile" value='${mem_mobile}'/>				 <input type="hidden" name="rsData" value='${rsData}'/>				 <input type="hidden" name="sndStoreid" value="${params.sndStoreid}"/>				 <input type="hidden" name="sndOrdernumber" value="${params.sndOrdernumber}"/>				 <input type="hidden" name="sndGoodname" value="${params.sndGoodname}"/>					 <input type="hidden" name="sndAmount" value="${params.sndAmount}"/>				 <input type="hidden" name="sndOrdername" value="${params.sndOrdername}"/>				 <input type="hidden" name="sndEmail" value="${params.sndEmail}"/>				 <input type="hidden" name="sndMobile" value="${params.sndMobile}"/>			     <input type="hidden" name="sndServicePeriod"  value="${params.sndServicePeriod}~${params.sndServicePeriod}"/>			     <input type="hidden" name="reserveUid" value="${reserveUid}"/> 	 	 	 	 	 	 	 	  	 				 	<ul class="payment_list">			<!--             <li class="ssg"> -->			<!--                 <a href="javascript:void(0);" onclick=""> -->			<!--                     <div class="tit"><img class="ico" src="/common/front/images/ico/ico_pay_ssg.png"/>SSGPAY 결제</div><input type="radio" name="sndPaymethod" value=""> -->			<!--                 </a> -->			<!--             </li> -->									<c:forEach items="${codePAY_TYPE }" var="code" >							<li class="${code.CODE_ID }">				                <div class="tit">				                    <p class="chk_motion lst_check3">				                        <label><img class="ico" src="/common/front/images/ico/ico_pay_${code.CODE_ID }.png"/><input type="radio" name="sndPaymethod" value="${code.PG_PAY_CD }" data-value="${code.CODE_ID }"><span>${code.CODE_NM }</span><span class="bx_chk"></span></label>				                    </p>				                </div>				            </li>						</c:forEach>			        </ul>			        <input type="hidden" name=sndSsgPay value="">					<input type="hidden" name=sndReply value="">					<input type="hidden" name=sndGoodType value="1"> 					<input type="hidden" name=sndShowcard value="I,M">					<input type="hidden" name=sndCurrencytype value="WON">					<input type="hidden" name=sndInstallmenttype value="ALL(0:2:3:4:5:6:7:8:9:10:11:12)">					<input type="hidden" name=sndInteresttype value="NONE">					<input type="hidden" name=sndEscrow value="0">					<input type="hidden" name=sndWptype value="1"> 					<input type="hidden" name=sndAdulttype value="1"> 				    <input type="hidden" name=sndCashReceipt value="1">					<input type="hidden" name=sndMembId value="userid">					<input type="hidden" name=reWHCid 	value="">					<input type="hidden" name=reWHCtype 	value="">					<input type="hidden" name=reWHHash 	value="">					<input type="hidden" name="reserveMemUid" id="reserveMemUid" value="${params.reserveMemUid}" />					<input type="hidden" name="ECHA" value="${params.reserveMemUid}" /> <!-- 예약회원키값 -->					<input type="hidden" name="ECHB" value="${reserveUid}" /> <!-- 예약테이블 키값 -->					<input type="hidden" name="ECHC" value="" /> <!-- 결제수단 구분값 -->				 </form>			 				 <footer class="btns center">			        <!-- <a href="/reserve/newResStep.af" class="btn gray">이전으로</a> -->		         	<button class="btn s_org" onclick="_payChek(document.KSPayWeb);">			         	<span>결제하기</span>			        </button>				 </footer>			 </div>		 </div>		 </div>		 </div>     <script type="text/javascript">	     var ip = "";	     $(function() {	 		$.getJSON("https://api.ipify.org?format=jsonp&callback=?",	 			function(json) {	 				ip = json.ip;	 			}	 		);	 	});	    	    function Tab(type) {	 		if(confirm('예약유형을 변경하시면 이전 예약상품이 초기화 됩니다. 계속하시겠습니까?')) {	 			if(type == 1) {	 				location.href = '/reserve/newResStep.af?type=res';		 			} else {	 				location.href = '/reserve/newResStep.af?type=pakage';		 			}		 		} else {	 			return;	 		}	 	}	             setChkAndRadio();                (function(){			var shown = false;			var btnSubmit = $("#btn_pay_check");			if(ischrome && !isandroid){				$('input[name=sndPaymethod]').click(function(e){					if($(this).filter(function(){return this.value === '0010000000'}).prop('checked')){						if(!shown) $(".bank .txt_alert").text('PC 크롬브라우저에서는 실시간 계좌이체를 지원하지 않습니다.').show();						btnSubmit.addClass('gray3');						shown = true;					}else{						btnSubmit.removeClass('gray3');						if(shown) $(".bank .txt_alert").text('').hide();						shown = false;					}					return;				});			}		})();             	function _payChek(obj){     		if(ischrome && !isandroid && $('.bank input[name=sndPaymethod]').prop('checked')) return; //피씨 크롬이며, 실시간 계좌이체일때는 결제 막기     		     		if($('.ssg input[name=sndPaymethod]').prop('checked')){     			//개발서버     			var isTest = "${isTest}";				/* if(isTest == "Y"){					$('input[name=sndSsgPay]').val('1');				}				else{					alert("SSGPAY 결제는 서비스 준비중입니다.");					return false;				} */     			$('input[name=sndSsgPay]').val('1');			}else{				$('input[name=sndSsgPay]').val('');			}     					var formData = $("#KSPayWeb").serialize();	    	$.ajax({		   		type: "post"		   		,url : '/reserve/payCheck.af'		   		,data : formData		   		,dataType : "html"		   		,success: function(data){		   			eval(data);		   		}		   		,error: function(xhr, option, error){		   			alert("에러가 발생했습니다. 잠시 후에 다시하세요.");		   		}	   		});    		     	};	 	function _pay(_frm) 		{	 		 // 인증모듈 옵션	 		  //if ( (navigator.appName == 'Netscape') && (navigator.userAgent.toLowerCase().indexOf('trident') == -1) && $(':radio[name="sndPaymethod"]:checked').val() === '0010000000')	 		  //{					//alert('실시간 계좌이체는 Internet Explorer에서만 사용이 가능합니다.');					//return;	 		  //}else{					var sndPaymethod = $(':radio[name="sndPaymethod"]:checked').data("value");					$("input[name=ECHC]").val(sndPaymethod);										var agent = navigator.userAgent;					var midx		= agent.indexOf("MSIE");					var out_size	= (midx != -1 && agent.charAt(midx+5) < '7');					if( /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent) ) {//						20170704  KBR :  						//	실서버 환경						_frm.sndReply.value           = "https://" + location.host + "/reserve/mkspay.af";												// 개발서버 환경						var isTest = "${isTest}";						if(isTest === "Y"){							_frm.sndReply.value = "http://" + location.host + "/reserve/mkspay_test.af";						}												//모바일						_frm.action ='https://kspay.ksnet.to/store/mb2/KSPayPWeb_utf8.jsp';					}else{						//	20170704  KBR :  						//	실서버 환경						_frm.sndReply.value           = "https://" + location.host + "/reserve/kspayRcv.af";											// 개발서버 환경						var isTest = "${isTest}";						if(isTest === "Y"){							_frm.sndReply.value = "http://" + location.host + "/reserve/kspayRcv.af";						}						// 개발서버 환경						//_frm.sndReply.value           = "http://" + location.host + "/reserve/kspayRcv.af"; 						//getLocalUrl("/reserve/kspayRcv.af") ;												//PC						var width_	= 500;						var height_	= out_size ? 568 : 518;						var left_	= screen.width;						var top_	= screen.height;						left_ = left_/2 - (width_/2);						top_ = top_/2 - (height_/2);						op = window.open('about:blank','AuthFrmUp',						        'height='+height_+',width='+width_+',status=yes,scrollbars=no,resizable=no,left='+left_+',top='+top_+'');						if (op == null)						{							alert("팝업이 차단되어 결제를 진행할 수 없습니다.");							return false;						}						_frm.target = 'AuthFrmUp';						_frm.action ='https://kspay.ksnet.to/store/KSPayFlashV1.3/KSPayPWeb.jsp?sndCharSet=utf-8';					}					_frm.submit();	 		  //}	    }			function getLocalUrl(mypage) 		{ 			var myloc = location.href; 			return myloc.substring(0, myloc.lastIndexOf('/')) + '/' + mypage;		} 				function goResult(){			var formData = $("#KSPayWeb").serialize().replace(/%/g,'%25');			var mem_id = $("input[name=sndEmail]").val();						// 실서버 환경			var url = "/reserve/payProccess.af";			// 20170703 - KBR			// 개발서버 환경			var isTest = "${isTest}";			if(isTest === "Y"){				url = "/reserve/payProccess_test.af";				}	    	$.ajax({		   		type: "post"		   		//,contentType: "application/x-www-form-urlencoded"		   		,url: url		   		,data: formData		   		,dataType: "html"		   		,success: function(data){		   			eval(data);		   		}		   		,error: function(xhr, option, error){		   			alert("에러가 발생했습니다. 잠시 후에 다시하세요.");		   		}		   		/* ,beforeSend: function() {		   			  var width = 0;		   			  var height = 0;		   			  var left = 0;		              var top = 0;		              width = 50;		              height = 50;				              top = ( $(window).height() - height ) / 2 + $(window).scrollTop();		              left = ( $(window).width() - width ) / 2 + $(window).scrollLeft();				              if($("#div_ajax_load_image").length != 0) {		                     $("#div_ajax_load_image").css({		                            "top": top+"px",		                            "left": left+"px"		                     });		                     $("#div_ajax_load_image").show();		              }		              else {		                     $('body').append('<div id="div_ajax_load_image" style="position:absolute; top:' + top + 'px; left:' + left + 'px; width:' + width + 'px; height:' + height + 'px; z-index:9999; background:#f0f0f0; filter:alpha(opacity=50); opacity:alpha*0.5; margin:auto; padding:0; "><img src="/common/front/images/reservation/ajax_loader.gif" style="width:50px; height:50px;"></div>');		              }		       	}		   		,complete: function(){		   			$("#div_ajax_load_image").hide();		   		} */	   		});		}		function eparamSet(rcid, rctype, rhash, reserveMemUid){			document.KSPayWeb.reWHCid.value 	= rcid;			document.KSPayWeb.reWHCtype.value   = rctype  ;			document.KSPayWeb.reWHHash.value 	= rhash  ;			document.KSPayWeb.reserveMemUid.value 	= reserveMemUid  ;		}				function gotoFinal(reserveMemUid){			$('#reserveMemUid').val(reserveMemUid);			$("#KSPayWeb").attr("target","");			$('#KSPayWeb').attr('action','/reserve/final.af');			$('#KSPayWeb').serialize().replace(/%/g,'%25');			$('#KSPayWeb').submit();										 			//실제 결제 완료 화면			//document.KSPayWeb.reserveMemUid.value 	= reserveMemUid  ;			//reservationPop2.addCont({url : '/reserve/final.af', data : $("#KSPayWeb").serialize().replace(/%/g,'%25')});		}				var numType = ${num};		if(numType == 10000000) {			$(".tab_menu2").removeClass("active");			$('.tab_menu1').addClass("active");		} else if(numType == 20000000) {			$(".tab_menu1").removeClass("active");			$('.tab_menu2').addClass("active");		}     </script></section>