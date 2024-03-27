getData(USER_URL).then(response => {
    console.log(response.status)
    return response.user
}).then(user => {
    localStorage.setItem('last_first_name', user.lastFirstName)
    getElement('last_first_name').textContent = user.lastFirstName
})
function handleResourcesBtn() {
    window.location.replace(UI_RESOURCES_ALL_URL)
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