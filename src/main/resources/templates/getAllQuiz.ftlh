
<#import "parts/common.ftlh" as e>
<#include "parts/security.ftlh">

<@e.page>


<div xmlns="">
    <title>Все викторины</title>
    <link href='https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css'>
    <script src='https://stackpath.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.bundle.min.js'></script>
    <script src='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js'></script>
    <script src='/static/checkAnswerSession.js'></script>
    <script src='/static/restoreSession.js'></script>
    <script src="/static/page.js"></script>
    <script src="/static/pageSize.js"></script>






    <div class="row">
    <#list test as testList>

        <#--<div class="card w-75">
            <div class="card-body">
                <h5 class="card-title">${testList.description}</h5>
                <p class="card-text">Количество вопросов: </p>
                <a href="#" class="btn btn-primary">Кнопка</a>
            </div>
        </div>-->

<#--        <div class="card my-3">-->
            <#--<div class="m-2">
                <span><a href="/about/${testList.user.username}">${testList.user.username}</a></span>
            </div>
            <div class="card-footer text-muted">
                <i>${testList.description}</i>
            </div>-->

            <div class="col-sm-6">
            <div class="card mb-3 shadow p-3 mb-5 bg-white rounded">
                <script src='/static/checkAnswerSession.js'></script>

<#--                <img src="/../../../../img/look.com.ua_2016.02-111-1920x1080/${img[testList?index].name}" height="100px" class="card-img-top" alt="...">-->
                <div class="card-body">
                    <h5 class="card-title">${testList.description}</h5>
                    <p class="card-text">Количество вопросов: ${testList.quizzes?size}</p>
                    <p class="card-text" ><small  class="text-muted">Автор: <a href="/about/${testList.user.username}">${testList.user.username}</a></small></p>
                </div>
                <#--<form method="get" action="/api/quizzes/${testList.id?c}/solve/" class="form-inline">
                    <button type="submit" class="btn btn-primary ml-3 mb-3">Приступить к выполнению</button>
                </form>-->

                <#--<button type="button" class="btn btn-primary ml-2 my-1" data-toggle="modal" data-target="#staticBackdrop">
                    Приступить к выполнению
                </button>-->


                <#if known>
                    <button onclick="checkAnswerSession(${testList.id?c})" type="submit" class="btn btn-primary ml-3 mb-3">Приступить к выполнению</button>

                <#else>
                    <form method="get" action="/quizzes/${testList.id?c}/solve/" class="form-inline">
                        <button type="submit" class="btn btn-primary ml-3 mb-3">Приступить к выполнению</button>
                    </form>
                </#if>


                <div class="modal fade" id="staticBackdrop${testList.id?c}" data-backdrop="static" data-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
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

                                    <#--                        <input type="text" class="form-control" id="oldpassword" placeholder="Придумайте новый пароль"  aria-describedby="emailHelp" name="password">-->
<#--                                    <input type="password" class="form-control" id="password1" placeholder="Придумайте новый пароль"  aria-describedby="emailHelp" name="password1">-->
<#--                                    <input type="password" class="form-control mt-3" id="password2" placeholder="Введите новый пароль ещё раз" aria-describedby="emailHelp" name="password2">-->
                                    У вас есть незаконченная сессия. Восстановить её?
                                </div>
                            </div>
                            <div class="modal-footer">
                                <form method="get" action="/quizzes/${testList.id?c}/solve">
<#--                                    <input type="hidden" name="restore" value="false"/>-->
                                    <button type="submit"  <#--onclick="restoreSession(false, ${testList.id?c})"--> class="btn btn-secondary" <#--data-dismiss="modal"-->>Нет</button>

                                </form>

                                <form method="get" action="/quizzes/${testList.id?c}/solve">

                                    <input type="hidden" name="restore" value="true"/>
                                    <button type="submit" <#--onclick="restoreSession(true, ${testList.id?c})"--> class="btn btn-primary">Да</button>

                                </form>
                            </div>

                        </div>
                    </div>
                </div>


            </div>
            </div>

<#--            <div class="card-footer text-muted">-->
<#--                <#list quiz.options as options>-->
<#--                ${options}-->
<#--                </#list>-->
<#--            </div>-->
            <#--<div class="card-footer text-muted">
                <a href="/about/${testList.user.username}">${testList.user.username}</a>
            </div>-->

<#--        </div>-->
    <#else>
        Здесь пока ничего нет

    </#list>

    </div>






    <div class="row">

        <#--<div class="col">
            <div class="form-row align-items-center ml-3">Размер:
                <div class="col-auto my-1 ">
                    <label class="mr-sm-2 sr-only" for="inlineFormCustomSelect">Preference</label>
                    <select class="custom-select"  name = "size" id="inlineFormCustomSelect">
                        <option onclick="paging()" value="no" selected>Все</option>
                        <option onclick="paging()" value="1">1</option>
                        <option onclick="paging()" value="5">5</option>
                        <option onclick="paging()" value="10">10</option>
                    </select>
                </div>
            </div>
        </div>-->



        <div class="col">
    <nav aria-label="Page navigation example" >
        <ul class="pagination justify-content-end">
            <li class="page-item">
                <a class="page-link"  aria-label="Previous">
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