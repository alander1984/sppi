$(document).ready(function () {
    $.ajax({
        type: "GET",
        url: _ctx+'question/' + questionid,
        contentType: "application/json; charset=utf-8",
        success: function (response) {
           // showAlert("Задача запущена");
            $("#question").append(response.content);
            if(response.note.trim() == '') {
            	$("#toggle-container").hide();
            }
            $("#question_note").append(response.note);
            $("#question_note").hide();
        },
        error: function (error) {
            //showAlert("Задача не запущена: " + error)
        }
    });
});

$(".answerBtn").on("click",function() {
    var answer = $(this).data("tag");
    var fullNameVerifier = $('#verifiers_list option:selected').val();
    if(fullNameVerifier === 'Не выбрано') {
    	fullNameVerifier = null;
    }
    getNextQuestionData = {answer: answer, fullNameVerifier: fullNameVerifier}
    console.log(answer);
    getNext(processId, questionid, getNextQuestionData);
});

$(".navPseudo").click(function (e) {
    if (isFirstStep) {
        $.ajax({
            type: "POST",
            url: location.origin+_ctx+'process/' + processId + '/deleteUnusedProcess',
            contentType: 'application/json; charset=utf-8',
            async: false
        });
    }
});

$(".close-test-btn").on("click",function(e) {
    window.location = location.origin+_ctx+"my_processes?status=paused";
})


$(function() {
	$("#show-note-toggle-input").change(function() {
		if($(this).prop('checked')) {
			$("#question_note").show();
		} else {
			$("#question_note").hide();
		}
	})
})



