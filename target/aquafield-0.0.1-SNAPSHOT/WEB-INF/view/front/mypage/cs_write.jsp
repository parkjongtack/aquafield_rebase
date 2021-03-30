<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../common/taglibs.jsp" %>
    <section id="cs_write">
        <header>
            <h3><img class="ico" src="/common/front/images/ico/ico_cs_view.png"><span>1:1 문의 남기기</span></h3>
        </header>
        <div class="inputBox">
            <div class="inner">
            	<form id="insform" name="insform" action="" method="post" onsubmit="return false;">
                <div class="title">
                    <div class="select st2">
                        <select name="askType" id="askType" class="customized-select w150">
                              <option value="">문의유형 선택</option>
	                        <c:forEach items="${typeList}" var="type">
                              <option value="${type.CODE_ID}">${type.CODE_NM}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <input type="text" class="ipt" id="title" name="title" onblur="">
                </div>
                <div class="textArea">
                    <textarea class="txa" id="content" name="content"></textarea>
                </div>
                </form>
                <div class="btns center">
                    <button class="btn_pack btn_mo blue" onclick="askSave();">저장</button>
                    <button class="btn_pack btn_mo gray" onclick="contentBox.showCont({url:'/mypage/cs.af', move:'prev'})">취소</button>
                </div>
            </div>
        </div>
        <script type="text/javascript">
            $('.customized-select').customSelect();
            
            function askSave(){
            	if($("#askType").val() == ''){alert('문의유형을 선택해 주시기 바랍니다.'); $("#askType").focus(); return false;}
            	if($.trim($("#title").val()) == ''){alert('문의내역 제목을 작성해 주시기 바랍니다.'); $("#title").focus(); return false;}
            	if($.trim($("#content").val()) == ''){alert('문의내용 작성해 주시기 바랍니다.'); $("#content").focus(); return false;} 
            	
       	    	$.ajax({
    				type: "post"
       		   		,url : '/mypage/ajaxCsIns.af'
       		   		,data : $("#insform").serialize()
       		   		,dataType : "html"
       		   		,success: function(obj){
						eval(obj);
       		   		}
       		   		,error: function(xhr, option, error){
       		   			alert("에러가 발생했습니다. 잠시 후에 다시하세요.");
       		   		}
       	   		});	             	
            	
            };
        </script>
    </section>