<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../common/taglibs.jsp" %>
    <section id="cs_view">
        <header>
            <h3><img class="ico" src="/common/front/images/ico/ico_cs_view.png"><span>문의내역 확인</span></h3>
            <div class="view_head">
                <div class="title">${oneToOneInfo.ASK_TITLE}</div>
                <div class="date"><img class="ico" src="/common/front/images/ico/ico_date_clock.png"><span>${oneToOneInfo.INS_DATE}</span></div>
            </div>
        </header>
        <div class="iscContent">
        	<div class="inner">
        		<article>
                    <div class="view_content">
                        ${oneToOneInfo.ASK_CONTENT}
                    </div>
                    <c:if test="${oneToOneInfo.RE_DATE ne null}">
	                    <div class="view_answer">
	                        <ul>
	                            <li>
	                                <div class="answer_header">
	                                    <img class="ico" src="/common/front/images/ico/ico_cs_avatar.png">
	                                    <span class="name">담당자 답변</span>
	                                    <span class="date">${oneToOneInfo.RE_DATE}</span>
	                                </div>
	                                <div class="answer_content">
										${oneToOneInfo.ASK_CONTENT2}
	                                </div>
	                            </li>
	                        </ul>
	                    </div>
                    </c:if>
        		</article>
        	</div>
        </div>
        <footer>
        	<div class="btns center">
	        	<button class="btn_pack btn_mo white" onclick="contentBox.showCont({url : '/mypage/cs.af', move:'prev'})">목록</button>
	        </div>
        </footer>
        <script type="text/javascript">

        </script>
    </section>