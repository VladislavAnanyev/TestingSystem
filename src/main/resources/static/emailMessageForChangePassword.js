function sendEmailMessage() {

    let xhr = new XMLHttpRequest();



    xhr.open('POST', '/update/userinfo/password');
    xhr.setRequestHeader('Content-type','application/json; charset=utf-8');
    xhr.onreadystatechange = function () {
        if(xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {

        }
    };
    xhr.send();
}

