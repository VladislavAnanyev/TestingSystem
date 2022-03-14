function changePersonalInfo() {

    let fisrtname = $('input[name=firstName]').val();
    let lastname = $('input[name=lastName]').val();
    let username = $('input[name=username]').val();
    let button = document.getElementById("confirm");

    button.disabled = true;

    let json = {
        firstName: fisrtname,
        lastName: lastname
    }

    let xhr = new XMLHttpRequest();
    xhr.open('PUT', '/update/user/' + username);
    xhr.setRequestHeader('Content-type','application/json; charset=utf-8');
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {

            console.log("asdfg");
        }
    };
    xhr.send(JSON.stringify(json));
    let div = document.createElement("div");
    div.style.background = 'MediumSpringGreen';
    div.innerText = 'Успешно';
    div.style.padding = '20px';
    div.style.borderRadius = '10px';
    div.style.opacity = '0.9';

    let div2 = document.getElementById("profile");
    //document.body.append(div);
    div2.before(div);
    setTimeout(() => $(div).slideUp('slow', function () {
            //button.disabled = false;
            $(this).remove();
            button.disabled = false;

    }
        ), 3000);



}