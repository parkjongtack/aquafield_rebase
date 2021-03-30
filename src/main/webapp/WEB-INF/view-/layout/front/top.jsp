<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/taglibs.jsp" %>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no, target-densitydpi=device-dpi">


<!-- 20180319 추가 -->
<c:if test="${POINT_CODE eq 'POINT01'}">
	<title>아쿠아필드 하남│AQUAFIELD HANAM</title>
	<meta name="keywords" content="아쿠아필드,아쿠아필드하남,AQUAFIELD,HANAM,찜질스파,사우나,워터파크,인피니티풀,스타필드하남">
    <meta name="description" content="찜질스파와 워터파크가 결합된 도심 속 힐링체험공간. 스타필드 하남 3층">
    <meta name="naver-site-verification" content="02f5474843f0a32a701ebc4dbb30bf0fe3629d5c"/>
    <meta property="og:type" content="website">
    <meta property="og:title" content="아쿠아필드 하남│AQUAFIELD HANAM">
    <meta property="og:description" content="찜질스파와 워터파크가 결합된 도심 속 힐링체험공간. 스타필드 하남 3층">
    <meta property="og:image" content=https://www.aquafield-ssg.co.kr/common/front/images/main/Logo_Gate.png>
    <meta property="og:url" content="http://www.aquafield-ssg.co.kr/hanam/">
</c:if>
<c:if test="${POINT_CODE eq 'POINT03'}">
	<title>아쿠아필드 고양│AQUAFIELD GOYANG</title>
	<meta name="keywords" content="아쿠아필드,아쿠아필드고양,AQUAFIELD,GOYANG,찜질스파,사우나,루프탑풀,스타필드고양">
    <meta name="description" content="찜질스파와 루프탑풀이 결합된 도심 속 힐링체험공간. 스타필드 고양 4층">
    <meta name="naver-site-verification" content="02f5474843f0a32a701ebc4dbb30bf0fe3629d5c"/>
    <meta property="og:type" content="website">
    <meta property="og:title" content="아쿠아필드 고양│AQUAFIELD GOYANG">
    <meta property="og:description" content="찜질스파와 루프탑풀이 결합된 도심 속 힐링체험공간. 스타필드 고양 4층">
    <meta property="og:image" content=https://www.aquafield-ssg.co.kr/common/front/images/main/Logo_Gate.png>
    <meta property="og:url" content="http://www.aquafield-ssg.co.kr/goyang/">
</c:if>
<c:if test="${POINT_CODE eq 'POINT05'}">
	<title>아쿠아필드 안성│AQUAFIELD ANSEONG</title>
	<meta name="keywords" content="아쿠아필드,아쿠아필드고양,AQUAFIELD,ANSEONG,찜질스파,사우나,루프탑풀,스타필드안성">
    <meta name="description" content="찜질스파와 루프탑풀이 결합된 도심 속 힐링체험공간. 스타필드 안성 4층">
    <meta name="naver-site-verification" content="02f5474843f0a32a701ebc4dbb30bf0fe3629d5c"/>
    <meta property="og:type" content="website">
    <meta property="og:title" content="아쿠아필드 안성│AQUAFIELD ANSEONG">
    <meta property="og:description" content="찜질스파와 루프탑풀이 결합된 도심 속 힐링체험공간. 스타필드 안성 3층">
    <meta property="og:image" content=https://www.aquafield-ssg.co.kr/common/front/images/main/Logo_Gate.png>
    <meta property="og:url" content="http://www.aquafield-ssg.co.kr/anseong/">
    <link rel="stylesheet" href="/common/front/css/swiper.min.css">
</c:if>

<script src="/common/front/js/swiper.min.js"></script>
<link rel="stylesheet" type="text/css" href="/common/front/css/jquery-ui.min.css"/>
<link rel="stylesheet" type="text/css" href="/common/front/css/slick.css"/>
<link rel="stylesheet" type="text/css" href="/common/front/css/common.css"/>
<link rel="stylesheet" type="text/css" href="/common/front/css/content.css"/>
<%
String ua=request.getHeader("User-Agent").toLowerCase();
if(ua.matches("(?i).*(android|avantgo|bada\\/|blackberry|blazer|compal|elaine|fennec|ip(hone|od)|iris|kindle|mobile.+firefox|netfront|opera m(ob|in)i|palm( os)?|p(ixi|re)\\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\\.(browser|link)|vodafone|wap|windows ce|xda|xiino).*")||ua.substring(0,4).matches("(?i)1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\\-(n|u)|c55\\/|capi|ccwa|cdm\\-|cell|chtm|cldc|cmd\\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\\-s|devi|dica|dmob|do(c|p)o|ds(12|\\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\\-|_)|g1 u|g560|gene|gf\\-5|g\\-mo|go(\\.w|od)|gr(ad|un)|haie|hcit|hd\\-(m|p|t)|hei\\-|hi(pt|ta)|hp( i|ip)|hs\\-c|ht(c(\\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\\-(20|go|ma)|i230|iac( |\\-|\\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\\/)|klon|kpt |kwc\\-|kyo(c|k)|le(no|xi)|lg( g|\\/(k|l|u)|50|54|\\-[a-w])|libw|lynx|m1\\-w|m3ga|m50\\/|ma(te|ui|xo)|mc(01|21|ca)|m\\-cr|me(rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\\-2|po(ck|rt|se)|prox|psio|pt\\-g|qa\\-a|qc(07|12|21|32|60|\\-[2-7]|i\\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\\-|oo|p\\-)|sdk\\/|se(c(\\-|0|1)|47|mc|nd|ri)|sgh\\-|shar|sie(\\-|m)|sk\\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\\-|v\\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\\-|tdg\\-|tel(i|m)|tim\\-|t\\-mo|to(pl|sh)|ts(70|m\\-|m3|m5)|tx\\-9|up(\\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|yas\\-|your|zeto|zte\\-")) {
%>
<link rel="stylesheet" type="text/css" href="/common/front/css/mobile.css"/>
<%}%>
<link rel="stylesheet" type="text/css" href="/common/front/css/respond.css"/>
<!--[if lt IE 9]>
	<script src="/common/front/js/html5.js"></script>
<![endif]-->
<!--[if lt IE 8]>
	<script src="http://ie7-js.googlecode.com/svn/version/2.1(beta4)/IE8.js"></script>
<![endif]-->
<script type="text/javascript" src="/common/front/js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="/common/front/js/iscroll.js"></script>
<script type="text/javascript" src="/common/front/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="/common/front/js/jquery-datepicker.js"></script>
<script type="text/javascript" src="/common/front/js/jquery.tweenmax.js"></script>
<script type="text/javascript" src="/common/front/js/jquery.bxslider.min.js"></script>
<!-- <script type="text/javascript" src="/common/front/js/jquery.swiper.min.js"></script> -->
<script type="text/javascript" src="/common/front/js/slick.min.js"></script>
<script type="text/javascript" src="/common/front/js/Draggable.min.js"></script>
<script type="text/javascript" src="https://spi.maps.daum.net/imap/map_js_init/postcode.v2.js"></script>
<!-- <script type="text/javascript" src="https://openapi.map.naver.com/openapi/v3/maps.js?clientId=R8yg5baYkecMdu4ek2Vc"></script> -->
<!-- <script type="text/javascript" src="https://openapi.map.naver.com/openapi/v3/maps.js?clientId=traxKGRv2RvbYav81Pt8"></script> -->
<!-- 20180326 네이버 지도 수정 -->
 <!-- <script type="text/javascript" src="https://openapi.map.naver.com/openapi/v3/maps.js?clientId=occAe_qDtkFxlWrdSTnc"></script> -->
 <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=7ce3cc24dce206936545f361d5804fb5"></script>
 
<script type="text/javascript" src="/common/front/js/common.js"></script>
<script type="text/javascript" src="/common/front/js/commonUI.js"></script>
<script type="text/javascript" src="/common/front/js/router.js"></script>
<script type="text/javascript" src="/common/front/js/reservation.js"></script>
<script type="text/javascript" src="/common/front/js/common2.js"></script>
<script type="text/javascript" src="/common/front/js/statistics.js"></script>
<script type="text/javascript" src="/common/js/input.js"></script>
