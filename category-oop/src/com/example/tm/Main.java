package com.example.tm;

import com.example.tm.exception.BusinessException;
import com.example.tm.model.Category;
import com.example.tm.model.Task;
import com.example.tm.repository.CategoryRepository;
import com.example.tm.repository.InMemoryCategoryRepository;
import com.example.tm.repository.InMemoryTaskRepository;
import com.example.tm.repository.TaskRepository;
import com.example.tm.service.CategoryService;

import java.time.LocalDate;
import java.time.LocalTime;

/** Chạy thử lần lượt 4 mục của dòng "category". */
public class Main {

    public static void main(String[] args) {
        CategoryRepository categoryRepo = new InMemoryCategoryRepository();
        TaskRepository taskRepo = new InMemoryTaskRepository();
        CategoryService service = new CategoryService(categoryRepo, taskRepo);

        // Dữ liệu task có sẵn để thử phần "gán"
        Task report = taskRepo.save(new Task("Viết báo cáo đồ án Java", LocalDate.of(2026, 10, 20), LocalTime.of(9, 0)));
        Task gym = taskRepo.save(new Task("Tập gym", LocalDate.of(2026, 10, 18), LocalTime.of(18, 30)));

        title("1. THÊM category");
        Category study = service.addCategory("Học tập", "Bài tập, đồ án, ôn thi");
        Category health = service.addCategory("Sức khỏe", "Thể thao, ăn uống");
        Category temp = service.addCategory("Tạm", "Sẽ xóa sau");
        service.getAllCategories().forEach(System.out::println);
        tryRun("Thêm trùng tên", () -> service.addCategory("học tập", ""));
        tryRun("Thêm tên rỗng", () -> service.addCategory("   ", ""));

        title("2. SỬA category");
        System.out.println(service.updateCategory(study.getId(), "Học tập & Đồ án", "Mọi việc liên quan đến trường"));
        tryRun("Sửa thành tên đã có", () -> service.updateCategory(study.getId(), "Sức khỏe", ""));
        tryRun("Sửa category không tồn tại", () -> service.updateCategory(99L, "X", ""));

        title("3. XÓA category");
        service.deleteCategory(temp.getId());
        System.out.println("Đã xóa 'Tạm'. Còn lại:");
        service.getAllCategories().forEach(System.out::println);
        tryRun("Xóa category không tồn tại", () -> service.deleteCategory(temp.getId()));

        title("4. GÁN category cho task");
        System.out.println(service.assignCategoryToTask(report.getId(), study.getId()));
        System.out.println(service.assignCategoryToTask(gym.getId(), health.getId()));
        tryRun("Gán cho task không tồn tại", () -> service.assignCategoryToTask(99L, study.getId()));
        tryRun("Gán category không tồn tại", () -> service.assignCategoryToTask(report.getId(), 99L));

        title("Kiểm tra chéo: xóa category đã được gán cho task");
        tryRun("Xóa category đang có task", () -> service.deleteCategory(study.getId()));
    }

    private static void title(String text) {
        System.out.println("\n=== " + text + " ===");
    }

    private static void tryRun(String label, Runnable action) {
        try {
            action.run();
            System.out.println("[?] " + label + ": không có lỗi (không mong đợi)");
        } catch (BusinessException e) {
            System.out.println("[Lỗi đúng dự kiến] " + label + " -> " + e.getMessage());
        }
    }
}
