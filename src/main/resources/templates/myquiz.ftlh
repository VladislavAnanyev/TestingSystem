<#import "parts/common.ftlh" as e>

<@e.page>
    <title>Мои викторины</title>

    <#--${test}-->
        <#list myquiz as test>

<#--            <#list test.quizzes as quiz>-->


            <div class="card mb-3 shadow p-3 mb-5 bg-white rounded" >
                <div id="${test.id?c}">
                <#--<div class="m-2">
                    <span>${quiz.title}</span>
                </div>
                <div class="card-footer text-muted">
                    <i>${quiz.text}</i>
                </div>
                <div class="card-footer text-muted">
                    <#list quiz.options as options>
                        ${options}
                    </#list>
                </div>
                <div class="card-footer text-muted">
                    ${quiz.text}
                </div>-->
                <div class="card-body" id="thisid">
                    <div class="row">

                        <div class="col">
                    <h5 class="card-title">${test.description}</h5>
                    <p class="card-text">Количество вопросов: ${test.quizzes?size}</p>
                    <p class="card-text">Количество ответов пользователей: ${test.answers?size}</p>

                    <p class="card-text" ><small  class="text-muted">Автор: <a href="/about/${test.user.username}">
                                ${test.user.username}
                            </a></small></p>
                        </div>

                        <div class="col">

                            <div class="row">
                        <form method="get" action="/quizzes/${test.id?c}/info/" class="mt-2">
                            <#--                        <button type="submit" class="btn btn-primary ml-2 my-1">Подробнее</button>-->
                            <#--<svg  type="submit" xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-bar-chart-fill" viewBox="0 0 16 16">

                                <path d="M1 11a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1v3a1 1 0 0 1-1 1H2a1 1 0 0 1-1-1v-3zm5-4a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1v7a1 1 0 0 1-1 1H7a1 1 0 0 1-1-1V7zm5-5a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1v12a1 1 0 0 1-1 1h-2a1 1 0 0 1-1-1V2z"/>

                            </svg>-->
                            <button type="submit" class="btn btn-primary btn-block">
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-bar-chart-fill mb-1 mr-1" viewBox="0 0 16 16">
                                    <path d="M1 11a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1v3a1 1 0 0 1-1 1H2a1 1 0 0 1-1-1v-3zm5-4a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1v7a1 1 0 0 1-1 1H7a1 1 0 0 1-1-1V7zm5-5a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1v12a1 1 0 0 1-1 1h-2a1 1 0 0 1-1-1V2z"></path>
                                </svg>
                                Статистика
                            </button>

                        </form>



                        <form method="get" action="/update/${test.id?c}" class="mt-2">
                            <button type="submit" class="btn btn-primary btn-block">
                                <svg type="button" xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-pencil-fill mb-1 mr-1" viewBox="0 0 16 16">
                                    <path d="M12.854.146a.5.5 0 0 0-.707 0L10.5 1.793 14.207 5.5l1.647-1.646a.5.5 0 0 0 0-.708l-3-3zm.646 6.061L9.793 2.5 3.293 9H3.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.207l6.5-6.5zm-7.468 7.468A.5.5 0 0 1 6 13.5V13h-.5a.5.5 0 0 1-.5-.5V12h-.5a.5.5 0 0 1-.5-.5V11h-.5a.5.5 0 0 1-.5-.5V10h-.5a.499.499 0 0 1-.175-.032l-.179.178a.5.5 0 0 0-.11.168l-2 5a.5.5 0 0 0 .65.65l5-2a.5.5 0 0 0 .168-.11l.178-.178z"/>
                                </svg>
                                Изменить
                            </button>
                        </form>


                        <form class="mt-2">
                            <button onclick="deleteQuiz(${test.id?c})" type="button" class="btn btn-primary btn-block">
                                <svg  onclick="deleteQuiz(${test.id?c})" xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-archive-fill 1butt mb-1 mr-1" viewBox="0 0 16 16">
                                    <path d="M12.643 15C13.979 15 15 13.845 15 12.5V5H1v7.5C1 13.845 2.021 15 3.357 15h9.286zM5.5 7h5a.5.5 0 0 1 0 1h-5a.5.5 0 0 1 0-1zM.8 1a.8.8 0 0 0-.8.8V3a.8.8 0 0 0 .8.8h14.4A.8.8 0 0 0 16 3V1.8a.8.8 0 0 0-.8-.8H.8z"/>
                                </svg>
                                Удалить</button>
                        </form>
                            </div>


                        </div>
                        <#--<button type="button" class="btn btn-secondary">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-pencil-fill" viewBox="0 0 16 16">
                                <path d="M12.854.146a.5.5 0 0 0-.707 0L10.5 1.793 14.207 5.5l1.647-1.646a.5.5 0 0 0 0-.708l-3-3zm.646 6.061L9.793 2.5 3.293 9H3.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.207l6.5-6.5zm-7.468 7.468A.5.5 0 0 1 6 13.5V13h-.5a.5.5 0 0 1-.5-.5V12h-.5a.5.5 0 0 1-.5-.5V11h-.5a.5.5 0 0 1-.5-.5V10h-.5a.499.499 0 0 1-.175-.032l-.179.178a.5.5 0 0 0-.11.168l-2 5a.5.5 0 0 0 .65.65l5-2a.5.5 0 0 0 .168-.11l.178-.178z"></path>
                            </svg>
                        </button>-->

                    </div>


                </div>
                <#--<div class="card-footer text-muted">
                    <#list quiz.answer as answers>
                        ${answers}
                    </#list>
                </div>-->
                </div>
            </div>



        <#else>
            Здесь пока ничего нет
<#--        </#list>-->
        </#list>
    <script src="/static/deletereq.js"></script>
    <#--<script>let style = document.getElementById("test" + i).style;
        style.padding = '20px';</script>-->

</@e.page>