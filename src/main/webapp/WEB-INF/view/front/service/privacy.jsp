<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../common/taglibs.jsp" %>
<section>
    <header>
    	<h1>개인정보 처리방침</h1>
    	<div class="select st2">
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
			<div id="privacy">
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
    			,url : "/service/terms_view.af"
    			,data : {'num':$(this).val()}
    			,dataType : "html"
    			,success: function(obj){
    				//$("#termsContent").html(obj);
    				$("#privacy").html(obj);
    			}
    			,error: function(xhr, option, error){
    				alert("에러가 발생했습니다. 잠시 후에 다시하세요.");
    			}
    		});

    	});    	
    </script>
</section>