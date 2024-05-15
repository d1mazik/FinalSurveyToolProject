// surveyService.js

export const startSurveySession = async (surveyId, userId) => {
    const token = localStorage.getItem('token');
    try {
        const response = await fetch('http://localhost:8080/api/session/start', {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ surveyId, userId })  // Ensure both surveyId and userId are included
        });
        if (!response.ok) {
            throw new Error('Failed to start session: ' + response.statusText);
        }
        return await response.json();
    } catch (error) {
        console.error('Error starting session:', error);
        throw error;
    }
};


// Assuming this file is in src/service/surveyService.js

export const submitAnswer = async (sessionId, questionId, answer) => {
    const token = localStorage.getItem('token');  // Make sure you are retrieving the JWT correctly
    try {
        const response = await fetch('/api/answers', {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                sessionId: sessionId,
                questionId: questionId,
                ...answer
            })
        });
        if (!response.ok) {
            throw new Error(`Failed to submit answer: ${response.statusText}`);
        }
        return await response.json();  // This should return the newly created or updated answer
    } catch (error) {
        console.error('Error submitting answer:', error);
        throw error;
    }
};

// In src/service/surveyService.js

export const getQuestionsForSurvey = async (surveyId) => {
    const token = localStorage.getItem('token');
    try {
        const response = await fetch(`/api/surveys/${surveyId}/questions`, {
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        });
        if (!response.ok) {
            throw new Error(`Failed to fetch questions: ${response.statusText}`);
        }
        return await response.json();
    } catch (error) {
        console.error('Error fetching questions:', error);
        throw error;
    }
};

export const getSurveyTitle = async (surveyId) => {
    const token = localStorage.getItem('token');
    try {
        const response = await fetch(`/api/surveys/${surveyId}/title`, {
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        });
        if (!response.ok) {
            throw new Error(`Failed to fetch survey title: ${response.statusText}`);
        }
        const data = await response.json();
        return data.title;
    } catch (error) {
        console.error('Error fetching survey title:', error);
        throw error;
    }
};


