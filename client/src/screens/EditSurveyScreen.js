import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { getSurveys, deleteSurvey, updateSurvey } from '../service/surveyService';
import { getQuestionsForSurvey } from '../service/questionService';
import { deleteQuestion, updateQuestion } from '../service/questionService';
import '../styles/EditSurvey.css';

function EditSurveyScreen() {
    const [surveys, setSurveys] = useState([]);
    const [questions, setQuestions] = useState({});
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const [isModalOpen, setIsModalOpen] = useState(false);
    const [currentEdit, setCurrentEdit] = useState({ type: '', data: {} });

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
        const survey = surveys.find(s => s.id === id);
        openModal('survey', { id: survey.id, title: survey.title, description: survey.description });
    };

    const openModal = (type, data) => {
        setCurrentEdit({ type, data });
        setIsModalOpen(true);
    };

    const closeModal = () => {
        setIsModalOpen(false);
    };

    const handleSave = async (e) => {
        e.preventDefault();
        closeModal();
        try {
            if (currentEdit.type === 'survey') {
                await updateSurvey(currentEdit.data.id, {
                    title: currentEdit.data.title,
                    description: currentEdit.data.description,
                    userId: currentEdit.data.userId
                });
                fetchAllSurveys();
            } else {
                await updateQuestion(currentEdit.data.id, { text: currentEdit.data.text });
                fetchQuestions(currentEdit.data.surveyId);
            }
        } catch (error) {
            console.error('Error updating:', error);
            setError('Failed to update: ' + (currentEdit.type === 'survey' ? 'survey' : 'question'));
        }
    };

    const fetchQuestions = async (surveyId) => {
        try {
            const fetchedQuestions = await getQuestionsForSurvey(surveyId);
            setQuestions(prev => ({ ...prev, [surveyId]: fetchedQuestions }));
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
        <div className="survey-container">
            <h1>Edit Surveys</h1>
            {error && <p className="error">{error}</p>}
            {loading ? <p>Loading...</p> : surveys.map((survey) => (
                <div key={survey.id} className="survey-form">
                    <h2>{survey.title}</h2>
                    <button onClick={() => handleEditSurvey(survey.id)} className="edit-button">Edit</button>
                    <button onClick={() => fetchQuestions(survey.id)} className="load-button">Questions</button>
                    <button onClick={() => handleDeleteSurvey(survey.id)} className="delete-button">Delete</button>
                    {questions[survey.id] && questions[survey.id].map(question => (
                        <div key={question.id}>
                            <p>{question.text}</p>
                            <button onClick={() => handleDeleteQuestion(survey.id, question.id)} className="delete-button">Delete Question</button>
                        </div>
                    ))}
                </div>
            ))}
            {isModalOpen && (
                <div className="modal" style={{ display: isModalOpen ? 'block' : 'none' }}>
                    <div className="modal-content">
                        <span className="close" onClick={closeModal}>&times;</span>
                        <form onSubmit={handleSave}>
                            <label>{currentEdit.type === 'survey' ? 'Edit Survey' : 'Edit Question'}</label>
                            <input
                                type="text"
                                value={currentEdit.type === 'survey' ? currentEdit.data.title : currentEdit.data.text}
                                onChange={(e) => setCurrentEdit({
                                    ...currentEdit,
                                    data: { ...currentEdit.data, title: e.target.value, text: e.target.value }
                                })}
                                required
                            />
                            {currentEdit.type === 'survey' && (
                                <input
                                    type="text"
                                    value={currentEdit.data.description}
                                    onChange={(e) => setCurrentEdit({
                                        ...currentEdit,
                                        data: { ...currentEdit.data, description: e.target.value }
                                    })}
                                    required
                                />
                            )}
                            <button type="submit">Save Changes</button>
                        </form>
                    </div>
                </div>
            )}
        </div>
    );
}

export default EditSurveyScreen;