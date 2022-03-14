function meetgeo() {

// Check if the browser has support for the Geolocation API
    if (!navigator.geolocation) {


        console.log("error")

    } else {

        let map = new GMaps({
            el: '#map',
            lat: 58.0,
            lng: 58.0
        });


        /*m = map.addMarker({
            lat: lat,
            lng: lng
        });*/





    }
}