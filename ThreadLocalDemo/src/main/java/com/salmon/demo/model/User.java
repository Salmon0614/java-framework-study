package com.salmon.demo.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户
 *
 * @author Salmon
 * @since 2024-05-25
 */
@Data
public class User implements Serializable {
    private Long id;
    private String name;
}
