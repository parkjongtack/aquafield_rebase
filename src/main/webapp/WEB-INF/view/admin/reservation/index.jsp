<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="../../common/taglibs.jsp" %><script type="text/javascript">//검색function doSearch(type){	if(!dateValidation("srch_reg_s")) return false;	if(!dateValidation("srch_reg_e")) return false;	if(!dateToDate("srch_reg_s", "srch_reg_e")) return false;	if(type === "search"){		$("input[name='page']").val("1");		}	document.serach_form.submit();};<!--	$( document ).ready(function() {		// msg		if("${msg}" != ""){			alert("${msg}");		}	});	//검색	function doSearch(type){		if(!dateValidation("srch_reg_s")) return false;		if(!dateValidation("srch_reg_e")) return false;		if(!dateToDate("srch_reg_s", "srch_reg_e")) return false;		if(type === "search"){			$("input[name='page']").val("1");			}		document.serach_form.submit();	};			//페이징	function doPaging(num){		$("input[name='page']").val(num);		doSearch("paging");	};		//상세 이동	function goDetail(idx){		$("#resLinkIdx").val(idx);		document.serach_form.action = "/secu_admaf/admin/reservation/res_edit.af";		document.serach_form.submit();	}		//--> </script><section id="contents">	<div class="contents_bx_type1">		<div class="contents_bx_inner">			<div id="path" class="fixed">				<div class="pathInner">					<h2>예약내역</h2>					<div class="btns_area"> 						<!-- <button type="button" class="btn_pack btn_mo green2" onclick="excelDownload()"><img src="/admaf/images/common/ico_excel.png" alt="회원리스트"> Exel</button> -->					</div>				</div>			</div>			<form name="serach_form" action="/secu_admaf/admin/reservation/index.af" method="post" onsubmit="doSearch('search');return false;">				<input type="hidden" name="page" value="${resultParam.page }">				<div class="tb_type2">					<h3>조건검색</h3>					<table style="margin-bottom: 10px;">						<tbody>							<th>예약정보 바로보기</th>							<td>								<input type="text" name="reserve_uid" id="resLinkIdx" value="${resultParam.ORDER_NUM }" class="ipt2 w270" onkeydown="return onlyNumber(event);" onkeyup="removeChar(event);" style='ime-mode:disabled;'/>								<button class="btn_pack btn_mo d_gray" type="button" onclick="resLink();">확인</button>							</td>							<th>지점</th>							<td>								<select name="point_code" id="point_code" class="customized-select">									<option value="">전체</option>									<c:forEach items="${codePOINT_CODE }" var="code" >										<option value="${code.CODE_ID }" <c:if test="${resultParam.point_code == code.CODE_ID }">selected="selected"</c:if> >${code.CODE_NM }</option>									</c:forEach>								</select>							</td>						</tbody>					</table>					<table>						<tbody>							<tr>								<th>카테고리</th>								<td colspan="3">									<div class="lst_check radio">										<span <c:if test="${empty resultParam.category || resultParam.category eq '' }">class="on"</c:if>>											<label>												전체<input type="radio" name="category" id="" value="" onclick="checkradio(this);" <c:if test="${empty resultParam.category || resultParam.category eq '' }">checked=""</c:if>>											</label>										</span>										<span <c:if test="${not empty resultParam.category && resultParam.category eq '0' }">class="on"</c:if>>											<label>												입장상품<input type="radio" name="category" id="" value="0" onclick="checkradio(this);" <c:if test="${not empty resultParam.category && resultParam.category eq '0' }">checked=""</c:if>>											</label>										</span>										<span <c:if test="${not empty resultParam.category && resultParam.category eq '1' }">class="on"</c:if>>											<label>												패키지<input type="radio" name="category" id="" value="1" onclick="checkradio(this);" <c:if test="${not empty resultParam.category && resultParam.category eq '1' }">checked=""</c:if>>											</label>										</span>									</div>								</td>							</tr>							<tr>								<th>회원명</th>								<td><input type="text" name="mem_nm" class="ipt2 w100p" value="${resultParam.mem_nm }"/></td>								<th>휴대폰번호</th>								<td><input type="tel" name="mob_no" class="ipt2 w100p" value="${resultParam.mob_no }" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' style='ime-mode:disabled;' /></td>							</tr>							<tr>								<th>상태</th>								<td>									<select name="reserve_state" id="reserve_state" class="customized-select">										<option value="" >전체</option>										<c:forEach items="${codeRSRVT_TYPE }" var="code" >											<option value="${code.CODE_ID }" <c:if test="${resultParam.reserve_state == code.CODE_ID }">selected="selected"</c:if> >${code.CODE_NM }</option>										</c:forEach>										<c:if test="${not empty resultParam.noPmtYN}">											<option value="NOPMT" <c:if test="${resultParam.reserve_state == 'NOPMT' }">selected="selected"</c:if> >예약대기</option>										</c:if>									</select>								</td>								<th>방문일</th>								<td>									<input type="text" name="srch_reg_s" value="${resultParam.srch_reg_s }" class="ipt2 datepicker"/> 									~ <input type="text" name="srch_reg_e" value="${resultParam.srch_reg_e }" class="ipt2 datepicker"/>								</td>							</tr>							<tr>								<th colspan="4">									<div class="tb_btn_area center">										<button class="btn_pack btn_mo d_gray">Search</button>									</div>								</th>							</tr>						</tbody>					</table>				</div>			</form>			<div class="tb_type1">				<h3>검색결과</h3>				<div class="result_summary">Total : <strong>${totalCount }</strong></div>				<table>				 	<thead>				 		<tr>							<th>결제일</th>							<th width="40px">지점</th>							<th width="100px">예약번호</th>							<th>회원명</th>							<th width="100px">휴대폰번호</th>							<th>선택상품</th>							<!-- <th>대여상품</th> -->							<th>인	원</th>							<th>방문일</th>							<th>상	태</th>							<th width="50px">관	리</th>						</tr>				 	</thead>					<tbody>						<c:forEach var="vo" items="${rList }" varStatus="num" >							<tr id="res${vo.RESERVE_UID}">								<td>${vo.PAYMENT_DATE }</td>								<td>${vo.POINT_NM }</td>								<td>									<a href="javascript:void(0)" class="link" onclick="showResPanel('${vo.ORDER_NUM}')">										${vo.ORDER_NUM}									</a>								</td>								<td>									<c:choose>										<c:when test="${MEMINFOYN eq 'Y'}">											${vo.MEM_NM }																				</c:when>										<c:otherwise>											${vo.MASK_MEM_NM }										</c:otherwise>									</c:choose>								</td>								<td>									<c:if test="${MEMINFOYN eq 'Y'}">								    <c:choose>								    <c:when test="${fn:length(vo.MEM_MOBILE) > 10 }">								    	${fn:substring(vo.MEM_MOBILE,0,3)}-${fn:substring(vo.MEM_MOBILE,3,7)}-${fn:substring(vo.MEM_MOBILE,7,11)}								    </c:when>								    <c:otherwise>								    	${fn:substring(vo.MEM_MOBILE,0,3)}-${fn:substring(vo.MEM_MOBILE,3,6)}-${fn:substring(vo.MEM_MOBILE,6,10)}								    </c:otherwise>							    								    </c:choose>									    </c:if>								    <c:if test="${MEMINFOYN eq 'N'}">								    	${vo.MASK_MEM_MOBILE}								    </c:if>																	</td>								<td>${vo.ORDER_NM}</td>								<%-- <td>									<c:if test="${not empty vo.RENTAL1_ITEM_NM}">										${vo.RENTAL1_ITEM_NM }(${vo.RENTAL1_CNT })									</c:if>									<c:if test="${not empty vo.RENTAL1_ITEM_NM && 													(not empty vo.RENTAL2_ITEM_NM || not empty vo.RENTAL3_ITEM_NM ) }" >, </c:if>									<c:if test="${not empty vo.RENTAL2_ITEM_NM}">										${vo.RENTAL2_ITEM_NM }(${vo.RENTAL2_CNT })									</c:if>									<c:if test="${not empty vo.RENTAL2_ITEM_NM && not empty vo.RENTAL3_ITEM_NM  }" >, </c:if>									<c:if test="${not empty vo.RENTAL3_ITEM_NM}">										${vo.RENTAL3_ITEM_NM }(${vo.RENTAL3_CNT })									</c:if>								</td> --%>								<td>${vo.TOTNUM } 명</td>								<td>${vo.RESERVE_DATE }</td>									<c:choose>										<c:when test="${vo.RESERVE_STATE eq 'ING' }">											<c:set var="color" value="res" />										</c:when>										<c:when test="${vo.RESERVE_STATE eq 'USE' || vo.RESERVE_STATE eq 'NOUSE' }">											<c:set var="color" value="" />										</c:when>										<c:otherwise>											<c:set var="color" value="cancel" />										</c:otherwise>									</c:choose>								<td class="stat ${color }">									<c:choose>										<c:when test="${vo.RESERVE_STATE eq 'NOPMT'}" >											예약대기										</c:when>										<c:otherwise>${vo.RESERVE_STATE_NM }</c:otherwise>									</c:choose>								</td>								<td>									<c:if test="${vo.RESERVE_STATE ne 'NOPMT'}">										<a href="#" onclick="goDetail('${vo.ORDER_NUM}'); return false;" class="link">수정</a>									</c:if>								</td>							</tr>						</c:forEach>						<c:if test="${empty rList }">							<tr>								<td colspan="10">조회된 예약 내역이 없습니다.</td>							</tr>						</c:if>					</tbody>				</table>			</div>			<div class="paginate">				<c:if test="${pu.blockStartNum > 1 }">					<a href="javascript:;" onclick="doPaging('${pu.blockStartNum - 1 }');" class="btn prev">PREV</a>				</c:if>				<c:forEach var="i" begin="${pu.blockStartNum}" end="${pu.blockEndNum }">					<a href="javascript:;" onclick="doPaging('${i}');" class="num <c:if test="${i == pu.pageNum }">on</c:if>">${i}</a>				</c:forEach>				<c:if test="${pu.blockEndNum < pu.pageTotalCount }">					<a href="javascript:;" onclick="doPaging('${pu.blockEndNum + 1 }');" class="btn next">NEXT</a>				</c:if>			</div>		</div>	</div></section><script type="text/javascript">//엑셀 다운로드function excelDownload(){	if(!confirm('다운로드 하시겠습니까?')) return;	var temp_url = document.serach_form.action;	document.serach_form.action = "/secu_admaf/admin/reservation/res_excel_down.af";	document.serach_form.target="_blank";	document.serach_form.submit();		// 다운로드 후 form 옵션 초기화	document.serach_form.action = temp_url;	document.serach_form.target="_self";	//location.href='ajax_pop_no_apply_list_excel.do?scode=2016070407069'}// 예약정보 바로가기는 엔터키 따로 동작하게 처리$("#resLinkIdx").keydown(function (key) {    if(key.keyCode == 13){    	resLink();    	return false;    }}); //예약정보 바로보기 팝업function showResPanel(resIdx){	$("#resLinkIdx").focusout();	ajaxShowPopCont({		url : "/secu_admaf/admin/reservation/ajax_res_view.af",		data : { reserve_uid : resIdx}	});}//null 체크function resLink() {	var result = $("#resLinkIdx").val();	if(result != ''){		showResPanel(result);	}else{		alert("예약정보를 입력해주세요.");	}	}$(document).ready(function(){	var first = "${first}";	if(first === "Y"){		var now = new Date();	    var year= now.getFullYear();	    var mon = (now.getMonth()+1)>9 ? ''+(now.getMonth()+1) : '0'+(now.getMonth()+1);	    var day = now.getDate()>9 ? ''+now.getDate() : '0'+now.getDate();	    var chan_val = year + '.' + mon + '.' + day;	    console.log("chan_val::: " + chan_val);		$("input[name=srch_reg_s]").val(chan_val);		$("input[name=srch_reg_e]").val(chan_val);		//doSearch("search");	}});</script>