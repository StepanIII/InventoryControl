function registrationHandleBtn() {
    if (getElement('password').value !== getElement('password_confirm').value) {
        getElement('error_desc').textContent = 'Пароли не совпадают'
    }

    let userRequest = {
        login: getElement('username').value,
        password: getElement('password').value,
        email: getElement('email').value
    }

    postData(USER_URL, userRequest).then(response => {
        console.log(response)
        if (response.state !== SUCCESS) {
            getElement('error_desc').textContent = response.description
        } else {
            window.location.replace(LOGIN_URL)
        }
    })
}