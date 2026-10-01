package com.springboot.scm.controller;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springboot.scm.entities.Article;
import com.springboot.scm.entities.ContentCategory;
import com.springboot.scm.entities.ContentSubCategory;
import com.springboot.scm.entities.Podcast;
import com.springboot.scm.entities.SocialMediaUrls;
import com.springboot.scm.helpers.AppConstants;
import com.springboot.scm.services.ArticleService;
import com.springboot.scm.services.ComplainService;
import com.springboot.scm.services.ContentCategoryService;
import com.springboot.scm.services.ContentSubCategoryService;
import com.springboot.scm.services.OpeningService;
import com.springboot.scm.services.PodcastService;
import com.springboot.scm.services.SocialMediaUrlsService;

@Controller
@RequestMapping("/public")
public class PublicController {

@Autowired
private OpeningService	openingService;

@Autowired
private ComplainService complainService;

@Autowired
private PodcastService podcastService;

@Autowired
private SocialMediaUrlsService socialMediaUrlsService;

@Autowired
private ArticleService articleService;

@Autowired
private  ContentCategoryService categoryService;

@Autowired
private ContentSubCategoryService subCategoryService;

@RequestMapping("/viewOpening")
public String viewOpening(Model model) {
	model.addAttribute("openingPage",openingService.getAllOpening());
	
	
	return "client/view_opening";
}

@RequestMapping("/viewComplain")
public String viewComplain(Model model) {
	model.addAttribute("complainPage",complainService.getAllComplain());
	
	
	return "client/view_complain";
}

@RequestMapping("/moreVideos")
public String moreVideos(
		@RequestParam(defaultValue="") String keyword,
		@RequestParam(value="page",defaultValue="0") int page,
		@RequestParam(value="size",defaultValue=AppConstants.PAGE_SIZE) int size,
		@RequestParam(value="sortBy",defaultValue="dateTime") String sortBy,
		@RequestParam(value="direction",defaultValue="desc") String direction,
		Model model) {
	
	//All Social Media Videos
	Page<SocialMediaUrls>socialMediaPage=socialMediaUrlsService.getAll(size,page,sortBy,direction);
	
	
	String search = keyword == null ? "" : keyword.trim().toLowerCase();
	Page<SocialMediaUrls>socialMediaSearchPage;
	socialMediaSearchPage=socialMediaUrlsService.searchBySubjectOrAddress(keyword, keyword, page, size, sortBy, direction);
	
	
	

	if (search.startsWith("latest") || search.startsWith("today")) {

		socialMediaSearchPage=socialMediaUrlsService.searchByDateTime(LocalDateTime.now(), page, size, sortBy, direction);

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
	
	
		
		return"client/moreVideos";
	
		
}

@RequestMapping("/podcast/view")
public String podcastForPublicView(@RequestParam(defaultValue="") String keyword,
									@RequestParam(value = "date", required = false) String dateParam,
									@RequestParam(value="page",defaultValue="0") int page,
									@RequestParam(value="size",defaultValue="20") int size,
									@RequestParam(value="sortBy",defaultValue="date") String sortBy,
									@RequestParam(value="direction",defaultValue="desc") String direction,
									Model model) {
	
	Page<Podcast> 	podcast=podcastService.getAllPodcast(size,page,sortBy,direction);
	
	var search = keyword == null ? "" : keyword.trim().toLowerCase();
	
	 if (page < 0) {
	        page = 0;
	    }

	if(podcast==null) {
		podcast=Page.empty();
	}
   
	//Search by fields

    Date date = null;

    if (dateParam != null && !dateParam.isBlank()) {
        date = Date.valueOf(dateParam);
    }
    
	if(keyword==null || keyword.isBlank()) {
		model.addAttribute("podcast",podcast);
	}
	
	else {
		Page<Podcast> 	 searchPodcast=podcastService.searchByFieldsName(search, search, date, search, page, size, sortBy, direction);
		model.addAttribute("podcast",searchPodcast);
		
	}
	model.addAttribute("pageSize","20");
	return"client/viewPodcast";
}

@GetMapping("/epaper")
public String ePaperCalendar(Model model) {

    List<ContentCategory> categories =
            categoryService.getAllCategoriesByDate();

    model.addAttribute("categories", categories);

    return "client/epaper";
}


@GetMapping("/epaper/{id}")
public String ePaper(@PathVariable Long id, Model model) {

    ContentCategory category = categoryService.findById(id);

    if (category == null) {
        return "redirect:/public/epaper";
    }

    List<ContentSubCategory> subCategories = category.getSubCategories()
            .stream()
            .filter(ContentSubCategory::isEnable)
            .toList();

<<<<<<< HEAD
    Map<Long, List<Article>> articleMap = new HashMap<>();

    for (ContentSubCategory subCategory : subCategories) {

        List<Article> articles =
                articleService.getArticlesBySubCategory(subCategory.getId());
=======
Map<Long, List<Article>> articleMap = new HashMap<>();

    for (ContentSubCategory subCategory : subCategories) {
    	System.out.println("SubCategories from the epaper page  "+subCategory) ;
        List<Article> articles =articleService.getArticlesBySubCategory(subCategory.getId());
>>>>>>> f773d2d (first commit)

        articleMap.put(subCategory.getId(), articles);
    }
    
<<<<<<< HEAD
    List<ContentCategory> categories =
            categoryService.getAllCategoriesByDate();
=======
    List<ContentCategory> categories = categoryService.getAllCategoriesByDate();
>>>>>>> f773d2d (first commit)

    model.addAttribute("categories", categories);

    model.addAttribute("category", category);
    model.addAttribute("subCategories", subCategories);
    model.addAttribute("articleMap", articleMap);

    return "client/epaper";
}

@GetMapping("/epaper/article/{articleId}")
public String subCategoryEPaper(@PathVariable String articleId, Model model) {	
	
	Article article =articleService.getArticleByArticleId(articleId);
	
    // Increase view count
    Long views = article.getViewCount() == null ? 0L : article.getViewCount();
    article.setViewCount(views + 1);
    articleService.saveArticle(article);
	model.addAttribute("article", article);
	
	return "client/article-view";
}








}
