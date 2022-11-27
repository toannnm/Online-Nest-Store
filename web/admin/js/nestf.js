function incrementValue(e) {
    e.preventDefault();
    var max = document.getElementById("number-input").max;
    var fieldName = $(e.target).data('field');
    var parent = $(e.target).closest('div');
    var currentVal = parseInt(parent.find('input[name=' + fieldName + ']').val(), 10);

    if (!isNaN(currentVal) && currentVal < max) {
        parent.find('input[name=' + fieldName + ']').val(currentVal + 1);
    }
}

function decrementValue(e) {
    e.preventDefault();
    var fieldName = $(e.target).data('field');
    var parent = $(e.target).closest('div');
    var currentVal = parseInt(parent.find('input[name=' + fieldName + ']').val(), 10);

    if (!isNaN(currentVal) && currentVal > 1) {
        parent.find('input[name=' + fieldName + ']').val(currentVal - 1);
    } else {
        parent.find('input[name=' + fieldName + ']').val(1);
    }
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
