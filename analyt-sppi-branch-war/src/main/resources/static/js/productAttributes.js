$("#acceptAttributes").click(function (e) {
    var productCode = $(this).data("product");
    var productName = $(this).data("product-name");
    $.ajax({
        type: "POST",
        url: _ctx+'question_dictionary',
        success: function () {
            window.location = _ctx+'question_dictionary'
        },
        error: function (error) {
            alert("Ошибка!")
        }
    });
});