import React, { useState, useEffect } from 'react';
import { useAuth } from '../context/AuthContext';
import { getUsers, getDevices, assignDevice } from '../services/apiService';

import UserList from './crud/UserList';
import DeviceList from './crud/DeviceList';

const AdminDashboard = () => {
    const { isAdmin, logout } = useAuth();
    const [users, setUsers] = useState([]);
    const [devices, setDevices] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const [selectedUserId, setSelectedUserId] = useState('');
    const [selectedDeviceId, setSelectedDeviceId] = useState('');

    const fetchData = async () => {
        setLoading(true);
        try {
            const [userData, deviceData] = await Promise.all([getUsers(), getDevices()]);
            setUsers(userData || []);
            setDevices(deviceData || []);
            setError(null);
        } catch (err) {
            setError("Nu a reușit să încarce datele. Reautentificați-vă.");
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        if (isAdmin) {
            fetchData();
        }
    }, [isAdmin]);

    const handleAssignment = async (e) => {
        e.preventDefault();
        if (!selectedUserId || !selectedDeviceId) {
            alert("Vă rugăm selectați ambele ID-uri.");
            return;
        }
        try {
            // API call: PATCH /api/devices/{deviceId}/assign/{userId}
            await assignDevice(selectedDeviceId, selectedUserId);
            alert(`Dispozitivul ${selectedDeviceId} a fost asignat utilizatorului ${selectedUserId}!`);
            setSelectedUserId('');
            setSelectedDeviceId('');
        } catch (err) {
            setError(`Asignarea a eșuat. Verificați permisiunile. ${err.message}`);
        }
    };

    if (!isAdmin) return <p>Acces nepermis.</p>;
    if (loading) return <div>Se încarcă Admin Dashboard...</div>;
    if (error) return <div style={{ color: 'red', padding: '20px' }}>Eroare: {error}</div>;

    return (
        <div style={{ padding: '20px' }}>
            <h1>Admin Panel</h1>
            <button onClick={logout} style={{ float: 'right', marginTop: '-50px' }}>Logout</button>

            <UserList users={users} refresh={fetchData} />

            <hr style={{ margin: '40px 0' }} />

            <DeviceList users={users} devices={devices} refresh={fetchData} />

            <hr style={{ margin: '40px 0' }} />

            {/*/!* Secțiunea de Asignare Dispozitive *!/*/}
            {/*<h2>Asignare Dispozitiv (Admin)</h2>*/}
            {/*<form onSubmit={handleAssignment} style={{ border: '1px solid #ddd', padding: '20px' }}>*/}
            {/*    <select value={selectedUserId} onChange={e => setSelectedUserId(e.target.value)} required>*/}
            {/*        <option value="">1. Selectează Utilizator</option>*/}
            {/*        {users.map(u => <option key={u.id} value={u.id}>{u.username} (ID: {u.id})</option>)}*/}
            {/*    </select>*/}

            {/*    <select value={selectedDeviceId} onChange={e => setSelectedDeviceId(e.target.value)} required style={{ marginLeft: '10px' }}>*/}
            {/*        <option value="">2. Selectează Dispozitiv</option>*/}
            {/*        {devices.map(d => <option key={d.id} value={d.id}>{d.name} (Consum: {d.maxConsumption})</option>)}*/}
            {/*    </select>*/}

            {/*    <button type="submit" style={{ marginLeft: '10px' }}>Asignează</button>*/}
            {/*</form>*/}
        </div>
    );
};

export default AdminDashboard;