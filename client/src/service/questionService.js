
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
