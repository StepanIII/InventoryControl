getElement('resources_btn').onclick = () => window.location.replace(uiResourcesAllUrl)
getElement('acceptance_btn').onclick = () => window.location.replace(uiAcceptanceAllUrl)

getElement('add_acceptance_btn').onclick = () => window.location.replace(uiAcceptanceEditUrl)

getData(acceptanceUrl).then((response) => {
    let table = getElement('acceptance_table')
    let acceptance = response.acceptance
    for (let i = 0; i < acceptance.length; i++) {
        let accept = acceptance[i]
        let tr = createTr([accept.id.toString(), accept.createdTime, accept.warehouse, accept.benefactor])
        table.appendChild(tr)
    }
})