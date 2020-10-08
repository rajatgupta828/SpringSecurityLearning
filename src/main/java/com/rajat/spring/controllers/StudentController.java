package com.rajat.spring.controllers;

import com.rajat.spring.models.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {

    private static final List<Student> STUDENTS = new ArrayList<>();

    @GetMapping(path = "{studentId}")
    public Student studentDetails(@PathVariable ("studentId") Integer studentId){
        STUDENTS.add(new Student(1, "Rajat Gupta"));
        STUDENTS.add(new Student(2, "Pankaj Dabas"));
        STUDENTS.add(new Student(3, "Spider Man"));
        STUDENTS.add(new Student(4, "James Bond"));

        return STUDENTS.stream().filter(
                student -> studentId.equals(student.getStudentId()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Student Not found"));
    }
}
