package ru.gelman.user_crud_service.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.gelman.user_crud_service.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends ListCrudRepository<User, Long> {
    @Query(value = "SELECT u FROM User u WHERE u.login = :login and u.password = :password")
    Optional<User> findByLoginAndPassword(@Param("login") String login, @Param("password") String password);
}
