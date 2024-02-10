let issueId = localStorage.getItem('issue_id');
getData(ISSUE_URL + "/" + issueId).then(response => {
    console.log(response)

    let issue = response.issue

    getElement('code').textContent = issue.id
    getElement('created_time').textContent = issue.createdTime
    getElement('beneficiary_fio').textContent = issue.beneficiaryFio
    getElement('warehouse_name').textContent = issue.warehouseName

    let tBody = document.querySelector('#resource_table tbody')

    issue.resources.forEach(resource => {
        let tr = createTr([resource.id, resource.name, resource.count])
        tBody.appendChild(tr)
    })
    localStorage.removeItem('issue_id')
})
