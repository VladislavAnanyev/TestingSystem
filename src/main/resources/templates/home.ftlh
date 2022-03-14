<#import "parts/common.ftlh" as e>
<#include "parts/security.ftlh">

<@e.page>
    <head>
        <title>Главная</title>
    <meta name="yandex-verification" content="135f209071de02b1" />
    </head>
    <div class="jumbotron shadow rounded">
        <h1 class="display-4">Система Web-викторин</h1>
        <p class="lead"> Добавляйте свои викторины, отвечайте на чужие, просматривайте статистику и многое другое.</p>
        <hr class="my-4">
        <#if !nowUser??>
        <p>Зарегистрируйтесь, чтобы получить возможность общаться и взаимодействовать с викторинами.</p>
        <a class="btn btn-primary" href="/reg" role="button">Регистрация</a>
        <a class="btn btn-primary" href="/signin" role="button">Войти</a>

            <#else >
                <p>Добавьте свою или попробуйте ответить на чужие викторины</p>
                <a class="btn btn-primary" href="/quizzes" role="button">Пройти викторину</a>
                <a class="btn btn-primary" href="/add" role="button">Добавить викторину</a>
        </#if>
    </div>






</@e.page>
