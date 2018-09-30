$(".start-btn").click(function (e) {
    $(this).data("product");
    e.preventDefault();
    var productTypeId = $(this).data("product");
    fillAttributes(productTypeId);
    //startProcess(productTypeId);
});

$("#my_processes_button").click(function (e) {
    window.location = '..'+_ctx+"my_processes";
});
$("#question_dictionary_button").click(function (e) {
    e.preventDefault();
    $.ajax({
        type: "GET",
        url: _ctx+'question_dictionary',
        success: function () {
             window.location = _ctx+'question_dictionary'
        },
        error: function (error) {
            alert("Ошибка!")
        }
    });
});


function fillAttributes(productId) {

    fillModalWithAttributes(productId);
    $("#showProcessParams").modal("show");
}

function startProcessWithParams() {
}

function fillModalWithAttributes(productId) {
  $("#modalBodyForm").empty();
    $.ajax({
        type: "GET",
        url: _ctx+'productType/'+productId+"/attributes",
        success: function (attributes) {
            $.each(attributes,function(index,attribute){
                $("#modalBodyForm").append(
                    $('<div class="form-group row field">'+
                        '<label for="attr-index+'+index+'" class="col-6 col-form-label">'+attribute.name+'</label>'+
                        '<div class="col-6">'+
                          '<input class=" form-control attribute-field" data-field="'+attribute.name+'" type="text" data-field="'+attribute.name+'" id="attr-index'+index+'"/>'+
                        '</div>'+
                      '</div>')
                );
                console.log(attribute.name);
            })
        },
        error: function (error) {
            alert("Ошибка!")
        }
    });

}
