// Вынести этот метод отднльно для всех страниц ресурсов, а не добавлять на каждую страницу отдельно
function handleResourceManagementBtn() {
    fillSubheaderSecond()
}

function fillSubheaderSecond() {
    getElement('subheader_second').innerHTML =
        '<button type="button" id="resources_btn" onclick="handleResourcesBtn()">Ресурсы</button>\n' +
        '<button type="button" id="acceptance_btn" onclick="handleAcceptanceBtn()">Приемка</button>\n' +
        '<button type="button" id="write_off_btn" onclick="handleWriteOffBtn()">Списания</button>\n' +
        '<button type="button" id="remaining_btn" onclick="handleRemainingBtn()">Остатки</button>\n' +
        '<button type="button" id="inventory_resources_btn">Инвентаризация</button>\n' +
        '<button type="button" id="orders_btn">Заказы</button>\n' +
        '<button type="button" id="movements_resources_btn">Внутренние перемещения</button>'
}


function handleResourcesBtn() {
    window.location.replace(UI_RESOURCES_ALL_URL)
}

function handleAcceptanceBtn() {
    window.location.replace(UI_ACCEPTANCE_ALL_URL)
}

function handleWriteOffBtn() {
    window.location.replace(UI_WRITE_OFF_ALL_URL)
}

function handleRemainingBtn() {
    window.location.replace(UI_REMAINING_ALL_URL)
}