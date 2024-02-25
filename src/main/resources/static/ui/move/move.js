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
        tr.onclick = () => {
            localStorage.setItem('move_id', move.id)
            window.location.replace(UI_MOVE_SHOW_URL)
        }
        tBody.appendChild(tr)
    })
})