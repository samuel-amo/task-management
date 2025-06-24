package task.manager.task_manager.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import task.manager.task_manager.common.exceptions.ErrorResponseWriter;
import task.manager.task_manager.common.responses.JSendFailResponse;

import java.io.IOException;
import java.util.Map;

@Component
public class AppAccessDeniedHandler implements AccessDeniedHandler {

    private final ErrorResponseWriter errorResponseWriter;

    public AppAccessDeniedHandler(ErrorResponseWriter errorResponseWriter) {
        this.errorResponseWriter = errorResponseWriter;
    }

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {


        JSendFailResponse failResponse = new JSendFailResponse(
                Map.of("authorization", "You do not have permission to access this resource.")
        );

        errorResponseWriter.write(response, HttpServletResponse.SC_FORBIDDEN, failResponse);
    }
}
