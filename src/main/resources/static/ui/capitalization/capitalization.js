function handleAddCapitalizationBtn() {
    window.location.replace(UI_CAPITALIZATION_EDIT_URL)
}

getData(CAPITALIZATION_URL).then(response => {
    console.log(response)
    let tBody = document.querySelector('#capitalization_table tbody')
    response.capitalization.forEach(capitalization => {
        let tr = createTr([capitalization.id, capitalization.createdTime, capitalization.warehouseName])
        tr.onclick = () => {
            localStorage.setItem('capitalization_id', capitalization.id)
            window.location.replace(UI_CAPITALIZATION_SHOW_URL)
        }
        tBody.appendChild(tr)
    })
})