package com.andband.auth.web;

import com.andband.auth.persistence.Role;
import com.andband.auth.persistence.User;
import com.andband.auth.persistence.UserRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private PasswordEncoder mockPasswordEncoder;

    @BeforeMethod
    public void init() {
        initMocks(this);
    }

    @Test
    public void testRegisterUser() {
        String username = "user@email.com";
        String password = "password1";
        String accountId = "accountId";
        String encodedPassword = "encodedPassword1";

        when(mockPasswordEncoder.encode(password)).thenReturn(encodedPassword);

        userService.registerUser(username, password, accountId);

        User user = new User();
        user.setUsername(username);
        user.setPassword(encodedPassword);
        user.setAccountId(accountId);
        user.setRoles(Collections.singletonList(Role.USER));

        verify(mockPasswordEncoder).encode(password);
        verify(mockUserRepository).save(refEq(user));
    }

    @Test
    public void testEnableUser() {
        String accountId = "account123";

        userService.enableUser(accountId);

        verify(mockUserRepository).enableUserWhereAccountId(accountId);
    }

    @Test
    public void testDeleteUser() {
        String accountId = "account123";

        userService.deleteUser(accountId);

        verify(mockUserRepository).deleteUserByAccountId(accountId);
    }

}
