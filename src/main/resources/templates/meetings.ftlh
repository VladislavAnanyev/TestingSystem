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
    <h5 class="mb-3" id="example">Возможно, вы сегодня видели:<a class="anchorjs-link" aria-label="Anchor" data-anchorjs-icon="#" href="#example" style="padding-left: 0.375em;"></a></h5>


    <div class="row">
    <#list meetings as meeting>
        <div class="col-sm-6">
        <#if meeting.secondUser.username != myUsername>
            <div class="card mt-2" style="width: 18rem;">
            <img class="rounded-circle" src="${meeting.secondUser.avatar}" alt="...">
            <div class="card-body">
                <h5 class="card-title">${meeting.secondUser.firstName} ${meeting.secondUser.lastName}</h5>
                <p id="${meeting.id?c}" class="card-text"></p>

                <script>

                    function setTime(time, id) {
                        let userTime = new Date(time)
                        document.getElementById(id).textContent = userTime.toLocaleDateString() + " " + userTime.toLocaleTimeString()
                    }


                    <#if meeting.time??>
                    setTime(${meeting.time?long?c}, ${meeting.id?c})


                    <#else >
                    console.log("nulllllllllll")
                    </#if>




                </script>

<#--                <div class="input-group mb-2">-->

<#--                </div>-->

                <#if friendsName?seq_contains(meeting.secondUser.username)>
                    <a  onclick="writeMsg('${meeting.secondUser.username}')" href="#" id="button${meeting.secondUser.username}" class="btn btn-primary">Написать сообщение</a>

                <#else>

                    <span class="input-group-text mb-1">Сообщение</span>
                    <textarea class="form-control mb-2" id="${meeting.secondUser.username}msg" aria-label="With textarea"></textarea>

                    <a  onclick="sendRequest('${meeting.secondUser.username}', '${meeting.id?c}')" href="#" id="button${meeting.secondUser.username}" class="btn btn-primary">Отправить запрос</a>


                </#if>

            </div>



        <#else>

            <div class="card" style="width: 18rem;">
                <img class="rounded-circle" src="${meeting.firstUser.avatar}" alt="...">
                <div class="card-body">
                    <h5 class="card-title">${meeting.firstUser.firstName} ${meeting.firstUser.lastName}</h5>
                    <p class="card-text">${meeting.time?datetime?string["dd.MM.yyyy HH:mm:ss"]}</p>



                    <#if friendsName?seq_contains(meeting.firstUser.username)>
                        <a  onclick="writeMsg('${meeting.firstUser.username}')" href="#" id="button${meeting.firstUser.username}" class="btn btn-primary">Написать сообщение</a>

                    <#else>

                        <span class="input-group-text mb-1">Сообщение</span>
                        <textarea class="form-control mb-2" id="${meeting.firstUser.username}msg" aria-label="With textarea"></textarea>

                        <a  onclick="sendRequest('${meeting.firstUser.username}', '${meeting.id?c}')" href="#" id="button${meeting.firstUser.username}" class="btn btn-primary">Отправить запрос</a>


                    </#if>


                </div>

<#--                </div>-->

        </#if>



        <ul class="list-group list-group-flush">
            <li class="list-group-item">
                <div class="map-overlay">
                    <div class="map" id="map${meeting_index}"></div>
                </div>
            </li>


        </ul>



    </div>



    </div>
    </#list>
    </div>

        <script>

            // Check if the browser has support for the Geolocation API



            <#list meetings as mt>
            new GMaps({
                el: '#map${mt_index}',
                lat: ${mt.lat?replace(",",".")},
                lng: ${mt.lng?replace(",",".")}
            }).addMarker({
                lat: ${mt.lat?replace(",",".")},
                lng: ${mt.lng?replace(",",".")}
            });
            </#list>




            /*var map,map2;

            var myOptions = {
                zoom: 6,
                center: new google.maps.LatLng(51, -1),
                mapTypeId: google.maps.MapTypeId.ROADMAP
            }

            var myOptions2 = {
                zoom: 4,
                center: new google.maps.LatLng(53, -2),
                mapTypeId: google.maps.MapTypeId.SATELLITE
            }

            map = new google.maps.Map(document.getElementById("map_canvas"),
                myOptions);

            map2 = new google.maps.Map(document.getElementById("map_canvas2"),
                myOptions2);*/



        </script>


        <script src="/static/sendRequest.js"></script>
</@e.page>