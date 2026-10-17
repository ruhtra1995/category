package com.example.tm.service;

import com.example.tm.exception.ConflictException;
import com.example.tm.exception.NotFoundException;
import com.example.tm.model.Category;
import com.example.tm.model.Task;
import com.example.tm.repository.CategoryRepository;
import com.example.tm.repository.TaskRepository;

import java.util.List;

/**
 * Nghiệp vụ của domain "category".
 * Phụ thuộc vào interface (CategoryRepository, TaskRepository) chứ không phụ thuộc cài đặt cụ thể.
 */
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final TaskRepository taskRepository;

    public CategoryService(CategoryRepository categoryRepository, TaskRepository taskRepository) {
        this.categoryRepository = categoryRepository;
        this.taskRepository = taskRepository;
    }

    // ------------------------------------------------------------------
    // 1. Thêm category
    // ------------------------------------------------------------------
    public Category addCategory(String name, String description) {
        Category category = new Category(name, description);   // Category tự kiểm tra tên rỗng
        ensureNameIsAvailable(category.getName(), null);
        return categoryRepository.save(category);
    }

    // ------------------------------------------------------------------
    // 2. Sửa category
    // ------------------------------------------------------------------
    public Category updateCategory(Long id, String newName, String newDescription) {
        Category category = getCategory(id);

        // Kiểm tra trước, đổi sau, để lỗi thì không làm hỏng object đang có.
        String candidate = newName == null ? "" : newName.trim();
        if (!candidate.isEmpty()) {
            ensureNameIsAvailable(candidate, id);
        }
        category.rename(newName);                // ném lỗi nếu tên rỗng
        category.changeDescription(newDescription);
        return categoryRepository.save(category);
    }

    // ------------------------------------------------------------------
    // 3. Xóa category
    // ------------------------------------------------------------------
    public void deleteCategory(Long id) {
        Category category = getCategory(id);
        if (taskRepository.existsByCategoryId(category.getId())) {
            throw new ConflictException("Không thể xóa category \"" + category.getName()
                    + "\" vì vẫn còn công việc đang dùng");
        }
        categoryRepository.deleteById(category.getId());
    }

    // ------------------------------------------------------------------
    // 4. Gán category cho task
    // ------------------------------------------------------------------
    public Task assignCategoryToTask(Long taskId, Long categoryId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("task", taskId));
        Category category = getCategory(categoryId);

        task.assignCategory(category);
        return taskRepository.save(task);
    }

    // ------------------------------------------------------------------
    // Hỗ trợ
    // ------------------------------------------------------------------
    public Category getCategory(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("category", id));
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    /** Tên category không được trùng (không phân biệt hoa thường). selfId = category đang sửa. */
    private void ensureNameIsAvailable(String name, Long selfId) {
        categoryRepository.findByName(name)
                .filter(existing -> !existing.getId().equals(selfId))
                .ifPresent(existing -> {
                    throw new ConflictException("Category \"" + existing.getName() + "\" đã tồn tại");
                });
    }
}
