import { useState, useEffect } from "react";

export default function TransportationPanel({ selected, onAdd }) {

    const [form, setForm] = useState({
        originLocation: "",
        destLocation: "",
        type: "",
        operatingDays: []
    });

    const [locations, setLocations] = useState([]);

    const [originSearch, setOriginSearch] = useState("");

    const [destSearch, setDestSearch] = useState("");

    useEffect(() => {
        fetch("http://localhost:8080/api/location")
            .then(res => res.json())
            .then(data => setLocations(data));
    }, []);

    useEffect(() => {
        if (selected) {
            setForm({
                originLocation: String(selected.originLocation.id),
                destLocation: String(selected.destLocation.id),
                type: selected.type,
                operatingDays: selected.operatingDays || []
            });
        } else {
            setForm({
                originLocation: "",
                destLocation: "",
                type: "",
                operatingDays: []
            });
        }
    }, [selected]);

    const disabled = !!selected;

    const handleChange = (e) => {
        setForm({ ...form, [e.target.name]: e.target.value });
    };

    const toggleDay = (day) => {
        if (disabled) return;

        setForm(prev => {
            const exists = prev.operatingDays.includes(day);
            return {
                ...prev,
                operatingDays: exists
                    ? prev.operatingDays.filter(d => d !== day)
                    : [...prev.operatingDays, day]
            };
        });
    };

    const handleAdd = () => {
        onAdd({
            originLocation: Number(form.originLocation),
            destLocation: Number(form.destLocation),
            type: form.type,
            operatingDays: form.operatingDays
        });

        setForm({
            originLocation: "",
            destLocation: "",
            type: "",
            operatingDays: []
        });
    };

    const filteredOrigin = locations.filter(loc =>
        loc.name.toLowerCase().includes(originSearch.toLowerCase()) ||
        loc.locationCode.toLowerCase().includes(originSearch.toLowerCase())
    );

    const filteredDest = locations.filter(loc =>
        loc.name.toLowerCase().includes(destSearch.toLowerCase()) ||
        loc.locationCode.toLowerCase().includes(destSearch.toLowerCase())
    );

    const dayLabels = ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"];

    return (
        <div className="transportationPanel">
            <p>Origin Location</p>
            <input placeholder="Search..." value={originSearch} onChange={(e) => setOriginSearch(e.target.value)} disabled={disabled} />

            <select name="originLocation" value={form.originLocation} onChange={handleChange} disabled={disabled} >
                <option value="">Select origin</option>
                {filteredOrigin.map(loc => (
                    <option key={loc.id} value={loc.id}>
                        {loc.name} ({loc.locationCode})
                    </option>
                ))}
            </select>

            <p>Destination Location</p>
            <input placeholder="Search..." value={destSearch} onChange={(e) => setDestSearch(e.target.value)} disabled={disabled} />

            <select name="destLocation" value={form.destLocation} onChange={handleChange} disabled={disabled} >
                <option value="">Select destination</option>
                {filteredDest.map(loc => (
                    <option key={loc.id} value={loc.id}>
                        {loc.name} ({loc.locationCode})
                    </option>
                ))}
            </select>

            <p>Type</p>
            <select name="type" value={form.type} onChange={handleChange} disabled={disabled} >
                <option value="">Select type</option>
                <option value="FLIGHT">FLIGHT</option>
                <option value="BUS">BUS</option>
                <option value="SUBWAY">SUBWAY</option>
                <option value="UBER">UBER</option>
            </select>

            <p>Operating Days</p>
            <div className="daysCheckboxes">
                {dayLabels.map((label, index) => {
                    const dayNumber = index + 1;
                    return (
                        <label key={dayNumber} style={{ marginRight: "10px" }}>
                            <input
                                type="checkbox"
                                checked={form.operatingDays.includes(dayNumber)}
                                onChange={() => toggleDay(dayNumber)}
                                disabled={disabled}
                            />
                            {label}
                        </label>
                    );
                })}
            </div>

            {!disabled && (
                <button onClick={handleAdd}>Add Transportation</button>
            )}
        </div>
    );
}