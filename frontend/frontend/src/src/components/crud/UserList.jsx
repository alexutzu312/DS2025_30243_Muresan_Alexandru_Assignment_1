import React, { useState, useEffect } from 'react';
import { getUsers, deleteUser, createUser, updateUser } from '../../services/apiService';

// Funcție auxiliară pentru a gestiona schimbările simple ale input-urilor
const handleChange = (e, setter) => {
    const { name, value, type } = e.target;
    setter(prev => ({
        ...prev,
        [name]: type === 'number' ? parseInt(value) : value,
    }));
};

// Componenta interna: Formularul de Adaugare/Editare
const UserForm = ({ initialData, onSave, onCancel }) => {
    const [formData, setFormData] = useState(initialData || {
        username: '',
        password: '',
        role: initialData ? initialData.role : 'ROLE_CLIENT'
    });
    const availableRoles = ['ROLE_CLIENT', 'ROLE_ADMIN'];
    const [error, setError] = useState(null);

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError(null);

        // Câmpul password este obligatoriu la POST, opțional la PUT (dacă nu e schimbat)
        if (!initialData && !formData.password) {
            setError("Parola este obligatorie la crearea unui utilizator nou.");
            return;
        }

        try {
            // Corpul cererii (îndepărtăm câmpurile care nu sunt în DTO-ul de intrare, dacă e necesar)
            const payload = {
                username: formData.username,
                password: formData.password,
                role: formData.role,
                id: initialData ? initialData.id : undefined
            };

            if (initialData) {
                // PUT call (Editează)
                await updateUser(initialData.id, payload);
            } else {
                // POST call (Creează)
                await createUser(payload);
            }
            onSave();
        } catch (err) {
            setError(`Eroare la salvare: ${err.message}`);
        }
    };

    return (
        <form onSubmit={handleSubmit} style={{border: '1px solid #ccc', padding: '15px', marginBottom: '20px'}}>
            <h3>{initialData ? `Editează Utilizator: ${initialData.username}` : 'Adaugă Utilizator'}</h3>
            {error && <p style={{color: 'red'}}>{error}</p>}

            <div style={{marginBottom: '10px'}}>
                <label style={{display: 'block'}}>Username:</label>
                <input
                    type="text"
                    name="username"
                    value={formData.username}
                    onChange={(e) => handleChange(e, setFormData)}
                    required
                />
            </div>

            <div style={{marginBottom: '10px'}}>
                <label style={{display: 'block'}}>Password:</label>
                <input
                    type="password"
                    name="password"
                    value={formData.password}
                    onChange={(e) => handleChange(e, setFormData)}
                    required={!initialData}
                />
                {!initialData && <p style={{fontSize: '0.8em', color: '#666'}}>*Parola este obligatorie la creare.</p>}
            </div>

            <div style={{marginBottom: '10px'}}>
                <label style={{display: 'block'}}>Rol:</label>
                <select
                    name="role"
                    value={formData.role}
                    onChange={(e) => handleChange(e, setFormData)}
                    required
                >
                    {availableRoles.map(r => (
                        <option key={r} value={r}>{r}</option>
                    ))}
                </select>
            </div>


            <button type="submit">Salvează</button>
            <button type="button" onClick={onCancel} style={{marginLeft: '10px'}}>Anulează</button>
        </form>
    );
};

// Componenta principala UserList (Ramane neschimbata)
const UserList = ({users, refresh}) => {
    const [localUsers, setLocalUsers] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [isFormVisible, setIsFormVisible] = useState(false);
    const [editingUser, setEditingUser] = useState(null);

    const fetchUsers = async () => {
        setLoading(true);
        try {
            const data = await getUsers();
            setLocalUsers(data || []);
            setError(null);
        } catch (err) {
            setError("Eroare la încărcarea utilizatorilor. (Verificați Traefik/Auth/Consolă)");
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchUsers();
    }, []);

    const handleDelete = async (id) => {
        if (window.confirm("Sunteți sigur că doriți să ștergeți acest utilizator?")) {
            try {
                await deleteUser(id); // DELETE call
                fetchUsers(); // Reincarca lista
            } catch (err) {
                setError("Ștergerea utilizatorului a eșuat.");
            }
        }
    };

    if (loading) return <div>Se încarcă lista de utilizatori...</div>;
    if (error) return <div style={{ color: 'red', margin: '20px' }}>Eroare: {error}</div>;

    return (
        <div style={{marginTop: '30px'}}>
            <h2>Utilizatori ({localUsers.length})</h2>
            <button onClick={() => { setIsFormVisible(true); setEditingUser(null); }} style={{ marginBottom: '15px' }}>
                Adaugă Utilizator
            </button>

            {isFormVisible &&
                <UserForm
                    initialData={editingUser}
                    onSave={() => { setIsFormVisible(false); fetchUsers(); }}
                    onCancel={() => setIsFormVisible(false)}
                />
            }

            <table border="1" style={{ width: '100%', borderCollapse: 'collapse' }}>
                <thead>
                <tr style={{backgroundColor: '#f2f2f2'}}>
                    <th>ID</th>
                    <th>Username</th>
                    <th>Role</th>
                    <th>Acțiuni</th>
                </tr>
                </thead>
                <tbody>
                {localUsers.map(user => (
                    <tr key={user.id}>
                        <td>{user.id}</td>
                        <td>{user.username}</td>
                        <td>{user.role}</td>
                        <td>
                            <button onClick={() => { setEditingUser(user); setIsFormVisible(true); }}>Editează</button>
                            <button onClick={() => handleDelete(user.id)} style={{ marginLeft: '10px' }}>Șterge</button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};

export default UserList;