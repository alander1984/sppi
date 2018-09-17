$(function() {
    $.fn.modal.Constructor.prototype.enforceFocus = function() {};
    $("#questionForm").jsForm({prefix:null});
    $('#questionTable').bootstrapTable({
                                 pagination: true,
                                 uniqueId: 'id',
                                 buttonsAlign: 'right',
                                 url: _ctx+'showall',
                                 sidePagination: 'client',
                                 search: 'true',
                                 //dataField: 'content',
                                 pageList: [10, 25, 50, 100],
                                 columns: [
                                        {
                                            field: 'id',
                                            title: 'ID'
                                        },
                                        {
                                            field: 'code',
                                            title: 'Код'
                                        },
                                        {
                                            field: 'answers',
                                            title: 'Ответы'
                                        },
                                        {
                                            field: 'content',
                                            title: 'Содержание'
                                        },
                                        {
                                            field: 'note',
                                            title: 'Примечание'
                                        }
                                 ],
                                 onClickRow: function (row, $element) {
                                            row_index=$element.data("index");
                                            record=row.id;
                                            showQuestion(row);
                                        }
                                });
});







function addQuestion() {
    $("#questionForm").jsForm("reset");
    $("#save").hide();
    $("#noActive").hide();
    $("#create").show();
    $("#questionModal").modal("show");
}


function saveQuestion() {

    var _data = $('#questionForm').serializeJSON();

    $.ajax( {
          type: "PUT",
          url: _ctx+'updateQuestion',
          data: JSON.stringify(_data),
          contentType: "application/json; charset=utf-8",
          success: function( response ) {
            $("#questionModal").modal("hide");
            $('#questionTable').bootstrapTable('updateRow', {index: row_index, row: response});

          },
          statusCode : {
            409: function() {
                alert('A');
            }
          },
          error: function(response){
                if (response.getResponseHeader('X-epCore-errorMessage')) {
                    alert(response.getResponseHeader('X-epCore-errorMessage'));
                }
          }
        } );
}

function showQuestion(question) {
    $("#save").show();
    $("#noActive").show();
    $("#create").hide();
    $("#questionForm").jsForm("fill",question);
    $("#questionModal").modal("show");
}

function createQuestion() {

    var _data = $('#questionForm').serializeJSON();

    $.ajax( {
        type: "POST",
        url: _ctx+'addQuestion',
        data: JSON.stringify(_data),
        contentType: "application/json; charset=utf-8",
        success: function( response ) {
          $("#questionModal").modal("hide");
          $('#questionTable').bootstrapTable('refresh');
          alert('Вопрос успешно добавлен')
        },
        statusCode : {
          409: function() {
              alert('Alert');
          }
        },
        error: function(response){
            if (response.getResponseHeader('X-epCore-errorMessage')) {
                alert(response.getResponseHeader('X-epCore-errorMessage'));
            }
        }
    });
}

function deleteQuestion() {
        if(confirm("Вы действительно хотите удалить вопрос?")) {
             var data = $('#questionForm').serializeJSON();
                    $.ajax( {
                          type: "DELETE",
                          url: _ctx+'deleteQuestion',
                          data: JSON.stringify(data),
                          contentType: "application/json; charset=utf-8",
                          success: function( response ) {
                            $("#questionModal").modal("hide");
                            $('#questionTable').bootstrapTable('removeByUniqueId', record);
                          }
                        } );
        }
}