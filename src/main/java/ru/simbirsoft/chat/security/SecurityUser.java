package ru.simbirsoft.chat.security;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import ru.simbirsoft.chat.entity.Client;

import java.util.Collection;
import java.util.List;

@Data
public class SecurityUser implements UserDetails {

    private String login;
    private String password;
    private List<SimpleGrantedAuthority> authorities;
    private boolean isBlock;

    public static UserDetails clientToUserDetails(Client client) {
        return new User(
                client.getLogin(), client.getPassword(),
                client.isBlock(),
                client.isBlock(),
                client.isBlock(),
                client.isBlock(),
                client.getRole().getAuthorities());
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
