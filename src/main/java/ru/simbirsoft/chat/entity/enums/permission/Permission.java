package ru.simbirsoft.chat.entity.enums.permission;

public enum Permission {
    CLIENT("CLIENT"),
    MODERATOR("MODERATOR"),
    ADMIN("ADMIN");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
