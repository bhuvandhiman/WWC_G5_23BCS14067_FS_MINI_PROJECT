package com.courseregistration.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    
    @Id
    @Column(unique = true, nullable = false)
    private int id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String course;
}
