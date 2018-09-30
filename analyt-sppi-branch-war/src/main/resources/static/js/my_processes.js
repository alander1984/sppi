function resumeTest(processId) {
    $.ajax({
         type: "GET",
            url: _ctx+'process/' + processId+'/getpaused',
            contentType: "application/json; charset=utf-8",
            success: function (response) {
            console.log(response);
                window.location = _ctx+'process/' + processId + '/showquestion/'+response;
            },
            error: function (error) {
                //showAlert("Задача не запущена: " + error)
            }
        });
}

$(document).ready(function () {
    $(".action-item.active").removeClass("active");
    if (status==="complete") {
        $(".action-item.complete-processes").addClass("active");
    }
    if (status==="paused") {
        $(".action-item.paused-processes").addClass("active");
    }
});

