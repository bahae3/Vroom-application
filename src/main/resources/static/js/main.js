// main.js
console.log("Hello from main.js loaded!");

// 1. Initialize map centered on Rabat (zoom level 6)
const map = L.map('map').setView([34.020882, -6.841650], 6);

// 2. Add OpenStreetMap tile layer
L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    maxZoom: 19,
    attribution: 'OpenStreetMap API - Bahae Dev'
}).addTo(map);

// 3. Auto-locate the user (re-centers map on find)
map.locate({ setView: true, maxZoom: 14, enableHighAccuracy: true });

function onLocationFound(e) {
    const userCoords = e.latlng;
    const accuracy = e.accuracy;

    // 4. Marker + circle for your location
    L.marker(userCoords).addTo(map)
        .bindPopup(`You (±${accuracy.toFixed(1)} m)`).openPopup();
    L.circle(userCoords, { radius: accuracy }).addTo(map);

    // 5. Hard-coded destination: Rabat, Morocco
    const rabatCoords = [33.985125, -6.726313];
    L.marker(rabatCoords).addTo(map)
        .bindPopup('Rabat, Morocco').openPopup();

    // 6. Fetch route from Rabat → You
    const url = `/api/trajet/route?startLat=${rabatCoords[0]}&startLng=${rabatCoords[1]}`
        + `&endLat=${userCoords.lat}&endLng=${userCoords.lng}`
        + `&t=${new Date().getTime()}`;

    fetch(url)
        .then(res => {
            return res.json();
        })
        .then(data => {
            console.log("Data processed:", data);

            if (!data || !data.routes || data.routes.length === 0) {
                throw new Error('No route data available');
            }

            const route = data.routes[0];
            const durationInSeconds = route.duration;
            const durationInMinutes = Math.round(durationInSeconds / 60);
            const durationText = durationInMinutes < 60
                ? `${durationInMinutes} min`
                : `${Math.floor(durationInMinutes / 60)}h ${durationInMinutes % 60}min`;

            // Add popup with travel time
            L.popup()
                .setLatLng(userCoords)
                .setContent(`Estimated travel time from Rabat: ${durationText}`)
                .openOn(map);

            // 7. Draw the GeoJSON route with custom style
            if (route.geometry) {
                L.geoJSON(route.geometry, {
                    style: {
                        color: 'blue',
                        weight: 4,
                        opacity: 0.8
                    }
                }).addTo(map);
            }
        })
        .catch(err => {
            console.error('Routing error:', err);
            alert(`Error getting route: ${err.message}`);
        });
}

function onLocationError(err) {
    console.error('Geolocation error:', err.code, err.message);
    alert(`Could not get your location: ${err.message}`);
}

map.on('locationfound', onLocationFound);
map.on('locationerror', onLocationError);