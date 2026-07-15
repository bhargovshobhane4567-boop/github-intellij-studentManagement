package com.dsoft.service.impl;

import com.dsoft.dto.studentdto.StudentRequest;
import com.dsoft.dto.studentdto.StudentResponse;
import com.dsoft.entity.Course;
import com.dsoft.entity.Student;
import com.dsoft.enums.StudentStatus;
import com.dsoft.error.ResourceAlreadyExistsException;
import com.dsoft.error.ResourceNotFoundException;
import com.dsoft.mapper.StudentMapper;
import com.dsoft.repository.CourseRepository;
import com.dsoft.repository.StudentRepository;
import com.dsoft.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final StudentMapper studentMapper;

   @Override
    public StudentResponse createStudent(StudentRequest request) {


       if (studentRepository.existsByEmailAndDeletedFalse(request.getEmail())) {
           throw new ResourceAlreadyExistsException(
                   "Course already exists with name: " + request.getEmail());
       }


       Course course = courseRepository.findByCourseIdAndDeletedFalse(request.getCourseId())
               .orElseThrow(() -> new ResourceNotFoundException("Course is not  found"));



        Student studentRequest = Student.builder()
                .rollNumber(getRollnoOfstudent(course))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .gender(request.getGender())
                .dateOfBirth(request.getDateOfBirth())
                .address(request.getAddress())
                .city(request.getCity())
                .state(request.getState())
                .pinCode(request.getPinCode())
                .admissionDate(request.getAdmissionDate())
                .course(course)
                .build();

       Student student = studentRepository.save(studentRequest);

        return studentMapper.mapToStudentResponse(student);

    }

    private String getRollnoOfstudent(Course course) {
        Long count = studentRepository.count()+ 1;
          int year = LocalDate.now().getYear();
          String Name =  course.getCourseName();

     return  Name + "-" + year + "-" + String.format("%03d", count);

    }

    @Override
    public List<StudentResponse> getAllStudent() {
            List<Student> studentList =studentRepository.findByDeletedFalse();

        return  studentList.stream().map(student -> studentMapper.mapToStudentResponse(student)).toList();
    }

    @Override
    public StudentResponse getStudentById(Long id) {
       Student student =  studentRepository.findByIdAndDeletedFalse(id).orElseThrow(()->new ResourceNotFoundException("Student not found"));

        return studentMapper.mapToStudentResponse(student);
    }


    @Override
    public StudentResponse updateStudent(StudentRequest request, Long id) {

        Student student = studentRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student not found with id: " + id));

        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Course not found with id: " + request.getCourseId()));

        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setEmail(request.getEmail());
        student.setPhone(request.getPhone());
        student.setGender(request.getGender());
        student.setDateOfBirth(request.getDateOfBirth());
        student.setAddress(request.getAddress());
        student.setCity(request.getCity());
        student.setState(request.getState());
        student.setPinCode(request.getPinCode());
        student.setStatus(StudentStatus.valueOf(request.getState()));
        student.setAdmissionDate(request.getAdmissionDate());
        student.setCourse(course);

        Student updatedStudent = studentRepository.save(student);

        return studentMapper.mapToStudentResponse(updatedStudent);
    }



    @Override
    public void deleteStudent(Long id) {

         Student student =studentRepository.findByIdAndDeletedFalse(id)
                 .orElseThrow(()->new ResourceNotFoundException("Student not found with id: " + id));
         student.setDeleted(true);
         student.setStatus(StudentStatus.DELETED);
         studentRepository.save(student);

    }

    @Override
    public StudentResponse getStudentByRollNumber(String rollNumber) {
          Student student =  studentRepository.findByRollNumberAndDeletedFalse(rollNumber)
                  .orElseThrow(() -> new ResourceNotFoundException("Student not found with roll Number "+rollNumber));

        return studentMapper.mapToStudentResponse(student);
    }

    @Override
    public StudentResponse getStudentByEmail(String email) {
           Student  student = studentRepository.findByEmailAndDeletedFalse(email)
                      .orElseThrow(()->new ResourceNotFoundException("Student not found with E-mail "+email));



        return studentMapper.mapToStudentResponse(student);
    }

    @Override
    public List<StudentResponse> getStudentsByCourse(Long courseId) {

       Course course = courseRepository.findById(courseId)
                    .orElseThrow(()->new ResourceNotFoundException("Course not found wth id" +courseId));

         Student  student= studentRepository.findByCourseCourseId(course.getCourseId())
                 .orElseThrow(()-> new ResourceNotFoundException("Student not found with course "+course.getCourseName()));

        return List.of(studentMapper.mapToStudentResponse(student));
    }

    @Override
    public List<StudentResponse> getStudentsByCity(String city) {
        List<Student> students = studentRepository.findByCityAndDeletedFalse(city);

        return students.stream()
                .map((student) ->studentMapper.mapToStudentResponse(student))
                .toList();

    }

    @Override
    public List<StudentResponse> getStudentsByStatus(String status) {

    List<Student> students = studentRepository.findByStatusAndDeletedFalse(status);

        return students.stream()
                .map(student -> studentMapper.mapToStudentResponse(student))
                .toList();

    }

    @Override
    public Long getStudentCount() {

      return studentRepository.countByDeletedFalse();
    }

    @Override
    public StudentResponse restoreStudent(Long id) {
       Student  student = studentRepository.findByIdAndDeletedTrue(id)
               .orElseThrow(() ->new ResourceNotFoundException("Deleted student not found"));
       student.setDeleted(false);
       student.setStatus(StudentStatus.DELETED);
       return studentMapper.mapToStudentResponse(student);

    }

    @Override
    public List<StudentResponse> getDeletedStudents() {
     List<Student>  students = studentRepository.findByDeletedTrue();

       return students.stream().map((student-> studentMapper.mapToStudentResponse(student))).toList();
    }

    @Override
    public StudentResponse getDeletedStudent(Long id) {
      Student student = studentRepository.findByIdAndDeletedTrue(id)
               .orElseThrow(()->new ResourceNotFoundException("Deleted student not found"));
        return studentMapper.mapToStudentResponse(student);
    }

    @Override
    public StudentResponse updateStudentStatus(Long id, StudentStatus status) {
           Student student = studentRepository.findByIdAndDeletedFalse(id)
                   .orElseThrow(()->new ResourceNotFoundException("Student not found"));
           student.setStatus(status);

          Student updateStudent = studentRepository.save(student);
         studentRepository.save(updateStudent);

       return studentMapper. mapToStudentResponse(student);
    }

    @Override
    public List<StudentResponse> getStudentsByAdmissionYear(Integer year) {

       LocalDate start = LocalDate.of(year,1,1);
       LocalDate end = LocalDate.of(year,12,31);

     List<Student> students = studentRepository.findByAdmissionDateBetweenAndDeletedFalse(start,end);
        return  students.stream().map(student ->studentMapper.mapToStudentResponse(student)).toList();
    }
}

