function sendEmailMsgWithoutAuth() {
    let xhr = new XMLHttpRequest();
    let username = $('input[name=name]').val();

    let json = {
        username: username
    }
    console.log(username)
    xhr.open('POST', '/update/userinfo/pswrdwithoutauth');
    xhr.setRequestHeader('Content-type','application/json; charset=utf-8');
    xhr.onreadystatechange = function () {
        if(xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            console.log(username)
        }
    };
    xhr.send(JSON.stringify(json));
}