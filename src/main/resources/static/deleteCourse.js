function deleteCourse(id) {
    let xhr = new XMLHttpRequest();
    console.log('/api/quizzes/' + id);
    let div = document.getElementById(id);
    xhr.open('DELETE', '/course/' + id);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            location.href = '/courses'
        }
    }
    xhr.send();
}