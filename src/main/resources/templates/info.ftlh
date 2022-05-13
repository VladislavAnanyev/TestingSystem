<#import "parts/commonWithoutAside.ftlh" as c>
<@c.page>

    <aside class="sidebar bg-light">
        <div class="container h-100">

            <div class="card border-4 mt-3">
                <div class="card-body">
                    <div class="row align-items-center gx-0">
                        <div class="col">
                            <h4 class="mb-1 dropdown">Информация:
                                <#if !RequestParameters.groupId??>по всем группам

                                <#else>
                                    группа №${selectedGroup.groupName}
                                </#if>

                            </h4>

                        </div>
                        <div class="col-auto">
                            <a class="btn btn-sm btn-icon btn-primary rounded-circle dropdown" data-bs-toggle="dropdown"
                               aria-expanded="false">
                                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"
                                     fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"
                                     stroke-linejoin="round" class="feather feather-edit-2">
                                    <path d="M17 3a2.828 2.828 0 1 1 4 4L7.5 20.5 2 22l1.5-5.5L17 3z"></path>
                                </svg>
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                                <#list groups as group>
                                    <li><a class="dropdown-item"
                                           href="?groupId=${group.groupId?c}">Группа №: ${group.groupName}</a></li>
                                </#list>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>

            <div class="card border-4 mt-3">
                <div class="card-body">
                    <div class="row align-items-center gx-0">
                        <div class="col">
                            <h4 class="mb-1">Самый трудный вопрос: №<#if min??>${min}</#if></h4>
                        </div>
                        <div class="col-auto">
                            <a class="btn btn-sm btn-icon btn-primary rounded-circle">
                                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"
                                     fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"
                                     stroke-linejoin="round" class="feather feather-trending-down">
                                    <polyline points="23 18 13.5 8.5 8.5 13.5 1 6"></polyline>
                                    <polyline points="17 18 23 18 23 12"></polyline>
                                </svg>
                            </a>
                        </div>
                    </div>
                </div>
            </div>

            <div class="card border-4 mt-3">
                <div class="card-body">
                    <div class="row align-items-center gx-0">
                        <div class="col">
                            <h4 class="mb-1">Самый лёгкий вопрос: №<#if max??>${max}</#if></h4>
                        </div>
                        <div class="col-auto">
                            <a class="btn btn-sm btn-icon btn-primary rounded-circle">
                                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"
                                     fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"
                                     stroke-linejoin="round" class="feather feather-trending-up">
                                    <polyline points="23 6 13.5 15.5 8.5 10.5 1 18"></polyline>
                                    <polyline points="17 6 23 6 23 12"></polyline>
                                </svg>
                            </a>
                        </div>
                    </div>
                </div>
            </div>

            <div class="card border-4 mt-3">
                <div class="card-body">
                    <div class="row align-items-center gx-0">
                        <div class="col">
                            <h4 class="mb-1">Показать тест</h4>
                        </div>
                        <div class="col-auto">
                            <a data-bs-toggle="modal" data-bs-target="#modal-test"
                               class="btn btn-sm btn-icon btn-primary rounded-circle">
                                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"
                                     fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"
                                     stroke-linejoin="round" class="feather feather-zoom-in">
                                    <circle cx="11" cy="11" r="8"></circle>
                                    <line x1="21" y1="21" x2="16.65" y2="16.65"></line>
                                    <line x1="11" y1="8" x2="11" y2="14"></line>
                                    <line x1="8" y1="11" x2="14" y2="11"></line>
                                </svg>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </aside>


    <main class="main is-visible">
        <div class="container h-100 mt-4 ml-2 chat-body hide-scrollbar flex-1 scrollspy-example" xmlns="">

            <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>

            <div class="row">
                <div class="col">
                    <div class="card mb-3 shadow p-3 mb-5 bg-white rounded">
                        <div class="card-body">

                            <div id="top_x_div" style="/*height: 278px*/" class="table-responsive"></div>
                        </div>
                    </div>
                </div>
                <div class="col">
                    <div class="card mb-3 shadow p-3 mb-5 bg-white rounded">
                        <div class="card-body">

                            <div id="top_time_div" style="/*height: 278px*/" class="table-responsive"></div>
                        </div>
                    </div>
                </div>
            </div>

            <script type="text/javascript">
                google.charts.load('current', {'packages': ['bar']});
                google.charts.setOnLoadCallback(drawStuff);

                //console.log("")
                function drawStuff() {
                    var data = new google.visualization.arrayToDataTable([
                        ['Тема', 'Процент'],
                        <#if chart??>
                        <#list chart?values as chartd>
                        ['${chartd_index + 1}' + "", ${chartd?c}],
                        </#list>
                        </#if>
                        /*["Knight to King 3 (Nf3)", 12],
                        ["Queen's bishop pawn (c4)", 10],
                        ['Other', 3]*/
                    ]);

                    var options = {
                        width: 300,
                        legend: {position: 'none'},
                        chart: {
                            title: 'Средний процент выполнения',
                            subtitle: 'По каждому из вопросов'
                        },
                        axes: {
                            x: {
                                0: {side: 'top', label: 'Вопросы'} // Top x-axis.
                            }
                        },
                        bar: {groupWidth: "90%"}
                    };

                    var chart = new google.charts.Bar(document.getElementById('top_x_div'));
                    // Convert the Classic options to Material options.
                    chart.draw(data, google.charts.Bar.convertOptions(options));
                };

            </script>

            <script>
                google.charts.load('current', {'packages': ['bar']});
                google.charts.setOnLoadCallback(drawStuff);

                //console.log("")
                function drawStuff() {
                    var data = new google.visualization.arrayToDataTable([
                        ['№ вопроса', 'Время в секундах'],
                        <#if timeChart??>
                        <#list timeChart?values as chartTime>
                        <#if chartTime??>
                        ['${chartTime_index + 1}' + "", ${chartTime?c}],
                        <#else >
                        ['${chartTime_index + 1}' + "", 0],
                        </#if>
                        </#list>
                        </#if>
                        /*["Knight to King 3 (Nf3)", 12],
                        ["Queen's bishop pawn (c4)", 10],
                        ['Other', 3]*/
                    ]);

                    var options = {
                        width: 300,
                        legend: {position: 'none'},
                        chart: {
                            title: 'Среднее время прохождения ',
                            subtitle: 'По каждому из вопросов'
                        },
                        axes: {
                            x: {
                                0: {side: 'top', label: 'Вопросы'} // Top x-axis.
                            }
                        },
                        bar: {groupWidth: "90%"}
                    };

                    var chart = new google.charts.Bar(document.getElementById('top_time_div'));
                    // Convert the Classic options to Material options.
                    chart.draw(data, google.charts.Bar.convertOptions(options));
                };
            </script>


            <!-- Content here -->


            <title>Статистика</title>
            <#--    <div>${stat}%</div>-->

            <div id="accordionBest">
                <div class="card">
                    <div class="card-header" id="headingBest">
                        <h5 class="mb-0">
                            <button class="btn btn-link" data-toggle="collapse" data-target="#collapseBest"
                                    aria-expanded="true" aria-controls="collapseOne">
                                Лучшие попытки каждого пользователя
                            </button>
                        </h5>
                    </div>

                    <div id="collapseBest" class="collapse" aria-labelledby="headingBest"
                         data-parent="#accordionBest">
                        <div class="card mb-3 shadow p-3 mb-5 bg-white rounded">
                            <div class="card-body">
                                <div class="table-responsive">
                                    <table class="table table-hover mr-5 table-sm align-middle text-center">

                                        <thead>
                                        <tr>
                                            <th scope="col">№</th>
                                            <th scope="col">Имя</th>
                                            <th scope="col">Фамилия</th>
                                            <th scope="col">Группа</th>
                                            <th scope="col">Логин</th>
                                            <th scope="col">Завершён в</th>
                                            <th scope="col">Процент</th>
                                            <th scope="col">Действие</th>
                                        </tr>
                                        </thead>
                                        <tbody>


                                        <#list answersOnQuiz as answer>

                                            <tr>
                                                <th scope="row">${answer_index + 1}</th>
                                                <td>${answer.firstName}</td>
                                                <td>${answer.lastName}</td>
                                                <#--            <td>${answer.completedAt.time?datetime}</td>-->
                                                <td>${answer.groupName}</td>
                                                <td><a href="/about/${answer.email}">${answer.email}</a></td>
                                                <td>
                                                    <#if answer.completedAt??>
                                                        ${answer.completedAt?datetime?string ["dd.MM.yyyy HH:mm:ss"]}
                                                    <#else>
                                                        -
                                                    </#if>

                                                </td>
                                                <#--            <td>${answer.status?c}</td>&ndash;&gt;-->


                                                <td>
                                                    <#if answer.percent??>
                                                        <a
                                                                class="btn btn-sm btn-icon btn-primary rounded-circle">
                                                            ${answer.percent}%
                                                        </a>
                                                    <#else>
                                                        -
                                                    </#if>
                                                </td>

                                                <td>
                                                    <a onclick="writeMsg('${answer.userId?c}')"
                                                       class="btn btn-sm btn-icon btn-primary rounded-circle">
                                                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24"
                                                             viewBox="0 0 24 24"
                                                             fill="none" stroke="currentColor" stroke-width="2"
                                                             stroke-linecap="round"
                                                             stroke-linejoin="round" class="feather feather-mail">
                                                            <path d="M4 4h16c1.1 0 2 .9 2 2v12c0 1.1-.9 2-2 2H4c-1.1 0-2-.9-2-2V6c0-1.1.9-2 2-2z"></path>
                                                            <polyline points="22,6 12,13 2,6"></polyline>
                                                        </svg>
                                                    </a>
                                                </td>


                                            </tr>


                                        </#list>

                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>


            <div id="accordionStat">
                <div class="card">
                    <div class="card-header" id="headingStat">
                        <h5 class="mb-0">
                            <button class="btn btn-link" data-toggle="collapse" data-target="#collapseStat"
                                    aria-expanded="true" aria-controls="collapseOne">
                                Оценивание ответов пользователей по каждому вопросу
                            </button>
                        </h5>
                    </div>

                    <div id="collapseStat" class="collapse" aria-labelledby="headingStat"
                         data-parent="#accordionStat">
                        <div class="card mb-3 shadow p-3 mb-5 bg-white rounded">
                            <div class="card-body table-responsive">
                                <table class="table table-hover mr-5 table-sm align-middle text-center">
                                    <caption>Ответы пользователей по каждому из вопросов</caption>
                                    <thead>
                                    <tr>
                                        <th scope="col">№</th>
                                        <th scope="col">Имя</th>
                                        <th scope="col">Фамилия</th>
                                        <#list quizzes as quiz>
                                            <th scope="col">${quiz_index + 1}</th>
                                        </#list>
                                        <th scope="col">Процент</th>
                                        <th scope="col">Действие</th>
                                    </tr>
                                    </thead>
                                    <tbody>


                                    <#list moreAnswers as answer>

                                        <tr>
                                            <th scope="row">${answer_index + 1}</th>
                                            <td>${answer.firstName}</td>
                                            <td>${answer.lastName}</td>
                                            <#--            <td>${answer.completedAt.time?datetime}</td>-->

                                            <#list answer.userQuizAnswers as x>
                                                <td>
                                                    <#if x.status>
                                                        <svg style="color: #008000" xmlns="http://www.w3.org/2000/svg"
                                                             width="20"
                                                             height="20"
                                                             viewBox="0 0 24 24" fill="none" stroke="currentColor"
                                                             stroke-width="2"
                                                             stroke-linecap="round" stroke-linejoin="round"
                                                             class="feather feather-check">
                                                            <polyline points="20 6 9 17 4 12"></polyline>
                                                        </svg>

                                                    <#else>

                                                        <svg style="color: red" xmlns="http://www.w3.org/2000/svg"
                                                             width="20"
                                                             height="20"
                                                             viewBox="0 0 24 24" fill="none" stroke="currentColor"
                                                             stroke-width="2"
                                                             stroke-linecap="round" stroke-linejoin="round"
                                                             class="feather feather-x">
                                                            <line x1="18" y1="6" x2="6" y2="18"></line>
                                                            <line x1="6" y1="6" x2="18" y2="18"></line>
                                                        </svg>

                                                    </#if>
                                                    <#--                                        ${x.status?string('1', '0')}-->
                                                </td>
                                            </#list>

                                            <td>
                                                <#if answer.percent??>
                                                    <a
                                                            class="btn btn-sm btn-icon btn-primary rounded-circle">
                                                        ${answer.percent}%
                                                    </a>
                                                <#else>
                                                    -
                                                </#if>
                                            </td>


                                            <td>
                                                <a onclick="writeMsg('${answer.userId?c}')"
                                                   class="btn btn-sm btn-icon btn-primary rounded-circle">
                                                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24"
                                                         viewBox="0 0 24 24"
                                                         fill="none" stroke="currentColor" stroke-width="2"
                                                         stroke-linecap="round"
                                                         stroke-linejoin="round" class="feather feather-mail">
                                                        <path d="M4 4h16c1.1 0 2 .9 2 2v12c0 1.1-.9 2-2 2H4c-1.1 0-2-.9-2-2V6c0-1.1.9-2 2-2z"></path>
                                                        <polyline points="22,6 12,13 2,6"></polyline>
                                                    </svg>
                                                </a>
                                            </td>


                                        </tr>

                                    </#list>

                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div id="accordionTime" class="mb-15">
                <div class="card">
                    <div class="card-header" id="headingTime">
                        <h5 class="mb-0">
                            <button class="btn btn-link" data-toggle="collapse" data-target="#collapseTime"
                                    aria-expanded="true" aria-controls="collapseOne">
                                Временная статистика по каждому вопросу
                            </button>
                        </h5>
                    </div>

                    <div id="collapseTime" class="collapse" aria-labelledby="headingTime"
                         data-parent="#accordionTime">
                        <div class="card mb-3 shadow p-3 mb-5 bg-white rounded">
                            <div class="card-body table-responsive">
                                <table class="table table-hover mr-5 table-sm align-middle text-center">
                                    <caption>Время затраченное на выполнение каждого вопроса в секундах</caption>
                                    <thead>
                                    <tr>
                                        <th scope="col">№</th>
                                        <th scope="col">Имя</th>
                                        <th scope="col">Фамилия</th>
                                        <#list quizzes as quiz>
                                            <th scope="col">${quiz_index + 1}</th>
                                        </#list>
                                        <th scope="col">Процент</th>
                                        <th scope="col">Действие</th>
                                    </tr>
                                    </thead>
                                    <tbody>


                                    <#list moreAnswers as answer>

                                        <tr>
                                            <th scope="row">${answer_index + 1}</th>
                                            <td>${answer.firstName}</td>
                                            <td>${answer.lastName}</td>
                                            <#--            <td>${answer.completedAt.time?datetime}</td>-->

                                            <#list answer.userQuizAnswers as x>

                                                <td>
                                                    ${x.duration?string["0.#"]}
                                                </td>
                                            </#list>

                                            <td>
                                                <#if answer.percent??>
                                                    <a
                                                            class="btn btn-sm btn-icon btn-primary rounded-circle">
                                                        ${answer.percent}%
                                                    </a>
                                                <#else>
                                                    -
                                                </#if>
                                            </td>


                                            <td>
                                                <a onclick="writeMsg('${answer.userId?c}')"
                                                   class="btn btn-sm btn-icon btn-primary rounded-circle">
                                                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24"
                                                         viewBox="0 0 24 24"
                                                         fill="none" stroke="currentColor" stroke-width="2"
                                                         stroke-linecap="round"
                                                         stroke-linejoin="round" class="feather feather-mail">
                                                        <path d="M4 4h16c1.1 0 2 .9 2 2v12c0 1.1-.9 2-2 2H4c-1.1 0-2-.9-2-2V6c0-1.1.9-2 2-2z"></path>
                                                        <polyline points="22,6 12,13 2,6"></polyline>
                                                    </svg>
                                                </a>
                                            </td>


                                        </tr>

                                    </#list>

                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>


    </main>

    <script src="/static/newActiveDialog.js"></script>

    <div class="modal fade" id="modal-test" tabindex="-1" aria-labelledby="modal-test" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered modal-fullscreen-xl-down">
            <div class="modal-content">
                <div class="modal-body py-0">
                    <#list test_id.quizzes as quiz>
                        <div id="list-item-${quiz_index + 1}"
                             class="card quiz mt-2 card mb-3 shadow p-3 mb-5 <#--bg-white--> rounded">
                            <b class="mr-4">${quiz_index + 1}.</b>
                            <div class="card-body">
                                <div id="test${quiz_index}">
                                    <#--                        <div><h6>${quiz.title}</h6></div>-->
                                    <div>
                                        <h5 class="user-select-none">${quiz.text}</h5>
                                        <p>Выберите один или несколько ответов</p>
                                    </div>
                                    <#if quiz.class == "class com.example.mywebquizengine.Model.Test.MapAnswerQuiz">
                                        <div class="custom-control custom-checkbox mt-2">

                                            <div class="card border-0 mt-3">
                                                <div class="card-body">

                                                    <#list quiz.answer as k,v>
                                                        <div class="row align-items-center gx-5 mt-2">
                                                            <div class="col">
                                                                <h5 class="map${quiz.quizId?c}key">${k}</h5>
                                                            </div>
                                                            <div class="col">
                                                                <div class="row">
                                                                    <select onchange="mapAnswer(${test_id.testId?c}, ${quiz.quizId?c})"
                                                                            class="form-select"
                                                                            aria-label="Default select example">
                                                                        <option disabled selected>Выберите ответ
                                                                        </option>
                                                                        <#list quiz.answer?values as value>
                                                                            <option class="map${quiz.quizId?c}value"
                                                                                    value="${value}">${value}</option>
                                                                        </#list>
                                                                    </select>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </#list>

                                                </div>
                                            </div>

                                        </div>
                                    </#if>
                                    <#if quiz.class == "class com.example.mywebquizengine.Model.Test.MultipleAnswerQuiz">

                                        <#list quiz.options as options>
                                            <div class="custom-control custom-checkbox mt-2">

                                                <div class="card border-0 mt-3">
                                                    <div class="card-body">

                                                        <div class="row align-items-center gx-5">
                                                            <div class="col-auto">
                                                                <div class="avatar">
                                                                    <span class="avatar-text">${options_index + 1}</span>
                                                                </div>
                                                            </div>
                                                            <div class="col">
                                                                <h5 class="user-select-none">${options}</h5>
                                                                <#--                                                    <p>last seen 3 days ago</p>-->
                                                            </div>
                                                            <div class="col-auto">
                                                                <div class="form-check">
                                                                    <input
                                                                            onclick="session(${test_id.testId?c}, ${quiz.quizId?c})"
                                                                            class="form-check-input answers${options_index}"
                                                                            type="checkbox" value="${options_index}"
                                                                            id="${options_index}_${quiz.quizId}"
                                                                            name="check${quiz.quizId?c}"
                                                                        <#if lastAnswer??>
                                                                        <#if lastAnswer.userQuizAnswers??>
                                                                        <#if lastAnswer.userQuizAnswers[quiz_index]??>
                                                                        <#if lastAnswer.userQuizAnswers[quiz_index].answer??>
                                                                        <#list lastAnswer.userQuizAnswers[quiz_index].answer as ans>
                                                                        <#if ans == options_index>
                                                                            checked
                                                                    </#if>
                                                                    </#list>
                                                                    </#if>
                                                                    </#if>
                                                                    </#if>
                                                                    </#if>
                                                                    <label class="form-check-label"
                                                                           for="id-member-1"></label>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <label class="stretched-label"
                                                               for="${options_index}_${quiz.quizId}"></label>
                                                    </div>
                                                </div>

                                            </div>
                                        </#list>

                                    </#if>

                                    <#if quiz.class == "class com.example.mywebquizengine.Model.Test.StringAnswerQuiz">
                                        <div id="inputAnswer" class="custom-control mt-3">
                                            <input oninput="onInput(${quiz.quizId?c}, ${quiz_index+1})" required
                                                   type="text"
                                                   class="form-control" id="stringAnswer${quiz_index+1}"
                                                   placeholder="Введите ответ">
                                        </div>
                                    </#if>

                                </div>
                            </div>


                        </div>
                    </#list>
                </div>
            </div>
        </div>
    </div>

    <script src='https://stackpath.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.bundle.min.js'></script>
    <script src='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js'></script>


</@c.page>