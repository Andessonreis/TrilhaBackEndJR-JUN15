package br.com.andesson.taskmanager.domain.task.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.andesson.taskmanager.domain.task.model.Task;

/**
 * Repository interface for Task entities.
 * <p>
 * Provides CRUD operations for managing Task entities.
 * </p>
 */
@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {
}
