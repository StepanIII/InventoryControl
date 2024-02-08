fillSubheaderSecond()

function handleAddResourceBtn() {
    window.location.replace(UI_RESOURCE_EDIT_URL)
}

getData(RESOURCE_URL).then(response => {
    console.log(response)
    let tBody = document.querySelector('#resources_table tbody')
    let resources = response.resources
    resources.forEach(resource => {
        let delBtn = createDeleteResourceBtn(resource.id)
        let editBtn = createEditResourceBtn(resource.id)
        let tr = createTr([resource.id, resource.name, resource.type, resource.unit, delBtn, editBtn])
        tBody.appendChild(tr)
    })
})

function createDeleteResourceBtn(resourceId) {
    let btn = createBtn('Удалить', 'delete_resource_btn')
    btn.onclick = () => {
        console.log(resourceId)
        deleteData(RESOURCE_URL + "/" + resourceId).then(response => {
            console.log(response)
            if (response.status !== SUCCESS) {
                getElement('error_desc').textContent = response.errorDescription
            } else {
                window.location.replace(UI_RESOURCES_ALL_URL)
            }
        })
    }
    return btn
}
function createEditResourceBtn(resourceId) {
    let btn = createBtn('Изменить', 'edit_resource_btn')
    btn.onclick = () => {
        localStorage.setItem('resource_id', resourceId)
        window.location.replace(UI_RESOURCE_EDIT_URL)
    }
    return btn
}