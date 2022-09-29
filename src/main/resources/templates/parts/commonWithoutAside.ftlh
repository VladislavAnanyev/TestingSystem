<#macro page>
    <!DOCTYPE html>
    <html lang="ru">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport"
              content="width=device-width, initial-scale=1.0, user-scalable=no, maximum-scale=1, shrink-to-fit=no, viewport-fit=cover">
        <title>Система тестирования</title>

        <!-- Favicon -->
        <#--    <link rel="shortcut icon" href="./assets/img/favicon/favicon.ico" type="image/x-icon">-->

        <!-- Font -->
        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700" rel="stylesheet">

        <!-- Template CSS -->
        <link rel="stylesheet" href="/static/template.bundle.css">
        <#--        <link rel="stylesheet" href="/static/template.dark.bundle.css" media="(prefers-color-scheme: dark)">-->
        <#--        <style>body {position: relative; height: 100%;}</style>-->
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

    </div>

    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

    <script src="/static/vendor.js"></script>
    <script src="/static/template.js"></script>
    <input type="file" multiple="multiple" class="dz-hidden-input" tabindex="-1"
           style="visibility: hidden; position: absolute; top: 0px; left: 0px; height: 0px; width: 0px;">

    </body>


    </html>
</#macro>
