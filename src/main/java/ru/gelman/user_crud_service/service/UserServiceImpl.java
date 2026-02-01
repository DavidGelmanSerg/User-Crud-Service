package ru.gelman.user_crud_service.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gelman.user_crud_service.entity.User;
import ru.gelman.user_crud_service.exception.UserNotFoundException;
import ru.gelman.user_crud_service.repository.UserRepository;


@Slf4j
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User create(String login, String password) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);

        log.info("saving new user {} to repository", user);
        return repository.save(user);
    }

    @Override
    public User update(Long id, String login, String password) {
        User user = repository.findById(id).orElseThrow(UserNotFoundException::new);
        user.setLogin(login);
        user.setPassword(password);

        log.info("saving updated user {} to repository", user);
        return repository.save(user);
    }

    @Override
    public void delete(Long id) {
        User user = repository.findById(id).orElseThrow(UserNotFoundException::new);

        log.info("deleting user {}", user);
        repository.delete(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User getById(Long id) {
        log.info("searching user in repository by id {}", id);
        return repository.findById(id).orElseThrow(UserNotFoundException::new);
    }
}
