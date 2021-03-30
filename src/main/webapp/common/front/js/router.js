var POINT_CODE;
var page_z = 2;
if (!Array.prototype.map) {
  Array.prototype.map = function(callback, thisArg) {
    var T, A, k;
    if (this == null) throw new TypeError(' this is null or not defined');
    var O = Object(this);
    var len = O.length >>> 0;
    if (typeof callback !== 'function') {throw new TypeError(callback + ' is not a function');}
    if (arguments.length > 1) {T = thisArg;}
    A = new Array(len);
    k = 0;
    while (k < len) {
      var kValue, mappedValue;
      if (k in O) {
        kValue = O[k];
        mappedValue = callback.call(T, kValue, k, O);
        A[k] = mappedValue;
      }
      k++;
    }
    return A;
  };
}

if (!Array.prototype.indexOf) {
  Array.prototype.indexOf = function(searchElement, fromIndex) {
    var k;
    if (this == null) throw new TypeError('"this" is null or not defined');

    var o = Object(this);
    var len = o.length >>> 0;
    if (len === 0) return -1;
    var n = +fromIndex || 0;
    if (Math.abs(n) === Infinity) n = 0;
    if (n >= len) return -1;
    k = Math.max(n >= 0 ? n : len - Math.abs(n), 0);
    while (k < len) {
      if (k in o && o[k] === searchElement) {return k;}
      k++;
    }
    return -1;
  };
}


/*hash 변경시 ajax로 컨텐츠 가져오기*/
var SPAByHash = function(){
    var win = $(window),
        html = $('html'),
        body = $('body'),
        header = $("#header"),
        conArea = $("#wrap .contentArea"),
        currP = conArea.eq(0),
        nextP = conArea.eq(1).addClass('on'),
        pagiBtnBx = $("#content_change_btns"),
        prevBtn = pagiBtnBx.find(".btn_prev"),
        nextBtn = pagiBtnBx.find(".btn_next"),
        _this = this, firstLoad = 1;

    POINT_CODE = $('body').attr('id');

    if(POINT_CODE == 'POINT01'){
        SPAByHash.prototype.urlData = urlData_POINT01;
    }else if(POINT_CODE == 'POINT03'){
        SPAByHash.prototype.urlData = urlData_POINT03;
    }else if(POINT_CODE == 'POINT05'){
        SPAByHash.prototype.urlData = urlData_POINT05;
    }else{
        SPAByHash.prototype.urlData = urlData_POINT01;
    }

    var urlDatalen = this.urlData.length;
    prevBtn.on('click', function(){
        for (var i = (urlDatalen + _this.pageIdx); i > _this.pageIdx; i--) {
            var item = _this.urlData[((i-1) + urlDatalen)%urlDatalen];
            if(item.skip){
                location = "#"+item.url;
                break;
            }
        }
    });
    nextBtn.on('click', function(){
        for (var i = _this.pageIdx; i < (urlDatalen + _this.pageIdx); i++) {
            var item = _this.urlData[((i+1) + urlDatalen)%urlDatalen];
            if(item.skip){
                location = "#"+item.url;
                break;
            }
        }
    })

    this.getUrlAndParams = function(){ //주소창에서 해시에 url과 파라미터 가져오기
        var arrLocation = location.hash.split("#");
        var urlDiv = arrLocation[1] ? arrLocation[1].split("?") : [];
        var hashPara = urlDiv[1] ? this.queryString(urlDiv[1]) : {}; //파라미터
        var urlObj = this.getRouter(arrLocation[1], hashPara); //url 주소
        // for (var v in urlObj.params) if (urlObj.params.hasOwnProperty(v)) hashPara[v] = urlObj.params[v];
        if(urlObj) return {url : urlObj.url , params : urlObj.params};
    };
    var flagGetting;
    this.getContent = function(o){ //컨텐츠 가져오기
        if(!o) return;
        o.params = o.params || {};
        pagiBtnBx.addClass('hide');
        function getAjax(){
            $.ajax({
                type:"GET",
                url:o.url,
                dataType:"html",
        	    data:o.params,
                success : function(res){
                    var data = o.params.changeMotion === 2 ? $(res).find('.box-content > .content:first-child > *') : $(res).find('.contentArea > *');
                    var winW = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
                    data.imagesLoaded(function(){
                        if(!firstLoad) firstLoad = 0;
                        if(!o.params.changeMotion){ //첫번째 댑스간의 이동일때..
                            var winH = $(window).height();
                            nextP.html(data).addClass('on');
                            currP.removeClass('on');
                            window.contentBox.initEle();
                            window.scrollTo(0,0);
                            TweenMax.to(currP,1,{y : '-30%', ease: Power3.easeInOut});
            				TweenMax.to(nextP,1,{y : '0%', ease: Power3.easeInOut, onComplete:function(){
                                currP.html("");
                                TweenMax.set(currP, {y:'100%'});
                                if(o.params.skip) pagiBtnBx.removeClass('hide');
                                _this.showParaMotion(nextP);
            				}});
                        }else if(o.params.index === 2){ //카테고리 페이지
                            data = $(res).find('.contentArea > *');
                            nextP.html(data);
                            var outBx = nextP.find('.content-box');
                            var currBx = outBx.find('.box-content > .content:last-child');
                            var nextBx = $('<div class="content" style="opacity:0"></div>');
                            var cateBx = nextP.find('.content-cate');
                            TweenMax.to(cateBx,0.5,{'opacity' : 1, 'display': 'block', ease: Power2.easeInOut});
                            TweenMax.to(outBx,0.5,{y : '50%', 'opacity' : 0, ease: Power2.easeInOut, onComplete:function(){
                                outBx.css({'display' : "none"});
                            }});
                        }else if(o.params.changeMotion === 2){ //첫번째 댑스는 같고 두번재 댑스간의 이동일때..
                            var outBx = nextP.find('.content-box');
                            var currBx = outBx.find('.box-content > .content:last-child');
                            if(currBx.prevAll().length) currBx.prevAll().remove();
                            var nextBx = $('<div class="content" style="opacity:0"></div>');
                            var cateBx = nextP.find('.content-cate');
                            outBx.css({'display' : ""});
                            currBx.after(nextBx);
                            nextBx.html(data);
                            if(o.params.tabChange){ //탭간의 이동일때 모션~
                                outBx.find(o.params.tabChange.el+':eq('+(_this.depth2-1)+')').siblings('.active').removeClass('active').end().addClass('active');
                                var dirc = o.params.tabChange.dirc > 0 ? 100 : -100;
                                if(o.params.depth1 == 5) {
                                    TweenMax.fromTo(nextBx,0.5,{y : dirc+'%', 'opacity' : 1}, {y : '0%',  ease: Power1.easeInOut, onComplete:function(){}});
                                    TweenMax.to(currBx,0.5,{y : -1*dirc+'%',  ease: Power1.easeInOut, onComplete:function(){
                                        currBx.remove();
                                        nextBx.addClass('on');
                                        window.contentBox.initEle();
                                        if(o.params.skip) pagiBtnBx.removeClass('hide');
                                        _this.showParaMotion(nextBx);
                                    }});
                                }else{
                                    TweenMax.fromTo(nextBx,0.5,{x : dirc+'%', 'opacity' : 1}, {x : '0%',  ease: Power1.easeInOut, onComplete:function(){}});
                                    TweenMax.to(currBx,0.5,{x : -1*dirc+'%',  ease: Power1.easeInOut, onComplete:function(){
                                        currBx.remove();
                                        nextBx.addClass('on');
                                        window.contentBox.initEle();
                                        if(o.params.skip) pagiBtnBx.removeClass('hide');
                                        _this.showParaMotion(nextBx);
                                    }});
                                }

                            /*}else if(o.params.index){
                                TweenMax.to(cateBx,0.5,{'opacity' : 1, ease: Power2.easeInOut});
                                TweenMax.to(outBx,0.5,{y : '50%', 'opacity' : 0, ease: Power2.easeInOut, onComplete:function(){
                                    outBx.css({'display' : "none"});
                                }});*/
                            }else{ //일반적인 이동일때 모션~
                                TweenMax.to(cateBx,1,{'opacity' : 0, 'display': 'none', ease: Power2.easeInOut});

                                if(winW > _this.breakpoint){
                                    TweenMax.to(outBx,0.5,{y : '50%', 'opacity' : 0, ease: Power2.easeInOut, onComplete:function(){
                                        currBx.remove();
                                        nextBx.css({'opacity' : 1}).addClass('on');
                                        window.contentBox.initEle();
                                        TweenMax.to(outBx,0.5,{y : '0%', 'opacity' : 1, ease: Power2.easeInOut, onComplete:function(){
                                            if(o.params.skip) pagiBtnBx.removeClass('hide');
                                            _this.showParaMotion(nextBx);
                                        }});
                                    }});
                                }else{
                                    nextBx.css({'opacity' : 1});
                                    window.contentBox.initEle();
                                    TweenMax.to(currBx,0.5,{'opacity' : 0, ease: Power2.easeInOut, onComplete:function(){
                                        nextBx.addClass('on');
                                        currBx.remove();
                                        if(o.params.skip) pagiBtnBx.removeClass('hide');
                                        _this.showParaMotion(nextBx);
                                    }});


                                    /*TweenMax.to(outBx,0.5,{'opacity' : 0, ease: Power2.easeInOut, onComplete:function(){
                                        currBx.remove();
                                        nextBx.css({'opacity' : 1}).addClass('on');
                                        window.contentBox.initEle();
                                        TweenMax.to(outBx,0.5,{'opacity' : 1, ease: Power2.easeInOut, onComplete:function(){
                                            if(o.params.skip) pagiBtnBx.removeClass('hide');
                                            _this.showParaMotion(nextBx);
                                        }});
                                    }});*/
                                }
                            }
                        }
                    });
                 },
                 error : function(xhr, status, error) {
                    alert(error);
                 }
            });
        };
        if(!o.params.changeMotion) {//첫번째 댑스간의 이동일때..
            var tmpP;
            tmpP = currP, currP = nextP, nextP = tmpP;
        }
        getAjax();
        // if(firstLoad){
        //     currP.css({"opacity":0});getAjax();
        // }else{
        //     TweenMax.to(currP,1,{'opacity' : 0, ease: Power3.easeInOut, onComplete : getAjax});
        // }
    };

    this.movePage = function(){ //페이지 이동
        this.getContent(this.getUrlAndParams());

        fullPage.hideArea('footer');
        $("#mainMenu ul.nav > li > a.active").trigger('click');
    };
    window.onhashchange = function(){_this.movePage.call(_this)};
    this.movePage();
}

SPAByHash.prototype.cateIndex = 0;
SPAByHash.prototype.breakpoint = 768;
SPAByHash.prototype.queryString = QueryString;
SPAByHash.prototype.urlData;
var urlData_POINT01 = [
    {depth : [0,1], skip : 0, path : '', url : '/main.af'},
    {depth : [0,1], skip : 0, path : '/', url : '/main.af'},
    {depth : [1,0], skip : 0, path : '/about/', url : '/about/index.af'},
    {depth : [1,1], skip : 1, path : '/about/concept/', url : '/about/concept/index.af'},
    {depth : [1,2], skip : 1, path : '/about/location/', url : '/about/location/index.af'},
    {depth : [2,0], skip : 0, path : '/facilities/', url : '/facilities/index.af'},
    {depth : [2,1], skip : 1, path : '', url : '/facilities/index.af?depth1=2'},
    {depth : [2,2], skip : 1, path : '', url : '/facilities/index.af?depth1=2&depth2=2'},
    {depth : [2,3], skip : 1, path : '', url : '/facilities/index.af?depth1=2&depth2=1'},
    {depth : [2,4], skip : 1, path : '', url : '/facilities/index.af?depth1=1'},
    {depth : [2,5], skip : 1, path : '', url : '/facilities/index.af?depth1=2&depth2=3'},
    {depth : [2,6], skip : 1, path : '', url : '/facilities/index.af?depth1=2&depth2=4'},
    {depth : [2,7], skip : 1, path : '', url : '/facilities/index.af?depth1=3'},
    {depth : [2,8], skip : 0, path : '', url : '/facilities/index.af?depth1=2&depth2=5'},
    {depth : [3,0], skip : 0, path : '/guide/', url : '/guide/index.af?page=0'},
    {depth : [3,1], skip : 1, path : '/guide/useinfo/', url : '/guide/index.af?page=1'},
    {depth : [3,2], skip : 1, path : '/guide/charges/', url : '/guide/index.af?page=2'},
    {depth : [3,3], skip : 1, path : '/guide/rental/', url : '/guide/index.af?page=3'},
    {depth : [3,4], skip : 1, path : '/guide/faq/', url : '/guide/index.af?page=4'},
    {depth : [3,5], skip : 1, path : '/guide/sequence/', url : '/guide/index.af?page=5'},
    {depth : [4,0], skip : 0, path : '/event/', url : '/event/eventIndex.af'},
    {depth : [4,1], skip : 1, path : '/event/all/', url : '/event/eventIndex.af?cate=0'},
    {depth : [4,2], skip : 1, path : '/event/notice/', url : '/event/eventIndex.af?cate=1'},
    {depth : [4,3], skip : 1, path : '/event/event/', url : '/event/eventIndex.af?cate=2'},
    {depth : [4,4], skip : 1, path : '/event/media/', url : '/event/eventIndex.af?cate=3'},
    {depth : [4,5], skip : 0, path : '/event/media/', url : '/event/eventIndex.af?cate=4'},
    {depth : [4,6], skip : 0, path : '/event/write/', url : '/event/write.af?type=notice'},
    {depth : [4,7], skip : 0, path : '/event/edit/', url : '/event/noticeWrite.af'},

    {depth : [5,1], skip : 0, path : '', url : '/service/index.af?page=terms2'},
    {depth : [5,2], skip : 0, path : '', url : '/service/index.af?page=terms'},
    {depth : [5,3], skip : 0, path : '', url : '/service/index.af?page=privacy'},
    {depth : [5,4], skip : 0, path : '', url : '/service/index.af?page=video'},
    {depth : [5,5], skip : 0, path : '', url : '/service/index.af?page=email'},
    {depth : [5,6], skip : 0, path : '', url : '/service/index.af?page=guide'}
    /*{depth : [6,0], skip : 0, path : '', url : '/mypage/mypage.af'},
    {depth : [6,1], skip : 0, path : '', url : '/mypage/mypage.af?page=pwd'},
    {depth : [6,2], skip : 0, path : '/mypage/', url : '/mypage/mypage.af?page=reserve'},
    {depth : [6,3], skip : 0, path : '', url : '/mypage/mypage.af?page=cs'},*/
];

var urlData_POINT03 = [
    {depth : [0,1], skip : 0, path : '', url : '/main.af'},
    {depth : [0,1], skip : 0, path : '/', url : '/main.af'},
    {depth : [1,0], skip : 0, path : '/about/', url : '/about/index.af'},
    {depth : [1,1], skip : 1, path : '/about/concept/', url : '/about/concept/index.af'},
    {depth : [1,2], skip : 1, path : '/about/location/', url : '/about/location/index.af'},
    {depth : [2,0], skip : 0, path : '/facilities/', url : '/facilities/index.af'},
    {depth : [2,1], skip : 1, path : '', url : '/facilities/index.af?depth1=2'},
    {depth : [2,2], skip : 1, path : '', url : '/facilities/index.af?depth1=2&depth2=1'},
    {depth : [2,3], skip : 1, path : '', url : '/facilities/index.af?depth1=2&depth2=2'},
    {depth : [2,4], skip : 1, path : '', url : '/facilities/index.af?depth1=2&depth2=3'},
    {depth : [2,5], skip : 1, path : '', url : '/facilities/index.af?depth1=1'},
    {depth : [3,0], skip : 0, path : '/guide/', url : '/guide/index.af?page=0'},
    {depth : [3,1], skip : 1, path : '/guide/useinfo/', url : '/guide/index.af?page=1'},
    {depth : [3,2], skip : 1, path : '/guide/charges/', url : '/guide/index.af?page=2'},
    {depth : [3,3], skip : 1, path : '/guide/rental/', url : '/guide/index.af?page=3'},
    {depth : [3,4], skip : 1, path : '/guide/faq/', url : '/guide/index.af?page=4'},
    {depth : [3,5], skip : 1, path : '/guide/sequence/', url : '/guide/index.af?page=5'},
    {depth : [4,0], skip : 0, path : '/event/', url : '/event/eventIndex.af'},
    {depth : [4,1], skip : 1, path : '/event/all/', url : '/event/eventIndex.af?cate=0'},
    {depth : [4,2], skip : 1, path : '/event/notice/', url : '/event/eventIndex.af?cate=1'},
    {depth : [4,3], skip : 1, path : '/event/event/', url : '/event/eventIndex.af?cate=2'},
    {depth : [4,4], skip : 1, path : '/event/media/', url : '/event/eventIndex.af?cate=3'},
    {depth : [4,5], skip : 0, path : '/event/media/', url : '/event/eventIndex.af?cate=4'},
    {depth : [4,6], skip : 0, path : '/event/write/', url : '/event/write.af?type=notice'},
    {depth : [4,7], skip : 0, path : '/event/edit/', url : '/event/noticeWrite.af'},
    {depth : [5,1], skip : 0, path : '', url : '/service/index.af?page=terms2'},
    {depth : [5,2], skip : 0, path : '', url : '/service/index.af?page=terms'},
    {depth : [5,3], skip : 0, path : '', url : '/service/index.af?page=privacy'},
    {depth : [5,4], skip : 0, path : '', url : '/service/index.af?page=video'},
    {depth : [5,5], skip : 0, path : '', url : '/service/index.af?page=email'},
    {depth : [5,6], skip : 0, path : '', url : '/service/index.af?page=guide'}
];

var urlData_POINT05 = [
    {depth : [0,1], skip : 0, path : '', url : '/main.af'},
    {depth : [0,1], skip : 0, path : '/', url : '/main.af'},
    {depth : [1,0], skip : 0, path : '/about/', url : '/about/index.af'},
    {depth : [1,1], skip : 1, path : '/about/concept/', url : '/about/concept/index.af'},
    {depth : [1,2], skip : 1, path : '/about/location/', url : '/about/location/index.af'},
    {depth : [2,0], skip : 0, path : '/facilities/', url : '/facilities/index.af'},
    {depth : [2,1], skip : 1, path : '', url : '/facilities/index.af?depth1=2'},
    {depth : [2,2], skip : 1, path : '', url : '/facilities/index.af?depth1=2&depth2=2'},
    {depth : [2,3], skip : 1, path : '', url : '/facilities/index.af?depth1=2&depth2=1'},
    {depth : [2,4], skip : 1, path : '', url : '/facilities/index.af?depth1=1'},
    {depth : [2,5], skip : 1, path : '', url : '/facilities/index.af?depth1=2&depth2=3'},
    {depth : [2,6], skip : 1, path : '', url : '/facilities/index.af?depth1=2&depth2=4'},
    {depth : [2,7], skip : 1, path : '', url : '/facilities/index.af?depth1=2&depth2=5'},
    {depth : [3,0], skip : 0, path : '/guide/', url : '/guide/index.af?page=0'},
    {depth : [3,1], skip : 1, path : '/guide/useinfo/', url : '/guide/index.af?page=1'},
    {depth : [3,2], skip : 1, path : '/guide/charges/', url : '/guide/index.af?page=2'},
    {depth : [3,3], skip : 1, path : '/guide/rental/', url : '/guide/index.af?page=3'},
    {depth : [3,4], skip : 1, path : '/guide/faq/', url : '/guide/index.af?page=4'},
    {depth : [3,5], skip : 1, path : '/guide/sequence/', url : '/guide/index.af?page=5'},
    {depth : [4,0], skip : 0, path : '/event/', url : '/event/eventIndex.af'},
    {depth : [4,1], skip : 1, path : '/event/all/', url : '/event/eventIndex.af?cate=0'},
    {depth : [4,2], skip : 1, path : '/event/notice/', url : '/event/eventIndex.af?cate=1'},
    {depth : [4,3], skip : 1, path : '/event/event/', url : '/event/eventIndex.af?cate=2'},
    {depth : [4,4], skip : 1, path : '/event/media/', url : '/event/eventIndex.af?cate=3'},
    {depth : [4,5], skip : 0, path : '/event/media/', url : '/event/eventIndex.af?cate=4'},
    {depth : [4,6], skip : 0, path : '/event/write/', url : '/event/write.af?type=notice'},
    {depth : [4,7], skip : 0, path : '/event/edit/', url : '/event/noticeWrite.af'},

    {depth : [5,1], skip : 0, path : '', url : '/service/index.af?page=terms2'},
    {depth : [5,2], skip : 0, path : '', url : '/service/index.af?page=terms'},
    {depth : [5,3], skip : 0, path : '', url : '/service/index.af?page=privacy'},
    {depth : [5,4], skip : 0, path : '', url : '/service/index.af?page=video'},
    {depth : [5,5], skip : 0, path : '', url : '/service/index.af?page=email'},
    {depth : [5,6], skip : 0, path : '', url : '/service/index.af?page=guide'}
    /*{depth : [6,0], skip : 0, path : '', url : '/mypage/mypage.af'},
    {depth : [6,1], skip : 0, path : '', url : '/mypage/mypage.af?page=pwd'},
    {depth : [6,2], skip : 0, path : '/mypage/', url : '/mypage/mypage.af?page=reserve'},
    {depth : [6,3], skip : 0, path : '', url : '/mypage/mypage.af?page=cs'},*/
];

SPAByHash.prototype.getRouter = function(str, p){
    var url, params = {}, _this = this;
    var idx = this.urlData.map(function(v){return v.path;}).indexOf(str);
    if(idx === -1) idx = this.urlData.map(function(v){return v.url;}).indexOf(str);

    if(p.cate && p.page) { //뉴스페이지 예외처리(파라메터 변수)
        str = str.split("?")[0] + '?cate=' + this.queryString(str.split("?")[1]).cate;
        idx = this.urlData.map(function(v){return v.url;}).indexOf(str);
    }

    if(!str) idx = 0; //해시 없을 경우 메인으로..
    if(idx > -1){
        urlObj = this.urlData[idx];
        function setPagi(){
            params.skip = urlObj.skip;
            _this.pageIdx = idx;
            _this.depth1 = urlObj.depth[0];
            _this.depth2 = urlObj.depth[1];
            this.cateIndex = 0;

            $('.menuList > ul > li').eq(_this.depth1-1).each(function(argument) {
                $(this).children('a').addClass('active');
                $(this).children('ul').css('display', 'block');
                $(this).find('ul > li > a').removeClass('active');
                $(this).find('ul > li').eq(_this.depth2-1).children('a').addClass('active');
            });
            if(_this.depth1 <= 0 || _this.depth1 >= 5) {
                $('.menuList').find('a').removeClass('active');
                $('.menuList').find('ul').css('display', '');
            }

        }

        if(urlObj.depth[1] === 0){
            params.index = 2;
            params.changeMotion = 2;
            this.cateIndex = 1;
        }else if(this.depth1 !== undefined && this.depth1 === urlObj.depth[0]){
            if(this.depth2 === urlObj.depth[1] && !p.page) return false; //같은 페이지 일 경우 중지
            if(urlObj.depth[0] === 2 ){ //시설안내일경우 예외처리...
                p.depth1 = p.depth1 && parseInt(p.depth1-1), p.depth2 = p.depth2 && parseInt(p.depth2-1);
                ////////////////////////////////////////////////////////////////
                var nowP = $(".contentArea.on");
                var outBx = nowP.find('.content-box');
                var cateBx = nowP.find('.content-cate');
                var pagiBtnBx = $("#content_change_btns");
                outBx.css({'display' : ""});
                TweenMax.to(cateBx,1,{'opacity' : 0, ease: Power2.easeInOut});
                TweenMax.to(outBx,0.5,{y : '0%', 'opacity' : 1, ease: Power2.easeInOut, onComplete:function(){
                    pagiBtnBx.removeClass('hide');
                }});
                ////////////////////////////////////////////////////////////////
                if(facility.getIndex() !== p.depth1){ //뎁스1간의 이동이면..
                    facility.moveFloor(p.depth1, this.cateIndex === 1 ? 0 : 1);
                }
                if(p.depth2 !== undefined){ //줌이 있으면
                    facility.zoomSect.call(facility.liArr[p.depth1].faci[p.depth2].el,null, p.depth1, p.depth2, 0.7);
                }else{
                    facility.setFullSizeMap({i : p.depth1, motion : 0.8});
                }

                setPagi();
                return false;
            }
            if(urlObj.depth[0] === 4 && (p.page || p.cate) ) { //뉴스페이지 예외처리
                TweenMax.to(news.grid.find('.item'), 0.5, {scale : 0, ease: Power2.easeInOut, onComplete : function(){
                    news.init();
                }});

                setPagi();
                return false;
            }

            if(urlObj.depth[0] === 6 || urlObj.depth[0] === 5 ) params.tabChange = {el : '.box-tab > ul > li', dirc : urlObj.depth[1] - this.depth2 };//마이페이지일 경우 탭 체인지
            params.changeMotion = 2;
            params.depth1 = urlObj.depth[0];
        }else if(this.depth1 !== urlObj.depth[0]){
            //params.changeMotion = 1;
        }
        setPagi();
        return {url : urlObj.url, params : params};
    }else{
        return {url : str, params : params};
        this.pageIdx = this.depth1 = this.depth2 = false;
    }
}

SPAByHash.prototype.showParaMotion = function(t){
    this.arrApp;
    var appLen;
    var winW = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
    this.animate = function(){
        var i, len = this.arrApp.length;
        for (i = 0; i < len ; i++) {
            var app = this.arrApp[i], dirc = {};
            dirc[app.dirc] = app.dist;
            var obj = {opacity : 1, delay : app.delay, ease : app.ease};
            obj[app.dirc] = 0;
            if(winW > this.breakpoint) {
                TweenMax.fromTo(app.item, app.dura, dirc, obj);
            }else{
                TweenMax.to(app.item,0,{opacity : 1});
            }
        }
    };

    this.initMotionEle = function(){
        this.arrApp = [];
        var appear = t.find('.paraShowing');
        appLen = appear.length;
        if(!appLen) return;
        var i;
    	for(i = 0 ; i < appLen ; i++){
    		var item = appear.eq(i);
    		this.arrApp.push({
    			item : item,
    			ease : item.data('ease') || 'Power2.easeInOut',
    			dura : parseFloat(item.data('dura') || 1),
    			delay : parseFloat(item.data('delay') || 0),
                dirc : item.data('dirc') || 'y',
                dist : parseInt(item.data('dist') || 100)
    		});
    	}
        this.animate();
    };
    this.initMotionEle();
}

$(function(){
    window.sapByHash = new SPAByHash();
});



//시설안내 관련 함수..
var FacilityFn = function(params){
	var defaults = {
		dth1 : undefined,
		dth2 : undefined,
		minZoom : 0.9,
        imgType :'svg'
	};
	params = params || {};
	for (var v in defaults) if (defaults.hasOwnProperty(v)) if(!params[v]) params[v] = defaults[v];
    if(params.imgType === 'svg' && !Modernizr.svg){
        throw Error('이 브라우저는 svg를 지원하지 않습니다. 혹시 익스플로러를 에물레이터 버젼으로 사용중이시면 이 에러가 발생할 수 있습니다.');
        return;
    }
	params.dth1 = params.dth1 && parseInt(params.dth1), params.dth2 = params.dth2 && parseInt(params.dth2);
	var bx = $('#facilities .list_floors'), ul = bx.find('> ul'), list = ul.find('> li'), _this = this, liArr, body = $('body');
    var floors;

    var sesin_f_POINT01 = "";
    var sesin_m_POINT01 = "";
    var sesin_f_POINT03 = "";
    var sesin_m_POINT03 = "";
	var sesin_f_POINT05 = "";
	var sesin_m_POINT05 = "";

    sesin_f_POINT01 = "<div class='title notice_caption' style='font-size:18px; margin-bottom: 5px;'>이용안내</div>" +
			    				"<div class='info'><strong>운영시간</strong> : 11:00 - 20:00 (LAST ORDER 19:30)</div>" +
			    				"<div> " +
				    				"<table>" +
					    				"<caption>세신 이용 요금 - 구분, 종류, 소요시간, 가격</caption>" +
										"<colgroup>" +
											"<col width='*'>" +
											"<col width='20%'>" +
											"<col width='20%'>" +
										"</colgroup>" +
										"<thead>" +
											"<tr>" +
												"<th>종류</th>" +
												"<th>소요시간</th>" +
												"<th class='last'>가격</th>" +
											"</tr>" +
										"</thead>" +
										"<tbody>" +
											"<tr>" +
												"<td>기본세신</td>" +
												"<td>25분</td>" +
												"<td class='last'>30,000</td>" +
											"</tr>" +
											"<tr>" +
												"<td>미니마사지</td>" +
												"<td>40분</td>" +
												"<td class='last'>60,000</td>" +
											"</tr>" +
											"<tr>" +
												"<td>아로마 미니 오일</td>" +
												"<td>40분</td>" +
												"<td class='last'>70,000</td>" +
											"</tr>" +
											"<tr>" +
												"<td>아로마 전신 마사지</td>" +
												"<td>60분</td>" +
												"<td class='last'>90,000</td>" +
											"</tr>" +
											"<tr>" +
												"<td>샴푸</td>" +
												"<td></td>" +
												"<td class='last'>5,000</td>" +
											"</tr>" +
										"</tbody>" +
									"</table>" +
								"</div>" +
								"<div class='notice_caption' style='margin-top:5px;'>※요금 및 이용시간은 달라질 수 있습니다.</div>";

    sesin_m_POINT01 = "<div class='title notice_caption' style='font-size:18px; margin-bottom: 5px;'>이용안내</div>" +
								"<div class='info'><strong>운영시간</strong> : 11:00 - 20:00 (LAST ORDER 19:30)</div>" +
								"<div> " +
									"<table>" +
										"<caption>세신 이용 요금 - 구분, 종류, 소요시간, 가격</caption>" +
										"<colgroup>" +
											"<col width='*'>" +
											"<col width='20%'>" +
											"<col width='20%'>" +
										"</colgroup>" +
										"<thead>" +
											"<tr>" +
												"<th>종류</th>" +
												"<th>소요시간</th>" +
												"<th class='last'>가격</th>" +
											"</tr>" +
										"</thead>" +
										"<tbody>" +
											"<tr>" +
												"<td>기본세신(모발관리)</td>" +
												"<td>20분</td>" +
												"<td class='last'>25,000</td>" +
											"</tr>" +
											"<tr>" +
												"<td>행복마사지(두피&바디)</td>" +
												"<td>20분</td>" +
												"<td class='last'>30,000</td>" +
											"</tr>" +
											"<tr>" +
												"<td>등,목 마사지</td>" +
												"<td>50분</td>" +
												"<td class='last'>60,000</td>" +
											"</tr>" +
											"<tr>" +
												"<td>전신 마사지</td>" +
												"<td>90분</td>" +
												"<td class='last'>100,000</td>" +
											"</tr>" +
											"<tr>" +
												"<td>달인표 VIP 마사지</td>" +
												"<td>120분</td>" +
												"<td class='last'>150,000</td>" +
											"</tr>" +
											"<tr>" +
												"<td>각질제거</td>" +
												"<td></td>" +
												"<td class='last'>10,000</td>" +
											"</tr>" +
										"</tbody>" +
									"</table>" +
								"</div>" +
								"<div class='notice_caption' style='margin-top:5px;'>※요금 및 이용시간은 달라질 수 있습니다.</div>";

    sesin_f_POINT03 = "<div class='title notice_caption' style='font-size:18px; margin-bottom: 10px;'>이용안내</div>" +
								"<div class='info'><strong>운영시간</strong> : 10:00 - 22:00</div>" +
								"<div> " +
									"<table>" +
										"<caption>세신 이용 요금 - 구분, 종류, 소요시간, 가격</caption>" +
										"<colgroup>" +
											"<col width='*'>" +
											/*"<col width='20%'>" +*/
											"<col width='40%'>" +
										"</colgroup>" +
										"<thead>" +
											"<tr>" +
												"<th>종류</th>" +
												/*"<th>소요시간</th>" +*/
												"<th class='last'>가격</th>" +
											"</tr>" +
										"</thead>" +
										"<tbody>" +
											"<tr>" +
												"<td>기본세신</td>" +
												/*"<td>25분</td>" +*/
												"<td class='last'>30,000</td>" +
											"</tr>" +
											"<tr>" +
												"<td>미니마사지</td>" +
												/*"<td>40분</td>" +*/
												"<td class='last'>60,000</td>" +
											"</tr>" +
											"<tr>" +
												"<td>아로마 미니 오일</td>" +
												/*"<td>40분</td>" +*/
												"<td class='last'>70,000</td>" +
											"</tr>" +
											"<tr>" +
												"<td>아로마 전신 마사지</td>" +
												/*"<td>90분</td>" +*/
												"<td class='last'>90,000</td>" +
											"</tr>" +
											"<tr>" +
												"<td>비타민 마사지</td>" +
												/*"<td>70분</td>" +*/
												"<td class='last'>120,000</td>" +
											"</tr>" +
											"<tr>" +
												"<td>태반 마사지</td>" +
												/*"<td></td>" +*/
												"<td class='last'>150,000</td>" +
											"</tr>" +
											"<tr>" +
												"<td>샴푸</td>" +
												/*"<td></td>" +*/
												"<td class='last'>5,000</td>" +
											"</tr>" +
										"</tbody>" +
									"</table>" +
								"</div>";
								"</div>" +
								"<div class='notice_caption' style='margin-top:5px;'>※요금 및 이용시간은 달라질 수 있습니다.</div>";

    sesin_m_POINT03 = "<div class='title notice_caption' style='font-size:18px; margin-bottom: 10px;'>이용안내</div>" +
								"<div class='info'><strong>운영시간</strong> : 10:00 - 22:00</div>" +
								"<div> " +
									"<table>" +
										"<caption>세신 이용 요금 - 구분, 종류, 소요시간, 가격</caption>" +
										"<colgroup>" +
											"<col width='*'>" +
											/*"<col width='20%'>" +*/
											"<col width='40%'>" +
										"</colgroup>" +
										"<thead>" +
											"<tr>" +
												"<th>종류</th>" +
												/*"<th>소요시간</th>" +*/
												"<th class='last'>가격</th>" +
											"</tr>" +
										"</thead>" +
										"<tbody>" +
											"<tr>" +
												"<td>기본세신(모발관리)</td>" +
												/*"<td>20분</td>" +*/
												"<td class='last'>25,000</td>" +
											"</tr>" +
											"<tr>" +
												"<td>행복마사지(두피&바디)</td>" +
												/*"<td>20분</td>" +*/
												"<td class='last'>30,000</td>" +
											"</tr>" +
											"<tr>" +
												"<td>등,목 마사지</td>" +
												/*"<td>50분</td>" +*/
												"<td class='last'>60,000</td>" +
											"</tr>" +
											"<tr>" +
												"<td>전신 마사지</td>" +
												/*"<td>90분</td>" +*/
												"<td class='last'>100,000</td>" +
											"</tr>" +
											"<tr>" +
												"<td>달인표 VIP 마사지</td>" +
												/*"<td>120분</td>" +*/
												"<td class='last'>150,000</td>" +
											"</tr>" +
											"<tr>" +
												"<td>각질제거</td>" +
												/*"<td></td>" +*/
												"<td class='last'>10,000</td>" +
											"</tr>" +
										"</tbody>" +
									"</table>" +
								"</div>";
								"</div>" +
								"<div class='notice_caption' style='margin-top:5px;'>※요금 및 이용시간은 달라질 수 있습니다.</div>";

	    sesin_f_POINT05 = "<div class='title notice_caption' style='font-size:18px; margin-bottom: 10px;'>이용안내</div>" +
								"<div class='info'><strong>운영시간</strong> : 11:00 - 19:30</div>" +
			    				"<div> " +
				    				"<table>" +
					    				"<caption>세신 이용 요금 - 구분, 종류, 가격</caption>" +
										"<colgroup>" +
											"<col width='*'>" +
											"<col width='20%'>" +
											"<col width='20%'>" +
										"</colgroup>" +
										"<thead>" +
											"<tr>" +
												"<th>종류</th>" +
												"<th>소요시간</th>" +
												"<th class='last'>가격</th>" +
											"</tr>" +
										"</thead>" +
										"<tbody>" +
											"<tr>" +
												"<td>기본세신</td>" +
												"<td>25분</td>" +
												"<td class='last'>30,000</td>" +
											"</tr>" +
											"<tr>" +
												"<td>미니마사지</td>" +
												"<td>40분</td>" +
												"<td class='last'>60,000</td>" +
											"</tr>" +
											"<tr>" +
												"<td>아로마 미니 오일</td>" +
												"<td>40분</td>" +
												"<td class='last'>70,000</td>" +
											"</tr>" +
											"<tr>" +
												"<td>아로마 전신 마사지</td>" +
												"<td>60분</td>" +
												"<td class='last'>90,000</td>" +
											"</tr>" +
											"<tr>" +
												"<td>샴푸</td>" +
												"<td></td>" +
												"<td class='last'>5,000</td>" +
											"</tr>" +
										"</tbody>" +
									"</table>" +
								"</div>" +
								"<div class='notice_caption' style='margin-top:5px;'>※요금 및 이용시간은 달라질 수 있습니다.</div>";

    sesin_m_POINT05 = "<div class='title notice_caption' style='font-size:18px; margin-bottom: 10px;'>이용안내</div>" +
						"<div class='info'><strong>운영시간</strong> : 11:00 - 19:30</div>" +
						"<div> " +
							"<table>" +
								"<caption>세신 이용 요금 - 구분, 종류, 가격</caption>" +
								"<colgroup>" +
									"<col width='*'>" +
									"<col width='20%'>" +
									"<col width='20%'>" +
								"</colgroup>" +
								"<thead>" +
									"<tr>" +
										"<th>종류</th>" +
										"<th>소요시간</th>" +
										"<th class='last'>가격</th>" +
									"</tr>" +
								"</thead>" +
								"<tbody>" +
									"<tr>" +
										"<td>기본세신(모발관리)</td>" +
										"<td>20분</td>" +
										"<td class='last'>25,000</td>" +
									"</tr>" +
									"<tr>" +
										"<td>행복마사지(두피&바디)</td>" +
										"<td>20분</td>" +
										"<td class='last'>30,000</td>" +
									"</tr>" +
									"<tr>" +
										"<td>등,목 마사지</td>" +
										"<td>50분</td>" +
										"<td class='last'>60,000</td>" +
									"</tr>" +
									"<tr>" +
										"<td>전신 마사지</td>" +
										"<td>90분</td>" +
										"<td class='last'>100,000</td>" +
									"</tr>" +
									"<tr>" +
										"<td>달인표 VIP 마사지</td>" +
										"<td>120분</td>" +
										"<td class='last'>150,000</td>" +
									"</tr>" +
									"<tr>" +
										"<td>각질제거</td>" +
										"<td></td>" +
										"<td class='last'>10,000</td>" +
									"</tr>" +
								"</tbody>" +
							"</table>" +
						"</div>" +
						"<div class='notice_caption' style='margin-top:5px;'>※요금 및 이용시간은 달라질 수 있습니다.</div>";

    var johnny_content_POINT01 = "";

    johnny_content_POINT01 = /*"<div class='title'>자니로켓</div>" +*/
								"<div class='info'><strong>운영시간</strong> : 주말 11:30 - 20:00 (주중 미운영)</div>" +
                                "<div class='info'><strong>메인메뉴</strong></div>" +
								"<div class='contents'>" +
									"<ul>" +
										"<li>· Hamburger 햄버거</li>" +
										"<li>· Starters 스타터스</li>" +
										"<li>· Drinks & Shakes 음료 & 쉐이크</li>" +
									"</ul>" +
								"</div>" +
								"<div class='notice_caption'>※푸드 코너 운영시간 및 메뉴는 상황에 따라 달라질 수 있음.</div>";

    var table_selecor_content_POINT01 = "";
    var table_selecor_content_POINT03 = "";
    var table_selecor_content_POINT03_1 = "";
    var table_selecor_content_POINT05 = "";

    table_selecor_content_POINT01 = /*"<div class='title'>Select the table(푸드코트) MAIN MENU</div>" +*/
									"<div class='info' style='font-weight: bold;'><strong>운영시간</strong> : 11:30 - 19:30(찜질스파 & 워터파크)</div>" +
                                    "<div class='info'><strong><br/>메인메뉴</strong></div>" +
									"<div class='contents'>" +
										"<ul>" +
											"<li>· 육해밥 (한식류):쇠고기 미역국</li>" +
											"<li>· 난카츠 & 난우동(양식류):왕돈까스</li>" +
											"<li>· 차이나가든 (중식류):자장면 & 탕수육 셋트</li>" +
											"<li>· 오반클럽 (탄산음료): 콜라, 사이다</li>" +
											"<li>· 오밀조밀 (분식류): 떡볶이, 순대</li>" +
										"</ul>" +
									"</div>" +
									"<div class='notice_caption'>※푸드 코너 운영시간 및 메뉴는 상황에 따라 달라질 수 있음.</div>";

    table_selecor_content_POINT03 = /*"<div class='title'>Select the table(푸드코트) MAIN MENU</div>" +*/
									"<div class='info'><strong>운영시간</strong> : 11:00 - 18:30(루프탑풀)<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;11:30 - 19:30 (찜질스파)</div>" +
                                    "<div class='info'><strong><br/>메인메뉴</strong></div>" +
									"<div class='contents'>" +
										"<ul>" +
											"<li>· 육해밥 (한식류): 쇠고기 미역국</li>" +
											"<li>· 난카츠 & 난우동(양식류):왕돈까스</li>" +
											"<li>· 차이나가든 (중식류):자장면 & 탕수육 셋트</li>" +
											"<li>· 오밀조밀 (분식류): 떡볶이, 순대</li>" +
										"</ul>" +
									"</div>" +
									"<div class='notice_caption'>※푸드 코너 운영시간 및 메뉴는 상황에 따라 달라질 수 있음.</div>";

	table_selecor_content_POINT03_1 = /*"<div class='title'>Select the table(푸드코트) MAIN MENU</div>" +*/
									"<div class='info'><strong>운영시간</strong> : 11:00 - 18:30(루프탑풀)<br/>11:30 - 19:30 (찜질스파)</div>" +
                                    "<div class='info'><strong><br/>메인메뉴</strong></div>" +
									"<div class='contents'>" +
										"<ul>" +
											"<li>· 육해밥 (한식류): 쇠고기 미역국</li>" +
											"<li>· 난카츠 & 난우동(양식류):왕돈까스</li>" +
											"<li>· 차이나가든 (중식류):자장면 & 탕수육 셋트</li>" +
											"<li>· 오밀조밀 (분식류): 떡볶이, 순대</li>" +
										"</ul>" +
									"</div>" +
									"<div class='notice_caption'>※푸드 코너 운영시간 및 메뉴는 상황에 따라 달라질 수 있음.</div>";
									
	table_selecor_content_POINT05 = /*"<div class='title'>Select the table(푸드코트) MAIN MENU</div>" +*/
									"<div class='info'><br/><strong>*운영시간</strong> : 11:30 - 19:30 (라스트오더 19:00)</div>";
									//"<div class='contents'>" +
										//"<ul>" +
											//"<li>· 육해밥 (한식류): 쇠고기 미역국</li>" +
											//"<li>· 난카츠 & 난우동(양식류):왕돈까스</li>" +
											//"<li>· 차이나가든 (중식류):자장면 & 탕수육 셋트</li>" +
											//"<li>· 오밀조밀 (분식류): 떡볶이, 순대</li>" +
										//"</ul>" +
									//"</div>" +
									//"<div class='notice_caption'>※푸드 코너 운영시간 및 메뉴는 상황에 따라 달라질 수 있음.</div>";

	waveBar_POINT05 = /*"<div class='title'>Select the table(푸드코트) MAIN MENU</div>" +*/
						"<div class='info'><br/><strong>*운영시간</strong> : 11:30 - 18:00</div>";


	var cartoon_content_POINT05 = "";
	
	cartoon_content_POINT05 = "<div class='info'><br/><strong>*운영 시간</strong> : 10:00 - 22:00</div>";
					
	var outside_content_POINT05 = "";
	
	outside_content_POINT05 = "<div class='info notice_caption'><br/><strong class='option_sp'>자켓대여소에서 렌탈하실 수 있으며, 자세한 요금은 렌탈안내 페이지를 참조하여 주시기 바랍니다.</strong></div>";

    var spa_snack_content_POINT01 = "";
	var spa_snack_content_POINT03 = "";
	var family_room = "";

	family_room = "<div class='info'><br/><strong>이용대상</strong></div>" +
					"<div class='contents'>" +
						"<ul>" +
							"<li>· 성별이 다른 36개월 이상 ~ 초등학생 2학년 이하 자녀 및 동반 보호자 가족</li>" +
							"<li>· 등본, 가족관계증명서 등 서류 지참 필수</li>" +
						"</ul>" +
					"</div>";

    spa_snack_content_POINT01 = /*"<div class='title'>SPA SNACK MENU</div>" +*/
    							"<div class='info' style='font-weight: bold;'><strong>운영시간</strong> : 11:30 - 19:30</div>" +
                                "<div class='info'><strong><br/>메인메뉴</strong></div>" +
    							"<div class='contents'>" +
									"<ul>" +
										"<li>· 핫바, 구운계란, 케이크, 육포, 과자류, 음료,주류, 아이스크림 등</li>" +
									"</ul>" +
								"</div>" +
								"<div class='notice_caption'>※푸드 코너 운영시간 및 메뉴는 상황에 따라 달라질 수 있음.</div>";

    spa_snack_content_POINT03 = /* "<div class='title'>SPA SNACK MENU</div>" +*/
								"<div class='info'><strong>운영시간</strong> : 11:30 - 19:30</div>" +
                                "<div class='info'><strong><br/>메인메뉴</strong></div>" +
								"<div class='contents'>" +
									"<ul>" +
										"<li>· 핫바, 구운계란, 케이크, 육포, 과자류, 음료,주류, 아이스크림 등</li>" +
									"</ul>" +
								"</div>" +
								"<div class='notice_caption'>※푸드 코너 운영시간 및 메뉴는 상황에 따라 달라질 수 있음.</div>";
    

    var aqua_shop_content_POINT01 = "";
    aqua_shop_content_POINT01 = "<div class='info'><strong>운영시간</strong> : 11:00 - 18:00</div>";
    
    var shim_spa_content_POINT01 = "";
    var shim_spa_content_POINT03 = "";
    shim_spa_content_POINT01 = "<div class='info'><strong>운영시간</strong> : 11:00 - 20:00</div>";
    shim_spa_content_POINT03 = "<div class='info'><strong>운영시간</strong> : 11:00 - 20:00</div>";
    
    
	var floors_POINT01 = [
		{id : 'floor_rf', zoom:0, name : '실외 워터파크', cate : 0, faci : [{
			id : 'floor_rf', name : '실외 워터파크', name2: 'Outdoor Waterpark', cate : 0,
			unit : [
				{l:12, b:38,name : '주니어풀', text: '낮은 수심의 어린이 전용 풀이며,아이들이 더욱 안전하게 물놀이를 즐길 수 있습니다.', cate:0},
				{l:14, b:12,name : '자쿠지', text: '자쿠지는 물에서 기포가 발생하도록 한 욕조라는 의미로, 야외에서 4계절 모두 따뜻하게 물놀이를 즐길 수 있도록 체온유지에 도움을 주는 풀입니다.', cate:0},
				{l:27.5, b:21,name : '샌드풀', text: '촉각을 발달시키는 모래를 설치하여 유아의 상상력을 유발하고 정신과 육체적 성장에 도움을 주는 유아전용 풀입니다.', cate:0},
				{l:45, b:67,name : '테라피스파', text: '4계절 내내 노천욕을 즐길 수 있으며, 아로마 등 다양한 입욕제를 사용하여 더욱 편안하게 휴식할 수 있습니다.', cate:0},
				{l:45, b:53,name : '아로마스파', text: '4계절 내내 노천욕을 즐길 수 있으며, 아로마 등 다양한 입욕제를 사용하여 더욱 편안하게 휴식할 수 있습니다.', cate:0},
				{l:45, b:29,name : '인피니티풀', text: '‘L’자형의 국내 최장 인피니티 풀로 자연 그대로의 햇살과 함께 한강, 검단산을 비롯한 주변 경치를 바라보며 여유로운 물놀이를 즐길 수 있습니다.', cate:0},
				{l:73, b:3,name : '카바나', text: '물놀이 중 가족, 연인, 친구와 함께 주변경치를 바라보며 편안하게 휴식하고 힐링을 할 수 있는 쾌적하고 아늑한 휴식공간입니다.', cate:0}
			]
		}]},
		{id : 'floor_4f', zoom:1, name : '4층 전체', cate : 1, faci : [{
			id : 'floor_4f_2', name : '실내 워터파크', name2: 'Indoor Waterpark', cate : 2,
			unit : [
				{l:15.1, b:74,name : '바데풀', text: '물의 부력과 압력을 활용한 마사지 기능이 있으며, 등과 허리를 집중적으로 마사지 해주는 ‘파워 마사지’와 전신을 마사지해주는 ‘하이드로 젯’으로 뭉친 근육과 쌓인 피로를' +
                        ' 풀어주는 풀입니다.', cate:2},
				{l:18.5, b:78,name : '라군풀', text: '물의 흐름이 거의 없어 풀장 안에 설치되어 있는 의자에 앉아 여유롭게 휴식을 하거나, 남녀노소 부담 없이 물놀이를 할 수 있는 잔잔한 풀입니다.', cate:2},
				{l:18.5, b:89,name : '유수풀', text: '느린 속도로 물이 순환하여 가만히 있어도 마치 흐르는 강물에 몸을 맡긴 듯한 여유로움을 느낄 수 있습니다.', cate:2},
				{l:33.5, b:87.5,name : '웨이브바', text: '물놀이에 지친 몸을 달래줄 신세계 푸드 devil`s door의 맥주, 논알콜 칵테일, 음료, 스낵 등 다양한 먹거리를 즐길 수 있습니다.', cate:2},
				{l:35, b:72.5,name : '다크<br/> 트위스터', text: '터널 형태의 파이프 슬라이드로 암흑 속에서의 더욱 짜릿한 스릴감과 속도감을 즐길 수 있는 워터 슬라이드입니다.', cate:2},
				{l:35.5, b:59.5,name : '스카이<br/> 트위스터', text: '슬라이드 상단을 오픈하여 슬라이드 바깥의 풍경을 보며 2번의 연속커브와 함께 스릴감을 즐길 수 있는 워터 슬라이드입니다.', cate:2},
				{l:45.5, b:80.5,name : '보텍스풀', text: '물이 안쪽으로 계속 회전하는 풀로, 온 가족이 함께 물놀이를 즐길 수 있는 국내 워터파크 최초의 소용돌이 풀입니다.', cate:2},
				{l:41.1, b:74,name : '로보카폴리<br/> 키즈존', text: '로보카폴리, 로이, 엠버 등 캐릭터 조형물과 함께 인증샷을 찍을 수 있고, 워터 스프레이, 바닥분수 등이 설치되어 있어 물에 들어가지 않아도 물놀이를 즐길 수 있는 워터플레이존입니다.', cate:2},
				{l:41.3, b:63.5,name : '키즈<br/> 슬라이드', text: '어린이들이 안전하고 재미있게 즐길 수 있는 키즈용 슬라이드입니다.', cate:2},
				{l:44.7, b:63.5,name : '키즈풀', text: '가장 낮은 수심의 유아용 물놀이 풀로, 보호자가 안심하고 아이와 함께 안전한 물놀이를 즐길 수 있습니다.', cate:2},
				{l:26.4, b:52.5,name : '수유실', text: '수유가 필요한 아이에게 편안하고 쾌적하게 수유할 수 있으며, 잠시 잠을 재우거나, 기저귀를 교체할 수 있는 공간입니다.', cate:2},
				{l:29.4, b:52.5,name : '의무실', text: '물 놀이 중 다쳤을 경우, 치료와 안정을 취할 수 있도록 상처소독, 응급처치 및 병원 후송업무 등을 도와드립니다.', cate:2},
                {l:47.5, b:75,name : '윔풀', text: '따뜻한 온도의 풀로서 체온유지를 할 수 있습니다.', cate:2}
			]
		},{
			id : 'floor_4f_3', name : '찜질스파', name2: 'Jjimjil SPA', cate : 3,
			unit : [
				{l:37, b:25,name : '키즈룸', text: '어린이들이 안전하고 재미있는 시간을 보낼 수 있고, 보호자는 편히 휴식할 수 있도록 도움을 주는 어린이 전용 놀이공간입니다.', cate:3},
				{l:40, b:22.5,name : '미디어<br/> 아트룸', text: '360도 파노라마 영상을 활용한 시설로, 다양한 테마가 있는 영상과 함께 온열찜질을 체험 할 수 있는 힐링 공간입니다.', cate:3},
				{l:44, b:25,name : '힐링홀', text: 'TV를 시청하거나 상영되는 영화를 즐기며 일상을 잊을 수 있는 휴식공간입니다. 맥반석룸이나 키즈룸을 이용하는  일행을 기다릴 수 있는 만남의 장소입니다.', cate:3},
                {l:48, b:25,name : '맥반석룸', text: '경북 예천 맥반석을 사용하며, 몸 안에 쌓인 중금속과 노폐물을 제거하여 체질 개선과 스트레스 해소에 도움을 주는 찜질공간 입니다.', cate:3},
				{l:51, b:25,name : '구름방', text: '촉촉한 미스트로 구름 이미지를 연출하여 마치 구름 안에 있는 듯한 편안함으로, 깊은 휴식을 취하며 찜질을 즐길 수 있는 찜질공간입니다.', cate:3},
                {l:45.5, b:12,name : '커먼홀', text: '락커룸과 이어지는 통로이자 모든 찜질스파 이용고객을 위한 열린 휴식공간입니다.', cate:3},
				{l:64.5, b:28.5,name : '풋스파', text: '아름다운 한강 뷰를 감상하며 족욕을 통해 피로를 풀고 지친 일상에서 벗어나 여유로움과 편안함을 느낄 수 있는 야외 풋스파입니다.', cate:3},
                {l:74.5, b:28.5,name : '풋스파', text: '아름다운 한강 뷰를 감상하며 족욕을 통해 피로를 풀고 지친일상에서 벗어나 여유로움과 편안함을 느낄 수 있는 야외 풋스파입니다.', cate:3},
				{l:64.5, b:19.5,name : '패밀리홀', text: '다양한 찜질 공간에 가까이 위치하여 찜질스파 이용에 활기를 더해주는 휴식 공간입니다. 지인을 기다리거나 담소를 나누며 전면에 펼쳐진 한강 뷰를 즐길 수 있습니다.', cate:3},
                {l:61, b:5,name : '참숯방', text: '참숯의 명품이라 불리는 강원도 횡성 참나무 소재의 참숯을 사용한 찜질룸으로, 참숯의 음이온이 공기를 정화하고 체질개선에 도움을 주는 찜질공간입니다.', cate:3},
				{l:65.5, b:5,name : '황토방', text: '품질이 우수한 고창 황토를 사용하여, 황토의 향과  원적외선을 통해 심리적 안정과 편안함을 느끼게 해주는 전통적인 찜질공간입니다.', cate:3},
				{l:70, b:5,name : '불가마', text: '고창 황토벽돌을 사용한 고온의 찜질공간으로, 아로마 수증기와 고온에 의해 몸 스스로 열을 발생시키고 땀을 다량 배출하게 하여 노폐물 제거와 혈액순환에 도움을 줍니다.', cate:3},
				{l:74, b:4,name : '편백<br/>나무방', text: '편백나무의 피톤치드와 숲 속 영상으로 마치 자연에 있는 듯한 편안함을 느끼며 휴식을 취할 수 있습니다.', cate:3},
				{l:78, b:5,name : '소금방', text: '세계적으로 유명한 청정소금인 히말라야 암염 원석을 사용하여 노폐물과 각질 제거, 피부염 개선을 통해 피부미용은 물론 피부건강에도 도움을 줄 수 있는 찜질공간입니다.', cate:3},
				{l:83.5, b:25,name : '릴렉스룸', text: '고급 리클라이너 체어에서 편안하게 수면을 취하거나, TV, 영화, 음악 등을 즐길 수 있는 성인전용 힐링공간 입니다.', cate:3},
				{l:90, b:5,name : '쉼스파', text: '전문 테라피스트의 손길로 간단한 부분 마사지부터 스페셜 케어까지 다양한 에스테틱으로 몸의 피로를 풀고 피부미용에도 탁월한 힐링공간입니다.' + shim_spa_content_POINT01, cate:3}
			]
		},{
			id : 'floor_4f_5', name : '사우나', name2: 'Sauna', cate : 4,
			unit : [
				{l:1.8, b:49,name : '노천탕<br/>(여)', text: '야외 탕 속에서 피로를 풀고, 여유로운 시간과 색다른 재미를 느낄 수 있습니다.', cate:4},
				{l:4.8, b:49,name : '탄산탕<br/>(여)', text: '탄산이 피부를 부드럽게 해주고 노폐물 제거와 혈액순환을 도와주어 어른, 아이 모두 피부건강과 색다른 재미를 느낄 수 있습니다.', cate:4},
				{l:6.9, b:44,name : '세신<br/>(여)', text: '업계 최고의 세신가가 위생적인 세신도구와 프라이빗한 공간에서 최상의 서비스를 제공합니다.' + sesin_f_POINT01, cate:4},
				{l:44.5, b:49,name : '노천탕<br/>(남)', text: '야외 탕 속에서 피로를 풀고, 여유로운 시간과 색다른 재미를 느낄 수 있습니다.', cate:4},
				{l:48, b:49,name : '항아리탕<br/>(남)', text: '독소 및 노폐물 제거를 통해 혈액순환을 도와주고 피로를 풀어주는 항아리 모양의 탕입니다.', cate:4},
				{l:51, b:49,name : '세신<br/>(남)', text: '업계 최고의 세신가가 위생적인 세신도구와 프라이빗한 공간에서 최상의 서비스를 제공합니다.' + sesin_m_POINT01, cate:4},
                {l:19, b:30,name : '린넨카운터<br/>(남)', text: '수건 및 각종 사우나 용품을 구매하거나 대여할 수 있으며 찜질스파 이용고객을 위한 찜질복이 준비되어 있습니다.', cate:4},
                {l:14.2, b:11,name : '린넨카운터<br/>(여)', text: '수건 및 각종 사우나 용품을 구매하거나 대여할 수 있으며 찜질스파 이용고객을 위한 찜질복이 준비되어 있습니다.', cate:4}
			]
		},{
			id : 'floor_4f_1', name : 'F&B', name2: '', cate : 5,
			unit : [
				{l:58, b:71,name : '셀렉더<br/>테이블<br>(푸드코트)', text: '찜질스파와 워터파크 모두에서 이용 가능하며, 신세계 푸드의 다양한 대표 메뉴가 입점하여 든든하고 맛있는 음식과 질 좋은 서비스를 제공합니다.' + table_selecor_content_POINT01, cate:5},
				/*{l:58, b:62,name : '자니로켓', text: '미국 정통 프리미엄 햄버거 레스토랑을 즐기실 수 있습니다.' + johnny_content_POINT01, cate:5},*/
				{l:58, b:28.4,name : '스파<br/>스낵', text: '찜질스파에서 흘린 땀을 식혀줄 추억을 담은 전통적인 먹거리와 간단한 스낵류를 즐길 수 있습니다.' + spa_snack_content_POINT01, cate:5}
			]
		},{
			id : 'floor_4f_4', name : '기타시설', name2: 'Additional Facilities', cate : 6,
			unit : [
				{l:10.9, b:44,name : '정산소', text: '아쿠아필드 내부 시설물에서 이용하신 요금을 정산하실 수 있습니다.', cate:6}
			]
		}]},
		{id : 'floor_3f', zoom:0, name : '기타시설', cate : 6, faci : [{
			id : 'floor_3f', name : '기타시설', name2: 'Additional Facilities', cate : 7,
			unit : [
				{l:56, b:27,name : '매표소', text: '찝질스파 또는 워터파크 입장권을 매표소, 무인 키오스크에서 구매 하실 수 있습니다.', cate:7},
				{l:42, b:86,name : '컨시어즈', text: '음식물, 이동제한물품 등을 컨시어즈 데스크에 맡기시면 직원이 안전하게 보관해 드립니다.', cate:7},
				{l:7, b:44,name : '아쿠아샵', text: '다양한 브랜드의 비치웨어와 물놀이 용품, 그리고 아쿠아필드에서만 구매할 수 있는 시그니처 상품을 자유롭게 구경하고 구매하실 수 있습니다.' + aqua_shop_content_POINT01, cate:7}
			]
		}]}
	];

    var floors_POINT03 = [
        {id : 'floor_rf', zoom:0, name : '루프탑풀', cate : 0, faci : [{
            id : 'floor_rf', name : '루프탑풀', name2: 'Rooftop Pool', cate : 0,
            unit : [
                {l:4, b:63,name : '트램폴린', text: '아이들의 성장판을 자극하여 키성장에 도움을 주는 점프 활동을 할 수 있는 놀이 시설입니다.', img: 'img_tl_pop_01', cate:0},
                {l:8, b:42,name : '풀 나인틴(19)', text: '', img: 'img_pop_0_9', cate:0},
                {l:24, b:68,name : '셀렉더 테이블<br/>(푸드코트)', text: '신세계 푸드의 다양한 대표 메뉴가 입점하여 든든하고 맛있는 음식과 질 좋은 서비스를 제공합니다.' + table_selecor_content_POINT03, img: 'img_pop_5_2', cate:0},
                /*{l:12, b:22,name : '자쿠지', text: '자쿠지는 물에서 기포가 발생하도록 한 욕조라는 의미로, 야외에서 따뜻하게 물놀이를 즐길 수 있도록 체온유지에 도움을 주는 풀입니다.', img: 'img_pop_0_2', cate:0},*/
                {l:54, b:82,name : '인피니티풀', text: '인피니티풀에서 자연 그대로의 햇살과 함께 북한산의 경치를 바라보며 여유로운 물놀이를 즐길 수 있습니다.', img: 'img_pop_0_6', cate:0},
                {l:46, b:68,name : '테라피스파', text: '북한산을 바라보며 따뜻한 노천탕에서 편안하게 휴식할 수 있습니다.', img: 'img_pop_0_4', cate:0},
                {l:61, b:68,name : '아로마스파', text: '북한산을 바라보며 따뜻한 노천탕에서 편안하게 휴식할 수 있습니다.', img: 'img_pop_0_5', cate:0},
                {l:65, b:58,name : '스플래쉬 키즈존', text: '워터 스프레이, 바닥분수 등이 설치되어 있어 물에 들어가지 않아도 물놀이를 즐길 수 있는 워터플레이 존입니다.', img: 'img_pop_0_8', cate:0},
                /*{l:59, b:47,name : '샌드풀', text: '촉각을 발달시키는 모래를 설치하여 유아의 상상력을 유발하고 정신과 육체적 성장에 도움을 주는 유아전용 풀입니다.', img: 'img_pop_0_3', cate:0},
                {l:67, b:44,name : '주니어풀', text: '낮은 수심의 어린이 전용 풀이며,아이들이 더욱 안전하게 물놀이를 즐길 수 있습니다.', img: 'img_pop_0_1', cate:0}*/
                /*{l:32, b:47,name : '파라솔', text: '', cate:0},*/
                {l:29, b:18,name : '버블 플레이', text: '시각적인 색채의 LED조명과 폭신폭신한 과일향 버블로 어린이들의 오감을 활발하게 하는 놀이 공간입니다.', img: 'img_bb_01', cate:0},
                /*{l:17, b:8,name : '의무실', text: '물 놀이 중 다쳤을 경우, 치료와 안정을 취할 수 있도록 상처소독, 응급처치 및 병원 후송업무 등을 도와드립니다.', cate:0},
                {l:23, b:6,name : '구명조끼<br/> 대여소', text: '', cate:0},
                {l:33, b:6,name : '남자 파우더룸', text: '', cate:0},
                {l:5, b:1,name : '여자 파우더룸', text: '', cate:0}*/
            ]
        }]},
        {id : 'floor_4f', zoom:1, name : '4층 전체', cate : 1, faci : [{
            id : 'floor_4f_1', name : '찜질스파', name2: 'Jjimjil SPA', cate : 2,
            unit : [
                {l:25, b:91,name : '풋스파', text: '아름다운 북한산 뷰를 감상하며 족욕을 통해 피로를 풀고 지친일상에서 벗어나 여유로움과 편안함을 느낄 수 있는 야외 풋스파입니다.', img: 'img_pop_3_7', cate:2},
                {l:28, b:78,name : '키즈룸', text: '어린이들이 안전하고 재미있는 시간을 보낼 수 있고, 보호자는 편히 휴식할 수 있도록 도움을 주는 어린이 전용 놀이공간입니다.', img: 'img_pop_3_1', cate:2},
                {l:40, b:86,name : '패밀리홀', text: '다양한 찜질 공간에 가까이 위치하여 찜질스파 이용에 활기를 더해주는 휴식 공간입니다. 지인을 기다리거나 담소를 나누실수 있습니다.', img: 'img_pop_3_9', cate:2},
                {l:48.5, b:76,name : '편백<br/>나무방', text: '편백나무의 피톤치드와 숲 속 영상으로 마치 자연에 있는 듯한 편안함을 느끼며 휴식을 취할 수 있습니다.', img: 'img_pop_3_13', cate:2},
                {l:57, b:89,name : '릴렉스룸', text: '고급 리클라이너 체어에서 편안하게 수면을 취하거나, TV, 영화, 음악 등을 즐길 수 있는 성인전용 힐링공간 입니다.', img: 'img_pop_3_15', cate:2},
                {l:61, b:71,name : '참숯방', text: '참숯의 명품이라 불리는 강원도 횡성 참나무 소재의 참숯을 사용한 찜질룸으로, 참숯의 음이온이 공기를 정화하고 체질개선에 도움을 주는 찜질공간입니다.', img: 'img_pop_3_10', cate:2},
                {l:73, b:83,name : '쉼스파', text: '전문 테라피스트의 손길로 간단한 부분 마사지부터 스페셜 케어까지 다양한 에스테틱으로 몸의 피로를 풀고 피부미용에도 탁월한 힐링공간입니다.' + shim_spa_content_POINT03, img: 'img_pop_3_16', cate:2},
                {l:79, b:76,name : '맥반석룸', text: '경북 예천 맥반석을 사용하며, 몸 안에 쌓인 중금속과 노폐물을 제거하여 체질개선과 스트레스 해소에 도움을 주는 찜질공간 입니다.', img: 'img_pop_3_4', cate:2},
                {l:73.5, b:73.5,name : '스파스낵', text: '찜질스파에서 흘린 땀을 식혀줄 추억을 담은 전통적인 먹거리와 간단한 스낵류를 즐길 수 있습니다.' + spa_snack_content_POINT03, img: 'img_pop_5_3', cate:2},
                {l:84, b:63,name : '불가마', text: '고창 황토벽돌을 사용한 고온의 찜질공간으로, 아로마 수증기와 고온에 의해 몸 스스로 열을 발생시키고 땀을 다량 배출하게 하여 노폐물 제거와 혈액순환에 도움을 줍니다.', img: 'img_pop_3_12', cate:2},
                {l:85, b:50,name : '힐링홀', text: 'TV를 시청하거나 상영되는 영화를 즐기며 일상을 잊을 수 있는 휴식공간입니다.', img: 'img_pop_3_3', cate:2},
                {l:84, b:31,name : '미디어<br/> 아트룸', text: '360도 파노라마 영상을 활용한 시설로, 다양한 테마가 있는 영상과 함께 온열찜질을 체험 할 수 있는 힐링 공간입니다.', img: 'img_pop_3_2', cate:2},
                {l:78, b:47,name : '황토방', text: '품질이 우수한 고창 황토를 사용하여, 황토의 향과 원적외선을 통해 심리적 안정과 편안함을 느끼게 해주는 전통적인 찜질공간입니다.', img: 'img_pop_3_11', cate:2},
                {l:67, b:46,name : '소금방', text: '세계적으로 유명한 청정소금인 히말라야 암염 원석을 사용하여 노폐물과 각질 제거, 피부염 개선을 통해 피부미용은 물론 피부건강에도 도움을 줄 수 있는 찜질공간입니다.', img: 'img_pop_3_14', cate:2},
                {l:62, b:48,name : '구름방', text: '촉촉한 미스트로 구름 이미지를 연출하여 마치 구름 안에 있는 듯한 편안함으로, 깊은 휴식을 취하며 찜질을 즐길 수 있는 찜질공간입니다.', img: 'img_pop_3_5', cate:2},
                /*{l:42.5, b:49,name : '패밀리홀', text: '다양한 찜질 공간에 가까이 위치하여 찜질스파 이용에 활기를 더해주는 휴식 공간입니다. 지인을 기다리거나 담소를 나누실수 있습니다.', img: 'img_pop_3_3', cate:2},*/
                {l:58, b:56,name : '수유실', text: '수유가 필요한 아이에게 편안하고 쾌적하게 수유할 수 있으며, 잠시 잠을 재우거나, 기저귀를 교체할 수 있는 공간입니다.', img: 'img_pop_2_11', cate:2}

            ]
        },{
            id : 'floor_4f_2', name : '사우나', name2: 'Sauna', cate : 3,
            unit : [
                {l:2.7, b:59,name : '세신<br/>(여)', text: '업계 최고의 세신가가 위생적인 세신도구와 프라이빗한 공간에서 최상의 서비스를 제공합니다.' + sesin_f_POINT03, img: 'img_pop_4_3', cate:3},
                {l:16, b:57,name : '사우나(여)', text: '다양한 탕속애서 피로를 풀고, 여유로운 시간과 색다른 재미를 느낄 수 있습니다.', img: 'img_pop_4_1', cate:3}, //사우나(여)
                {l:36.5, b:56,name : '사우나(남)', text: '다양한 탕속애서 피로를 풀고, 여유로운 시간과 색다른 재미를 느낄 수 있습니다.', img: 'img_pop_4_2', cate:3}, //사우나(남)
                {l:51, b:66,name : '세신(남)', text: '업계 최고의 세신가가 위생적인 세신도구와 프라이빗한 공간에서 최상의 서비스를 제공합니다.' + sesin_m_POINT03, img: 'img_pop_4_6', cate:3}
                /*{l:1.8, b:49,name : '노천탕<br/>(여)', text: '야외 탕 속에서 피로를 풀고, 여유로운 시간과 색다른 재미를 느낄 수 있습니다.', cate:3},
                {l:4.8, b:49,name : '탄산탕<br/>(여)', text: '탄산이 피부를 부드럽게 해주고 노폐물 제거와 혈액순환을 도와주어 어른, 아이 모두 피부건강과 색다른 재미를 느낄 수 있습니다.', cate:3},*/
                /* {l:44.5, b:49,name : '노천탕<br/>(남)', text: '야외 탕 속에서 피로를 풀고, 여유로운 시간과 색다른 재미를 느낄 수 있습니다.', cate:3},
                {l:48, b:49,name : '항아리탕<br/>(남)', text: '독소 및 노폐물 제거를 통해 혈액순환을 도와주고 피로를 풀어주는 항아리 모양의 탕입니다.', cate:3}, */
                /* {l:19, b:30,name : '린넨카운터<br/>(남)', text: '수건 및 각종 사우나 용품을 구매하거나 대여할 수 있으며 찜질스파 이용고객을 위한 찜질복이 준비되어 있습니다.', cate:3},
                {l:14.2, b:11,name : '린넨카운터<br/>(여)', text: '수건 및 각종 사우나 용품을 구매하거나 대여할 수 있으며 찜질스파 이용고객을 위한 찜질복이 준비되어 있습니다.', cate:3}*/
            ]
        },{
            id : 'floor_4f_3', name : 'F&B', name2: '', cate : 4,
            unit : [
                {l:5, b:86,name : '셀렉더 테이블(푸드코트)', text: '신세계 푸드의 다양한 대표 메뉴가 입점하여 든든하고 맛있는 음식과 질 좋은 서비스를 제공합니다.' + table_selecor_content_POINT03, img: 'img_pop_5_2', cate:4}
            ]
        }]},
    ];
var is_slide = 0;
var btn_down = 0;
var dubble_ico = 0;
	var floors_POINT05 = [
		{id : 'floor_rf', zoom:0, name : '야외 워터파크', cate : 0, faci : [{
			id : 'floor_rf', name : '야외 워터파크', name2: 'Outdoor Waterpark', cate : 0,
			unit : [
				{l:46.5, b:81,name : '인피티니풀', text: '도심 속의 휴양지에서 휴식과 힐링을 느낄 수 있으며, 넓은 면적에서 여유롭게 물놀이를 즐길 수 있습니다.', cate:0},
				{l:50.5, b:39,name : '샌드풀', text: '촉각을 발달시키는 모래를 설치하여 유아의 상상력을 유발하고 정신과 육체적 성장에 도움을 주는 유아전용 풀입니다.', cate:0},
				{l:52.5, b:60,name : '자쿠지', text: '물에서 기포가 발생하는 야외 욕조에서 따뜻하게 물놀이를 즐길 수 있도록 체온유지에 도움을 주는 풀입니다.', cate:0},
				{l:52.5, b:26,name : '트램폴린', text: '아이들의 성장판을 자극해서 키 성장에 도움을 주는 점프 활동을 할 수 있는 놀이 시설입니다.', cate:0},
				{l:62.5, b:32,name : '카바나', text: '럭셔리하고 프라이빗한 공간에서 편안하게 물놀이를 즐기실 수 있습니다.' + outside_content_POINT05, cate:0},
				{l:47.2, b:17,name : '데이베드', text: '럭셔리하고 프라이빗한 공간에서 편안하게 물놀이를 즐기실 수 있습니다.' + outside_content_POINT05, cate:0},
				{l:35.5, b:56,name : '썬베드', text: '실내 및 야외에 설치되어 있어 편안하게 물놀이를 즐기실 수 있습니다.' + outside_content_POINT05, cate:0},
				{l:61.5, b:56,name : '썬베드', text: '실내 및 야외에 설치되어 있어 편안하게 물놀이를 즐기실 수 있습니다.' + outside_content_POINT05, cate:0, dubble_ico:1},
				{l:36.5, b:30,name : '빈백 라운지', text: '편안하게 눕거나 앉을 수 있는 안락하고 푹신푹신한 빈백에서 인피니티풀의 전경을 보며 힐링을 할 수 있는 특별한 공간입니다.' + outside_content_POINT05, cate:0}
			]
		}]},
		{id : 'floor_4f', zoom:1, name : '3층 전체', cate : 1, faci : [{
			id : 'floor_4f_2', name : '실내 워터파크', name2: 'Indoor Waterpark', cate : 2,
			unit : [
				{l:15.95, b:59.8,name : '토들러존', text: '낮은 수심의 유아용풀에 워터토이, 미니슬라이드 등 어린이가 안전하고 재미있게 즐길 수 있는 복합 워터 놀이공간입니다.', cate:2, is_slide:1},
				//{l:18.5, b:78,name : '워터 토이', text: '물의 흐름이 거의 없어 풀장 안에 설치되어 있는 의자에 앉아 여유롭게 휴식을 하거나, 남녀노소 부담 없이 물놀이를 할 수 있는 잔잔한 풀입니다.', cate:2},
				//{l:18.5, b:89,name : '유수풀', text: '느린 속도로 물이 순환하여 가만히 있어도 마치 흐르는 강물에 몸을 맡긴 듯한 여유로움을 느낄 수 있습니다.', cate:2},
                {l:13.5, b:90.5,name : '키즈존', text: '시원하게 쏟아지는 물의 재미를 느낄 수 있는 워터 버켓, 아이들을 위한 터널&와이드 슬라이드, 물 속의 언덕에서 오르락 내리락 하며 즐겁게 놀 수 있는 웻바운스 등 다양하게 체험할 수 있는 복합 워터 놀이공간입니다.', cate:2, is_slide:1},
				{l:4, b:79,name : '버블 플레이', text: '시각적인 색채의 LED조명과 폭신폭신한 과일향 버블로 어린이들의 오감을 발달시키는 놀이 공간입니다.', cate:2},
				{l:22.7, b:64.5,name : '피쉬풀', text: '국내 최초로 워터파크에서 선보이는 피쉬풀은 다양한 열대해수어를 보면서 간접적인 스노클링을 체험할 수 있는 공간입니다.', cate:2},
				{l:27.1, b:76,name : '미디어 샌드풀', text: '촉각을 발달시키는 모래놀이로 유아의 상상력을 유발하고 정신과 육체적 성장에 도움을 주는 유아전용 풀입니다.', cate:2},
				{l:24, b:85.5,name : '패밀리풀', text: '최대 수심1.2m의 넓은 공간의 풀에서 자유롭게 수영을 즐길 수 있는 공간입니다.', cate:2},
				{l:10.9, b:71,name : '대여소', text: '물놀이 용품을 구매하거나 구명자켓, 카바나, 데이베드  등 렌탈용품을 대여하실 수 있습니다.', cate:2},
				{l:10.9, b:67.5,name : '수유실', text: '수유가 필요한 아이에게 편안하고 쾌적하게 수유할 수 있으며, 잠시 잠을 재우거나, 기저귀를 교체할 수 있는 공간입니다.', cate:2},
				{l:10.9, b:63.2,name : '의무실', text: '물놀이 중 다쳤을 경우, 치료와 안정을 취할 수 있도록 상처소독, 응급처치 및 병원 후송업무 등을 도와드립니다.', cate:2},
				//{l:41.3, b:63.5,name : '키즈<br/> 슬라이드', text: '어린이들이 안전하고 재미있게 즐길 수 있는 키즈용 슬라이드입니다.', cate:2},
				//{l:44.7, b:63.5,name : '키즈풀', text: '가장 낮은 수심의 유아용 물놀이 풀로, 보호자가 안심하고 아이와 함께 안전한 물놀이를 즐길 수 있습니다.', cate:2},
			]
		},{
			id : 'floor_4f_3', name : '찜질스파', name2: 'Jjimjil SPA', cate : 3,
			unit : [
                {l:25, b:27,name : 'GX룸', text: '요가, 명상 등 다양한 프로그램을 체험하면서 바쁘고 지친 생활 속에서 건강하고 아름다운 나를 찾는 공간입니다.', cate:3},// + shim_spa_content_POINT01,
                {l:28.2, b:27,name : '소금방', text: '세계적으로 유명한 청정소금인 히말라야 암염 원석을 사용하여 노폐물과 각질 제거, 피부염 개선을 통해 피부미용은 물론 피부건강에도 도움을 줄 수 있는 찜질공간입니다.', cate:3},
                {l:47.5, b:86,name : '힐링홀', text: '다양한 찜질 공간에 가까이 위치하여 찜질스파 이용에 활기를 더해주는 휴식 공간입니다. 지인을 기다리거나 담소를 나누며 전면에 펼쳐진 뷰를 즐길 수 있습니다.', cate:3},
                {l:42, b:27,name : '황토방', text: '품질이 우수한 고창 황토를 사용하여, 황토의 향과  원적외선을 통해 심리적 안정과 편안함을 느끼게 해주는 전통적인 찜질공간입니다.', cate:3},
                {l:38.5, b:41,name : '구름방', text: '촉촉한 미스트로 구름 이미지를 연출하여 마치 구름 안에 있는 듯한 편안함으로, 깊은 휴식을 취하며 찜질을 즐길 수 있는 공간입니다.', cate:3},
                {l:49.5, b:39,name : '파동석룸', text: '원적외선, 음이온, 테라헤르츠파 등을 방출하는 파동석 위에 편안하게 엎드리거나 누워 체내 독소를 배출시키는 찜질공간입니다.', cate:3},
                {l:54, b:40,name : '로울리<br/>불가마', text: '뜨겁게 달궈진 사우나 스톤으로 떨어지는 물이 수증기 열을 발생시키는 원리인 핀란드식 증기욕 ‘로울리(Loyly)’에 전통적인 한국식 불가마를 결합하여 색다른 찜질을 경험을 하실 수 있는 공간입니다.', cate:3, btn_down:1},
                {l:43.5, b:49,name : '미디어<br/>아트룸', text: '360도 파노라마 영상을 활용한 시설로, 시각적인 재미와 함께 찜질을 체험 할 수 있는 힐링 공간입니다.', cate:3},
				{l:42.5, b:65,name : '키즈룸', text: '어린이들이 안전하고 재미있는 시간을 보낼 수 있고, 보호자는 편히 휴식할 수 있도록 도움을 주는 어린이 전용 놀이공간입니다.', cate:3},
				{l:49.5, b:71,name : '편백<br/>나무방', text: '편백나무의 피톤치드와 숲 속 영상으로 마치 자연에 있는 듯한 편안함을 느끼며 휴식을 취할 수 있습니다.', cate:3},
				{l:35.5, b:27,name : '패밀리홀', text: '편안하게 미디어를 시청하며, 일상을 잊을 수 있는 휴식공간입니다.', cate:3},
                {l:53, b:92,name : '풋스파', text: '야외에서 즐기는 족욕을 통해 피로를 풀고 지친일상에서 벗어나 여유로움과 편안함을 느낄 수 있는 공간입니다.', cate:3},
				{l:63, b:87,name : '릴렉스룸', text: '고급 리클라이너 체어에서 편안하게 수면을 취하거나, TV, 영화, 음악 등을 즐길 수 있는 성인전용 힐링공간 입니다.', cate:3}
                //{l:61, b:5,name : '참숯방', text: '참숯의 명품이라 불리는 강원도 횡성 참나무 소재의 참숯을 사용한 찜질룸으로, 참숯의 음이온이 공기를 정화하고 체질개선에 도움을 주는 찜질공간입니다.', cate:3},
			]
		},{
			id : 'floor_4f_5', name : '사우나', name2: 'Sauna', cate : 4,
			unit : [
				//{l:1.8, b:49,name : '락카룸<br/>(여)', text: '락카룸', cate:4},
				{l:34, b:54.5,name : '파우더룸<br/>(여)', text: '아쿠아필드 어매니티 ‘캄모멘트리’로 편안하게 힐링의 마무리를 하실 수 있는 공간입니다.', cate:4},
				{l:22.8, b:54,name : '노천탕<br/>(여)', text: '야외 탕 속에서 피로를 풀고, 여유로운 시간과 색다른 재미를 느낄 수 있습니다.', cate:4},
				{l:26, b:68,name : '사우나<br/>(여)', text: '다양한 탕 속에서 색다른 재미를 느끼며, 피로를 풀고 여유로운 시간을 보내실 수 있습니다.', cate:4},
				//{l:4.8, b:49,name : '건식사우나<br/>(여)', text: '건식사우나', cate:4},
                {l:39, b:62,name : '세신실<br/>(여)', text: '아쿠아필드 전문 세신사가 위생적인 세신 도구와 프라이빗한 공간에서 최상의 서비스를 제공합니다.' + sesin_f_POINT05, cate:4}, 
                //{l:16.2, b:53,name : '가족<br/>샤워실', text: '혼자 샤워가 어려운 이성 자녀를 편안하게 케어할 수 있는 공간으로 이용 대상 여부 확인 후 이용하실 수 있습니다.', cate:4},
                //{l:1.8, b:49,name : '락카룸<br/>(남)', text: '락카룸', cate:4},
				{l:8.7, b:43,name : '파우더룸<br/>(남)', text: '아쿠아필드 어매니티 ‘캄모멘트리’로 편안하게 힐링의 마무리를 하실 수 있는 공간입니다.', cate:4},
				{l:2, b:48.5,name : '노천탕<br/>(남)', text: '야외 탕 속에서 피로를 풀고, 여유로운 시간과 색다른 재미를 느낄 수 있습니다.', cate:4},
				{l:1.7, b:60,name : '사우나<br/>(남)', text: '다양한 탕 속에서 색다른 재미를 느끼며, 피로를 풀고 여유로운 시간을 보내실 수 있습니다.', cate:4},
				//{l:48, b:49,name : '건식사우나<br/>(남)', text: '건식사우나', cate:4},
				{l:4.6, b:68,name : '세신실<br/>(남)', text: '아쿠아필드 전문 세신사가 위생적인 세신 도구와 프라이빗한 공간에서 최상의 서비스를 제공합니다.' + sesin_m_POINT05, cate:4},// + sesin_m_POINT01
                
			]
		},{
			id : 'floor_4f_1', name : 'F&B', name2: '', cate : 5,
			unit : [
				{l:38.3, b:86,name : '푸드코트', text: '찜질스파와 워터파크 모두에서 이용 가능하며, 한식, 양식, 중식 다양한 메뉴의 푸드 서비스를 제공합니다.'  + table_selecor_content_POINT05, cate:5},
				{l:35.3, b:90,name : '웨이브바', text: '맥주, 음료, 스낵, 디저트 등 다양한 먹거리를 즐길 수 있습니다.'  + waveBar_POINT05, cate:5}//
			]
		},{
			id : 'floor_4f_4', name : '기타시설', name2: 'Additional Facilities', cate : 6,
			unit : [
				{l:31, b:12,name : 'Day Comics<br /> 만화&보드게임', text: '가족, 친구, 연인과 함께 만화책, 보드게임 등 다양한 놀거리와 맛있는 식음료를 먹으면 힐링할 수 있는 공간입니다.' + cartoon_content_POINT05, cate:7},
				{l:40, b:12,name : 'Cafe De AQUA<br /> 수족관 카페', text: '다양한 어종의 물고기를 보며 여유로운 휴식과 맛있는 카페음료를 먹을 수 있는 공간입니다.' + cartoon_content_POINT05, cate:7},
				{l:19, b:11.5,name : '매표소', text: '찜질스파 또는 워터파크 입장권을 무인 키오스크에서 구매하실 수 있습니다.', cate:7},
				{l:17.5, b:15,name : '컨시어즈', text: '입장 후 음식물, 이용제한물품 등을 컨시어즈 데스크에 데스크에 맡기실 수 있습니다.', cate:7},
				{l:15, b:4,name : '록시걸(용품샵)', text: '록시, 퀵실버, 배럴 등 엄선된 브랜드의 스윔&비치웨어와 물놀이 용품 등 20개 이상의 브랜드를 아쿠아샵 록시걸에서 편안하게 만나보실 수 있습니다.', cate:7},
				{l:10, b:29,name : '신발장', text: '발권 영수증에 기재된 번호의 신발장을 이용하실 수 있습니다.', cate:7},
				{l:14, b:44,name : '정산소', text: '락커키로 결제한 부대시설 등의 이용금액을 정산하실 수 있습니다.', cate:7},
				{l:12, b:53,name : '가족샤워실', text: '혼자 샤워가 어려운 이성 자녀를 편안하게 케어할 수 있는 공간으로 이용 대상 여부 확인 후 이용하실 수 있습니다.' + family_room, cate:7},
				//{l:7, b:44,name : '아쿠아샵', text: '다양한 브랜드의 비치웨어와 물놀이 용품, 그리고 아쿠아필드에서만 구매할 수 있는 시그니처 상품을 자유롭게 구경하고 구매하실 수 있습니다.' + aqua_shop_content_POINT01, cate:7}
			]
		}]},
	];

    if($('body').attr('id') == 'POINT01'){
        floors = floors_POINT01;
    }else if($('body').attr('id') == 'POINT03'){
        floors = floors_POINT03;
    }else if($('body').attr('id') == 'POINT05'){
        floors = floors_POINT05;
	}else{
        floors = floors_POINT01;
    }
	var icoImgs = [
		{on:'ico_cate0_on.png', off:'ico_cate0_off.png', color:''},
		{on:'ico_cate1_on.png', off:'ico_cate1_off.png', color:''},
		{on:'ico_cate2_on.png', off:'ico_cate2_off.png', color:''},
		{on:'ico_cate3_on.png', off:'ico_cate3_off.png', color:''},
		{on:'ico_cate4_on.png', off:'ico_cate4_off.png', color:''},
		{on:'ico_cate5_on.png', off:'ico_cate5_off.png', color:''},
		{on:'ico_cate6_on.png', off:'ico_cate6_off.png', color:''}
	]
	var nav = $('#facilities .nav_floor'), navLi = nav.find('li'), draggable = [], trans = {};
	var zoomBtnArea = $(".zoomArea"), zoomInBtn = zoomBtnArea.find('button.in'), zoomOutBtn = zoomBtnArea.find('button.out'), zoomRate = params.zoomRate || 0.25;
    var zoomFlag = false;
    trans = params.transforms ? {x:'x',y:'y'} : {x:'left',y:'top'};
	this.liArr = liArr = [];
	for (var i = 0; i < list.length; i++) { //퍼포먼스 향상을 위해 각각의 엘리먼트들은 배열로 저장해 둠.
		liArr[i] = {};
		liArr[i].el = list.eq(i);
		liArr[i].liIn = liArr[i].el.find(' > .in');
		liArr[i].bxSvg = liArr[i].el.find('.bx_svg');
		liArr[i].svg = liArr[i].bxSvg.find(params.imgType);
		liArr[i].pin = liArr[i].el.find('.bx_pin');
		liArr[i].navSect = liArr[i].el.find('.nav_sect');
		if(floors[i].faci){
			liArr[i].faci = [];
			for (var j = 0; j < floors[i].faci.length; j++) {
				liArr[i].faci[j] = {};
				liArr[i].faci[j].el = document.getElementById(floors[i].faci[j].id);
			}
		}
	}

	this.getIndex = function(){return this.activeEle ? this.activeEle.index() : 0;} //활성화된 층의 인덱스 가져오기
    this.tweenMaxCross = function(tp,el,d,o){
        var obj = new Object();
        tp = tp || 'to';
        if(o.x !== undefined) obj[trans.x] = o.x;
        if(o.y !== undefined) obj[trans.y] = o.y;
        if(o.ease) obj['ease'] = o.ease;
        if(o.onComplete) obj['onComplete'] = o.onComplete;
        TweenMax[tp](el, d, obj);
    };

	this.getSize = function(o){ //영역사이즈 계산하기
		var obj = {};
		o.svg = $(o.svg);
        obj.win = {w : window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth, h : window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight};
		obj.li = {w : o.liEle.width(), h : o.liEle.height()};
        obj.minSize = {w : obj.li.w*params.minZoom, h : obj.li.h*params.minZoom};
		obj.svg = {w : o.svg.width(), h : o.svg.height(),t : o.svg.offset().top, l : o.svg.offset().left}
		return obj;
	};

	this.setUints = function(i, j, o){ //각 시설들 좌표 세팅
		o = o || {};
		if(floors[i].faci[j] && floors[i].faci[j].unit){
			var str = '', units = floors[i].faci[j].unit;
			str +='<ul class="'+(o.hide ? 'hide' : '')+'">\n';
			for (var k = 0; k < units.length; k++) {
				if(units[k]['btn_down'] == 1){
					str +='	<li style="left:'+units[k].l+'%; bottom:'+units[k].b+'%"><button data-dth1="'+i+'" data-dth2="'+j+'" data-dth3="'+k+'"><span class="text">'+units[k].name+'</span><span class="ico"><img src="/common/front/images/'+POINT_CODE+'/facilities/ico_point_'+i+'.png"/ alt="POINT"></span></button></li>\n';
				}else{
					if(units[k]['dubble_ico'] == 1){
						str +='	<li style="left:'+units[k].l+'%; bottom:'+units[k].b+'%"><button data-dth1="'+i+'" data-dth2="'+j+'" data-dth3="'+(k-1)+'"><span class="ico"><img src="/common/front/images/'+POINT_CODE+'/facilities/ico_point_'+i+'.png"/ alt="POINT"></span><span class="text">'+units[k].name+'</span></button></li>\n';
					}else{
						str +='	<li style="left:'+units[k].l+'%; bottom:'+units[k].b+'%"><button data-dth1="'+i+'" data-dth2="'+j+'" data-dth3="'+k+'"><span class="ico"><img src="/common/front/images/'+POINT_CODE+'/facilities/ico_point_'+i+'.png"/ alt="POINT"></span><span class="text">'+units[k].name+'</span></button></li>\n';
					}
				}
			}
			str +='</ul>\n';
		}
		liArr[i].faci[j].unit = units;
		liArr[i].faci[j].unitEl = $(str);
		liArr[i].pin.append(liArr[i].faci[j].unitEl);
	};

    this.hideUnit = function(i){
        for (var j = 0; j < liArr[i].faci.length; j++) {
            if(liArr[i].faci[j].el) liArr[i].faci[j].el.setAttribute('class','hover_able');
            liArr[i].faci[j].unitEl.addClass('hide');
        }
    };

	this.setNavSect = function(i){
		var str = '', imgPath = '/common/front/images/'+POINT_CODE+'/facilities/';
		str +='<li class="c'+floors[i].cate+'"><a href="#/facilities/index.af?depth1='+(i+1)+'"><span class="i-h"><img src="'+imgPath+icoImgs[floors[i].cate].off+'" alt="ICON_OFF" /><img class="ov" src="'+imgPath+icoImgs[floors[i].cate].on+'" alt="ICON_ON" /></span><span class="txt">'+floors[i].name+'</span></a></li>'
		var faci = floors[i].faci;
		if(faci.length > 1) {
			for (var j = 0; j < faci.length; j++) {
				str += '<li class="c'+faci[j].cate+'"><a href="#/facilities/index.af?depth1='+(i+1)+'&depth2='+(j+1)+'"><span class="i-h"><img src="'+imgPath+icoImgs[faci[j].cate].off+'" alt="ICON_OFF" /><img class="ov" src="'+imgPath+icoImgs[faci[j].cate].on+'" alt="ICON_ON" /></span><span class="txt">'+faci[j].name+'</span></a></li>\n';
			}
		}
		liArr[i].navSect.append(str);
		var navSectLi = liArr[i].navSect.find('li');
		liArr[i].navLi = [];
		for (var j = 0; j < navSectLi.length; j++) {
			liArr[i].navLi[j] = navSectLi.eq(j);
		}
	};

	this.setFullSizeMap = function(op){ //이미지들 풀사이즈로 맞추기
		var o = {liEle:liArr[op.i].el, listIn:liArr[op.i].bxSvg, svg : liArr[op.i].svg};
		op.motion = op.motion || 0;
		var obj = this.getSize(o);
		var rate = (obj.li.w/obj.li.h) > (obj.svg.w/obj.svg.h) ? obj.minSize.h/obj.svg.h : obj.minSize.w/obj.svg.w;
		if(obj.win.w <= 768) rate = rate*2
        var listInCng = {x : obj.svg.w*rate, y : obj.svg.h*rate};
		if(floors[op.i].zoom) _this.hideUnit(op.i);
		if(liArr[op.i].navLi) liArr[op.i].navLi[0].siblings('.on').removeClass('on').end().addClass('on');
        TweenMax.to(o.svg, op.motion, {width : listInCng.x,height : listInCng.y, ease : Power3.easeInOut});
		if(obj.win.w <= 768){
            bx.css('height', bx.width());
            if(obj.win.w <= 500) { bx.css('height', 510); }
            this.tweenMaxCross('to', o.listIn, op.motion, {x: 0,  y: 0, ease : Power3.easeInOut, onComplete : function(){zoomFlag = false;}});
        }else{
            bx.css('height', '');
            var x = 0;
            if(op.i == 2) x = 100;
            this.tweenMaxCross('to', o.listIn, op.motion, {x: (obj.li.w - listInCng.x)/2 - x,  y: (obj.li.h - listInCng.y)/2, ease : Power3.easeInOut, onComplete : function(){zoomFlag = false;}});
        }
    };
	this.moveFloor = function(idx, interval){ //층 이동 함수
		var cIdx = _this.getIndex();
		if(idx === cIdx) return;
		var ulT = ul.offset().top;
		navLi.eq(idx).siblings('.on').removeClass('on').end().addClass('on');
		_this.activeEle = liArr[idx].el.siblings('.on').removeClass('on').end().addClass('on');
		var liT = _this.activeEle.offset().top;
		_this.setFullSizeMap({i:idx});
		_this.tweenMaxCross('to', ul,interval,{y : ulT-liT, ease : Power3.easeInOut, onComplete : function(){
			if(cIdx !== ''){
				if(floors[cIdx].zoom) _this.hideUnit(cIdx);
			}
		}});
        $('#facilities .list_floors .bx_pin button').css('top', '');
	};
	this.zoomSect = function(e, i, j, interval){//섹션 줌인 시키기
        var o = {unit : liArr[i].faci[j].el,svg : liArr[i].svg,liEle : liArr[i].el,listIn : liArr[i].bxSvg};
		var obj = _this.getSize(o);
        var self = _this.getUnitPositon(this);
		var rate = (obj.li.w/obj.li.h) > (self.w/self.h) ? obj.li.h/self.h : obj.li.w/self.w;
        if(obj.win.w <= 768){
            if(j == 0){
                rate = rate*2;
            }else if(j== 1){
                rate = rate*4;
            }else if(j== 2){
                rate = rate*3;
            }else if(j== 3){
                rate = rate*2;
            }else if(j== 4){
                rate = rate*1.5;
            }
            if(POINT_CODE == "POINT03"){
                if(j == 0){
                    rate = rate/1.5;
                }else if(j== 1){
                    rate = rate/3;
                }
            }
        }
        var listInCng = {x : obj.svg.w*rate, y : obj.svg.h*rate};
		var tween = {
			x : Math.min(Math.max((obj.li.w - self.w*rate)/2 + obj.svg.l - (obj.svg.l+(self.l-obj.svg.l)*rate), obj.li.w-listInCng.x), 0),
			y : Math.min(Math.max((obj.li.h - self.h*rate)/2 + obj.svg.t - (obj.svg.t+(self.t-obj.svg.t)*rate), obj.li.h-listInCng.y), 0)
		};
		for (var k = 0; k < liArr[i].faci.length; k++) {
			var sec = liArr[i].faci[k].el;
			sec ? (k == j ? sec.setAttribute('class','hover_able on') : sec.setAttribute('class','hover_able')) : null;
		}
		liArr[i].navLi[j+1].siblings('.on').removeClass('on').end().addClass('on');
		liArr[i].faci[j].unitEl.siblings().addClass('hide').end().removeClass('hide');

        if(obj.win.w <= 768){
             if(j== 1){
                tween.x = tween.x-200
            }else if(j== 2){
                tween.x = 0
                tween.y = tween.y+100
            }
            if(POINT_CODE == "POINT03"){
                if(j== 0){
                    tween.y = 0
                }else if(j== 1){
                    tween.x = 0
                    tween.y = tween.y+50
                }
            }
        }

        TweenMax.to(o.svg, interval, {width : listInCng.x,height : listInCng.y,ease : Power3.easeInOut});
		_this.tweenMaxCross('to', o.listIn, interval, {x : tween.x,y : tween.y,ease : Power3.easeInOut,onComplete : function(){
			if(draggable[_this.getIndex()]) draggable[_this.getIndex()][0].update();
		}});
		if(e) e.stopPropagation();
        $('#facilities .list_floors .bx_pin button').css('top', '');
	};
	for (var i = 0; i < liArr.length; i++) {
		var listIn = liArr[i].bxSvg;

		draggable[i] = Draggable.create(listIn, {
			type:trans.x+', '+trans.y,
			bounds:liArr[i].liIn,
			throwProps:true,
			ease:Power2.easeInOut,
            onClick:function(){zoomFlag = false;},
            onDragEnd : function(){zoomFlag = false;}
		});
		this.setNavSect(i);
		if(!liArr[i].faci || liArr[i].faci.length <= 1){
			this.setUints(i, 0);
		}else{
			for (var j = 0; j < liArr[i].faci.length; j++) {
				this.setUints(i, j, {hide : 1});
				this.setSectClick(i, j);
			}
		}
		this.setFullSizeMap({i:i});
	};
	if(params.dth1 !== undefined) this.moveFloor(params.dth1-1, 0);
    if(liArr[params.dth1-1]){
        if(liArr[params.dth1-1].faci && params.dth2 !== undefined && liArr[params.dth1-1].faci[params.dth2-1]){
            _this.zoomSect.call(liArr[params.dth1-1].faci[params.dth2-1].el, null, params.dth1-1, params.dth2-1, 0);
        }
    }


	bx.on('click',function(){
		var $this = $(this);
		$this.hasClass('detail') || $this.addClass('detail');
	});


	this.zoomInOut = function(type){ //줌인, 줌아웃 함수
		if(zoomFlag) return;
		zoomFlag = true;
		var zoomRate = type + 1;
		var idx = this.getIndex(), t = liArr[idx].el, listIn = liArr[idx].bxSvg, svg = liArr[idx].svg;
		var obj = _this.getSize({liEle:t, listIn:listIn, svg : svg});
		var targetSize = {w : obj.svg.w*zoomRate, h : obj.svg.h*zoomRate};
		var rate = (obj.li.w/obj.li.h) < (obj.svg.w/obj.svg.h) ? 'x' : 'y';
        /*if(type < 0 && floors[idx].zoom) {
			liArr[idx].navLi[0].siblings('.on').removeClass('on').end().addClass('on');
			this.hideUnit(idx);
		}*/
        if((obj.svg.w <= obj.minSize.w*(zoomRate+1) && obj.svg.h <= obj.minSize.h*(zoomRate+1)) && type < 0 && floors[idx].zoom){
            liArr[idx].navLi[0].siblings('.on').removeClass('on').end().addClass('on');
            this.hideUnit(idx);
            location.href = '#/facilities/index.af?depth1=2';
        }
		if(type < 0 && zoomInBtn.hasClass('disable'))zoomInBtn.removeClass('disable');
		if(type > 0 && zoomOutBtn.hasClass('disable'))zoomOutBtn.removeClass('disable');
		if((obj.svg.w <= obj.minSize.w && obj.svg.h <= obj.minSize.h) && type < 0){
            zoomFlag = false;
			return;
		}
		if((targetSize.w <= obj.minSize.w && targetSize.h <= obj.minSize.h) && type < 0){ //더이상 작게 할 수 없음
            if(rate === 'x'){targetSize.w = obj.minSize.w; targetSize.h = obj.svg.h*targetSize.w/obj.svg.w;}
			if(rate === 'y'){targetSize.h = obj.minSize.h; targetSize.w = obj.svg.w*targetSize.h/obj.svg.h;}
			zoomOutBtn.addClass('disable');
		}
		// }else if((targetSize.w > obj.minSize.w*2 || targetSize.h > obj.li.minSize*2) && type > 0){ //더이상 크게 할 수 없음
		// 	zoomFlag = false;
		// 	zoomInBtn.addClass('disable');
		// 	return;
		// }
		targetSize.x = (listIn.position().left - obj.li.w/2) * targetSize.w/obj.svg.w + obj.li.w/2;
		targetSize.y = (listIn.position().top - obj.li.h/2) * targetSize.h/obj.svg.h + obj.li.h/2;
		if(targetSize.w >= obj.li.w) targetSize.x = Math.min(Math.max(targetSize.x, obj.li.w - targetSize.w),0);
		if(targetSize.h >= obj.li.h) targetSize.y = Math.min(Math.max(targetSize.y, obj.li.h - targetSize.h),0);

		TweenMax.to(svg,0.5,{width : targetSize.w, height : targetSize.h, ease : Power3.easeInOut});
		this.tweenMaxCross('to', listIn,0.5,{x : targetSize.x, y : targetSize.y,ease : Power3.easeInOut, onComplete : function(){
			draggable[idx][0].update();
			zoomFlag = false;
		}});

        if(POINT_CODE == 'POINT03'){
            TweenMax.to($('#facilities .list_floors .bx_pin button'),0.5,{top : targetSize.y/5, ease : Power3.easeInOut});
        }
	};

	zoomInBtn.on('click',function(){_this.zoomInOut(zoomRate)});
	zoomOutBtn.on('click',function(){_this.zoomInOut(-zoomRate)});
	var navFlag;
	nav.find('a').on('click',function(){
		if(navFlag) return;
		navFlag = true;
		_this.moveFloor($(this).data('idx'), 1);
		setTimeout(function(){navFlag = false;},1000)
	});
	bx.on('click','.bx_pin button',function(){
		var $this = $(this);
        _this.setPopSlider($this);
	})
    this.setPopSlider = function(params){
        params = params || {};
        ajaxShowPopCont({
            url : '/facilities/popup.af',
            onLoad : function(){
				var sample_text_img = '<img class="sample_img" src="/common/front/images/POINT05/facilities/sample_text_img.png" alt="샘플 이미지">';
				var swiper_nav = '';
                var str = '',
                    faci = floors[params.data('dth1')].faci[params.data('dth2')];
					units = faci.unit;
				console.log(units);
                for (var k = 0; k < units.length; k++) {
					var imgCode = "img_pop_"+faci.cate+"_"+(k+1);
					var new_str = '';
					if(POINT_CODE == 'POINT03') imgCode = units[k].img;
					if(units[k]['is_slide'] != undefined){
						for(var j = 0; j < units[k]['is_slide']+1; j++){
							var imgCode = "img_pop_"+faci.cate+"_"+(k+1);
							imgCode = imgCode+'_'+j;
							new_str += '<div class="swiper-slide">';
							new_str += '<img src="/common/front/images/'+POINT_CODE+'/facilities/'+imgCode+'.jpg"/>';
							new_str += '</div>';
						}
						str +='<div class="detail_item iscContent"><div class="inner"><div class="dth2_tit"><span class="tit1">'+faci.name+'</span><span class="tit2">'+faci.name2+'</span></div><div class="img swiper-container main_slider'+k+'">'+sample_text_img+'<div class="swiper-wrapper">'+new_str+'</div></div><div class="txt"><div class="dth3_tit">'+units[k].name+'</div><div class="dth3_txt">'+units[k].text+'</div></div></div></div>\n';
						swiper_nav += '<div class="main_slider_pagi'+k+' main_slider_pagi"><div class="swiper-pagination"></div><div class="swiper-button-next"></div><div class="swiper-button-prev"></div></div>';
					}else{
						if(units[k]['dubble_ico'] != 1){
							str +='<div class="detail_item iscContent"><div class="inner"><div class="dth2_tit"><span class="tit1">'+faci.name+'</span><span class="tit2">'+faci.name2+'</span></div><div class="img">'+sample_text_img+'<img src="/common/front/images/'+POINT_CODE+'/facilities/'+imgCode+'.jpg"/></div><div class="txt"><div class="dth3_tit">'+units[k].name+'</div><div class="dth3_txt">'+units[k].text+'</div></div></div></div>\n';	
						}
					}
                }

				if(POINT_CODE == 'POINT05'){
					$("#detail_list").before(swiper_nav);
					$("#detail_list").append(str);
				}else{
					$("#detail_list").html(str);
				}
				for (var k = 0; k < units.length; k++) {
					var swiper = new Swiper('.main_slider'+k, {
						slidesPerView: 1,
						spaceBetween: 30,
						pagination: {
						el: '.main_slider_pagi'+k+' .swiper-pagination',
						clickable: true,
						},
						navigation: {
						nextEl: '.main_slider_pagi'+k+' .swiper-button-next',
						prevEl: '.main_slider_pagi'+k+' .swiper-button-prev',
						},
					});
				}
				
                $("#detail_list").on('init', function(event, slick, direction){
                    setTimeout(function() {
                        $('#facPopSlider .count .curr').text(slick.currentSlide+1);
						$('#facPopSlider .count .total').text(slick.slideCount);
						$('.main_slider_pagi').css({visibility: 'hidden'});
						$('.main_slider_pagi').eq(slick.currentSlide).css({visibility: 'visible'});
                    }, 1);
                });

                var popSlider = $("#detail_list").slick({
                    arrows: true,
                    infinite: false,
                    swipe:false,
				});
				popSlider.slick('slickGoTo', params.data('dth3'), true);
				
				$('.main_slider_pagi').css({visibility: 'hidden'});
				$('.main_slider_pagi').eq(0).css({visibility: 'visible'});

                popSlider.on('beforeChange', function(event, slick, currentSlide, nextSlide){
					$('.main_slider_pagi').css({visibility: 'hidden'});
					$('.main_slider_pagi').eq(nextSlide).css({visibility: 'visible'});
					page_z++;
                    $('#facPopSlider .count .curr').text(nextSlide+1);
                    $('#facPopSlider .count .total').text();
                });
            }
        });
    };

	$(window).resize(function(){
		var ulT = ul.offset().top;
		var liT = _this.activeEle ? _this.activeEle.offset().top : 0;
        var twObj = new Object();
        twObj[trans.y] = ulT-liT;
		TweenMax.set(ul, twObj);
		_this.setFullSizeMap({i:_this.getIndex()});
	})
};