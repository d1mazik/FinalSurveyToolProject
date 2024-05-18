import React, { useState, useEffect } from 'react';
import { getQuestions } from '../service/questionService';

function AllQuestionsScreen() {
    const [questions, setQuestions] = useState([]);

    useEffect(() => {
        fetchQuestions();
    }, []);

    const fetchQuestions = async () => {
        try {
            const data = await getQuestions();
            setQuestions(data);
        } catch (error) {
            console.error('Error fetching questions:', error);
        }
    };

    return (
        <div>
            <h1>Alla Frågor</h1>
            <ul>
                {questions.map(question => (
                    <li key={question.id}>{question.text}</li>
                ))}
            </ul>
        </div>
    );
}

export default AllQuestionsScreen;