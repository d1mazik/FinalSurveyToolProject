import React, { useState } from 'react';
import { createSurvey, createQuestion } from '../service/questionService';
import '../styles/CreateSurveyAndQuestionScreen.css'; // Importera specifika CSS för den här komponenten

function CreateSurveyAndQuestionScreen() {
    // Tillstånd för undersökning och fråga
    const [survey, setSurvey] = useState({ title: '', description: '' });
    const [question, setQuestion] = useState({ text: '', type: 'OPTIONS', options: [], surveyId: '' });
    const [optionsTemp, setOptionsTemp] = useState(''); // Tillstånd för temporära svarsalternativ

    // Funktion för att hantera inmatningsändringar
    const handleInputChange = (e, setter) => {
        setter(prev => ({ ...prev, [e.target.name]: e.target.value }));
    };

    // Funktion för att lägga till svarsalternativ
    const addOption = () => {
        setQuestion(prev => ({ ...prev, options: [...prev.options, optionsTemp] }));
        setOptionsTemp('');
    };

    // Funktion för att skapa en ny undersökning
    const handleCreateSurvey = async (e) => {
        e.preventDefault();
        try {
            const data = await createSurvey(survey);
            alert('Survey created successfully');
            setQuestion(prev => ({ ...prev, surveyId: data.id }));
        } catch (error) {
            alert('Error creating survey: ' + error.message);
        }
    };

    // Funktion för att lägga till en ny fråga
    const handleCreateQuestion = async (e) => {
        e.preventDefault();
        if (!question.surveyId) {
            alert('Please create a survey first');
            return;
        }
        try {
            await createQuestion(question);
            alert('Question added successfully');
            setQuestion(prev => ({ ...prev, text: '', options: [], surveyId: '' }));
            setOptionsTemp(''); // Återställ temporära svarsalternativ
        } catch (error) {
            alert('Error adding question: ' + error.message);
        }
    };

    // Rendera komponenten
    return (
        <div className="survey-container">
            <div className="survey-form">
                {/* Formulär för att skapa en undersökning */}
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
                    <button type="submit" className="survey-button">Create Survey</button>
                </form>
                {/* Formulär för att lägga till en fråga */}
                <form onSubmit={handleCreateQuestion}>
                    <h2>Add Question</h2>
                    <input
                        type="text"
                        name="text"
                        className="survey-input"
                        value={question.text}
                        onChange={e => handleInputChange(e, setQuestion)}
                        placeholder="Enter question text"
                        required
                    />
                    <input
                        type="text"
                        name="surveyId"
                        className="survey-input"
                        value={question.surveyId}
                        onChange={e => handleInputChange(e, setQuestion)}
                        placeholder="Enter survey ID"
                        required
                    />
                    <input
                        type="text"
                        className="survey-input"
                        value={optionsTemp}
                        onChange={e => setOptionsTemp(e.target.value)}
                        placeholder="Enter option"
                    />
                    <button type="button" className="survey-button" onClick={addOption}>Add Option</button>
                    <ul className="options-list">
                        {/* Visa alla tillagda svarsalternativ */}
                        {question.options.map((option, index) => (
                            <li key={index}>{option}</li>
                        ))}
                    </ul>
                    <button type="submit" className="survey-button">Add Question to Survey</button>
                </form>
            </div>
        </div>
    );
}

export default CreateSurveyAndQuestionScreen;
