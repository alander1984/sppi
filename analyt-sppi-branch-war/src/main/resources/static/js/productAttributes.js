/*
$("#acceptAttributes").click(function (e) {
    var productCode = $(this).data("product");
    var productName = $(this).data("product-name");
    $.ajax({
        type: "POST",
        url: _ctx+'question_dictionary',
        success: function () {
            window.location = _ctx+'question_dictionary'
        },
        error: function (error) {
            alert("Ошибка!")
        }
    });
});
*/

$(document).ready(function() {
    $("#attributesForm").submit(function(event) {
		// Prevent the form from submitting via the browser.
		event.preventDefault();
		postAttributes();
	});

    function postAttributes() {
        var form = $("#attributesForm");//$(this);
        // var url = form.attr('action');
        $.ajax({
			type : "POST",
			contentType : "application/json",
			url : _ctx+'saveAttributes',//ctx+'productAttributes/',
			data : form.serialize(),//JSON.stringify(form),
			dataType : 'json',
			success : function(result) {

			},
			error : function(e) {
				alert("Error!");
				console.log("ERROR: ", e);
			}
		});
    }

});