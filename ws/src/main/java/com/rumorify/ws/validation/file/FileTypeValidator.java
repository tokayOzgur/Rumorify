package com.rumorify.ws.validation.file;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

import com.rumorify.ws.file.FileService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FileTypeValidator implements ConstraintValidator<FileType, String> {
    private final FileService fileService;

    String[] types;

    @Override
    public void initialize(FileType constraintAnnotation) {
        this.types = constraintAnnotation.types();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        String type = fileService.getFileType(value);
        for (String t : types)
            if (type.contains(t))
                return true;

        String validTypes = Arrays.stream(types).collect(Collectors.joining(", "));
        context.disableDefaultConstraintViolation();
        HibernateConstraintValidatorContext hibernateContext = context
                .unwrap(HibernateConstraintValidatorContext.class);
        hibernateContext.addMessageParameter("types", validTypes);
        hibernateContext.buildConstraintViolationWithTemplate("{rumorify.ws.validation.file.FileType.message}")
                .addConstraintViolation();
        return false;
    }

}
