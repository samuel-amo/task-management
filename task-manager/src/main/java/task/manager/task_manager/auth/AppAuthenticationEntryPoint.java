package task.manager.task_manager.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import task.manager.task_manager.common.responses.ApiErrorResponse;
import task.manager.task_manager.common.exceptions.ErrorResponseWriter;
import task.manager.task_manager.common.responses.JSendFailResponse;

import java.io.IOException;
import java.util.Map;

@Component
public class AppAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(AppAuthenticationEntryPoint.class);
    private final ErrorResponseWriter errorResponseWriter;

    public AppAuthenticationEntryPoint(ErrorResponseWriter errorResponseWriter) {
        this.errorResponseWriter = errorResponseWriter;
    }


    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        logger.warn("Unauthorized access attempt to '{}' - Reason: {}", request.getRequestURI(), authException.getMessage());


        JSendFailResponse failResponse = new JSendFailResponse(
                Map.of("authentication", "You are not authenticated to perform this action.")
        );

        errorResponseWriter.write(response, HttpServletResponse.SC_UNAUTHORIZED, failResponse);
    }
}

