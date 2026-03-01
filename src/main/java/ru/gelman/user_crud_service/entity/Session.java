package ru.gelman.user_crud_service.entity;

import lombok.Data;

@Data
public class Session {
    private final String token;
}
