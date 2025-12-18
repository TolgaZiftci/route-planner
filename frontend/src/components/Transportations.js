import { useEffect, useState } from "react";
import Transportation from "./Transportation";
import TransportationPanel from "./TransportationPanel";

export default function Transportations() {

    const [transportations, setTransportations] = useState([]);
    const [selected, setSelected] = useState(null);

    const loadTransportations = () => {
        fetch("http://localhost:8080/api/transportation")
            .then(res => res.json())
            .then(data => setTransportations(data))
            .catch(err => console.error(err));
    };

    useEffect(() => {
        loadTransportations();
    }, []);

    const handleAdd = async (transportationData) => {
        try {
            console.log(transportationData)
            const response = await fetch("http://localhost:8080/api/transportation", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(transportationData)
            });

            if (!response.ok) {
                alert("Add failed: Transportation already exists or origin and destination is the same");
                return;
            }

            loadTransportations();

        } catch (error) {
            alert("Network error while adding the transportation");
            console.error(error);
        }
    };

    const handleDelete = async (id) => {
        try {
            const response = await fetch(`http://localhost:8080/api/transportation/${id}`, {
                method: "DELETE"
            });

            if (!response.ok) {
                alert("Delete failed: Server error");
                return;
            }

            loadTransportations();

            // Clear panel if the deleted item was selected
            if (selected?.id === id) {
                setSelected(null);
            }

        } catch (error) {
            alert("Network error while deleting the transportation");
            console.error(error);
        }
    };

    const handleUpdate = async (id, transportationData) => {
        try {
            const response = await fetch(`http://localhost:8080/api/transportation/${id}`, {
                method: "PUT",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(transportationData)
            });

            if (!response.ok) {
                alert("Update failed: Transportation already exists or origin/destination invalid");
                return;
            }

            loadTransportations();
            setSelected(null); // close panel after update

        } catch (error) {
            alert("Network error while updating the transportation");
            console.error(error);
        }
    };

    return (
        <div>
            <p className="pagetitle">
                Transportations
            </p>
            <div className="transportationContainer">
                <div className="transportationList">
                    {transportations.map(trans => (
                        <Transportation
                            key={trans.id}
                            id={trans.id}
                            originLocation={trans.originLocation}
                            destLocation={trans.destLocation}
                            type={trans.type}
                            operatingDays={trans.operatingDays}
                            onSelect={(trans) => {
                                    // If clicking the same transportation again -> deselect
                                    if (selected && selected.id === trans.id) {
                                        setSelected(null);
                                    } else {
                                        setSelected(trans);
                                    }
                                }}
                            onDelete={handleDelete}
                        />
                    ))}
                </div>
                <TransportationPanel selected={selected} onAdd={handleAdd} onUpdate={handleUpdate} />
            </div>
        </div>
    );
}