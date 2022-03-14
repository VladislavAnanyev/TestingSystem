function writeMsg(username) {
    let xhr = new XMLHttpRequest();
    let json = {
        username: username
    }
    console.log(username)
xhr.open('POST', '/checkdialog');
xhr.setRequestHeader('Content-type','application/json; charset=utf-8');
xhr.onreadystatechange = function () {
if(xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
    location.href='../chat/' + xhr.responseText
}
};
xhr.send(JSON.stringify(json));
}

function writeMsgFromChat(username) {
    let xhr = new XMLHttpRequest();
    let json = {
        username: username
    }
    console.log(username)
    xhr.open('POST', '/checkdialog');
    xhr.setRequestHeader('Content-type','application/json; charset=utf-8');
    xhr.onreadystatechange = function () {
        if(xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            location.href='/chat/' + xhr.responseText
        }
    };
    xhr.send(JSON.stringify(json));
}