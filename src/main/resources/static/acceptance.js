
// Приемка
function acceptanceResourcesBtnHandler() {
    let content = getElement('content')
    removeChildNodes(content)
    let acceptanceTable = createAcceptanceTable();
    content.appendChild(acceptanceTable)

    getData(acceptanceUrl)
        .then((response) => {
            let acceptance = response.acceptance
            for (let i = 0; i < acceptance.length; i++) {
                let accept = acceptance[i]
                let tr = createTr([accept.id.toString(), accept.createdTime, accept.warehouse, accept.benefactor])
                acceptanceTable.appendChild(tr)
            }
        })
}

function createAcceptanceTable() {
    return createTable(
        'acceptance_resources_table',
        ['№', 'Время', 'Место хранения', 'Благодетель'])
}