function restoreSession(answer, id) {

    let json

    /*if (answer === true) {
        json = {
            restore: true
        }
    } else {
        json = {
            restore: false
        }
    }*/

    console.log(JSON.stringify(json))


    let xhr = new XMLHttpRequest();
    xhr.open('POST', '/api/quizzes/' + id + '/solves',true);
    xhr.setRequestHeader('Content-type','application/json; charset=utf-8');
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {

            console.log("123")
            // location.href='/api/quizzes/' + id + '/solve'

        }
    };
    xhr.send(answer)
}