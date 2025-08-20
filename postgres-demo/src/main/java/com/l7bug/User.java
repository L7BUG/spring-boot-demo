package com.l7bug;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * User
 *
 * @author Administrator
 * @since 2025/8/19 12:11
 */
@Data
@Entity
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "username", nullable = false, length = 64)
	private String username;

	@Column(name = "password", nullable = false, length = 256)
	private String password;

	@Column(name = "create_by", nullable = false, length = 64)
	private String createBy;

	@Column(name = "update_by", nullable = false, length = 64)
	private String updateBy;

	@Column(name = "create_time", nullable = false, length = 64)
	private LocalDateTime createTime = LocalDateTime.now();
	@Column(name = "update_time", nullable = false, length = 64)
	private LocalDateTime updateTime = LocalDateTime.now();
}