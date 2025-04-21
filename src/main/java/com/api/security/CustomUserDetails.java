package com.api.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;

@Getter
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {
    private final Long id;
    private final String username;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    @SuppressWarnings("RedundantMethodOverride")
    @Override public boolean isAccountNonExpired() { return true; }

    @SuppressWarnings("RedundantMethodOverride")
    @Override public boolean isAccountNonLocked() { return true; }

    @SuppressWarnings("RedundantMethodOverride")
    @Override public boolean isCredentialsNonExpired() { return true; }

    @SuppressWarnings("RedundantMethodOverride")
    @Override public boolean isEnabled() { return true; }
}
