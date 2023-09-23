package portfolio.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NewsService {

    private static final Logger logger = LoggerFactory.getLogger(NewsService.class);

    @Value("${news.api.key}")
    private String apiKey;

    private final String NEWS_API_URL = "https://newsapi.org/v2/top-headlines?country=us&apiKey=";

    @Autowired
    private NewsCacheService newsCacheService;

    public String getNews() {
        // Check if news data is in the cache
        String cachedNews = newsCacheService.getNews();
        logger.info(cachedNews);

        if(cachedNews != null) {
            logger.info("Found and returned in cache");
            return cachedNews;
        } else {
            RestTemplate restTemplate = new RestTemplate();
            String newsData = restTemplate.getForObject(NEWS_API_URL + apiKey, String.class);
            newsCacheService.cacheNews(newsData);

            logger.info("No cache found, created new cache");
            return newsData;
        }
    }
}
