fillSubheaderSecond()

function handleAddAcceptanceBtn() {
    window.location.replace(UI_ACCEPT_EDIT_URL)
}

getData(ACCEPT_URL).then(response => {
    console.log(response)
    let tBody = document.querySelector('#acceptance_table tbody')
    response.acceptance.forEach(accept => {
        let tr = createTr([accept.id, accept.createdTime, accept.warehouseName, accept.benefactorFio])
        // tr.addEventListener('click', handleEditAcceptanceTr)
        tBody.appendChild(tr)
    })
})

function handleEditAcceptanceTr(e) {
    let selectedEditAcceptance = e.currentTarget
    let tdId = selectedEditAcceptance.childNodes[0]
    localStorage.setItem('accept_id', tdId.textContent)
    window.location.replace(UI_ACCEPT_EDIT_URL)
}