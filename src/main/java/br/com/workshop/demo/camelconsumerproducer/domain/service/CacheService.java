package br.com.workshop.demo.camelconsumerproducer.domain.service;

import br.com.workshop.demo.camelconsumerproducer.domain.Cacheable;

public interface CacheService {

    <T> T get(Class<T> clazz, String key);

    <T> void cacheable(Cacheable cacheable);
}
