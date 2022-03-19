<#include "security.ftlh">
<#import "../singin.ftlh" as e>

<main data-barba-namespace="navig">
    <nav class="navbar navbar-expand-lg navbar-light bg-light shadow p-3 mb-5 rounded">


        <a data-barba-prevent class="navbar-brand" href="/">
            WebQuizzes</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse " id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <#--<li class="nav-item">
                    <a class="nav-link" href="/reg">Регистрация</a>
                </li>-->
                <li class="nav-item mt-1">
                    <a data-barba-prevent class="nav-link" href="/courses">Все курсы</a>
                </li>
                <#if nowUser??>
                    <li class="nav-item mt-1">
                        <a data-barba-prevent class="nav-link" href="/courses/my">Мои курсы</a>
                    </li>
                    <li class="nav-item mt-1">
                        <a data-barba-prevent class="nav-link" href="/chat">Сообщения</a>
                    </li>
                </#if>

<#--                <#if isAdmin??>-->
                    <li class="nav-item mt-1">
                        <a class="nav-link" href="/course/create">Создать курс</a>
                    </li>
<#--                </#if>-->

                <#if isAdmin??>
                    <li class="nav-item mt-1">
                        <a class="nav-link" href="/course/create">Создать курс</a>
                    </li>
                </#if>
            </ul>

            <div class="dropdown">
                <a class="btn btn-secondary mt-1 dropdown-toggle mr-3" href="#" role="button"
                   id="dropdownMenuLink autoUser" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    ${name}
                </a>

                <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                    <a data-barba-prevent class="dropdown-item" href="/profile">Профиль</a>
                    <#-- <a class="dropdown-item" href="#">Another action</a>
                     <a class="dropdown-item" href="#">Something else here</a>-->
                </div>
            </div>


            <#--        <div class = "navbar-text mr-3">${name}</div>-->
            <@e.logout />

        </div>


    </nav>
</main>