package portfolio.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class NewsService {

    private static final Logger logger = LoggerFactory.getLogger(NewsService.class);

    @Value("${news.api.key}")
    private String apiKey;

    private final String NEWS_API_URL = "https://newsapi.org/v2/top-headlines";
    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private NewsCacheService newsCacheService;

    private String buildNewsApiUrl() {
        return UriComponentsBuilder.fromHttpUrl(NEWS_API_URL)
                .queryParam("country", "us")
                .queryParam("apiKey", this.apiKey)
                .queryParam("pageSize", 100)
                .toUriString();
    }

    public String getNews() {
        String finalNewsUrl = buildNewsApiUrl();
        // Check if news data is in the cache

        String cachedNews = newsCacheService.getNews();

        if(cachedNews != null) {
            logger.info("Found and returned in cache");
            return cachedNews;
        } else {
            String newsData = restTemplate.getForObject(finalNewsUrl, String.class);
            newsCacheService.cacheNews(newsData);
            logger.info("No cache found, created new cache");
            return newsData;
        }
    }
}
