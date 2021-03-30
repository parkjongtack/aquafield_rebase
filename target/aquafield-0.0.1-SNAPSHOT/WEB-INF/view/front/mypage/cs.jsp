<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../common/taglibs.jsp" %>
<section id="cs_list">
    <header>
        <div class="searhBox">
        	<form id="serach_form" name="serach_form" action="" method="post" onsubmit="return false;">
        	<input type="hidden" name="page" value="${resultParam.page}">
            <table>
                <tr>
                    <th>제목검색</th>
                    <td>
                        <div class="placeh">
                            <input type="text" class="ipt" id="searchtext" name="searchtext" value="" onblur="">
                        </div>
                    </td>
                </tr>
                <tr>
                    <th>일자검색</th>
                    <td>
                        <div class="placeh">
                            <input type="text" class="ipt w100 datepicker" id="statdate" name="statdate" value="" onblur="chkAccount.birthday(this)">
                            <span> ~ </span>
                            <input type="text" class="ipt w100 datepicker" id="enddate" name="enddate" value="" onblur="chkAccount.birthday(this)">
                        </div>
                    </td>
                </tr>
            </table>
            </form>
            <div class="btns right">
                <button class="btn_pack btn_mo gray" onclick="doSearch();">조회</button>
            </div>
        </div>
    </header>
    <div class="iscContent">
    </div>
    <footer>
    	<div class="btns right">
        	<button class="btn_pack btn_mo blue" onclick="contentBox.showCont({url : '/mypage/csWrite.af', move:'next'})">문의하기</button>
        </div>
    </footer>
    <script type="text/javascript">
         inputlabel.align();
         placeHFn.align();
    
         /* Datepicker */
        $(".datepicker").datepicker({
            showOn: "both",
            buttonImage: "../common/front/images/ico/ico_calen.png",
            buttonImageOnly: true,
            buttonText: "Select date",
            dateFormat: "yy.mm.dd",
            onSelect: function(event) {
                $(this).focus();
            }
        });
         
        doSearch();
         
    	//검색
    	function doSearch(){
    		if(!dateValidation("statdate")) return false;
    		if(!dateValidation("enddate")) return false;
    		if(!dateToDate("statdate", "enddate")) return false;

   	    	$.ajax({
				type: "post"
   		   		,url : '/mypage/ajaxCs.af'
   		   		,data : $("#serach_form").serialize()
   		   		,dataType : "html"
   		   		,success: function(obj){
   		   			$(".iscContent").html("");
					$(".iscContent").append(obj);
   		   		}
   		   		,error: function(xhr, option, error){
   		   			alert("에러가 발생했습니다. 잠시 후에 다시하세요.");
   		   		}
   	   		});	  		
    		
    	};
    		
    	//페이징
    	function doPaging(num){
    		$("input[name='page']").val(num);
    		doSearch();
    	};         
    </script>
</section>