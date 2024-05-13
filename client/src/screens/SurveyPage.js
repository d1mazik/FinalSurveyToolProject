// SurveyPage.jsx

import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { getQuestionsForSurvey, getSurveyTitle } from '../service/questionService';
import '../styles/surveyPage.css';

function SurveyPage() {
    const { surveyId } = useParams();
    const [questions, setQuestions] = useState([]);
    const [surveyTitle, setSurveyTitle] = useState('');
    const [currentQuestionIndex, setCurrentQuestionIndex] = useState(0);
    const [userAnswers, setUserAnswers] = useState([]);

    useEffect(() => {
        fetchQuestions();
        fetchSurveyTitle();
    }, [surveyId]);

    const fetchQuestions = async () => {
        try {
            const data = await getQuestionsForSurvey(surveyId);
            setQuestions(data);
        } catch (error) {
            console.error('Failed to fetch questions:', error);
        }
    };

    const fetchSurveyTitle = async () => {
        try {
            const title = await getSurveyTitle(surveyId);
            setSurveyTitle(title);
        } catch (error) {
            console.error('Failed to fetch survey title:', error);
        }
    };

    const handleAnswerSelect = (selectedOption) => {
        setUserAnswers(prevAnswers => {
            const updatedAnswers = [...prevAnswers];
            updatedAnswers[currentQuestionIndex] = selectedOption;
            return updatedAnswers;
        });
        setCurrentQuestionIndex(prevIndex => prevIndex + 1);
    };

    const renderCurrentQuestion = () => {
        const currentQuestion = questions[currentQuestionIndex];
        if (!currentQuestion) {
            return <h2 className="thank-you">Tack för dina svar!</h2>;
        }

        return (
            <div className="question-container">
                <h2 className="question-text">{currentQuestion.text}</h2>
                <ul className="options-list">
                    {currentQuestion.options.map(option => (
                        <li key={option.id} className="option-item" onClick={() => handleAnswerSelect(option)}>
                            <button className="option-button">{option.text}</button>
                        </li>
                    ))}
                </ul>
            </div>
        );
    };

    return (
        <div className="survey-page-container">
            <h1 className="survey-title">{surveyTitle}</h1>
            {renderCurrentQuestion()}
        </div>
    );
}

export default SurveyPage;
