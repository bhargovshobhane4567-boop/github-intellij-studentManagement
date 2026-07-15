package com.dsoft.service;

import com.dsoft.dto.studentdto.StudentRequest;
import com.dsoft.dto.studentdto.StudentResponse;
import com.dsoft.enums.StudentStatus;

import java.util.List;

public interface StudentService {

    StudentResponse createStudent(StudentRequest request);

    List<StudentResponse> getAllStudent();

    StudentResponse getStudentById(Long id);

    StudentResponse updateStudent(StudentRequest request , Long id);

    void deleteStudent(Long id);

    // Search Student By Roll Number
    StudentResponse getStudentByRollNumber(String rollNumber);

    // Search Student By Email
    StudentResponse getStudentByEmail(String email);

    // Get Students By Course
    List<StudentResponse> getStudentsByCourse(Long courseId);

    // Get Students By City
    List<StudentResponse> getStudentsByCity(String city);

    // Get Students By Status
    List<StudentResponse> getStudentsByStatus(String status);

    // Get Total Students
    Long getStudentCount();

    // Restore Deleted Student
    StudentResponse restoreStudent(Long id);

    // Get Deleted Students
    List<StudentResponse> getDeletedStudents();


    StudentResponse getDeletedStudent(Long id);

    // Update Student Status
    StudentResponse updateStudentStatus(Long id, StudentStatus status);

    // Get Students By Admission Year
    List<StudentResponse> getStudentsByAdmissionYear(Integer year);


}
