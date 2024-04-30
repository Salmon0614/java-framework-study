package com.salmon.model;

import java.io.Serializable;

/**
 * 用户
 *
 * @author Salmon
 * @since 2024-04-25
 */
public class User implements Serializable {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
