<#import "parts/common.ftlh" as e>
<#include "parts/security.ftlh">

<@e.page>


    <div xmlns="">
        <title>Все тесты</title>
        <link href='https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css'>
        <script src='https://stackpath.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.bundle.min.js'></script>
        <script src='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js'></script>
        <script src='/static/checkAnswerSession.js'></script>
        <script src='/static/restoreSession.js'></script>
        <script src="/static/page.js"></script>
        <script src="/static/pageSize.js"></script>


        <div class="row">
            <#list tests as test>
                <div class="col-sm-6">
                    <div class="card mb-3 shadow p-3 mb-5 bg-white rounded">
                        <script src='/static/checkAnswerSession.js'></script>

                        <div class="card-body">
                            <h5 class="card-title">${test.description}</h5>
                            <p class="card-text">Количество вопросов: ${test.numberOfQuestions}</p>
                            <p class="card-text"><small class="text-muted">Автор: <a
                                            href="/about/${test.author}">${test.author}</a></small>
                            </p>
                        </div>

                        <#if known>
                            <button onclick="checkAnswerSession(${test.testId?c})" type="submit"
                                    class="btn btn-primary ml-3 mb-3">Приступить к выполнению
                            </button>

                        <#else>
                            <form method="get" action="/quizzes/${test.testId?c}/solve/" class="form-inline">
                                <button type="submit" class="btn btn-primary ml-3 mb-3">Приступить к выполнению
                                </button>
                            </form>
                        </#if>


                        <div class="modal fade" id="staticBackdrop${test.testId?c}" data-backdrop="static"
                             data-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel"
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
                                        <form method="get" action="/quizzes/${test.testId?c}/solve">
                                            <button type="submit" <#--onclick="restoreSession(false, ${testList.id?c})"-->
                                                    class="btn btn-secondary" <#--data-dismiss="modal"-->>Нет
                                            </button>
                                        </form>
                                        <form method="get" action="/quizzes/${test.testId?c}/solve">
                                            <input type="hidden" name="restore" value="true"/>
                                            <button type="submit" <#--onclick="restoreSession(true, ${testList.id?c})"-->
                                                    class="btn btn-primary">Да
                                            </button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            <#else>
                Здесь пока ничего нет
            </#list>
        </div>

        <div class="row">

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
        </div>
    </div>

</@e.page>