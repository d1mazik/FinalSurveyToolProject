// questionService.js

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

export const getQuestionsForSurvey = async (surveyId) => {
    const token = localStorage.getItem('token');
    try {
        const response = await fetch(`/api/questions/survey/${surveyId}`, {
            headers: {
                'Authorization': `Bearer ${token}`,
            },
        });
        if (!response.ok) throw new Error('Failed to fetch questions');
        return await response.json();
    } catch (error) {
        console.error('Error fetching questions:', error);
        throw error;
    }
};

export const getQuestions = async () => {
    try {
        const response = await fetch('/api/questions');
        if (!response.ok) throw new Error('Network response was not ok');
        return await response.json();
    } catch (error) {
        console.error('Error fetching questions:', error);
        throw error;
    }
};

export const createQuestion = async (questionData) => {
    try {
        const response = await fetch('/api/questions', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(questionData)
        });
        if (!response.ok) throw new Error('Failed to create question');
        return await response.json();
    } catch (error) {
        console.error('Error creating question:', error);
        throw error;
    }
};
export const updateQuestion = async (id, questionData) => {
    try {
        const response = await fetch(`/api/questions/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(questionData)
        });
        if (!response.ok) throw new Error('Failed to update question');
        return await response.json();
    } catch (error) {
        console.error('Error updating question:', error);
        throw error;
    }
};

export const deleteQuestion = async (id) => {
    try {
        const response = await fetch(`/api/questions/${id}`, {
            method: 'DELETE'
        });
        if (!response.ok) throw new Error('Failed to delete question');
        return 'Question deleted successfully';
    } catch (error) {
        console.error('Error deleting question:', error);
        throw error;
    }

};

export const submitAnswer = async (sessionId, questionId, answerData) => {
    const token = localStorage.getItem('token');
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
                ...answerData
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