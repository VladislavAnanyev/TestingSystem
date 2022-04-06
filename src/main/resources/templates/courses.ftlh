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

                <div class="card mb-3 shadow p-3 mb-5 bg-white rounded" style="width: 15rem;">
                    <img src="${course.image}" class="rounded-circle card-img-top" alt="...">
                    <script src='/static/checkAnswerSession.js'></script>

                    <div class="card-body">
                        <h5 class="card-title">${course.name}</h5>

                        <div class="progress mb-5" style="height: 20px;">
                            <div class="progress-bar" role="progressbar" style="width: ${course.percentageOfComplete?c}%;" aria-valuenow="${course.percentageOfComplete}" aria-valuemin="0" aria-valuemax="100">
                                ${course.percentageOfComplete?c}%
                            </div>
                        </div>

                        <a href="/course/${course.courseId?c}/tests" class="btn btn-icon btn-primary rounded-circle">
                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-chevron-right"><polyline points="9 18 15 12 9 6"></polyline></svg>
                        </a>

                        <#if course.ownerUserId = nameId>
                            <a href="/course/${course.courseId?c}/test/create" class="btn btn-icon btn-primary rounded-circle">
                                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-plus"><line x1="12" y1="5" x2="12" y2="19"></line><line x1="5" y1="12" x2="19" y2="12"></line></svg>
                            </a>
                        </#if>

                        <#if course.ownerUserId = nameId>
                            <a href="/course/${course.courseId?c}/manage" class="btn btn-icon btn-primary rounded-circle">
                                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-settings"><circle cx="12" cy="12" r="3"></circle><path d="M19.4 15a1.65 1.65 0 0 0 .33 1.82l.06.06a2 2 0 0 1 0 2.83 2 2 0 0 1-2.83 0l-.06-.06a1.65 1.65 0 0 0-1.82-.33 1.65 1.65 0 0 0-1 1.51V21a2 2 0 0 1-2 2 2 2 0 0 1-2-2v-.09A1.65 1.65 0 0 0 9 19.4a1.65 1.65 0 0 0-1.82.33l-.06.06a2 2 0 0 1-2.83 0 2 2 0 0 1 0-2.83l.06-.06a1.65 1.65 0 0 0 .33-1.82 1.65 1.65 0 0 0-1.51-1H3a2 2 0 0 1-2-2 2 2 0 0 1 2-2h.09A1.65 1.65 0 0 0 4.6 9a1.65 1.65 0 0 0-.33-1.82l-.06-.06a2 2 0 0 1 0-2.83 2 2 0 0 1 2.83 0l.06.06a1.65 1.65 0 0 0 1.82.33H9a1.65 1.65 0 0 0 1-1.51V3a2 2 0 0 1 2-2 2 2 0 0 1 2 2v.09a1.65 1.65 0 0 0 1 1.51 1.65 1.65 0 0 0 1.82-.33l.06-.06a2 2 0 0 1 2.83 0 2 2 0 0 1 0 2.83l-.06.06a1.65 1.65 0 0 0-.33 1.82V9a1.65 1.65 0 0 0 1.51 1H21a2 2 0 0 1 2 2 2 2 0 0 1-2 2h-.09a1.65 1.65 0 0 0-1.51 1z"></path></svg>
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