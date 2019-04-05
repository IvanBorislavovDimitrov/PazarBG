$(document).ready(() => {
    const reviewsSerction = $('#reviews');
    const pathname = window.location.pathname;
    const splits = pathname.split(/\//);
    const advertId = splits[splits.length - 1];
    $.getJSON("/api/reviews/all?advertId=" + advertId, function (reviews) {
        reviews.forEach(function (review) {
            if (review.username === review.loggedUserUsername || review.loggedUserRoles.includes('ROLE_ADMIN') ||
                review.loggedUserRoles.includes('ROLE_MODERATOR') || review.loggedUserRoles.includes('ROLE_ROOT')) {

                const myValue = ("button" + review.id);
                reviewsSerction.append(`<button type="submit" id="${myValue}" class="btn btn-danger md-3">Delete</button>`);
                $('#' + myValue).on("click", function () {
                    let c = confirm("Post sent");
                    if (c) {
                        $.ajax({
                            type: 'POST',
                            url: `/api/reviews/delete?reviewId=${review.id}`,
                            headers: {
                                'X-CSRF-TOKEN': _csrf_token,
                                'CONTENT-TYPE': 'application/json;charset=UTF-8'
                            },
                            success: function (result) {
                                window.location.replace(window.location.href);
                            }
                        });
                    }
                });
            }
            reviewsSerction.append(`<p>${review.text}</p>
                        <small class="text-muted">Posted by ${review.username} on ${review.addedOn}</small>
                        <hr>`);
        });
    });
});