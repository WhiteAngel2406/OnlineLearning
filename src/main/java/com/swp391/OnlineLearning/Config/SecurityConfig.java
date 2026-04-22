package com.swp391.OnlineLearning.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final MySimpleUrlAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    public SecurityConfig(MySimpleUrlAuthenticationSuccessHandler myAuthenticationSuccessHandler) {
        this.myAuthenticationSuccessHandler = myAuthenticationSuccessHandler;
    }



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login", "/register", "/home",
                                "/courses/learner","/courses/{id}/learner",
                                "/blogs", "/blogs/{id}",
                                "/css/**", "/js/**",
                                "/uploads/**",
                                "/chats").permitAll()
                        .requestMatchers("/marketing/**").hasRole("MARKETING")
                        // --- 2. QUYá»€N Cá»¦A EXPERT (CÃ¡c rule chá»‰ Expert cÃ³) ---
                        .requestMatchers(HttpMethod.POST, "/courses/{id}/submit-review").hasRole("EXPERT")

                        // Gom nhÃ³m cÃ¡c API cá»§a Expert
                        .requestMatchers(HttpMethod.PUT,
                                "/api/chapters/{chapterId}",
                                "/api/lectures/{lectureId}",
                                "/api/quizzes/{quizId}"
                        ).hasRole("EXPERT")

                        .requestMatchers(HttpMethod.DELETE,
                                "/api/courses/{courseId}/chapters/{chapterId}",
                                "/api/chapters/{chapterId}/lessons/{id}"
                        ).hasRole("EXPERT")

                        .requestMatchers(HttpMethod.POST,
                                "/api/courses/{courseId}/chapters",
                                "/api/chapters/{chapterId}/lectures",
                                "/api/chapters/{chapterId}/quizzes"
                        ).hasRole("EXPERT")

                        // Gom nhÃ³m cÃ¡c Ä‘Æ°á»ng dáº«n táº¡o/cáº­p nháº­t Question
                        .requestMatchers(
                                "/courses/{courseId}/quizzes/{quizId}/questions/multiple-choice/create",
                                "/courses/{courseId}/quizzes/{quizId}/questions/short-answer/create",
                                "/courses/{courseId}/quizzes/{quizId}/questions/{questionId}/delete",
                                "/courses/{courseId}/quizzes/{quizId}/questions/{questionId}/multiple-choice/update",
                                "/courses/{courseId}/quizzes/{quizId}/questions/{questionId}/short-answer/update"
                        ).hasRole("EXPERT")

                        // --- 3. QUYá»€N CHUNG (Cáº£ ADMIN vÃ  EXPERT Ä‘á»u cÃ³) ---
                        .requestMatchers(HttpMethod.GET,
                                "/courses/{id}",
                                "/courses/create",
                                "/courses/{id}/update",
                                "/courses/users/{id}",
                                "/courses/{courseId}/quizzes/{quizId}/questions/**"
                        ).hasAnyRole("ADMIN", "EXPERT")

                        .requestMatchers(HttpMethod.POST,
                                "/courses/create",
                                "/courses/{id}/update",
                                "/courses/{id}/delete"
                        ).hasAnyRole("ADMIN", "EXPERT")

                        // --- QUYá»€N Cá»¦A ADMIN (Æ¯u tiÃªn cÃ¡c rule cá»¥ thá»ƒ nháº¥t) ---
                        .requestMatchers("/quiz/*").hasRole("ADMIN")
                        .requestMatchers("/admin/**", "/courses/admin/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/courses/{courseId}").hasRole("ADMIN") // Change status
                        .requestMatchers("/api/{courseId}/toggle-featured").hasRole("ADMIN") // Giáº£ sá»­ lÃ  POST/PUT, náº¿u rÃµ method thÃ¬ nÃªn ghi

                        // Táº¥t cáº£ cÃ¡c request cÃ²n láº¡i Ä‘á»u cáº§n pháº£i xÃ¡c thá»±c
                        .anyRequest().permitAll()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")   // chá»— nÃ y pháº£i Ä‘Ãºng action form
                        .defaultSuccessUrl("/", true)
                        .successHandler(myAuthenticationSuccessHandler)// true = luÃ´n luÃ´n redirect vá» /home
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable()); // Táº¯t CSRF cho POST requests

        return http.build();
    }

}
