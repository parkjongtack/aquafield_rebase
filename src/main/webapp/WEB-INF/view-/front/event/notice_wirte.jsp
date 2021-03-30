<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../common/taglibs.jsp" %>
<script type="text/javascript" src="/common/front/js/jquery.form.min.js"></script>
<script type="text/javascript" src="/common/editor/js/HuskyEZCreator.js" charset="utf-8"></script>
    <section>
        <div class="miniTit">
            <h1>글쓰기</h1>
            <ul>
                <li>HOME</li>
                <li>NEWS</li>
                <li>WIRTE</li>
            </ul>
        </div>
        <form name="form" id="form" method="post" action="" enctype="multipart/form-data">
        <input type="hidden" name="mode" value="inst" />
        <input type="hidden" name="POINT_CODE" id="POINT_CODE" value="${pointCode }" />
        <div class="iscContent">
            <div class="inner">
                <div class="tb_type1 col s6">
                    <table>
                        <tbody>
                            <tr class="require">
                                <th>분   류<span>*</span></th>
                                <td>
                                    <div class="chk_motion lst_check radio">
                                        <span>
                                            <label>
                                                공지사항<input type="radio" name="KIND" id="writeType" value="1" checked="">
                                            </label>
                                        </span>
                                        <span>
                                            <label>
                                                이벤트<input type="radio" name="KIND" id="writeType" value="2">
                                            </label>
                                        </span>
                                    </div>
                                </td>
                            </tr>
                            <tr class="require">
                                <th>노출기간<span>*</span></th>
                                <td>
                                    <div class="placeh">
                                        <input type="text" class="ipt w160 datepicker" name="VIEW_START_DATE" id="VIEW_START_DATE">
                                    </div>
                                        <span> ~ </span>
                                    <div class="placeh">
                                        <input type="text" class="ipt w160 datepicker" name="VIEW_END_DATE" id="VIEW_END_DATE">
                                        <label for="VIEW_END_DATE">미입력 시 무제한</label>
                                    </div>
                                </td>
                            </tr>
                            <tr class="require">
                                <th>진행기간<span></span></th>
                                <td>
                                    <div class="placeh">
                                        <input type="text" class="ipt w160 datepicker" name="START_DATE" id="START_DATE">
                                    </div>
                                        <span> ~ </span>
                                    <div class="placeh">
                                        <input type="text" class="ipt w160 datepicker" name="END_DATE" id="END_DATE">
                                        <label for="END_DATE">미입력 시 무제한</label>
                                    </div>
                                </td>
                            </tr>
                            <tr class="require">
                                <th>제   목<span>*</span></th>
                                <td>
                                    <input type="text" class="ipt mw430" name="TITLE" onblur="" maxlength="200">
                                </td>
                            </tr>
                            <tr class="require">
                                <th>썸   네  일<span>*</span><br/>(300x240)</th>
                                <td>
                                    <div class="imginputs" data-text="파일첨부">
                                        <input type="file" class="file" name="FILE_LIST" id="FILE_LIST" onchange="readURL(this, 1);">
                                        <div class="fakeimg">
                                            <div id="imgArea_1" class="imgArea"></div>
                                            <button type="button">파일첨부</button>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                            <tr class="require">
                                <th>본문내용<span>*</span><br/>(800x∞)</th>
                                <td>
                                    <div class="chk_motion lst_check radio" style="margin-bottom: 10px;">
                                        <span>
                                            <label>
                                                이미지<input type="radio" name="WRITE_TYPE" id="WRITE_TYPE" value="1" checked="">
                                            </label>
                                        </span>
                                        <span>
                                            <label>
                                                에디터<input type="radio" name="WRITE_TYPE" id="WRITE_TYPE" value="2" >
                                            </label>
                                        </span>
                                    </div>
                                    <div class="imgUpload">
                                        <div class="imginputs pics" data-text="파일첨부">
                                            <input type="file" class="file" name="FILE_CONTENT" id="FILE_CONTENT" onchange="readURL(this, 2);">
                                            <div class="fakeimg">
                                                <div id="imgArea_2" class="imgArea"></div>
                                                <button type="button">파일첨부</button>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="bx_editer">
                                        <textarea name="CONTENTS" id="CONTENTS" rows="20" cols="100"></textarea>
                                    </div>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <footer>
            <div class="btns center">
                <button type="button" class="btn_pack btn_mo blue" onclick="doSave()">저장</button>
                <button type="button" class="btn_pack btn_mo gray preview">미리보기</button>
                <button type="button" class="btn_pack btn_mo gray" onclick="noticeCancel();">취소</button>
            </div>
        </footer>
        </form>
        <script type="text/javascript">
            $('button.preview').click(function(e) {             
                window.newsPop = new NewsPopFn({data:{num:0, type:'preview'}});
            });

            $('.customized-select').customSelect();
            inputlabel.align();
            placeHFn.align();
            initFileUploads('btn');
            setChkAndRadio();
            
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

            $(':radio[name="WRITE_TYPE"]').change(function(e) {
                var _val = $(':radio[name="WRITE_TYPE"]:checked').val();
                if(_val == 1) {
                    $('.bx_editer').css('display', 'none');
                    $('.imgUpload').css('display', 'block');
                }else{
                    $('.bx_editer').css('display', 'block');
                    $('.imgUpload').css('display', 'none');
                }
                $(window).resize();
            });
            $(':radio[name="WRITE_TYPE"]').trigger('change');

            //파일 읽어들이기
            function readURL(input, section){
                var isIE = (navigator.appName=="Microsoft Internet Explorer");
                var path = input.value;
                var ext = path.substring(path.lastIndexOf('.') + 1).toLowerCase();
                
                if(path == ""){
                    $(input).val('');
                    $('#imgArea_' + section).html('');
                    $(window).resize();
                   return;
                }
                if($.inArray(ext, ['gif','png','jpg','jpeg']) == -1) {
                    alert('gif,png,jpg,jpeg 파일만 업로드 할수 있습니다.');
                    $(input).val('');
                     $('#imgArea_' + section).html('');
                     $(window).resize();
                    return;
                }

                if(isIE) {
                    var img = '<img src="'+path+'" class="img"/>'
                    $('#imgArea_' + section).html(img);
                    $(window).resize();
                }else{
                     if (input.files && input.files[0]) {
                        var reader = new FileReader(); //파일을 읽기 위한 FileReader객체 생성
                        reader.onload = function (e) {
                            //파일 읽어들이기를 성공했을때 호출되는 이벤트 핸들러
                            var img = '<img src="'+e.target.result+'" class="img"/>'
                            $('#imgArea_' + section).html(img);
                            //이미지 Tag의 SRC속성에 읽어들인 File내용을 지정
                            //(아래 코드에서 읽어들인 dataURL형식)
                            $(window).resize();
                        }                   
                        reader.readAsDataURL(input.files[0]);
                        //File내용을 읽어 dataURL형식의 문자열로 저장
                     }
                        
                }
            }


        </script>
        <script type="text/javascript">
        function doSave(){
            oEditors.getById["CONTENTS"].exec("UPDATE_CONTENTS_FIELD", []);
            var f = document.form;
            if(!chkNull(f.VIEW_START_DATE, "노출기간을 시작일을 선택하세요.")) return false;
            //if(!chkNull(f.START_DATE, "진행기간을 시작일을 선택하세요.")) return false;
            if(!chkNull(f.TITLE, "제목을 입력하세요.")) return false;
            if(!chkNull(f.FILE_LIST, "썸네일을 선택하세요.")) return false;
            if(!chkImageExt(f.FILE_LIST,"썸네일은 gif,jpg,png 만 등록가능 합니다.")) return false;
            if($(':radio[name="WRITE_TYPE"]:checked').val() == 1){
                if(!chkNull(f.FILE_CONTENT, "본문이미지를 선택하세요.")) return false;
                if(!chkImageExt(f.FILE_CONTENT,"본문이미지는 gif,jpg,png 만 등록가능 합니다.")) return false;
            }
            else{
                if(!chkNull(f.CONTENTS, "내용을 입력하세요.")) return false;
            }
            
            
            
            if(confirm("등록하시겠습니까?"))
            {
                f.action = "/event/noticeEventWriteAction.af";
                $("#form").ajaxSubmit({
                    success : function(obj) {
                        var data = $.parseJSON(obj);
                        if(data.result){
                            alert(data.msg);
                            location.href="#/event/eventIndex.af?"+new Date().getTime()+"&pointCode="+$("#POINT_CODE").val();
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
            location.href='#/event/eventIndex.af?'+new Date().getTime()+"&pointCode="+$("#POINT_CODE").val();
        }
       
        </script>
<script type="text/javascript">
var oEditors = [];

// 추가 글꼴 목록
//var aAdditionalFontSet = [["MS UI Gothic", "MS UI Gothic"], ["Comic Sans MS", "Comic Sans MS"],["TEST","TEST"]];
$(function(){
    nhn.husky.EZCreator.createInIFrame({
        oAppRef: oEditors,
        elPlaceHolder: "CONTENTS",
        sSkinURI: "/common/editor/SmartEditor2Skin.jsp",
        htParams : {            
            bUseToolbar : true,             // 툴바 사용 여부 (true:사용/ false:사용하지 않음)
            bUseVerticalResizer : true,     // 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
            bUseModeChanger : true,         // 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
            //aAdditionalFontList : aAdditionalFontSet,     // 추가 글꼴 목록
            fOnBeforeUnload : function(){
                //alert("완료!");
            }
        }, //boolean
        fOnAppLoad : function(){
            //예제 코드
            //oEditors.getById["CONTENTS"].exec("PASTE_HTML", ["로딩이 완료된 후에 본문에 삽입되는 text입니다."]);
             //$("#content").find("iframe").css("width", "488px");      
             $("#smart_editor2", parent.frames[0].document).css("width", "100%");
             //oEditors.registerPlugin(new nhn.husky.SE_WYSIWYGEnterKey("BR")); 
             

        },
        fCreator: "createSEditor2"
    });
})
function pasteHTML(sHTML) {
    //var sHTML = "<span style='color:#FF0000;'>이미지도 같은 방식으로 삽입합니다.<\/span>";
    oEditors.getById["CONTENTS"].exec("PASTE_HTML", [sHTML]);
}

function showHTML() {
    return oEditors.getById["CONTENTS"].getIR();
}
    
function submitContents(elClickedObj) {
    oEditors.getById["CONTENTS"].exec("UPDATE_CONTENTS_FIELD", []); // 에디터의 내용이 textarea에 적용됩니다.
    
    // 에디터의 내용에 대한 값 검증은 이곳에서 document.getElementById("CONTENTS").value를 이용해서 처리하면 됩니다.
    
    try {
        elClickedObj.form.submit();
    } catch(e) {}
}

function setDefaultFont() {
    var sDefaultFont = '돋움';
    var nFontSize = 9;
    oEditors.getById["CONTENTS"].setDefaultFont(sDefaultFont, nFontSize);
}
</script>   
    </section>