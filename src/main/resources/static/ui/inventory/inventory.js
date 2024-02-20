function handleAddInventoryBtn() {
    window.location.replace(UI_INVENTORY_EDIT_URL)
}

getData(INVENTORY_URL).then(response => {
    console.log(response)
    let tBody = document.querySelector('#inventory_table tbody')
    response.inventory.forEach(inventory => {
        let tr = createTr([inventory.id, inventory.createdTime, inventory.warehouseName])
        tr.onclick = () => {
            localStorage.setItem('inventory_id', inventory.id)
            window.location.replace(UI_INVENTORY_SHOW_URL)
        }
        tBody.appendChild(tr)
    })
})