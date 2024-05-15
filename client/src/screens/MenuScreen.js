import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { startSurveySession } from '../service/surveyService'; // Justera sökvägen vid behov
import surveyLogo from "../assets/Online_Survey_Icon_or_logo.svg.png";
import '../styles/MenuScreen.css';
import { getUserId, getToken } from "../utils/auth"; // Importera getToken om den är tillgänglig

function MenuScreen() {
    const [surveys, setSurveys] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchSurveys = async () => {
            const token = getToken(); // Hämta token från localStorage
            const headers = token ? { 'Authorization': `Bearer ${token}` } : {};

            try {
                const response = await fetch('/api/surveys', { headers });
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                const data = await response.json();
                setSurveys(data);
            } catch (error) {
                console.error('Error fetching surveys:', error);
            }
        };

        fetchSurveys();
    }, []);

    const handleSurveyClick = async (surveyId) => {
        const userId = getUserId(); // Använd funktionen för att hämta userId
        if (!userId) {
            console.error('No user ID found, user must be logged in');
            return;
        }
        try {
            const session = await startSurveySession(surveyId, userId);
            navigate(`/survey/${surveyId}`, { state: { sessionId: session.id } });
        } catch (error) {
            console.error('Failed to initiate survey:', error);
        }
    };

    return (
        <div className="menu-container">
            <img src={surveyLogo} alt="Survey Logo" className="survey-logo" />
            <div className="survey-list">
                {surveys.map(survey => (
                    <button key={survey.id} className="menu-button" onClick={() => handleSurveyClick(survey.id)}>
                        {survey.title}
                    </button>
                ))}
            </div>
        </div>
    );
}

export default MenuScreen;
