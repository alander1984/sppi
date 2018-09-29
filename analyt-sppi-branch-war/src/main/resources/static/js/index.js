$(".start-btn").click(function (e) {
    $(this).data("product");
    e.preventDefault();
    var productCode = $(this).data("product");
    var productName = $(this).data("product-name");
    fillAttributes(productCode);
    // startProcess(productCode, productName);
});

$("#my_processes_button").click(function (e) {
    window.location = '..'+_ctx+"my_processes";
});
$("#question_dictionary_button").click(function (e) {
    e.preventDefault();
    $.ajax({
        type: "GET",
        url: _ctx+'question_dictionary',
        success: function () {
             window.location = _ctx+'question_dictionary'
        },
        error: function (error) {
            alert("Ошибка!")
        }
    });
});


function fillAttributes(productCode) {
    window.location = _ctx+'productAttributes/' + productCode;
}
