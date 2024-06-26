package br.com.andesson.taskmanager.domain.user.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.andesson.taskmanager.domain.user.dto.UserSimpleResponseDto;
import br.com.andesson.taskmanager.domain.user.model.User;
import br.com.andesson.taskmanager.domain.user.repository.UserRepository;
import br.com.andesson.taskmanager.infrastructure.exception.BusinessException;
import br.com.andesson.taskmanager.infrastructure.exception.BusinessExceptionMessage;
import br.com.andesson.taskmanager.infrastructure.util.ObjectMapperUtil;

/**
 * Service class for managing user-related operations.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapperUtil objectMapperUtil;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Saves a user in the database.
     *
     * @param user The user object to save.
     * @return The saved user response DTO.
     * @throws BusinessException If the user with the same ID already exists.
     */
    public UserSimpleResponseDto saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        return Optional.of(user)
                .filter(us -> us.getId() == null || !this.userRepository.existsById(us.getId()))
                .map(us -> objectMapperUtil.map(this.userRepository.save(us), UserSimpleResponseDto.class))
                .orElseThrow(() -> new BusinessException(
                        BusinessExceptionMessage.ATTRIBUTE_VALUE_ALREADY_EXISTS.getMessageValueAlreadyExists("user")));
    }

}
