

function addTest() {


    let quizzes_mas = [];
    let name = document.getElementById("description validationDefault01");
    /*if (!name.checkValidity()) {
        //document.getElementById("demo").innerHTML = name.validationMessage;
        alert(1)
        return
    }*/


    console.log(document.getElementsByClassName("checkFormCl").length)
    let isValidForm = true

    // Цикл с конца так как только в таком случае валидация работает сверху вниз, а не внизу вверх
    for (let i = document.getElementsByClassName("checkFormCl").length-1; i >= 0 ; i--) {
        document.forms['checkForm' + i].reportValidity()
        isValidForm = isValidForm && document.forms['checkForm' + i].checkValidity()
    }

    if (isValidForm)
    {
        //let ftext = data.ftext.value;
        //$.post('saveser.php',{ ftext:ftext},function(data){ },'json');
        //window.alert('Спасибо! Ваше сообщение уже у нас в ящике!');
        let countOfQuiz = document.getElementsByClassName("quiz").length;
        let title = document.getElementsByName("title");
        let text = document.getElementsByName("text");

        let time = document.getElementById("time")

        if (!document.getElementById("flexRadioDefault2").checked) {
            time = null
        }


        //console.log(time)


        for (let i = 0; i < countOfQuiz; i++) {
            let options = document.getElementsByName(String(Number(i+1) + "options"));
            let opt_values = [];
            let answer_values = [];
            for (let j = 0; j < options.length; j++) {

                opt_values.push(options[j].value)
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

        let json;

        if (time != null) {


            json = {
                description: name.value,
                quizzes: quizzes_mas,
                duration: time.value
            }
        } else {
            json = {
                description: name.value,
                quizzes: quizzes_mas,
            }
        }
        //console.log("abcde")

        let xhr = new XMLHttpRequest();
        xhr.open('POST', '/quizzes/',true);
        xhr.setRequestHeader('Content-type','application/json; charset=utf-8');
        xhr.onreadystatechange = function () {
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

            } else if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 400) {
                let div = document.createElement("div");
                div.setAttribute('class', 'alert alert-danger');
                div.setAttribute('role', 'alert');

                div.innerText = 'Ошибка!';

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
        };
        xhr.send(JSON.stringify(json))


        /*let xhr_2 = new XMLHttpRequest();
        xhr_2.open('GET', '/api/quizzes/',true);
        xhr_2.setRequestHeader('Content-type','application/json; charset=utf-8');
        xhr_2.send();*/
    }
    /*else
    {
        window.alert('Вы заполнили не все поля!');
    }*/




}