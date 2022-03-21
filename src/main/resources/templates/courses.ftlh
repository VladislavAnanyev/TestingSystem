<#import "parts/common.ftlh" as e>
<#include "parts/security.ftlh">

<@e.page>

    <div class="row">
    <#list courses as course>

        <#if course??>
            <div class="col-sm-6">
                <div class="card mb-3 shadow p-3 mb-5 bg-white rounded">
                    <script src='/static/checkAnswerSession.js'></script>

                    <div class="card-body">
                        <h5 class="card-title">${course.name}</h5>
                    </div>

                    <#if course.ownerUsername = name>
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

                    <#if course.ownerUsername = name>
                        <a href="/course/${course.courseId?c}/manage">
                            <button type="button" class="btn btn-primary ml-3 mb-3">
                                Управление
                            </button>
                        </a>
                    </#if>

                </div>

            </div>

        <#else>
            Здесь пока ничего нет
        </#if>
    </#list>

</@e.page>