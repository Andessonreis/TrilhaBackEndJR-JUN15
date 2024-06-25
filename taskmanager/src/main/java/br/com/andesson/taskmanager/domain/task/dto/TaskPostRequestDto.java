package br.com.andesson.taskmanager.domain.task.dto;

import br.com.andesson.taskmanager.domain.status.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO for receiving data from requests directed to the 'Task' entity.
 */
public record TaskPostRequestDto(

        @JsonProperty("name")
        @NotNull(message = "Name is mandatory.")
        @NotBlank(message = "Name cannot be blank.")
        @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters.")
        String name,

        @JsonProperty("status")
        @NotNull(message = "Status is mandatory.")
        Status status
) {
}
