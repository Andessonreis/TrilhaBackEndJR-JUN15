package br.com.andesson.taskmanager.domain.task.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.andesson.taskmanager.domain.task.dto.TaskPutRequestDto;
import br.com.andesson.taskmanager.domain.task.dto.TaskResponseDto;
import br.com.andesson.taskmanager.domain.task.model.Task;
import br.com.andesson.taskmanager.domain.task.repository.TaskRepository;
import br.com.andesson.taskmanager.infrastructure.exception.BusinessException;
import br.com.andesson.taskmanager.infrastructure.exception.BusinessExceptionMessage;
import br.com.andesson.taskmanager.infrastructure.util.ObjectMapperUtil;

/**
 * Service class for managing tasks.
 */
@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ObjectMapperUtil objectMapperUtil;

    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);

    /**
     * Creates and saves a new task.
     *
     * @param task the task to be saved
     * @return the saved task as TaskResponseDto
     * @throws BusinessException if a task with the same id already exists
     */
    public TaskResponseDto saveTask(Task task) {
        if (task.getId() == null) {
            Task savedTask = this.taskRepository.save(task);
            return objectMapperUtil.map(savedTask, TaskResponseDto.class);
        } else {
            return Optional.of(task)
                    .filter(tas -> !this.taskRepository.existsById(tas.getId()))
                    .map(tas -> {
                        Task savedTask = this.taskRepository.save(task);
                        return objectMapperUtil.map(savedTask, TaskResponseDto.class);
                    })
                    .orElseThrow(() -> new BusinessException(
                        BusinessExceptionMessage.ATTRIBUTE_VALUE_ALREADY_EXISTS.getMessageValueAlreadyExists("task")));
        }
    }

    /**
     * Updates a task and returns the corresponding TaskResponseDto.
     *
     * @param taskDto the task to be updated
     * @return the updated task as TaskResponseDto
     * @throws BusinessException if the task does not exist
     */
    public TaskResponseDto updateTask(TaskPutRequestDto taskDto) {
        logger.info("Updating task with ID: {}", taskDto.getId());

        return Optional.ofNullable(taskDto.getId())
                .filter(taskRepository::existsById)
                .map(id -> {
                    Task task = objectMapperUtil.map(taskDto, Task.class);
                    Task savedTask = taskRepository.save(task);
                    return objectMapperUtil.map(savedTask, TaskResponseDto.class);
                })
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));
    }

    /**
     * Deletes a task by its ID.
     *
     * @param id the ID of the task to be deleted
     * @return the deleted task as TaskResponseDto
     * @throws BusinessException if the task does not exist
     */
    public TaskResponseDto deleteTask(Long id) {
        return this.taskRepository.findById(id)
                .map(task -> {
                    taskRepository.delete(task);
                    return objectMapperUtil.map(task, TaskResponseDto.class);
                })
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));
    }

    /**
     * Retrieves all tasks.
     *
     * @return a list of all tasks as TaskResponseDto
     */
    public List<TaskResponseDto> getAllTasks() {
        Iterable<Task> tasksIterable = this.taskRepository.findAll();
        Collection<Task> tasksCollection = new ArrayList<>();
        tasksIterable.forEach(tasksCollection::add);

        return objectMapperUtil.mapAll(tasksCollection, TaskResponseDto.class);
    }
}
