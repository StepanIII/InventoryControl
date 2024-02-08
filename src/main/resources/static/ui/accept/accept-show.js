let acceptId = localStorage.getItem('accept_id');
getData(ACCEPT_URL + "/" + acceptId).then(response => {
    console.log(response)

    let accept = response.accept

    getElement('code').textContent = accept.id
    getElement('created_time').textContent = accept.createdTime
    getElement('benefactor_fio').textContent = accept.benefactorFio
    getElement('warehouse_name').textContent = accept.warehouseName

    let tBody = document.querySelector('#resource_table tbody')

    accept.resources.forEach(resource => {
        let tr = createTr([resource.id, resource.name, resource.count])
        tBody.appendChild(tr)
    })
    localStorage.removeItem('accept_id')
})
