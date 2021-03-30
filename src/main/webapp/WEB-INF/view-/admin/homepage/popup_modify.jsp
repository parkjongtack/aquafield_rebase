<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../common/taglibs.jsp" %>
<script type="text/javascript" src="/common/front/js/jquery.form.min.js"></script>
<script type="text/javascript" src="/common/editor/js/HuskyEZCreator.js" charset="utf-8"></script>
<section id="contents">
	<div class="contents_bx_type1">
		<div class="contents_bx_inner">
			<div id="path" class="fixed">
				<div class="pathInner">
					<h2>팝업 관리</h2>
					<div class="btns_area">
						<button class="btn_pack btn_mo d_gray" onclick="doSave();"><img src="/admaf/images/common/ico_save.png">저장</button>
						<a href="#list" class="btn_pack btn_mo gray" onclick="goList(); return false;"><img src="/admaf/images/common/ico_list.png">목록</a>
						<!-- <a href="#" class="btn_pack btn_mo gray" id="delInfo"><img src="/admaf/images/common/ico_del.png">삭제</a> -->
					</div>
				</div>
			</div>
			<form name="form" id="form" method="post" action="terms_write_action.af" onsubmit="return false;">
			<c:choose>
				<c:when test="${empty result}"><input type="hidden" name="mode" id="mode" value="inst"></c:when>
				<c:otherwise><input type="hidden" name="mode" id="mode" value="updt"></c:otherwise>
			</c:choose>
			<input type="hidden" name="num" id="num" value="${resultParam.num}">
			<input type="hidden" name="page" id="page" value="${resultParam.page}">
				<div class="tb_type2">
					<h3>팝업관리</h3>
					<table>
						<tbody>
							<tr>
								<th>제	목<span class="red">*</span></th>
								<td colspan="3"><input type="text" name="title" class="ipt2 w100p" value="${result.TITLE}" /></td>
							</tr>
							<tr>
								<th>게시일</th>
								<td colspan="3">
								<input type="text" name="start_date" value="${resultParam.srch_reg_s }" class="ipt2 datepicker"/> 
									~ <input type="text" name="end_date" value="${resultParam.srch_reg_e }" class="ipt2 datepicker"/>
								</td>
							</tr>
							<tr>
								<th>여	백</th>
								<td>
								왼쪽 : <input name="left_m" type="text" class="ipt2 w60" id="left_m" value="${list.LEFTM }" title="왼쪽여백 입력"
								style="ime-mode:disabled" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')"> 
								위쪽 : <input name="top_m" type="text" class="ipt2 w60" id="top_m" value="${list.TOPM }" title="위쪽여백 입력"
								style="ime-mode:disabled" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')">
								</td>
								<th>크기</th>
								<td>
								가로 : <input name="width" type="text" class="ipt2 w60" id="width" value="${list.WIDTH }" title="가로 입력"
								style="ime-mode:disabled" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')"> 
								세로 : <input name="height" type="text" class="ipt2 w60" id="heigth" value="${list.HEIGHT }" title="세로 입력"
								style="ime-mode:disabled" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')">
								</td>
							</tr>
							<tr>
								<th>우선순위</th>
								<td>
									<input type="text" name="print_order" class="ipt2 w100p" value="${result.TERMS_TITLE}" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')"/>
									<!-- <span id="print_state" class="fc_red fc_red_hidden">우선순위 중복</span> -->
								</td>
								<th>사용여부</th>
								<td>
									<div class="lst_check radio">
                                        <span <c:if test="${empty resultParam.category || resultParam.category eq '' }">class="on"</c:if>>
                                            <label>
                                            	사용<input type="radio" name="use_yn" id="use_yn" onclick="checkradio(this);" value="Y" checked>
                                            </label>
                                        </span>
                                        <span>
                                            <label>
                                            	미사용<input type="radio" name="use_yn" id="use_yn" onclick="checkradio(this);" value="N" >
                                            </label>
                                        </span>
                                    </div>
								</td>
							</tr>
							<tr>
								<th>내용표시</th>
								<td colspan="3">
									 <div class="lst_check radio">
                                        <span <c:if test="${empty resultParam.category || resultParam.category eq '' }">class="on"</c:if>>
                                            <label>
                                            	이미지<input type="radio" name="content_type" id="content_type" onclick="checkradio(this);" value="1" checked>
                                            </label>
                                        </span>
                                        <span>
                                            <label>
                                            	에디터<input type="radio" name="content_type" id="content_type" onclick="checkradio(this);" value="2" >
                                            </label>
                                        </span>
                                    </div>
								</td>
							</tr>
							<tr>
								<th>내	용</th>
								<td colspan="3">
									<div class="imgUpload">
                                        <div class="imginputs pics" data-text="파일첨부">
                                            <input type="file" class="file" name="file_content" id="file_content" onchange="readURL(this, 2);" />
                                            <div class="fakeimg">
                                                <div id="imgArea" class="imgArea"></div>
                                                <button type="button">파일첨부</button>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="bx_editer">
                                        <textarea name="pop_contents" id="pop_contents" rows="20" cols="100"></textarea>
                                    </div>
								</td>
							</tr>
							<tr>
								<th>링	크</th>
								<td colspan="3"><input type="text" name="link_url" class="ipt2 w100p" value="${result.TERMS_TITLE}" /></td>
							</tr>
						</tbody>
					</table>
				</div>
			</form>
		</div>
	</div>
</section>
<script type="text/javascript">
//우선순위 중복확인
$("#print_order").focusout(function(){
	$.ajax({
        type:"post",
        url:"/secu_admaf/admin/homepage/popupModifyPringOrder.af",
        ansync : false,
        data : $("#form").serialize(),
        dataType:"html",
        success : function(data) {
        	if(data=='Y'){
        		$("#print_state").addClass("fc_red_hidden");
        	}
        	if(data == 'N'){
        		if(eval($("#print_order").val()) != eval('${list.PRINT_ORDER}')){
            		$("#print_state").removeClass("fc_red_hidden");
        		}else {
        			$("#print_state").addClass("fc_red_hidden");
				}
        	}
        },
        complete : function(data) {},
        error : function(xhr, status, error) {
         	alert(error);
        }
   });
});

$(':radio[name="content_type"]').change(function(e) {
    var _val = $(':radio[name="content_type"]:checked').val();
    if(_val == 1) {
        $('.bx_editer').css('display', 'none');
        $('.imgUpload').css('display', 'block');
    }else{
        $('.bx_editer').css('display', 'block');
        $('.imgUpload').css('display', 'none');
    }
    $(window).resize();
});
$(':radio[name="content_type"]').trigger('change');

//파일 읽어들이기
function readURL(input){
    var isIE = (navigator.appName=="Microsoft Internet Explorer");
    var path = input.value;
    var ext = path.substring(path.lastIndexOf('.') + 1).toLowerCase();
    
    if(path == ""){
        $(input).val('');
        $('#imgArea').html('');
        $(window).resize();
       return;
    }
    if($.inArray(ext, ['gif','png','jpg','jpeg']) == -1) {
        alert('gif,png,jpg,jpeg 파일만 업로드 할수 있습니다.');
        $(input).val('');
         $('#imgArea').html('');
         $(window).resize();
        return;
    }

    if(isIE) {
        var img = '<img src="'+path+'" class="img"/>'
        $('#imgArea').html(img);
        $(window).resize();
    }else{
         if (input.files && input.files[0]) {
            var reader = new FileReader(); //파일을 읽기 위한 FileReader객체 생성
            reader.onload = function (e) {
                //파일 읽어들이기를 성공했을때 호출되는 이벤트 핸들러
                var img = '<img src="'+e.target.result+'" class="img"/>'
                $('#imgArea').html(img);
                //이미지 Tag의 SRC속성에 읽어들인 File내용을 지정
                //(아래 코드에서 읽어들인 dataURL형식)
                $(window).resize();
            }                   
            reader.readAsDataURL(input.files[0]);
            //File내용을 읽어 dataURL형식의 문자열로 저장
         }
            
    }
}

//목록 이동
function goList(){
	var thisForm				= document.form;
	thisForm.action="/secu_admaf/admin/homepage/popupIndex.af";
	thisForm.submit();

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

//등록
function doSave(){
    oEditors.getById["pop_contents"].exec("UPDATE_CONTENTS_FIELD", []);
    var f = document.form;
    if(!chkNull(f.title, "제목을 입력하세요.")) return false;
    if($(':radio[name="content_type"]:checked').val() == 1){
        if(!chkNull(f.file_content, "본문이미지를 선택하세요.")) return false;
        if(!chkImageExt(f.file_content,"본문이미지는 gif,jpg,png 만 등록가능 합니다.")) return false;
    }
    else{
        if(!chkNull(f.pop_contents, "내용을 입력하세요.")) return false;
    }
    
    if(confirm("등록하시겠습니까?"))
    {
        f.action = "/secu_admaf/admin/homepage/popupWriteAction.af";
        $("#form").ajaxSubmit({
            success : function(obj) {
                var data = $.parseJSON(obj);
                if(data.result){
                    alert(data.msg);
                    location.href="/secu_admaf/admin/homepage/popup_index.af";
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

//placeHFn.align();
</script>
<script type="text/javascript">
var oEditors = [];

// 추가 글꼴 목록
//var aAdditionalFontSet = [["MS UI Gothic", "MS UI Gothic"], ["Comic Sans MS", "Comic Sans MS"],["TEST","TEST"]];
$(function(){
    nhn.husky.EZCreator.createInIFrame({
        oAppRef: oEditors,
        elPlaceHolder: "pop_contents",
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
    oEditors.getById["pop_contents"].exec("PASTE_HTML", [sHTML]);
}

function showHTML() {
    return oEditors.getById["pop_contents"].getIR();
}
    
function submitContents(elClickedObj) {
    oEditors.getById["pop_contents"].exec("UPDATE_CONTENTS_FIELD", []); // 에디터의 내용이 textarea에 적용됩니다.
    
    // 에디터의 내용에 대한 값 검증은 이곳에서 document.getElementById("CONTENTS").value를 이용해서 처리하면 됩니다.
    
    try {
        elClickedObj.form.submit();
    } catch(e) {}
}

function setDefaultFont() {
    var sDefaultFont = '돋움';
    var nFontSize = 9;
    oEditors.getById["pop_contents"].setDefaultFont(sDefaultFont, nFontSize);
}
</script>   
<script type="text/javascript">
	$("#delInfo").on("click",function(e) {
		e.preventDefault()
		if(confirm("정말 삭제하시겠습니까? 되돌릴수 없습니다.")) {
			$.ajax({
				 type: "post"
				,url : "terms_delete_action.af"
				,data : {num : "${resultParam.num}", tt : "${resultParam.tt}", title : "${result.TERMS_TITLE}"}
				,dataType : "json"
				,success: function(data){
					if(data.result == true){
						alert(data.msg);
						goList();
					}
					else{
						alert(data.msg);
					}
				}
				,error: function(xhr, option, error){
					alert("오류가 있습니다. 잠시후에 다시하세요");
				}
			});
		}
	});
</script>