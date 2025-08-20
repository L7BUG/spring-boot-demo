package com.l7bug;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * UserRepository
 *
 * @author Administrator
 * @since 2025/8/19 12:12
 */
public interface UserRepository extends JpaRepository<User, Long> {

}
