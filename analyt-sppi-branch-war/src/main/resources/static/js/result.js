$(".close-complete-test-btn").on("click",function(e) {
    window.location = location.origin+_ctx+"my_processes?status=complete";
})

$("#downloadTestReportBtn").on("click",function() {
    var url = _ctx+'process/' + processId + '/testReport/';
    var testReportName = 'testReport-' + uti + '.xlsx';
    downloadFiles(url, testReportName);
});

function downloadFiles(url, fileNameForDownLoad) {
    var request = createRequest();
    request.open("GET", url, true);
    request.responseType = "blob";
    request.fileNameForDownLoad = fileNameForDownLoad;
    request.onload = function (e) {
        if (this.status === 200) {
            if (window.navigator.msSaveBlob) {
                window.navigator.msSaveBlob(this.response, this.fileNameForDownLoad);
            } else {
                var file = window.URL.createObjectURL(this.response);
                var a = document.createElement("a");
                a.href = file;
                a.download = this.fileNameForDownLoad;
                document.body.appendChild(a);
                a.click();
            }
        };
    };
    request.send();
}

function createRequest(){
    if (window.XDomainRequest) {
        return new window.XDomainRequest();
    } else {
        return new XMLHttpRequest();
    }
}