$(document).ready(function () {
    const r = $("#region");
    r.append('<option selected="selected" disabled="disabled">Town</option>');
    $.getJSON("/api/regions/all", function (regions) {
        regions.forEach(function (region) {
            r.append("<option>" + region.name + "</option>")
        })
    });
});