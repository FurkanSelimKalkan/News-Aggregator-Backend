package portfolio.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NewsService {

    @Value("${news.api.key}")
    private String apiKey;

    private final String NEWS_API_URL = "https://newsapi.org/v2/top-headlines?country=us&apiKey=";

    public String getNews() {
        System.out.println(apiKey);
        RestTemplate restTemplate = new RestTemplate();
        String newsData = restTemplate.getForObject(NEWS_API_URL + apiKey, String.class);
        return newsData;
    }
}
