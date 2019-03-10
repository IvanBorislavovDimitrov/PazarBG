$(document).ready(function () {
    $("#category").empty().append("<option selected disabled>Category</option>");
    $.getJSON("/api/subcategories/all", function (categories) {
        categories.forEach(function (category) {
            $("#category").append("<option>" + category.name + "</option>")
        })
   })
});