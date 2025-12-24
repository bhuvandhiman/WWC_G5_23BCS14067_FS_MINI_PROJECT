package com.courseregistration.controller;

import com.courseregistration.dto.StudentDTO;
import com.courseregistration.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
@CrossOrigin(origins = "*", maxAge = 3600)
public class StudentController {
    
    @Autowired
    private StudentService studentService;
    
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> registerStudent(@RequestBody StudentDTO studentDTO) {
        try {
            StudentDTO savedStudent = studentService.registerStudent(studentDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedStudent);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            if (e.getMessage().contains("already exists")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        List<StudentDTO> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getStudentById(@PathVariable int id) {
        try {
            StudentDTO student = studentService.getStudentById(id);
            return ResponseEntity.ok(student);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteStudent(@PathVariable int id) {
        try {
            studentService.deleteStudent(id);
            return ResponseEntity.ok("Student deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
