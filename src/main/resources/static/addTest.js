

async function addTest() {
    let quizzes_mas = [];
    let name = document.getElementById("description validationDefault01");
    let courseId = document.getElementById("courseId");
    console.log(document.getElementsByClassName("checkFormCl").length)
    let isValidForm = true

    // Цикл с конца так как только в таком случае валидация работает сверху вниз, а не внизу вверх
    /*for (let i = document.getElementsByClassName("checkFormCl").length-1; i >= 0 ; i--) {
        document.forms['checkForm' + i].reportValidity()
        isValidForm = isValidForm && document.forms['checkForm' + i].checkValidity()
    }*/

    if (isValidForm) {

        let countOfQuiz = document.getElementsByClassName("quiz").length;
        let title = document.getElementsByName("title");
        console.log(title)
        let text = document.getElementsByName("question");
        console.log(text)
        let time = null
        let startDate = null
        let endDate = null
        let attempts = null
        let viewAnswers = false

        if (document.getElementById("flexRadioDefault2").checked) {
            time = document.getElementById("time").value
        }

        if (document.getElementById("flexRadioDefault10").checked) {
            viewAnswers = true
        }

        if (document.getElementById("flexRadioDefault8").checked) {
            attempts = document.getElementById('attempts').value
        }

        if (document.getElementById("flexRadioDefault4").checked) {
            startDate = document.getElementById('startDate').value
        }

        if (document.getElementById("flexRadioDefault6").checked) {
            endDate = document.getElementById('endDate').value
        }



        for (let i = 0; i < countOfQuiz; i++) {
            const fileObject = document.querySelector('[name=file' + Number(i+1) + ']').files
            let options = document.getElementsByName(String(Number(i + 1) + "options"));
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
                if (answers[i].checked) {
                    console.log(answers[i].value)
                    answer_values.push(answers[i].value)
                }
            }

            let quiz
            console.log(i)
            console.log(String(Number(i + 1) + 'optionstest'))
            console.log(document.getElementById(String(Number(i + 1) + 'optionstest')))
            // let file = document.getElementsByName("file" + Number(i + 1))



            let file
            if (fileObject.length === 0) {
                file = null
            } else {

                let bytes = String(await toBase64(fileObject[0])).replace('data:', '')
                    .replace(/^.+,/, '');

                file = {
                    bytes: bytes,
                    filename: fileObject[0].name
                }
            }



            if (document.getElementById(String(Number(i + 1) + 'optionstest')).hidden === false) {
                quiz = {
                    type: "MULTIPLE",
                    title: title[i].value,
                    text: text[i].value,
                    file: file,
                    options: opt_values,
                    answer: answer_values
                }
            } else if (document.getElementById(String(Number(i + 1) + 'inputAnswer')).hidden === false) {
                let stringAnswer = document.getElementById('stringAnswer' + Number(i + 1))

                quiz = {
                    type: "STRING",
                    title: title[i].value,
                    text: text[i].value,
                    file: file,
                    answer: stringAnswer.value
                }
            } else if (document.getElementById(String(Number(i + 1) + 'mapAnswer')).hidden === false) {
                let map = new Map()

                let map_keys = document.getElementsByClassName('mapkey' + Number(i + 1))
                let map_values = document.getElementsByClassName('mapvalue' + Number(i + 1))
                for (let i = 0; i < map_values.length; i++) {
                    map.set(map_keys[i].value, map_values[i].value)
                }

                const obj = Object.fromEntries(map);
                console.log(obj)
                console.log(JSON.stringify(obj))

                quiz = {
                    type: "MAP",
                    file: file,
                    title: title[i].value,
                    text: text[i].value,
                    answer: obj
                }
            }

            console.log(String(Number(i + 1) + 'optionstest'))
            console.log(document.getElementById(String(Number(i + 1) + 'optionstest')))

            quizzes_mas.push(quiz)
        }

        let json;

        json = {
            description: name.value,
            quizzes: quizzes_mas,
            duration: time,
            courseId: courseId.value,
            startAt: startDate,
            finishAt: endDate,
            attempts: attempts,
            displayAnswers: viewAnswers,
            percent: document.getElementById("rangeval").textContent
        }


        console.log(json)
        let xhr = new XMLHttpRequest();
        xhr.open('POST', '/test/create', true);
        xhr.setRequestHeader('Content-type','application/json');
        xhr.onreadystatechange = function () {
            if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
                var successModal = new bootstrap.Modal(document.getElementById('staticBackdropAdd'))
                successModal.toggle()
            } else if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 400) {
                let div = document.createElement("div");
                div.setAttribute('class', 'alert alert-danger');
                div.setAttribute('role', 'alert');

                div.innerText = 'Ошибка!';

                let div2 = document.getElementById("checkForm0");
                div2.before(div);

                setTimeout(() => $(div).slideUp('slow', function () {
                        $(this).remove();
                    }
                ), 3000);
            } else if (xhr.status === 405) {
                let xhrFix = new XMLHttpRequest();
                xhrFix.open('POST', '/test/create', true);
                xhrFix.setRequestHeader('Content-type','application/json');
                xhrFix.onreadystatechange = function () {
                    if (xhrFix.readyState === XMLHttpRequest.DONE && xhrFix.status === 200) {
                        var successModal = new bootstrap.Modal(document.getElementById('staticBackdropAdd'))
                        successModal.toggle()
                    } else if (xhrFix.readyState === XMLHttpRequest.DONE && xhrFix.status === 400) {
                        let div = document.createElement("div");
                        div.setAttribute('class', 'alert alert-danger');
                        div.setAttribute('role', 'alert');

                        div.innerText = 'Ошибка!';

                        let div2 = document.getElementById("checkForm0");
                        div2.before(div);

                        setTimeout(() => $(div).slideUp('slow', function () {
                                $(this).remove();
                            }
                        ), 3000);
                    }
                };
                xhrFix.send(JSON.stringify(json))
            }
        };
        xhr.send(JSON.stringify(json))

    }

}

const toBase64 = file => new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => resolve(reader.result);
    reader.onerror = error => reject(error);
});