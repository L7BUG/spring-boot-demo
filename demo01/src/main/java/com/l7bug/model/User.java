package com.l7bug.model;

import com.l7bug.config.DiffField;
import lombok.Getter;
import lombok.Setter;

/**
 * User
 *
 * @author Administrator
 * @since 2025/4/15 11:59
 */
@Getter
@Setter
public class User {
    @DiffField("姓名")
    private String name;
    @DiffField("城市")
    private String city;
    // @DiffField("电话")
    private Phone phone;
}
