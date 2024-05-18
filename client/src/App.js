import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import LoginScreen from './screens/LoginScreen';
import MenuScreen from "./screens/MenuScreen";
import SurveyPage from './screens/SurveyPage';
import SurveyDetails from './components/SurveyDetails';
import CreateSurveyScreen from './screens/CreateSurveyScreen';
import EditSurveyScreen from "./screens/EditSurveyScreen";

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
                    <Route path="/create-survey" element={<CreateSurveyScreen />} />
                    <Route path="/edit-survey" element={<EditSurveyScreen />} />
                </Routes>
            </div>
        </Router>
    );
}

export default App;
