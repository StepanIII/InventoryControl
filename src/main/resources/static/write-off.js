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

function handleFilter() {
    let fromDateString = getElement("from_date").value
    let toDateString = getElement("to_date").value

    let fromDate = new Date(fromDateString).getTime()
    let toDate = new Date(toDateString).getTime()

    if (stringIsBlank(fromDateString) || stringIsBlank(toDateString)) {
        getElement('error_desc').textContent = 'Укажите даты для фильтрации данных.'
        showModal('error_modal')
    } else if (fromDate > toDate) {
        getElement('error_desc').textContent = 'Дата начала фильтрации больше даты конца фильтрации.'
        showModal('error_modal')
    } else {
        getData(WRITE_OFF_URL).then(response => {
            console.log(response)
            let tBody = document.querySelector('#write_off_table tbody')
            clearTBody(tBody)
            response.writeOffs
                .filter(writeOff => (new Date(writeOff.createdTime).getTime() > fromDate) && (new Date(writeOff.createdTime).getTime() < toDate))
                .forEach(writeOff => {
                    let tr = createTr([writeOff.id, writeOff.createdTime, writeOff.warehouseName])
                    trHandler(tr, writeOff.id)
                    mouseOnTrHandler(tr)
                    tBody.appendChild(tr)
                })
        })
    }
}

function handleResetFilter() {
    getElement("from_date").value = ''
    getElement("to_date").value = ''

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
}