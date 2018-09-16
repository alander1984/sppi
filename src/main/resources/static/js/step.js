$(document).ready(function () {
    $.ajax({
        type: "GET",
        url: "/question/" + questionid,
        contentType: "application/json; charset=utf-8",
        success: function (response) {
            showAlert("Задача запущена");
            $("#question").append(response.content);
        },
        error: function (error) {
            showAlert("Задача не запущена: " + error)
        }
    });
});

$(".answerBtn").on("click",function() {
    var answer = $(this).data("tag");
    console.log(answer);
    getNext(processId, questionid, answer);
});

$("#suspend_test_button").on("click", function () {
    $.ajax({
        type: "POST",
        url: "/process/" + processId + "/suspendTest",
        success: function (response) {
            window.location = "/process/result/" + response;
        },
        error: function (error) {
            showAlert("Невозможно приостановить выполнение теста!")
        }
    });
});