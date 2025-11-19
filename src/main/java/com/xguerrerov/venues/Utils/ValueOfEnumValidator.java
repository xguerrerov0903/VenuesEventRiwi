package com.xguerrerov.venues.Utils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.stream.Stream;

public class ValueOfEnumValidator implements ConstraintValidator<ValueOfEnum, CharSequence> {
    private List<String> acceptedValues;

    @Override
    public void initialize(ValueOfEnum annotation) {
        // Obtenemos todos los valores posibles del Enum (NORMAL, CRAZY, BORING)
        acceptedValues = Stream.of(annotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .toList();
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Dejamos que @NotNull maneje los nulos si es necesario
        }
        // Verificamos si el valor enviado está en la lista permitida
        return acceptedValues.contains(value.toString());
    }
}