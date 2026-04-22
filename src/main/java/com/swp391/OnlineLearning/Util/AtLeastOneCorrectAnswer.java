package com.swp391.OnlineLearning.util;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
// ÄÃ¢y lÃ  dÃ²ng quan trá»ng nháº¥t, nÃ³ liÃªn káº¿t annotation nÃ y vá»›i lá»›p xá»­ lÃ½ logic
@Constraint(validatedBy = AtLeastOneCorrectAnswerValidator.class)
// Annotation nÃ y cÃ³ thá»ƒ Ä‘Æ°á»£c dÃ¹ng trÃªn trÆ°á»ng (FIELD) hoáº·c phÆ°Æ¡ng thá»©c (METHOD)
@Target({ElementType.FIELD, ElementType.METHOD})
// Annotation nÃ y sáº½ Ä‘Æ°á»£c giá»¯ láº¡i lÃºc runtime Ä‘á»ƒ framework cÃ³ thá»ƒ xá»­ lÃ½
@Retention(RetentionPolicy.RUNTIME)
public @interface AtLeastOneCorrectAnswer {

    // ThÃ´ng bÃ¡o lá»—i máº·c Ä‘á»‹nh khi validation tháº¥t báº¡i
    String message() default "Pháº£i cÃ³ Ã­t nháº¥t má»™t Ä‘Ã¡p Ã¡n Ä‘Ãºng";

    // Boilerplate báº¯t buá»™c cho má»i custom validation annotation
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
