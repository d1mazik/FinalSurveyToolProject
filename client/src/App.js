import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import LoginScreen from './screens/LoginScreen';
import MenuScreen from "./screens/MenuScreen";

function App() {
    return (
        <Router>
            <div className="App">
                <Routes>
                    {/* Redirect från root URL till /login */}
                    <Route path="/" element={<Navigate replace to="/login" />} />
                    <Route path="/login" element={<LoginScreen />} />
                    <Route path="/menu" element={<MenuScreen />} />
                </Routes>
            </div>
        </Router>
    );
}

export default App;
