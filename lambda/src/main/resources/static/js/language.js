document.addEventListener("DOMContentLoaded", function () {

    const languageButton =
        document.getElementById("languageButton");

    const languagePopup =
        document.getElementById("languagePopup");

    const closeLanguagePopup =
        document.getElementById("closeLanguagePopup");

    const selectedLanguage =
        document.getElementById("selectedLanguage");

    const languageOptions =
        document.querySelectorAll(".language-option");


    // ==========================================
    // CURRENT LANGUAGE
    // ==========================================

    const urlParams =
        new URLSearchParams(window.location.search);

    const currentLanguage =
        urlParams.get("lang") || "en";


    const currentOption =
        document.querySelector(
            `.language-option[data-language="${currentLanguage}"]`
        );


    if (currentOption) {

        selectedLanguage.textContent =
            currentOption.getAttribute("data-name");

        setActiveLanguage(currentOption);

    }


    // ==========================================
    // OPEN POPUP
    // ==========================================

    languageButton.addEventListener("click", function (event) {

        event.stopPropagation();

        languagePopup.classList.toggle("hidden");

    });


    // ==========================================
    // CLOSE BUTTON
    // ==========================================

    closeLanguagePopup.addEventListener("click", function () {

        languagePopup.classList.add("hidden");

    });


    // ==========================================
    // SELECT LANGUAGE
    // ==========================================

    languageOptions.forEach(function (option) {

        option.addEventListener("click", function (event) {

            event.stopPropagation();

            const language =
                this.getAttribute("data-language");


            // Save selected language

            localStorage.setItem(
                "selectedLanguage",
                language
            );


            // Current URL

            const url =
                new URL(window.location.href);


            // Change lang parameter

            url.searchParams.set(
                "lang",
                language
            );


            // Reload current page

            window.location.href =
                url.toString();

        });

    });


    // ==========================================
    // ACTIVE LANGUAGE
    // ==========================================

    function setActiveLanguage(option) {

        languageOptions.forEach(function (item) {

            item.classList.remove(
                "border-red-200",
                "bg-red-50"
            );

            item.classList.add(
                "border-gray-200"
            );

        });


        option.classList.remove(
            "border-gray-200"
        );

        option.classList.add(
            "border-red-200",
            "bg-red-50"
        );

    }


    // ==========================================
    // CLICK OUTSIDE
    // ==========================================

    document.addEventListener("click", function (event) {

        if (
            !languagePopup.contains(event.target) &&
            !languageButton.contains(event.target)
        ) {

            languagePopup.classList.add("hidden");

        }

    });

});