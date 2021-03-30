<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../common/taglibs.jsp" %>
<section id="cs_list">
    <header>
        <div class="searhBox" style="display: none;">
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
                            <input type="text" class="ipt w160 datepicker" id="statdate" name="statdate" value="" onblur="chkAccount.birthday(this)">
                            <span> ~ </span>
                            <input type="text" class="ipt w160 datepicker" id="enddate" name="enddate" value="" onblur="chkAccount.birthday(this)">
                        </div>
                    </td>
                </tr>
            </table>
            </form>
            <div class="btns right">
                <button class="btn_pack btn_mo gray" onclick="doSearch();">조회</button>
            </div>
        </div>
        <h1><span>${name}</span> 님의 문의 내역 입니다.</h1>
    </header>
    <div class="iscContent">
        <div class="inner">
            <div class="tb_type2">
                <table>
                    <colgroup>
                    	<col width="10%">
                        <col width="15%">
                        <col width="50%">
                        <col width="15%">
                        <col width="10%">
                    </colgroup>
                    <thead>
                        <tr>
                        	<th>지점</th>
                            <th>문의유형</th>
                            <th>제목</th>
                            <th>작성일</th>
                            <th>처리상태</th>
                        </tr>
                    </thead>
                    <tbody>

                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <footer>
        <div class="btns right">
            <!-- <button class="btn_pack btn_mo white" onclick="contentBox.showCont({url : '/mypage/csWrite.af', move:'next'})"><img src="/common/front/images/ico/ico_write.png" class="ico"><span>문의하기</span></button> -->
            <button class="btn_pack btn_mo white" onclick="contentBox.popupLayer({url : '/mypage/csWrite.af'})"><img src="/common/front/images/ico/ico_write.png" class="ico"><span>문의하기</span></button>
        </div>
        <div class="list-paging"></div>
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
            //if(!dateValidation("statdate")) return false;
            //if(!dateValidation("enddate")) return false;
            //if(!dateToDate("statdate", "enddate")) return false;

            $.ajax({
                type: "post"
                ,url : '/mypage/ajaxCs.af'
                ,data : $("#serach_form").serialize()
                ,dataType : "html"
                ,success: function(obj){
                    $("#cs_list tbody").html(obj);
                }
                ,error: function(xhr, option, error){
                    alert("에러가 발생했습니다. 잠시 후에 다시하세요.");
                }
            });

        };

        function setPaging(o){

            var paging = $(".list-paging"),
                totalCount = o.totalCount, //게시물 총갯수
                recordCount = o.recordCount, //페이지당 게시물 갯수
                perPage = o.perPage, //페이지 표시 갯수
                currentIndex = parseInt(o.page), //현재페이지
                totalIndexCount = Math.ceil(totalCount / recordCount),
                first = (parseInt((currentIndex-1) / perPage) * perPage) + 1,
                last = (parseInt(totalIndexCount/perPage) == parseInt(currentIndex/perPage)) ? totalIndexCount%perPage : perPage;

            paging.empty();
            var prevStr = "";
            var nextStr = "";
            var firstStr = "";
            var lastStr = "";
            var pageStr = "";

            if(first-perPage > 0) {
                firstStr += '<a href="javascript:void(0);" onclick="doPaging('+(first-perPage)+');" class="btn prevPage">PREV PAGE</a>';
            }else{
                firstStr += '<a class="btn prevPage disable">PREV PAGE</a>';
            }

            if(currentIndex-1 > 0) {
                prevStr += '<a href="javascript:void(0);" onclick="doPaging('+(currentIndex-1)+');" class="btn prev">PREV</a>';
            }else{
                prevStr += '<a class="btn prev disable">PREV</a>';
            }

            if(currentIndex+1 <= totalIndexCount) {
                nextStr += '<a href="javascript:void(0);" onclick="doPaging('+(currentIndex+1)+');" class="btn next">NEXT</a>';
            }else{
                nextStr += '<a class="btn next disable">NEXT</a>';
            }

            if(first+perPage <= totalIndexCount) {
                lastStr += '<a href="javascript:void(0);" onclick="doPaging('+(first+perPage)+');" class="btn nextPage">NEXT PAGE</a>';
            }else{
                lastStr += '<a class="btn nextPage disable">NEXT PAGE</a>';
            }

            for(var i=first; i<(first+last); i++){
                if(i != currentIndex){
                    pageStr += '<a href="javascript:void(0);" onclick="doPaging('+i+');" class="num">'+i+'</a>';
                }
                else{
                    pageStr += '<a href="javascript:void(0);" class="num on">'+i+'</a>';
                }
            }
            paging.append(firstStr+ prevStr + pageStr + nextStr + lastStr);

            $(".news-paging").find('.num').click(function(e) {
                $(this).siblings('.on').removeClass('on').end().addClass('on');
            })
        }

        //페이징
        function doPaging(num){
            $("input[name='page']").val(num);
            doSearch();
        };
    </script>
</section>
