package com.dsoft.controller;


import com.dsoft.dto.*;
import com.dsoft.dto.coursedto.CourseResponse;
import com.dsoft.dto.studentdto.StudentResponse;
import com.dsoft.dto.userdto.RegisterRequest;
import com.dsoft.dto.userdto.UserResponse;
import com.dsoft.enums.StudentStatus;
import com.dsoft.service.CourseService;
import com.dsoft.service.StudentService;
import com.dsoft.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final StudentService studentService;
    private final CourseService courseService;
    private final UserService userService;


    @GetMapping("/students/deleted")
    public ResponseEntity<ResponseApi<List<StudentResponse>>> getDeletedStudents(){
        List <StudentResponse>   studentResponse= studentService.getDeletedStudents();

        ResponseApi<List<StudentResponse>> responseApi = ResponseApi.<List<StudentResponse>>builder()
                .status(HttpStatus.OK.value())
                .success(true)
                .data(studentResponse)
                .message("Deleted student fetch successfully")
                .timeStamp(LocalTime.now())
                .build();
        return ResponseEntity.ok(responseApi);
    }

    @GetMapping("/students/deleted/{id}")
    public ResponseEntity<ResponseApi<StudentResponse>> getDeletedStudent(
            @PathVariable Long id) {

        StudentResponse studentResponse = studentService.getDeletedStudent(id);

        ResponseApi<StudentResponse> response = ResponseApi.<StudentResponse>builder()
                .success(true)
                .status(HttpStatus.OK.value())
                .message("Deleted student fetched successfully.")
                .data(studentResponse)
                .timeStamp(LocalTime.now())
                .build();

        return ResponseEntity.ok(response);
    }


    @PatchMapping("/students/{id}/status")
    public ResponseEntity<ResponseApi<StudentResponse>> updateStudentStatus(@PathVariable Long id,@RequestParam StudentStatus status){
        StudentResponse studentResponse = studentService.updateStudentStatus(id,status);

        ResponseApi<StudentResponse> responseApi = ResponseApi.<StudentResponse>builder()
                .success(true)
                .data(studentResponse)
                .message("Student fetch successfully")
                .timeStamp(LocalTime.now())
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(responseApi);
    }


    @PatchMapping("/students/{id}/restore")
    public ResponseEntity<ResponseApi<StudentResponse>> restoreStudent(@PathVariable Long id){
        StudentResponse studentResponse = studentService.restoreStudent(id);

        ResponseApi<StudentResponse> responseApi = ResponseApi.<StudentResponse>builder()
                .success(true)
                .data(studentResponse)
                .timeStamp(LocalTime.now())
                .message("Student restore successfully")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(responseApi);
    }


    @PatchMapping("/courses/{id}/restore")
    public ResponseEntity<ResponseApi<CourseResponse>> restoreCourse(@PathVariable Long id){
        CourseResponse courseResponse = courseService.restoreCourse(id);

        ResponseApi<CourseResponse> responseApi = ResponseApi.<CourseResponse>builder()
                .success(true)
                .data(courseResponse)
                .timeStamp(LocalTime.now())
                .message("Course restore successfully")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(responseApi);
    }

    @GetMapping("/courses/deleted/{id}")
    public ResponseEntity<ResponseApi<CourseResponse>> getDeletedCourse(
            @PathVariable Long id) {

       CourseResponse courseResponse =  courseService.getDeletedCourse(id);

        ResponseApi<CourseResponse> responseApi = ResponseApi.<CourseResponse>builder()
                .success(true)
                .status(HttpStatus.OK.value())
                .message("Deleted student fetched successfully.")
                .data(courseResponse)
                .timeStamp(LocalTime.now())
                .build();

        return ResponseEntity.ok(responseApi);
    }

    @GetMapping("/courses/deleted")
    public ResponseEntity<ResponseApi<List<CourseResponse>>> getDeletedCourse(){

        List<CourseResponse> course  = courseService.getDeletedCourse();

        ResponseApi<List<CourseResponse>> responseApi = ResponseApi.<List<CourseResponse>>builder()
                .success(true)
                .data(course)
                .message("Deleted Course fetch successfully")
                .timeStamp(LocalTime.now())
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(responseApi);
    }

    // Create User (ADMIN/STUDENT)
    @PostMapping("/users")
    public ResponseEntity<ResponseApi<UserResponse>> createUser(
            @Valid @RequestBody RegisterRequest request) {

        UserResponse userResponse = userService.createUser(request);

        ResponseApi<UserResponse> response = ResponseApi.<UserResponse>builder()
                .success(true)
                .status(HttpStatus.CREATED.value())
                .message("User created successfully.")
                .data(userResponse)
                .timeStamp(LocalTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Get All Users
    @GetMapping("/users")
    public ResponseEntity<ResponseApi<List<UserResponse>>> getAllUsers() {

        List<UserResponse> users = userService.getAllUsers();

        ResponseApi<List<UserResponse>> response = ResponseApi.<List<UserResponse>>builder()
                .success(true)
                .status(HttpStatus.OK.value())
                .message("Users fetched successfully.")
                .data(users)
                .timeStamp(LocalTime.now())
                .build();

        return ResponseEntity.ok(response);
    }

    // Update User
    @PutMapping("/users/{id}")
    public ResponseEntity<ResponseApi<UserResponse>> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody RegisterRequest request) {

        UserResponse userResponse = userService.updateUser(id, request);

        ResponseApi<UserResponse> response = ResponseApi.<UserResponse>builder()
                .success(true)
                .status(HttpStatus.OK.value())
                .message("User updated successfully.")
                .data(userResponse)
                .timeStamp(LocalTime.now())
                .build();

        return ResponseEntity.ok(response);
    }

    // Delete User
    @DeleteMapping("/users/{id}")
    public ResponseEntity<ResponseApi<Void>> deleteUser(
            @PathVariable Long id) {

        userService.deleteUser(id);

        ResponseApi<Void> response = ResponseApi.<Void>builder()
                .success(true)
                .status(HttpStatus.OK.value())
                .message("User deleted successfully.")
                .data(null)
                .timeStamp(LocalTime.now())
                .build();

        return ResponseEntity.ok(response);
    }


}