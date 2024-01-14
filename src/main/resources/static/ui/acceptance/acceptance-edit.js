let acceptId = localStorage.getItem('accept_id');
if (acceptId !== null) {
    getData(ACCEPTANCE_URL + "/" + acceptId).then(response => {
        let benefactor = response.benefactor
        let warehouse = response.warehouse
        let addedResources = response.resources

        let selectedBenefactor = getElement('selected_benefactor')
        selectedBenefactor.textContent = benefactor.fio
        selectedBenefactor.hidden = false

        let selectedBenefactorId = getElement('selected_benefactor_id')
        selectedBenefactorId.textContent = benefactor.id

        let selectedWarehouse = getElement('selected_warehouse')
        selectedWarehouse.textContent = warehouse.name
        selectedWarehouse.hidden = false

        let selectedWarehouseId = getElement('selected_warehouse_id')
        selectedWarehouseId.textContent = warehouse.id

        let selectedResourceTableBody = document.querySelector('#selected_resource_table tbody')
        addedResources.forEach(r => {
            selectedResourceTableBody.appendChild(createTr([r.id, r.name, r.count, createDeleteAcceptResourceBtn()]))
        })
    })
    localStorage.removeItem('accept_id')
}

function createDeleteAcceptResourceBtn() {
    let btn = createBtn('Удалить', '')
    btn.onclick = (e) => {
        let tr = e.currentTarget.parentNode.parentNode
        tr.remove()
    }
    return btn
}

function handleBenefactorSelect() {
    getData(BENEFACTORS_URL).then((response) => {
        let tBody = document.querySelector('#benefactor_table_select tbody')
        removeChildNodes(tBody)
        let trHeader = createTr(['Код', 'ФИО'])
        tBody.appendChild(trHeader)

        let benefactors = response.benefactors
        benefactors.forEach((benefactor) => {
            let tr = createTr([benefactor.id, benefactor.fio])
            tr.addEventListener('click', handleSelectBenefactorTr)
            tBody.appendChild(tr)
        })
    })
    getElement('benefactor_block').hidden = false
}

function handleSelectBenefactorTr(e) {
    let tr = e.currentTarget
    let id = tr.childNodes[0].textContent
    let fio = tr.childNodes[1].textContent
    let pFio = getElement('selected_benefactor')
    getElement('selected_benefactor_id').textContent = id
    pFio.textContent = fio
    pFio.hidden = false
    getElement('benefactor_block').hidden = true
}

function handleWarehouseSelect() {
    getData(WAREHOUSES_URL).then((response) => {
        let tBody = document.querySelector('#warehouse_table_select tbody')
        removeChildNodes(tBody)
        let trHeader = createTr(['Код', 'Наименование'])
        tBody.appendChild(trHeader)

        let warehouses = response.warehouses
        console.log(warehouses)
        warehouses.forEach((warehouse) => {
            let tr = createTr([warehouse.id, warehouse.name])
            tr.addEventListener('click', handleSelectWarehouseTr)
            tBody.appendChild(tr)
        })
    })
    getElement('warehouse_block').hidden = false
}

function handleSelectWarehouseTr(e) {
    let tr = e.currentTarget
    let id = tr.childNodes[0].textContent
    let name = tr.childNodes[1].textContent
    let pName = getElement('selected_warehouse')
    getElement('selected_warehouse_id').textContent = id
    pName.textContent = name
    pName.hidden = false
    getElement('warehouse_block').hidden = true
}

function handleResourceSelect() {
    getData(RESOURCES_URL).then((response) => {
        let tBody = document.querySelector('#resources_table_select tbody')
        removeChildNodes(tBody)
        let trHeader = createTr(['Код', 'Наименование', 'Количество'])
        tBody.appendChild(trHeader)

        let resources = response.resources
        resources.forEach((resource) => {
            let tr = createTr([resource.id, resource.name, createElement('input')])
            tBody.appendChild(tr)
        })
    })
    getElement('select_resources_block').hidden = false
}

function handleSelectResourceBtn() {
    let resourcesTableSelectBody = document.querySelector('#resources_table_select tbody')
    let selectedResourceTableBody = document.querySelector('#selected_resource_table tbody')

    let selectTrs = resourcesTableSelectBody.childNodes

    for (let i = 1; i < selectTrs.length; i++) {
        let selectTr = selectTrs[i]
        let selectTds = selectTr.childNodes

        let selectCount = selectTds[2].firstChild.value
        let selectCode = selectTds[0].textContent
        let selectName = selectTds[1].textContent

        if (selectCount !== null && selectCount > 0) {
            let selectedTr = null
            let selectedTds = selectedResourceTableBody.childNodes
            for (let i = 1; i < selectedTds.length; i++) {
                let tr = selectedTds[i]
                let tds = tr.childNodes
                let tdCodeValue = tds[0].textContent
                if (tdCodeValue === selectCode) {
                    selectedTr = tr
                    break
                }
            }

            if (selectedTr !== null) {
                selectedTr.childNodes[2].textContent = (Number(selectedTr.childNodes[2].textContent) + Number(selectCount)).toString()
            } else {
                selectedResourceTableBody.appendChild(createTr([selectCode, selectName, selectCount, createDeleteAcceptResourceBtn()]))
            }
        }
    }
    getElement('select_resources_block').hidden = true
}

function handleSaveAcceptanceBtn() {
    let benefactorId = Number(getElement('selected_benefactor_id').textContent)
    let warehouseId = Number(getElement('selected_warehouse_id').textContent)
    let selectedResourceTbody = document.querySelector('#selected_resource_table tbody')
    let trs = selectedResourceTbody.childNodes
    let resourcesRequest = []
    for (let i = 1; i < trs.length; i++) {
        let resourceId = trs[i].childNodes[0].textContent
        let count = trs[i].childNodes[2].textContent
        let resourceReq = {
            resourceId: resourceId,
            count: count
        }
        resourcesRequest.push(resourceReq)
    }
    let acceptRequest = {
        benefactorId: benefactorId,
        warehouseId: warehouseId,
        resources: resourcesRequest
    }
    if (acceptId !== null) {
        putData(ACCEPTANCE_URL + "/" + acceptId, acceptRequest).then(window.location.replace(UI_ACCEPTANCE_ALL_URL))
    } else {
        postData(ACCEPTANCE_URL, acceptRequest).then(window.location.replace(UI_ACCEPTANCE_ALL_URL))
    }
}
