package task.manager.task_manager.auth.user;

import task.manager.task_manager.auth.InvalidRoleException;

import java.util.stream.Stream;

public enum Role {
    USER,
    ADMIN;


    public static Role fromString(String text) {
        return Stream.of(Role.values())
                .filter(role -> role.name().equalsIgnoreCase(text))
                .findFirst()
                .orElseThrow(() -> new InvalidRoleException(String.format("Invalid role: '%s'.", text)));
    }
}