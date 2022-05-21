<#import "parts/commonWithoutAside.ftlh" as e>
<#include "parts/security.ftlh">

<@e.page>

    <aside class="sidebar bg-light">
        <div class="container h-100">
            <div class="card border-4 mt-3">
                <div class="card-body">
                    <div class="row align-items-center gx-0">
                        <div class="col">
                            <h4 class="mb-1">Количество тестов: ${tests?size}</h4>
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
            <title>Все тесты</title>
            <link href='https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css'>
            <script src='https://stackpath.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.bundle.min.js'></script>
            <script src='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js'></script>
            <script src='/static/checkAnswerSession.js'></script>
            <script src='/static/restoreSession.js'></script>
            <script src="/static/page.js"></script>
            <script src="/static/pageSize.js"></script>
            <script src="/static/getAnswerSessionId.js"></script>



            <div class="row">

                <#list tests as test>
                    <div class="col-sm-6">
                        <div class="card mb-3 shadow p-3 mb-5 bg-white rounded">
                            <script src='/static/checkAnswerSession.js'></script>

                            <div class="card-body ">
                                <div class="row">
                                    <div class="col">
                                        <h5 class="card-title">${test.description}</h5>
                                        <p class="card-text">Количество вопросов: ${test.numberOfQuestions}</p>
                                        <#--<p class="card-text"><small class="text-muted">Автор: <a
                                                        href="/about/${test.author}">${test.author}</a></small>-->
                                        <#--</p>-->
                                        <#if test.attempts??>
                                            <p class="card-text">Число попыток: ${test.attempts}</p>

                                        <#else>
                                            <p class="card-text">Число попыток: неограничено</p>
                                        </#if>

                                        <#if test.duration??>
                                            <p class="card-text">Ограничение по времени: ${test.duration}</p>

                                        <#else>
                                            <p class="card-text">Ограничение по времени: отсутствует</p>
                                        </#if>

                                        <#if test.startTime??>
                                            <p class="card-text">Тест доступен с: ${test.startTime?datetime}</p>

                                        <#else>
                                            <p class="card-text">Тест доступен в любое время</p>
                                        </#if>
                                    </div>


                                    <div class="col-auto d-flex align-items-end flex-column bd-highlight">
                                        <#if known>

                                            <a onclick="checkAnswerSession(${test.testId?c})"
                                               class="btn btn-icon btn-primary rounded-circle">
                                                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24"
                                                     viewBox="0 0 24 24"
                                                     fill="none" stroke="currentColor" stroke-width="2"
                                                     stroke-linecap="round"
                                                     stroke-linejoin="round" class="feather feather-chevron-right">
                                                    <polyline points="9 18 15 12 9 6"></polyline>
                                                </svg>
                                            </a>

                                        <#else>
                                        <#--                            <form method="get" action="/quizzes/${test.testId?c}/solve/" class="form-inline">-->
                                            <a href="/quizzes/${test.testId?c}/solve/"
                                               class="btn btn-icon btn-primary rounded-circle">
                                                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24"
                                                     viewBox="0 0 24 24"
                                                     fill="none" stroke="currentColor" stroke-width="2"
                                                     stroke-linecap="round"
                                                     stroke-linejoin="round" class="feather feather-chevron-right">
                                                    <polyline points="9 18 15 12 9 6"></polyline>
                                                </svg>
                                            </a>

                                        </#if>

                                        <#if test.complete>
                                            <a class="justify-content-center mt-10 mx-3 p-2 bd-highlight"
                                               data-bs-toggle="tooltip"
                                               data-bs-placement="right"
                                               title="Тест пройден">
                                                <svg style="color: green" xmlns="http://www.w3.org/2000/svg" width="24"
                                                     height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor"
                                                     stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
                                                     class="feather feather-check-square">
                                                    <polyline points="9 11 12 14 22 4"></polyline>
                                                    <path d="M21 12v7a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h11"></path>
                                                </svg>
                                            </a>
                                        </#if>
                                    </div>
                                </div>
                            </div>


                            <div class="modal fade" id="staticBackdrop${test.testId?c}" data-backdrop="static"
                                 <#--data-keyboard="false"--> <#--tabindex="-1"--> aria-labelledby="staticBackdropLabel"
                                 aria-hidden="true">
                                <div class="modal-dialog modal-dialog-centered">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title" id="staticBackdropLabel">Предупреждение</h5>
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                <span aria-hidden="true">&times;</span>
                                            </button>
                                        </div>
                                        <div class="modal-body">
                                            <div class="form-group">
                                                У вас есть незаконченная сессия. Восстановить её?
                                            </div>
                                        </div>
                                        <div class="modal-footer">
                                                <button onclick="getAnswerSessionId(${test.testId?c}, false)" type="submit" <#--onclick="restoreSession(false, ${testList.id?c})"-->
                                                        class="btn btn-secondary" <#--data-dismiss="modal"-->>Нет
                                                </button>
                                                <button onclick="getAnswerSessionId(${test.testId?c}, true)" type="submit" <#--onclick="restoreSession(true, ${testList.id?c})"-->
                                                        class="btn btn-primary">Да
                                                </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                <#else>
                    Здесь пока ничего нет
                </#list>

                <div class="modal fade" id="staticBackdropForbidden" data-backdrop="static"
                        <#--data-keyboard="false"--> <#--tabindex="-1"--> aria-labelledby="staticBackdropLabel"
                     aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="staticBackdropLabel">Доступ ограничен</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                <div class="form-group">
                                    Доступ к этому тесту ограничен. Условия не выполнены.
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button data-dismiss="modal" type="submit"
                                        class="btn btn-primary">Понятно
                                </button>
                            </div>
                        </div>
                    </div>
                </div>

            </div>

            <#--<div class="row">

                <div class="col">
                    <nav aria-label="Page navigation example">
                        <ul class="pagination justify-content-end">
                            <li class="page-item">
                                <a class="page-link" aria-label="Previous">
                                    <span aria-hidden="true" onclick="pagePrev()">&laquo;</span>
                                </a>
                            </li>
                            <li id="first" class="page-item"><a class="page-link" onclick="pageOne(0)">1</a></li>
                            <li id="second" class="page-item"><a class="page-link" onclick="pageTwo(1)">2</a></li>
                            <li id="third" class="page-item"><a class="page-link" onclick="pageThree(2)">3</a></li>
                            <li class="page-item">
                                <a class="page-link" aria-label="Next">
                                    <span onclick="pageNext()" aria-hidden="true">&raquo;</span>
                                </a>
                            </li>
                            <li>
                            </li>
                        </ul>
                    </nav>
                </div>
            </div>-->
        </div>
    </main>

</@e.page>