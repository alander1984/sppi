$("#start_button").click(function (e) {
    e.preventDefault();
    var quizType = {
        code: $("#quiz-type option:selected").val()
    };
    $.ajax({
        type: "POST",
        url: "/start_process",
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(quizType),
        success: function (response) {
            window.location = "../showquestion/" + response.id;
        },
        error: function (error) {
            alert("Не найден первый вопрос!")
        }
    });
});
