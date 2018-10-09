var testResultCode;

$(document).ready(function () {
    $("#attributeContent").empty();
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
    $.ajax({
        type: "GET",
        url: _ctx+'process/' + processId+'/attributes',
        contentType: "application/json; charset=utf-8",
        success: function (attributes) {

             $.each(attributes,function(index,attribute){
                            $("#attributeContent").append(
                                $('<div class="form-group row field">'+
                                    '<label for="attr-index+'+index+'" class="col-6 col-form-label">'+attribute.name+'</label>'+
                                    '<div class="col-6">'+
                                      '<label class="form-control attribute-field" data-type="'+attribute.type+'" type="text" data-field="'+attribute.name+'" id="attr-index'+index+'">'+attribute.value+'</label>'+
                                    '</div>'+
                                  '</div>')
                            );
                        })
           // showAlert("Задача запущена");
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
});


$(function() {
	$("#show-note-toggle-input").change(function() {
		if($(this).prop('checked')) {
			$("#question_note").show();
		} else {
			$("#question_note").hide();
		}
	})
});

function resultDialog(resultCode) {
    testResultCode = resultCode;
    $("#showResultDialog").modal({backdrop: "static", keyboard: true, show: true});
}

$("#resultDialogCancelBtn").on("click", function (e) {
    alert("Результаты теста аннулированы и не сохранены!");
    $.ajax({
        type: "POST",
        url: location.origin + _ctx + 'process/' + processId + '/deleteUnusedProcess',
        contentType: 'application/json; charset=utf-8',
        async: false,
        success: function () {
            window.location = location.origin + _ctx;
        }
    });
});

$("#resultDialogAcceptBtn").on("click",function(e) {
    window.location = _ctx+'process/' + processId +'/result/' + testResultCode.toLowerCase();
});

$(function () {
    $('[data-toggle="tooltip"]').tooltip()
});

