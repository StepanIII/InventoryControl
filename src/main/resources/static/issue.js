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