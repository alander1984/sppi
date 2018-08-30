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

$(".answerBtn").on("click",function() {
    console.log($(this).data("tag"));
    $.ajax({
        type: "POST",
        url: '../getnext/'+questionid,
        contentType: "application/json; charset=utf-8",
        data: $(this).data("tag"),
        success: function (response) {
            window.location = "../showquestion/"+response.id;
            //$("#question").text(response.content);
        },
        error: function (error) {
            showAlert("Не найдено продолжение опроса...")
        }
    });
})