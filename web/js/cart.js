
function incrementValue(e) {
    var max = document.getElementById("number-input").max;
    var fieldName = $(e.target).data('field');
    var parent = $(e.target).closest('div');
    var currentVal = parseInt(parent.find('input[name=' + fieldName + ']').val(), 10);
    if (!isNaN(currentVal) && currentVal < max) {
        parent.find('input[name=' + fieldName + ']').val(currentVal + 1);
        checkState(e.currentTarget);
    } else {
        document.getElementById("triggerFail").click();
    }
}

function decrementValue(e) {
    var fieldName = $(e.target).data('field');
    var parent = $(e.target).closest('div');
    var currentVal = parseInt(parent.find('input[name=' + fieldName + ']').val(), 10);
    if (!isNaN(currentVal) && currentVal > 1) {
        parent.find('input[name=' + fieldName + ']').val(currentVal - 1);
    } else {
        parent.find('input[name=' + fieldName + ']').val(1);
    }
    checkState(e.currentTarget);
}

$('.input-group').on('click', '.button-plus', function (e) {
    incrementValue(e);

});

$('.input-group').on('click', '.button-minus', function (e) {
    decrementValue(e);
});

function maxCheck(object) {
    if (object.value > object.max)
        object.value = object.max;
}

function minCheck(object) {
    if (object.value == '' || object.value < object.min)
        object.value = object.min;
}
//function validate(){
//    let password = document.getElementById("password").value;
//    let confirm = document.getElementById("confirm").value;
//    if (password != confirm){
//        let form = document.getElementById("reg-form");
//        form.prevetDefault();
//        alert("ko trung");
//    } else {
//        form.removeEventListener()
//    }
//}
function pickVoucher(param) {
    document.getElementById('buy-button').innerHTML = param.value;
    document.getElementById('voucher-use').value = param.firstElementChild.value;
    document.getElementById('saleMargin').value = param.lastElementChild.value;
    calculateTotal();
}
function cancelVoucher() {
    document.getElementById('voucher-use').value = "";
    document.getElementById('voucher-button').firstElementChild.innerHTML = 'Chọn voucher';
    document.getElementById('saleMargin').value = 0;
    calculateTotal();
}
function checkState(e) {
    var form = e.parentElement;
    var productID = $(form).find('#productID').val();
    var newQuantity = $(form).find('#number-input').val();
    console.log(productID);
    console.log(newQuantity);
    addProduct(productID, newQuantity);
}
addProduct = (productID, newQuantity) => {
    $.ajax({
        url: "addToCart",
        method: "GET",
        cache: "false",
        data: {
            productID: productID,
            newQuantity: newQuantity
        },
        success: function (state) {
            calculateTotal();
            if (state == 'fail') {
                document.getElementById("triggerFail").click();
            }
        }
    })
}
$('#triggerFail').click(function () {
    setTimeout(function () {
        $('#fail').modal('hide');
    }, 1500);
});
function calculateTotal() {
    var cartTotal = 0;
    var products = document.getElementsByName('productID');
    if (products.length > 0) {
        for (let i = 0; i < products.length; i++) {
            var parentForm = products[i].parentElement;
//        console.log(parentForm);
            var price = $(parentForm).find('input[name=price]').val();
            var quantity = $(parentForm).find('#number-input').val();
            var thisTotal = price * quantity;
            $(parentForm).find('#thisProductTotal').html(new Intl.NumberFormat('de-DE', {style: 'currency', currency: 'VND'}).format(thisTotal));
            cartTotal += thisTotal;
        }
        var voucherId = document.getElementById('voucher-use').value;
        if (voucherId != "") {
            var saleMargin = document.getElementById('saleMargin').value;
            cartTotal -= saleMargin;
        }
        document.getElementById('total-display').innerHTML = new Intl.NumberFormat('de-DE', {style: 'currency', currency: 'VND'}).format(cartTotal);
        console.log(cartTotal);
    }
}
$(document).ready(function () {
    calculateTotal();
    var reasons = document.getElementsByName('cancelReason');
    for (var i = 0; i < reasons.length; i++) {
        getReason(reasons[i]);
    }
}
);
function getReason(reason) {
    var value = $('label[for=' + reason.id + ']').text();
    $(reason).val(value);
}
function loadCancelForm(button) {
    var billID = $(button).find('input[name=billID').val();
    $('form[action=cancelBill]').find('input[name=billID').val(billID);
}
