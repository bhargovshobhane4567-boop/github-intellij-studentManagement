package com.dsoft.repository;

import com.dsoft.entity.Course;
import com.dsoft.entity.Student;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student,Long> {
    List<Student> findByDeletedFalse();

    Optional<Student> findByIdAndDeletedFalse(Long id);

    Optional<Student> findByRollNumberAndDeletedFalse(String rollNumber);

    Optional<Student> findByEmailAndDeletedFalse(String email);

    Optional<Student> findByCourseCourseId(Long courseid);

    List<Student> findByCityAndDeletedFalse(String city);

    List<Student> findByStatusAndDeletedFalse(String status);

    Long countByDeletedFalse();

    List<Student> findByAdmissionDateBetweenAndDeletedFalse(
            LocalDate startDate,
            LocalDate endDate);

    Optional<Student> findByIdAndDeletedTrue(Long id);

    List<Student> findByDeletedTrue();

    boolean existsByEmailAndDeletedFalse(@Email(message = "invalid email format") @NotBlank(message = "Email is required") String email);

    List<Student> findByCourseCourseIdAndDeletedFalse(Long courseId);

    boolean existsByCourseCourseIdAndDeletedFalse(Long id);
}
