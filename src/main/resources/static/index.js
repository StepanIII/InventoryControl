getData(USER_URL + '/current').then(response => {
    console.log(response.status)
    return response.user
}).then(user => {
    // localStorage.setItem('last_first_name', user.lastFirstName)
    localStorage.setItem('current_login', user.login)
    getElement('last_first_name').textContent = user.lastName + ' ' + user.firstName
    return user.roles
}).then(roles => {
    roles.forEach(role => {
        if (role !== 'ROLE_ADMIN') {
            getElement('menu_user').hidden = true
        }
        if (role !== 'ROLE_ADMIN' && role !== 'ROLE_MANAGER') {
            getElement('menu_warehouse').hidden = true
        }
    })
})

function handleUserBtn() {
    window.location.replace(UI_USER_ALL_URL)
}

function handleResourcesBtn() {
    window.location.replace(UI_RESOURCES_ALL_URL)
}

function handleWarehouseBtn() {
    window.location.replace(UI_WAREHOUSE_ALL_URL)
}

function handleClientBtn() {
    window.location.replace(UI_CLIENT_ALL_URL)
}

function handleAcceptanceBtn() {
    window.location.replace(UI_ACCEPT_ALL_URL)
}

function handleIssueBtn() {
    window.location.replace(UI_ISSUE_ALL_URL)
}

function handleCapitalizationBtn() {
    window.location.replace(UI_CAPITALIZATION_ALL_URL)
}

function handleWriteOffBtn() {
    window.location.replace(UI_WRITE_OFF_ALL_URL)
}

function handleMoveBtn() {
    window.location.replace(UI_MOVE_ALL_URL)
}

function handleRemainingBtn() {
    window.location.replace(UI_REMAINING_ALL_URL)
}

function handleInventoryBtn() {
    window.location.replace(UI_INVENTORY_ALL_URL)
}

function handleEditCurrentUser() {
    getData(USER_URL + '/current').then(response => {
        console.log(response.status)
        return response.user
    }).then(user => {
        getElement("current_user_id").value = user.id
        getElement("current_user_login_input").value = user.login
        getElement("current_user_password_input").value = '******'
        getElement("current_user_last_name_input").value = user.lastName
        getElement("current_user_first_name_input").value = user.firstName
        getElement("current_user_middle_name_input").value = user.middleName
        getElement("current_user_email_input").value = user.email

        getElement('currentEditModalLabel').textContent = user.lastName + " " + user.firstName
    })

    showModal('current_user_edit_modal')
}

function handleSaveCurrentUserBtn() {
    let userId = getElement("current_user_id").value

    let enterPassword = getElement('current_user_password_input').value
    let password = null;
    if (enterPassword !== '******') {
        password = enterPassword
    }

    let request = {
        login: getElement('current_user_login_input').value,
        password: password,
        lastName: getElement('current_user_last_name_input').value,
        firstName: getElement('current_user_first_name_input').value,
        middleName: getElement('current_user_middle_name_input').value,
        email: getElement('current_user_email_input').value
    }

    putData(USER_URL + "/" + userId, request).then(response => {
        console.log(response)
        if (response.status !== SUCCESS) {
            getElement('error_modal_desc').textContent = response.description
        } else {
            window.location.reload()
        }
    })
}