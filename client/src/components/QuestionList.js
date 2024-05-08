import React, { useEffect, useState } from 'react';
import { getQuestions } from '../services/questionService';

function QuestionList() {
    const [questions, setQuestions] = useState([]);
    const [error, setError] = useState(null);

    useEffect(() => {
        getQuestions().then(setQuestions).catch(err => {
            setError(err.message);
            console.error('Failed to fetch questions:', err);
        });
    }, []);

    return (
        <div>
            <h1>Frågor</h1>
            {error ? <p>{error}</p> : questions.map(question => (
                <div key={question.id}>{question.text}</div>
            ))}
        </div>
    );
}

export default QuestionList;
