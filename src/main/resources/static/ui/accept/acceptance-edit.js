
function handleBenefactorSelect() {
    getData(BENEFACTORS_URL).then(response => {
        console.log(response)

        let openListBenefactorBtn = getElement('open_list_benefactor_btn')
        let table = getElement('benefactor_table');
        let tBody = document.querySelector('#benefactor_table tbody')

        response.benefactors.forEach(benefactor => {
            let tr = createTr([benefactor.id, benefactor.fio])
            tr.onclick = () => {
                getElement('selected_benefactor').textContent = benefactor.fio
                getElement('selected_benefactor_id').textContent = benefactor.id

                clearTBody(tBody)
                table.hidden = true
                openListBenefactorBtn.disabled = false
            }
            tBody.appendChild(tr)
        })

        table.hidden = false
        openListBenefactorBtn.disabled = true
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
            }
            tBody.appendChild(tr)
        })

        table.hidden = false
        openListWarehouseBtn.disabled = true
    })
}

function handleResourceSelect() {
    getData(RESOURCE_URL).then(response => {
        console.log(response)

        let tBody = document.querySelector('#resources_table tbody')
        response.resources.forEach(resource => {
            let tr = createTr([resource.id, resource.name, createInput('number', 0)])
            tBody.appendChild(tr)
        })

        getElement('select_resources_block').hidden = false
        getElement('open_list_resources_btn').disabled = true
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

function handleSaveAcceptanceBtn() {
    let benefactorId = Number(getElement('selected_benefactor_id').textContent)
    let warehouseId = Number(getElement('selected_warehouse_id').textContent)

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
        benefactorId: benefactorId,
        warehouseId: warehouseId,
        resources: resourcesRequest
    }

    postData(ACCEPT_URL, request).then(response => {
        console.log(response)
        window.location.replace(UI_ACCEPT_ALL_URL)
    })
}
