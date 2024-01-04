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