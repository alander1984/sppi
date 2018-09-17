$("#start_button").click(function (e) {
    e.preventDefault();
    var productCode = $("#product_type option:selected").val();
    var productName = $("#product_type option:selected").text();
    startProcess(productCode, productName);
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
