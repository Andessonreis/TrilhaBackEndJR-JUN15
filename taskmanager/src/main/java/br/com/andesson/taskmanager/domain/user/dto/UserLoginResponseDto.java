package br.com.andesson.taskmanager.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for responding to user login requests.
 * This DTO includes the username and authentication token.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginResponseDto {

    /**
     * The login username of the user.
     */
    @JsonProperty("username")
    private String username;

    /**
     * The authentication token of the user.
     */
    @JsonProperty("token")
    private String token;
}
