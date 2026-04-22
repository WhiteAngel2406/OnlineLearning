package com.swp391.OnlineLearning.util;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidMediaTypeValidator.class) // LiÃªn káº¿t vá»›i logic validator
@Target({ElementType.TYPE}) // Quan trá»ng: Chá»‰ Ä‘á»‹nh annotation nÃ y dÃ¹ng cho CLASS
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidMediaType {

    // ThÃ´ng bÃ¡o lá»—i máº·c Ä‘á»‹nh
    String message() default "Loáº¡i file upload khÃ´ng khá»›p vá»›i MediaType Ä‘Ã£ chá»n.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
