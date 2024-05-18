import React, { useState, useEffect } from 'react';
import { useParams, useLocation, useNavigate } from 'react-router-dom';
// Importera endast relevanta funktioner från vardera tjänst
import { getSurveyTitle, getQuestionsForSurvey, submitAnswer } from '../service/questionService';
import { endSession } from '../service/surveyService';  // Importera endSession från surveyService


import '../styles/surveyPage.css';

function SurveyPage() {
    const { surveyId } = useParams();
    const { state } = useLocation();
    const sessionId = state.sessionId;
    const [questions, setQuestions] = useState([]);
    const [surveyTitle, setSurveyTitle] = useState('');
    const [currentQuestionIndex, setCurrentQuestionIndex] = useState(0);
    const [currentAnswer, setCurrentAnswer] = useState({});
    const navigate = useNavigate();

    useEffect(() => {
        async function fetchData() {
            const questionsData = await getQuestionsForSurvey(surveyId);
            setQuestions(questionsData);
            const title = await getSurveyTitle(surveyId);
            setSurveyTitle(title);
        }
        fetchData();
    }, [surveyId]);

    const handleAnswerSelect = async () => {
        await submitAnswer(sessionId, questions[currentQuestionIndex].id, currentAnswer);
        if (currentQuestionIndex < questions.length - 1) {
            setCurrentQuestionIndex(currentQuestionIndex + 1);
            setCurrentAnswer({});
        } else {
            await endSession(sessionId);
            navigate('/menu');
        }
    };

    const handleOptionChange = (event) => {
        setCurrentAnswer({ selectedOption: event.target.value });
    };

    const handleScaleChange = (event) => {
        setCurrentAnswer({ scale: parseInt(event.target.value, 10) });
    };

    const handleTextChange = (event) => {
        setCurrentAnswer({ text: event.target.value });
    };

    const renderQuestionInput = () => {
        const currentQuestion = questions[currentQuestionIndex];
        switch(currentQuestion.type) {
            case 'OPTIONS':
                return (
                    <div>
                        {currentQuestion.options.map(option => (
                            <label key={option.id}>
                                <input
                                    type="radio"
                                    value={option.id}
                                    checked={currentAnswer.selectedOption === option.id}
                                    onChange={handleOptionChange}
                                />
                                {option.text}
                            </label>
                        ))}
                    </div>
                );
            case 'SCALE':
                return (
                    <div>
                        {[...Array(currentQuestion.maxScale - currentQuestion.minScale + 1).keys()].map(scale => (
                            <label key={scale + currentQuestion.minScale}>
                                <input
                                    type="radio"
                                    value={scale + currentQuestion.minScale}
                                    checked={currentAnswer.scale === (scale + currentQuestion.minScale)}
                                    onChange={handleScaleChange}
                                />
                                {scale + currentQuestion.minScale}
                            </label>
                        ))}
                    </div>
                );
            case 'TEXT':
                return (
                    <textarea
                        value={currentAnswer.text || ''}
                        onChange={handleTextChange}
                    />
                );
            default:
                return <div>Unknown question type</div>;
        }
    };

    return (
        <div className="survey-page-container">
            <h1 className="survey-title">{surveyTitle}</h1>
            {questions.length > 0 && (
                <div>
                    <h2>{questions[currentQuestionIndex].text}</h2>
                    {renderQuestionInput()}
                    <button onClick={handleAnswerSelect}>
                        {currentQuestionIndex < questions.length - 1 ? 'Next' : 'Finish'}
                    </button>
                </div>
            )}
        </div>
    );
}

export default SurveyPage;