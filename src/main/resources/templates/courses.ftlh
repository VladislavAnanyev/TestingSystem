<#import "parts/common.ftlh" as e>
<#include "parts/security.ftlh">

<@e.page>

    <div class="row">
    <#list courses as course>

        <#if course??>



            <div class="col-sm-6">

                <#--<div class="card ml-5" style="width: 20rem;">
                    <img src="/img/11.jpg" class="card-img-top" alt="...">
                    <div class="card-body">
                        <h4 class="card-title">Card Title</h4>
                        <p class="card-text">This card has a regular title and short paragraphy of text below it.</p>
                        <p class="card-text"><small class="text-muted">Last updated 3 mins ago</small></p>
                    </div>
                </div>-->

                <div class="card mb-3 shadow p-3 mb-5 bg-white rounded" <#--style="width: 15rem;"-->>
<#--                    <img src="/img/11.jpg" class="card-img-top" alt="...">-->
                    <script src='/static/checkAnswerSession.js'></script>

                    <div class="card-body">
                        <h5 class="card-title">${course.name}</h5>

                        <div class="progress mb-5" style="height: 20px;">
                            <div class="progress-bar" role="progressbar" style="width: ${course.percentageOfComplete?c}%;" aria-valuenow="${course.percentageOfComplete}" aria-valuemin="0" aria-valuemax="100">
                                ${course.percentageOfComplete?c}%
                            </div>
                        </div>

                        <#if course.ownerEmail = name>
                            <a href="/course/${course.courseId?c}/test/add">
                                <button type="button" class="btn btn-primary ml-3 mb-3">
                                    Добавить тест
                                </button>
                            </a>
                        </#if>

                        <a href="/course/${course.courseId?c}/tests">
                            <button type="button" class="btn btn-primary ml-3 mb-3">
                                Все тесты курса
                            </button>
                        </a>

                        <#if course.ownerEmail = name>
                            <a href="/course/${course.courseId?c}/manage">
                                <button type="button" class="btn btn-primary ml-3 mb-3">
                                    Управление
                                </button>
                            </a>
                        </#if>

                    </div>



                </div>

            </div>

        <#else>
            Здесь пока ничего нет
        </#if>
    </#list>

</@e.page>