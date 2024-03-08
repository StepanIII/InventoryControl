
function showBeneficiaryHandler() {
    let tBody = document.querySelector('#beneficiary_table tbody')
    clearTBody(tBody)

    getData(BENEFICIARIES_URL).then(response => {
        console.log(response)
        return response.beneficiaries
    }).then(beneficiaries => {
        beneficiaries.forEach(beneficiary => {
            let checkBox = createCheckBox()
            checkBox.onclick = () => {
                let trs = tBody.childNodes
                trs.forEach(tr => {
                    let otherCheckbox = tr.childNodes[3].firstChild
                    otherCheckbox.checked = false
                })
                checkBox.checked = true
                getElement('selected_beneficiary_id').textContent = beneficiary.id
            }
            let phone = ''
            if (!stringIsBlank(beneficiary.phone)) {
                phone = beneficiary.phone
            }
            let tr = createTr([beneficiary.id, beneficiary.fio, phone, checkBox])
            tBody.appendChild(tr)
        })

    })

    showModal('beneficiary_modal')
}

function selectBeneficiaryHandler() {
    let trs = document.querySelector('#beneficiary_table tbody').childNodes
    trs.forEach(tr => {
        let checkbox = tr.childNodes[3].firstChild
        if (checkbox.checked) {
            getElement('selected_beneficiary').value = tr.childNodes[1].textContent
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
    let selectedResourcesTBody = document.querySelector('#selected_resource_table tbody')
    clearTBody(selectedResourcesTBody)
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
    let warehouseId = getElement('selected_warehouse_id').textContent
    if (stringIsBlank(warehouseId)) {
        showModalError('Выберите склад с которого будет производится выборка ресурсов.')
    } else {
        let tBody = document.querySelector('#resource_table tbody')
        clearTBody(tBody)

        getData(WAREHOUSES_URL + '/' + warehouseId + '/remains').then(response => {
            console.log(response)
            return response.remains
        }).then(remains => {
            remains.forEach(remain => {
                let inputCount = createInput('number', 0, remain.count)
                inputCount.className += ' form-control'
                inputCount.value = getCountResourcesByIdFromSelectedTable(remain.resourceId)

                let size = ''
                if (remain.size) {
                    size = remain.size
                }

                let tr = createTr([remain.resourceId, remain.name, size, inputCount, remain.count, remain.unit])
                tBody.appendChild(tr)
            })
        })

        showModal('resource_modal')
    }
}

function handleSelectResourceBtn() {
    let resourcesTBody = document.querySelector('#resource_table tbody')
    let selectedResourcesTBody = document.querySelector('#selected_resource_table tbody')
    clearTBody(selectedResourcesTBody)

    let trs = resourcesTBody.childNodes
    let showErrorNegativeCount = false
    let showErrorMoreCount = false
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
        showModalError('Количество ресурсов на выдачу не может быть отрицательным.')
    }
    if (showErrorMoreCount) {
        showModalError('На складе нет такого количества ресурсов.')
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

function handleSaveIssueBtn() {
    let benefactorId = getElement('selected_beneficiary_id').textContent
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
        beneficiaryId: benefactorId,
        warehouseId: warehouseId,
        resources: resourcesRequest
    }

    postData(ISSUE_URL, request).then(response => {
        console.log(response)

        if (response.status !== SUCCESS) {
            showModalError(response.description)
        } else {
            window.location.replace(UI_ISSUE_ALL_URL)
        }
    })
}

function closeEditIssueHandler() {
    window.location.replace(UI_ISSUE_ALL_URL)
}

function showModalError(errorDescription) {
    getElement('error_desc').textContent = errorDescription
    showModal('error_modal')
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
