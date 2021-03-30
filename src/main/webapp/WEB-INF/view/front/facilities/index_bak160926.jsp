<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../common/taglibs.jsp" %>
<div class="contentArea active dsfaf">
<div class="zoomArea" style="display:none">
	<button class="in" onclick="$mapId.zoomIn(true,0);">zoom in</button>
	<button class="out on" onclick="$mapId.zoomOut(true,0);">zoom out</button>
</div>

<%@ include file="../facilities/popup.jsp"%>

				<div id="facilities" class="content">
					<div class="content-inner">

						<div id="floorMap">
							<section class="floorMenuArea">
								<ul>
									<li onclick="$(this).fadeHover(true);" class="n1"><button onclick="bxSlide.goToSlide(1);" data-num="1"><strong class="h">RF</strong><span class="img i-h"><img src="/common/front/images/facilities/btn_rnav1_off.png" alt=""></span></button></li>
									<li onclick="$(this).fadeHover(true);" class="n0"><button onclick="bxSlide.goToSlide(0);" data-num="0"><strong class="h">4F</strong><span class="img i-h"><img src="/common/front/images/facilities/btn_rnav2_off.png" alt=""></span></button></li>
									<li onclick="$(this).fadeHover(true);" class="n2"><button onclick="bxSlide.goToSlide(2);" data-num="2"><strong class="h">3F</strong><span class="img i-h"><img src="/common/front/images/facilities/btn_rnav3_off.png" alt=""></span></button></li>
								</ul>
							</section>
							<section class="floorMapArea">
								<div class="inner">
									<div class="titArea">
										<h1><button class="btn_floorMenuArea">3F</button> 시설안내</h1>
										<div class="list">
											<div class="lst n6 f4" data-num="6"><button onclick="$mapId.zoomOut(true,0);">
												<span class="img i-h"><img src="/common/front/images/facilities/btn_u0_off.png" alt=""></span>
												<span class="txt"><span>4층 전체</span></span>
											</button></div>
											<div class="lst n0 f4" data-num="0"><button onclick="$mapId.zoomIn(true,3);">
												<span class="img i-h"><img src="/common/front/images/facilities/btn_u1_off.png" alt=""></span>
												<span class="txt"><span>찜질스파</span></span>
											</button></div>
											<div class="lst n1 f4" data-num="1"><button onclick="$mapId.zoomIn(true,0);">
												<span class="img i-h"><img src="/common/front/images/facilities/btn_u2_off.png" alt=""></span>
												<span class="txt"><span>실내<br class="m_br">워터파크</span></span>
											</button></div>
											<div class="lst n2 rf" data-num="2"><button  onclick="rfMotion(0)">
												<span class="img i-h"><img src="/common/front/images/facilities/btn_u3_off.png" alt=""></span>
												<span class="txt"><span>야외 워터파크</span></span>
											</button></div>
											<div class="lst n3 f4" data-num="3"><button  onclick="$mapId.zoomIn(true,2);">
												<span class="img i-h"><img src="/common/front/images/facilities/btn_u4_off.png" alt=""></span>
												<span class="txt"><span>사우나</span></span>
											</button></div>
											<div class="lst n4 f4" data-num="4"><button onclick="$mapId.zoomIn(true,1);">
												<span class="img i-h"><img src="/common/front/images/facilities/btn_u5_off.png" alt=""></span>
												<span class="txt"><span>F&amp;B</span></span>
											</button></div>
											<div class="lst n4 rf" data-num="4"><button onclick="rfMotion(1)">
												<span class="img i-h"><img src="/common/front/images/facilities/btn_u5_off.png" alt=""></span>
												<span class="txt"><span>F&amp;B</span></span>
											</button></div>
											<div class="lst n5 f4" data-num="5"><button onclick="$mapId.zoomIn(true,4);">
												<span class="img i-h"><img src="/common/front/images/facilities/btn_u6_off.png" alt=""></span>
												<span class="txt"><span>기타시설</span></span>
											</button></div>
											<div class="lst n5 f3" data-num="5"><button>
												<span class="img i-h"><img src="/common/front/images/facilities/btn_u6_off.png" alt=""></span>
												<span class="txt"><span>기타시설</span></span>
											</button></div>
										</div>
									</div>
									<div class="mapArea">
										<div class="lst n0" data-num="0">
											<div class="pos">
												<div class="box_map">
														<div class="box_marker">
															<img class="map" src="/common/front/images/facilities/map_4f.png"/>
															<div class="mk s0">
															<button class="mark before n0 n1_1" onclick="rLayerPopup('.n1_1');">
																<span class="img"><img src="/common/front/images/facilities/btn_more1_1.png"/></span>
															</button>
															<button class="mark before n1 n1_2" onclick="rLayerPopup('.n1_2');">
																<span class="img"><img src="/common/front/images/facilities/btn_more1_2.png"/></span>
															</button>
															<button class="mark before n2 n1_3" onclick="rLayerPopup('.n1_3');">
																<span class="img"><img src="/common/front/images/facilities/btn_more1_3.png"/></span>
															</button>
															<button class="mark before n3 n1_4" onclick="rLayerPopup('.n1_4');">
																<span class="img"><img src="/common/front/images/facilities/btn_more1_4.png"/></span>
															</button>
															<button class="mark before n7 n1_8" onclick="rLayerPopup('.n1_8');">
																<span class="img"><img src="/common/front/images/facilities/btn_more1_10.png"/></span>
															</button>
															<button class="mark before n5 n1_6" onclick="rLayerPopup('.n1_6');">
																<span class="img"><img src="/common/front/images/facilities/btn_more1_6.png"/></span>
															</button>
															<button class="mark before n4 n1_5" onclick="rLayerPopup('.n1_5');">
																<span class="img"><img src="/common/front/images/facilities/btn_more1_5.png"/></span>
															</button>
															<button class="mark before n6 n1_7" onclick="rLayerPopup('.n1_7');">
																<span class="img"><img src="/common/front/images/facilities/btn_more1_9.png"/></span>
															</button>
															<button class="mark before n8 n1_9" onclick="rLayerPopup('.n1_9');">
																<span class="img"><img src="/common/front/images/facilities/btn_more1_11.png"/></span>
															</button>
															<button class="mark before n9 n1_10" onclick="rLayerPopup('.n1_10');">
																<span class="img"><img src="/common/front/images/facilities/btn_more1_12.png"/></span>
															</button>
															<button class="mark before n10 n1_11" onclick="rLayerPopup('.n1_11');">
																<span class="img"><img src="/common/front/images/facilities/btn_more1_8.png"/></span>
															</button>
															<button class="mark before n11 n1_12" onclick="rLayerPopup('.n1_12');">
																<span class="img"><img src="/common/front/images/facilities/btn_more1_7.png"/></span>
															</button>
															</div>
															<div class="mk s1">
															<button class="mark before n12 n1_21" onclick="rLayerPopup('.n1_21');">
																<span class="img"><img src="/common/front/images/facilities/btn_more4_1.png"/></span>
															</button>
															<button class="mark before n13 n1_22" onclick="rLayerPopup('.n1_22');">
																<span class="img"><img src="/common/front/images/facilities/btn_more4_3.png"/></span>
															</button>
															<button class="mark before n13 n1_23" onclick="rLayerPopup('.n1_23');">
																<span class="img"><img src="/common/front/images/facilities/btn_more4_2.png"/></span>
															</button>
															</div>
															<div class="mk s2">
															<button class="mark before n16 n1_33" onclick="rLayerPopup('.n1_33');">
																<span class="img"><img src="/common/front/images/facilities/btn_more2_3.png"/></span>
															</button>
															<button class="mark before n15 n1_32" onclick="rLayerPopup('.n1_32');">
																<span class="img"><img src="/common/front/images/facilities/btn_more2_2.png"/></span>
															</button>
															<button class="mark before n14 n1_31" onclick="rLayerPopup('.n1_31');">
																<span class="img"><img src="/common/front/images/facilities/btn_more2_1.png"/></span>
															</button>
															<button class="mark before n17 n1_34" onclick="rLayerPopup('.n1_34');">
																<span class="img"><img src="/common/front/images/facilities/btn_more2_4.png"/></span>
															</button>
															<button class="mark before n18 n1_35" onclick="rLayerPopup('.n1_35');">
																<span class="img"><img src="/common/front/images/facilities/btn_more2_5.png"/></span>
															</button>
															<button class="mark before n19 n1_36" onclick="rLayerPopup('.n1_36');">
																<span class="img"><img src="/common/front/images/facilities/btn_more2_6.png"/></span>
															</button>
															</div>
															<div class="mk s3">
															<button class="mark before n20 n1_41" onclick="rLayerPopup('.n1_41');">
																<span class="img"><img src="/common/front/images/facilities/btn_more3_2.png"/></span>
															</button>
															<button class="mark before n21 n1_42" onclick="rLayerPopup('.n1_42');">
																<span class="img"><img src="/common/front/images/facilities/btn_more3_3.png"/></span>
															</button>
															<button class="mark before n22 n1_43" onclick="rLayerPopup('.n1_43');">
																<span class="img"><img src="/common/front/images/facilities/btn_more3_5.png"/></span>
															</button>
															<button class="mark before n28 n1_49" onclick="rLayerPopup('.n1_49');">
																<span class="img"><img src="/common/front/images/facilities/btn_more3_6.png"/></span>
															</button>
															<button class="mark before n23 n1_44" onclick="rLayerPopup('.n1_44');">
																<span class="img"><img src="/common/front/images/facilities/btn_more3_7.png"/></span>
															</button>
															<button class="mark before n24 n1_45" onclick="rLayerPopup('.n1_45');">
																<span class="img"><img src="/common/front/images/facilities/btn_more3_8.png"/></span>
															</button>
															<button class="mark before n25 n1_46" onclick="rLayerPopup('.n1_46');">
																<span class="img"><img src="/common/front/images/facilities/btn_more3_9.png"/></span>
															</button>
															<button class="mark before n26 n1_47" onclick="rLayerPopup('.n1_47');">
																<span class="img"><img src="/common/front/images/facilities/btn_more3_10.png"/></span>
															</button>
															<button class="mark before n27 n1_48" onclick="rLayerPopup('.n1_48');">
																<span class="img"><img src="/common/front/images/facilities/btn_more3_11.png"/></span>
															</button>

															<button class="mark before n29 n1_410" onclick="rLayerPopup('.n1_410');">
																<span class="img"><img src="/common/front/images/facilities/btn_more3_12.png"/></span>
															</button>
															<button class="mark before n30 n1_411" onclick="rLayerPopup('.n1_411');">
																<span class="img"><img src="/common/front/images/facilities/btn_more3_4.png"/></span>
															</button>
															<button class="mark before n31 n1_412" onclick="rLayerPopup('.n1_412');">
																<span class="img"><img src="/common/front/images/facilities/btn_more3_1.png"/></span>
															</button>
															</div>
															<div class="mk s4">
																<button class="mark before n32 n1_413" onclick="rLayerPopup('.n3_5');">
																	<span class="img"><img src="/common/front/images/facilities/btn_more6_4.png"/></span>
																</button>
															</div>
														</div>
														<img class="shadow" src="/common/front/images/facilities/map_4f_shadow.png"/>

												</div>
											</div>
										</div>

										<div class="lst n1"  data-num="1">
											<div class="pos">
												<div class="box_map">
														<div class="box_marker">
															<img class="map" src="/common/front/images/facilities/map_rf.png"/>
															<div class="mk s0">
																<button class="mark before n0 n2_1" onclick="rLayerPopup('.n2_1');"><img src="/common/front/images/facilities/btn_more5_1.png"/></button>
																<button class="mark before n1 n2_2" onclick="rLayerPopup('.n2_2');"><img src="/common/front/images/facilities/btn_more5_2.png"/></button>
																<button class="mark before n2 n2_3" onclick="rLayerPopup('.n2_3');"><img src="/common/front/images/facilities/btn_more5_3.png"/></button>
																<button class="mark before n4 n2_5" onclick="rLayerPopup('.n2_5');"><img src="/common/front/images/facilities/btn_more5_4.png"/></button>
																<button class="mark before n5 n2_6" onclick="rLayerPopup('.n2_6');"><img src="/common/front/images/facilities/btn_more5_5.png"/></button>
																<button class="mark before n6 n2_7" onclick="rLayerPopup('.n2_7');"><img src="/common/front/images/facilities/btn_more5_6.png"/></button>
																<button class="mark before n8 n2_9" onclick="rLayerPopup('.n2_7');"><img src="/common/front/images/facilities/btn_more5_6.png"/></button>
																<button class="mark before n7 n2_8" onclick="rLayerPopup('.n2_8');"><img src="/common/front/images/facilities/btn_more5_7.png"/></button>
															</div>
															<div class="mk s1">
																<button class="mark before n9 n2_10" onclick="rLayerPopup('.n2_4');"><img src="/common/front/images/facilities/btn_more5_8.png"/></button>
																<button class="mark before n3 n2_4" onclick="rLayerPopup('.n2_4');"><img src="/common/front/images/facilities/btn_more5_8.png"/></button>
															</div>
														</div>
														<img class="shadow" src="/common/front/images/facilities/map_rf_shadow.png"/>
												</div>
											</div>
										</div>


										<div class="lst n2"  data-num="2">
											<div class="pos">
												<div class="box_map">
														<div class="box_marker">
															<img class="map" src="/common/front/images/facilities/map_3f.png"/>
															<button class="mark before n0 n3_1" onclick="rLayerPopup('.n3_1');"><img src="/common/front/images/facilities/btn_more6_1.png"/></button>
															<button class="mark before n2 n3_3" onclick="rLayerPopup('.n3_3');"><img src="/common/front/images/facilities/btn_more6_2.png"/></button>
															<button class="mark before n3 n3_4" onclick="rLayerPopup('.n3_4');"><img src="/common/front/images/facilities/btn_more6_3.png"/></button>
														</div>
														<img class="shadow" src="/common/front/images/facilities/map_3f_shadow.png"/>
												</div>
											</div>
										</div>


									</div>
								</div>
							</section>
							<section class="floorDetailArea">

							</section>
						</div>
					</div>
					<style>


					</style>


					<script type="text/javascript">

/*! modernizr 3.3.1 (Custom Build) | MIT *
 * https://modernizr.com/download/?-touchevents-setclasses !*/
!function(e,n,t){function o(e,n){return typeof e===n}function s(){var e,n,t,s,a,i,r;for(var l in c)if(c.hasOwnProperty(l)){if(e=[],n=c[l],n.name&&(e.push(n.name.toLowerCase()),n.options&&n.options.aliases&&n.options.aliases.length))for(t=0;t<n.options.aliases.length;t++)e.push(n.options.aliases[t].toLowerCase());for(s=o(n.fn,"function")?n.fn():n.fn,a=0;a<e.length;a++)i=e[a],r=i.split("."),1===r.length?Modernizr[r[0]]=s:(!Modernizr[r[0]]||Modernizr[r[0]]instanceof Boolean||(Modernizr[r[0]]=new Boolean(Modernizr[r[0]])),Modernizr[r[0]][r[1]]=s),f.push((s?"":"no-")+r.join("-"))}}function a(e){var n=u.className,t=Modernizr._config.classPrefix||"";if(p&&(n=n.baseVal),Modernizr._config.enableJSClass){var o=new RegExp("(^|\\s)"+t+"no-js(\\s|$)");n=n.replace(o,"$1"+t+"js$2")}Modernizr._config.enableClasses&&(n+=" "+t+e.join(" "+t),p?u.className.baseVal=n:u.className=n)}function i(){return"function"!=typeof n.createElement?n.createElement(arguments[0]):p?n.createElementNS.call(n,"http://www.w3.org/2000/svg",arguments[0]):n.createElement.apply(n,arguments)}function r(){var e=n.body;return e||(e=i(p?"svg":"body"),e.fake=!0),e}function l(e,t,o,s){var a,l,f,c,d="modernizr",p=i("div"),h=r();if(parseInt(o,10))for(;o--;)f=i("div"),f.id=s?s[o]:d+(o+1),p.appendChild(f);return a=i("style"),a.type="text/css",a.id="s"+d,(h.fake?h:p).appendChild(a),h.appendChild(p),a.styleSheet?a.styleSheet.cssText=e:a.appendChild(n.createTextNode(e)),p.id=d,h.fake&&(h.style.background="",h.style.overflow="hidden",c=u.style.overflow,u.style.overflow="hidden",u.appendChild(h)),l=t(p,e),h.fake?(h.parentNode.removeChild(h),u.style.overflow=c,u.offsetHeight):p.parentNode.removeChild(p),!!l}var f=[],c=[],d={_version:"3.3.1",_config:{classPrefix:"",enableClasses:!0,enableJSClass:!0,usePrefixes:!0},_q:[],on:function(e,n){var t=this;setTimeout(function(){n(t[e])},0)},addTest:function(e,n,t){c.push({name:e,fn:n,options:t})},addAsyncTest:function(e){c.push({name:null,fn:e})}},Modernizr=function(){};Modernizr.prototype=d,Modernizr=new Modernizr;var u=n.documentElement,p="svg"===u.nodeName.toLowerCase(),h=d._config.usePrefixes?" -webkit- -moz- -o- -ms- ".split(" "):["",""];d._prefixes=h;var m=d.testStyles=l;Modernizr.addTest("touchevents",function(){var t;if("ontouchstart"in e||e.DocumentTouch&&n instanceof DocumentTouch)t=!0;else{var o=["@media (",h.join("touch-enabled),("),"heartz",")","{#modernizr{top:9px;position:absolute}}"].join("");m(o,function(e){t=9===e.offsetTop})}return t}),s(),a(f),delete d.addTest,delete d.addAsyncTest;for(var v=0;v<Modernizr._q.length;v++)Modernizr._q[v]();e.Modernizr=Modernizr}(window,document);


							var pageStartNum = 6;
							var $mapId = $("#floorMap");
							var $topMenu1 = $("#floorMap .floorMapArea .titArea .list .lst");
							var mobileSize = 1200;
							var mobileSize2 = 639;
							var leftPopup = false;
							var rightPopup = false;
							var rPopupSetTime;

							$.fn.extend({imgConversion : function(s,type){var xt = $(this).attr('src').lastIndexOf('.') + 1;xt = $(this).attr('src').substr(xt);if(s){$(this).attr('src', $(this).attr('src').replace('off.'+xt, (type != "hover")? 'on.'+xt :'hover.'+xt ));}else{$(this).attr('src', $(this).attr('src').replace((type != "hover")? 'on.'+xt : 'hover.'+xt , 'off.'+xt));};return $(this);}});


							function winHeight(){
								var winH = $(window).height();
								if(winH <= 860 && winH > 768){
									$("body").removeClass("okM")
									$("body").addClass("okT")
								}else if(winH <= 768){
									$("body").removeClass("okT")
									$("body").addClass("okM")
								}else{
									$("body").removeClass("okT")	.removeClass("okM")
								}

							}

							$(".btn_floorMenuArea").on("click",function(){
								var $this = $(this);
								if(!$(this).hasClass("on")){
									TweenMax.to($(".floorMenuArea"),0.6,{"width":260});
									TweenMax.to($("#floorMap"),0.6,{"padding-left":260});
									TweenMax.set($(".floorMenuArea ul"),{"margin-left":-260});
									TweenMax.to($(".floorMenuArea ul"),0.6,{"margin-left":0});
									TweenMax.to($(this),0.6,{"left":260});
									$("#floorMap .floorMapArea").css("width",$("#floorMap .floorMapArea").width());
									$(this).addClass("on");
									if(rightPopup){
										rLayerPopup("close");
									}
									leftPopup = true;
									$("#floorMap .floorMenuArea > ul > li button").off("click floorMenu").on("click floorMenu",function(){
											$this.trigger("click");
											$("#floorMap .floorMenuArea > ul > li button").off("click floorMenu");
									})
								}else{

									TweenMax.to($(".floorMenuArea"),0.6,{"width":0,onComplete:function(){
										$("#floorMap .floorMapArea").css("width","");
									}});
									TweenMax.to($("#floorMap"),0.6,{"padding-left":0});
									TweenMax.to($(".floorMenuArea ul"),0.6,{"margin-left":-260});
									TweenMax.to($(this),0.6,{"left":0});
									$(this).removeClass("on");
									leftPopup = false;
								}


							})

							$(window).on("resize",function(){
								var win_w = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
								if(mobileSize < win_w){
									$(".floorMenuArea").css("width","");
									$("#floorMap").css("padding-left","");
									$(".floorMenuArea ul").css("margin-left","");
									$(".btn_floorMenuArea").css("left","").removeClass("on");
								}
							})


							$.fn.forFade = function(o){
								var el = this;
								o = $.extend({
									delay : 100,
									InDelay : 300,
									outDelay : 200,
									switch : true
								}, o || {});
								return this.each(function(){
									var $child = $(this);
									var delay = o.delay;
									var InDelay = o.InDelay;
									var outDelay = o.outDelay;
									var Switch = o.switch;
								        var length = $child.length;
								         if(Switch){
								            for (var i = 0; i < length; i++) {
								              $child.filter(":eq("+i+")").css({
								                "display":"",
								                "opacity":0
								              }).stop().delay(delay*i).animate({
								                "opacity":1
								              },InDelay);
								            };
								          }else{
								            for (var i = 0; i < length; i++) {
								              $child.filter(":eq("+i+")").stop().animate({
								                  "opacity":0
								                },outDelay,function(){
								                 $(this).css({"display":"none"});
								                });

								            };
								          }
								})//each
							}//forFade

							$.fn.forFade2 = function(o){
								var el = this;
								o = $.extend({
									delay : 100,
									InDelay : 300,
									outDelay : 200,
									switch : true
								}, o || {});

								return this.each(function(){
									var $this = $(this);
									var $child = $this;
									var delay = o.delay;
									var InDelay = o.InDelay;
									var outDelay = o.outDelay;
									var Switch = o.switch;
							          var length = $child.length;
								         if(Switch){
								            for (var i = 0; i < length; i++) {
								             TweenMax.set($child,{"y":-40,"opacity":0,"display":"block"});
								             alert(i);
								             TweenMax.to($child.filter(":eq("+i+")"),0.6,{"y":0,"opacity":1,"delay":i*1});
								            };//for
								          }else{
								            for (var i = 0; i < length; i++) {
								              TweenMax.to($child.eq(i),1,{"y":-20,"opacity":0, onComplete:function(){
								              	TweenMax.set($child,{"y":-20,"opacity":0,"display":"none"});
								              }})

								            };//for
								          }//Switch
								})//each
							}//forFade2




							$.fn.fadeHover = function(o){
								var el = this;
								o = $.extend({
									flag : true
								}, o || {});

								return this.each(function(event){
									var $this = $(this);
									var flag = o.flag;
									var imgIn = function(elemt){
											var imgOvr = elemt.find("span.i-h>img").attr("src").replace("_off","_on");
										      elemt.find("span.i-h").append($("<img />").attr("src",imgOvr).css({
										        "opacity":0,
										        "position":"absolute",
										        "left":0,
										        "top":0
										      }).addClass("ovr"))
										      .find(".ovr").stop().animate({"opacity":1},200);
									}//imgin
									var imgOut = function(elemt,speed){
										elemt.find("span.i-h .ovr").stop().animate({"opacity":0},speed,function(){$(this).remove();});
									}//imgin

									el.resetHover = function(){
										$this.siblings(".on").removeClass("on").trigger("mouseleave");
									}


									if(!flag){
										//if(){
											if(!Modernizr.touchevents){
											$this.off().on("mouseenter mouseleave",function(e){
											    var elemt = $(this);
												    if(!elemt.hasClass("on")){
													    (e.type == "mouseenter") ? 	imgIn(elemt) : imgOut(elemt,200);
												    }//hasClass
										  	 });
										}else{
											$this.off().on("click",function(e){
											    var elemt = $(this);
												    if(!elemt.hasClass("on")){
											    			imgOut(elemt.siblings(".on"),0);
											    			elemt.siblings(".on").removeClass("on")
											    			imgIn(elemt);
											    			elemt.addClass("on");
									    				}
										  	 });
										}
										//}else{

										//}
										console.log(Modernizr.touchevents);

								    	}else{//flag
								    		if(!Modernizr.touchevents){
									    		if(!$this.hasClass("on")){
										    		$this.siblings(".on").removeClass("on").trigger("mouseleave").end().addClass("on");
									    			if ($this.find(".ovr").length == 0)  imgIn($this);
							    				}
						    				}else{
						    					if(!$this.hasClass("on")){
										    		imgOut($this.siblings(".on"),0);
									    			$this.siblings(".on").removeClass("on")
									    			imgIn($this);
									    			$this.addClass("on");
							    				}
						    				}
								    	}
								})//each
							}//fadeHover

							function rLayerPopup(obj){
								var $box = $("#box-facilit-right");
								var win_w = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
								var win_h = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;



									if(obj == "close"){
										rightPopup = false;
										TweenMax.to($box,0.6,{width:0, onComplete:function(){
											TweenMax.to($("#topMenu .rightUtil"),0.3,{"y":"0"});
										 }});
										//if(bxSlide.getCurrentSlide() == 0){
											if(mobileSize < win_w){
												$mapId.popSize(false);
											}
										//}
										$(window).off("resize.popupLoad");
										$("#Mheader").css("display","");

									}else{
										rightPopup = true;
										if(leftPopup){
											$(".btn_floorMenuArea").trigger("click");
										}

											if(win_w <= mobileSize2){
												$("#Mheader").css("display","none");
											}

										if($box.width() == 0){
											if(win_w > mobileSize2){
												TweenMax.to($box,0.6,{width:380, onComplete:function(){  }});
												TweenMax.set($box.find(".inner"),{width:380});
											}else{
												$(window).scrollTop(0);
												TweenMax.to($box,0.6,{width:"100%", onComplete:function(){ resizePopup(); }});
												TweenMax.set($box.find(".inner"),{width:win_w});
												var bh = $("#wrap").outerHeight() - ($("#footer").outerHeight() + $("#footerTop").outerHeight());
												$box.height(bh);
											}
											TweenMax.to($("#topMenu .rightUtil"),0.3,{"y":"-200"});
										}
										$box.find(".pop").css("display","none");
										$box.find(".pop"+obj).css("display","block");
										var s =$box.find(".pop"+obj).data("num");

										function resizePopup(){
											win_w = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
											win_h = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
											if(win_w <= mobileSize2){
												$("#Mheader").css("display","none");
											}else{
												$("#Mheader").css("display","");
											}

											if(win_w > mobileSize2){
												TweenMax.set($box,{width:380, onComplete:function(){  }});
												TweenMax.set($box.find(".inner"),{width:380});
												$box.css("height","");
											}else{
												TweenMax.set($box,{width:"100%", onComplete:function(){ }});
												TweenMax.set($box.find(".inner"),{width:win_w});
												var bh = $("#wrap").outerHeight() - ($("#footer").outerHeight() + $("#footerTop").outerHeight());
												$box.height(bh);
											}

											if(mobileSize < win_w){
												var th = win_h - ( $box.find(".pop"+obj).find(".mid").offset().top + $("#mainMenu").height() + $("#footerTop").height() );
											}else if(mobileSize >= win_w &&  mobileSize2 < win_w){
												var th = win_h - ( $box.find(".pop"+obj).find(".mid").offset().top + $("#footerTop").height() + 40 );
											}else{
												var th = $("#wrap").height() - ( $box.find(".pop"+obj).find(".mid").offset().top + $("#footerTop").height() + $("#footer").height() + 40 );
											}

											$("#box-facilit-right  .mid").height(th);
											if(win_w > mobileSize2){
												if(iContScroll == null){
													iContScroll = [];
													for (var i = 0; i < $("#box-facilit-right .pop").length-1; i++) {
					  									iContScroll[i] = new IScroll("#box-facilit-right .pop.s"+i+" .mid", {
															scrollbars: 'custom',
												    			mouseWheel: true,
												    			disableMouse: true
							  							});
						  							}
												}
												iContScroll[s].refresh();
											}else{
												iContScroll[s].destroy();
												iContScroll[s] = null;

											}

										}



										if(bxSlide.getCurrentSlide() == 0){
											if(mobileSize < win_w){
												$mapId.popSize(true);
											}
										}

										$(window).off("resize.popupLoad").on("resize.popupLoad",function(){
											win_w = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
											win_h = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
											rPopupSetTime = setTimeout(function(){
												resizePopup();
											},1101)

										})


									};
							};

							$("#box-facilit-right").find(".close img").on("mouseenter mouseleave",function(e){
								if(e.type == "mouseenter"){
									TweenMax.to($(this),0.3,{"rotation":180, onComplete:function(){  }});
								}else{
									TweenMax.to($(this),0.3,{"rotation":0, onComplete:function(){  }});
								}
							});



						 var bxSlide;
						 var bxPoint = 0;
						 var prevBxPoint = 0;
						 var zoomPoint = 0;
						 var bxOption = {
						 	//mode:"vertical",
						 	 auto: false,
						       autoControls: false,
						       touchEnabled:false,
						       //randomStart:true,
						       responsive:false,
						       stopAuto: false,
						       startSlide:bxPoint,
						       speed:600,
						       swipeThreshold:150,
						        onSliderLoad: function(currentIndex){
						           var  currentIndex = "#floorMap .floorMapArea .mapArea .lst.n"+bxPoint;
						           motion(currentIndex);
						        },
						        onSlideBefore: function(currentIndex){
						          motion(currentIndex);
						        },
						        onSlideAfter: function(currentIndex){}
						      }

						 var mapAni = new TimelineMax();

						 function motion(currentIndex){
							bxPoint = $(currentIndex).data("num");
						 	if(bxPoint != zoomPoint){
						 		setTimeout(function(){$mapId.zoomOut(true,0);},0);
						 		//$(".zoomArea button").forFade({switch:false});
						 	}else{
						 		//$(".zoomArea button").forFade({switch:true});
						 	}
						 	$("#floorMap .floorMenuArea ul > li.n"+bxPoint).fadeHover({flag:true});
						 	if($("#box-facilit-right").width() > 10){
						 		rLayerPopup("close");
						 	};
						 	if(bxPoint == 0){
						 		var txtFloor = "4F"
						 	}else if(bxPoint == 1){
						 		var txtFloor = "RF"
						 	}else if(bxPoint == 2){
						 		var txtFloor = "3F"
						 	}

						 	$(".btn_floorMenuArea").text(txtFloor);

						 	// alert(bxPoint);
						 	// if(bxPoint == 0){

						 	// }
						 	// //$topMenu1.siblings(".n"+pageStartNum).trigger("click").find("button").trigger("click");
					 		iconMotion();
					 		menuFade(bxPoint);
						 }



						 function iconMotion(){
						 	var mark = $("#floorMap .floorMapArea .mapArea .lst.n"+bxPoint+" .box_marker .mark.before");
						 	//var length = Math.ceil(mark.length /2);
						 	var length = mark.length ;
						 	TweenMax.set($("#floorMap .floorMapArea .mapArea .lst .box_marker .mark.before"),{"y":"-25","opacity":"0"});
						 	//TweenMax.to(mark,1.5,{y:0,"opacity":1, "delay":0.1, "ease": Elastic.easeOut.config(1, 0.3)});
						 	setTimeout(function(){
							 	for (var i = 0; i < length; i++) {
							 		//TweenMax.set(mark.filter(":eq("+i+")"),{"y": "-45","opacity":0});
							 		TweenMax.to($("#floorMap .floorMapArea .mapArea .lst.n"+bxPoint+" .box_marker .mark.before.n"+i),0.6,{y:0,"opacity":1,  ease: Power3.easeIn,"delay":0.03*i});
							 	}
							 	prevBxPoint = bxPoint;
						 	},(bxPoint != prevBxPoint) ? bxOption.speed : 0);


						 	// mapAni.fromTo($(".mapArea .map"),.7,{y: '30%'},{y: '0%', delay:2, onComplete:function(){  }});

						 	// $("#floorMap .floorMapArea .mapArea .lst.n"+bxPoint+" .mark.before").forFade({
						 	// 	delay : 1000,
								// InDelay : 2500,
								// switch : true
						 	// });
						 }

						 function menuFade(num){
						 	//var length = Math.ceil(mark.length /2);
						 	var length = $topMenu1.length ;
						 	TweenMax.set($topMenu1,{"y":"-15","opacity":"0","display":"none"});
						 	if(num == 0){
							 	for (var i = 0; i < $topMenu1.siblings(".f4").length; i++) {
							 		TweenMax.to($topMenu1.siblings(".f4").filter(":eq("+i+")"),0,{"display":"block",y:0,"opacity":1,  ease: Power3.easeIn,"delay":0*i});
							 	}
						 	}else if(num == 1){
							 	for (var i = 0; i < $topMenu1.siblings(".rf").length; i++) {
							 		TweenMax.to($topMenu1.siblings(".rf").filter(":eq("+i+")"),0,{"display":"block",y:0,"opacity":1,  ease: Power3.easeIn,"delay":0*i});
							 		//$topMenu1.siblings(".rf").fadeHover({flag:true});
							 	}

							 	$("#floorMap .floorMapArea .titArea .list .lst.n2.rf").fadeHover({flag:true});

							 	rfMotion(0);
					 		}else if(num == 2){
							 	TweenMax.set($topMenu1,{"y":"-25","opacity":"0"});
							 	for (var i = 0; i < $topMenu1.siblings(".f3").length; i++) {
							 		TweenMax.to($topMenu1.siblings(".f3").filter(":eq("+i+")"),0,{"display":"block",y:0,"opacity":1,  ease: Power3.easeIn,"delay":0*i});

							 	}
						 	}

						 };

						 function rfMotion(num){
						 	iconMotion();
						 	$("#floorMap .n1 .box_marker").find(".mk").css("display","none");
							$("#floorMap .n1 .box_marker").find(".mk.s"+num).css("display","block");
						 }//rfMotion


						 // $("#floorMap .box_mark_after .mark.after, #floorMap .floorMapArea .mapArea .lst.n1 .box_marker .mark.before , #floorMap .floorMapArea .mapArea .lst.n2 .box_marker .mark.before").on("mouseenter mouseleave",function(e){
							// 	if(e.type == "mouseenter"){
							// 		TweenMax.to($(this),0.3,{"rotation":180, onComplete:function(){  }});
							// 	}else{
							// 		TweenMax.to($(this),0.3,{"rotation":0, onComplete:function(){  }});
							// 	}
							// });


						 $("#floorMap .n"+bxPoint+" .mark.before").off("click").on("click",function(){
						 	var $this = $(this);
						 	var num = $this.data("num");
						 	//$("#floorMap .floorMapArea .titArea .list li:eq("+num+")").fadeHover({flag:true});

						 });




						(function($){
							bxSlide = $("#floorMap .floorMapArea .mapArea").bxSlider(bxOption);


							$.fn.aquaMapArea = function(o){
								var el = this;
								o = $.extend({
									start : 0,
									rightMargin : 7,
									delay : 6000
								}, o || {});



								return this.each(function(){
									var $this = $(this);
									var $win = $(window);
									var $boxMap = $this.find(".bx-wrapper");
									var $mainmenu = $("#mainMenu");
									var $boxZoom =  $(".zoomArea");
									var $popup = $("#box-facilit-right");
									var win_w = $win.width();
									var win_h = $win.height();
									var boxOffet = $boxMap.offset();
									var menuOffset = $mainmenu.offset();
									var start = o.start;
									var rM = ($win.width() / 100) * o.rightMargin;
									var boxw ,boxh ,myDraggable;
									var viewSetTime;
									var flag = start;
									var flag_zoom = false;
									var zF = false;
									var mapPoint = 0;
									var area = new Array();
									var popSize = false;
									var popMargin = 400;


									area[0] = new Array(); // 실내 워터파크
									area[0][0] = "-7%";
									area[0][1] = "0%";
									area[1] = new Array(); // F&B
									area[1][0] = "-42%";
									area[1][1] = "-8%";
									area[2] = new Array(); //사우나
									area[2][0] = "-12%";
									area[2][1] = "-24%";
									area[3] = new Array(); //찜질 스파
									area[3][0] = "-44%";
									area[3][1] = "-20%";
									area[4] = new Array(); //기타시설
									area[4][0] = "-5%";
									area[4][1] = "-30%";


									el.resetSize  = function(e){
										// $boxMap = $this.find(".bx-wrapper");
										// $boxMap.css({
										// 	"height": ""
										// })
										// .find(".bx-viewport").css({
										// 	"height": ""
										// });
										// $boxMap.find(".lst").css({
										// 	"width": "",
										// 	"height": ""
										// });
										// $boxMap.find(".pos").css({
										// 	"width": "",
										// 	"height": ""
										// });
									}

									el.viewport  = function(e){
										$boxMap = $this.find(".bx-wrapper");
										var topmenu = $("#floorMap .floorMapArea .titArea");
										win_w = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
										win_h = $win.height();
										boxOffet = $boxMap.offset();
										menuOffset = $mainmenu.offset();

										if(topmenu.length > 0){
											if(mobileSize < win_w){
												boxw = win_w -  boxOffet.left;
												var totalHeight = $("#floorMap .floorMapArea .titArea").outerHeight(true) + parseInt($("#floorMap").css("padding-top"))  + $("#mainMenu").height(); + $("#footerTop").height();
												//boxh =  (win_h - ( topmenu.offset().top + topmenu.outerHeight() ) ) + (menuOffset.top - win_h)  + 60;
												boxh =  ( win_h - (totalHeight) );
											}else if(mobileSize >= win_w &&  mobileSize2 < win_w){
												boxw = win_w;
												var totalHeight = $("#floorMap .floorMapArea .titArea").outerHeight(true) + parseInt($("#floorMap").css("padding-top"))  + $("#footerTop").height();
												boxh =  ( win_h - (totalHeight) );
											}else{
												boxw = win_w;
												//var totalHeight = $("#floorMap .floorMapArea .titArea").outerHeight(true) + parseInt($("#floorMap").css("padding-top"))  + $("#footerTop").height();
												boxh = 300;
											}


										}

										//console.log("win_w::: " + win_w);
										//console.log("win_h::: " + win_h);
										//console.log("boxOffet::: " + boxOffet);
										//console.log("menuOffset::: " + menuOffset);
										//console.log("boxw::: " + boxw);
										//console.log("boxh::: " + boxh);


										TweenMax.set($("#wrap"),{ "y": 0  });
										$boxMap.css({
											"height": boxh,
										})
										.find(".bx-viewport").css({
											"height": boxh,
										});
										$boxMap.find(".lst").css({
											"width": boxw,
											"height": boxh,
										});
										$boxMap.find(".pos").css({
											"width": boxw,
											"height": boxh,
											"position":"absolute",
											"left":0,
											"top":0,
											"overflow":"hidden"
										});
										TweenMax.set($boxMap.find(".mapArea"),{ "x": -(boxw * (bxPoint+1))  });

										// setTimeout(function(){
										// 	bxSlide.goToSlide(bxPoint);
										// },500)


										if(zF){
											el.zoomIn(false,0);
										}else{
											if(!popSize){
												var mapw = (mobileSize < win_w) ? boxw - rM : boxw- rM;
											}else{
												var mapw = (mobileSize < win_w) ? boxw - rM - $popup.outerWidth() : boxw;
											}

											if(mobileSize < win_w){
												$boxMap.find(".pos .box_map").css({
													"width": mapw,
													"height": boxh,
													"overflow":"hidden",
													"left":0,
													"margin-left":0,
													"top":0
												});
											//}else if(mobileSize >= win_w &&  mobileSize2 < win_w){
												// $boxMap.find(".pos .box_map").css({
												// 	"width": mapw,
												// 	"height": boxh,
												// 	"overflow":"hidden",
												// 	"left":0,
												// 	"top":0
												// });
											}else{
												for (var i = 0; i < $this.find(".bx-wrapper").find(".lst").length; i++) {
													if(i == 1 || i == 2){
													$this.find(".bx-wrapper").find(".lst.n"+i).find(".pos .box_map").css({
														"width": mapw + ( ( $(window).width() / 10 ) *5  ),
														"height": boxh,
														"overflow":"hidden",
														"left":0,
														"margin-left": -( $(window).width() / 12 ) * 1,
														"top":0
													});
													}else{
														$this.find(".bx-wrapper").find(".lst.n"+i).find(".pos .box_map").css({
															"width": mapw,
															"height": boxh,
															"overflow":"hidden",
															"left":0,
															"margin-left":0,
															"top":0
														});

													}
												}

											}


											myDraggable[0].disable();
										}

									};//viewport

									var reset = function(start){
										$boxMap = $this.find(".bx-wrapper");


										if(!start){

										}else{
											mapAni.to($boxMap.find(".n"+flag+" .pos .box_map"),0.6,{
												"width": (!popSize) ? boxw - rM : boxw - rM - $popup.outerWidth(),
												"height": boxh,
												"x":0,
												"y":0,
												"x":"0%",
												"y":"0%",
												ease:Power2.easeInOut,
												onComplete:function(){
												myDraggable[0].disable();
											}  });
											//$boxZoom.find("button.on").removeClass("on").end().find(".out").addClass("on");
											if(bxPoint==zoomPoint){
												//$topMenu1.resetHover();
											}
											//el.zoomOut(true,0);
											//$this.find(".box_mark").css("display","block");
											//$this.find(".box_mark_after").css("display","none");
											$this.find(".mk").css("display","block");

										}
									};


									el.zoomIn = function(start,point){
										var setTimeBx = 0;
										if(mobileSize < win_w){
											var zomL = 2;
											area[0] = new Array(); // 실내 워터파크
											area[0][0] = "-7%";
											area[0][1] = "0%";
											area[1] = new Array(); // F&B
											area[1][0] = "-42%";
											area[1][1] = "-8%";
											area[2] = new Array(); //사우나
											area[2][0] = "-12%";
											area[2][1] = "-24%";
											area[3] = new Array(); //찜질 스파
											area[3][0] = "-44%";
											area[3][1] = "-20%";
											area[4] = new Array(); //기타시설
											area[4][0] = "-5%";
											area[4][1] = "-30%";
										}else if(mobileSize >= win_w &&  mobileSize2 < win_w){
											var zomL = 3;
											area[0] = new Array(); // 실내 워터파크
											area[0][0] = "-7%";
											area[0][1] = "0%";
											area[1] = new Array(); // F&B
											area[1][0] = "-42%";
											area[1][1] = "-8%";
											area[2] = new Array(); //사우나
											area[2][0] = "-12%";
											area[2][1] = "-24%";
											area[3] = new Array(); //찜질 스파
											area[3][0] = "-44%";
											area[3][1] = "-20%";
											area[4] = new Array(); //기타시설
											area[4][0] = "-5%";
											area[4][1] = "-30%";
										}else{
											var zomL = 4;
											area[0] = new Array(); // 실내 워터파크
											area[0][0] = "-15%";
											area[0][1] = "-7%";
											area[1] = new Array(); // F&B
											area[1][0] = "-42%";
											area[1][1] = "-8%";
											area[2] = new Array(); //사우나
											area[2][0] = "-10%";
											area[2][1] = "-32%";
											area[3] = new Array(); //찜질 스파
											area[3][0] = "-42%";
											area[3][1] = "-40%";
											area[4] = new Array(); //기타시설
											area[4][0] = "-15%";
											area[4][1] = "-35%";
										}
										$boxMap = $this.find(".bx-wrapper");
											if(bxPoint != zoomPoint){
												bxSlide.goToSlide(0);
												setTimeBx = bxOption.speed;
											}//
											setTimeout(function(){
												if(zF == true){
													// if(mapPoint == point){
													// 	return;
													// }else{
														mapPoint = point;
														mapAni.to($boxMap.find(".n"+flag+" .pos .box_map"),0.4,{"x":area[mapPoint][0],"y":area[mapPoint][1],ease:Power2.easeInOut });
														return;
													//}
												}//zF

												$boxMap.find(".n"+flag+" .pos").css({
													"width": boxw,
													"height":boxh
												});
												if(!start){
													//console.log("boxw:::  " + boxw);
													//console.log("boxw*2:::  " + boxw*2);
													mapAni.set($boxMap.find(".n"+flag+" .pos .box_map"),{"width": boxw*zomL,"height":"","x":area[point][0],"y":area[point][1]});
													myDraggable[0].enable();
													mapPoint = point;
													//myDraggable[0].applyBounds({top:100,left:100})
												}else{
													// mapAni.to(
													// 	$boxMap.find(".n"+flag+" .pos .box_map"),
													// 	0.2,
													// 	{"x":area.a1[0],"y":area.a1[1],ease:Power2.easeInOut}
													// );
													mapAni.set($boxMap.find(".n"+flag+" .pos .box_map"),{"width": boxw - rM});
													mapAni.to($boxMap.find(".n"+flag+" .pos .box_map"),0.4,{"width": boxw*zomL,"height":"","x":area[point][0],"y":area[point][1],ease:Power2.easeInOut,onComplete:function(){
														myDraggable[0].enable();
														mapPoint = point;
														//$boxMap.find(".n"+flag+" .pos .box_map").css({"height":""});
													}  });

													//myDraggable[0].applyBounds({top:100,left:100})
													//mapAni.to($boxMap.find(".n"+flag+" .pos .box_map"),0.4,{"x":area.a1[0],"y":area.a1[1],ease:Power2.easeInOut});


													// mapAni.fromTo($boxMap.find(".n"+flag+" .pos .box_map"),0.4,{"width": boxw - rM},{"width": boxw*2,"x":area.a1[0],"y":area.a1[1],ease:Power2.easeInOut,onComplete:function(){
													// 	myDraggable[0].enable();
													// 	$boxMap.find(".n"+flag+" .pos .box_map").css({"height":""});
													// 	//mapAni.to($boxMap.find(".n"+flag+" .pos .box_map"),0.6,{});
													// //	myDraggable[0].applyBounds({top:100,left:100})
													// }  });

												}

												zF = true;
												//$boxZoom.find("button.on").removeClass("on").end().find(".in").addClass("on");
												$this.addClass("zoom");
												//$this.find(".box_mark_after").css("display","block")
												//$this.find(".box_mark_after").forFade2({target:".mark",switch:true});

											},setTimeBx);
												$this.find(".mk").css("display","none");
												$this.find(".mk.s"+point).css("display","block");
												TweenMax.set($this.find(".mk.s"+point).find(".mark"),{"y":"-25","opacity":"0"});
											 	for (var i = 0; i < $this.find(".mk.s"+point).find(".mark").length; i++) {
											 		TweenMax.to($this.find(".mk.s"+point).find(".mark:eq("+i+")"),0.5,{y:0,"opacity":1, "delay":0.1*i});
											 	}


									};//zoomIn

									el.popSize = function(b){
										win_w = $win.width();
										win_h = $win.height();
										boxOffet = $boxMap.offset();
										menuOffset = $mainmenu.offset();
										boxw = win_w -  boxOffet.left;
										boxh =  (win_h - boxOffet.top) + (menuOffset.top - win_h)  + 60;

										if(b){
											popSize = true;
											if(zF == true){

											}else{
												TweenMax.to($boxMap.find(".pos .box_map"),0.6,{ "width": (!popSize) ? boxw - rM : boxw - rM - popMargin });
											}

										}else{
											popSize = false;
											if(zF == true){
											}else{
												TweenMax.to($boxMap.find(".pos .box_map"),0.6,{ "width": boxw - rM, });
											}

										}

									};

									el.zoomOut = function(start){
										if(zF){
											reset(true);
											zF = false;
										}else{
											return;
										}
										$this.removeClass("zoom");

									};//zoomIn


									$boxMap.checkImgsLoad(function(){
										myDraggable = Draggable.create($boxMap.find(".n"+flag+" .pos .box_map"), {
											type:"x,y",
											bounds:$boxMap.find(".n"+flag+" .pos"),
											throwProps:true,
											ease:Power2.easeInOut
										});
										el.viewport();
										winHeight();

									});
									$(window).off("resize.aquaMap").on("resize.aquaMap",function(){
										clearInterval(viewSetTime);
										winHeight();
										el.resetSize();
										viewSetTime = setTimeout(function(){
											el.viewport();
										},1100);

									});






								});
							}
						})(jQuery);



						// var locationHref2 = location.href;
						// var arrLocationHref2 = locationHref2.split("#");
						// var jayuUrl2 = new String(arrLocationHref2[2]);

						$mapId.aquaMapArea({start:0 });
						$topMenu1.fadeHover({flag:false});
						$topMenu1.on("click",function(){
							$(this).fadeHover({flag:true});
						});


						$("#floorMap .floorMenuArea ul > li").fadeHover({flag:false});
						$("#floorMap .floorMenuArea ul > li.n"+bxPoint).fadeHover({flag:true});

						 $("#floorMap .floorMenuArea > ul > li").on("click",function(){
						 	var index = $(this).index();
						 	if(index == 0){
						 		$topMenu1.siblings(".n2").trigger("click");
						 	}else if(index == 1){
						 		$topMenu1.siblings(".n6").trigger("click");
					 		}else if(index == 2){
						 		$topMenu1.siblings(".n5").trigger("click");
						 	}
						 });




							// var myRe = new RegExp("tabCode_", "ig");
  					// 		var resultArray = jayuUrl2.match(myRe);
  					// 		var resultArrayLeg = (resultArray != null) ? resultArray.length : 0;
  					// 		if(resultArrayLeg > 0){
								//var pNum = jayuUrl2.replace("tabCode_","");
//								pageStartNum = pNum;
  							//}
							var pNum = getParameter("pageing");
							window.changeFacilitiesViewArea = function(pNum){
								if (pNum != undefined) {
								    pageStartNum = pNum;
								    $mapId.checkImgsLoad(function() {
								        if (pNum == 3) {
								            bxPoint = 1;
								            bxSlide.goToSlide(bxPoint);
								        } else if (pNum == 4) {
								            bxPoint = 0;
								            bxSlide.goToSlide(bxPoint);
								            $topMenu1.siblings(".n3").trigger("click").find("button").trigger("click");
								        } else if (pNum == 6) {
								            bxSlide.goToSlide(bxPoint);
								            $topMenu1.siblings(".n4.f4").trigger("click").find("button").trigger("click");
								        } else if (pNum == 5) {
								            bxPoint = 2;
								            bxSlide.goToSlide(bxPoint);
								            $topMenu1.siblings(".n5.f3").trigger("click").find("button").trigger("click");
								        } else {
								            $topMenu1.siblings(".n" + pageStartNum).trigger("click").find("button").trigger("click");
								        }
								    });
								}
							};
							changeFacilitiesViewArea(pNum);


  							var iContScroll = [];
  							for (var i = 0; i < $("#box-facilit-right .pop").length-1; i++) {
  									iContScroll[i] = new IScroll("#box-facilit-right .pop.s"+i+" .mid", {
										scrollbars: 'custom',
							    			mouseWheel: true,
							    			disableMouse: true
		  							});
  							}









					</script>
				</div>
			</div>
