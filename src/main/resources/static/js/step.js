$(document).ready(function () {
    $.ajax({
        type: "GET",
        url: '../question/'+questionid,
        contentType: "application/json; charset=utf-8",
        success: function (response) {
            showAlert("Задача запущена");
            $("#question").text(response.content);
        },
        error: function (error) {
            showAlert("Задача не запущена: " + error)
        }
    });
})