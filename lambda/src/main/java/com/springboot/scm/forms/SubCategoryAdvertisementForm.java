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
    
    // =========================================================
    // IMPORTENT NEWS TEXT
    // =========================================================
    private String importantText1;
    
    private String importantText2;
    
    private String importantText3;

	
}
