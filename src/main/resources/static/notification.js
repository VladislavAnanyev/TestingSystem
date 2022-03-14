/*
'use strict';

var stompClient = null;
var username = null;

var colors2 = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

function notificationConnect() {

        console.log("Go")
        var socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, onConnectedNotif, onErrorNotif)

}

function onConnectedNotif() {
    meetingConnect()



    let xhr = new XMLHttpRequest();
    xhr.open('GET', '/authuser', false);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            username = xhr.responseText
        }
    }
    xhr.send();

    stompClient.subscribe('/topic/' + username, onMessageReceivedNotif);

}


function onErrorNotif(error) {
    console.log("fail")
    notificationConnect()
    console.log("try")
}





function onMessageReceivedNotif(payload) {

    var message = JSON.parse(payload.body);

    console.log(message)

   // username = frame.headers['type'];


    if (message.type === "MESSAGE" && !location.pathname.includes(message.dialog.dialogId)) {


        if (username !== message.sender.username) {

            var toastLiveExample = document.getElementById('liveToastMessage')

            let body = document.getElementById("toast-text-msg")
            let head = document.getElementById("toast-head-msg")

            body.textContent = message.content
            head.textContent = message.sender.username

            var toast = new bootstrap.Toast(toastLiveExample, null)

            toast.show()


        }
    }


}



*/
