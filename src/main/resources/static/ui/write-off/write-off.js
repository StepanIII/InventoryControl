fillSubheaderSecond()

function handleAddWriteOffBtn() {
    window.location.replace(UI_WRITE_OFF_EDIT_URL)
}

getData(WRITE_OFF_URL).then(response => {
    console.log(response)
    let tBody = document.querySelector('#write_off_table tbody')
    response.writeOffs.forEach(writeOff => {
        let tr = createTr([writeOff.id, writeOff.createdTime, writeOff.warehouseName])
        tr.onclick = () => {
            localStorage.setItem('write_off_id', writeOff.id)
            window.location.replace(UI_WRITE_OFF_SHOW_URL)
        }
        tBody.appendChild(tr)
    })
})