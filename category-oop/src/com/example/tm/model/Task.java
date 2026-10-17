package com.example.tm.model;

import com.example.tm.exception.BusinessException;

import java.time.LocalDate;
import java.time.LocalTime;

/** Entity: một công việc. Mỗi task thuộc tối đa một Category (quan hệ N-1). */
public class Task {

    private Long id;
    private String title;
    private LocalDate date;
    private LocalTime time;
    private Category category;     // có thể null = chưa phân loại

    public Task(String title, LocalDate date, LocalTime time) {
        if (title == null || title.isBlank()) {
            throw new BusinessException("Tiêu đề task không được để trống");
        }
        this.title = title.trim();
        this.date = date;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public void assignId(Long id) {
        if (this.id != null) {
            throw new BusinessException("Task đã có id, không thể cấp lại");
        }
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public Category getCategory() {
        return category;
    }

    /** Gán (hoặc đổi) category cho task. */
    public void assignCategory(Category category) {
        if (category == null) {
            throw new BusinessException("Category không được null");
        }
        this.category = category;
    }

    public boolean belongsTo(Category c) {
        return category != null && category.equals(c);
    }

    @Override
    public String toString() {
        return "Task{id=" + id + ", title='" + title + "', " + date + " " + time
                + ", category=" + (category == null ? "—" : category.getName()) + "}";
    }
}
