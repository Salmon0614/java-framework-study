package com.salmon.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Salmon
 * @since 2024-04-30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private String uid;
    private String username;
}
