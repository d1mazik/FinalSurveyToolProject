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

export const getSurveys = async () => {
    try {
        const response = await fetch('/api/surveys');
        if (!response.ok) throw new Error('Network response was not ok');
        return await response.json();
    } catch (error) {
        console.error('Error fetching surveys:', error);
        throw error;
    }
};

export const createSurvey = async (surveyData) => {
    try {
        const response = await fetch('/api/surveys', {
            method: 'POST',
            headers: {
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

export const updateSurvey = async (id, surveyData) => {
    try {
        const response = await fetch(`/api/surveys/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(surveyData)
        });
        if (!response.ok) throw new Error('Failed to update survey');
        return await response.json();
    } catch (error) {
        console.error('Error updating survey:', error);
        throw error;
    }
};

export const deleteSurvey = async (id) => {
    try {
        const response = await fetch(`/api/surveys/${id}`, {
            method: 'DELETE'
        });
        if (!response.ok) throw new Error('Failed to delete survey');
        return 'Survey deleted successfully';
    } catch (error) {
        console.error('Error deleting survey:', error);
        throw error;
    }
};

export const getQuestionsForSurvey = async (surveyId) => {
    try {
        const response = await fetch(`/api/questions/survey/${surveyId}`);
        if (!response.ok) throw new Error('Network response was not ok');
        return await response.json();
    } catch (error) {
        console.error('Error fetching questions for survey:', error);
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
