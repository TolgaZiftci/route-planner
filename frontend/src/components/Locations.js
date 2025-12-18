import { useEffect, useState } from "react";
import Location from "./Location";
import LocationPanel from "./LocationPanel";

export default function Locations() {

    const [locations, setLocations] = useState([]);
    const [selected, setSelected] = useState(null);

    const loadLocations = () => {
        fetch("http://localhost:8080/api/location")
            .then(res => res.json())
            .then(data => setLocations(data))
            .catch(err => console.error(err));
    };

    useEffect(() => {
        loadLocations();
    }, []);

    const handleAdd = async (locationData) => {
        try {
            const response = await fetch("http://localhost:8080/api/location", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(locationData)
            });

            if (!response.ok) {
                alert("Add failed: Location already exists or fields left empty");
                return;
            }

            loadLocations();

        } catch (error) {
            alert("Network error while adding the location");
            console.error(error);
        }
    };

    const handleDelete = async (id) => {
        try {
            const response = await fetch(`http://localhost:8080/api/location/${id}`, {
                method: "DELETE"
            });

            if (!response.ok) {
                if (response.status === 400) {
                    alert("Delete failed: Location has transportations attached, delete those first");
                } else {
                    alert("Delete failed: Server error");
                }
                return;
            }

            loadLocations();

            // Clear panel if the deleted item was selected
            if (selected?.id === id) {
                setSelected(null);
            }

        } catch (error) {
            alert("Network error while deleting the location");
            console.error(error);
        }
    };

    const handleUpdate = async (id, locationData) => {
        try {
            const response = await fetch(`http://localhost:8080/api/location/${id}`, {
                method: "PUT",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(locationData)
            });

            if (!response.ok) {
                alert("Update failed: Location already exists or fields left empty");
                return;
            }

            loadLocations();
            setSelected(null); // close panel after update

        } catch (error) {
            alert("Network error while updating the location");
            console.error(error);
        }
    };
    
    return (
        <div>
            <p className="pagetitle">
                Locations
            </p>
            <div className="locationContainer">
                <div className="locationList">
                    {locations.map(loc => (
                        <Location
                            key={loc.id}
                            id={loc.id}
                            name={loc.name}
                            city={loc.city}
                            country={loc.country}
                            code={loc.locationCode}
                            onSelect={(loc) => {
                                    // If clicking the same location again -> deselect
                                    if (selected && selected.id === loc.id) {
                                        setSelected(null);
                                    } else {
                                        setSelected(loc);
                                    }
                                }}
                            onDelete={handleDelete}
                        />
                    ))}
                </div>
                <LocationPanel selected={selected} onAdd={handleAdd} onUpdate={handleUpdate} />
            </div>
        </div>
    );
}