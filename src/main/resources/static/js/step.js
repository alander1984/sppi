$(document).ready(function () {
    $.ajax({
        type: "GET",
        url: _ctx+'question/' + questionid,
        contentType: "application/json; charset=utf-8",
        success: function (response) {
            //showAlert("Задача запущена");
            $("#question").append(response.content);
            $("#question_note").append(response.note);
        },
        error: function (error) {
            //showAlert("Задача не запущена: " + error)
        }
    });
});

$(".answerBtn").on("click",function() {
    var answer = $(this).data("tag");
    console.log(answer);
    getNext(processId, questionid, answer);
});

$(".navPseudo").click(function (e) {
    if (isFirstStep) {
        $.ajax({
            type: "POST",
            url: _ctx+"/process/" + processId + "/delete_unused_process",
            contentType: 'application/json; charset=utf-8',
            async: false
        });
    }
});