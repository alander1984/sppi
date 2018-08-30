$(document).ready(function () {
    $.ajax({
        type: "GET",
        url: '..' + _ctx + '/task/' + id +'/run',
        contentType: "application/json; charset=utf-8",
        success: function (response) {
            showAlert("Задача запущена")
        },
        error: function (error) {
            showAlert("Задача не запущена: " + error)
        }
    });
})