<#import "parts/common.ftlh" as e>
<#--<#include "parts/security.ftlh">-->

<@e.page>

    <script src='//cdn.jsdelivr.net/gmaps4rails/2.1.2/gmaps4rails.js'> </script>
    <script src='//cdnjs.cloudflare.com/ajax/libs/underscore.js/1.8.3/underscore.js'> </script>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
    <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAWyCb1Xq7gDRWSWRnOAVF3VsBz9TQW-og"></script>
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/gmaps.js/0.4.24/gmaps.min.js"></script>

    <link rel="stylesheet" href="/static/geoloc.css">
<div class="container">

    <h1>Геолокация</h1>




    <form id="geocoding_form" class="form-horizontal">
        <div class="form-group">
            <div class="col-xs-12 col-md-6 col-md-offset-3">
                <button <#--onclick="geo()"--> type="button" id="findme" class="find-me btn btn-info btn-block">Find My Location</button>
            </div>
        </div>
    </form>

    <p class="no-browser-support">Sorry, the Geolocation API isn't supported in Your browser.</p>
    <p class="coordinates">Latitude: <b class="latitude">42</b> Longitude: <b class="longitude">32</b></p>

    <div class="map-overlay">
        <div class="map" id="map"></div>
    </div>

</div>



</@e.page>