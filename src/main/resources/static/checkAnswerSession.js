function checkAnswerSession(id) {

    let xhr = new XMLHttpRequest();
    xhr.open('GET', '/test/' + id + '/answer/check',true);
    xhr.setRequestHeader('Content-type','application/json; charset=utf-8');
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {

            //console.log(xhr.responseText)
            if (xhr.responseText === 'true') {
                var myModal = new bootstrap.Modal(document.getElementById('staticBackdrop' + id), {
                    keyboard: false
                })
                myModal.toggle()
                console.log(xhr.response)
            } else {
                //document.location.href =  "/test/answer/" + id + "/start";
                console.log(xhr.response)
                getAnswerSessionId(id, false)
            }

        } else if (xhr.status === 404) {
            //location.href = "/test/answer/" + id + "/start";
            getAnswerSessionId(id, false)
        }
    };
    xhr.send()


}

