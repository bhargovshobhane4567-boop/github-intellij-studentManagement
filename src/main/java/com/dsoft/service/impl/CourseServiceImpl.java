package com.dsoft.service.impl;

import com.dsoft.dto.coursedto.CourseRequest;
import com.dsoft.dto.coursedto.CourseResponse;
import com.dsoft.dto.studentdto.StudentResponse;
import com.dsoft.entity.Course;
import com.dsoft.entity.Student;
import com.dsoft.enums.CourseStatus;
import com.dsoft.error.BadRequestException;
import com.dsoft.error.ResourceAlreadyExistsException;
import com.dsoft.error.ResourceNotFoundException;
import com.dsoft.mapper.CourseMapper;
import com.dsoft.mapper.StudentMapper;
import com.dsoft.repository.CourseRepository;
import com.dsoft.repository.StudentRepository;
import com.dsoft.service.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final StudentMapper studentMapper;
    private final StudentRepository studentRepository;

  @Override
    public CourseResponse createCourse(CourseRequest request) {

      if (courseRepository.existsByCourseNameAndDeletedFalse(request.getCourseName())) {
          throw new ResourceAlreadyExistsException(
                  "Course already exists with name: " + request.getCourseName());
      }


      Course courseRequest = Course.builder()
                .courseName(request.getCourseName())
                .fees(request.getFees())
                .duration(request.getDuration())
                .build();

           Course course = courseRepository.save(courseRequest);

        return courseMapper.mapToCourseResponse(course);

    }
@Override
    public List<CourseResponse> getAllCourse() {

      List<Course> courseList = courseRepository.findByDeletedFalse();

     return courseList.stream().map(courseMapper::mapToCourseResponse).toList();

    }

    @Override
    public CourseResponse getCourseById(Long id) {
             Course course = courseRepository.findByCourseIdAndDeletedFalse(id)
                     .orElseThrow(() ->new ResourceNotFoundException("Course  not found"));

        return courseMapper.mapToCourseResponse(course);
    }

    @Override
    public CourseResponse updateCourse(CourseRequest request, Long id) {

        Course course = courseRepository.findByCourseIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Course not found with id: " + id));

        // Check duplicate course name
        if (!course.getCourseName().equalsIgnoreCase(request.getCourseName())
                && courseRepository.existsByCourseNameAndDeletedFalse(request.getCourseName())) {

            throw new ResourceAlreadyExistsException(
                    "Course already exists with name: " + request.getCourseName());
        }

        course.setCourseName(request.getCourseName());
        course.setDuration(request.getDuration());
        course.setFees(request.getFees());

        Course updatedCourse = courseRepository.save(course);

        return courseMapper.mapToCourseResponse(updatedCourse);
    }
    @Override
    public void deleteCourse(Long id) {
      Course course = courseRepository.findByCourseIdAndDeletedFalse(id)
              .orElseThrow(()->new ResourceNotFoundException("Course is not found"));


        if (studentRepository.existsByCourseCourseIdAndDeletedFalse(id)) {
            throw new BadRequestException(
                    "Cannot delete course because students are assigned to it.");
        }

      course.setDeleted(true);
      course.setStatus(CourseStatus.INACTIVE);

      courseRepository.save(course);



    }

    @Override
    public List<StudentResponse> getStudentsByCourse(Long courseId) {

        // Check if course exists
        courseRepository.findByCourseIdAndDeletedFalse(courseId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Course not found with id: " + courseId));

        List<Student> students = studentRepository.findByCourseCourseIdAndDeletedFalse(courseId);

        return students.stream()
                .map(studentMapper::mapToStudentResponse)
                .toList();
    }

    @Override
    public CourseResponse searchCourseByName(String courseName) {

        Course course = courseRepository
                .findByCourseNameAndDeletedFalse(courseName)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Course not found with name: " + courseName));

        return courseMapper.mapToCourseResponse(course);
    }

    @Override
    public CourseResponse restoreCourse(Long courseId) {
        Course course = courseRepository.findByCourseIdAndDeletedTrue(courseId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Deleted course not found with id: "+courseId));

        course.setDeleted(false);
        course.setStatus(CourseStatus.DELETED);
        return courseMapper.mapToCourseResponse(course);
    }

    @Override
    public Long getCourseCount() {
        return courseRepository.countByDeletedFalse();
    }

    @Override
    public List<CourseResponse> getDeletedCourse() {
        List<Course>  courses = courseRepository.findByDeletedTrue();

        return courses.stream().map((courseMapper::mapToCourseResponse)).toList();
    }

    @Override
    public CourseResponse getDeletedCourse(Long courseId) {

      Course course =courseRepository.findByCourseIdAndDeletedTrue(courseId).orElseThrow(()->new ResourceNotFoundException(
              "Deleted course not found with id: "+courseId
      ));
        return courseMapper.mapToCourseResponse(course);
    }


}
