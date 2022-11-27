/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function checkSimilar() {
    var newPass = $('#newPass').val();
    var confirm = $('#confirm');
    var submitButton = $(confirm).closest('form').find('input[name=submitButton]');
    if (newPass != confirm.val()) {
        $(submitButton).prop('disabled', true);
    } else {
        $(submitButton).prop('disabled', false);
    }
}

updateName = () => {
    var newName = $('input[name=newCustomerName]').val();
    $.ajax({
        url: "changeNameAction",
        method: "POST",
        cache: "false",
        data: {
            newCustomerName : newName
        },
        success: function (state) {
            console.log(state);
            if (state == 'success') {
                document.getElementById("triggerSuccess").click();
                $('input[name=newCustomerName]').val("");
                $('#name-display').html(newName);
                
            }
            if (state == 'fail') {
                document.getElementById("triggerFail").click();
            }
        }
    })
}
updateAddress = () => {
    var newAddress = $('input[name=newAddress]').val();
    $.ajax({
        url: "changeAddressAction",
        method: "POST",
        cache: "false",
        data: {
            newAddress : newAddress
        },
        success: function (state) {
            console.log(state);
            if (state == 'success') {
                document.getElementById("triggerSuccess").click();
                $('input[name=newAddress]').val("");
                $('#current-address').html(newAddress);
                
            }
            if (state == 'fail') {
                document.getElementById("triggerFail").click();
            }
        }
    })
}
$('#triggerSuccess').click(function () {
    setTimeout(function () {
        $('#success').modal('hide');
    }, 1500);
});
$('#triggerFail').click(function () {
    setTimeout(function () {
        $('#fail').modal('hide');
    }, 1500);
});
