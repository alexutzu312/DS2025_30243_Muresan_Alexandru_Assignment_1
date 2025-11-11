import React, { useState } from 'react';
// Asigură-te că aceste căi sunt corecte în structura ta de fișiere
import { loginUser } from './services/apiService';
import { useAuth } from "./context/AuthContext";

const Login = () => {
    const { login, isAuthenticated } = useAuth();
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState(null);

    // 1. Redirecționare simplă dacă e deja autentificat
    // Logica principală de redirecționare este în App.js, dar acest return oprește randarea formularului.
    // if (isAuthenticated) {
    //     return null;
    // }

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError(null);
        try {
            // Apel API către Traefik: POST http://localhost:8081/api/auth/login
            const response = await loginUser({ username, password });

            // Răspunsul așteptat este de tip JwtResponse: { token, id, username, role }
            const jwtToken = response.accessToken || response.token;
            const userRole = response.role;

            if (jwtToken && userRole) {
                // Stochează token-ul, rolul și ID-ul în context/localStorage
                login(jwtToken); // Funcția 'login' preia tokenul și îl decodifică
            } else {
                setError('Autentificare eșuată: Răspuns invalid de la server.');
            }
        } catch (err) {
            setError('Autentificare eșuată. Verificați datele.');
        }
    };

    return (
        <div style={{ padding: '50px', maxWidth: '400px', margin: '50px auto', border: '1px solid #ccc', borderRadius: '5px' }}>
            <h2>Login</h2>
            <form onSubmit={handleSubmit}>

                {/* ELEMENT NOU: Username Input */}
                <div style={{ marginBottom: '15px' }}>
                    <label style={{ display: 'block', marginBottom: '5px' }}>Username:</label>
                    <input
                        type="text"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        required
                        style={{ width: '100%', padding: '8px', boxSizing: 'border-box' }}
                    />
                </div>

                {/* ELEMENT NOU: Password Input */}
                <div style={{ marginBottom: '20px' }}>
                    <label style={{ display: 'block', marginBottom: '5px' }}>Password:</label>
                    <input
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                        style={{ width: '100%', padding: '8px', boxSizing: 'border-box' }}
                    />
                </div>

                <button type="submit" style={{ padding: '10px 15px', cursor: 'pointer', backgroundColor: '#333', color: 'white', border: 'none', borderRadius: '3px' }}>
                    Login
                </button>
                {error && <p style={{ color: 'red', marginTop: '10px', fontSize: '0.9em' }}>{error}</p>}
            </form>
        </div>
    );
};

export default Login;