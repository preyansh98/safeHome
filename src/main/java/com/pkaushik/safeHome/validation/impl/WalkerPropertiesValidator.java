package com.pkaushik.safeHome.validation.impl;

import com.pkaushik.safeHome.validation.WalkerPropertiesValidationIF;
import org.springframework.stereotype.Component;

@Component
public class WalkerPropertiesValidator implements WalkerPropertiesValidationIF {

    @Override
    public boolean validateRating(double rating) {
        return (rating >= 0 && rating <=5);
    }
}
