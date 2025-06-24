package task.manager.task_manager.task;

import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface TaskMapper {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "owner", ignore = true)
    Task mapToTask(TaskRequest taskRequest);


    TaskResponse mapToTaskResponse(Task task);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "owner", ignore = true)
    void updateTaskFromRequest(TaskUpdateRequest request, @MappingTarget Task task);
}