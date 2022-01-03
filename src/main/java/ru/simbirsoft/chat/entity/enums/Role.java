package ru.simbirsoft.chat.entity.enums;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.simbirsoft.chat.entity.enums.permission.Permission;

import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    USER(Set.of(Permission.CLIENT)),
    MODERATOR(Set.of(Permission.MODERATOR)),
    ADMIN(Set.of(Permission.ADMIN));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}
