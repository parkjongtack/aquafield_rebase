/*
 * 20160729 김민주생성 공통 스크립트
 */


/* 
 * @input
 * 	name : 인풋의 name
 * 	msg : null 일경우 메시지
 * @기능 : null 체크, alert, focus
 * @return : 정상 -> true , 비정상 -> false
 */
function checkInputNull(name, msg){
	var $el = $("input[name='"+name+"']");
	if($el.val() == ""){
		alert(msg);
		$el.focus();
		return false;
	}else{
		return true;
	}
};

/* 
 * @input
 * 	date : 인풋의 name
 * 	name : null 일경우 메시지
 * @기능 : 이메일 검증 null 일경우 검사 안함
 * @return : 정상 -> true , 비정상 -> false
 */
function dateValidation(name){
	var $el = $("input[name='"+name+"']");
	var date = $el.val();
	if(date == "") return true;
	var dateArr = date.split(".");
	if(date.length != 10 || dateArr.length != 3 || dateArr[0].length != 4 || dateArr[1].length != 2 || dateArr[2].length != 2 ){
		alert("잘못된 형식의 입력입니다.\n ex) 2016.07.04");
		$el.val("");
		$el.focus();
		return false;
	}else{
		return true;
	}
};


/*
 * @input event
 * @기능 : 숫자만 입력(tab 키 사용가능, 엔터키도 사용가능)
 * @return : 숫자일경우 true, 숫자 아닐경우 false
 * 사용법
 * <input type="text" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' style='ime-mode:disabled;'>
 */
function onlyNumber(event){
    event = event || window.event;
    var keyID = (event.which) ? event.which : event.keyCode;
    if ( (keyID >= 48 && keyID <= 57) || (keyID >= 96 && keyID <= 105) || keyID == 8 || keyID == 9 || keyID == 46 || keyID == 37 || keyID == 39 || keyID == 13)
        return;
    else
        return false;
};
function removeChar(event) {
    event = event || window.event;
    var keyID = (event.which) ? event.which : event.keyCode;
    if ( keyID == 8 || keyID == 46 || keyID == 37 || keyID == 39 || keyID == 9 || keyID == 13  )
        return;
    else
        event.target.value = event.target.value.replace(/[^0-9]/g, "");
};


/*
 * @input
 * 	startName : 시작일 input name
 * 	endName : 종료일 input name
 * @기능 : 시작일 종료일 범위가 잘못되어있는지 체크
 * @return : 정상 -> true , 비정상 -> false
 */
function dateToDate(startName, endName ){
	var $start = $("input[name='"+startName+"']").val();
	var $end = $("input[name='"+endName+"']").val();
	if($start == "" || $end == "" ){
		return true;
	}
	$start = $start.split(".").join("");
	$end = $end.split(".").join("");
	if($start > $end){
		alert("기간을 확인해주시기 바랍니다.");
		$("input[name='"+startName+"']").focus();
		return false;
	}else{
		return true;
	}
}

