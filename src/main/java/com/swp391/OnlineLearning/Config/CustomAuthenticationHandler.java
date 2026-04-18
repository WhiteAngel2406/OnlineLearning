package com.swp391.OnlineLearning.Config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        String errorMessage;

        if (exception instanceof DisabledException) {
            errorMessage = "Tài khoản đã bị vô hiệu hóa";
        } else if (exception instanceof BadCredentialsException) {
            errorMessage = "Email hoặc mật khẩu không đúng";
        } else {
            errorMessage = "Đăng nhập thất bại: " + exception.getMessage();
        }

        response.sendRedirect("/login?error=" + java.net.URLEncoder.encode(errorMessage, java.nio.charset.StandardCharsets.UTF_8));
    }
}
