getData(CLIENT_URL).then(response => {
    console.log(response)
    return response.clients
}).then(clients => {
    let tBody = document.querySelector('#client_table tbody')
    clients.forEach(client => {
        let delBtn = createDeleteClientSymbol(client.id)
        let middleName = ''
        if (!stringIsBlank(client.middleName)) {
            middleName = client.middleName
        }
        let tr = createTr([client.id, client.lastName, client.firstName, middleName, client.phone, client.type, delBtn])
        trHandler(tr, client.id)
        tBody.appendChild(tr)
    })
})

getData(CLIENT_URL + "/types").then(response => {
    console.log(response)
    return response.clientTypes
}).then(types => {
    let resourceTypeSelected = getElement('client_type')
    types.forEach(type => {
        let option = createElement('option')
        option.textContent = type
        resourceTypeSelected.appendChild(option)
    })
})

function handleAddClientBtn() {
    getElement('editModalLabel').textContent = 'Добавить клиента'
    getElement("client_id").value = ''
    getElement("client_last_name_input").value = ''
    getElement("client_fist_name_input").value = ''
    getElement("client_middle_name_input").value = ''
    getElement("client_phone_input").value = ''
    getElement("client_type").value = ''

    showModal('edit_modal')
}

function handleSaveClientBtn() {
    let clientId = getElement("client_id").value
    let request = {
        lastName: getElement('client_last_name_input').value,
        firstName: getElement('client_fist_name_input').value,
        middleName: getElement('client_middle_name_input').value,
        phone: getElement('client_phone_input').value,
        type: getElement('client_type').value
    }

    if (stringIsBlank(clientId)) {
        postData(CLIENT_URL, request).then(response => {
            console.log(response)
            if (response.status !== SUCCESS) {
                getElement('error_modal_desc').textContent = response.description
            } else {
                window.location.reload()
            }
        })
    } else {
        putData(CLIENT_URL + "/" + clientId, request).then(response => {
            console.log(response)
            if (response.status !== SUCCESS) {
                getElement('error_modal_desc').textContent = response.description
            } else {
                window.location.reload()
            }
        })
    }
}

function trHandler(tr, clientId) {
    let tds = tr.childNodes
    for (let i = 0; i < tds.length - 1; i++) {
        tds[i].onclick = () => {
            getData(CLIENT_URL + "/" + clientId).then(response => {
                console.log(response)
                return response.client
            }).then(client => {
                getElement("client_id").value = client.id
                getElement("client_last_name_input").value = client.lastName
                getElement("client_fist_name_input").value = client.firstName
                getElement("client_middle_name_input").value = client.middleName
                getElement("client_phone_input").value = client.phone
                getElement("client_type").value = client.type

                getElement('editModalLabel').textContent = 'Изменить клиента'
                getElement('edit_modal_code_row').hidden = false

                showModal('edit_modal')
            })
        }
    }
    mouseOnTrHandler(tr)
}

function createDeleteClientSymbol(clientId) {
    let deleteSymbol = document.createElement('span')
    deleteSymbol.textContent = '✖'
    deleteSymbol.style.fontSize = '10px'
    deleteSymbol.style.cursor = 'pointer'
    deleteSymbol.title = 'Удалить'
    deleteSymbol.onclick = () => {
        getElement('error_del_desc').textContent = ''
        getData(CLIENT_URL + "/" + clientId).then(response => {
            console.log(response)
            return response.client
        }).then(client => {
            getElement("del_client_id").value = client.id
            getElement("del_client_last_name_input").value = client.lastName
            getElement("del_client_fist_name_input").value = client.firstName
            getElement("del_client_middle_name_input").value = client.middleName
            getElement("del_client_phone_input").value = client.phone
            getElement("del_client_type").value = client.type

            showModal('delete_modal')
        })
    }
    return deleteSymbol
}

function handleDeleteClientBtn() {
    let clientId = getElement("del_client_id").value
    deleteData(CLIENT_URL + "/" + clientId).then(response => {
        console.log(response)
        if (response.status !== SUCCESS) {
            getElement('error_del_desc').textContent = response.description
        } else {
            window.location.reload()
        }
    })
}