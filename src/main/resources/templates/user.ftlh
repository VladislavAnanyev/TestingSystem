<#import "parts/common.ftlh" as e>
<#include "parts/security.ftlh">

<@e.page>


    <title>Профиль</title>
<#--    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>-->
    <script src="/static/changePersonalInfo.js"></script>
    <script src="/static/changePassword.js"></script>
    <script src="/static/newActiveDialog.js"></script>

<#--    <form>-->
    <div class="form-row mt-2" id="profile">
        <div class="col-md-6 mb-3">
            <label for="validationDefault01">Имя</label>
            <input type="text" class="form-control" id="validationDefault01" disabled value="${user.firstName}" name="firstName" required>
        </div>
        <div class="col-md-6 mb-3">
            <label for="validationDefault02">Фамилия</label>
            <input type="text" class="form-control" id="validationDefault02" disabled value="${user.lastName}" name="lastName" required>
        </div>
        <div class="col-md-6 mb-3">
            <label for="validationDefault03">Почта</label>
            <input type="text" class="form-control" id="validationDefault03" disabled value="${user.email}" name="email" required>
        </div>
        <div class="col-md-6 mb-3">
            <label for="validationDefault03">Статус аккаунта</label>
            <input type="text" class="form-control" id="validationDefault04" disabled value="${user.status?c}" name="email" required>
        </div>
    </div>

    <div class="form-group">
        <label for="exampleInputEmail1">Логин</label>
        <input type="text" class="form-control" id="exampleInputEmail1" disabled value="${user.username}" aria-describedby="emailHelp" name="username">
        <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
    </div>

    <div class="col-md-6 mb-3">
        <label for="validationDefault05">Онлайн</label>
        <input type="text" class="form-control" id="validationDefault05" disabled value="${user.online}" name="online" required>
    </div>

    <#if nowUser??>
        <button onclick="writeMsg('${user.username}')" id="confirm" type="submit" class="btn btn-primary ml-2 my-1">Написать сообщение</button
    <#else >
        <button onclick="location.href='/signin'" type="submit" class="btn btn-primary mb-2 mt-2">Написать сообщение</button>

    </#if>


</@e.page>