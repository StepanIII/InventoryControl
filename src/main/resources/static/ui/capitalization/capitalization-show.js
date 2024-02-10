let capitalizationId = localStorage.getItem('capitalization_id');
getData(CAPITALIZATION_URL + "/" + capitalizationId).then(response => {
    console.log(response)

    let capitalization = response.capitalization

    getElement('code').textContent = capitalization.id
    getElement('created_time').textContent = capitalization.createdTime
    getElement('warehouse_name').textContent = capitalization.warehouseName
    getElement('description').textContent = capitalization.description

    let tBody = document.querySelector('#resource_table tbody')

    capitalization.resources.forEach(resource => {
        let tr = createTr([resource.id, resource.name, resource.count])
        tBody.appendChild(tr)
    })
    localStorage.removeItem('capitalization_id')
})
