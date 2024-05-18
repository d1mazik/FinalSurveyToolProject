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
            body: JSON.stringify({ surveyId, userId })  // Ensure both IDs are sent
        });
        if (!response.ok) {
            throw new Error(`Failed to start session: ${response.statusText}`);
        }
        return await response.json();
    } catch (error) {
        console.error('Error starting session:', error);
        throw error;
    }
};

export const createSurvey = async (surveyData) => {
    const token = localStorage.getItem('token');
    try {
        const response = await fetch('/api/surveys', {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(surveyData)
        });
        if (!response.ok) throw new Error('Failed to create survey');
        return await response.json();
    } catch (error) {
        console.error('Error creating survey:', error);
        throw error;
    }
};

export const getSurveys = async () => {
    const token = localStorage.getItem('token');
    try {
        const response = await fetch('/api/surveys', {
            headers: { 'Authorization': `Bearer ${token}` }
        });
        if (!response.ok) {
            throw new Error(`Failed to fetch surveys: Status ${response.status}`);
        }
        return await response.json();
    } catch (error) {
        console.error(`Error fetching surveys:`, error.message);
        throw error;
    }
};

export const updateSurvey = async (surveyId, surveyData) => {
    const token = localStorage.getItem('token');
    try {
        const response = await fetch(`/api/surveys/${surveyId}`, {
            method: 'PUT',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(surveyData)
        });

        if (!response.ok) {
            const errorText = await response.text(); // Hämta hela felmeddelandet från servern
            throw new Error(`Failed to update survey: ${errorText}`);
        }

        return await response.json();
    } catch (error) {
        console.error('Error updating survey:', error);
        throw error;
    }
};


export const deleteSurvey = async (surveyId) => {
    const token = localStorage.getItem('token');
    try {
        const response = await fetch(`/api/surveys/${surveyId}`, {
            method: 'DELETE',
            headers: { 'Authorization': `Bearer ${token}` }
        });
        if (!response.ok) throw new Error('Failed to delete survey');
        return 'Survey deleted successfully';
    } catch (error) {
        console.error('Error deleting survey:', error);
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
    try {
        const response = await fetch(`/api/surveys/${surveyId}`);
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        const data = await response.json();
        return data.title; // Returnerar titeln på undersökningen från API-responsen
    } catch (error) {
        console.error('Error fetching survey title:', error);
        throw error;
    }
};
export const endSession = async (sessionId) => {
    const token = localStorage.getItem('token');
    try {
        const response = await fetch(`http://localhost:8080/api/session/end/${sessionId}`, {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ sessionId })
        });
        if (!response.ok) {
            throw new Error('Failed to end session');
        }
        return await response.json();  // Om servern skickar tillbaka session objektet
    } catch (error) {
        console.error('Error ending session:', error);
        throw error;  // Kasta fel så att det kan hanteras i frontend
    }
};