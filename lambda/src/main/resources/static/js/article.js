document.addEventListener("DOMContentLoaded", function () {

    const categorySelect =
        document.getElementById("categoryId");

    const subCategorySelect =
        document.getElementById("subCategoryId");


    // =========================================================
    // GET EXISTING SUBCATEGORY FROM DATA ATTRIBUTE
    // =========================================================

    const selectedSubCategoryId =
        subCategorySelect.dataset.existingId;

    console.log("================================");
    console.log("CATEGORY:", categorySelect.value);
    console.log(
        "SUBCATEGORY FROM DATA ATTRIBUTE:",
        selectedSubCategoryId
    );
    console.log("================================");


    // =========================================================
    // LOAD SUBCATEGORIES
    // =========================================================

    function loadSubCategories(categoryId, selectedId) {

        console.log("Loading category:", categoryId);
        console.log("Need to select subcategory:", selectedId);


        // Loading state
        subCategorySelect.innerHTML =
            '<option value="">Loading...</option>';

        subCategorySelect.disabled = true;


        // No category
        if (!categoryId) {

            subCategorySelect.innerHTML =
                '<option value="">Select Sub Category</option>';

            subCategorySelect.disabled = false;

            return;
        }


        // =====================================================
        // API CALL
        // =====================================================

        fetch(
            "/editor/category/subcategories/" + categoryId,
            {
                method: "GET",
                headers: {
                    "Accept": "application/json"
                }
            }
        )

        .then(function (response) {

            if (!response.ok) {
                throw new Error(
                    "HTTP Error: " + response.status
                );
            }

            return response.json();
        })


        // =====================================================
        // PROCESS RESPONSE
        // =====================================================

        .then(function (data) {

            console.log("API DATA:", data);


            // Clear existing options
            subCategorySelect.innerHTML =
                '<option value="">Select Sub Category</option>';


            // =================================================
            // ADD API OPTIONS
            // =================================================

            data.forEach(function (subCategory) {

                const option =
                    document.createElement("option");

                option.value =
                    String(subCategory.id);

                option.textContent =
                    subCategory.name;

                subCategorySelect.appendChild(option);


                console.log(
                    "Added option:",
                    option.value,
                    option.textContent
                );
            });


            // =================================================
            // AUTO SELECT EXISTING SUBCATEGORY
            // =================================================

            if (
                selectedId !== null &&
                selectedId !== undefined &&
                selectedId !== ""
            ) {

                const selectedValue =
                    String(selectedId);


                console.log(
                    "Trying to select:",
                    selectedValue
                );


                // Set value directly
                subCategorySelect.value =
                    selectedValue;


                console.log(
                    "SELECT VALUE:",
                    subCategorySelect.value
                );


                // Verify
                if (
                    subCategorySelect.value === selectedValue
                ) {

                    console.log(
                        "AUTO SELECTED:",
                        selectedValue
                    );

                } else {

                    console.error(
                        "Subcategory ID " +
                        selectedValue +
                        " was NOT found in API response"
                    );
                }
            }


            // Enable select
            subCategorySelect.disabled = false;

        })


        // =====================================================
        // ERROR
        // =====================================================

        .catch(function (error) {

            console.error(
                "Subcategory loading error:",
                error
            );

            subCategorySelect.innerHTML =
                '<option value="">Failed to load Sub Category</option>';

            subCategorySelect.disabled = true;
        });
    }


    // =========================================================
    // CATEGORY CHANGE
    // =========================================================

    categorySelect.addEventListener(
        "change",
        function () {

            const categoryId =
                this.value;


            console.log(
                "CATEGORY CHANGED:",
                categoryId
            );


            // New category = no old subcategory
            loadSubCategories(
                categoryId,
                null
            );
        }
    );


    // =========================================================
    // UPDATE PAGE INITIAL LOAD
    // =========================================================

    if (categorySelect.value) {

        console.log(
            "UPDATE PAGE - Loading existing subcategory"
        );

        loadSubCategories(
            categorySelect.value,
            selectedSubCategoryId
        );
    }

});

// =====================================================
// Share Pop-up
// =====================================================


	const articleUrl = window.location.href;

	
	function openSharePopup() {
	    document.getElementById("sharePopup").classList.add("show");
	}

	function closeSharePopup() {
	    document.getElementById("sharePopup").classList.remove("show");
	}
	
	
	function shareFacebook() {

		const url =
			"https://www.facebook.com/sharer/sharer.php?u="
			+ encodeURIComponent(articleUrl);

		window.open(
			url,
			"_blank",
			"width=700,height=500"
		);
	}


	function shareTwitter() {

		const url =
			"https://twitter.com/intent/tweet?url="
			+ encodeURIComponent(articleUrl);

		window.open(
			url,
			"_blank",
			"width=700,height=500"
		);
	}


	function shareWhatsApp() {

		const url =
			"https://api.whatsapp.com/send?text="
			+ encodeURIComponent(articleUrl);

		window.open(
			url,
			"_blank"
		);
	}


	function shareInstagram() {
	    window.open("https://www.instagram.com/", "_blank");
	}
	
	

	function copyArticleLinkFromPopup() {

	    const articleUrl = window.location.href;
	    const copyMessage = document.getElementById("copyMessage");

	    navigator.clipboard.writeText(articleUrl)
	        .then(() => {

	            copyMessage.textContent = "Article link copied!";
	            copyMessage.classList.add("show");

	            setTimeout(() => {
	                copyMessage.classList.remove("show");
	            }, 2000);

	        })
	        .catch(() => {

	            copyMessage.textContent = "Unable to copy link.";
	            copyMessage.classList.add("show");

	        });
	}
	
	document.getElementById("sharePopup").addEventListener("click", function(event) {

	    if (event.target === this) {
	        closeSharePopup();
	    }

	});