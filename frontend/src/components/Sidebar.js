import { Link } from "react-router-dom";

export default function Sidebar() {
    return (
        <div className="Sidebar">
            <Link to="locations">
                <button>Locations</button>
            </Link>
            <Link to="transportations">
                <button>Transportations</button>
            </Link>
            <Link to="routeplanning">
                <button>Route Planning</button>
            </Link>
        </div>
    );
}