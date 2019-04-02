$(document).ready(() => {
    const categorySelect = $('#category');
    const currentCategory = categorySelect.val();
    $.getJSON("/api/categories/all", (categories) => {
        categories.forEach((category) => {
                categorySelect.append(`<option>${category.name}</option>`)
        });
    });
    const subcategorySelect= $('#subcategory');
    categorySelect.change(function () {
        subcategorySelect.empty().append("<option selected disabled>Subcategory</option>");
        let category = categorySelect.val();
        $.getJSON("/api/subcategories/all?category=" + category, function (subcategories) {
            subcategories.forEach(function (subcategory) {
                subcategorySelect.append("<option>" + subcategory.name + "</option>")
            })
        })
    });
});