package com.l7bug;

import cn.hutool.core.date.DateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class AppTest {
	HashOperations<String, Object, Object> operations;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@BeforeEach
	void setUp() {
		operations = stringRedisTemplate.opsForHash();
	}

	@Test
	public void test() {
		for (int i = 0; i < 5; i++) {
			DateTime dateTime = new DateTime();
			operations.put("hello", dateTime.getTime() + "", dateTime.toString());
		}
		stringRedisTemplate.expire("hello", 180L, TimeUnit.DAYS);
		System.out.println("operations.entries(\"hello\") = " + operations.entries("hello"));
	}

	@Test
	public void showHash() {
		Set<Object> hello = operations.keys("hello");
		String key = hello.stream().findFirst().map(Object::toString).orElse("");
		operations.delete("hello", key);
		System.err.println(hello);
		System.err.println(operations.values("hello"));
	}

	@Test
	public void delete() {
		operations.delete("hello", UUID.randomUUID().toString());
	}
}