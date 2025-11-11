// Aici vom folosi noile rute: /users, /devices și /devices/user/{userId}

const DEVICE_API_BASE_URL = process.env.REACT_APP_DEVICE_API_GATEWAY_URL || 'http://localhost:8082';
const USER_API_BASE_URL = process.env.REACT_APP_USER_API_GATEWAY_URL || 'http://localhost:8081';
const AUTH_API_BASE_URL = process.env.REACT_APP_AUTH_API_GATEWAY_URL || 'http://localhost:8083';

const getToken = () => localStorage.getItem('jwtToken');

const fetchApi = async (API_BASE_URL, endpoint, method = 'GET', body = null) => {
    const token = getToken();
    const headers = {
        'Content-Type': 'application/json',
    };

    if (token) {
        headers['Authorization'] = `Bearer ${token}`;
    }

    const config = {
        method,
        headers,
        body: body ? JSON.stringify(body) : undefined,
    };

    try {
        const response = await fetch(`${API_BASE_URL}${endpoint}`, config);

        if (response.status === 401 || response.status === 403) {
            // Lăsăm componenta să gestioneze 401/403 de la Traefik
            throw new Error(response.status === 403 ? 'Acces Interzis (403)' : 'Autentificare eșuată/Expirată (401)');
        }

        if (!response.ok) {
            const errorData = await response.json().catch(() => ({}));
            throw new Error(errorData.message || `API Error: ${response.status}`);
        }

        // Gestiunea răspunsurilor fără conținut
        if (response.status === 204 || response.headers.get('Content-Length') === '0') {
            return null;
        }

        return await response.json();
    } catch (error) {
        throw error;
    }
};

// --- CRUD USERS (Rute: /api/users) ---
export const getUsers = () => fetchApi(USER_API_BASE_URL, '/users');
export const createUser = (userData) => fetchApi(AUTH_API_BASE_URL, '/auth/register', 'POST', userData);
export const updateUser = (id, userData) => fetchApi(USER_API_BASE_URL, `/users/update/${id}`, 'PATCH', userData);
export const deleteUser = (id) => fetchApi(USER_API_BASE_URL, `/users/${id}`, 'DELETE');

// --- CRUD DEVICES (Rute: /api/devices) ---
export const getDevices = () => fetchApi(DEVICE_API_BASE_URL, '/devices');
export const createDevice = (deviceData) => fetchApi(DEVICE_API_BASE_URL, '/devices', 'POST', deviceData);
export const updateDevice = (id, deviceData) => fetchApi(DEVICE_API_BASE_URL, `/devices/update/${id}`, 'PATCH', deviceData);
export const deleteDevice = (id) => fetchApi(DEVICE_API_BASE_URL, `/devices/${id}`, 'DELETE');

// --- ASOCIERE & CLIENT VIEW ---
export const assignDevice = (deviceId, userId) =>
    fetchApi(DEVICE_API_BASE_URL, `/devices/${deviceId}/assign/${userId}`, 'PATCH'); // PATCH call to the new endpoint

export const getDevicesByUserId = (userId) =>
    fetchApi(DEVICE_API_BASE_URL, `/devices/user/${userId}`); // GET call for client's devices

// --- AUTH ---
export const loginUser = (credentials) => fetchApi(AUTH_API_BASE_URL, '/auth/login', 'POST', credentials);