function addQuiz() {
    let div = document.createElement("div");
    let count = document.getElementsByClassName("custom-control-label").length + 1;
    count = 1;

    let allTest = document.getElementsByName("title").length + 1
    div.id = allTest + "Id";
    div.setAttribute('class', "quiz");
    //console.log(allTest.length);
    div.innerHTML = "<form id=\"checkForm" + Number(allTest) + "\" class='checkFormCl'><div class=\"form-group pt-5\">\n" +
        "        <label for=\"exampleFormControlInput" + allTest + "\">Тема</label>\n" +
        "        <input type=\"text\" class=\"form-control\" required id=\"titleID\" placeholder=\"Напишите здесь тему вопроса из викторины\" name=\"title\">\n" +
        "    </div>\n" +
        "\n" +
        "    <div class=\"form-group\">\n" +
        "        <label for=\"exampleFormControlInput1\">Вопрос</label>\n" +
        "        <input type=\"text\" class=\"form-control\" required id=\"questionID\" placeholder=\"Напишите здесь ваш вопрос\"  name=\"question\">\n" +
        "    </div>\n" +
        "\n" +
        "<div>\n" +
        "                    <label for=\"exampleFormControlInput1 validationDefault01\">Выберите тип вопроса</label>\n" +
        "                    <div class=\"custom-control custom-checkbox\">\n" +
        "                        <input onclick=\"document.getElementById('" + allTest + "optionstest').hidden = false\" type=\"checkbox\"\n" +
        "                               class=\"custom-control-input 1input\" id=\"choose1\" name=\"1choose\" value=\"0\">\n" +
        "                        <label class=\"custom-control-label 1opt validationDefault01\" id=\"1label1\" for=\"choose1\">\n" +
        "                            <input disabled value=\"Один или несколько ответов\" required type=\"text\" class=\"form-control\"\n" +
        "                                   placeholder=\"1)\" name=\"1choose\">\n" +
        "                        </label>\n" +
        "                    </div>\n" +
        "\n" +
        "                    <div class=\"custom-control custom-checkbox mt-2\">\n" +
        "                        <input onclick=\"document.getElementById('" + allTest + "inputAnswer').hidden = false\" type=\"checkbox\"\n" +
        "                               class=\"custom-control-input 1input\" id=\"choose2\" name=\"1choose\" value=\"1\">\n" +
        "                        <label class=\"custom-control-label 1opt \" id=\"1label2\" for=\"choose2\">\n" +
        "                            <input disabled value=\"Ответ в виде строки\" required type=\"text\" class=\"form-control\"\n" +
        "                                   placeholder=\"2)\" name=\"1choose\">\n" +
        "                        </label>\n" +
        "                    </div>\n" +
        "\n" +
        "                    <div class=\"custom-control custom-checkbox mt-2\">\n" +
        "                        <input onclick=\"document.getElementById('" + allTest + "mapAnswer').hidden = false\" type=\"checkbox\"\n" +
        "                               class=\"custom-control-input 1input\" id=\"choose3\" name=\"1choose\"\n" +
        "                               value=\"2\">\n" +
        "                        <label class=\"custom-control-label 1opt\" id=\"1label3\" for=\"choose3\">\n" +
        "                            <input disabled value=\"Соответствие\" required type=\"text\" class=\"form-control\"\n" +
        "                                   placeholder=\"3)\"\n" +
        "                                   name=\"1choose\">\n" +
        "                        </label>\n" +
        "                    </div>\n" +
        "                </div>" +
        "    <div hidden class=\"options\" id=\"" + allTest + "optionstest\">\n" +
        " <div id='" + allTest + "optionstext'" +
        "        <label for=\"exampleFormControlInput1\">Варианты ответа</label>\n" +
        "    <div class=\"custom-control custom-checkbox\" id=" + allTest + "Id" + count + "\>\n" +
        "<input type=\"checkbox\" class=\"custom-control-input " + allTest + "input\" id=" + Number(allTest) + "customCheck" + Number(count) + "\ name=" + allTest + "check\ value=" + Number(count - 1) + ">\n" +
        "        <label class=\"custom-control-label " + allTest + "opt\" id=" + allTest + "label" + count + "\ for=" + Number(allTest) + "customCheck" + Number(count) + "\>\n" +
        "        <input type=\"text\" required class=\"form-control\" id=" + allTest + "options" + Number(count) + "\ placeholder=" + Number(count) + ")\ name=" + allTest + "options\>\n" +
        "        </label> " +
        "                    <svg onclick=\"removeOptions(" + Number(allTest) + "," + Number(count) + ")\"  xmlns=\"http://www.w3.org/2000/svg\" width=\"16\" height=\"16\" fill=\"currentColor\" class=\"bi bi-archive-fill ml-2 " + Number(allTest) + "butt\" viewBox=\"0 0 16 16\">\n" +
        "                        <path d=\"M12.643 15C13.979 15 15 13.845 15 12.5V5H1v7.5C1 13.845 2.021 15 3.357 15h9.286zM5.5 7h5a.5.5 0 0 1 0 1h-5a.5.5 0 0 1 0-1zM.8 1a.8.8 0 0 0-.8.8V3a.8.8 0 0 0 .8.8h14.4A.8.8 0 0 0 16 3V1.8a.8.8 0 0 0-.8-.8H.8z\"/>\n" +
        "                    </svg>\n" +
        "\n" +
        "    </div>\n" +
        "\n" +
        "    <div class=\"custom-control custom-checkbox mt-2\" id=" + allTest + "Id" + (++count) + "\>\n" +
        "<input type=\"checkbox\" class=\"custom-control-input " + allTest + "input\" id=" + Number(allTest) + "customCheck" + Number(count) + "\ name=" + allTest + "check\ value=" + Number(count - 1) + ">\n" +
        "        <label class=\"custom-control-label " + allTest + "opt\" id=" + allTest + "label" + count + "\  for=" + Number(allTest) + "customCheck" + Number(count) + "\>\n" +
        "        <input type=\"text\" required class=\"form-control\" id=" + allTest + "options" + Number(count) + "\  placeholder=" + Number(count) + ")\ name=" + allTest + "options\>\n" +
        "        </label> " +
        "                    <svg onclick=\"removeOptions(" + Number(allTest) + "," + Number(count) + ")\"  xmlns=\"http://www.w3.org/2000/svg\" width=\"16\" height=\"16\" fill=\"currentColor\" class=\"bi bi-archive-fill ml-2 " + Number(allTest) + "butt\" viewBox=\"0 0 16 16\">\n" +
        "                        <path d=\"M12.643 15C13.979 15 15 13.845 15 12.5V5H1v7.5C1 13.845 2.021 15 3.357 15h9.286zM5.5 7h5a.5.5 0 0 1 0 1h-5a.5.5 0 0 1 0-1zM.8 1a.8.8 0 0 0-.8.8V3a.8.8 0 0 0 .8.8h14.4A.8.8 0 0 0 16 3V1.8a.8.8 0 0 0-.8-.8H.8z\"/>\n" +
        "                    </svg>\n" +
        "    </div>\n" +
        "\n" +
        "    <div class=\"custom-control custom-checkbox mt-2\" id=" + allTest + "Id" + (++count) + "\>\n" +
        "<input type=\"checkbox\" class=\"custom-control-input " + allTest + "input\" id=" + Number(allTest) + "customCheck" + Number(count) + "\ name=" + allTest + "check\ value=" + Number(count - 1) + ">\n" +
        "        <label class=\"custom-control-label " + allTest + "opt\" id=" + allTest + "label" + count + "\ for=" + Number(allTest) + "customCheck" + Number(count) + "\>\n" +
        "        <input type=\"text\" required class=\"form-control\" id=" + allTest + "options" + Number(count) + "\ placeholder=" + Number(count) + ")\ name=" + allTest + "options\>\n" +
        "        </label> " +
        "                    <svg onclick=\"removeOptions(" + Number(allTest) + "," + Number(count) + ")\"  xmlns=\"http://www.w3.org/2000/svg\" width=\"16\" height=\"16\" fill=\"currentColor\" class=\"bi bi-archive-fill ml-2 " + Number(allTest) + "butt\" viewBox=\"0 0 16 16\">\n" +
        "                        <path d=\"M12.643 15C13.979 15 15 13.845 15 12.5V5H1v7.5C1 13.845 2.021 15 3.357 15h9.286zM5.5 7h5a.5.5 0 0 1 0 1h-5a.5.5 0 0 1 0-1zM.8 1a.8.8 0 0 0-.8.8V3a.8.8 0 0 0 .8.8h14.4A.8.8 0 0 0 16 3V1.8a.8.8 0 0 0-.8-.8H.8z\"/>\n" +
        "                    </svg>\n" +
        "    </div>\n" +
        "\n" +
        "    <div class=\"custom-control custom-checkbox mt-2\" id=" + Number(allTest) + "Id" + Number(++count) + "\>\n" +
        "<input type=\"checkbox\" class=\"custom-control-input " + allTest + "input\" id=" + Number(allTest) + "customCheck" + Number(count) + "\ name=" + allTest + "check\ value=" + Number(count - 1) + ">\n" +
        "        <label class=\"custom-control-label " + allTest + "opt\" id=" + allTest + "label" + count + "\ for=" + Number(allTest) + "customCheck" + Number(count) + "\>\n" +
        "        <input type=\"text\" required class=\"form-control\" id=" + allTest + "options" + Number(count) + "\ placeholder=" + Number(count) + ")\ name=" + allTest + "options\>\n" +
        "        </label> " +
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
        "</div>" +
        "    <div>\n" +
        "        <button onclick=\"addOptions(" + allTest + ")\" class=\"btn btn-primary mt-2\">Добавить вариант</button>\n" +
        "    </div>" +
        " <div hidden id='" + allTest + "inputAnswer' class=\"custom-control mt-3\">\n" +
        "\n" +
        "                    <input required type=\"text\" class=\"form-control\" id='stringAnswer" + allTest + "' placeholder=\"Введите ответ\">\n" +
        "\n" +
        "                </div>\n" +
        "\n" +
        "\n" +
        "                <div hidden id='" + allTest + "mapAnswer' class=\"custom-control custom-checkbox mt-2\">\n" +
        "\n" +
        "                    <div class=\"card border-0 mt-3\">\n" +
        "                        <div class=\"card-body\">\n" +
        "\n" +
        "                            <div class=\"row align-items-center gx-5 mt-2\">\n" +
        "\n" +
        "                                <div class=\"col\">\n" +
        "                                    <input required type=\"text\" class='form-control mapkey" + allTest + "' id=\"validationDefault01\"\n" +
        "                                           placeholder=\"Напишите здесь ответ\" name=\"text\">\n" +
        "                                </div>\n" +
        "\n" +
        "                                <div class=\"col\">\n" +
        "                                    <input required type=\"text\" class='form-control mapvalue" + allTest + "' id=\"validationDefault01\"\n" +
        "                                           placeholder=\"Напишите здесь ответ\" name=\"text\">\n" +
        "                                </div>\n" +
        "                            </div>\n" +
        "\n" +
        "                            <div class=\"row align-items-center gx-5 mt-2\">\n" +
        "\n" +
        "                                <div class=\"col\">\n" +
        "                                    <input required type=\"text\" class='form-control mapkey" + allTest + "' id=\" validationDefault01\"\n" +
        "                                           placeholder=\"Напишите здесь ответ\" name=\"text\">\n" +
        "                                </div>\n" +
        "\n" +
        "                                <div class=\"col\">\n" +
        "                                    <input required type=\"text\" class='form-control mapvalue" + allTest + "' id=\" validationDefault01\"\n" +
        "                                           placeholder=\"Напишите здесь ответ\" name=\"text\">\n" +
        "                                </div>\n" +
        "                            </div>\n" +
        "\n" +
        "                            <div class=\"row align-items-center gx-5 mt-2\">\n" +
        "\n" +
        "                                <div class=\"col\">\n" +
        "                                    <input required type=\"text\" class='form-control mapkey" + allTest + "' id=\" validationDefault01\"\n" +
        "                                           placeholder=\"Напишите здесь ответ\" name=\"text\">\n" +
        "                                </div>\n" +
        "\n" +
        "                                <div class=\"col\">\n" +
        "                                    <input required type=\"text\" class='form-control mapvalue" + allTest + "' id=\" validationDefault01\"\n" +
        "                                           placeholder=\"Напишите здесь ответ\" name=\"text\">\n" +
        "                                </div>\n" +
        "                            </div>\n" +
        "\n" +
        "                            <div class=\"row align-items-center gx-5 mt-2\">\n" +
        "\n" +
        "                                <div class=\"col\">\n" +
        "                                    <input required type=\"text\" class='form-control mapkey" + allTest + "' id=\"validationDefault01\"\n" +
        "                                           placeholder=\"Напишите здесь ответ\" name=\"text\">\n" +
        "                                </div>\n" +
        "\n" +
        "                                <div class=\"col\">\n" +
        "                                    <input required type=\"text\" class='form-control mapvalue" + allTest + "' id=\" validationDefault01\"\n" +
        "                                           placeholder=\"Напишите здесь ответ\" name=\"text\">\n" +
        "                                </div>\n" +
        "                            </div>\n" +
        "\n" +
        "\n" +
        "                        </div>\n" +
        "                    </div>\n" +
        "\n" +
        "                </div>"
    let opt = document.getElementById("addTest");
    opt.before(div);
}
