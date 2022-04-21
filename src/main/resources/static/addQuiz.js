function addQuiz() {
    let div = document.createElement("div");
    let count = document.getElementsByClassName("custom-control-label").length + 1;
    count = 1;

    let allTest = document.getElementsByName("title").length + 1


    div.id = "list-item-" + allTest;

    div.setAttribute('class', "card mt-2 card mb-3 shadow p-3 mb-5  rounded")
    //div.setAttribute('class', "quiz");
    //console.log(allTest.length);


    div.innerHTML = "<div class=\"card-body\">\n" +
        "\n" +
        "                    <div id='" + allTest + "Id' class=\"quiz\">\n" +
        "<form id=\"checkForm" + Number(allTest) + "\" class='checkFormCl'><div class=\"form-group pt-5\">\n" +
        "        <label for=\"exampleFormControlInput" + allTest + "\">Тема</label>\n" +
        "        <input type=\"text\" class=\"form-control\" required id=\"titleID\" placeholder=\"Напишите здесь тему вопроса из викторины\" name=\"title\">\n" +
        "    </div>\n" +
        "\n" +
        "    <div class=\"form-group\">\n" +
        "        <label for=\"exampleFormControlInput1\">Вопрос</label>\n" +
        "        <input type=\"text\" class=\"form-control\" required id=\"questionID\" placeholder=\"Напишите здесь ваш вопрос\"  name=\"question\">\n" +
        "    </div>\n" +
        "\n" +
        "<div id='chooseType" + allTest + "'>\n" +
        "                    <label for=\"exampleFormControlInput1 validationDefault01\">Выберите тип вопроса</label>\n" +
        "                    <div class=\"custom-control custom-checkbox\">\n" +
        "                        <input onclick=\"document.getElementById('" + allTest + "optionstest').hidden = false;  document.getElementById('chooseType" + allTest + "').hidden = true\" type=\"checkbox\"\n" +
        "                               class=\"custom-control-input 1input\" id=\"choose1\" name=\"1choose\" value=\"0\">\n" +
        "                        <label class=\"custom-control-label 1opt validationDefault01\" id=\"1label1\" for=\"choose1\">\n" +
        "                            <input disabled value=\"Один или несколько ответов\" required type=\"text\" class=\"form-control\"\n" +
        "                                   placeholder=\"1)\" name=\"1choose\">\n" +
        "                        </label>\n" +
        "                    </div>\n" +
        "\n" +
        "                    <div class=\"custom-control custom-checkbox mt-2\">\n" +
        "                        <input onclick=\"document.getElementById('" + allTest + "inputAnswer').hidden = false;  document.getElementById('chooseType" + allTest + "').hidden = true\" type=\"checkbox\"\n" +
        "                               class=\"custom-control-input 1input\" id=\"choose2\" name=\"1choose\" value=\"1\">\n" +
        "                        <label class=\"custom-control-label 1opt \" id=\"1label2\" for=\"choose2\">\n" +
        "                            <input disabled value=\"Ответ в виде строки\" required type=\"text\" class=\"form-control\"\n" +
        "                                   placeholder=\"2)\" name=\"1choose\">\n" +
        "                        </label>\n" +
        "                    </div>\n" +
        "\n" +
        "                    <div class=\"custom-control custom-checkbox mt-2\">\n" +
        "                        <input onclick=\"document.getElementById('" + allTest + "mapAnswer').hidden = false;  document.getElementById('chooseType" + allTest + "').hidden = true\" type=\"checkbox\"\n" +
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
        "<div class=\"card border-0 mt-3\">\n" +
        "                                            <div class=\"card-body\">\n" +
        "\n" +
        "                                                <div class=\"row align-items-center gx-5\">\n" +
        "                                                    <div class=\"col-auto\">\n" +
        "                                                        <div class=\"avatar\">\n" +
        "                                                            <span class=\"avatar-text\">" + count + "</span>\n" +
        "                                                        </div>\n" +
        "                                                    </div>\n" +
        "                                                    <div class=\"col\">\n" +
        "                                                        <input required type=\"text\" class=\"form-control\"\n" +
        "                                                               id='" + allTest + "options" + Number(count) + "'" +
        "                                                               name='" + allTest + "options'>\n" +
        "                                                    </div>\n" +
        "                                                    <div class=\"col-auto\">\n" +
        "                                                        <input type=\"checkbox\" class=\"form-check-input " + allTest + "input\"\n" +
        "                                                               id=" + Number(allTest) + "customCheck" + Number(count) + "\ name=" + allTest + "check\ value=" + Number(count - 1) + ">\n" +
        "                                                    </div>\n" +
        "                                                    <div class=\"col-auto\">\n" +
        "\n" +
        "                                                        <a href=\"#\" style=\"color: #ebeaf7\" data-bs-toggle=\"tooltip\"\n" +
        "                                                           data-bs-placement=\"right\"\n" +
        "                                                           title=\"Удалить вариант\">\n" +
        "                                                            <svg onclick=\"removeOptions(" + Number(allTest) + "," + Number(count) + ")\"\n" +
        "                                                                 xmlns=\"http://www.w3.org/2000/svg\"\n" +
        "                                                                 width=\"16\"\n" +
        "                                                                 height=\"16\"\n" +
        "                                                                 fill=\"currentColor\"\n" +
        "                                                                 class=\"bi bi-archive-fill ml-2 " + Number(allTest) + "butt\"\n" +
        "                                                                 viewBox=\"0 0 16 16\">\n" +
        "                                                                <path d=\"M12.643 15C13.979 15 15 13.845 15 12.5V5H1v7.5C1 13.845 2.021 15 3.357 15h9.286zM5.5 7h5a.5.5 0 0 1 0 1h-5a.5.5 0 0 1 0-1zM.8 1a.8.8 0 0 0-.8.8V3a.8.8 0 0 0 .8.8h14.4A.8.8 0 0 0 16 3V1.8a.8.8 0 0 0-.8-.8H.8z\"/>\n" +
        "                                                            </svg>\n" +
        "                                                        </a>\n" +
        "\n" +
        "\n" +
        "                                                    </div>\n" +
        "                                                </div>\n" +
        "                                            </div>\n" +
        "                                        </div>" +
        "\n" +
        "    <div class=\"custom-control custom-checkbox mt-2\" id=" + allTest + "Id" + (++count) + "\>\n" +
        "<div class=\"card border-0 mt-3\">\n" +
        "                                            <div class=\"card-body\">\n" +
        "\n" +
        "                                                <div class=\"row align-items-center gx-5\">\n" +
        "                                                    <div class=\"col-auto\">\n" +
        "                                                        <div class=\"avatar\">\n" +
        "                                                            <span class=\"avatar-text\">" + count + "</span>\n" +
        "                                                        </div>\n" +
        "                                                    </div>\n" +
        "                                                    <div class=\"col\">\n" +
        "                                                        <input required type=\"text\" class=\"form-control\"\n" +
        "                                                               id='" + allTest + "options" + Number(count) + "'" +
        "                                                               name='" + allTest + "options'>\n" +
        "                                                    </div>\n" +
        "                                                    <div class=\"col-auto\">\n" +
        "                                                        <input type=\"checkbox\" class=\"form-check-input " + allTest + "input\"\n" +
        "                                                               id=" + Number(allTest) + "customCheck" + Number(count) + "\ name=" + allTest + "check\ value=" + Number(count - 1) + ">\n" +
        "                                                    </div>\n" +
        "                                                    <div class=\"col-auto\">\n" +
        "\n" +
        "                                                        <a href=\"#\" style=\"color: #ebeaf7\" data-bs-toggle=\"tooltip\"\n" +
        "                                                           data-bs-placement=\"right\"\n" +
        "                                                           title=\"Удалить вариант\">\n" +
        "                                                            <svg onclick=\"removeOptions(" + Number(allTest) + "," + Number(count) + ")\"\n" +
        "                                                                 xmlns=\"http://www.w3.org/2000/svg\"\n" +
        "                                                                 width=\"16\"\n" +
        "                                                                 height=\"16\"\n" +
        "                                                                 fill=\"currentColor\"\n" +
        "                                                                 class=\"bi bi-archive-fill ml-2 " + Number(allTest) + "butt\"\n" +
        "                                                                 viewBox=\"0 0 16 16\">\n" +
        "                                                                <path d=\"M12.643 15C13.979 15 15 13.845 15 12.5V5H1v7.5C1 13.845 2.021 15 3.357 15h9.286zM5.5 7h5a.5.5 0 0 1 0 1h-5a.5.5 0 0 1 0-1zM.8 1a.8.8 0 0 0-.8.8V3a.8.8 0 0 0 .8.8h14.4A.8.8 0 0 0 16 3V1.8a.8.8 0 0 0-.8-.8H.8z\"/>\n" +
        "                                                            </svg>\n" +
        "                                                        </a>\n" +
        "\n" +
        "\n" +
        "                                                    </div>\n" +
        "                                                </div>\n" +
        "                                            </div>\n" +
        "    </div>\n" +
        "\n" +
        "    <div class=\"custom-control custom-checkbox mt-2\" id=" + allTest + "Id" + (++count) + "\>\n" +
        "<div class=\"card border-0 mt-3\">\n" +
        "                                            <div class=\"card-body\">\n" +
        "\n" +
        "                                                <div class=\"row align-items-center gx-5\">\n" +
        "                                                    <div class=\"col-auto\">\n" +
        "                                                        <div class=\"avatar\">\n" +
        "                                                            <span class=\"avatar-text\">" + count + "</span>\n" +
        "                                                        </div>\n" +
        "                                                    </div>\n" +
        "                                                    <div class=\"col\">\n" +
        "                                                        <input required type=\"text\" class=\"form-control\"\n" +
        "                                                               id='" + allTest + "options" + Number(count) + "'" +
        "                                                               name='" + allTest + "options'>\n" +
        "                                                    </div>\n" +
        "                                                    <div class=\"col-auto\">\n" +
        "                                                        <input type=\"checkbox\" class=\"form-check-input " + allTest + "input\"\n" +
        "                                                               id=" + Number(allTest) + "customCheck" + Number(count) + "\ name=" + allTest + "check\ value=" + Number(count - 1) + ">\n" +
        "                                                    </div>\n" +
        "                                                    <div class=\"col-auto\">\n" +
        "\n" +
        "                                                        <a href=\"#\" style=\"color: #ebeaf7\" data-bs-toggle=\"tooltip\"\n" +
        "                                                           data-bs-placement=\"right\"\n" +
        "                                                           title=\"Удалить вариант\">\n" +
        "                                                            <svg onclick=\"removeOptions(" + Number(allTest) + "," + Number(count) + ")\"\n" +
        "                                                                 xmlns=\"http://www.w3.org/2000/svg\"\n" +
        "                                                                 width=\"16\"\n" +
        "                                                                 height=\"16\"\n" +
        "                                                                 fill=\"currentColor\"\n" +
        "                                                                 class=\"bi bi-archive-fill ml-2 " + Number(allTest) + "butt\"\n" +
        "                                                                 viewBox=\"0 0 16 16\">\n" +
        "                                                                <path d=\"M12.643 15C13.979 15 15 13.845 15 12.5V5H1v7.5C1 13.845 2.021 15 3.357 15h9.286zM5.5 7h5a.5.5 0 0 1 0 1h-5a.5.5 0 0 1 0-1zM.8 1a.8.8 0 0 0-.8.8V3a.8.8 0 0 0 .8.8h14.4A.8.8 0 0 0 16 3V1.8a.8.8 0 0 0-.8-.8H.8z\"/>\n" +
        "                                                            </svg>\n" +
        "                                                        </a>\n" +
        "\n" +
        "\n" +
        "                                                    </div>\n" +
        "                                                </div>\n" +
        "                                            </div>\n" +
        "    </div>\n" +
        "\n" +
        "    <div class=\"custom-control custom-checkbox mt-2\" id=" + Number(allTest) + "Id" + Number(++count) + "\>\n" +
        "<div class=\"card border-0 mt-3\">\n" +
        "                                            <div class=\"card-body\">\n" +
        "\n" +
        "                                                <div class=\"row align-items-center gx-5\">\n" +
        "                                                    <div class=\"col-auto\">\n" +
        "                                                        <div class=\"avatar\">\n" +
        "                                                            <span class=\"avatar-text\">" + count + "</span>\n" +
        "                                                        </div>\n" +
        "                                                    </div>\n" +
        "                                                    <div class=\"col\">\n" +
        "                                                        <input required type=\"text\" class=\"form-control\"\n" +
        "                                                               id='" + allTest + "options" + Number(count) + "'" +
        "                                                               name='" + allTest + "options'>\n" +
        "                                                    </div>\n" +
        "                                                    <div class=\"col-auto\">\n" +
        "                                                        <input type=\"checkbox\" class=\"form-check-input " + allTest + "input\"\n" +
        "                                                               id=" + Number(allTest) + "customCheck" + Number(count) + "\ name=" + allTest + "check\ value=" + Number(count - 1) + ">\n" +
        "                                                    </div>\n" +
        "                                                    <div class=\"col-auto\">\n" +
        "\n" +
        "                                                        <a href=\"#\" style=\"color: #ebeaf7\" data-bs-toggle=\"tooltip\"\n" +
        "                                                           data-bs-placement=\"right\"\n" +
        "                                                           title=\"Удалить вариант\">\n" +
        "                                                            <svg onclick=\"removeOptions(" + Number(allTest) + "," + Number(count) + ")\"\n" +
        "                                                                 xmlns=\"http://www.w3.org/2000/svg\"\n" +
        "                                                                 width=\"16\"\n" +
        "                                                                 height=\"16\"\n" +
        "                                                                 fill=\"currentColor\"\n" +
        "                                                                 class=\"bi bi-archive-fill ml-2 " + Number(allTest) + "butt\"\n" +
        "                                                                 viewBox=\"0 0 16 16\">\n" +
        "                                                                <path d=\"M12.643 15C13.979 15 15 13.845 15 12.5V5H1v7.5C1 13.845 2.021 15 3.357 15h9.286zM5.5 7h5a.5.5 0 0 1 0 1h-5a.5.5 0 0 1 0-1zM.8 1a.8.8 0 0 0-.8.8V3a.8.8 0 0 0 .8.8h14.4A.8.8 0 0 0 16 3V1.8a.8.8 0 0 0-.8-.8H.8z\"/>\n" +
        "                                                            </svg>\n" +
        "                                                        </a>\n" +
        "\n" +
        "\n" +
        "                                                    </div>\n" +
        "                                                </div>\n" +
        "                                            </div>\n" +
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

        "\n" +
        "                </div></div>" +
        "</div>" +
        " <div hidden id='" + allTest + "inputAnswer' class=\"custom-control mt-3\">\n" +
        "<div class=\"card border-0 mt-3\">\n" +
        "                                    <div class=\"card-body\">\n" +
        "                                        <div class=\"row align-items-center gx-5\">\n" +
        "                                            <div class=\"col-auto\">\n" +
        "                                                <a href=\"/course/1/tests\"\n" +
        "                                                   class=\"btn btn-icon btn-primary rounded-circle\">" +
        "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"24\" height=\"24\"\n" +
        "                                                         viewBox=\"0 0 24 24\"\n" +
        "                                                         fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\"\n" +
        "                                                         stroke-linecap=\"round\"\n" +
        "                                                         stroke-linejoin=\"round\" class=\"feather feather-edit-2\">\n" +
        "                                                        <path d=\"M17 3a2.828 2.828 0 1 1 4 4L7.5 20.5 2 22l1.5-5.5L17 3z\"></path>\n" +
        "                                                    </svg></a></div>" +
        "<div class=\"col\">" +
        "\n" +
        "                    <input required type=\"text\" class=\"form-control\" id='stringAnswer" + allTest + "' placeholder=\"Введите ответ\">\n" +
        "\n" +
        "                </div></div></div></div></div>\n" +
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
        "                    </div>\n";
    let opt = document.getElementById("addQuiz");
    opt.before(div);
}
