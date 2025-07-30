
public class SecurityUtils {
    private static final String AUTHENTICATED_USER_NOT_FOUND = "Authenticated user not found in the database.";
    private static final String TASK_NOT_FOUND_OR_NO_PERMISSION = "Task not found with id: %d, or you do not have permission to access it.";

    public static String getCurrentUserEmail() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getName)
                .orElseThrow(() -> new SecurityException("No authentication found in context"));
    }

    public static void validateTaskAccess(Long taskId, String userEmail, String ownerEmail) {
        if (!userEmail.equals(ownerEmail)) {
            throw new TaskNotFoundException(String.format(TASK_NOT_FOUND_OR_NO_PERMISSION, taskId));
        }
    }
}