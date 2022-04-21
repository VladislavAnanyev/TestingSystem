<#import "parts/commonWithoutAside.ftlh" as c>
<@c.page>


    <aside class="sidebar bg-light">
        <div class="container h-100">
            <div class="card border-4 mt-3">
                <div class="card-body">
                    <div class="row align-items-center gx-0">
                        <div class="col">
                            <h4 class="mb-1">Количество участников: ${members?size}</h4>
                        </div>
                        <div class="col-auto">
                            <a href="#<#--/course/${courseId?c}/members/add-->"
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
        </div>
    </aside>


    <main class="main is-visible">
        <div class="container h-100 mt-4 ml-2 chat-body hide-scrollbar flex-1 scrollspy-example" xmlns="">
            <#--            <div class="table-responsive-md">-->
            <div class="card mb-3 shadow p-3 mb-5 bg-white rounded">
                <div class="card-body">
                    <table class="table table-hover mr-5 table-sm align-middle text-center">
                        <thead>
                        <tr>
                            <th scope="col">№</th>
                            <th scope="col">Имя</th>
                            <th scope="col">Фамилия</th>
                            <th scope="col">Почта</th>
                            <th scope="col">Процент</th>
                            <th scope="col">Действие</th>
                        </tr>
                        </thead>
                        <tbody>


                        <#list members as answer>

                            <tr>
                                <th scope="row">${answer_index + 1}</th>
                                <td>${answer.firstName}</td>
                                <td>${answer.lastName}</td>
                                <#--            <td>${answer.completedAt.time?datetime}</td>-->
                                <td><a href="/about/${answer.email}">${answer.email}</a></td>

                                <td>

                                    <div class="progress <#--mt-4-->" style="height: 20px;">
                                        <div class="progress-bar" role="progressbar"
                                             style="width: ${answer.percentageOfComplete}%;"
                                             aria-valuenow="${answer.percentageOfComplete}" aria-valuemin="-1"
                                             aria-valuemax="100">
                                            ${answer.percentageOfComplete}%
                                        </div>
                                    </div>


                                </td>

                                <td>
                                    <a onclick="writeMsg('${answer.userId?c}')"
                                       class="btn btn-sm btn-icon btn-primary rounded-circle ">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24"
                                             viewBox="0 0 24 24"
                                             fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"
                                             stroke-linejoin="round" class="feather feather-mail">
                                            <path d="M4 4h16c1.1 0 2 .9 2 2v12c0 1.1-.9 2-2 2H4c-1.1 0-2-.9-2-2V6c0-1.1.9-2 2-2z"></path>
                                            <polyline points="22,6 12,13 2,6"></polyline>
                                        </svg>
                                    </a>
                                </td>


                            </tr>


                        </#list>

                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <#--        </div>-->
    </main>

    <script src="/static/newActiveDialog.js"></script>

</@c.page>