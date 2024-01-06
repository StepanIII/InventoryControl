//Управление ресурсами
getElement('resource_management_btn').onclick = () => {
    document.getElementById('subheader_second').innerHTML =
        '<button type="button" id="resources_btn">Ресурсы</button>\n' +
        '<button type="button" id="acceptance_btn">Приемка</button>\n' +
        '<button type="button" id="remainder_resources_btn">Остатки</button>\n' +
        '<button type="button" id="write_off_resources_btn">Списания</button>\n' +
        '<button type="button" id="inventory_resources_btn">Инвентаризация</button>\n' +
        '<button type="button" id="orders_btn">Заказы</button>\n' +
        '<button type="button" id="movements_resources_btn">Внутренние перемещения</button>'

    getElement('resources_btn').onclick = () => window.location.replace(uiResourcesAllUrl)
    getElement('acceptance_btn').onclick = () => window.location.replace(uiAcceptanceAllUrl)
}