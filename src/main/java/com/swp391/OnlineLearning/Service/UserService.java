package com.swp391.OnlineLearning.Service;

import com.swp391.OnlineLearning.Model.User;
import com.swp391.OnlineLearning.Model.UserRole;
import com.swp391.OnlineLearning.Model.dto.UserDTO;
import com.swp391.OnlineLearning.Repository.RoleRepository;
import com.swp391.OnlineLearning.Repository.UserRepository;
import com.swp391.OnlineLearning.Service.Specification.UserSpecs;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
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
        Specification<User> spec = UserSpecs.searchByContainingEmailOrFullNameOrMobileKeyword(keyword);
        if (roleId != null) {
            spec = spec.and(UserSpecs.searchByRole(roleId.toString()));
        }
        if (enabled != null) {
            spec = spec.and(UserSpecs.searchByEnabled(enabled));
        }
        return userRepository.findAll(spec, pageable);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public Optional<User> findByEmailAndEnabledTrue(String email) {
        return userRepository.findByEmailAndEnabledTrue(email);
    }

    public void ensureEmailNotExists(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalStateException("Email already exists");
        }
    }

    public void save(User newUser) {
        userRepository.save(newUser);
    }

    public User buildNewUser(@Valid UserDTO userDTO) {
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setFullName(userDTO.getFullName());
        user.setEnabled(true);
        return user;
    }

    public void updatePassword(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    public boolean isOldPasswordCorrect(User currentUser, String oldPassword) {
        return passwordEncoder.matches(oldPassword, currentUser.getPassword());
    }

    public User getById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public Page<User> findPaginated(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Page<User> getUsersWithSpecs(Pageable pageable, String gender, String role, Boolean enabled, String search) {
        return userRepository.findAll(UserSpecs.withFilters(search, gender, role, enabled), pageable);
    }

    public List<User> getUsersByRoleName(String roleName) {
        UserRole role = roleRepository.findByName(roleName).orElse(null);
        if (role == null) {
            return Collections.emptyList();
        }
        return userRepository.findAllByRole(role);
    }

    public List<User> geUsersById(List<Long> ids) {
        return userRepository.findAllById(ids);
    }

    public User getRandomMarketing() {
        List<User> marketings = getUsersByRoleName("MARKETING");
        if (marketings.isEmpty()) {
            return null;
        }
        return marketings.get(new Random().nextInt(marketings.size()));
    }
}
