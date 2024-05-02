package com.salmon.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Salmon
 * @since 2024-05-02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest implements Serializable {
    private String uid;
}
