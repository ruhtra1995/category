package com.example.tm.repository;

import com.example.tm.model.Category;

import java.util.List;
import java.util.Optional;

/** Trừu tượng hóa nơi lưu Category. Service chỉ biết interface này, không biết lưu ở đâu. */
public interface CategoryRepository {
    Category save(Category category);

    Optional<Category> findById(Long id);

    Optional<Category> findByName(String name);

    List<Category> findAll();

    void deleteById(Long id);
}
