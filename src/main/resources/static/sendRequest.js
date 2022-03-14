function sendRequest(to, id) {
    let xhr = new XMLHttpRequest();

    let content = document.getElementById(to + "msg").value
    console.log(content)
    let json
    if (content !== null && content !== "") {
        json = {
            to: {username: to},
            meeting: {id: id},
            message: {content: document.getElementById(to + "msg").value}
        }
    } else {


        json = {
            to: {username: to},
            meeting: {id: id},
        }
    }
    //console.log(username)
    xhr.open('POST', '/sendRequest');
    xhr.setRequestHeader('Content-type','application/json; charset=utf-8');
    xhr.onreadystatechange = function () {
        if(xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            //location.href='../chat/' + xhr.responseText
            console.log("OK")
            document.getElementById("button" + to).hidden = true
        }
    };
    xhr.send(JSON.stringify(json));

}