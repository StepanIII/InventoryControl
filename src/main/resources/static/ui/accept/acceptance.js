function handleAddAcceptanceBtn() {
    window.location.replace(UI_ACCEPT_EDIT_URL)
}

getData(ACCEPT_URL).then(response => {
    console.log(response)
    let tBody = document.querySelector('#acceptance_table tbody')
    response.acceptance.forEach(accept => {
        let tr = createTr([accept.id, accept.createdTime, accept.warehouseName, accept.benefactorFio])
        trHandler(tr, accept.id)
        mouseOnTrHandler(tr)
        tBody.appendChild(tr)
    })
})

function trHandler(tr, acceptId) {
    tr.onclick = () => {
        getData(ACCEPT_URL + "/" + acceptId).then(response => {
            console.log(response)
            return response.accept
        }).then(accept => {
            getElement('show_number_accept').value = accept.id
            getElement('show_time_accept').value = accept.createdTime
            getElement('show_warehouse_accept').value = accept.warehouseName
            getElement('show_benefactor_accept').value = accept.benefactorFio

            let tBody = document.querySelector('#accept_resource_table tbody')
            accept.resources.forEach(resource => {
                let tr = createTr([resource.id, resource.name, resource.count, 'Единица измерения'])
                tBody.appendChild(tr)
            })
        })

        showModal('show_modal')
    }
}

