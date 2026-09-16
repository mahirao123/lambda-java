package com.springboot.scm.employeeController;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.scm.employeeEntities.EmployeeDetails;
import com.springboot.scm.employeeServices.EmployeeService;
import com.springboot.scm.entities.Article;
import com.springboot.scm.entities.Complain;
import com.springboot.scm.entities.ContentCategory;
import com.springboot.scm.entities.ContentSubCategory;
import com.springboot.scm.entities.Live;
import com.springboot.scm.entities.Slider;
import com.springboot.scm.entities.SlidingText;
import com.springboot.scm.entities.SocialMediaUrls;
import com.springboot.scm.forms.ArticleForm;
import com.springboot.scm.forms.LiveForm;
import com.springboot.scm.forms.OpeningForm;
import com.springboot.scm.forms.SliderForm;
import com.springboot.scm.forms.SlidingTextForm;
import com.springboot.scm.forms.SocialMediaUrlsForm;
import com.springboot.scm.helpers.AppConstants;
import com.springboot.scm.helpers.EmployeeHelper;
import com.springboot.scm.helpers.Message;
import com.springboot.scm.helpers.MessageType;
import com.springboot.scm.helpers.ResourceNotFoundException;
import com.springboot.scm.services.ArticleService;
import com.springboot.scm.services.ComplainService;
import com.springboot.scm.services.ContentCategoryService;
import com.springboot.scm.services.ContentSubCategoryService;
import com.springboot.scm.services.ImageService;
import com.springboot.scm.services.LiveService;
import com.springboot.scm.services.SliderService;
import com.springboot.scm.services.SlidingTextService;
import com.springboot.scm.services.SocialMediaUrlsService;
import com.springboot.scm.util.SocialMediaUrlUtil;
import com.springboot.scm.validator.CreateGroup;
import com.springboot.scm.validator.UpdateGroup;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
@RequestMapping("/editor")
public class EditorController<subCategoryService> {
	
	@Autowired
	private SliderService sliderService;
	
	@Autowired
	private ComplainService complainService;
	
	@Autowired
	private SocialMediaUrlsService socialMedia;
	
	@Autowired
	private ImageService cloudinaryService;
	
	@Autowired
	private SlidingTextService slidingTextService;
	
	@Autowired
	private LiveService liveService;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private  ContentCategoryService categoryService;
	
	@Autowired
	private ContentSubCategoryService subCategoryService;
	
	@RequestMapping("/dashboard")
	public String editorDashboard(Model model) {
		
		return "editor/dashboard";
	}
	
	@RequestMapping("/slider/add")
	public String mainSliderSave(Model model) {
		
		SliderForm sliderForm=new SliderForm();
		model.addAttribute("sliderForm",sliderForm);
	
	return "editor/add_slider";
	}
	
	@RequestMapping(value="/slider/save",method=RequestMethod.POST)
	public String processMainSlider(@Validated(CreateGroup.class)
									@ModelAttribute SliderForm sliderForm,
							        BindingResult bindingResult,
							        HttpSession session,
							        Model model) {
		
		if(bindingResult.hasErrors()) {
			
			session.setAttribute("message", Message.builder()
	.content("Correct folowing errors")
			.type(MessageType.red)
			.build());
	return "editor/add_slider";
	}
	
	Slider slider=new Slider();
	
	String filename=UUID.randomUUID().toString();
	
	
	try {
	String fileUrl=cloudinaryService.uploadFile(sliderForm.getFile(), filename);
	
	slider.setSliderType(sliderForm.getSliderType());
	slider.setAbout(sliderForm.getAbout());
	slider.setMediaUrl(fileUrl);
	slider.setCloudinaryId(filename);
	
	String contentType=sliderForm.getFile().getContentType();
	if(contentType.startsWith("video")) {
	slider.setMediaType("VIDEO");
	}else {
		slider.setMediaType("IMAGE");
	}
	
	
	sliderService.saveMainSlider(slider);
	
	Message message=Message.builder()
			.content("Slide submited successfully")
			.type(MessageType.green)
			.build();
	
	session.setAttribute("message",message);
	
	return "redirect:/editor/slider/viewSlide";	
	}
	
	catch(ResourceNotFoundException e) {
		
		model.addAttribute("error",e.getMessage());
	model.addAttribute("sliderForm",sliderForm);
	
	return "editor/add_slider";
			
		}
	
		
	}
	
	//Main slide
	@RequestMapping("/slider/viewSlide")
	public String viewMainSlideTable(Model model) {
		List<Slider> slider=sliderService.getAllSlider();
		model.addAttribute("slider",slider);
	
	return "editor/view_slide";
	}
	
	
	@RequestMapping("/slider/update/{sliderId}")
	public String updateMainSlide(@PathVariable String sliderId,
									Model model) {
		SliderForm sliderForm =new SliderForm();
     Slider slider= sliderService.getBySliderId(sliderId).orElseThrow(()-> new RuntimeException("Not Found"));
	
	 sliderForm.setSliderType(slider.getSliderType());
	 sliderForm.setAbout(slider.getAbout());
	 sliderForm.setMediaUrl(slider.getMediaUrl());
	 model.addAttribute("sliderForm",sliderForm);
	 model.addAttribute("sliderId",sliderId);
	
	 return "editor/update_slider";
	}
	
	@RequestMapping ("/slider/do-update/{sliderId}")
	public String updateMainSlide(@Validated(UpdateGroup.class)
								  @ModelAttribute SliderForm sliderForm,
								  @PathVariable String sliderId,
								  BindingResult bindingResult,
			                      HttpSession session,Model model) {
	if(bindingResult.hasErrors()) {
			
			session.setAttribute("message", Message.builder()
	.content("Correct folowing errors")
			.type(MessageType.red)
			.build());
	return "editor/update_slider";
	}
	
			Slider slider= sliderService.getBySliderId(sliderId).orElseThrow(()-> new RuntimeException("Not Found"));
	
	
	try {
	        	
	       if(sliderForm.getFile()!=null && !sliderForm.getFile().isEmpty()) {
	    	   
	    	 //delete old cloudinary file
	String type=slider.getMediaType();
	String cloudinaryId=slider.getCloudinaryId();
	
	
	cloudinaryService.deleteCloudinaryFile(cloudinaryId, type);
	
	//set new cloudinary file
	String filename=UUID.randomUUID().toString();
	
	String mediaUrl= cloudinaryService.uploadFile(sliderForm.getFile(), filename);
	slider.setCloudinaryId(filename);
	slider.setMediaUrl(mediaUrl);
	
	String contentType=sliderForm.getFile().getContentType();
	if(contentType.startsWith("video")) {
	slider.setMediaType("VIDEO");
	}else {
		slider.setMediaType("IMAGE");
	    	}
	   }
	   
	   
	       slider.setSliderType(sliderForm.getSliderType());
	       slider.setAbout(sliderForm.getAbout());
	       slider.setBannerText(sliderForm.getBannerText());
	   sliderService.updateMainSlider(slider);
	   
	Message message=Message.builder()
			.content("Slide Updated successfully")
			.type(MessageType.green)
			.build();
	
	session.setAttribute("message",message);
	
	return "redirect:/editor/slider/viewSlide";	
	}catch(Exception e) {
		e.getMessage();
		return "editor/update_slider";
	        }
	}
	
	@RequestMapping("/slider/delete/{sliderId}")
	public String deleteMainSlide(@PathVariable String sliderId,HttpSession session,
		
			Model model) {
		
		
		
		Slider slider=sliderService.getBySliderId(sliderId).orElseThrow(()->new RuntimeException("SliderId Not Found"));
	 //delete old cloudinary file
	String cloudinaryId=slider.getCloudinaryId();
	String type=slider.getMediaType();
	
	try {
		sliderService.deleteMainSlider(sliderId);
		cloudinaryService.deleteCloudinaryFile(cloudinaryId,type);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
	
	
	session.setAttribute("message", Message.builder()
	.content("Slide Deleted Successfully")
		    .type(MessageType.green)
		    .build());
	
	return"redirect:/editor/slider/viewSlide";
	}
	
	@RequestMapping("/complainsResult")
	public String complainResultPostByPublic(Model model) {
		
		model.addAttribute("complainPage",complainService.getAllComplain());
		
		return "editor/view_complain";
	}
	
	@RequestMapping("/addSocialUrlLinks")
	public String saveSocialMediaUrl(Model model) {
		
		SocialMediaUrlsForm socialUrlsForm=new SocialMediaUrlsForm();
		model.addAttribute("socialUrlsForm",socialUrlsForm);
		
		return"editor/add_socialMediaUrls";
	}
	
	@RequestMapping(value="/socialMediaUrls/add",method=RequestMethod.POST)
	public String processSocialMediaUrlsData(@Valid 
												@ModelAttribute("socialUrlsForm") SocialMediaUrlsForm socialMediaUrlsForm,
												BindingResult bindingResult,
									            HttpSession session,
									            Model model) {
		
	    // Validation
	if (bindingResult.hasErrors()) {
		session.setAttribute("message",Message.builder()
	.content("Please correct the following errors")
			.type(MessageType.red)
			.build());
	
		return"editor/add_socialMediaUrls";
	}
	
	SocialMediaUrls media=new SocialMediaUrls();
	
	try {
		EmployeeDetails employee=EmployeeHelper.getLoggedInEmployee();
		media.setEmployee(employee);
		media.setPostedBy(employee.getName());
	} catch (Exception e) {
		
		e.printStackTrace();
	}
	
	String mediaId=UUID.randomUUID().toString();
	
	// Save Original video on cloudinary
	MultipartFile emergencyFile = socialMediaUrlsForm.getEmergencyVideo();

	if (emergencyFile != null && !emergencyFile.isEmpty()) {

	    String cloudinaryUrl = cloudinaryService.uploadFile(emergencyFile, mediaId);
	    media.setEmergencyVideo(cloudinaryUrl);

	    String contentType = emergencyFile.getContentType();

	    if (contentType != null) {
	        if (contentType.startsWith("video/")) {
	        	media.setMediaType("VIDEO");
	        } else if (contentType.startsWith("image/")) {
	        	media.setMediaType("IMAGE");
	        }
	    }
	}
	
	media.setId(mediaId);
	
	media.setDateTime(LocalDateTime.now());
	media.setSubject(socialMediaUrlsForm.getSubject());
	media.setAddress(socialMediaUrlsForm.getAddress());
	media.setFacebookSortLink(socialMediaUrlsForm.getFacebookSortLink());
	media.setInstagramSortLink(socialMediaUrlsForm.getInstagramSortLink());
	media.setYoutubeSortLink(socialMediaUrlsForm.getYoutubeSortLink());
	media.setYoutubeLink(socialMediaUrlsForm.getYoutubeLink());
	
	
	
	try {
		
		socialMedia.saveSocialMediaUrlsLink(media);
		
		Message message = Message.builder()
		        .content("Urls Added Successfully")
		        .type(MessageType.green)
		        .build();
		
		session.setAttribute("message", message);
		return "redirect:/editor/addSocialUrlLinks";
	}
	catch(Exception e) {
		
		model.addAttribute("socialUrlsForm",socialMediaUrlsForm);
		model.addAttribute("error",e.getMessage());
		
		return"editor/add_socialMediaUrls";
	}
		
	}
	
	// View And search Method For Videos and Links
@RequestMapping("/viewAllVideos")
public String moreVideos(
		@RequestParam(defaultValue="") String keyword,
		@RequestParam(value="page",defaultValue="0") int page,
		@RequestParam(value="size",defaultValue=AppConstants.PAGE_SIZE) int size,
		@RequestParam(value="sortBy",defaultValue="dateTime") String sortBy,
		@RequestParam(value="direction",defaultValue="desc") String direction,
		Model model) {
	
	//All Social Media Videos
	Page<SocialMediaUrls>socialMediaPage=socialMedia.getAll(size,page,sortBy,direction);
	
	Page<SocialMediaUrls>socialMediaSearchPage;
	socialMediaSearchPage=socialMedia.searchBySubjectOrAddress(keyword, keyword, page, size, sortBy, direction);
	
	
	String search = keyword == null ? "" : keyword.trim().toLowerCase();

	if (search.startsWith("latest") || search.startsWith("today")) {

		socialMediaSearchPage=socialMedia.searchByDateTime(LocalDateTime.now(), page, size, sortBy, direction);

	}
	
	if(socialMediaPage==null) {
		socialMediaPage=Page.empty();
	}
	
	if(socialMediaSearchPage==null) {
		socialMediaSearchPage=Page.empty();
	}
	 if (page < 0) {
	        page = 0;
	    }
	if(keyword==null || keyword.isBlank()) {
		
		model.addAttribute("socialMediaPage",socialMediaPage);
	}
	else {
		model.addAttribute("socialMediaPage",socialMediaSearchPage);
		
	}
	model.addAttribute("pageSize",AppConstants.PAGE_SIZE);
	return"editor/complainVideos";
}
	
//Delete Method
	
@RequestMapping("deleteVideoOneByOne/{id}")
public String deleteVideoOneByone(@PathVariable String id,HttpSession session,Model model) {

	SocialMediaUrls newUrl=socialMedia.getById(id).orElseThrow(()-> new RuntimeException("Not Found"));
	
	//Delete clodinary Video
	String mediaType=newUrl.getMediaType();
	

	
	
	
	try {
		
		if(mediaType!=null && !mediaType.isEmpty()) {
			cloudinaryService.deleteCloudinaryFile(id, mediaType);
			
			}
		
		socialMedia.deletSocilaMediaUrlsLink(id);
	}catch(Exception e) {
		model.addAttribute("message",e.getMessage());
	}
	
	session.setAttribute("message", Message.builder()
			.content("Videos  Deleted Successfully")
				    .type(MessageType.green)
				    .build());
	
	return"redirect:/editor/viewAllVideos";
	
}

@GetMapping("/updateAllVideos/{id}")
public String updateVideos(@PathVariable String id, Model model) {
	
	SocialMediaUrlsForm socialUrlsForm=new SocialMediaUrlsForm();
	
		SocialMediaUrls oldUrl=socialMedia.getById(id).orElseThrow(()-> new RuntimeException("Not Found"));
		socialUrlsForm.setSubject(oldUrl.getSubject());
		socialUrlsForm.setAddress(oldUrl.getAddress());
		socialUrlsForm.setYoutubeLink(oldUrl.getYoutubeLink());
		socialUrlsForm.setYoutubeSortLink(oldUrl.getYoutubeSortLink());
		socialUrlsForm.setInstagramSortLink(oldUrl.getInstagramSortLink());
		socialUrlsForm.setFacebookSortLink(oldUrl.getFacebookSortLink());
		
	model.addAttribute("socialUrlsForm",socialUrlsForm);
	model.addAttribute("id",id);
	return"editor/update_socialMediaUrls";
}

@RequestMapping(value="/socialMediaUrls/update/{id}",method=RequestMethod.POST)
public String socialMediaUrlsUpdateProcessing(@Valid 
												@ModelAttribute SocialMediaUrlsForm socialUrlsForm,
												BindingResult bindingResult,HttpSession session,
												@PathVariable String id,
												Model model) {
	
	if(bindingResult.hasErrors()) {
		session.setAttribute("message", Message.builder()
				.content("Please correct the following errors ")
				.type(MessageType.red));
		
		return "editor/update_socialMediaUrls";
	}
	SocialMediaUrls newUrl=socialMedia.getById(id).orElseThrow(()-> new RuntimeException("Not Found"));
	
	// Cloudinary Video update
	MultipartFile emergencyFile = socialUrlsForm.getEmergencyVideo();

	
	newUrl.setDateTime(LocalDateTime.now());
	newUrl.setSubject(socialUrlsForm.getSubject());
	newUrl.setAddress(socialUrlsForm.getAddress());
	newUrl.setYoutubeLink(socialUrlsForm.getYoutubeLink());
	newUrl.setYoutubeSortLink(socialUrlsForm.getYoutubeSortLink());
	newUrl.setInstagramSortLink(socialUrlsForm.getInstagramSortLink());
	newUrl.setFacebookSortLink(socialUrlsForm.getFacebookSortLink());
	
	try {
		
		if (emergencyFile != null && !emergencyFile.isEmpty()) {
			
			//Delete old clodinary Video before new upload
			String mediaType=newUrl.getMediaType();
			
			if(mediaType!=null && !mediaType.isEmpty()) {
			cloudinaryService.deleteCloudinaryFile(id, mediaType);
			}

		    String cloudinaryUrl = cloudinaryService.uploadFile(emergencyFile, id);
		    newUrl.setEmergencyVideo(cloudinaryUrl);

		    String contentType = emergencyFile.getContentType();

		    if (contentType != null) {
		        if (contentType.startsWith("video/")) {
		        	newUrl.setMediaType("VIDEO");
		        } else if (contentType.startsWith("image/")) {
		        	newUrl.setMediaType("IMAGE");
		        }
		    }
		}
		socialMedia.saveSocialMediaUrlsLink(newUrl);
	    Message message = Message.builder()
	            .content("Updated Successfully")
	        .type(MessageType.green)
	        .build();
	
	session.setAttribute("message", message);
		
	return"redirect:/editor/viewAllVideos";
	}catch(Exception e) {
		
		model.addAttribute("socialUrlsForm",socialUrlsForm);
		model.addAttribute("error",e.getMessage());
		return "editor/update_socialMediaUrls";
	}
	
	
}

@RequestMapping("/slidingText")
public String slidingTextsave(Model model) {
	SlidingTextForm form=new SlidingTextForm();
	model.addAttribute("slidingTextForm",form);

	
	return "editor/addSlidingText";
}

@RequestMapping(value="/slidingText/add",method=RequestMethod.POST)
public String processSlidingText(@Valid 
									@ModelAttribute("slidingTextForm") SlidingTextForm form,
									BindingResult bindingResult,
							        HttpSession session,
							        Model model) {
	
    // Validation
if (bindingResult.hasErrors()) {
	session.setAttribute("message",Message.builder()
.content("Please correct the following errors")
		.type(MessageType.red)
		.build());

	return"editor/addSlidingText";
}
	
	SlidingText newText= new SlidingText();
	
	newText.setText(form.getText());
	newText.setUrl(form.getUrl());
	
	
	try {
		slidingTextService.saveSlidingText(newText);
		Message message = Message.builder()
		        .content(" Add Successfully")
		        .type(MessageType.green)
		        .build();
		
		session.setAttribute("message", message);
		return "redirect:/editor/viewSlidingText";
		
	}catch(Exception e) {
		model.addAttribute("slidingTextForm",form);
		model.addAttribute("error",e.getMessage());
	}
	
	return "editor/addSlidingText";
}

@RequestMapping("/viewSlidingText")
public String viewSlidingText(Model model) {
	List<SlidingText> slidingText=slidingTextService.getAllSlidingText();
	model.addAttribute("slidingText",slidingText);
	
	return"editor/viewSlidingText";
}

@RequestMapping("/slidingText/edit/{id}")
public String editSlidingText(@PathVariable Long id,Model model) {
	SlidingTextForm form=new SlidingTextForm();
	
	SlidingText oldText=slidingTextService.getSlidingTextById(id).orElseThrow(()-> new RuntimeException("Not Found"));
	form.setText(oldText.getText());
	form.setUrl(oldText.getUrl());
	
	model.addAttribute("slidingTextForm",form);
	
	return"editor/updateSlidingText";
}

@RequestMapping(value="/slidingText/update/{id}",method=RequestMethod.POST)
public String updateSlidingText(@Valid 
								@ModelAttribute("slidingTextForm") SlidingTextForm form,
								BindingResult bindingResult,HttpSession session,
								@PathVariable Long id,
								Model model) {
	
    // Validation
if (bindingResult.hasErrors()) {
	session.setAttribute("message",Message.builder()
.content("Please correct the following errors")
		.type(MessageType.red)
		.build());

	return"editor/updateSlidingText";
}

SlidingText newText=slidingTextService.getSlidingTextById(id).orElseThrow(()-> new RuntimeException("Not Found"));
newText.setText(form.getText());
newText.setUrl(form.getUrl());

try {
	slidingTextService.updateSlidingText(newText);
	Message message = Message.builder()
	        .content(" Updated Successfully")
	        .type(MessageType.green)
	        .build();
	
	session.setAttribute("message", message);
	return "redirect:/editor/viewSlidingText";
	
}catch(Exception e) {
	model.addAttribute("slidingTextForm",form);
	model.addAttribute("error",e.getMessage());
}

	return"editor/updateSlidingText";
}

@RequestMapping("/slidingText/delete/{id}")
public String deleteSlidingText(@PathVariable Long id,HttpSession session ) {
	
	slidingTextService.deleteSlidingText(id);
	Message message = Message.builder()
	        .content(" Deleted Successfully")
	        .type(MessageType.green)
	        .build();
	
	session.setAttribute("message", message);
	return "redirect:/editor/viewSlidingText";
	
	
}



@RequestMapping("/liveUrl")
public String saveLiveUrl(Model model) {
	LiveForm liveForm=new LiveForm();
	
model.addAttribute("liveForm",liveForm);
	return"editor/addLiveUrl";
}

@RequestMapping(value="/liveUrl/add",method=RequestMethod.POST)
public String processingLiveUrlForm(@Valid 
									@ModelAttribute("liveForm") LiveForm liveForm,
									BindingResult bindingResult,HttpSession session,
									
									Model model) {
	
    // Validation
if (bindingResult.hasErrors()) {
	session.setAttribute("message",Message.builder()
.content("Please correct the following errors")
		.type(MessageType.red)
		.build());

	return"editor/addLiveUrl";
		
	
}

Live url=new Live();
url.setLiveUrl(liveForm.getLiveUrl());

try {
	liveService.saveLiveUrl(url);
	Message message = Message.builder()
	        .content(" Add Live Url Successfully")
	        .type(MessageType.green)
	        .build();
	
	session.setAttribute("message", message);
	return "redirect:/editor/viewLiveUrl";
	
}catch(Exception e) {
	model.addAttribute("liveForm",liveForm);
	model.addAttribute("error", e.getMessage());
}

return "editor/addLiveUrl";	
}

@RequestMapping("/viewLiveUrl")
public String viewLiveUrl(Model model) {
	
List<Live>liveUrl=	liveService.getLiveUrl();
model.addAttribute("liveUrl",liveUrl);

return"editor/viewLiveUrl";
	
}

@RequestMapping("/liveUrl/delete/{id}")
public String deleteLiveUrl(@PathVariable Long id,HttpSession session) {
	
	liveService.deleteLiveUrl(id);
	Message message = Message.builder()
	        .content(" Delete Live Url Successfully")
	        .type(MessageType.green)
	        .build();
	session.setAttribute("message", message);
	return "redirect:/editor/viewLiveUrl";
	
}

  
// ======================================================
// CATEGORY PAGE
// ======================================================

@GetMapping("/ourAims/category")
public String aimsCategoryPage(Model model) {

    model.addAttribute(
            "categories",
            categoryService.getAll()
    );

    model.addAttribute(
            "category",
            new ContentCategory()
    );

    return "editor/content-category";
}

// ======================================================
// ADD CATEGORY
// ======================================================
@PostMapping("/category/add")
public String addAimsCategory(
        @RequestParam("date") Date date,
        @RequestParam("day") String day) {

    // Check duplicate date
    if (categoryService.isExistContent(date)) {

        // Duplicate found
        return "redirect:/editor/ourAims/category?error=duplicate";
    }

    // Validate input
    if (date != null
            && day != null
            && !day.trim().isEmpty()) {

        ContentCategory category =
                ContentCategory.builder()
                        .date(date)
                        .day(day.trim())
                        .build();

        categoryService.save(category);
    }

    return "redirect:/editor/ourAims/category";
}

// ======================================================
// UPDATE CATEGORY
// ======================================================

@PostMapping("/category/update")
public String updateAimsCategory(
        @RequestParam("id") Long id,
        @RequestParam("date") Date date,
        @RequestParam("day") String day) {
	
    // Check duplicate date
    if (categoryService.isExistContent(date)) {

        // Duplicate found
        return "redirect:/editor/ourAims/category?error=duplicate";
    }
    ContentCategory category =
            categoryService.findById(id);

    if (category == null) {

        throw new RuntimeException(
                "Category not found with id: " + id
        );
    }



    // UPDATE DAY

    if (day != null
            && !day.trim().isEmpty()) {

        category.setDay(day.trim());
    }


    categoryService.save(category);


    return "redirect:/editor/ourAims/category";
}

// ======================================================
// ADD SUBCATEGORY
// ======================================================

@PostMapping("/category/subcategory/add")
public String addAimsSubCategory(
        @RequestParam("categoryId") Long categoryId,
        @RequestParam("name") String name) {

    if (name != null
            && !name.trim().isEmpty()) {

        ContentCategory category =
                categoryService.findById(categoryId);


        if (category == null) {

            throw new RuntimeException(
                    "Category not found with id: "
                    + categoryId
            );
        }


        ContentSubCategory subCategory =
                ContentSubCategory.builder()
                        .name(name.trim())
                        .category(category)
                        .build();


        subCategoryService.saveSubCategory(
                subCategory
        );
    }


    return "redirect:/editor/ourAims/category";
}


// ======================================================
// UPDATE SUBCATEGORY
// ======================================================

@PostMapping("/category/subcategory/update")
public String updateAimsSubCategory(
        @RequestParam("id") Long id,
        @RequestParam("name") String name) {


    Optional<ContentSubCategory> optionalSubCategory =
            subCategoryService.getById(id);


    if (optionalSubCategory.isEmpty()) {

        throw new RuntimeException(
                "Subcategory not found with id: " + id
        );
    }


    ContentSubCategory subCategory =
            optionalSubCategory.get();


    if (name != null
            && !name.trim().isEmpty()) {

        subCategory.setName(
                name.trim()
        );
    }


    subCategoryService.saveSubCategory(
            subCategory
    );


    return "redirect:/editor/ourAims/category";
}


// ======================================================
// GET SUBCATEGORIES BY CATEGORY
// ======================================================

@GetMapping("/category/subcategories/{categoryId}")
@ResponseBody
public List<Map<String, Object>> getSubCategories(
        @PathVariable("categoryId") Long categoryId) {


    List<ContentSubCategory> subCategories =
            subCategoryService
                    .getByCategoryId(
                            categoryId
                    );


    return subCategories
            .stream()
            .map(sub -> {

                Map<String, Object> map =
                        new HashMap<>();


                map.put(
                        "id",
                        sub.getId()
                );


                map.put(
                        "name",
                        sub.getName()
                );


                return map;

            })
            .toList();
}


// ======================================================
// DELETE CATEGORY
// ======================================================

@GetMapping("/category/delete/{id}")
public String deleteCategory(
        @PathVariable("id") Long id) {


    ContentCategory category =
            categoryService.findById(id);


    if (category == null) {

        throw new RuntimeException(
                "Category not found with id: " + id
        );
    }


    categoryService.delete(id);


    return "redirect:/editor/ourAims/category";
}


// ======================================================
// DELETE SUBCATEGORY
// ======================================================

@GetMapping("/category/subcategory/delete/{id}")
public String deleteSbCategory(
        @PathVariable("id") Long id) {


    Optional<ContentSubCategory> optionalSubCategory =
            subCategoryService.getById(id);


    if (optionalSubCategory.isEmpty()) {

        throw new RuntimeException(
                "Subcategory not found with id: " + id
        );
    }


    subCategoryService.deleteSubCategory(id);


    return "redirect:/editor/ourAims/category";
}





// =========================================================
// ARTICLE LIST
// GET /editor/article/list
// =========================================================

@GetMapping("/article/list")
public String articleList(Model model) {
	List<Article> articles = articleService.getAllArticles();
	model.addAttribute("articles", articles);
	return "editor/article-list";
}
    // =========================================================
    // ADD ARTICLE FORM
    // GET /editor/article/add
    // =========================================================

@GetMapping("article/add")
public String addArticleForm(Model model) {

    ArticleForm articleForm = new ArticleForm();

    model.addAttribute("articleForm", articleForm);

    List<ContentCategory> allCategories = categoryService.getAll();

    LocalDate today = LocalDate.now();

    List<ContentCategory> categories = allCategories.stream()
            .filter(category -> category.getDate() != null)
            .filter(category ->
                    !category.getDate().toLocalDate().isBefore(today)
            )
            .toList();

    model.addAttribute("categories", categories);

    return "editor/article-add";
}

	// =========================================================
	// SAVE ARTICLE
	// POST /editor/article/save
	// =========================================================


@PostMapping("article/save")
public String saveArticle(
        @Valid @ModelAttribute("articleForm") ArticleForm articleForm,
        BindingResult bindingResult,
        HttpSession session,
        Authentication authentication,
        Model model)  {

    // =====================================================
    // VALIDATION
    // =====================================================

if (bindingResult.hasErrors()) {

    session.setAttribute(
            "message",
            Message.builder()
                    .content("Please correct the following errors")
                    .type(MessageType.red)
                    .build()
    );
    

//    
//    List<ContentCategory> categories =
//            categoryService.getAll();
//
//    model.addAttribute("categories", categories);

    return "editor/article-add";
}

    Article article = new Article();

    String fileName = UUID.randomUUID().toString();


    // =====================================================
    // SUBCATEGORY
    // =====================================================
    ContentSubCategory subCategory =
            subCategoryService
                    .getById(articleForm.getSubCategoryId())
                    .orElseThrow(
                            () -> new RuntimeException(
                                    "SubCategory not found"
                            )
                    );
    subCategory.setEnable(true);
    article.setSubCategory(subCategory);

    // =====================================================
    // MAIN FRAME IMAGE
    // =====================================================

    if (articleForm.getMainFrameImageFile() != null
            && !articleForm.getMainFrameImageFile().isEmpty()) {

        String imageId = "mainFrameImage_" + fileName;
        
        String fileUrl = cloudinaryService.uploadFile(articleForm.getMainFrameImageFile(),imageId);
        article.setMainFrameCloudinaryid(imageId);
        article.setMainFrameImageUrl(fileUrl);


    }

    
    
    // =====================================================
    // AUTHOR
    // =====================================================

    if (authentication != null
            && authentication.isAuthenticated()) {

        EmployeeDetails employee =
                employeeService
                        .getEmployeeByEmail(authentication.getName())
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Employee not found"
                                )
                        );

        article.setAuthor(employee);
    }


    // =====================================================
    // ARTICLE
    // =====================================================
    //Start Main Article if
if(articleForm.getContent()!=null && !articleForm.getContent().isEmpty()) {
    article.setArticleId("ART-" + System.currentTimeMillis() );

    article.setTitle(articleForm.getTitle() );

    article.setShortDescription(articleForm.getShortDescription());

    article.setContentType(articleForm.getContentType() );

    article.setContent(articleForm.getContent());


    // =====================================================
    // SLUG
    // =====================================================

    if (articleForm.getSlug() == null || articleForm.getSlug().isBlank()) {

        article.setSlug(generateSlug(articleForm.getTitle()));

    } else {

        article.setSlug(articleForm.getSlug());
    }


    // =====================================================
    // STATUS
    // =====================================================

    article.setStatus( articleForm.getStatus());


    // =====================================================
    // CREATED / PUBLISHED DATE
    // =====================================================

    LocalDateTime now = LocalDateTime.now();

    article.setCreatedAt(now);
    article.setPublishedAt(now);


    // =====================================================
    // BREAKING NEWS
    // =====================================================

    article.setBreakingNews( Boolean.TRUE.equals(articleForm.getBreakingNews()));


    // =====================================================
    // FEATURED
    // =====================================================

    article.setFeatured( Boolean.TRUE.equals(articleForm.getFeatured()));


    // =====================================================
    // IMAGE
    // =====================================================

    if (articleForm.getImageFile() != null && !articleForm.getImageFile().isEmpty()) {

        String imageId = "image_" + fileName;

        String fileUrl = cloudinaryService.uploadFile( articleForm.getImageFile(),imageId);

        article.setImageCloudinaryId(imageId);

        article.setMediaType("IMAGE");

        article.setImageUrl(fileUrl);

        article.setCaption(articleForm.getCaption());
    }


    // =====================================================
    // URLS
    // =====================================================

    article.setMainUrl(articleForm.getMainUrl());
    
    article.setEmbedMainUrl(SocialMediaUrlUtil.youtubeEmbedUrl(articleForm.getMainUrl()));

    article.setSubUrl1(articleForm.getSubUrl1());

    article.setSubUrl2(articleForm.getSubUrl2());

    article.setSubUrl3( articleForm.getSubUrl3());

    article.setSubUrl4(articleForm.getSubUrl4());


    // =====================================================
    // SEO
    // =====================================================

    article.setSeoTitle( articleForm.getSeoTitle());

    article.setSeoDescription(articleForm.getSeoDescription());

    article.setKeywords(articleForm.getKeywords() );

    article.setCanonicalUrl(articleForm.getCanonicalUrl());

    article.setMetaRobots(articleForm.getMetaRobots());
// End if
}


    // =====================================================
    // SAVE
    // =====================================================

    try {

        articleService.saveArticle(article);

        session.setAttribute(
                "message",
                Message.builder()
                        .content("Article Added Successfully")
                        .type(MessageType.green)
                        .build()
        );

        return "redirect:/editor/article/list";

    } catch (Exception e) {

        e.printStackTrace();

        List<ContentCategory> categories =
                categoryService.getAll();

        model.addAttribute(
                "categories",
                categories
        );

        model.addAttribute(
                "error",
                e.getMessage()
        );

        model.addAttribute(
                "articleForm",
                articleForm
        );

        return "editor/article-add";
    }
}

    // =========================================================
    // VIEW ARTICLE
    // GET /editor/article/view/{id}
    // =========================================================

    @GetMapping("article/view/{id}")
    public String viewArticle(
            @PathVariable Long id,
            Model model) {


        Article article =
                articleService.getArticleById(id);


        model.addAttribute(
                "article",
                article
        );


        return "editor/article-view";
    }


    // =========================================================
    // UPDATE FORM
    // GET /editor/article/update/{id}
    // =========================================================

@GetMapping("article/update/{id}")
public String updateArticleForm(
        @PathVariable Long id,
        Model model) {

    // =========================================================
    // GET ARTICLE
    // =========================================================

    Article article = articleService.getArticleById(id);

    if (article == null) {
        throw new RuntimeException("Article not found with id: " + id);
    }


    // =========================================================
    // CREATE FORM
    // =========================================================

    ArticleForm articleForm = new ArticleForm();


    // =========================================================
    // CATEGORY / SUBCATEGORY
    // =========================================================

    if (article.getSubCategory() != null) {

        // Existing SubCategory
        Long subCategoryId =
                article.getSubCategory().getId();

        articleForm.setSubCategoryId(subCategoryId);

        System.out.println(
                "SubCategoryId = " + subCategoryId
        );


        // Existing Category
        if (article.getSubCategory().getCategory() != null) {

            Long categoryId =
                    article.getSubCategory()
                           .getCategory()
                           .getId();

            articleForm.setCategoryId(categoryId);

            System.out.println(
                    "CategoryId = " + categoryId
            );
        }
    }


    // =========================================================
    // BASIC INFORMATION
    // =========================================================

    articleForm.setTitle(article.getTitle());
    articleForm.setSlug(article.getSlug());
    articleForm.setContentType(article.getContentType());
    articleForm.setShortDescription(article.getShortDescription());
    articleForm.setContent(article.getContent());
    articleForm.setStatus(article.getStatus());

    articleForm.setBreakingNews(
            article.getBreakingNews()
    );

    articleForm.setFeatured(
            article.getFeatured()
    );


    // =========================================================
    // MEDIA
    // =========================================================

    articleForm.setMainFrameImagePreviewUrl(
            article.getMainFrameImageUrl()
    );

    articleForm.setImagePreviewUrl(
            article.getImageUrl()
    );

    articleForm.setMainUrl(
            article.getMainUrl()
    );

    articleForm.setSubUrl1(
            article.getSubUrl1()
    );

    articleForm.setSubUrl2(
            article.getSubUrl2()
    );

    articleForm.setSubUrl3(
            article.getSubUrl3()
    );

    articleForm.setSubUrl4(
            article.getSubUrl4()
    );

    articleForm.setCaption(
            article.getCaption()
    );


    // =========================================================
    // SEO
    // =========================================================

    articleForm.setSeoTitle(
            article.getSeoTitle()
    );

    articleForm.setSeoDescription(
            article.getSeoDescription()
    );

    articleForm.setKeywords(
            article.getKeywords()
    );

    articleForm.setCanonicalUrl(
            article.getCanonicalUrl()
    );

    articleForm.setMetaRobots(
            article.getMetaRobots()
    );


    // =========================================================
    // DEBUG
    // =========================================================

    System.out.println(
            "FORM CategoryId = "
            + articleForm.getCategoryId()
    );

    System.out.println(
            "FORM SubCategoryId = "
            + articleForm.getSubCategoryId()
    );


    // =========================================================
    // MODEL
    // =========================================================

    model.addAttribute("articleForm",articleForm);

    model.addAttribute("categories",categoryService.getAll());
    
    model.addAttribute("subCategories",subCategoryService.getAll());

    model.addAttribute("id",id);


    return "editor/article-update";
}


	// =========================================================
	// UPDATE ARTICLE
	// POST /editor/article/update
	// =========================================================

	@PostMapping("article/update/{id}")
    public String updateArticle(
					    		@Valid @ModelAttribute("articleForm") ArticleForm articleForm,
					    		@PathVariable Long id,
					            BindingResult bindingResult,
					            HttpSession session,
					            Authentication authentication,Model model) throws IOException {

if (bindingResult.hasErrors()) {
    session.setAttribute(
            "message",
            Message.builder()
                    .content("Please correct the following errors")
                    .type(MessageType.red)
                    .build() );
    
    return "editor/article-add";
}

    Article article = articleService.getArticleById(id);

    String fileName = UUID.randomUUID().toString();


    // =====================================================
    // SUBCATEGORY
    // =====================================================
    ContentSubCategory subCategory =subCategoryService.getById(articleForm.getSubCategoryId())
                    .orElseThrow(() -> new RuntimeException("SubCategory not found"));

                      article.setSubCategory(subCategory);

    // =====================================================
    // AUTHOR
    // =====================================================

    if (authentication != null && authentication.isAuthenticated()) {

        EmployeeDetails employee = employeeService.getEmployeeByEmail(authentication.getName())
                        .orElseThrow(() -> new RuntimeException("Employee not found") );

        article.setAuthor(employee);
    }


    // =====================================================
    // ARTICLE
    // =====================================================

    article.setTitle(articleForm.getTitle());

    article.setShortDescription(articleForm.getShortDescription());

    article.setContentType(articleForm.getContentType());

    article.setContent(articleForm.getContent());


    // =====================================================
    // SLUG
    // =====================================================

    if (articleForm.getSlug() == null || articleForm.getSlug().isBlank()) {

        article.setSlug(generateSlug(articleForm.getTitle()) );

    } else {
        article.setSlug(articleForm.getSlug() );
    }


    // =====================================================
    // STATUS
    // =====================================================

    article.setStatus(articleForm.getStatus());

    // =====================================================
    // UPDATED DATE
    // =====================================================
    LocalDateTime now = LocalDateTime.now();

    article.setUpdatedAt(now);
 


    // =====================================================
    // BREAKING NEWS
    // =====================================================

    article.setBreakingNews(Boolean.TRUE.equals( articleForm.getBreakingNews()));


    // =====================================================
    // FEATURED
    // =====================================================

    article.setFeatured(Boolean.TRUE.equals( articleForm.getFeatured()));
    
    // =====================================================
    // MAIN FRAME IMAGE
    // =====================================================

    if (articleForm.getMainFrameImageFile() != null
            && !articleForm.getMainFrameImageFile().isEmpty()) {
    	
    	cloudinaryService.deleteCloudinaryFile(article.getMainFrameCloudinaryid(),"IMAGE");
    	
        String imageId = "mainFrameImage_" + fileName;
        
        String fileUrl = cloudinaryService.uploadFile(articleForm.getMainFrameImageFile(),imageId);
        article.setMainFrameCloudinaryid(imageId);

        article.setMainFrameImageUrl(fileUrl);

    }


    // =====================================================
    // IMAGE
    // =====================================================

    if (articleForm.getImageFile() != null && !articleForm.getImageFile().isEmpty()) {
    	
    	cloudinaryService.deleteCloudinaryFile(article.getImageCloudinaryId(), article.getMediaType());
        String imageId ="image_" + fileName;

        String fileUrl = cloudinaryService.uploadFile(articleForm.getImageFile(),imageId);

        article.setImageCloudinaryId(imageId);

        article.setMediaType("IMAGE");

        article.setImageUrl(fileUrl);

        article.setCaption( articleForm.getCaption());
    }


    // =====================================================
    // URLS
    // =====================================================

    article.setMainUrl(articleForm.getMainUrl());
    
    article.setEmbedMainUrl(SocialMediaUrlUtil.youtubeEmbedUrl(articleForm.getMainUrl()));

    article.setSubUrl1(articleForm.getSubUrl1());

    article.setSubUrl2(articleForm.getSubUrl2());

    article.setSubUrl3(articleForm.getSubUrl3());

    article.setSubUrl4(articleForm.getSubUrl4());


    // =====================================================
    // SEO
    // =====================================================

    article.setSeoTitle(articleForm.getSeoTitle());

    article.setSeoDescription(articleForm.getSeoDescription());

    article.setKeywords(articleForm.getKeywords());

    article.setCanonicalUrl(articleForm.getCanonicalUrl());

    article.setMetaRobots(articleForm.getMetaRobots());




    // =====================================================
    // SAVE
    // =====================================================

    try {

        articleService.updateArticle(id, article);

        session.setAttribute(
                "message",
                Message.builder()
                        .content("Article Updated Successfully")
                        .type(MessageType.green)
                        .build()
        );

        return "redirect:/editor/article/list";

    } catch (Exception e) {


        List<ContentCategory> categories =categoryService.getAll();

        model.addAttribute("categories",categories);
        
        model.addAttribute("subCategories",subCategoryService.getAll());

        model.addAttribute("error",e.getMessage());

        model.addAttribute("articleForm",articleForm);

        return "editor/article-update";
    }
    
    
    
    }


    // =========================================================
    // DELETE
    // POST /editor/article/delete/{id}
    // =========================================================

    @RequestMapping("article/delete/{id}")
    public String deleteArticle(@PathVariable Long id,HttpSession session,Model model) {
	    
    	Article article=articleService.getArticleById(id);
    	
    	if (article.getMainFrameImageUrl() != null && !article.getMainFrameImageUrl().isEmpty()) {
    		
    		try {
    			cloudinaryService.deleteCloudinaryFile(article.getMainFrameCloudinaryid(),"IMAGE");
    		} catch (IOException e) {
    			model.addAttribute("error",e.getMessage());
    		}
    	} 	
  
    	
        if (article.getImageUrl() != null && !article.getImageUrl().isEmpty()) {
        	
        	try {
				cloudinaryService.deleteCloudinaryFile(article.getImageCloudinaryId(), article.getMediaType());
			} catch (IOException e) {
				model.addAttribute("error",e.getMessage());
			}
        } 	
    	
    	articleService.deleteArticle(id);

        session.setAttribute(
                "message",
                Message.builder()
                        .content("Article Deleted Successfully")
                        .type(MessageType.green)
                        .build());

        return "redirect:/editor/article/list";
    }


    // =========================================================
    // PUBLIC ARTICLE
    // GET /article/{slug}
    // =========================================================

    @GetMapping("article/public/{slug}")
    public String publicArticle(

            @PathVariable String slug,

            Model model) {


        Article article =
                articleService
                    .getArticleBySlug(slug);


        if ("PUBLISHED".equalsIgnoreCase(
                article.getStatus())) {

            articleService.increaseViewCount(
                    article.getId()
            );
        }


        model.addAttribute(
                "article",
                article
        );


        return "article/article-detail";
    }


    // =========================================================
    // SLUG GENERATOR
    // =========================================================

    private String generateSlug(
            String title) {


        if (title == null) {

            return "article-" +
                    System.currentTimeMillis();
        }


        return title
                .toLowerCase()
                .trim()
                .replaceAll("[^a-z0-9\\s-]", "")
                .replaceAll("\\s+", "-")
                .replaceAll("-+", "-");
    }







}
