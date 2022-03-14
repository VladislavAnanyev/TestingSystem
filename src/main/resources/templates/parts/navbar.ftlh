<#include "security.ftlh">
<#import "../singin.ftlh" as e>

<main data-barba-namespace="navig">
<nav class="navbar navbar-expand-lg navbar-light bg-light shadow p-3 mb-5 rounded">




    <a data-barba-prevent class="navbar-brand" href="/">
        WebQuizzes</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse " id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <#--<li class="nav-item">
                <a class="nav-link" href="/reg">Регистрация</a>
            </li>-->
            <li class="nav-item mt-1">
                <a data-barba-prevent class="nav-link"  href="/quizzes">Все викторины</a>
            </li>
            <#--<li class="nav-item">
                <a class="nav-link" href="">Завершённые</a>
            </li>-->
            <li class="nav-item mt-1">
                <a data-barba-prevent class="nav-link" href="/add">Добавить викторину</a>
            </li>
            <#if nowUser??>
            <li class="nav-item mt-1">
                <a data-barba-prevent class="nav-link" href="/myquiz">Мои викторины</a>
            </li>
            <li class="nav-item mt-1">
                <a data-barba-prevent class="nav-link" href="/chat">Сообщения</a>
            </li>
            </#if>

            <#if isAdmin??>
                <li class="nav-item mt-1">
                    <a class="nav-link" href="/h2-console">База данных</a>
                </li>
            </#if>
        </ul>


        <#--<#global balance>
            ${balance2}
        </#global>-->




        <#if known>



        <div class="mt-2 mr-4"><i class="bi bi-cash-coin mr-1" id="balance"></i>


                <script>

                    xhr = new XMLHttpRequest();

                    xhr.open('GET', '/getbalance',true);
                    xhr.setRequestHeader('Content-type','application/json; charset=utf-8');
                    xhr.onreadystatechange = function () {
                        if(xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
                            document.getElementById("balance").textContent = xhr.response
                        }
                    };
                    xhr.send();

                </script>




            <a href="/checkout" style="color: black">
        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" class="bi bi-cash-coin mt-1" viewBox="0 0 16 16">
            <path fill-rule="evenodd" d="M11 15a4 4 0 1 0 0-8 4 4 0 0 0 0 8zm5-4a5 5 0 1 1-10 0 5 5 0 0 1 10 0z"/>
            <path d="M9.438 11.944c.047.596.518 1.06 1.363 1.116v.44h.375v-.443c.875-.061 1.386-.529 1.386-1.207 0-.618-.39-.936-1.09-1.1l-.296-.07v-1.2c.376.043.614.248.671.532h.658c-.047-.575-.54-1.024-1.329-1.073V8.5h-.375v.45c-.747.073-1.255.522-1.255 1.158 0 .562.378.92 1.007 1.066l.248.061v1.272c-.384-.058-.639-.27-.696-.563h-.668zm1.36-1.354c-.369-.085-.569-.26-.569-.522 0-.294.216-.514.572-.578v1.1h-.003zm.432.746c.449.104.655.272.655.569 0 .339-.257.571-.709.614v-1.195l.054.012z"/>
            <path d="M1 0a1 1 0 0 0-1 1v8a1 1 0 0 0 1 1h4.083c.058-.344.145-.678.258-1H3a2 2 0 0 0-2-2V3a2 2 0 0 0 2-2h10a2 2 0 0 0 2 2v3.528c.38.34.717.728 1 1.154V1a1 1 0 0 0-1-1H1z"/>
            <path d="M9.998 5.083 10 5a2 2 0 1 0-3.132 1.65 5.982 5.982 0 0 1 3.13-1.567z"/>
        </svg>
            </a>

        </div>
        </#if>

        <div class="dropdown">
            <a class="btn btn-secondary mt-1 dropdown-toggle mr-3" href="#" role="button" id="dropdownMenuLink autoUser" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
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