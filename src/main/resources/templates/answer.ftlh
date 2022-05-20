<#import "parts/commonWithoutAside.ftlh" as e>
<#include "parts/security.ftlh">

<@e.page>
    <link type="text/html" rel="stylesheet" href="/static/chart.css">
    <link href="/static/timer.css" type="text/css" rel="stylesheet">
<#--    <link href='https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css'>-->

    <title>Ответить</title>

    <input type="hidden" id="username" value="${name}">
    <input type="hidden" id="testid" value="${test_id.testId?c}">
    <input type="hidden" id="useranswerid" value="${lastAnswer.userAnswerId?c}">

    <#if test_id.duration??>
        <input type="hidden" id="time" value="${timeout?datetime?string ["MM.dd.yyyy HH:mm:ss"]}">
    </#if>

    <aside class="sidebar bg-light">
        <div class="container h-100">

            <div class="progress mt-5" style="height: 20px;">
                <div class="progress-bar" id="progressbar" role="progressbar" style="width: 0%;" aria-valuenow="0"
                     aria-valuemin="0"
                     aria-valuemax="100">0%
                </div>
            </div>

            <div class="<#--d-flex--> <#--align-content-end--> flex-wrap mt-5">
                <nav id="navbar-example3" class="navbar navbar-light bg-light px-3" aria-label="...">
                    <ul class="justify-content-center pagination nav nav-pills" style="flex-wrap: wrap">
                        <#--                        <div class="row">-->

                        <#list test_id.quizzes as quiz>
                        <#--                            <div class="col-3 mt-2 &lt;#&ndash;d-flex&ndash;&gt; align-items-stretch">-->
                            <li style="width: 60px" class="ml-5 mt-2 card page-item  nav-item mx-1 <#--active-->"
                                aria-current="page">
                                <a style="color: #5376fc" id="${quiz.quizId?c}"
                                   class="card-body page-link nav-link text-center"
                                   href="#list-item-${quiz_index + 1}">${quiz_index + 1}
                                    <#--                                    <span class="page-link">${quiz_index + 1}</span>-->
                                </a>
                            </li>
                        <#--                            </div>-->
                        </#list>

                        <#--                        </div>-->

                    </ul>

                </nav>

            </div>

            <#if test_id.duration??>
                <div class="card border-4">
                    <div class="card-body">
                        <div class="row align-items-center gx-0">
                            <div class="col">
                                <h4 class="mb-1">Осталось времени: </h4>

                                <ul class="list-inline">
                                    <h5 hidden class="list-inline-item" id="days"></h5>
                                    <h5 class="list-inline-item" id="hours"></h5>:
                                    <h5 class="list-inline-item" id="minutes"></h5>:
                                    <h5 class="list-inline-item" id="seconds"></h5>
                                </ul>

                            </div>
                            <div class="col-auto">
                                <a class="btn btn-sm btn-icon btn-primary rounded-circle">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"
                                         fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"
                                         stroke-linejoin="round" class="feather feather-clock">
                                        <circle cx="12" cy="12" r="10"></circle>
                                        <polyline points="12 6 12 12 16 14"></polyline>
                                    </svg>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
                <script src="/static/downtimer.js"></script>

            <#else>

                <div class="card border-4 mt-3">
                    <div class="card-body">
                        <div class="row align-items-center gx-0">
                            <div class="col">
                                <h4 class="mb-1">Время на выполнение не ограничено</h4>
                            </div>
                            <div class="col-auto">
                                <a class="btn btn-sm btn-icon btn-primary rounded-circle">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"
                                         fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"
                                         stroke-linejoin="round" class="feather feather-clock">
                                        <circle cx="12" cy="12" r="10"></circle>
                                        <polyline points="12 6 12 12 16 14"></polyline>
                                    </svg>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>

            </#if>


            <script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.4.0/sockjs.js"></script>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
            <script src="/static/timeover.js"></script>
            <#--                    <script src="/static/subscribe.js"></script>-->
            <script>connect()</script>

        </div>
    </aside>

    <main class="main is-visible">
        <div class="container h-100 mt-2 ml-2 chat-body hide-scrollbar flex-1 scrollspy-example" data-bs-offset="225"
             tabindex="0" data-bs-spy="scroll" data-bs-target="#navbar-example3">
            <div>

                <#list quizzes as quiz>
                    <div id="list-item-${quiz_index + 1}"
                         class="card quiz mt-2 card mb-3 shadow p-3 mb-5 <#--bg-white--> rounded">
                        <b class="mr-4">${quiz_index + 1}.</b>
                        <div class="card-body">
                            <div id="test${quiz_index}">
                                <#--                        <div><h6>${quiz.title}</h6></div>-->
                                <div>
                                    <h5 class="user-select-none">${quiz.text}</h5>
                                    <#if quiz.fileUrl??>

                                        <#if quiz.fileUrl?contains("download")>
                                            <a href="${quiz.fileUrl}">
                                                <div class="avatar avatar-lg dropzone-file-preview"><span
                                                            class="avatar-text rounded bg-secondary text-body file-title"
                                                            title="Скачать">            <svg
                                                                viewBox="0 0 24 24"
                                                                width="24"
                                                                height="24"
                                                                stroke="currentColor"
                                                                stroke-width="2"
                                                                fill="none"
                                                                stroke-linecap="round"
                                                                stroke-linejoin="round"
                                                                class="css-i6dzq1"><path
                                                                    d="M13 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V9z"></path><polyline
                                                                    points="13 2 13 9 20 9"></polyline></svg>        </span>
                                                </div>
                                            </a>

                                        <#else>
                                            <img class="img-fluid" src="${quiz.fileUrl}" alt="...">

                                        </#if>

                                    </#if>
                                    <p>Выберите один или несколько ответов</p>
                                </div>
                                <#if quiz.class == "class com.example.mywebquizengine.Model.Test.MapAnswerQuiz">
                                    <div class="custom-control custom-checkbox mt-2">

                                        <div class="card border-0 mt-3">
                                            <div class="card-body">

                                                <#if lastAnswer.userQuizAnswers[quiz_index].answer?size != 0>


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


                                                                        <option disabled>Выберите ответ
                                                                        </option>
                                                                        <#list quiz.answer?values as value>
                                                                            <option
                                                                                    <#if lastAnswer.userQuizAnswers[quiz_index].answer?values[v?index]??>
                                                                                        <#if value == lastAnswer.userQuizAnswers[quiz_index].answer?values[v?index]>selected</#if>
                                                                                    </#if>
                                                                                    class="map${quiz.quizId?c}value"
                                                                                    value="${value}">${value}</option>
                                                                        </#list>
                                                                    </select>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </#list>

                                                <#else >
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





                                                </#if>


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
                                        <input oninput="onInput(${quiz.quizId?c}, ${quiz_index+1})" required type="text"
                                               class="form-control" id="stringAnswer${quiz_index+1}"
                                                <#if lastAnswer??>
                                                    value="<#if lastAnswer.userQuizAnswers[quiz_index]??><#if lastAnswer.userQuizAnswers[quiz_index].answer??>${lastAnswer.userQuizAnswers[quiz_index].answer}</#if></#if>"
                                                </#if>
                                               placeholder="Введите ответ">
                                    </div>
                                </#if>

                            </div>
                        </div>


                    </div>
                </#list>


                <button <#--onmouseenter="scrollListener()"--> type="submit"
                                                               data-toggle="modal" <#--data-target="#staticBackdrop"-->
                                                               class="btn btn-primary ml-2 mb-2" id="btnAns"
                                                               onclick="f(${test_id.testId?c})">Ответить
                </button>


                <div <#--onload="chartpie(${test_id.id?c})"--> class="modal fade" id="staticBackdrop"
                                                               data-backdrop="static"
                                                               data-keyboard="false" tabindex="-1"
                                                               aria-labelledby="staticBackdropLabel" aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered modal-lg">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="staticBackdropLabel">Ваши результаты</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body">

                                <div class="page-content page-container" id="page-content">
                                    <div class="padding">
                                        <div class="row">
                                            <div class="container-fluid d-flex justify-content-center">
                                                <div class="col-sm-8 col-md-6">
                                                    <div class="card justify-content-center">
                                                        <#--<div class="card-header">Диаграмма</div>-->
                                                        <div class="card-body" style="height: 420px">
                                                            <div class="chartjs-size-monitor"
                                                                 style="position: absolute; left: 0px; top: 0px; right: 0px; bottom: 0px; overflow: hidden; pointer-events: none; visibility: hidden; z-index: -1;">
                                                                <div class="chartjs-size-monitor-expand"
                                                                     style="position:absolute;left:0;top:0;right:0;bottom:0;overflow:hidden;pointer-events:none;visibility:hidden;z-index:-1;">
                                                                    <div style="position:absolute;width:1000000px;height:1000000px;left:0;top:0"></div>
                                                                </div>
                                                                <div class="chartjs-size-monitor-shrink"
                                                                     style="position:absolute;left:0;top:0;right:0;bottom:0;overflow:hidden;pointer-events:none;visibility:hidden;z-index:-1;">
                                                                    <div style="position:absolute;width:200%;height:200%;left:0; top:0"></div>
                                                                </div>
                                                            </div>
                                                            <canvas id="chart-line" width="299" height="200"
                                                                    class="chartjs-render-monitor"
                                                                    style="display: block; width: 299px; height: 200px;"></canvas>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <#--                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Назад</button>-->
                                <button type="button"
                                        data-dismiss="modal" class="btn btn-primary">Понятно
                                </button>
                            </div>

                        </div>
                    </div>


                </div>

                <script src='https://stackpath.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.bundle.min.js'></script>
                <script src='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js'></script>
                <script src='https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.1.4/Chart.bundle.min.js'></script>
                <script src="/static/answer.js"></script>
                <script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.4.0/sockjs.js"></script>
                <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
                <script src="/static/session.js"></script>
                <script src="/static/mapAnswer.js"></script>
                <script src="/static/onInputAnswer.js"></script>
                <script>
                    const anchors = document.querySelectorAll('a[href*="#"]')

                    for (let anchor of anchors) {
                        anchor.addEventListener('click', function (e) {
                            e.preventDefault()

                            const blockID = anchor.getAttribute('href').substr(1)

                            document.getElementById(blockID).scrollIntoView({
                                behavior: 'smooth',
                                block: 'start'
                            })
                        })
                    }
                </script>

                <div id="quizcount" hidden>${test_id.quizzes?size?c}</div>

            </div>
        </div>

        <script>

            let previousId = 0;
            let previousTime = new Date()

            function scrollListener() {
                let elem = document.getElementsByClassName('active');
                console.log(previousId)
                let now = new Date()
                let finalTime = (now - previousTime) / 1000;
                console.log(finalTime)
                previousTime = new Date();

                if (finalTime > 1.0) {
                    console.log("Запрос")
                    let answerId = document.getElementById('useranswerid').value
                    let json = {
                        answerSessionId: answerId,
                        quizId: previousId,
                        duration: finalTime
                    }
                    console.log(json)
                    let xhr = new XMLHttpRequest();
                    xhr.open('POST', '/test/answer/duration', false);
                    xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
                    xhr.onreadystatechange = function () {
                    };
                    xhr.send(JSON.stringify(json))
                }
                previousId = elem[0].getAttribute('id')
            }

            var firstScrollSpyEl = document.querySelector('[data-bs-spy="scroll"]')
            firstScrollSpyEl.addEventListener('activate.bs.scrollspy', function () {
                scrollListener()
            })


        </script>


    </main>


</@e.page>