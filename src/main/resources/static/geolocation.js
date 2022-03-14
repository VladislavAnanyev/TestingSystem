function geo() {
    //var findMeButton = document.getElementById("findme")

    let lat
    let lng
    let jsonBack
    let lastTimestamp = new Date().getMilliseconds()

    let geoconfig={

        EnableHighAccuracy: true, maximumAge: 59000

    };
// Check if the browser has support for the Geolocation API
    if (!navigator.geolocation) {


        console.log("Браузер не поддерживается")
        //findMeButton.addClass("disabled");
        //$('.no-browser-support').addClass("visible");

    } else {



        var map
        var m

        if (location.pathname.includes("geo")) {
            navigator.geolocation.getCurrentPosition(function (position) {

                // Get the coordinates of the current possition.
                lat = position.coords.latitude;
                lng = position.coords.longitude;

                if (document.getElementById("map") != null) {
                    map = new GMaps({
                        el: '#map',
                        lat: lat,
                        lng: lng
                    });


                    m = map.addMarker({
                        lat: lat,
                        lng: lng,
                        title: "hello"
                    });


                    let json = {
                        lat: lat,
                        lng: lng
                    }


                    let xhr = new XMLHttpRequest();
                    xhr.open('GET', '/getAllGeoWithoutMe', true);
                    xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
                    xhr.onreadystatechange = function () {
                        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {

                            let array = JSON.parse(xhr.response)
                            console.log(array)
                            for (let i = 0; i < array.length; i++) {

                                // console.log()


                                map.addMarker({
                                    lat: array[i].lat,
                                    lng: array[i].lng,
                                    title: "hello"
                                }).addListener("click", () => {

                                    console.log(array[i].user)
                                });


                                /*marker.addListener("click", () => {
                                    infowindow.open(marker.get("map"), marker);
                                });*/
                                /*const infowindow = new google.maps.InfoWindow({
                                    content: "hello",
                                });*/
                            }


                        } else if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 400) {

                        }
                    };
                    xhr.send()
                }


            }, null, geoconfig);
        }



        navigator.geolocation.watchPosition(position => {
            if (position.timestamp/1000 - lastTimestamp/1000 > 5) {

                console.log(position)
                lat = position.coords.latitude;
                lng = position.coords.longitude;

                lastTimestamp = position.timestamp

                $('.latitude').text(lat);
                $('.longitude').text(lng);
                $('.coordinates').addClass('visible');

                // Create a new map and place a marker at the device location.
                /*var map = new GMaps({
                    el: '#map',
                    lat: lat,
                    lng: lng
                });*/

                if (document.getElementById("map")) {
                    map.removeMarker(m)

                    m = map.addMarker({
                        lat: lat,
                        lng: lng,
                        title: "hello"
                    });
                }


                let json = {
                    lat: lat,
                    lng: lng
                }


                let xhr = new XMLHttpRequest();
                xhr.open('POST', '/sendGeolocation', true);
                xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
                xhr.onreadystatechange = function () {
                    if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {

                        console.log("Отправлено")
                    } else if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 400) {

                        console.log("Нет")
                    }

                }
                xhr.send(JSON.stringify(json))

            }
        }, null, geoconfig)



        setInterval(() => {


            console.log("Геолокация из фона" + " " + new Date())
            jsonBack = {
                lat: lat,
                lng: lng
            }
            console.log(lat + " " + lng)

            if (lat != null && lng != null) {
                let xhrBackground = new XMLHttpRequest();
                xhrBackground.open('POST', '/sendGeolocation',true);
                xhrBackground.setRequestHeader('Content-type','application/json; charset=utf-8');
                xhrBackground.onreadystatechange = function () {
                    if (xhrBackground.readyState === XMLHttpRequest.DONE && xhrBackground.status === 200) {



                    } else if (xhrBackground.readyState === XMLHttpRequest.DONE && xhrBackground.status === 400) {

                    }
                };
                xhrBackground.send(JSON.stringify(jsonBack))

                /*let xhrTest3 = new XMLHttpRequest();
                xhrTest3.open('GET', '/testConnection',true);
                xhrTest3.setRequestHeader('Content-type','application/json; charset=utf-8');
                xhrTest3.onreadystatechange = function () {
                    if (xhrTest3.readyState === XMLHttpRequest.DONE && xhrTest3.status === 200) {
                        console.log(xhr.responseText)


                    } else if (xhrTest3.readyState === XMLHttpRequest.DONE && xhrTest3.status === 400) {

                    }
                };
                xhrTest3.send()*/

            } else {
                let xhrTest = new XMLHttpRequest();
                xhrTest.open('GET', '/testConnection',true);
                xhrTest.setRequestHeader('Content-type','application/json; charset=utf-8');
                xhrTest.onreadystatechange = function () {
                    if (xhrTest.readyState === XMLHttpRequest.DONE && xhrTest.status === 200) {
                        console.log(xhrTest.responseText)


                    } else if (xhrTest.readyState === XMLHttpRequest.DONE && xhrTest.status === 400) {

                    }
                };
                xhrTest.send()
            }


        }, 20000)






    }
}