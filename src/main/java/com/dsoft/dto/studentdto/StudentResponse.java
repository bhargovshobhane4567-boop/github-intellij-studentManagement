package com.dsoft.dto.studentdto;

import com.dsoft.enums.StudentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentResponse {
    private Long id;

    private String rollNumber;

    private String firstName;

    private String lastName;

    private String email;

    private String city;

    private String  phone;

    private String gender;

    private LocalDate dateOfBirth;

    private String address;

    private String state;

    private String pinCode;

    private LocalDate admissionDate;

    private Long courseId;

    private String courseName;

    private StudentStatus status ;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
