function handleAddIssueBtn() {
    window.location.replace(UI_ISSUE_EDIT_URL)
}

getData(ISSUE_URL).then(response => {
    console.log(response)
    let tBody = document.querySelector('#issue_table tbody')
    response.issuance.forEach(issue => {
        let tr = createTr([issue.id, issue.createdTime, issue.warehouseName, issue.beneficiaryFio])
        tr.onclick = () => {
            localStorage.setItem('issue_id', issue.id)
            window.location.replace(UI_ISSUE_SHOW_URL)
        }
        tBody.appendChild(tr)
    })
})