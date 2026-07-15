package com.dsoft.controller;

import com.dsoft.dto.coursedto.CourseRequest;
import com.dsoft.dto.coursedto.CourseResponse;
import com.dsoft.dto.ResponseApi;
import com.dsoft.dto.studentdto.StudentResponse;
import com.dsoft.mapper.ApiMapper;
import com.dsoft.service.CourseService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/course")
public class CourseController {

    public CourseService courseService;



    @PostMapping
    public  ResponseEntity<ResponseApi<CourseResponse>>  createCourse(@Valid @RequestBody CourseRequest request){
              CourseResponse courseResponse = courseService.createCourse(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiMapper<CourseResponse>().mapToApiMapper(courseResponse,"Course is retrieve successfully") );

    }

    @GetMapping
    public ResponseEntity<ResponseApi<List<CourseResponse>>> getAllCourse(){
        List<CourseResponse> courseResponse = courseService.getAllCourse();

        ResponseApi<List<CourseResponse>> responseApi = ResponseApi.<List<CourseResponse>>builder()
                .success(true)
                .message("Course is retrieve successfully")
                .status(HttpStatus.OK.value())
                .data(courseResponse)
                .timeStamp(LocalTime.now())
                .build();

       return ResponseEntity.ok(responseApi) ;
    }


    @GetMapping("/{courseId}/students")
    public ResponseEntity<ResponseApi<List<StudentResponse>>> getStudentsByCourse(
            @PathVariable Long courseId) {

        List<StudentResponse> students = courseService.getStudentsByCourse(courseId);

        ResponseApi<List<StudentResponse>> response = ResponseApi.<List<StudentResponse>>builder()
                .success(true)
                .status(HttpStatus.OK.value())
                .message("Students fetched successfully.")
                .data(students)
                .timeStamp(LocalTime.now())
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/search/{courseName}")
    public ResponseEntity<ResponseApi<CourseResponse>> searchCourseByName(
            @PathVariable String courseName) {

        CourseResponse courseResponse = courseService.searchCourseByName(courseName);

        ResponseApi<CourseResponse> response = ResponseApi.<CourseResponse>builder()
                .success(true)
                .status(HttpStatus.OK.value())
                .message("Course fetched successfully.")
                .data(courseResponse)
                .timeStamp(LocalTime.now())
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<ResponseApi<CourseResponse>> updateCourse(@PathVariable Long courseId , @RequestBody CourseRequest courseRequest){
        CourseResponse courseResponse = courseService.updateCourse(courseRequest,courseId);

        ResponseApi<CourseResponse> response = ResponseApi.<CourseResponse>builder()
                .success(true)
                .message("Courses updated successfully")
                .status(HttpStatus.OK.value())
                .data(courseResponse)
                .timeStamp(LocalTime.now())
                .build();

        return ResponseEntity.ok(response);

    }

    @GetMapping("/{courseId}")
     public ResponseEntity<ResponseApi<CourseResponse>> getCourseById(@PathVariable Long courseId){
        CourseResponse courseResponse = courseService.getCourseById(courseId);

        ResponseApi<CourseResponse> response = ResponseApi.<CourseResponse>builder()
                .success(true)
                .message("Courses fetched successfully")
                .status(HttpStatus.OK.value())
                .data(courseResponse)
                .timeStamp(LocalTime.now())
                .build();
        return ResponseEntity.ok(response);

    }

       @DeleteMapping("/{courseId}")
       public ResponseEntity<ResponseApi<CourseResponse>> deleteCourse(@PathVariable Long courseId){
         courseService.deleteCourse(courseId);

         ResponseApi<CourseResponse> response = ResponseApi.<CourseResponse>builder()
                 .success(true)
                 .message("Courses delete successfully")
                 .status(HttpStatus.OK.value())
                 .data(null)
                 .timeStamp(LocalTime.now())
                 .build();
          return ResponseEntity.ok((response));

    }


    @GetMapping("/count")
    private ResponseEntity<ResponseApi<Long>> getCourseCount()
    {
        Long count = courseService.getCourseCount();

        ResponseApi<Long> responseApi = ResponseApi.<Long>builder()
                .success(true)
                .data(count)
                .message("Count of Course successfully fetch  ")
                .timeStamp(LocalTime.now())
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(responseApi);
    }



}
