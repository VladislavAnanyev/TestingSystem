<#import "parts/commonWithoutAside.ftlh" as e>

<@e.page>
    <title>Мои викторины</title>

    <aside class="sidebar bg-light">
        <div class="container h-100">
            <div class="card border-4 mt-3">
                <div class="card-body">
                    <div class="row align-items-center gx-0">
                        <div class="col">
                            <h4 class="mb-1">Пригласить участника</h4>
                        </div>
                        <div class="col-auto">
                            <a <#--href="/course/${courseId?c}/members/add" -->data-bs-toggle="modal"
                               data-bs-target="#modal-invite"
                               class="btn btn-sm btn-icon btn-primary rounded-circle">
                                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"
                                     fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"
                                     stroke-linejoin="round" class="feather feather-user-plus">
                                    <path d="M16 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path>
                                    <circle cx="8.5" cy="7" r="4"></circle>
                                    <line x1="20" y1="8" x2="20" y2="14"></line>
                                    <line x1="23" y1="11" x2="17" y2="11"></line>
                                </svg>
                            </a>
                        </div>
                    </div>
                </div>
            </div>

            <div class="card border-4 mt-3">
                <div class="card-body">
                    <div class="row align-items-center gx-0">
                        <div class="col">
                            <h4 class="mb-1">Статистика по курсу</h4>
                        </div>
                        <div class="col-auto">
                            <a href="/course/${courseId?c}/info"
                               class="btn btn-sm btn-icon btn-primary rounded-circle">
                                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"
                                     fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"
                                     stroke-linejoin="round" class="feather feather-trending-up">
                                    <polyline points="23 6 13.5 15.5 8.5 10.5 1 18"></polyline>
                                    <polyline points="17 6 23 6 23 12"></polyline>
                                </svg>
                            </a>
                        </div>
                    </div>
                </div>
            </div>

            <div class="card border-4 mt-3">
                <div class="card-body">
                    <div class="row align-items-center gx-0">
                        <div class="col">
                            <h4 class="mb-1">Добавить тест</h4>
                        </div>
                        <div class="col-auto">
                            <a href="/course/${courseId?c}/test/create"
                               class="btn btn-sm btn-icon btn-primary rounded-circle">
                                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24"
                                     viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"
                                     stroke-linecap="round" stroke-linejoin="round"
                                     class="feather feather-plus">
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
            <div class="row">
                <#list myquiz as test>
                    <div class="col-sm-6">
                        <div class="card mb-3 shadow p-3 mb-5 bg-white rounded">
                            <div id="${test.testId?c}">

                                <div class="card-body" id="thisid">
                                    <div class="row">

                                        <div class="col">
                                            <h5 class="card-title">${test.description}</h5>
                                            <p class="card-text">Количество вопросов: ${test.numberOfQuestions}</p>
                                            <#--                            <p class="card-text">Количество ответов пользователей: ${test.answers?size}</p>-->

                                            <#-- <p class="card-text"><small class="text-muted">Автор: <a
                                                             href="/about/${test.author}">
                                                         ${test.author}
                                                     </a></small></p>-->
                                        </div>

                                        <div class="col">
                                            <div class="row">

                                                        <div onclick="location.href='/quizzes/${test.testId?c}/info'" class="btn btn-primary button">
                                                            <svg xmlns="http://www.w3.org/2000/svg" width="16"
                                                                 height="16"
                                                                 fill="currentColor"
                                                                 class="bi bi-bar-chart-fill mb-1 mr-1"
                                                                 viewBox="0 0 16 16">
                                                                <path d="M1 11a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1v3a1 1 0 0 1-1 1H2a1 1 0 0 1-1-1v-3zm5-4a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1v7a1 1 0 0 1-1 1H7a1 1 0 0 1-1-1V7zm5-5a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1v12a1 1 0 0 1-1 1h-2a1 1 0 0 1-1-1V2z"></path>
                                                            </svg>
                                                            Статистика
                                                        </div>




                                                        <div onclick="location.href='/update/${test.testId?c}'" class="btn btn-primary button mt-2">
                                                            <svg xmlns="http://www.w3.org/2000/svg"
                                                                 width="16"
                                                                 height="16"
                                                                 fill="currentColor" class="bi bi-pencil-fill mb-1 mr-1"
                                                                 viewBox="0 0 16 16">
                                                                <path d="M12.854.146a.5.5 0 0 0-.707 0L10.5 1.793 14.207 5.5l1.647-1.646a.5.5 0 0 0 0-.708l-3-3zm.646 6.061L9.793 2.5 3.293 9H3.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.207l6.5-6.5zm-7.468 7.468A.5.5 0 0 1 6 13.5V13h-.5a.5.5 0 0 1-.5-.5V12h-.5a.5.5 0 0 1-.5-.5V11h-.5a.5.5 0 0 1-.5-.5V10h-.5a.499.499 0 0 1-.175-.032l-.179.178a.5.5 0 0 0-.11.168l-2 5a.5.5 0 0 0 .65.65l5-2a.5.5 0 0 0 .168-.11l.178-.178z"/>
                                                            </svg>
                                                            Изменить
                                                        </div>



                                                        <div onclick="deleteQuiz(${test.testId?c})"
                                                                class="btn btn-primary button mt-2">
                                                            <svg onclick="deleteQuiz(${test.testId?c})"
                                                                 xmlns="http://www.w3.org/2000/svg"
                                                                 width="16" height="16" fill="currentColor"
                                                                 class="bi bi-archive-fill 1butt mb-1 mr-1"
                                                                 viewBox="0 0 16 16">
                                                                <path d="M12.643 15C13.979 15 15 13.845 15 12.5V5H1v7.5C1 13.845 2.021 15 3.357 15h9.286zM5.5 7h5a.5.5 0 0 1 0 1h-5a.5.5 0 0 1 0-1zM.8 1a.8.8 0 0 0-.8.8V3a.8.8 0 0 0 .8.8h14.4A.8.8 0 0 0 16 3V1.8a.8.8 0 0 0-.8-.8H.8z"/>
                                                            </svg>
                                                            Удалить
                                                        </div>


                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>
                <#else>
                    Здесь пока ничего нет
                <#--        </#list>-->
                </#list>
            </div>

        </div>
    </main>
    <script src="/static/deletereq.js"></script>

    <!-- Modal: Invite -->
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

                            <h4 class="fw-bold mb-1">Добавить участника</h4>
                            <p style="font-size: 16px;">Отправить приглашение для вступления</p>
                        </div>
                    </div>
                    <!-- Header -->

                    <hr class="hr-bold modal-gx-n my-0">

                    <!-- Form -->
                    <div class="modal-py">
                        <form class="row gy-6">
                            <div class="col-12">
                                <label for="invite-email" class="form-label text-muted">E-mail</label>
                                <input type="email" class="form-control form-control-lg" id="invite-email"
                                       placeholder="name@example.com">
                            </div>

                            <div class="col-12">
                                <label for="invite-message" class="form-label text-muted">Группа</label>
                                <textarea class="form-control form-control-lg" id="invite-message" rows="3" placeholder="Введите имя группы"></textarea>
                            </div>
                        </form>
                    </div>
                    <!-- Form -->

                    <hr class="hr-bold modal-gx-n my-0">

                    <!-- Button -->
                    <div data-bs-dismiss="modal" aria-label="Close" onclick="addMember()" class="modal-py">
                        <a class="btn btn-lg btn-primary w-100 d-flex align-items-center" <#--data-bs-toggle="modal" data-bs-target="#invite-modal"-->>
                            Отправить

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
    <input type="hidden" id="courseId" value="${courseId?c}">
    <script src="/static/addMember.js"></script>


</@e.page>