package br.com.andesson.taskmanager.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for responding to requests directed to the 'User' entity.
 * This DTO represents public user information, including the username.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class  UserSimpleResponseDto {

    /**
     * 
     * The username of the user.
     */
    @JsonProperty("username")
    private String username;

}
