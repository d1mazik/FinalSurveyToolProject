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
        if (currentQuestionIndex < questions.length - 1) {
            await submitAnswer(sessionId, questions[currentQuestionIndex].id, currentAnswer);
            setCurrentQuestionIndex(currentQuestionIndex + 1);
            setCurrentAnswer({});
        } else {
            await submitAnswer(sessionId, questions[currentQuestionIndex].id, currentAnswer);
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
                            <button key={option.id} className="option-button" onClick={() => handleAnswerSelect({ optionId: option.id })}>
                                {option.text}
                            </button>
                        ))}
                    </div>
                );
            case 'SCALE':
                return (
                    <div>
                        {[...Array(currentQuestion.maxScale - currentQuestion.minScale + 1).keys()].map(scale => (
                            <button key={scale + currentQuestion.minScale} className="scale-button" onClick={() => handleAnswerSelect({ scale: scale + currentQuestion.minScale })}>
                                {scale + currentQuestion.minScale}
                            </button>
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
