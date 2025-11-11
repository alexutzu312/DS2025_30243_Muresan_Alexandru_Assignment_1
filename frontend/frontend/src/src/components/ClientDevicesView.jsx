import React, { useState, useEffect } from 'react';
import { useAuth } from '../context/AuthContext';
import { getDevicesByUserId } from '../services/apiService';
// Asigură-te că ai instalat 'jwt-decode': npm install jwt-decode

const ClientDevicesView = () => {
    const { userId, logout, isAuthenticated } = useAuth();
    const [devices, setDevices] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        if (!isAuthenticated || !userId) return;

        const fetchClientData = async () => {
            try {
                // userId este preluat din AuthContext (care îl decodifică din JWT)
                // API call: GET /api/devices/user/{userId}
                const data = await getDevicesByUserId(userId);
                setDevices(data || []);
            } catch (err) {
                setError("Eroare la încărcarea dispozitivelor asignate. Reautentificați-vă.");
            } finally {
                setLoading(false);
            }
        };

        fetchClientData();
    }, [isAuthenticated, userId]);

    if (!isAuthenticated) return <div>Vă rugăm să vă autentificați.</div>;
    if (loading) return <div>Se încarcă dispozitivele personale...</div>;
    if (error) return <div style={{ color: 'red' }}>Eroare: {error}</div>;

    return (
        <div style={{ padding: '20px' }}>
            <h1>Dispozitivele Mele (Client View)</h1>
            <p>ID Utilizator: **{userId}**</p>
            <button onClick={logout}>Logout</button>

            {devices.length === 0 ? (
                <p>Nu aveți dispozitive asignate momentan.</p>
            ) : (
                <table border="1" style={{ width: '100%', borderCollapse: 'collapse', marginTop: '15px' }}>
                    <thead>
                    <tr style={{backgroundColor: '#f2f2f2'}}><th>Nume</th><th>Consum Maxim (W)</th></tr>
                    </thead>
                    <tbody>
                    {devices.map(d => (
                        <tr key={d.id}>
                            <td>{d.name}</td>
                            <td>{d.maxConsumption}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            )}
        </div>
    );
};

export default ClientDevicesView;