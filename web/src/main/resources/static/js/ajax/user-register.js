$(document).ready(function () {
    $.getJSON("/api/regions/all", function (regions) {
        regions.forEach(function (region) {
            $("#regions").append("<option>" + region.name + "</option>")
        })
    });

    $("#regions").change(function () {
        $("#towns").empty().append("<option selected disabled>Town</option>");
        var region = $("#regions").val();
        $.getJSON("/api/towns/all?region=" + region, function (towns) {
            towns.forEach(function (town) {
                $("#towns").append("<option>" + town.name + "</option>")
            })
        })
    });
});

