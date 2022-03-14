<#import "parts/common.ftlh" as c>
<@c.page>




    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>

    <div id="top_x_div" style="height: 278px" class="table-responsive"></div>



    <script type="text/javascript">
        google.charts.load('current', {'packages':['bar']});
        google.charts.setOnLoadCallback(drawStuff);

        //console.log("")
        function drawStuff() {
            var data = new google.visualization.arrayToDataTable([
                ['Тема', 'Процент'],
                <#if chart??>
                <#list chart?values as chartd>
                ['${quizzes[chartd_index].title}' + "", ${chartd?c}],
                </#list>
                </#if>
                /*["Knight to King 3 (Nf3)", 12],
                ["Queen's bishop pawn (c4)", 10],
                ['Other', 3]*/
            ]);

            var options = {
                width: 300,
                legend: { position: 'none' },
                chart: {
                    title: 'Средний процент выполнения',
                    subtitle: 'По каждому из вопросов' },
                axes: {
                    x: {
                        0: { side: 'top', label: 'Вопросы'} // Top x-axis.
                    }
                },
                bar: { groupWidth: "90%" }
            };

            var chart = new google.charts.Bar(document.getElementById('top_x_div'));
            // Convert the Classic options to Material options.
            chart.draw(data, google.charts.Bar.convertOptions(options));
        };

    </script>


        <!-- Content here -->


    <title>Статистика</title>
<#--    <div>${stat}%</div>-->

    <div class="table-responsive-md">
    <table class="table table-hover mr-5">
        <thead>
        <tr>
            <th scope="col">№</th>
            <th scope="col">Имя</th>
            <th scope="col">Фамилия</th>
            <th scope="col">Логин</th>
            <th scope="col">Время</th>
            <th scope="col">Процент</th>
        </tr>
        </thead>
        <tbody>


        <#list answersOnQuiz as answer>

            <tr>
                <th scope="row">${answer_index + 1}</th>
                <td>${answer.user.firstName}</td>
                <td>${answer.user.lastName}</td>
                <#--            <td>${answer.completedAt.time?datetime}</td>-->
                <td><a href="/about/${answer.user.username}">${answer.user.username}</a></td>
                <td>
                    <#if answer.completedAt??>
                        ${answer.completedAt.time?datetime?string ["dd.MM.yyyy HH:mm:ss"]}
                        <#else>
                        -
                    </#if>

                </td>
                <#--            <td>${answer.status?c}</td>&ndash;&gt;-->



                <td>
                    <#if answer.percent??>
                        ${answer.percent}
                    <#else>
                        -
                    </#if>
                </td>


            </tr>


        </#list>

        </tbody>
    </table>
    </div>





</@c.page>