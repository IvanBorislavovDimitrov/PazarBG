$(document).ready(() => {

    const titleInput = $("#title");
    const titleError = $("#title-error");

    const shipmentInput = $("#shipment");
    const shipmentError = $("#shipment-error");

    const priceInput = $("#price");
    const priceError = $("#price-error");

    const stateInput = $("#state");
    const stateError = $("#state-error");

    const categoryInput = $("#category");
    const categoryError = $("#category-error");

    const subcategoryInput = $("#subcategory");
    const subCategoryError = $("#subcategory-error");

    let isTitleValid = titleInput.val() !== '';
    let isValidShipment = shipmentInput.val() !== '';
    let isPriceValid = priceInput.val() !== '';
    let isValidState = stateInput.val() !== '';
    let isCategoryValid = categoryInput.val() !== '';
    let isSubcategoryValid = subcategoryInput.val() !== '';

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

    if (!shipmentInput.val()) {
        shipmentError.append('Please select a shipment');
    } else {
        shipmentError.empty();
    }
    shipmentInput.on('input', () => {
        isValidShipment = true;
        shipmentError.empty();
    });


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


    if (!stateInput.val()) {
        stateError.append('Please select a shipment');
    } else {
        stateError.empty();
    }
    stateInput.on('input', () => {
        isValidState = true;
        stateError.empty();
    });


    if (!categoryInput.val()) {
        categoryError.append('Please select a category');
    } else {
        categoryError.empty();
    }
    categoryInput.on('input', () => {
        isCategoryValid = true;
        categoryError.empty();
    });


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