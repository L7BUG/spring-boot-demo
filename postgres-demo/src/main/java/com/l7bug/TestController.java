package com.l7bug;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.TimeZone;

/**
 * TestController
 *
 * @author Administrator
 * @since 2025/8/19 14:01
 */
@RequestMapping("/test")
@RestController
public class TestController {
	private final UserRepository userRepository;

	public TestController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@GetMapping
	public List<User> all() {
		return userRepository.findAll();
	}

	@Bean
	public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
		return builder -> {
			// 序列化格式
			builder.serializers(
				new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
				new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
			);
			// 反序列化格式
			builder.deserializers(
				new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
				new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
			);
			// 设置时区（解决时差问题）
			builder.timeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		};
	}
}
