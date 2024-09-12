package com.example.todo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseStatus {

    OK(200, "OK"),
    SAVED(200, "Successfully saved"),
    DELETED(200, "Successfully deleted"),
    UPDATED(200, "Successfully updated");

    private final int code;
    private final String message;
}
