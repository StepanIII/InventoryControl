fillSubheaderSecond()

function handleAddWriteOffBtn() {
    window.location.replace(UI_WRITE_OFF_EDIT_URL)
}

getData(WRITE_OFF_URL).then((response) => {
    let tBody = document.querySelector('#write_off_table tbody')
    let writeOffs = response.writeOffs
    for (let i = 0; i < writeOffs.length; i++) {
        let writeOff = writeOffs[i]
        let tr = createTr([writeOff.id, writeOff.createdTime, writeOff.warehouseName])
        tr.addEventListener('click', handleEditWriteOffTr)
        tBody.appendChild(tr)
    }
})

function handleEditWriteOffTr(e) {
    let selectedEditAcceptance = e.currentTarget
    let tdId = selectedEditAcceptance.childNodes[0]
    localStorage.setItem('write_off_id', tdId.textContent)
    window.location.replace(UI_WRITE_OFF_EDIT_URL)
}