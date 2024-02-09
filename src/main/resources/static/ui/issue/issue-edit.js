function handleBeneficiarySelect() {
    getData(BENEFICIARIES_URL).then(response => {
        console.log(response)

        let openListBeneficiaryBtn = getElement('open_list_beneficiary_btn')
        let table = getElement('beneficiary_table');
        let tBody = document.querySelector('#beneficiary_table tbody')

        response.beneficiaries.forEach(beneficiary => {
            let tr = createTr([beneficiary.id, beneficiary.fio])
            tr.onclick = () => {
                getElement('selected_beneficiary').textContent = beneficiary.fio
                getElement('selected_beneficiary_id').textContent = beneficiary.id

                clearTBody(tBody)
                table.hidden = true
                openListBeneficiaryBtn.disabled = false
            }
            tBody.appendChild(tr)
        })

        table.hidden = false
        openListBeneficiaryBtn.disabled = true
    })
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
                table.hidden = true
                openListWarehouseBtn.disabled = false

                getData(WAREHOUSES_URL + '/' + warehouse.id + "/remains").then(response => {
                    console.log(response)
                    let tBody = document.querySelector('#resources_table tbody')
                    clearTBody(tBody)
                    response.remains.forEach(remain => {
                        let inputCount = createInput('number', 0, remain.count)
                        let tr = createTr([remain.resourceId, remain.name, inputCount, remain.count])
                        tBody.appendChild(tr)
                    })
                })
            }
            tBody.appendChild(tr)
        })

        table.hidden = false
        openListWarehouseBtn.disabled = true
    })
}

function handleResourceSelect() {
    getElement('select_resources_block').hidden = false
    getElement('open_list_resources_btn').disabled = true
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
        let count = tds[2].firstChild.value

        if (count !== null && count > 0) {
            let btn = createBtn('Удалить', null)
            btn.onclick = (e) => {
                let tr = e.currentTarget.parentNode.parentNode
                tr.remove()
            }
            let tr = createTr([code, name, count, btn])
            selectedResourcesTBody.appendChild(tr)
        }
    })

    clearTBody(resourcesTBody)
    getElement('select_resources_block').hidden = true
    getElement('open_list_resources_btn').disabled = false
}

function handleSaveIssueBtn() {
    let benefactorId = getElement('selected_beneficiary_id').textContent
    let warehouseId = getElement('selected_warehouse_id').textContent

    let selectedResourceTbody = document.querySelector('#selected_resource_table tbody')
    let resourcesRequest = []

    let trs = selectedResourceTbody.childNodes
    trs.forEach(tr => {
        let resourceId = tr.childNodes[0].textContent
        let count = tr.childNodes[2].textContent
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
            getElement('error_desc').textContent = response.errorDescription
        } else {
            window.location.replace(UI_ISSUE_ALL_URL)
        }
    })
}