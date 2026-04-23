package com.swp391.OnlineLearning.Config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class LoginIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    // ===== TEST PHÂN QUYỀN TRUY CẬP (Authorization) =====

    @Test
    @DisplayName("Marketing Dashboard - Role MARKETING → truy cập được (200)")
    @WithMockUser(username = "marketing@example.com", roles = {"MARKETING"})
    void marketingDashboard_withMarketingRole_shouldReturn200() throws Exception {
        mockMvc.perform(get("/marketing/dashboard"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Marketing Dashboard - Role USER → bị từ chối (403)")
    @WithMockUser(username = "user@example.com", roles = {"USER"})
    void marketingDashboard_withUserRole_shouldReturn403() throws Exception {
        mockMvc.perform(get("/marketing/dashboard"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Marketing Dashboard - Role ADMIN → bị từ chối (403)")
    @WithMockUser(username = "admin@example.com", roles = {"ADMIN"})
    void marketingDashboard_withAdminRole_shouldReturn403() throws Exception {
        mockMvc.perform(get("/marketing/dashboard"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Marketing Dashboard - Chưa login → redirect login page")
    void marketingDashboard_anonymous_shouldRedirectToLogin() throws Exception {
        mockMvc.perform(get("/marketing/dashboard"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    // ===== TEST TRANG PUBLIC (Không cần đăng nhập) =====

    @Test
    @DisplayName("Trang chủ - Ai cũng truy cập được")
    void homePage_anonymous_shouldReturn200() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Trang blogs - Ai cũng truy cập được")
    void blogsPage_anonymous_shouldReturn200() throws Exception {
        mockMvc.perform(get("/blogs"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Trang login - Ai cũng truy cập được")
    void loginPage_anonymous_shouldReturn200() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk());
    }

    // ===== TEST LOGIN FAILURE =====

    @Test
    @DisplayName("Login sai password → redirect /login?error=true")
    void login_wrongPassword_shouldRedirectWithError() throws Exception {
        mockMvc.perform(post("/login")
                        .param("username", "marketing@example.com")
                        .param("password", "wrongpassword")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error=true"));
    }

    @Test
    @DisplayName("Login email không tồn tại → redirect /login?error=true")
    void login_nonExistentUser_shouldRedirectWithError() throws Exception {
        mockMvc.perform(post("/login")
                        .param("username", "notexist@example.com")
                        .param("password", "password123")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error=true"));
    }

    @Test
    @DisplayName("Login thiếu password → redirect /login?error=true")
    void login_emptyPassword_shouldRedirectWithError() throws Exception {
        mockMvc.perform(post("/login")
                        .param("username", "marketing@example.com")
                        .param("password", "")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error=true"));
    }
}
