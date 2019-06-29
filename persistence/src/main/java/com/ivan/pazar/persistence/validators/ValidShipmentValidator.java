package com.ivan.pazar.persistence.validators;


import com.ivan.pazar.domain.model.enums.Shipment;
import com.ivan.pazar.persistence.validation_annotations.ValidShipment;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ValidShipmentValidator implements ConstraintValidator<ValidShipment, String> {

    @Override
    public boolean isValid(String shipment, ConstraintValidatorContext context) {
        return shipment != null && Arrays.stream(Shipment.values())
                .map(Enum::name)
                .collect(Collectors.toList())
                .contains(shipment);
    }
}
