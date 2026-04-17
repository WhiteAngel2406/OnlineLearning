package com.swp391.OnlineLearning.service;

import com.swp391.OnlineLearning.Model.User;
import com.swp391.OnlineLearning.repository.RoleRepository;
import com.swp391.OnlineLearning.repository.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Page<User> findWithFilters(String keyword, Long roleId, Boolean enabled, Pageable pageable) {
        return userRepository.findWithFilters(keyword, roleId, enabled, pageable);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public User save(User user) {
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        if (user.getRole() != null && user.getRole().getId() != null) {
            user.setRole(roleRepository.findById(user.getRole().getId()).orElse(user.getRole()));
        }
        return userRepository.save(user);
    }

    public void createUser(User user, Long roleId) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email đã tồn tại!");
        }
        user.setRole(roleRepository.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Role not found")));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void updateUser(Long id, User userDetails, Long roleId) {
        User user = findById(id);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        user.setFullName(userDetails.getFullName());
        user.setEmail(userDetails.getEmail());
        user.setMobile(userDetails.getMobile());
        user.setAddress(userDetails.getAddress());
        user.setGender(userDetails.getGender());
        user.setDob(userDetails.getDob());
        user.setEnabled(userDetails.isEnabled());
        if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        }
        if (roleId != null) {
            user.setRole(roleRepository.findById(roleId)
                    .orElseThrow(() -> new IllegalArgumentException("Role not found")));
        }
        userRepository.save(user);
    }

    public void toggleUserStatus(Long id) {
        User user = findById(id);
        if (user != null) {
            user.setEnabled(!user.isEnabled());
            userRepository.save(user);
        }
    }

    public void deleteUser(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Không thể xóa người dùng này vì đang có dữ liệu liên quan (khóa học hoặc đơn hàng).");
        }
    }

    public User update(User user) {
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            User existing = findById(user.getId());
            if (existing != null) {
                user.setPassword(existing.getPassword());
            }
        }
        if (user.getRole() != null && user.getRole().getId() != null) {
            user.setRole(roleRepository.findById(user.getRole().getId()).orElse(user.getRole()));
        }
        return userRepository.save(user);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public long count() {
        return userRepository.count();
    }

    public long countUsers() {
        return userRepository.count();
    }
}
