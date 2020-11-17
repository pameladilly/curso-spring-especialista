package io.github.pameladilly.validation.constraintvalidation;

import io.github.pameladilly.validation.NotEmptyList;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class NotEmptyListValidation implements ConstraintValidator<NotEmptyList, List> {
    @Override
    public void initialize(NotEmptyList constraintAnnotation) {

    }

    @Override
    public boolean isValid(List list, ConstraintValidatorContext ConstraintValidatorContext) {

        return list != null && !list.isEmpty();
    }
}
