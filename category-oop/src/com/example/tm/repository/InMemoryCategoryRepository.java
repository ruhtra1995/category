package com.example.tm.repository;

import com.example.tm.model.Category;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/** Cài đặt lưu trong bộ nhớ. Muốn dùng MySQL chỉ cần viết lớp khác implements CategoryRepository. */
public class InMemoryCategoryRepository implements CategoryRepository {

    private final Map<Long, Category> store = new LinkedHashMap<>();
    private final AtomicLong sequence = new AtomicLong(0);

    @Override
    public Category save(Category category) {
        if (category.getId() == null) {
            category.assignId(sequence.incrementAndGet());
        }
        store.put(category.getId(), category);
        return category;
    }

    @Override
    public Optional<Category> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Category> findByName(String name) {
        return store.values().stream()
                .filter(c -> c.getName().equalsIgnoreCase(name.trim()))
                .findFirst();
    }

    @Override
    public List<Category> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void deleteById(Long id) {
        store.remove(id);
    }
}
