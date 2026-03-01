package ru.gelman.user_crud_service.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gelman.user_crud_service.dto.CreateClientRq;
import ru.gelman.user_crud_service.dto.UserDto;
import ru.gelman.user_crud_service.entity.Session;
import ru.gelman.user_crud_service.entity.User;
import ru.gelman.user_crud_service.mapper.UserMapper;
import ru.gelman.user_crud_service.service.UserService;

import java.util.UUID;

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

    @PostMapping("/auth")
    public ResponseEntity<Session> login(@RequestBody CreateClientRq loginData, HttpServletRequest request) {
        String login = loginData.login();
        String password = loginData.password();
        log.info("login user {}", login);
        if (service.hasUser(login, password)) {
            String token = UUID.randomUUID().toString();
            request.getSession().setAttribute("authToken", token);
            log.info("Auth successful. Created session for user {} with token {}", login, token);
            return ResponseEntity.ok(new Session(token));
        }

        log.info("auth failed. Invalid user name or password");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
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
