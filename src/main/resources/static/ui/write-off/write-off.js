function handleAddWriteOffBtn() {
    window.location.replace(UI_WRITE_OFF_EDIT_URL)
}

getData(WRITE_OFF_URL).then(response => {
    console.log(response)
    let tBody = document.querySelector('#write_off_table tbody')
    response.writeOffs.forEach(writeOff => {
        let tr = createTr([writeOff.id, writeOff.createdTime, writeOff.warehouseName])
        trHandler(tr, writeOff.id)
        mouseOnTrHandler(tr)
        tBody.appendChild(tr)
    })
})

function trHandler(tr, writeOffId) {
    tr.onclick = () => {
        getData(WRITE_OFF_URL + "/" + writeOffId).then(response => {
            console.log(response)
            return response.writeOff
        }).then(writeOff => {
            getElement('show_number_write_off').value = writeOff.id
            getElement('show_time_write_off').value = writeOff.createdTime
            getElement('show_warehouse_write_off').value = writeOff.warehouseName
            getElement('show_description_write_off').value = writeOff.description

            let tBody = document.querySelector('#write_off_resource_table tbody')
            clearTBody(tBody)
            writeOff.resources.forEach(resource => {
                let size = ''
                if (!stringIsBlank(resource.size)) {
                    size = resource.size
                }
                let tr = createTr([resource.id, resource.name, size, resource.count, resource.unit])
                tBody.appendChild(tr)
            })
        })

        showModal('show_modal')
    }
}