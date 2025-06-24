package task.manager.task_manager.common.responses;


public record ApiSuccessResponse<T>(String status, T data) {

    private static final String SUCCESS_STATUS = "success";

    public static <T> ApiSuccessResponse<T> of(T data) {
        return new ApiSuccessResponse<>(SUCCESS_STATUS, data);
    }

    public static ApiSuccessResponse<Void> empty() {
        return new ApiSuccessResponse<>(SUCCESS_STATUS, null);
    }
}