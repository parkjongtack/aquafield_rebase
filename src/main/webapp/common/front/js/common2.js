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

