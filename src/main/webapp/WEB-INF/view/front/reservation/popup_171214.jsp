<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="../../common/taglibs.jsp" %><section id="pop_reservation" class="pop_type1 pop_style_reservation">    <div class="inner">    	<button class="btn_close">닫기</button>        <div id="bx_multi_tab">        	<ul>        		<li class="enter active"><a href="javascript:void(0);" onclick='reservationPop.addCont({url : "/reserve/step01.af", type: "입장상품", pointCode: "${POINTINFO.CODE_ID}", pointNm: "${POINTINFO.CODE_NM}", target:this})'><span>입장상품</span></a></li>        		<li class="package"><a href="javascript:void(0);" onclick='alert("패키지상품은 준비중입니다.")'><span>패키지</span></a></li>        	</ul>        </div>        <div id="bx_multi_content">            <div class="content"></div>            <div class="content"></div>        </div>        <aside id="bx_multi_aside"></aside>    </div></section><script type="text/javascript">	reservationPop.setInitialPoint({pointCode : "${POINTINFO.CODE_ID}", pointNm: "${POINTINFO.CODE_NM}"})</script>