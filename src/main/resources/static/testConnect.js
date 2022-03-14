function testConnect() {





        let xhr = new XMLHttpRequest();

        //console.log(username)
        xhr.open('GET', '/testConnection', true);
        xhr.setRequestHeader('Content-type','application/json; charset=utf-8');
        xhr.onreadystatechange = function () {
            if(xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
                console.log(xhr.responseText)
            }
        };
        xhr.send();

}