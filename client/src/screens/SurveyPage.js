import React, { useState, useEffect } from 'react';
import { useParams, useLocation } from 'react-router-dom';
import { getSurveyTitle, getQuestionsForSurvey} from '../service/questionService';
import '../styles/surveyPage.css';

function SurveyPage() {
    const { surveyId } = useParams();
    const { state } = useLocation();
    const sessionId = state.sessionId; // Assume sessionId is passed in state when navigating
    const [questions, setQuestions] = useState([]);
    const [surveyTitle, setSurveyTitle] = useState('');
    const [currentQuestionIndex, setCurrentQuestionIndex] = useState(0);

    useEffect(() => {
        fetchQuestions();
        fetchSurveyTitle();
    }, [surveyId]);

    const fetchQuestions = async () => {
        const data = await getQuestionsForSurvey(surveyId);
        setQuestions(data);
    };

    const fetchSurveyTitle = async () => {
        const title = await getSurveyTitle(surveyId);
        setSurveyTitle(title);
    };

    const handleAnswerSelect = async (answer) => {
        const currentQuestion = questions[currentQuestionIndex];
        const answerData = {
            text: answer.text || '',  // For TEXT and SCALE (if any text is needed)
            selectedOption: answer.optionId,  // For OPTION type
            scale: answer.scale  // For SCALE type
        };

        if (currentQuestionIndex < questions.length - 1) {
            setCurrentQuestionIndex(currentQuestionIndex + 1);
        } else {
            console.log("Survey completed!");
            // Handle survey completion here
        }
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