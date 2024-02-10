let resourceId = localStorage.getItem('resource_id');
if (resourceId !== null) {
    getData(RESOURCE_URL + "/" + resourceId).then(response => {
        console.log(response)
        let resource = response.resource
        getElement("resource_name_input").value = resource.name
        getElement("resource_type_select").value = resource.type
        getElement("resource_unit_select").value = resource.unit

    })
    localStorage.removeItem('resource_id')
}

getData(RESOURCE_URL + "/types").then(response => {
    console.log(response)
    let resourceTypes = response.resourceTypes
    let resourceTypeSelected = getElement('resource_type_select')
    resourceTypes.forEach(type => {
        let option = createElement('option')
        option.textContent = type
        resourceTypeSelected.appendChild(option)
    })
})

getData(RESOURCE_URL + "/units").then(response => {
    console.log(response)
    let resourceUnits = response.resourceUnits
    let resourceUnitsSelected = getElement('resource_unit_select')
    resourceUnits.forEach(unit => {
        let option = createElement('option')
        option.textContent = unit
        resourceUnitsSelected.appendChild(option)
    })
})

function handleSaveResourceBtn() {
    let request = {
        name : getElement('resource_name_input').value,
        type : getElement('resource_type_select').value,
        unit : getElement('resource_unit_select').value
    }

    if (resourceId === null) {
        postData(RESOURCE_URL, request).then(response => {
            console.log(response)
            if (response.status !== SUCCESS) {
                getElement('error_desc').textContent = response.description;
            } else {
                window.location.replace(UI_RESOURCES_ALL_URL)
            }
        })
    } else {
        putData(RESOURCE_URL + "/" + resourceId, request).then(response => {
            console.log(response)
            if (response.status !== SUCCESS) {
                getElement('error_desc').textContent = response.description;
            } else {
                window.location.replace(UI_RESOURCES_ALL_URL)
            }
        })
    }

}