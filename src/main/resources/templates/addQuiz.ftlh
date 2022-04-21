<#import "parts/commonWithoutAside.ftlh" as c>
<#include "parts/security.ftlh">
<@c.page>
    <title>Добавить викторину</title>
    <script src="/static/addTest.js"></script>
    <script src="/static/addOptions.js"></script>
    <script src="/static/addQuiz.js"></script>
    <script src="/static/deletereq.js"></script>

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link rel="stylesheet"
          href="https://fonts.googleapis.com/css2?family=Heebo:wght@400;500;700&family=Roboto:wght@400;500;700&display=swap">

    <link rel="stylesheet" type="text/css"
          href="https://eduport.webestica.com/assets/vendor/font-awesome/css/all.min.css">
    <link rel="stylesheet" type="text/css"
          href="https://eduport.webestica.com/assets/vendor/bootstrap-icons/bootstrap-icons.css">
    <link rel="stylesheet" type="text/css"
          href="https://eduport.webestica.com/assets/vendor/choices/css/choices.min.css">
    <link rel="stylesheet" type="text/css" href="https://eduport.webestica.com/assets/vendor/aos/aos.css">
    <link rel="stylesheet" type="text/css"
          href="https://eduport.webestica.com/assets/vendor/glightbox/css/glightbox.css">
    <link rel="stylesheet" type="text/css" href="https://eduport.webestica.com/assets/vendor/quill/css/quill.snow.css">
    <link rel="stylesheet" type="text/css"
          href="https://eduport.webestica.com/assets/vendor/stepper/css/bs-stepper.min.css">


    <aside class="sidebar bg-light">
        <div class="container h-100">
            <#--            <div class="flex-wrap mt-5">-->
            <nav id="navbar-example3" class="navbar navbar-light bg-light px-3" aria-label="...">
                <ul class="justify-content-center pagination nav nav-pills" style="flex-wrap: wrap">
                    <li style="width: 60px" class="ml-5 mt-2 card page-item  nav-item mx-1 <#--active-->"
                        aria-current="page">
                        <a style="color: #5376fc" id="nav1" class="card-body page-link nav-link text-center"
                           href="#list-item-1">
                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"
                                 fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"
                                 stroke-linejoin="round" class="feather feather-sliders">
                                <line x1="4" y1="21" x2="4" y2="14"></line>
                                <line x1="4" y1="10" x2="4" y2="3"></line>
                                <line x1="12" y1="21" x2="12" y2="12"></line>
                                <line x1="12" y1="8" x2="12" y2="3"></line>
                                <line x1="20" y1="21" x2="20" y2="16"></line>
                                <line x1="20" y1="12" x2="20" y2="3"></line>
                                <line x1="1" y1="14" x2="7" y2="14"></line>
                                <line x1="9" y1="8" x2="15" y2="8"></line>
                                <line x1="17" y1="16" x2="23" y2="16"></line>
                            </svg>
                        </a>
                    </li>
                    <li style="width: 60px" class="ml-5 mt-2 card page-item  nav-item mx-1 <#--active-->"
                        aria-current="page">
                        <a style="color: #5376fc" id="nav1" class="card-body page-link nav-link text-center"
                           href="#list-item-1">1
                        </a>
                    </li>
                    <li style="width: 60px" class="ml-5 mt-2 card page-item  nav-item mx-1 <#--active-->"
                        aria-current="page">
                        <a style="color: #5376fc" id="nav1" class="card-body page-link nav-link text-center"
                           href="#list-item-1">2
                        </a>
                    </li>
                    <li style="width: 60px" class="ml-5 mt-2 card page-item  nav-item mx-1 <#--active-->"
                        aria-current="page">
                        <a style="color: #5376fc" id="nav1" class="card-body page-link nav-link text-center"
                           href="#list-item-1">3
                        </a>
                    </li>
                    <li aria-disabled="true" disabled style="width: 60px"
                        class="ml-5 mt-2 card page-item  nav-item mx-1 <#--active-->"
                        aria-current="page">
                        <a disabled="true" aria-disabled="true" style="color: #5376fc" id="nav1"
                           class="card-body page-link nav-link text-center"
                           href="#list-item-1">4
                        </a>
                    </li>
                </ul>
            </nav>
            <#--            </div>-->
        </div>
    </aside>

    <!-- Bootstrap JS -->
    <script src="https://eduport.webestica.com/assets/vendor/bootstrap/dist/js/bootstrap.bundle.min.js"></script>

    <!-- Vendors -->
    <script src="https://eduport.webestica.com/assets/vendor/choices/js/choices.min.js"></script>
    <script src="https://eduport.webestica.com/assets/vendor/aos/aos.js"></script>
    <script src="https://eduport.webestica.com/assets/vendor/glightbox/js/glightbox.js"></script>
    <script src="https://eduport.webestica.com/assets/vendor/quill/js/quill.min.js"></script>
    <script src="https://eduport.webestica.com/assets/vendor/stepper/js/bs-stepper.min.js"></script>

    <!-- Template Functions -->
    <script src="https://eduport.webestica.com/assets/js/functions.js"></script>

    <main class="main is-visible">
        <div class="container h-100 mt-2 ml-2 chat-body hide-scrollbar flex-1">


            <div id="Props" class="card props mt-2 card mb-3 shadow p-3 mb-5  rounded">

                <div class="card-body">
                    <div id="addTest">

                        <div id="Props">
                            <form id="checkForm0" class="checkFormCl">
                                <div class="form-group">
                                    <label for="description validationDefault01" class="form-label">Название</label>
                                    <textarea rows="1" placeholder="Напишите здесь название теста" name="description"
                                              class="form-control" id="description validationDefault01"></textarea>
                                </div>
                            </form>
                        </div>

                        <input type="hidden" id="courseId" value="${courseId?c}">


                        <div class="mt-2">Время на выполнение
                            <div class="form-check ml-4">
                                <input class="form-check-input" type="radio" name="flexRadioDefault"
                                       id="flexRadioDefault1"
                                       checked>
                                <label class="form-check-label" for="flexRadioDefault1">
                                    Неограничено
                                </label>
                            </div>
                            <div class="form-check ml-4">
                                <input class="form-check-input" type="radio" name="flexRadioDefault"
                                       id="flexRadioDefault2">
                                <label class="form-check-label" for="flexRadioDefault2">
                                    Ограничено
                                </label>
                                <label for="time"> (чч:мм)</label>
                                <input class="input-text" type="time" value="01:00" id="time">
                            </div>
                            <div>

                            </div>
                        </div>

                        <div class="mt-2">Время начала

                            <div class="form-check ml-4">
                                <input class="form-check-input" type="radio" name="flexRadioDefault2"
                                       id="flexRadioDefault3"
                                       checked>
                                <label class="form-check-label" for="flexRadioDefault3">
                                    Любое
                                </label>
                            </div>
                            <div class="form-check ml-4">
                                <input class="form-check-input" type="radio" name="flexRadioDefault2"
                                       id="flexRadioDefault4">
                                <label class="form-check-label" for="flexRadioDefault4">
                                    Заданное
                                </label>
                                <label for="time"> (чч:мм)</label>
                                <input class="time" type="datetime-local" id="startDate">
                            </div>

                        </div>
                        <div class="mt-2">Время конца

                            <div class="form-check ml-4">
                                <input class="form-check-input" type="radio" name="flexRadioDefault3"
                                       id="flexRadioDefault5"
                                       checked>
                                <label class="form-check-label" for="flexRadioDefault5">
                                    Любое
                                </label>
                            </div>
                            <div class="form-check ml-4">
                                <input class="form-check-input" type="radio" name="flexRadioDefault3"
                                       id="flexRadioDefault6">
                                <label class="form-check-label" for="flexRadioDefault6">
                                    Заданное
                                </label>
                                <label for="time"> (чч:мм)</label>
                                <input type="datetime-local" id="endDate">
                            </div>

                            <div class="mt-2">Количество попыток
                                <div class="form-check ml-4">
                                    <input class="form-check-input" type="radio" name="flexRadioDefault4"
                                           id="flexRadioDefault7"
                                           checked>
                                    <label class="form-check-label" for="flexRadioDefault7">
                                        Неограничено
                                    </label>
                                </div>
                                <div class="form-check ml-4">
                                    <input class="form-check-input" type="radio" name="flexRadioDefault4"
                                           id="flexRadioDefault8">
                                    <label class="form-check-label" for="flexRadioDefault8">
                                        Ограничено
                                    </label>
                                    <input type="text" id="attempts">
                                </div>
                                <div>

                                </div>
                            </div>

                            <div class="mt-2">Отображать ответы пользователям
                                <div class="form-check ml-4">
                                    <input class="form-check-input" type="radio" name="flexRadioDefault5"
                                           id="flexRadioDefault9"
                                           checked>
                                    <label class="form-check-label" for="flexRadioDefault9">
                                        Нет
                                    </label>
                                </div>
                                <div class="form-check ml-4">
                                    <input class="form-check-input" type="radio" name="flexRadioDefault5"
                                           id="flexRadioDefault10">
                                    <label class="form-check-label" for="flexRadioDefault10">
                                        Да
                                    </label>
                                </div>
                                <div>

                                </div>
                            </div>

                        </div>

                        <#--    <form method="get" action="/" >-->

                        <#--    </form>-->


                    </div>
                </div>
            </div>

            <#--<div class="chat-body hide-scrollbar flex-1 h-100">-->

            <div id="list-item-1" class="card mt-2 card mb-3 shadow p-3 mb-5  rounded">

                <div class="card-body">

                    <div id="1Id" class="quiz">
                        <form id="checkForm1" class="checkFormCl">


                            <div class="form-group">
                                <label for="exampleFormControlInput1 validationDefault01">Тема</label>
                                <textarea required type="text" class="form-control" id="titleID validationDefault01"
                                          placeholder="Напишите здесь тему вопроса" rows="1" name="title"></textarea>
                                <input type="hidden">
                            </div>

                            <div class="form-group">
                                <label for="exampleFormControlInput1 validationDefault01">Вопрос</label>
                                <textarea rows="1" required type="text" class="form-control"
                                          id="questionID validationDefault01"
                                          placeholder="Напишите здесь ваш вопрос" name="question"></textarea>
                            </div>


                            <div id="chooseType1">
                                <label for="exampleFormControlInput1 validationDefault01">Выберите тип вопроса</label>
                                <div class="custom-control custom-checkbox">
                                    <input onclick="document.getElementById('1optionstest').hidden = false; document.getElementById('chooseType1').hidden = true"
                                           type="checkbox"
                                           class="custom-control-input" id="choose1" name="1choose" value="0">
                                    <label class="custom-control-label 1opt validationDefault01" id="1label1"
                                           for="choose1">
                                        <input disabled value="Один или несколько ответов" required type="text"
                                               class="form-control"
                                               placeholder="1)" name="1choose">
                                    </label>
                                </div>

                                <div class="custom-control custom-checkbox mt-2">
                                    <input onclick="document.getElementById('1inputAnswer').hidden = false; document.getElementById('chooseType1').hidden = true"
                                           type="checkbox"
                                           class="custom-control-input " id="choose2" name="1choose" value="1">
                                    <label class="custom-control-label 1opt " id="1label2" for="choose2">
                                        <input disabled value="Ответ в виде строки" required type="text"
                                               class="form-control"
                                               placeholder="2)" name="1choose">
                                    </label>
                                </div>

                                <div class="custom-control custom-checkbox mt-2">
                                    <input onclick="document.getElementById('1mapAnswer').hidden = false; document.getElementById('chooseType1').hidden = true"
                                           type="checkbox"
                                           class="custom-control-input" id="choose3" name="1choose"
                                           value="2">
                                    <label class="custom-control-label 1opt" id="1label3" for="choose3">
                                        <input disabled value="Соответствие" required type="text" class="form-control"
                                               placeholder="3)"
                                               name="1choose">
                                    </label>
                                </div>
                            </div>


                            <div hidden class="options mt-2" id="1optionstest">
                                <div id="1optionstext">
                                    <label for="exampleFormControlInput1 validationDefault01">Варианты ответа</label>
                                    <div class="custom-control custom-checkbox" id="1Id1">

                                        <div class="card border-0 mt-3">
                                            <div class="card-body">

                                                <div class="row align-items-center gx-5">
                                                    <div class="col-auto">
                                                        <div class="avatar">
                                                            <span class="avatar-text">1</span>
                                                        </div>
                                                    </div>
                                                    <div class="col">
                                                        <textarea rows="1" required type="text" class="form-control"
                                                                  id="1options1 validationDefault01"
                                                                  name="1options"></textarea>
                                                    </div>
                                                    <div class="col-auto">
                                                        <input type="checkbox" class="form-check-input 1input"
                                                               id="1customCheck1"
                                                               name="1check"
                                                               value="0">
                                                    </div>
                                                    <div class="col-auto">

                                                        <a href="#" style="color: #ebeaf7" data-bs-toggle="tooltip"
                                                           data-bs-placement="right"
                                                           title="Удалить вариант">
                                                            <svg onclick="removeOptions(1,1)"
                                                                 xmlns="http://www.w3.org/2000/svg"
                                                                 width="16"
                                                                 height="16"
                                                                 fill="currentColor"
                                                                 class="bi bi-archive-fill ml-2 1butt"
                                                                 viewBox="0 0 16 16">
                                                                <path d="M12.643 15C13.979 15 15 13.845 15 12.5V5H1v7.5C1 13.845 2.021 15 3.357 15h9.286zM5.5 7h5a.5.5 0 0 1 0 1h-5a.5.5 0 0 1 0-1zM.8 1a.8.8 0 0 0-.8.8V3a.8.8 0 0 0 .8.8h14.4A.8.8 0 0 0 16 3V1.8a.8.8 0 0 0-.8-.8H.8z"/>
                                                            </svg>
                                                        </a>


                                                    </div>
                                                </div>
                                            </div>
                                        </div>


                                        <#--<a href="#" style="color: black" data-bs-toggle="tooltip"
                                           data-bs-placement="right"
                                           title="Удалить вариант">
                                            <svg onclick="removeOptions(1,1)" xmlns="http://www.w3.org/2000/svg"
                                                 width="16"
                                                 height="16"
                                                 fill="currentColor" class="bi bi-archive-fill ml-2 1butt"
                                                 viewBox="0 0 16 16">
                                                <path d="M12.643 15C13.979 15 15 13.845 15 12.5V5H1v7.5C1 13.845 2.021 15 3.357 15h9.286zM5.5 7h5a.5.5 0 0 1 0 1h-5a.5.5 0 0 1 0-1zM.8 1a.8.8 0 0 0-.8.8V3a.8.8 0 0 0 .8.8h14.4A.8.8 0 0 0 16 3V1.8a.8.8 0 0 0-.8-.8H.8z"/>
                                            </svg>
                                        </a>-->

                                    </div>


                                    <div class="custom-control custom-checkbox mt-2" id="1Id2">
                                        <div class="card border-0 mt-3">
                                            <div class="card-body">

                                                <div class="row align-items-center gx-5">
                                                    <div class="col-auto">
                                                        <div class="avatar">
                                                            <span class="avatar-text">2</span>
                                                        </div>
                                                    </div>
                                                    <div class="col">
                                                        <textarea rows="1" required type="text" class="form-control"
                                                                  id="1options2 validationDefault01"
                                                                  name="1options"></textarea>
                                                    </div>
                                                    <div class="col-auto">
                                                        <input type="checkbox" class="form-check-input 1input"
                                                               id="1customCheck2"
                                                               name="1check"
                                                               value="1">
                                                    </div>
                                                    <div class="col-auto">
                                                        <a href="#" style="color: #ebeaf7" data-bs-toggle="tooltip"
                                                           data-bs-placement="right"
                                                           title="Удалить вариант">
                                                            <svg onclick="removeOptions(1,2)"
                                                                 xmlns="http://www.w3.org/2000/svg"
                                                                 width="16"
                                                                 height="16"
                                                                 fill="currentColor"
                                                                 class="bi bi-archive-fill ml-2 1butt"
                                                                 viewBox="0 0 16 16">
                                                                <path d="M12.643 15C13.979 15 15 13.845 15 12.5V5H1v7.5C1 13.845 2.021 15 3.357 15h9.286zM5.5 7h5a.5.5 0 0 1 0 1h-5a.5.5 0 0 1 0-1zM.8 1a.8.8 0 0 0-.8.8V3a.8.8 0 0 0 .8.8h14.4A.8.8 0 0 0 16 3V1.8a.8.8 0 0 0-.8-.8H.8z"/>
                                                            </svg>
                                                        </a>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                    </div>

                                    <div class="custom-control custom-checkbox mt-2" id="1Id3">
                                        <div class="card border-0 mt-3">
                                            <div class="card-body">

                                                <div class="row align-items-center gx-5">
                                                    <div class="col-auto">
                                                        <div class="avatar">
                                                            <span class="avatar-text">3</span>
                                                        </div>
                                                    </div>

                                                    <div class="col">
                                                        <textarea rows="1" required type="text" class="form-control"
                                                                  id="1options3 validationDefault01"
                                                                  name="1options"></textarea>
                                                    </div>
                                                    <div class="col-auto">
                                                        <input type="checkbox" class="form-check-input 1input"
                                                               id="1customCheck3"
                                                               name="1check"
                                                               value="2">
                                                    </div>
                                                    <div class="col-auto">
                                                        <a href="#" style="color: #ebeaf7" data-bs-toggle="tooltip"
                                                           data-bs-placement="right"
                                                           title="Удалить вариант">
                                                            <svg onclick="removeOptions(1,3)"
                                                                 xmlns="http://www.w3.org/2000/svg"
                                                                 width="16"
                                                                 height="16"
                                                                 fill="currentColor"
                                                                 class="bi bi-archive-fill ml-2 1butt"
                                                                 viewBox="0 0 16 16">
                                                                <path d="M12.643 15C13.979 15 15 13.845 15 12.5V5H1v7.5C1 13.845 2.021 15 3.357 15h9.286zM5.5 7h5a.5.5 0 0 1 0 1h-5a.5.5 0 0 1 0-1zM.8 1a.8.8 0 0 0-.8.8V3a.8.8 0 0 0 .8.8h14.4A.8.8 0 0 0 16 3V1.8a.8.8 0 0 0-.8-.8H.8z"/>
                                                            </svg>
                                                        </a>
                                                    </div>


                                                </div>
                                            </div>
                                        </div>
                                    </div>


                                    <div id="1Id4" class="custom-control custom-checkbox mt-2">


                                        <div class="card border-0 mt-3">
                                            <div class="card-body">

                                                <div class="row align-items-center gx-5">
                                                    <div class="col-auto">
                                                        <div class="avatar">
                                                            <span class="avatar-text">4</span>
                                                        </div>
                                                    </div>

                                                    <div class="col">

                                                        <textarea rows="1" required type="text" class="form-control"
                                                                  id="1options4 validationDefault01"
                                                                  name="1options"></textarea>

                                                    </div>

                                                    <div class="col-auto">
                                                        <input type="checkbox"
                                                               class="form-check-input<#--custom-control-input--> 1input"
                                                               id="1customCheck4"
                                                               name="1check"
                                                               value="3">
                                                    </div>
                                                    <#---->

                                                    <div class="col-auto">
                                                        <a href="#" style="color: #ebeaf7" data-bs-toggle="tooltip"
                                                           data-bs-placement="right"
                                                           title="Удалить вариант">
                                                            <svg onclick="removeOptions(1,4)"
                                                                 xmlns="http://www.w3.org/2000/svg"
                                                                 width="16"
                                                                 height="16"
                                                                 fill="currentColor"
                                                                 class="bi bi-archive-fill ml-2 1butt"
                                                                 viewBox="0 0 16 16">
                                                                <path d="M12.643 15C13.979 15 15 13.845 15 12.5V5H1v7.5C1 13.845 2.021 15 3.357 15h9.286zM5.5 7h5a.5.5 0 0 1 0 1h-5a.5.5 0 0 1 0-1zM.8 1a.8.8 0 0 0-.8.8V3a.8.8 0 0 0 .8.8h14.4A.8.8 0 0 0 16 3V1.8a.8.8 0 0 0-.8-.8H.8z"/>
                                                            </svg>
                                                        </a>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                    </div>

                                    <div>
                                        <button onclick="addOptions(1)" class="btn btn-primary mt-2">Добавить
                                            вариант
                                        </button>
                                    </div>

                                </div>

                            </div>


                            <div hidden id="1inputAnswer" class="custom-control mt-3">

                                <div class="card border-0 mt-3">
                                    <div class="card-body">
                                        <div class="row align-items-center gx-5">
                                            <div class="col-auto">
                                                <a href="/course/1/tests"
                                                   class="btn btn-icon btn-primary rounded-circle">
                                                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24"
                                                         viewBox="0 0 24 24"
                                                         fill="none" stroke="currentColor" stroke-width="2"
                                                         stroke-linecap="round"
                                                         stroke-linejoin="round" class="feather feather-edit-2">
                                                        <path d="M17 3a2.828 2.828 0 1 1 4 4L7.5 20.5 2 22l1.5-5.5L17 3z"></path>
                                                    </svg>
                                                </a>
                                            </div>


                                            <div class="col">
                                                <input required type="text" class="form-control" id="stringAnswer1"
                                                       placeholder="Введите ответ">
                                            </div>
                                        </div>
                                    </div>
                                </div>

                            </div>


                            <div hidden id="1mapAnswer" class="custom-control custom-checkbox mt-2">

                                <div class="card border-0 mt-3">
                                    <div class="card-body">

                                        <div class="row align-items-center gx-5 mt-2">

                                            <div class="col">
                                                <input required type="text" class="form-control mapkey1"
                                                       id="validationDefault01"
                                                       placeholder="Напишите здесь ответ" name="text">
                                            </div>

                                            <div class="col">
                                                <input required type="text" class="form-control mapvalue1"
                                                       id="validationDefault01"
                                                       placeholder="Напишите здесь ответ" name="text">
                                            </div>
                                        </div>

                                        <div class="row align-items-center gx-5 mt-2">

                                            <div class="col">
                                                <input required type="text" class="form-control mapkey1"
                                                       id=" validationDefault01"
                                                       placeholder="Напишите здесь ответ" name="text">
                                            </div>

                                            <div class="col">
                                                <input required type="text" class="form-control mapvalue1"
                                                       id=" validationDefault01"
                                                       placeholder="Напишите здесь ответ" name="text">
                                            </div>
                                        </div>

                                        <div class="row align-items-center gx-5 mt-2">

                                            <div class="col">
                                                <input required type="text" class="form-control mapkey1"
                                                       id=" validationDefault01"
                                                       placeholder="Напишите здесь ответ" name="text">
                                            </div>

                                            <div class="col">
                                                <input required type="text" class="form-control mapvalue1"
                                                       id=" validationDefault01"
                                                       placeholder="Напишите здесь ответ" name="text">
                                            </div>
                                        </div>

                                        <div class="row align-items-center gx-5 mt-2">

                                            <div class="col">
                                                <input required type="text" class="form-control mapkey1"
                                                       id="validationDefault01"
                                                       placeholder="Напишите здесь ответ" name="text">
                                            </div>

                                            <div class="col">
                                                <input required type="text" class="form-control mapvalue1"
                                                       id=" validationDefault01"
                                                       placeholder="Напишите здесь ответ" name="text">
                                            </div>
                                        </div>


                                    </div>
                                </div>

                            </div>
                        </form>


                        <#--    <form method="get" action="/myquiz" >-->
                        <#--    </div>-->

                        <#--<div id="deleteQuiz">
                            <button onclick="deleteTempQuiz(1)" class="btn btn-primary mt-2">Удалить вопрос</button>
                        </div>-->
                    </div>
                </div>

            </div>


            <#if nowUser??>

                <div id="addQuiz">
                    <button onclick="addQuiz()" class="btn btn-primary mt-2">Добавить вопрос</button>
                </div>

                <button onclick="addTest()" type="submit" id="createTestBtn" class="btn btn-primary mb-2 mt-2">Создать
                    тест
                </button>
            <#else >

                <div id="addQuiz">
                    <button onclick="addQuiz()" class="btn btn-primary mt-2">Добавить вопрос</button>
                </div>

                <button onclick="location.href='/signin'" id="createTestBtn" type="submit"
                        class="btn btn-primary mb-2 mt-2">
                    Создать
                    тест
                </button>

            </#if>
        </div>

    </main>

</@c.page>