package com.andband.auth.web;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("accountId") String accountId) {
        userService.registerUser(username, password, accountId);
    }

    @PostMapping("/enable")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void enableUser(@RequestParam("accountId") String accountId) {
        userService.enableUser(accountId);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@RequestParam("accountId") String accountId) {
        userService.deleteUser(accountId);
    }

}
