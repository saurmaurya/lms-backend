package com.srsdev.tech.lms.validations

import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class EnumValueValidator : ConstraintValidator<Enum?, Set<String>?> {
    private var annotation: Enum? = null
    override fun initialize(annotation: Enum?) {
        this.annotation = annotation
    }

    override fun isValid(
        valueForValidation: Set<String>?,
        constraintValidatorContext: ConstraintValidatorContext
    ): Boolean {
        if (valueForValidation == null)
            return true
        var result = false
        val enumValues: List<Any> = annotation!!.enumClass.java.enumConstants.map { it.name }
        for (enumValue in enumValues) {
            for (value in valueForValidation) {
                if (value == enumValue.toString() || annotation!!.ignoreCase && value.equals(
                        enumValue.toString(),
                        ignoreCase = true
                    )
                ) {
                    result = true
                    break
                }
            }
        }
        return result
    }
}