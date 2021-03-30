<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../common/taglibs.jsp" %>
<!--
<link rel="stylesheet" href="/common/front/css/slick-theme.css">
-->
<script type="text/javascript">
    $( document ).ready(function() {
        var point = $('[name="pointUrl"]').attr('value');
        var now = new Date();
        var year= now.getFullYear();
        var mon = (now.getMonth()+1)>9 ? ''+(now.getMonth()+1) : '0'+(now.getMonth()+1);
        var day = now.getDate()>9 ? ''+now.getDate() : '0'+now.getDate();
        var chan_val = year +  mon + day;

        var filter = "win16|win32|win64|mac|macintel";
/*
        $('.popSlider').slick({
            lazyLoad: 'progressive',
            autoplay: true,
            autoplaySpeed: 3000
        });
*/
        var mobileYn = false;
        if ( navigator.platform ) {
            if ( filter.indexOf( navigator.platform.toLowerCase() ) < 0 ) { //mobile
                mobileYn = true;
            } else { //pc
                mobileYn = false;
            }

        }

        if(point == '/goyang/index.af') {

            if(mobileYn){
                if(getCookie('goyang20200304') != 'Y') {
                    $("#goyang20200304").css("display","block");
                }

            }else{
                if(getCookie('goyang20200304') != 'Y') {
                    $("#goyang20200304").css("display","block");
                }

            }
            /*if(mobileYn){
               if(getCookie('aqua_server2') != 'Y') {
                   $("#aqua_server2").css("display","block");
               }
               
           }else{
               if(getCookie('aqua_server2') != 'Y') {
                   $("#aqua_server2").css("display","block");
               }
           }
           */

            //window.open('/common/front/popup_180627_point03.jsp','pop','width=450,height=450,top=50,left=150'); 공지
            //window.open('/common/front/popup_point03_choo.jsp','pop','width=450,height=450,top=50,left=150');  //추석
            //window.open('/common/front/popup_point03.jsp','pop2','width=302,height=240,top=50,left=650');  //연간이용권
            //window.open('/common/front/popup_180827.jsp','pop1','width=450,height=450,top=50,left=650');
            //window.open('/common/front/popup_server.jsp','pop1','width=435,height=487,top=50,left=650');  //서버점검
        } else if(point == '/hanam/index.af'){
            /*
            if(mobileYn){
                if(getCookie('hanam20190422') != 'Y') {
                    $("#hanam20190422").css("display","block");
                }
                
            }else{
                if(getCookie('hanam20190422') != 'Y') {
                    $("#hanam20190422").css("display","block");
                }
            }
            */
            /* if(mobileYn){
                if(getCookie('aqua_server1') != 'Y') {
                    $("#aqua_server1").css("display","block");
                }
                
            }else{
                if(getCookie('aqua_server1') != 'Y') {
                    $("#aqua_server1").css("display","block");
                }
            } */
            /*
                        if(mobileYn){
                            if(getCookie('hanam20190920') != 'Y') {
                                $("#hanam20190920").css("display","block");
                            }
                            
                        }else{
                            if(getCookie('hanam20190920') != 'Y') {
                                $("#hanam20190920").css("display","block");
                            }
                        }
            */
            if(mobileYn){
                if(getCookie('hanam20200304') != 'Y') {
                    $("#hanam20200304").css("display","block");
                }

            }else{
                if(getCookie('hanam20200304') != 'Y') {
                    $("#hanam20200304").css("display","block");
                }
            }

            //window.open('/common/front/popup_180726_point01.jsp','pop','width=450,height=450,top=50,left=150');
            //window.open('/common/front/popup_point01_choo.jsp','pop','width=450,height=450,top=50,left=150');  //추석
            //window.open('/common/front/popup_server.jsp','pop1','width=435,height=487,top=50,left=650');  //서버점검
            //window.open('/common/front/popup_20190920.jsp','pop1','width=700,height=700,top=50,left=650');
        }

    });

    function getCookie(name) {
        name += "=";
        var arr = decodeURIComponent(document.cookie).split(';');
        for (var i = 0; i < arr.length; i++) {
            var c = arr[i];
            while (c.charAt(0) == ' ') c = c.substring(1);
            if (c.indexOf(name) == 0) return c.substring(name.length, c.length);
        }
        return "";
    }

    function setCookie(name, value, expiredays) {
        var today = new Date();
        today.setDate(today.getDate() + expiredays);
        document.cookie = name + '=' + escape(value) + '; path=/; expires=' + today.toGMTString() + ';'
    }
    function closePop(name) {
        setCookie(name, 'Y', 1);
        $("#"+name).css("display","none");
    }
</script>
<c:if test="${POINT_CODE eq 'POINT01'}">
    <!--
    <div class="layer-wrap" id="hanam20190422" style="display: none;">
        <div class="layerbg"></div>
        <div class="pop-layer size-720">
            <div class="pop-container">
                <div class="pop-conts">
                    <div class="popup">
                        <img src="/common/front/images/POINT01/main/hanam20190422.jpg" alt="공지사항 팝업">
                    </div>
                    <div class="button-area">
                        <div class="checkArea">
                            <input class="checkbox" id="hanam20190422c" type="checkbox" onclick="closePop('hanam20190422');" checked>
                            <label for="hanam20190422c">오늘 하루 열지 않기</label>
                        </div>
                        <div class="close-button">
                               <a class="pop-close" href="javascript:;" onclick="$('#hanam20190422').css('display','none');return false;" >닫기</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
     -->
    <div class="layer-wrap" id="hanam20200304" style="display: none;">
        <div class="layerbg"></div>
        <div class="pop-layer size-720">
            <div class="pop-container">
                <div class="pop-conts">
                    <div class="popup">
                        <img src="/common/front/images/popup/hanam_20200304.jpg" alt="아쿠아필드 하남 운영안내">
                    </div>
                    <div class="button-area">
                        <div class="checkArea">
                            <input class="checkbox" id="hanam20200304c" type="checkbox" onclick="closePop('hanam20200304');" checked>
                            <label for="hanam20200304c">오늘 하루 열지 않기</label>
                        </div>
                        <div class="close-button">
                            <a class="pop-close" href="javascript:;" onclick="$('#hanam20200304').css('display','none');return false;" >닫기</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
  <!--
    <div class="layer-wrap" id="hanam20190920" style="display: none;">
        <div class="layerbg"></div>
        <div class="pop-layer size-720">
            <div class="pop-container">
                <div class="pop-conts">
                    <div class="popup">
                        <img src="/common/front/images/POINT01/main/popup_20190920.jpg" alt="공지사항 팝업">
                    </div>
                    <div class="button-area">
                        <div class="checkArea">
                            <input class="checkbox" id="hanam20190920c" type="checkbox" onclick="closePop('hanam20190920');" checked>
                            <label for="hanam20190920c">오늘 하루 열지 않기</label>
                        </div>
                        <div class="close-button">
                            <a class="pop-close" href="javascript:;" onclick="$('#hanam20190920').css('display','none');return false;" >닫기</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <div class="layer-wrap" id="aqua_server1" style="display: none;/* left: -436px; */">
        <div class="layerbg"></div>
        <div class="pop-layer size-720">
            <div class="pop-container">
                <div class="pop-conts">
                    <div class="popup">
                        <img src="/common/front/images/main/aqua_server.jpg" alt="서버점검 팝업">
                    </div>
                    <div class="button-area">
                        <div class="checkArea">
                            <input class="checkbox" id="aqua_server1c" type="checkbox" onclick="closePop('aqua_server1');" checked>
                            <label for="aqua_server1c">오늘 하루 열지 않기</label>
                        </div>
                        <div class="close-button">
                            <a class="pop-close" href="javascript:;" onclick="$('#aqua_server1').css('display','none');return false;" >닫기</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    -->
</c:if>
<c:if test="${POINT_CODE eq 'POINT03'}">
    
    <div class="layer-wrap" id="goyang20200304" style="display: none;">
        <div class="layerbg"></div>
        <div class="pop-layer slider size-720">
            <div class="pop-container">
                <div class="pop-conts">
                    <div class="popup">
                        <img src="/common/front/images/popup/goyang_20200304.jpg" alt="아쿠아필드 하남 운영안내">
<!--
                        <div class="popSlider">
                            <div><img src="/common/front/images/POINT03/main/goyang200226.jpg" alt="찜질스파 운연시간 안내"></div>
                            <div><img src="/common/front/images/POINT03/main/goyang200226-1.jpg" alt="서버점검 팝업"></div>
                        </div>
-->
                    </div>
                    <div class="button-area">
                        <div class="checkArea">
                            <input class="checkbox" id="goyang20200304c" type="checkbox" onclick="closePop('goyang20200304');" checked>
                            <label for="goyang20200304c">오늘 하루 열지 않기</label>
                        </div>
                        <div class="close-button">
                            <a class="pop-close" href="javascript:;" onclick="$('#goyang20200304').css('display','none');return false;" >닫기</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script>
            $(document).ready(function () {
            });
        </script>
    </div>
    
    <!--<div class="layer-wrap" id="aqua_server2" style="display: none; left: -436px;">
		<div class="layerbg"></div>
		<div class="pop-layer size-720">
			<div class="pop-container">
				<div class="pop-conts">
					<div class="popup">
						<img src="/common/front/images/main/aqua_server.jpg" alt="서버점검 팝업">
					</div>
					<div class="button-area">
						<div class="checkArea">
							<input class="checkbox" id="aqua_server2c" type="checkbox" onclick="closePop('aqua_server2');" checked>
							<label for="aqua_server2c">오늘 하루 열지 않기</label>
						</div>
						<div class="close-button">
				       		<a class="pop-close" href="javascript:;" onclick="$('#aqua_server2').css('display','none');return false;" >닫기</a>
				        </div>
					</div>
				</div>
			</div>
		</div>
	</div>-->
</c:if>
<div class="contentArea">

</div>
<div class="contentArea">

</div>

<div id="content_change_btns" class="hide">
    <button class="btn_prev"><span>이전</span></button>
    <button class="btn_next"><span>다음</span></button>
</div>
