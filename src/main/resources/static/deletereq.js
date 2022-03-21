
function deleteQuiz(id) {

    let xhr = new XMLHttpRequest();
    console.log('/api/quizzes/' + id);
    let div = document.getElementById(id);
    xhr.open('DELETE', '/quizzes/' + id);
            setTimeout(() => $(div).slideUp('slow', function () {
                    $(this).remove();
                }
            ), 0);
    xhr.send();
}

function deleteTempQuiz(id) {
    let div = document.getElementById(id + "Id");
    setTimeout(() => $(div).slideUp('slow', function () {
            //button.disabled = false;
            $(this).remove();
            //button.disabled = false;

        }
    ), 0);
}
