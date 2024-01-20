let writeOffId = localStorage.getItem('write_off_id');
// if (acceptId !== null) {
//     getData(ACCEPTANCE_URL + "/" + acceptId).then(response => {
//         let benefactor = response.benefactor
//         let warehouse = response.warehouse
//         let addedResources = response.resources
//
//         let selectedBenefactor = getElement('selected_benefactor')
//         selectedBenefactor.textContent = benefactor.fio
//         selectedBenefactor.hidden = false
//
//         let selectedBenefactorId = getElement('selected_benefactor_id')
//         selectedBenefactorId.textContent = benefactor.id
//
//         let selectedWarehouse = getElement('selected_warehouse')
//         selectedWarehouse.textContent = warehouse.name
//         selectedWarehouse.hidden = false
//
//         let selectedWarehouseId = getElement('selected_warehouse_id')
//         selectedWarehouseId.textContent = warehouse.id
//
//         let selectedResourceTableBody = document.querySelector('#selected_resource_table tbody')
//         addedResources.forEach(r => {
//             selectedResourceTableBody.appendChild(createTr([r.id, r.name, r.count, createDeleteAcceptResourceBtn()]))
//         })
//     })
//     localStorage.removeItem('accept_id')
// }

function createDeleteWriteOffResourceBtn() {
    let btn = createBtn('Удалить', '')
    btn.onclick = (e) => {
        let tr = e.currentTarget.parentNode.parentNode
        tr.remove()
    }
    return btn
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
                }warehouses
            }
            if (selectedTr !== null) {
                selectedTr.childNodes[2].textContent = (Number(selectedTr.childNodes[2].textContent) + Number(selectCount)).toString()
            } else {
                selectedResourceTableBody.appendChild(createTr([selectCode, selectName, selectCount, createDeleteWriteOffResourceBtn()]))
            }
        }
    }
    getElement('select_resources_block').hidden = true
}


function handleSaveWriteOffBtn() {
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
    let writeOffRequest = {
        warehouseId: warehouseId,
        resources: resourcesRequest
    }
    postData(WRITE_OFF_URL, writeOffRequest).then(window.location.replace(UI_WRITE_OFF_ALL_URL))
    // if (writeOffId !== null) {
    //     putData(ACCEPTANCE_URL + "/" + writeOffId, writeOffRequest).then(window.location.replace(UI_ACCEPTANCE_ALL_URL))
    // } else {
    //     postData(WRITE_OFF_URL, acceptRequest).then(window.location.replace(UI_WRITE_OFF_ALL_URL))
    // }
}
