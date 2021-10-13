$('#birth').keyup(function (event) {
    event = event || window.event;
    let val = this.value.trim();
    this.value = autoBirthHypen(val);
});
// 생년월일 입력시 자동 하이픈(-) 처리
function autoBirthHypen(str) {
    str = str.replace(/[^0-9]/g, '');
    if (str.length < 5) {
        return str;
    } else if (str.length < 7) {
        return str.substr(0,4)+'-'+str.substr(4);
    } else if (str.length < 9) {
        return str.substr(0, 4)+'-'+str.substr(4, 2)+'-'+str.substr(6);
    } else {
        return str.substr(0, 4)+'-'+str.substr(4, 2)+'-'+str.substr(6);
    }
    return str;

}

$('#phone').keyup(function (event) {
    event = event || window.event;
    let val = this.value.trim();
    this.value = autoPhoneHypen(val);
});
// 핸드폰 번호 입력시 자동 하이픈(-) 처리
function autoPhoneHypen(str) {
    str = str.replace(/[^0-9]/g, '');
    if (str.substring(0, 2) == '02') {
        // 서울 전화번호일 경우 10자리까지만 나타나고 그 이상의 자리수는 자동삭제
        if (str.length < 3) {
            return str;
        } else if (str.length < 6) {
            return str.substr(0, 2)+'-'+str.substr(2);
        } else if (str.length < 10) {
            return str.substr(0, 2)+'-'+str.substr(2, 3)+'-'+str.substr(5);
        } else {
            return str.substr(0, 2)+'-'+str.substr(2, 4)+'-'+str.substr(6, 4);
        }
    } else {
        // 핸드폰 및 다른 지역 전화번호 일 경우
        if (str.length < 4) {
            return str;
        } else if (str.length < 7) {
            return str.substr(0, 3)+"-"+str.substr(3);
        } else if (str.length < 11) {
            return str.substr(0, 3)+"-"+str.substr(3, 3)+'-'+str.substr(6);
        } else {
            return str.substr(0, 3)+"-"+str.substr(3, 4)+'-'+str.substr(7);
        }
    }
    return str;
}