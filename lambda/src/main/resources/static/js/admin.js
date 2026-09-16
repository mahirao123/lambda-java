document.addEventListener("DOMContentLoaded", function () {

    document.querySelector("#image_file_input")
        .addEventListener("change", function (event) {

            let file = event.target.files[0];

            if (!file) return;

            if (!file.type.startsWith("image/")&&!file.type.startsWith("video/")) {
                alert("Only image/video files allowed!");
                event.target.value = "";
                return;
            }

            let reader = new FileReader();

            reader.onload = function () {
                document.querySelector("#upload_image_preview")
                    .setAttribute("src", reader.result);
            };
            let reader2 = new FileReader();

            reader2.onload = function () {
                document.querySelector("#upload_video_preview")
                    .setAttribute("src", reader2.result);
            };

            reader.readAsDataURL(file);
            reader2.readAsDataURL(file);
        });

});
document.addEventListener("DOMContentLoaded", function () {

    

    document.querySelector("#mainFrameImage_file_input")
        .addEventListener("change", function (event) {

            let file = event.target.files[0];

            if (!file) return;

            if (!file.type.startsWith("image/")&&!file.type.startsWith("video/")) {
                alert("Only image/video files allowed!");
                event.target.value = "";
                return;
            }

            let reader = new FileReader();

            reader.onload = function () {
                document.querySelector("#mianFrameUpload_image_preview")
                    .setAttribute("src", reader.result);
            };
            let reader2 = new FileReader();

            reader2.onload = function () {
                document.querySelector("#mainFrameUpload_video_preview")
                    .setAttribute("src", reader2.result);
            };

            reader.readAsDataURL(file);
            reader2.readAsDataURL(file);
        });

});