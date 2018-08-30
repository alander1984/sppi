$("#start_button").click(function(e) {
    e.preventDefault();
    window.location='../start_process';
/*    $.ajax({
        type: "GET",
        url: "/start_process",
        success: function(result) {
            alert('ok');
        },
        error: function(result) {
            alert('error');
        }
    });*/
});
