import React, { useState } from 'react';
import axios from 'axios';
import '../styles/LoginScreen.css';

function LoginScreen() {
    const [loginInfo, setLoginInfo] = useState({ username: '', password: '' });
    const [registerInfo, setRegisterInfo] = useState({ firstName: '', lastName: '', email: '', password: '' });
    const [showRegister, setShowRegister] = useState(false);
    const [errorMessage, setErrorMessage] = useState('');

    const handleLogin = async (event) => {
        event.preventDefault();
        setErrorMessage('');
        try {
            const response = await axios.post('http://localhost:8080/api/auth/authenticate', {
                email: loginInfo.username,
                password: loginInfo.password
            });
            console.log('Login Success:', response.data);
        } catch (error) {
            console.error('Login Error:', error);
            setErrorMessage('Felaktig e-postadress eller lösenord.');
        }
    };

    const handleRegister = async (event) => {
        event.preventDefault();
        setErrorMessage('');
        try {
            const response = await axios.post('http://localhost:8080/api/auth/register', {
                firstName: registerInfo.firstName,
                lastName: registerInfo.lastName,
                email: registerInfo.email,
                password: registerInfo.password
            });
            console.log('Registration Success:', response.data);
            // Rensa formuläret eller omdirigera användaren
        } catch (error) {
            if (error.response) {
                // Servern svarade med en statuskod som ligger utanför 2xx-serien
                console.error('Registration Error:', error.response.data);
                setErrorMessage(error.response.data.message || 'Registrering misslyckades. Kontrollera uppgifterna och försök igen.');
            } else {
                // Något hände i inställningen av förfrågan som utlöste ett fel
                console.error('Registration Error:', error.message);
                setErrorMessage('Kan inte nå servern. Kontrollera din nätverksanslutning.');
            }
        }
    };

    const toggleRegister = () => {
        setShowRegister(!showRegister);
    };

    return (
        <div className="login-container">
            <h2>Logga In</h2>
            <form onSubmit={handleLogin}>
                <div className="form-group">
                    <label>E-post:</label>
                    <input
                        type="email"
                        value={loginInfo.username}
                        onChange={(e) => setLoginInfo({ ...loginInfo, username: e.target.value })}
                        required
                    />
                </div>
                <div className="form-group">
                    <label>Lösenord:</label>
                    <input
                        type="password"
                        value={loginInfo.password}
                        onChange={(e) => setLoginInfo({ ...loginInfo, password: e.target.value })}
                        required
                    />
                </div>
                <button type="submit">Logga in</button>
                <button type="button" onClick={toggleRegister} className="register">Registrera</button>
            </form>
            {errorMessage && <div className="error-message">{errorMessage}</div>}

            <div className={`form-section ${showRegister ? 'open' : ''}`}>
                {showRegister && (
                    <div>
                        <h2>Registrera</h2>
                        <form onSubmit={handleRegister}>
                            <div className="form-group">
                                <label>Förnamn:</label>
                                <input
                                    type="text"
                                    value={registerInfo.firstName}
                                    onChange={(e) => setRegisterInfo({ ...registerInfo, firstName: e.target.value })}
                                    required
                                />
                            </div>
                            <div className="form-group">
                                <label>Efternamn:</label>
                                <input
                                    type="text"
                                    value={registerInfo.lastName}
                                    onChange={(e) => setRegisterInfo({ ...registerInfo, lastName: e.target.value })}
                                    required
                                />
                            </div>
                            <div className="form-group">
                                <label>E-post:</label>
                                <input
                                    type="email"
                                    value={registerInfo.email}
                                    onChange={(e) => setRegisterInfo({ ...registerInfo, email: e.target.value })}
                                    required
                                />
                            </div>
                            <div className="form-group">
                                <label>Lösenord:</label>
                                <input
                                    type="password"
                                    value={registerInfo.password}
                                    onChange={(e) => setRegisterInfo({ ...registerInfo, password: e.target.value })}
                                    required
                                />
                            </div>
                            <button type="submit">Registrera</button>
                        </form>
                    </div>
                )}
            </div>
        </div>
    );
}

export default LoginScreen;
