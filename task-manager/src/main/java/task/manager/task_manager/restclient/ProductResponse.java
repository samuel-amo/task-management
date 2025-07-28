package task.manager.task_manager.restclient;

public record ProductResponse(
        Long id,
        String name,
        String description,
        Double price,
        String category
) {}
