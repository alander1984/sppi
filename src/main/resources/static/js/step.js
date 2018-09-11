$(document).ready(function () {
    $.ajax({
        type: "GET",
        url: '../question/'+questionid,
        contentType: "application/json; charset=utf-8",
        success: function (response) {
            showAlert("Задача запущена");
            $("#question").append(response.content);
            $("#question_note").append(response.note);
        },
        error: function (error) {
            showAlert("Задача не запущена: " + error)
        }
    });
});

$(".answerBtn").on("click",function() {
    console.log($(this).data("tag"));
    $.ajax({
        type: "POST",
        url: '../getnext/'+questionid,
        contentType: "application/json; charset=utf-8",
        data: $(this).data("tag"),
        success: function (response) {
            console.log(response);
            if (response.code === "SUCCESS" || response.code === "FAIL") {
                window.location = "../showresult/" + response.code.toLowerCase();
            } else {
                window.location = "../showquestion/"+response.id;
            }
        },
        error: function (error) {
            showAlert("Не найдено продолжение опроса...")
        }
    });
});