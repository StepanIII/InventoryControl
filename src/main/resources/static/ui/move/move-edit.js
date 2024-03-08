function closeEditMoveHandler() {
    window.location.replace(UI_MOVE_ALL_URL)
}

function showWarehouseFromHandler() {
    let tBody = document.querySelector('#from_warehouse_table tbody')
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
                getElement('from_warehouse_id').textContent = warehouse.id
            }
            let tr = createTr([warehouse.id, warehouse.name, checkBox])
            tBody.appendChild(tr)
        })
    })

    showModal('from_warehouse_modal')
}

function selectWarehouseFromHandler() {
    let trs = document.querySelector('#from_warehouse_table tbody').childNodes
    let showError
    trs.forEach(tr => {
        let checkbox = tr.childNodes[2].firstChild
        if (checkbox.checked) {
            if (getElement('to_warehouse_id').textContent === getElement('from_warehouse_id').textContent) {
                showError = true
            } else {
                getElement('from_warehouse').value = tr.childNodes[1].textContent
                return
            }
        }
    })

    if (showError) {
        showModalError('Нельзя выполнить перемещение с одного и того же склада')
    }
}

function showWarehouseToHandler() {
    let tBody = document.querySelector('#to_warehouse_table tbody')
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
                getElement('to_warehouse_id').textContent = warehouse.id
            }
            let tr = createTr([warehouse.id, warehouse.name, checkBox])
            tBody.appendChild(tr)
        })
    })

    showModal('to_warehouse_modal')
}

function selectWarehouseToHandler() {
    let trs = document.querySelector('#to_warehouse_table tbody').childNodes
    let showError
    trs.forEach(tr => {
        let checkbox = tr.childNodes[2].firstChild
        if (checkbox.checked) {
            if (getElement('to_warehouse_id').textContent === getElement('from_warehouse_id').textContent) {
                showError = true
            } else {
                getElement('to_warehouse').value = tr.childNodes[1].textContent
                return
            }
        }
    })

    if (showError) {
        showModalError('Нельзя выполнить перемещение с одного и того же склада')
    }
}

function showResourceHandler() {
    let warehouseIdFrom = getElement('from_warehouse_id').textContent
    let warehouseIdTo = getElement('to_warehouse_id').textContent
    if (stringIsBlank(warehouseIdFrom)) {
        showModalError('Выберите склад с которого будет производится перемещение ресурсов.')
    } else if (stringIsBlank(warehouseIdTo)) {
        showModalError('Выберите склад на который будет производится перемещение ресурсов.')
    } else {
        let tBody = document.querySelector('#resource_table tbody')
        clearTBody(tBody)

        getData(WAREHOUSES_URL + '/' + warehouseIdFrom + '/remains').then(response => {
            console.log(response)
            return response.remains
        }).then(remains => {
            remains.forEach(remain => {
                let inputCount = createInput('number', 0, remain.count)
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

function handleSelectResourceBtn() {
    let resourcesTBody = document.querySelector('#resource_table tbody')
    let selectedResourcesTBody = document.querySelector('#selected_resource_table tbody')
    clearTBody(selectedResourcesTBody)

    let showErrorNegativeCount
    let showErrorMoreCount
    let trs = resourcesTBody.childNodes
    trs.forEach(tr => {
        let tds = tr.childNodes

        let code = tds[0].textContent
        let name = tds[1].textContent
        let size = tds[2].textContent
        let count = tds[3].firstChild.value
        let remain = tds[4].textContent
        let unit = tds[5].textContent

        if (count < 0) {
            showErrorNegativeCount = true
        }

        if (count > Number(remain)) {
            showErrorMoreCount = true
        }

        if (count !== null && count > 0) {
            let tr = createTr([code, name, size, count, unit, createDeleteResourceSymbol()])
            selectedResourcesTBody.appendChild(tr)
        }
    })

    if (showErrorNegativeCount) {
        showModalError('Количество ресурсов на перемещение не может быть отрицательным.')
    }
    if (showErrorMoreCount) {
        showModalError('На складе нет такого количества ресурсов.')
    }
}

function showModalError(errorDescription) {
    getElement('error_desc').textContent = errorDescription
    showModal('error_modal')
}

function handleSaveMoveBtn() {
    let fromWarehouseId = getElement('from_warehouse_id').textContent
    let toWarehouseId = getElement('to_warehouse_id').textContent

    let selectedResourceTbody = document.querySelector('#selected_resource_table tbody')
    let resourcesRequest = []

    let trs = selectedResourceTbody.childNodes
    trs.forEach(tr => {
        let resourceId = tr.childNodes[0].textContent
        let count = tr.childNodes[3].textContent

        let resourceReq = {
            resourceId: resourceId,
            count: count
        }
        resourcesRequest.push(resourceReq)
    })

    let request = {
        fromWarehouseId: fromWarehouseId,
        toWarehouseId: toWarehouseId,
        resources: resourcesRequest
    }

    postData(MOVE_URL, request).then(response => {
        console.log(response)

        if (response.status !== SUCCESS) {
            showModalError(response.description)
        } else {
            window.location.replace(UI_MOVE_ALL_URL)
        }
    })
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
