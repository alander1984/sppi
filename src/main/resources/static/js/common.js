var _ctx = $("meta[name='ctx']").attr("content");

function showAlert(message){
    $("#alertBoxMessage").html(message);
    $("#alertBox").fadeTo(1000,1).delay(2000).fadeTo(3000,0,function () {$(this).hide();});
}

$("#return_button").click(function (e) {
    window.location = "/";
});

function getNext(processId, questionId, answer) {
    $.ajax({
        type: "POST",
        url: "/process/" + processId + "/getnext/" + questionId,
        contentType: "application/json; charset=utf-8",
        data: answer,
        success: function (response) {
            console.log(response);
            if (response.code === "SUCCESS" || response.code === "FAIL") {
                window.location = "/process/result/" + response.code.toLowerCase();
            } else {
                window.location = "/process/" + processId + "/showquestion/"+response.id;
            }
        },
        error: function (error) {
            showAlert("Не найдено продолжение опроса...")
        }
    });
}

function startProcess(productCode, productName) {
    var product = {
        productCode: productCode,
        productName: productName
    };
    $.ajax({
        type: "POST",
        url: "/start_process",
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(product),
        success: function (response) {
            window.location = "/process/" + response.processId + "/showquestion/" + response.current.id;
        },
        error: function (error) {
            alert("Не найден первый вопрос!")
        }
    });
}