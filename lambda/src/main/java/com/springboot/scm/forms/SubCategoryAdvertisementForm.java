package com.springboot.scm.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubCategoryAdvertisementForm {

    // =========================================================
    // SUBCATEGORY INFORMATION
    // =========================================================

    private String name;
    
    // =========================================================
    // Advertisement 
    // =========================================================
    
    private String longFrameAdvertisement;
    
    private String sortFrameAdvertisement1;
    
    private String sortFrameAdvertisement2;

	
}
