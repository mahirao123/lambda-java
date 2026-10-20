
/* =========================================================
   EPAPER NAVIGATION
   Works with <a class="section-btn"> links
   ========================================================= */


document.addEventListener("DOMContentLoaded", function () {

    const pages = Array.from(document.querySelectorAll(".epaper-page"));
    const buttons = Array.from(
        document.querySelectorAll("#sectionNavigation .section-btn")
    );

    const previousButton = document.getElementById("previousPage");
    const nextButton = document.getElementById("nextPage");
    const pageNumber = document.getElementById("pageNumber");

    // Determine selected subcategory from the current URL.
    const pathParts = window.location.pathname.split("/").filter(Boolean);
    const currentSubCategoryId = pathParts[pathParts.length - 1];

    // Match the active link by its URL, not by index.
    buttons.forEach(button => {
        const linkParts = new URL(button.href, window.location.origin)
            .pathname.split("/")
            .filter(Boolean);

        const linkSubCategoryId = linkParts[linkParts.length - 1];

        button.classList.toggle(
            "active-section",
            linkSubCategoryId === currentSubCategoryId
        );
    });

    // Match the selected page using its subcategory ID.
    let currentPage = pages.findIndex(page =>
        page.dataset.subcategoryId === currentSubCategoryId
    );

    // Fall back to the server-rendered active page.
    if (currentPage < 0) {
        currentPage = pages.findIndex(page =>
            page.classList.contains("active-page")
        );
    }

    if (currentPage < 0) currentPage = 0;

    function showPage(index) {
        if (!pages.length) return;

        currentPage = (index + pages.length) % pages.length;

        pages.forEach((page, i) => {
            page.classList.toggle("hidden", i !== currentPage);
            page.classList.toggle("active-page", i === currentPage);
        });

        if (pageNumber) {
            pageNumber.textContent = `${currentPage + 1} / ${pages.length}`;
        }
    }

    if (nextButton) {
        nextButton.addEventListener("click", () => showPage(currentPage + 1));
    }

    if (previousButton) {
        previousButton.addEventListener("click", () => showPage(currentPage - 1));
    }

    document.addEventListener("keydown", function (event) {
        if (
            ["INPUT", "TEXTAREA", "SELECT"].includes(event.target.tagName) ||
            event.target.isContentEditable
        ) return;

        if (event.key === "ArrowRight") showPage(currentPage + 1);
        if (event.key === "ArrowLeft") showPage(currentPage - 1);
    });

    if (pages.length) showPage(currentPage);
});


/* =========================================================
   ADVERTISEMENT VIDEO SOUND
   ========================================================= */

function toggleAdSound(button) {

    const wrapper = button.closest(".paper-ad-video-wrapper");

    if (!wrapper) {
        return;
    }

    const video = wrapper.querySelector("video");
    const icon = button.querySelector("i");

    if (!video || !icon) {
        return;
    }

    video.muted = !video.muted;

    if (video.muted) {

        icon.classList.remove("fa-volume-high");
        icon.classList.add("fa-volume-xmark");

        button.setAttribute(
            "aria-label",
            "Unmute advertisement"
        );

    } else {

        icon.classList.remove("fa-volume-xmark");
        icon.classList.add("fa-volume-high");

        button.setAttribute(
            "aria-label",
            "Mute advertisement"
        );
    }

    if (video.paused) {
        video.play().catch(error => {
            console.error("Video playback failed:", error);
        });
    }
}
