$(document).ready(() => {
    const reviews = $('#reviews');
    const pathname = window.location.pathname;
    const splits = pathname.split(/\//);
    const advertId = splits[splits.length - 1];
    $.getJSON("/api/reviews/all?advertId=" + advertId, function (adverts) {
        adverts.forEach(function (advert) {
            reviews.append(`<p>${advert.text}</p>
                        <small class="text-muted">Posted by ${advert.username} on ${advert.addedOn}</small>
                        <hr>`);
        });
    });
});