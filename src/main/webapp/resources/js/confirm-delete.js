document.addEventListener("DOMContentLoaded", () => {
    const deleteForms = document.querySelectorAll(".delete-form");

    deleteForms.forEach(form => {
        form.addEventListener("submit", (event) => {
            const restaurantName = form.getAttribute("restaurant-name");
            if (!confirm(`Are you sure you want to delete ${restaurantName}?`)) {
                event.preventDefault();
            }
        });
    });
});
