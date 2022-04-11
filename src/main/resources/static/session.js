function session(id, quizId) {

    let progress = document.getElementById("progressbar")
    let savedProgressValue = progress.getAttribute('aria-valuenow')
    let reduceValue = 100 / document.getElementById("quizcount").textContent
    console.log(savedProgressValue)
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
    if (answer_values.length === 1) {
        let nowValue = Number(savedProgressValue) + Number(reduceValue)
        console.log(nowValue)
        progress.style.width = nowValue + '%'
        progress.setAttribute('aria-valuenow', String(nowValue))
        progress.textContent = nowValue + '%'
    } else if (answer_values.length === 0) {
        let nowValue = Number(savedProgressValue - reduceValue)

        progress.style.width = nowValue + '%'
        progress.setAttribute('aria-valuenow', String(nowValue))
        progress.textContent = nowValue + '%'
    }

     test =
     {
             answer: answer_values,
             quizId: quizId
     }

    let xhr = new XMLHttpRequest();
    console.log(test)
    xhr.open('POST', '/test/answer/update', true);
    xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {

        }
    };
    xhr.send(JSON.stringify(test));

}