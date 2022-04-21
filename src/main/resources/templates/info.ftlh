<#import "parts/commonWithoutAside.ftlh" as c>
<@c.page>

    <aside class="sidebar bg-light">
        <div class="container h-100">
            <div class="card border-4 mt-3">
                <div class="card-body">
                    <div class="row align-items-center gx-0">
                        <div class="col">
                            <h4 class="mb-1">Самый трудный вопрос: 2</h4>
                        </div>
                        <div class="col-auto">
                            <a class="btn btn-sm btn-icon btn-primary rounded-circle">
                                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"
                                     fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"
                                     stroke-linejoin="round" class="feather feather-layers">
                                    <polygon points="12 2 2 7 12 12 22 7 12 2"></polygon>
                                    <polyline points="2 17 12 22 22 17"></polyline>
                                    <polyline points="2 12 12 17 22 12"></polyline>
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
                            <h4 class="mb-1">Самый лёгкий вопрос: 1</h4>
                        </div>
                        <div class="col-auto">
                            <a class="btn btn-sm btn-icon btn-primary rounded-circle">
                                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"
                                     fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"
                                     stroke-linejoin="round" class="feather feather-layers">
                                    <polygon points="12 2 2 7 12 12 22 7 12 2"></polygon>
                                    <polyline points="2 17 12 22 22 17"></polyline>
                                    <polyline points="2 12 12 17 22 12"></polyline>
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
                            <a class="btn btn-sm btn-icon btn-primary rounded-circle">
                                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"
                                     fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"
                                     stroke-linejoin="round" class="feather feather-layers">
                                    <polygon points="12 2 2 7 12 12 22 7 12 2"></polygon>
                                    <polyline points="2 17 12 22 22 17"></polyline>
                                    <polyline points="2 12 12 17 22 12"></polyline>
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
                <div class="col"></div>
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
                        ['${chartd_index}' + "", ${chartd?c}],
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


            <!-- Content here -->


            <title>Статистика</title>
            <#--    <div>${stat}%</div>-->

            <div class="card mb-3 shadow p-3 mb-5 bg-white rounded">
                <div class="card-body">
                    <table class="table table-hover mr-5 table-sm align-middle text-center">

                        <thead>
                        <tr>
                            <th scope="col">№</th>
                            <th scope="col">Имя</th>
                            <th scope="col">Фамилия</th>
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
                                        <div class="progress" style="height: 20px;">
                                            <div class="progress-bar" role="progressbar"
                                                 style="width: ${answer.percent}%;"
                                                 aria-valuenow="${answer.percent}" aria-valuemin="-1"
                                                 aria-valuemax="100">
                                                ${answer.percent}%
                                            </div>
                                        </div>

                                    <#else>
                                        -
                                    </#if>
                                </td>

                                <td>
                                    <a onclick="writeMsg('${answer.userId?c}')"
                                       class="btn btn-sm btn-icon btn-primary rounded-circle">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24"
                                             viewBox="0 0 24 24"
                                             fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"
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


            <div class="card mb-3 shadow p-3 mb-5 bg-white rounded">
                <div class="card-body">
                    <table class="table table-hover mr-5 table-sm align-middle text-center">

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
                                        ${x.status?string('1', '0')}
                                    </td>
                                </#list>

                                <td>
                                    <#if answer.percent??>
                                        <div class="progress" style="height: 20px;">
                                            <div class="progress-bar" role="progressbar"
                                                 style="width: ${answer.percent}%;"
                                                 aria-valuenow="${answer.percent}" aria-valuemin="-1"
                                                 aria-valuemax="100">
                                                ${answer.percent}%
                                            </div>
                                        </div>

                                    <#else>
                                        -
                                    </#if>
                                </td>


                                <td>
                                    <a onclick="writeMsg('${answer.userId?c}')"
                                       class="btn btn-sm btn-icon btn-primary rounded-circle">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24"
                                             viewBox="0 0 24 24"
                                             fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"
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
    </main>

    <script src="/static/newActiveDialog.js"></script>

</@c.page>