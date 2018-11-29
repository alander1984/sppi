$(".btn-signin").on("click",function() {
    var registrationData = {
        username:$("#inputUsername").val(),
        userFIO:$("#inputUserFIO").val(),
        userPersonnelNum:$("#inputUserPersonnelNum").val(),
        password:$("#inputPassword").val()
    };
    $.ajax({
        type: "POST",
        url: _ctx+'commit-self-registration',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(registrationData),
        success: function (response) {
            alert("Пользователь успешно зарегистрирован");
            window.location = '..'+_ctx+"login";
        },
        error: function (error) {
            if (error.status === 300) {
                alert("Пользователь с таким именем уже существует!");
            } else {
                alert("Не удалось зарегистрировать пользователя!");
            }
        }
    });
});