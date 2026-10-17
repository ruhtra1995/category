package com.example.tm.repository;

import com.example.tm.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    Task save(Task task);

    Optional<Task> findById(Long id);

    List<Task> findAll();

    /** Có task nào đang dùng category này không? */
    boolean existsByCategoryId(Long categoryId);
}
