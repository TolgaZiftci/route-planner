import { useState, useEffect } from "react";

export default function LocationPanel({ selected, onAdd, onUpdate }) {

    const [form, setForm] = useState({
        name: "",
        country: "",
        city: "",
        code: ""
    });

    useEffect(() => {
        if (selected) {
            setForm({
                name: selected.name,
                country: selected.country,
                city: selected.city,
                code: selected.code
            });
        } else {
            setForm({
                name: "",
                country: "",
                city: "",
                code: ""
            });
        }
    }, [selected]);

    const handleChange = (e) => {
        setForm({ ...form, [e.target.name]: e.target.value });
    };

    const handleAdd = () => {
        onAdd({
            name: form.name,
            country: form.country,
            city: form.city,
            locationCode: form.code
        });
        setForm({ name: "", country: "", city: "", code: "" });
    };
    
    return (
        <div className="locationPanel">
            <p>Location Name</p>
            <input name="name" value={form.name} onChange={handleChange} />

            <p>Country</p>
            <input name="country" value={form.country} onChange={handleChange} />

            <p>City</p>
            <input name="city" value={form.city} onChange={handleChange} />

            <p>Location Code</p>
            <input name="code" value={form.code} onChange={handleChange} />

            {!selected && (
                <button onClick={handleAdd}>Add Location</button>
            )}

            {selected && (
                <button
                    onClick={() =>
                        onUpdate(selected.id, {
                            name: form.name,
                            country: form.country,
                            city: form.city,
                            locationCode: form.code
                        })
                    }
                >
                    Update Location
                </button>
            )}
        </div>
    );
}