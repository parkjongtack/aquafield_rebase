<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<script type="text/javascript" src="/common/front/js/jquery.form.min.js"></script>
    <section>
        <header>
            <h1>
                뉴스 게시글 관리
                <p>검색기능을 사용하여 문의사항을 빠르게 찾아볼 수 있습니다.</p>
            </h1>
        </header>
        <form name="form" id="form" method="post" action="" enctype="multipart/form-data">
        <input type="hidden" name="mode" value="inst" />
        <input type="hidden" name="pageType" value="2" />
        <div class="iscContent">
            <div class="inner">
                <article>
                    <div class="tb_type1 col s6 mb20">
                        <table>
                            <tbody>
                                <tr>
                                    <th>분류</th>
                                    <td colspan="3">
                                        <div class="select st2">
                                            <select name="kind" id="writeType" class="customized-select w150">
                                                <option value="1">공지사항</option>
                                                <option value="2">이벤트</option>
                                            </select>
                                        </div>
                                        <input type="checkbox" name="openYn" id="openYn" value="N"> 비공개
                                    </td>
                                </tr>
                                <tr class="eventOption">
                                    <th>기간</th>
                                    <td>
                                        <div class="placeh">
                                            <input type="text" class="ipt w100 datepicker" name="startDate" id="startDate" onblur="chkAccount.birthday(this)">
                                            <span> - </span>
                                            <input type="text" class="ipt w100 datepicker" name="endDate" id="endDate" onblur="chkAccount.birthday(this)">
                                        </div>
                                    </td>
                                    <th>장소</th>
                                    <td>
                                        <input type="text" class="ipt w100p" name="location" id="location" onblur="" maxlength="200">
                                    </td>
                                </tr>
                                <tr class="require">
                                    <th>제목<span>*</span></th>
                                    <td colspan="3">
                                        <input type="text" class="ipt w100p" name="title" onblur="" maxlength="200">
                                    </td>
                                </tr>
                                <tr>
                                    <th>대표이미지<br/>(사이즈x사이즈)</th>
                                    <td>
                                        <div class="fileinputs" data-text="파일첨부">
                                            <input type="file" class="file" name="fileList" id="fileList">
                                        </div>
                                        <!-- <div class="lst_Upload">
                                            <span class="tag" id="span-banner_1">Event.jpg<button onclick="deleteAttach('10', 'banner', '1');" alt="삭제하기" title="삭제하기">삭제</button></span>
                                        </div> -->
                                    </td>
                                    <th>상세이미지<br/>(사이즈x사이즈)</th>
                                    <td>
                                        <div class="fileinputs" data-text="파일첨부">
                                            <input type="file" class="file" name="fileContent" id="fileContent">
                                        </div>
                                        <!-- <div class="lst_Upload">
                                            <span class="tag" id="span-banner_1">Event.jpg<button onclick="deleteAttach('10', 'banner', '1');" alt="삭제하기" title="삭제하기">삭제</button></span>
                                        </div> -->
                                    </td>
                                </tr>
                                <tr>
                                    <th>입력옵션</th>
                                    <td colspan="3">
                                        <ul class="inputOption">
                                            <li><input type="checkbox" name="page1" value="Y"> 1Page</li>
                                            <li>
                                                - Left
                                                <div class="select st2">
                                                    <select name="page1LeftKind" id="page1LeftKind" class="customized-select w150 select_left">
                                                        <option value="text" selected="">Text</option>
                                                        <option value="image">image</option>
                                                    </select>
                                                </div>
                                            </li>
                                            <li>
                                                - Right
                                                <div class="select st2">
                                                    <select name="page1RightKind" id="page1RightKind" class="customized-select w150 select_right">
                                                        <option value="text">Text</option>
                                                        <option value="image" selected="">image</option>
                                                    </select>
                                                </div>
                                            </li>
                                        </ul>
                                        <ul class="inputOption">
                                            <li><input type="checkbox" name="page2" value="Y"> 2Page</li>
                                            <li>
                                                - Left
                                                <div class="select st2">
                                                    <select name="page2LeftKind" id="page2LeftKind" class="customized-select w150 select_left">
                                                        <option value="text" selected="">Text</option>
                                                        <option value="image">image</option>
                                                    </select>
                                                </div>
                                            </li>
                                            <li>
                                                - Right
                                                <div class="select st2">
                                                    <select name="page2RightKind" id="page2RightKind" class="customized-select w150 select_right">
                                                        <option value="text">Text</option>
                                                        <option value="image" selected="">image</option>
                                                    </select>
                                                </div>
                                            </li>
                                        </ul>
                                        <ul class="inputOption">
                                            <li><input type="checkbox" name="page3" value="Y"> 3Page</li>
                                            <li>
                                                - Left
                                                <div class="select st2">
                                                    <select name="page3LeftKind" id="page3LeftKind" class="customized-select w150 select_left">
                                                        <option value="text" selected="">Text</option>
                                                        <option value="image">image</option>
                                                    </select>
                                                </div>
                                            </li>
                                            <li>
                                                - Right
                                                <div class="select st2">
                                                    <select name="page3RightKind" id="page3RightKind" class="customized-select w150 select_right">
                                                        <option value="text">Text</option>
                                                        <option value="image" selected="">image</option>
                                                    </select>
                                                </div>
                                            </li>
                                        </ul>
                                    </td>
                                </tr>
                                <tr class="page">
                                    <th>페이지</th>
                                    <th class="center">Left</th>
                                    <th class="center" colspan="2">Right</th>
                                </tr>
                                <tr class="page1">
                                    <td>1Page</td>
                                    <td class="left">
                                        <div class="textArea">
                                            <textarea name="page1LeftContent" id="page1LeftContent" rows="10"></textarea>
                                        </div>
                                    </td>
                                    <td class="right" colspan="2">
                                        <div class="fileArea">
                                            <div class="fileinputs" data-text="파일첨부">
                                                <input type="file" class="file" name="page1RightImg" id="page1RightImg">
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                                <tr class="page2">
                                    <td>2Page</td>
                                    <td class="left">
                                        <div class="textArea">
                                            <textarea name="page2LeftContent" id="page2LeftContent" rows="10"></textarea>
                                        </div>
                                    </td>
                                    <td class="right" colspan="2">
                                        <div class="fileArea">
                                            <div class="fileinputs" data-text="파일첨부">
                                                <input type="file" class="file" name="page2RightImg" id="page2RightImg">
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                                <tr class="page3">
                                    <td>3Page</td>
                                    <td class="left">
                                        <div class="textArea">
                                            <textarea name="page3LeftContent" id="page3LeftContent" rows="10"></textarea>
                                        </div>
                                    </td>
                                    <td class="right" colspan="2">
                                        <div class="fileArea">
                                            <div class="fileinputs" data-text="파일첨부">
                                                <input type="file" class="file" name="page3RightImg" id="page3RightImg">
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </article>
            </div>
        </div>
        <footer>
            <div class="btns center">
                <button type="button" class="btn_pack btn_mo blue" onclick="doSave()">저장</button>
                <!-- <button type="button" class="btn_pack btn_mo gray" onclick="window.newsPop = new NewsPopFn({data:{idx:'6', type:'event'}});">미리보기</button>-->
                <button type="button" class="btn_pack btn_mo gray" onclick="noticeCancel();">취소</button>
            </div>
        </footer>
        </form>
        <script type="text/javascript">
        function doSave(){
        	var f = document.form;
        	if(f.kind.value == "2"){
        		if(!chkNull(f.startDate, "시작기간을 선택하세요.")) return false;
        		if(!chkNull(f.endDate, "종료기간을 선택하세요.")) return false;
        		if(!chkNull(f.location, "장소를 입력하세요.")) return false;
        	}
        	if(!chkNull(f.title, "제목을 입력하세요.")) return false;
        	if(!chkNull(f.fileList, "대표이미지를 선택하세요.")) return false;
        	if(!chkImageExt(f.fileList,"대표이미지는 gif,jpg,png 만 등록가능 합니다.")) return false;
        	if(!chkNull(f.fileContent, "상세이미지를 선택하세요.")) return false;
        	if(!chkImageExt(f.fileContent,"상세이미지는 gif,jpg,png 만 등록가능 합니다.")) return false;
        	
        	if(f.page1.checked == false && f.page2.checked == false  && f.page3.checked == false ){
        		alert("페이지는 한개이상 등록 하셔야 합니다.");
        		return false;
        	}
        	
        	if(f.page1.checked == true){
        		var isTrue = false;
        		if(f.page1LeftKind.value == "text"){
        			if($.trim(f.page1LeftContent.value) != "") isTrue = true;
        		}
        		else{
        			if($.trim(f.page1LeftImg.value) != ""){
        				if(!chkImageExt(f.page1LeftImg,"이미지는 gif,jpg,png 만 등록가능 합니다.")) return false;
        				isTrue = true;
        			}
        		}
        		if(f.page1RightKind.value == "text"){
        			if($.trim(f.page1RightContent.value) != "") isTrue = true;
        		}
        		else{
        			if($.trim(f.page1RightImg.value) != ""){
        				if(!chkImageExt(f.page1RightImg,"이미지는 gif,jpg,png 만 등록가능 합니다.")) return false;
        				isTrue = true;
        			}
        		}
        		if(!isTrue){
            		alert("1Page 내용 또는 이미지를 입력하세요. ");
            		return false;
        		}
        	}
        	if(f.page2.checked == true){
        		var isTrue = false;
        		if(f.page2LeftKind.value == "text"){
        			if($.trim(f.page2LeftContent.value) != "") isTrue = true;
        		}
        		else{
        			if($.trim(f.page2LeftImg.value) != ""){
        				if(!chkImageExt(f.page2LeftImg,"이미지는 gif,jpg,png 만 등록가능 합니다.")) return false;
        				isTrue = true;
        			}
        		}
        		if(f.page2RightKind.value == "text"){
        			if($.trim(f.page2RightContent.value) != "") isTrue = true;
        		}
        		else{
        			if($.trim(f.page2RightImg.value) != ""){
        				if(!chkImageExt(f.page2RightImg,"이미지는 gif,jpg,png 만 등록가능 합니다.")) return false;
        				isTrue = true;
        			}
        		}
        		if(!isTrue){
            		alert("2Page 내용 또는 이미지를 입력하세요. ");
            		return false;
        		}
        	}
        	if(f.page3.checked == true){
        		var isTrue = false;
        		if(f.page3LeftKind.value == "text"){
        			if($.trim(f.page3LeftContent.value) != "") isTrue = true;
        		}
        		else{
        			if($.trim(f.page3LeftImg.value) != ""){
        				if(!chkImageExt(f.page3LeftImg,"이미지는 gif,jpg,png 만 등록가능 합니다.")) return false;
        				isTrue = true;
        			}
        		}
        		if(f.page3RightKind.value == "text"){
        			if($.trim(f.page3RightContent.value) != "") isTrue = true;
        		}
        		else{
        			if($.trim(f.page3RightImg.value) != ""){
        				if(!chkImageExt(f.page3RightImg,"이미지는 gif,jpg,png 만 등록가능 합니다.")) return false;
        				isTrue = true;
        			}
        		}
        		if(!isTrue){
            		alert("3Page 내용 또는 이미지를 입력하세요. ");
            		return false;
        		}
        	}
        	
        	
        	if(confirm("등록하시겠습니까?"))
        	{
        		if(f.kind.value == "1"){
        			$("#startDate, #endDate, #location").val("");
        		}
        		f.action = "/event/noticeEventWriteAction.af";
        		$("#form").ajaxSubmit({
        			success : function(obj) {
        				var data = $.parseJSON(obj);
        				if(data.result){
        					alert(data.msg);
        					location.href="#/event/eventIndex.af?"+new Date().getTime();
        				}
        				else{
        					alert(data.msg);
        				}
        			},
        			error : function(error) {
        				alert("요청 처리 중 오류가 발생하였습니다.");
        			}
        		});
        	}
        }
        function chkNull(el, msg){
        	if(el.value == "" || $.trim(el.value) == ""){
        		el.focus();
        		alert(msg);
        		return false;
        	}
        	return true;        	
        }
        function chkImageExt(el,msg){
        	if(el.value.length > 0)
        	{
        		var ext = el.value.substring(el.value.length - 3,el.value.length).toLocaleLowerCase();
        		if(/gif|jpg|png/.test(ext)) return true;
        	}
        	alert(msg);
        	el.focus();
        	return false;
        }
        function noticeCancel(){
        	location.href='#/event/eventIndex.af';
        }
       
        </script>
        <script type="text/javascript">
            $('.customized-select').customSelect();
            inputlabel.align();
            placeHFn.align();
            initFileUploads('btn');
            /* Datepicker */
            $(".datepicker").datepicker({
                showOn: "both",
                buttonImage: "/common/front/images/ico/ico_calen.png",
                buttonImageOnly: true,
                buttonText: "Select date",
                dateFormat: "yy.mm.dd",
                onSelect: function(event) {
                    $(this).focus();
                }
            });

            $('.inputOption').each(function(i, v) {
                var checkbox = $(this).find("input[type=checkbox]"),
                    leftSelect = $(this).find(".select_left"),
                    rightSelect = $(this).find(".select_right"),
                    page = i+1;
                    textArea =  $('.page'+page).find('.textArea').html();
                    //fileArea =  $('.page'+page).find('.fileArea').html();
                    fileArea =  '<div class="fileinputs" data-text="파일첨부"><input type="file" class="file" name="" id=""></div>'
                
                checkbox.change(function(e) {
                    if($(this).prop("checked") == true) {
                        $('.page'+page).addClass('active');
                    }else{
                        $('.page'+page).removeClass('active');
                    }
                    $('.iScrollVerticalScrollbar').remove();
                    contentBox.isc = new IScroll($('.iscContent').selector+' > .inner', {
                        scrollbars: 'custom',
                        mouseWheel: true,
                        disableMouse: true
                    });
                });

                leftSelect.change(function(e) {
                    $('.page'+page).find('.left').html('');
                    if($(this).val() == 'text'){
                        $('.page'+page).find('.left').append('<div class="textArea">'+textArea+'</div>');
                        $('.page'+page).find('.left').find("textarea").attr({
                			"id": "page" + page + "LeftContent",
                			"name": "page" + page + "LeftContent"
                        });
                    }else if($(this).val() == 'image') {
                        $('.page'+page).find('.left').append('<div class="fileArea">'+fileArea+'</div>');
                        $('.page'+page).find('.left').find("input").attr({
                			"id": "page" + page + "LeftImg",
                			"name": "page" + page + "LeftImg"
                        });
                        initFileUploads('btn');
                    }
                });
                rightSelect.change(function(e) {
                    $('.page'+page).find('.right').html('');
                    if($(this).val() == 'text'){
                        $('.page'+page).find('.right').append('<div class="textArea">'+textArea+'</div>');
                        $('.page'+page).find('.right').find("textarea").attr({
                			"id": "page" + page + "RightContent",
                			"name": "page" + page + "RightContent"
                        });
                    }else if($(this).val() == 'image') {
                        $('.page'+page).find('.right').append('<div class="fileArea">'+fileArea+'</div>');
                        $('.page'+page).find('.right').find("input").attr({
                			"id": "page" + page + "RightImg",
                			"name": "page" + page + "RightImg"
                        });
                        initFileUploads('btn');
                    }
                });
                    
            });

            $('#writeType').change(function(e) {
                if($(this).val() == 1) {
                    $('.eventOption').css('display', 'none');
                }else{
                    $('.eventOption').css('display', 'table-row');
                }
            });
            $('#writeType').trigger('change');
        </script>
    </section>