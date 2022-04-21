<#import "parts/commonWithoutAside.ftlh" as e>
<#include "parts/security.ftlh">

<@e.page>

    <aside class="sidebar bg-light" xmlns="http://www.w3.org/1999/html">

        <div class="container h-100">
            <div class="card border-4 mt-3">
                <div class="card-body">
                    <div class="row align-items-center gx-0">
                        <div class="col">
                            <h4 class="mb-1">Количество доступных курсов: ${courses?size}</h4>
                        </div>
                        <div class="col-auto">
                            <a <#--onclick="location.href='/course/create'" -->data-bs-toggle="modal"
                               data-bs-target="#modal-invite"
                               class="btn btn-sm btn-icon btn-primary rounded-circle">
                                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"
                                     fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"
                                     stroke-linejoin="round" class="feather feather-plus">
                                    <line x1="12" y1="5" x2="12" y2="19"></line>
                                    <line x1="5" y1="12" x2="19" y2="12"></line>
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

            <div class="mb-8">
                <h2 class="fw-bold m-0">Список доступных курсов: </h2>
            </div>

            <div class="row">
                <#list courses as course>

                    <#if course??>

                        <div class="col-sm-6">


                            <div class="card mb-3 shadow p-3 mb-5 bg-white rounded" style="width: 15rem;">
                                <img src="${course.image}" class="rounded-circle card-img-top" alt="...">
                                <script src='/static/checkAnswerSession.js'></script>

                                <div class="card-body">
                                    <h5 class="card-title">${course.name}</h5>

                                    <div class="progress mb-5" style="height: 20px;">
                                        <div class="progress-bar" role="progressbar"
                                             style="width: ${course.percentageOfComplete?c}%;"
                                             aria-valuenow="${course.percentageOfComplete}" aria-valuemin="0"
                                             aria-valuemax="100">
                                            ${course.percentageOfComplete?c}%
                                        </div>
                                    </div>

                                    <div class="row text-center">

                                        <#if course.ownerUserId = nameId>

                                            <div class="col">
                                                <a href="/course/${course.courseId?c}/manage"
                                                   class="btn btn-icon btn-primary rounded-circle">
                                                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24"
                                                         viewBox="0 0 24 24" fill="none" stroke="currentColor"
                                                         stroke-width="2"
                                                         stroke-linecap="round" stroke-linejoin="round"
                                                         class="feather feather-settings">
                                                        <circle cx="12" cy="12" r="3"></circle>
                                                        <path d="M19.4 15a1.65 1.65 0 0 0 .33 1.82l.06.06a2 2 0 0 1 0 2.83 2 2 0 0 1-2.83 0l-.06-.06a1.65 1.65 0 0 0-1.82-.33 1.65 1.65 0 0 0-1 1.51V21a2 2 0 0 1-2 2 2 2 0 0 1-2-2v-.09A1.65 1.65 0 0 0 9 19.4a1.65 1.65 0 0 0-1.82.33l-.06.06a2 2 0 0 1-2.83 0 2 2 0 0 1 0-2.83l.06-.06a1.65 1.65 0 0 0 .33-1.82 1.65 1.65 0 0 0-1.51-1H3a2 2 0 0 1-2-2 2 2 0 0 1 2-2h.09A1.65 1.65 0 0 0 4.6 9a1.65 1.65 0 0 0-.33-1.82l-.06-.06a2 2 0 0 1 0-2.83 2 2 0 0 1 2.83 0l.06.06a1.65 1.65 0 0 0 1.82.33H9a1.65 1.65 0 0 0 1-1.51V3a2 2 0 0 1 2-2 2 2 0 0 1 2 2v.09a1.65 1.65 0 0 0 1 1.51 1.65 1.65 0 0 0 1.82-.33l.06-.06a2 2 0 0 1 2.83 0 2 2 0 0 1 0 2.83l-.06.06a1.65 1.65 0 0 0-.33 1.82V9a1.65 1.65 0 0 0 1.51 1H21a2 2 0 0 1 2 2 2 2 0 0 1-2 2h-.09a1.65 1.65 0 0 0-1.51 1z"></path>
                                                    </svg>
                                                </a>
                                            </div>

                                        </#if>

                                        <div class="col">
                                            <a href="/course/${course.courseId?c}/tests"
                                               class="btn btn-icon btn-primary rounded-circle">
                                                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24"
                                                     viewBox="0 0 24 24" fill="none" stroke="currentColor"
                                                     stroke-width="2"
                                                     stroke-linecap="round" stroke-linejoin="round"
                                                     class="feather feather-chevron-right">
                                                    <polyline points="9 18 15 12 9 6"></polyline>
                                                </svg>
                                            </a>
                                        </div>

                                    </div>

                                </div>


                            </div>

                        </div>

                    <#else>
                        Здесь пока ничего нет
                    </#if>
                </#list>
            </div>
        </div>
    </main>

    <div class="modal fade" id="modal-invite" tabindex="-1" aria-labelledby="modal-invite" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered modal-fullscreen-xl-down">
            <div class="modal-content">

                <!-- Modal: Body -->
                <div class="modal-body py-0">
                    <!-- Header -->
                    <div class="profile modal-gx-n">
                        <div class="profile-img text-primary rounded-top-xl">
                            <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 400 140.74">
                                <defs>
                                    <style>.cls-2 {
                                            fill: #fff;
                                            opacity: 0.1;
                                        }</style>
                                </defs>
                                <g>
                                    <g>
                                        <path d="M400,125A1278.49,1278.49,0,0,1,0,125V0H400Z"/>
                                        <path class="cls-2"
                                              d="M361.13,128c.07.83.15,1.65.27,2.46h0Q380.73,128,400,125V87l-1,0a38,38,0,0,0-38,38c0,.86,0,1.71.09,2.55C361.11,127.72,361.12,127.88,361.13,128Z"/>
                                        <path class="cls-2"
                                              d="M12.14,119.53c.07.79.15,1.57.26,2.34v0c.13.84.28,1.66.46,2.48l.07.3c.18.8.39,1.59.62,2.37h0q33.09,4.88,66.36,8,.58-1,1.09-2l.09-.18a36.35,36.35,0,0,0,1.81-4.24l.08-.24q.33-.94.6-1.9l.12-.41a36.26,36.26,0,0,0,.91-4.42c0-.19,0-.37.07-.56q.11-.86.18-1.73c0-.21,0-.42,0-.63,0-.75.08-1.51.08-2.28a36.5,36.5,0,0,0-73,0c0,.83,0,1.64.09,2.45C12.1,119.15,12.12,119.34,12.14,119.53Z"/>
                                        <circle class="cls-2" cx="94.5" cy="57.5" r="22.5"/>
                                        <path class="cls-2" d="M276,0a43,43,0,0,0,43,43A43,43,0,0,0,362,0Z"/>
                                    </g>
                                </g>
                            </svg>

                            <div class="position-absolute top-0 start-0 p-5">
                                <button type="button" class="btn-close btn-close-white btn-close-arrow opacity-100"
                                        data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                        </div>

                        <div class="profile-body">
                            <div class="avatar avatar-lg">
                                    <span class="avatar-text bg-primary">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24"
                                             viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"
                                             stroke-linecap="round" stroke-linejoin="round"
                                             class="feather feather-user-plus"><path
                                                    d="M16 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path><circle
                                                    cx="8.5" cy="7" r="4"></circle><line x1="20" y1="8" x2="20"
                                                                                         y2="14"></line><line x1="23"
                                                                                                              y1="11"
                                                                                                              x2="17"
                                                                                                              y2="11"></line></svg>
                                    </span>
                            </div>

                            <h4 class="fw-bold mb-1">Invite your friends</h4>
                            <p style="font-size: 16px;">Send invitation links to your friends</p>
                        </div>
                    </div>
                    <!-- Header -->

                    <hr class="hr-bold modal-gx-n my-0">

                    <!-- Form -->
                    <div class="modal-py">
                        <form class="row gy-6">
                            <div class="col-12">
                                <label for="invite-email" class="form-label text-muted">Название курса</label>
                                <input type="email" class="form-control form-control-lg" id="course-name"
                                       placeholder="name@example.com">
                            </div>

                            <#--<div class="col-12">
                                <label for="invite-message" class="form-label text-muted">Message</label>
                                <textarea class="form-control form-control-lg" id="invite-message" rows="3" placeholder="Custom message"></textarea>
                            </div>-->
                        </form>
                    </div>
                    <!-- Form -->

                    <hr class="hr-bold modal-gx-n my-0">

                    <!-- Button -->
                    <div data-bs-dismiss="modal" aria-label="Close" onclick="addCourse()" class="modal-py">
                        <a href="#" class="btn btn-lg btn-primary w-100 d-flex align-items-center"
                           data-bs-toggle="modal" data-bs-target="#invite-modal">
                            Send

                            <span class="icon ms-auto">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"
                                         fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"
                                         stroke-linejoin="round" class="feather feather-chevron-right"><polyline
                                                points="9 18 15 12 9 6"></polyline></svg>
                                </span>
                        </a>
                    </div>
                    <!-- Button -->
                </div>
                <!-- Modal: Body -->

            </div>
        </div>
    </div>

    <script src="/static/addCourse.js"></script>

</@e.page>