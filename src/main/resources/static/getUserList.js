/*
function getUserList() {

    let xhr = new XMLHttpRequest();

    xhr.open('GET', '/getUserList');
    xhr.setRequestHeader('Content-type','application/json; charset=utf-8');
    xhr.onreadystatechange = function() {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            console.log(xhr.response)
            let div = document.createElement("div");

            let id = document.getElementsByClassName(numTest + "input").length + 1;
            div.className = "custom-control custom-checkbox mt-2";
            div.id = numTest + "Id" + id;
            div.innerHTML = "<input type=\"checkbox\" class=\"custom-control-input" + " " + numTest + "input\" id=" + numTest + "customCheck" + id + " name=\"" + numTest + "check\" value=" + (id-1) + ">\n" +
                "        <label class=\"custom-control-label" + " " + numTest + "opt\" id=" + numTest + "label" + id + " for=" + numTest + "customCheck" + id + ">\n" +
                "        <input type=\"text\" class=\"form-control\" id=" + numTest + "options" + id + " placeholder=" + id  + ")\ name=\"" + numTest + "options\">\n" +
                "        </label> " +
                // "        <button onclick=\"removeOptions(" + numTest + "," + id + ")\" class=\"btn btn-primary " + numTest + "butt\">Удалить вариант</button>" +
                "<a href=\"#\" style=\"color: black\"  data-bs-toggle=\"tooltip\" data-bs-placement=\"right\" title=\"Удалить вариант\">\n" +
                "                    <svg onclick=\"removeOptions(" + numTest + "," + id + ")\"  xmlns=\"http://www.w3.org/2000/svg\" width=\"16\" height=\"16\" fill=\"currentColor\" class=\"bi bi-archive-fill ml-2 1butt\" viewBox=\"0 0 16 16\">\n" +
                "                        <path d=\"M12.643 15C13.979 15 15 13.845 15 12.5V5H1v7.5C1 13.845 2.021 15 3.357 15h9.286zM5.5 7h5a.5.5 0 0 1 0 1h-5a.5.5 0 0 1 0-1zM.8 1a.8.8 0 0 0-.8.8V3a.8.8 0 0 0 .8.8h14.4A.8.8 0 0 0 16 3V1.8a.8.8 0 0 0-.8-.8H.8z\"/>\n" +
                "                    </svg>\n" +
                "                </a>"

            let opt = document.getElementById(numTest + "optionstest");
            opt.append(div);
        }
    }
    xhr.send();

}*/
