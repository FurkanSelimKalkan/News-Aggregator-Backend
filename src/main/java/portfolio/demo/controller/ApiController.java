package portfolio.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import portfolio.demo.service.NewsCacheService;
import portfolio.demo.service.NewsService;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private NewsCacheService newsCacheService;

    @GetMapping("/public")
    public String getMessage() {
        return "Hello";
    }

    @GetMapping("/private")
    public String secured() {
        System.out.printf("Forbidden route works");
        return "Hello, Secured";
    }

    @GetMapping("/news")
    public String getNews() {
        return newsService.getNews();
    }
}
