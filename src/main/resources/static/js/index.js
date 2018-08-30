alert("FFFFFFFFFFFFFFFFFFFFFf");
$("#start_button").click(function(e) {
    e.preventDefault();
    $.ajax({
        type: "GET",
        url: "/start_process",
        /*data: {
            id: $(this).val(), // < note use of 'this' here
            access_token: $("#access_token").val()
        },*/
        success: function(result) {
            alert('ok');
        },
        error: function(result) {
            alert('error');
        }
    });
});
