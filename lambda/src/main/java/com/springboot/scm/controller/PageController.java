package com.springboot.scm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.scm.entities.Live;
import com.springboot.scm.entities.Slider;
import com.springboot.scm.entities.SlidingText;
import com.springboot.scm.entities.SocialMediaUrls;
import com.springboot.scm.entities.User;
import com.springboot.scm.forms.UserForm;
import com.springboot.scm.helpers.AppConstants;
import com.springboot.scm.helpers.Message;
import com.springboot.scm.helpers.MessageType;
import com.springboot.scm.helpers.UserAlreadyExistsException;
import com.springboot.scm.services.LiveService;
import com.springboot.scm.services.OpeningService;
import com.springboot.scm.services.SliderService;
import com.springboot.scm.services.SlidingTextService;
import com.springboot.scm.services.SocialMediaUrlsService;
import com.springboot.scm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
public class PageController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private OpeningService openingService;
	
	@Autowired
	private SliderService sliderService;
	
	@Autowired
	private SocialMediaUrlsService  socialMediaUrlsService;
	
	@Autowired
	private SlidingTextService slidingTextService;
	
	@Autowired
	private LiveService liveService;
	
	@GetMapping("/")
	public String index() {
		
		return "redirect:/public/home";
		
	}
	
@RequestMapping("/home")
public String homePage(
						
						@RequestParam(value="page",defaultValue="0") int page,
						@RequestParam(value="size",defaultValue=AppConstants.PAGE_SIZE) int size,
						@RequestParam(value="sortBy",defaultValue="dateTime") String sortBy,
						@RequestParam(value="direction",defaultValue="desc") String direction,
						Model model) {
	// main slider
	List<Slider> sliders=sliderService.getAllSlider();
	
	//Live Url
    List<Live>live=	liveService.getLiveUrl();
	
	//Slider Text
	List<SlidingText>slidingTexts=slidingTextService.getAllSlidingText();
	
	//All Social Media Videos
	Page<SocialMediaUrls>socialMediaPages=socialMediaUrlsService.getAll(size,page,sortBy,direction);
	
	if(socialMediaPages==null) {
		socialMediaPages=Page.empty();
	}
	 if (page < 0) {
	        page = 0;
	    }
	 
	 List<Slider> mainSliders = sliders.stream()
		        .filter(slider ->
		                slider.getSliderType() != null &&
		                slider.getSliderType().equalsIgnoreCase("MAIN"))
		        .toList();

		List<Slider> otherSliders = sliders.stream()
		        .filter(slider ->
		                slider.getSliderType() != null &&
		                slider.getSliderType().equalsIgnoreCase("OTHER"))
		        .toList();

	
		model.addAttribute("mainSliders", mainSliders);
		model.addAttribute("otherSliders", otherSliders);
	model.addAttribute("socialMediaPage",socialMediaPages);
	model.addAttribute("pageSize",AppConstants.PAGE_SIZE);
	model.addAttribute("sliders",sliders);
	model.addAttribute("slidingTexts",slidingTexts);
	model.addAttribute("live",live);
	
	
	return"home";
}
@RequestMapping("/about")
public String aboutPage() {
	
	
	return"about";
}
@RequestMapping("/ourAims")
public String servicePage() {
	
	
	return"companyAim/ourAims";
}

@RequestMapping("/contact")
public String contactPage() {
	
	
	return"contact";
}

@GetMapping("/login")
public String loginPage() {
	
	
	return"login";
}

@GetMapping("/signup")
public String registertPage(Model model) {
	UserForm userForm = new UserForm();
//	userForm.setName("raj");
	model.addAttribute("userForm",userForm);
	
	return"register";
}

@RequestMapping(value="/do-register", method=RequestMethod.POST)
public String processRegister(@Valid @ModelAttribute UserForm userForm,BindingResult rBindingResult,HttpSession session,Model model) {
	
	//Validation
	if(rBindingResult.hasErrors()) {
		
		Message message=Message.builder().content("You have already registered ").type(MessageType.red).build();
		session.setAttribute("message",message);
		return "register";
	}
	
	User user=new User();

	user.setName(userForm.getName());
	user.setEmail(userForm.getEmail());
	user.setPassword(userForm.getPassword());
	user.setPhoneNumber(userForm.getPhoneNumber());
	user.setAbout(userForm.getAbout());
	user.setProfilePic("https://media.licdn.com/dms/image/v2/D5603AQGPEexj7QySBA/profile-displayphoto-scale_400_400/B56ZfJ.34VGQAg-/0/1751440393220?e=1757548800&v=beta&t=PMwLzZHo1lmGBoy619n9P6mOVvDU50GtukryC8Bco44");
	
	//set the user roles
	user.setRoleList(List.of(AppConstants.ROLE_USER));
	
	
	try {
	User savedUser=userService.saveUser(user);
	System.out.println(savedUser);
	
	
	Message message=Message.builder().content("Registration Successful").type(MessageType.green).build();
	session.setAttribute("message",message);
	return "redirect:user/dashboard";
	
	}catch (UserAlreadyExistsException e) {  // 2. First check if email already exists (using form email, not user object)
        model.addAttribute("error", e.getMessage());
        return "register";  // show error on same page 
    }
}

// View all openings
@RequestMapping("/viewOpening")
public String viewOpening(Model model) {
	model.addAttribute("openingPage",openingService.getAllOpening());
	
	
	return "client/view_opening";
}

}
