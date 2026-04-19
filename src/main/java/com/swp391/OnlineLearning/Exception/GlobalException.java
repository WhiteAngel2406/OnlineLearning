package com.swp391.OnlineLearning.Exception;

import com.swp391.OnlineLearning.model.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiResponse<?>> handleNotFound(NoSuchElementException ex) {
        ApiResponse<?> body = new ApiResponse<>(HttpStatus.NOT_FOUND, ex.getMessage(), null, null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    //xá»­ lÃ­ lá»—i tráº£ vá» do @Valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errorList = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getDefaultMessage()).collect(Collectors.toList());
        String errors = String.join("\n", errorList);

        ApiResponse<Object> response = new ApiResponse<>(HttpStatus.BAD_REQUEST, errors, null, "VALIDATION_ERROR");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Object>> handleIllegalArgument(IllegalArgumentException ex) {
        ApiResponse<Object> response = new ApiResponse<>(
                HttpStatus.BAD_REQUEST,
                ex.getMessage(), // chá»‰ láº¥y message, khÃ´ng cÃ³ field errors
                null,
                "BAD_REQUEST"
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    //xá»­ lÃ­ lá»—i chung
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleAllExceptions(Exception ex) {
        ApiResponse<?> body = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), null, null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ModelAndView handleAccessDenied(AccessDeniedException ex) {
        // Táº¡o má»™t Ä‘á»‘i tÆ°á»£ng ModelAndView
        ModelAndView modelAndView = new ModelAndView();

        // Äáº·t tÃªn cá»§a view (file HTML) mÃ  báº¡n muá»‘n hiá»ƒn thá»‹
        // TÆ°Æ¡ng á»©ng vá»›i file: templates/error/403.html
        modelAndView.setViewName("error/403");

        // (TÃ¹y chá»n) ThÃªm thÃ´ng tin lá»—i vÃ o Model Ä‘á»ƒ hiá»ƒn thá»‹ trÃªn trang
        modelAndView.addObject("errorMessage", "Ráº¥t tiáº¿c, báº¡n khÃ´ng cÃ³ quyá»n truy cáº­p trang nÃ y.");

        return modelAndView;
    }
}
