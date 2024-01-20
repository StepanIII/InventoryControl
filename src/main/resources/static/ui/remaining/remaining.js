fillSubheaderSecond()

getData(REMAINING_URL).then(response => {
    let tBody = document.querySelector('#remaining_table tbody')
    let remaining = response.remaining
    for (let i = 0; i < remaining.length; i++) {
        let remain = remaining[i]
        let tr = createTr([remain.resourceId, remain.resourceName, remain.count, remain.warehouse])
        // tr.addEventListener('click', handleEditAcceptanceTr)
        tBody.appendChild(tr)
    }
})