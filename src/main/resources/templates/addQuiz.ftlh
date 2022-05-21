<#import "parts/commonWithoutAside.ftlh" as c>
<#include "parts/security.ftlh">
<@c.page>
    <title>Добавить тест</title>


<#--
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link rel="stylesheet"
          href="https://fonts.googleapis.com/css2?family=Heebo:wght@400;500;700&family=Roboto:wght@400;500;700&display=swap">
-->



    <aside class="sidebar bg-light">
        <div class="container h-100">
            <#--            <div class="flex-wrap mt-5">-->
            <div class="card border-4 mt-3">
                <div class="card-body">
                    <div class="row align-items-center gx-0">
                        <div class="col">
                            <h4 class="mb-1">1. Укажите общую информацию и ограничения на тестирование</h4>
                        </div>
                    </div>
                </div>
            </div>
            <div class="card border-4 mt-3">
                <div class="card-body">
                    <div class="row align-items-center gx-0">
                        <div class="col">
                            <h4 class="mb-1">2. Создайте тестовые задания</h4>
                        </div>
                    </div>
                </div>
            </div>
            <div class="card border-4 mt-3">
                <div class="card-body">
                    <div class="row align-items-center gx-0">
                        <div class="col">
                            <h4 class="mb-1">3. Подтвердите создание теста кнопкой "создать тест"</h4>
                        </div>
                    </div>
                </div>
            </div>
            <#--            </div>-->
        </div>
    </aside>

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


                        <div class="row card-body">
                            <div class="col-auto mt-8">
                                <a class="btn btn-sm btn-icon btn-primary rounded-circle">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"
                                         fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"
                                         stroke-linejoin="round" class="feather feather-clock">
                                        <circle cx="12" cy="12" r="10"></circle>
                                        <polyline points="12 6 12 12 16 14"></polyline>
                                    </svg>
                                </a>
                            </div>
                            <div class="mt-2 col">Время на выполнение
                                <div class="form-check ml-4">
                                    <input onclick="document.getElementById('time').hidden=true"
                                           class="form-check-input" type="radio" name="flexRadioDefault"
                                           id="flexRadioDefault1"
                                           checked>
                                    <label class="form-check-label" for="flexRadioDefault1">
                                        Неограничено
                                    </label>
                                </div>
                                <div class="form-check ml-4">
                                    <input onclick="document.getElementById('time').hidden=false"
                                           class="form-check-input" type="radio" name="flexRadioDefault"
                                           id="flexRadioDefault2">
                                    <label class="form-check-label" for="flexRadioDefault2">
                                        Ограничено
                                    </label>
                                    <label for="time"> (чч:мм)</label>
                                    <input hidden class="form-control" type="time" value="01:00" id="time">
                                </div>
                                <div>

                                </div>
                            </div>
                            <div class="mt-2 col">Количество попыток
                                <div class="form-check ml-4">
                                    <input onclick="document.getElementById('attempts').hidden=true"
                                           class="form-check-input" type="radio" name="flexRadioDefault4"
                                           id="flexRadioDefault7"
                                           checked>
                                    <label class="form-check-label" for="flexRadioDefault7">
                                        Неограничено
                                    </label>
                                </div>
                                <div class="form-check ml-4">
                                    <input onclick="document.getElementById('attempts').hidden=false"
                                           class="form-check-input" type="radio" name="flexRadioDefault4"
                                           id="flexRadioDefault8">
                                    <label class="form-check-label" for="flexRadioDefault8">
                                        Ограничено
                                    </label>
                                    <input hidden placeholder="Введите число доступных попыток" class="form-control"
                                           type="number" id="attempts">
                                </div>
                            </div>

                        </div>

                        <div class="row card-body">
                            <div class="col-auto mt-8">
                                <a class="btn btn-sm btn-icon btn-primary rounded-circle">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"
                                         fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"
                                         stroke-linejoin="round" class="feather feather-calendar">
                                        <rect x="3" y="4" width="18" height="18" rx="2" ry="2"></rect>
                                        <line x1="16" y1="2" x2="16" y2="6"></line>
                                        <line x1="8" y1="2" x2="8" y2="6"></line>
                                        <line x1="3" y1="10" x2="21" y2="10"></line>
                                    </svg>
                                </a>
                            </div>

                            <div class="mt-2 col">Дата и время начала доступа

                                <div class="form-check ml-4">
                                    <input onclick="document.getElementById('startDate').hidden=true"
                                           class="form-check-input" type="radio" name="flexRadioDefault2"
                                           id="flexRadioDefault3"
                                           checked>
                                    <label class="form-check-label" for="flexRadioDefault3">
                                        Любое
                                    </label>
                                </div>
                                <div class="form-check ml-4">
                                    <input onclick="document.getElementById('startDate').hidden=false"
                                           class="form-check-input" type="radio" name="flexRadioDefault2"
                                           id="flexRadioDefault4">
                                    <label class="form-check-label" for="flexRadioDefault4">
                                        Заданное
                                    </label>
                                    <label for="time"> (чч:мм)</label>
                                    <input hidden class="form-control" type="datetime-local" id="startDate">
                                </div>

                            </div>
                            <div class="mt-2 col">Дата и время окончания доступа

                                <div class="form-check ml-4">
                                    <input onclick="document.getElementById('endDate').hidden=true"
                                           class="form-check-input"
                                           type="radio" name="flexRadioDefault3"
                                           id="flexRadioDefault5"
                                           checked>
                                    <label class="form-check-label" for="flexRadioDefault5">
                                        Любое
                                    </label>
                                </div>
                                <div class="form-check ml-4">
                                    <input onclick="document.getElementById('endDate').hidden=false"
                                           class="form-check-input" type="radio" name="flexRadioDefault3"
                                           id="flexRadioDefault6">
                                    <label class="form-check-label" for="flexRadioDefault6">
                                        Заданное
                                    </label>
                                    <label for="time"> (чч:мм)</label>
                                    <input hidden class="form-control" type="datetime-local" id="endDate">
                                </div>
                            </div>


                        </div>

                        <div class="row card-body">
                            <div class="col-auto mt-6">
                                <a class="btn btn-sm btn-icon btn-primary rounded-circle">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"
                                         fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"
                                         stroke-linejoin="round" class="feather feather-trending-up">
                                        <polyline points="23 6 13.5 15.5 8.5 10.5 1 18"></polyline>
                                        <polyline points="17 6 23 6 23 12"></polyline>
                                    </svg>

                                </a>
                            </div>
                            <div class="form-group mt-2 col">
                                <label for="formControlRange">Проходной балл:
                                    <span id="rangeval">50</span>
                                </label>
                                <input type="range" class="form-range" id="formControlRange"
                                       onInput="$('#rangeval').html($(this).val())">

                            </div>
                        </div>


                        <div class="row card-body">
                            <div class="col-auto mt-8">
                                <a class="btn btn-sm btn-icon btn-primary rounded-circle">
                                    ?
                                </a>
                            </div>
                            <div class="mt-2 col">Отображать ответы пользователям по окончании тестирования
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

                    </div>

                    <#--    <form method="get" action="/" >-->

                    <#--    </form>-->


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
                                <label for="exampleFormControlInput1 validationDefault01">формулировка</label>
                                <textarea rows="1" required type="text" class="form-control"
                                          id="questionID validationDefault01"
                                          placeholder="Напишите здесь формулировку вашего задания" name="question"></textarea>
                            </div>


                            <div class="form-switch mt-4 mb-4">
                                <input class="form-check-input" onchange="document.getElementById('file1').hidden=false"
                                       type="checkbox" role="switch" id="flexSwitchCheckDefault">
                                <label class="form-check-label" for="flexSwitchCheckDefault">Прикрепить файл</label>
                            </div>


                            <div hidden id="file1" class="form-group">
                                <label for="exampleFormControlInput1 validationDefault01">Файл</label>
                                <input class="form-control" type="file" name="file1">
                            </div>

                            <div id="chooseType1">
                                <label for="exampleFormControlInput1 validationDefault01">Выберите тип задания</label>
                                <div class="card-body row">
                                    <div class="col-auto mt-8">
                                        <a class="btn btn-sm btn-icon btn-primary rounded-circle">
                                            ?
                                        </a>
                                    </div>
                                    <div class="col">
                                        <div class="custom-control custom-checkbox row">

                                            <div class="col-auto">
                                                <input onclick="document.getElementById('1optionstest').hidden = false; document.getElementById('chooseType1').hidden = true"
                                                       type="checkbox"
                                                       class="form-check-input" id="choose1" name="1choose" value="0">
                                            </div>

                                            <label class="col custom-control-label 1opt validationDefault01"
                                                   id="1label1" for="choose1">
                                                Один или несколько ответов
                                            </label>

                                        </div>

                                        <div class="custom-control custom-checkbox mt-2 row">
                                            <div class="col-auto">
                                                <input onclick="document.getElementById('1inputAnswer').hidden = false; document.getElementById('chooseType1').hidden = true"
                                                       type="checkbox"
                                                       class="form-check-input" id="choose2" name="1choose" value="1">
                                            </div>
                                            <label class="col custom-control-label 1opt " id="1label2" for="choose2">
                                                Ответ в виде строки
                                            </label>
                                        </div>

                                        <div class="custom-control custom-checkbox mt-2 row">
                                            <div class="col-auto">
                                                <input onclick="document.getElementById('1mapAnswer').hidden = false; document.getElementById('chooseType1').hidden = true"
                                                       type="checkbox"
                                                       class="form-check-input" id="choose3" name="1choose"
                                                       value="2">
                                            </div>

                                            <label class="col custom-control-label 1opt" id="1label3" for="choose3">
                                                Соответствие
                                            </label>
                                        </div>
                                    </div>
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
                                        <button onclick="addOptions(1)" class="btn btn-primary mt-2">
                                            Добавить вариант
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

                <div class="row mb-5">
                    <div id="addQuiz" class="col">
                        <button onclick="addQuiz()" class="btn btn-primary mt-2">Добавить задание</button>
                    </div>

                    <div class="col text-end">
                        <button onclick="addTest()" type="submit" id="createTestBtn"
                                class="btn btn-primary mb-2 mt-2">
                            Создать тест
                        </button>
                    </div>
                </div>
            <#else >

                <div id="addQuiz">
                    <button onclick="addQuiz()" class="btn btn-primary mt-2">Добавить задание</button>
                </div>

                <div>
                    <button onclick="location.href='/signin'" id="createTestBtn" type="submit"
                            class="btn btn-primary mb-2 mt-2">
                        Создать тест
                    </button>
                </div>

            </#if>
        </div>

        <div class="modal fade" id="staticBackdropAdd" data-backdrop="static"
                <#--data-keyboard="false"--> <#--tabindex="-1"--> aria-labelledby="staticBackdropLabel"
             aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="staticBackdropLabel">Уведомление</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            Тест успешно создан
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="submit"
                                onclick="location.href='/course/${courseId?c}/test/create'" <#--onclick="restoreSession(false, ${testList.id?c})"-->
                                class="btn btn-secondary" <#--data-dismiss="modal"-->>Создать новый
                        </button>
                        <button type="submit"
                                onclick="location.href='/course/${courseId?c}/tests'" <#--onclick="restoreSession(true, ${testList.id?c})"-->
                                class="btn btn-primary">К списку тестов
                        </button>
                    </div>
                </div>
            </div>
        </div>

        <script src="/static/addTest.js"></script>
        <script src="/static/addOptions.js"></script>
        <script src="/static/addQuiz.js"></script>
        <script src="/static/deletereq.js"></script>
        <script src='https://stackpath.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.bundle.min.js'></script>
        <script src='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js'></script>

    </main>

</@c.page>