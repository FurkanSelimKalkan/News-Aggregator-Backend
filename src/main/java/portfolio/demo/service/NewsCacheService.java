package portfolio.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class NewsCacheService {

    private static final String CACHE_KEY = "news_data";
    private static final int expirationSeconds = 120;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void cacheNews(String newsData) {
        redisTemplate.opsForValue().set(CACHE_KEY, newsData, expirationSeconds, TimeUnit.SECONDS);
    }

    public String getNews() {
        refreshCacheTTL();
        return (String) redisTemplate.opsForValue().get(CACHE_KEY);
    }

    public void refreshCacheTTL() {
        redisTemplate.expire(CACHE_KEY, expirationSeconds, TimeUnit.SECONDS);
    }

    public void removeNews() {
        redisTemplate.delete(CACHE_KEY);
    }
}
