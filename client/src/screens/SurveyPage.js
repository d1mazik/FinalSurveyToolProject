import React, { useState, useEffect } from 'react';
import { useParams, useLocation, useNavigate } from 'react-router-dom';
import { getSurveyTitle, getQuestionsForSurvey, submitAnswer } from '../service/questionService';
import { endSession } from '../service/surveyService';
import '../styles/surveyPage.css';

function SurveyPage() {
    const { surveyId } = useParams();
    const { state } = useLocation();
    const sessionId = state.sessionId;
    const [questions, setQuestions] = useState([]);
    const [surveyTitle, setSurveyTitle] = useState('');
    const [currentQuestionIndex, setCurrentQuestionIndex] = useState(0);
    const [currentAnswer, setCurrentAnswer] = useState({});
    const [surveyCompleted, setSurveyCompleted] = useState(false);
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
            setSurveyCompleted(true);
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
        if (!currentQuestion || surveyCompleted) {
            return <h2 className="thank-you">Tack för dina svar!</h2>;
        }

        switch(currentQuestion.type) {
            case 'OPTIONS':
                return (
                    <div className="options-list">
                        {currentQuestion.options.map(option => (
                            <div key={option.id} className="option-item">
                                <input
                                    type="radio"
                                    id={`option-${option.id}`}
                                    name={`options-${currentQuestionIndex}`}
                                    value={option.id}
                                    checked={currentAnswer.selectedOption === option.id}
                                    onChange={handleOptionChange}
                                />
                                <label htmlFor={`option-${option.id}`} className="option-button">
                                    {option.text}
                                </label>
                            </div>
                        ))}
                    </div>
                );
            case 'SCALE':
                return (
                    <div className="scale-list">
                        {[...Array(currentQuestion.maxScale - currentQuestion.minScale + 1).keys()].map(scale => (
                            <div key={scale + currentQuestion.minScale} className="scale-item">
                                <input
                                    type="radio"
                                    id={`scale-${scale + currentQuestion.minScale}`}
                                    name={`scale-${currentQuestionIndex}`}
                                    value={scale + currentQuestion.minScale}
                                    checked={currentAnswer.scale === (scale + currentQuestion.minScale)}
                                    onChange={handleScaleChange}
                                />
                                <label htmlFor={`scale-${scale + currentQuestion.minScale}`} className="scale-button">
                                    {scale + currentQuestion.minScale}
                                </label>
                            </div>
                        ))}
                    </div>
                );
            case 'TEXT':
                return (
                    <textarea
                        className="text-input"
                        value={currentAnswer.text || ''}
                        onChange={handleTextChange}
                    />
                );
            default:
                return <div>Okänd frågetyp</div>;
        }
    };

    return (
        <div className="survey-page-container">
            <h1 className="survey-title">{surveyTitle}</h1>
            <h2>{questions[currentQuestionIndex] ? questions[currentQuestionIndex].text : ''}</h2>
            {renderQuestionInput()}
            {!surveyCompleted && (
                <button
                    onClick={handleAnswerSelect}
                    className={currentQuestionIndex < questions.length - 1 ? "next-button" : "finish-button"}>
                    {currentQuestionIndex < questions.length - 1 ? 'Nästa' : 'Avsluta'}
                </button>
            )}
        </div>
    );
}

export default SurveyPage;