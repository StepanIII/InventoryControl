function handleAddIssueBtn() {
    window.location.replace(UI_ISSUE_EDIT_URL)
}

getData(ISSUE_URL).then(response => {
    console.log(response)
    let tBody = document.querySelector('#issue_table tbody')
    response.issuance.forEach(issue => {
        let tr = createTr([issue.id, issue.createdTime, issue.warehouseName, issue.beneficiaryFio])
        mouseOnTrHandler(tr)
        trHandler(tr, issue.id)
        tBody.appendChild(tr)
    })
})

function trHandler(tr, issueId) {
    tr.onclick = () => {
        getData(ISSUE_URL + "/" + issueId).then(response => {
            console.log(response)
            return response.issue
        }).then(issue => {
            getElement('show_number_issue').value = issue.id
            getElement('show_time_issue').value = issue.createdTime
            getElement('show_warehouse_issue').value = issue.warehouseName
            getElement('show_beneficiary_issue').value = issue.beneficiaryFio

            let tBody = document.querySelector('#issue_resource_table tbody')
            clearTBody(tBody)
            issue.resources.forEach(resource => {
                let size = ''
                if (!stringIsBlank(resource.size)) {
                    size = resource.size
                }
                let tr = createTr([resource.id, resource.name, size, resource.count, resource.unit])
                tBody.appendChild(tr)
            })
        })

        showModal('show_modal')
    }
}

function handleFilter() {
    let fromDateString = getElement("from_date").value
    let toDateString = getElement("to_date").value

    let fromDate = new Date(fromDateString).getTime()
    let toDate = new Date(toDateString).getTime()

    if (stringIsBlank(fromDateString) || stringIsBlank(toDateString)) {
        getElement('error_desc').textContent = 'Укажите даты для фильтрации данных.'
        showModal('error_modal')
    } else if (fromDate > toDate) {
        getElement('error_desc').textContent = 'Дата начала фильтрации больше даты конца фильтрации.'
        showModal('error_modal')
    } else {
        getData(ISSUE_URL).then(response => {
            console.log(response)
            let tBody = document.querySelector('#issue_table tbody')
            clearTBody(tBody)
            response.issuance
                .filter(issue => (new Date(issue.createdTime).getTime() > fromDate) && (new Date(issue.createdTime).getTime() < toDate))
                .forEach(issue => {
                    let tr = createTr([issue.id, issue.createdTime, issue.warehouseName, issue.beneficiaryFio])
                    mouseOnTrHandler(tr)
                    trHandler(tr, issue.id)
                    tBody.appendChild(tr)
                })
        })
    }
}

function handleResetFilter() {
    getElement("from_date").value = ''
    getElement("to_date").value = ''

    getData(ISSUE_URL).then(response => {
        console.log(response)
        let tBody = document.querySelector('#issue_table tbody')
        response.issuance.forEach(issue => {
            let tr = createTr([issue.id, issue.createdTime, issue.warehouseName, issue.beneficiaryFio])
            mouseOnTrHandler(tr)
            trHandler(tr, issue.id)
            tBody.appendChild(tr)
        })
    })
}