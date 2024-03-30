const currentLogin = localStorage.getItem('current_login')
let withLogout = false

getData(USER_URL).then(response => {
    console.log(response)
    return response.users
}).then(users => {
    let tBody = document.querySelector('#user_table tbody')
    users.forEach(user => {
        let delBtn = createDeleteUserSymbol(user.id)
        let middleName = ''
        if (!stringIsBlank(user.middleName)) {
            middleName = user.middleName
        }
        let tr = createTr([user.id, user.login, '******', user.lastName, user.firstName, middleName, user.email, delBtn])
        trHandler(tr, user.id)
        tBody.appendChild(tr)
    })
})

getData(USER_URL + "/roles").then(response => {
    console.log(response)
    return response.roles
}).then(roles => {
    let userRoleSelectTable = document.querySelector('#user_role_table tbody')
    roles.forEach(role => {
        let checkBox = createCheckBox()
        let tr = createTr([role, checkBox])
        userRoleSelectTable.appendChild(tr)
    })
})

function handleAddUserBtn() {
    getElement('edit_modal_code_row').hidden = true
    getElement('editModalLabel').textContent = 'Добавить пользователя'
    getElement("user_id").value = ''
    getElement("user_login_input").value = ''
    getElement("user_password_input").value = ''
    getElement("user_last_name_input").value = ''
    getElement("user_first_name_input").value = ''
    getElement("user_middle_name_input").value = ''
    getElement("user_email_input").value = ''
    clearEditRole()
    showEditModal()
}

function handleSaveUserBtn() {
    let userId = getElement("user_id").value
    let roles = []
    document.querySelector('#user_role_table tbody')
        .childNodes.forEach(tr => {
            if (tr.childNodes[1].firstChild.checked) {
                roles.push(tr.childNodes[0].textContent)
            }
    })

    let request = {
        login: getElement('user_login_input').value,
        password: getElement('user_password_input').value,
        lastName: getElement('user_last_name_input').value,
        firstName: getElement('user_first_name_input').value,
        middleName: getElement('user_middle_name_input').value,
        email: getElement('user_email_input').value,
        roles: roles
    }

    if (stringIsBlank(userId)) {
        postData(USER_URL + '/add', request).then(response => {
            console.log(response)
            if (response.status !== SUCCESS) {
                getElement('error_modal_desc').textContent = response.description
            } else {
                window.location.reload()
            }
        })
    } else {
        putData(USER_URL + "/" + userId, request).then(response => {
            console.log(response)
            if (response.status !== SUCCESS) {
                getElement('error_modal_desc').textContent = response.description
            } else {
                if (withLogout) {
                    console.log('LOGOUT')
                    getElement('logout_btn').click()
                } else {
                    window.location.reload()
                }
            }
        })
    }
}

function handleConfirmUserBtn() {
    showEditModal()

    // let userId = getElement("user_id").value
    // let roles = []
    // document.querySelector('#user_role_table tbody')
    //     .childNodes.forEach(tr => {
    //     if (tr.childNodes[1].firstChild.checked) {
    //         roles.push(tr.childNodes[0].textContent)
    //     }
    // })
    //
    // let request = {
    //     login: getElement('user_login_input').value,
    //     password: getElement('user_password_input').value,
    //     lastName: getElement('user_last_name_input').value,
    //     firstName: getElement('user_first_name_input').value,
    //     middleName: getElement('user_middle_name_input').value,
    //     email: getElement('user_email_input').value,
    //     roles: roles
    // }
    //
    // putData(USER_URL + "/" + userId, request).then(response => {
    //     console.log(response)
    //     if (response.status !== SUCCESS) {
    //         getElement('error_modal_desc').textContent = response.description
    //     } else {
    //         logout()
    //     }
    // })
}

function trHandler(tr, userId) {
    let tds = tr.childNodes
    for (let i = 0; i < tds.length - 1; i++) {
        tds[i].onclick = () => {
            getData(USER_URL + "/" + userId).then(response => {
                console.log(response)
                return response.user
            }).then(user => {
                getElement("user_id").value = user.id
                getElement("user_login_input").value = user.login
                getElement("user_password_input").value = '******'
                getElement("user_last_name_input").value = user.lastName
                getElement("user_first_name_input").value = user.firstName
                getElement("user_middle_name_input").value = user.middleName
                getElement("user_email_input").value = user.email

                clearEditRole()

                let userRoleSelectTable = document.querySelector('#user_role_table tbody')
                userRoleSelectTable.childNodes.forEach(tr => {
                    let role = tr.childNodes[0].textContent
                    let checkBox = tr.childNodes[1].firstChild

                    user.roles.forEach(roleExpected => {
                        if (role === roleExpected) {
                            checkBox.checked = true
                        }
                    })
                })

                getElement('editModalLabel').textContent = 'Изменить пользователя'
                getElement('edit_modal_code_row').hidden = false

                if (currentLogin === getElement('user_login_input').value) {
                    getElement('confirm_desc').textContent = 'Вы изменяете свои учетные данные. После подтверждения изменений вам нужно будет занаво пройти аутентификацию.'
                    showModal('confirm_modal')
                    withLogout = true
                } else {
                    withLogout = false
                    showEditModal()
                }
            })
        }
    }
    mouseOnTrHandler(tr)
}

function clearEditRole() {
    let userRoleSelectTable = document.querySelector('#user_role_table tbody')
    userRoleSelectTable.childNodes.forEach(tr => {
        tr.childNodes[1].firstChild.checked = false
    })
}

function showEditModal() {
    showModal('edit_modal')
}

function createDeleteUserSymbol(userId) {
    let deleteSymbol = document.createElement('span')
    deleteSymbol.textContent = '✖'
    deleteSymbol.style.fontSize = '10px'
    deleteSymbol.style.cursor = 'pointer'
    deleteSymbol.title = 'Удалить'
    deleteSymbol.onclick = () => {
        getElement('error_del_desc').textContent = ''
        getData(USER_URL + "/" + userId).then(response => {
            console.log(response)
            return response.user
        }).then(user => {
            getElement("del_user_id").value = user.id
            getElement("del_user_login_input").value = user.login
            getElement("del_user_password_input").value = '******'
            getElement("del_user_last_name_input").value = user.lastName
            getElement("del_user_first_name_input").value = user.firstName
            getElement("del_user_middle_name_input").value = user.middleName
            getElement("del_user_email_input").value = user.email

            let userRoleSelectTable = document.querySelector('#del_user_role_table tbody')
            clearTBody(userRoleSelectTable)

            user.roles.forEach(role => {
                let tr = createTr([role])
                userRoleSelectTable.appendChild(tr)
            })

            showModal('delete_modal')
        })
    }
    return deleteSymbol
}
