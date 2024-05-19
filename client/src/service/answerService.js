import axios from 'axios';

const API_URL = 'http://localhost:8080/api/answers';

export const getAnswersForQuestion = async (questionId) => {
    const response = await axios.get(`${API_URL}/question/${questionId}`);
    return response.data;
};

export const updateAnswer = async (id, answerData) => {
    const response = await axios.put(`${API_URL}/${id}`, answerData);
    return response.data;
};

export const deleteAnswer = async (id) => {
    const response = await axios.delete(`${API_URL}/${id}`);
    return response.data;
};
