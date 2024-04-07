function handleAddMoveBtn() {
    window.location.replace(UI_MOVE_EDIT_URL)
}

getData(MOVE_URL).then(response => {
    console.log(response)
    return response.moves
}).then(moves => {
    let tBody = document.querySelector('#move_table tbody')
    moves.forEach(move => {
        let tr = createTr([move.id, move.createdTime, move.fromWarehouseName, move.toWarehouseName])
        trHandler(tr, move.id)
        mouseOnTrHandler(tr)
        tBody.appendChild(tr)
    })
})

function trHandler(tr, moveId) {
    tr.onclick = () => {
        getData(MOVE_URL + "/" + moveId).then(response => {
            console.log(response)
            return response.move
        }).then(move => {
            getElement('show_number_move').value = move.id
            getElement('show_time_move').value = move.createdTime
            getElement('show_warehouse_from').value = move.fromWarehouseName
            getElement('show_warehouse_to').value = move.toWarehouseName

            let tBody = document.querySelector('#move_resource_table tbody')
            clearTBody(tBody)
            move.resources.forEach(resource => {
                let size = ''
                if (!stringIsBlank(resource.size)) {
                    size = resource.size
                }
                let tr = createTr([resource.id, resource.name, size, resource.count, resource.fromRemainCount, resource.toRemainCount, resource.unit])
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
        getData(MOVE_URL).then(response => {
            console.log(response)
            return response.moves
        }).then(moves => {
            let tBody = document.querySelector('#move_table tbody')
            clearTBody(tBody)
            moves
                .filter(move => (new Date(move.createdTime).getTime() > fromDate) && (new Date(move.createdTime).getTime() < toDate))
                .forEach(move => {
                    let tr = createTr([move.id, move.createdTime, move.fromWarehouseName, move.toWarehouseName])
                    trHandler(tr, move.id)
                    mouseOnTrHandler(tr)
                    tBody.appendChild(tr)
                })
        })
    }
}

function handleResetFilter() {
    getElement("from_date").value = ''
    getElement("to_date").value = ''

    getData(MOVE_URL).then(response => {
        console.log(response)
        return response.moves
    }).then(moves => {
        let tBody = document.querySelector('#move_table tbody')
        moves.forEach(move => {
            let tr = createTr([move.id, move.createdTime, move.fromWarehouseName, move.toWarehouseName])
            trHandler(tr, move.id)
            mouseOnTrHandler(tr)
            tBody.appendChild(tr)
        })
    })
}