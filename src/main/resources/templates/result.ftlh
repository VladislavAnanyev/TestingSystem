<#import "parts/common.ftlh" as e>
<#include "parts/security.ftlh">

<@e.page>

    <div class="modal fade" id="staticBackdrop"
                                                   data-backdrop="static"
                                                   data-keyboard="false" tabindex="-1"
                                                   aria-labelledby="staticBackdropLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="staticBackdropLabel">Ваши результаты</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">

                    <div class="page-content page-container" id="page-content">
                        <div class="padding">
                            <div class="row">
                                <div class="container-fluid d-flex justify-content-center">
                                    <div class="col-sm-8 col-md-6">
                                        <div class="card justify-content-center">
                                            <#--<div class="card-header">Диаграмма</div>-->
                                            <div class="card-body" style="height: 420px">
                                                <div class="chartjs-size-monitor"
                                                     style="position: absolute; left: 0px; top: 0px; right: 0px; bottom: 0px; overflow: hidden; pointer-events: none; visibility: hidden; z-index: -1;">
                                                    <div class="chartjs-size-monitor-expand"
                                                         style="position:absolute;left:0;top:0;right:0;bottom:0;overflow:hidden;pointer-events:none;visibility:hidden;z-index:-1;">
                                                        <div style="position:absolute;width:1000000px;height:1000000px;left:0;top:0"></div>
                                                    </div>
                                                    <div class="chartjs-size-monitor-shrink"
                                                         style="position:absolute;left:0;top:0;right:0;bottom:0;overflow:hidden;pointer-events:none;visibility:hidden;z-index:-1;">
                                                        <div style="position:absolute;width:200%;height:200%;left:0; top:0"></div>
                                                    </div>
                                                </div>
                                                <canvas id="chart-line" width="299" height="200"
                                                        class="chartjs-render-monitor"
                                                        style="display: block; width: 299px; height: 200px;"></canvas>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <#--                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Назад</button>-->
                    <button type="button"
                            data-dismiss="modal" class="btn btn-primary">Понятно
                    </button>
                </div>

            </div>
        </div>


    </div>


    <link type="text/html" rel="stylesheet" href="/static/chart.css">
    <link href="/static/timer.css" type="text/css" rel="stylesheet">
    <link href='https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css'>
    <script src='https://stackpath.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.bundle.min.js'></script>
    <script src='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js'></script>
    <script src='https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.1.4/Chart.bundle.min.js'></script>
    <script src="/static/answer.js"></script>

    <script>

        // не работает без сет таймаута
        setTimeout(()=> {
            var myModal = new bootstrap.Modal(document.getElementById('staticBackdrop'), {
                keyboard: false
            })
            myModal.toggle()

            chartpie(${sendAnswerResponse.percent})
        }, 500)


    </script>


</@e.page>