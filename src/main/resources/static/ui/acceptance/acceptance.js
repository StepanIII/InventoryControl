fillSubheaderSecond()

function handleAddAcceptanceBtn() {
    window.location.replace(UI_ACCEPTANCE_EDIT_URL)
}

getData(ACCEPTANCE_URL).then((response) => {
    let tBody = document.querySelector('#acceptance_table tbody')
    let acceptance = response.acceptance
    for (let i = 0; i < acceptance.length; i++) {
        let accept = acceptance[i]
        let tr = createTr([accept.id, accept.createdTime, accept.warehouse, accept.benefactor])
        tr.addEventListener('click', handleEditAcceptanceTr)
        tBody.appendChild(tr)
    }
})

function handleEditAcceptanceTr(e) {
    let selectedEditAcceptance = e.currentTarget
    let tdId = selectedEditAcceptance.childNodes[0]
    localStorage.setItem('accept_id', tdId.textContent)
    window.location.replace(UI_ACCEPTANCE_EDIT_URL)
}