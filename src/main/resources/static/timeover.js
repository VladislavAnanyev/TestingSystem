'use strict';

/*var usernamePage = document.querySelector('#username-page');
var chatPage = document.querySelector('#chat-page');
var usernameForm = document.querySelector('#usernameForm');
var messageForm = document.querySelector('#messageForm');*/
//var messageInput = document.querySelector('#message');

/*var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('.connecting');*/

var stompClient2 = null;
var username2 = null;

var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

function connect() {

    console.log("Go")
    var socket = new SockJS('/ws');
    stompClient2 = Stomp.over(socket);

    stompClient2.connect({}, onConnected, onError);
}


function onConnected() {

    let xhr = new XMLHttpRequest();
    xhr.open('GET', '/authuser', false);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            console.log(xhr.responseText)
            username2 = xhr.responseText
        }
    }
    xhr.send();

    // Subscribe to the Public Topic

    let id = document.getElementById("useranswerid").value

    console.log('/topic/' + username2);
    stompClient2.subscribe('/topic/' + username2 + id, onMessageReceived);

    console.log("uuu")


    // Tell your username to the server
    /*stompClient.send("/app/testchat",
         {},
         JSON.stringify({sender: username, type: 'JOIN'})
     )

     connectingElement.classList.add('hidden');*/
}


function onError(error) {
    /*connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';*/
    console.log("fail")

    connect()
    console.log("try")
}




function onMessageReceived(payload) {
    let answer = JSON.parse(payload.body);
    let size = document.getElementsByClassName("quiz");
    let result
    if (answer.result != null) {
        result = answer.result.replaceAll("\"", "")




        for (let i = 0; i < size.length; i++) {
            let style = document.getElementById("test" + i).style;
            style.padding = '20px';
            style.borderRadius = '10px';
            style.opacity = '0.9';


            if (result[i] == "1") {
                style.background = 'MediumSpringGreen';
            } else {
                style.background = 'Salmon';
            }


        }
    }


    let btn = document.getElementById("btnAns")

    let name = "check";

    for (let i = 0; i < size.length; i++) {
        let answer = document.getElementsByName("" + name + i);
        console.log(answer)
        for (let j = 0; j < answer.length; j++) {
            if (answer[j].checked) {
                //console.log(answer[j].value)
                //answer_values.push(answer[j].value)
            }
            answer[j].disabled = true;
        }

    }

    btn.disabled = true

    var myModal = new bootstrap.Modal(document.getElementById('staticBackdrop'), {
        keyboard: false
    })
    myModal.toggle()
    console.log("1234567890")

    chartpie(answer.percent)


    let clock = document.getElementById("clock")
    if (clock !== null) {
        clock.hidden = true
    }

}
