package com.example.tm.exception;

/** Gốc của mọi lỗi nghiệp vụ. Thông điệp tiếng Việt, hiển thị thẳng cho người dùng. */
public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
