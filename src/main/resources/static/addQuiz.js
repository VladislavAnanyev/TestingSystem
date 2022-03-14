function addQuiz() {
    let div = document.createElement("div");
    let count = document.getElementsByClassName("custom-control-label").length + 1;
    count = 1;

    let allTest = document.getElementsByName("title").length + 1
    div.id = allTest + "Id";
    div.setAttribute('class', "quiz");
    //console.log(allTest.length);
    div.innerHTML = "<form id=\"checkForm"+ Number(allTest) + "\" class='checkFormCl'><div class=\"form-group pt-5\">\n" +
        "        <label for=\"exampleFormControlInput" + allTest + "\">Тема</label>\n" +
        "        <input type=\"text\" class=\"form-control\" required id=\"titleID\" placeholder=\"Напишите здесь тему вопроса из викторины\" name=\"title\">\n" +
        "    </div>\n" +
        "\n" +
        "    <div class=\"form-group\">\n" +
        "        <label for=\"exampleFormControlInput1\">Вопрос</label>\n" +
        "        <input type=\"text\" class=\"form-control\" required id=\"questionID\" placeholder=\"Напишите здесь ваш вопрос\"  name=\"text\">\n" +
        "    </div>\n" +
        "\n" +
        "    <div class=\"options\" id=\"" + allTest + "optionstest\">\n" +
        "        <label for=\"exampleFormControlInput1\">Варианты ответа</label>\n" +
        "    <div class=\"custom-control custom-checkbox\" id=" + allTest +"Id" + count + "\>\n" +
        "<input type=\"checkbox\" class=\"custom-control-input " + allTest + "input\" id=" + Number(allTest) + "customCheck" + Number(count) + "\ name=" + allTest + "check\ value=" + Number(count-1) + ">\n" +
        "        <label class=\"custom-control-label " + allTest + "opt\" id=" + allTest + "label" + count + "\ for=" + Number(allTest) + "customCheck" + Number(count) + "\>\n" +
        "        <input type=\"text\" required class=\"form-control\" id=" + allTest + "options" + Number(count) + "\ placeholder=" + Number(count)  + ")\ name=" + allTest + "options\>\n" +
        "        </label> " +
        //"        <button onclick=\"removeOptions(" + Number(allTest) + "," + Number(count) + ")\" class=\"btn btn-primary " + allTest + "butt\">Удалить вариант</button>\n" +
        //"<a href=\"#\" style=\"color: black\"  data-bs-toggle=\"tooltip\" data-bs-placement=\"right\" title=\"Удалить вариант\">\n" +
        "                    <svg onclick=\"removeOptions(" + Number(allTest) + "," + Number(count) + ")\"  xmlns=\"http://www.w3.org/2000/svg\" width=\"16\" height=\"16\" fill=\"currentColor\" class=\"bi bi-archive-fill ml-2 " + Number(allTest) + "butt\" viewBox=\"0 0 16 16\">\n" +
        "                        <path d=\"M12.643 15C13.979 15 15 13.845 15 12.5V5H1v7.5C1 13.845 2.021 15 3.357 15h9.286zM5.5 7h5a.5.5 0 0 1 0 1h-5a.5.5 0 0 1 0-1zM.8 1a.8.8 0 0 0-.8.8V3a.8.8 0 0 0 .8.8h14.4A.8.8 0 0 0 16 3V1.8a.8.8 0 0 0-.8-.8H.8z\"/>\n" +
        "                    </svg>\n" +
        //"                </a>" +
        "\n" +
        "    </div>\n" +
        "\n" +
        "    <div class=\"custom-control custom-checkbox mt-2\" id=" + allTest +"Id" + (++count) + "\>\n" +
        "<input type=\"checkbox\" class=\"custom-control-input " + allTest + "input\" id=" + Number(allTest) + "customCheck" + Number(count) + "\ name=" + allTest + "check\ value=" + Number(count-1) + ">\n" +
        "        <label class=\"custom-control-label " + allTest + "opt\" id=" + allTest + "label" + count + "\  for=" + Number(allTest) + "customCheck" + Number(count) + "\>\n" +
        "        <input type=\"text\" required class=\"form-control\" id=" + allTest + "options" + Number(count) + "\  placeholder=" + Number(count)  + ")\ name=" + allTest + "options\>\n" +
        "        </label> " +
        //"        <button onclick=\"removeOptions(" + Number(allTest) + "," + Number(count) + ")\" class=\"btn btn-primary " + allTest + "butt\">Удалить вариант</button>\n" +
        //"<a href=\"#\" style=\"color: black\"  data-bs-toggle=\"tooltip\" data-bs-placement=\"right\" title=\"Удалить вариант\">\n" +
        "                    <svg onclick=\"removeOptions(" + Number(allTest) + "," + Number(count) + ")\"  xmlns=\"http://www.w3.org/2000/svg\" width=\"16\" height=\"16\" fill=\"currentColor\" class=\"bi bi-archive-fill ml-2 " + Number(allTest) + "butt\" viewBox=\"0 0 16 16\">\n" +
        "                        <path d=\"M12.643 15C13.979 15 15 13.845 15 12.5V5H1v7.5C1 13.845 2.021 15 3.357 15h9.286zM5.5 7h5a.5.5 0 0 1 0 1h-5a.5.5 0 0 1 0-1zM.8 1a.8.8 0 0 0-.8.8V3a.8.8 0 0 0 .8.8h14.4A.8.8 0 0 0 16 3V1.8a.8.8 0 0 0-.8-.8H.8z\"/>\n" +
        "                    </svg>\n" +
        //"                </a>" +
        "    </div>\n" +
        "\n" +
        "    <div class=\"custom-control custom-checkbox mt-2\" id=" + allTest +"Id" + (++count) + "\>\n" +
        "<input type=\"checkbox\" class=\"custom-control-input " + allTest + "input\" id=" + Number(allTest) + "customCheck" + Number(count) + "\ name=" + allTest + "check\ value=" + Number(count-1) + ">\n" +
        "        <label class=\"custom-control-label " + allTest + "opt\" id=" + allTest + "label" + count + "\ for=" + Number(allTest) + "customCheck" + Number(count) + "\>\n" +
        "        <input type=\"text\" required class=\"form-control\" id=" + allTest + "options" + Number(count) + "\ placeholder=" + Number(count)  + ")\ name=" + allTest + "options\>\n" +
        "        </label> " +
       // "        <button onclick=\"removeOptions(" + Number(allTest) + "," + Number(count) + ")\" class=\"btn btn-primary " + allTest + "butt\">Удалить вариант</button>\n" +
        //"<a href=\"#\" style=\"color: black\"  data-bs-toggle=\"tooltip\" data-bs-placement=\"right\" title=\"Удалить вариант\">\n" +
        "                    <svg onclick=\"removeOptions(" + Number(allTest) + "," + Number(count) + ")\"  xmlns=\"http://www.w3.org/2000/svg\" width=\"16\" height=\"16\" fill=\"currentColor\" class=\"bi bi-archive-fill ml-2 " + Number(allTest) + "butt\" viewBox=\"0 0 16 16\">\n" +
        "                        <path d=\"M12.643 15C13.979 15 15 13.845 15 12.5V5H1v7.5C1 13.845 2.021 15 3.357 15h9.286zM5.5 7h5a.5.5 0 0 1 0 1h-5a.5.5 0 0 1 0-1zM.8 1a.8.8 0 0 0-.8.8V3a.8.8 0 0 0 .8.8h14.4A.8.8 0 0 0 16 3V1.8a.8.8 0 0 0-.8-.8H.8z\"/>\n" +
        "                    </svg>\n" +
        //"                </a>" +
        "    </div>\n" +
        "\n" +
        "    <div class=\"custom-control custom-checkbox mt-2\" id=" + Number(allTest) + "Id" + Number(++count) + "\>\n" +
        "<input type=\"checkbox\" class=\"custom-control-input " + allTest + "input\" id=" + Number(allTest) + "customCheck" + Number(count) + "\ name=" + allTest + "check\ value=" + Number(count-1) + ">\n" +
        "        <label class=\"custom-control-label " + allTest + "opt\" id=" + allTest + "label" + count + "\ for=" + Number(allTest) + "customCheck" + Number(count) + "\>\n" +
        "        <input type=\"text\" required class=\"form-control\" id=" + allTest + "options" + Number(count) + "\ placeholder=" + Number(count)  + ")\ name=" + allTest + "options\>\n" +
        "        </label> " +
        //"        <button onclick=\"removeOptions(" + Number(allTest) + "," + Number(count) + ")\" class=\"btn btn-primary " + allTest + "butt\">Удалить вариант</button>\n" +
        //"<a href=\"#\" style=\"color: black\"  data-bs-toggle=\"tooltip\" data-bs-placement=\"right\" title=\"Удалить вариант\">\n" +
        "                    <svg onclick=\"removeOptions(" + Number(allTest) + "," + Number(count) + ")\"  xmlns=\"http://www.w3.org/2000/svg\" width=\"16\" height=\"16\" fill=\"currentColor\" class=\"bi bi-archive-fill ml-2 " + Number(allTest) + "butt\" viewBox=\"0 0 16 16\">\n" +
        "                        <path d=\"M12.643 15C13.979 15 15 13.845 15 12.5V5H1v7.5C1 13.845 2.021 15 3.357 15h9.286zM5.5 7h5a.5.5 0 0 1 0 1h-5a.5.5 0 0 1 0-1zM.8 1a.8.8 0 0 0-.8.8V3a.8.8 0 0 0 .8.8h14.4A.8.8 0 0 0 16 3V1.8a.8.8 0 0 0-.8-.8H.8z\"/>\n" +
        "                    </svg>\n" +
        //"                </a>" +
        "    </div>\n" +
        "\n" +
        "    </div>\n" +
        "\n" +
        "    </div>\n" +
        "</form>" +
        "    <div>\n" +
        "        <button onclick=\"addOptions(" + allTest + ")\" class=\"btn btn-primary mt-2\">Добавить вариант</button>\n" +
        "    </div>" /*+
        "<div id=\"deleteQuiz\">\n" +
        "        <button onclick=\"deleteTempQuiz(" + allTest + ")\" class=\"btn btn-primary mt-2\">Удалить вопрос</button>\n" +
        "    </div>"*/;
    let opt = document.getElementById("addTest");
    opt.before(div);
}


/*
<input type=\"checkbox\" class=\"custom-control-input\" id=\"customCheck" + count + "\" name=\"check\" value=" + count + ">\n" +
"        <label class=\"custom-control-label\" for=\"customCheck" + count + "\">\n" +
        "        <input type=\"text\" class=\"form-control\" id=\"options" + count + "\" placeholder=" + count  + ")\ name=\"options\">\n" +
        "        </label> " +
"        <button onclick=\"removeOptions(" + count + ")\" class=\"btn btn-primary mt-3\*/
