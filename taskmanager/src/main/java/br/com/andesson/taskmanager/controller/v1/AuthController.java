package br.com.andesson.taskmanager.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.andesson.taskmanager.controller.v1.util.ResultError;
import br.com.andesson.taskmanager.domain.user.dto.UserLoginResponseDto;
import br.com.andesson.taskmanager.domain.user.dto.UserPostRequestDto;
import br.com.andesson.taskmanager.domain.user.model.User;
import br.com.andesson.taskmanager.domain.user.repository.UserRepository;
import br.com.andesson.taskmanager.domain.user.service.UserService;
import br.com.andesson.taskmanager.infrastructure.service.TokenService;
import br.com.andesson.taskmanager.infrastructure.util.ObjectMapperUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * REST controller for handling user authentication and registration.
 */
@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapperUtil objectMapperUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    /**
     * Endpoint for user login.
     *
     * @param body the user login request body
     * @return ResponseEntity with user login response or error
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserPostRequestDto body) {
        User user = userRepository.findUserByUsername(body.username())
                                  .orElseThrow(() -> new RuntimeException("User not found"));

        if (passwordEncoder.matches(body.password(), user.getPassword())) {
            String token = tokenService.generateToken(user);
            return ResponseEntity.ok(new UserLoginResponseDto(user.getUsername(), token));
        }        

        return ResponseEntity.badRequest().build();
    }
    
    /**
     * Endpoint for user registration.
     *
     * @param userDTO the user registration request body
     * @param result the binding result
     * @return ResponseEntity with created user or validation errors
     */
    @PostMapping(path = "/register", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserPostRequestDto userDTO, BindingResult result) {
        return result.hasErrors()
                ? ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResultError.getResultErrors(result))
                : ResponseEntity.status(HttpStatus.CREATED)
                        .body(userService.saveUser(objectMapperUtil.map(userDTO, new User())));
    }
}
