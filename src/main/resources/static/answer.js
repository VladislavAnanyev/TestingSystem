

function f(id) {
    //let answer = $('input[name=check]:checked')

    scrollListener()

    let arr2 = [];

    let answerId = document.getElementById("useranswerid");

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
            answer[j].disabled = true;
        }
        let test = {answer: answer_values}
        answers.push(test)
        answer_values = []

    }




    const json = {
        //userQuizAnswers: answers
        //userQuizAnswers: answers,
        userAnswerId: answerId.value
        //userQuizAnswers: [{answer: [0,1]}, {answer: [1,2]}]
    }


    console.log(JSON.stringify(json))



    let xhr = new XMLHttpRequest();

    xhr.open('POST', '/test/answer/' + answerId.value + '/send',true);
    xhr.setRequestHeader('Content-type','application/json; charset=utf-8');
    xhr.onreadystatechange = function () {
        if(xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            /*console.log(xhr.response)
            for (let i = 0; i < size.length; i++) {
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

            /*var myModal = new bootstrap.Modal(document.getElementById('staticBackdrop'), {
                keyboard: false
            })
            myModal.toggle()*/

            //chartpie(xhr.response)
            let btn = document.getElementById("btnAns")
            btn.disabled = true
        }
    };
    xhr.send(JSON.stringify(json));
}

function chartpie(percent) {

    let stat = percent / 100.0

    $(document).ready(function () {
        var ctx = $("#chart-line");
        var myLineChart = new Chart(ctx, {
            type: 'doughnut',
            data: {
                labels: ["Правильно", "Неправильно"],
                datasets: [{
                    data: [(100*stat.toFixed(3)).toFixed(1), 100-(100*stat.toFixed(3)).toFixed(1)],
                    backgroundColor: ["rgba(100, 255, 0, 0.5)", "rgba(255, 0, 0, 0.5)"]
                }]
            },
            options: {
                title: {
                    display: true,
                    text: 'Процент выполнения: ' + (100*stat.toFixed(3)).toFixed(1)
                }
            }
        });
    });
}

