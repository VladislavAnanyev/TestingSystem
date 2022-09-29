
function getAnswerSessionId(testId, restore) {
    let xhr = new XMLHttpRequest();
    console.log(testId)
    xhr.open('POST', '/test/answer/' + testId + '/start?restore=' + restore, true);
    xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            location.href = '/test/' + testId + '/' + xhr.responseText + '/solve'
        } else if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 403) {
            console.log("Что-то пошло не так")
            var myModalForbidden = new bootstrap.Modal(document.getElementById('staticBackdropForbidden'), {
                keyboard: false
            })
            myModalForbidden.toggle()
        }
    };
    xhr.send();
}

