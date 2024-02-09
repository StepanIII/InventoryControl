// document.querySelector('#resources_table tbody')

const getElement = (id) => {
    return document.getElementById(id)
}

const createElement = (name) => {
    return document.createElement(name)
}

const createInput = (type, min, max) => {
    let input = document.createElement('input')
    input.type = type
    input.min = min
    if (max !== null) {
        input.max = max
    }
    return input
}

const createElementWithId = (name, id) => {
    let element = createElement(name)
    element.id = id
    return element
}

const createBtn = (name, id) => {
    let btn = document.createElement('button')
    btn.textContent = name
    btn.id = id
    return btn
}

const createTr = (columns) => {
    let tr = document.createElement('tr')
    for (let i = 0; i < columns.length; i++) {
        let td = document.createElement('td')
        let element = columns[i]
        if (element instanceof Node) {
            td.appendChild(columns[i])
        } else if (typeof element === 'string' || typeof element === 'number') {
            td.textContent = element
        } else {
            console.error('Невозможно добавить элемент или значение "' + element + '" в td. Тип данных : ' + typeof element)
        }
        tr.appendChild(td)
    }
    return tr
}

const createTable = (id, columns) => {
    let table = createElementWithId('table', id)
    let trHeader = createTr(columns)
    table.appendChild(trHeader)
    return table
}


// Удалить все строки, кроме шапки
// const clearTable = (table) => {
//     let trs = table.childNodes
//     for (let i = 0; i < trs.length; i++) {
//         trs[i].remove()
//     }
// }

const clearTable = (tBody) => {
    while (tBody.childNodes.length !== 1) {
        let tr = tBody.firstChild
        console.log(tr)
        if (tr.id !== 'table_header') {
            tr.remove()
        }
    }
}

const clearTBody = (tBody) => {
    while (tBody.hasChildNodes()) {
        tBody.firstChild.remove()
    }
}

const removeChildNodes = (element) => {
    while (element.hasChildNodes()) {
        element.firstChild.remove()
    }
}

const stringIsBlank = (value) => {
    return value === null || value.length === 0
}