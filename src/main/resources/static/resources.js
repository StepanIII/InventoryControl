
// Написать обработчики для кнопок отдельно
// Добавить кнопку отменить изменения

// const resourcesTable = createTable(
//     'resources_table',
//     ['Код', 'Наименование', 'Тип', 'Еденицы измерения', 'Операция'])

let fieldInProcessChanged = false

//Ресурсы
function resourcesBtnHandler() {
    let content = getElement('content')
    removeChildNodes(content)
    let resourcesTable = createResourceTable()
    addToTableResources(resourcesTable)
    content.appendChild(resourcesTable)
}

function createResourceTable() {
    let resourceTable = createTable(
        'resources_table',
        ['Код', 'Наименование', 'Тип', 'Еденицы измерения', 'Операция'])
    resourceTable.appendChild(createAddResourceTr())
    return resourceTable
}

function createAddResourceTr() {
    let addResourceTr = createTr([
        '',
        createElement('input'),
        createElement('input'),
        createElement('input'),
        createSaveResourceBtn()])
    addResourceTr.id = 'add_resource_tr'
    return addResourceTr
}

function createAndAddResourceTrInTable() {
    getElement('resources_table')
        .appendChild(createAddResourceTr())
}

function createSaveResourceBtn() {
    let btn = createBtn('Сохранить', 'save_resource_btn')
    saveResourcesBtnHandler(btn)
    return btn
}

function createSaveChangesResourceBtn() {
    let btn = createBtn('Сохранить', 'save_changes_resource_btn')
    btn.addEventListener('click', saveChangesResourceBtnHandler)
    return btn
}

function createDeleteResourceBtn() {
    let btn = createBtn('Удалить', 'delete_resource_btn')
    delResourceBtnHandler(btn)
    return btn
}

function createCancelChangeResourceBtn(oldValues) {
    let btn = createBtn('Отменить', 'cancel_change_resource_btn')
    cancelChangeResourceBtnHandler(btn, oldValues)
    return btn
}

function addChangeTableValueHandler(tr) {
    let tds = tr.childNodes
    tds[0].addEventListener('click', changeTableValueHandler)
    tds[1].addEventListener('click', changeTableValueHandler)
    tds[2].addEventListener('click', changeTableValueHandler)
    tds[3].addEventListener('click', changeTableValueHandler)
}

const changeResourceHandler = (e) => {
    let btn = getElement('save_changes_resource_btn')
    if (btn === null) {
        btn = createSaveChangesResourceBtn()
        e.currentTarget.parentNode.childNodes[4].appendChild(btn)
    }
}

function saveChangesResourceBtnHandler(e) {
    let thisBtn = e.currentTarget
    let tr = thisBtn.parentNode.parentNode
    let tds = tr.childNodes
    let code = tds[0].textContent
    let name = tds[1].firstChild.value
    let type = tds[2].firstChild.value
    let units = tds[3].firstChild.value

    let changedResource = {
        name: name,
        type: type,
        units: units
    }

    putData(resourceUrl + "/" + code, changedResource)
        .then((response) => {
            console.log(response)
            tds[0].firstChild.remove()
            tds[1].firstChild.remove()
            tds[2].firstChild.remove()
            tds[3].firstChild.remove()
            tds[4].firstChild.remove()

            tds[0].textContent = code
            tds[1].textContent = name
            tds[2].textContent = type
            tds[3].textContent = units

            tds[4].firstChild.remove()
            tds[4].appendChild(createDeleteResourceBtn())
            createAndAddResourceTrInTable()
            addChangeTableValueHandler(tr)
        })
    fieldInProcessChanged = false
}

function saveResourcesBtnHandler(saveResourcesBtn) {
    saveResourcesBtn.onclick = function() {
        let oldSaveResourceBtn = getElement('save_resource_btn')
        let tr = oldSaveResourceBtn.parentElement.parentElement
        tr.id = ''

        let tdCode = tr.children.item(0)
        let tdName = tr.children.item(1)
        let tdType = tr.children.item(2)
        let tdUnits = tr.children.item(3)

        let nameInput = tdName.firstChild
        let typeInput = tdType.firstChild
        let unitsInput = tdUnits.firstChild

        let inputNameValue = nameInput.value
        let inputTypeValue = typeInput.value
        let inputUnitsValue = unitsInput.value

        let delTd = tr.children.item(4)
        let errorTd = getElement('error_td')

        if (inputNameValue === '' || inputTypeValue === '' || inputUnitsValue === '') {
            if (errorTd === null) {
                delTd.insertAdjacentHTML('afterend', '<td id="error_td"><p>Заполните все поля</p></td>')
            }
        } else {
            let newResource = {
                name : inputNameValue,
                resourceType : inputTypeValue,
                units : inputUnitsValue
            }
            postData(resourceUrl, newResource)
                .then((data) => {
                    console.log(data);
                    nameInput.remove()
                    typeInput.remove()
                    unitsInput.remove()
                    let addedResource = data.addedResource;
                    tdCode.textContent = addedResource.id
                    tdName.textContent = addedResource.name
                    tdType.textContent = addedResource.resourceType
                    tdUnits.textContent = addedResource.units
                });

            if (errorTd !== null) {
                errorTd.remove()
            }

            createAndAddResourceTrInTable()
            addChangeTableValueHandler(tr)

            oldSaveResourceBtn.remove()

            delTd.innerHTML = '<button type="button">Удалить</button>'

            let delResourceBtn = delTd.firstChild
            delResourceBtnHandler(delResourceBtn)
            let saveResourcesBtn = document.getElementById('save_resource_btn')
            saveResourcesBtnHandler(saveResourcesBtn)
        }
    }
}

function delResourceBtnHandler(delResourcesBtn) {
    delResourcesBtn.onclick = function() {
        let tr = delResourcesBtn.parentNode.parentNode
        let resourceId = tr.childNodes[0].textContent
        deleteData(resourceUrl + '/' + resourceId, null)
            .then((data) => {
                console.log(data);
                tr.remove()
            });
    }
}

// При изменении одного поля, блокировать изменения для других полей
const changeTableValueHandler = (e) => { // Написать обработчик не для tr, а для td
    let line = e.currentTarget.parentNode

    let tdCode = line.childNodes[0]
    let tdName = line.childNodes[1]
    let tdType = line.childNodes[2]
    let tdUnits = line.childNodes[3]
    let tdOperation = line.childNodes[4]


    let oldName = tdName.textContent
    let oldType = tdType.textContent
    let oldUnits = tdUnits.textContent

    line.oldName = oldName
    line.oldType = oldType
    line.oldUnits = oldUnits

    if (fieldInProcessChanged === false) {
        fieldInProcessChanged = true

        tdOperation.firstChild.remove()

        let inputName = document.createElement('input')
        let inputType = document.createElement('input')
        let inputUnits = document.createElement('input')


        inputName.value = tdName.textContent
        inputType.value = tdType.textContent
        inputUnits.value = tdUnits.textContent

        tdName.textContent = ''
        tdType.textContent = ''
        tdUnits.textContent = ''

        tdName.appendChild(inputName)
        tdType.appendChild(inputType)
        tdUnits.appendChild(inputUnits)
        tdOperation.appendChild(createCancelChangeResourceBtn([oldName, oldType, oldUnits]))

        addChangeTableValueHandler(line)

        getElement('add_resource_tr').remove()
    }
}

function cancelChangeResourceBtnHandler(btn, oldValues) {
    btn.onclick = function () {
        let tr = btn.parentNode.parentNode
        let tds = tr.childNodes

        tds[1].firstChild.remove()
        tds[2].firstChild.remove()
        tds[3].firstChild.remove()

        tds[4].firstChild.remove()
        if (tds[4].firstChild !== null) {
            tds[4].firstChild.remove()
        }

        tds[1].textContent = oldValues[0]
        tds[2].textContent = oldValues[1]
        tds[3].textContent = oldValues[2]
        tds[4].appendChild(createDeleteResourceBtn())


        addChangeTableValueHandler(tr)

        createAndAddResourceTrInTable()
        fieldInProcessChanged = false
    }
}

function addToTableResources(table) {
    getData(resourceUrl).then((response) => {
        let resources = response.resources
        for (let i = 0; i < resources.length; i++) {
            let resource = resources[i]
            let tr = document.createElement('tr')
            tr.innerHTML =
                '<td>' + resource.id + '</td>' +
                '<td>' + resource.name + '</td>' +
                '<td>' + resource.resourceType + '</td>' +
                '<td>' + resource.units + '</td>' +
                '<td><button type="button">Удалить</button></td>'

            let delResourcesBtn = tr.childNodes[4].firstChild
            delResourceBtnHandler(delResourcesBtn)

            addChangeTableValueHandler(tr)

            for (let i = 0; i < tr.childNodes.length; i++) {
                tr.childNodes[i].addEventListener('keydown', changeResourceHandler)
            }
            let lastLine = table.childNodes[i]
            lastLine.after(tr)
        }
    })
}



// document.onclick = function clickPastChangedLine(e) {
//     let errorTd = document.getElementById('error_td')
//     const withinBoundaries = e.composedPath().includes(line);
//
//     if (!withinBoundaries) {
//         if (oldName !== inputName.value || oldType !== inputType.value || oldUnits !== inputUnits.value) {
//             if (errorTd === null) {
//                 line.childNodes[4].insertAdjacentHTML('afterend', '<td id="error_td"><p>Сохраните изменения</p></td>')
//             }
//         } else {
//             if (errorTd !== null) {
//                 errorTd.remove()
//             }
//         }
//     }
// }

// document.onclick = function clickPastChangedLine(e) {
//     let tr = getElement('changed_resource_tr')
//     const withinBoundaries = e.composedPath().includes(tr);
//
//     if (!withinBoundaries) {
//         tr.id = ''
//         let tds = tr.childNodes
//
//         tds[1].firstChild.remove()
//         tds[2].firstChild.remove()
//         tds[3].firstChild.remove()
//
//         tds[4].firstChild.remove()
//         if (tds[4].firstChild !== null) {
//             tds[4].firstChild.remove()
//         }
//
//         tds[1].textContent = tr.oldName
//         tds[2].textContent = tr.oldType
//         tds[3].textContent = tr.oldUnits
//         tds[4].appendChild(createDeleteResourceBtn())
//
//         tds[0].addEventListener('click', changeTableValueHandler)
//         tds[1].addEventListener('click', changeTableValueHandler)
//         tds[2].addEventListener('click', changeTableValueHandler)
//         tds[3].addEventListener('click', changeTableValueHandler)
//         // fieldInProcessChanged = false
//     }
// }