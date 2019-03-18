$(document).ready(() => {
    const usernameInput = $('#username');
    const table = $('#users-roles');
    const userRole = $('#users-form');
    usernameInput.keypress(() => {
        const username = usernameInput.val();
        table.empty();
        $.getJSON("/api/users/all?username=" + username, (users) => {
            userRole.empty();
            users.forEach(user => {
                userRole.append('<form method="post" th:action="@{/admin/change-user-role}" id="update_user_role_'
                    + user.username + '"></form>\n');
                const tableRow =
                    '<tr>\n' +
                    '    <td>' + user.username + '</td>\n' +
                    '    <td>' + getHTML('ADMIN', user.roles.includes('ROLE_ADMIN'), user.username) + '</td>\n' +
                    '    <td>' + getHTML('MODERATOR', user.roles.includes('ROLE_MODERATOR'), user.username) + '</td>\n' +
                    '    <td>' + getHTML('USER', user.roles.includes('ROLE_USER'), user.username) + '</td>\n' +
                    '    <td>' + '<input form="update_user_role_' + user.username + '" type="submit" class="btn btn-danger" value="Upadte" />' + '</td>\n' +
                    '</tr>';
                table.append(tableRow);
            });
        });
    });
});

function getHTML(role, isValid, username) {
    if (isValid) {
        return '<div class="form-check">\n' +
            '<label class="form-check-label">\n' +
            '<input form="update_user_role_' + username + '" class="form-check-input" type="checkbox" name="' + role + '" checked>\n' +
            role +
            '</label>\n</div>'
    }

    return '<div class="form-check">\n' +
        '<label class="form-check-label">\n' +
        '<input form="update_user_role_' + username + '" class="form-check-input" type="checkbox" name="' + role + '">\n' +
        role +
        '</label>\n</div>'
}