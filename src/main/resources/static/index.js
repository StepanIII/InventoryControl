//Управление ресурсами
function handleResourceManagementBtn() {
    getElement('subheader_second').innerHTML =
        '<button type="button" id="resources_btn" onclick="handleResourcesBtn()">Ресурсы</button>\n' +
        '<button type="button" id="acceptance_btn" onclick="handleAcceptanceBtn()">Приемка</button>\n' +
        '<button type="button" id="remainder_resources_btn">Остатки</button>\n' +
        '<button type="button" id="write_off_resources_btn">Списания</button>\n' +
        '<button type="button" id="inventory_resources_btn">Инвентаризация</button>\n' +
        '<button type="button" id="orders_btn">Заказы</button>\n' +
        '<button type="button" id="movements_resources_btn">Внутренние перемещения</button>'
}


function handleResourcesBtn() {
    window.location.replace(uiResourcesAllUrl)
}

function handleAcceptanceBtn() {
    window.location.replace(UI_ACCEPTANCE_ALL_URL)
}