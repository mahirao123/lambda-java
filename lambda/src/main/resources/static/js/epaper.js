/* =========================================================
   Commun Fragment
   ========================================================= */





document.addEventListener("DOMContentLoaded", function () {


    const pages =
        Array.from(
            document.querySelectorAll(".epaper-page")
        );


    const buttons =
        Array.from(
            document.querySelectorAll(".section-btn")
        );


    const previousButton =
        document.getElementById("previousPage");


    const nextButton =
        document.getElementById("nextPage");


    const pageNumber =
        document.getElementById("pageNumber");


    let currentPage = 0;


    // =====================================================
    // SHOW PAGE
    // =====================================================

    function showPage(index) {


        if (pages.length === 0) {
            return;
        }


        if (index < 0) {
            index = pages.length - 1;
        }


        if (index >= pages.length) {
            index = 0;
        }


        currentPage = index;


        // Hide all pages

        pages.forEach(function (page) {

            page.classList.add("hidden");
            page.classList.remove("active-page");

        });


        // Show selected page

        pages[currentPage].classList.remove("hidden");
        pages[currentPage].classList.add("active-page");


        // Update navigation

        buttons.forEach(function (button, buttonIndex) {

            button.classList.remove(
                "active-section"
            );

            if (buttonIndex === currentPage) {

                button.classList.add(
                    "active-section"
                );

                // Scroll selected section into view

                button.scrollIntoView({
                    behavior: "smooth",
                    block: "nearest",
                    inline: "center"
                });
            }

        });


        // Page number

        pageNumber.textContent =
            (currentPage + 1) +
            " / " +
            pages.length;


        // Scroll top

        window.scrollTo({
            top: 0,
            behavior: "smooth"
        });

    }


    // =====================================================
    // NEXT
    // =====================================================

    nextButton.addEventListener(
        "click",
        function () {

            showPage(currentPage + 1);

        }
    );


    // =====================================================
    // PREVIOUS
    // =====================================================

    previousButton.addEventListener(
        "click",
        function () {

            showPage(currentPage - 1);

        }
    );


    // =====================================================
    // SECTION BUTTON
    // =====================================================

    buttons.forEach(function (button) {

        button.addEventListener(
            "click",
            function () {

                const index =
                    Number(
                        this.dataset.slide
                    );

                showPage(index);

            }
        );

    });


    // =====================================================
    // KEYBOARD NAVIGATION
    // =====================================================

    document.addEventListener(
        "keydown",
        function (event) {

            if (event.key === "ArrowRight") {

                showPage(currentPage + 1);

            }


            if (event.key === "ArrowLeft") {

                showPage(currentPage - 1);

            }

        }
    );


    // =====================================================
    // INITIAL PAGE
    // =====================================================

    showPage(0);

});



