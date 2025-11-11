import React, { useState, useEffect } from 'react';
import { getDevices, deleteDevice, createDevice, updateDevice } from '../../services/apiService';

// Componenta interna: Formularul de Adaugare/Editare
const DeviceForm = ({ initialData, users, onSave, onCancel }) => {

    // Inițializarea stării: include structura UserReference
    const [formData, setFormData] = useState(() => {
        const initial = initialData || {
            name: '',
            maxConsumption: 0,
            userReference: { id: '' }
        };

        // Dacă este editare (initialData există), trebuie să ne asigurăm că
        // userReference.id este setat corect. Presupunem că initialData ar putea avea idUser.
        // NOTĂ: Dacă initialData.idUser nu există, îl setăm pe null sau ''
        if (initialData && initialData.idUser) {
            initial.userReference = { id: initialData.idUser };
        }

        // Convertim maxConsumption la număr dacă este string
        initial.maxConsumption = parseInt(initial.maxConsumption) || 0;

        return initial;
    });

    const [error, setError] = useState(null);

    // 1. Gestiunea schimbărilor pentru câmpurile simple (name, maxConsumption)
    const handleChange = (e) => {
        const { name, value, type } = e.target;
        setFormData(prev => ({
            ...prev,
            [name]: type === 'number' ? parseInt(value) : value,
        }));
    };

    // 2. Gestiunea schimbării utilizatorului din dropdown
    const handleUserChange = (e) => {
        const userId = e.target.value;
        setFormData(prev => ({
            ...prev,
            // Construim obiectul UserReference cerut de DTO-ul Java
            userReference: { id: userId === '' ? null : userId }
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError(null);

        // Verificare că a fost selectat un utilizator (dacă nu e gol/null)
        if (!formData.userReference.id) {
            setError('Vă rugăm selectați utilizatorul căruia i se asignează dispozitivul.');
            return;
        }

        try {
            if (initialData) {
                await updateDevice(initialData.id, { ...formData, id: initialData.id });
            } else {
                // Dacă este creare (POST)
                await createDevice(formData);
            }
            onSave(); // Apel la funcția de salvare din componenta părinte
        } catch (err) {
            setError(`Eroare la salvare: ${err.message}`);
        }
    };

    // Preluarea ID-ului utilizatorului curent selectat (pentru a seta valoarea dropdown-ului)
    const currentUserId = formData.userReference?.id || '';


    // Dacă lista de utilizatori nu a fost încărcată, nu putem afișa dropdown-ul
    if (!users || users.length === 0) {
        return <div style={{ color: 'orange' }}>Nu se pot adăuga/edita dispozitive: Lista de utilizatori nu este disponibilă.</div>;
    }


    return (
        <form onSubmit={handleSubmit} style={{ border: '1px solid #ccc', padding: '15px', marginBottom: '20px', backgroundColor: '#f9f9f9' }}>
            <h3>{initialData ? `Editează Dispozitiv: ${initialData.name}` : 'Adaugă Dispozitiv Nou'}</h3>
            {error && <p style={{ color: 'red' }}>{error}</p>}

            {/* 1. INPUT: NAME */}
            <div style={{ marginBottom: '10px' }}>
                <label style={{ display: 'block' }}>Nume Dispozitiv:</label>
                <input
                    type="text"
                    name="name"
                    value={formData.name}
                    onChange={handleChange}
                    required
                />
            </div>

            {/* 2. INPUT: MAX CONSUMPTION */}
            <div style={{ marginBottom: '10px' }}>
                <label style={{ display: 'block' }}>Consum Maxim (W):</label>
                <input
                    type="number"
                    name="maxConsumption"
                    value={formData.maxConsumption}
                    onChange={handleChange}
                    required
                />
            </div>

            {/* 3. DROPDOWN: USER REFERENCE (ID-ul utilizatorului) */}
            <div style={{ marginBottom: '20px' }}>
                <label style={{ display: 'block' }}>Asignează Utilizator:</label>
                <select value={currentUserId} onChange={handleUserChange} required>
                    <option value="">-- Selectează un Utilizator --</option>
                    {users.map(user => (
                        // Opțiunile folosesc ID-ul (UUID) din lista de utilizatori
                        <option key={user.id} value={user.id}>
                            {user.username} (ID: {user.id})
                        </option>
                    ))}
                </select>
            </div>


            <button type="submit" style={{ padding: '8px 12px', marginRight: '10px' }}>
                {initialData ? 'Actualizează' : 'Creează'}
            </button>
            <button type="button" onClick={onCancel} style={{ marginLeft: '10px' }}>Anulează</button>
        </form>
    );
};



// Componenta principala DeviceList
const DeviceList = ({ users, refresh }) => {
    const [devices, setDevices] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [isFormVisible, setIsFormVisible] = useState(false);
    const [editingDevice, setEditingDevice] = useState(null);

    const fetchDevices = async () => {
        setLoading(true);
        try {
            const data = await getDevices();
            setDevices(data || []);
            setError(null);
        } catch (err) {
            setError("Eroare la încărcarea dispozitivelor.");
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchDevices();
    }, []);

    const handleDelete = async (id) => {
        if (window.confirm("Sunteți sigur că doriți să ștergeți acest dispozitiv?")) {
            try {
                await deleteDevice(id);
                fetchDevices();
            } catch (err) {
                setError("Ștergerea dispozitivului a eșuat.");
            }
        }
    };

    const handleFormSave = () => {
        setIsFormVisible(false);
        fetchDevices(); // Reincarca lista dupa adaugare/editare
    };


    if (loading) return <div>Se încarcă lista de dispozitive...</div>;
    if (error) return <div style={{ color: 'red', margin: '20px' }}>Eroare: {error}</div>;

    const getUserMap = (userList) => {
        return userList.reduce((acc, user) => {
            acc[user.id] = user.username;
            return acc;
        }, {});
    };
    const userMap = getUserMap(users);

    return (
        <div style={{ marginTop: '30px' }}>
            <h2>Dispozitive ({devices.length})</h2>
            <button onClick={() => {
                setIsFormVisible(true);
                setEditingDevice(null);
            }} style={{ marginBottom: '15px' }}>
                Adaugă Dispozitiv
            </button>

            {isFormVisible &&
                <DeviceForm
                    initialData={editingDevice}
                    users={users}
                    onSave={handleFormSave}
                    onCancel={() => setIsFormVisible(false)}
                />
            }

            <table border="1" style={{ width: '100%', borderCollapse: 'collapse' }}>
                <thead>
                <tr style={{ backgroundColor: '#f2f2f2' }}>
                    <th>ID</th>
                    <th>Nume</th>
                    <th>Consum Max (W)</th>
                    <th>Utilizator Asignat</th> {/* COLOANĂ NOUĂ */}
                    <th>Acțiuni</th>
                </tr>
                </thead>
                <tbody>
                {devices.map(device => (
                    <tr key={device.id}>
                        <td>{device.id}</td>
                        <td>{device.name}</td>
                        <td>{device.maxConsumption}</td>
                        <td>
                            {device.userId
                                ? (userMap[device.userId] || 'Nume Necunoscut')
                                : 'Neasignat'}
                        </td>
                        <td>
                            <button onClick={() => {
                                setEditingDevice(device);
                                setIsFormVisible(true);
                            }}>Editează
                            </button>
                            <button onClick={() => handleDelete(device.id)} style={{marginLeft: '10px'}}>Șterge</button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};

export default DeviceList;