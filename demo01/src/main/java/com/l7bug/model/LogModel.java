package com.l7bug.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * LogModel
 *
 * @author Administrator
 * @since 2025/4/15 11:33
 */
@ToString
@Getter
@Setter
public class LogModel {
    private String content;
    private String id;
    private String type;
}
