$(document).ready(function () {
    $.getJSON("api/categories/all", function (categories) {
        categories.forEach(function (category) {
            $("#category-list").append("<a href=\"#\" class=\"list-group-item\">"
                + "<img id=\"category-img\" src=\"data:image/png;base64," + category.picture + "\">" + " " + category.name + "</a>"
            )
        })
    })
});