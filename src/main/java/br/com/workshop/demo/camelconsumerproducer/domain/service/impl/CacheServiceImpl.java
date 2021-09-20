package br.com.workshop.demo.camelconsumerproducer.domain.service.impl;

import br.com.workshop.demo.camelconsumerproducer.domain.Cacheable;
import br.com.workshop.demo.camelconsumerproducer.domain.service.CacheService;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CacheServiceImpl implements CacheService {

    private static final String SEPARATOR = ":";

    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public <T> T get(Class<T> clazz, String key) {
        Object cacheResult = redisTemplate.opsForHash().get(clazz.getName() + SEPARATOR + key, key);
        return clazz.cast(cacheResult);
    }

    @Override
    public <T> void cacheable(Cacheable cacheable) {
        String cacheKey = cacheable.getObject().getClass().getName() + SEPARATOR + cacheable.getKey();
        redisTemplate.opsForHash().putIfAbsent(cacheKey, cacheable.getKey(), cacheable.getObject());
        redisTemplate.expire(cacheKey, cacheable.getPeriod(), cacheable.getTimeUnit());
    }
}
