function addMember() {
    let xhr = new XMLHttpRequest();
    let courseId = document.getElementById("courseId").value
    let email = document.getElementById("exampleInputEmail1").value
    let json = {
        courseId: courseId,
        email: email
    }

    xhr.open('POST', '/course/' + courseId + "/members/add");
    xhr.setRequestHeader('Content-type','application/json; charset=utf-8');
    xhr.onreadystatechange = function () {
        if(xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            console.log("Успешно")
        }
    };
    xhr.send(JSON.stringify(json));
}