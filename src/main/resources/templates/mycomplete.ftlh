
<#import "parts/common.ftlh" as e>

<@e.page>
    <div>
        <#list comp as quiz>
            <div class="card my-3">
                <div class="m-2">
                    <span>${quiz.test.title}</span>
                    <i>${quiz.test.text}</i>
                </div>
                <div class="card-footer text-muted">
                    ${quiz.status?c}
                </div>

            </div>
        <#else>
            No message
        </#list>
    </div>
</@e.page>