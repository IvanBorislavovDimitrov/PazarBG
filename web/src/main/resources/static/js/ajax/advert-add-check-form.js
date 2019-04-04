$(document).ready(() => {

    let isTitleValid = false;
    let isValidShipment = false;
    let isPriceValid = false;
    let isValidState = false;
    let isCategoryValid = true;
    let isSubcategoryValid = true;


    const titleInput = $("#title");
    const titleError = $("#title-error");
    titleInput.on('input', () => {
        if (titleInput.val().length < 3) {
            titleError.empty();
            titleInput.addClass('is-invalid');
            titleError.append("Invalid Title!");
            isTitleValid = false;
        } else {
            isTitleValid = true;
            titleInput.removeClass('is-invalid');
            titleError.empty();
        }
    });

    const shipmentInput = $("#shipment");
    const shipmentError = $("#shipment-error");
    if (!shipmentInput.val()) {
        shipmentError.append('Please select a shipment');
    } else {
        shipmentError.empty();
    }
    shipmentInput.on('input', () => {
        isValidShipment = true;
        shipmentError.empty();
    });

    const priceInput = $("#price");
    const priceError = $("#price-error");
    priceInput.on('input', () => {
        if (Number.parseFloat(priceInput.val()) <= 0) {
            priceError.empty();
            priceInput.addClass('is-invalid');
            priceError.append("Invalid price!");
            isPriceValid = false;
        } else {
            priceError.empty();
            priceInput.removeClass('is-invalid');
            isPriceValid = true;
        }
    });

    const stateInput = $("#state");
    const stateError = $("#state-error");
    if (!stateInput.val()) {
        stateError.append('Please select a shipment');
    } else {
        stateError.empty();
    }
    stateInput.on('input', () => {
        isValidState = true;
        stateError.empty();
    });

    const categoryInput = $("#category");
    const categoryError = $("#category-error");
    if (!categoryInput.val()) {
        categoryError.append('Please select a category');
    } else {
        categoryError.empty();
    }
    categoryInput.on('input', () => {
        isCategoryValid = true;
        categoryError.empty();
    });

    const subcategoryInput = $("#subcategory");
    const subCategoryError = $("#subcategory-error");
    if (!subcategoryInput .val()) {
        subCategoryError.append('Please select a subcategory');
    } else {
        subCategoryError.empty();
    }
    subcategoryInput .on('input', () => {
        isSubcategoryValid = true;
        subCategoryError.empty();
    });

    const addNewAdvertForm = $("#form-advert");

    addNewAdvertForm.submit((event) => {
        if (isSubcategoryValid && isCategoryValid && isValidState && isPriceValid && isTitleValid && isValidShipment) {
            $(this).unbind('submit').submit();
        } else {
            event.preventDefault();
        }
    });
});