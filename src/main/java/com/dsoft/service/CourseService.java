package com.dsoft.service;

import com.dsoft.dto.coursedto.CourseRequest;
import com.dsoft.dto.coursedto.CourseResponse;
import com.dsoft.dto.studentdto.StudentResponse;

import java.util.List;

public interface CourseService {

    CourseResponse createCourse(CourseRequest request);

    List<CourseResponse> getAllCourse();

    CourseResponse  getCourseById(Long id);

    CourseResponse updateCourse(CourseRequest request, Long Id);

    void  deleteCourse(Long id);

    List<StudentResponse>  getStudentsByCourse(Long courseId);

     CourseResponse searchCourseByName(String courseName);

    CourseResponse restoreCourse(Long id);

    Long getCourseCount();


    List<CourseResponse> getDeletedCourse();

    CourseResponse getDeletedCourse(Long id);
}
