getData(WAREHOUSES_URL).then(response => {
    console.log(response)
    let tBody = document.querySelector('#warehouse_table tbody')
    let warehouses = response.warehouses
    warehouses.forEach(warehouse => {
        let delBtn = createDeleteWarehouseSymbol(warehouse.id)
        let tr = createTr([warehouse.id, warehouse.name, delBtn])
        trHandler(tr, warehouse.id)
        tBody.appendChild(tr)
    })
})

function handleAddWarehouseBtn() {
    getElement('editModalLabel').textContent = 'Добавить склад'
    getElement("warehouse_id").value = ''
    getElement("warehouse_name_input").value = ''

    showModal('edit_modal')
}

function handleSaveWarehouseBtn() {
    let warehouseId = getElement("warehouse_id").value
    let request = {
        name: getElement('warehouse_name_input').value
    }

    if (stringIsBlank(warehouseId)) {
        postData(WAREHOUSES_URL, request).then(response => {
            console.log(response)
            if (response.status !== SUCCESS) {
                getElement('error_desc_modal').textContent = response.description
            } else {
                window.location.reload()
            }
        })
    } else {
        putData(WAREHOUSES_URL + "/" + warehouseId, request).then(response => {
            console.log(response)
            if (response.status !== SUCCESS) {
                getElement('error_desc_modal').textContent = response.description
            } else {
                window.location.reload()
            }
        })
    }
}

function createDeleteWarehouseSymbol(warehouseId) {
    let deleteSymbol = document.createElement('span')
    deleteSymbol.textContent = '✖'
    deleteSymbol.style.fontSize = '10px'
    deleteSymbol.style.cursor = 'pointer'
    deleteSymbol.title = 'Удалить'
    deleteSymbol.onclick = () => {
        getElement('error_del_desc').textContent = ''
        getData(WAREHOUSES_URL + "/" + warehouseId).then(response => {
            console.log(response)
            return response.warehouse
        }).then(warehouse => {
            getElement("del_warehouse_id").value = warehouse.id
            getElement("del_warehouse_name").value = warehouse.name

            showModal('delete_modal')
        })
    }
    return deleteSymbol
}

function handleDeleteWarehouseBtn() {
    let warehouseId = getElement("del_warehouse_id").value
    deleteData(WAREHOUSES_URL + "/" + warehouseId).then(response => {
        console.log(response)
        if (response.status !== SUCCESS) {
            getElement('error_del_desc').textContent = response.description
        } else {
            window.location.reload()
        }
    })
}

function trHandler(tr, warehouseId) {
    let tds = tr.childNodes
    for (let i = 0; i < tds.length - 1; i++) {
        tds[i].onclick = () => {
            getData(WAREHOUSES_URL + "/" + warehouseId).then(response => {
                console.log(response)
                return response.warehouse
            }).then(warehouse => {
                getElement("warehouse_id").value = warehouse.id
                getElement("warehouse_name_input").value = warehouse.name

                getElement('editModalLabel').textContent = 'Изменить склад'
                getElement('edit_modal_code_row').hidden = false

                showModal('edit_modal')
            })
        }
    }
    mouseOnTrHandler(tr)
}