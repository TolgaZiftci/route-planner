export default function Location({ id, name, city, country, code, onSelect, onDelete }) {
    return (
        <button className="Location" onClick={() => onSelect({ id, name, city, country, code })}>
            <p>{name}</p>
            <p>{city} / {country}</p>
            <p>{code}</p>
            <button className="deleteBtn" onClick={(e) => {
                    e.stopPropagation();
                    onDelete(id);
                }}
            >
                Delete
            </button>
        </button>
    );
}