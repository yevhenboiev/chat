package ru.simbirsoft.chat.entity.util;

public enum Role {
    USER("User"),
    MODERATOR("Moderator"),
    ADMIN("Admin");

    private final String title;

    Role(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
