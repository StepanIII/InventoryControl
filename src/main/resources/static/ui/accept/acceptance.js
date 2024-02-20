function handleAddAcceptanceBtn() {
    window.location.replace(UI_ACCEPT_EDIT_URL)
}

getData(ACCEPT_URL).then(response => {
    console.log(response)
    let tBody = document.querySelector('#acceptance_table tbody')
    response.acceptance.forEach(accept => {
        let tr = createTr([accept.id, accept.createdTime, accept.warehouseName, accept.benefactorFio])
        tr.onclick = () => {
            localStorage.setItem('accept_id', accept.id)
            window.location.replace(UI_ACCEPT_SHOW_URL)
        }
        tBody.appendChild(tr)
    })
})