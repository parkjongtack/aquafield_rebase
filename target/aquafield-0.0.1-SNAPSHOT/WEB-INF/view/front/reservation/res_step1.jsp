<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="../../common/taglibs.jsp" %><section id="res_step1">	 <header>	 	<h3><span>STEP 1</span> 예약정보를 선택해주세요.</h3>	 </header>	 <form method="post" onsubmit="return false;">	 <input type="hidden" name="prodNum" value="${num}"/> 	 	<ul class="res_info_list">            <li class="date">                <a href="javascript:void(0);" onclick="reservationFn.date(this);">                    <div class="tit"><img class="ico" src="/common/front/images/ico/ico_res_date.png"/>방문일 선택</div>                    <div class="info">                        <span></span>                    </div>                </a>            </li>            <li class="plant">                <c:choose>                	<c:when test="${num eq 10000000}">                 		<a href="javascript:void(0);" onclick="reservationFn.plant(this);">               	                	</c:when>                	<c:otherwise>                		<a href="javascript:void(0);" onclick="reservationFn.package(this);">                	</c:otherwise>                </c:choose>                    <div class="tit"><img class="ico" src="/common/front/images/ico/ico_res_plant.png"/>인원선택</div>                    <div class="info">                        <span class="plantCount">0</span>건 / <span class="plantPerson">0</span>명                    </div>                </a>            </li> <!--             <li class="rantal"> --><!--                 <a href="javascript:void(0);" onclick="reservationFn.rantal(this);"> --><!--                     <div class="tit"><img class="ico" src="/common/front/images/ico/ico_res_rantal.png"/>대여상품</div> --><!--                     <div class="info"> --><!--                         <span class="rantalCount">0</span>건 --><!--                     </div> --><!--                 </a> --><!--             </li>  -->            <li class="event">                <a href="javascript:void(0);" onclick="reservationFn.event(this);">                    <div class="tit"><img class="ico" src="/common/front/images/ico/ico_res_event.png"/>이벤트</div>                    <div class="info">                        <span class="eventCount">미참여</span>                    </div>                </a>            </li>              </ul>        <div class="item_list">            <table>            </table>        </div>        <div class="total_price">            <ul>                <li class="dpayment">                    <div class="tit">합계</div>                    <div class="val"><span>0</span>원</div>                </li>            </ul>        </div>	 </form>	 <footer class="btns center">        <button class="btn_pack wide btn_mo orange" onclick='reservationPop.checkResStpe1()'>바로예약</button>	 </footer>     <script type="text/javascript">        reservationPop.setResList();     </script></section>