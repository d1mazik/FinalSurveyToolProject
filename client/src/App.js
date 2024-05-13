import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import LoginScreen from './screens/LoginScreen';
import MenuScreen from "./screens/MenuScreen";
import SurveyPage from './screens/SurveyPage'; // Uppdaterat import
import SurveyDetails from './components/SurveyDetails';

function App() {
    return (
        <Router>
            <div className="App">
                <Routes>
                    {/* Redirect från root URL till /login */}
                    <Route path="/" element={<Navigate replace to="/login" />} />
                    <Route path="/login" element={<LoginScreen />} />
                    <Route path="/menu" element={<MenuScreen />} />
                    <Route path="/survey/:surveyId" element={<SurveyPage />} /> {/* Ny rutt */}
                    <Route path="/survey/:surveyId/details" element={<SurveyDetails />} />
                </Routes>
            </div>
        </Router>
    );
}

export default App;
