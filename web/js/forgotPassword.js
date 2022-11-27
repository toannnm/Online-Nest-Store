/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
getPassword = (button) => {
    var phone = $('input[name=phone]').val();
    if (phone != "") {
        $.ajax({
            url: "sendPassword",
            method: "GET",
            cache: false,
            data: {
                phone: phone
            },
            success: function (result) {
                if (result == 'sent') {
                    $('#notification').html('Mật khẩu đã được gửi tới số điện thoại của quý khách. <a href="login"> Đăng nhập ngay</a>');
                    lockButton(button);
                }
                if (result == 'notfound') {
                    $('#notification').html('Không tìm thấy số điện thoại. <a href="register">Đăng ký ngay</a>');
                }
            }
        });
    }
}
function lockButton(button)
{
    var clock = "5:00";
    $(button).prop('disabled', true);
    $(button).html(clock);
    var interval = setInterval(function () {
        console.log(clock);
        var timer = clock.split(':');
        //by parsing integer, I avoid all extra string processing
        var minutes = parseInt(timer[0], 10);
        var seconds = parseInt(timer[1], 10);
        --seconds;
        minutes = (seconds < 0) ? --minutes : minutes;
        if (minutes < 0) {
            clearInterval(interval);
            $(button).prop('disabled', false);
        }
        seconds = (seconds < 0) ? 59 : seconds;
        seconds = (seconds < 10) ? '0' + seconds : seconds;
        //minutes = (minutes < 10) ?  minutes : minutes;
        $(button).html(minutes + ':' + seconds);
        clock = minutes + ':' + seconds;
    }, 1000);
}
function checkBlank(field){
    var data = $(field).val();
    if (data.length == 0){
        $('button[name=sendPass]').prop('disabled', true);
    } else {
        $('button[name=sendPass]').prop('disabled', false);
    }
}
