let inventoryEditId = localStorage.getItem('inventory_id')
if (inventoryEditId !== null) {
    getData(INVENTORY_URL + "/" + inventoryEditId).then(response => {
        console.log(response)
        return response.inventory
    }).then(inventory => {
        let selectedResourcesTBody = document.querySelector('#selected_resource_table tbody')
        let time = getElement('created_time')
        time.textContent = inventory.createdTime
        getElement('time_block').hidden = false

        getElement('selected_warehouse_id').textContent = inventory.warehouseId
        getElement('selected_warehouse').value = inventory.warehouseName

        inventory.resources.forEach(resource => {
            let size = ''
            if (!stringIsBlank(resource.size)) {
                size = resource.size
            }
            let tr = createTr([resource.id, resource.name, size, resource.actualCount, resource.estimatedCount, resource.difference,  resource.unit, createDeleteResourceSymbol()])
            selectedResourcesTBody.appendChild(tr)
        })

    })
    localStorage.removeItem('inventory_id')
}

function closeEditInventoryHandler() {
    window.location.replace(UI_INVENTORY_ALL_URL)
}

function showWarehouseHandler() {
    let tBody = document.querySelector('#warehouse_table tbody')
    clearTBody(tBody)

    getData(WAREHOUSES_URL).then(response => {
        console.log(response)
        return response.warehouses
    }).then(warehouses => {
        warehouses.forEach(warehouse => {
            let checkBox = createCheckBox()
            checkBox.onclick = () => {
                let trs = tBody.childNodes
                trs.forEach(tr => {
                    let otherCheckbox = tr.childNodes[2].firstChild
                    otherCheckbox.checked = false
                })
                checkBox.checked = true
                getElement('selected_warehouse_id').textContent = warehouse.id
            }
            let tr = createTr([warehouse.id, warehouse.name, checkBox])
            tBody.appendChild(tr)
        })
    })

    showModal('warehouse_modal')
}

function selectWarehouseHandler() {
    let trs = document.querySelector('#warehouse_table tbody').childNodes
    let selectedResourcesTBody = document.querySelector('#selected_resource_table tbody')
    clearTBody(selectedResourcesTBody)
    trs.forEach(tr => {
        let checkbox = tr.childNodes[2].firstChild
        if (checkbox.checked) {
            getElement('selected_warehouse').value = tr.childNodes[1].textContent
            return
        }
    })
}

function showResourceHandler() {
    let warehouseId = getElement('selected_warehouse_id').textContent
    if (stringIsBlank(warehouseId)) {
        showModalError('Выберите склад для которого призводится инвентаризация.')
    } else {
        let tBody = document.querySelector('#resource_table tbody')
        clearTBody(tBody)
        getData(REMAINING_URL + "/" + warehouseId).then(response => {
            console.log(response)
            return response.remains
        }).then(remains => {

            remains.forEach(remain => {
                let inputCount = createInput('number', 0, null)
                inputCount.className += ' form-control'
                inputCount.value = getCountResourcesByIdFromSelectedTable(remain.resourceId)

                let size = ''
                if (!stringIsBlank(remain.size)) {
                    size = remain.size
                }

                let tr = createTr([remain.resourceId, remain.name, size, inputCount, remain.count, remain.unit])
                tBody.appendChild(tr)
            })
        })

        showModal('resource_modal')
    }
}

function getCountResourcesByIdFromSelectedTable(id) {
    let count
    let selectedResourcesTBody = document.querySelector('#selected_resource_table tbody')
    let trs = selectedResourcesTBody.childNodes
    trs.forEach(tr => {
        if (tr.childNodes[0].textContent === String(id)) {
            count = tr.childNodes[3].textContent
            return
        }
    })
    return count
}

function showModalError(errorDescription) {
    getElement('error_desc').textContent = errorDescription
    showModal('error_modal')
}

function createDeleteResourceSymbol() {
    let deleteSymbol = document.createElement('span')
    deleteSymbol.textContent = '✖'
    deleteSymbol.style.fontSize = '10px'
    deleteSymbol.style.cursor = 'pointer'
    deleteSymbol.title = 'Удалить'
    deleteSymbol.onclick = () => {
        deleteSymbol.parentNode.parentNode.remove()
    }
    return deleteSymbol
}

function handleSelectResourceBtn() {
    let resourcesTBody = document.querySelector('#resource_table tbody')
    let selectedResourcesTBody = document.querySelector('#selected_resource_table tbody')
    clearTBody(selectedResourcesTBody)

    let trs = resourcesTBody.childNodes
    let showErrorNegativeCount = false

    trs.forEach(tr => {
        let tds = tr.childNodes

        let code = tds[0].textContent
        let name = tds[1].textContent
        let size = tds[2].textContent
        let count = tds[3].firstChild.value
        let remain = tds[4].textContent
        let diff = Number(count) - Number(remain)
        let unit = tds[5].textContent

        if (count < 0) {
            showErrorNegativeCount = true
        }

        if (count !== null && count > 0) {
            let tr = createTr([code, name, size, count, remain, diff,  unit, createDeleteResourceSymbol()])
            selectedResourcesTBody.appendChild(tr)
        }
    })

    if (showErrorNegativeCount) {
        showModalError('Фвктический остаток ресурсов не может быть отрицательным.')
    }
}

function handleSaveInventoryBtn() {
    let warehouseId = getElement('selected_warehouse_id').textContent

    let selectedResourceTbody = document.querySelector('#selected_resource_table tbody')
    let resourcesRequest = []

    let trs = selectedResourceTbody.childNodes
    trs.forEach(tr => {
        let resourceId = tr.childNodes[0].textContent
        let actualCount = tr.childNodes[3].textContent
        let settlementCount = tr.childNodes[4].textContent
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
                showModalError(response.description)
            } else {
                window.location.replace(UI_INVENTORY_ALL_URL)
            }
        })
    } else {
        postData(INVENTORY_URL, request).then(response => {
            console.log(response)

            if (response.status !== SUCCESS) {
                showModalError(response.description)
            } else {
                window.location.replace(UI_INVENTORY_ALL_URL)
            }
        })
    }

}
