function session(id, quizId) {
    //let answer = $('input[name=check]:checked')
    let arr2 = [];

    console.log(id)

    let answer = [];
    let answers = [];
    let answer_values = [];
    let size = document.getElementsByClassName("quiz");
    let name = "check";
    let test
    for (let i = 0; i < size.length; i++) {
        answer = document.getElementsByName("" + name + i);

        for (j = 0; j < answer.length; j++) {
            if(answer[j].checked) {
                //console.log(answer[j].value)
                answer_values.push(Number(answer[j].value))
            }
        }
         test =
         {
                 answer: answer_values,
                 quizId: quizId
         }
    }

    let xhr = new XMLHttpRequest();
    console.log(test)
    xhr.open('POST', '/answersession/' + id,true);
    xhr.setRequestHeader('Content-type','application/json; charset=utf-8');
    xhr.onreadystatechange = function () {
        if(xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
           /* for (let i = 0; i < size.length; i++){
                let style = document.getElementById("test" + i).style;
                style.padding = '20px';
                style.borderRadius = '10px';
                style.opacity = '0.9';


                if (xhr.response[i] === "1") {
                    style.background = 'MediumSpringGreen';
                } else {
                    style.background = 'Salmon';
                }


            }*/

        }
    };
    xhr.send(JSON.stringify(test));

}