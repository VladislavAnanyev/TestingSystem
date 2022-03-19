<#import "parts/common.ftlh" as c>
<#include "parts/security.ftlh">
<@c.page>
    <title>Создать курс</title>
    <script src="/static/addTest.js"></script>
    <script src="/static/addOptions.js"></script>
    <script src="/static/addCourse.js"></script>
    <script src="/static/deletereq.js"></script>

    <div id="TestTittle">
        <form id="checkForm0" class="checkFormCl">
            <div class="form-group">
                <label for="exampleFormControlInput1 validationDefault01">Название</label>
                <input required type="text" class="form-control" id="description validationDefault01" placeholder="Напишите здесь название курса" name="courseName">
            </div>
        </form>
    </div>

    <div id="addCourse">

        <#--    <form method="get" action="/" >-->
        <#if nowUser??>
            <button onclick="addCourse()" type="submit" class="btn btn-primary mb-2 mt-2">Создать курс</button>
        <#else >
            <button onclick="location.href='/signin'" type="submit" class="btn btn-primary mb-2 mt-2">Создать курс</button>

        </#if>
        <#--    </form>-->
    </div>


</@c.page>