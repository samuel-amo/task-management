package task.manager.task_manager.common.responses;

public record JSendErrorResponse(String status, String message) {
    public JSendErrorResponse(String message) {
        this("error", message);
    }
}
