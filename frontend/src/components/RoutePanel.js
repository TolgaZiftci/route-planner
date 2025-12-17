export default function RoutePanel({ transportations }) {

    const dayLabels = ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"];

    return (
        <div className="routePanel">
            <h3>Full Route</h3>

            <div className="routeNode">
                <div className="circle"></div>
                <p>{transportations[0].originLocation.name} ({transportations[0].originLocation.locationCode})</p>
            </div>
            {transportations.map((t, index) => (
                <div key={index} >
                    <p className="routeTransport"><strong>{t.type}</strong> | Operating Days: {t.operatingDays.map(day => dayLabels[day - 1]).join(", ")}</p>
                    <div className="routeNode">
                        <div className="circle"></div>
                        <p>{t.destLocation.name} ({t.destLocation.locationCode})</p>
                    </div>
                </div>
            ))}
        </div>
    );
}