<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.net.*" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="../../common/taglibs.jsp" %>
    <div class="iscContent">
        <div class="inner">
            <section id="cs_view">
                <div class="row">
                    <div class="question col s6">
                        <div class="inner">
                            <div class="header">
                                <h1><img class="ico" src="/common/front/images/mypage/question.png"/><span>문의하신 내용</span></h1>
                            </div>
                            <div class="tb_type1">
                                <table>
                                    <colgroup>
                                        <col width="15%">
                                        <col width="75%">
                                    </colgroup>
                                    <tbody>
                                         <tr>
                                            <th>문의 유형</th>
                                            <td>${oneToOneInfo.ASK_TYPE}</td>
                                        </tr>
                                        <tr>
                                            <th>제   목</th>
                                            <td>${oneToOneInfo.ASK_TITLE}</td>
                                        </tr>
                                        <tr>
                                            <th>내   용</th>
                                            <td class="cont" id="contents999">
                                            	${fn:escapeXml(oneToOneInfo.ASK_CONTENT.replaceAll("<br />","\\\r\\\n"))}
                                                <!-- ${oneToOneInfo.ASK_CONTENT}  -->
                                                <c:out value='${oneToOneInfo.ASK_CONTENT.replaceAll("<br />","@한@줄@내@림@")}' />
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <div class="answer col s6">
                        <div class="inner">
                            <div class="header">
                                <h1><img class="ico" src="/common/front/images/mypage/answer.png"/><span>답변내용</span></h1>
                            </div>
                            <div class="tb_type1">
                                 <table>
                                    <tbody>
                                         <tr>
                                            <td class="cont">
                                                <c:choose>
                                                    <c:when test="${oneToOneInfo.RE_DATE ne null}">
                                                        ${oneToOneInfo.ASK_CONTENT2}
                                                    </c:when>
                                                    <c:otherwise>
                                                        답변전 입니다.
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="btns center">
                    <button class="btn_pack btn_mo white btn_close">돌아가기</button>
                </div>
                <script type="text/javascript">
                var str = $("#contents999").text();
                str = str.replace(/@한@줄@내@림@/gi, "<br/>");
                $("#contents999").html(str);
                </script>
            </section>
        </div>
        <div class="rightBg"></div>
    </div>
