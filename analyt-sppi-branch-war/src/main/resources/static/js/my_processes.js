function resumeTest(processId, questionId, answer) {
    getNext(processId, questionId, answer);
}

function passTestAgain(productCode, productName) {
    startProcess(productCode, productName)
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

