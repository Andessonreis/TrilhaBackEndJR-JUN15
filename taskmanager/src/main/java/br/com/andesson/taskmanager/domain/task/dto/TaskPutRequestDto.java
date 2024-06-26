package br.com.andesson.taskmanager.domain.task.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.andesson.taskmanager.domain.status.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for updating a task.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskPutRequestDto {

    /**
     * Unique identifier for the task.
     * 
     * @return the id of the task
     */
    @JsonProperty("id")
    @NotNull(message = "ID is mandatory.")
    private Long id;

    /**
     * Name of the task.
     * 
     * @return the name of the task
     */
    @JsonProperty("name")
    @NotNull(message = "Name is mandatory.")
    @NotBlank(message = "Name cannot be blank.")
    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters.")
    private String name;

    /**
     * Status of the task.
     * 
     * @return the status of the task
     */
    @JsonProperty("status")
    @NotNull(message = "Status is mandatory.")
    private Status status;
}
