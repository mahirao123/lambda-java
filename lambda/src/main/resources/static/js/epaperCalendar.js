
document.addEventListener("DOMContentLoaded", function () {

    // =====================================================
    // ELEMENTS
    // =====================================================

    const calendarButton =
        document.getElementById("calendarButton");

    const calendarPopup =
        document.getElementById("calendarPopup");

    const calendar =
        document.getElementById("calendar");

    const monthTitle =
        document.getElementById("monthTitle");

    const previousMonthButton =
        document.getElementById("previousMonth");

    const nextMonthButton =
        document.getElementById("nextMonth");

    const calendarButtonDate =
        document.getElementById("calendarButtonDate");

    const calendarButtonDay =
        document.getElementById("calendarButtonDay");


    // =====================================================
    // CATEGORY DATA
    // =====================================================

    const categoryData = {};

    document
        .querySelectorAll(".category-data")
        .forEach(function (element) {

            const date =
                element.getAttribute("data-date");

            const categoryId =
                element.getAttribute("data-category-id");

            if (date && categoryId) {

                categoryData[date] = categoryId;

            }

        });


    console.log("E-Paper category data:", categoryData);


    // =====================================================
    // CURRENT DATE
    // =====================================================

    const today = new Date();

    let currentMonth =
        today.getMonth();

    let currentYear =
        today.getFullYear();


    // =====================================================
    // FORMAT DATE
    // =====================================================

    function formatDate(date) {

        const year =
            date.getFullYear();

        const month =
            String(date.getMonth() + 1)
                .padStart(2, "0");

        const day =
            String(date.getDate())
                .padStart(2, "0");

        return `${year}-${month}-${day}`;
    }


    // =====================================================
    // BUTTON DATE
    // =====================================================

    function updateCalendarButton() {

        const optionsDate = {
            day: "2-digit",
            month: "short",
            year: "numeric"
        };

        const optionsDay = {
            weekday: "long"
        };

        calendarButtonDate.textContent =
            today.toLocaleDateString(
                "en-IN",
                optionsDate
            );

        calendarButtonDay.textContent =
            today.toLocaleDateString(
                "en-IN",
                optionsDay
            );

    }


    updateCalendarButton();


    // =====================================================
    // RENDER CALENDAR
    // =====================================================

    function renderCalendar() {

        calendar.innerHTML = "";


        // MONTH TITLE

        const monthName =
            new Date(
                currentYear,
                currentMonth,
                1
            ).toLocaleDateString(
                "en-IN",
                {
                    month: "long",
                    year: "numeric"
                }
            );

        monthTitle.textContent =
            monthName;


        // FIRST DAY

        const firstDay =
            new Date(
                currentYear,
                currentMonth,
                1
            ).getDay();


        // NUMBER OF DAYS

        const daysInMonth =
            new Date(
                currentYear,
                currentMonth + 1,
                0
            ).getDate();


        // EMPTY CELLS

        for (let i = 0; i < firstDay; i++) {

            const empty =
                document.createElement("div");

            empty.className =
                "h-10";

            calendar.appendChild(empty);

        }


        // DAYS

        for (let day = 1; day <= daysInMonth; day++) {

            const date =
                new Date(
                    currentYear,
                    currentMonth,
                    day
                );


            const dateString =
                formatDate(date);


            const button =
                document.createElement("button");


            button.type = "button";


            button.textContent = day;


            // =================================================
            // BASE STYLE
            // =================================================

            button.className =
                "relative h-10 w-full " +
                "rounded-lg " +
                "flex items-center justify-center " +
                "transition";


            // =================================================
            // TODAY
            // =================================================

            const isToday =
                dateString === formatDate(today);


            // =================================================
            // EPAPER AVAILABLE
            // =================================================

            const categoryId =
                categoryData[dateString];


            if (categoryId) {

                // AVAILABLE DATE

                button.classList.add(
                    "font-bold",
                    "text-gray-900",
                    "hover:bg-red-50",
                    "hover:text-red-600"
                );


                // RED DOT

                const dot =
                    document.createElement("span");

                dot.className =
                    "absolute bottom-1 " +
                    "w-1.5 h-1.5 " +
                    "rounded-full bg-red-600";


                button.appendChild(dot);


                // =================================================
                // TODAY + AVAILABLE
                // =================================================

                if (isToday) {

                    button.classList.add(
                        "bg-red-600",
                        "text-white",
                        "hover:bg-red-700"
                    );

                    dot.classList.remove(
                        "bg-red-600"
                    );

                    dot.classList.add(
                        "bg-white"
                    );

                }


                // =================================================
                // CLICK DATE
                // =================================================

                button.addEventListener(
                    "click",
                    function (event) {

                        event.stopPropagation();


                        // Close popup

                        calendarPopup.classList.add(
                            "hidden"
                        );


                        // Redirect immediately

                        window.location.href =
                            "/public/epaper/" +
                            categoryId;

                    }
                );


            } else {

                // =================================================
                // NO EPAPER
                // =================================================

                button.classList.add(
                    "text-gray-300",
                    "cursor-not-allowed"
                );


                button.disabled = true;

            }


            // =================================================
            // TODAY BORDER
            // =================================================

            if (isToday && !categoryId) {

                button.classList.add(
                    "ring-2",
                    "ring-red-300",
                    "text-red-600"
                );

            }


            calendar.appendChild(button);

        }

    }


    // =====================================================
    // OPEN / CLOSE POPUP
    // =====================================================

    calendarButton.addEventListener(
        "click",
        function (event) {

            event.stopPropagation();

            calendarPopup.classList.toggle(
                "hidden"
            );

            if (
                !calendarPopup.classList.contains(
                    "hidden"
                )
            ) {

                renderCalendar();

            }

        }
    );


    // =====================================================
    // PREVIOUS MONTH
    // =====================================================

    previousMonthButton.addEventListener(
        "click",
        function (event) {

            event.stopPropagation();

            currentMonth--;

            if (currentMonth < 0) {

                currentMonth = 11;
                currentYear--;

            }

            renderCalendar();

        }
    );


    // =====================================================
    // NEXT MONTH
    // =====================================================

    nextMonthButton.addEventListener(
        "click",
        function (event) {

            event.stopPropagation();

            currentMonth++;

            if (currentMonth > 11) {

                currentMonth = 0;
                currentYear++;

            }

            renderCalendar();

        }
    );


    // =====================================================
    // CLICK OUTSIDE POPUP
    // =====================================================

    document.addEventListener(
        "click",
        function (event) {

            if (
                !calendarPopup.contains(event.target) &&
                !calendarButton.contains(event.target)
            ) {

                calendarPopup.classList.add(
                    "hidden"
                );

            }

        }
    );


    // =====================================================
    // PREVENT POPUP CLOSE WHEN CLICKING INSIDE
    // =====================================================

    calendarPopup.addEventListener(
        "click",
        function (event) {

            event.stopPropagation();

        }
    );

});

