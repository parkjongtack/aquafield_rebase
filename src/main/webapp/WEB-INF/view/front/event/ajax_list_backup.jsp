<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../common/taglibs.jsp" %>
<!-- <c:set var="pageParam" value="col=${resultParam.col}&sw=${resultParam.sw}"/>
<c:set var="pageUncount" value="${pu.totalRowCount - ((pu.pageNum - 1) *  pu.pageListSize)}"/>
<c:forEach items="${results}" var="result">
								    <a href="javascript:void(0)" class="item ${result.KIND_CODE}" onclick="window.newsPop = new NewsPopFn2({data:{num:'${result.NOTICE_EVENT_UID}', type:'${result.KIND_CODE}'}});">
										<div class="imgArea">
											<c:choose>
												<c:when test="${result.DATE_STAT eq 'Y'}"><div class="tagIco"><img src="/images/ico/ico_event_now.png"></div></c:when>
												<c:when test="${result.DATE_STAT eq 'Y'}"><div class="tagIco"><img src="/images/ico/ico_event_end.png"></div></c:when>
											</c:choose>
											<c:if test="${not empty result.FILE_LIST_FULL}">
								    		<img src="${result.FILE_LIST_FULL}">
								    		</c:if>
								    	</div>
										<div class="txtArea">
											<div class="inner">
												<h1 class="title">${result.TITLE}</h1>
												<c:if test="${result.KIND eq '2'}">
												<div class="date">기간 : ${result.START_DATE} ~ ${result.END_DATE}</div>
												<div class="location">장소 : ${result.LOCATION}</div>
												</c:if>
												<div class="tag">${result.KIND_NAME}</div>
												<div class="icon"></div>
											</div>
										</div>
										<input type="hidden" name="" class="pageCount" value="${pageUncount}">
								    </a>
	<c:set var="pageUncount" value="${pageUncount - 1 }" />
</c:forEach> -->
<c:if test="${cate eq 2}">
	<div class="news-top container">
		<ul class="newsFilter con_sub">
			<li style="margin-left: 25px;" class="active"><a href="javascript:fnnewsFilter(2);">진행중인 이벤트</a></li>
			<li><a href="javascript:fnnewsFilter(4);">지난 이벤트</a></li>
		</ul>
	</div>
</c:if>
<c:if test="${cate eq 4}">
	<div class="news-top container">
		<ul class="newsFilter con_sub">
			<li style="margin-left: 25px;"><a href="javascript:fnnewsFilter(2);">진행중인 이벤트</a></li>
			<li class="active"><a href="javascript:fnnewsFilter(4);">지난 이벤트</a></li>
		</ul>
	</div>
		<!--  <script>
	 $(function(){
		 /* $(".newsFilter.con_sub li:last-child").addClass("active"); */
	  var sBtn = $("ul > li");    //  ul > li 이를 sBtn으로 칭한다. (클릭이벤트는 li에 적용 된다.)
	  sBtn.find("a").click(function(){   // sBtn에 속해 있는  a 찾아 클릭 하면.
	   sBtn.removeClass("active");     // sBtn 속에 (active) 클래스를 삭제 한다.
	   $(this).parent().addClass("active"); // 클릭한 a에 (active)클래스를 넣는다.
	  })
	 });
	</script> -->
</c:if>

<script>
/* $(function(){
 //var sBtn = $("ul > li");    //  ul > li 이를 sBtn으로 칭한다. (클릭이벤트는 li에 적용 된다.)
 var sBtn = $(".newsFilter > li");    //  ul > li 이를 sBtn으로 칭한다. (클릭이벤트는 li에 적용 된다.)
 sBtn.find("a").click(function(){   // sBtn에 속해 있는  a 찾아 클릭 하면.
	//var idx = $(this).closest("li").index();
  sBtn.removeClass("active");     // sBtn 속에 (active) 클래스를 삭제 한다.
  $(this).parent().addClass("active"); // 클릭한 a에 (active)클래스를 넣는다.
  //$(this).closest("li").index().addClass("active")
  //return false;
 })
}); */
</script>


<c:forEach items="${results}" var="result">
  <c:if test="${result.KIND eq '1'}"> <c:set value="notice" var="kind"/> <c:set value="Notice" var="TYPE"/></c:if>
  <c:if test="${result.KIND eq '2'}"> <c:set value="event" var="kind"/> <c:set value="Event" var="TYPE"/></c:if> 
  <c:if test="${result.KIND eq '3'}"> <c:set value="event" var="kind"/> <c:set value="Media" var="TYPE"/></c:if>   
	
	<li class="item ${kind}">
		<c:choose>
			<c:when test="${result.DATE_STAT != 'N'}">
				<a href="javascript:void(0);" class="inner" onclick="window.newsPop = new NewsPopFn({data:{num:'${result.NEWS_UID}', type:'${kind}'}});">
					<div class="imgArea">
						<img src="${result.FILE_LIST_FULL}"/>
					</div>
					<div class="txtArea">
						<div class="inner">
							<div class="cate">${TYPE}</div>
							<div class="title">${result.TITLE}</div>
							<div class="date">${result.START_DATE} ~ ${result.END_DATE}</div>
						</div>
					</div>
				</a>
			</c:when>
			<c:otherwise>
				<c:choose>
					<c:when test="${kind eq 'event'}">
						<a href="javascript:void(0);" class="inner inner2" onclick="check();">
							<div class="imgArea">
								<img src="${result.FILE_LIST_FULL}"/>
							</div>
							<div class="txtArea">
								<div class="inner">
									<div class="cate">${TYPE}</div>
									<div class="title">${result.TITLE}</div>
									<div class="date">${result.START_DATE} ~ ${result.END_DATE}</div>
								</div>
							</div>
						</a>
					</c:when>
					<c:otherwise>
						<a href="javascript:void(0);" class="inner" onclick="window.newsPop = new NewsPopFn({data:{num:'${result.NEWS_UID}', type:'${kind}'}});">
							<div class="imgArea">
								<img src="${result.FILE_LIST_FULL}"/>
							</div>
							<div class="txtArea">
								<div class="inner">
									<div class="cate">${TYPE}</div>
									<div class="title">${result.TITLE}</div>
									<div class="date">${result.START_DATE} ~ ${result.END_DATE}</div>
								</div>
							</div>
						</a>
					</c:otherwise>
				</c:choose>
			</c:otherwise>
		</c:choose>
	</li>
</c:forEach>

<script type="text/javascript">
	function check() {
		alert('이벤트기간이 종료되었습니다.');
	}
	
	news.setPaging({ totalCount: '${totalCount}', recordCount: '${blockListSize}', perPage: '${pageListSize}' });
	
	
</script>