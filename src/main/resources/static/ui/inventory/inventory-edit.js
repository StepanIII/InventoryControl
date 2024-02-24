let inventoryEditId = localStorage.getItem('inventory_id')
if (inventoryEditId !== null) {
    getData(INVENTORY_URL + "/" + inventoryEditId).then(response => {
        console.log(response)
        return response.inventory
    }).then(inventory => {
        getElement('created_time').textContent = inventory.createdTime
        getElement('created_time_block').hidden = false

        getElement('selected_warehouse').textContent = inventory.warehouseName
        getElement('selected_warehouse_id').textContent = inventory.warehouseId
        inventory.resources.forEach(resource => {
            fillSelectedResourceTable(resource.id, resource.name, resource.actualCount, resource.estimatedCount, resource.difference, resource.unit, true)
        })
    })
    localStorage.removeItem('inventory_id')
}


function handleWarehouseSelect() {
    getData(WAREHOUSES_URL).then(response => {
        console.log(response)

        let openListWarehouseBtn = getElement('open_list_warehouse_btn')
        let table = getElement('warehouse_table');
        let tBody = document.querySelector('#warehouse_table tbody')

        response.warehouses.forEach(warehouse => {
            let tr = createTr([warehouse.id, warehouse.name])
            tr.onclick = () => {
                getElement('selected_warehouse').textContent = warehouse.name
                getElement('selected_warehouse_id').textContent = warehouse.id
                clearTBody(tBody)
                let selectedResourcesTBody = document.querySelector('#selected_resource_table tbody')
                clearTBody(selectedResourcesTBody)
                table.hidden = true
                openListWarehouseBtn.disabled = false
                getElement('open_list_resources_btn').disabled = false
            }
            tBody.appendChild(tr)
        })

        table.hidden = false
        openListWarehouseBtn.disabled = true
        getElement('select_resources_block').hidden = true
        getElement('open_list_resources_btn').disabled = false
    })
}

function handleResourceSelect() {
    let warehouseId = getElement('selected_warehouse_id').textContent
    fillResourceTable(warehouseId)
    getElement('select_resources_block').hidden = false
    getElement('open_list_resources_btn').disabled = true
}

function fillResourceTable(warehouseId) {
    getData(REMAINING_URL + "/" + warehouseId).then(response => {
        console.log(response)

        let tBody = document.querySelector('#resources_table tbody')
        clearTBody(tBody)
        response.remains.forEach(remain => {
            let tr = createTr([remain.resourceId, remain.name, remain.count, remain.unit, createCheckBox()])
            tBody.appendChild(tr)
        })
    })
}

function handleSelectResourceBtn() {
    let resourcesTBody = document.querySelector('#resources_table tbody')
    let selectedResourcesTBody = document.querySelector('#selected_resource_table tbody')
    clearTBody(selectedResourcesTBody)

    let trs = resourcesTBody.childNodes
    trs.forEach(tr => {
        let tds = tr.childNodes

        let code = tds[0].textContent
        let name = tds[1].textContent
        let remainCount = tds[2].textContent
        let unit = tds[3].textContent
        let checked = tds[4].firstChild.checked

        let actualCount = createInput('number', 0, null)
        actualCount.value = '0'

        if (checked) {
            fillSelectedResourceTable(code, name, actualCount, remainCount, 0, unit, false)
        }
    })

    clearTBody(resourcesTBody)
    getElement('select_resources_block').hidden = true
    getElement('open_list_resources_btn').disabled = false
}

function fillSelectedResourceTable(code, name, actualCount, remainCount, difference, unit, changed) {
    let deleteBtn = createBtn('Удалить', null)
    deleteBtn.onclick = (e) => {
        let tr = e.currentTarget.parentNode.parentNode
        tr.remove()
    }

    let okBtn = createBtn('ОК', null)
    okBtn.onclick = (e) => {
        let tr = e.currentTarget.parentNode.parentNode
        let tds = tr.childNodes
        let actualCount = tds[2].firstChild.value
        let remainCount = tds[3].textContent

        console.log(tds[2])

        let difference = Number(actualCount) - Number(remainCount)
        let differenceTd = tds[4]
        differenceTd.textContent = difference

        tds[2].firstChild.remove()
        tds[2].textContent = actualCount
        okBtn.hidden = true
        editBtn.hidden = false
    }

    let editBtn = createBtn('Изменить', null)
    editBtn.onclick = (e) => {
        okBtn.hidden = false
        editBtn.hidden = true

        let tr = e.currentTarget.parentNode.parentNode
        let tds = tr.childNodes
        let actualCount = createInput('number', 0, null)
        actualCount.value = tds[2].textContent
        tds[2].firstChild.remove()
        tds[2].appendChild(actualCount)
    }

    if (changed) {
        okBtn.hidden = true
        editBtn.hidden = false
    } else {
        okBtn.hidden = false
        editBtn.hidden = true
    }

    let tr = createTr([code, name, actualCount, remainCount, difference, unit, okBtn, editBtn, deleteBtn])
    document.querySelector('#selected_resource_table tbody').appendChild(tr)
}

function handleSaveAcceptanceBtn() {
    let warehouseId = getElement('selected_warehouse_id').textContent

    let selectedResourceTbody = document.querySelector('#selected_resource_table tbody')
    let resourcesRequest = []

    let trs = selectedResourceTbody.childNodes
    trs.forEach(tr => {
        let resourceId = tr.childNodes[0].textContent
        let actualCount = tr.childNodes[2].textContent
        let settlementCount = tr.childNodes[3].textContent
        let resourceReq = {
            resourceId: resourceId,
            actualCount: actualCount,
            settlementCount: settlementCount
        }
        resourcesRequest.push(resourceReq)
    })

    let request = {
        warehouseId: warehouseId,
        resources: resourcesRequest
    }

    if (inventoryEditId !== null) {
        putData(INVENTORY_URL + '/' + inventoryEditId, request).then(response => {
            console.log(response)

            if (response.status !== SUCCESS) {
                getElement('error_desc').textContent = response.description
            } else {
                window.location.replace(UI_INVENTORY_ALL_URL)
            }
        })
    } else {
        postData(INVENTORY_URL, request).then(response => {
            console.log(response)

            if (response.status !== SUCCESS) {
                getElement('error_desc').textContent = response.description
            } else {
                window.location.replace(UI_INVENTORY_ALL_URL)
            }
        })
    }

}
