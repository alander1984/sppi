package com.egartech.sppi.web;

import com.egartech.sppi.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class UserController {
    @Autowired
    UserRepository userRepository;
}
