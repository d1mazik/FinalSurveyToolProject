import React, { useEffect, useState } from 'react';
import { getQuestionsForSurvey } from '../service/questionService';

function QuestionList({ surveyId }) {
    const [questions, setQuestions] = useState([]);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchQuestions = async () => {
            try {
                const data = await getQuestionsForSurvey(surveyId);
                setQuestions(data);
            } catch (err) {
                setError(err.message);
                console.error('Failed to fetch questions:', err);
            }
        };

        fetchQuestions();
    }, [surveyId]);

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
