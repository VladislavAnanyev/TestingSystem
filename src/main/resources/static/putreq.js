function updateQuiz(id) {
/*

    let arr2 = [];
    let options2 = [];

    let xhr = new XMLHttpRequest();
    let title_1 = document.getElementById("exampleFormControlInput1")
    let answer = $('input[name=check]:checked');
    let title = $('input[name=title]').val();
    let text = $('input[name=text]').val();
    let opt = $('input[name=options]');

    for (i = 0; i < answer.length; i++) {
        arr2.push(answer[i].value)
    }

    for (i = 0; i < opt.length; i++) {
        options2.push(opt[i].value)
    }

    const json = {
        title: title_1.value,
        text: text,
        options: options2,
       // options: options2,
        answer: arr2
    }

    console.log(JSON.stringify(json));
    xhr.open('PUT', '/update/' + id);
    xhr.setRequestHeader('Content-type','application/json; charset=utf-8');
    xhr.send(JSON.stringify(json));
*/





    let quizzes_mas = [];

    let countOfQuiz = document.getElementsByClassName("quiz").length;
    let title = document.getElementsByName("title");
    let text = document.getElementsByName("text");
    let description = document.getElementById("exampleFormControlInput0");

    console.log(countOfQuiz)


    for (let i = 0; i < countOfQuiz; i++) {
        let options = document.getElementsByName(String(Number(i+1) + "options"));
        let opt_values = [];
        let answer_values = [];
        for (let j = 0; j < options.length; j++) {

            opt_values.push(options[j].value)
            console.log(options[j].value)
        }
        let name = Number(i + 1) + "check";

        //arr = $('input[name=' + name + ']:checked');

        let answers = document.getElementsByName(name);
        console.log(answers.length)

        for (let i = 0; i < answers.length; i++) {
            if(answers[i].checked) {
                console.log(answers[i].value)
                answer_values.push(answers[i].value)
            }
        }


        let quiz = {
            title: title[i].value,
            text: text[i].value,
            options:opt_values,
            answer: answer_values
        }
        quizzes_mas.push(quiz)
    }

    //let title = $('input[name=title]').val();
    //let text = $('input[name=text]').val();

    /*let opt = $('input[name=options]');

    for (i = 0; i < arr.length; i++) {
        arr2.push(arr[i].value)
    }*/



    // let quizzes = {
    //         title: title,
    //         text: text,
    //         options: options,
    //         answer: arr2
    //     }

    // quizzes_mas.push(quiz)


    console.log(description.value)

    const json = {
        description: description.value,
        quizzes: quizzes_mas
    }
    //console.log("abcde")

    let xhr = new XMLHttpRequest();
    console.log(JSON.stringify(json));
    xhr.open('PUT', '/update/' + id);
    xhr.setRequestHeader('Content-type','application/json; charset=utf-8');
    xhr.onreadystatechange = function() {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            document.location.href = "#"
            let div = document.createElement("div");
            div.setAttribute('class', 'alert alert-success');
            div.setAttribute('role', 'alert');

            div.innerText = 'Успешно!';

            let div2 = document.getElementById("checkForm0");
            //document.body.append(div);
            div2.before(div);

            setTimeout(() => $(div).slideUp('slow', function () {
                    //button.disabled = false;
                    $(this).remove();
                    //button.disabled = false;

                }
            ), 3000);
        }
    }
    xhr.send(JSON.stringify(json));


    /*let xhr_2 = new XMLHttpRequest();
    xhr_2.open('GET', '/api/quizzes/',true);
    xhr_2.setRequestHeader('Content-type','application/json; charset=utf-8');
    xhr_2.send();*/


}