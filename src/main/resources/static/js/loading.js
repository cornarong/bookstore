/* 로딩화면 구현 */
function showLoading(){
    //화면의 높이와 너비를 구합니다.
    let maskHeight = $(document).height();
    let maskWidth  = window.document.body.clientWidth;

    //화면에 출력할 마스크를 설정해줍니다.
    let mask ="<div id='mask' style='position:absolute; z-index:1000; background-color:#000000; left:0; top:0;'></div>";

    //화면에 레이어 추가
    $('body')
        .append(mask)

    //마스크의 높이와 너비를 화면 것으로 만들어 전체 화면을 채웁니다.
    $('#mask').css({
        'width' : maskWidth
        ,'height': maskHeight
        ,'opacity' :'0.3'
    });

    $("#loadingStatus").show();
}
function hideLoading(){
    $("#mask").remove();
    $("#loadingStatus").hide();
}