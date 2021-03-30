<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../common/taglibs.jsp" %>
    <section id="video">
        <header>
        	<h1>통신판매사업자정보</h1>
        	<div class="select st3">
                <span>시행일</span>
	       		<select name="terms_select" id="terms_select" class="customized-select w150">
					<c:forEach items="${results}" var="result">
					<option value="${result.TERMS_UID}">${result.INS_DATE_TXT}</option>
					</c:forEach>
				</select>
            </div>
        </header>
        <div class="iscContent">
        	<div class="inner">
				<div id="uterms">
           			${resultContent}
				</div>
    	</div>
        </div>
        <script type="text/javascript">
        	$('.customized-select').customSelect();
        	$("#terms_select").on("change",function(){
        		$.ajax({
        			 async: false
        			,type: "post"
        			,url : "/service/communication.af"
        			,data : {'num':$(this).val()}
        			,dataType : "html"
        			,success: function(obj){
        				$("#termsContent").html(obj);
        			}
        			,error: function(xhr, option, error){
        				alert("에러가 발생했습니다. 잠시 후에 다시하세요.");
        			}
        		});

        	});         	
        </script>
    </section>