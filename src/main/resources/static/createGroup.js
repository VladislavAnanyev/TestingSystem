function createGroup() {



    let dialog = document.getElementById("dialogs")
    let name = document.getElementById("chatName")
    let answer_values = [];

    //document.forms['GroupName'].repo

    //document.forms['GroupName'].reportValidity()
    //document.forms['GroupName'].checkValidity()

    //name.checkValidacity()

    let size = document.getElementsByClassName("users");
    let usersNames = document.getElementsByClassName("usersValue")

    let users = []
    let user;

    console.log(size.length)


        for (let i = 0; i < size.length; i++) {
            // answer = document.getElementsByName("" + name + i);

            //for (j = 0; j < answer.length; j++) {
            if (size[i].checked) {
                //console.log(answer[j].value)
                //console.log(usersNames[i].textContent.trim())
                user = {
                    username: usersNames[i].textContent.trim()
                }
                users.push(user)
                user = ""
            }
            //answer[j].disabled = true;
            // }
            // let test = {answer: answer_values}
            // answers.push(test)
            //answer_values = []

        }

    if (users.length === 1) {
        let xhr = new XMLHttpRequest();
        let json = {
            username: users[0].username
        }

        xhr.open('POST', '/checkdialog');
        xhr.setRequestHeader('Content-type','application/json; charset=utf-8');
        xhr.onreadystatechange = function () {
            if(xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {

                console.log(xhr.responseText)

                if (document.getElementById(xhr.responseText) === null) {

                    let newDialog = document.createElement("div")
                    newDialog.setAttribute('class', "chat_list")
                    newDialog.setAttribute('id', xhr.responseText)



                    newDialog.innerHTML = "<a id=" + xhr.responseText + "href\" href=\"/chat/" + xhr.responseText + "\">" +
                        "                                    <div class=\"chat_people\">\n" +
                        "                                        <div class=\"chat_img\"> <img class='rounded-circle' src=" + location.origin + "/img/default.jpg" + " alt=\"sunil\"> </div>\n" +
                        "                                        <div class=\"chat_ib\">\n" +
                        "                                            <h5 class=\"dialogsuser\">" + users[0].username + "<span class=\"chat_date\"></span></h5>\n" +
                        "                                            <p id=\"lastMsg" + xhr.responseText + "\">" + "" + "</p>\n" +
                        "                                        </div>\n" +
                        "                                    </div></a>"

                    dialog.prepend(newDialog)
                } else {
                    let existDialog = document.getElementById(xhr.responseText)
                    existDialog.remove()
                    dialog.prepend(existDialog)
                    //document.getElementById("lastMsg" + x).textContent = message.content
                }



                //location.href='../chat/' + xhr.responseText
            }
        };
        xhr.send(JSON.stringify(json));
    } else {


        let json = {
            name: name.value,
            users: users
        }

        console.log(json)

        let xhr = new XMLHttpRequest()
        xhr.open('POST', '/createGroup', true)
        xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
        xhr.onreadystatechange = function () {
            if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
                let div2 = document.createElement("div")

                div2.setAttribute('id', xhr.response)
                div2.setAttribute('class', "chat_list")
               // div2.setAttribute('onclick', "activeChat(" + xhr.response + ")")
                //div2.setAttribute('onclick', "activeChat(" + xhr.response + ")")

                console.log(name.value)
                if (name.value === "") {
                    name.value = "Конференция"
                }


                div2.innerHTML = "<a id=" + xhr.response+"href\" href=\"/chat/" + xhr.response + "\">" +
                    "                                    <div class=\"chat_people\">\n" +
                    "                                        <div class=\"chat_img\"> <img class=\"rounded-circle\" src=\"https://" + location.host + "/img/default.jpg" + "\" alt=\"sunil\"> </div>\n" +
                    "                                        <div class=\"chat_ib\">\n" +
                    "                                            <h5 class=\"dialogsuser\">" + name.value + "<span class=\"chat_date\"></span></h5>\n" +
                    "                                            <p id=\"lastMsg" + xhr.response + "\">Группа создана</p>\n" +
                    "                                        </div>\n" +
                    "                                    </div></a>"


                //console.log(message.sender.username)
                //console.log(dialogsNameArr.indexOf(message.sender.username))

                //if (dialogsNameArr.indexOf(message.sender.username) === -1) {
                dialog.prepend(div2)
                //}


                //document.location.href = '/chat/';
            }
        };
        xhr.send(JSON.stringify(json));
    }
}