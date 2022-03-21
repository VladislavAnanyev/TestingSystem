<#import "parts/common.ftlh" as e>
<#include "parts/security.ftlh">

<@e.page>
<#--    <link href='https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css'>-->

<#--    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.css" type="text/css" rel="stylesheet">-->
    <link type="text/html" rel="stylesheet" href="/static/chart.css">
    <link href="/static/timer.css" type="text/css" rel="stylesheet">

<#--    <script src='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js'></script>-->

    <title>Ответить</title>

    <input type="hidden" id="username" value="${name}">
    <input type="hidden" id="testid" value="${test_id.testId?c}">
    <input type="hidden" id="useranswerid" value="${lastAnswer.userAnswerId?c}">

    <#if test_id.duration??>
        <input type="hidden" id="time" value="${timeout?datetime?string ["MM.dd.yyyy HH:mm:ss"]}">
    </#if>


    <#if test_id.duration??>
        <div class="alltime mb-3" id="clock">
            <h1 class="countdown-title">Осталось времени</h1>
            <div id="countdown" class="countdown">
                <div class="countdown-number">
                    <span class="days countdown-time"></span>
                    <span class="countdown-text">Дни</span>
                </div>
                <div class="countdown-number">
                    <span class="hours countdown-time"></span>
                    <span class="countdown-text">Часы</span>
                </div>
                <div class="countdown-number">
                    <span class="minutes countdown-time"></span>
                    <span class="countdown-text">Минуты</span>
                </div>
                <div class="countdown-number">
                    <span class="seconds countdown-time"></span>
                    <span class="countdown-text">Секунды</span>
                </div>
            </div>
        </div>
    </#if>

    <div>

        <#list test_id.quizzes as quiz>
            <div class="card quiz mt-2 card mb-3 shadow p-3 mb-5 bg-white rounded">
                <b class="mr-4">${quiz_index + 1}.</b>
                <div class="card-body">
                    <div id="test${quiz_index}">
                        <div><b>${quiz.title}</b></div>
                        <div>${quiz.text}</div>
                        <#if quiz.options??>
                            <#list quiz.options as options>
                                <div class="custom-control custom-checkbox mt-2">
                                    <input type="checkbox" onclick="session(${test_id.testId?c}, ${quiz.quizId?c})"
                                           class="custom-control-input answers${options_index}"
                                           id="${options_index}_${quiz.quizId}" name="check${quiz.quizId?c}"
                                           value="${options_index}"
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

                                            </#if>>
                                    <label class="custom-control-label" for="${options_index}_${quiz.quizId}">
                                        ${options}
                                    </label>
                                </div>
                            </#list>
                        </#if>

                        <#if !quiz.options??>
                            <div id="inputAnswer" class="custom-control mt-3">

                                <input required type="text" class="form-control" id="stringAnswer1"
                                       placeholder="Введите ответ">

                            </div>
                        </#if>
                    </div>
                </div>


            </div>
        </#list>

        <button type="submit" data-toggle="modal" <#--data-target="#staticBackdrop"-->
                class="btn btn-primary ml-2 mb-2" id="btnAns" onclick="f(${test_id.testId?c})">Ответить
        </button>


        <div <#--onload="chartpie(${test_id.id?c})"--> class="modal fade" id="staticBackdrop" data-backdrop="static"
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
                        <#--                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Назад</button>-->
                        <button type="button"
                                onclick="document.getElementById('staticBackdrop').hidden=true; document.getElementsByClassName('modal-backdrop fade show').item(0).hidden=true"
                                data-dismiss="modal" class="btn btn-primary">Понятно
                        </button>
                    </div>

                </div>
            </div>


        </div>


    </div>




    <!--
    https://denis-creative.com/jstimer/
    https://www.sitepoint.com/build-javascript-countdown-timer-no-dependencies/
    -->
    <script src="/static/timeover.js"></script>
    <script src='https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.1.4/Chart.bundle.min.js'></script>
<#--    <script src='https://stackpath.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.bundle.min.js'></script>-->
    <script src="/static/answer.js"></script>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.4.0/sockjs.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
    <script src="/static/session.js"></script>
    <#if test_id.duration??>
        <script src="/static/downtimer.js"></script>
    </#if>
    <script>connect()</script>
    <script>

        //const input = document.querySelector('input')

        document.onkeyup = function (e) {
            console.log("123")

            let text = document.getElementById('stringAnswer1')

            if (text.value !== "") {
                let json = {
                    answer: text.value,
                    quizId: Number(${test_id.quizzes[0].quizId?c})
                }

                console.log(json)
                let xhrTyping = new XMLHttpRequest();

                xhrTyping.open('POST', '/answersession/' + ${test_id.testId?c}, true);
                xhrTyping.setRequestHeader('Content-type', 'application/json; charset=utf-8');
                xhrTyping.onreadystatechange = function () {
                    if (xhrTyping.readyState === XMLHttpRequest.DONE && xhrTyping.status === 200) {
                        console.log("Успех")
                    }
                };
                xhrTyping.send(JSON.stringify(json));
            }


        };
    </script>



</@e.page>