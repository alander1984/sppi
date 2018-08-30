var _ctx = $("meta[name='ctx']").attr("content");

function showAlert(message){
    $("#alertBoxMessage").html(message);
    $("#alertBox").fadeTo(1000,1).delay(2000).fadeTo(3000,0,function () {$(this).hide();});
}