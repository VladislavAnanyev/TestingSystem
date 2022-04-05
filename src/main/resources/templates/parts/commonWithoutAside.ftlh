<#macro page>
    <!DOCTYPE html>
    <html lang="ru">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport"
              content="width=device-width, initial-scale=1.0, user-scalable=no, maximum-scale=1, shrink-to-fit=no, viewport-fit=cover">
        <title>Messenger - 2.0.1</title>

        <!-- Favicon -->
        <#--    <link rel="shortcut icon" href="./assets/img/favicon/favicon.ico" type="image/x-icon">-->

        <!-- Font -->
        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700" rel="stylesheet">

        <!-- Template CSS -->
        <link rel="stylesheet" href="/static/template.bundle.css">
        <link rel="stylesheet" href="/static/template.dark.bundle.css" media="(prefers-color-scheme: dark)">
    </head>
    <#--<body>-->
    <body>

    <#if nowUser??>


        <script>
            // notificationConnect()
        </script>


    </#if>
    <!-- Layout -->
    <div class="layout overflow-hidden">
        <!-- Navigation -->
        <#include "navbar.ftlh">

        <#nested>
        <!-- Navigation -->

        <#--    <aside class="sidebar bg-light">-->
        <#--    </aside>-->

        <#--<div class="container">
            <#nested>
        </div>-->

        <#--    нормальная совместимость-->

        <#--<aside class="sidebar bg-light">
            <#if test_id??>

                <div class="container h-100">

                    <#if test_id.duration??>
                        <input type="hidden" id="time" value="${timeout?datetime?string ["MM.dd.yyyy HH:mm:ss"]}">
                    </#if>

                    <#if test_id.duration??>
                        <div class="alltime mb-3" id="clock">
                            <h1 class="countdown-title">Осталось времени</h1>
                            <div id="countdown" class="countdown">
                                <div class="countdown-number">
                                    <span class="days countdown-time"></span>
                                    <span class="countdown-text">Дни</span>
                                </div>
                                <div class="countdown-number">
                                    <span class="hours countdown-time"></span>
                                    <span class="countdown-text">Часы</span>
                                </div>
                                <div class="countdown-number">
                                    <span class="minutes countdown-time"></span>
                                    <span class="countdown-text">Минуты</span>
                                </div>
                                <div class="countdown-number">
                                    <span class="seconds countdown-time"></span>
                                    <span class="countdown-text">Секунды</span>
                                </div>
                            </div>
                        </div>
                    </#if>

                    <div class="progress mt-5" style="height: 20px;">
                        <div class="progress-bar" role="progressbar" style="width: 25%;" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100">25%</div>
                    </div>

                    <div class="d-flex flex-column h-100 justify-content-center text-center">
                        <nav aria-label="...">
                            <ul class="pagination pagination">
                                <#list test_id.quizzes as quiz>

                                &lt;#&ndash;<div id="list-example" class="list-group">
                                    <a class="list-group-item list-group-item-action" href="#list-item-1">Элемент 1</a>
                                    <a class="list-group-item list-group-item-action" href="#list-item-2">Элемент 2</a>
                                    <a class="list-group-item list-group-item-action" href="#list-item-3">Элемент 3</a>
                                    <a class="list-group-item list-group-item-action" href="#list-item-4">Элемент 4</a>
                                </div>&ndash;&gt;
                                &lt;#&ndash;<div data-bs-spy="scroll" data-bs-target="#list-example" data-bs-offset="0" class="scrollspy-example" tabindex="0">
                                    <h4 id="list-item-1">Элемент 1</h4>
                                    <p>...</p>
                                    <h4 id="list-item-2">Элемент 2</h4>
                                    <p>...</p>
                                    <h4 id="list-item-3">Элемент 3</h4>
                                    <p>...</p>
                                    <h4 id="list-item-4">Элемент 4</h4>
                                    <p>...</p>
                                </div>&ndash;&gt;

                                    <li class="page-item &lt;#&ndash;active&ndash;&gt;" aria-current="page">
                                        <a class="&lt;#&ndash;list-group-item list-group-item-action&ndash;&gt;" href="#list-item-${quiz_index + 1}">
                                            <span class="page-link">${quiz_index + 1}</span>
                                        </a>
                                    </li>
                                </#list>

                            </ul>

                        </nav>

                    </div>

                    <#if test_id.duration??>


                        <script src="/static/downtimer.js"></script>

                    </#if>

                    <script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.4.0/sockjs.js"></script>
                    <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
                    <script src="/static/timeover.js"></script>
                    &lt;#&ndash;                    <script src="/static/subscribe.js"></script>&ndash;&gt;
                    <script>connect()</script>
                </div>
            </#if>
        </aside>


        <main class="main">
            <div class="container h-100 mt-2 ml-2 chat-body hide-scrollbar flex-1">
                <#nested>

            </div>

        </main>-->
    </div>

    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

    <script src="/static/vendor.js"></script>
    <script src="/static/template.js"></script>


    </body>


    </html>
</#macro>
