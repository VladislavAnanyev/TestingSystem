
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
        <!-- Navigation -->

        <#--    <aside class="sidebar bg-light">-->
        <#--    </aside>-->

        <#--<div class="container">
            <#nested>
        </div>-->

        <#--    нормальная совместимость-->

        <aside class="sidebar bg-light">
            <#if test_id??>


            </#if>
        </aside>


        <main class="main">
            <div class="container h-100 mt-2 ml-2 chat-body hide-scrollbar flex-1">
                <#nested>
                <#--<div class="d-flex flex-column h-100 justify-content-center text-center">
                    <div class="mb-6">
                                <span class="icon icon-xl text-muted">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-message-square"><path d="M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z"></path></svg>
                                </span>
                    </div>

                    <p class="text-muted">Pick a person from left menu, <br> and start your conversation.</p>

                </div>-->
            </div>


        </main>
    </div>
    <!-- Layout -->


    <!-- Scripts -->
    <#--<script src="../static/vendor.js"></script>-->
    <#--<script src="../static/template.js"></script>-->
    <#--</body>-->

    <#--<div class="layout overflow-hidden">-->



    <#--<#include "navbar.ftlh">-->





    <#--</div>-->

    <#--<div class="container">


            <#nested>



        <div class="toast-container position-fixed bottom-0 end-0 p-3">
            <div id="liveToastMessage" class="toast fade" role="alert" aria-live="assertive" aria-atomic="true">
                <div class="toast-header">
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-envelope-fill" viewBox="0 0 16 16">
                        <path d="M.05 3.555A2 2 0 0 1 2 2h12a2 2 0 0 1 1.95 1.555L8 8.414.05 3.555zM0 4.697v7.104l5.803-3.558L0 4.697zM6.761 8.83l-6.57 4.027A2 2 0 0 0 2 14h12a2 2 0 0 0 1.808-1.144l-6.57-4.027L8 9.586l-1.239-.757zm3.436-.586L16 11.801V4.697l-5.803 3.546z"/>
                    </svg>
                    <strong class="me-auto ml-2" id="toast-head-msg">Bootstrap</strong>
                    <small class="text-muted">прямо сейчас</small>
                    <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Закрыть"></button>
                </div>
                <div class="toast-body" id="toast-text-msg">
                    Видите? Именно так.
                </div>
            </div>

        </div>


        <div class="toast-container position-fixed bottom-0 end-0 p-3">
            <div id="liveToastMeet" class="toast fade" role="alert" aria-live="assertive" aria-atomic="true">
                <div class="toast-header">
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-envelope-fill" viewBox="0 0 16 16">
                        <path d="M.05 3.555A2 2 0 0 1 2 2h12a2 2 0 0 1 1.95 1.555L8 8.414.05 3.555zM0 4.697v7.104l5.803-3.558L0 4.697zM6.761 8.83l-6.57 4.027A2 2 0 0 0 2 14h12a2 2 0 0 0 1.808-1.144l-6.57-4.027L8 9.586l-1.239-.757zm3.436-.586L16 11.801V4.697l-5.803 3.546z"/>
                    </svg>
                    <strong class="me-auto ml-2" id="toast-head-meet">Bootstrap</strong>
                    <small class="text-muted">прямо сейчас</small>
                    <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Закрыть"></button>
                </div>
                <div class="toast-body" id="toast-text-meet">
                    Видите? Именно так.
                </div>
            </div>
        </div>



    </div>-->



    <#--<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">-->
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>


    <#--Без этих трех строк не работает с телефона развертывание навбара-->
    <#--<script src="https://code.jquery.com/jquery-3.5.1.min.js" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>-->
    <#--<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>-->
    <#--<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>-->


    <#--<#if nowUser??>

    </#if>-->

    <#if test_id??>
        <#if nowUser??>
        <#--<#if !dialog??>-->
        <#--<script src="/static/notification.js"></script>-->
        <#--    <script>setTimeout(() => { notificationConnect();}, 5000);</script>-->
        <#--</#if>-->
        </#if>
    </#if>

    <#if !test_id??>
        <#if nowUser??>
            <#if !dialog??>
            <#--<script src="/static/notification.js"></script>-->
            <#--            <script>notificationConnect()</script>-->
            </#if>
        </#if>
    </#if>

    <#--<#if dialog??>
        <script src="/static/notification.js"></script>
        <script>setTimeout(() => { notificationConnect();}, 5000);</script>
    </#if>-->
    <#--<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-/bQdsTh/da6pkI1MST/rWKFNjaCP5gBSY4sEBT38Q/9RBh9AH40zEOg7Hlq2THRZ" crossorigin="anonymous"></script>-->

    <#--<script>$(function () {
            $('[data-toggle="tooltip"]').tooltip()
        })</script>-->


    <!-- Scripts -->



    <script src="/static/vendor.js"></script>
    <script src="/static/template.js"></script>


    </body>


    </html>
</#macro>
