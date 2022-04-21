function addCourse() {
    let name = document.getElementById("course-name");
    let json = {
        courseName: name.value
    }

    let xhr = new XMLHttpRequest();
    xhr.open('POST', '/course',true);
    xhr.setRequestHeader('Content-type','application/json; charset=utf-8');
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {

            document.location.href = "#"
            let div = document.createElement("div");
            div.setAttribute('class', 'alert alert-success');
            div.setAttribute('role', 'alert');

            div.innerText = 'Успешно!';

            /*let div2 = document.getElementById("checkForm0");
            div2.before(div);

            setTimeout(() => $(div).slideUp('slow', function () {
                    $(this).remove();

                }
            ), 3000);*/

        } else if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 400) {
            /*let div = document.createElement("div");
            div.setAttribute('class', 'alert alert-danger');
            div.setAttribute('role', 'alert');

            div.innerText = 'Ошибка!';

            let div2 = document.getElementById("checkForm0");
            div2.before(div);

            setTimeout(() => $(div).slideUp('slow', function () {
                    $(this).remove();

                }
            ), 3000);*/
        }
    };
    xhr.send(JSON.stringify(json))
}