$(document).ready(() => {
    const usernameInput = $('#username');
    const table = $('#users-roles');
    usernameInput.keypress(() => {
        const username = usernameInput.val();
        table.empty();
        $.getJSON("/api/users/all?username=" + username, (users) => {
            users.forEach(user => {
                const tableRow =
                    '<tr>\n' +
                    '    <td>' + user.username + '</td>\n' +
                    '    <td>' + getHTML('ADMIN', user.roles.includes('ROLE_ADMIN')) + '</td>\n' +
                    '    <td>' + getHTML('MODERATOR', user.roles.includes('ROLE_MODERATOR')) + '</td>\n' +
                    '    <td>' + getHTML('USER', user.roles.includes('ROLE_USER')) + '</td>\n' +
                    '    <td>' + '<input type="submit" class="btn btn-danger" value="Upadte" />' + '</td>\n' +
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
            '<input class="form-check-input" type="checkbox" name="admin" checked>\n' +
            role +
            '</label>\n</div>'
    }

    return '<div class="form-check">\n' +
        '<label class="form-check-label">\n' +
        '<input class="form-check-input" type="checkbox" name="admin">\n' +
        role +
        '</label>\n</div>'
}