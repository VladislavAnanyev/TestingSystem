function mapAnswer(id, quizId) {
    let answer_values = [];
    let test

    let keys = document.getElementsByClassName("map" + quizId + "key")
    console.log(keys)
    let values = document.getElementsByClassName("map" + quizId + "value")
    //console.log(values)

    for (let j = 0; j < values.length; j++) {
        if(values[j].selected) {
            answer_values.push(values[j])
        }
    }

    console.log(answer_values)
    let map = new Map()

    for (let i = 0; i < answer_values.length; i++) {
        map.set(keys[i].innerHTML, answer_values[i].label)
    }

    console.log(map)
    const obj = Object.fromEntries(map);
    console.log(obj)
    console.log(JSON.stringify(obj))

    test =
        {
            answer: obj,
            quizId: quizId
        }


    let xhr = new XMLHttpRequest();
    console.log(JSON.stringify(test))
    xhr.open('POST', '/test/answer/update', true);
    xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {

        }
    };
    xhr.send(JSON.stringify(test));
}