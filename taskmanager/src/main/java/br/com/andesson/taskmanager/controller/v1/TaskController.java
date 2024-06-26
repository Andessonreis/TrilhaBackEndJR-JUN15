package br.com.andesson.taskmanager.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.andesson.taskmanager.controller.v1.util.ResultError;
import br.com.andesson.taskmanager.domain.task.dto.TaskPostRequestDto;
import br.com.andesson.taskmanager.domain.task.dto.TaskPutRequestDto;
import br.com.andesson.taskmanager.domain.task.dto.TaskResponseDto;
import br.com.andesson.taskmanager.domain.task.model.Task;
import br.com.andesson.taskmanager.domain.task.service.TaskService;
import br.com.andesson.taskmanager.infrastructure.util.ObjectMapperUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * REST controller for managing tasks.
 */
@RestController
@RequestMapping(path = "/api/v1")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private ObjectMapperUtil objectMapperUtil;

    /**
     * Endpoint to save a task.
     *
     * @param taskDto the task data transfer object
     * @param result the binding result
     * @return ResponseEntity with the created task or error details
     */
    @PostMapping(path = "/tasks/task", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> saveTask(@Valid @RequestBody TaskPostRequestDto taskDto, BindingResult result) {
        return result.hasErrors()
                ? ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResultError.getResultErrors(result))
                : ResponseEntity.status(HttpStatus.CREATED).body(taskService.saveTask(objectMapperUtil.map(taskDto, Task.class)));
    }

    /**
     * Endpoint to retrieve all tasks.
     *
     * @return ResponseEntity with the list of all tasks
     */
    @GetMapping(path = "/tasks", produces = "application/json")
    public ResponseEntity<?> getTasks() {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.getAllTasks());
    }

    /**
     * Endpoint to update a task.
     *
     * @param taskDto the task data transfer object
     * @param result the binding result
     * @return ResponseEntity with the updated task or error details
     */
    @PutMapping(path = "/tasks/task", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> updateTask(@Valid @RequestBody TaskPutRequestDto taskDto, BindingResult result) {
        return result.hasErrors()
                ? ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResultError.getResultErrors(result))
                : ResponseEntity.status(HttpStatus.OK).body(taskService.updateTask(taskDto));
    }

    /**
     * Endpoint to delete a task.
     *
     * @param id the id of the task to be deleted
     * @return ResponseEntity with the deleted task or error details
     */
    @DeleteMapping(path = "/tasks/task/{id}", consumes = "application/json")
    public ResponseEntity<?> deleteTask(@PathVariable("id") @NotNull Long id) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(taskService.deleteTask(id));
    }
}
