function onInput(quizId, index) {

    console.log('stringAnswer' + index)
    let text = document.getElementById('stringAnswer' + index)
    console.log(text)
    if (text.value !== "") {
        let json = {
            answer: text.value,
            quizId: Number(quizId)
        }

        console.log(json)
        let xhrTyping = new XMLHttpRequest();

        xhrTyping.open('POST', '/test/answer/update', true);
        xhrTyping.setRequestHeader('Content-type', 'application/json; charset=utf-8');
        xhrTyping.onreadystatechange = function () {
            if (xhrTyping.readyState === XMLHttpRequest.DONE && xhrTyping.status === 200) {
                console.log("Успех")
            }
        };
        xhrTyping.send(JSON.stringify(json));
    }


}