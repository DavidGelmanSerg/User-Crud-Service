package ru.gelman.user_crud_service.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.gelman.user_crud_service.dto.CreateClientRq;
import ru.gelman.user_crud_service.dto.UserDto;
import ru.gelman.user_crud_service.entity.User;
import ru.gelman.user_crud_service.mapper.UserMapper;
import ru.gelman.user_crud_service.service.UserService;

@RestController
@Slf4j
public class UserController {
    private final UserMapper mapper;

    private final UserService service;

    @Autowired
    public UserController(UserMapper mapper, UserService service) {
        this.mapper = mapper;
        this.service = service;
    }

    @GetMapping("/users/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        log.info("finding user by id {}", id);
        User user = service.getById(id);
        return mapper.toUserDto(user);
    }

    @PostMapping("/users")
    public UserDto createUser(@RequestBody CreateClientRq userData) {
        log.info("creating user by login {} and password {}", userData.login(), userData.password());
        User user = service.create(userData.login(), userData.password());
        return mapper.toUserDto(user);
    }

    @PutMapping("/users/{id}")
    public UserDto updateUser(@PathVariable Long id, @RequestBody CreateClientRq userData) {
        log.info("updating user with data: {}", userData);
        User user = service.update(id, userData.login(), userData.password());
        return mapper.toUserDto(user);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id) {
        log.info("deleting user with id: {}", id);
        service.delete(id);
    }
}
