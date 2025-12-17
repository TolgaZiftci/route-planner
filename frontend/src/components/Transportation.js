export default function Transportation({ id, originLocation, destLocation, type, operatingDays, onSelect, onDelete }) {
    return (
        <button className="Transportation" onClick={() => onSelect({ id, originLocation, destLocation, type, operatingDays })}>
            <p>{originLocation.name} -&gt; </p>
            <p>{destLocation.name}</p>
            <p>{type}</p>
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