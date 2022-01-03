package ru.simbirsoft.chat.entity.enums.permission;

public enum Permission {
    CLIENT("client"),
    MODERATOR("moderator"),
    ADMIN("admin");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
