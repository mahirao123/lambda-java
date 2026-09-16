/*
  * MainSliter Text js
  */

document.addEventListener("DOMContentLoaded", function () {

    const containers = document.querySelectorAll(
        ".relative.flex-1.h-full.overflow-hidden"
    );

    containers.forEach(container => {

        const marquee = container.querySelector(".animate-marquee");

        if (!marquee) {
            return;
        }

        let position = container.offsetWidth;
        let speed = 1.6;
        let paused = false;

        /*
         * Start from RIGHT side
         */
        marquee.style.transform =
            `translateX(${position}px)`;


        /*
         * Pause when mouse enters
         */
        container.addEventListener("mouseenter", function () {
            paused = true;
        });


        /*
         * Continue when mouse leaves
         */
        container.addEventListener("mouseleave", function () {
            paused = false;
        });


        /*
         * Animation
         */
        function moveMarquee() {

            if (!paused) {

                position -= speed;

                const marqueeWidth = marquee.offsetWidth;

                /*
                 * When complete text disappears
                 * from LEFT side
                 */
                if (position <= -marqueeWidth) {

                    /*
                     * Immediately move it
                     * back to RIGHT side
                     */
                    position = container.offsetWidth;
                }

                marquee.style.transform =
                    `translateX(${position}px)`;
            }

            requestAnimationFrame(moveMarquee);
        }


        moveMarquee();

    });

});


