<#import "parts/common.ftlh" as c>
<@c.page>
    <title>Изменить викторину</title>
    <script src="/static/putreq.js"></script>
    <script src="/static/addOptions.js"></script>
    <script src="/static/addQuiz.js"></script>

    <div id="TestTittle">
        <form id="checkForm0" class="checkFormCl">
    <div class="form-group mt-4">
        <label for="exampleFormControlInput0">Название</label>
        <input type="text" class="form-control" id="exampleFormControlInput0" value="${oldTest.description}" name="description">
    </div>
        </form>
    </div>

    <#list oldTest.quizzes as oldQuiz>

    <div id="${oldQuiz_index + 1}Id" class="quiz">



        <div class="form-group mt-4">
            <label for="exampleFormControlInput1">Тема</label>
            <input type="text" class="form-control" id="exampleFormControlInput1" value="${oldQuiz.title}" name="title">
        </div>

        <div class="form-group">
            <label for="exampleFormControlInput2">Вопрос</label>
            <input type="text" class="form-control" id="exampleFormControlInput2" value="${oldQuiz.text}"  name="text">
        </div>


    <#--<div class="options" id="optionstest">
        <label for="exampleFormControlInput1">Варианты ответа</label>
        <div class="custom-control custom-checkbox" id="1Id1">
            <input type="checkbox" class="custom-control-input 1input" id="1customCheck1" name="1check" value="0">
            <label class="custom-control-label 1opt" id="1label1"  for="1customCheck1">
                <input type="text" class="form-control" id="1options1" placeholder="1)" name="1options">
            </label>
            <button onclick="removeOptions(1,1)" class="btn btn-primary mt-3 1butt">Удалить вариант</button>

        </div>-->

        <div class="options" id="${oldQuiz_index+1}optionstest">
            <label for="exampleFormControlInput3">Варианты ответа</label>
        <#list oldQuiz.options as opt>

        <div class="custom-control custom-checkbox mt-2" id="${oldQuiz_index+1}Id${opt_index+1}">
            <input type="checkbox" class="custom-control-input ${oldQuiz_index+1}input" id="${oldQuiz_index+1}customCheck${opt_index+1}" name="${oldQuiz_index+1}check"  value="${opt_index}" <#list oldQuiz.answer as ans> <#if ans == opt_index>checked</#if> </#list> >
            <label class="custom-control-label ${oldQuiz_index+1}opt" id="${oldQuiz_index+1}label${opt_index+1}" for="${oldQuiz_index+1}customCheck${opt_index+1}">
                <input type="text" class="form-control" value="${opt}" id="${oldQuiz_index+1}options${opt_index+1}" name="${oldQuiz_index+1}options">
            </label>
            <button onclick="removeOptions(${oldQuiz_index+1},${opt_index + 1})" class="btn btn-primary mt-3 ${oldQuiz_index+1}butt">Удалить вариант</button>
        </div>

        </#list>

        </div>

        <div>
            <button onclick="addOptions(${oldQuiz_index+1})" class="btn btn-primary mt-2">Добавить вариант</button>
        </div>



    </div>
    </#list>


<#--        <form method="get" action="/myquiz" >-->
        <button  onclick="updateQuiz(${oldTest.id?c})" type="submit" class="btn btn-primary mt-2">Изменить</button>
<#--        </form>-->



</@c.page>