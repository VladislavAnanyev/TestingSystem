<#import "parts/common.ftlh" as e>
<#include "parts/security.ftlh">

<@e.page>


    <input type="hidden" id="courseId" value="${courseId?c}">

    <div class="form-group">
        <label for="exampleInputEmail1">Почта</label>
        <input type="text" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" name="email">
    </div>


    <a class="btn btn-primary" onclick="addMember()" role="button">Добавить</a>

    <script src="/static/addMember.js"></script>
</@e.page>