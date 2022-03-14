function chartpie(id) {


    $(document).ready(function () {
        let xhr = new XMLHttpRequest();

        xhr.open('GET', '/api/quizzes/' + id +'/solve/info', true);
        xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
        xhr.onreadystatechange = function () {
            if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {

                let countFalse = xhr.response
                console.log(countFalse)
                console.log(countFalse)
                console.log(countFalse)
                console.log(countFalse)
                console.log(countFalse)

            }
        };
        xhr.send(${test_id.id?c});
        var ctx = $("#chart-line");
        var myLineChart = new Chart(ctx, {
            type: 'doughnut',
            data: {
                labels: ["Правильно", "Неправильно"],
                datasets: [{
                    data: [2000, 2000],
                    backgroundColor: ["rgba(100, 255, 0, 0.5)", "rgba(255, 0, 0, 0.5)"]
                }]
            },
            options: {
                title: {
                    display: true,
                    text: 'Weather'
                }
            }
        });
    });
}