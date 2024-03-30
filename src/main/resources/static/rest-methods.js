const SUCCESS = 'SUCCESS'

// Определяем функцию которая принимает в качестве параметров url и данные которые необходимо обработать:
const postData = async (url = '', data = {}) => {
    // Формируем запрос
    const response = await fetch(url, {
        // Метод, если не указывать, будет использоваться GET
        method: 'POST',
        // Заголовок запроса
        headers: {
            'Content-Type': 'application/json'
        },
        // Данные
        body: JSON.stringify(data)
    });
    return response.json();
}

const putData = async (url = '', data = {}) => {
    const response = await fetch(url, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    });
    return response.json();
}

const getData = async (url = '') => {
    const response = await fetch(url, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        },
    });
    return response.json();
}

const deleteData = async (url = '') => {
    const response = await fetch(url, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        },
    });
    return response.json();
}

const logout = async () => {
    // Формируем запрос
    const response = await fetch(CURRENT_PROTOCOL_HOST_PORT + '/logout', {
        // Метод, если не указывать, будет использоваться GET
        method: 'POST',
        // Заголовок запроса
        headers: {
            'Content-Type': 'application/json'
        },
    });
    return response.json();
}