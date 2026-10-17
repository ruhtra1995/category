package com.example.tm.model;

import com.example.tm.exception.BusinessException;

import java.util.Objects;

/**
 * Entity: một nhóm dùng để phân loại công việc.
 * Đóng gói (encapsulation): field private, chỉ đổi dữ liệu qua phương thức có kiểm tra hợp lệ.
 */
public class Category {

    private Long id;               // do repository cấp khi lưu lần đầu
    private String name;
    private String description;

    public Category(String name, String description) {
        rename(name);
        changeDescription(description);
    }

    public Long getId() {
        return id;
    }

    /** Chỉ repository được gọi khi cấp id (cùng package cha nên để public có chủ đích). */
    public void assignId(Long id) {
        if (this.id != null) {
            throw new BusinessException("Category đã có id, không thể cấp lại");
        }
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public final void rename(String newName) {
        if (newName == null || newName.isBlank()) {
            throw new BusinessException("Tên category không được để trống");
        }
        this.name = newName.trim();
    }

    public final void changeDescription(String newDescription) {
        this.description = newDescription == null ? "" : newDescription.trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category other)) return false;
        return id != null && Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Category{id=" + id + ", name='" + name + "', description='" + description + "'}";
    }
}
