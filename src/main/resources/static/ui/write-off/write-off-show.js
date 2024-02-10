let writeOffId = localStorage.getItem('write_off_id');
getData(WRITE_OFF_URL + "/" + writeOffId).then(response => {
    console.log(response)

    let writeOff = response.writeOff

    getElement('code').textContent = writeOff.id
    getElement('created_time').textContent = writeOff.createdTime
    getElement('warehouse_name').textContent = writeOff.warehouseName
    getElement('description').textContent = writeOff.description

    let tBody = document.querySelector('#resource_table tbody')

    writeOff.resources.forEach(resource => {
        let tr = createTr([resource.id, resource.name, resource.count])
        tBody.appendChild(tr)
    })
    localStorage.removeItem('issue_id')
})
