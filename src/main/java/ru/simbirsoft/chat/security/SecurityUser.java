package ru.simbirsoft.chat.security;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
public class SecurityUser implements UserDetails {

    private String login;
    private String password;
    private List<SimpleGrantedAuthority> authorities;
    private boolean isBlock;

    @Autowired
    public SecurityUser(String login, String password, List<SimpleGrantedAuthority> authorities, boolean isBlock) {
        this.login = login;
        this.password = password;
        this.authorities = authorities;
        this.isBlock = isBlock;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !isBlock;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isBlock;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !isBlock;
    }

    @Override
    public boolean isEnabled() {
        return !isBlock;
    }

}
