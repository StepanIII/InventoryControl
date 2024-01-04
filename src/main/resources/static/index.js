
// Написать обработчики для кнопок отдельно
// Добавить кнопку отменить изменения
const clickEvent = 'click'

//Управление ресурсами
getElement('resource_management_btn')
    .addEventListener(clickEvent, function () {
        document.getElementById('subheader_second').innerHTML =
            '<button type="button" id="resources_btn">Ресурсы</button>\n' +
            '<button type="button" id="remainder_resources_btn">Остатки</button>\n' +
            '<button type="button" id="acceptance_resources_btn">Приемка</button>\n' +
            '<button type="button" id="write_off_resources_btn">Списания</button>\n' +
            '<button type="button" id="inventory_resources_btn">Инвентаризация</button>\n' +
            '<button type="button" id="orders_btn">Заказы</button>\n' +
            '<button type="button" id="movements_resources_btn">Внутренние перемещения</button>'

        getElement('resources_btn')
            .addEventListener(clickEvent, resourcesBtnHandler)
    })

//Ресурсы
function resourcesBtnHandler() {
    let resourceTable = createResourceTable()
    getElement('content').appendChild(resourceTable)
    addToTableResources(resourceTable)
}

function createResourceTable() {
    let resourceTable = createElement('table')
    resourceTable.id = 'resources_table'

    let trHeader = createTr(['Код', 'Наименование', 'Тип', 'Еденицы измерения', 'Операция'])
    let addResourceTr = createTr([
        '',
        createElement('input'),
        createElement('input'),
        createElement('input'),
        createSaveResourceBtn()])

    resourceTable.appendChild(trHeader)
    resourceTable.appendChild(addResourceTr)
    return resourceTable
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

const changeResourceHandler = (e) => {
    let btn = getElement('save_changes_resource_btn')
    if (btn === null) {
        btn = createSaveChangesResourceBtn()
        e.currentTarget.parentNode.childNodes[4].appendChild(btn)
    }
}

function saveChangesResourceBtnHandler(e) {
    let thisBtn = e.currentTarget
    let tr = thisBtn.parentNode
    let tds = tr.parentNode.childNodes
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
            tds[4].appendChild(createDeleteResourceBtn())

            // tr.removeEventListener('click', changeTableHandler, false)
        })
}

function saveResourcesBtnHandler(saveResourcesBtn) {
    saveResourcesBtn.onclick = function() {
        let oldSaveResourceBtn = getElement('save_resource_btn')
        let tr = oldSaveResourceBtn.parentElement.parentElement

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
            tr.insertAdjacentHTML('afterend',
                '<tr>' +
                '   <td></td>' +
                '   <td><input type="text"></td>' +
                '   <td><input type="text"></td>' +
                '   <td><input type="text"></td>' +
                '   <td><button type="button" id="save_resource_btn">Сохранить</button></td>' +
                '</tr>')

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
    let line = e.currentTarget
    let tdName = line.childNodes[1]
    let tdType = line.childNodes[2]
    let tdUnits = line.childNodes[3]

    let oldName = tdName.textContent
    let oldType = tdType.textContent
    let oldUnits = tdUnits.textContent

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

    line.childNodes[4].firstChild.remove()

    document.onclick = function clickPastChangedLine(e) {
        const clickSaveBtn = e.composedPath().includes(line.childNodes[4].firstChild)
        let errorTd = document.getElementById('error_td')
        if (clickSaveBtn) {
            if (errorTd !== null) {
                errorTd.remove()
            }
            document.removeEventListener('click', clickPastChangedLine)
        }

        const withinBoundaries = e.composedPath().includes(line);

        if (!withinBoundaries) {
            if (oldName !== inputName.value || oldType !== inputType.value || oldUnits !== inputUnits.value) {
                if (errorTd === null) {
                    line.childNodes[4].insertAdjacentHTML('afterend', '<td id="error_td"><p>Сохраните изменения</p></td>')
                }
            } else {
                if (errorTd !== null) {
                    errorTd.remove()
                }
                inputName.remove()
                inputType.remove()
                inputUnits.remove()

                tdName.textContent = inputName.value
                tdType.textContent = inputType.value
                tdUnits.textContent = inputUnits.value
                line.addEventListener('click', changeTableValueHandler, false)

                line.childNodes[4].appendChild(createDeleteResourceBtn())
            }
        }
    }

    line.removeEventListener('click', changeTableValueHandler, false)
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
            tr.addEventListener('click', changeTableValueHandler)
            for (let i = 0; i < tr.childNodes.length; i++) {
                tr.childNodes[i].addEventListener('keydown', changeResourceHandler, false)
            }
            let lastLine = table.childNodes[i]
            lastLine.after(tr)
        }
    })
}
