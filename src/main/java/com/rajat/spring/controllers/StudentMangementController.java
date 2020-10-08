package com.rajat.spring.controllers;

import com.rajat.spring.models.Student;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/management/api/v1/students")
public class StudentMangementController {

    private static List<Student> STUDENTS = new ArrayList<>();

    //add Students
    public List<Student> addStudents(){
        STUDENTS.add(new Student(1, "Rajat Gupta"));
        STUDENTS.add(new Student(2, "Pankaj Dabas"));
        STUDENTS.add(new Student(3, "Spider Man"));
        STUDENTS.add(new Student(4, "James Bond"));
        return STUDENTS;
    }

    @GetMapping()
    public List<Student> studentDetails(){
        STUDENTS = addStudents();
        return STUDENTS;
    }

    //Register a student
    @PostMapping
    public void registerStudent(@RequestBody  Student student){
        System.out.println(student);
    }

    //Delete a Student
    @DeleteMapping(path = "{studentId}")
    public void deleteStudent(Integer studentId){
        System.out.println(studentId);
    }

    //Update a method
    @PutMapping(path = "{studentID}")
    public void updateStudent(@PathVariable Integer studentID,@RequestBody Student student) {
        System.out.println(String.format("%s %s", studentID, student));
    }
}