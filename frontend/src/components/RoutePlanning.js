import { useState, useEffect } from "react"
import Route from "./Route";
import RoutePanel from "./RoutePanel";

export default function RoutePlanning() {

    const [locations, setLocations] = useState([]);
    const [originSearch, setOriginSearch] = useState("");
    const [destSearch, setDestSearch] = useState("");
    const [routes, setRoutes] = useState([]);
    const [selectedRoute, setSelectedRoute] = useState(null);

    const [form, setForm] = useState({
        originLocation: "",
        destLocation: "",
        travelDate: ""
    });

    useEffect(() => {
        fetch("http://localhost:8080/api/location")
            .then(res => res.json())
            .then(data => setLocations(data))
            .catch(err => console.error(err));
    }, []);
    
    const filteredOrigin = locations.filter(loc =>
        loc.name.toLowerCase().includes(originSearch.toLowerCase()) ||
        loc.locationCode.toLowerCase().includes(originSearch.toLowerCase())
    );

    const filteredDest = locations.filter(loc =>
        loc.name.toLowerCase().includes(destSearch.toLowerCase()) ||
        loc.locationCode.toLowerCase().includes(destSearch.toLowerCase())
    );

    const handleChange = (e) => {
        setForm({ ...form, [e.target.name]: e.target.value });
    };

    const handleFindRoute = async () => {
        if (!form.originLocation || !form.destLocation || !form.travelDate) {
            alert("Please select origin, destination, and date.");
            return;
        }

        const url = `http://localhost:8080/api/route?originLocation=${form.originLocation}&destLocation=${form.destLocation}&date=${form.travelDate}`;

        const response = await fetch(url)
        if (!response.ok) {
            console.error("Failed to fetch routes", response.error);
            return;
        }
        const data = await response.json();
        setRoutes(data);
    };

    return (
        <div>
            <p className="pagetitle">
                Route Planning
            </p>
            <div className="routeContainer">
                <div className="routeInputs">
                    <div className="inputGroup">
                        <p>Origin Location</p>
                        <input
                            placeholder="Search..."
                            value={originSearch}
                            onChange={(e) => setOriginSearch(e.target.value)}
                        />

                        <select
                            name="originLocation"
                            value={form.originLocation}
                            onChange={handleChange}
                        >
                            <option value="">Select origin</option>
                            {filteredOrigin.map(loc => (
                                <option key={loc.id} value={loc.id}>
                                    {loc.name} ({loc.locationCode})
                                </option>
                            ))}
                        </select>
                    </div>

                    <div className="inputGroup">
                        <p>Destination Location</p>
                        <input
                            placeholder="Search..."
                            value={destSearch}
                            onChange={(e) => setDestSearch(e.target.value)}
                        />

                        <select
                            name="destLocation"
                            value={form.destLocation}
                            onChange={handleChange}
                        >
                            <option value="">Select destination</option>
                            {filteredDest.map(loc => (
                                <option key={loc.id} value={loc.id}>
                                    {loc.name} ({loc.locationCode})
                                </option>
                            ))}
                        </select>
                    </div>

                    <div className="inputGroup">
                        <p>Travel Date</p>
                        <input
                            type="date"
                            name="travelDate"
                            value={form.travelDate}
                            onChange={handleChange}
                        />
                    </div>
                </div>

                <button className="findRouteButton" onClick={handleFindRoute}>
                    Find Route
                </button>
                <div className="routeResults">
                    <div className="routeList">
                        {routes.length === 0 && (
                            <p>No routes found. Try searching.</p>
                        )}

                        {routes.map((route, index) => (
                            <Route 
                            key={index} 
                            transportations={route.transportations} 
                            onSelect={(t) => {
                                    // If clicking the same route again -> deselect
                                    if (selectedRoute && selectedRoute[0].id === t[0].id) {
                                        setSelectedRoute(null);
                                    } else {
                                        setSelectedRoute(t);
                                    }
                                }} />
                        ))}
                    </div>
                    {selectedRoute && (
                        <RoutePanel transportations={selectedRoute} />
                    )}
                </div>
            </div>
        </div>
    );
}