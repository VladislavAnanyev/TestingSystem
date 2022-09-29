function addCourse() {

    let name = document.getElementById("course-name").value;
    let json = {
        courseName: name
    }

    let xhr = new XMLHttpRequest();
    xhr.open('POST', '/course',true);
    xhr.setRequestHeader('Content-type','application/json; charset=utf-8');
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            // обработка успешного выполнения запроса
            // т.е вывод созданного курса на экран
        } else if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 400) {
            // обработка ошибки
        }
    };
    xhr.send(JSON.stringify(json))
}

/*
document.location.href = "#"
let div = document.createElement("div");
div.setAttribute('class', 'alert alert-success');
div.setAttribute('role', 'alert');

div.innerText = 'Успешно!';*/
