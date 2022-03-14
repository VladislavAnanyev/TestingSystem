function session(id) {
    //let answer = $('input[name=check]:checked')
    let arr2 = [];

    console.log(id)

    let answer = [];
    let answers = [];
    let answer_values = [];
    let size = document.getElementsByClassName("quiz");
    let name = "check";

    for (let i = 0; i < size.length; i++) {
        answer = document.getElementsByName("" + name + i);

        for (j = 0; j < answer.length; j++) {
            if(answer[j].checked) {
                //console.log(answer[j].value)
                answer_values.push(answer[j].value)
            }
        }
        let test = {answer: answer_values}
        answers.push(test)
        answer_values = []

    }


    const json = {
        //userQuizAnswers: answers
        //userAnswerId: id,
        userQuizAnswers: answers
        //userQuizAnswers: [{answer: [0,1]}, {answer: [1,2]}]
    }



    let xhr = new XMLHttpRequest();

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
    xhr.send(JSON.stringify(json));

}