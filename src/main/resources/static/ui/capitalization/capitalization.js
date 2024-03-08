function handleAddCapitalizationBtn() {
    window.location.replace(UI_CAPITALIZATION_EDIT_URL)
}

getData(CAPITALIZATION_URL).then(response => {
    console.log(response)
    let tBody = document.querySelector('#capitalization_table tbody')
    response.capitalization.forEach(capitalization => {
        let tr = createTr([capitalization.id, capitalization.createdTime, capitalization.warehouseName])
        trHandler(tr, capitalization.id)
        mouseOnTrHandler(tr)
        tBody.appendChild(tr)
    })
})

function trHandler(tr, capitalizationId) {
    tr.onclick = () => {
        getData(CAPITALIZATION_URL + "/" + capitalizationId).then(response => {
            console.log(response)
            return response.capitalization
        }).then(capitalization => {
            getElement('show_number_capital').value = capitalization.id
            getElement('show_time_capital').value = capitalization.createdTime
            getElement('show_warehouse_capital').value = capitalization.warehouseName
            getElement('show_description_capital').value = capitalization.description

            let tBody = document.querySelector('#capital_resource_table tbody')
            clearTBody(tBody)
            capitalization.resources.forEach(resource => {
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