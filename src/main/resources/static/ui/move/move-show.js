let moveId = localStorage.getItem('move_id');
getData(MOVE_URL + "/" + moveId).then(response => {
    console.log(response)
    return response.move
}).then(move => {
    getElement('code').textContent = move.id
    getElement('time').textContent = move.createdTime
    getElement('from_warehouse').textContent = move.fromWarehouseName
    getElement('to_warehouse').textContent = move.toWarehouseName

    let tBody = document.querySelector('#resource_table tbody')

    move.resources.forEach(resource => {
        let tr = createTr([resource.id, resource.name, resource.count, resource.fromRemainCount, resource.toRemainCount, resource.unit])
        tBody.appendChild(tr)
    })
    localStorage.removeItem('move_id')
})
