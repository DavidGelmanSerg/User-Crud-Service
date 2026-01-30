package ru.gelman.user_crud_service.service;

import ru.gelman.user_crud_service.entity.User;

public interface UserService {
    User create(String login, String password);

    User update(Long id, String login, String password);

    void delete(Long id);

    User getById(Long id);
}
