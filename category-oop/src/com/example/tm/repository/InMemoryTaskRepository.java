package com.example.tm.repository;

import com.example.tm.model.Task;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryTaskRepository implements TaskRepository {

    private final Map<Long, Task> store = new LinkedHashMap<>();
    private final AtomicLong sequence = new AtomicLong(0);

    @Override
    public Task save(Task task) {
        if (task.getId() == null) {
            task.assignId(sequence.incrementAndGet());
        }
        store.put(task.getId(), task);
        return task;
    }

    @Override
    public Optional<Task> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Task> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public boolean existsByCategoryId(Long categoryId) {
        return store.values().stream()
                .anyMatch(t -> t.getCategory() != null && categoryId.equals(t.getCategory().getId()));
    }
}
