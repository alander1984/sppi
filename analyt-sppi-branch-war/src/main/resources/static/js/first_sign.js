$(".btn-signin").on("click",function() {
	changePassword = {
			username:$("#inputUsername").val(),
			password:$("#inputPassword").val(),
			newpassword:$("#inputNewPassword1").val()
	}
	$.ajax({
        type: "POST",
        url: _ctx+'setup-new-password',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(changePassword),
        success: function (response) {
        	alert("Пароль успешно установлен");
        	 window.location = '..'+_ctx+"login";
        },
        error: function (error) {
            alert("Не удалось установить пароль")
        }
    });
})