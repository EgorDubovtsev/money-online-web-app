package com.test.service.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class Errors {
    private static final String UNDEFINED_ERROR = "Прозошла ошибка. Обратитесь к администратору";
    private final List<String> errors = new ArrayList<>();

    public Errors addError(String error) {
        errors.add(error);
        return this;
    }

    public Errors undefinedError(){
        errors.add(UNDEFINED_ERROR);
        return this;
    }

    @Override
    public String toString() {
        return String.join(", ", errors);
    }
}
