const getElement = (id) => {
    return document.getElementById(id)
}

const createElement = (name) => {
    return document.createElement(name)
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
        } else if (typeof element === 'string') {
            td.textContent = element
        } else {
            console.error('Невозможно добавить элемент или значение в td. Тип данных : ' + typeof element)
        }
        tr.appendChild(td)
    }
    return tr
}

const deleteElementArray = (arr, element) => {
  let id = arr.indexOf(element)
    if (id === -1) {
        console.error('Такого значения нет в массиве.')
    } else {
        arr.splice(id, 1)
    }
}