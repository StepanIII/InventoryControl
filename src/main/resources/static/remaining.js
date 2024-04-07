getData(REMAINING_URL).then(response => {
    console.log(response)

    let tBody = document.querySelector('#remaining_table tbody')
    let remaining = response.remaining
    for (let i = 0; i < remaining.length; i++) {
        let remain = remaining[i]
        let size = ''
        if (!stringIsBlank(remain.size)) {
            size = remain.size
        }
        let tr = createTr([remain.resourceId, remain.name, size, remain.count, remain.unit, remain.warehouseName])
        mouseOnTrHandler(tr)
        tBody.appendChild(tr)
    }
})

getData(WAREHOUSES_URL).then(response => {
    console.log(response)
    let warehouses = response.warehouses
    let warehouseSelected = getElement('warehouse_select')
    warehouses.forEach(warehouse => {
        let option = createElement('option')
        option.onclick = () => {
            getElement('warehouse_select_id').textContent = warehouse.id
        }
        option.textContent = warehouse.name
        warehouseSelected.appendChild(option)
    })
})

function handleFilter() {
    let tBody = document.querySelector('#remaining_table tbody')
    clearTBody(tBody)
    let selectedWarehouseName = getElement('warehouse_select').value
    console.log(selectedWarehouseName)

    getData(REMAINING_URL).then(response => {
        console.log(response)
        return response.remaining
    }).then(remains => {
        remains
            .filter(remain => remain.warehouseName === selectedWarehouseName)
            .forEach(remain => {
            let size = ''
            if (!stringIsBlank(remain.size)) {
                size = remain.size
            }
            let tr = createTr([remain.resourceId, remain.name, size, remain.count, remain.unit, remain.warehouseName])
            mouseOnTrHandler(tr)
            tBody.appendChild(tr)
        })
    })
}

function handleResetFilter() {
    getData(REMAINING_URL).then(response => {
        console.log(response)
        let tBody = document.querySelector('#remaining_table tbody')
        clearTBody(tBody)
        let remaining = response.remaining
        for (let i = 0; i < remaining.length; i++) {
            let remain = remaining[i]
            let size = ''
            if (!stringIsBlank(remain.size)) {
                size = remain.size
            }
            let tr = createTr([remain.resourceId, remain.name, size, remain.count, remain.unit, remain.warehouseName])
            mouseOnTrHandler(tr)
            tBody.appendChild(tr)
        }
    })
}