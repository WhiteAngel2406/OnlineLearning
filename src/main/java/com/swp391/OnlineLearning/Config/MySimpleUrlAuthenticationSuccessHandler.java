package com.swp391.OnlineLearning.config;

import com.swp391.OnlineLearning.model.User;
import com.swp391.OnlineLearning.service.SessionService;
import com.swp391.OnlineLearning.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class MySimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserService userService;
    @Autowired
    private SessionService sessionService;

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication) throws IOException {
        handle(request, response, authentication);
        clearAuthenticationAttributes(request, authentication);
    }

    // This method handles the redirection after successful authentication
    protected void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException {

        String targetUrl = determineTargetUrl(authentication);
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    // This method determines the target URL based on the user's roles
    protected String determineTargetUrl(final Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();

        final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (final GrantedAuthority grantedAuthority : authorities) {
            String authorityName = grantedAuthority.getAuthority();
            if (authorityName.equals("ROLE_ADMIN")) {
                return "/admin";
            } else if (authorityName.equals("ROLE_MARKETING")) {
                return "/marketing";
            } else if (authorityName.equals("ROLE_EXPERT")) {
                return "/courses/users/" + currentUser.getId();
            } else if (authorityName.equals("ROLE_USER")) {
                return "/";
            }
        }

        throw new IllegalStateException();
    }

    // This method clears the authentication attributes from the session
    protected void clearAuthenticationAttributes(HttpServletRequest request, Authentication authentication) {
        HttpSession session = request.getSession(false);// láº¥y session hiá»‡n táº¡i náº¿u cÃ³, náº¿u khÃ´ng cÃ³ thÃ¬ tráº£ vá» null
        if (session == null) {
            return;
        }
        sessionService.storeUserInSession(request, authentication);
    }
}
