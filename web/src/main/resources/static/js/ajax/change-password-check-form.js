$(document).ready(() => {

    let isOldPasswordValid = false;
    let isNewPasswordValid = false;
    let isConfirmPasswordValid = false;

    const oldPasswordInput = $("#old-password");
    const oldPasswordError = $("#old-password-error");
    oldPasswordInput.on('input', () => {
        if (oldPasswordInput.val().length < 8) {
            oldPasswordError.empty();
            oldPasswordInput.addClass('is-invalid');
            oldPasswordError.append("Invalid Password. Must be at least 8 symbols!");
            isOldPasswordValid = false;
        } else {
            oldPasswordError.empty();
            oldPasswordInput.removeClass('is-invalid');
            isOldPasswordValid = true;
        }
    });

    const passwordsNotMatchError = $("#passwords-do-not-match");

    const newPasswordInput = $("#new-password");
    const newPasswordError = $("#new-password-error");
    newPasswordInput.on('input', () => {
        if (newPasswordInput.val().length < 8) {
            newPasswordError.empty();
            newPasswordInput.addClass('is-invalid');
            newPasswordError.append("Invalid Password. Must be at least 8 symbols!");
            isNewPasswordValid = false;
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
            isNewPasswordValid = true;
        }
    });

    const confirmPasswordInput = $("#confirm-password");
    const confirmPasswordError = $("#confirm-password-error");
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
            console.log(confirmPasswordInput.val());
            console.log(newPasswordInput.val());
        }
    });

    const userForm = $("#user-form");
    userForm.submit((event) => {
        if (isOldPasswordValid && isNewPasswordValid && isConfirmPasswordValid) {
            $(this).unbind('submit').submit();
        } else {
            event.preventDefault();
        }
    });
});