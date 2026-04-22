package com.swp391.OnlineLearning.model.dto;

import com.swp391.OnlineLearning.util.PasswordMatches;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@PasswordMatches(message = "Máº­t kháº©u nháº­p láº¡i khÃ´ng giá»‘ng")
public class UserDTO {
    @NotBlank(message = "Vui lÃ²ng nháº­p tÃªn Ä‘áº§y Ä‘á»§")
    private String fullName;

    @Email(message = "Email khÃ´ng há»£p lá»‡")
    @NotBlank(message = "Email khÃ´ng Ä‘Æ°á»£c Ä‘á»ƒ trá»‘ng")
    private String email;

    @NotBlank(message = "Vui lÃ²ng nháº­p máº­t kháº©u")
    private String password;
    private String confirmedPassword;


    public UserDTO() {
    }

    public UserDTO(String fullName, String email, String password, String confirmedPassword) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.confirmedPassword = confirmedPassword;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmedPassword() {
        return confirmedPassword;
    }

    public void setConfirmedPassword(String confirmedPassword) {
        this.confirmedPassword = confirmedPassword;
    }
}
