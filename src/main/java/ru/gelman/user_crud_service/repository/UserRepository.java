package ru.gelman.user_crud_service.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import ru.gelman.user_crud_service.entity.User;

@Repository
public interface UserRepository extends ListCrudRepository<User, Long> {
}
