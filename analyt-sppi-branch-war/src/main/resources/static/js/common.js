var _ctx = $("meta[name='ctx']").attr("content");
// Prepend context path to all jQuery AJAX requests
/*$.ajaxPrefilter(function( options, originalOptions, jqXHR ) {
console.log(options);
    if (!options.crossDomain) {
        options.url = _ctx + options.url;
    }
});*/
function showAlert(message){
    $("#alertBoxMessage").html(message);
    $("#alertBox").fadeTo(1000,1).delay(2000).fadeTo(3000,0,function () {$(this).hide();});
}

$("#return_button").click(function (e) {
    window.location = _ctx;
});

function getNext(processId, questionId, getNextQuestionData) {
    $.ajax({
        type: "POST",
        url: _ctx+'process/' + processId + '/getnext/' + questionId,
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(getNextQuestionData),
        success: function (response) {
            console.log(response);
            if (response.code === "SUCCESS" || response.code === "FAIL") {
                window.location = _ctx+'process/' + processId +'/result/' + response.code.toLowerCase();
            } else {
                window.location = _ctx+'process/' + processId + '/showquestion/'+response.id;
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
        url: _ctx+'start_process',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(product),
        success: function (response) {
            window.location = _ctx+'process/' + response.processId + '/showquestion/' + response.current.id;
        },
        error: function (error) {
            alert("Не найден первый вопрос!")
        }
    });
}

$(".action-item").on("click",function() {
    $(".action-item.active").removeClass("active");
    $(this).addClass("active");
})


$(".index-tab").on("click",function(e) {
    window.location = location.origin+_ctx;
})

$(".complete-processes").on("click",function(e) {
    window.location = location.origin+_ctx+"my_processes?status=complete";
})

$(".paused-processes").on("click",function(e) {
    window.location = location.origin+_ctx+"my_processes?status=paused";
})