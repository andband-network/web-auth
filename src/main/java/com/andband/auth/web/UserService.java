package com.andband.auth.web;

import com.andband.auth.persistence.user.Role;
import com.andband.auth.persistence.user.User;
import com.andband.auth.persistence.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    void registerUser(String username, String password, String accountId) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setAccountId(accountId);
        user.setRoles(Collections.singletonList(Role.USER));
        user.setEnabled(true);
        userRepository.save(user);
    }

}
