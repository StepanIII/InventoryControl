function handleAddInventoryBtn() {
    window.location.replace(UI_INVENTORY_EDIT_URL)
}

getData(INVENTORY_URL).then(response => {
    console.log(response)
    let tBody = document.querySelector('#inventory_table tbody')
    response.inventory.forEach(inventory => {
        let tr = createTr([inventory.id, inventory.createdTime, inventory.warehouseName, createDeleteResourceSymbol(inventory.id)])
        trHandler(tr, inventory.id)
        tBody.appendChild(tr)
    })
})

function trHandler(tr, inventoryId) {
    let tds = tr.childNodes
    for (let i = 0; i < tds.length - 1; i++) {
        tds[i].onclick = () => {
            localStorage.setItem('inventory_id', inventoryId)
            window.location.replace(UI_INVENTORY_EDIT_URL)
        }
    }
    mouseOnTrHandler(tr)
}

function createDeleteResourceSymbol(inventoryId) {
    let deleteSymbol = document.createElement('span')
    deleteSymbol.textContent = '✖'
    deleteSymbol.style.fontSize = '10px'
    deleteSymbol.style.cursor = 'pointer'
    deleteSymbol.title = 'Удалить'
    deleteSymbol.onclick = () => {
        getElement('error_del_desc').textContent = ''
        getData(INVENTORY_URL + "/" + inventoryId).then(response => {
            console.log(response)
            return response.inventory
        }).then(inventory => {
            getElement("del_inventory_id").value = inventory.id
            getElement("del_inventory_time").value = inventory.createdTime
            getElement("del_inventory_warehouse").value = inventory.warehouseName

            inventory.resources.forEach(resource => {
                let size = ''
                if (!stringIsBlank(resource.size)) {
                    size = resource.size
                }
                let tr = createTr([resource.id, resource.name, size, resource.actualCount, resource.estimatedCount, resource.difference, resource.unit])
                document.querySelector('#inventory_resource_table tbody').appendChild(tr)
            })

            showModal('delete_modal')
        })
    }
    return deleteSymbol
}

function handleDeleteInventoryBtn() {
    let inventoryId = getElement("del_inventory_id").value
    deleteData(INVENTORY_URL + "/" + inventoryId).then(response => {
        console.log(response)
        if (response.status !== SUCCESS) {
            showModalError(response.description)
        } else {
            window.location.reload()
        }
    })
}

function showModalError(errorDescription) {
    getElement('error_desc').textContent = errorDescription
    showModal('error_modal')
}