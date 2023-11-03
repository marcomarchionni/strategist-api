package com.marcomarchionni.ibportfolio.controllers.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class AssetCategoryValidator implements ConstraintValidator<AssetCategory, String> {

    //TODO: get allowed asset categories from application properties
    final List<String> allowedAssetCategories = Arrays.asList("STK", "OPT", "FUT", "CASH");

    @Override
    public void initialize(AssetCategory assetCategoryAnnotation) {
    }

    @Override
    public boolean isValid(String assetCategory, ConstraintValidatorContext constraintValidatorContext) {
        return (assetCategory == null) || (allowedAssetCategories.contains(assetCategory));
    }
}
