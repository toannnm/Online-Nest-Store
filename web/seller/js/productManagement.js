function pageLoad() {
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    const type = urlParams.get('type');

    if (type !== null) {
        removeActiveAndShow();
        outOfStock();
    }

    cleanUri();

}

function cleanUri() {
    var uri = window.location.toString();
    if (uri.indexOf("?") > 0) {
        var clean = uri.substring(0, uri.indexOf("?"));
        window.history.replaceState({}, document.title, clean);
    }
}

function allProduct() {
    removeActiveAndShow();

    var productManage = document.getElementById('your-product-manage');
    productManage.classList.add('active');

    var allProductTab = document.getElementById('all-product-tab');
    allProductTab.classList.add('active');

    var allProductTabPane = document.getElementById('all-product-tab-pane');
    allProductTabPane.classList.add('active', 'show');

    document.getElementById('categoryOutOfStockOption').selectedIndex = 0;
    filterCateFunc('categoryOption', 'out-of-stock-count');
    
    document.getElementById('txtOutOfStockSearch').value = '';
    searchFunc('txtOutOfStockSearch', 'out-of-stock-count', 'product-detail-2');
}

function outOfStock() {
    removeActiveAndShow();

    var outOfStockOrder = document.getElementById('out-of-stock-order');
    outOfStockOrder.classList.add('active');

    var outOfStockTab = document.getElementById('out-of-stock-tab');
    outOfStockTab.classList.add('active');

    var outOfStockTabPane = document.getElementById('out-of-stock-tab-pane');
    outOfStockTabPane.classList.add('active', 'show');

    document.getElementById('categoryOption').selectedIndex = 0;
    filterCateFunc('categoryOption', 'product-count');

    document.getElementById('txtProductSearch').value = '';
    searchFunc('txtProductSearch', 'product-count', 'product-detail-1');
}

function removeActiveAndShow() {
    var products = document.getElementsByClassName('all-product');
    for (var i = 0; i < products.length; i++) {
        if (hasClass(products[i], 'active')) {
            products[i].classList.remove('active');
        }
    }

    var allProductBtn = document.getElementsByClassName('all-product-btn');
    for (var i = 0; i < allProductBtn.length; i++) {
        if (hasClass(allProductBtn[i], 'active')) {
            allProductBtn[i].classList.remove('active');
        }
    }

    var tabPane = document.getElementsByClassName('tab-pane');
    for (var i = 0; i < tabPane.length; i++) {
        if (hasClass(tabPane[i], 'active')) {
            tabPane[i].classList.remove('active');
        }
        if (hasClass(tabPane[i], 'show')) {
            tabPane[i].classList.remove('show');
        }
    }
}

function hasClass(element, className) {
    return (' ' + element.className + ' ').indexOf(' ' + className + ' ') > -1;
}

function stringToSlug(str) {
    // remove accents
    var from = "àáãảạăằắẳẵặâầấẩẫậèéẻẽẹêềếểễệđùúủũụưừứửữựòóỏõọôồốổỗộơờớởỡợìíỉĩịäëïîöüûñçýỳỹỵỷ",
            to = "aaaaaaaaaaaaaaaaaeeeeeeeeeeeduuuuuuuuuuuoooooooooooooooooiiiiiaeiiouuncyyyyy";
    for (var i = 0, l = from.length; i < l; i++) {
        str = str.toString().replace(RegExp(from[i], "gi"), to[i]);
    }

    str = str.toLowerCase().trim().replace(/[^a-z0-9\-]/g, '-').replace(/-+/g, '-');

    return str;
}