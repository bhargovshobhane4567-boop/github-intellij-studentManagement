package com.dsoft.mapper;

import com.dsoft.dto.coursedto.CourseResponse;
import com.dsoft.entity.Course;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {

    public CourseResponse mapToCourseResponse(Course course){
        return CourseResponse.builder()
                .courseId(course.getCourseId())
                .courseName(course.getCourseName())
                .fees(course.getFees())
                .duration(course.getDuration())
                .build();
    }
}
