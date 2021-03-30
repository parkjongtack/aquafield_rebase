<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../common/taglibs.jsp" %>
<script type="text/javascript" src="/common/front/js/jquery.form.min.js"></script>
<script type="text/javascript" src="/common/editor/js/HuskyEZCreator.js" charset="utf-8"></script>
    <section>
        <header>
        	<h1>
                뉴스 게시글 관리
                <p>검색기능을 사용하여 문의사항을 빠르게 찾아볼 수 있습니다.</p>
            </h1>
        </header>
        <form name="form" id="form" method="post" action="" enctype="multipart/form-data">
        <input type="hidden" name="mode" value="inst" />
        <input type="hidden" name="pageType" value="1" />
        <div class="iscContent">
        	<div class="inner">
        		<article>
        			<div class="tb_type1 col s6">
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
                                    <td colspan="3">
                                        <div class="fileinputs" data-text="파일첨부">
                                            <input type="file" class="file" name="fileList" id="fileList">
                                        </div>
                                        <%-- 
                                        <div class="lst_Upload">
                                            <span class="tag" id="span-banner_1">Event.jpg<button onclick="deleteAttach('10', 'banner', '1');" alt="삭제하기" title="삭제하기">삭제</button></span>
                                        </div>
                                        --%>
                                    </td>
                                </tr>
                                <tr>
                                    <th>내용</th>
                                    <td colspan="3">
                                        <div class="bx_editer">
                                            <textarea name="contents" id="contents" rows="10" cols="100"></textarea>
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
                <button type="button" class="btn_pack btn_mo gray" onclick="window.newsPop = new NewsPopFn({data:{idx:'6', type:'notice'}});">미리보기</button>
                <button type="button" class="btn_pack btn_mo gray" onclick="noticeCancel();">취소</button>
            </div>
        </footer>
        </form>
        <script type="text/javascript">
        function doSave(){
        	oEditors.getById["contents"].exec("UPDATE_CONTENTS_FIELD", []);
        	var f = document.form;
        	if(f.kind.value == "2"){
        		if(!chkNull(f.startDate, "시작기간을 선택하세요.")) return false;
        		if(!chkNull(f.endDate, "종료기간을 선택하세요.")) return false;
        		if(!chkNull(f.location, "장소를 입력하세요.")) return false;
        	}
        	if(!chkNull(f.title, "제목을 입력하세요.")) return false;
        	if(!chkNull(f.fileList, "대표이미지를 선택하세요.")) return false;
        	if(!chkImageExt(f.fileList,"대표이미지는 gif,jpg,png 만 등록가능 합니다.")) return false;
        	if(!chkNull(f.contents, "내용을 입력하세요.")) return false;
        	
        	if(confirm("등록하시겠습니까?"))
        	{
        		if(f.kind.value == "1"){
        			$("#startDate, #endDate, #location").val("");
        		}
        		f.action = "event/noticeEventWriteAction.af";
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
        	location.href='#/event/eventIndex.af?'+new Date().getTime();
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

            $('#writeType').change(function(e) {
                if($(this).val() == 1) {
                    $('.eventOption').css('display', 'none');
                }else{
                    $('.eventOption').css('display', 'table-row');
                }
            });
            $('#writeType').trigger('change');            
        </script>
<script type="text/javascript">
var oEditors = [];

// 추가 글꼴 목록
//var aAdditionalFontSet = [["MS UI Gothic", "MS UI Gothic"], ["Comic Sans MS", "Comic Sans MS"],["TEST","TEST"]];
$(function(){
	nhn.husky.EZCreator.createInIFrame({
		oAppRef: oEditors,
		elPlaceHolder: "contents",
		sSkinURI: "/common/editor/SmartEditor2Skin.jsp",
		htParams : {			
			bUseToolbar : true,				// 툴바 사용 여부 (true:사용/ false:사용하지 않음)
			bUseVerticalResizer : true,		// 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
			bUseModeChanger : true,			// 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
			//aAdditionalFontList : aAdditionalFontSet,		// 추가 글꼴 목록
			fOnBeforeUnload : function(){
				//alert("완료!");
			}
		}, //boolean
		fOnAppLoad : function(){
			//예제 코드
			//oEditors.getById["contents"].exec("PASTE_HTML", ["로딩이 완료된 후에 본문에 삽입되는 text입니다."]);
			 //$("#content").find("iframe").css("width", "488px");			 
			 //$("#smart_editor2", parent.frames[0].document).css("width", "488px");
			 oEditors.registerPlugin(new nhn.husky.SE_WYSIWYGEnterKey("BR")); 
			 

		},
		fCreator: "createSEditor2"
	});
})
function pasteHTML(sHTML) {
	//var sHTML = "<span style='color:#FF0000;'>이미지도 같은 방식으로 삽입합니다.<\/span>";
	oEditors.getById["contents"].exec("PASTE_HTML", [sHTML]);
}

function showHTML() {
	return oEditors.getById["contents"].getIR();
}
	
function submitContents(elClickedObj) {
	oEditors.getById["contents"].exec("UPDATE_CONTENTS_FIELD", []);	// 에디터의 내용이 textarea에 적용됩니다.
	
	// 에디터의 내용에 대한 값 검증은 이곳에서 document.getElementById("contents").value를 이용해서 처리하면 됩니다.
	
	try {
		elClickedObj.form.submit();
	} catch(e) {}
}

function setDefaultFont() {
	var sDefaultFont = '돋움';
	var nFontSize = 9;
	oEditors.getById["contents"].setDefaultFont(sDefaultFont, nFontSize);
}
</script>	
    </section>