import React, { useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import surveyLogo from "../assets/Online_Survey_Icon_or_logo.svg.png";
import '../styles/MenuScreen.css';

function MenuScreen() {
    const [surveys, setSurveys] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        fetchSurveys();
    }, []);

    const fetchSurveys = async () => {
        try {
            const response = await fetch('http://localhost:8080/api/surveys');
            const data = await response.json();
            setSurveys(data);
        } catch (error) {
            console.error('Failed to fetch surveys:', error);
        }
    };

    return (
        <div className="menu-container">
            <img src={surveyLogo} alt="Survey Logo" className="survey-logo" />
            <div className="survey-list">
                {surveys.map(survey => (
                    <button key={survey.id} className="menu-button" onClick={() => navigate(`/survey/${survey.id}`)}>
                        {survey.title}
                    </button>
                ))}
            </div>
        </div>
    );
}

export default MenuScreen;
