package task.manager.task_manager.common.responses;

public record JSendSuccessResponse<T>(String status, T data) {

    private static final String SUCCESS_STATUS = "success";


    public static <T> JSendSuccessResponse<T> of(T data) {
        return new JSendSuccessResponse<>(SUCCESS_STATUS, data);
    }


    public static JSendSuccessResponse<Void> empty() {
        return new JSendSuccessResponse<>(SUCCESS_STATUS, null);
    }
}