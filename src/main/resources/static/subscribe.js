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


    let xhr = new XMLHttpRequest();
    xhr.open('GET', '/authuser', false);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            username = xhr.responseText
        }
    }
    xhr.send();

    stompClient.subscribe('/topic/' + username, onMessageReceived);

}


function onErrorNotif(error) {
    console.log("fail")
    console.log('STOMP: ' + error);
    stompClient.disconnect()
    setTimeout( () => {notificationConnect()}, 5000);
    console.log('STOMP: Reconecting in 5 seconds');
    console.log("try")
}


function onMessageReceived(payload) {

    let message = JSON.parse(payload.body);
    let toastLiveExample

    let toast
    // username = frame.headers['type'];


    if (message.type === "MESSAGE" && !location.pathname.includes(message.dialog.dialogId)) {


        if (username !== message.sender.username) {

            toastLiveExample = document.getElementById('liveToastMessage')

            let body = document.getElementById("toast-text-msg")
            let head = document.getElementById("toast-head-msg")

            body.textContent = message.content
            head.textContent = message.sender.username

            toast = new bootstrap.Toast(toastLiveExample, null)

            toast.show()


        }
    }

    if (message.type === "MEETING") {

        console.log(111)


        toastLiveExample = document.getElementById('liveToastMeet')

        let body = document.getElementById("toast-text-meet")
        let head = document.getElementById("toast-head-meet")

        body.textContent = "Скорее посмотрите их"
        head.textContent = "У вас новые встречи"

        toast = new bootstrap.Toast(toastLiveExample, null)

        toast.show()
    }

    if (message.type === "MESSAGE" && !location.pathname.includes(message.dialog.dialogId)
            && location.pathname.includes("/chat")) {


        if (message.dialog.name !== null) {

            console.log("Успех")
            console.log(message)

            let dialog = document.getElementById("dialogs")

            //console.log(freme.headers['type'])

            let dialogsName = document.getElementsByClassName("dialogsuser")
            let dialogsNameArr = []

            for (let i = 0; i < dialogsName.length; i++) {
                dialogsNameArr.push(dialogsName[i].textContent)
                //  console.log(dialogsName[i].textContent)
            }


            let div2 = document.createElement("div")
            div2.setAttribute('class', "chat_list")
            div2.setAttribute('id', message.dialog.dialogId)
            //div2.setAttribute('onclick', "activeChat(" + message.sender.username + ")")
            //div2.setAttribute('onclick', "activeChat(123)")


            if (username !== message.sender.username) {

                div2.innerHTML = "<a id=" + message.dialog.dialogId + "href\" href=\"/chat/" + message.dialog.dialogId + "\">" +
                    "                                    <div class=\"chat_people\">\n" +
                    "                                        <div class=\"chat_img\"> <img class='rounded-circle' src=" + message.dialog.image + " alt=\"sunil\"> </div>\n" +
                    "                                        <div class=\"chat_ib\">\n" +
                    "                                            <h5 class=\"dialogsuser\">" + message.dialog.name + "<span class=\"chat_date\"></span></h5>\n" +
                    "                                            <p id=\"lastMsg" + message.dialog.dialogId + "\">" + message.content + "</p>\n" +
                    "                                        </div>\n" +
                    "                                    </div></a>"


                if (dialogsNameArr.indexOf(message.dialog.name) === -1) {
                    dialog.prepend(div2)
                }

                //if (dialogsNameArr.indexOf(message.dialog.name) === 0) {
                    let existDialog = document.getElementById(message.dialog.dialogId)
                    existDialog.remove()
                    dialog.prepend(existDialog)
                    document.getElementById("lastMsg" + message.dialog.dialogId).textContent = message.content
                //}

            }

        } else {
            console.log("Успех")
            console.log(message)

            let dialog = document.getElementById("dialogs")

            //console.log(freme.headers['type'])

            let dialogsName = document.getElementsByClassName("dialogsuser")
            let dialogsNameArr = []

            for (let i = 0; i < dialogsName.length; i++) {
                dialogsNameArr.push(dialogsName[i].textContent)
                //  console.log(dialogsName[i].textContent)
            }


            let div2 = document.createElement("div")
            div2.setAttribute('class', "chat_list")
            div2.setAttribute('id', message.dialog.dialogId)
            //div2.setAttribute('onclick', "activeChat(" + message.dialog.dialogId + ")")


            if (username !== message.sender.username) {

                div2.innerHTML = "<a id="+ message.dialog.dialogId + "href\" href=\"/chat/" + message.dialog.dialogId + "\">" +
                    "                                    <div class=\"chat_people\">\n" +
                    "                                        <div class=\"chat_img\"> <img class='rounded-circle' src=" + message.sender.avatar + " alt=\"sunil\"> </div>\n" +
                    "                                        <div class=\"chat_ib\">\n" +
                    "                                            <h5 class=\"dialogsuser\">" + message.sender.username + "<span class=\"chat_date\"></span></h5>\n" +
                    "                                            <p id=\"lastMsg" + message.dialog.dialogId + "\">" + message.content + "</p>\n" +
                    "                                        </div>\n" +
                    "                                    </div></a>"



                if (dialogsNameArr.indexOf(message.sender.username) === -1) {
                    dialog.prepend(div2)
                }


                //if (dialogsNameArr.indexOf(message.sender.username) === 0) {
                let existDialog = document.getElementById(message.dialog.dialogId)
                existDialog.remove()
                dialog.prepend(existDialog)
                document.getElementById("lastMsg" + message.dialog.dialogId).textContent = message.content
                //}



            }
        }







    }


    if (message.type === "MESSAGE" && location.pathname.includes(message.dialog.dialogId)) {
        console.log("Успех")
        console.log(message)

        if (username !== message.sender.username) {
        let dialog = document.getElementById("dialogs")

        //console.log(freme.headers['type'])

        let dialogsName = document.getElementsByClassName("dialogsuser")
        let dialogsNameArr = []

        for (let i = 0; i < dialogsName.length; i++) {
            dialogsNameArr.push(dialogsName[i].textContent)
            //  console.log(dialogsName[i].textContent)
        }


        let div2 = document.createElement("div")
        div2.setAttribute('class', "chat_list")
        div2.setAttribute('id', message.dialog.dialogId)
        //div2.setAttribute('onclick', "activeChat(" + message.sender.username + ")")
        //div2.setAttribute('data-barba-prevent', "")

        if (username !== message.sender.username) {

            div2.innerHTML="<a data-barba-prevent id="+ message.dialog.dialogId + "href\" href=\"/chat/" + message.dialog.dialogId + "\">" +
                "                                    <div class=\"chat_people\">\n" +
                "                                        <div class=\"chat_img\"> <img class='rounded-circle' src=" + message.sender.avatar + " alt=\"sunil\"> </div>\n" +
                "                                        <div class=\"chat_ib\">\n" +
                "                                            <h5 class=\"dialogsuser\">" + message.sender.username + "<span class=\"chat_date\"></span></h5>\n" +
                "                                            <p id=\"lastMsg" + message.dialog.dialogId + "\">" + message.content + "</p>\n" +
                "                                        </div>\n" +
                "                                    </div></a>"



            if (dialogsNameArr.indexOf(message.sender.username) === -1) {
                 // dialog.prepend(div2)
            }


            //if (dialogsNameArr.indexOf(message.sender.username) === 0) {
                //document.getElementById("lastMsg" + message.dialog.dialogId).textContent = message.content
            //}





            /*let existDialog = document.getElementById(message.dialog.dialogId)
            existDialog.remove()
            dialog.prepend(existDialog)
            document.getElementById("lastMsg" + message.dialog.dialogId).textContent = message.content*/


        }


        let div = document.createElement("div");

        console.log(username)
            console.log(message.sender.userId)
        if (Number(username) !== Number(message.sender.userId)) {

            console.log("Привет")

            div.setAttribute('class', "message")
            let date = new Date(message.timestamp)
            /*div.innerHTML = "<div class=\"sent_msg\">\n" +
                "                                    <p>" + message.content + "</p>\n" +
                "                                    <span class=\"time_date\">" + date.toLocaleDateString() + " " + date.toLocaleTimeString() + "</span> </div>\n" +
                "                            "*/



            div.innerHTML =
                "                                            <a href=\"#\" data-bs-toggle=\"modal\" data-bs-target=\"#modal-user-profile\" class=\"avatar avatar-responsive\">\n" +
                "                                                <img class=\"avatar-img\" src=\"https://localhost/img/default.jpg\" alt=\"\">\n" +
                "                                            </a>\n" +
                "\n" +
                "                                            <div class=\"message-inner\">\n" +
                "                                                <div class=\"message-body\">\n" +
                "                                                    <div class=\"message-content\">\n" +
                "                                                        <div class=\"message-text\">\n" +
                "                                                            <p>" + message.content + "</p>\n" +
                "                                                        </div>\n" +
                "\n" +
                "                                                        <!-- Dropdown -->\n" +
                "                                                        <div class=\"message-action\">\n" +
                "                                                            <div class=\"dropdown\">\n" +
                "                                                                <a class=\"icon text-muted\" href=\"#\" role=\"button\" data-bs-toggle=\"dropdown\" aria-expanded=\"false\">\n" +
                "                                                                    <svg xmlns=\"http://www.w3.org/2000/svg\" width=\"24\" height=\"24\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\" stroke-linecap=\"round\" stroke-linejoin=\"round\" class=\"feather feather-more-vertical\">\n" +
                "                                                                        <circle cx=\"12\" cy=\"12\" r=\"1\"></circle>\n" +
                "                                                                        <circle cx=\"12\" cy=\"5\" r=\"1\"></circle>\n" +
                "                                                                        <circle cx=\"12\" cy=\"19\" r=\"1\"></circle>\n" +
                "                                                                    </svg>\n" +
                "                                                                </a>\n" +
                "\n" +
                "                                                                <ul class=\"dropdown-menu\">\n" +
                "                                                                    <li>\n" +
                "                                                                        <a class=\"dropdown-item d-flex align-items-center\" href=\"#\">\n" +
                "                                                                            <span class=\"me-auto\">Edit</span>\n" +
                "                                                                            <div class=\"icon\">\n" +
                "                                                                                <svg xmlns=\"http://www.w3.org/2000/svg\" width=\"24\" height=\"24\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\" stroke-linecap=\"round\" stroke-linejoin=\"round\" class=\"feather feather-edit-3\">\n" +
                "                                                                                    <path d=\"M12 20h9\"></path>\n" +
                "                                                                                    <path d=\"M16.5 3.5a2.121 2.121 0 0 1 3 3L7 19l-4 1 1-4L16.5 3.5z\"></path>\n" +
                "                                                                                </svg>\n" +
                "                                                                            </div>\n" +
                "                                                                        </a>\n" +
                "                                                                    </li>\n" +
                "                                                                    <li>\n" +
                "                                                                        <a class=\"dropdown-item d-flex align-items-center\" href=\"#\">\n" +
                "                                                                            <span class=\"me-auto\">Reply</span>\n" +
                "                                                                            <div class=\"icon\">\n" +
                "                                                                                <svg xmlns=\"http://www.w3.org/2000/svg\" width=\"24\" height=\"24\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\" stroke-linecap=\"round\" stroke-linejoin=\"round\" class=\"feather feather-corner-up-left\">\n" +
                "                                                                                    <polyline points=\"9 14 4 9 9 4\"></polyline>\n" +
                "                                                                                    <path d=\"M20 20v-7a4 4 0 0 0-4-4H4\"></path>\n" +
                "                                                                                </svg>\n" +
                "                                                                            </div>\n" +
                "                                                                        </a>\n" +
                "                                                                    </li>\n" +
                "                                                                    <li>\n" +
                "                                                                        <hr class=\"dropdown-divider\">\n" +
                "                                                                    </li>\n" +
                "                                                                    <li>\n" +
                "                                                                        <a class=\"dropdown-item d-flex align-items-center text-danger\" href=\"#\">\n" +
                "                                                                            <span class=\"me-auto\">Delete</span>\n" +
                "                                                                            <div class=\"icon\">\n" +
                "                                                                                <svg xmlns=\"http://www.w3.org/2000/svg\" width=\"24\" height=\"24\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\" stroke-linecap=\"round\" stroke-linejoin=\"round\" class=\"feather feather-trash-2\">\n" +
                "                                                                                    <polyline points=\"3 6 5 6 21 6\"></polyline>\n" +
                "                                                                                    <path d=\"M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2\"></path>\n" +
                "                                                                                    <line x1=\"10\" y1=\"11\" x2=\"10\" y2=\"17\"></line>\n" +
                "                                                                                    <line x1=\"14\" y1=\"11\" x2=\"14\" y2=\"17\"></line>\n" +
                "                                                                                </svg>\n" +
                "                                                                            </div>\n" +
                "                                                                        </a>\n" +
                "                                                                    </li>\n" +
                "                                                                </ul>\n" +
                "                                                            </div>\n" +
                "                                                        </div>\n" +
                "                                                    </div>\n" +
                "\n" +
                "\n" +
                "                                                <div class=\"message-footer\">\n" +
                "                                                    <span class=\"extra-small text-muted\">" + date.toLocaleDateString() + " " + date.toLocaleTimeString() + "</span>\n" +
                "                                                </div>\n" +
                "                                            </div>\n" +
                "                                        </div>"

            //if (dialogsNameArr.indexOf(message.sender.username) === 0) {
            // document.getElementById("lastMsg" + message.dialog.dialogId).textContent = message.content
            //}
            /*div.innerHTML =
                "<div class=\"incoming_msg_img\"> <img class='rounded-circle' src=" + message.sender.avatar + " alt=\"sunil\"> </div>" +
                "                        <div class=\"received_msg\">\n" +
                "                        <div class=\"received_withd_msg\">\n" +
                "                            <p>" + message.content + "</p>\n" +
                "                            <span class=\"time_date\">" + date.toLocaleDateString() + " " + date.toLocaleTimeString() + "</span> </div> </div>\n"
*/

        } else {
            div.setAttribute('class', "message message-out")
            let date = new Date(message.timestamp)

            div.innerHTML =
                "                                            <a href=\"#\" data-bs-toggle=\"modal\" data-bs-target=\"#modal-profile\" class=\"avatar avatar-responsive\">\n" +
                "                                                <img class=\"avatar-img\" src=\"https://localhost/img/default.jpg\" alt=\"\">\n" +
                "                                            </a>\n" +
                "\n" +
                "                                            <div class=\"message-inner\">\n" +
                "                                                <div class=\"message-body\">\n" +
                "                                                    <div class=\"message-content\">\n" +
                "                                                        <div class=\"message-text\">\n" +
                "                                                            <p>" + message.content + "</p>\n" +
                "                                                        </div>\n" +
                "\n" +
                "                                                        <!-- Dropdown -->\n" +
                "                                                        <div class=\"message-action\">\n" +
                "                                                            <div class=\"dropdown\">\n" +
                "                                                                <a class=\"icon text-muted\" href=\"#\" role=\"button\" data-bs-toggle=\"dropdown\" aria-expanded=\"false\">\n" +
                "                                                                    <svg xmlns=\"http://www.w3.org/2000/svg\" width=\"24\" height=\"24\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\" stroke-linecap=\"round\" stroke-linejoin=\"round\" class=\"feather feather-more-vertical\">\n" +
                "                                                                        <circle cx=\"12\" cy=\"12\" r=\"1\"></circle>\n" +
                "                                                                        <circle cx=\"12\" cy=\"5\" r=\"1\"></circle>\n" +
                "                                                                        <circle cx=\"12\" cy=\"19\" r=\"1\"></circle>\n" +
                "                                                                    </svg>\n" +
                "                                                                </a>\n" +
                "\n" +
                "                                                                <ul class=\"dropdown-menu\">\n" +
                "                                                                    <li>\n" +
                "                                                                        <a class=\"dropdown-item d-flex align-items-center\" href=\"#\">\n" +
                "                                                                            <span class=\"me-auto\">Edit</span>\n" +
                "                                                                            <div class=\"icon\">\n" +
                "                                                                                <svg xmlns=\"http://www.w3.org/2000/svg\" width=\"24\" height=\"24\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\" stroke-linecap=\"round\" stroke-linejoin=\"round\" class=\"feather feather-edit-3\">\n" +
                "                                                                                    <path d=\"M12 20h9\"></path>\n" +
                "                                                                                    <path d=\"M16.5 3.5a2.121 2.121 0 0 1 3 3L7 19l-4 1 1-4L16.5 3.5z\"></path>\n" +
                "                                                                                </svg>\n" +
                "                                                                            </div>\n" +
                "                                                                        </a>\n" +
                "                                                                    </li>\n" +
                "                                                                    <li>\n" +
                "                                                                        <a class=\"dropdown-item d-flex align-items-center\" href=\"#\">\n" +
                "                                                                            <span class=\"me-auto\">Reply</span>\n" +
                "                                                                            <div class=\"icon\">\n" +
                "                                                                                <svg xmlns=\"http://www.w3.org/2000/svg\" width=\"24\" height=\"24\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\" stroke-linecap=\"round\" stroke-linejoin=\"round\" class=\"feather feather-corner-up-left\">\n" +
                "                                                                                    <polyline points=\"9 14 4 9 9 4\"></polyline>\n" +
                "                                                                                    <path d=\"M20 20v-7a4 4 0 0 0-4-4H4\"></path>\n" +
                "                                                                                </svg>\n" +
                "                                                                            </div>\n" +
                "                                                                        </a>\n" +
                "                                                                    </li>\n" +
                "                                                                    <li>\n" +
                "                                                                        <hr class=\"dropdown-divider\">\n" +
                "                                                                    </li>\n" +
                "                                                                    <li>\n" +
                "                                                                        <a class=\"dropdown-item d-flex align-items-center text-danger\" href=\"#\">\n" +
                "                                                                            <span class=\"me-auto\">Delete</span>\n" +
                "                                                                            <div class=\"icon\">\n" +
                "                                                                                <svg xmlns=\"http://www.w3.org/2000/svg\" width=\"24\" height=\"24\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\" stroke-linecap=\"round\" stroke-linejoin=\"round\" class=\"feather feather-trash-2\">\n" +
                "                                                                                    <polyline points=\"3 6 5 6 21 6\"></polyline>\n" +
                "                                                                                    <path d=\"M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2\"></path>\n" +
                "                                                                                    <line x1=\"10\" y1=\"11\" x2=\"10\" y2=\"17\"></line>\n" +
                "                                                                                    <line x1=\"14\" y1=\"11\" x2=\"14\" y2=\"17\"></line>\n" +
                "                                                                                </svg>\n" +
                "                                                                            </div>\n" +
                "                                                                        </a>\n" +
                "                                                                    </li>\n" +
                "                                                                </ul>\n" +
                "                                                            </div>\n" +
                "                                                        </div>\n" +
                "                                                    </div>\n" +
                "\n" +
                "                                                </div>\n" +
                "\n" +
                "                                                <div class=\"message-footer\">\n" +
                "                                                    <span class=\"extra-small text-muted\">" + date.toLocaleDateString() + " " + date.toLocaleTimeString() + "</span>\n" +
                "                                                </div>\n" +
                "                                            </div>\n"
        }

        let last = document.getElementById("msg");
        last.append(div)
        var div3 = $("#msg2");
        div3.scrollTop(div3.prop('scrollHeight'));
        }
    }
}


function sendMessage(dialog) {


    let messageInput = document.getElementById("inputtextarea");
    console.log(messageInput.value)
    if (messageInput.value.length !== 0) {

        var messageContent = messageInput.value.trim();
        if(messageContent && stompClient) {
            var chatMessage = {
                sender: {userId: username},
                content: messageContent,
                dialog: {dialogId: dialog}
            };
            stompClient.send("/app/user/" + dialog, {}, JSON.stringify(chatMessage));

        }

        /*let date = new Date()
        console.log(Intl.DateTimeFormat().resolvedOptions().timeZone)

        let div = document.createElement("div");
        div.setAttribute('class', "outgoing_msg")

        div.innerHTML =
            "                        <div class=\"sent_msg\">\n" +
            "                            <p>" + messageContent + "</p>\n" +
            "                            <span class=\"time_date\">" +  date.toLocaleDateString() + " " + date.toLocaleTimeString() + "</span> </div>\n"


        let last = document.getElementById("msg");

        last.append(div)

        var div2 = $("#msg");
        div2.scrollTop(div2.prop('scrollHeight'));

        let lastMsg = document.getElementById("lastMsg" + dialog)*/
        //console.log(lastMsg)
        //let msgInput = messageInput.value

        /*let dialogList = document.getElementById("dialogs")
        let existDialog = document.getElementById(dialog)
        existDialog.remove()
        dialogList.prepend(existDialog)*/
        //document.getElementById("lastMsg" + dialog).textContent = message.content


        messageInput.value = ''
        //lastMsg.textContent = messageContent

    }
}



