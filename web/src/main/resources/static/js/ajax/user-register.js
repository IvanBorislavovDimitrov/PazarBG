$(document).ready(function () {
    const regionsField = $("#regions");
    const currentRegion = regionsField.val();
    $.getJSON("/api/regions/all", function (regions) {
        regions.forEach(function (region) {
            if (currentRegion !== region.name) {
                regionsField.append("<option>" + region.name + "</option>");
            }
        })
    });

    regionsField.change(function () {
        $("#towns").empty().append("<option selected disabled>Town</option>");
        let region = $("#regions").val();
        $.getJSON("/api/towns/all?region=" + region, function (towns) {
            towns.forEach(function (town) {
                $("#towns").append("<option>" + town.name + "</option>");
            })
        })
    });
});

