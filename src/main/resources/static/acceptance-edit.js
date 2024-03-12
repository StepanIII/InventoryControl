
function handleSaveAcceptanceBtn() {
    let benefactorId = getElement('selected_benefactor_id').textContent
    let warehouseId = getElement('selected_warehouse_id').textContent

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
        benefactorId: benefactorId,
        warehouseId: warehouseId,
        resources: resourcesRequest
    }

    postData(ACCEPT_URL, request).then(response => {
        console.log(response)

        if (response.status !== SUCCESS) {
            showModalError(response.description)
        } else {
            window.location.replace(UI_ACCEPT_ALL_URL)
        }
    })
}

function closeEditAcceptHandler() {
    window.location.replace(UI_ACCEPT_ALL_URL)
}

function showBenefactorHandler() {
    let tBody = document.querySelector('#benefactor_table tbody')
    clearTBody(tBody)

    getData(BENEFACTORS_URL).then(response => {
        console.log(response)
        return response.benefactors
    }).then(benefactors => {
        benefactors.forEach(benefactor => {
            let checkBox = createCheckBox()
            checkBox.onclick = () => {
                let trs = tBody.childNodes
                trs.forEach(tr => {
                    let otherCheckbox = tr.childNodes[3].firstChild
                    otherCheckbox.checked = false
                })
                checkBox.checked = true
                getElement('selected_benefactor_id').textContent = benefactor.id
            }

            let phone = ''
            if (!stringIsBlank(benefactor.phone)) {
                phone = benefactor.phone
            }
            let tr = createTr([benefactor.id, benefactor.fio, phone, checkBox])
            tBody.appendChild(tr)
        })

    })

    showModal('benefactors_modal')
}

function selectBenefactorHandler() {
    let trs = document.querySelector('#benefactor_table tbody').childNodes
    trs.forEach(tr => {
        let checkbox = tr.childNodes[3].firstChild
        if (checkbox.checked) {
            getElement('selected_benefactor').value = tr.childNodes[1].textContent
            return
        }
    })
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
    trs.forEach(tr => {
        let checkbox = tr.childNodes[2].firstChild
        if (checkbox.checked) {
            getElement('selected_warehouse').value = tr.childNodes[1].textContent
            return
        }
    })
}

function showResourceHandler() {
    let tBody = document.querySelector('#resource_table tbody')
    clearTBody(tBody)

    getData(RESOURCE_URL).then(response => {
        console.log(response)
        return response.resources
    }).then(resources => {
        resources.forEach(resource => {
            let input = createInput('number', 0, null)
            input.className += ' form-control'
            input.value = getCountResourcesByIdFromSelectedTable(resource.id)

            let size = ''
            if (!stringIsBlank(resource.size)) {
                size = resource.size
            }

            let tr = createTr([resource.id, resource.name, size, input, resource.unit])
            tBody.appendChild(tr)
        })
    })

    showModal('resource_modal')
}

function getCountResourcesByIdFromSelectedTable(id) {
    let count
    let selectedResourcesTBody = document.querySelector('#selected_resource_table tbody')
    let trs = selectedResourcesTBody.childNodes
    trs.forEach(tr => {
        if (tr.childNodes[0].textContent === String(id)) {
            count = tr.childNodes[4].textContent
            return
        }
    })
    return count
}

function handleSelectResourceBtn() {
    let resourcesTBody = document.querySelector('#resource_table tbody')
    let selectedResourcesTBody = document.querySelector('#selected_resource_table tbody')
    clearTBody(selectedResourcesTBody)

    let trs = resourcesTBody.childNodes
    let showErrorCount = false
    trs.forEach(tr => {
        let tds = tr.childNodes

        let code = tds[0].textContent
        let name = tds[1].textContent
        let size = tds[2].textContent
        let count = tds[3].firstChild.value
        let unit = tds[4].textContent

        if (count < 0) {
            showErrorCount = true
        }

        if (count !== null && count > 0) {
            let tr = createTr([code, name, size, count, unit, createDeleteResourceSymbol()])
            selectedResourcesTBody.appendChild(tr)
        }
    })

    if (showErrorCount) {
        showModalError('Количество добавляемых ресурсов не может быть отрицательным.')
    }
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

function showModalError(errorDescription) {
    getElement('error_desc').textContent = errorDescription
    showModal('error_modal')
}