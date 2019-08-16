package com.lambdaschool.todo.repository;

import com.lambdaschool.todo.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long>
{
    User findByUsername(String username);
}
