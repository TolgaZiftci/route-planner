export default function Route({ transportations, onSelect }) {
    return (
        transportations.map((transportation, index) => {
            if (transportation.type === "FLIGHT") {
                return (
                    <button key={index} className="RouteElement" onClick={() => onSelect(transportations)} >
                        via {transportation.originLocation.name} ({transportation.originLocation.locationCode})
                    </button>
                );
            }
            return null;
        })
    );
}