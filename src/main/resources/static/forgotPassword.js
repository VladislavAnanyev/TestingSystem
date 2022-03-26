function sendEmailMsgWithoutAuth() {
    let xhr = new XMLHttpRequest();
    let username = document.getElementById("resetpassword-email")

    let json = {
        email: username.value
    }
    console.log(username.value)
    xhr.open('POST', '/update/userinfo/pswrdwithoutauth');
    xhr.setRequestHeader('Content-type','application/json; charset=utf-8');
    xhr.onreadystatechange = function () {
        if(xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            console.log(username.value)
        }
    };
    xhr.send(JSON.stringify(json));
}