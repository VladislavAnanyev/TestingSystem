function acceptRequest(id) {
    let xhr = new XMLHttpRequest();
    let json = {
        id: id
    }
    console.log(username)
    xhr.open('POST', '/acceptRequest');
    xhr.setRequestHeader('Content-type','application/json; charset=utf-8');
    xhr.onreadystatechange = function () {
        if(xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            location.href='../chat/' + xhr.responseText
        }
    };
    xhr.send(JSON.stringify(json));
}