package com.dsoft.repository;

import com.dsoft.entity.Course;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {

    // Get all active courses
    List<Course> findByDeletedFalse();

    // Get all deleted courses
    List<Course> findByDeletedTrue();

    // Get active course by ID
    Optional<Course> findByCourseIdAndDeletedFalse(Long id);

    // Get deleted course by ID
    Optional<Course> findByCourseIdAndDeletedTrue(Long id);

    // Search active course by name
    Optional<Course> findByCourseNameAndDeletedFalse(String courseName);

    // Count active courses
    long countByDeletedFalse();

    // Check if active course exists
    boolean existsByCourseIdAndDeletedFalse(Long id);

    // Check if active course exists by name
    boolean existsByCourseNameAndDeletedFalse(String courseName);


}