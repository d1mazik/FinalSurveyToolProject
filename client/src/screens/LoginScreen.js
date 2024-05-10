import React, { useState } from 'react';
import axios from 'axios';
import emailIcon from "../assets/email-logo.png";
import passwordIcon from "../assets/password-logo.png";
import backgroundImage from "../assets/survey-backround.webp";
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
        } catch (error) {
            console.error('Registration Error:', error);
            setErrorMessage('Registrering misslyckades. Kontrollera uppgifterna och försök igen.');
        }
    };

    const toggleRegister = () => {
        setShowRegister(!showRegister);
    };

    return (
        <div className="login-container">
            {!showRegister ? (
                <div className="login-form">
                    <h2>Login</h2>
                    <form onSubmit={handleLogin}>
                        <div className="form-group">
                            <img src={emailIcon} alt="Email" className="iconLogin" />
                            <input
                                type="email"
                                placeholder="Enter your email"
                                value={loginInfo.username}
                                onChange={(e) => setLoginInfo({ ...loginInfo, username: e.target.value })}
                                required
                            />
                        </div>
                        <div className="form-group">
                            <img src={passwordIcon} alt="Password" className="iconLogin" />
                            <input
                                type="password"
                                placeholder="Enter your password"
                                value={loginInfo.password}
                                onChange={(e) => setLoginInfo({ ...loginInfo, password: e.target.value })}
                                required
                            />
                        </div>
                        <button type="submit">Login</button>
                        {errorMessage && <div className="error-message">{errorMessage}</div>}
                        <div className="signup-link">
                            Don't have an account? <span onClick={toggleRegister} style={{color: '#6200EA', cursor: 'pointer'}}>Signup now</span>
                        </div>
                    </form>
                </div>
            ) : (
                <div className="login-form">
                    <h2>Signup</h2>
                    <form onSubmit={handleRegister}>
                        <div className="form-group">
                            <input
                                type="firstName"
                                placeholder="Enter your first name"
                                value={registerInfo.firstName}
                                onChange={(e) => setRegisterInfo({ ...registerInfo, firstName: e.target.value })}
                                required
                            />
                        </div>
                        <div className="form-group">
                            <input
                                type="lastName"
                                placeholder="Enter your last name"
                                value={registerInfo.lastName}
                                onChange={(e) => setRegisterInfo({ ...registerInfo, lastName: e.target.value })}
                                required
                            />
                        </div>
                        <div className="form-group">
                            <input
                                type="email"
                                placeholder="Enter your email"
                                value={registerInfo.email}
                                onChange={(e) => setRegisterInfo({ ...registerInfo, email: e.target.value })}
                                required
                            />
                        </div>
                        <div className="form-group">
                            <input
                                type="password"
                                placeholder="Enter your password"
                                value={registerInfo.password}
                                onChange={(e) => setRegisterInfo({ ...registerInfo, password: e.target.value })}
                                required
                            />
                        </div>
                        <button type="submit">Signup</button>
                        {errorMessage && <div className="error-message">{errorMessage}</div>}
                        <div className="signup-link">
                            Already have an account? <span onClick={toggleRegister} style={{color: '#6200EA', cursor: 'pointer'}}>Login</span>
                        </div>
                    </form>
                </div>


            )}
            { !showRegister && <div className="right-section" style={{ backgroundImage: `url(${backgroundImage})` }}></div>}
        </div>
    );
}

export default LoginScreen;
