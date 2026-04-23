package com.fc2o.api.validator;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ValidatorHandler {

    private final Validator validator;

    public void validate(Object request) {
        Errors errors = new BeanPropertyBindingResult(request, Object.class.getName());
        this.validator.validate(request, errors);
        if (!errors.getAllErrors().isEmpty()) {
            throw new IllegalArgumentException(buildFailure(errors));
        }
    }

    private String buildFailure(Errors errors) {
        return errors.getFieldErrors().parallelStream().map(FieldError::getField).collect(Collectors.joining(" | "));
    }
}
