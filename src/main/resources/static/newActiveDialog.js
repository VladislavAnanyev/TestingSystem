function writeMsg(username) {
    let xhr = new XMLHttpRequest();
    let json = {
        userId: username
    }
    console.log(username)
    xhr.open('POST', '/checkdialog', false);
    xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            console.log("тут")
            location.href = '/chat/' + xhr.responseText
        }
    };
    xhr.send(JSON.stringify(json));
    console.log(xhr.responseText)
    //location.href = '/chat/' + xhr.responseText

}

function writeMsgFromChat(username) {
    let xhr = new XMLHttpRequest();
    let json = {
        username: username
    }
    console.log(username)
    xhr.open('POST', '/checkdialog');
    xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            location.href = '/chat/' + xhr.responseText
        }
    };
    xhr.send(JSON.stringify(json));
}