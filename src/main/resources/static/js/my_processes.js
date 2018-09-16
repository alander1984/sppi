function resumeTest(processId, questionId, answer) {
    getNext(processId, questionId, answer);
}

function passTestAgain(productCode, productName) {
    startProcess(productCode, productName)
}