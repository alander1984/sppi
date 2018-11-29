package com.egartech.sppi.repo;

import com.egartech.sppi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsernameAndActivated(String username, Boolean activated);
    User findByUsernameAndFirstPasswordAndActivated(String username, String firstPassword, Boolean activated);
    List<User> findByIsSelfRegistered(Boolean isSelfRegistered);
    List<User> findByUsername(String username);
}
