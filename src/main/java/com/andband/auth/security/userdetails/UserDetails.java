package com.andband.auth.security.userdetails;

import com.andband.auth.persistence.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.stream.Collectors;

public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {

    private String username;
    private String password;
    private String accountId;
    private boolean enabled;
    private boolean accountNonLocked;
    private Collection<? extends GrantedAuthority> grantedAuthorities;

    UserDetails(User user) {
        username = user.getUsername();
        password = user.getPassword();
        accountId = user.getAccountId();
        enabled = user.isEnabled();
        accountNonLocked = !user.isAccountLocked();
        grantedAuthorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public String getAccountId() {
        return accountId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

}
