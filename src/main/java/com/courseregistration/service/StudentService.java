package com.courseregistration.service;

import com.courseregistration.dto.StudentDTO;
import com.courseregistration.entity.Student;
import com.courseregistration.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {
    
    @Autowired
    private StudentRepository studentRepository;
    
    public StudentDTO registerStudent(StudentDTO studentDTO) {
        // Validate input
        if (studentDTO.getName() == null || studentDTO.getName().isEmpty()) {
            throw new IllegalArgumentException("Student name must not be null or empty");
        }
        if (studentDTO.getCourse() == null || studentDTO.getCourse().isEmpty()) {
            throw new IllegalArgumentException("Course must not be null or empty");
        }
        
        // Check for duplicate student ID
        Optional<Student> existingStudent = studentRepository.findById(studentDTO.getId());
        if (existingStudent.isPresent()) {
            throw new RuntimeException("Student with ID " + studentDTO.getId() + " already exists");
        }
        
        Student student = new Student();
        student.setId(studentDTO.getId());
        student.setName(studentDTO.getName());
        student.setCourse(studentDTO.getCourse());
        
        Student savedStudent = studentRepository.save(student);
        return convertToDTO(savedStudent);
    }
    
    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public StudentDTO getStudentById(int id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            throw new RuntimeException("Student with ID " + id + " not found");
        }
        return convertToDTO(student.get());
    }
    
    public void deleteStudent(int id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            throw new RuntimeException("Student with ID " + id + " not found");
        }
        studentRepository.deleteById(id);
    }
    
    private StudentDTO convertToDTO(Student student) {
        return new StudentDTO(student.getId(), student.getName(), student.getCourse());
    }
}
