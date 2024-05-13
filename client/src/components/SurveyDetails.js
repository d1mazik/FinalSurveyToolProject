import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';

function SurveyDetails() {
    const [questions, setQuestions] = useState([]);
    const { surveyId } = useParams();

    useEffect(() => {
        fetchQuestions();
    }, []);

    const fetchQuestions = async () => {
        try {
            const response = await fetch(`http://localhost:8080/api/surveys/${surveyId}/questions`);
            const data = await response.json();
            setQuestions(data);
        } catch (error) {
            console.error('Failed to fetch questions:', error);
        }
    };

    return (
        <div>
            <h1>Survey Questions</h1>
            <ul>
                {questions.map(question => (
                    <li key={question.id}>{question.text}</li>
                ))}
            </ul>
        </div>
    );
}

export default SurveyDetails;
