import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { getSurveys, deleteSurvey, getQuestionsForSurvey, deleteQuestion } from '../service/surveyService';
import '../styles/EditSurvey.css';

function EditSurveyScreen() {
    const [surveys, setSurveys] = useState([]);
    const [questions, setQuestions] = useState({});
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        fetchAllSurveys();
    }, []);

    const fetchAllSurveys = async () => {
        setLoading(true);
        setError('');
        try {
            const data = await getSurveys();
            setSurveys(data);
            setLoading(false);
        } catch (error) {
            console.error('Failed to fetch surveys', error);
            setError('Failed to fetch surveys');
            setLoading(false);
        }
    };

    const handleDeleteSurvey = async (id) => {
        try {
            await deleteSurvey(id);
            setSurveys(prevSurveys => prevSurveys.filter(survey => survey.id !== id));
        } catch (error) {
            console.error('Failed to delete survey', error);
            setError(`Failed to delete survey: ${id}`);
        }
    };

    const handleEditSurvey = (id) => {
        navigate(`/edit-survey/${id}`);
    };

    const fetchQuestions = async (surveyId) => {
        try {
            const questions = await getQuestionsForSurvey(surveyId);
            setQuestions(prev => ({ ...prev, [surveyId]: questions }));
        } catch (error) {
            console.error('Failed to fetch questions for survey', error);
            setError(`Failed to fetch questions for survey: ${surveyId}`);
        }
    };

    const handleDeleteQuestion = async (surveyId, questionId) => {
        try {
            await deleteQuestion(questionId);
            setQuestions(prev => ({
                ...prev,
                [surveyId]: prev[surveyId].filter(question => question.id !== questionId)
            }));
        } catch (error) {
            console.error('Failed to delete question', error);
            setError(`Failed to delete question: ${questionId}`);
        }
    };

    return (
        <div>
            <h1>Edit Surveys</h1>
            {error && <p className="error">{error}</p>}
            {loading ? <p>Loading...</p> : surveys.map((survey) => (
                <div key={survey.id}>
                    <h2>{survey.title}</h2>
                    <button onClick={() => handleEditSurvey(survey.id)}>Edit</button>
                    <button onClick={() => handleDeleteSurvey(survey.id)}>Delete</button>
                    <button onClick={() => fetchQuestions(survey.id)}>Load Questions</button>
                    {questions[survey.id] && questions[survey.id].map(question => (
                        <div key={question.id}>
                            <p>{question.text}</p>
                            <button onClick={() => handleDeleteQuestion(survey.id, question.id)}>Delete Question</button>
                        </div>
                    ))}
                </div>
            ))}
        </div>
    );
}

export default EditSurveyScreen;
