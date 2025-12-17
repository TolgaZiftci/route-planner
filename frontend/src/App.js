import React from "react";
import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import './App.css';
import Header from './components/Header';
import Sidebar from './components/Sidebar';
import Locations from "./components/Locations";
import Transportations from "./components/Transportations";
import RoutePlanning from "./components/RoutePlanning";

function App() {
  return (
    <div className="App">
      <Router>
        <Header />
        <Sidebar />
        <div className="content">
          <Routes>
            <Route path="/" element={<Navigate to="/routeplanning" replace />} />
            <Route path="/locations" element={<Locations />} />
            <Route path="/transportations" element={<Transportations />} />
            <Route path="/routeplanning" element={<RoutePlanning />} />
          </Routes>
        </div>
      </Router>
    </div>
  );
}

export default App;
