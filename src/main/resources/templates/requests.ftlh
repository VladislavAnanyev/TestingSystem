<#import "parts/common.ftlh" as e>

<@e.page>

    <script src="/static/newActiveDialog.js"></script>
<#--    <script src="/static/meetgeo.js"></script>-->
    <script src='//cdn.jsdelivr.net/gmaps4rails/2.1.2/gmaps4rails.js'> </script>
    <script src='//cdnjs.cloudflare.com/ajax/libs/underscore.js/1.8.3/underscore.js'> </script>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
    <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAWyCb1Xq7gDRWSWRnOAVF3VsBz9TQW-og"></script>
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/gmaps.js/0.4.24/gmaps.min.js"></script>
    <link rel="stylesheet" href="/static/geoloc.css">
    <h5 class="mb-3" id="example">Запросы на переписку:<a class="anchorjs-link" aria-label="Anchor" data-anchorjs-icon="#" href="#example" style="padding-left: 0.375em;"></a></h5>


    <div class="row">

        <#if meetings??>
        <#list meetings as request>
            <div class="col-sm-6" id="${request.id?c}">

            <div class="card mt-2" style="width: 18rem;">
                <img class="rounded-circle" src="${request.sender.avatar}" alt="...">
                <div class="card-body">
                    <h5 class="card-title">${request.sender.firstName} ${request.sender.lastName}</h5>
                    <#if request.message??>
                    <p>Сообщение для вас: ${request.message.content}</p>
                    </#if>
<#--                    <p class="card-text">${meeting.time?datetime?string["dd.MM.yyyy HH:mm:ss"]}</p>-->
                    <a onclick="acceptRequest('${request.id?c}')" href="#" class="btn btn-primary">Принять</a>
                    <a onclick="rejectRequest('${request.id?c}')" href="#" class="btn btn-primary">Отклонить</a>

                </div>





                    <ul class="list-group list-group-flush">
                        <li class="list-group-item">
                            <div class="map-overlay">
                                <div class="map" id="map${request_index}"></div>
                            </div>
                        </li>


                    </ul>



            </div>



            </div>
        </#list>
        </#if>
        </div>

        <script>

            // Check if the browser has support for the Geolocation API



            <#list meetings as mt>
            new GMaps({
                el: '#map${mt_index}',
                lat: ${mt.meeting.lat?replace(",",".")},
                lng: ${mt.meeting.lng?replace(",",".")}
            }).addMarker({
                lat: ${mt.meeting.lat?replace(",",".")},
                lng: ${mt.meeting.lng?replace(",",".")}
            });
            </#list>





        </script>

    <script src="/static/acceptRequest.js"></script>
    <script src="/static/rejectRequest.js"></script>

</@e.page>