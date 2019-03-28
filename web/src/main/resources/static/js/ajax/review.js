$(document).ready(() => {
    const reviewsSerction = $('#reviews');
    const pathname = window.location.pathname;
    const splits = pathname.split(/\//);
    const advertId = splits[splits.length - 1];
    $.getJSON("/api/reviews/all?advertId=" + advertId, function (reviews) {
        reviews.forEach(function (review) {
            if (review.username === review.loggedUserUsername || review.loggedUserRoles.includes('ROLE_ADMIN') ||
                review.loggedUserRoles.includes('ROLE_MODERATOR')) {

                reviewsSerction.append(`<button type="submit" id="delete" class="btn btn-danger">Delete</button>`);
                $('#delete').on("click", function () {
                    alert("Post sent");
                    $.ajax({
                        type: 'POST',
                        url: `/reviews/delete?reviewId=${review.id}`,
                        headers: {'X-CSRF-TOKEN': _csrf_token},
                        success: function (result) {

                        }
                    });
                });
            }
            reviewsSerction.append(`<p>${review.text}</p>
                        <small class="text-muted">Posted by ${review.username} on ${review.addedOn}</small>
                        <hr>`);
        });
    });
});