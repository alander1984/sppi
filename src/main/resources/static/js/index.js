$("#start_button").click(function (e) {
    e.preventDefault();
    var productCode = $("#product_type option:selected").val();
    var productName = $("#product_type option:selected").text();
    startProcess(productCode, productName);
});

$("#my_processes_button").click(function (e) {
    window.location = "/my_processes";
});