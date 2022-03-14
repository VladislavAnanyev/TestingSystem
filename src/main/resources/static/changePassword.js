function changePass() {
    let username = $('input[name=username]').val();
    let oldPass = $('input[name=password]').val();
    /*console.log(document.location.href.toString.toString().replace(document.location.href.hostname
        + "/" + "updatepass/", ""))*/
    let pass1 = $('input[name=password1]').val();
    let pass2 = $('input[name=password2]').val();

    f(pass1,pass2)
}

function f(pass1, pass2) {
    let username = $('input[name=username]').val();
    if (pass1 === pass2) {
        let xhrPut = new XMLHttpRequest();
        let jsonPut = {
            username: username,
            password: pass1
        }

        let code = document.location.pathname
        xhrPut.open('PUT',  code);
        xhrPut.setRequestHeader('Content-type','application/json; charset=utf-8');
        xhrPut.onreadystatechange = function () {
            if(xhrPut.readyState === XMLHttpRequest.DONE && xhrPut.status === 200) {
                document.location.href= "../../signin"
            }
        };
        xhrPut.send(JSON.stringify(jsonPut));
        console.log("Успех");
        document.getElementById("password1").style.background = 'MediumSpringGreen';
        document.getElementById("password2").style.background = 'MediumSpringGreen';

    } else {
        document.getElementById("password1").style.background = 'Salmon';
        document.getElementById("password2").style.background = 'Salmon';
        console.log("Ошибка");
    }
}