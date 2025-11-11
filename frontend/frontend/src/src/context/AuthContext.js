import React, { createContext, useState, useContext } from 'react';
import { jwtDecode } from 'jwt-decode'; // Instalează: npm install jwt-decode

const AuthContext = createContext(null);

export const AuthProvider = ({ children }) => {
    const [token, setToken] = useState(localStorage.getItem('jwtToken'));
    const [role, setRole] = useState(localStorage.getItem('userRole'));
    const [userId, setUserId] = useState(localStorage.getItem('userId')); // NOU: Stocare User ID

    const decodeTokenAndSetState = (jwtToken) => {
        if (!jwtToken) {
            setToken(null); setRole(null); setUserId(null);
            localStorage.removeItem('jwtToken');
            localStorage.removeItem('userRole');
            localStorage.removeItem('userId');
            return;
        }

        try {
            const decoded = jwtDecode(jwtToken);
            console.log(decoded);

            // Extrage datele esențiale
            const userRole = decoded.role || decoded.roles;
            const newUserId = decoded.userId || decoded.id;

            setToken(jwtToken);
            setRole(userRole);
            setUserId(newUserId);

            // Stocare în localStorage
            localStorage.setItem('jwtToken', jwtToken);
            localStorage.setItem('userRole', userRole);
            localStorage.setItem('userId', newUserId);
        } catch (e) {
            console.error("Token decoding failed:", e);
            logout();
        }
    };

    const login = (jwtToken) => {
        decodeTokenAndSetState(jwtToken);
    };

    const logout = () => {
        decodeTokenAndSetState(null);
    };

    const isAdmin = role === 'ROLE_ADMIN';
    const isAuthenticated = !!token;

    return (
        <AuthContext.Provider value={{ token, userId, role, isAdmin, isAuthenticated, login, logout }}>
            {children}
        </AuthContext.Provider>
    );
};

export const useAuth = () => {
    return useContext(AuthContext);
};