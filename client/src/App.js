import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import MenuScreen from './screens/MenuScreen';
import AllQuestionsScreen from './screens/AllQuestionsScreen'; // Importera AllQuestionsScreen-komponenten

function App() {
    return (
        <Router>
            <div className="App">
                <Routes>
                    <Route path="/" element={<MenuScreen />} />
                    <Route path="/all-questions" element={<AllQuestionsScreen />} /> {/* Använd AllQuestionsScreen-komponenten */}
                </Routes>
            </div>
        </Router>
    );
}

export default App;
