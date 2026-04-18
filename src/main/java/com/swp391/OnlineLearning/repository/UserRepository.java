package com.swp391.OnlineLearning.Repository;

import com.swp391.OnlineLearning.Model.User;
import com.swp391.OnlineLearning.Model.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndEnabledTrue(String email);
    Page<User> findAll(Specification<User> specs, Pageable pageale);
    List<User> findAllByRole(UserRole role);

    @Query("SELECT u FROM User u WHERE " +
           "(:keyword IS NULL OR :keyword = '' OR u.email LIKE %:keyword% OR u.fullName LIKE %:keyword%) " +
           "AND (:roleId IS NULL OR u.role.id = :roleId) " +
           "AND (:enabled IS NULL OR u.enabled = :enabled)")
    Page<User> findWithFilters(@Param("keyword") String keyword,
                               @Param("roleId") Long roleId,
                               @Param("enabled") Boolean enabled,
                               Pageable pageable);

    boolean existsByEmail(String email);
}
