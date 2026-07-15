package com.dsoft.controller;


import com.dsoft.dto.ResponseApi;
import com.dsoft.dto.studentdto.StudentRequest;
import com.dsoft.dto.studentdto.StudentResponse;
import com.dsoft.service.StudentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/student")
public class StudentController {

    @Autowired
    public   StudentService studentService;

    @PostMapping
    public ResponseEntity<ResponseApi<StudentResponse>> createStudent(@Valid  @RequestBody StudentRequest studentRequest){
         StudentResponse studentResponse = studentService.createStudent(studentRequest);
        ResponseApi<StudentResponse> responseApi = ResponseApi.<StudentResponse>builder()
                .success(true)
                .message("Student created successfully")
                .status(HttpStatus.CREATED.value())
                .data(studentResponse)
                .timeStamp(LocalTime.now())
                .build();

       return ResponseEntity.status(HttpStatus.CREATED)
               .body(responseApi);

     }

    @GetMapping
    public ResponseEntity<ResponseApi<List<StudentResponse>>> getAllStudent() {
        List<StudentResponse> studentResponses = studentService.getAllStudent();

        ResponseApi<List<StudentResponse>> responseApi = ResponseApi.<List<StudentResponse>>builder()
                .success(true)
                .status(HttpStatus.CREATED.value())
                .data(studentResponses)
                .message("All students is fetch successfully")
                .timeStamp(LocalTime.now())
                .build();
        return ResponseEntity.ok(responseApi);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ResponseApi<StudentResponse>>getStudentById(@PathVariable Long id) {

        StudentResponse studentResponse = studentService.getStudentById(id);


        ResponseApi<StudentResponse> responseApi = ResponseApi.<StudentResponse>builder()
                .success(true)
                .status(HttpStatus.FOUND.value())
                .data(studentResponse)
                .message("Student fetch successfully")
                .timeStamp(LocalTime.now())
                .build();
        return ResponseEntity.ok(responseApi);
    }

    @PutMapping("/{id}")
     public ResponseEntity<ResponseApi<StudentResponse>> updateStudent(@RequestBody StudentRequest request,@PathVariable Long id){


        StudentResponse studentResponse = studentService.updateStudent(request,id);

        ResponseApi<StudentResponse> responseApi = ResponseApi.<StudentResponse>builder()
                .success(true)
                .status(HttpStatus.FOUND.value())
                .data(studentResponse)
                .message("Student is updated successfully")
                .timeStamp(LocalTime.now())
                .build();
        return ResponseEntity.ok(responseApi);

        }

        @DeleteMapping("/{id}")
        public ResponseEntity<ResponseApi<Void>> deleteStudent(@PathVariable Long id){
                studentService.deleteStudent(id);

                ResponseApi<Void> responseApi = ResponseApi.<Void>builder()
                        .success(true)
                        .message("Student deleted successfully")
                        .timeStamp(LocalTime.now())
                        .status(HttpStatus.OK.value())
                        .build();
                return ResponseEntity.ok(responseApi);
        }

        @GetMapping("/search/roll/{rollNo}")
        public ResponseEntity<ResponseApi<StudentResponse>> getStudentByRollNumber(@PathVariable  String rollNo){
                 StudentResponse studentResponse =  studentService.getStudentByRollNumber(rollNo);

                 ResponseApi<StudentResponse> responseApi = ResponseApi.<StudentResponse>builder()
                         .success(true)
                         .message("Student fetch successfully")
                         .data(studentResponse)
                         .timeStamp(LocalTime.now())
                         .status(HttpStatus.OK.value())
                         .build();
         return ResponseEntity.ok(responseApi);
            }

            @GetMapping("/search/email/{email}")
        public ResponseEntity<ResponseApi<StudentResponse>> getStudentByEmail(@PathVariable String email){
                StudentResponse studentResponse = studentService.getStudentByEmail(email);

                ResponseApi<StudentResponse> responseApi = ResponseApi.<StudentResponse>builder()
                .success(true)
                .data(studentResponse)
                .message("Student fetch successfully")
                .timeStamp(LocalTime.now())
                .status(HttpStatus.OK.value())
                .build();

             return ResponseEntity.ok(responseApi);

       }

       @GetMapping("/course/{courseId}")
       public ResponseEntity<ResponseApi<List<StudentResponse>>> getStudentByCourse(@PathVariable Long id){
                List<StudentResponse> studentResponse = studentService.getStudentsByCourse(id);

                ResponseApi<List<StudentResponse>> responseApi = ResponseApi.<List<StudentResponse>>builder()
                    .success(true)
                    .data(studentResponse)
                    .message("Student fetch successfully")
                    .timeStamp(LocalTime.now())
                    .status(HttpStatus.OK.value())
                    .build();
                return ResponseEntity.ok(responseApi);
       }

       @GetMapping("/city/{city}")
       public ResponseEntity<ResponseApi<List<StudentResponse>>> getStudentByCity(@PathVariable String city){
                List<StudentResponse> studentResponse = studentService.getStudentsByCity(city);

                ResponseApi<List<StudentResponse>> responseApi = ResponseApi.<List<StudentResponse>>builder()
                        .success(true)
                        .data(studentResponse)
                        .message("Student fetch successfully")
                        .timeStamp(LocalTime.now())
                        .status(HttpStatus.OK.value())
                        .build();
                return ResponseEntity.ok(responseApi);
        }

       @GetMapping("/status/{status}")
         private ResponseEntity<ResponseApi<List<StudentResponse>>> getStudentByStatus(@PathVariable String status){
                   List<StudentResponse>  studentResponse = studentService.getStudentsByStatus(status);

                   ResponseApi<List<StudentResponse>> responseApi = ResponseApi.<List<StudentResponse>>builder()
                           .success(true)
                           .data(studentResponse)
                           .message("Student fetch successfully")
                           .timeStamp(LocalTime.now())
                           .status(HttpStatus.OK.value())
                           .build();
                      return ResponseEntity.ok(responseApi);

         }



         @GetMapping("/count")
         private ResponseEntity<ResponseApi<Long>> getStudentsCount()
         {
                    Long count = studentService.getStudentCount();

                    ResponseApi<Long> responseApi = ResponseApi.<Long>builder()
                            .success(true)
                            .data(count)
                            .message("Student fetch successfully")
                            .timeStamp(LocalTime.now())
                            .status(HttpStatus.OK.value())
                            .build();
                    return ResponseEntity.ok(responseApi);
         }



    @GetMapping("/admission-year/{year}")
    public ResponseEntity<ResponseApi<List<StudentResponse>>> getStudentsByAdmissionYear(
            @PathVariable Integer year) {

        List<StudentResponse> students =
                studentService.getStudentsByAdmissionYear(year);

        ResponseApi<List<StudentResponse>> response =
                ResponseApi.<List<StudentResponse>>builder()
                        .success(true)
                        .status(HttpStatus.OK.value())
                        .message("Students fetched successfully.")
                        .data(students)
                        .timeStamp(LocalTime.now())
                        .build();

        return ResponseEntity.ok(response);
    }

}
