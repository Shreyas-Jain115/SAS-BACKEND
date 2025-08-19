package com.shreyas.SAS.Service;


import com.shreyas.SAS.DTO.RequestKey;
import com.shreyas.SAS.DTO.RequestType;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


@Service
public class RedisService {

    @CachePut(value = "tempCommands", key = "#key.toString()")
    public String storeCommand(RequestKey key, String command) {
        return command;
    }

    @Cacheable(value = "tempCommands", key = "#key.toString()")
    public String getCommand(RequestKey key) {
        return null; // will return from cache if exists
    }

    @CacheEvict(value = "tempCommands", key = "#key.toString()")
    public void removeCommand(RequestKey key) {
        // evicts cache
    }

}


//anotherway is (one way is above implementation+expiraation time in another file using config)
//put this in config file and get object (for more control)

//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
//@Configuration
//public class RedisConfig {
//
//    @Bean
//    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
//        RedisTemplate<String, String> template = new RedisTemplate<>();
//        template.setConnectionFactory(factory);
//
//        // Use simple String serialization (custom key is stringified)
//        template.setKeySerializer(new StringRedisSerializer());
//        template.setValueSerializer(new StringRedisSerializer());
//        return template;
//    }
//}

//then can use it like hashmap

//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Service;
//
//import java.time.Duration;
//
//@Service
//public class TempCommandRedisService {
//
//    private final RedisTemplate<String, String> redisTemplate;
//    private static final Duration TTL = Duration.ofMinutes(5);
//
//    public TempCommandRedisService(RedisTemplate<String, String> redisTemplate) {
//        this.redisTemplate = redisTemplate;
//    }
//
//    public void store(String userId, RequestType type, String command) {
//        RequestKey key = RequestKey.of(userId, type);
//        redisTemplate.opsForValue().set(key.toString(), command, TTL);
//    }
//
//    public String get(String userId, RequestType type) {
//        RequestKey key = RequestKey.of(userId, type);
//        return redisTemplate.opsForValue().get(key.toString());
//    }
//
//    public void remove(String userId, RequestType type) {
//        RequestKey key = RequestKey.of(userId, type);
//        redisTemplate.delete(key.toString());
//    }
//
//    public boolean validateAndRemove(String userId, RequestType type, String expectedCommand) {
//        String actual = get(userId, type);
//        if (expectedCommand.equals(actual)) {
//            remove(userId, type);
//            return true;
//        }
//        return false;
//    }
//}

