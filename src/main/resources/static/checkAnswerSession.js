function checkAnswerSession(id) {


    let xhr = new XMLHttpRequest();
    xhr.open('GET', '/checklastanswer/' + id,true);
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
                document.location.href = "/quizzes/" + id + "/solve/";
                console.log(xhr.response)
            }

        } else if (xhr.status === 404) {
            location.href = "/quizzes/" + id + "/solve";
            console.log("Идите нахуй")
        }
    };
    xhr.send()


}

