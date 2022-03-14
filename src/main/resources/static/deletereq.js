
function deleteQuiz(id) {

    let xhr = new XMLHttpRequest();
    console.log('/api/quizzes/' + id);
    let div = document.getElementById(id);
    xhr.open('DELETE', '/quizzes/' + id);
    //xhr.onreadystatechange = function () {
      //  if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            setTimeout(() => $(div).slideUp('slow', function () {
                    //button.disabled = false;
                    $(this).remove();
                    //button.disabled = false;

                }
            ), 0);

     //   }
    //}
    xhr.send();




    //div.remove();

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
