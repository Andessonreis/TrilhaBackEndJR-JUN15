package br.com.andesson.taskmanager.domain.task.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.andesson.taskmanager.domain.status.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for Task.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponseDto {

    /**
     * Unique identifier for the task.
     */
    @JsonProperty("id")
    private Long id;

    /**
     * Name of the task.
     */
    @JsonProperty("name")
    private String name;

    /**
     * Status of the task.
     */
    @JsonProperty("status")
    private Status status;

    /**
     * Date and time when the task was created.
     */
    @JsonProperty("creation_date")
    private LocalDateTime creationDate;

    /**
     * Date and time when the task was last updated.
     */
    @JsonProperty("update_date")
    private LocalDateTime updateDate;
}
