$(document).ready(() => {
    const emailInput = $("#email");
    const emailAddressError = $("#email-address-error");

    const usernameInput = $("#username");
    const usernameError = $("#username-error");

    const phoneNumberInput = $("#phoneNumber");
    const phoneNumberError = $("#phoneNumber-error");

    let isEmailValid = false;
    let isUsernameValid = false;
    let isPhoneNumberValid = false;

    emailInput.on('input', () => {
        const regex = /^[A-Za-z][A-Za-z.0-9]+@([A-Za-z]+(\.)){1,}[A-Za-z0-9]+$/;
        if (!emailInput.val().match(regex)) {
            emailAddressError.empty();
            emailInput.addClass('is-invalid');
            emailAddressError.append("Invalid Email Address!");
            isEmailValid = false;
        } else {
            isEmailValid = true;
            emailInput.removeClass('is-invalid');
            emailAddressError.empty();
        }
    });


    usernameInput.on('input', () => {
        if (usernameInput.val().length < 3) {
            usernameError.empty();
            usernameInput.addClass('is-invalid');
            usernameError.append("Invalid Username. Must be at least 3 symbols!");
            isUsernameValid = false;
        } else {
            usernameError.empty();
            usernameInput.removeClass('is-invalid');
            isUsernameValid = true;
        }
    });

    phoneNumberInput.on('input', () => {
        if (phoneNumberInput.val().match(/^\+\d{3,}$/)) {
            phoneNumberError.empty();
            phoneNumberInput.removeClass('is-invalid');
            isPhoneNumberValid = true;
        } else {
            phoneNumberError.empty();
            phoneNumberInput.addClass('is-invalid');
            phoneNumberError.append("Invalid Phone Number. Must start with + and be more than 3 symbols long!");
            isPhoneNumberValid = false;
        }
    });

    const contact = $("#contact");

    contact.submit((event) => {
        if (isUsernameValid && isEmailValid && isPhoneNumberValid) {
            $(this).unbind('submit').submit();
        } else {
            event.preventDefault();
        }
    });
});