package com.swp391.OnlineLearning.Service;

import com.swp391.OnlineLearning.Model.User;
import com.swp391.OnlineLearning.Model.UserRole;
import com.swp391.OnlineLearning.Model.dto.UserDTO;
import com.swp391.OnlineLearning.Repository.RoleRepository;
import com.swp391.OnlineLearning.Repository.UserRepository;
import com.swp391.OnlineLearning.Service.Specification.UserSpecs;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

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
        userRepository.deleteById(id);
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
        try {
            userRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Không thể xóa người dùng này vì đang có dữ liệu liên quan (khóa học hoặc đơn hàng).");
        }
    }

    public long count() {
        return userRepository.count();
    }

    public long countUsers() {
        return userRepository.count();
    }

    // Methods from UserServiceImpl
    public void ensureEmailNotExists(String email) {
        userRepository.findByEmail(email)
                .ifPresent(u -> { throw new IllegalArgumentException("Email already exists"); });
    }

    public void saveNewUser(User newUser) {
        userRepository.save(newUser);
    }

    public User buildNewUser(@Valid UserDTO userDTO) {
        UserRole roleUser = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new IllegalStateException("Default role not found"));

        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setFullName(userDTO.getFullName());
        user.setRole(roleUser);
        user.setEnabled(false);
        return this.userRepository.save(user);
    }

    public Optional<User> findByEmailAndEnabledTrue(String email) {
        return userRepository.findByEmailAndEnabledTrue(email);
    }

    public void updatePassword(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        this.userRepository.save(user);
    }

    public boolean isOldPasswordCorrect(User currentUser, String oldPassword) {
        return passwordEncoder.matches(oldPassword, currentUser.getPassword());
    }

    public User getById(Long userId) {
        return this.userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public Page<User> findPaginated(Pageable pageable) {
        List<User> users = userRepository.findAll();
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<User> list;
        if (users.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, users.size());
            list = users.subList(startItem, toIndex);
        }
        return new PageImpl<>(list, pageable, users.size());
    }

    public Page<User> getUsersWithSpecs(Pageable pageable, String gender, String role, Boolean enabled, String search) {
        Specification<User> spec = UserSpecs.withFilters(search, gender, role, enabled);
        return userRepository.findAll(spec, pageable);
    }

    public List<User> getUsersByRoleName(String roleName) {
        UserRole role = this.roleRepository.findByName(roleName).orElseThrow(() -> new IllegalArgumentException("Role not found"));
        return this.userRepository.findAllByRole(role);
    }

    public List<User> geUsersById(List<Long> ids) {
        return userRepository.findAllById(ids);
    }

    public User getRandomMarketing() {
        List<User> users = getUsersByRoleName("ROLE_MARKETING");
        if (users == null || users.isEmpty()) {
            return null;
        }

        Random rand = new Random();
        return users.get(rand.nextInt(users.size()));
    }
}
