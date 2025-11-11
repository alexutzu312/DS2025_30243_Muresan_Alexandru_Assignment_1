import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import {AuthProvider, useAuth} from "./src/context/AuthContext";
import Login from "./src/Login";
import AdminDashboard from "./src/components/AdminDashboard";
import ClientDevicesView from "./src/components/ClientDevicesView";


// Rută protejată care verifică autentificarea și rolul
const ProtectedRoute = ({ children, roles }) => {
  const { isAuthenticated, role, logout } = useAuth();

  if (!isAuthenticated) {
    return <Navigate to="/login" replace />;
  }

  // Verifică dacă rolul utilizatorului este inclus în rolurile permise
  if (roles && !roles.includes(role)) {
    return (
        <div style={{padding: '20px', color: 'red'}}>
          <h1>Acces Interzis (403)</h1>
          <p>Nu aveți rolul necesar pentru a accesa această resursă.</p>
          <button onClick={logout}>Logout</button>
        </div>
    );
  }

  return children;
};

function App() {
  return (

      <Router>
        <AuthProvider>
          <div className="App">
            <Routes>
              <Route path="/login" element={<Login />} />

              {/* RUTA ADMIN: CRUD Users & Devices */}
              <Route path="/admin" element={
                <ProtectedRoute roles={['ROLE_ADMIN']}>
                  <AdminDashboard />
                </ProtectedRoute>
              } />

              {/* RUTA CLIENT: Vizualizare Dispozitive Personale */}
              <Route path="/client/devices" element={
                <ProtectedRoute roles={['ROLE_ADMIN', 'ROLE_CLIENT']}>
                  <ClientDevicesView />
                </ProtectedRoute>
              } />

              {/* RUTA DEFAULT/HOME: Redirecționează la pagina corespunzătoare după rol */}
              <Route path="/" element={<HomeRedirect />} />
            </Routes>
          </div>
        </AuthProvider>
      </Router>
  );
}

// Componentă pentru a redirecționa automat după login
const HomeRedirect = () => {
  const { isAuthenticated, isAdmin } = useAuth();

  if (!isAuthenticated) {
    return <Navigate to="/login" replace />;
  }

  if (isAdmin) {
    return <Navigate to="/admin" replace />;
  } else {
    return <Navigate to="/client/devices" replace />;
  }
};


export default App;