$(document).ready(() => {

    let isUsernameValid = false;
    let isPasswordValid = false;

    const usernameInput = $("#username");
    const usernameError = $("#username-error");
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

    const passwordInput = $("#password");
    const passwordError = $("#password-error");
    passwordInput.on('input', () => {
        if (passwordInput.val().length < 8) {
            passwordError.empty();
            passwordInput.addClass('is-invalid');
            passwordError.append("Invalid Password. Must be at least 8 symbols!");
            isPasswordValid = false;
        } else {
            passwordError.empty();
            passwordInput.removeClass('is-invalid');
            isPasswordValid = true;
        }
    });

    const registerUserForm = $("#form-login");
    registerUserForm.submit((event) => {
        if (isUsernameValid && isPasswordValid) {
            $(this).unbind('submit').submit();
        } else {
            event.preventDefault();
        }
    });
});