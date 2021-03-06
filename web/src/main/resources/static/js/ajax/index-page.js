$(document).ready(function () {
    $.getJSON("/api/adverts/best-recent", function (adverts) {
        const bestRecentAdverts = $('#best-recent-adverts');
        adverts.forEach((advert) => {
            bestRecentAdverts.append(`<div class="col-lg-4 mt-3" style="height: 362px;">
                    <div class="card mt-3" style="height: 362px;">
                        <a href="/adverts/${advert.id}"><img class="card-img-top" style="max-height: 150px" src="/content/${advert.picture}" alt=""></a>
                        <div class="card-body" >
                            <h4 class="card-title">
                                <a href="/adverts/${advert.id}">${advert.title}</a>
                            </h4>
                            <h5>$${advert.price}</h5>
                            <p class="card-text">${advert.description}</p>
                        </div>
                        <div class="card-footer">
                            <small class="text-muted">
                            <a href="/users/other-user-profile?username=${advert.userUsername}">${advert.userUsername}</a></small>
                        </div>
                    </div>
                </div>`)
        })
    })
});