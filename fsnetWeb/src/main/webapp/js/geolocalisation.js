/* need jquery*/

/* global variables */
var directionDisplay;
var directionsService;
var map;

/* */
function getDestinationAddresse() {
	var destinationAddresse = null;
	if (document.getElementById("address") != null) {
		destinationAddresse = document.getElementById("address").getAttribute(
				"value");
	}
	return destinationAddresse;
}

/* */
function initializeGeolocalisation() {
	initializeMap();
	initializeActions();

	putDestinationOnMap();
}

/* */
function putDestinationOnMap(){
	var destinationAddresse = getDestinationAddresse();
	if (destinationAddresse != null) {
		putOnMapEvent(destinationAddresse);
		changeLinkToGoogleMapWithAddr(destinationAddresse);

	}
}

/* */
function putOnMapEvent(addr) {
	var geocoder = new google.maps.Geocoder();

	geocoder
			.geocode(
					{
						'address' : addr
					},
					function(results, status) {
						if (status == google.maps.GeocoderStatus.OK) {
							var coordinates = new google.maps.LatLng(
									results[0].geometry.location.lat(),
									results[0].geometry.location.lng());
							var marker = new google.maps.Marker({
								position : coordinates
							});
							marker.setMap(map);
							map.setCenter(coordinates);
							document.getElementById("geolocalisation").style.visibility = "visible";
						} else {
							document.getElementById("geolocalisation").style.visibility = "hidden";
						}
					});
}

/* */
function changeLinkToGoogleMapWithAddr(addr) {
	var link = "http://maps.google.fr/maps?q=" + addr.trim();
	link = link.replace(/ /g, "%20");
	if ($('a[name="linktogooglemap"]')) {
		$('a[name="linktogooglemap"]').attr('href', link);
	}
}

/* */
function changeLinkToGoogleMapWithRoutePosition(startLat, startLng, endAddr) {
	var link = "http://maps.google.fr/maps?";
	var start = "f=d&source=s_d&saddr=" + startLat + "," + startLng;
	var end = "&daddr=" + endAddr.trim();
	link += start + end;
	link = link.replace(/ /g, "%20");
	if ($('a[name="linktogooglemap"]')) {
		$('a[name="linktogooglemap"]').attr('href', link);
	}
}

/* */
function changeLinkToGoogleMapWithRoute(startAddr, endAddr) {
	var link = "http://maps.google.fr/maps?";
	var start = "f=d&source=s_d&saddr=" + startAddr.trim();
	var end = "&daddr=" + endAddr.trim();
	link += start + end;
	link = link.replace(/ /g, "%20");
	if ($('a[name="linktogooglemap"]')) {
		$('a[name="linktogooglemap"]').attr('href', link);
	}
}

/* */
function leadVisitorToEvent(position) {
	if (position == null) {
		return;
	}

	var start = new google.maps.LatLng(position.coords.latitude,
			position.coords.longitude);
	var end = getDestinationAddresse();
	buildRoute(start, end);
	changeLinkToGoogleMapWithRoutePosition(position.coords.latitude,
			position.coords.longitude, end);

}

/* */
function initializeMap() {
	directionsDisplay = new google.maps.DirectionsRenderer();
	directionsService = new google.maps.DirectionsService();

	var latlng = new google.maps.LatLng(48.8566667, 2.3509871);
	var myOptions = {
		zoom : 12,
		center : latlng,
		mapTypeId : google.maps.MapTypeId.ROADMAP
	}
	map = new google.maps.Map(document.getElementById("mapCanvas"), myOptions);
}

/* */
function initializeActions() {
	checkUserAdressNotEmpty();
	checkEnableGeolocalisation();

}

/* */
function checkEnableGeolocalisation() {
	if (navigator.geolocation == false) {
		$('button[name="workRouteGeo"]').remove();
		return false;
	}
	return true;
}

/* */
function checkUserAdressNotEmpty() {
	if ($.trim($('input[id="userAddress"]').val()).length == 0) {
		$('button[name="workRouteHome"]').remove();
		return false;
	}
	return true;
}

/* */
function buildRouteFromGeolocalisation() {
	initializeMap();
	putDestinationOnMap();
	document.getElementById("errorGeo").style.visibility = "hidden";
	if (navigator.geolocation) {
		navigator.geolocation.getCurrentPosition(leadVisitorToEvent);
	}
}

/* */
function buildRouteFromHome() {
	initializeMap();
	putDestinationOnMap();
	document.getElementById("errorGeo").style.visibility = "hidden";
	var start = $.trim($('input[id="userAddress"]').val());
	var end = getDestinationAddresse();
	buildRouteAddrr(start, end);
	changeLinkToGoogleMapWithRoute(start, end);
	displayErrorIfNotExist(start);

}

/* */
function buildRoute(start, end) {
	directionsDisplay.setMap(map);
	var requeteItineraire = {
		origin : start,
		destination : end,
		travelMode : google.maps.DirectionsTravelMode.DRIVING
	};
	directionsService.route(requeteItineraire, function(response, status) {
		if (status == google.maps.DirectionsStatus.OK) {
			directionsDisplay.setDirections(response);
		}else if(status == google.maps.DirectionsStatus.ZERO_RESULTS){
			var marker = new google.maps.Marker({
				position : start
			});
			marker.setMap(map);
			map.setZoom(0);
		}else{
			map.setZoom(0);
		}
	});
}

/* */
function buildRouteAddrr(start, end){
	directionsDisplay.setMap(map);
	var requeteItineraire = {
		origin : start,
		destination : end,
		travelMode : google.maps.DirectionsTravelMode.DRIVING
	};
	directionsService.route(requeteItineraire, function(response, status) {
		if (status == google.maps.DirectionsStatus.OK) {
			directionsDisplay.setDirections(response);
		}else if(status == google.maps.DirectionsStatus.ZERO_RESULTS){
			putOnMapEvent(start);
			map.setZoom(0);
		}else{
			map.setZoom(0);
		}
	});
	
}


/* */
function displayErrorIfNotExist(addr) {
	var geocoder = new google.maps.Geocoder();
	geocoder.geocode({
		'address' : addr
	}, function(results, status) {
		if (status == google.maps.GeocoderStatus.OK) {
			document.getElementById("errorGeo").style.visibility = "hidden";
		} else {
			document.getElementById("errorGeo").style.visibility = "visible";
		}
	});
}

