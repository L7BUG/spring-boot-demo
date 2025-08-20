package com.l7bug;

import cn.hutool.core.date.DateUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
class UserRepositoryTest {
	@Autowired
	private UserRepository userRepository;

	@BeforeEach
	void setUp() {
	}

	@AfterEach
	void tearDown() {
	}

	@Test
	public void test() {
		userRepository.deleteAll();
		User data = new User();
		data.setUsername("admin");
		data.setPassword("admin");
		data.setCreateBy("admin");
		data.setUpdateBy("admin");
		userRepository.save(data);
		System.out.println(data);
	}

	@Test
	public void selectAll() {
		List<User> all = userRepository.findAll();
		System.out.println(all);
	}

	@Test
	public void test1() {
		System.out.println(DateUtil.format(new Date(1755596678333L), "yyyy-MM-dd HH:mm:ss"));
	}
}