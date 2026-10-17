package com.example.tm.exception;

/** Thao tác xung đột với dữ liệu hiện có (trùng tên, đang được sử dụng...). */
public class ConflictException extends BusinessException {
    public ConflictException(String message) {
        super(message);
    }
}
