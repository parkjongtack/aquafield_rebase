<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no, target-densitydpi=device-dpi">

<title>아쿠아필드│AQUAFIELD</title>
<meta name="keywords" content="아쿠아필드,AQUAFIELD,찜질스파,사우나,워터파크,루프탑풀">
<meta name="description" content="찜질스파와 물놀이가 결합된 도심 속 힐링체험공간">
<meta name="naver-site-verification" content="02f5474843f0a32a701ebc4dbb30bf0fe3629d5c"/>
<meta property="og:type" content="website">
<meta property="og:title" content="아쿠아필드│AQUAFIELD">
<meta property="og:description" content="찜질스파와 물놀이가 결합된 도심 속 힐링체험공간">
<meta property="og:image" content=https://www.aquafield-ssg.co.kr/common/front/images/main/Logo_Gate.png>
<meta property="og:url" content="http://www.aquafield-ssg.co.kr">

<link rel="stylesheet" type="text/css" href="/common/front/css/slick.css">
<link rel="stylesheet" type="text/css" href="/common/front/css/common.css">
<link rel="stylesheet" type="text/css" href="/common/front/css/intro.css">
<script type="text/javascript" src="/common/front/js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="/common/front/js/slick.min.js"></script>
</head>
<body>
<div id="intro-slider" class="bx-wrapper">
	<div class="slider-item hanam"></div>
	<div class="slider-item goyang"></div>
	<div class="slider-item ansung"></div> 
</div>
<div id="intro_wrap">
	<div class="inner hanam">
		<a href="/hanam/index.af" class="intro_section hanam" onmouseover="hoverEvent('hanam');">
			<img src="/common/front/images/intro/intro_text_hanam_en.png" class="en" alt="HANAM">
			<img src="/common/front/images/intro/intro_text_hanam_kr.png" class="kr" alt="하남">
		</a>
		<a href="/goyang/index.af" class="intro_section goyang" onmouseover="hoverEvent('goyang');">
			<img src="/common/front/images/intro/intro_text_goyang_en.png" class="en" alt="GOYANG">
			<img src="/common/front/images/intro/intro_text_goyang_kr.png" class="kr" alt="고양">
		</a>
		<a href="/anseong/index.af" class="intro_section ansung" onmouseover="hoverEvent('ansung');">
			<img src="/common/front/images/intro/intro_text_ansung_en.png" class="en" alt="ansung">
			<img src="/common/front/images/intro/intro_text_ansung_kr.png" class="kr" alt="안성">
		</a>
	</div>
</div>
<script type="text/javascript">
	var introSlider = $("#intro-slider").slick({
	  	dots: false,
	  	arrows: false,
	  	fade: true,
	  	infinite: false,
	  	speed: 1000,
	  	autoplay: false,
	  	cssEase: 'ease'
	});

	introSlider.on('setPosition', function(event, slick, direction){
		var winW = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
		var winH = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
		$("#intro-slider .slider-item").css('height', winH);
	});

	function hoverEvent(point) {
		//$('#intro_wrap > .inner').removeClass("hanam goyang").addClass(point);
		if(point == "hanam"){
			introSlider.slick('slickGoTo', 0);
		}else if(point == "goyang"){
			introSlider.slick('slickGoTo', 1);
		}else if(point == "ansung"){
			introSlider.slick('slickGoTo', 2);
		}
	}
</script>
</body>
</html>
