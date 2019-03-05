package com.cogrammer.testcontainers.config;

import com.cogrammer.testcontainers.dto.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

import java.util.ArrayList;

@EnableCaching
@Configuration
class CacheConfiguration {

    @Primary
    @Bean
    CacheManager productsListCacheManager(RedisConnectionFactory redisConnectionFactory, ObjectMapper mapper) {
        final CollectionType type = mapper.getTypeFactory().constructCollectionType(ArrayList.class, Product.class);
        final SerializationPair<Object> serializer = SerializationPair.fromSerializer(new Jackson2JsonRedisSerializer<>(type));
        final RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(serializer);
        final RedisCacheWriter writer = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
        return new RedisCacheManager(writer, cacheConfig);
    }
}
