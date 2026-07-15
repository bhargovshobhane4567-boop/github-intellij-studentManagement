package com.dsoft.mapper;

import com.dsoft.dto.studentdto.StudentResponse;
import com.dsoft.entity.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {
    public StudentResponse mapToStudentResponse(Student student) {
        return   StudentResponse.builder()
                .id(student.getId())
                .rollNumber(student.getRollNumber())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .email(student.getEmail())
                .phone(student.getPhone())
                .gender(student.getGender())
                .dateOfBirth(student.getDateOfBirth())
                .address(student.getAddress())
                .city(student.getCity())
                .state(student.getState())
                .pinCode(student.getPinCode())
                .admissionDate(student.getAdmissionDate())
                .status(student.getStatus())
                .courseId(student.getCourse().getCourseId())
                .courseName(student.getCourse().getCourseName())
                .createdAt(student.getCreatedAt())
                .updatedAt(student.getUpdatedAt())
                .build();
    }
}
