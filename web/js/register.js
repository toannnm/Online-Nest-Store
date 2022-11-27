/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function checkPassword() {
    var check = true;
    var password = $('#password').val();
    if (password.length < $('#password')[0].minLength) {
        $('#password-tooltip').css('display', 'inline-block');
        check = false;
    } else
        $('#password-tooltip').css('display', 'none');
    var confirm = $('#confirm');
    if (password != confirm.val()) {
        $('#similar-tooltip').css('display', 'inline-block');
        check = false;
    } else 
        $('#similar-tooltip').css('display', 'none');
    return check;
}

function checkPhoneLength(e) {
    var text = $(e).val();
    if (text.length >= $(e)[0].maxLength) {
        $(e).val(text.slice(0, $(e)[0].maxLength));
        $('#error-tooltip').css('display', 'none');
        return true;
    }
    $('#error-tooltip').attr('data-bs-original-title', 'Số điện thoại phải dài 10 chữ số');
    $('#error-tooltip').css('display', 'inline-block');
    return false;
}

function checkName(e) {
    var name = e.value;
    if (name.length < e.minLength) {
        $('#name-tooltip').attr('data-bs-original-title', 'Độ dài họ và tên tối thiểu ' + e.minLength +' ký tự');
        $('#name-tooltip').css('display', 'inline-block');
        return false;
    }
    var regex = /^[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂẾưăạảấầẩẫậắằẳẵặẹẻẽềềểếỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\s]+$/;
    if (regex.test(name)) {       
        $('#name-tooltip').css('display', 'none');
        return true;
    }
    $('#name-tooltip').attr('data-bs-original-title', 'Định dạng tên không phù hợp');
    $('#name-tooltip').css('display', 'inline-block');
    return false;
}
function checkAddress(e){
    var address = $(e).val();
    if (address.length < $(e)[0].minLength) {
        $('#address-tooltip').attr('data-bs-original-title', 'Độ dài địa chỉ tối thiểu '+ $(e)[0].minLength+ ' ký tự');
        $('#address-tooltip').css('display', 'inline-block');
        return false;
    }
    var regex = /^[a-zA-Z0-9ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂẾưăạảấầẩẫậắằẳẵặẹẻẽềềểếỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\s]+$/;
    if (regex.test(address)) {      
        $('#address-tooltip').css('display', 'none');
        return true;
    }
    $('#address-tooltip').attr('data-bs-original-title', 'Định dạng địa chỉ không thích hợp');
    $('#address-tooltip').css('display', 'inline-block');
    return false;
}
function validateForm() {
    var disabled = false;
    var name = document.getElementById('name');
    if (!checkName(name))
        disabled = true;
    var phone = $('input[name=phone]');
    if (!checkPhoneLength(phone))
        disabled = true;
    if (!checkPassword())
        disabled = true;
    var address = $('input[name=address]');
    if (!checkAddress(address))
    var submitButton = $('input[name=submitButton]');
    $(submitButton).prop('disabled', disabled);
}