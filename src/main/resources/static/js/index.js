$("#start_button").click(function (e) {
    e.preventDefault();
    var product = {
        productCode: $("#product_type option:selected").val()
    };
    $.ajax({
        type: "POST",
        url: "/start_process",
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(product),
        success: function (response) {
            window.location = "../showquestion/" + response.id;
        },
        error: function (error) {
            alert("Не найден первый вопрос!")
        }
    });
});
