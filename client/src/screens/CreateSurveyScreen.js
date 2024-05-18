import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { createSurvey, createQuestion } from '../service/surveyService';
import '../styles/CreateSurveyAndQuestionScreen.css';

function CreateSurveyAndQuestionScreen() {
    const navigate = useNavigate();
    const [survey, setSurvey] = useState({ title: '', description: '', userId: '' });
    const [question, setQuestion] = useState({
        text: '',
        type: 'OPTIONS',
        options: [],
        minScale: null,
        maxScale: null,
        surveyId: ''
    });
    const [optionsTemp, setOptionsTemp] = useState('');

    const handleInputChange = (e, setter) => {
        setter(prev => ({ ...prev, [e.target.name]: e.target.value }));
    };

    const handleSelectChange = (e) => {
        setQuestion(prev => ({ ...prev, type: e.target.value, options: [], minScale: null, maxScale: null }));
    };

    const addOption = () => {
        if (optionsTemp !== '') {
            setQuestion(prev => ({ ...prev, options: [...prev.options, optionsTemp] }));
            setOptionsTemp('');
        }
    };

    const handleCreateSurvey = async (e) => {
        e.preventDefault();
        try {
            const data = await createSurvey(survey);
            alert('Survey created successfully');
            setQuestion(prev => ({ ...prev, surveyId: data.id.toString() })); // Converting ID to string if needed
        } catch (error) {
            alert('Error creating survey: ' + error.message);
        }
    };

    const handleCreateQuestion = async (e) => {
        e.preventDefault();
        if (!question.surveyId) {
            alert('Please create a survey first or enter a survey ID');
            return;
        }
        try {
            await createQuestion(question);
            alert('Question added successfully');
            setQuestion(prev => ({ ...prev, text: '', options: [], minScale: null, maxScale: null }));
        } catch (error) {
            alert('Error adding question: ' + error.message);
        }
    };

    const navigateToEditScreen = () => {
        navigate('/edit-survey'); // Använd rätt path som definierats i din routerkonfiguration
    };

    return (
        <div className="survey-container">
            <div className="survey-form">
                <form onSubmit={handleCreateSurvey}>
                    <h2>Create Survey</h2>
                    <input
                        type="text"
                        name="title"
                        className="survey-input"
                        value={survey.title}
                        onChange={e => handleInputChange(e, setSurvey)}
                        placeholder="Enter survey title"
                        required
                    />
                    <textarea
                        name="description"
                        className="survey-textarea"
                        value={survey.description}
                        onChange={e => handleInputChange(e, setSurvey)}
                        placeholder="Enter survey description"
                        required
                    />
                    <input
                        type="text"
                        name="userId"
                        className="survey-input"
                        value={survey.userId}
                        onChange={e => handleInputChange(e, setSurvey)}
                        placeholder="Enter user ID"
                        required
                    />
                    <button type="submit" className="survey-button">Create Survey</button>
                </form>
                <form onSubmit={handleCreateQuestion}>
                    <h2>Add Question</h2>
                    <input
                        type="text"
                        name="surveyId"
                        className="survey-input"
                        value={question.surveyId}
                        onChange={e => handleInputChange(e, setQuestion)}
                        placeholder="Enter Survey ID"
                        required
                    />
                    <select name="type" onChange={handleSelectChange} value={question.type} className="survey-input">
                        <option value="TEXT">Text</option>
                        <option value="OPTIONS">Options</option>
                        <option value="SCALE">Scale</option>
                    </select>
                    <input
                        type="text"
                        name="text"
                        className="survey-input"
                        value={question.text}
                        onChange={e => handleInputChange(e, setQuestion)}
                        placeholder="Enter question text"
                        required
                    />
                    {question.type === 'OPTIONS' && (
                        <>
                            <input
                                type="text"
                                className="survey-input"
                                value={optionsTemp}
                                onChange={e => setOptionsTemp(e.target.value)}
                                placeholder="Enter option"
                            />
                            <button type="button" className="survey-button" onClick={addOption}>Add Option</button>
                            <ul className="options-list">
                                {question.options.map((option, index) => (
                                    <li key={index}>{option}</li>
                                ))}
                            </ul>
                        </>
                    )}
                    {question.type === 'SCALE' && (
                        <>
                            <input
                                type="number"
                                name="minScale"
                                className="survey-input"
                                value={question.minScale || ''}
                                onChange={e => handleInputChange(e, setQuestion)}
                                placeholder="Enter minimum scale"
                            />
                            <input
                                type="number"
                                name="maxScale"
                                className="survey-input"
                                value={question.maxScale || ''}
                                onChange={e => handleInputChange(e, setQuestion)}
                                placeholder="Enter maximum scale"
                            />
                        </>
                    )}
                    <button type="submit" className="survey-button">Add Question to Survey</button>
                </form>
                <button onClick={navigateToEditScreen} className="survey-button edit-survey-button">Edit Surveys and Questions</button>
            </div>
        </div>
    );
}

export default CreateSurveyAndQuestionScreen;
