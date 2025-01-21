package com.eh.demo.repository;

import com.eh.demo.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

    @Query(nativeQuery = true, value = "SELECT * FROM USER WHERE username = ?1 AND password = ?2")
    User findByUsernameAndPassword(String username, String password);

}
