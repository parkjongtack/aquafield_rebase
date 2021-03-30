<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../common/taglibs.jsp" %>
<section id="myinfo">
    <div class="iscContent">
        	<div class="inner">
        		<article>
				    <form name="submitForm" id="submitForm" method="post" onsubmit="return false;">
				    <div class="row">
				    	<div class="tb_type1 col s6 mb20">
							<table>
								<tbody>
									<tr class="require">
										<th>이름<span>*</span></th>
										<td>${memberInfo.MEM_NM}</td>
									</tr>
									<tr class="require">
										<th>휴대폰 번호<span>*</span></th>
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
									<tr class="require">
										<th>아이디(이메일)<span>*</span></th>
										<td>${memberInfo.MEM_ID}</td>
									</tr>
									<tr class="require">
										<th>새 비밀번호<span>*</span></th>
										<td>
											<div class="placeh">
												<input type="password" class="ipt w180" id="PWD1" name="PWD1" onblur="chkAccount.pw01(this)">
				                            	<label for="PWD1">영 소문자, 숫자 조합 8자리 이상</label>
				                            </div>
										</td>
									</tr>
									<tr class="require">
										<th>비밀번호 확인<span>*</span></th>
										<td>
											<div class="placeh">
												<input type="password" class="ipt w180" id="PWD2" name="PWD2" onblur="chkAccount.pw02(this,'#PWD1')">
				                            	<label for="PWD2">영 소문자, 숫자 조합 8자리 이상</label>
				                            </div>
										</td>
									</tr>
									<tr class="require">
										<th>생년월일<span>*</span></th>
										<td>
											<div class="placeh">
				                                <input type="text" class="ipt w160 datepicker" id="birthday" name="birthday" value="${memberInfo.MEM_BIRTH}" onblur="chkAccount.birthday(this)">
				                                <label for="birthday">1987.03.11</label>
				                            </div>
				                            <div class="chk_motion lst_check3">
					                            <label><input type="radio" id="" name="lunar" value="Y" <c:if test="${memberInfo.BIRTH_TYPE eq 'Y'}">checked</c:if>><span class="bx_chk"></span> <span>양력</span></label>
					                            <label><input type="radio" id="" name="lunar" value="N" <c:if test="${memberInfo.BIRTH_TYPE eq 'N'}">checked</c:if>><span class="bx_chk"></span> <span>음력</span></label>
					                        </div>
										</td>
									</tr>
									<tr class="require">
										<th>성별<span>*</span></th>
										<td>
											<div class="chk_motion lst_check3">
				                                <label><input type="radio" id="" name="sex" value="M" <c:if test="${memberInfo.MEM_GENDER eq 'M'}">checked</c:if>><span class="bx_chk"></span> <span>남</span></label>
				                                <label><input type="radio" id="" name="sex" value="W" <c:if test="${memberInfo.MEM_GENDER eq 'W'}">checked</c:if>><span class="bx_chk"></span> <span>여</span></label>
				                            </div>
										</td>
									</tr>
									<tr class="require">
										<th>주소<span>*</span></th>
										<td>
											<button type="button" onclick="postcode('address');" class="btn_pack btn_mo gray mb10 small"><span>주소검색</span></button>
				                            <div class="placeh">
				                                <input type="text" class="ipt w100p mb10" id="address" name="address" value="${memberInfo.HOME_ADDR1}" onblur="chkAccount.address(this);">
				                                <input type="text" class="ipt w100p mb10" id="address2" name="address2" value="${memberInfo.HOME_ADDR2}" onblur="">
				                            </div>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
						<div class="tb_type1 col s6 mb20">
							<div class="emailArea">
								<div class="tit">이메일/SMS 정보수신 동의 (선택)<input type="checkbox" name=""></div>
								<div class="contBox">
									이메일/SMS 수신에 대한 동의는 서비스 필수 알림기능(예약/회원정보 찾기 등)과 무관하며 동의하실 경우 아쿠아필드에서 진행하는 이벤트 및 공지사항을 편리하게 받아보실 수 있습니다.
								</div>
							</div>
						</div>
				    </div>
				    </form>
				</article>
			</div>
		</div>
		<footer>
			<div class="btns center">
				 <button class="btn_pack btn_mo blue" onclick="submit();">확인</button>
				<button class="btn_pack btn_mo gray" onclick="contentBox.showCont({url : '/mypage/myinfo.af', move:'prev'});">취소</button>
			</div>
		</footer>
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
	        onSelect: function(event) {
	            $(this).focus();
	        }
	    });
	     
   	    function submit(){    	
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
	    };	     
	</script>
</section>