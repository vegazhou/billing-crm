package com.kt.service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.kt.common.RedisConstants;

/**
 * The Class CacheService.
 */
@Service
public class CacheService {

	/** The string redis template. */
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	/**
	 * Gets the user map by token.
	 *
	 * @param token
	 *            the token
	 * @return the user map by token
	 */
	public Map<Object, Object> getUserMapByToken(String token) {
		return stringRedisTemplate.opsForHash().entries(RedisConstants.REDIS_NAME_TOKEN + token);
	}

	/**
	 * Gets the property value by token.
	 *
	 * @param token
	 *            the token
	 * @param propertyName
	 *            the property name
	 * @return the property value by token
	 */
	public String getPropertyValueByToken(String token, String propertyName) {
		return (String) stringRedisTemplate.opsForHash().get(RedisConstants.REDIS_NAME_TOKEN + token, propertyName);
	}

	/**
	 * Gets the expire time.
	 *
	 * @param key
	 *            the key
	 * @param timeUnit
	 *            the time unit
	 * @return the expire time
	 */
	public long getExpireTime(String key, TimeUnit timeUnit) {
		return stringRedisTemplate.getExpire(key, timeUnit);
	}

	/**
	 * Sets the expire time.
	 *
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 * @param timeUnit
	 *            the time unit
	 */
	public void setExpireTime(String key, int value, TimeUnit timeUnit) {
		stringRedisTemplate.expire(key, value, timeUnit);
	}

	/**
	 * Sets the hash value.
	 *
	 * @param key
	 *            the key
	 * @param hashKey
	 *            the hash key
	 * @param hashValue
	 *            the hash value
	 */
	public void setHashValue(String key, String hashKey, String hashValue) {
		stringRedisTemplate.opsForHash().put(key, hashKey, hashValue);
	}

	/**
	 * Gets the list.
	 *
	 * @return the list
	 */
	public ListOperations<String, String> getList() {
		return stringRedisTemplate.opsForList();
	}

	/**
	 * Delete.
	 *
	 * @param key
	 *            the key
	 */
	public void delete(String key) {
		stringRedisTemplate.delete(key);
	}

	/**
	 * Gets the property value by property name.
	 *
	 * @param propertyName
	 *            the property name
	 * @return the property value by property name
	 */
	public String getPropertyValueByPropertyName(String propertyName) {
		return (String) stringRedisTemplate.opsForValue().get(propertyName);
	}
}
