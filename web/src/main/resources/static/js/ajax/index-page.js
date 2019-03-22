$(document).ready(function () {
    $.getJSON("/api/adverts/best-recent", function (adverts) {
        const bestRecentAdverts = $('#best-recent-adverts');
        adverts.forEach((advert) => {
            bestRecentAdverts.append(`<div class="col-lg-4 col-md-6 mb-4">
                    <div class="card h-100">
                        <a href="/adverts/${advert.id}"><img class="card-img-top" src="/content/${advert.picture}" alt=""></a>
                        <div class="card-body">
                            <h4 class="card-title">
                                <a href="#">${advert.title}</a>
                            </h4>
                            <h5>$${advert.price}</h5>
                            <p class="card-text">${advert.description}</p>
                        </div>
                        <div class="card-footer">
                            <small class="text-muted">&#9733; &#9733; &#9733; &#9733; &#9734;</small>
                        </div>
                    </div>
                </div>`)
        })
    })
});