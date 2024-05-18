// Import the service function in your MenuScreen component
import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { startSurveySession } from '../service/surveyService'; // Adjust the path as necessary
import surveyLogo from "../assets/Online_Survey_Icon_or_logo.svg.png";
import '../styles/MenuScreen.css';
import {getUserId} from "../utils/auth";

function MenuScreen() {
    const [surveys, setSurveys] = useState([]);
    const navigate = useNavigate();

    // Placeholder function to fetch surveys, replace with actual fetching logic
    const fetchSurveys = async () => {
        // Example fetch logic, replace with actual API call
        const response = await fetch('/api/surveys');
        const data = await response.json();
        setSurveys(data);
    };

    useEffect(() => {
        fetchSurveys();
    }, []);

    const handleSurveyClick = async (surveyId) => {
        const userId = getUserId(); // Fetch the userId from localStorage
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