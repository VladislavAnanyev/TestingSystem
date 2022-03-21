function session(id, quizId) {

    let answer = [];
    let answer_values = [];
    let name = "check";
    let test
    answer = document.getElementsByName("" + name + quizId);
    console.log(answer)
    for (j = 0; j < answer.length; j++) {
        if(answer[j].checked) {
            answer_values.push(Number(answer[j].value))
        }
    }
     test =
     {
             answer: answer_values,
             quizId: quizId
     }

    let xhr = new XMLHttpRequest();
    console.log(test)
    xhr.open('POST', '/answersession/' + id, true);
    xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {

        }
    };
    xhr.send(JSON.stringify(test));

}