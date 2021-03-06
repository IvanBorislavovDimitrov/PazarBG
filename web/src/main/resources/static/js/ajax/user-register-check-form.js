$(document).ready(() => {

    const emailInput = $("#email");
    const emailAddressError = $("#email-address-error");

    const usernameInput = $("#username");
    const usernameError = $("#username-error");

    const newPasswordInput = $("#password");
    const newPasswordError = $("#password-error");

    const confirmPasswordInput = $("#confirmPassword");
    const confirmPasswordError = $("#confirmPassword-error");

    const firstNameInput = $("#firstName");
    const firstNameError = $("#firstName-error");

    const lastNameInput = $("#lastName");
    const lastNameError = $("#lastName-error");

    const phoneNumberInput = $("#phoneNumber");
    const phoneNumberError = $("#phoneNumber-error");

    const regionInput = $("#regions");
    const regionError = $("#region-error");

    const townInput = $("#towns");
    const townError = $("#town-error");

    let isEmailValid = emailInput.val() !== '';
    let isUsernameValid = usernameInput.val() !== '';
    let isPasswordValid = newPasswordInput !== '';
    let isConfirmPasswordValid = confirmPasswordInput.val() !== '';
    let isFirstNameValid = firstNameInput.val() !== '';
    let isLastNameValid = lastNameInput.val() !== '';
    let isPhoneNumberValid = phoneNumberInput.val() !== '';
    let isValidRegion = regionInput.val() !== '';
    let isValidTown = townInput.val() !== '';

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

    const passwordsNotMatchError = $("#passwords-do-not-match");


    newPasswordInput.on('input', () => {
        if (newPasswordInput.val().length < 8) {
            newPasswordError.empty();
            newPasswordInput.addClass('is-invalid');
            newPasswordError.append("Invalid Password. Must be at least 8 symbols!");
            isPasswordValid = false;
            passwordsNotMatchError.empty();
            if (confirmPasswordInput.val() !== newPasswordInput.val()) {
                passwordsNotMatchError.empty();
                passwordsNotMatchError.append("Passwords do not match!");
            } else {
                passwordsNotMatchError.empty();
            }
        } else {
            if (confirmPasswordInput.val() !== newPasswordInput.val()) {
                passwordsNotMatchError.empty();
                passwordsNotMatchError.append("Passwords do not match!");
            } else {
                passwordsNotMatchError.empty();
            }
            newPasswordError.empty();
            newPasswordInput.removeClass('is-invalid');
            isPasswordValid = true;
        }
    });

    confirmPasswordInput.on('input', () => {
        if (confirmPasswordInput.val().length < 8) {
            confirmPasswordError.empty();
            confirmPasswordInput.addClass('is-invalid');
            confirmPasswordError.append("Invalid Password. Must be at least 8 symbols!");
            isConfirmPasswordValid = false;
            passwordsNotMatchError.empty();

            if (confirmPasswordInput.val() !== newPasswordInput.val()) {
                passwordsNotMatchError.empty();
                passwordsNotMatchError.append("Passwords do not match!");
            } else {
                passwordsNotMatchError.empty();
            }
        } else {
            if (confirmPasswordInput.val() !== newPasswordInput.val()) {
                passwordsNotMatchError.empty();
                passwordsNotMatchError.append("Passwords do not match!");
            } else {
                passwordsNotMatchError.empty();
            }
            confirmPasswordError.empty();
            confirmPasswordInput.removeClass('is-invalid');
            isConfirmPasswordValid = true;
        }
    });


    firstNameInput.on('input', () => {
        if (firstNameInput.val().length < 3) {
            firstNameError.empty();
            firstNameInput.addClass('is-invalid');
            firstNameError.append("Invalid First Name. Must be at least 3 symbols!");
            isFirstNameValid = false;
        } else {
            firstNameError.empty();
            firstNameInput.removeClass('is-invalid');
            isFirstNameValid = true;
        }
    });


    lastNameInput.on('input', () => {
        if (lastNameInput.val().length < 3) {
            lastNameError.empty();
            lastNameInput.addClass('is-invalid');
            lastNameError.append("Invalid Last Name. Must be at least 3 symbols!");
            isLastNameValid = false;
        } else {
            lastNameError.empty();
            lastNameInput.removeClass('is-invalid');
            isLastNameValid = true;
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

    console.log(regionInput.val());
    if (regionInput.val() === null) {
        regionError.append('Please select a region');
    } else {
        regionError.empty();
    }
    regionInput.on('input', () => {
        isValidRegion = true;
        regionError.empty();
    });

    console.log(townInput.val());
    if (townInput.val() === null) {
        townError.append('Please select a region');
    } else {
        townError.empty();
    }
    townInput.on('input', () => {
        isValidTown = true;
        townError.empty();
    });

    const registerUserForm = $("#user-form");

    registerUserForm.submit((event) => {
        if (isUsernameValid && isEmailValid && isPasswordValid && isPhoneNumberValid && isConfirmPasswordValid && isFirstNameValid
            && isLastNameValid && isValidRegion && isValidTown) {
            $(this).unbind('submit').submit();
        } else {
            event.preventDefault();
        }
    });
});