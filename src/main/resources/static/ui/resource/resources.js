// Добавить пагинацию, фильтрацию

function handleAddResourceBtn() {
    getElement('editModalLabel').textContent = 'Добавить ресурс'
    showModal('edit_modal')
}

getData(RESOURCE_URL).then(response => {
    console.log(response)
    let tBody = document.querySelector('#resources_table tbody')
    let resources = response.resources
    resources.forEach(resource => {
        let delBtn = createDeleteResourceSymbol(resource.id)
        let tr = createTr([resource.id, resource.name, resource.type, resource.unit, delBtn])
        trHandler(tr, resource.id)
        tBody.appendChild(tr)
    })
})

function createDeleteResourceSymbol(resourceId) {
    let deleteSymbol = document.createElement('span')
    deleteSymbol.textContent = '✖'
    deleteSymbol.style.fontSize = '10px'
    deleteSymbol.style.cursor = 'pointer'
    deleteSymbol.title = 'Удалить'
    deleteSymbol.onclick = () => {
        getElement('error_del_desc').textContent = ''
        getData(RESOURCE_URL + "/" + resourceId).then(response => {
            console.log(response)
            return response.resource
        }).then(resource => {
            getElement("del_resource_id").value = resource.id
            getElement("del_resource_name").value = resource.name
            getElement("del_resource_type").value = resource.type
            getElement("del_resource_unit").value = resource.unit

            showModal('delete_modal')
        })
    }
    return deleteSymbol
}

function handleDeleteResourceBtn() {
    let resourceId = getElement("del_resource_id").value
    deleteData(RESOURCE_URL + "/" + resourceId).then(response => {
        console.log(response)
        if (response.status !== SUCCESS) {
            getElement('error_del_desc').textContent = response.description
        } else {
            window.location.reload()
        }
    })
}

function trHandler(tr, resourceId) {
    let tds = tr.childNodes
    for (let i = 0; i < tds.length - 1; i++) {
        tds[i].onclick = () => {
            getData(RESOURCE_URL + "/" + resourceId).then(response => {
                console.log(response)
                return response.resource
            }).then(resource => {
                getElement("resource_id").value = resource.id
                getElement("resource_name_input").value = resource.name
                getElement("resource_type_select").value = resource.type
                getElement("resource_unit_select").value = resource.unit

                getElement('editModalLabel').textContent = 'Изменить ресурс'
                getElement('edit_modal_code_row').hidden = false

                showModal('edit_modal')
            })
        }
    }
    mouseOnTrHandler(tr)
}

getData(RESOURCE_URL + "/types").then(response => {
    console.log(response)
    return response.resourceTypes
}).then(types => {
    let resourceTypeSelected = getElement('resource_type_select')
    types.forEach(type => {
        let option = createElement('option')
        option.textContent = type
        resourceTypeSelected.appendChild(option)
    })
})

getData(RESOURCE_URL + "/units").then(response => {
    console.log(response)
    return response.resourceUnits
}).then(units => {
    let resourceUnitsSelected = getElement('resource_unit_select')
    units.forEach(unit => {
        let option = createElement('option')
        option.textContent = unit
        resourceUnitsSelected.appendChild(option)
    })
})

function handleSaveResourceBtn() {
    let resourceId = getElement("resource_id").value
    let request = {
        name: getElement('resource_name_input').value,
        type: getElement('resource_type_select').value,
        unit: getElement('resource_unit_select').value
    }

    if (stringIsBlank(resourceId)) {
        postData(RESOURCE_URL, request).then(response => {
            console.log(response)
            if (response.status !== SUCCESS) {
                showModalError(response.description)
            } else {
                window.location.reload()
            }
        })
    } else {
        putData(RESOURCE_URL + "/" + resourceId, request).then(response => {
            console.log(response)
            if (response.status !== SUCCESS) {
                showModalError(response.description)
            } else {
                window.location.reload()
            }
        })
    }

}

function showModalError(errorDescription) {
    getElement('error_desc').textContent = errorDescription
    showModal('error_modal')
}