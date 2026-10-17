package com.example.tm.exception;

/** Không tìm thấy đối tượng theo id. */
public class NotFoundException extends BusinessException {
    public NotFoundException(String what, Long id) {
        super("Không tìm thấy " + what + " #" + id);
    }
}
