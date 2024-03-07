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
                let tr = createTr([resource.id, resource.name, resource.count, resource.fromRemainCount, resource.toRemainCount, resource.unit])
                tBody.appendChild(tr)
            })
        })

        showModal('show_modal')
    }
}