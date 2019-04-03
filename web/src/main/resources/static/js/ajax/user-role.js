$(document).ready(() => {
    const usernameInput = $('#username');
    const table = $('#users-roles');
    const userRole = $('#users-form');
    usernameInput.on('input', () => {
        const username = usernameInput.val();
        table.empty();

        $.getJSON("/api/users/all?username=" + username, (users) => {
            userRole.empty();
            users.forEach(user => {
                const form = '<form method="get" action="/admin/change-user-role-confirm" id="update_user_role_'
                    + user.username + '">' +
                    '<input type="hidden" name="username" value="'+user.username+'" />'+
                    '</form>\n';
                userRole.append(form);
                const tableRow =
                    '<tr>\n' +
                    '    <td>' + user.username + '</td>\n' +
                    '    <td>' + getHTML('ADMIN', user.roles.includes('ROLE_ADMIN')) + '</td>\n' +
                    '    <td>' + getHTML('MODERATOR', user.roles.includes('ROLE_MODERATOR')) + '</td>\n' +
                    '    <td>' + getHTML('USER', user.roles.includes('ROLE_USER')) + '</td>\n' +
                    '    <td>' + '<input form="update_user_role_' + user.username + '" type="submit" class="btn btn-danger" value="Update" />' + '</td>\n' +
                    '</tr>';
                table.append(tableRow);
            });
        });
    });
});

function getHTML(role, isValid) {
    if (isValid) {
        return '<div class="form-check">\n' +
            '<label class="form-check-label">\n' +
            '<input disabled class="form-check-input" type="checkbox" name="' + role + '" checked>\n' +
            role +
            '</label>\n</div>'
    }

    return '<div class="form-check">\n' +
        '<label class="form-check-label">\n' +
        '<input disabled class="form-check-input" type="checkbox" name="' + role + '">\n' +
        role +
        '</label>\n</div>'
}