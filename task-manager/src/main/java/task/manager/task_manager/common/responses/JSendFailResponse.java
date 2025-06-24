package task.manager.task_manager.common.responses;

import java.util.Map;

public record JSendFailResponse(String status, Map<String, String> data) {
    public JSendFailResponse(Map<String, String> data) {
        this("fail", data);
    }
}
