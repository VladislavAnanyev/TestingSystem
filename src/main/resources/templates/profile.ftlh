<#import "parts/common.ftlh" as e>
<#include "parts/security.ftlh">

    <#--<head>
        <meta charset="UTF-8">
        <title>Профиль</title>
    </head>-->
<@e.page>
    <title>Профиль</title>
<#--    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>-->
    <script src="/static/changePersonalInfo.js"></script>
    <script src="/static/changePassword.js"></script>
    <script src="/static/emailMessageForChangePassword.js"></script>

<#--    <form>-->

    <#--<div class="mt-2 mr-4">Баланс: &lt;#&ndash;<i class="bi bi-cash-coin mr-1">&ndash;&gt;${balance}&lt;#&ndash;</i>&ndash;&gt;



    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" class="bi bi-cash-coin mt-1" viewBox="0 0 16 16">
        <path fill-rule="evenodd" d="M11 15a4 4 0 1 0 0-8 4 4 0 0 0 0 8zm5-4a5 5 0 1 1-10 0 5 5 0 0 1 10 0z"/>
        <path d="M9.438 11.944c.047.596.518 1.06 1.363 1.116v.44h.375v-.443c.875-.061 1.386-.529 1.386-1.207 0-.618-.39-.936-1.09-1.1l-.296-.07v-1.2c.376.043.614.248.671.532h.658c-.047-.575-.54-1.024-1.329-1.073V8.5h-.375v.45c-.747.073-1.255.522-1.255 1.158 0 .562.378.92 1.007 1.066l.248.061v1.272c-.384-.058-.639-.27-.696-.563h-.668zm1.36-1.354c-.369-.085-.569-.26-.569-.522 0-.294.216-.514.572-.578v1.1h-.003zm.432.746c.449.104.655.272.655.569 0 .339-.257.571-.709.614v-1.195l.054.012z"/>
        <path d="M1 0a1 1 0 0 0-1 1v8a1 1 0 0 0 1 1h4.083c.058-.344.145-.678.258-1H3a2 2 0 0 0-2-2V3a2 2 0 0 0 2-2h10a2 2 0 0 0 2 2v3.528c.38.34.717.728 1 1.154V1a1 1 0 0 0-1-1H1z"/>
        <path d="M9.998 5.083 10 5a2 2 0 1 0-3.132 1.65 5.982 5.982 0 0 1 3.13-1.567z"/>
    </svg>
    </div>-->

    <div class="form-row mt-2" id="profile">
        <div class="col-md-6 mb-3">
            <label for="validationDefault01">Имя</label>
            <input type="text" class="form-control" id="validationDefault01" value="${user.firstName}" name="firstName" required>
        </div>
        <div class="col-md-6 mb-3">
            <label for="validationDefault02">Фамилия</label>
            <input type="text" class="form-control" id="validationDefault02" value="${user.lastName}" name="lastName" required>
        </div>
        <div class="col-md-6 mb-3">
            <label for="validationDefault03">Почта</label>
            <input type="text" class="form-control" id="validationDefault03" disabled value="${user.email}" name="email" required>
        </div>
        <div class="col-md-6 mb-3">
            <label for="validationDefault04">Статус аккаунта</label>
            <input type="text" class="form-control" id="validationDefault04" disabled value="${user.status?c}" name="email" required>
        </div>





        <div class="col-md-6 mb-3">
            <form method="post" enctype="multipart/form-data" action="/upload">
                <label for="formFile" class="form-label">Фотография</label>
                <input class="form-control" type="file" id="formFile" name="file" required>
                <label for="test" class="mt-2">Нажмите чтобы загрузить</label>
                <button type="submit"  class="btn btn-primary form-control" id="test">Отправить</button>
            </form>
        </div>

        <div>


                <img class="rounded-circle" width="100px" height="100px" src="${user.avatar}" alt="${user.avatar}">




        </div>



    </div>

   <#-- <div>
        <video controls width="400" height="300" src="../../../../video/drownedInst.mp4"></video>
    </div>-->

    <div class="form-group">
        <label for="exampleInputEmail1">Логин</label>
        <input type="text" class="form-control" id="exampleInputEmail1" disabled value="${user.username}" aria-describedby="emailHelp" name="username">
        <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
    </div>


    <button onclick="changePersonalInfo()" id="confirm" type="submit" class="btn btn-primary ml-2 my-1">Подтвердить</button>




    <!-- Button trigger modal -->
    <button type="button" id="bbb" class="btn btn-primary ml-2 my-1" data-toggle="modal" data-target="#staticBackdrop">

        Изменить пароль
    </button>





    <div class="col-md-6 mb-3">
        <label for="validationDefault05">Онлайн</label>
        <input type="text" class="form-control" id="validationDefault05" disabled value="${user.online}" name="online" required>
    </div>

    <div class="modal fade" id="staticBackdrop" data-backdrop="static" data-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="staticBackdropLabel">Изменение пароля</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div class="form-group"> Вы действительно хотите изменить пароль?

<#--                        <input type="text" class="form-control" id="oldpassword" placeholder="Придумайте новый пароль"  aria-describedby="emailHelp" name="password">-->
                        <#--<input type="text" class="form-control" id="password1" placeholder="Придумайте новый пароль"  aria-describedby="emailHelp" name="password1">
                        <input type="text" class="form-control mt-3" id="password2" placeholder="Введите новый пароль ещё раз" aria-describedby="emailHelp" name="password2">-->
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Назад</button>
                    <button type="button" onclick="sendEmailMessage()" data-dismiss="modal" class="btn btn-primary">Да</button>
                </div>

            </div>
        </div>



    </div>





</@e.page>