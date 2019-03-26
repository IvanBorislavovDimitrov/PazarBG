$(document).ready(() => {
    const reviewsSerction = $('#reviews');
    const pathname = window.location.pathname;
    const splits = pathname.split(/\//);
    const advertId = splits[splits.length - 1];
    $.getJSON("/api/reviews/all?advertId=" + advertId, function (reviews) {
        reviews.forEach(function (review) {
            if (review.username === review.loggedUserUsername || review.loggedUserRoles.includes('ROLE_ADMIN') ||
            review.loggedUserRoles.includes('ROLE_MODERATOR')) {

                reviewsSerction.append(`<p><a href='/reviews/edit?reviewId=${review.id}' class="btn btn-danger">Edit</a></p>`);
                reviewsSerction.append(`<p><a href='/reviews/delete?reviewId=${review.id}' class="btn btn-danger">Delete</a></p>`);
            }
            reviewsSerction.append(`<p>${review.text}</p>
                        <small class="text-muted">Posted by ${review.username} on ${review.addedOn}</small>
                        <hr>`);
        });
    });
});