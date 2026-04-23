package com.swp391.OnlineLearning.model.dto;

import com.swp391.OnlineLearning.util.PasswordMatches;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

@PasswordMatches(message = "Máº­t kháº©u nháº­p láº¡i khÃ´ng giá»‘ng")
public class UserDTO {

    @NotBlank(message = "Vui lòng nhập tên đầy đủ")
    @Size(min = 2, max = 50, message = "Tên phải từ 2 đến 50 ký tự")
    @Pattern(
            regexp = "^[A-Za-zÀ-ỹ\\s]+$",
            message = "Tên chỉ được chứa chữ cái và khoảng trắng"
    )
    @Pattern(
            regexp = ".*\\S.*",
            message = "Tên không được chỉ chứa khoảng trắng"
    )
    private String fullName;

    @NotBlank(message = "Email không được để trống")
    @ValidEmail(message = "Email không hợp lệ")
    private String email;

    @NotBlank(message = "Vui lòng nhập mật khẩu")
    @Size(min = 6, max = 20, message = "Mật khẩu phải từ 6 đến 20 ký tự")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d).+$",
            message = "Mật khẩu phải chứa ít nhất 1 chữ và 1 số"
    )
    private String password;

    @NotBlank(message = "Vui lòng nhập lại mật khẩu")
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