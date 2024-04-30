package com.salmon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author Salmon
 * @since 2024-04-30
 */
@Service
public class OrderService {

    @Autowired
    private RestTemplate restTemplate;

    public String getOrder() {
        String result = restTemplate.getForObject("http://localhost:8080/user", String.class);
        return result;
    }
}
